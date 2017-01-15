package mineplex.core.personalServer;

import java.util.Random;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.jsonchat.ChildJsonMessage;
import mineplex.core.common.jsonchat.ClickEvent;
import mineplex.core.common.jsonchat.Color;
import mineplex.core.common.jsonchat.HoverEvent;
import mineplex.core.common.jsonchat.JsonMessage;
import mineplex.core.common.jsonchat.JsonMessage.MessageType;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.recharge.Recharge;
import mineplex.serverdata.Region;
import mineplex.serverdata.data.ServerGroup;
import mineplex.serverdata.servers.ServerManager;
import mineplex.serverdata.servers.ServerRepository;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class PersonalServerManager
  extends MiniPlugin
{
  private ServerRepository _repository;
  private CoreClientManager _clientManager;
  private boolean _us;
  private int _interfaceSlot = 6;
  private ItemStack _interfaceItem;
  private boolean _giveInterfaceItem = true;
  
  public PersonalServerManager(JavaPlugin plugin, CoreClientManager clientManager)
  {
    super("Personal Server Manager", plugin);
    
    this._clientManager = clientManager;
    
    setupConfigValues();
    
    this._us = plugin.getConfig().getBoolean("serverstatus.us");
    
    Region region = this._us ? Region.US : Region.EU;
    this._repository = ServerManager.getServerRepository(region);
    
    this._interfaceItem = ItemStackFactory.Instance.CreateStack(Material.SPECKLED_MELON, (byte)0, 1, C.cGreen + "/hostserver");
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent event)
  {
    if (this._giveInterfaceItem) {
      event.getPlayer().getInventory().setItem(this._interfaceSlot, this._interfaceItem);
    }
  }
  
  @EventHandler
  public void openServer(PlayerInteractEvent event)
  {
    if (this._interfaceItem.equals(event.getPlayer().getItemInHand()))
    {
      if (!Recharge.Instance.use(event.getPlayer(), "Host Server Melon", 30000L, false, false)) {
        return;
      }
      if (this._clientManager.Get(event.getPlayer()).GetRank().Has(Rank.LEGEND)) {
        showHostMessage(event.getPlayer());
      } else {
        UtilPlayer.message(event.getPlayer(), F.main("Server", "Only players with " + F.rank(Rank.LEGEND) + C.mBody + "+ can host private servers"));
      }
    }
  }
  
  public void showHostMessage(Player player)
  {
    UtilPlayer.message(player, C.cRed + "------------------------------------------------");
    UtilPlayer.message(player, "This will create a Mineplex Player Server for you.");
    UtilPlayer.message(player, "Here you can play your favorite games with friends!");
    
    new JsonMessage("Please ").click(ClickEvent.RUN_COMMAND, "/hostserver")
      .hover(HoverEvent.SHOW_TEXT, C.cGray + "Click to Create Server")
      .extra("CLICK HERE").color(Color.GREEN).extra(" to confirm you want to do this.")
      .color(Color.WHITE).send(JsonMessage.MessageType.CHAT_BOX, new Player[] { player });
    
    UtilPlayer.message(player, C.cRed + "------------------------------------------------");
  }
  
  public void addCommands()
  {
    addCommand(new HostServerCommand(this));
    addCommand(new HostEventServerCommand(this));
  }
  
  private void setupConfigValues()
  {
    try
    {
      getPlugin().getConfig().addDefault("serverstatus.us", Boolean.valueOf(true));
      getPlugin().getConfig().set("serverstatus.us", Boolean.valueOf(getPlugin().getConfig().getBoolean("serverstatus.us")));
      
      getPlugin().saveConfig();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void hostServer(Player player, String serverName, boolean eventServer)
  {
    int ram = 1024;
    int cpu = 1;
    
    Rank rank = this._clientManager.Get(player).GetRank();
    if ((eventServer) || (rank.Has(Rank.SNR_MODERATOR)) || (rank == Rank.YOUTUBE) || (rank == Rank.TWITCH))
    {
      ram = 2048;
      cpu = 4;
    }
    if (eventServer) {
      createGroup(player, "EVENT", ram, cpu, 40, 80, "Event", eventServer);
    } else {
      createGroup(player, serverName, ram, cpu, 40, 80, "Smash", eventServer);
    }
  }
  
  private void createGroup(final Player host, final String serverName, final int ram, final int cpu, final int minPlayers, final int maxPlayers, final String games, final boolean event)
  {
    getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        for (ServerGroup existingServerGroup : PersonalServerManager.this._repository.getServerGroups(null)) {
          if ((existingServerGroup.getPrefix().equalsIgnoreCase(serverName)) || (existingServerGroup.getName().equalsIgnoreCase(serverName)))
          {
            if (host.getName().equalsIgnoreCase(existingServerGroup.getHost())) {
              host.sendMessage(F.main(PersonalServerManager.this.getName(), "Your server is still being created or already exists.  If you haven't been connected in 20 seconds, type /server " + serverName + "-1."));
            } else {
              host.sendMessage(C.cRed + "Sorry, but you're not allowed to create a MPS server because you have chosen a name to glitch the system :)");
            }
            return;
          }
        }
        final ServerGroup serverGroup = new ServerGroup(serverName, serverName, host.getName(), ram, cpu, 1, 0, UtilMath.random.nextInt(250) + 19999, true, "arcade.zip", "Arcade.jar", "plugins/Arcade/", minPlayers, maxPlayers, 
          true, false, false, games, "Player", true, event, false, true, false, true, true, false, false, false, false, true, true, true, false, false, "", PersonalServerManager.this._us ? Region.US : Region.EU);
        
        PersonalServerManager.this.getPlugin().getServer().getScheduler().runTaskAsynchronously(PersonalServerManager.this.getPlugin(), new Runnable()
        {
          public void run()
          {
            PersonalServerManager.this._repository.updateServerGroup(serverGroup);
            Bukkit.getScheduler().runTask(PersonalServerManager.this.getPlugin(), new Runnable()
            {
              public void run()
              {
                this.val$host.sendMessage(F.main(PersonalServerManager.this.getName(), this.val$serverName + "-1 successfully created.  You will be sent to it shortly."));
                this.val$host.sendMessage(F.main(PersonalServerManager.this.getName(), "If you haven't been connected in 20 seconds, type /server " + this.val$serverName + "-1."));
              }
            });
          }
        });
      }
    });
  }
}

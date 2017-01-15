package mineplex.core.serverConfig;

import java.lang.reflect.Field;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.serverdata.Region;
import mineplex.serverdata.data.ServerGroup;
import mineplex.serverdata.servers.ServerManager;
import mineplex.serverdata.servers.ServerRepository;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PlayerList;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerConfiguration
  extends MiniPlugin
{
  private CoreClientManager _clientManager;
  private Field _playerListMaxPlayers;
  private ServerGroup _serverGroup;
  
  public ServerConfiguration(JavaPlugin plugin, CoreClientManager clientManager)
  {
    super("Server Configuration", plugin);
    
    this._clientManager = clientManager;
    Region region = plugin.getConfig().getBoolean("serverstatus.us") ? Region.US : Region.EU;
    String groupName = plugin.getConfig().getString("serverstatus.group");
    
    this._serverGroup = ServerManager.getServerRepository(region).getServerGroup(groupName);
    if (this._serverGroup == null) {
      return;
    }
    try
    {
      this._playerListMaxPlayers = PlayerList.class.getDeclaredField("maxPlayers");
      this._playerListMaxPlayers.setAccessible(true);
      this._playerListMaxPlayers.setInt(((CraftServer)this._plugin.getServer()).getHandle(), this._serverGroup.getMaxPlayers());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    this._plugin.getServer().setWhitelist(this._serverGroup.getWhitelist());
    ((CraftServer)this._plugin.getServer()).getServer().setPvP(this._serverGroup.getPvp());
  }
  
  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event)
  {
    if ((this._serverGroup.getStaffOnly()) && (!this._clientManager.Get(event.getPlayer().getName()).GetRank().Has(event.getPlayer(), Rank.HELPER, false))) {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "This is a staff only server.");
    }
  }
  
  public ServerGroup getServerGroup()
  {
    return this._serverGroup;
  }
}

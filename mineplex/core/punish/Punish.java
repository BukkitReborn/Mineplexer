package mineplex.core.punish;

import java.io.PrintStream;
import java.util.HashMap;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.account.event.ClientWebPunishResponseEvent;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.common.util.UtilTime.TimeUnit;
import mineplex.core.punish.Tokens.PunishClientToken;
import mineplex.core.punish.Tokens.PunishmentToken;
import mineplex.serverdata.commands.ServerCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Punish
  extends MiniPlugin
{
  private HashMap<String, PunishClient> _punishClients = new HashMap();
  private PunishRepository _repository;
  private CoreClientManager _clientManager;
  private String _mineplexName;
  private String _website;
  
  public Punish(JavaPlugin plugin, String webServerAddress, CoreClientManager clientManager, String mineplexName, String website)
  {
    super("Punish", plugin);
    this._clientManager = clientManager;
    this._repository = new PunishRepository(webServerAddress);
    ServerCommandManager.getInstance().registerCommandType("PunishCommand", mineplex.serverdata.commands.PunishCommand.class, new PunishmentHandler(this));
    
    this._mineplexName = mineplexName;
    this._website = website;
  }
  
  public PunishRepository GetRepository()
  {
    return this._repository;
  }
  
  public void addCommands()
  {
    addCommand(new mineplex.core.punish.Command.PunishCommand(this));
  }
  
  @EventHandler
  public void OnClientWebResponse(ClientWebPunishResponseEvent event)
  {
    PunishClientToken token = (PunishClientToken)new Gson().fromJson(event.GetResponse(), PunishClientToken.class);
    System.out.println("Punish Token Result:" + event.GetResponse());
    LoadClient(token);
  }
  
  @EventHandler
  public void PlayerQuit(PlayerQuitEvent event)
  {
    this._punishClients.remove(event.getPlayer().getName().toLowerCase());
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void PlayerLogin(AsyncPlayerPreLoginEvent event)
  {
    PunishClient client;
    if ((this._punishClients.containsKey(event.getName().toLowerCase())) && ((client = GetClient(event.getName())).IsBanned()))
    {
      Punishment punishment = client.GetPunishment(PunishmentSentence.Ban);
      String time = UtilTime.convertString(punishment.GetRemaining(), 0, UtilTime.TimeUnit.FIT);
      if (punishment.GetHours() == -1.0D) {
        time = "Permanent";
      }
      String reason = C.cRed + C.Bold + "You are banned for " + time + "\n" + C.cWhite + punishment.GetReason() + "\n" + C.cDGreen + "Unfairly banned? Appeal at " + C.cGreen + this._website;
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, reason);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void PunishChatEvent(AsyncPlayerChatEvent event)
  {
    PunishClient client = GetClient(event.getPlayer().getName());
    if ((client != null) && (client.IsMuted()))
    {
      event.getPlayer().sendMessage(F.main(getName(), "Shh, you're muted because " + client.GetPunishment(PunishmentSentence.Mute).GetReason() + " by " + client.GetPunishment(PunishmentSentence.Mute).GetAdmin() + " for " + C.cGreen + UtilTime.convertString(client.GetPunishment(PunishmentSentence.Mute).GetRemaining(), 1, UtilTime.TimeUnit.FIT) + "."));
      event.setCancelled(true);
    }
  }
  
  public void Help(Player caller)
  {
    UtilPlayer.message(caller, F.main(this._moduleName, "Commands List:"));
    UtilPlayer.message(caller, F.help("/punish", "<player> <reason>", Rank.MODERATOR));
  }
  
  public void AddPunishment(final String playerName, Category category, final String reason, final Player caller, int severity, boolean ban, long duration)
  {
    if (!this._punishClients.containsKey(playerName.toLowerCase())) {
      this._punishClients.put(playerName.toLowerCase(), new PunishClient());
    }
    PunishmentSentence sentence = !ban ? PunishmentSentence.Mute : PunishmentSentence.Ban;
    final long finalDuration = duration;
    this._repository.Punish(new Callback()
    {
      public void run(String result)
      {
        PunishmentResponse banResult = PunishmentResponse.valueOf(result);
        if (banResult == PunishmentResponse.AccountDoesNotExist)
        {
          if (caller != null) {
            caller.sendMessage(F.main(Punish.this.getName(), "Account with name " + F.elem(playerName) + " does not exist."));
          } else {
            System.out.println(F.main(Punish.this.getName(), "Account with name " + F.elem(playerName) + " does not exist."));
          }
        }
        else if (banResult == PunishmentResponse.InsufficientPrivileges)
        {
          if (caller != null) {
            caller.sendMessage(F.main(Punish.this.getName(), "You have insufficient rights to punish " + F.elem(playerName) + "."));
          } else {
            System.out.println(F.main(Punish.this.getName(), "You have insufficient rights to punish " + F.elem(playerName) + "."));
          }
        }
        else if (banResult == PunishmentResponse.Punished)
        {
          final String durationString = UtilTime.convertString(finalDuration < 0L ? -1L : finalDuration * 3600000L, 1, UtilTime.TimeUnit.FIT);
          if (reason == PunishmentSentence.Ban)
          {
            if (caller == null) {
              System.out.println(F.main(Punish.this.getName(), F.elem(caller == null ? Punish.this._mineplexName + " Anti-Cheat" : caller.getName()) + " banned " + F.elem(playerName) + " because of " + F.elem(this.val$reason) + " for " + durationString + "."));
            }
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Punish.this.getPlugin(), new Runnable()
            {
              public void run()
              {
                String kickReason = C.cRed + C.Bold + "You were banned for " + durationString + " by " + (this.val$caller == null ? Punish.this._mineplexName + " Anti-Cheat" : this.val$caller.getName()) + "\n" + C.cWhite + this.val$reason + "\n" + C.cDGreen + "Unfairly banned? Appeal at " + C.cGreen + Punish.this._website;
                Player target = UtilPlayer.searchOnline(null, this.val$playerName, false);
                if (target != null) {
                  target.kickPlayer(kickReason);
                } else {
                  new mineplex.serverdata.commands.PunishCommand(this.val$playerName, true, false, kickReason).publish();
                }
              }
            });
            Punish.this.informOfPunish(playerName, F.main(Punish.this.getName(), caller.getName() + " banned " + playerName + " for " + durationString + "."));
          }
          else
          {
            if (caller == null) {
              System.out.println(F.main(Punish.this.getName(), F.elem(caller == null ? Punish.this._mineplexName + " Anti-Cheat" : caller.getName()) + " muted " + F.elem(playerName) + " because of " + F.elem(this.val$reason) + " for " + durationString + "."));
            }
            if (finalDuration == 0L) {
              Punish.this.informOfPunish(playerName, F.main(Punish.this.getName(), caller.getName() + " issued a friendly warning to " + playerName + "."));
            } else {
              Punish.this.informOfPunish(playerName, F.main(Punish.this.getName(), caller.getName() + " muted " + playerName + " for " + durationString + "."));
            }
            Player target = UtilPlayer.searchExact(playerName);
            if (target != null)
            {
              UtilPlayer.message(target, F.main("Punish", F.elem(new StringBuilder().append(C.cGray).append(C.Bold).append("Reason: ").toString()) + this.val$reason));
              target.playSound(target.getLocation(), Sound.CAT_MEOW, 1.0F, 1.0F);
            }
            else
            {
              new mineplex.serverdata.commands.PunishCommand(playerName, false, finalDuration != 0L, F.main("Punish", F.elem(new StringBuilder().append(C.cGray).append(C.Bold).append(finalDuration != 0L ? "Mute" : "Warning").append(" Reason: ").toString()) + this.val$reason)).publish();
            }
            Punish.this._repository.LoadPunishClient(playerName, new Callback()
            {
              public void run(PunishClientToken token)
              {
                Punish.this.LoadClient(token);
              }
            });
          }
        }
      }
    }, playerName, category.toString(), sentence, reason, duration, caller == null ? this._mineplexName + " Anti-Cheat" : caller.getName(), severity);
  }
  
  private void informOfPunish(String punishee, String msg)
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if ((this._clientManager.Get(player).GetRank().Has(Rank.HELPER)) || (player.getName().equals(punishee))) {
        player.sendMessage(msg);
      }
    }
  }
  
  public void LoadClient(PunishClientToken token)
  {
    PunishClient client = new PunishClient();
    long timeDifference = System.currentTimeMillis() - token.Time;
    for (PunishmentToken punishment : token.Punishments) {
      client.AddPunishment(Category.valueOf(punishment.Category), new Punishment(punishment.PunishmentId, PunishmentSentence.valueOf(punishment.Sentence), Category.valueOf(punishment.Category), punishment.Reason, punishment.Admin, punishment.Duration, punishment.Severity, punishment.Time + timeDifference, punishment.Active, punishment.Removed, punishment.RemoveAdmin, punishment.RemoveReason));
    }
    this._punishClients.put(token.Name.toLowerCase(), client);
  }
  
  /* Error */
  public PunishClient GetClient(String name)
  {
    // Byte code:
    //   0: aload_0
    //   1: astore_2
    //   2: aload_2
    //   3: dup
    //   4: astore_3
    //   5: monitorenter
    //   6: aload_0
    //   7: getfield 29	mineplex/core/punish/Punish:_punishClients	Ljava/util/HashMap;
    //   10: aload_1
    //   11: invokevirtual 146	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   14: invokevirtual 473	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   17: checkcast 173	mineplex/core/punish/PunishClient
    //   20: aload_3
    //   21: monitorexit
    //   22: areturn
    //   23: aload_3
    //   24: monitorexit
    //   25: athrow
    // Line number table:
    //   Java source line #231	-> byte code offset #0
    //   Java source line #232	-> byte code offset #2
    //   Java source line #233	-> byte code offset #6
    //   Java source line #232	-> byte code offset #23
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	26	0	this	Punish
    //   0	26	1	name	String
    //   1	2	2	punish	Punish
    //   4	20	3	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   6	22	23	finally
    //   23	25	23	finally
  }
  
  public void RemovePunishment(int punishmentId, String target, Player admin, String reason, Callback<String> callback)
  {
    this._repository.RemovePunishment(callback, punishmentId, target, reason, admin.getName());
  }
  
  public void RemoveBan(String name, String reason)
  {
    this._repository.RemoveBan(name, reason);
  }
  
  public CoreClientManager GetClients()
  {
    return this._clientManager;
  }
  
  public int factorial(int n)
  {
    if (n == 0) {
      return 1;
    }
    return n * factorial(n - 1);
  }
}

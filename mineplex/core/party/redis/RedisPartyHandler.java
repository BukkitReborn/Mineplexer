package mineplex.core.party.redis;

import mineplex.core.party.Party;
import mineplex.core.party.PartyManager;
import mineplex.serverdata.commands.CommandCallback;
import mineplex.serverdata.commands.ServerCommand;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class RedisPartyHandler
  implements CommandCallback
{
  private PartyManager _partyManager;
  
  public RedisPartyHandler(PartyManager partyManager)
  {
    this._partyManager = partyManager;
  }
  
  public void run(ServerCommand command)
  {
    final RedisPartyData data = (RedisPartyData)command;
    
    this._partyManager.getPlugin().getServer().getScheduler().runTask(this._partyManager.getPlugin(), new Runnable()
    {
      public void run()
      {
        RedisPartyHandler.this._partyManager.addParty(new Party(RedisPartyHandler.this._partyManager, data));
      }
    });
  }
}

package mineplex.core.teleport.redis;

import mineplex.core.teleport.Teleport;
import mineplex.serverdata.commands.CommandCallback;
import mineplex.serverdata.commands.ServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RedisLocateHandler
  implements CommandCallback
{
  private Teleport _plugin;
  private String _serverName;
  
  public RedisLocateHandler(Teleport plugin)
  {
    this._plugin = plugin;
    this._serverName = this._plugin.getPlugin().getConfig().getString("serverstatus.name");
  }
  
  public void run(ServerCommand command)
  {
    if ((command instanceof RedisLocate))
    {
      RedisLocate locate = (RedisLocate)command;
      
      Player target = Bukkit.getPlayerExact(locate.getTarget());
      if (target != null)
      {
        RedisLocateCallback callback = new RedisLocateCallback(locate, this._serverName, target.getName());
        callback.publish();
      }
    }
    else if ((command instanceof RedisLocateCallback))
    {
      this._plugin.handleLocateCallback((RedisLocateCallback)command);
    }
  }
}

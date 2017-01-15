package mineplex.core.resourcepack;

import mineplex.core.resourcepack.redis.RedisUnloadResPack;
import mineplex.serverdata.commands.CommandCallback;
import mineplex.serverdata.commands.ServerCommand;
import mineplex.serverdata.commands.ServerCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ResPackManager
  implements CommandCallback
{
  private ResUnloadCheck _packUnloadCheck;
  
  public ResPackManager(ResUnloadCheck packUnloadCheck)
  {
    this._packUnloadCheck = packUnloadCheck;
    
    ServerCommandManager.getInstance().registerCommandType("RedisUnloadResPack", RedisUnloadResPack.class, this);
  }
  
  public void run(ServerCommand command)
  {
    if ((command instanceof RedisUnloadResPack))
    {
      RedisUnloadResPack redisCommand = (RedisUnloadResPack)command;
      
      Player player = Bukkit.getPlayerExact(redisCommand.getPlayer());
      if (player != null) {
        if (this._packUnloadCheck.canSendUnload(player)) {
          player.setResourcePack("http://www.chivebox.com/file/c/empty.zip");
        }
      }
    }
  }
}

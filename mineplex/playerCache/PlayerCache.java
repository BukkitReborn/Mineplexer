package mineplex.playerCache;

import java.util.UUID;
import mineplex.serverdata.Region;
import mineplex.serverdata.redis.RedisDataRepository;
import mineplex.serverdata.servers.ServerManager;

public class PlayerCache
{
  private RedisDataRepository<PlayerInfo> _repository;
  
  public PlayerCache()
  {
    this._repository = new RedisDataRepository(
      ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), 
      
      Region.ALL, 
      PlayerInfo.class, 
      "playercache");
  }
  
  public void addPlayer(PlayerInfo player)
  {
    this._repository.addElement(player, 21600);
  }
  
  public PlayerInfo getPlayer(UUID uuid)
  {
    return (PlayerInfo)this._repository.getElement(uuid.toString());
  }
  
  public void clean()
  {
    this._repository.clean();
  }
}

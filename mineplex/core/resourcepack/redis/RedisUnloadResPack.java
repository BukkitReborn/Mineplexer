package mineplex.core.resourcepack.redis;

import mineplex.serverdata.commands.ServerCommand;

public class RedisUnloadResPack
  extends ServerCommand
{
  private String _player;
  
  public RedisUnloadResPack(String player)
  {
    super(new String[0]);
    
    this._player = player;
  }
  
  public String getPlayer()
  {
    return this._player;
  }
}

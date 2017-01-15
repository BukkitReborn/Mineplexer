package mineplex.core.party.redis;

import java.util.Collection;
import mineplex.core.party.Party;
import mineplex.serverdata.commands.ServerCommand;

public class RedisPartyData
  extends ServerCommand
{
  private String[] _players;
  private String _leader;
  private String _previousServer;
  
  public RedisPartyData(Party party, String previousServer)
  {
    super(new String[0]);
    
    this._players = ((String[])party.GetPlayers().toArray(new String[0]));
    this._leader = party.GetLeader();
    this._previousServer = previousServer;
  }
  
  public String getPreviousServer()
  {
    return this._previousServer;
  }
  
  public String[] getPlayers()
  {
    return this._players;
  }
  
  public String getLeader()
  {
    return this._leader;
  }
}

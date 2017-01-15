package mineplex.core.friend.redis;

import mineplex.serverdata.commands.ServerCommand;

public class FriendRequest
  extends ServerCommand
{
  private String _requester;
  private String _requested;
  
  public String getRequester()
  {
    return this._requester;
  }
  
  public String getRequested()
  {
    return this._requested;
  }
  
  public FriendRequest(String requester, String requested)
  {
    super(new String[0]);
    
    this._requester = requester;
    this._requested = requested;
  }
  
  public void run() {}
}

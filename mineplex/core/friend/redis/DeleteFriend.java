package mineplex.core.friend.redis;

import mineplex.serverdata.commands.ServerCommand;

public class DeleteFriend
  extends ServerCommand
{
  private String _deleter;
  private String _deleted;
  
  public String getDeleter()
  {
    return this._deleter;
  }
  
  public String getDeleted()
  {
    return this._deleted;
  }
  
  public DeleteFriend(String deleter, String deleted)
  {
    super(new String[0]);
    
    this._deleter = deleter;
    this._deleted = deleted;
  }
  
  public void run() {}
}

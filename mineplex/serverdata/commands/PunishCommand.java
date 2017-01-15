package mineplex.serverdata.commands;

public class PunishCommand
  extends ServerCommand
{
  private String _playerName;
  private boolean _ban;
  private boolean _mute;
  private String _message;
  
  public String getPlayerName()
  {
    return this._playerName;
  }
  
  public boolean getBan()
  {
    return this._ban;
  }
  
  public boolean getMute()
  {
    return this._mute;
  }
  
  public String getMessage()
  {
    return this._message;
  }
  
  public PunishCommand(String playerName, boolean ban, boolean mute, String message)
  {
    super(new String[0]);
    
    this._playerName = playerName;
    this._ban = ban;
    this._mute = mute;
    this._message = message;
  }
  
  public void run() {}
}

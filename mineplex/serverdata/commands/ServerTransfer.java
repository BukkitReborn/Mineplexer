package mineplex.serverdata.commands;

public class ServerTransfer
{
  private String _playerName;
  private String _serverName;
  
  public String getPlayerName()
  {
    return this._playerName;
  }
  
  public String getServerName()
  {
    return this._serverName;
  }
  
  public ServerTransfer(String playerName, String serverName)
  {
    this._playerName = playerName;
    this._serverName = serverName;
  }
}

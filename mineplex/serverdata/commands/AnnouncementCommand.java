package mineplex.serverdata.commands;

public class AnnouncementCommand
  extends ServerCommand
{
  private boolean _displayTitle;
  private String _message;
  
  public boolean getDisplayTitle()
  {
    return this._displayTitle;
  }
  
  public String getMessage()
  {
    return this._message;
  }
  
  public AnnouncementCommand(boolean displayTitle, String message)
  {
    super(new String[0]);
    
    this._displayTitle = displayTitle;
    this._message = message;
  }
  
  public void run() {}
}

package mineplex.core.chat;

public class MessageData
{
  private String _message;
  private long _timeSent;
  
  public MessageData(String message)
  {
    this(message, System.currentTimeMillis());
  }
  
  public MessageData(String message, long timeSent)
  {
    this._message = message;
    this._timeSent = timeSent;
  }
  
  public String getMessage()
  {
    return this._message;
  }
  
  public long getTimeSent()
  {
    return this._timeSent;
  }
}

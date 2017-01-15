package mineplex.serverdata.commands;

public class CommandType
{
  private Class<? extends ServerCommand> _commandClazz;
  private CommandCallback _commandCallback;
  
  public Class<? extends ServerCommand> getCommandType()
  {
    return this._commandClazz;
  }
  
  public CommandCallback getCallback()
  {
    return this._commandCallback;
  }
  
  public CommandType(Class<? extends ServerCommand> commandClazz, CommandCallback commandCallback)
  {
    this._commandClazz = commandClazz;
    this._commandCallback = commandCallback;
  }
}

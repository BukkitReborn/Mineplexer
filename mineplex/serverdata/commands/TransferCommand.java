package mineplex.serverdata.commands;

public class TransferCommand
  extends ServerCommand
{
  private ServerTransfer _transfer;
  
  public ServerTransfer getTransfer()
  {
    return this._transfer;
  }
  
  public TransferCommand(ServerTransfer transfer)
  {
    super(new String[0]);
    
    this._transfer = transfer;
  }
  
  public void run() {}
}

package mineplex.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import mineplex.core.account.CoreClientManager;
import mineplex.core.account.ILoginProcessor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MiniDbClientPlugin<DataType>
  extends MiniClientPlugin<DataType>
  implements ILoginProcessor
{
  protected CoreClientManager ClientManager = null;
  
  public MiniDbClientPlugin(String moduleName, JavaPlugin plugin, CoreClientManager clientManager)
  {
    super(moduleName, plugin);
    this.ClientManager = clientManager;
    clientManager.addStoredProcedureLoginProcessor(this);
  }
  
  public abstract void processLoginResultSet(String paramString, int paramInt, ResultSet paramResultSet)
    throws SQLException;
  
  public CoreClientManager getClientManager()
  {
    return this.ClientManager;
  }
}

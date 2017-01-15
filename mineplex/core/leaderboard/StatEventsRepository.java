package mineplex.core.leaderboard;

import mineplex.core.database.DBPool;
import mineplex.core.database.RepositoryBase;
import mineplex.core.database.column.Column;
import mineplex.core.database.column.ColumnInt;
import mineplex.core.database.column.ColumnVarChar;
import org.bukkit.plugin.java.JavaPlugin;

public class StatEventsRepository
  extends RepositoryBase
{
  private static String CREATE_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS statsEvents (eventId INT(10), accountId INT(10), date DATE, gamemode TINYINT(3), serverGroup VARCHAR(100), type TINYINT(3), value SMALLINT(5), PRIMARY KEY (eventId, accountId, date, gamemode, serverGroup, type));";
  private static String INSERT_EVENT = "INSERT INTO statEvents(accountId, gamemode, serverGroup, type, value, date) SELECT accounts.id, ?, ?, ?, ?, CURRENT_DATE() FROM accounts WHERE name = ? ON DUPLICATE KEY UPDATE value=value+";
  
  public StatEventsRepository(JavaPlugin plugin)
  {
    super(plugin, DBPool.ACCOUNT);
  }
  
  protected void initialize() {}
  
  protected void update() {}
  
  public void insertStatEvent(String playerName, int gamemode, String serverGroup, int type, int value)
  {
    executeUpdate(INSERT_EVENT + value + ";", new Column[] { new ColumnInt("gamemode", gamemode), new ColumnVarChar("serverGroup", 100, serverGroup), 
      new ColumnInt("type", type), new ColumnInt("value", value), new ColumnVarChar("name", 100, playerName) });
  }
}

package mineplex.core.report;

import mineplex.core.database.DBPool;
import mineplex.core.database.RepositoryBase;
import mineplex.core.database.column.Column;
import mineplex.core.database.column.ColumnInt;
import mineplex.core.database.column.ColumnVarChar;
import org.bukkit.plugin.java.JavaPlugin;

public class ReportRepository
  extends RepositoryBase
{
  private static String CREATE_TICKET_TABLE = "CREATE TABLE IF NOT EXISTS reportTickets (reportId INT NOT NULL, date LONG, eventDate LONG, playerId INT NOT NULL, server VARCHAR(50), closerId INT NOT NULL, result VARCHAR(25), reason VARCHAR(100), PRIMARY KEY (reportId), INDEX playerIdIndex (playerId), INDEX closerIdIndex (closerId));";
  private static String CREATE_HANDLER_TABLE = "CREATE TABLE IF NOT EXISTS reportHandlers (id INT NOT NULL AUTO_INCREMENT, reportId INT NOT NULL, eventDate LONG, handlerId INT NOT NULL, PRIMARY KEY (id), INDEX handlerIdIndex (handlerId) );";
  private static String CREATE_REPORTERS_TABLE = "CREATE TABLE IF NOT EXISTS reportSenders (id INT NOT NULL AUTO_INCREMENT, reportId INT NOT NULL, eventDate LONG, reporterId INT NOT NULL, reason VARCHAR(100), PRIMARY KEY (id), INDEX reporterIdIndex (reporterId));";
  private static String INSERT_TICKET = "INSERT INTO reportTickets (reportId, eventDate, playerId, server, closerId, result, reason) VALUES (?, now(), ?, ?, ?, ?, ?);";
  private static String INSERT_HANDLER = "INSERT INTO reportHandlers (eventDate, reportId, handlerId) VALUES(now(), ?, ?);";
  private static String INSERT_SENDER = "INSERT INTO reportSenders (eventDate, reportId, reporterId, reason) VALUES(now(), ?, ?, ?);";
  
  public ReportRepository(JavaPlugin plugin, String connectionString)
  {
    super(plugin, DBPool.ACCOUNT);
  }
  
  protected void initialize()
  {
    executeUpdate(CREATE_TICKET_TABLE, new Column[0]);
    executeUpdate(CREATE_HANDLER_TABLE, new Column[0]);
    executeUpdate(CREATE_REPORTERS_TABLE, new Column[0]);
  }
  
  protected void update() {}
  
  public void logReportHandling(int reportId, int handlerId)
  {
    executeUpdate(INSERT_HANDLER, new Column[] { new ColumnInt("reportId", reportId), new ColumnInt("handlerId", handlerId) });
  }
  
  public void logReportSending(int reportId, int reporterId, String reason)
  {
    executeUpdate(INSERT_SENDER, new Column[] { new ColumnInt("reportId", reportId), new ColumnInt("reporterId", reporterId), 
      new ColumnVarChar("reason", 100, reason) });
  }
  
  public void logReport(int reportId, int playerId, String server, int closerId, ReportResult result, String reason)
  {
    executeUpdate(INSERT_TICKET, new Column[] { new ColumnInt("reportId", reportId), new ColumnInt("playerId", playerId), 
      new ColumnVarChar("server", 50, server), new ColumnInt("closerId", closerId), 
      new ColumnVarChar("result", 25, result.toString()), new ColumnVarChar("reason", 100, reason) });
  }
}

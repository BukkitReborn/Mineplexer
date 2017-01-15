package mineplex.core.report;

import java.util.HashSet;
import java.util.Set;
import mineplex.serverdata.data.Data;

public class Report
  implements Data
{
  private int _reportId;
  private String _serverName;
  private String _playerName;
  private Set<String> _reporters;
  
  public int getReportId()
  {
    return this._reportId;
  }
  
  public String getServerName()
  {
    return this._serverName;
  }
  
  public String getPlayerName()
  {
    return this._playerName;
  }
  
  public Set<String> getReporters()
  {
    return this._reporters;
  }
  
  public void addReporter(String reporter)
  {
    this._reporters.add(reporter);
  }
  
  public Report(int reportId, String playerName, String serverName)
  {
    this._reportId = reportId;
    this._playerName = playerName;
    this._serverName = serverName;
    this._reporters = new HashSet();
  }
  
  public String getDataId()
  {
    return String.valueOf(this._reportId);
  }
}

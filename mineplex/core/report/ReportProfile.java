package mineplex.core.report;

import mineplex.serverdata.data.Data;

public class ReportProfile
  implements Data
{
  private String _playerName;
  private int _playerId;
  private int _totalReports;
  private int _successfulReports;
  private int _reputation;
  private boolean _banned;
  
  public int getReputation()
  {
    return this._reputation;
  }
  
  public ReportProfile(String playerName, int playerId)
  {
    this._playerName = playerName;
    this._playerId = playerId;
    this._totalReports = 0;
    this._successfulReports = 0;
    this._reputation = 0;
    this._banned = false;
  }
  
  public String getDataId()
  {
    return String.valueOf(this._playerId);
  }
  
  public boolean canReport()
  {
    return !this._banned;
  }
  
  public void onReportClose(ReportResult result)
  {
    this._totalReports += 1;
    if ((result == ReportResult.MUTED) || (result == ReportResult.BANNED))
    {
      this._successfulReports += 1;
      this._reputation += 1;
    }
    else if (result == ReportResult.ABUSE)
    {
      this._reputation = -1;
      this._banned = true;
    }
  }
}

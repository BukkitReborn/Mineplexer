package mineplex.core.report;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandCenter;
import mineplex.core.portal.Portal;
import mineplex.core.report.command.ReportNotification;
import mineplex.serverdata.Region;
import mineplex.serverdata.data.DataRepository;
import mineplex.serverdata.redis.RedisDataRepository;
import org.bukkit.entity.Player;

public class ReportManager
{
  private static ReportManager instance;
  private DataRepository<Report> reportRepository;
  private DataRepository<ReportProfile> reportProfiles;
  private ReportRepository reportSqlRepository;
  private Map<String, Integer> activeReports;
  
  private ReportManager()
  {
    this.reportRepository = new RedisDataRepository(Region.ALL, Report.class, "reports");
    this.reportProfiles = new RedisDataRepository(Region.ALL, ReportProfile.class, "reportprofiles");
    this.activeReports = new HashMap();
    
    this.reportSqlRepository = new ReportRepository(ReportPlugin.getPluginInstance(), "CONNECTION STRING HERE");
    this.reportSqlRepository.initialize();
  }
  
  public void retrieveReportResult(int reportId, Player reportCloser, String reason) {}
  
  public void closeReport(int reportId, Player reportCloser, String reason)
  {
    retrieveReportResult(reportId, reportCloser, reason);
  }
  
  public void closeReport(int reportId, Player reportCloser, String reason, ReportResult result)
  {
    if (isActiveReport(reportId))
    {
      Report report = getReport(reportId);
      this.reportRepository.removeElement(String.valueOf(reportId));
      removeActiveReport(reportId);
      
      int closerId = getPlayerAccount(reportCloser).getAccountId();
      String playerName = getReport(reportId).getPlayerName();
      int playerId = getPlayerAccount(playerName).getAccountId();
      String server = null;
      this.reportSqlRepository.logReport(reportId, playerId, server, closerId, result, reason);
      for (String reporterName : report.getReporters())
      {
        CoreClient reporterAccount = getPlayerAccount(reporterName);
        ReportProfile reportProfile = getReportProfile(String.valueOf(reporterAccount.getAccountId()));
        reportProfile.onReportClose(result);
        this.reportProfiles.addElement(reportProfile);
      }
      if (reportCloser != null) {
        sendReportNotification(String.format("[Report %d] %s closed this report. (%s).", new Object[] { Integer.valueOf(reportId), 
          reportCloser.getName(), result.toDisplayMessage() }));
      }
    }
  }
  
  public void handleReport(int reportId, Player reportHandler)
  {
    if (this.reportRepository.elementExists(String.valueOf(reportId)))
    {
      Report report = getReport(reportId);
      Portal.transferPlayer(reportHandler.getName(), report.getServerName());
      String handlerName = reportHandler.getName();
      sendReportNotification(String.format("[Report %d] %s is handling this report.", new Object[] { Integer.valueOf(reportId), handlerName }));
      
      int handlerId = getPlayerAccount(reportHandler).getAccountId();
      this.reportSqlRepository.logReportHandling(reportId, handlerId);
    }
  }
  
  public void reportPlayer(Player reporter, Player reportedPlayer, String reason)
  {
    int reporterId = getPlayerAccount(reporter).getAccountId();
    ReportProfile reportProfile = getReportProfile(String.valueOf(reporterId));
    if (reportProfile.canReport())
    {
      Report report = null;
      if (hasActiveReport(reportedPlayer))
      {
        int reportId = getActiveReport(reportedPlayer.getName());
        report = getReport(reportId);
        report.addReporter(reporter.getName());
      }
      else
      {
        String serverName = null;
        int reportId = generateReportId();
        report = new Report(reportId, reportedPlayer.getName(), serverName);
        report.addReporter(reporter.getName());
        this.activeReports.put(reportedPlayer.getName().toLowerCase(), Integer.valueOf(report.getReportId()));
        this.reportRepository.addElement(report);
      }
      if (report != null)
      {
        String message = String.format("[Report %d] [%s %d] [%s - %d - %s]", new Object[] { Integer.valueOf(report.getReportId()), 
          reporter.getName(), Integer.valueOf(reportProfile.getReputation()), 
          reportedPlayer.getName(), Integer.valueOf(report.getReporters().size()), reason });
        sendReportNotification(message);
        this.reportSqlRepository.logReportSending(report.getReportId(), reporterId, reason);
      }
    }
  }
  
  public void onPlayerQuit(Player player)
  {
    if (hasActiveReport(player))
    {
      int reportId = getActiveReport(player.getName());
      closeReport(reportId, null, "Player Quit", ReportResult.UNDETERMINED);
      
      sendReportNotification(String.format("[Report %d] %s has left the game.", new Object[] { Integer.valueOf(reportId), player.getName() }));
    }
  }
  
  public ReportProfile getReportProfile(String playerName)
  {
    ReportProfile profile = (ReportProfile)this.reportProfiles.getElement(playerName);
    if (profile == null)
    {
      profile = new ReportProfile(playerName, getAccountId(playerName));
      saveReportProfile(profile);
    }
    return profile;
  }
  
  private void saveReportProfile(ReportProfile profile)
  {
    this.reportProfiles.addElement(profile);
  }
  
  /* Error */
  public int generateReportId()
  {
    // Byte code:
    //   0: iconst_1
    //   1: invokestatic 299	mineplex/serverdata/Utility:getPool	(Z)Lredis/clients/jedis/JedisPool;
    //   4: astore_1
    //   5: aload_1
    //   6: invokevirtual 305	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   9: checkcast 310	redis/clients/jedis/Jedis
    //   12: astore_2
    //   13: ldc2_w 312
    //   16: lstore_3
    //   17: aload_2
    //   18: ldc_w 314
    //   21: invokevirtual 316	redis/clients/jedis/Jedis:incr	(Ljava/lang/String;)Ljava/lang/Long;
    //   24: invokevirtual 320	java/lang/Long:longValue	()J
    //   27: lstore_3
    //   28: goto +43 -> 71
    //   31: astore 5
    //   33: aload 5
    //   35: invokevirtual 326	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   38: aload_1
    //   39: aload_2
    //   40: invokevirtual 331	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   43: aconst_null
    //   44: astore_2
    //   45: aload_2
    //   46: ifnull +34 -> 80
    //   49: aload_1
    //   50: aload_2
    //   51: invokevirtual 335	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   54: goto +26 -> 80
    //   57: astore 6
    //   59: aload_2
    //   60: ifnull +8 -> 68
    //   63: aload_1
    //   64: aload_2
    //   65: invokevirtual 335	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   68: aload 6
    //   70: athrow
    //   71: aload_2
    //   72: ifnull +8 -> 80
    //   75: aload_1
    //   76: aload_2
    //   77: invokevirtual 335	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   80: lload_3
    //   81: l2i
    //   82: ireturn
    // Line number table:
    //   Java source line #193	-> byte code offset #0
    //   Java source line #194	-> byte code offset #5
    //   Java source line #195	-> byte code offset #13
    //   Java source line #199	-> byte code offset #17
    //   Java source line #200	-> byte code offset #28
    //   Java source line #201	-> byte code offset #31
    //   Java source line #203	-> byte code offset #33
    //   Java source line #204	-> byte code offset #38
    //   Java source line #205	-> byte code offset #43
    //   Java source line #209	-> byte code offset #45
    //   Java source line #211	-> byte code offset #49
    //   Java source line #208	-> byte code offset #57
    //   Java source line #209	-> byte code offset #59
    //   Java source line #211	-> byte code offset #63
    //   Java source line #213	-> byte code offset #68
    //   Java source line #209	-> byte code offset #71
    //   Java source line #211	-> byte code offset #75
    //   Java source line #215	-> byte code offset #80
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	83	0	this	ReportManager
    //   4	72	1	pool	redis.clients.jedis.JedisPool
    //   12	65	2	jedis	redis.clients.jedis.Jedis
    //   16	65	3	uniqueReportId	long
    //   31	3	5	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   57	12	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   17	28	31	redis/clients/jedis/exceptions/JedisConnectionException
    //   17	45	57	finally
  }
  
  public Report getReport(int reportId)
  {
    return (Report)this.reportRepository.getElement(String.valueOf(reportId));
  }
  
  private CoreClient getPlayerAccount(Player player)
  {
    return getPlayerAccount(player.getName());
  }
  
  private CoreClient getPlayerAccount(String playerName)
  {
    return CommandCenter.Instance.GetClientManager().Get(playerName);
  }
  
  private int getAccountId(String playerName)
  {
    return getPlayerAccount(playerName).getAccountId();
  }
  
  public boolean hasReportNotifications(Player player)
  {
    return false;
  }
  
  public void sendReportNotification(String message)
  {
    ReportNotification reportNotification = new ReportNotification(message);
    reportNotification.publish();
  }
  
  public int getActiveReport(String playerName)
  {
    if (this.activeReports.containsKey(playerName.toLowerCase())) {
      return ((Integer)this.activeReports.get(playerName.toLowerCase())).intValue();
    }
    return -1;
  }
  
  public boolean hasActiveReport(Player player)
  {
    return getActiveReport(player.getName()) != -1;
  }
  
  public boolean isActiveReport(int reportId)
  {
    for (Map.Entry<String, Integer> activeReport : this.activeReports.entrySet()) {
      if (((Integer)activeReport.getValue()).intValue() == reportId) {
        return true;
      }
    }
    return false;
  }
  
  public boolean removeActiveReport(int reportId)
  {
    for (Map.Entry<String, Integer> activeReport : this.activeReports.entrySet()) {
      if (((Integer)activeReport.getValue()).intValue() == reportId)
      {
        this.activeReports.remove(activeReport.getKey());
        return true;
      }
    }
    return false;
  }
  
  public static ReportManager getInstance()
  {
    if (instance == null) {
      instance = new ReportManager();
    }
    return instance;
  }
}

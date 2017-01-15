package mineplex.core.chat;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.achievement.AchievementManager;
import mineplex.core.chat.command.BroadcastCommand;
import mineplex.core.chat.command.ChatSlowCommand;
import mineplex.core.chat.command.SilenceCommand;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilText;
import mineplex.core.common.util.UtilTime;
import mineplex.core.common.util.UtilTime.TimeUnit;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.preferences.UserPreferences;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Chat
  extends MiniPlugin
{
  private CoreClientManager _clientManager;
  private PreferencesManager _preferences;
  private AchievementManager _achievements;
  private String[] _hackusations = { "hack", "hax", "hacker", "hacking", "cheat", "cheater", "cheating", "forcefield", "flyhack", "flyhacking", "autoclick", "aimbot" };
  private String _filterUrl = "https://10.33.53.5:8003/content/item/moderate";
  private String _appId = "34018d65-466d-4a91-8e92-29ca49f022c4";
  private String _apiKey = "oUywMpwZcIzZO5AWnfDx";
  private String _serverName;
  private int _chatSlow = 0;
  private long _silenced = 0L;
  private boolean _threeSecondDelay = true;
  private HashMap<UUID, MessageData> _playerLastMessage = new HashMap();
  private String _website;
  
  public Chat(JavaPlugin plugin, CoreClientManager clientManager, PreferencesManager preferences, AchievementManager achievements, String serverName, String website)
  {
    super("Chat", plugin);
    this._clientManager = clientManager;
    this._serverName = serverName;
    this._preferences = preferences;
    this._achievements = achievements;
    
    this._website = website;
    try
    {
      trustCert();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void addCommands()
  {
    addCommand(new SilenceCommand(this));
    addCommand(new BroadcastCommand(this));
    addCommand(new ChatSlowCommand(this));
  }
  
  public void setChatSlow(int seconds, boolean inform)
  {
    if (seconds < 0) {
      seconds = 0;
    }
    this._chatSlow = seconds;
    if (inform) {
      if (seconds == 0) {
        UtilServer.broadcast(F.main("Chat", "Chat Slow is now disabled"));
      } else {
        UtilServer.broadcast(F.main("Chat", "Chat slow is now enabled with a cooldown of " + F.time(new StringBuilder().append(seconds).append(" seconds").toString())));
      }
    }
  }
  
  public void Silence(long duration, boolean inform)
  {
    this._silenced = (duration > 0L ? System.currentTimeMillis() + duration : duration);
    if (!inform) {
      return;
    }
    if (duration == -1L) {
      UtilServer.broadcast(F.main("Chat", "Chat has been silenced for " + F.time("Permanent") + "."));
    } else if (duration == 0L) {
      UtilServer.broadcast(F.main("Chat", "Chat is no longer silenced."));
    } else {
      UtilServer.broadcast(F.main("Chat", "Chat has been silenced for " + F.time(UtilTime.MakeStr(duration, 1)) + "."));
    }
  }
  
  @EventHandler
  public void preventMe(PlayerCommandPreprocessEvent event)
  {
    if ((event.getMessage().toLowerCase().startsWith("/me ")) || (event.getMessage().toLowerCase().startsWith("/bukkit")))
    {
      event.getPlayer().sendMessage(F.main(getName(), "No, you!"));
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void lagTest(PlayerCommandPreprocessEvent event)
  {
    if ((event.getMessage().equals("lag")) || (event.getMessage().equals("ping")))
    {
      event.getPlayer().sendMessage(F.main(getName(), "PONG!"));
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void SilenceUpdate(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    SilenceEnd();
  }
  
  public void SilenceEnd()
  {
    if (this._silenced <= 0L) {
      return;
    }
    if (System.currentTimeMillis() > this._silenced) {
      Silence(0L, true);
    }
  }
  
  public boolean SilenceCheck(Player player)
  {
    SilenceEnd();
    if (this._silenced == 0L) {
      return false;
    }
    if (this._clientManager.Get(player).GetRank().Has(player, Rank.MODERATOR, false)) {
      return false;
    }
    if (this._silenced == -1L) {
      UtilPlayer.message(player, F.main(getName(), "Chat is silenced permanently."));
    } else {
      UtilPlayer.message(player, F.main(getName(), "Chat is silenced for " + F.time(UtilTime.MakeStr(this._silenced - System.currentTimeMillis(), 1)) + "."));
    }
    return true;
  }
  
  @EventHandler
  public void removeChat(AsyncPlayerChatEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (event.isAsynchronous()) {
      for (Iterator<Player> playerIterator = event.getRecipients().iterator(); playerIterator.hasNext();) {
        if (!((UserPreferences)this._preferences.Get((Player)playerIterator.next())).ShowChat) {
          playerIterator.remove();
        }
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onSignChange(SignChangeEvent event)
  {
    if (this._clientManager.Get(event.getPlayer()).GetRank().Has(Rank.ADMIN)) {
      return;
    }
    if (SilenceCheck(event.getPlayer()))
    {
      event.setCancelled(true);
      return;
    }
    for (int i = 0; i < event.getLines().length; i++)
    {
      String line = event.getLine(i);
      String filteredLine;
      if ((line != null) && (line.length() > 0) && ((filteredLine = getFilteredMessage(event.getPlayer(), line)) != null))
      {
        String filteredLine;
        event.setLine(i, filteredLine);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void filterChat(AsyncPlayerChatEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (event.isAsynchronous())
    {
      String filteredMessage = getFilteredMessage(event.getPlayer(), event.getMessage());
      for (Player onlinePlayer : event.getRecipients()) {
        onlinePlayer.sendMessage(String.format(event.getFormat(), new Object[] { event.getPlayer().getDisplayName(), filteredMessage }));
      }
      event.setCancelled(true);
    }
  }
  
  public String getFilteredMessage(Player player, String originalMessage)
  {
    return originalMessage;
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void HandleChat(AsyncPlayerChatEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    Player sender = event.getPlayer();
    if (SilenceCheck(sender))
    {
      event.setCancelled(true);
      return;
    }
    if ((this._threeSecondDelay) && (this._clientManager.Get(sender).GetRank() == Rank.ALL) && (this._achievements.getMineplexLevelNumber(sender, Rank.ALL) < 25) && (!Recharge.Instance.use(sender, "All Chat Message", 3000L, false, false)))
    {
      UtilPlayer.message(sender, C.cYellow + "You can only chat once every 3 seconds to prevent spam.");
      UtilPlayer.message(sender, C.cYellow + "Buy a Rank at " + C.cGreen + this._website + C.cYellow + " to remove this limit!");
      event.setCancelled(true);
    }
    else if ((!this._clientManager.Get(sender).GetRank().Has(Rank.MODERATOR)) && (!Recharge.Instance.use(sender, "Chat Message", 400L, false, false)))
    {
      UtilPlayer.message(sender, F.main("Chat", "You are sending messages too fast."));
      event.setCancelled(true);
    }
    else if ((!this._clientManager.Get(sender).GetRank().Has(Rank.HELPER)) && (msgContainsHack(event.getMessage())))
    {
      UtilPlayer.message(sender, F.main("Chat", "Accusing players of cheating in-game is against the rules.If you think someone is cheating, please gather evidence and report it at " + F.link(this._website)));
      event.setCancelled(true);
    }
    else if (this._playerLastMessage.containsKey(sender.getUniqueId()))
    {
      MessageData lastMessage = (MessageData)this._playerLastMessage.get(sender.getUniqueId());
      long chatSlowTime = 1000L * this._chatSlow;
      long timeDiff = System.currentTimeMillis() - lastMessage.getTimeSent();
      if ((timeDiff < chatSlowTime) && (!this._clientManager.Get(sender).GetRank().Has(Rank.HELPER)))
      {
        UtilPlayer.message(sender, F.main("Chat", "Chat slow enabled. Please wait " + F.time(UtilTime.convertString(chatSlowTime - timeDiff, 1, UtilTime.TimeUnit.FIT))));
        event.setCancelled(true);
      }
      else if ((!this._clientManager.Get(sender).GetRank().Has(Rank.MODERATOR)) && (UtilText.isStringSimilar(event.getMessage(), lastMessage.getMessage(), 0.8F)))
      {
        UtilPlayer.message(sender, F.main("Chat", "This message is too similar to your previous message."));
        event.setCancelled(true);
      }
    }
    if (!event.isCancelled()) {
      this._playerLastMessage.put(sender.getUniqueId(), new MessageData(event.getMessage()));
    }
  }
  
  private boolean msgContainsHack(String msg)
  {
    msg = " " + msg.toLowerCase().replaceAll("[^a-z ]", "") + " ";
    String[] arrayOfString;
    int j = (arrayOfString = this._hackusations).length;
    for (int i = 0; i < j; i++)
    {
      String s = arrayOfString[i];
      if (msg.contains(" " + s + " ")) {
        return true;
      }
    }
    return false;
  }
  
  public String hasher(JSONArray hasharray, String message)
  {
    StringBuilder newmsg = new StringBuilder(message);
    for (int i = 0; i < hasharray.size(); i++)
    {
      Long charindex = (Long)hasharray.get(i);
      int charidx = charindex.intValue();
      newmsg.setCharAt(charidx, '*');
    }
    return newmsg.toString();
  }
  
  public JSONArray parseHashes(String response)
  {
    JSONObject checkhash = (JSONObject)JSONValue.parse(response);
    JSONArray hasharray = (JSONArray)checkhash.get("hashes");
    return hasharray;
  }
  
  private JSONObject buildJsonChatObject(String filtertype, String name, String player, String msg, String server, int rule)
  {
    JSONObject message = new JSONObject();
    String str;
    switch ((str = filtertype).hashCode())
    {
    case -618857213: 
      if (str.equals("moderate")) {
        break;
      }
      break;
    case -265713450: 
      if (str.equals("username")) {}
    case 3052376: 
      if ((goto 285) && (str.equals("chat")))
      {
        message.put("content", msg);
        break label285;
        JSONObject content = new JSONObject();
        content.put("content", msg);
        content.put("type", "text");
        JSONArray parts = new JSONArray();
        parts.add(content);
        JSONObject mainContent = new JSONObject();
        mainContent.put("applicationId", this._appId);
        mainContent.put("createInstant", Long.valueOf(System.currentTimeMillis()));
        mainContent.put("parts", parts);
        mainContent.put("senderDisplayName", name);
        mainContent.put("senderId", player);
        message.put("content", mainContent);
        break label285;
        message.put("player_id", name);
        message.put("username", name);
        message.put("language", "en");
        message.put("rule", Integer.valueOf(rule));
      }
      break;
    }
    label285:
    return message;
  }
  
  /* Error */
  private String getResponseFromCleanSpeak(JSONObject message, String filtertype)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 67	mineplex/core/chat/Chat:_filterUrl	Ljava/lang/String;
    //   4: astore_3
    //   5: aconst_null
    //   6: astore 4
    //   8: aconst_null
    //   9: astore 5
    //   11: aconst_null
    //   12: astore 6
    //   14: aconst_null
    //   15: astore 7
    //   17: aconst_null
    //   18: astore 8
    //   20: new 645	java/net/URL
    //   23: dup
    //   24: aload_3
    //   25: invokespecial 647	java/net/URL:<init>	(Ljava/lang/String;)V
    //   28: astore 10
    //   30: aload 10
    //   32: invokevirtual 648	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   35: checkcast 652	javax/net/ssl/HttpsURLConnection
    //   38: astore 5
    //   40: aload 5
    //   42: ldc_w 654
    //   45: ldc_w 656
    //   48: invokevirtual 658	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   51: aload 5
    //   53: ldc_w 664
    //   56: ldc_w 666
    //   59: invokevirtual 658	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   62: aload 5
    //   64: ldc_w 668
    //   67: aload_0
    //   68: getfield 75	mineplex/core/chat/Chat:_apiKey	Ljava/lang/String;
    //   71: invokevirtual 670	java/net/HttpURLConnection:addRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   74: aload_1
    //   75: invokevirtual 673	org/json/simple/JSONObject:toString	()Ljava/lang/String;
    //   78: astore 11
    //   80: aload 5
    //   82: iconst_1
    //   83: invokevirtual 674	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   86: new 677	java/io/DataOutputStream
    //   89: dup
    //   90: aload 5
    //   92: invokevirtual 679	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   95: invokespecial 683	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   98: astore 6
    //   100: aload 6
    //   102: aload 11
    //   104: invokevirtual 686	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   107: aload 6
    //   109: invokevirtual 689	java/io/DataOutputStream:flush	()V
    //   112: aload 6
    //   114: invokevirtual 692	java/io/DataOutputStream:close	()V
    //   117: new 695	java/io/InputStreamReader
    //   120: dup
    //   121: aload 5
    //   123: invokevirtual 697	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   126: ldc_w 701
    //   129: invokestatic 703	java/nio/charset/Charset:forName	(Ljava/lang/String;)Ljava/nio/charset/Charset;
    //   132: invokespecial 709	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
    //   135: astore 8
    //   137: new 712	java/io/BufferedReader
    //   140: dup
    //   141: aload 8
    //   143: invokespecial 714	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   146: astore 7
    //   148: new 717	java/lang/StringBuffer
    //   151: dup
    //   152: invokespecial 719	java/lang/StringBuffer:<init>	()V
    //   155: astore 4
    //   157: goto +11 -> 168
    //   160: aload 4
    //   162: aload 9
    //   164: invokevirtual 720	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   167: pop
    //   168: aload 7
    //   170: invokevirtual 723	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   173: dup
    //   174: astore 9
    //   176: ifnonnull -16 -> 160
    //   179: aload 7
    //   181: invokevirtual 726	java/io/BufferedReader:close	()V
    //   184: goto +210 -> 394
    //   187: astore 9
    //   189: getstatic 727	java/lang/System:out	Ljava/io/PrintStream;
    //   192: new 162	java/lang/StringBuilder
    //   195: dup
    //   196: ldc_w 731
    //   199: invokespecial 166	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   202: aload 9
    //   204: invokevirtual 733	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   207: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: invokevirtual 178	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   213: invokevirtual 734	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   216: aload 5
    //   218: ifnull +8 -> 226
    //   221: aload 5
    //   223: invokevirtual 739	java/net/HttpURLConnection:disconnect	()V
    //   226: aload 6
    //   228: ifnull +33 -> 261
    //   231: aload 6
    //   233: invokevirtual 689	java/io/DataOutputStream:flush	()V
    //   236: goto +10 -> 246
    //   239: astore 13
    //   241: aload 13
    //   243: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   246: aload 6
    //   248: invokevirtual 692	java/io/DataOutputStream:close	()V
    //   251: goto +10 -> 261
    //   254: astore 13
    //   256: aload 13
    //   258: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   261: aload 7
    //   263: ifnull +18 -> 281
    //   266: aload 7
    //   268: invokevirtual 726	java/io/BufferedReader:close	()V
    //   271: goto +10 -> 281
    //   274: astore 13
    //   276: aload 13
    //   278: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   281: aload 8
    //   283: ifnull +196 -> 479
    //   286: aload 8
    //   288: invokevirtual 745	java/io/InputStreamReader:close	()V
    //   291: goto +188 -> 479
    //   294: astore 13
    //   296: aload 13
    //   298: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   301: goto +178 -> 479
    //   304: astore 12
    //   306: aload 5
    //   308: ifnull +8 -> 316
    //   311: aload 5
    //   313: invokevirtual 739	java/net/HttpURLConnection:disconnect	()V
    //   316: aload 6
    //   318: ifnull +33 -> 351
    //   321: aload 6
    //   323: invokevirtual 689	java/io/DataOutputStream:flush	()V
    //   326: goto +10 -> 336
    //   329: astore 13
    //   331: aload 13
    //   333: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   336: aload 6
    //   338: invokevirtual 692	java/io/DataOutputStream:close	()V
    //   341: goto +10 -> 351
    //   344: astore 13
    //   346: aload 13
    //   348: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   351: aload 7
    //   353: ifnull +18 -> 371
    //   356: aload 7
    //   358: invokevirtual 726	java/io/BufferedReader:close	()V
    //   361: goto +10 -> 371
    //   364: astore 13
    //   366: aload 13
    //   368: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   371: aload 8
    //   373: ifnull +18 -> 391
    //   376: aload 8
    //   378: invokevirtual 745	java/io/InputStreamReader:close	()V
    //   381: goto +10 -> 391
    //   384: astore 13
    //   386: aload 13
    //   388: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   391: aload 12
    //   393: athrow
    //   394: aload 5
    //   396: ifnull +8 -> 404
    //   399: aload 5
    //   401: invokevirtual 739	java/net/HttpURLConnection:disconnect	()V
    //   404: aload 6
    //   406: ifnull +33 -> 439
    //   409: aload 6
    //   411: invokevirtual 689	java/io/DataOutputStream:flush	()V
    //   414: goto +10 -> 424
    //   417: astore 13
    //   419: aload 13
    //   421: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   424: aload 6
    //   426: invokevirtual 692	java/io/DataOutputStream:close	()V
    //   429: goto +10 -> 439
    //   432: astore 13
    //   434: aload 13
    //   436: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   439: aload 7
    //   441: ifnull +18 -> 459
    //   444: aload 7
    //   446: invokevirtual 726	java/io/BufferedReader:close	()V
    //   449: goto +10 -> 459
    //   452: astore 13
    //   454: aload 13
    //   456: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   459: aload 8
    //   461: ifnull +18 -> 479
    //   464: aload 8
    //   466: invokevirtual 745	java/io/InputStreamReader:close	()V
    //   469: goto +10 -> 479
    //   472: astore 13
    //   474: aload 13
    //   476: invokevirtual 742	java/io/IOException:printStackTrace	()V
    //   479: aconst_null
    //   480: astore 9
    //   482: aload 4
    //   484: ifnull +10 -> 494
    //   487: aload 4
    //   489: invokevirtual 746	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   492: astore 9
    //   494: aload 9
    //   496: areturn
    // Line number table:
    //   Java source line #347	-> byte code offset #0
    //   Java source line #348	-> byte code offset #5
    //   Java source line #349	-> byte code offset #8
    //   Java source line #350	-> byte code offset #11
    //   Java source line #351	-> byte code offset #14
    //   Java source line #352	-> byte code offset #17
    //   Java source line #355	-> byte code offset #20
    //   Java source line #356	-> byte code offset #30
    //   Java source line #357	-> byte code offset #40
    //   Java source line #358	-> byte code offset #51
    //   Java source line #359	-> byte code offset #62
    //   Java source line #360	-> byte code offset #74
    //   Java source line #361	-> byte code offset #80
    //   Java source line #362	-> byte code offset #86
    //   Java source line #363	-> byte code offset #100
    //   Java source line #364	-> byte code offset #107
    //   Java source line #365	-> byte code offset #112
    //   Java source line #366	-> byte code offset #117
    //   Java source line #367	-> byte code offset #137
    //   Java source line #368	-> byte code offset #148
    //   Java source line #369	-> byte code offset #157
    //   Java source line #370	-> byte code offset #160
    //   Java source line #369	-> byte code offset #168
    //   Java source line #372	-> byte code offset #179
    //   Java source line #373	-> byte code offset #184
    //   Java source line #374	-> byte code offset #187
    //   Java source line #375	-> byte code offset #189
    //   Java source line #378	-> byte code offset #216
    //   Java source line #379	-> byte code offset #221
    //   Java source line #381	-> byte code offset #226
    //   Java source line #383	-> byte code offset #231
    //   Java source line #384	-> byte code offset #236
    //   Java source line #385	-> byte code offset #239
    //   Java source line #386	-> byte code offset #241
    //   Java source line #389	-> byte code offset #246
    //   Java source line #390	-> byte code offset #251
    //   Java source line #391	-> byte code offset #254
    //   Java source line #392	-> byte code offset #256
    //   Java source line #395	-> byte code offset #261
    //   Java source line #397	-> byte code offset #266
    //   Java source line #398	-> byte code offset #271
    //   Java source line #399	-> byte code offset #274
    //   Java source line #400	-> byte code offset #276
    //   Java source line #403	-> byte code offset #281
    //   Java source line #405	-> byte code offset #286
    //   Java source line #406	-> byte code offset #291
    //   Java source line #407	-> byte code offset #294
    //   Java source line #408	-> byte code offset #296
    //   Java source line #377	-> byte code offset #304
    //   Java source line #378	-> byte code offset #306
    //   Java source line #379	-> byte code offset #311
    //   Java source line #381	-> byte code offset #316
    //   Java source line #383	-> byte code offset #321
    //   Java source line #384	-> byte code offset #326
    //   Java source line #385	-> byte code offset #329
    //   Java source line #386	-> byte code offset #331
    //   Java source line #389	-> byte code offset #336
    //   Java source line #390	-> byte code offset #341
    //   Java source line #391	-> byte code offset #344
    //   Java source line #392	-> byte code offset #346
    //   Java source line #395	-> byte code offset #351
    //   Java source line #397	-> byte code offset #356
    //   Java source line #398	-> byte code offset #361
    //   Java source line #399	-> byte code offset #364
    //   Java source line #400	-> byte code offset #366
    //   Java source line #403	-> byte code offset #371
    //   Java source line #405	-> byte code offset #376
    //   Java source line #406	-> byte code offset #381
    //   Java source line #407	-> byte code offset #384
    //   Java source line #408	-> byte code offset #386
    //   Java source line #411	-> byte code offset #391
    //   Java source line #378	-> byte code offset #394
    //   Java source line #379	-> byte code offset #399
    //   Java source line #381	-> byte code offset #404
    //   Java source line #383	-> byte code offset #409
    //   Java source line #384	-> byte code offset #414
    //   Java source line #385	-> byte code offset #417
    //   Java source line #386	-> byte code offset #419
    //   Java source line #389	-> byte code offset #424
    //   Java source line #390	-> byte code offset #429
    //   Java source line #391	-> byte code offset #432
    //   Java source line #392	-> byte code offset #434
    //   Java source line #395	-> byte code offset #439
    //   Java source line #397	-> byte code offset #444
    //   Java source line #398	-> byte code offset #449
    //   Java source line #399	-> byte code offset #452
    //   Java source line #400	-> byte code offset #454
    //   Java source line #403	-> byte code offset #459
    //   Java source line #405	-> byte code offset #464
    //   Java source line #406	-> byte code offset #469
    //   Java source line #407	-> byte code offset #472
    //   Java source line #408	-> byte code offset #474
    //   Java source line #412	-> byte code offset #479
    //   Java source line #413	-> byte code offset #482
    //   Java source line #414	-> byte code offset #487
    //   Java source line #416	-> byte code offset #494
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	497	0	this	Chat
    //   0	497	1	message	JSONObject
    //   0	497	2	filtertype	String
    //   4	21	3	url	String
    //   6	482	4	response	StringBuffer
    //   9	391	5	connection	java.net.HttpURLConnection
    //   12	413	6	outputStream	java.io.DataOutputStream
    //   15	430	7	bufferedReader	java.io.BufferedReader
    //   18	447	8	inputStream	java.io.InputStreamReader
    //   160	3	9	inputLine	String
    //   174	3	9	inputLine	String
    //   187	16	9	exception	Exception
    //   480	15	9	pmresponse	String
    //   28	3	10	obj	java.net.URL
    //   78	25	11	urlParameters	String
    //   304	88	12	localObject	Object
    //   239	3	13	e	java.io.IOException
    //   254	3	13	e	java.io.IOException
    //   274	3	13	e	java.io.IOException
    //   294	3	13	e	java.io.IOException
    //   329	3	13	e	java.io.IOException
    //   344	3	13	e	java.io.IOException
    //   364	3	13	e	java.io.IOException
    //   384	3	13	e	java.io.IOException
    //   417	3	13	e	java.io.IOException
    //   432	3	13	e	java.io.IOException
    //   452	3	13	e	java.io.IOException
    //   472	3	13	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   20	184	187	java/lang/Exception
    //   231	236	239	java/io/IOException
    //   246	251	254	java/io/IOException
    //   266	271	274	java/io/IOException
    //   286	291	294	java/io/IOException
    //   20	216	304	finally
    //   321	326	329	java/io/IOException
    //   336	341	344	java/io/IOException
    //   356	361	364	java/io/IOException
    //   376	381	384	java/io/IOException
    //   409	414	417	java/io/IOException
    //   424	429	432	java/io/IOException
    //   444	449	452	java/io/IOException
    //   464	469	472	java/io/IOException
  }
  
  public static void trustCert()
    throws Exception
  {
    TrustManager[] trustAllCerts = { new X509TrustManager()
    {
      public X509Certificate[] getAcceptedIssuers()
      {
        return null;
      }
      
      public void checkClientTrusted(X509Certificate[] certs, String authType) {}
      
      public void checkServerTrusted(X509Certificate[] certs, String authType) {}
    } };
    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    HostnameVerifier allHostsValid = new HostnameVerifier()
    {
      public boolean verify(String hostname, SSLSession session)
      {
        return true;
      }
    };
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
  }
  
  public long Silenced()
  {
    return this._silenced;
  }
  
  @EventHandler
  public void playerQuit(PlayerQuitEvent event)
  {
    this._playerLastMessage.remove(event.getPlayer().getUniqueId());
  }
  
  public void setThreeSecondDelay(boolean b)
  {
    this._threeSecondDelay = b;
  }
}

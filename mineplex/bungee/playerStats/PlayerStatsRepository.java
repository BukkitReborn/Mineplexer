package mineplex.bungee.playerStats;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import mineplex.core.database.DBPool;

public class PlayerStatsRepository
{
  private Connection _connection = null;
  private static String INSERT_PLAYERINFO = "INSERT INTO playerInfo (uuid, name, version) VALUES (?, ?, ?);";
  private static String SELECT_PLAYERINFO = "SELECT id, name, version FROM playerInfo WHERE uuid = ?;";
  private static String UPDATE_PLAYERINFO = "UPDATE playerInfo SET name = ?, version = ? WHERE id = ?;";
  private static String INSERT_IPINFO = "INSERT INTO ipInfo (ipAddress) VALUES (?);";
  private static String SELECT_IPINFO = "SELECT id FROM ipInfo WHERE ipAddress = ?;";
  private static String UPDATE_PLAYERSTATS = "INSERT IGNORE INTO playerIps (playerInfoId, ipInfoId, date) VALUES (?, ?, curdate());";
  private static String UPDATE_LOGINSESSION = "UPDATE playerLoginSessions SET timeInGame = TIME_TO_SEC(TIMEDIFF(now(), loginTime)) / 60 WHERE id = ?;";
  
  public void initialize()
  {
    try
    {
      if ((this._connection == null) || (this._connection.isClosed())) {
        this._connection = DriverManager.getConnection(DBPool.getUrl() + "/Account", DBPool.getUser(), DBPool.getPass());
      }
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
    System.out.println("Initialized PlayerStats.");
  }
  
  /* Error */
  public mineplex.playerCache.PlayerInfo getPlayer(java.util.UUID uuid, String name, int version)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore 6
    //   9: aload_0
    //   10: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   13: ifnull +15 -> 28
    //   16: aload_0
    //   17: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   20: invokeinterface 56 1 0
    //   25: ifeq +37 -> 62
    //   28: aload_0
    //   29: new 62	java/lang/StringBuilder
    //   32: dup
    //   33: invokestatic 64	mineplex/core/database/DBPool:getUrl	()Ljava/lang/String;
    //   36: invokestatic 70	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   39: invokespecial 76	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   42: ldc 79
    //   44: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   50: invokestatic 88	mineplex/core/database/DBPool:getUser	()Ljava/lang/String;
    //   53: invokestatic 91	mineplex/core/database/DBPool:getPass	()Ljava/lang/String;
    //   56: invokestatic 94	java/sql/DriverManager:getConnection	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/sql/Connection;
    //   59: putfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   62: aload_0
    //   63: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   66: getstatic 24	mineplex/bungee/playerStats/PlayerStatsRepository:SELECT_PLAYERINFO	Ljava/lang/String;
    //   69: invokeinterface 123 2 0
    //   74: astore 5
    //   76: aload 5
    //   78: iconst_1
    //   79: aload_1
    //   80: invokevirtual 127	java/util/UUID:toString	()Ljava/lang/String;
    //   83: invokeinterface 130 3 0
    //   88: aload 5
    //   90: invokeinterface 136 1 0
    //   95: astore 6
    //   97: goto +37 -> 134
    //   100: new 140	mineplex/playerCache/PlayerInfo
    //   103: dup
    //   104: aload 6
    //   106: iconst_1
    //   107: invokeinterface 142 2 0
    //   112: aload_1
    //   113: aload 6
    //   115: iconst_2
    //   116: invokeinterface 148 2 0
    //   121: aload 6
    //   123: iconst_3
    //   124: invokeinterface 142 2 0
    //   129: invokespecial 152	mineplex/playerCache/PlayerInfo:<init>	(ILjava/util/UUID;Ljava/lang/String;I)V
    //   132: astore 4
    //   134: aload 6
    //   136: invokeinterface 155 1 0
    //   141: ifne -41 -> 100
    //   144: aload 6
    //   146: invokeinterface 158 1 0
    //   151: aload 5
    //   153: invokeinterface 161 1 0
    //   158: aload 4
    //   160: ifnonnull +122 -> 282
    //   163: aload_0
    //   164: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   167: getstatic 20	mineplex/bungee/playerStats/PlayerStatsRepository:INSERT_PLAYERINFO	Ljava/lang/String;
    //   170: iconst_1
    //   171: invokeinterface 162 3 0
    //   176: astore 5
    //   178: aload 5
    //   180: iconst_1
    //   181: aload_1
    //   182: invokevirtual 127	java/util/UUID:toString	()Ljava/lang/String;
    //   185: invokeinterface 130 3 0
    //   190: aload 5
    //   192: iconst_2
    //   193: aload_2
    //   194: invokeinterface 130 3 0
    //   199: aload 5
    //   201: iconst_3
    //   202: iload_3
    //   203: invokeinterface 165 3 0
    //   208: aload 5
    //   210: invokeinterface 169 1 0
    //   215: pop
    //   216: iconst_0
    //   217: istore 7
    //   219: aload 5
    //   221: invokeinterface 173 1 0
    //   226: astore 6
    //   228: goto +13 -> 241
    //   231: aload 6
    //   233: iconst_1
    //   234: invokeinterface 142 2 0
    //   239: istore 7
    //   241: aload 6
    //   243: invokeinterface 155 1 0
    //   248: ifne -17 -> 231
    //   251: new 140	mineplex/playerCache/PlayerInfo
    //   254: dup
    //   255: iload 7
    //   257: aload_1
    //   258: aload_2
    //   259: iload_3
    //   260: invokespecial 152	mineplex/playerCache/PlayerInfo:<init>	(ILjava/util/UUID;Ljava/lang/String;I)V
    //   263: astore 4
    //   265: aload 6
    //   267: invokeinterface 158 1 0
    //   272: aload 5
    //   274: invokeinterface 161 1 0
    //   279: goto +190 -> 469
    //   282: aload 4
    //   284: invokevirtual 176	mineplex/playerCache/PlayerInfo:getName	()Ljava/lang/String;
    //   287: aload_2
    //   288: invokevirtual 179	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   291: ifeq +12 -> 303
    //   294: aload 4
    //   296: invokevirtual 183	mineplex/playerCache/PlayerInfo:getVersion	()I
    //   299: iload_3
    //   300: if_icmpeq +169 -> 469
    //   303: aload_0
    //   304: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   307: getstatic 28	mineplex/bungee/playerStats/PlayerStatsRepository:UPDATE_PLAYERINFO	Ljava/lang/String;
    //   310: invokeinterface 123 2 0
    //   315: astore 5
    //   317: aload 5
    //   319: iconst_1
    //   320: aload_2
    //   321: invokeinterface 130 3 0
    //   326: aload 5
    //   328: iconst_2
    //   329: iload_3
    //   330: invokeinterface 165 3 0
    //   335: aload 5
    //   337: iconst_3
    //   338: aload 4
    //   340: invokevirtual 186	mineplex/playerCache/PlayerInfo:getId	()I
    //   343: invokeinterface 165 3 0
    //   348: aload 5
    //   350: invokeinterface 169 1 0
    //   355: pop
    //   356: aload 5
    //   358: invokeinterface 161 1 0
    //   363: goto +106 -> 469
    //   366: astore 7
    //   368: aload 7
    //   370: invokevirtual 100	java/lang/Exception:printStackTrace	()V
    //   373: aload 5
    //   375: ifnull +20 -> 395
    //   378: aload 5
    //   380: invokeinterface 161 1 0
    //   385: goto +10 -> 395
    //   388: astore 9
    //   390: aload 9
    //   392: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   395: aload 6
    //   397: ifnull +116 -> 513
    //   400: aload 6
    //   402: invokeinterface 158 1 0
    //   407: goto +106 -> 513
    //   410: astore 9
    //   412: aload 9
    //   414: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   417: goto +96 -> 513
    //   420: astore 8
    //   422: aload 5
    //   424: ifnull +20 -> 444
    //   427: aload 5
    //   429: invokeinterface 161 1 0
    //   434: goto +10 -> 444
    //   437: astore 9
    //   439: aload 9
    //   441: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   444: aload 6
    //   446: ifnull +20 -> 466
    //   449: aload 6
    //   451: invokeinterface 158 1 0
    //   456: goto +10 -> 466
    //   459: astore 9
    //   461: aload 9
    //   463: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   466: aload 8
    //   468: athrow
    //   469: aload 5
    //   471: ifnull +20 -> 491
    //   474: aload 5
    //   476: invokeinterface 161 1 0
    //   481: goto +10 -> 491
    //   484: astore 9
    //   486: aload 9
    //   488: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   491: aload 6
    //   493: ifnull +20 -> 513
    //   496: aload 6
    //   498: invokeinterface 158 1 0
    //   503: goto +10 -> 513
    //   506: astore 9
    //   508: aload 9
    //   510: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   513: aload 4
    //   515: areturn
    // Line number table:
    //   Java source line #48	-> byte code offset #0
    //   Java source line #49	-> byte code offset #3
    //   Java source line #50	-> byte code offset #6
    //   Java source line #54	-> byte code offset #9
    //   Java source line #55	-> byte code offset #28
    //   Java source line #57	-> byte code offset #62
    //   Java source line #59	-> byte code offset #76
    //   Java source line #61	-> byte code offset #88
    //   Java source line #63	-> byte code offset #97
    //   Java source line #65	-> byte code offset #100
    //   Java source line #63	-> byte code offset #134
    //   Java source line #68	-> byte code offset #144
    //   Java source line #69	-> byte code offset #151
    //   Java source line #71	-> byte code offset #158
    //   Java source line #73	-> byte code offset #163
    //   Java source line #74	-> byte code offset #178
    //   Java source line #75	-> byte code offset #190
    //   Java source line #76	-> byte code offset #199
    //   Java source line #78	-> byte code offset #208
    //   Java source line #80	-> byte code offset #216
    //   Java source line #82	-> byte code offset #219
    //   Java source line #84	-> byte code offset #228
    //   Java source line #86	-> byte code offset #231
    //   Java source line #84	-> byte code offset #241
    //   Java source line #89	-> byte code offset #251
    //   Java source line #91	-> byte code offset #265
    //   Java source line #92	-> byte code offset #272
    //   Java source line #93	-> byte code offset #279
    //   Java source line #94	-> byte code offset #282
    //   Java source line #96	-> byte code offset #303
    //   Java source line #97	-> byte code offset #317
    //   Java source line #98	-> byte code offset #326
    //   Java source line #99	-> byte code offset #335
    //   Java source line #101	-> byte code offset #348
    //   Java source line #102	-> byte code offset #356
    //   Java source line #104	-> byte code offset #363
    //   Java source line #105	-> byte code offset #366
    //   Java source line #107	-> byte code offset #368
    //   Java source line #111	-> byte code offset #373
    //   Java source line #115	-> byte code offset #378
    //   Java source line #116	-> byte code offset #385
    //   Java source line #117	-> byte code offset #388
    //   Java source line #119	-> byte code offset #390
    //   Java source line #123	-> byte code offset #395
    //   Java source line #127	-> byte code offset #400
    //   Java source line #128	-> byte code offset #407
    //   Java source line #129	-> byte code offset #410
    //   Java source line #131	-> byte code offset #412
    //   Java source line #110	-> byte code offset #420
    //   Java source line #111	-> byte code offset #422
    //   Java source line #115	-> byte code offset #427
    //   Java source line #116	-> byte code offset #434
    //   Java source line #117	-> byte code offset #437
    //   Java source line #119	-> byte code offset #439
    //   Java source line #123	-> byte code offset #444
    //   Java source line #127	-> byte code offset #449
    //   Java source line #128	-> byte code offset #456
    //   Java source line #129	-> byte code offset #459
    //   Java source line #131	-> byte code offset #461
    //   Java source line #134	-> byte code offset #466
    //   Java source line #111	-> byte code offset #469
    //   Java source line #115	-> byte code offset #474
    //   Java source line #116	-> byte code offset #481
    //   Java source line #117	-> byte code offset #484
    //   Java source line #119	-> byte code offset #486
    //   Java source line #123	-> byte code offset #491
    //   Java source line #127	-> byte code offset #496
    //   Java source line #128	-> byte code offset #503
    //   Java source line #129	-> byte code offset #506
    //   Java source line #131	-> byte code offset #508
    //   Java source line #136	-> byte code offset #513
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	516	0	this	PlayerStatsRepository
    //   0	516	1	uuid	java.util.UUID
    //   0	516	2	name	String
    //   0	516	3	version	int
    //   1	513	4	playerInfo	mineplex.playerCache.PlayerInfo
    //   4	471	5	preparedStatement	java.sql.PreparedStatement
    //   7	490	6	resultSet	ResultSet
    //   217	39	7	id	int
    //   366	3	7	exception	Exception
    //   420	47	8	localObject	Object
    //   388	3	9	e	java.sql.SQLException
    //   410	3	9	e	java.sql.SQLException
    //   437	3	9	e	java.sql.SQLException
    //   459	3	9	e	java.sql.SQLException
    //   484	3	9	e	java.sql.SQLException
    //   506	3	9	e	java.sql.SQLException
    // Exception table:
    //   from	to	target	type
    //   9	363	366	java/lang/Exception
    //   378	385	388	java/sql/SQLException
    //   400	407	410	java/sql/SQLException
    //   9	373	420	finally
    //   427	434	437	java/sql/SQLException
    //   449	456	459	java/sql/SQLException
    //   474	481	484	java/sql/SQLException
    //   496	503	506	java/sql/SQLException
  }
  
  /* Error */
  public mineplex.bungee.playerStats.data.IpInfo getIp(String ipAddress)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aconst_null
    //   5: astore 4
    //   7: aload_0
    //   8: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   11: ifnull +15 -> 26
    //   14: aload_0
    //   15: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   18: invokeinterface 56 1 0
    //   23: ifeq +37 -> 60
    //   26: aload_0
    //   27: new 62	java/lang/StringBuilder
    //   30: dup
    //   31: invokestatic 64	mineplex/core/database/DBPool:getUrl	()Ljava/lang/String;
    //   34: invokestatic 70	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   37: invokespecial 76	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   40: ldc 79
    //   42: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   48: invokestatic 88	mineplex/core/database/DBPool:getUser	()Ljava/lang/String;
    //   51: invokestatic 91	mineplex/core/database/DBPool:getPass	()Ljava/lang/String;
    //   54: invokestatic 94	java/sql/DriverManager:getConnection	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/sql/Connection;
    //   57: putfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   60: aload_0
    //   61: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   64: getstatic 36	mineplex/bungee/playerStats/PlayerStatsRepository:SELECT_IPINFO	Ljava/lang/String;
    //   67: invokeinterface 123 2 0
    //   72: astore_3
    //   73: aload_3
    //   74: iconst_1
    //   75: aload_1
    //   76: invokeinterface 130 3 0
    //   81: aload_3
    //   82: invokeinterface 136 1 0
    //   87: astore 4
    //   89: goto +28 -> 117
    //   92: new 210	mineplex/bungee/playerStats/data/IpInfo
    //   95: dup
    //   96: invokespecial 212	mineplex/bungee/playerStats/data/IpInfo:<init>	()V
    //   99: astore_2
    //   100: aload_2
    //   101: aload 4
    //   103: iconst_1
    //   104: invokeinterface 142 2 0
    //   109: putfield 213	mineplex/bungee/playerStats/data/IpInfo:id	I
    //   112: aload_2
    //   113: aload_1
    //   114: putfield 215	mineplex/bungee/playerStats/data/IpInfo:ipAddress	Ljava/lang/String;
    //   117: aload 4
    //   119: invokeinterface 155 1 0
    //   124: ifne -32 -> 92
    //   127: aload 4
    //   129: invokeinterface 158 1 0
    //   134: aload_3
    //   135: invokeinterface 161 1 0
    //   140: aload_2
    //   141: ifnonnull +200 -> 341
    //   144: aload_0
    //   145: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   148: getstatic 32	mineplex/bungee/playerStats/PlayerStatsRepository:INSERT_IPINFO	Ljava/lang/String;
    //   151: iconst_1
    //   152: invokeinterface 162 3 0
    //   157: astore_3
    //   158: aload_3
    //   159: iconst_1
    //   160: aload_1
    //   161: invokeinterface 130 3 0
    //   166: aload_3
    //   167: invokeinterface 169 1 0
    //   172: pop
    //   173: iconst_0
    //   174: istore 5
    //   176: aload_3
    //   177: invokeinterface 173 1 0
    //   182: astore 4
    //   184: goto +13 -> 197
    //   187: aload 4
    //   189: iconst_1
    //   190: invokeinterface 142 2 0
    //   195: istore 5
    //   197: aload 4
    //   199: invokeinterface 155 1 0
    //   204: ifne -17 -> 187
    //   207: new 210	mineplex/bungee/playerStats/data/IpInfo
    //   210: dup
    //   211: invokespecial 212	mineplex/bungee/playerStats/data/IpInfo:<init>	()V
    //   214: astore_2
    //   215: aload_2
    //   216: iload 5
    //   218: putfield 213	mineplex/bungee/playerStats/data/IpInfo:id	I
    //   221: aload_2
    //   222: aload_1
    //   223: putfield 215	mineplex/bungee/playerStats/data/IpInfo:ipAddress	Ljava/lang/String;
    //   226: aload 4
    //   228: invokeinterface 158 1 0
    //   233: aload_3
    //   234: invokeinterface 161 1 0
    //   239: goto +102 -> 341
    //   242: astore 5
    //   244: aload 5
    //   246: invokevirtual 100	java/lang/Exception:printStackTrace	()V
    //   249: aload_3
    //   250: ifnull +19 -> 269
    //   253: aload_3
    //   254: invokeinterface 161 1 0
    //   259: goto +10 -> 269
    //   262: astore 7
    //   264: aload 7
    //   266: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   269: aload 4
    //   271: ifnull +112 -> 383
    //   274: aload 4
    //   276: invokeinterface 158 1 0
    //   281: goto +102 -> 383
    //   284: astore 7
    //   286: aload 7
    //   288: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   291: goto +92 -> 383
    //   294: astore 6
    //   296: aload_3
    //   297: ifnull +19 -> 316
    //   300: aload_3
    //   301: invokeinterface 161 1 0
    //   306: goto +10 -> 316
    //   309: astore 7
    //   311: aload 7
    //   313: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   316: aload 4
    //   318: ifnull +20 -> 338
    //   321: aload 4
    //   323: invokeinterface 158 1 0
    //   328: goto +10 -> 338
    //   331: astore 7
    //   333: aload 7
    //   335: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   338: aload 6
    //   340: athrow
    //   341: aload_3
    //   342: ifnull +19 -> 361
    //   345: aload_3
    //   346: invokeinterface 161 1 0
    //   351: goto +10 -> 361
    //   354: astore 7
    //   356: aload 7
    //   358: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   361: aload 4
    //   363: ifnull +20 -> 383
    //   366: aload 4
    //   368: invokeinterface 158 1 0
    //   373: goto +10 -> 383
    //   376: astore 7
    //   378: aload 7
    //   380: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   383: aload_2
    //   384: areturn
    // Line number table:
    //   Java source line #141	-> byte code offset #0
    //   Java source line #142	-> byte code offset #2
    //   Java source line #143	-> byte code offset #4
    //   Java source line #147	-> byte code offset #7
    //   Java source line #148	-> byte code offset #26
    //   Java source line #150	-> byte code offset #60
    //   Java source line #151	-> byte code offset #73
    //   Java source line #153	-> byte code offset #81
    //   Java source line #155	-> byte code offset #89
    //   Java source line #157	-> byte code offset #92
    //   Java source line #158	-> byte code offset #100
    //   Java source line #159	-> byte code offset #112
    //   Java source line #155	-> byte code offset #117
    //   Java source line #162	-> byte code offset #127
    //   Java source line #163	-> byte code offset #134
    //   Java source line #165	-> byte code offset #140
    //   Java source line #167	-> byte code offset #144
    //   Java source line #168	-> byte code offset #158
    //   Java source line #170	-> byte code offset #166
    //   Java source line #172	-> byte code offset #173
    //   Java source line #174	-> byte code offset #176
    //   Java source line #176	-> byte code offset #184
    //   Java source line #178	-> byte code offset #187
    //   Java source line #176	-> byte code offset #197
    //   Java source line #181	-> byte code offset #207
    //   Java source line #182	-> byte code offset #215
    //   Java source line #183	-> byte code offset #221
    //   Java source line #185	-> byte code offset #226
    //   Java source line #186	-> byte code offset #233
    //   Java source line #188	-> byte code offset #239
    //   Java source line #189	-> byte code offset #242
    //   Java source line #191	-> byte code offset #244
    //   Java source line #195	-> byte code offset #249
    //   Java source line #199	-> byte code offset #253
    //   Java source line #200	-> byte code offset #259
    //   Java source line #201	-> byte code offset #262
    //   Java source line #203	-> byte code offset #264
    //   Java source line #207	-> byte code offset #269
    //   Java source line #211	-> byte code offset #274
    //   Java source line #212	-> byte code offset #281
    //   Java source line #213	-> byte code offset #284
    //   Java source line #215	-> byte code offset #286
    //   Java source line #194	-> byte code offset #294
    //   Java source line #195	-> byte code offset #296
    //   Java source line #199	-> byte code offset #300
    //   Java source line #200	-> byte code offset #306
    //   Java source line #201	-> byte code offset #309
    //   Java source line #203	-> byte code offset #311
    //   Java source line #207	-> byte code offset #316
    //   Java source line #211	-> byte code offset #321
    //   Java source line #212	-> byte code offset #328
    //   Java source line #213	-> byte code offset #331
    //   Java source line #215	-> byte code offset #333
    //   Java source line #218	-> byte code offset #338
    //   Java source line #195	-> byte code offset #341
    //   Java source line #199	-> byte code offset #345
    //   Java source line #200	-> byte code offset #351
    //   Java source line #201	-> byte code offset #354
    //   Java source line #203	-> byte code offset #356
    //   Java source line #207	-> byte code offset #361
    //   Java source line #211	-> byte code offset #366
    //   Java source line #212	-> byte code offset #373
    //   Java source line #213	-> byte code offset #376
    //   Java source line #215	-> byte code offset #378
    //   Java source line #220	-> byte code offset #383
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	385	0	this	PlayerStatsRepository
    //   0	385	1	ipAddress	String
    //   1	383	2	ipInfo	mineplex.bungee.playerStats.data.IpInfo
    //   3	343	3	preparedStatement	java.sql.PreparedStatement
    //   5	362	4	resultSet	ResultSet
    //   174	43	5	id	int
    //   242	3	5	exception	Exception
    //   294	45	6	localObject	Object
    //   262	3	7	e	java.sql.SQLException
    //   284	3	7	e	java.sql.SQLException
    //   309	3	7	e	java.sql.SQLException
    //   331	3	7	e	java.sql.SQLException
    //   354	3	7	e	java.sql.SQLException
    //   376	3	7	e	java.sql.SQLException
    // Exception table:
    //   from	to	target	type
    //   7	239	242	java/lang/Exception
    //   253	259	262	java/sql/SQLException
    //   274	281	284	java/sql/SQLException
    //   7	249	294	finally
    //   300	306	309	java/sql/SQLException
    //   321	328	331	java/sql/SQLException
    //   345	351	354	java/sql/SQLException
    //   366	373	376	java/sql/SQLException
  }
  
  public int updatePlayerStats(int playerId, int ipId)
  {
    Statement statement = null;
    ResultSet resultSet = null;
    try
    {
      if ((this._connection == null) || (this._connection.isClosed())) {
        this._connection = DriverManager.getConnection(DBPool.getUrl() + "/Account", DBPool.getUser(), DBPool.getPass());
      }
      statement = this._connection.createStatement();
      
      String queryString = UPDATE_PLAYERSTATS;
      queryString = queryString.replaceFirst("\\?", playerId);
      queryString = queryString.replaceFirst("\\?", ipId);
      queryString = queryString.replaceFirst("\\?", playerId);
      queryString = queryString.replaceFirst("\\?", playerId);
      
      statement.executeUpdate(queryString, 1);
      
      statement.getMoreResults();
      statement.getMoreResults();
      resultSet = statement.getGeneratedKeys();
      if (resultSet.next()) {
        return resultSet.getInt(1);
      }
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
    finally
    {
      try
      {
        if (statement != null) {
          statement.close();
        }
      }
      catch (Exception exception)
      {
        exception.printStackTrace();
      }
      try
      {
        if (resultSet != null) {
          resultSet.close();
        }
      }
      catch (Exception exception)
      {
        exception.printStackTrace();
      }
    }
    try
    {
      if (statement != null) {
        statement.close();
      }
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
    try
    {
      if (resultSet != null) {
        resultSet.close();
      }
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
    return -1;
  }
  
  /* Error */
  public void updatePlayerSession(int loginSessionId)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   6: ifnull +15 -> 21
    //   9: aload_0
    //   10: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   13: invokeinterface 56 1 0
    //   18: ifeq +37 -> 55
    //   21: aload_0
    //   22: new 62	java/lang/StringBuilder
    //   25: dup
    //   26: invokestatic 64	mineplex/core/database/DBPool:getUrl	()Ljava/lang/String;
    //   29: invokestatic 70	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   32: invokespecial 76	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   35: ldc 79
    //   37: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   43: invokestatic 88	mineplex/core/database/DBPool:getUser	()Ljava/lang/String;
    //   46: invokestatic 91	mineplex/core/database/DBPool:getPass	()Ljava/lang/String;
    //   49: invokestatic 94	java/sql/DriverManager:getConnection	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/sql/Connection;
    //   52: putfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   55: aload_0
    //   56: getfield 51	mineplex/bungee/playerStats/PlayerStatsRepository:_connection	Ljava/sql/Connection;
    //   59: getstatic 44	mineplex/bungee/playerStats/PlayerStatsRepository:UPDATE_LOGINSESSION	Ljava/lang/String;
    //   62: invokeinterface 123 2 0
    //   67: astore_2
    //   68: aload_2
    //   69: iconst_1
    //   70: iload_1
    //   71: invokeinterface 165 3 0
    //   76: aload_2
    //   77: invokeinterface 169 1 0
    //   82: pop
    //   83: goto +56 -> 139
    //   86: astore_3
    //   87: aload_3
    //   88: invokevirtual 100	java/lang/Exception:printStackTrace	()V
    //   91: aload_2
    //   92: ifnull +67 -> 159
    //   95: aload_2
    //   96: invokeinterface 161 1 0
    //   101: goto +58 -> 159
    //   104: astore 5
    //   106: aload 5
    //   108: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   111: goto +48 -> 159
    //   114: astore 4
    //   116: aload_2
    //   117: ifnull +19 -> 136
    //   120: aload_2
    //   121: invokeinterface 161 1 0
    //   126: goto +10 -> 136
    //   129: astore 5
    //   131: aload 5
    //   133: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   136: aload 4
    //   138: athrow
    //   139: aload_2
    //   140: ifnull +19 -> 159
    //   143: aload_2
    //   144: invokeinterface 161 1 0
    //   149: goto +10 -> 159
    //   152: astore 5
    //   154: aload 5
    //   156: invokevirtual 189	java/sql/SQLException:printStackTrace	()V
    //   159: return
    // Line number table:
    //   Java source line #284	-> byte code offset #0
    //   Java source line #288	-> byte code offset #2
    //   Java source line #289	-> byte code offset #21
    //   Java source line #291	-> byte code offset #55
    //   Java source line #292	-> byte code offset #68
    //   Java source line #294	-> byte code offset #76
    //   Java source line #295	-> byte code offset #83
    //   Java source line #296	-> byte code offset #86
    //   Java source line #298	-> byte code offset #87
    //   Java source line #302	-> byte code offset #91
    //   Java source line #306	-> byte code offset #95
    //   Java source line #307	-> byte code offset #101
    //   Java source line #308	-> byte code offset #104
    //   Java source line #310	-> byte code offset #106
    //   Java source line #301	-> byte code offset #114
    //   Java source line #302	-> byte code offset #116
    //   Java source line #306	-> byte code offset #120
    //   Java source line #307	-> byte code offset #126
    //   Java source line #308	-> byte code offset #129
    //   Java source line #310	-> byte code offset #131
    //   Java source line #313	-> byte code offset #136
    //   Java source line #302	-> byte code offset #139
    //   Java source line #306	-> byte code offset #143
    //   Java source line #307	-> byte code offset #149
    //   Java source line #308	-> byte code offset #152
    //   Java source line #310	-> byte code offset #154
    //   Java source line #314	-> byte code offset #159
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	160	0	this	PlayerStatsRepository
    //   0	160	1	loginSessionId	int
    //   1	143	2	preparedStatement	java.sql.PreparedStatement
    //   86	2	3	exception	Exception
    //   114	23	4	localObject	Object
    //   104	3	5	e	java.sql.SQLException
    //   129	3	5	e	java.sql.SQLException
    //   152	3	5	e	java.sql.SQLException
    // Exception table:
    //   from	to	target	type
    //   2	83	86	java/lang/Exception
    //   95	101	104	java/sql/SQLException
    //   2	91	114	finally
    //   120	126	129	java/sql/SQLException
    //   143	149	152	java/sql/SQLException
  }
}

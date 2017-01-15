package mineplex.core.account.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mineplex.core.account.repository.token.DonorRequestToken;
import mineplex.core.account.repository.token.LoginToken;
import mineplex.core.account.repository.token.RankUpdateToken;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.database.DBPool;
import mineplex.core.database.DatabaseRunnable;
import mineplex.core.database.RepositoryBase;
import mineplex.core.database.ResultSetCallable;
import mineplex.core.database.column.Column;
import mineplex.core.database.column.ColumnBoolean;
import mineplex.core.database.column.ColumnTimestamp;
import mineplex.core.database.column.ColumnVarChar;
import mineplex.core.server.remotecall.JsonWebCall;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class AccountRepository
  extends RepositoryBase
{
  private static String CREATE_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS accounts (id INT NOT NULL AUTO_INCREMENT, uuid VARCHAR(100), name VARCHAR(40), gems INT, gold INT, coins INT, donorRank VARCHAR(20), rank VARCHAR(40), rankPerm BOOL, rankExpire LONG, lastLogin LONG, totalPlayTime LONG, PRIMARY KEY (id), UNIQUE INDEX uuidIndex (uuid), UNIQUE INDEX nameIndex (name), INDEX rankIndex (rank));";
  private static String ACCOUNT_LOGIN_NEW = "INSERT INTO accounts (uuid, name, lastLogin) values(?, ?, now());";
  private static String UPDATE_ACCOUNT_RANK = "UPDATE accounts SET rank=?, rankPerm=false, rankExpire=now() + INTERVAL 1 MONTH WHERE uuid = ?;";
  private static String UPDATE_ACCOUNT_RANK_DONOR = "UPDATE accounts SET rank=?, donorRank=?, rankPerm=false, rankExpire=now() + INTERVAL 1 MONTH WHERE uuid = ?;";
  private static String UPDATE_ACCOUNT_RANK_PERM = "UPDATE accounts SET rank=?, rankPerm=true WHERE uuid = ?;";
  private static String UPDATE_ACCOUNT_RANK_DONOR_PERM = "UPDATE accounts SET rank=?, donorRank=?, rankPerm=true WHERE uuid = ?;";
  private static String UPDATE_ACCOUNT_NULL_RANK = "UPDATE accounts SET rank=?, donorRank=?, rankPerm=?, rankExpire=? WHERE uuid = ? AND rank IS NULL;";
  private static String SELECT_ACCOUNT_UUID_BY_NAME = "SELECT uuid FROM accounts WHERE name = ? ORDER BY lastLogin DESC;";
  private String _webAddress;
  
  public AccountRepository(JavaPlugin plugin, String webAddress)
  {
    super(plugin, DBPool.ACCOUNT);
    this._webAddress = webAddress;
  }
  
  protected void initialize()
  {
    executeUpdate(CREATE_ACCOUNT_TABLE, new Column[0]);
  }
  
  /* Error */
  public int login(mineplex.core.common.util.NautHashMap<String, mineplex.core.account.ILoginProcessor> loginProcessors, String uuid, String name)
  {
    // Byte code:
    //   0: iconst_m1
    //   1: istore 4
    //   3: aload_0
    //   4: invokevirtual 81	mineplex/core/account/repository/AccountRepository:getConnection	()Ljava/sql/Connection;
    //   7: astore 5
    //   9: aconst_null
    //   10: astore 6
    //   12: aload 5
    //   14: invokeinterface 85 1 0
    //   19: astore 7
    //   21: aconst_null
    //   22: astore 8
    //   24: aload 7
    //   26: new 91	java/lang/StringBuilder
    //   29: dup
    //   30: ldc 93
    //   32: invokespecial 95	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   35: aload_2
    //   36: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: ldc 102
    //   41: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: invokeinterface 108 2 0
    //   52: pop
    //   53: aload 7
    //   55: invokeinterface 114 1 0
    //   60: astore 9
    //   62: goto +13 -> 75
    //   65: aload 9
    //   67: iconst_1
    //   68: invokeinterface 118 2 0
    //   73: istore 4
    //   75: aload 9
    //   77: invokeinterface 124 1 0
    //   82: ifne -17 -> 65
    //   85: iload 4
    //   87: iconst_m1
    //   88: if_icmpne +79 -> 167
    //   91: new 128	java/util/ArrayList
    //   94: dup
    //   95: iconst_1
    //   96: invokespecial 130	java/util/ArrayList:<init>	(I)V
    //   99: astore 10
    //   101: aload_0
    //   102: getstatic 24	mineplex/core/account/repository/AccountRepository:ACCOUNT_LOGIN_NEW	Ljava/lang/String;
    //   105: new 133	mineplex/core/account/repository/AccountRepository$1
    //   108: dup
    //   109: aload_0
    //   110: aload 10
    //   112: invokespecial 135	mineplex/core/account/repository/AccountRepository$1:<init>	(Lmineplex/core/account/repository/AccountRepository;Ljava/util/ArrayList;)V
    //   115: iconst_2
    //   116: anewarray 71	mineplex/core/database/column/Column
    //   119: dup
    //   120: iconst_0
    //   121: new 138	mineplex/core/database/column/ColumnVarChar
    //   124: dup
    //   125: ldc -116
    //   127: bipush 100
    //   129: aload_2
    //   130: invokespecial 142	mineplex/core/database/column/ColumnVarChar:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   133: aastore
    //   134: dup
    //   135: iconst_1
    //   136: new 138	mineplex/core/database/column/ColumnVarChar
    //   139: dup
    //   140: ldc -111
    //   142: bipush 40
    //   144: aload_3
    //   145: invokespecial 142	mineplex/core/database/column/ColumnVarChar:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   148: aastore
    //   149: invokevirtual 147	mineplex/core/account/repository/AccountRepository:executeInsert	(Ljava/lang/String;Lmineplex/core/database/ResultSetCallable;[Lmineplex/core/database/column/Column;)I
    //   152: pop
    //   153: aload 10
    //   155: iconst_0
    //   156: invokevirtual 151	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   159: checkcast 155	java/lang/Integer
    //   162: invokevirtual 157	java/lang/Integer:intValue	()I
    //   165: istore 4
    //   167: new 91	java/lang/StringBuilder
    //   170: dup
    //   171: ldc -95
    //   173: invokespecial 95	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   176: aload_3
    //   177: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   180: ldc -93
    //   182: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: iload 4
    //   187: invokevirtual 165	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   190: ldc -88
    //   192: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   198: astore 10
    //   200: aload_1
    //   201: invokevirtual 170	mineplex/core/common/util/NautHashMap:values	()Ljava/util/Collection;
    //   204: invokeinterface 176 1 0
    //   209: astore 12
    //   211: goto +46 -> 257
    //   214: aload 12
    //   216: invokeinterface 182 1 0
    //   221: checkcast 187	mineplex/core/account/ILoginProcessor
    //   224: astore 11
    //   226: new 91	java/lang/StringBuilder
    //   229: dup
    //   230: aload 10
    //   232: invokestatic 189	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   235: invokespecial 95	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   238: aload 11
    //   240: iload 4
    //   242: aload_2
    //   243: aload_3
    //   244: invokeinterface 195 4 0
    //   249: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   252: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   255: astore 10
    //   257: aload 12
    //   259: invokeinterface 199 1 0
    //   264: ifne -50 -> 214
    //   267: aload 7
    //   269: aload 10
    //   271: invokeinterface 108 2 0
    //   276: pop
    //   277: aload 7
    //   279: invokeinterface 202 1 0
    //   284: pop
    //   285: aload 7
    //   287: invokeinterface 205 1 0
    //   292: pop
    //   293: aload_1
    //   294: invokevirtual 170	mineplex/core/common/util/NautHashMap:values	()Ljava/util/Collection;
    //   297: invokeinterface 176 1 0
    //   302: astore 12
    //   304: goto +40 -> 344
    //   307: aload 12
    //   309: invokeinterface 182 1 0
    //   314: checkcast 187	mineplex/core/account/ILoginProcessor
    //   317: astore 11
    //   319: aload 11
    //   321: aload_3
    //   322: iload 4
    //   324: aload 7
    //   326: invokeinterface 114 1 0
    //   331: invokeinterface 208 4 0
    //   336: aload 7
    //   338: invokeinterface 205 1 0
    //   343: pop
    //   344: aload 12
    //   346: invokeinterface 199 1 0
    //   351: ifne -44 -> 307
    //   354: goto +56 -> 410
    //   357: astore 9
    //   359: aload 9
    //   361: astore 8
    //   363: aload 9
    //   365: athrow
    //   366: astore 13
    //   368: aload 7
    //   370: ifnull +37 -> 407
    //   373: aload 8
    //   375: ifnull +25 -> 400
    //   378: aload 7
    //   380: invokeinterface 212 1 0
    //   385: goto +22 -> 407
    //   388: astore 14
    //   390: aload 8
    //   392: aload 14
    //   394: invokevirtual 215	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   397: goto +10 -> 407
    //   400: aload 7
    //   402: invokeinterface 212 1 0
    //   407: aload 13
    //   409: athrow
    //   410: aload 7
    //   412: ifnull +93 -> 505
    //   415: aload 8
    //   417: ifnull +25 -> 442
    //   420: aload 7
    //   422: invokeinterface 212 1 0
    //   427: goto +78 -> 505
    //   430: astore 14
    //   432: aload 8
    //   434: aload 14
    //   436: invokevirtual 215	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   439: goto +66 -> 505
    //   442: aload 7
    //   444: invokeinterface 212 1 0
    //   449: goto +56 -> 505
    //   452: astore 7
    //   454: aload 7
    //   456: astore 6
    //   458: aload 7
    //   460: athrow
    //   461: astore 15
    //   463: aload 5
    //   465: ifnull +37 -> 502
    //   468: aload 6
    //   470: ifnull +25 -> 495
    //   473: aload 5
    //   475: invokeinterface 221 1 0
    //   480: goto +22 -> 502
    //   483: astore 16
    //   485: aload 6
    //   487: aload 16
    //   489: invokevirtual 215	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   492: goto +10 -> 502
    //   495: aload 5
    //   497: invokeinterface 221 1 0
    //   502: aload 15
    //   504: athrow
    //   505: aload 5
    //   507: ifnull +47 -> 554
    //   510: aload 6
    //   512: ifnull +25 -> 537
    //   515: aload 5
    //   517: invokeinterface 221 1 0
    //   522: goto +32 -> 554
    //   525: astore 16
    //   527: aload 6
    //   529: aload 16
    //   531: invokevirtual 215	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   534: goto +20 -> 554
    //   537: aload 5
    //   539: invokeinterface 221 1 0
    //   544: goto +10 -> 554
    //   547: astore 5
    //   549: aload 5
    //   551: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   554: iload 4
    //   556: ireturn
    // Line number table:
    //   Java source line #72	-> byte code offset #0
    //   Java source line #74	-> byte code offset #3
    //   Java source line #75	-> byte code offset #9
    //   Java source line #77	-> byte code offset #12
    //   Java source line #78	-> byte code offset #21
    //   Java source line #80	-> byte code offset #24
    //   Java source line #81	-> byte code offset #53
    //   Java source line #82	-> byte code offset #62
    //   Java source line #83	-> byte code offset #65
    //   Java source line #82	-> byte code offset #75
    //   Java source line #85	-> byte code offset #85
    //   Java source line #86	-> byte code offset #91
    //   Java source line #87	-> byte code offset #101
    //   Java source line #95	-> byte code offset #121
    //   Java source line #87	-> byte code offset #149
    //   Java source line #96	-> byte code offset #153
    //   Java source line #98	-> byte code offset #167
    //   Java source line #99	-> byte code offset #200
    //   Java source line #100	-> byte code offset #226
    //   Java source line #99	-> byte code offset #257
    //   Java source line #102	-> byte code offset #267
    //   Java source line #103	-> byte code offset #277
    //   Java source line #104	-> byte code offset #285
    //   Java source line #105	-> byte code offset #293
    //   Java source line #106	-> byte code offset #319
    //   Java source line #107	-> byte code offset #336
    //   Java source line #105	-> byte code offset #344
    //   Java source line #109	-> byte code offset #354
    //   Java source line #110	-> byte code offset #357
    //   Java source line #111	-> byte code offset #359
    //   Java source line #112	-> byte code offset #363
    //   Java source line #114	-> byte code offset #366
    //   Java source line #115	-> byte code offset #368
    //   Java source line #116	-> byte code offset #373
    //   Java source line #118	-> byte code offset #378
    //   Java source line #119	-> byte code offset #385
    //   Java source line #120	-> byte code offset #388
    //   Java source line #121	-> byte code offset #390
    //   Java source line #123	-> byte code offset #397
    //   Java source line #124	-> byte code offset #400
    //   Java source line #127	-> byte code offset #407
    //   Java source line #115	-> byte code offset #410
    //   Java source line #116	-> byte code offset #415
    //   Java source line #118	-> byte code offset #420
    //   Java source line #119	-> byte code offset #427
    //   Java source line #120	-> byte code offset #430
    //   Java source line #121	-> byte code offset #432
    //   Java source line #123	-> byte code offset #439
    //   Java source line #124	-> byte code offset #442
    //   Java source line #128	-> byte code offset #449
    //   Java source line #129	-> byte code offset #452
    //   Java source line #130	-> byte code offset #454
    //   Java source line #131	-> byte code offset #458
    //   Java source line #133	-> byte code offset #461
    //   Java source line #134	-> byte code offset #463
    //   Java source line #135	-> byte code offset #468
    //   Java source line #137	-> byte code offset #473
    //   Java source line #138	-> byte code offset #480
    //   Java source line #139	-> byte code offset #483
    //   Java source line #140	-> byte code offset #485
    //   Java source line #142	-> byte code offset #492
    //   Java source line #143	-> byte code offset #495
    //   Java source line #146	-> byte code offset #502
    //   Java source line #134	-> byte code offset #505
    //   Java source line #135	-> byte code offset #510
    //   Java source line #137	-> byte code offset #515
    //   Java source line #138	-> byte code offset #522
    //   Java source line #139	-> byte code offset #525
    //   Java source line #140	-> byte code offset #527
    //   Java source line #142	-> byte code offset #534
    //   Java source line #143	-> byte code offset #537
    //   Java source line #147	-> byte code offset #544
    //   Java source line #148	-> byte code offset #547
    //   Java source line #149	-> byte code offset #549
    //   Java source line #151	-> byte code offset #554
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	557	0	this	AccountRepository
    //   0	557	1	loginProcessors	mineplex.core.common.util.NautHashMap<String, mineplex.core.account.ILoginProcessor>
    //   0	557	2	uuid	String
    //   0	557	3	name	String
    //   1	554	4	accountId	int
    //   7	531	5	connection	java.sql.Connection
    //   547	3	5	exception	Exception
    //   10	518	6	throwable	Throwable
    //   19	424	7	statement	java.sql.Statement
    //   452	7	7	statement	Throwable
    //   22	411	8	throwable2	Throwable
    //   60	16	9	resultSet	ResultSet
    //   357	7	9	resultSet	Throwable
    //   99	55	10	tempList	ArrayList
    //   198	72	10	loginString	String
    //   224	15	11	loginProcessor2	mineplex.core.account.ILoginProcessor
    //   317	3	11	loginProcessor2	mineplex.core.account.ILoginProcessor
    //   209	136	12	localIterator	java.util.Iterator
    //   366	42	13	localObject1	Object
    //   388	5	14	resultSet	Throwable
    //   430	5	14	resultSet	Throwable
    //   461	42	15	localObject2	Object
    //   483	5	16	statement	Throwable
    //   525	5	16	statement	Throwable
    // Exception table:
    //   from	to	target	type
    //   24	354	357	java/lang/Throwable
    //   24	366	366	finally
    //   378	385	388	java/lang/Throwable
    //   420	427	430	java/lang/Throwable
    //   12	449	452	java/lang/Throwable
    //   12	461	461	finally
    //   473	480	483	java/lang/Throwable
    //   515	522	525	java/lang/Throwable
    //   3	544	547	java/lang/Exception
  }
  
  public String GetClient(String name, UUID uuid, String ipAddress)
  {
    LoginToken token = new LoginToken();
    token.Name = name;
    token.Uuid = uuid.toString();
    token.IpAddress = ipAddress;
    return new JsonWebCall(this._webAddress + "PlayerAccount/Login.php").ExecuteReturnStream(token);
  }
  
  public String GetDonor(String name, UUID uuid)
  {
    DonorRequestToken token = new DonorRequestToken();
    token.name = name;
    token.uuid = uuid.toString();
    return new JsonWebCall(this._webAddress + "PlayerAccount/DonorRequest.php").ExecuteReturnStream(token);
  }
  
  public String getClientByUUID(UUID uuid)
  {
    return new JsonWebCall(this._webAddress + "PlayerAccount/GetAccountByUUID.php").ExecuteReturnStream(uuid.toString());
  }
  
  public UUID getClientUUID(String name)
  {
    final ArrayList uuids = new ArrayList();
    executeQuery(SELECT_ACCOUNT_UUID_BY_NAME, new ResultSetCallable()
    
      new ColumnVarChar
      {
        public void processResultSet(ResultSet resultSet)
          throws SQLException
        {
          while (resultSet.next()) {
            uuids.add(UUID.fromString(resultSet.getString(1)));
          }
        }
      }, new Column[] {
      
      new ColumnVarChar("name", 100, name) });
    if (uuids.size() > 0) {
      return (UUID)uuids.get(uuids.size() - 1);
    }
    return null;
  }
  
  public void saveRank(final Callback<Rank> callback, String name, final UUID uuid, final Rank rank, final boolean perm)
  {
    final RankUpdateToken token = new RankUpdateToken();
    token.Name = name;
    token.Rank = rank.toString();
    token.Perm = perm;
    final Callback<Rank> extraCallback = new Callback()
    {
      public void run(final Rank response)
      {
        if ((rank == Rank.ULTRA) || (rank == Rank.HERO) || (rank == Rank.LEGEND))
        {
          if (perm) {
            AccountRepository.this.executeUpdate(AccountRepository.UPDATE_ACCOUNT_RANK_DONOR_PERM, new Column[] { new ColumnVarChar("rank", 100, rank.toString()), new ColumnVarChar("donorRank", 100, rank.toString()), new ColumnVarChar("uuid", 100, uuid.toString()) });
          } else {
            AccountRepository.this.executeUpdate(AccountRepository.UPDATE_ACCOUNT_RANK_DONOR, new Column[] { new ColumnVarChar("rank", 100, rank.toString()), new ColumnVarChar("donorRank", 100, rank.toString()), new ColumnVarChar("uuid", 100, uuid.toString()) });
          }
        }
        else if (perm) {
          AccountRepository.this.executeUpdate(AccountRepository.UPDATE_ACCOUNT_RANK_PERM, new Column[] { new ColumnVarChar("rank", 100, rank.toString()), new ColumnVarChar("uuid", 100, uuid.toString()) });
        } else {
          AccountRepository.this.executeUpdate(AccountRepository.UPDATE_ACCOUNT_RANK, new Column[] { new ColumnVarChar("rank", 100, rank.toString()), new ColumnVarChar("uuid", 100, uuid.toString()) });
        }
        Bukkit.getServer().getScheduler().runTask(AccountRepository.this.Plugin, new Runnable()
        {
          public void run()
          {
            this.val$callback.run(response);
          }
        });
      }
    };
    handleDatabaseCall(new DatabaseRunnable(new Runnable()
    {
      public void run()
      {
        new JsonWebCall(AccountRepository.this._webAddress + "PlayerAccount/RankUpdate.php").Execute(Rank.class, extraCallback, token);
      }
    }), "Error saving player  " + token.Name + "'s rank in AccountRepository : ");
  }
  
  public void matchPlayerName(final Callback<List<String>> callback, final String userName)
  {
    Thread asyncThread = new Thread(new Runnable()
    {
      public void run()
      {
        List tokenList = (List)new JsonWebCall(AccountRepository.this._webAddress + "PlayerAccount/GetMatches.php").Execute(new TypeToken() {}.getType(), userName);
        callback.run(tokenList);
      }
    });
    asyncThread.start();
  }
  
  protected void update() {}
  
  public void updateMysqlRank(final String uuid, final String rank, final boolean perm, final String rankExpire)
  {
    handleDatabaseCall(new DatabaseRunnable(new Runnable()
    {
      public void run()
      {
        AccountRepository.this.executeUpdate(AccountRepository.UPDATE_ACCOUNT_NULL_RANK, new Column[] { new ColumnVarChar("rank", 100, rank), new ColumnVarChar("donorRank", 100, rank), new ColumnBoolean("rankPerm", perm), new ColumnTimestamp("rankExpire", Timestamp.valueOf(rankExpire)), new ColumnVarChar("uuid", 100, uuid) });
      }
    }), "Error updating player's mysql rank AccountRepository : ");
  }
  
  public String getClientByName(String playerName)
  {
    return new JsonWebCall(this._webAddress + "PlayerAccount/GetAccount.php").ExecuteReturnStream(playerName);
  }
}

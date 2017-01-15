package mineplex.core.database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.database.column.Column;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class RepositoryBase
  implements Listener
{
  private static Object _queueLock = new Object();
  private NautHashMap<DatabaseRunnable, String> _failedQueue = new NautHashMap();
  private DataSource _dataSource;
  protected JavaPlugin Plugin;
  
  public RepositoryBase(JavaPlugin plugin, DataSource dataSource)
  {
    this.Plugin = plugin;
    this._dataSource = dataSource;
    Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable()
    {
      public void run()
      {
        RepositoryBase.this.initialize();
        RepositoryBase.this.update();
      }
    });
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  
  protected abstract void initialize();
  
  protected abstract void update();
  
  protected DataSource getConnectionPool()
  {
    return this._dataSource;
  }
  
  protected Connection getConnection()
  {
    try
    {
      return this._dataSource.getConnection();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  protected int executeUpdate(String query, Column<?>... columns)
  {
    return executeInsert(query, null, columns);
  }
  
  /* Error */
  protected int executeInsert(String query, ResultSetCallable callable, Column<?>... columns)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 4
    //   3: aload_0
    //   4: invokevirtual 107	mineplex/core/database/RepositoryBase:getConnection	()Ljava/sql/Connection;
    //   7: astore 5
    //   9: aconst_null
    //   10: astore 6
    //   12: aload 5
    //   14: aload_1
    //   15: iconst_1
    //   16: invokeinterface 108 3 0
    //   21: astore 7
    //   23: aconst_null
    //   24: astore 8
    //   26: iconst_0
    //   27: istore 9
    //   29: goto +19 -> 48
    //   32: aload_3
    //   33: iload 9
    //   35: aaload
    //   36: aload 7
    //   38: iload 9
    //   40: iconst_1
    //   41: iadd
    //   42: invokevirtual 114	mineplex/core/database/column/Column:setValue	(Ljava/sql/PreparedStatement;I)V
    //   45: iinc 9 1
    //   48: iload 9
    //   50: aload_3
    //   51: arraylength
    //   52: if_icmplt -20 -> 32
    //   55: aload 7
    //   57: invokeinterface 120 1 0
    //   62: istore 4
    //   64: aload_2
    //   65: ifnull +72 -> 137
    //   68: aload_2
    //   69: aload 7
    //   71: invokeinterface 125 1 0
    //   76: invokeinterface 129 2 0
    //   81: goto +56 -> 137
    //   84: astore 9
    //   86: aload 9
    //   88: astore 8
    //   90: aload 9
    //   92: athrow
    //   93: astore 10
    //   95: aload 7
    //   97: ifnull +37 -> 134
    //   100: aload 8
    //   102: ifnull +25 -> 127
    //   105: aload 7
    //   107: invokeinterface 135 1 0
    //   112: goto +22 -> 134
    //   115: astore 11
    //   117: aload 8
    //   119: aload 11
    //   121: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   124: goto +10 -> 134
    //   127: aload 7
    //   129: invokeinterface 135 1 0
    //   134: aload 10
    //   136: athrow
    //   137: aload 7
    //   139: ifnull +93 -> 232
    //   142: aload 8
    //   144: ifnull +25 -> 169
    //   147: aload 7
    //   149: invokeinterface 135 1 0
    //   154: goto +78 -> 232
    //   157: astore 11
    //   159: aload 8
    //   161: aload 11
    //   163: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   166: goto +66 -> 232
    //   169: aload 7
    //   171: invokeinterface 135 1 0
    //   176: goto +56 -> 232
    //   179: astore 7
    //   181: aload 7
    //   183: astore 6
    //   185: aload 7
    //   187: athrow
    //   188: astore 12
    //   190: aload 5
    //   192: ifnull +37 -> 229
    //   195: aload 6
    //   197: ifnull +25 -> 222
    //   200: aload 5
    //   202: invokeinterface 144 1 0
    //   207: goto +22 -> 229
    //   210: astore 13
    //   212: aload 6
    //   214: aload 13
    //   216: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   219: goto +10 -> 229
    //   222: aload 5
    //   224: invokeinterface 144 1 0
    //   229: aload 12
    //   231: athrow
    //   232: aload 5
    //   234: ifnull +57 -> 291
    //   237: aload 6
    //   239: ifnull +25 -> 264
    //   242: aload 5
    //   244: invokeinterface 144 1 0
    //   249: goto +42 -> 291
    //   252: astore 13
    //   254: aload 6
    //   256: aload 13
    //   258: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   261: goto +30 -> 291
    //   264: aload 5
    //   266: invokeinterface 144 1 0
    //   271: goto +20 -> 291
    //   274: astore 5
    //   276: aload 5
    //   278: invokevirtual 87	java/sql/SQLException:printStackTrace	()V
    //   281: goto +10 -> 291
    //   284: astore 5
    //   286: aload 5
    //   288: invokevirtual 145	java/lang/Exception:printStackTrace	()V
    //   291: iload 4
    //   293: ireturn
    // Line number table:
    //   Java source line #84	-> byte code offset #0
    //   Java source line #86	-> byte code offset #3
    //   Java source line #87	-> byte code offset #9
    //   Java source line #89	-> byte code offset #12
    //   Java source line #90	-> byte code offset #23
    //   Java source line #92	-> byte code offset #26
    //   Java source line #93	-> byte code offset #32
    //   Java source line #92	-> byte code offset #45
    //   Java source line #95	-> byte code offset #55
    //   Java source line #96	-> byte code offset #64
    //   Java source line #97	-> byte code offset #68
    //   Java source line #99	-> byte code offset #81
    //   Java source line #100	-> byte code offset #84
    //   Java source line #101	-> byte code offset #86
    //   Java source line #102	-> byte code offset #90
    //   Java source line #104	-> byte code offset #93
    //   Java source line #105	-> byte code offset #95
    //   Java source line #106	-> byte code offset #100
    //   Java source line #108	-> byte code offset #105
    //   Java source line #109	-> byte code offset #112
    //   Java source line #110	-> byte code offset #115
    //   Java source line #111	-> byte code offset #117
    //   Java source line #113	-> byte code offset #124
    //   Java source line #114	-> byte code offset #127
    //   Java source line #117	-> byte code offset #134
    //   Java source line #105	-> byte code offset #137
    //   Java source line #106	-> byte code offset #142
    //   Java source line #108	-> byte code offset #147
    //   Java source line #109	-> byte code offset #154
    //   Java source line #110	-> byte code offset #157
    //   Java source line #111	-> byte code offset #159
    //   Java source line #113	-> byte code offset #166
    //   Java source line #114	-> byte code offset #169
    //   Java source line #118	-> byte code offset #176
    //   Java source line #119	-> byte code offset #179
    //   Java source line #120	-> byte code offset #181
    //   Java source line #121	-> byte code offset #185
    //   Java source line #123	-> byte code offset #188
    //   Java source line #124	-> byte code offset #190
    //   Java source line #125	-> byte code offset #195
    //   Java source line #127	-> byte code offset #200
    //   Java source line #128	-> byte code offset #207
    //   Java source line #129	-> byte code offset #210
    //   Java source line #130	-> byte code offset #212
    //   Java source line #132	-> byte code offset #219
    //   Java source line #133	-> byte code offset #222
    //   Java source line #136	-> byte code offset #229
    //   Java source line #124	-> byte code offset #232
    //   Java source line #125	-> byte code offset #237
    //   Java source line #127	-> byte code offset #242
    //   Java source line #128	-> byte code offset #249
    //   Java source line #129	-> byte code offset #252
    //   Java source line #130	-> byte code offset #254
    //   Java source line #132	-> byte code offset #261
    //   Java source line #133	-> byte code offset #264
    //   Java source line #137	-> byte code offset #271
    //   Java source line #138	-> byte code offset #274
    //   Java source line #139	-> byte code offset #276
    //   Java source line #141	-> byte code offset #284
    //   Java source line #142	-> byte code offset #286
    //   Java source line #144	-> byte code offset #291
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	294	0	this	RepositoryBase
    //   0	294	1	query	String
    //   0	294	2	callable	ResultSetCallable
    //   0	294	3	columns	Column[]
    //   1	291	4	affectedRows	int
    //   7	258	5	connection	Connection
    //   274	3	5	exception	SQLException
    //   284	3	5	exception	Exception
    //   10	245	6	throwable	Throwable
    //   21	149	7	preparedStatement	java.sql.PreparedStatement
    //   179	7	7	preparedStatement	Throwable
    //   24	136	8	throwable2	Throwable
    //   27	22	9	i	int
    //   84	7	9	i	Throwable
    //   93	42	10	localObject1	Object
    //   115	5	11	i	Throwable
    //   157	5	11	i	Throwable
    //   188	42	12	localObject2	Object
    //   210	5	13	preparedStatement	Throwable
    //   252	5	13	preparedStatement	Throwable
    // Exception table:
    //   from	to	target	type
    //   26	81	84	java/lang/Throwable
    //   26	93	93	finally
    //   105	112	115	java/lang/Throwable
    //   147	154	157	java/lang/Throwable
    //   12	176	179	java/lang/Throwable
    //   12	188	188	finally
    //   200	207	210	java/lang/Throwable
    //   242	249	252	java/lang/Throwable
    //   3	271	274	java/sql/SQLException
    //   3	271	284	java/lang/Exception
  }
  
  /* Error */
  protected void executeQuery(java.sql.PreparedStatement statement, ResultSetCallable callable, Column<?>... columns)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 4
    //   3: goto +18 -> 21
    //   6: aload_3
    //   7: iload 4
    //   9: aaload
    //   10: aload_1
    //   11: iload 4
    //   13: iconst_1
    //   14: iadd
    //   15: invokevirtual 114	mineplex/core/database/column/Column:setValue	(Ljava/sql/PreparedStatement;I)V
    //   18: iinc 4 1
    //   21: iload 4
    //   23: aload_3
    //   24: arraylength
    //   25: if_icmplt -19 -> 6
    //   28: aload_1
    //   29: invokeinterface 168 1 0
    //   34: astore 4
    //   36: aconst_null
    //   37: astore 5
    //   39: aload_2
    //   40: aload 4
    //   42: invokeinterface 129 2 0
    //   47: goto +56 -> 103
    //   50: astore 6
    //   52: aload 6
    //   54: astore 5
    //   56: aload 6
    //   58: athrow
    //   59: astore 7
    //   61: aload 4
    //   63: ifnull +37 -> 100
    //   66: aload 5
    //   68: ifnull +25 -> 93
    //   71: aload 4
    //   73: invokeinterface 170 1 0
    //   78: goto +22 -> 100
    //   81: astore 8
    //   83: aload 5
    //   85: aload 8
    //   87: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   90: goto +10 -> 100
    //   93: aload 4
    //   95: invokeinterface 170 1 0
    //   100: aload 7
    //   102: athrow
    //   103: aload 4
    //   105: ifnull +57 -> 162
    //   108: aload 5
    //   110: ifnull +25 -> 135
    //   113: aload 4
    //   115: invokeinterface 170 1 0
    //   120: goto +42 -> 162
    //   123: astore 8
    //   125: aload 5
    //   127: aload 8
    //   129: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   132: goto +30 -> 162
    //   135: aload 4
    //   137: invokeinterface 170 1 0
    //   142: goto +20 -> 162
    //   145: astore 4
    //   147: aload 4
    //   149: invokevirtual 87	java/sql/SQLException:printStackTrace	()V
    //   152: goto +10 -> 162
    //   155: astore 4
    //   157: aload 4
    //   159: invokevirtual 145	java/lang/Exception:printStackTrace	()V
    //   162: return
    // Line number table:
    //   Java source line #149	-> byte code offset #0
    //   Java source line #150	-> byte code offset #6
    //   Java source line #149	-> byte code offset #18
    //   Java source line #152	-> byte code offset #28
    //   Java source line #153	-> byte code offset #36
    //   Java source line #155	-> byte code offset #39
    //   Java source line #156	-> byte code offset #47
    //   Java source line #157	-> byte code offset #50
    //   Java source line #158	-> byte code offset #52
    //   Java source line #159	-> byte code offset #56
    //   Java source line #161	-> byte code offset #59
    //   Java source line #162	-> byte code offset #61
    //   Java source line #163	-> byte code offset #66
    //   Java source line #165	-> byte code offset #71
    //   Java source line #166	-> byte code offset #78
    //   Java source line #167	-> byte code offset #81
    //   Java source line #168	-> byte code offset #83
    //   Java source line #170	-> byte code offset #90
    //   Java source line #171	-> byte code offset #93
    //   Java source line #174	-> byte code offset #100
    //   Java source line #162	-> byte code offset #103
    //   Java source line #163	-> byte code offset #108
    //   Java source line #165	-> byte code offset #113
    //   Java source line #166	-> byte code offset #120
    //   Java source line #167	-> byte code offset #123
    //   Java source line #168	-> byte code offset #125
    //   Java source line #170	-> byte code offset #132
    //   Java source line #171	-> byte code offset #135
    //   Java source line #175	-> byte code offset #142
    //   Java source line #176	-> byte code offset #145
    //   Java source line #177	-> byte code offset #147
    //   Java source line #179	-> byte code offset #155
    //   Java source line #180	-> byte code offset #157
    //   Java source line #182	-> byte code offset #162
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	163	0	this	RepositoryBase
    //   0	163	1	statement	java.sql.PreparedStatement
    //   0	163	2	callable	ResultSetCallable
    //   0	163	3	columns	Column[]
    //   1	21	4	i	int
    //   34	102	4	resultSet	java.sql.ResultSet
    //   145	3	4	exception	SQLException
    //   155	3	4	exception	Exception
    //   37	89	5	throwable	Throwable
    //   50	7	6	var6_10	Throwable
    //   59	42	7	localObject	Object
    //   81	5	8	var6_9	Throwable
    //   123	5	8	var6_9	Throwable
    // Exception table:
    //   from	to	target	type
    //   39	47	50	java/lang/Throwable
    //   39	59	59	finally
    //   71	78	81	java/lang/Throwable
    //   113	120	123	java/lang/Throwable
    //   0	142	145	java/sql/SQLException
    //   0	142	155	java/lang/Exception
  }
  
  /* Error */
  protected void executeQuery(String query, ResultSetCallable callable, Column<?>... columns)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 107	mineplex/core/database/RepositoryBase:getConnection	()Ljava/sql/Connection;
    //   4: astore 4
    //   6: aconst_null
    //   7: astore 5
    //   9: aload 4
    //   11: aload_1
    //   12: invokeinterface 180 2 0
    //   17: astore 6
    //   19: aconst_null
    //   20: astore 7
    //   22: aload_0
    //   23: aload 6
    //   25: aload_2
    //   26: aload_3
    //   27: invokevirtual 183	mineplex/core/database/RepositoryBase:executeQuery	(Ljava/sql/PreparedStatement;Lmineplex/core/database/ResultSetCallable;[Lmineplex/core/database/column/Column;)V
    //   30: goto +56 -> 86
    //   33: astore 8
    //   35: aload 8
    //   37: astore 7
    //   39: aload 8
    //   41: athrow
    //   42: astore 9
    //   44: aload 6
    //   46: ifnull +37 -> 83
    //   49: aload 7
    //   51: ifnull +25 -> 76
    //   54: aload 6
    //   56: invokeinterface 135 1 0
    //   61: goto +22 -> 83
    //   64: astore 10
    //   66: aload 7
    //   68: aload 10
    //   70: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   73: goto +10 -> 83
    //   76: aload 6
    //   78: invokeinterface 135 1 0
    //   83: aload 9
    //   85: athrow
    //   86: aload 6
    //   88: ifnull +93 -> 181
    //   91: aload 7
    //   93: ifnull +25 -> 118
    //   96: aload 6
    //   98: invokeinterface 135 1 0
    //   103: goto +78 -> 181
    //   106: astore 10
    //   108: aload 7
    //   110: aload 10
    //   112: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   115: goto +66 -> 181
    //   118: aload 6
    //   120: invokeinterface 135 1 0
    //   125: goto +56 -> 181
    //   128: astore 6
    //   130: aload 6
    //   132: astore 5
    //   134: aload 6
    //   136: athrow
    //   137: astore 11
    //   139: aload 4
    //   141: ifnull +37 -> 178
    //   144: aload 5
    //   146: ifnull +25 -> 171
    //   149: aload 4
    //   151: invokeinterface 144 1 0
    //   156: goto +22 -> 178
    //   159: astore 12
    //   161: aload 5
    //   163: aload 12
    //   165: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   168: goto +10 -> 178
    //   171: aload 4
    //   173: invokeinterface 144 1 0
    //   178: aload 11
    //   180: athrow
    //   181: aload 4
    //   183: ifnull +57 -> 240
    //   186: aload 5
    //   188: ifnull +25 -> 213
    //   191: aload 4
    //   193: invokeinterface 144 1 0
    //   198: goto +42 -> 240
    //   201: astore 12
    //   203: aload 5
    //   205: aload 12
    //   207: invokevirtual 138	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   210: goto +30 -> 240
    //   213: aload 4
    //   215: invokeinterface 144 1 0
    //   220: goto +20 -> 240
    //   223: astore 4
    //   225: aload 4
    //   227: invokevirtual 87	java/sql/SQLException:printStackTrace	()V
    //   230: goto +10 -> 240
    //   233: astore 4
    //   235: aload 4
    //   237: invokevirtual 145	java/lang/Exception:printStackTrace	()V
    //   240: return
    // Line number table:
    //   Java source line #186	-> byte code offset #0
    //   Java source line #187	-> byte code offset #6
    //   Java source line #189	-> byte code offset #9
    //   Java source line #190	-> byte code offset #19
    //   Java source line #192	-> byte code offset #22
    //   Java source line #193	-> byte code offset #30
    //   Java source line #194	-> byte code offset #33
    //   Java source line #195	-> byte code offset #35
    //   Java source line #196	-> byte code offset #39
    //   Java source line #198	-> byte code offset #42
    //   Java source line #199	-> byte code offset #44
    //   Java source line #200	-> byte code offset #49
    //   Java source line #202	-> byte code offset #54
    //   Java source line #203	-> byte code offset #61
    //   Java source line #204	-> byte code offset #64
    //   Java source line #205	-> byte code offset #66
    //   Java source line #207	-> byte code offset #73
    //   Java source line #208	-> byte code offset #76
    //   Java source line #211	-> byte code offset #83
    //   Java source line #199	-> byte code offset #86
    //   Java source line #200	-> byte code offset #91
    //   Java source line #202	-> byte code offset #96
    //   Java source line #203	-> byte code offset #103
    //   Java source line #204	-> byte code offset #106
    //   Java source line #205	-> byte code offset #108
    //   Java source line #207	-> byte code offset #115
    //   Java source line #208	-> byte code offset #118
    //   Java source line #212	-> byte code offset #125
    //   Java source line #213	-> byte code offset #128
    //   Java source line #214	-> byte code offset #130
    //   Java source line #215	-> byte code offset #134
    //   Java source line #217	-> byte code offset #137
    //   Java source line #218	-> byte code offset #139
    //   Java source line #219	-> byte code offset #144
    //   Java source line #221	-> byte code offset #149
    //   Java source line #222	-> byte code offset #156
    //   Java source line #223	-> byte code offset #159
    //   Java source line #224	-> byte code offset #161
    //   Java source line #226	-> byte code offset #168
    //   Java source line #227	-> byte code offset #171
    //   Java source line #230	-> byte code offset #178
    //   Java source line #218	-> byte code offset #181
    //   Java source line #219	-> byte code offset #186
    //   Java source line #221	-> byte code offset #191
    //   Java source line #222	-> byte code offset #198
    //   Java source line #223	-> byte code offset #201
    //   Java source line #224	-> byte code offset #203
    //   Java source line #226	-> byte code offset #210
    //   Java source line #227	-> byte code offset #213
    //   Java source line #231	-> byte code offset #220
    //   Java source line #232	-> byte code offset #223
    //   Java source line #233	-> byte code offset #225
    //   Java source line #235	-> byte code offset #233
    //   Java source line #236	-> byte code offset #235
    //   Java source line #238	-> byte code offset #240
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	241	0	this	RepositoryBase
    //   0	241	1	query	String
    //   0	241	2	callable	ResultSetCallable
    //   0	241	3	columns	Column[]
    //   4	210	4	connection	Connection
    //   223	3	4	exception	SQLException
    //   233	3	4	exception	Exception
    //   7	197	5	throwable	Throwable
    //   17	102	6	preparedStatement	java.sql.PreparedStatement
    //   128	7	6	preparedStatement	Throwable
    //   20	89	7	throwable2	Throwable
    //   33	7	8	var8_13	Throwable
    //   42	42	9	localObject1	Object
    //   64	5	10	var8_12	Throwable
    //   106	5	10	var8_12	Throwable
    //   137	42	11	localObject2	Object
    //   159	5	12	preparedStatement	Throwable
    //   201	5	12	preparedStatement	Throwable
    // Exception table:
    //   from	to	target	type
    //   22	30	33	java/lang/Throwable
    //   22	42	42	finally
    //   54	61	64	java/lang/Throwable
    //   96	103	106	java/lang/Throwable
    //   9	125	128	java/lang/Throwable
    //   9	137	137	finally
    //   149	156	159	java/lang/Throwable
    //   191	198	201	java/lang/Throwable
    //   0	220	223	java/sql/SQLException
    //   0	220	233	java/lang/Exception
  }
  
  protected void handleDatabaseCall(final DatabaseRunnable databaseRunnable, final String errorMessage)
  {
    Thread asyncThread = new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          databaseRunnable.run();
        }
        catch (Exception exception)
        {
          RepositoryBase.this.processFailedDatabaseCall(databaseRunnable, exception.getMessage(), errorMessage);
        }
      }
    });
    asyncThread.start();
  }
  
  protected void processFailedDatabaseCall(DatabaseRunnable databaseRunnable, String errorPreMessage, String runnableMessage)
  {
    if (databaseRunnable.getFailedCounts() < 4)
    {
      databaseRunnable.incrementFailCount();
      Object object = _queueLock;
      synchronized (object)
      {
        this._failedQueue.put(databaseRunnable, runnableMessage);
      }
    }
  }
  
  @EventHandler
  public void processDatabaseQueue(UpdateEvent event)
  {
    if (event.getType() != UpdateType.MIN_01) {
      return;
    }
    processFailedQueue();
  }
  
  private void processFailedQueue()
  {
    Object object = _queueLock;
    synchronized (object)
    {
      for (DatabaseRunnable databaseRunnable : this._failedQueue.keySet()) {
        handleDatabaseCall(databaseRunnable, (String)this._failedQueue.get(databaseRunnable));
      }
    }
  }
}

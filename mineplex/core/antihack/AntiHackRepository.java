package mineplex.core.antihack;

import org.bukkit.entity.Player;

public class AntiHackRepository
{
  private String _serverName;
  private static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS AntiHack_Kick_Log (id INT NOT NULL AUTO_INCREMENT, updated LONG, playerName VARCHAR(256), motd VARCHAR(56), gameType VARCHAR(56), map VARCHAR(256), serverName VARCHAR(256), report VARCHAR(256), ping VARCHAR(25), PRIMARY KEY (id));";
  private static String UPDATE_PLAYER_OFFENSES = "INSERT INTO AntiHack_Kick_Log (updated, playerName, motd, gameType, map, serverName, report, ping) VALUES (now(), ?, ?, ?, ?, ?, ?, ?);";
  
  public AntiHackRepository(String serverName)
  {
    this._serverName = serverName;
  }
  
  /* Error */
  public void initialize()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: getstatic 32	mineplex/core/database/DBPool:STATS_MINEPLEX	Ljavax/sql/DataSource;
    //   5: invokeinterface 38 1 0
    //   10: astore_2
    //   11: aconst_null
    //   12: astore_3
    //   13: aload_2
    //   14: getstatic 14	mineplex/core/antihack/AntiHackRepository:CREATE_TABLE	Ljava/lang/String;
    //   17: invokeinterface 44 2 0
    //   22: astore_1
    //   23: aload_1
    //   24: invokeinterface 50 1 0
    //   29: pop
    //   30: goto +50 -> 80
    //   33: astore 4
    //   35: aload 4
    //   37: astore_3
    //   38: aload 4
    //   40: athrow
    //   41: astore 5
    //   43: aload_2
    //   44: ifnull +33 -> 77
    //   47: aload_3
    //   48: ifnull +23 -> 71
    //   51: aload_2
    //   52: invokeinterface 56 1 0
    //   57: goto +20 -> 77
    //   60: astore 6
    //   62: aload_3
    //   63: aload 6
    //   65: invokevirtual 59	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   68: goto +9 -> 77
    //   71: aload_2
    //   72: invokeinterface 56 1 0
    //   77: aload 5
    //   79: athrow
    //   80: aload_2
    //   81: ifnull +33 -> 114
    //   84: aload_3
    //   85: ifnull +23 -> 108
    //   88: aload_2
    //   89: invokeinterface 56 1 0
    //   94: goto +20 -> 114
    //   97: astore 6
    //   99: aload_3
    //   100: aload 6
    //   102: invokevirtual 59	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   105: goto +9 -> 114
    //   108: aload_2
    //   109: invokeinterface 56 1 0
    //   114: aload_1
    //   115: ifnull +19 -> 134
    //   118: aload_1
    //   119: invokeinterface 65 1 0
    //   124: goto +10 -> 134
    //   127: astore 8
    //   129: aload 8
    //   131: invokevirtual 66	java/sql/SQLException:printStackTrace	()V
    //   134: return
    //   135: astore_2
    //   136: aload_2
    //   137: invokevirtual 71	java/lang/Exception:printStackTrace	()V
    //   140: aload_1
    //   141: ifnull +47 -> 188
    //   144: aload_1
    //   145: invokeinterface 65 1 0
    //   150: goto +38 -> 188
    //   153: astore 8
    //   155: aload 8
    //   157: invokevirtual 66	java/sql/SQLException:printStackTrace	()V
    //   160: goto +28 -> 188
    //   163: astore 7
    //   165: aload_1
    //   166: ifnull +19 -> 185
    //   169: aload_1
    //   170: invokeinterface 65 1 0
    //   175: goto +10 -> 185
    //   178: astore 8
    //   180: aload 8
    //   182: invokevirtual 66	java/sql/SQLException:printStackTrace	()V
    //   185: aload 7
    //   187: athrow
    //   188: return
    // Line number table:
    //   Java source line #25	-> byte code offset #0
    //   Java source line #28	-> byte code offset #2
    //   Java source line #31	-> byte code offset #13
    //   Java source line #32	-> byte code offset #23
    //   Java source line #33	-> byte code offset #30
    //   Java source line #34	-> byte code offset #33
    //   Java source line #36	-> byte code offset #35
    //   Java source line #39	-> byte code offset #41
    //   Java source line #40	-> byte code offset #43
    //   Java source line #41	-> byte code offset #47
    //   Java source line #44	-> byte code offset #51
    //   Java source line #45	-> byte code offset #57
    //   Java source line #46	-> byte code offset #60
    //   Java source line #48	-> byte code offset #62
    //   Java source line #50	-> byte code offset #68
    //   Java source line #51	-> byte code offset #71
    //   Java source line #54	-> byte code offset #77
    //   Java source line #40	-> byte code offset #80
    //   Java source line #41	-> byte code offset #84
    //   Java source line #44	-> byte code offset #88
    //   Java source line #45	-> byte code offset #94
    //   Java source line #46	-> byte code offset #97
    //   Java source line #48	-> byte code offset #99
    //   Java source line #50	-> byte code offset #105
    //   Java source line #51	-> byte code offset #108
    //   Java source line #63	-> byte code offset #114
    //   Java source line #66	-> byte code offset #118
    //   Java source line #67	-> byte code offset #124
    //   Java source line #68	-> byte code offset #127
    //   Java source line #70	-> byte code offset #129
    //   Java source line #55	-> byte code offset #134
    //   Java source line #57	-> byte code offset #135
    //   Java source line #59	-> byte code offset #136
    //   Java source line #63	-> byte code offset #140
    //   Java source line #66	-> byte code offset #144
    //   Java source line #67	-> byte code offset #150
    //   Java source line #68	-> byte code offset #153
    //   Java source line #70	-> byte code offset #155
    //   Java source line #62	-> byte code offset #163
    //   Java source line #63	-> byte code offset #165
    //   Java source line #66	-> byte code offset #169
    //   Java source line #67	-> byte code offset #175
    //   Java source line #68	-> byte code offset #178
    //   Java source line #70	-> byte code offset #180
    //   Java source line #73	-> byte code offset #185
    //   Java source line #74	-> byte code offset #188
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	189	0	this	AntiHackRepository
    //   1	169	1	preparedStatement	java.sql.PreparedStatement
    //   10	99	2	connection	java.sql.Connection
    //   135	2	2	exception	Exception
    //   12	88	3	localThrowable3	Throwable
    //   33	6	4	localThrowable1	Throwable
    //   41	37	5	localObject1	Object
    //   60	4	6	localThrowable2	Throwable
    //   97	4	6	localThrowable2	Throwable
    //   163	23	7	localObject2	Object
    //   127	3	8	e	java.sql.SQLException
    //   153	3	8	e	java.sql.SQLException
    //   178	3	8	e	java.sql.SQLException
    // Exception table:
    //   from	to	target	type
    //   13	30	33	java/lang/Throwable
    //   13	41	41	finally
    //   51	57	60	java/lang/Throwable
    //   88	94	97	java/lang/Throwable
    //   118	124	127	java/sql/SQLException
    //   2	114	135	java/lang/Exception
    //   144	150	153	java/sql/SQLException
    //   2	114	163	finally
    //   135	140	163	finally
    //   169	175	178	java/sql/SQLException
  }
  
  public void saveOffense(final Player player, final String motd, final String game, final String map, final String report)
  {
    new Thread(new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_1
        //   2: getstatic 39	mineplex/core/database/DBPool:STATS_MINEPLEX	Ljavax/sql/DataSource;
        //   5: invokeinterface 45 1 0
        //   10: astore_2
        //   11: aconst_null
        //   12: astore_3
        //   13: aload_2
        //   14: invokestatic 51	mineplex/core/antihack/AntiHackRepository:access$0	()Ljava/lang/String;
        //   17: invokeinterface 57 2 0
        //   22: astore_1
        //   23: aload_1
        //   24: iconst_1
        //   25: aload_0
        //   26: getfield 21	mineplex/core/antihack/AntiHackRepository$1:val$player	Lorg/bukkit/entity/Player;
        //   29: invokeinterface 63 1 0
        //   34: invokeinterface 68 3 0
        //   39: aload_1
        //   40: iconst_2
        //   41: aload_0
        //   42: getfield 23	mineplex/core/antihack/AntiHackRepository$1:val$motd	Ljava/lang/String;
        //   45: invokeinterface 68 3 0
        //   50: aload_1
        //   51: iconst_3
        //   52: aload_0
        //   53: getfield 25	mineplex/core/antihack/AntiHackRepository$1:val$game	Ljava/lang/String;
        //   56: invokeinterface 68 3 0
        //   61: aload_1
        //   62: iconst_4
        //   63: aload_0
        //   64: getfield 27	mineplex/core/antihack/AntiHackRepository$1:val$map	Ljava/lang/String;
        //   67: invokeinterface 68 3 0
        //   72: aload_1
        //   73: iconst_5
        //   74: aload_0
        //   75: getfield 19	mineplex/core/antihack/AntiHackRepository$1:this$0	Lmineplex/core/antihack/AntiHackRepository;
        //   78: invokestatic 74	mineplex/core/antihack/AntiHackRepository:access$1	(Lmineplex/core/antihack/AntiHackRepository;)Ljava/lang/String;
        //   81: invokeinterface 68 3 0
        //   86: aload_1
        //   87: bipush 6
        //   89: aload_0
        //   90: getfield 29	mineplex/core/antihack/AntiHackRepository$1:val$report	Ljava/lang/String;
        //   93: invokeinterface 68 3 0
        //   98: aload_1
        //   99: bipush 7
        //   101: new 78	java/lang/StringBuilder
        //   104: dup
        //   105: aload_0
        //   106: getfield 21	mineplex/core/antihack/AntiHackRepository$1:val$player	Lorg/bukkit/entity/Player;
        //   109: checkcast 80	org/bukkit/craftbukkit/v1_7_R4/entity/CraftPlayer
        //   112: invokevirtual 82	org/bukkit/craftbukkit/v1_7_R4/entity/CraftPlayer:getHandle	()Lnet/minecraft/server/v1_7_R4/EntityPlayer;
        //   115: getfield 86	net/minecraft/server/v1_7_R4/EntityPlayer:ping	I
        //   118: invokestatic 92	java/lang/String:valueOf	(I)Ljava/lang/String;
        //   121: invokespecial 98	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   124: ldc 101
        //   126: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   129: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   132: invokeinterface 68 3 0
        //   137: aload_1
        //   138: invokeinterface 110 1 0
        //   143: pop
        //   144: goto +50 -> 194
        //   147: astore 4
        //   149: aload 4
        //   151: astore_3
        //   152: aload 4
        //   154: athrow
        //   155: astore 5
        //   157: aload_2
        //   158: ifnull +33 -> 191
        //   161: aload_3
        //   162: ifnull +23 -> 185
        //   165: aload_2
        //   166: invokeinterface 114 1 0
        //   171: goto +20 -> 191
        //   174: astore 6
        //   176: aload_3
        //   177: aload 6
        //   179: invokevirtual 117	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
        //   182: goto +9 -> 191
        //   185: aload_2
        //   186: invokeinterface 114 1 0
        //   191: aload 5
        //   193: athrow
        //   194: aload_2
        //   195: ifnull +33 -> 228
        //   198: aload_3
        //   199: ifnull +23 -> 222
        //   202: aload_2
        //   203: invokeinterface 114 1 0
        //   208: goto +20 -> 228
        //   211: astore 6
        //   213: aload_3
        //   214: aload 6
        //   216: invokevirtual 117	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
        //   219: goto +9 -> 228
        //   222: aload_2
        //   223: invokeinterface 114 1 0
        //   228: aload_1
        //   229: ifnull +19 -> 248
        //   232: aload_1
        //   233: invokeinterface 123 1 0
        //   238: goto +10 -> 248
        //   241: astore 8
        //   243: aload 8
        //   245: invokevirtual 124	java/sql/SQLException:printStackTrace	()V
        //   248: return
        //   249: astore_2
        //   250: aload_2
        //   251: invokevirtual 129	java/lang/Exception:printStackTrace	()V
        //   254: aload_1
        //   255: ifnull +47 -> 302
        //   258: aload_1
        //   259: invokeinterface 123 1 0
        //   264: goto +38 -> 302
        //   267: astore 8
        //   269: aload 8
        //   271: invokevirtual 124	java/sql/SQLException:printStackTrace	()V
        //   274: goto +28 -> 302
        //   277: astore 7
        //   279: aload_1
        //   280: ifnull +19 -> 299
        //   283: aload_1
        //   284: invokeinterface 123 1 0
        //   289: goto +10 -> 299
        //   292: astore 8
        //   294: aload 8
        //   296: invokevirtual 124	java/sql/SQLException:printStackTrace	()V
        //   299: aload 7
        //   301: athrow
        //   302: return
        // Line number table:
        //   Java source line #82	-> byte code offset #0
        //   Java source line #85	-> byte code offset #2
        //   Java source line #88	-> byte code offset #13
        //   Java source line #90	-> byte code offset #23
        //   Java source line #91	-> byte code offset #39
        //   Java source line #92	-> byte code offset #50
        //   Java source line #93	-> byte code offset #61
        //   Java source line #94	-> byte code offset #72
        //   Java source line #95	-> byte code offset #86
        //   Java source line #96	-> byte code offset #98
        //   Java source line #98	-> byte code offset #137
        //   Java source line #99	-> byte code offset #144
        //   Java source line #100	-> byte code offset #147
        //   Java source line #102	-> byte code offset #149
        //   Java source line #105	-> byte code offset #155
        //   Java source line #106	-> byte code offset #157
        //   Java source line #107	-> byte code offset #161
        //   Java source line #110	-> byte code offset #165
        //   Java source line #111	-> byte code offset #171
        //   Java source line #112	-> byte code offset #174
        //   Java source line #114	-> byte code offset #176
        //   Java source line #116	-> byte code offset #182
        //   Java source line #117	-> byte code offset #185
        //   Java source line #120	-> byte code offset #191
        //   Java source line #106	-> byte code offset #194
        //   Java source line #107	-> byte code offset #198
        //   Java source line #110	-> byte code offset #202
        //   Java source line #111	-> byte code offset #208
        //   Java source line #112	-> byte code offset #211
        //   Java source line #114	-> byte code offset #213
        //   Java source line #116	-> byte code offset #219
        //   Java source line #117	-> byte code offset #222
        //   Java source line #129	-> byte code offset #228
        //   Java source line #132	-> byte code offset #232
        //   Java source line #133	-> byte code offset #238
        //   Java source line #134	-> byte code offset #241
        //   Java source line #136	-> byte code offset #243
        //   Java source line #121	-> byte code offset #248
        //   Java source line #123	-> byte code offset #249
        //   Java source line #125	-> byte code offset #250
        //   Java source line #129	-> byte code offset #254
        //   Java source line #132	-> byte code offset #258
        //   Java source line #133	-> byte code offset #264
        //   Java source line #134	-> byte code offset #267
        //   Java source line #136	-> byte code offset #269
        //   Java source line #128	-> byte code offset #277
        //   Java source line #129	-> byte code offset #279
        //   Java source line #132	-> byte code offset #283
        //   Java source line #133	-> byte code offset #289
        //   Java source line #134	-> byte code offset #292
        //   Java source line #136	-> byte code offset #294
        //   Java source line #139	-> byte code offset #299
        //   Java source line #140	-> byte code offset #302
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	303	0	this	1
        //   1	283	1	preparedStatement	java.sql.PreparedStatement
        //   10	213	2	connection	java.sql.Connection
        //   249	2	2	exception	Exception
        //   12	202	3	localThrowable3	Throwable
        //   147	6	4	localThrowable1	Throwable
        //   155	37	5	localObject1	Object
        //   174	4	6	localThrowable2	Throwable
        //   211	4	6	localThrowable2	Throwable
        //   277	23	7	localObject2	Object
        //   241	3	8	e	java.sql.SQLException
        //   267	3	8	e	java.sql.SQLException
        //   292	3	8	e	java.sql.SQLException
        // Exception table:
        //   from	to	target	type
        //   13	144	147	java/lang/Throwable
        //   13	155	155	finally
        //   165	171	174	java/lang/Throwable
        //   202	208	211	java/lang/Throwable
        //   232	238	241	java/sql/SQLException
        //   2	228	249	java/lang/Exception
        //   258	264	267	java/sql/SQLException
        //   2	228	277	finally
        //   249	254	277	finally
        //   283	289	292	java/sql/SQLException
      }
    })
    
      .start();
  }
}

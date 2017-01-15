package mineplex.core.preferences;

import java.sql.ResultSet;
import java.sql.SQLException;
import mineplex.core.database.DBPool;
import mineplex.core.database.RepositoryBase;
import mineplex.core.database.column.Column;
import org.bukkit.plugin.java.JavaPlugin;

public class PreferencesRepository
  extends RepositoryBase
{
  private static String CREATE_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS accountPreferences (id INT NOT NULL AUTO_INCREMENT, uuid VARCHAR(256), games BOOL NOT NULL DEFAULT 1, visibility BOOL NOT NULL DEFAULT 1, showChat BOOL NOT NULL DEFAULT 1, friendChat BOOL NOT NULL DEFAULT 1, privateMessaging BOOL NOT NULL DEFAULT 1, partyRequests BOOL NOT NULL DEFAULT 0, invisibility BOOL NOT NULL DEFAULT 0, forcefield BOOL NOT NULL DEFAULT 0, showMacReports BOOL NOT NULL DEFAULT 0, ignoreVelocity BOOL NOT NULL DEFAULT 0, PRIMARY KEY (id), UNIQUE INDEX uuid_index (uuid));";
  private static String INSERT_ACCOUNT = "INSERT INTO accountPreferences (uuid) VALUES (?) ON DUPLICATE KEY UPDATE uuid=uuid;";
  private static String UPDATE_ACCOUNT_PREFERENCES = "UPDATE accountPreferences SET games = ?, visibility = ?, showChat = ?, friendChat = ?, privateMessaging = ?, partyRequests = ?, invisibility = ?, forcefield = ?, showMacReports = ?, ignoreVelocity = ?, pendingFriendRequests = ?, friendDisplayInventoryUI = ? WHERE uuid=?;";
  
  public PreferencesRepository(JavaPlugin plugin)
  {
    super(plugin, DBPool.ACCOUNT);
  }
  
  protected void initialize()
  {
    executeUpdate(CREATE_ACCOUNT_TABLE, new Column[0]);
  }
  
  protected void update() {}
  
  /* Error */
  public void saveUserPreferences(mineplex.core.common.util.NautHashMap<String, UserPreferences> preferences)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aload_0
    //   5: invokevirtual 53	mineplex/core/preferences/PreferencesRepository:getConnection	()Ljava/sql/Connection;
    //   8: astore 4
    //   10: aload 4
    //   12: getstatic 22	mineplex/core/preferences/PreferencesRepository:UPDATE_ACCOUNT_PREFERENCES	Ljava/lang/String;
    //   15: invokeinterface 57 2 0
    //   20: astore 5
    //   22: aload_1
    //   23: invokevirtual 63	mineplex/core/common/util/NautHashMap:entrySet	()Ljava/util/Set;
    //   26: invokeinterface 69 1 0
    //   31: astore 7
    //   33: goto +300 -> 333
    //   36: aload 7
    //   38: invokeinterface 75 1 0
    //   43: checkcast 81	java/util/Map$Entry
    //   46: astore 6
    //   48: aload 5
    //   50: iconst_1
    //   51: aload 6
    //   53: invokeinterface 83 1 0
    //   58: checkcast 86	mineplex/core/preferences/UserPreferences
    //   61: getfield 88	mineplex/core/preferences/UserPreferences:HubGames	Z
    //   64: invokeinterface 92 3 0
    //   69: aload 5
    //   71: iconst_2
    //   72: aload 6
    //   74: invokeinterface 83 1 0
    //   79: checkcast 86	mineplex/core/preferences/UserPreferences
    //   82: getfield 98	mineplex/core/preferences/UserPreferences:ShowPlayers	Z
    //   85: invokeinterface 92 3 0
    //   90: aload 5
    //   92: iconst_3
    //   93: aload 6
    //   95: invokeinterface 83 1 0
    //   100: checkcast 86	mineplex/core/preferences/UserPreferences
    //   103: getfield 101	mineplex/core/preferences/UserPreferences:ShowChat	Z
    //   106: invokeinterface 92 3 0
    //   111: aload 5
    //   113: iconst_4
    //   114: aload 6
    //   116: invokeinterface 83 1 0
    //   121: checkcast 86	mineplex/core/preferences/UserPreferences
    //   124: getfield 104	mineplex/core/preferences/UserPreferences:FriendChat	Z
    //   127: invokeinterface 92 3 0
    //   132: aload 5
    //   134: iconst_5
    //   135: aload 6
    //   137: invokeinterface 83 1 0
    //   142: checkcast 86	mineplex/core/preferences/UserPreferences
    //   145: getfield 107	mineplex/core/preferences/UserPreferences:PrivateMessaging	Z
    //   148: invokeinterface 92 3 0
    //   153: aload 5
    //   155: bipush 6
    //   157: aload 6
    //   159: invokeinterface 83 1 0
    //   164: checkcast 86	mineplex/core/preferences/UserPreferences
    //   167: getfield 110	mineplex/core/preferences/UserPreferences:PartyRequests	Z
    //   170: invokeinterface 92 3 0
    //   175: aload 5
    //   177: bipush 7
    //   179: aload 6
    //   181: invokeinterface 83 1 0
    //   186: checkcast 86	mineplex/core/preferences/UserPreferences
    //   189: getfield 113	mineplex/core/preferences/UserPreferences:Invisibility	Z
    //   192: invokeinterface 92 3 0
    //   197: aload 5
    //   199: bipush 8
    //   201: aload 6
    //   203: invokeinterface 83 1 0
    //   208: checkcast 86	mineplex/core/preferences/UserPreferences
    //   211: getfield 116	mineplex/core/preferences/UserPreferences:HubForcefield	Z
    //   214: invokeinterface 92 3 0
    //   219: aload 5
    //   221: bipush 9
    //   223: aload 6
    //   225: invokeinterface 83 1 0
    //   230: checkcast 86	mineplex/core/preferences/UserPreferences
    //   233: getfield 119	mineplex/core/preferences/UserPreferences:ShowMacReports	Z
    //   236: invokeinterface 92 3 0
    //   241: aload 5
    //   243: bipush 10
    //   245: aload 6
    //   247: invokeinterface 83 1 0
    //   252: checkcast 86	mineplex/core/preferences/UserPreferences
    //   255: getfield 122	mineplex/core/preferences/UserPreferences:IgnoreVelocity	Z
    //   258: invokeinterface 92 3 0
    //   263: aload 5
    //   265: bipush 11
    //   267: aload 6
    //   269: invokeinterface 83 1 0
    //   274: checkcast 86	mineplex/core/preferences/UserPreferences
    //   277: getfield 125	mineplex/core/preferences/UserPreferences:PendingFriendRequests	Z
    //   280: invokeinterface 92 3 0
    //   285: aload 5
    //   287: bipush 12
    //   289: aload 6
    //   291: invokeinterface 83 1 0
    //   296: checkcast 86	mineplex/core/preferences/UserPreferences
    //   299: getfield 128	mineplex/core/preferences/UserPreferences:friendDisplayInventoryUI	Z
    //   302: invokeinterface 92 3 0
    //   307: aload 5
    //   309: bipush 13
    //   311: aload 6
    //   313: invokeinterface 131 1 0
    //   318: checkcast 134	java/lang/String
    //   321: invokeinterface 136 3 0
    //   326: aload 5
    //   328: invokeinterface 140 1 0
    //   333: aload 7
    //   335: invokeinterface 143 1 0
    //   340: ifne -304 -> 36
    //   343: aload 5
    //   345: invokeinterface 147 1 0
    //   350: astore 6
    //   352: iconst_0
    //   353: istore 7
    //   355: aload_1
    //   356: invokevirtual 63	mineplex/core/common/util/NautHashMap:entrySet	()Ljava/util/Set;
    //   359: invokeinterface 69 1 0
    //   364: astore 9
    //   366: goto +349 -> 715
    //   369: aload 9
    //   371: invokeinterface 75 1 0
    //   376: checkcast 81	java/util/Map$Entry
    //   379: astore 8
    //   381: aload 6
    //   383: iload 7
    //   385: iaload
    //   386: iconst_1
    //   387: if_icmpge +325 -> 712
    //   390: aload_0
    //   391: getstatic 18	mineplex/core/preferences/PreferencesRepository:INSERT_ACCOUNT	Ljava/lang/String;
    //   394: iconst_1
    //   395: anewarray 42	mineplex/core/database/column/Column
    //   398: dup
    //   399: iconst_0
    //   400: new 151	mineplex/core/database/column/ColumnVarChar
    //   403: dup
    //   404: ldc -103
    //   406: bipush 100
    //   408: aload 8
    //   410: invokeinterface 131 1 0
    //   415: checkcast 134	java/lang/String
    //   418: invokespecial 155	mineplex/core/database/column/ColumnVarChar:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   421: aastore
    //   422: invokevirtual 44	mineplex/core/preferences/PreferencesRepository:executeUpdate	(Ljava/lang/String;[Lmineplex/core/database/column/Column;)I
    //   425: pop
    //   426: aload 5
    //   428: iconst_1
    //   429: aload 8
    //   431: invokeinterface 83 1 0
    //   436: checkcast 86	mineplex/core/preferences/UserPreferences
    //   439: getfield 88	mineplex/core/preferences/UserPreferences:HubGames	Z
    //   442: invokeinterface 92 3 0
    //   447: aload 5
    //   449: iconst_2
    //   450: aload 8
    //   452: invokeinterface 83 1 0
    //   457: checkcast 86	mineplex/core/preferences/UserPreferences
    //   460: getfield 98	mineplex/core/preferences/UserPreferences:ShowPlayers	Z
    //   463: invokeinterface 92 3 0
    //   468: aload 5
    //   470: iconst_3
    //   471: aload 8
    //   473: invokeinterface 83 1 0
    //   478: checkcast 86	mineplex/core/preferences/UserPreferences
    //   481: getfield 101	mineplex/core/preferences/UserPreferences:ShowChat	Z
    //   484: invokeinterface 92 3 0
    //   489: aload 5
    //   491: iconst_4
    //   492: aload 8
    //   494: invokeinterface 83 1 0
    //   499: checkcast 86	mineplex/core/preferences/UserPreferences
    //   502: getfield 104	mineplex/core/preferences/UserPreferences:FriendChat	Z
    //   505: invokeinterface 92 3 0
    //   510: aload 5
    //   512: iconst_5
    //   513: aload 8
    //   515: invokeinterface 83 1 0
    //   520: checkcast 86	mineplex/core/preferences/UserPreferences
    //   523: getfield 107	mineplex/core/preferences/UserPreferences:PrivateMessaging	Z
    //   526: invokeinterface 92 3 0
    //   531: aload 5
    //   533: bipush 6
    //   535: aload 8
    //   537: invokeinterface 83 1 0
    //   542: checkcast 86	mineplex/core/preferences/UserPreferences
    //   545: getfield 110	mineplex/core/preferences/UserPreferences:PartyRequests	Z
    //   548: invokeinterface 92 3 0
    //   553: aload 5
    //   555: bipush 7
    //   557: aload 8
    //   559: invokeinterface 83 1 0
    //   564: checkcast 86	mineplex/core/preferences/UserPreferences
    //   567: getfield 113	mineplex/core/preferences/UserPreferences:Invisibility	Z
    //   570: invokeinterface 92 3 0
    //   575: aload 5
    //   577: bipush 8
    //   579: aload 8
    //   581: invokeinterface 83 1 0
    //   586: checkcast 86	mineplex/core/preferences/UserPreferences
    //   589: getfield 116	mineplex/core/preferences/UserPreferences:HubForcefield	Z
    //   592: invokeinterface 92 3 0
    //   597: aload 5
    //   599: bipush 9
    //   601: aload 8
    //   603: invokeinterface 83 1 0
    //   608: checkcast 86	mineplex/core/preferences/UserPreferences
    //   611: getfield 119	mineplex/core/preferences/UserPreferences:ShowMacReports	Z
    //   614: invokeinterface 92 3 0
    //   619: aload 5
    //   621: bipush 10
    //   623: aload 8
    //   625: invokeinterface 83 1 0
    //   630: checkcast 86	mineplex/core/preferences/UserPreferences
    //   633: getfield 122	mineplex/core/preferences/UserPreferences:IgnoreVelocity	Z
    //   636: invokeinterface 92 3 0
    //   641: aload 5
    //   643: bipush 11
    //   645: aload 8
    //   647: invokeinterface 83 1 0
    //   652: checkcast 86	mineplex/core/preferences/UserPreferences
    //   655: getfield 125	mineplex/core/preferences/UserPreferences:PendingFriendRequests	Z
    //   658: invokeinterface 92 3 0
    //   663: aload 5
    //   665: bipush 12
    //   667: aload 8
    //   669: invokeinterface 83 1 0
    //   674: checkcast 86	mineplex/core/preferences/UserPreferences
    //   677: getfield 128	mineplex/core/preferences/UserPreferences:friendDisplayInventoryUI	Z
    //   680: invokeinterface 92 3 0
    //   685: aload 5
    //   687: bipush 13
    //   689: aload 8
    //   691: invokeinterface 131 1 0
    //   696: checkcast 134	java/lang/String
    //   699: invokeinterface 136 3 0
    //   704: aload 5
    //   706: invokeinterface 158 1 0
    //   711: pop
    //   712: iinc 7 1
    //   715: aload 9
    //   717: invokeinterface 143 1 0
    //   722: ifne -353 -> 369
    //   725: aload 5
    //   727: ifnull +28 -> 755
    //   730: aload 5
    //   732: invokeinterface 161 1 0
    //   737: goto +18 -> 755
    //   740: astore_2
    //   741: aload 5
    //   743: ifnull +10 -> 753
    //   746: aload 5
    //   748: invokeinterface 161 1 0
    //   753: aload_2
    //   754: athrow
    //   755: aload 4
    //   757: ifnull +74 -> 831
    //   760: aload 4
    //   762: invokeinterface 164 1 0
    //   767: goto +64 -> 831
    //   770: astore_3
    //   771: aload_2
    //   772: ifnonnull +8 -> 780
    //   775: aload_3
    //   776: astore_2
    //   777: goto +13 -> 790
    //   780: aload_2
    //   781: aload_3
    //   782: if_acmpeq +8 -> 790
    //   785: aload_2
    //   786: aload_3
    //   787: invokevirtual 165	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   790: aload 4
    //   792: ifnull +10 -> 802
    //   795: aload 4
    //   797: invokeinterface 164 1 0
    //   802: aload_2
    //   803: athrow
    //   804: astore_3
    //   805: aload_2
    //   806: ifnonnull +8 -> 814
    //   809: aload_3
    //   810: astore_2
    //   811: goto +13 -> 824
    //   814: aload_2
    //   815: aload_3
    //   816: if_acmpeq +8 -> 824
    //   819: aload_2
    //   820: aload_3
    //   821: invokevirtual 165	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   824: aload_2
    //   825: athrow
    //   826: astore_2
    //   827: aload_2
    //   828: invokevirtual 171	java/lang/Exception:printStackTrace	()V
    //   831: return
    // Line number table:
    //   Java source line #40	-> byte code offset #0
    //   Java source line #42	-> byte code offset #4
    //   Java source line #43	-> byte code offset #10
    //   Java source line #46	-> byte code offset #22
    //   Java source line #48	-> byte code offset #48
    //   Java source line #49	-> byte code offset #69
    //   Java source line #50	-> byte code offset #90
    //   Java source line #51	-> byte code offset #111
    //   Java source line #52	-> byte code offset #132
    //   Java source line #53	-> byte code offset #153
    //   Java source line #54	-> byte code offset #175
    //   Java source line #55	-> byte code offset #197
    //   Java source line #56	-> byte code offset #219
    //   Java source line #57	-> byte code offset #241
    //   Java source line #58	-> byte code offset #263
    //   Java source line #59	-> byte code offset #285
    //   Java source line #60	-> byte code offset #307
    //   Java source line #62	-> byte code offset #326
    //   Java source line #46	-> byte code offset #333
    //   Java source line #65	-> byte code offset #343
    //   Java source line #66	-> byte code offset #352
    //   Java source line #68	-> byte code offset #355
    //   Java source line #70	-> byte code offset #381
    //   Java source line #72	-> byte code offset #390
    //   Java source line #74	-> byte code offset #426
    //   Java source line #75	-> byte code offset #447
    //   Java source line #76	-> byte code offset #468
    //   Java source line #77	-> byte code offset #489
    //   Java source line #78	-> byte code offset #510
    //   Java source line #79	-> byte code offset #531
    //   Java source line #80	-> byte code offset #553
    //   Java source line #81	-> byte code offset #575
    //   Java source line #82	-> byte code offset #597
    //   Java source line #83	-> byte code offset #619
    //   Java source line #84	-> byte code offset #641
    //   Java source line #85	-> byte code offset #663
    //   Java source line #86	-> byte code offset #685
    //   Java source line #87	-> byte code offset #704
    //   Java source line #90	-> byte code offset #712
    //   Java source line #68	-> byte code offset #715
    //   Java source line #92	-> byte code offset #725
    //   Java source line #93	-> byte code offset #826
    //   Java source line #95	-> byte code offset #827
    //   Java source line #97	-> byte code offset #831
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	832	0	this	PreferencesRepository
    //   0	832	1	preferences	mineplex.core.common.util.NautHashMap<String, UserPreferences>
    //   1	1	2	localObject1	Object
    //   740	32	2	localObject2	Object
    //   776	49	2	localObject3	Object
    //   826	2	2	exception	Exception
    //   3	1	3	localObject4	Object
    //   770	17	3	localThrowable1	Throwable
    //   804	17	3	localThrowable2	Throwable
    //   8	788	4	connection	java.sql.Connection
    //   20	727	5	preparedStatement	java.sql.PreparedStatement
    //   46	266	6	entry	java.util.Map.Entry<String, UserPreferences>
    //   350	32	6	rowsAffected	int[]
    //   31	303	7	localIterator1	java.util.Iterator
    //   353	360	7	i	int
    //   379	311	8	entry	java.util.Map.Entry<String, UserPreferences>
    //   364	352	9	localIterator2	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   22	725	740	finally
    //   10	755	770	finally
    //   4	804	804	finally
    //   0	826	826	java/lang/Exception
  }
  
  public UserPreferences loadClientInformation(ResultSet resultSet)
    throws SQLException
  {
    UserPreferences preferences = new UserPreferences();
    if (resultSet.next())
    {
      preferences.HubGames = resultSet.getBoolean(1);
      preferences.ShowPlayers = resultSet.getBoolean(2);
      preferences.ShowChat = resultSet.getBoolean(3);
      preferences.FriendChat = resultSet.getBoolean(4);
      preferences.PrivateMessaging = resultSet.getBoolean(5);
      preferences.PartyRequests = resultSet.getBoolean(6);
      preferences.Invisibility = resultSet.getBoolean(7);
      preferences.HubForcefield = resultSet.getBoolean(8);
      preferences.ShowMacReports = resultSet.getBoolean(9);
      preferences.IgnoreVelocity = resultSet.getBoolean(10);
      preferences.PendingFriendRequests = resultSet.getBoolean(11);
      preferences.friendDisplayInventoryUI = resultSet.getBoolean(12);
    }
    return preferences;
  }
}

package mineplex.serverdata.redis;

import java.util.Collection;
import java.util.HashSet;
import mineplex.serverdata.Region;
import mineplex.serverdata.Utility;
import mineplex.serverdata.data.MinecraftServer;
import mineplex.serverdata.servers.ConnectionData;
import mineplex.serverdata.servers.ServerRepository;
import redis.clients.jedis.JedisPool;

public class RedisServerRepository
  implements ServerRepository
{
  public final char KEY_DELIMITER = '.';
  private JedisPool _writePool;
  private JedisPool _readPool;
  private Region _region;
  
  public RedisServerRepository(ConnectionData writeConn, ConnectionData readConn, Region region)
  {
    this._writePool = Utility.generatePool(writeConn);
    this._readPool = (writeConn == readConn ? this._writePool : Utility.generatePool(readConn));
    this._region = region;
  }
  
  public Collection<MinecraftServer> getServerStatuses()
  {
    return getServerStatusesByPrefix("");
  }
  
  /* Error */
  public Collection<MinecraftServer> getServerStatusesByPrefix(String prefix)
  {
    // Byte code:
    //   0: new 62	java/util/HashSet
    //   3: dup
    //   4: invokespecial 64	java/util/HashSet:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 69	redis/clients/jedis/Jedis
    //   18: astore_3
    //   19: aload_0
    //   20: iconst_3
    //   21: anewarray 71	java/lang/String
    //   24: dup
    //   25: iconst_0
    //   26: ldc 73
    //   28: aastore
    //   29: dup
    //   30: iconst_1
    //   31: ldc 75
    //   33: aastore
    //   34: dup
    //   35: iconst_2
    //   36: aload_0
    //   37: getfield 34	mineplex/serverdata/redis/RedisServerRepository:_region	Lmineplex/serverdata/Region;
    //   40: invokevirtual 77	mineplex/serverdata/Region:toString	()Ljava/lang/String;
    //   43: aastore
    //   44: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   47: astore 4
    //   49: aload_3
    //   50: invokevirtual 85	redis/clients/jedis/Jedis:pipelined	()Lredis/clients/jedis/Pipeline;
    //   53: astore 5
    //   55: new 89	java/util/ArrayList
    //   58: dup
    //   59: invokespecial 91	java/util/ArrayList:<init>	()V
    //   62: astore 6
    //   64: aload_0
    //   65: aload 4
    //   67: invokevirtual 92	mineplex/serverdata/redis/RedisServerRepository:getActiveNames	(Ljava/lang/String;)Ljava/util/Set;
    //   70: invokeinterface 96 1 0
    //   75: astore 8
    //   77: goto +66 -> 143
    //   80: aload 8
    //   82: invokeinterface 102 1 0
    //   87: checkcast 71	java/lang/String
    //   90: astore 7
    //   92: aload_1
    //   93: invokevirtual 107	java/lang/String:isEmpty	()Z
    //   96: ifne +12 -> 108
    //   99: aload 7
    //   101: aload_1
    //   102: invokevirtual 111	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   105: ifeq +38 -> 143
    //   108: aload_0
    //   109: iconst_2
    //   110: anewarray 71	java/lang/String
    //   113: dup
    //   114: iconst_0
    //   115: aload 4
    //   117: aastore
    //   118: dup
    //   119: iconst_1
    //   120: aload 7
    //   122: aastore
    //   123: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   126: astore 9
    //   128: aload 6
    //   130: aload 5
    //   132: aload 9
    //   134: invokevirtual 115	redis/clients/jedis/Pipeline:get	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   137: invokeinterface 121 2 0
    //   142: pop
    //   143: aload 8
    //   145: invokeinterface 127 1 0
    //   150: ifne -70 -> 80
    //   153: aload 5
    //   155: invokevirtual 130	redis/clients/jedis/Pipeline:sync	()V
    //   158: aload 6
    //   160: invokeinterface 133 1 0
    //   165: astore 8
    //   167: goto +51 -> 218
    //   170: aload 8
    //   172: invokeinterface 102 1 0
    //   177: checkcast 134	redis/clients/jedis/Response
    //   180: astore 7
    //   182: aload 7
    //   184: invokevirtual 136	redis/clients/jedis/Response:get	()Ljava/lang/Object;
    //   187: checkcast 71	java/lang/String
    //   190: astore 9
    //   192: aload 9
    //   194: ldc -118
    //   196: invokestatic 140	mineplex/serverdata/Utility:deserialize	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   199: checkcast 138	mineplex/serverdata/data/MinecraftServer
    //   202: astore 10
    //   204: aload 10
    //   206: ifnull +12 -> 218
    //   209: aload_2
    //   210: aload 10
    //   212: invokeinterface 144 2 0
    //   217: pop
    //   218: aload 8
    //   220: invokeinterface 127 1 0
    //   225: ifne -55 -> 170
    //   228: goto +52 -> 280
    //   231: astore 4
    //   233: aload 4
    //   235: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   238: aload_0
    //   239: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   242: aload_3
    //   243: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   246: aconst_null
    //   247: astore_3
    //   248: aload_3
    //   249: ifnull +43 -> 292
    //   252: aload_0
    //   253: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   256: aload_3
    //   257: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   260: goto +32 -> 292
    //   263: astore 11
    //   265: aload_3
    //   266: ifnull +11 -> 277
    //   269: aload_0
    //   270: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   273: aload_3
    //   274: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   277: aload 11
    //   279: athrow
    //   280: aload_3
    //   281: ifnull +11 -> 292
    //   284: aload_0
    //   285: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   288: aload_3
    //   289: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   292: aload_2
    //   293: areturn
    // Line number table:
    //   Java source line #67	-> byte code offset #0
    //   Java source line #68	-> byte code offset #8
    //   Java source line #72	-> byte code offset #19
    //   Java source line #73	-> byte code offset #49
    //   Java source line #75	-> byte code offset #55
    //   Java source line #76	-> byte code offset #64
    //   Java source line #78	-> byte code offset #92
    //   Java source line #80	-> byte code offset #108
    //   Java source line #81	-> byte code offset #128
    //   Java source line #76	-> byte code offset #143
    //   Java source line #85	-> byte code offset #153
    //   Java source line #87	-> byte code offset #158
    //   Java source line #89	-> byte code offset #182
    //   Java source line #90	-> byte code offset #192
    //   Java source line #92	-> byte code offset #204
    //   Java source line #94	-> byte code offset #209
    //   Java source line #87	-> byte code offset #218
    //   Java source line #97	-> byte code offset #228
    //   Java source line #98	-> byte code offset #231
    //   Java source line #100	-> byte code offset #233
    //   Java source line #101	-> byte code offset #238
    //   Java source line #102	-> byte code offset #246
    //   Java source line #106	-> byte code offset #248
    //   Java source line #108	-> byte code offset #252
    //   Java source line #105	-> byte code offset #263
    //   Java source line #106	-> byte code offset #265
    //   Java source line #108	-> byte code offset #269
    //   Java source line #110	-> byte code offset #277
    //   Java source line #106	-> byte code offset #280
    //   Java source line #108	-> byte code offset #284
    //   Java source line #112	-> byte code offset #292
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	294	0	this	RedisServerRepository
    //   0	294	1	prefix	String
    //   7	286	2	servers	Collection<MinecraftServer>
    //   18	271	3	jedis	redis.clients.jedis.Jedis
    //   47	69	4	setKey	String
    //   231	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   53	101	5	pipeline	redis.clients.jedis.Pipeline
    //   62	97	6	responses	java.util.List<redis.clients.jedis.Response<String>>
    //   90	31	7	serverName	String
    //   180	3	7	response	redis.clients.jedis.Response<String>
    //   75	144	8	localIterator	java.util.Iterator
    //   126	7	9	dataKey	String
    //   190	3	9	serializedData	String
    //   202	9	10	server	MinecraftServer
    //   263	15	11	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   19	228	231	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	248	263	finally
  }
  
  public Collection<MinecraftServer> getServersByGroup(String serverGroup)
  {
    Collection<MinecraftServer> servers = new HashSet();
    for (MinecraftServer server : getServerStatuses()) {
      if (server.getGroup().equalsIgnoreCase(serverGroup)) {
        servers.add(server);
      }
    }
    return servers;
  }
  
  /* Error */
  public MinecraftServer getServerStatus(String serverName)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   6: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   9: checkcast 69	redis/clients/jedis/Jedis
    //   12: astore_3
    //   13: aload_0
    //   14: iconst_3
    //   15: anewarray 71	java/lang/String
    //   18: dup
    //   19: iconst_0
    //   20: ldc 73
    //   22: aastore
    //   23: dup
    //   24: iconst_1
    //   25: ldc 75
    //   27: aastore
    //   28: dup
    //   29: iconst_2
    //   30: aload_0
    //   31: getfield 34	mineplex/serverdata/redis/RedisServerRepository:_region	Lmineplex/serverdata/Region;
    //   34: invokevirtual 77	mineplex/serverdata/Region:toString	()Ljava/lang/String;
    //   37: aastore
    //   38: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   41: astore 4
    //   43: aload_0
    //   44: iconst_2
    //   45: anewarray 71	java/lang/String
    //   48: dup
    //   49: iconst_0
    //   50: aload 4
    //   52: aastore
    //   53: dup
    //   54: iconst_1
    //   55: aload_1
    //   56: aastore
    //   57: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   60: astore 5
    //   62: aload_3
    //   63: aload 5
    //   65: invokevirtual 198	redis/clients/jedis/Jedis:get	(Ljava/lang/String;)Ljava/lang/String;
    //   68: astore 6
    //   70: aload 6
    //   72: ldc -118
    //   74: invokestatic 140	mineplex/serverdata/Utility:deserialize	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   77: checkcast 138	mineplex/serverdata/data/MinecraftServer
    //   80: astore_2
    //   81: goto +52 -> 133
    //   84: astore 4
    //   86: aload 4
    //   88: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   91: aload_0
    //   92: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   95: aload_3
    //   96: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   99: aconst_null
    //   100: astore_3
    //   101: aload_3
    //   102: ifnull +43 -> 145
    //   105: aload_0
    //   106: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   109: aload_3
    //   110: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   113: goto +32 -> 145
    //   116: astore 7
    //   118: aload_3
    //   119: ifnull +11 -> 130
    //   122: aload_0
    //   123: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   126: aload_3
    //   127: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   130: aload 7
    //   132: athrow
    //   133: aload_3
    //   134: ifnull +11 -> 145
    //   137: aload_0
    //   138: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   141: aload_3
    //   142: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   145: aload_2
    //   146: areturn
    // Line number table:
    //   Java source line #134	-> byte code offset #0
    //   Java source line #135	-> byte code offset #2
    //   Java source line #139	-> byte code offset #13
    //   Java source line #140	-> byte code offset #43
    //   Java source line #141	-> byte code offset #62
    //   Java source line #142	-> byte code offset #70
    //   Java source line #143	-> byte code offset #81
    //   Java source line #144	-> byte code offset #84
    //   Java source line #146	-> byte code offset #86
    //   Java source line #147	-> byte code offset #91
    //   Java source line #148	-> byte code offset #99
    //   Java source line #152	-> byte code offset #101
    //   Java source line #154	-> byte code offset #105
    //   Java source line #151	-> byte code offset #116
    //   Java source line #152	-> byte code offset #118
    //   Java source line #154	-> byte code offset #122
    //   Java source line #156	-> byte code offset #130
    //   Java source line #152	-> byte code offset #133
    //   Java source line #154	-> byte code offset #137
    //   Java source line #158	-> byte code offset #145
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	147	0	this	RedisServerRepository
    //   0	147	1	serverName	String
    //   1	145	2	server	MinecraftServer
    //   12	130	3	jedis	redis.clients.jedis.Jedis
    //   41	10	4	setKey	String
    //   84	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   60	4	5	dataKey	String
    //   68	3	6	serializedData	String
    //   116	15	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   13	81	84	redis/clients/jedis/exceptions/JedisConnectionException
    //   13	101	116	finally
  }
  
  /* Error */
  public void updataServerStatus(MinecraftServer serverData, int timeout)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   4: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   7: checkcast 69	redis/clients/jedis/Jedis
    //   10: astore_3
    //   11: aload_1
    //   12: invokestatic 203	mineplex/serverdata/Utility:serialize	(Ljava/lang/Object;)Ljava/lang/String;
    //   15: astore 4
    //   17: aload_1
    //   18: invokevirtual 207	mineplex/serverdata/data/MinecraftServer:getName	()Ljava/lang/String;
    //   21: astore 5
    //   23: aload_0
    //   24: iconst_3
    //   25: anewarray 71	java/lang/String
    //   28: dup
    //   29: iconst_0
    //   30: ldc 73
    //   32: aastore
    //   33: dup
    //   34: iconst_1
    //   35: ldc 75
    //   37: aastore
    //   38: dup
    //   39: iconst_2
    //   40: aload_0
    //   41: getfield 34	mineplex/serverdata/redis/RedisServerRepository:_region	Lmineplex/serverdata/Region;
    //   44: invokevirtual 77	mineplex/serverdata/Region:toString	()Ljava/lang/String;
    //   47: aastore
    //   48: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   51: astore 6
    //   53: aload_0
    //   54: iconst_2
    //   55: anewarray 71	java/lang/String
    //   58: dup
    //   59: iconst_0
    //   60: aload 6
    //   62: aastore
    //   63: dup
    //   64: iconst_1
    //   65: aload 5
    //   67: aastore
    //   68: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   71: astore 7
    //   73: invokestatic 210	mineplex/serverdata/Utility:currentTimeSeconds	()J
    //   76: iload_2
    //   77: i2l
    //   78: ladd
    //   79: lstore 8
    //   81: aload_3
    //   82: invokevirtual 214	redis/clients/jedis/Jedis:multi	()Lredis/clients/jedis/Transaction;
    //   85: astore 10
    //   87: aload 10
    //   89: aload 7
    //   91: aload 4
    //   93: invokevirtual 218	redis/clients/jedis/Transaction:set	(Ljava/lang/String;Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   96: pop
    //   97: aload 10
    //   99: aload 6
    //   101: lload 8
    //   103: l2d
    //   104: aload 5
    //   106: invokevirtual 224	redis/clients/jedis/Transaction:zadd	(Ljava/lang/String;DLjava/lang/String;)Lredis/clients/jedis/Response;
    //   109: pop
    //   110: aload 10
    //   112: invokevirtual 228	redis/clients/jedis/Transaction:exec	()Ljava/util/List;
    //   115: pop
    //   116: goto +52 -> 168
    //   119: astore 4
    //   121: aload 4
    //   123: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   126: aload_0
    //   127: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   130: aload_3
    //   131: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   134: aconst_null
    //   135: astore_3
    //   136: aload_3
    //   137: ifnull +43 -> 180
    //   140: aload_0
    //   141: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   144: aload_3
    //   145: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   148: goto +32 -> 180
    //   151: astore 11
    //   153: aload_3
    //   154: ifnull +11 -> 165
    //   157: aload_0
    //   158: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   161: aload_3
    //   162: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   165: aload 11
    //   167: athrow
    //   168: aload_3
    //   169: ifnull +11 -> 180
    //   172: aload_0
    //   173: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   176: aload_3
    //   177: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   180: return
    // Line number table:
    //   Java source line #164	-> byte code offset #0
    //   Java source line #168	-> byte code offset #11
    //   Java source line #169	-> byte code offset #17
    //   Java source line #170	-> byte code offset #23
    //   Java source line #171	-> byte code offset #53
    //   Java source line #172	-> byte code offset #73
    //   Java source line #174	-> byte code offset #81
    //   Java source line #175	-> byte code offset #87
    //   Java source line #176	-> byte code offset #97
    //   Java source line #177	-> byte code offset #110
    //   Java source line #178	-> byte code offset #116
    //   Java source line #179	-> byte code offset #119
    //   Java source line #181	-> byte code offset #121
    //   Java source line #182	-> byte code offset #126
    //   Java source line #183	-> byte code offset #134
    //   Java source line #187	-> byte code offset #136
    //   Java source line #189	-> byte code offset #140
    //   Java source line #186	-> byte code offset #151
    //   Java source line #187	-> byte code offset #153
    //   Java source line #189	-> byte code offset #157
    //   Java source line #191	-> byte code offset #165
    //   Java source line #187	-> byte code offset #168
    //   Java source line #189	-> byte code offset #172
    //   Java source line #192	-> byte code offset #180
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	181	0	this	RedisServerRepository
    //   0	181	1	serverData	MinecraftServer
    //   0	181	2	timeout	int
    //   10	167	3	jedis	redis.clients.jedis.Jedis
    //   15	77	4	serializedData	String
    //   119	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   21	84	5	serverName	String
    //   51	49	6	setKey	String
    //   71	19	7	dataKey	String
    //   79	23	8	expiry	long
    //   85	26	10	transaction	redis.clients.jedis.Transaction
    //   151	15	11	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   11	116	119	redis/clients/jedis/exceptions/JedisConnectionException
    //   11	136	151	finally
  }
  
  /* Error */
  public void removeServerStatus(MinecraftServer serverData)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   4: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   7: checkcast 69	redis/clients/jedis/Jedis
    //   10: astore_2
    //   11: aload_1
    //   12: invokevirtual 207	mineplex/serverdata/data/MinecraftServer:getName	()Ljava/lang/String;
    //   15: astore_3
    //   16: aload_0
    //   17: iconst_3
    //   18: anewarray 71	java/lang/String
    //   21: dup
    //   22: iconst_0
    //   23: ldc 73
    //   25: aastore
    //   26: dup
    //   27: iconst_1
    //   28: ldc 75
    //   30: aastore
    //   31: dup
    //   32: iconst_2
    //   33: aload_0
    //   34: getfield 34	mineplex/serverdata/redis/RedisServerRepository:_region	Lmineplex/serverdata/Region;
    //   37: invokevirtual 77	mineplex/serverdata/Region:toString	()Ljava/lang/String;
    //   40: aastore
    //   41: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   44: astore 4
    //   46: aload_0
    //   47: iconst_2
    //   48: anewarray 71	java/lang/String
    //   51: dup
    //   52: iconst_0
    //   53: aload 4
    //   55: aastore
    //   56: dup
    //   57: iconst_1
    //   58: aload_3
    //   59: aastore
    //   60: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   63: astore 5
    //   65: aload_2
    //   66: invokevirtual 214	redis/clients/jedis/Jedis:multi	()Lredis/clients/jedis/Transaction;
    //   69: astore 6
    //   71: aload 6
    //   73: aload 5
    //   75: invokevirtual 241	redis/clients/jedis/Transaction:del	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   78: pop
    //   79: aload 6
    //   81: aload 4
    //   83: iconst_1
    //   84: anewarray 71	java/lang/String
    //   87: dup
    //   88: iconst_0
    //   89: aload_3
    //   90: aastore
    //   91: invokevirtual 244	redis/clients/jedis/Transaction:zrem	(Ljava/lang/String;[Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   94: pop
    //   95: aload 6
    //   97: invokevirtual 228	redis/clients/jedis/Transaction:exec	()Ljava/util/List;
    //   100: pop
    //   101: goto +50 -> 151
    //   104: astore_3
    //   105: aload_3
    //   106: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   109: aload_0
    //   110: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   113: aload_2
    //   114: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   117: aconst_null
    //   118: astore_2
    //   119: aload_2
    //   120: ifnull +43 -> 163
    //   123: aload_0
    //   124: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   127: aload_2
    //   128: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   131: goto +32 -> 163
    //   134: astore 7
    //   136: aload_2
    //   137: ifnull +11 -> 148
    //   140: aload_0
    //   141: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   144: aload_2
    //   145: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   148: aload 7
    //   150: athrow
    //   151: aload_2
    //   152: ifnull +11 -> 163
    //   155: aload_0
    //   156: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   159: aload_2
    //   160: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   163: return
    // Line number table:
    //   Java source line #197	-> byte code offset #0
    //   Java source line #201	-> byte code offset #11
    //   Java source line #202	-> byte code offset #16
    //   Java source line #203	-> byte code offset #46
    //   Java source line #205	-> byte code offset #65
    //   Java source line #206	-> byte code offset #71
    //   Java source line #207	-> byte code offset #79
    //   Java source line #208	-> byte code offset #95
    //   Java source line #209	-> byte code offset #101
    //   Java source line #210	-> byte code offset #104
    //   Java source line #212	-> byte code offset #105
    //   Java source line #213	-> byte code offset #109
    //   Java source line #214	-> byte code offset #117
    //   Java source line #218	-> byte code offset #119
    //   Java source line #220	-> byte code offset #123
    //   Java source line #217	-> byte code offset #134
    //   Java source line #218	-> byte code offset #136
    //   Java source line #220	-> byte code offset #140
    //   Java source line #222	-> byte code offset #148
    //   Java source line #218	-> byte code offset #151
    //   Java source line #220	-> byte code offset #155
    //   Java source line #223	-> byte code offset #163
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	RedisServerRepository
    //   0	164	1	serverData	MinecraftServer
    //   10	150	2	jedis	redis.clients.jedis.Jedis
    //   15	75	3	serverName	String
    //   104	2	3	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   44	38	4	setKey	String
    //   63	11	5	dataKey	String
    //   69	27	6	transaction	redis.clients.jedis.Transaction
    //   134	15	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   11	101	104	redis/clients/jedis/exceptions/JedisConnectionException
    //   11	119	134	finally
  }
  
  public boolean serverExists(String serverName)
  {
    return getServerStatus(serverName) != null;
  }
  
  /* Error */
  public Collection<mineplex.serverdata.data.DedicatedServer> getDedicatedServers()
  {
    // Byte code:
    //   0: new 62	java/util/HashSet
    //   3: dup
    //   4: invokespecial 64	java/util/HashSet:<init>	()V
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 69	redis/clients/jedis/Jedis
    //   18: astore_2
    //   19: aload_0
    //   20: iconst_2
    //   21: anewarray 71	java/lang/String
    //   24: dup
    //   25: iconst_0
    //   26: ldc 73
    //   28: aastore
    //   29: dup
    //   30: iconst_1
    //   31: ldc -3
    //   33: aastore
    //   34: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   37: astore_3
    //   38: aload_2
    //   39: aload_3
    //   40: invokevirtual 255	redis/clients/jedis/Jedis:smembers	(Ljava/lang/String;)Ljava/util/Set;
    //   43: astore 4
    //   45: new 258	java/util/HashMap
    //   48: dup
    //   49: invokespecial 260	java/util/HashMap:<init>	()V
    //   52: astore 5
    //   54: aload_2
    //   55: invokevirtual 85	redis/clients/jedis/Jedis:pipelined	()Lredis/clients/jedis/Pipeline;
    //   58: astore 6
    //   60: aload 4
    //   62: invokeinterface 96 1 0
    //   67: astore 8
    //   69: goto +49 -> 118
    //   72: aload 8
    //   74: invokeinterface 102 1 0
    //   79: checkcast 71	java/lang/String
    //   82: astore 7
    //   84: aload_0
    //   85: iconst_2
    //   86: anewarray 71	java/lang/String
    //   89: dup
    //   90: iconst_0
    //   91: aload_3
    //   92: aastore
    //   93: dup
    //   94: iconst_1
    //   95: aload 7
    //   97: aastore
    //   98: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   101: astore 9
    //   103: aload 5
    //   105: aload 7
    //   107: aload 6
    //   109: aload 9
    //   111: invokevirtual 261	redis/clients/jedis/Pipeline:hgetAll	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   114: invokevirtual 264	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   117: pop
    //   118: aload 8
    //   120: invokeinterface 127 1 0
    //   125: ifne -53 -> 72
    //   128: aload 6
    //   130: invokevirtual 130	redis/clients/jedis/Pipeline:sync	()V
    //   133: aload 5
    //   135: invokevirtual 268	java/util/HashMap:entrySet	()Ljava/util/Set;
    //   138: invokeinterface 96 1 0
    //   143: astore 8
    //   145: goto +108 -> 253
    //   148: aload 8
    //   150: invokeinterface 102 1 0
    //   155: checkcast 272	java/util/Map$Entry
    //   158: astore 7
    //   160: aload 7
    //   162: invokeinterface 274 1 0
    //   167: checkcast 134	redis/clients/jedis/Response
    //   170: invokevirtual 136	redis/clients/jedis/Response:get	()Ljava/lang/Object;
    //   173: checkcast 277	java/util/Map
    //   176: astore 9
    //   178: new 279	mineplex/serverdata/data/DedicatedServer
    //   181: dup
    //   182: aload 9
    //   184: invokespecial 281	mineplex/serverdata/data/DedicatedServer:<init>	(Ljava/util/Map;)V
    //   187: astore 10
    //   189: aload 10
    //   191: invokevirtual 284	mineplex/serverdata/data/DedicatedServer:getRegion	()Lmineplex/serverdata/Region;
    //   194: aload_0
    //   195: getfield 34	mineplex/serverdata/redis/RedisServerRepository:_region	Lmineplex/serverdata/Region;
    //   198: if_acmpne +55 -> 253
    //   201: aload_1
    //   202: aload 10
    //   204: invokeinterface 144 2 0
    //   209: pop
    //   210: goto +43 -> 253
    //   213: astore 10
    //   215: getstatic 288	java/lang/System:out	Ljava/io/PrintStream;
    //   218: new 294	java/lang/StringBuilder
    //   221: dup
    //   222: aload 7
    //   224: invokeinterface 296 1 0
    //   229: checkcast 71	java/lang/String
    //   232: invokestatic 299	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   235: invokespecial 302	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   238: ldc_w 305
    //   241: invokevirtual 307	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: invokevirtual 311	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   247: invokevirtual 312	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   250: aload 10
    //   252: athrow
    //   253: aload 8
    //   255: invokeinterface 127 1 0
    //   260: ifne -112 -> 148
    //   263: goto +50 -> 313
    //   266: astore_3
    //   267: aload_3
    //   268: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   271: aload_0
    //   272: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   275: aload_2
    //   276: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   279: aconst_null
    //   280: astore_2
    //   281: aload_2
    //   282: ifnull +43 -> 325
    //   285: aload_0
    //   286: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   289: aload_2
    //   290: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   293: goto +32 -> 325
    //   296: astore 11
    //   298: aload_2
    //   299: ifnull +11 -> 310
    //   302: aload_0
    //   303: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   306: aload_2
    //   307: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   310: aload 11
    //   312: athrow
    //   313: aload_2
    //   314: ifnull +11 -> 325
    //   317: aload_0
    //   318: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   321: aload_2
    //   322: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   325: aload_1
    //   326: areturn
    // Line number table:
    //   Java source line #234	-> byte code offset #0
    //   Java source line #235	-> byte code offset #8
    //   Java source line #239	-> byte code offset #19
    //   Java source line #240	-> byte code offset #38
    //   Java source line #241	-> byte code offset #45
    //   Java source line #243	-> byte code offset #54
    //   Java source line #245	-> byte code offset #60
    //   Java source line #247	-> byte code offset #84
    //   Java source line #248	-> byte code offset #103
    //   Java source line #245	-> byte code offset #118
    //   Java source line #251	-> byte code offset #128
    //   Java source line #253	-> byte code offset #133
    //   Java source line #255	-> byte code offset #160
    //   Java source line #259	-> byte code offset #178
    //   Java source line #261	-> byte code offset #189
    //   Java source line #262	-> byte code offset #201
    //   Java source line #263	-> byte code offset #210
    //   Java source line #264	-> byte code offset #213
    //   Java source line #266	-> byte code offset #215
    //   Java source line #267	-> byte code offset #250
    //   Java source line #253	-> byte code offset #253
    //   Java source line #270	-> byte code offset #263
    //   Java source line #271	-> byte code offset #266
    //   Java source line #273	-> byte code offset #267
    //   Java source line #274	-> byte code offset #271
    //   Java source line #275	-> byte code offset #279
    //   Java source line #279	-> byte code offset #281
    //   Java source line #281	-> byte code offset #285
    //   Java source line #278	-> byte code offset #296
    //   Java source line #279	-> byte code offset #298
    //   Java source line #281	-> byte code offset #302
    //   Java source line #283	-> byte code offset #310
    //   Java source line #279	-> byte code offset #313
    //   Java source line #281	-> byte code offset #317
    //   Java source line #285	-> byte code offset #325
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	327	0	this	RedisServerRepository
    //   7	319	1	servers	Collection<mineplex.serverdata.data.DedicatedServer>
    //   18	304	2	jedis	redis.clients.jedis.Jedis
    //   37	55	3	key	String
    //   266	2	3	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   43	18	4	serverNames	java.util.Set<String>
    //   52	82	5	serverDatas	java.util.HashMap<String, redis.clients.jedis.Response<java.util.Map<String, String>>>
    //   58	71	6	pipeline	redis.clients.jedis.Pipeline
    //   82	24	7	serverName	String
    //   158	65	7	responseEntry	java.util.Map.Entry<String, redis.clients.jedis.Response<java.util.Map<String, String>>>
    //   67	187	8	localIterator	java.util.Iterator
    //   101	9	9	dataKey	String
    //   176	7	9	data	java.util.Map<String, String>
    //   187	16	10	server	mineplex.serverdata.data.DedicatedServer
    //   213	38	10	ex	Exception
    //   296	15	11	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   178	210	213	java/lang/Exception
    //   19	263	266	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	281	296	finally
  }
  
  /* Error */
  public Collection<mineplex.serverdata.data.ServerGroup> getServerGroups(Collection<MinecraftServer> serverStatuses)
  {
    // Byte code:
    //   0: new 62	java/util/HashSet
    //   3: dup
    //   4: invokespecial 64	java/util/HashSet:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 69	redis/clients/jedis/Jedis
    //   18: astore_3
    //   19: ldc_w 339
    //   22: astore 4
    //   24: aload_3
    //   25: aload 4
    //   27: invokevirtual 255	redis/clients/jedis/Jedis:smembers	(Ljava/lang/String;)Ljava/util/Set;
    //   30: astore 5
    //   32: new 62	java/util/HashSet
    //   35: dup
    //   36: invokespecial 64	java/util/HashSet:<init>	()V
    //   39: astore 6
    //   41: aload_3
    //   42: invokevirtual 85	redis/clients/jedis/Jedis:pipelined	()Lredis/clients/jedis/Pipeline;
    //   45: astore 7
    //   47: aload 5
    //   49: invokeinterface 96 1 0
    //   54: astore 9
    //   56: goto +50 -> 106
    //   59: aload 9
    //   61: invokeinterface 102 1 0
    //   66: checkcast 71	java/lang/String
    //   69: astore 8
    //   71: aload_0
    //   72: iconst_2
    //   73: anewarray 71	java/lang/String
    //   76: dup
    //   77: iconst_0
    //   78: aload 4
    //   80: aastore
    //   81: dup
    //   82: iconst_1
    //   83: aload 8
    //   85: aastore
    //   86: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   89: astore 10
    //   91: aload 6
    //   93: aload 7
    //   95: aload 10
    //   97: invokevirtual 261	redis/clients/jedis/Pipeline:hgetAll	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   100: invokeinterface 341 2 0
    //   105: pop
    //   106: aload 9
    //   108: invokeinterface 127 1 0
    //   113: ifne -54 -> 59
    //   116: aload 7
    //   118: invokevirtual 130	redis/clients/jedis/Pipeline:sync	()V
    //   121: aload 6
    //   123: invokeinterface 96 1 0
    //   128: astore 9
    //   130: goto +114 -> 244
    //   133: aload 9
    //   135: invokeinterface 102 1 0
    //   140: checkcast 134	redis/clients/jedis/Response
    //   143: astore 8
    //   145: aload 8
    //   147: invokevirtual 136	redis/clients/jedis/Response:get	()Ljava/lang/Object;
    //   150: checkcast 277	java/util/Map
    //   153: astore 10
    //   155: new 342	mineplex/serverdata/data/ServerGroup
    //   158: dup
    //   159: aload 10
    //   161: aload_1
    //   162: invokespecial 344	mineplex/serverdata/data/ServerGroup:<init>	(Ljava/util/Map;Ljava/util/Collection;)V
    //   165: astore 11
    //   167: aload 11
    //   169: invokevirtual 347	mineplex/serverdata/data/ServerGroup:getRegion	()Lmineplex/serverdata/Region;
    //   172: getstatic 348	mineplex/serverdata/Region:ALL	Lmineplex/serverdata/Region;
    //   175: if_acmpeq +15 -> 190
    //   178: aload 11
    //   180: invokevirtual 347	mineplex/serverdata/data/ServerGroup:getRegion	()Lmineplex/serverdata/Region;
    //   183: aload_0
    //   184: getfield 34	mineplex/serverdata/redis/RedisServerRepository:_region	Lmineplex/serverdata/Region;
    //   187: if_acmpne +57 -> 244
    //   190: aload_2
    //   191: aload 11
    //   193: invokeinterface 144 2 0
    //   198: pop
    //   199: goto +45 -> 244
    //   202: astore 11
    //   204: getstatic 288	java/lang/System:out	Ljava/io/PrintStream;
    //   207: new 294	java/lang/StringBuilder
    //   210: dup
    //   211: ldc_w 351
    //   214: invokespecial 302	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   217: aload 10
    //   219: ldc_w 353
    //   222: invokeinterface 355 2 0
    //   227: checkcast 71	java/lang/String
    //   230: invokevirtual 307	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: invokevirtual 311	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   236: invokevirtual 312	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   239: aload 11
    //   241: invokevirtual 358	java/lang/Exception:printStackTrace	()V
    //   244: aload 9
    //   246: invokeinterface 127 1 0
    //   251: ifne -118 -> 133
    //   254: goto +52 -> 306
    //   257: astore 4
    //   259: aload 4
    //   261: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   264: aload_0
    //   265: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   268: aload_3
    //   269: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   272: aconst_null
    //   273: astore_3
    //   274: aload_3
    //   275: ifnull +43 -> 318
    //   278: aload_0
    //   279: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   282: aload_3
    //   283: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   286: goto +32 -> 318
    //   289: astore 12
    //   291: aload_3
    //   292: ifnull +11 -> 303
    //   295: aload_0
    //   296: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   299: aload_3
    //   300: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   303: aload 12
    //   305: athrow
    //   306: aload_3
    //   307: ifnull +11 -> 318
    //   310: aload_0
    //   311: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   314: aload_3
    //   315: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   318: aload_2
    //   319: areturn
    // Line number table:
    //   Java source line #291	-> byte code offset #0
    //   Java source line #292	-> byte code offset #8
    //   Java source line #296	-> byte code offset #19
    //   Java source line #297	-> byte code offset #24
    //   Java source line #298	-> byte code offset #32
    //   Java source line #300	-> byte code offset #41
    //   Java source line #302	-> byte code offset #47
    //   Java source line #304	-> byte code offset #71
    //   Java source line #305	-> byte code offset #91
    //   Java source line #302	-> byte code offset #106
    //   Java source line #308	-> byte code offset #116
    //   Java source line #310	-> byte code offset #121
    //   Java source line #312	-> byte code offset #145
    //   Java source line #316	-> byte code offset #155
    //   Java source line #318	-> byte code offset #167
    //   Java source line #319	-> byte code offset #190
    //   Java source line #320	-> byte code offset #199
    //   Java source line #321	-> byte code offset #202
    //   Java source line #323	-> byte code offset #204
    //   Java source line #324	-> byte code offset #239
    //   Java source line #310	-> byte code offset #244
    //   Java source line #327	-> byte code offset #254
    //   Java source line #328	-> byte code offset #257
    //   Java source line #330	-> byte code offset #259
    //   Java source line #331	-> byte code offset #264
    //   Java source line #332	-> byte code offset #272
    //   Java source line #336	-> byte code offset #274
    //   Java source line #338	-> byte code offset #278
    //   Java source line #335	-> byte code offset #289
    //   Java source line #336	-> byte code offset #291
    //   Java source line #338	-> byte code offset #295
    //   Java source line #340	-> byte code offset #303
    //   Java source line #336	-> byte code offset #306
    //   Java source line #338	-> byte code offset #310
    //   Java source line #342	-> byte code offset #318
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	320	0	this	RedisServerRepository
    //   0	320	1	serverStatuses	Collection<MinecraftServer>
    //   7	312	2	servers	Collection<mineplex.serverdata.data.ServerGroup>
    //   18	297	3	jedis	redis.clients.jedis.Jedis
    //   22	57	4	key	String
    //   257	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   30	18	5	names	java.util.Set<String>
    //   39	83	6	serverDatas	java.util.Set<redis.clients.jedis.Response<java.util.Map<String, String>>>
    //   45	72	7	pipeline	redis.clients.jedis.Pipeline
    //   69	15	8	serverName	String
    //   143	3	8	response	redis.clients.jedis.Response<java.util.Map<String, String>>
    //   54	191	9	localIterator	java.util.Iterator
    //   89	7	10	dataKey	String
    //   153	65	10	data	java.util.Map<String, String>
    //   165	27	11	serverGroup	mineplex.serverdata.data.ServerGroup
    //   202	38	11	exception	Exception
    //   289	15	12	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   155	199	202	java/lang/Exception
    //   19	254	257	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	274	289	finally
  }
  
  /* Error */
  protected java.util.Set<String> getActiveNames(String key)
  {
    // Byte code:
    //   0: new 62	java/util/HashSet
    //   3: dup
    //   4: invokespecial 64	java/util/HashSet:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 69	redis/clients/jedis/Jedis
    //   18: astore_3
    //   19: new 294	java/lang/StringBuilder
    //   22: dup
    //   23: ldc_w 366
    //   26: invokespecial 302	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   29: invokestatic 210	mineplex/serverdata/Utility:currentTimeSeconds	()J
    //   32: invokevirtual 368	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   35: invokevirtual 311	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: astore 4
    //   40: ldc_w 371
    //   43: astore 5
    //   45: aload_3
    //   46: aload_1
    //   47: aload 4
    //   49: aload 5
    //   51: invokevirtual 373	redis/clients/jedis/Jedis:zrangeByScore	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Set;
    //   54: astore_2
    //   55: goto +52 -> 107
    //   58: astore 4
    //   60: aload 4
    //   62: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   65: aload_0
    //   66: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   69: aload_3
    //   70: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   73: aconst_null
    //   74: astore_3
    //   75: aload_3
    //   76: ifnull +43 -> 119
    //   79: aload_0
    //   80: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   83: aload_3
    //   84: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   87: goto +32 -> 119
    //   90: astore 6
    //   92: aload_3
    //   93: ifnull +11 -> 104
    //   96: aload_0
    //   97: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   100: aload_3
    //   101: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   104: aload 6
    //   106: athrow
    //   107: aload_3
    //   108: ifnull +11 -> 119
    //   111: aload_0
    //   112: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   115: aload_3
    //   116: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   119: aload_2
    //   120: areturn
    // Line number table:
    //   Java source line #352	-> byte code offset #0
    //   Java source line #353	-> byte code offset #8
    //   Java source line #357	-> byte code offset #19
    //   Java source line #358	-> byte code offset #40
    //   Java source line #359	-> byte code offset #45
    //   Java source line #360	-> byte code offset #55
    //   Java source line #361	-> byte code offset #58
    //   Java source line #363	-> byte code offset #60
    //   Java source line #364	-> byte code offset #65
    //   Java source line #365	-> byte code offset #73
    //   Java source line #369	-> byte code offset #75
    //   Java source line #371	-> byte code offset #79
    //   Java source line #368	-> byte code offset #90
    //   Java source line #369	-> byte code offset #92
    //   Java source line #371	-> byte code offset #96
    //   Java source line #373	-> byte code offset #104
    //   Java source line #369	-> byte code offset #107
    //   Java source line #371	-> byte code offset #111
    //   Java source line #375	-> byte code offset #119
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	this	RedisServerRepository
    //   0	121	1	key	String
    //   7	113	2	names	java.util.Set<String>
    //   18	98	3	jedis	redis.clients.jedis.Jedis
    //   38	10	4	min	String
    //   58	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   43	7	5	max	String
    //   90	15	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   19	55	58	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	75	90	finally
  }
  
  /* Error */
  protected java.util.Set<String> getDeadNames(String key)
  {
    // Byte code:
    //   0: new 62	java/util/HashSet
    //   3: dup
    //   4: invokespecial 64	java/util/HashSet:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 69	redis/clients/jedis/Jedis
    //   18: astore_3
    //   19: ldc_w 380
    //   22: astore 4
    //   24: new 294	java/lang/StringBuilder
    //   27: dup
    //   28: invokestatic 210	mineplex/serverdata/Utility:currentTimeSeconds	()J
    //   31: invokestatic 382	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   34: invokespecial 302	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   37: invokevirtual 311	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: astore 5
    //   42: aload_3
    //   43: aload_1
    //   44: aload 4
    //   46: aload 5
    //   48: invokevirtual 373	redis/clients/jedis/Jedis:zrangeByScore	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Set;
    //   51: astore_2
    //   52: goto +52 -> 104
    //   55: astore 4
    //   57: aload 4
    //   59: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   62: aload_0
    //   63: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   66: aload_3
    //   67: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   70: aconst_null
    //   71: astore_3
    //   72: aload_3
    //   73: ifnull +43 -> 116
    //   76: aload_0
    //   77: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   80: aload_3
    //   81: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   84: goto +32 -> 116
    //   87: astore 6
    //   89: aload_3
    //   90: ifnull +11 -> 101
    //   93: aload_0
    //   94: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   97: aload_3
    //   98: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   101: aload 6
    //   103: athrow
    //   104: aload_3
    //   105: ifnull +11 -> 116
    //   108: aload_0
    //   109: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   112: aload_3
    //   113: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   116: aload_2
    //   117: areturn
    // Line number table:
    //   Java source line #384	-> byte code offset #0
    //   Java source line #385	-> byte code offset #8
    //   Java source line #389	-> byte code offset #19
    //   Java source line #390	-> byte code offset #24
    //   Java source line #391	-> byte code offset #42
    //   Java source line #392	-> byte code offset #52
    //   Java source line #393	-> byte code offset #55
    //   Java source line #395	-> byte code offset #57
    //   Java source line #396	-> byte code offset #62
    //   Java source line #397	-> byte code offset #70
    //   Java source line #401	-> byte code offset #72
    //   Java source line #403	-> byte code offset #76
    //   Java source line #400	-> byte code offset #87
    //   Java source line #401	-> byte code offset #89
    //   Java source line #403	-> byte code offset #93
    //   Java source line #405	-> byte code offset #101
    //   Java source line #401	-> byte code offset #104
    //   Java source line #403	-> byte code offset #108
    //   Java source line #407	-> byte code offset #116
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	this	RedisServerRepository
    //   0	118	1	key	String
    //   7	110	2	names	java.util.Set<String>
    //   18	95	3	jedis	redis.clients.jedis.Jedis
    //   22	23	4	min	String
    //   55	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   40	7	5	max	String
    //   87	15	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   19	52	55	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	72	87	finally
  }
  
  protected String concatenate(String... elements)
  {
    return Utility.concatenate('.', elements);
  }
  
  /* Error */
  public Collection<MinecraftServer> getDeadServers()
  {
    // Byte code:
    //   0: new 62	java/util/HashSet
    //   3: dup
    //   4: invokespecial 64	java/util/HashSet:<init>	()V
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 69	redis/clients/jedis/Jedis
    //   18: astore_2
    //   19: aload_2
    //   20: invokevirtual 85	redis/clients/jedis/Jedis:pipelined	()Lredis/clients/jedis/Pipeline;
    //   23: astore_3
    //   24: aload_0
    //   25: iconst_3
    //   26: anewarray 71	java/lang/String
    //   29: dup
    //   30: iconst_0
    //   31: ldc 73
    //   33: aastore
    //   34: dup
    //   35: iconst_1
    //   36: ldc 75
    //   38: aastore
    //   39: dup
    //   40: iconst_2
    //   41: aload_0
    //   42: getfield 34	mineplex/serverdata/redis/RedisServerRepository:_region	Lmineplex/serverdata/Region;
    //   45: invokevirtual 77	mineplex/serverdata/Region:toString	()Ljava/lang/String;
    //   48: aastore
    //   49: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   52: astore 4
    //   54: ldc_w 380
    //   57: astore 5
    //   59: new 294	java/lang/StringBuilder
    //   62: dup
    //   63: invokestatic 210	mineplex/serverdata/Utility:currentTimeSeconds	()J
    //   66: invokestatic 382	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   69: invokespecial 302	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   72: invokevirtual 311	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   75: astore 6
    //   77: new 89	java/util/ArrayList
    //   80: dup
    //   81: invokespecial 91	java/util/ArrayList:<init>	()V
    //   84: astore 7
    //   86: aload_2
    //   87: aload 4
    //   89: aload 5
    //   91: aload 6
    //   93: invokevirtual 391	redis/clients/jedis/Jedis:zrangeByScoreWithScores	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Set;
    //   96: invokeinterface 96 1 0
    //   101: astore 9
    //   103: goto +52 -> 155
    //   106: aload 9
    //   108: invokeinterface 102 1 0
    //   113: checkcast 394	redis/clients/jedis/Tuple
    //   116: astore 8
    //   118: aload_0
    //   119: iconst_2
    //   120: anewarray 71	java/lang/String
    //   123: dup
    //   124: iconst_0
    //   125: aload 4
    //   127: aastore
    //   128: dup
    //   129: iconst_1
    //   130: aload 8
    //   132: invokevirtual 396	redis/clients/jedis/Tuple:getElement	()Ljava/lang/String;
    //   135: aastore
    //   136: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   139: astore 10
    //   141: aload 7
    //   143: aload_3
    //   144: aload 10
    //   146: invokevirtual 115	redis/clients/jedis/Pipeline:get	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   149: invokeinterface 121 2 0
    //   154: pop
    //   155: aload 9
    //   157: invokeinterface 127 1 0
    //   162: ifne -56 -> 106
    //   165: aload_3
    //   166: invokevirtual 130	redis/clients/jedis/Pipeline:sync	()V
    //   169: aload 7
    //   171: invokeinterface 133 1 0
    //   176: astore 9
    //   178: goto +51 -> 229
    //   181: aload 9
    //   183: invokeinterface 102 1 0
    //   188: checkcast 134	redis/clients/jedis/Response
    //   191: astore 8
    //   193: aload 8
    //   195: invokevirtual 136	redis/clients/jedis/Response:get	()Ljava/lang/Object;
    //   198: checkcast 71	java/lang/String
    //   201: astore 10
    //   203: aload 10
    //   205: ldc -118
    //   207: invokestatic 140	mineplex/serverdata/Utility:deserialize	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   210: checkcast 138	mineplex/serverdata/data/MinecraftServer
    //   213: astore 11
    //   215: aload 11
    //   217: ifnull +12 -> 229
    //   220: aload_1
    //   221: aload 11
    //   223: invokeinterface 341 2 0
    //   228: pop
    //   229: aload 9
    //   231: invokeinterface 127 1 0
    //   236: ifne -55 -> 181
    //   239: goto +50 -> 289
    //   242: astore_3
    //   243: aload_3
    //   244: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   247: aload_0
    //   248: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   251: aload_2
    //   252: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   255: aconst_null
    //   256: astore_2
    //   257: aload_2
    //   258: ifnull +43 -> 301
    //   261: aload_0
    //   262: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   265: aload_2
    //   266: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   269: goto +32 -> 301
    //   272: astore 12
    //   274: aload_2
    //   275: ifnull +11 -> 286
    //   278: aload_0
    //   279: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   282: aload_2
    //   283: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   286: aload 12
    //   288: athrow
    //   289: aload_2
    //   290: ifnull +11 -> 301
    //   293: aload_0
    //   294: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   297: aload_2
    //   298: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   301: aload_1
    //   302: areturn
    // Line number table:
    //   Java source line #423	-> byte code offset #0
    //   Java source line #424	-> byte code offset #8
    //   Java source line #428	-> byte code offset #19
    //   Java source line #429	-> byte code offset #24
    //   Java source line #430	-> byte code offset #54
    //   Java source line #431	-> byte code offset #59
    //   Java source line #433	-> byte code offset #77
    //   Java source line #434	-> byte code offset #86
    //   Java source line #436	-> byte code offset #118
    //   Java source line #437	-> byte code offset #141
    //   Java source line #434	-> byte code offset #155
    //   Java source line #440	-> byte code offset #165
    //   Java source line #442	-> byte code offset #169
    //   Java source line #444	-> byte code offset #193
    //   Java source line #445	-> byte code offset #203
    //   Java source line #447	-> byte code offset #215
    //   Java source line #448	-> byte code offset #220
    //   Java source line #442	-> byte code offset #229
    //   Java source line #450	-> byte code offset #239
    //   Java source line #451	-> byte code offset #242
    //   Java source line #453	-> byte code offset #243
    //   Java source line #454	-> byte code offset #247
    //   Java source line #455	-> byte code offset #255
    //   Java source line #459	-> byte code offset #257
    //   Java source line #461	-> byte code offset #261
    //   Java source line #458	-> byte code offset #272
    //   Java source line #459	-> byte code offset #274
    //   Java source line #461	-> byte code offset #278
    //   Java source line #463	-> byte code offset #286
    //   Java source line #459	-> byte code offset #289
    //   Java source line #461	-> byte code offset #293
    //   Java source line #465	-> byte code offset #301
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	303	0	this	RedisServerRepository
    //   7	295	1	servers	java.util.Set<MinecraftServer>
    //   18	280	2	jedis	redis.clients.jedis.Jedis
    //   23	143	3	pipeline	redis.clients.jedis.Pipeline
    //   242	2	3	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   52	74	4	setKey	String
    //   57	33	5	min	String
    //   75	17	6	max	String
    //   84	86	7	responses	java.util.List<redis.clients.jedis.Response<String>>
    //   116	15	8	serverName	redis.clients.jedis.Tuple
    //   191	3	8	response	redis.clients.jedis.Response<String>
    //   101	129	9	localIterator	java.util.Iterator
    //   139	6	10	dataKey	String
    //   201	3	10	serializedData	String
    //   213	9	11	server	MinecraftServer
    //   272	15	12	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   19	239	242	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	257	272	finally
  }
  
  /* Error */
  public void updateServerGroup(mineplex.serverdata.data.ServerGroup serverGroup)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   4: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   7: checkcast 69	redis/clients/jedis/Jedis
    //   10: astore_2
    //   11: aload_1
    //   12: invokevirtual 403	mineplex/serverdata/data/ServerGroup:getDataMap	()Ljava/util/HashMap;
    //   15: astore_3
    //   16: getstatic 288	java/lang/System:out	Ljava/io/PrintStream;
    //   19: aload_3
    //   20: invokevirtual 407	java/io/PrintStream:println	(Ljava/lang/Object;)V
    //   23: aload_1
    //   24: invokevirtual 410	mineplex/serverdata/data/ServerGroup:getName	()Ljava/lang/String;
    //   27: astore 4
    //   29: ldc_w 339
    //   32: astore 5
    //   34: aload_0
    //   35: iconst_2
    //   36: anewarray 71	java/lang/String
    //   39: dup
    //   40: iconst_0
    //   41: aload 5
    //   43: aastore
    //   44: dup
    //   45: iconst_1
    //   46: aload 4
    //   48: aastore
    //   49: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   52: astore 6
    //   54: aload_2
    //   55: invokevirtual 214	redis/clients/jedis/Jedis:multi	()Lredis/clients/jedis/Transaction;
    //   58: astore 7
    //   60: aload 7
    //   62: aload 6
    //   64: aload_3
    //   65: invokevirtual 411	redis/clients/jedis/Transaction:hmset	(Ljava/lang/String;Ljava/util/Map;)Lredis/clients/jedis/Response;
    //   68: pop
    //   69: aload 7
    //   71: aload 5
    //   73: iconst_1
    //   74: anewarray 71	java/lang/String
    //   77: dup
    //   78: iconst_0
    //   79: aload 4
    //   81: aastore
    //   82: invokevirtual 415	redis/clients/jedis/Transaction:sadd	(Ljava/lang/String;[Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   85: pop
    //   86: aload 7
    //   88: invokevirtual 228	redis/clients/jedis/Transaction:exec	()Ljava/util/List;
    //   91: pop
    //   92: goto +50 -> 142
    //   95: astore_3
    //   96: aload_3
    //   97: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   100: aload_0
    //   101: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   104: aload_2
    //   105: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   108: aconst_null
    //   109: astore_2
    //   110: aload_2
    //   111: ifnull +43 -> 154
    //   114: aload_0
    //   115: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   118: aload_2
    //   119: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   122: goto +32 -> 154
    //   125: astore 8
    //   127: aload_2
    //   128: ifnull +11 -> 139
    //   131: aload_0
    //   132: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   135: aload_2
    //   136: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   139: aload 8
    //   141: athrow
    //   142: aload_2
    //   143: ifnull +11 -> 154
    //   146: aload_0
    //   147: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   150: aload_2
    //   151: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   154: return
    // Line number table:
    //   Java source line #471	-> byte code offset #0
    //   Java source line #475	-> byte code offset #11
    //   Java source line #476	-> byte code offset #16
    //   Java source line #477	-> byte code offset #23
    //   Java source line #478	-> byte code offset #29
    //   Java source line #479	-> byte code offset #34
    //   Java source line #481	-> byte code offset #54
    //   Java source line #482	-> byte code offset #60
    //   Java source line #483	-> byte code offset #69
    //   Java source line #484	-> byte code offset #86
    //   Java source line #485	-> byte code offset #92
    //   Java source line #486	-> byte code offset #95
    //   Java source line #488	-> byte code offset #96
    //   Java source line #489	-> byte code offset #100
    //   Java source line #490	-> byte code offset #108
    //   Java source line #494	-> byte code offset #110
    //   Java source line #496	-> byte code offset #114
    //   Java source line #493	-> byte code offset #125
    //   Java source line #494	-> byte code offset #127
    //   Java source line #496	-> byte code offset #131
    //   Java source line #498	-> byte code offset #139
    //   Java source line #494	-> byte code offset #142
    //   Java source line #496	-> byte code offset #146
    //   Java source line #499	-> byte code offset #154
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	this	RedisServerRepository
    //   0	155	1	serverGroup	mineplex.serverdata.data.ServerGroup
    //   10	141	2	jedis	redis.clients.jedis.Jedis
    //   15	50	3	serializedData	java.util.HashMap<String, String>
    //   95	2	3	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   27	53	4	serverGroupName	String
    //   32	40	5	key	String
    //   52	11	6	dataKey	String
    //   58	29	7	transaction	redis.clients.jedis.Transaction
    //   125	15	8	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   11	92	95	redis/clients/jedis/exceptions/JedisConnectionException
    //   11	110	125	finally
  }
  
  /* Error */
  public void removeServerGroup(mineplex.serverdata.data.ServerGroup serverGroup)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   4: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   7: checkcast 69	redis/clients/jedis/Jedis
    //   10: astore_2
    //   11: aload_1
    //   12: invokevirtual 410	mineplex/serverdata/data/ServerGroup:getName	()Ljava/lang/String;
    //   15: astore_3
    //   16: ldc_w 339
    //   19: astore 4
    //   21: aload_0
    //   22: iconst_2
    //   23: anewarray 71	java/lang/String
    //   26: dup
    //   27: iconst_0
    //   28: aload 4
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_3
    //   34: aastore
    //   35: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   38: astore 5
    //   40: aload_2
    //   41: invokevirtual 214	redis/clients/jedis/Jedis:multi	()Lredis/clients/jedis/Transaction;
    //   44: astore 6
    //   46: aload 6
    //   48: aload 5
    //   50: invokevirtual 241	redis/clients/jedis/Transaction:del	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   53: pop
    //   54: aload 6
    //   56: aload 4
    //   58: iconst_1
    //   59: anewarray 71	java/lang/String
    //   62: dup
    //   63: iconst_0
    //   64: aload_3
    //   65: aastore
    //   66: invokevirtual 421	redis/clients/jedis/Transaction:srem	(Ljava/lang/String;[Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   69: pop
    //   70: aload 6
    //   72: invokevirtual 228	redis/clients/jedis/Transaction:exec	()Ljava/util/List;
    //   75: pop
    //   76: goto +50 -> 126
    //   79: astore_3
    //   80: aload_3
    //   81: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   84: aload_0
    //   85: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   88: aload_2
    //   89: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   92: aconst_null
    //   93: astore_2
    //   94: aload_2
    //   95: ifnull +43 -> 138
    //   98: aload_0
    //   99: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   102: aload_2
    //   103: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   106: goto +32 -> 138
    //   109: astore 7
    //   111: aload_2
    //   112: ifnull +11 -> 123
    //   115: aload_0
    //   116: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   119: aload_2
    //   120: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   123: aload 7
    //   125: athrow
    //   126: aload_2
    //   127: ifnull +11 -> 138
    //   130: aload_0
    //   131: getfield 30	mineplex/serverdata/redis/RedisServerRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   134: aload_2
    //   135: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   138: return
    // Line number table:
    //   Java source line #504	-> byte code offset #0
    //   Java source line #508	-> byte code offset #11
    //   Java source line #509	-> byte code offset #16
    //   Java source line #510	-> byte code offset #21
    //   Java source line #512	-> byte code offset #40
    //   Java source line #513	-> byte code offset #46
    //   Java source line #514	-> byte code offset #54
    //   Java source line #515	-> byte code offset #70
    //   Java source line #516	-> byte code offset #76
    //   Java source line #517	-> byte code offset #79
    //   Java source line #519	-> byte code offset #80
    //   Java source line #520	-> byte code offset #84
    //   Java source line #521	-> byte code offset #92
    //   Java source line #525	-> byte code offset #94
    //   Java source line #527	-> byte code offset #98
    //   Java source line #524	-> byte code offset #109
    //   Java source line #525	-> byte code offset #111
    //   Java source line #527	-> byte code offset #115
    //   Java source line #529	-> byte code offset #123
    //   Java source line #525	-> byte code offset #126
    //   Java source line #527	-> byte code offset #130
    //   Java source line #530	-> byte code offset #138
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	139	0	this	RedisServerRepository
    //   0	139	1	serverGroup	mineplex.serverdata.data.ServerGroup
    //   10	125	2	jedis	redis.clients.jedis.Jedis
    //   15	50	3	serverName	String
    //   79	2	3	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   19	38	4	setKey	String
    //   38	11	5	dataKey	String
    //   44	27	6	transaction	redis.clients.jedis.Transaction
    //   109	15	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   11	76	79	redis/clients/jedis/exceptions/JedisConnectionException
    //   11	94	109	finally
  }
  
  /* Error */
  public mineplex.serverdata.data.ServerGroup getServerGroup(String serverGroup)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   6: invokevirtual 65	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   9: checkcast 69	redis/clients/jedis/Jedis
    //   12: astore_3
    //   13: aload_0
    //   14: iconst_2
    //   15: anewarray 71	java/lang/String
    //   18: dup
    //   19: iconst_0
    //   20: ldc_w 339
    //   23: aastore
    //   24: dup
    //   25: iconst_1
    //   26: aload_1
    //   27: aastore
    //   28: invokevirtual 81	mineplex/serverdata/redis/RedisServerRepository:concatenate	([Ljava/lang/String;)Ljava/lang/String;
    //   31: astore 4
    //   33: aload_3
    //   34: aload 4
    //   36: invokevirtual 426	redis/clients/jedis/Jedis:hgetAll	(Ljava/lang/String;)Ljava/util/Map;
    //   39: astore 5
    //   41: new 342	mineplex/serverdata/data/ServerGroup
    //   44: dup
    //   45: aload 5
    //   47: aconst_null
    //   48: invokespecial 344	mineplex/serverdata/data/ServerGroup:<init>	(Ljava/util/Map;Ljava/util/Collection;)V
    //   51: astore_2
    //   52: goto +52 -> 104
    //   55: astore 4
    //   57: aload 4
    //   59: invokevirtual 147	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   62: aload_0
    //   63: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   66: aload_3
    //   67: invokevirtual 152	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   70: aconst_null
    //   71: astore_3
    //   72: aload_3
    //   73: ifnull +43 -> 116
    //   76: aload_0
    //   77: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   80: aload_3
    //   81: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   84: goto +32 -> 116
    //   87: astore 6
    //   89: aload_3
    //   90: ifnull +11 -> 101
    //   93: aload_0
    //   94: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   97: aload_3
    //   98: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   101: aload 6
    //   103: athrow
    //   104: aload_3
    //   105: ifnull +11 -> 116
    //   108: aload_0
    //   109: getfield 32	mineplex/serverdata/redis/RedisServerRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   112: aload_3
    //   113: invokevirtual 156	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   116: aload_2
    //   117: areturn
    // Line number table:
    //   Java source line #535	-> byte code offset #0
    //   Java source line #536	-> byte code offset #2
    //   Java source line #539	-> byte code offset #13
    //   Java source line #540	-> byte code offset #33
    //   Java source line #542	-> byte code offset #41
    //   Java source line #543	-> byte code offset #52
    //   Java source line #544	-> byte code offset #55
    //   Java source line #546	-> byte code offset #57
    //   Java source line #547	-> byte code offset #62
    //   Java source line #548	-> byte code offset #70
    //   Java source line #552	-> byte code offset #72
    //   Java source line #554	-> byte code offset #76
    //   Java source line #551	-> byte code offset #87
    //   Java source line #552	-> byte code offset #89
    //   Java source line #554	-> byte code offset #93
    //   Java source line #556	-> byte code offset #101
    //   Java source line #552	-> byte code offset #104
    //   Java source line #554	-> byte code offset #108
    //   Java source line #558	-> byte code offset #116
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	this	RedisServerRepository
    //   0	118	1	serverGroup	String
    //   1	116	2	server	mineplex.serverdata.data.ServerGroup
    //   12	101	3	jedis	redis.clients.jedis.Jedis
    //   31	4	4	key	String
    //   55	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   39	7	5	data	java.util.Map<String, String>
    //   87	15	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   13	52	55	redis/clients/jedis/exceptions/JedisConnectionException
    //   13	72	87	finally
  }
}

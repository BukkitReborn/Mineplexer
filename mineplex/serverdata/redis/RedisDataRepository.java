package mineplex.serverdata.redis;

import java.util.Collection;
import mineplex.serverdata.Region;
import mineplex.serverdata.Utility;
import mineplex.serverdata.data.Data;
import mineplex.serverdata.data.DataRepository;
import mineplex.serverdata.servers.ConnectionData;
import mineplex.serverdata.servers.ServerManager;
import redis.clients.jedis.JedisPool;

public class RedisDataRepository<T extends Data>
  implements DataRepository<T>
{
  public final char KEY_DELIMITER = '.';
  private JedisPool _writePool;
  private JedisPool _readPool;
  private Region _region;
  private Class<T> _elementType;
  private String _elementLabel;
  
  public RedisDataRepository(ConnectionData writeConn, ConnectionData readConn, Region region, Class<T> elementType, String elementLabel)
  {
    this._writePool = Utility.generatePool(writeConn);
    this._readPool = (writeConn == readConn ? this._writePool : Utility.generatePool(readConn));
    this._region = region;
    this._elementType = elementType;
    this._elementLabel = elementLabel;
  }
  
  public RedisDataRepository(ConnectionData conn, Region region, Class<T> elementType, String elementLabel)
  {
    this(conn, conn, region, elementType, elementLabel);
  }
  
  public RedisDataRepository(Region region, Class<T> elementType, String elementLabel)
  {
    this(ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), region, elementType, elementLabel);
  }
  
  public String getElementSetKey()
  {
    return concatenate(new String[] { "data", this._elementLabel, this._region.toString() });
  }
  
  public String generateKey(T element)
  {
    return generateKey(element.getDataId());
  }
  
  public String generateKey(String dataId)
  {
    return concatenate(new String[] { getElementSetKey(), dataId });
  }
  
  public Collection<T> getElements()
  {
    return getElements(getActiveElements());
  }
  
  /* Error */
  public Collection<T> getElements(Collection<String> dataIds)
  {
    // Byte code:
    //   0: new 125	java/util/HashSet
    //   3: dup
    //   4: invokespecial 127	java/util/HashSet:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 128	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 132	redis/clients/jedis/Jedis
    //   18: astore_3
    //   19: aload_3
    //   20: invokevirtual 134	redis/clients/jedis/Jedis:pipelined	()Lredis/clients/jedis/Pipeline;
    //   23: astore 4
    //   25: new 138	java/util/ArrayList
    //   28: dup
    //   29: invokespecial 140	java/util/ArrayList:<init>	()V
    //   32: astore 5
    //   34: aload_1
    //   35: invokeinterface 141 1 0
    //   40: astore 7
    //   42: goto +34 -> 76
    //   45: aload 7
    //   47: invokeinterface 147 1 0
    //   52: checkcast 66	java/lang/String
    //   55: astore 6
    //   57: aload 5
    //   59: aload 4
    //   61: aload_0
    //   62: aload 6
    //   64: invokevirtual 105	mineplex/serverdata/redis/RedisDataRepository:generateKey	(Ljava/lang/String;)Ljava/lang/String;
    //   67: invokevirtual 152	redis/clients/jedis/Pipeline:get	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   70: invokeinterface 158 2 0
    //   75: pop
    //   76: aload 7
    //   78: invokeinterface 164 1 0
    //   83: ifne -38 -> 45
    //   86: aload 4
    //   88: invokevirtual 168	redis/clients/jedis/Pipeline:sync	()V
    //   91: aload 5
    //   93: invokeinterface 171 1 0
    //   98: astore 7
    //   100: goto +47 -> 147
    //   103: aload 7
    //   105: invokeinterface 147 1 0
    //   110: checkcast 172	redis/clients/jedis/Response
    //   113: astore 6
    //   115: aload 6
    //   117: invokevirtual 174	redis/clients/jedis/Response:get	()Ljava/lang/Object;
    //   120: checkcast 66	java/lang/String
    //   123: astore 8
    //   125: aload_0
    //   126: aload 8
    //   128: invokevirtual 176	mineplex/serverdata/redis/RedisDataRepository:deserialize	(Ljava/lang/String;)Lmineplex/serverdata/data/Data;
    //   131: astore 9
    //   133: aload 9
    //   135: ifnull +12 -> 147
    //   138: aload_2
    //   139: aload 9
    //   141: invokeinterface 180 2 0
    //   146: pop
    //   147: aload 7
    //   149: invokeinterface 164 1 0
    //   154: ifne -51 -> 103
    //   157: goto +52 -> 209
    //   160: astore 4
    //   162: aload 4
    //   164: invokevirtual 181	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   167: aload_0
    //   168: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   171: aload_3
    //   172: invokevirtual 186	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   175: aconst_null
    //   176: astore_3
    //   177: aload_3
    //   178: ifnull +43 -> 221
    //   181: aload_0
    //   182: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   185: aload_3
    //   186: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   189: goto +32 -> 221
    //   192: astore 10
    //   194: aload_3
    //   195: ifnull +11 -> 206
    //   198: aload_0
    //   199: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   202: aload_3
    //   203: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   206: aload 10
    //   208: athrow
    //   209: aload_3
    //   210: ifnull +11 -> 221
    //   213: aload_0
    //   214: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   217: aload_3
    //   218: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   221: aload_2
    //   222: areturn
    // Line number table:
    //   Java source line #95	-> byte code offset #0
    //   Java source line #96	-> byte code offset #8
    //   Java source line #100	-> byte code offset #19
    //   Java source line #102	-> byte code offset #25
    //   Java source line #103	-> byte code offset #34
    //   Java source line #105	-> byte code offset #57
    //   Java source line #103	-> byte code offset #76
    //   Java source line #109	-> byte code offset #86
    //   Java source line #111	-> byte code offset #91
    //   Java source line #113	-> byte code offset #115
    //   Java source line #114	-> byte code offset #125
    //   Java source line #116	-> byte code offset #133
    //   Java source line #118	-> byte code offset #138
    //   Java source line #111	-> byte code offset #147
    //   Java source line #121	-> byte code offset #157
    //   Java source line #122	-> byte code offset #160
    //   Java source line #124	-> byte code offset #162
    //   Java source line #125	-> byte code offset #167
    //   Java source line #126	-> byte code offset #175
    //   Java source line #130	-> byte code offset #177
    //   Java source line #132	-> byte code offset #181
    //   Java source line #129	-> byte code offset #192
    //   Java source line #130	-> byte code offset #194
    //   Java source line #132	-> byte code offset #198
    //   Java source line #134	-> byte code offset #206
    //   Java source line #130	-> byte code offset #209
    //   Java source line #132	-> byte code offset #213
    //   Java source line #136	-> byte code offset #221
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	223	0	this	RedisDataRepository<T>
    //   0	223	1	dataIds	Collection<String>
    //   7	215	2	elements	Collection<T>
    //   18	200	3	jedis	redis.clients.jedis.Jedis
    //   23	64	4	pipeline	redis.clients.jedis.Pipeline
    //   160	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   32	60	5	responses	java.util.List<redis.clients.jedis.Response<String>>
    //   55	8	6	dataId	String
    //   113	3	6	response	redis.clients.jedis.Response<String>
    //   40	108	7	localIterator	java.util.Iterator
    //   123	4	8	serializedData	String
    //   131	9	9	element	T
    //   192	15	10	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   19	157	160	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	177	192	finally
  }
  
  /* Error */
  public T getElement(String dataId)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   6: invokevirtual 128	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   9: checkcast 132	redis/clients/jedis/Jedis
    //   12: astore_3
    //   13: aload_0
    //   14: aload_1
    //   15: invokevirtual 105	mineplex/serverdata/redis/RedisDataRepository:generateKey	(Ljava/lang/String;)Ljava/lang/String;
    //   18: astore 4
    //   20: aload_3
    //   21: aload 4
    //   23: invokevirtual 215	redis/clients/jedis/Jedis:get	(Ljava/lang/String;)Ljava/lang/String;
    //   26: astore 5
    //   28: aload_0
    //   29: aload 5
    //   31: invokevirtual 176	mineplex/serverdata/redis/RedisDataRepository:deserialize	(Ljava/lang/String;)Lmineplex/serverdata/data/Data;
    //   34: astore_2
    //   35: goto +52 -> 87
    //   38: astore 4
    //   40: aload 4
    //   42: invokevirtual 181	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   45: aload_0
    //   46: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   49: aload_3
    //   50: invokevirtual 186	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   53: aconst_null
    //   54: astore_3
    //   55: aload_3
    //   56: ifnull +43 -> 99
    //   59: aload_0
    //   60: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   63: aload_3
    //   64: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   67: goto +32 -> 99
    //   70: astore 6
    //   72: aload_3
    //   73: ifnull +11 -> 84
    //   76: aload_0
    //   77: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   80: aload_3
    //   81: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   84: aload 6
    //   86: athrow
    //   87: aload_3
    //   88: ifnull +11 -> 99
    //   91: aload_0
    //   92: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   95: aload_3
    //   96: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   99: aload_2
    //   100: areturn
    // Line number table:
    //   Java source line #142	-> byte code offset #0
    //   Java source line #143	-> byte code offset #2
    //   Java source line #147	-> byte code offset #13
    //   Java source line #148	-> byte code offset #20
    //   Java source line #149	-> byte code offset #28
    //   Java source line #150	-> byte code offset #35
    //   Java source line #151	-> byte code offset #38
    //   Java source line #153	-> byte code offset #40
    //   Java source line #154	-> byte code offset #45
    //   Java source line #155	-> byte code offset #53
    //   Java source line #159	-> byte code offset #55
    //   Java source line #161	-> byte code offset #59
    //   Java source line #158	-> byte code offset #70
    //   Java source line #159	-> byte code offset #72
    //   Java source line #161	-> byte code offset #76
    //   Java source line #163	-> byte code offset #84
    //   Java source line #159	-> byte code offset #87
    //   Java source line #161	-> byte code offset #91
    //   Java source line #165	-> byte code offset #99
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	101	0	this	RedisDataRepository<T>
    //   0	101	1	dataId	String
    //   1	99	2	element	T
    //   12	84	3	jedis	redis.clients.jedis.Jedis
    //   18	4	4	key	String
    //   38	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   26	4	5	serializedData	String
    //   70	15	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   13	35	38	redis/clients/jedis/exceptions/JedisConnectionException
    //   13	55	70	finally
  }
  
  /* Error */
  public void addElement(T element, int timeout)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   4: invokevirtual 128	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   7: checkcast 132	redis/clients/jedis/Jedis
    //   10: astore_3
    //   11: aload_0
    //   12: aload_1
    //   13: invokevirtual 221	mineplex/serverdata/redis/RedisDataRepository:serialize	(Lmineplex/serverdata/data/Data;)Ljava/lang/String;
    //   16: astore 4
    //   18: aload_1
    //   19: invokeinterface 100 1 0
    //   24: astore 5
    //   26: aload_0
    //   27: invokevirtual 111	mineplex/serverdata/redis/RedisDataRepository:getElementSetKey	()Ljava/lang/String;
    //   30: astore 6
    //   32: aload_0
    //   33: aload_1
    //   34: invokevirtual 224	mineplex/serverdata/redis/RedisDataRepository:generateKey	(Lmineplex/serverdata/data/Data;)Ljava/lang/String;
    //   37: astore 7
    //   39: aload_0
    //   40: invokevirtual 226	mineplex/serverdata/redis/RedisDataRepository:currentTime	()Ljava/lang/Long;
    //   43: invokevirtual 230	java/lang/Long:longValue	()J
    //   46: iload_2
    //   47: i2l
    //   48: ladd
    //   49: lstore 8
    //   51: aload_3
    //   52: invokevirtual 236	redis/clients/jedis/Jedis:multi	()Lredis/clients/jedis/Transaction;
    //   55: astore 10
    //   57: aload 10
    //   59: aload 7
    //   61: aload 4
    //   63: invokevirtual 240	redis/clients/jedis/Transaction:set	(Ljava/lang/String;Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   66: pop
    //   67: aload 10
    //   69: aload 6
    //   71: lload 8
    //   73: l2d
    //   74: aload 5
    //   76: invokevirtual 246	java/lang/String:toString	()Ljava/lang/String;
    //   79: invokevirtual 247	redis/clients/jedis/Transaction:zadd	(Ljava/lang/String;DLjava/lang/String;)Lredis/clients/jedis/Response;
    //   82: pop
    //   83: aload 10
    //   85: invokevirtual 251	redis/clients/jedis/Transaction:exec	()Ljava/util/List;
    //   88: pop
    //   89: goto +52 -> 141
    //   92: astore 4
    //   94: aload 4
    //   96: invokevirtual 181	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   99: aload_0
    //   100: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   103: aload_3
    //   104: invokevirtual 186	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   107: aconst_null
    //   108: astore_3
    //   109: aload_3
    //   110: ifnull +43 -> 153
    //   113: aload_0
    //   114: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   117: aload_3
    //   118: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   121: goto +32 -> 153
    //   124: astore 11
    //   126: aload_3
    //   127: ifnull +11 -> 138
    //   130: aload_0
    //   131: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   134: aload_3
    //   135: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   138: aload 11
    //   140: athrow
    //   141: aload_3
    //   142: ifnull +11 -> 153
    //   145: aload_0
    //   146: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   149: aload_3
    //   150: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   153: return
    // Line number table:
    //   Java source line #171	-> byte code offset #0
    //   Java source line #175	-> byte code offset #11
    //   Java source line #176	-> byte code offset #18
    //   Java source line #177	-> byte code offset #26
    //   Java source line #178	-> byte code offset #32
    //   Java source line #179	-> byte code offset #39
    //   Java source line #181	-> byte code offset #51
    //   Java source line #182	-> byte code offset #57
    //   Java source line #183	-> byte code offset #67
    //   Java source line #184	-> byte code offset #83
    //   Java source line #185	-> byte code offset #89
    //   Java source line #186	-> byte code offset #92
    //   Java source line #188	-> byte code offset #94
    //   Java source line #189	-> byte code offset #99
    //   Java source line #190	-> byte code offset #107
    //   Java source line #194	-> byte code offset #109
    //   Java source line #196	-> byte code offset #113
    //   Java source line #193	-> byte code offset #124
    //   Java source line #194	-> byte code offset #126
    //   Java source line #196	-> byte code offset #130
    //   Java source line #198	-> byte code offset #138
    //   Java source line #194	-> byte code offset #141
    //   Java source line #196	-> byte code offset #145
    //   Java source line #199	-> byte code offset #153
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	RedisDataRepository<T>
    //   0	154	1	element	T
    //   0	154	2	timeout	int
    //   10	140	3	jedis	redis.clients.jedis.Jedis
    //   16	46	4	serializedData	String
    //   92	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   24	51	5	dataId	String
    //   30	40	6	setKey	String
    //   37	23	7	dataKey	String
    //   49	23	8	expiry	long
    //   55	29	10	transaction	redis.clients.jedis.Transaction
    //   124	15	11	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   11	89	92	redis/clients/jedis/exceptions/JedisConnectionException
    //   11	109	124	finally
  }
  
  public void addElement(T element)
  {
    addElement(element, 290304000);
  }
  
  public void removeElement(T element)
  {
    removeElement(element.getDataId());
  }
  
  /* Error */
  public void removeElement(String dataId)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   4: invokevirtual 128	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   7: checkcast 132	redis/clients/jedis/Jedis
    //   10: astore_2
    //   11: aload_0
    //   12: invokevirtual 111	mineplex/serverdata/redis/RedisDataRepository:getElementSetKey	()Ljava/lang/String;
    //   15: astore_3
    //   16: aload_0
    //   17: aload_1
    //   18: invokevirtual 105	mineplex/serverdata/redis/RedisDataRepository:generateKey	(Ljava/lang/String;)Ljava/lang/String;
    //   21: astore 4
    //   23: aload_2
    //   24: invokevirtual 236	redis/clients/jedis/Jedis:multi	()Lredis/clients/jedis/Transaction;
    //   27: astore 5
    //   29: aload 5
    //   31: aload 4
    //   33: invokevirtual 272	redis/clients/jedis/Transaction:del	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   36: pop
    //   37: aload 5
    //   39: aload_3
    //   40: iconst_1
    //   41: anewarray 66	java/lang/String
    //   44: dup
    //   45: iconst_0
    //   46: aload_1
    //   47: aastore
    //   48: invokevirtual 275	redis/clients/jedis/Transaction:zrem	(Ljava/lang/String;[Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   51: pop
    //   52: aload 5
    //   54: invokevirtual 251	redis/clients/jedis/Transaction:exec	()Ljava/util/List;
    //   57: pop
    //   58: goto +50 -> 108
    //   61: astore_3
    //   62: aload_3
    //   63: invokevirtual 181	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   66: aload_0
    //   67: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   70: aload_2
    //   71: invokevirtual 186	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   74: aconst_null
    //   75: astore_2
    //   76: aload_2
    //   77: ifnull +43 -> 120
    //   80: aload_0
    //   81: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   84: aload_2
    //   85: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   88: goto +32 -> 120
    //   91: astore 6
    //   93: aload_2
    //   94: ifnull +11 -> 105
    //   97: aload_0
    //   98: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   101: aload_2
    //   102: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   105: aload 6
    //   107: athrow
    //   108: aload_2
    //   109: ifnull +11 -> 120
    //   112: aload_0
    //   113: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   116: aload_2
    //   117: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   120: return
    // Line number table:
    //   Java source line #216	-> byte code offset #0
    //   Java source line #220	-> byte code offset #11
    //   Java source line #221	-> byte code offset #16
    //   Java source line #223	-> byte code offset #23
    //   Java source line #224	-> byte code offset #29
    //   Java source line #225	-> byte code offset #37
    //   Java source line #226	-> byte code offset #52
    //   Java source line #227	-> byte code offset #58
    //   Java source line #228	-> byte code offset #61
    //   Java source line #230	-> byte code offset #62
    //   Java source line #231	-> byte code offset #66
    //   Java source line #232	-> byte code offset #74
    //   Java source line #236	-> byte code offset #76
    //   Java source line #238	-> byte code offset #80
    //   Java source line #235	-> byte code offset #91
    //   Java source line #236	-> byte code offset #93
    //   Java source line #238	-> byte code offset #97
    //   Java source line #240	-> byte code offset #105
    //   Java source line #236	-> byte code offset #108
    //   Java source line #238	-> byte code offset #112
    //   Java source line #241	-> byte code offset #120
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	this	RedisDataRepository<T>
    //   0	121	1	dataId	String
    //   10	107	2	jedis	redis.clients.jedis.Jedis
    //   15	25	3	setKey	String
    //   61	2	3	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   21	11	4	dataKey	String
    //   27	26	5	transaction	redis.clients.jedis.Transaction
    //   91	15	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   11	58	61	redis/clients/jedis/exceptions/JedisConnectionException
    //   11	76	91	finally
  }
  
  public boolean elementExists(String dataId)
  {
    return getElement(dataId) != null;
  }
  
  /* Error */
  public int clean()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   4: invokevirtual 128	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   7: checkcast 132	redis/clients/jedis/Jedis
    //   10: astore_1
    //   11: aload_0
    //   12: invokevirtual 285	mineplex/serverdata/redis/RedisDataRepository:getDeadElements	()Ljava/util/Set;
    //   15: invokeinterface 288 1 0
    //   20: astore_3
    //   21: goto +58 -> 79
    //   24: aload_3
    //   25: invokeinterface 147 1 0
    //   30: checkcast 66	java/lang/String
    //   33: astore_2
    //   34: aload_0
    //   35: aload_2
    //   36: invokevirtual 105	mineplex/serverdata/redis/RedisDataRepository:generateKey	(Ljava/lang/String;)Ljava/lang/String;
    //   39: astore 4
    //   41: aload_1
    //   42: invokevirtual 236	redis/clients/jedis/Jedis:multi	()Lredis/clients/jedis/Transaction;
    //   45: astore 5
    //   47: aload 5
    //   49: aload 4
    //   51: invokevirtual 272	redis/clients/jedis/Transaction:del	(Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   54: pop
    //   55: aload 5
    //   57: aload_0
    //   58: invokevirtual 111	mineplex/serverdata/redis/RedisDataRepository:getElementSetKey	()Ljava/lang/String;
    //   61: iconst_1
    //   62: anewarray 66	java/lang/String
    //   65: dup
    //   66: iconst_0
    //   67: aload_2
    //   68: aastore
    //   69: invokevirtual 275	redis/clients/jedis/Transaction:zrem	(Ljava/lang/String;[Ljava/lang/String;)Lredis/clients/jedis/Response;
    //   72: pop
    //   73: aload 5
    //   75: invokevirtual 251	redis/clients/jedis/Transaction:exec	()Ljava/util/List;
    //   78: pop
    //   79: aload_3
    //   80: invokeinterface 164 1 0
    //   85: ifne -61 -> 24
    //   88: goto +50 -> 138
    //   91: astore_2
    //   92: aload_2
    //   93: invokevirtual 181	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   96: aload_0
    //   97: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   100: aload_1
    //   101: invokevirtual 186	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   104: aconst_null
    //   105: astore_1
    //   106: aload_1
    //   107: ifnull +43 -> 150
    //   110: aload_0
    //   111: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   114: aload_1
    //   115: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   118: goto +32 -> 150
    //   121: astore 6
    //   123: aload_1
    //   124: ifnull +11 -> 135
    //   127: aload_0
    //   128: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   131: aload_1
    //   132: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   135: aload 6
    //   137: athrow
    //   138: aload_1
    //   139: ifnull +11 -> 150
    //   142: aload_0
    //   143: getfield 37	mineplex/serverdata/redis/RedisDataRepository:_writePool	Lredis/clients/jedis/JedisPool;
    //   146: aload_1
    //   147: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   150: iconst_0
    //   151: ireturn
    // Line number table:
    //   Java source line #252	-> byte code offset #0
    //   Java source line #256	-> byte code offset #11
    //   Java source line #258	-> byte code offset #34
    //   Java source line #260	-> byte code offset #41
    //   Java source line #261	-> byte code offset #47
    //   Java source line #262	-> byte code offset #55
    //   Java source line #263	-> byte code offset #73
    //   Java source line #256	-> byte code offset #79
    //   Java source line #265	-> byte code offset #88
    //   Java source line #266	-> byte code offset #91
    //   Java source line #268	-> byte code offset #92
    //   Java source line #269	-> byte code offset #96
    //   Java source line #270	-> byte code offset #104
    //   Java source line #274	-> byte code offset #106
    //   Java source line #276	-> byte code offset #110
    //   Java source line #273	-> byte code offset #121
    //   Java source line #274	-> byte code offset #123
    //   Java source line #276	-> byte code offset #127
    //   Java source line #278	-> byte code offset #135
    //   Java source line #274	-> byte code offset #138
    //   Java source line #276	-> byte code offset #142
    //   Java source line #280	-> byte code offset #150
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	152	0	this	RedisDataRepository<T>
    //   10	137	1	jedis	redis.clients.jedis.Jedis
    //   33	35	2	dataId	String
    //   91	2	2	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   20	60	3	localIterator	java.util.Iterator
    //   39	11	4	dataKey	String
    //   45	29	5	transaction	redis.clients.jedis.Transaction
    //   121	15	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   11	88	91	redis/clients/jedis/exceptions/JedisConnectionException
    //   11	106	121	finally
  }
  
  /* Error */
  protected java.util.Set<String> getActiveElements()
  {
    // Byte code:
    //   0: new 125	java/util/HashSet
    //   3: dup
    //   4: invokespecial 127	java/util/HashSet:<init>	()V
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 128	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 132	redis/clients/jedis/Jedis
    //   18: astore_2
    //   19: new 292	java/lang/StringBuilder
    //   22: dup
    //   23: ldc_w 294
    //   26: invokespecial 296	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   29: aload_0
    //   30: invokevirtual 226	mineplex/serverdata/redis/RedisDataRepository:currentTime	()Ljava/lang/Long;
    //   33: invokevirtual 298	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   36: invokevirtual 302	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: astore_3
    //   40: ldc_w 303
    //   43: astore 4
    //   45: aload_2
    //   46: aload_0
    //   47: invokevirtual 111	mineplex/serverdata/redis/RedisDataRepository:getElementSetKey	()Ljava/lang/String;
    //   50: aload_3
    //   51: aload 4
    //   53: invokevirtual 305	redis/clients/jedis/Jedis:zrangeByScore	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Set;
    //   56: astore_1
    //   57: goto +50 -> 107
    //   60: astore_3
    //   61: aload_3
    //   62: invokevirtual 181	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   65: aload_0
    //   66: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   69: aload_2
    //   70: invokevirtual 186	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   73: aconst_null
    //   74: astore_2
    //   75: aload_2
    //   76: ifnull +43 -> 119
    //   79: aload_0
    //   80: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   83: aload_2
    //   84: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   87: goto +32 -> 119
    //   90: astore 5
    //   92: aload_2
    //   93: ifnull +11 -> 104
    //   96: aload_0
    //   97: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   100: aload_2
    //   101: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   104: aload 5
    //   106: athrow
    //   107: aload_2
    //   108: ifnull +11 -> 119
    //   111: aload_0
    //   112: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   115: aload_2
    //   116: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   119: aload_1
    //   120: areturn
    // Line number table:
    //   Java source line #285	-> byte code offset #0
    //   Java source line #286	-> byte code offset #8
    //   Java source line #290	-> byte code offset #19
    //   Java source line #291	-> byte code offset #40
    //   Java source line #292	-> byte code offset #45
    //   Java source line #293	-> byte code offset #57
    //   Java source line #294	-> byte code offset #60
    //   Java source line #296	-> byte code offset #61
    //   Java source line #297	-> byte code offset #65
    //   Java source line #298	-> byte code offset #73
    //   Java source line #302	-> byte code offset #75
    //   Java source line #304	-> byte code offset #79
    //   Java source line #301	-> byte code offset #90
    //   Java source line #302	-> byte code offset #92
    //   Java source line #304	-> byte code offset #96
    //   Java source line #306	-> byte code offset #104
    //   Java source line #302	-> byte code offset #107
    //   Java source line #304	-> byte code offset #111
    //   Java source line #308	-> byte code offset #119
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	this	RedisDataRepository<T>
    //   7	113	1	dataIds	java.util.Set<String>
    //   18	98	2	jedis	redis.clients.jedis.Jedis
    //   39	12	3	min	String
    //   60	2	3	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   43	9	4	max	String
    //   90	15	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   19	57	60	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	75	90	finally
  }
  
  /* Error */
  protected java.util.Set<String> getDeadElements()
  {
    // Byte code:
    //   0: new 125	java/util/HashSet
    //   3: dup
    //   4: invokespecial 127	java/util/HashSet:<init>	()V
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   12: invokevirtual 128	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   15: checkcast 132	redis/clients/jedis/Jedis
    //   18: astore_2
    //   19: ldc_w 313
    //   22: astore_3
    //   23: new 292	java/lang/StringBuilder
    //   26: dup
    //   27: invokespecial 315	java/lang/StringBuilder:<init>	()V
    //   30: aload_0
    //   31: invokevirtual 226	mineplex/serverdata/redis/RedisDataRepository:currentTime	()Ljava/lang/Long;
    //   34: invokevirtual 298	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   37: invokevirtual 302	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: astore 4
    //   42: aload_2
    //   43: aload_0
    //   44: invokevirtual 111	mineplex/serverdata/redis/RedisDataRepository:getElementSetKey	()Ljava/lang/String;
    //   47: aload_3
    //   48: aload 4
    //   50: invokevirtual 305	redis/clients/jedis/Jedis:zrangeByScore	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Set;
    //   53: astore_1
    //   54: goto +50 -> 104
    //   57: astore_3
    //   58: aload_3
    //   59: invokevirtual 181	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   62: aload_0
    //   63: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   66: aload_2
    //   67: invokevirtual 186	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   70: aconst_null
    //   71: astore_2
    //   72: aload_2
    //   73: ifnull +43 -> 116
    //   76: aload_0
    //   77: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   80: aload_2
    //   81: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   84: goto +32 -> 116
    //   87: astore 5
    //   89: aload_2
    //   90: ifnull +11 -> 101
    //   93: aload_0
    //   94: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   97: aload_2
    //   98: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   101: aload 5
    //   103: athrow
    //   104: aload_2
    //   105: ifnull +11 -> 116
    //   108: aload_0
    //   109: getfield 39	mineplex/serverdata/redis/RedisDataRepository:_readPool	Lredis/clients/jedis/JedisPool;
    //   112: aload_2
    //   113: invokevirtual 190	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   116: aload_1
    //   117: areturn
    // Line number table:
    //   Java source line #313	-> byte code offset #0
    //   Java source line #314	-> byte code offset #8
    //   Java source line #318	-> byte code offset #19
    //   Java source line #319	-> byte code offset #23
    //   Java source line #320	-> byte code offset #42
    //   Java source line #321	-> byte code offset #54
    //   Java source line #322	-> byte code offset #57
    //   Java source line #324	-> byte code offset #58
    //   Java source line #325	-> byte code offset #62
    //   Java source line #326	-> byte code offset #70
    //   Java source line #330	-> byte code offset #72
    //   Java source line #332	-> byte code offset #76
    //   Java source line #329	-> byte code offset #87
    //   Java source line #330	-> byte code offset #89
    //   Java source line #332	-> byte code offset #93
    //   Java source line #334	-> byte code offset #101
    //   Java source line #330	-> byte code offset #104
    //   Java source line #332	-> byte code offset #108
    //   Java source line #336	-> byte code offset #116
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	this	RedisDataRepository<T>
    //   7	110	1	dataIds	java.util.Set<String>
    //   18	95	2	jedis	redis.clients.jedis.Jedis
    //   22	26	3	min	String
    //   57	2	3	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   40	9	4	max	String
    //   87	15	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   19	54	57	redis/clients/jedis/exceptions/JedisConnectionException
    //   19	72	87	finally
  }
  
  protected T deserialize(String serializedData)
  {
    return (Data)Utility.deserialize(serializedData, this._elementType);
  }
  
  protected String serialize(T element)
  {
    return Utility.serialize(element);
  }
  
  protected Long currentTime()
  {
    return Long.valueOf(Utility.currentTimeSeconds());
  }
  
  protected String concatenate(String... elements)
  {
    return Utility.concatenate('.', elements);
  }
}

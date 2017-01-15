package mineplex.serverdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.ConcurrentHashMap;
import mineplex.serverdata.servers.ConnectionData;
import mineplex.serverdata.servers.ServerManager;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Utility
{
  private static Gson _gson = new GsonBuilder().create();
  
  public static Gson getGson()
  {
    return _gson;
  }
  
  private static final ConcurrentHashMap<String, JedisPool> _pools = new ConcurrentHashMap();
  private static JedisPool _masterPool;
  private static JedisPool _slavePool;
  
  public static String serialize(Object object)
  {
    return _gson.toJson(object);
  }
  
  public static <T> T deserialize(String serializedData, Class<T> type)
  {
    return (T)_gson.fromJson(serializedData, type);
  }
  
  public static String concatenate(char delimiter, String... elements)
  {
    int length = elements.length;
    String result = length > 0 ? elements[0] : new String();
    for (int i = 1; i < length; i++) {
      result = result + delimiter + elements[i];
    }
    return result;
  }
  
  /* Error */
  public static long currentTimeSeconds()
  {
    // Byte code:
    //   0: lconst_0
    //   1: lstore_0
    //   2: iconst_0
    //   3: invokestatic 95	mineplex/serverdata/Utility:getPool	(Z)Lredis/clients/jedis/JedisPool;
    //   6: astore_2
    //   7: aload_2
    //   8: invokevirtual 99	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   11: checkcast 105	redis/clients/jedis/Jedis
    //   14: astore_3
    //   15: aload_3
    //   16: invokevirtual 107	redis/clients/jedis/Jedis:time	()Ljava/util/List;
    //   19: iconst_0
    //   20: invokeinterface 111 2 0
    //   25: checkcast 62	java/lang/String
    //   28: invokestatic 117	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   31: lstore_0
    //   32: goto +43 -> 75
    //   35: astore 4
    //   37: aload 4
    //   39: invokevirtual 123	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   42: aload_2
    //   43: aload_3
    //   44: invokevirtual 128	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   47: aconst_null
    //   48: astore_3
    //   49: aload_2
    //   50: ifnull +34 -> 84
    //   53: aload_2
    //   54: aload_3
    //   55: invokevirtual 132	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   58: goto +26 -> 84
    //   61: astore 5
    //   63: aload_2
    //   64: ifnull +8 -> 72
    //   67: aload_2
    //   68: aload_3
    //   69: invokevirtual 132	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   72: aload 5
    //   74: athrow
    //   75: aload_2
    //   76: ifnull +8 -> 84
    //   79: aload_2
    //   80: aload_3
    //   81: invokevirtual 132	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   84: lload_0
    //   85: lreturn
    // Line number table:
    //   Java source line #77	-> byte code offset #0
    //   Java source line #78	-> byte code offset #2
    //   Java source line #79	-> byte code offset #7
    //   Java source line #83	-> byte code offset #15
    //   Java source line #84	-> byte code offset #32
    //   Java source line #85	-> byte code offset #35
    //   Java source line #87	-> byte code offset #37
    //   Java source line #88	-> byte code offset #42
    //   Java source line #89	-> byte code offset #47
    //   Java source line #93	-> byte code offset #49
    //   Java source line #95	-> byte code offset #53
    //   Java source line #92	-> byte code offset #61
    //   Java source line #93	-> byte code offset #63
    //   Java source line #95	-> byte code offset #67
    //   Java source line #97	-> byte code offset #72
    //   Java source line #93	-> byte code offset #75
    //   Java source line #95	-> byte code offset #79
    //   Java source line #99	-> byte code offset #84
    // Local variable table:
    //   start	length	slot	name	signature
    //   1	84	0	currentTime	long
    //   6	74	2	pool	JedisPool
    //   14	67	3	jedis	redis.clients.jedis.Jedis
    //   35	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   61	12	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   15	32	35	redis/clients/jedis/exceptions/JedisConnectionException
    //   15	49	61	finally
  }
  
  /* Error */
  public static long currentTimeMillis()
  {
    // Byte code:
    //   0: lconst_0
    //   1: lstore_0
    //   2: iconst_0
    //   3: invokestatic 95	mineplex/serverdata/Utility:getPool	(Z)Lredis/clients/jedis/JedisPool;
    //   6: astore_2
    //   7: aload_2
    //   8: invokevirtual 99	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   11: checkcast 105	redis/clients/jedis/Jedis
    //   14: astore_3
    //   15: aload_3
    //   16: invokevirtual 107	redis/clients/jedis/Jedis:time	()Ljava/util/List;
    //   19: iconst_0
    //   20: invokeinterface 111 2 0
    //   25: checkcast 62	java/lang/String
    //   28: invokestatic 117	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   31: lstore_0
    //   32: goto +43 -> 75
    //   35: astore 4
    //   37: aload 4
    //   39: invokevirtual 123	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
    //   42: aload_2
    //   43: aload_3
    //   44: invokevirtual 128	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   47: aconst_null
    //   48: astore_3
    //   49: aload_2
    //   50: ifnull +34 -> 84
    //   53: aload_2
    //   54: aload_3
    //   55: invokevirtual 132	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   58: goto +26 -> 84
    //   61: astore 5
    //   63: aload_2
    //   64: ifnull +8 -> 72
    //   67: aload_2
    //   68: aload_3
    //   69: invokevirtual 132	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   72: aload 5
    //   74: athrow
    //   75: aload_2
    //   76: ifnull +8 -> 84
    //   79: aload_2
    //   80: aload_3
    //   81: invokevirtual 132	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   84: lload_0
    //   85: ldc2_w 145
    //   88: lmul
    //   89: lreturn
    // Line number table:
    //   Java source line #108	-> byte code offset #0
    //   Java source line #109	-> byte code offset #2
    //   Java source line #110	-> byte code offset #7
    //   Java source line #114	-> byte code offset #15
    //   Java source line #115	-> byte code offset #32
    //   Java source line #116	-> byte code offset #35
    //   Java source line #118	-> byte code offset #37
    //   Java source line #119	-> byte code offset #42
    //   Java source line #120	-> byte code offset #47
    //   Java source line #124	-> byte code offset #49
    //   Java source line #126	-> byte code offset #53
    //   Java source line #123	-> byte code offset #61
    //   Java source line #124	-> byte code offset #63
    //   Java source line #126	-> byte code offset #67
    //   Java source line #128	-> byte code offset #72
    //   Java source line #124	-> byte code offset #75
    //   Java source line #126	-> byte code offset #79
    //   Java source line #130	-> byte code offset #84
    // Local variable table:
    //   start	length	slot	name	signature
    //   1	84	0	currentTime	long
    //   6	74	2	pool	JedisPool
    //   14	67	3	jedis	redis.clients.jedis.Jedis
    //   35	3	4	exception	redis.clients.jedis.exceptions.JedisConnectionException
    //   61	12	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   15	32	35	redis/clients/jedis/exceptions/JedisConnectionException
    //   15	49	61	finally
  }
  
  public static JedisPool generatePool(ConnectionData connData)
  {
    String key = getConnKey(connData);
    JedisPool pool = (JedisPool)_pools.get(key);
    if (pool == null)
    {
      JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
      jedisPoolConfig.setMaxWaitMillis(1000L);
      jedisPoolConfig.setMinIdle(5);
      jedisPoolConfig.setTestOnBorrow(true);
      
      jedisPoolConfig.setMaxTotal(20);
      jedisPoolConfig.setBlockWhenExhausted(true);
      
      pool = new JedisPool(jedisPoolConfig, connData.getHost(), connData.getPort());
      _pools.put(key, pool);
    }
    return pool;
  }
  
  public static JedisPool getPool(boolean writeable)
  {
    if (writeable)
    {
      if (_masterPool == null) {
        _masterPool = generatePool(ServerManager.getMasterConnection());
      }
      return _masterPool;
    }
    if (_slavePool == null)
    {
      ConnectionData slave = ServerManager.getSlaveConnection();
      
      _slavePool = generatePool(slave);
    }
    return _slavePool;
  }
  
  private static String getConnKey(ConnectionData connData)
  {
    return connData.getHost() + ":" + connData.getPort();
  }
}

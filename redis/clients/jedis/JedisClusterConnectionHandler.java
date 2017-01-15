package redis.clients.jedis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class JedisClusterConnectionHandler
{
  protected Map<String, JedisPool> nodes = new HashMap();
  protected Map<Integer, JedisPool> slots = new HashMap();
  
  abstract Jedis getConnection();
  
  protected void returnConnection(Jedis connection)
  {
    ((JedisPool)this.nodes.get(getNodeKey(connection.getClient()))).returnResource(connection);
  }
  
  public void returnBrokenConnection(Jedis connection)
  {
    ((JedisPool)this.nodes.get(getNodeKey(connection.getClient()))).returnBrokenResource(connection);
  }
  
  abstract Jedis getConnectionFromSlot(int paramInt);
  
  public JedisClusterConnectionHandler(Set<HostAndPort> nodes)
  {
    initializeSlotsCache(nodes);
  }
  
  public Map<String, JedisPool> getNodes()
  {
    return this.nodes;
  }
  
  /* Error */
  private void initializeSlotsCache(Set<HostAndPort> startNodes)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface 13 1 0
    //   6: astore_2
    //   7: aload_2
    //   8: invokeinterface 14 1 0
    //   13: ifeq +134 -> 147
    //   16: aload_2
    //   17: invokeinterface 15 1 0
    //   22: checkcast 16	redis/clients/jedis/HostAndPort
    //   25: astore_3
    //   26: new 5	redis/clients/jedis/JedisPool
    //   29: dup
    //   30: aload_3
    //   31: invokevirtual 17	redis/clients/jedis/HostAndPort:getHost	()Ljava/lang/String;
    //   34: aload_3
    //   35: invokevirtual 18	redis/clients/jedis/HostAndPort:getPort	()I
    //   38: invokespecial 19	redis/clients/jedis/JedisPool:<init>	(Ljava/lang/String;I)V
    //   41: astore 4
    //   43: aload_0
    //   44: getfield 1	redis/clients/jedis/JedisClusterConnectionHandler:nodes	Ljava/util/Map;
    //   47: invokeinterface 20 1 0
    //   52: aload_0
    //   53: getfield 11	redis/clients/jedis/JedisClusterConnectionHandler:slots	Ljava/util/Map;
    //   56: invokeinterface 20 1 0
    //   61: aconst_null
    //   62: astore 5
    //   64: aload 4
    //   66: invokevirtual 21	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
    //   69: checkcast 22	redis/clients/jedis/Jedis
    //   72: astore 5
    //   74: aload_0
    //   75: aload 5
    //   77: invokespecial 23	redis/clients/jedis/JedisClusterConnectionHandler:discoverClusterNodesAndSlots	(Lredis/clients/jedis/Jedis;)V
    //   80: aload 5
    //   82: ifnull +65 -> 147
    //   85: aload 4
    //   87: aload 5
    //   89: invokevirtual 6	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   92: goto +55 -> 147
    //   95: astore 6
    //   97: aload 5
    //   99: ifnull +13 -> 112
    //   102: aload 4
    //   104: aload 5
    //   106: invokevirtual 7	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
    //   109: aconst_null
    //   110: astore 5
    //   112: aload 5
    //   114: ifnull +30 -> 144
    //   117: aload 4
    //   119: aload 5
    //   121: invokevirtual 6	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   124: goto +20 -> 144
    //   127: astore 7
    //   129: aload 5
    //   131: ifnull +10 -> 141
    //   134: aload 4
    //   136: aload 5
    //   138: invokevirtual 6	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
    //   141: aload 7
    //   143: athrow
    //   144: goto -137 -> 7
    //   147: aload_1
    //   148: invokeinterface 13 1 0
    //   153: astore_2
    //   154: aload_2
    //   155: invokeinterface 14 1 0
    //   160: ifeq +21 -> 181
    //   163: aload_2
    //   164: invokeinterface 15 1 0
    //   169: checkcast 16	redis/clients/jedis/HostAndPort
    //   172: astore_3
    //   173: aload_0
    //   174: aload_3
    //   175: invokespecial 25	redis/clients/jedis/JedisClusterConnectionHandler:setNodeIfNotExist	(Lredis/clients/jedis/HostAndPort;)V
    //   178: goto -24 -> 154
    //   181: return
    // Line number table:
    //   Java source line #38	-> byte code offset #0
    //   Java source line #39	-> byte code offset #26
    //   Java source line #42	-> byte code offset #43
    //   Java source line #43	-> byte code offset #52
    //   Java source line #45	-> byte code offset #61
    //   Java source line #47	-> byte code offset #64
    //   Java source line #48	-> byte code offset #74
    //   Java source line #58	-> byte code offset #80
    //   Java source line #59	-> byte code offset #85
    //   Java source line #50	-> byte code offset #95
    //   Java source line #51	-> byte code offset #97
    //   Java source line #52	-> byte code offset #102
    //   Java source line #53	-> byte code offset #109
    //   Java source line #58	-> byte code offset #112
    //   Java source line #59	-> byte code offset #117
    //   Java source line #58	-> byte code offset #127
    //   Java source line #59	-> byte code offset #134
    //   Java source line #62	-> byte code offset #144
    //   Java source line #64	-> byte code offset #147
    //   Java source line #65	-> byte code offset #173
    //   Java source line #66	-> byte code offset #178
    //   Java source line #67	-> byte code offset #181
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	182	0	this	JedisClusterConnectionHandler
    //   0	182	1	startNodes	Set<HostAndPort>
    //   6	11	2	i$	java.util.Iterator
    //   153	11	2	i$	java.util.Iterator
    //   25	10	3	hostAndPort	HostAndPort
    //   172	3	3	node	HostAndPort
    //   41	94	4	jp	JedisPool
    //   62	75	5	jedis	Jedis
    //   95	3	6	e	redis.clients.jedis.exceptions.JedisConnectionException
    //   127	15	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   64	80	95	redis/clients/jedis/exceptions/JedisConnectionException
    //   64	80	127	finally
    //   95	112	127	finally
    //   127	129	127	finally
  }
  
  private void discoverClusterNodesAndSlots(Jedis jedis)
  {
    String localNodes = jedis.clusterNodes();
    for (String nodeInfo : localNodes.split("\n"))
    {
      HostAndPort node = getHostAndPortFromNodeLine(nodeInfo, jedis);
      setNodeIfNotExist(node);
      
      JedisPool nodePool = (JedisPool)this.nodes.get(getNodeKey(node));
      populateNodeSlots(nodeInfo, nodePool);
    }
  }
  
  private void setNodeIfNotExist(HostAndPort node)
  {
    String nodeKey = getNodeKey(node);
    if (this.nodes.containsKey(nodeKey)) {
      return;
    }
    JedisPool nodePool = new JedisPool(node.getHost(), node.getPort());
    this.nodes.put(nodeKey, nodePool);
  }
  
  private void populateNodeSlots(String nodeInfo, JedisPool nodePool)
  {
    String[] nodeInfoArray = nodeInfo.split(" ");
    if (nodeInfoArray.length > 7) {
      for (int i = 8; i < nodeInfoArray.length; i++) {
        processSlot(nodeInfoArray[i], nodePool);
      }
    }
  }
  
  private void processSlot(String slot, JedisPool nodePool)
  {
    if (slot.contains("-"))
    {
      String[] slotRange = slot.split("-");
      for (int i = Integer.valueOf(slotRange[0]).intValue(); i <= Integer.valueOf(slotRange[1]).intValue(); i++) {
        this.slots.put(Integer.valueOf(i), nodePool);
      }
    }
    else
    {
      this.slots.put(Integer.valueOf(slot), nodePool);
    }
  }
  
  private HostAndPort getHostAndPortFromNodeLine(String nodeInfo, Jedis currentConnection)
  {
    String stringHostAndPort = nodeInfo.split(" ", 3)[1];
    if (":0".equals(stringHostAndPort)) {
      return new HostAndPort(currentConnection.getClient().getHost(), currentConnection.getClient().getPort());
    }
    String[] arrayHostAndPort = stringHostAndPort.split(":");
    return new HostAndPort(arrayHostAndPort[0], Integer.valueOf(arrayHostAndPort[1]).intValue());
  }
  
  public void assignSlotToNode(int slot, HostAndPort targetNode)
  {
    JedisPool targetPool = (JedisPool)this.nodes.get(getNodeKey(targetNode));
    if (targetPool != null)
    {
      this.slots.put(Integer.valueOf(slot), targetPool);
    }
    else
    {
      setNodeIfNotExist(targetNode);
      
      targetPool = (JedisPool)this.nodes.get(getNodeKey(targetNode));
      this.slots.put(Integer.valueOf(slot), targetPool);
    }
  }
  
  protected JedisPool getRandomConnection()
  {
    Object[] nodeArray = this.nodes.values().toArray();
    return (JedisPool)nodeArray[new java.util.Random().nextInt(nodeArray.length)];
  }
  
  protected String getNodeKey(HostAndPort hnp)
  {
    return hnp.getHost() + ":" + hnp.getPort();
  }
  
  protected String getNodeKey(Client client)
  {
    return client.getHost() + ":" + client.getPort();
  }
}

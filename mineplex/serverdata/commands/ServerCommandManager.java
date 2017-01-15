package mineplex.serverdata.commands;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import mineplex.serverdata.Utility;
import mineplex.serverdata.servers.ServerManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ServerCommandManager
{
  private static ServerCommandManager _instance;
  public final String SERVER_COMMANDS_CHANNEL = "commands.server";
  private JedisPool _writePool;
  private JedisPool _readPool;
  private Map<String, CommandType> _commandTypes;
  private String _localServerName;
  
  public void initializeServer(String serverName)
  {
    this._localServerName = serverName;
  }
  
  public boolean isServerInitialized()
  {
    return this._localServerName != null;
  }
  
  private ServerCommandManager()
  {
    this._writePool = Utility.generatePool(ServerManager.getMasterConnection());
    this._readPool = Utility.generatePool(ServerManager.getSlaveConnection());
    
    this._commandTypes = new HashMap();
    
    initialize();
  }
  
  private void initialize()
  {
    final Jedis jedis = (Jedis)this._readPool.getResource();
    
    Thread thread = new Thread("Redis Manager")
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 14	mineplex/serverdata/commands/ServerCommandManager$1:val$jedis	Lredis/clients/jedis/Jedis;
        //   4: new 27	mineplex/serverdata/commands/ServerCommandListener
        //   7: dup
        //   8: invokespecial 29	mineplex/serverdata/commands/ServerCommandListener:<init>	()V
        //   11: iconst_1
        //   12: anewarray 31	java/lang/String
        //   15: dup
        //   16: iconst_0
        //   17: ldc 33
        //   19: aastore
        //   20: invokevirtual 35	redis/clients/jedis/Jedis:psubscribe	(Lredis/clients/jedis/JedisPubSub;[Ljava/lang/String;)V
        //   23: goto +76 -> 99
        //   26: astore_1
        //   27: aload_1
        //   28: invokevirtual 41	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
        //   31: aload_0
        //   32: getfield 12	mineplex/serverdata/commands/ServerCommandManager$1:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   35: invokestatic 46	mineplex/serverdata/commands/ServerCommandManager:access$0	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   38: aload_0
        //   39: getfield 14	mineplex/serverdata/commands/ServerCommandManager$1:val$jedis	Lredis/clients/jedis/Jedis;
        //   42: invokevirtual 52	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
        //   45: aload_0
        //   46: getfield 12	mineplex/serverdata/commands/ServerCommandManager$1:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   49: invokestatic 46	mineplex/serverdata/commands/ServerCommandManager:access$0	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   52: ifnull +71 -> 123
        //   55: aload_0
        //   56: getfield 12	mineplex/serverdata/commands/ServerCommandManager$1:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   59: invokestatic 46	mineplex/serverdata/commands/ServerCommandManager:access$0	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   62: aload_0
        //   63: getfield 14	mineplex/serverdata/commands/ServerCommandManager$1:val$jedis	Lredis/clients/jedis/Jedis;
        //   66: invokevirtual 58	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
        //   69: goto +54 -> 123
        //   72: astore_2
        //   73: aload_0
        //   74: getfield 12	mineplex/serverdata/commands/ServerCommandManager$1:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   77: invokestatic 46	mineplex/serverdata/commands/ServerCommandManager:access$0	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   80: ifnull +17 -> 97
        //   83: aload_0
        //   84: getfield 12	mineplex/serverdata/commands/ServerCommandManager$1:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   87: invokestatic 46	mineplex/serverdata/commands/ServerCommandManager:access$0	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   90: aload_0
        //   91: getfield 14	mineplex/serverdata/commands/ServerCommandManager$1:val$jedis	Lredis/clients/jedis/Jedis;
        //   94: invokevirtual 58	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
        //   97: aload_2
        //   98: athrow
        //   99: aload_0
        //   100: getfield 12	mineplex/serverdata/commands/ServerCommandManager$1:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   103: invokestatic 46	mineplex/serverdata/commands/ServerCommandManager:access$0	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   106: ifnull +17 -> 123
        //   109: aload_0
        //   110: getfield 12	mineplex/serverdata/commands/ServerCommandManager$1:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   113: invokestatic 46	mineplex/serverdata/commands/ServerCommandManager:access$0	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   116: aload_0
        //   117: getfield 14	mineplex/serverdata/commands/ServerCommandManager$1:val$jedis	Lredis/clients/jedis/Jedis;
        //   120: invokevirtual 58	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
        //   123: return
        // Line number table:
        //   Java source line #57	-> byte code offset #0
        //   Java source line #58	-> byte code offset #23
        //   Java source line #59	-> byte code offset #26
        //   Java source line #61	-> byte code offset #27
        //   Java source line #62	-> byte code offset #31
        //   Java source line #66	-> byte code offset #45
        //   Java source line #68	-> byte code offset #55
        //   Java source line #65	-> byte code offset #72
        //   Java source line #66	-> byte code offset #73
        //   Java source line #68	-> byte code offset #83
        //   Java source line #70	-> byte code offset #97
        //   Java source line #66	-> byte code offset #99
        //   Java source line #68	-> byte code offset #109
        //   Java source line #71	-> byte code offset #123
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	124	0	this	1
        //   26	2	1	exception	redis.clients.jedis.exceptions.JedisConnectionException
        //   72	26	2	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   0	23	26	redis/clients/jedis/exceptions/JedisConnectionException
        //   0	45	72	finally
      }
    };
    thread.start();
  }
  
  public void publishCommand(final ServerCommand serverCommand)
  {
    new Thread(new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 14	mineplex/serverdata/commands/ServerCommandManager$2:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   4: invokestatic 26	mineplex/serverdata/commands/ServerCommandManager:access$1	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   7: invokevirtual 32	redis/clients/jedis/JedisPool:getResource	()Ljava/lang/Object;
        //   10: checkcast 38	redis/clients/jedis/Jedis
        //   13: astore_1
        //   14: aload_0
        //   15: getfield 16	mineplex/serverdata/commands/ServerCommandManager$2:val$serverCommand	Lmineplex/serverdata/commands/ServerCommand;
        //   18: invokevirtual 40	java/lang/Object:getClass	()Ljava/lang/Class;
        //   21: invokevirtual 44	java/lang/Class:getSimpleName	()Ljava/lang/String;
        //   24: astore_2
        //   25: aload_0
        //   26: getfield 16	mineplex/serverdata/commands/ServerCommandManager$2:val$serverCommand	Lmineplex/serverdata/commands/ServerCommand;
        //   29: invokestatic 50	mineplex/serverdata/Utility:serialize	(Ljava/lang/Object;)Ljava/lang/String;
        //   32: astore_3
        //   33: aload_1
        //   34: new 56	java/lang/StringBuilder
        //   37: dup
        //   38: ldc 58
        //   40: invokespecial 60	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   43: aload_2
        //   44: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   47: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   50: aload_3
        //   51: invokevirtual 70	redis/clients/jedis/Jedis:publish	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Long;
        //   54: pop
        //   55: goto +71 -> 126
        //   58: astore_2
        //   59: aload_2
        //   60: invokevirtual 74	redis/clients/jedis/exceptions/JedisConnectionException:printStackTrace	()V
        //   63: aload_0
        //   64: getfield 14	mineplex/serverdata/commands/ServerCommandManager$2:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   67: invokestatic 26	mineplex/serverdata/commands/ServerCommandManager:access$1	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   70: aload_1
        //   71: invokevirtual 79	redis/clients/jedis/JedisPool:returnBrokenResource	(Lredis/clients/jedis/Jedis;)V
        //   74: aconst_null
        //   75: astore_1
        //   76: aload_0
        //   77: getfield 14	mineplex/serverdata/commands/ServerCommandManager$2:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   80: invokestatic 26	mineplex/serverdata/commands/ServerCommandManager:access$1	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   83: ifnull +64 -> 147
        //   86: aload_0
        //   87: getfield 14	mineplex/serverdata/commands/ServerCommandManager$2:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   90: invokestatic 26	mineplex/serverdata/commands/ServerCommandManager:access$1	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   93: aload_1
        //   94: invokevirtual 83	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
        //   97: goto +50 -> 147
        //   100: astore 4
        //   102: aload_0
        //   103: getfield 14	mineplex/serverdata/commands/ServerCommandManager$2:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   106: invokestatic 26	mineplex/serverdata/commands/ServerCommandManager:access$1	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   109: ifnull +14 -> 123
        //   112: aload_0
        //   113: getfield 14	mineplex/serverdata/commands/ServerCommandManager$2:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   116: invokestatic 26	mineplex/serverdata/commands/ServerCommandManager:access$1	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   119: aload_1
        //   120: invokevirtual 83	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
        //   123: aload 4
        //   125: athrow
        //   126: aload_0
        //   127: getfield 14	mineplex/serverdata/commands/ServerCommandManager$2:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   130: invokestatic 26	mineplex/serverdata/commands/ServerCommandManager:access$1	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   133: ifnull +14 -> 147
        //   136: aload_0
        //   137: getfield 14	mineplex/serverdata/commands/ServerCommandManager$2:this$0	Lmineplex/serverdata/commands/ServerCommandManager;
        //   140: invokestatic 26	mineplex/serverdata/commands/ServerCommandManager:access$1	(Lmineplex/serverdata/commands/ServerCommandManager;)Lredis/clients/jedis/JedisPool;
        //   143: aload_1
        //   144: invokevirtual 83	redis/clients/jedis/JedisPool:returnResource	(Lredis/clients/jedis/Jedis;)V
        //   147: return
        // Line number table:
        //   Java source line #87	-> byte code offset #0
        //   Java source line #91	-> byte code offset #14
        //   Java source line #92	-> byte code offset #25
        //   Java source line #93	-> byte code offset #33
        //   Java source line #94	-> byte code offset #55
        //   Java source line #95	-> byte code offset #58
        //   Java source line #97	-> byte code offset #59
        //   Java source line #98	-> byte code offset #63
        //   Java source line #99	-> byte code offset #74
        //   Java source line #103	-> byte code offset #76
        //   Java source line #105	-> byte code offset #86
        //   Java source line #102	-> byte code offset #100
        //   Java source line #103	-> byte code offset #102
        //   Java source line #105	-> byte code offset #112
        //   Java source line #107	-> byte code offset #123
        //   Java source line #103	-> byte code offset #126
        //   Java source line #105	-> byte code offset #136
        //   Java source line #108	-> byte code offset #147
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	148	0	this	2
        //   13	131	1	jedis	Jedis
        //   24	20	2	commandType	String
        //   58	2	2	exception	redis.clients.jedis.exceptions.JedisConnectionException
        //   32	19	3	serializedCommand	String
        //   100	24	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   14	55	58	redis/clients/jedis/exceptions/JedisConnectionException
        //   14	76	100	finally
      }
    })
    
      .start();
  }
  
  public void handleCommand(String commandType, String serializedCommand)
  {
    if (!isServerInitialized()) {
      return;
    }
    if (this._commandTypes.containsKey(commandType))
    {
      Class<? extends ServerCommand> commandClazz = ((CommandType)this._commandTypes.get(commandType)).getCommandType();
      ServerCommand serverCommand = (ServerCommand)Utility.deserialize(serializedCommand, commandClazz);
      if (serverCommand.isTargetServer(this._localServerName))
      {
        CommandCallback callback = ((CommandType)this._commandTypes.get(commandType)).getCallback();
        serverCommand.run();
        if (callback != null) {
          callback.run(serverCommand);
        }
      }
    }
  }
  
  public void registerCommandType(String commandName, Class<? extends ServerCommand> commandType, CommandCallback callback)
  {
    this._commandTypes.containsKey(commandName);
    
    CommandType cmdType = new CommandType(commandType, callback);
    this._commandTypes.put(commandName, cmdType);
    System.out.println("Registered : " + commandName);
  }
  
  public void registerCommandType(String commandName, Class<? extends ServerCommand> commandType)
  {
    registerCommandType(commandName, commandType, null);
  }
  
  public static ServerCommandManager getInstance()
  {
    if (_instance == null) {
      _instance = new ServerCommandManager();
    }
    return _instance;
  }
}

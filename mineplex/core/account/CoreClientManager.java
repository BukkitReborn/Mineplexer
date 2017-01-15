package mineplex.core.account;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import mineplex.core.MiniPlugin;
import mineplex.core.account.command.UpdateRank;
import mineplex.core.account.event.ClientUnloadEvent;
import mineplex.core.account.event.ClientWebResponseEvent;
import mineplex.core.account.repository.AccountRepository;
import mineplex.core.account.repository.token.ClientToken;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.timing.TimingManager;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import mineplex.serverdata.Region;
import mineplex.serverdata.redis.RedisDataRepository;
import mineplex.serverdata.servers.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class CoreClientManager
  extends MiniPlugin
{
  private static NautHashMap<String, Object> _clientLoginLock = new NautHashMap();
  private JavaPlugin _plugin;
  private AccountRepository _repository;
  private NautHashMap<String, CoreClient> _clientList;
  private HashSet<String> _duplicateLoginGlitchPreventionList;
  private RedisDataRepository<AccountCache> _accountCacheRepository;
  private NautHashMap<String, ILoginProcessor> _loginProcessors = new NautHashMap();
  private Object _clientLock = new Object();
  private static AtomicInteger _clientsConnecting = new AtomicInteger(0);
  private static AtomicInteger _clientsProcessing = new AtomicInteger(0);
  
  public CoreClientManager(JavaPlugin plugin, String webServer)
  {
    super("Client Manager", plugin);
    
    this._plugin = plugin;
    this._repository = new AccountRepository(plugin, webServer);
    this._clientList = new NautHashMap();
    this._duplicateLoginGlitchPreventionList = new HashSet();
    
    this._accountCacheRepository = new RedisDataRepository(ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), 
      Region.ALL, AccountCache.class, "accountCache");
  }
  
  public AccountRepository getRepository()
  {
    return this._repository;
  }
  
  public void addCommands()
  {
    addCommand(new UpdateRank(this));
  }
  
  public CoreClient Add(String name)
  {
    CoreClient newClient = null;
    if (newClient == null) {
      newClient = new CoreClient(name);
    }
    CoreClient oldClient = null;
    synchronized (this._clientLock)
    {
      oldClient = (CoreClient)this._clientList.put(name, newClient);
    }
    if (oldClient != null) {
      oldClient.Delete();
    }
    return newClient;
  }
  
  public void Del(String name)
  {
    synchronized (this._clientLock)
    {
      this._clientList.remove(name);
    }
    this._plugin.getServer().getPluginManager().callEvent(new ClientUnloadEvent(name));
  }
  
  /* Error */
  public CoreClient Get(String name)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 60	mineplex/core/account/CoreClientManager:_clientLock	Ljava/lang/Object;
    //   4: dup
    //   5: astore_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 70	mineplex/core/account/CoreClientManager:_clientList	Lmineplex/core/common/util/NautHashMap;
    //   11: aload_1
    //   12: invokevirtual 170	mineplex/core/common/util/NautHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   15: checkcast 122	mineplex/core/account/CoreClient
    //   18: aload_2
    //   19: monitorexit
    //   20: areturn
    //   21: aload_2
    //   22: monitorexit
    //   23: athrow
    // Line number table:
    //   Java source line #119	-> byte code offset #0
    //   Java source line #121	-> byte code offset #7
    //   Java source line #119	-> byte code offset #21
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	24	0	this	CoreClientManager
    //   0	24	1	name	String
    //   5	17	2	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   7	20	21	finally
    //   21	23	21	finally
  }
  
  /* Error */
  public CoreClient Get(Player player)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 60	mineplex/core/account/CoreClientManager:_clientLock	Ljava/lang/Object;
    //   4: dup
    //   5: astore_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 70	mineplex/core/account/CoreClientManager:_clientList	Lmineplex/core/common/util/NautHashMap;
    //   11: aload_1
    //   12: invokeinterface 174 1 0
    //   17: invokevirtual 170	mineplex/core/common/util/NautHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   20: checkcast 122	mineplex/core/account/CoreClient
    //   23: aload_2
    //   24: monitorexit
    //   25: areturn
    //   26: aload_2
    //   27: monitorexit
    //   28: athrow
    // Line number table:
    //   Java source line #127	-> byte code offset #0
    //   Java source line #129	-> byte code offset #7
    //   Java source line #127	-> byte code offset #26
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	29	0	this	CoreClientManager
    //   0	29	1	player	Player
    //   5	22	2	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   7	25	26	finally
    //   26	28	26	finally
  }
  
  public int getPlayerCountIncludingConnecting()
  {
    return Bukkit.getOnlinePlayers().size() + Math.max(0, _clientsConnecting.get());
  }
  
  /* Error */
  @EventHandler(priority=EventPriority.LOWEST)
  public void AsyncLogin(org.bukkit.event.player.AsyncPlayerPreLoginEvent event)
  {
    // Byte code:
    //   0: getstatic 43	mineplex/core/account/CoreClientManager:_clientsConnecting	Ljava/util/concurrent/atomic/AtomicInteger;
    //   3: invokevirtual 210	java/util/concurrent/atomic/AtomicInteger:incrementAndGet	()I
    //   6: pop
    //   7: goto +17 -> 24
    //   10: ldc2_w 213
    //   13: invokestatic 215	java/lang/Thread:sleep	(J)V
    //   16: goto +8 -> 24
    //   19: astore_2
    //   20: aload_2
    //   21: invokevirtual 221	java/lang/InterruptedException:printStackTrace	()V
    //   24: getstatic 45	mineplex/core/account/CoreClientManager:_clientsProcessing	Ljava/util/concurrent/atomic/AtomicInteger;
    //   27: invokevirtual 195	java/util/concurrent/atomic/AtomicInteger:get	()I
    //   30: iconst_5
    //   31: if_icmpge -21 -> 10
    //   34: getstatic 45	mineplex/core/account/CoreClientManager:_clientsProcessing	Ljava/util/concurrent/atomic/AtomicInteger;
    //   37: invokevirtual 210	java/util/concurrent/atomic/AtomicInteger:incrementAndGet	()I
    //   40: pop
    //   41: aload_0
    //   42: aload_0
    //   43: aload_1
    //   44: invokevirtual 226	org/bukkit/event/player/AsyncPlayerPreLoginEvent:getName	()Ljava/lang/String;
    //   47: invokevirtual 229	mineplex/core/account/CoreClientManager:Add	(Ljava/lang/String;)Lmineplex/core/account/CoreClient;
    //   50: aload_1
    //   51: invokevirtual 231	org/bukkit/event/player/AsyncPlayerPreLoginEvent:getUniqueId	()Ljava/util/UUID;
    //   54: aload_1
    //   55: invokevirtual 235	org/bukkit/event/player/AsyncPlayerPreLoginEvent:getAddress	()Ljava/net/InetAddress;
    //   58: invokevirtual 239	java/net/InetAddress:getHostAddress	()Ljava/lang/String;
    //   61: invokespecial 244	mineplex/core/account/CoreClientManager:LoadClient	(Lmineplex/core/account/CoreClient;Ljava/util/UUID;Ljava/lang/String;)Z
    //   64: ifne +50 -> 114
    //   67: aload_1
    //   68: getstatic 248	org/bukkit/event/player/AsyncPlayerPreLoginEvent$Result:KICK_OTHER	Lorg/bukkit/event/player/AsyncPlayerPreLoginEvent$Result;
    //   71: ldc -2
    //   73: invokevirtual 256	org/bukkit/event/player/AsyncPlayerPreLoginEvent:disallow	(Lorg/bukkit/event/player/AsyncPlayerPreLoginEvent$Result;Ljava/lang/String;)V
    //   76: goto +38 -> 114
    //   79: astore_2
    //   80: aload_1
    //   81: getstatic 248	org/bukkit/event/player/AsyncPlayerPreLoginEvent$Result:KICK_OTHER	Lorg/bukkit/event/player/AsyncPlayerPreLoginEvent$Result;
    //   84: ldc_w 260
    //   87: invokevirtual 256	org/bukkit/event/player/AsyncPlayerPreLoginEvent:disallow	(Lorg/bukkit/event/player/AsyncPlayerPreLoginEvent$Result;Ljava/lang/String;)V
    //   90: aload_2
    //   91: invokevirtual 262	java/lang/Exception:printStackTrace	()V
    //   94: getstatic 45	mineplex/core/account/CoreClientManager:_clientsProcessing	Ljava/util/concurrent/atomic/AtomicInteger;
    //   97: invokevirtual 265	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   100: pop
    //   101: goto +20 -> 121
    //   104: astore_3
    //   105: getstatic 45	mineplex/core/account/CoreClientManager:_clientsProcessing	Ljava/util/concurrent/atomic/AtomicInteger;
    //   108: invokevirtual 265	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   111: pop
    //   112: aload_3
    //   113: athrow
    //   114: getstatic 45	mineplex/core/account/CoreClientManager:_clientsProcessing	Ljava/util/concurrent/atomic/AtomicInteger;
    //   117: invokevirtual 265	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   120: pop
    //   121: invokestatic 268	org/bukkit/Bukkit:hasWhitelist	()Z
    //   124: ifeq +103 -> 227
    //   127: aload_0
    //   128: aload_1
    //   129: invokevirtual 226	org/bukkit/event/player/AsyncPlayerPreLoginEvent:getName	()Ljava/lang/String;
    //   132: invokevirtual 272	mineplex/core/account/CoreClientManager:Get	(Ljava/lang/String;)Lmineplex/core/account/CoreClient;
    //   135: invokevirtual 274	mineplex/core/account/CoreClient:GetRank	()Lmineplex/core/common/Rank;
    //   138: getstatic 278	mineplex/core/common/Rank:MODERATOR	Lmineplex/core/common/Rank;
    //   141: invokevirtual 284	mineplex/core/common/Rank:Has	(Lmineplex/core/common/Rank;)Z
    //   144: ifne +83 -> 227
    //   147: invokestatic 288	org/bukkit/Bukkit:getWhitelistedPlayers	()Ljava/util/Set;
    //   150: invokeinterface 292 1 0
    //   155: astore_3
    //   156: goto +37 -> 193
    //   159: aload_3
    //   160: invokeinterface 298 1 0
    //   165: checkcast 304	org/bukkit/OfflinePlayer
    //   168: astore_2
    //   169: aload_2
    //   170: invokeinterface 306 1 0
    //   175: aload_1
    //   176: invokevirtual 226	org/bukkit/event/player/AsyncPlayerPreLoginEvent:getName	()Ljava/lang/String;
    //   179: invokevirtual 307	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   182: ifeq +11 -> 193
    //   185: getstatic 43	mineplex/core/account/CoreClientManager:_clientsConnecting	Ljava/util/concurrent/atomic/AtomicInteger;
    //   188: invokevirtual 265	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   191: pop
    //   192: return
    //   193: aload_3
    //   194: invokeinterface 311 1 0
    //   199: ifne -40 -> 159
    //   202: aload_1
    //   203: getstatic 314	org/bukkit/event/player/AsyncPlayerPreLoginEvent$Result:KICK_WHITELIST	Lorg/bukkit/event/player/AsyncPlayerPreLoginEvent$Result;
    //   206: ldc_w 317
    //   209: invokevirtual 256	org/bukkit/event/player/AsyncPlayerPreLoginEvent:disallow	(Lorg/bukkit/event/player/AsyncPlayerPreLoginEvent$Result;Ljava/lang/String;)V
    //   212: goto +15 -> 227
    //   215: astore 4
    //   217: getstatic 43	mineplex/core/account/CoreClientManager:_clientsConnecting	Ljava/util/concurrent/atomic/AtomicInteger;
    //   220: invokevirtual 265	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   223: pop
    //   224: aload 4
    //   226: athrow
    //   227: getstatic 43	mineplex/core/account/CoreClientManager:_clientsConnecting	Ljava/util/concurrent/atomic/AtomicInteger;
    //   230: invokevirtual 265	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   233: pop
    //   234: return
    // Line number table:
    //   Java source line #143	-> byte code offset #0
    //   Java source line #144	-> byte code offset #7
    //   Java source line #148	-> byte code offset #10
    //   Java source line #149	-> byte code offset #16
    //   Java source line #150	-> byte code offset #19
    //   Java source line #152	-> byte code offset #20
    //   Java source line #144	-> byte code offset #24
    //   Java source line #158	-> byte code offset #34
    //   Java source line #160	-> byte code offset #41
    //   Java source line #161	-> byte code offset #67
    //   Java source line #162	-> byte code offset #76
    //   Java source line #163	-> byte code offset #79
    //   Java source line #165	-> byte code offset #80
    //   Java source line #166	-> byte code offset #90
    //   Java source line #170	-> byte code offset #94
    //   Java source line #169	-> byte code offset #104
    //   Java source line #170	-> byte code offset #105
    //   Java source line #171	-> byte code offset #112
    //   Java source line #170	-> byte code offset #114
    //   Java source line #173	-> byte code offset #121
    //   Java source line #175	-> byte code offset #147
    //   Java source line #177	-> byte code offset #169
    //   Java source line #188	-> byte code offset #185
    //   Java source line #179	-> byte code offset #192
    //   Java source line #175	-> byte code offset #193
    //   Java source line #183	-> byte code offset #202
    //   Java source line #185	-> byte code offset #212
    //   Java source line #187	-> byte code offset #215
    //   Java source line #188	-> byte code offset #217
    //   Java source line #189	-> byte code offset #224
    //   Java source line #188	-> byte code offset #227
    //   Java source line #190	-> byte code offset #234
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	235	0	this	CoreClientManager
    //   0	235	1	event	org.bukkit.event.player.AsyncPlayerPreLoginEvent
    //   19	2	2	e	InterruptedException
    //   79	12	2	exception	Exception
    //   168	2	2	player	org.bukkit.OfflinePlayer
    //   104	9	3	localObject1	Object
    //   155	39	3	localIterator	Iterator
    //   215	10	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   10	16	19	java/lang/InterruptedException
    //   34	76	79	java/lang/Exception
    //   34	94	104	finally
    //   0	185	215	finally
    //   193	215	215	finally
  }
  
  public void loadClientByName(final String playerName, final Runnable runnable)
  {
    Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_1
        //   2: new 30	org/bukkit/craftbukkit/libs/com/google/gson/Gson
        //   5: dup
        //   6: invokespecial 32	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
        //   9: astore_2
        //   10: aload_0
        //   11: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   14: aload_0
        //   15: getfield 18	mineplex/core/account/CoreClientManager$1:val$playerName	Ljava/lang/String;
        //   18: invokevirtual 33	mineplex/core/account/CoreClientManager:loadUUIDFromDB	(Ljava/lang/String;)Ljava/util/UUID;
        //   21: astore_3
        //   22: aload_3
        //   23: ifnonnull +11 -> 34
        //   26: aload_0
        //   27: getfield 18	mineplex/core/account/CoreClientManager$1:val$playerName	Ljava/lang/String;
        //   30: invokestatic 39	mineplex/core/common/util/UUIDFetcher:getUUIDOf	(Ljava/lang/String;)Ljava/util/UUID;
        //   33: astore_3
        //   34: ldc 44
        //   36: astore 4
        //   38: aload_3
        //   39: ifnonnull +22 -> 61
        //   42: aload_0
        //   43: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   46: invokestatic 46	mineplex/core/account/CoreClientManager:access$0	(Lmineplex/core/account/CoreClientManager;)Lmineplex/core/account/repository/AccountRepository;
        //   49: aload_0
        //   50: getfield 18	mineplex/core/account/CoreClientManager$1:val$playerName	Ljava/lang/String;
        //   53: invokevirtual 50	mineplex/core/account/repository/AccountRepository:getClientByName	(Ljava/lang/String;)Ljava/lang/String;
        //   56: astore 4
        //   58: goto +16 -> 74
        //   61: aload_0
        //   62: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   65: invokestatic 46	mineplex/core/account/CoreClientManager:access$0	(Lmineplex/core/account/CoreClientManager;)Lmineplex/core/account/repository/AccountRepository;
        //   68: aload_3
        //   69: invokevirtual 56	mineplex/core/account/repository/AccountRepository:getClientByUUID	(Ljava/util/UUID;)Ljava/lang/String;
        //   72: astore 4
        //   74: aload_2
        //   75: aload 4
        //   77: ldc 60
        //   79: invokevirtual 62	org/bukkit/craftbukkit/libs/com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
        //   82: checkcast 60	mineplex/core/account/repository/token/ClientToken
        //   85: astore_1
        //   86: aload_0
        //   87: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   90: aload_0
        //   91: getfield 18	mineplex/core/account/CoreClientManager$1:val$playerName	Ljava/lang/String;
        //   94: invokevirtual 66	mineplex/core/account/CoreClientManager:Add	(Ljava/lang/String;)Lmineplex/core/account/CoreClient;
        //   97: astore 5
        //   99: aload 5
        //   101: aload_1
        //   102: getfield 70	mineplex/core/account/repository/token/ClientToken:Rank	Ljava/lang/String;
        //   105: invokestatic 73	mineplex/core/common/Rank:valueOf	(Ljava/lang/String;)Lmineplex/core/common/Rank;
        //   108: invokevirtual 79	mineplex/core/account/CoreClient:SetRank	(Lmineplex/core/common/Rank;)V
        //   111: aload 5
        //   113: aload_0
        //   114: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   117: invokestatic 46	mineplex/core/account/CoreClientManager:access$0	(Lmineplex/core/account/CoreClientManager;)Lmineplex/core/account/repository/AccountRepository;
        //   120: aload_0
        //   121: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   124: invokestatic 85	mineplex/core/account/CoreClientManager:access$1	(Lmineplex/core/account/CoreClientManager;)Lmineplex/core/common/util/NautHashMap;
        //   127: aload_3
        //   128: invokevirtual 89	java/util/UUID:toString	()Ljava/lang/String;
        //   131: aload 5
        //   133: invokevirtual 95	mineplex/core/account/CoreClient:GetPlayerName	()Ljava/lang/String;
        //   136: invokevirtual 98	mineplex/core/account/repository/AccountRepository:login	(Lmineplex/core/common/util/NautHashMap;Ljava/lang/String;Ljava/lang/String;)I
        //   139: invokevirtual 102	mineplex/core/account/CoreClient:setAccountId	(I)V
        //   142: invokestatic 106	org/bukkit/Bukkit:getServer	()Lorg/bukkit/Server;
        //   145: invokeinterface 112 1 0
        //   150: new 118	mineplex/core/account/event/ClientWebResponseEvent
        //   153: dup
        //   154: aload 4
        //   156: aload_3
        //   157: invokespecial 120	mineplex/core/account/event/ClientWebResponseEvent:<init>	(Ljava/lang/String;Ljava/util/UUID;)V
        //   160: invokeinterface 123 2 0
        //   165: aload 5
        //   167: invokevirtual 129	mineplex/core/account/CoreClient:getAccountId	()I
        //   170: ifle +108 -> 278
        //   173: aload_0
        //   174: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   177: invokestatic 133	mineplex/core/account/CoreClientManager:access$2	(Lmineplex/core/account/CoreClientManager;)Lmineplex/serverdata/redis/RedisDataRepository;
        //   180: new 137	mineplex/core/account/AccountCache
        //   183: dup
        //   184: aload_3
        //   185: aload 5
        //   187: invokevirtual 129	mineplex/core/account/CoreClient:getAccountId	()I
        //   190: invokespecial 139	mineplex/core/account/AccountCache:<init>	(Ljava/util/UUID;I)V
        //   193: invokevirtual 142	mineplex/serverdata/redis/RedisDataRepository:addElement	(Lmineplex/serverdata/data/Data;)V
        //   196: goto +82 -> 278
        //   199: astore_1
        //   200: aload_1
        //   201: invokevirtual 148	java/lang/Exception:printStackTrace	()V
        //   204: invokestatic 106	org/bukkit/Bukkit:getServer	()Lorg/bukkit/Server;
        //   207: invokeinterface 153 1 0
        //   212: aload_0
        //   213: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   216: invokevirtual 157	mineplex/core/account/CoreClientManager:getPlugin	()Lorg/bukkit/plugin/java/JavaPlugin;
        //   219: new 161	mineplex/core/account/CoreClientManager$1$1
        //   222: dup
        //   223: aload_0
        //   224: aload_0
        //   225: getfield 20	mineplex/core/account/CoreClientManager$1:val$runnable	Ljava/lang/Runnable;
        //   228: invokespecial 163	mineplex/core/account/CoreClientManager$1$1:<init>	(Lmineplex/core/account/CoreClientManager$1;Ljava/lang/Runnable;)V
        //   231: invokeinterface 166 3 0
        //   236: pop
        //   237: goto +74 -> 311
        //   240: astore 6
        //   242: invokestatic 106	org/bukkit/Bukkit:getServer	()Lorg/bukkit/Server;
        //   245: invokeinterface 153 1 0
        //   250: aload_0
        //   251: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   254: invokevirtual 157	mineplex/core/account/CoreClientManager:getPlugin	()Lorg/bukkit/plugin/java/JavaPlugin;
        //   257: new 161	mineplex/core/account/CoreClientManager$1$1
        //   260: dup
        //   261: aload_0
        //   262: aload_0
        //   263: getfield 20	mineplex/core/account/CoreClientManager$1:val$runnable	Ljava/lang/Runnable;
        //   266: invokespecial 163	mineplex/core/account/CoreClientManager$1$1:<init>	(Lmineplex/core/account/CoreClientManager$1;Ljava/lang/Runnable;)V
        //   269: invokeinterface 166 3 0
        //   274: pop
        //   275: aload 6
        //   277: athrow
        //   278: invokestatic 106	org/bukkit/Bukkit:getServer	()Lorg/bukkit/Server;
        //   281: invokeinterface 153 1 0
        //   286: aload_0
        //   287: getfield 16	mineplex/core/account/CoreClientManager$1:this$0	Lmineplex/core/account/CoreClientManager;
        //   290: invokevirtual 157	mineplex/core/account/CoreClientManager:getPlugin	()Lorg/bukkit/plugin/java/JavaPlugin;
        //   293: new 161	mineplex/core/account/CoreClientManager$1$1
        //   296: dup
        //   297: aload_0
        //   298: aload_0
        //   299: getfield 20	mineplex/core/account/CoreClientManager$1:val$runnable	Ljava/lang/Runnable;
        //   302: invokespecial 163	mineplex/core/account/CoreClientManager$1$1:<init>	(Lmineplex/core/account/CoreClientManager$1;Ljava/lang/Runnable;)V
        //   305: invokeinterface 166 3 0
        //   310: pop
        //   311: return
        // Line number table:
        //   Java source line #200	-> byte code offset #0
        //   Java source line #201	-> byte code offset #2
        //   Java source line #204	-> byte code offset #10
        //   Java source line #206	-> byte code offset #22
        //   Java source line #207	-> byte code offset #26
        //   Java source line #209	-> byte code offset #34
        //   Java source line #211	-> byte code offset #38
        //   Java source line #213	-> byte code offset #42
        //   Java source line #214	-> byte code offset #58
        //   Java source line #217	-> byte code offset #61
        //   Java source line #220	-> byte code offset #74
        //   Java source line #222	-> byte code offset #86
        //   Java source line #223	-> byte code offset #99
        //   Java source line #224	-> byte code offset #111
        //   Java source line #227	-> byte code offset #142
        //   Java source line #229	-> byte code offset #165
        //   Java source line #230	-> byte code offset #173
        //   Java source line #231	-> byte code offset #196
        //   Java source line #232	-> byte code offset #199
        //   Java source line #234	-> byte code offset #200
        //   Java source line #238	-> byte code offset #204
        //   Java source line #237	-> byte code offset #240
        //   Java source line #238	-> byte code offset #242
        //   Java source line #246	-> byte code offset #275
        //   Java source line #238	-> byte code offset #278
        //   Java source line #247	-> byte code offset #311
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	312	0	this	1
        //   1	101	1	token	ClientToken
        //   199	2	1	exception	Exception
        //   9	66	2	gson	Gson
        //   21	164	3	uuid	UUID
        //   36	119	4	response	String
        //   97	89	5	client	CoreClient
        //   240	36	6	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   0	196	199	java/lang/Exception
        //   0	204	240	finally
      }
    });
  }
  
  private boolean LoadClient(final CoreClient client, final UUID uuid, String ipAddress)
  {
    TimingManager.start(client.GetPlayerName() + " LoadClient Total.");
    long timeStart = System.currentTimeMillis();
    
    _clientLoginLock.put(client.GetPlayerName(), new Object());
    ClientToken token = null;
    Gson gson = new Gson();
    
    runAsync(new Runnable()
    {
      public void run()
      {
        client.setAccountId(CoreClientManager.this._repository.login(CoreClientManager.this._loginProcessors, uuid.toString(), client.GetPlayerName()));
        CoreClientManager._clientLoginLock.remove(client.GetPlayerName());
      }
    });
    TimingManager.start(client.GetPlayerName() + " GetClient.");
    String response = this._repository.GetClient(client.GetPlayerName(), uuid, ipAddress);
    TimingManager.stop(client.GetPlayerName() + " GetClient.");
    
    token = (ClientToken)gson.fromJson(response, ClientToken.class);
    
    client.SetRank(Rank.valueOf(token.Rank));
    
    Bukkit.getServer().getPluginManager().callEvent(new ClientWebResponseEvent(response, uuid));
    while ((_clientLoginLock.containsKey(client.GetPlayerName())) && (System.currentTimeMillis() - timeStart < 15000L)) {
      try
      {
        Thread.sleep(2L);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
    if (_clientLoginLock.containsKey(client.GetPlayerName())) {
      System.out.println("MYSQL TOO LONG TO LOGIN....");
    }
    TimingManager.stop(client.GetPlayerName() + " LoadClient Total.");
    
    System.out.println(client.GetPlayerName() + "'s account id = " + client.getAccountId());
    if (client.getAccountId() > 0) {
      this._accountCacheRepository.addElement(new AccountCache(uuid, client.getAccountId()));
    }
    return !_clientLoginLock.containsKey(client.GetPlayerName());
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void Login(PlayerLoginEvent event)
  {
    synchronized (this._clientLock)
    {
      if (!this._clientList.containsKey(event.getPlayer().getName())) {
        this._clientList.put(event.getPlayer().getName(), new CoreClient(event.getPlayer().getName()));
      }
    }
    CoreClient client = Get(event.getPlayer().getName());
    if ((client == null) || (client.GetRank() == null))
    {
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "There was an error logging you in.  Please reconncet.");
      return;
    }
    client.SetPlayer(event.getPlayer());
    if (Bukkit.getOnlinePlayers().size() >= Bukkit.getServer().getMaxPlayers())
    {
      if (client.GetRank().Has(event.getPlayer(), Rank.ULTRA, false))
      {
        event.allow();
        event.setResult(PlayerLoginEvent.Result.ALLOWED);
        return;
      }
      event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "This server is full and no longer accepts players.");
    }
  }
  
  @EventHandler
  public void Kick(PlayerKickEvent event)
  {
    if (event.getReason().contains("You logged in from another location")) {
      this._duplicateLoginGlitchPreventionList.add(event.getPlayer().getName());
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void Quit(PlayerQuitEvent event)
  {
    if (!this._duplicateLoginGlitchPreventionList.contains(event.getPlayer().getName()))
    {
      Del(event.getPlayer().getName());
      this._duplicateLoginGlitchPreventionList.remove(event.getPlayer().getName());
    }
  }
  
  public void SaveRank(final String name, UUID uuid, Rank rank, boolean perm)
  {
    this._repository.saveRank(new Callback()
    {
      public void run(Rank newRank)
      {
        if (CoreClientManager.this._plugin.getServer().getPlayer(name) != null)
        {
          CoreClient client = CoreClientManager.this.Get(name);
          
          client.SetRank(newRank);
        }
      }
    }, name, uuid, rank, perm);
  }
  
  public void checkPlayerNameExact(final Callback<Boolean> callback, final String playerName)
  {
    this._repository.matchPlayerName(new Callback()
    {
      public void run(List<String> matches)
      {
        for (String match : matches) {
          if (match.equalsIgnoreCase(playerName)) {
            callback.run(Boolean.valueOf(true));
          }
        }
        callback.run(Boolean.valueOf(false));
      }
    }, playerName);
  }
  
  public void checkPlayerName(final Player caller, final String playerName, final Callback<String> callback)
  {
    this._repository.matchPlayerName(new Callback()
    {
      public void run(List<String> matches)
      {
        String tempName = null;
        for (String match : matches) {
          if (match.equalsIgnoreCase(playerName))
          {
            tempName = match;
            break;
          }
        }
        final String matchedName = tempName;
        if (matchedName != null) {
          for (Object matchIterator = matches.iterator(); ((Iterator)matchIterator).hasNext();) {
            if (!((String)((Iterator)matchIterator).next()).equalsIgnoreCase(playerName)) {
              ((Iterator)matchIterator).remove();
            }
          }
        }
        UtilPlayer.searchOffline(matches, new Callback()
        {
          public void run(String target)
          {
            if (target == null)
            {
              this.val$callback.run(matchedName);
              return;
            }
            this.val$callback.run(matchedName);
          }
        }, caller, playerName, true);
      }
    }, playerName);
  }
  
  public UUID loadUUIDFromDB(String name)
  {
    return this._repository.getClientUUID(name);
  }
  
  @EventHandler
  public void cleanGlitchedClients(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOW) {
      return;
    }
    synchronized (this._clientLock)
    {
      for (Iterator<Map.Entry<String, CoreClient>> clientIterator = this._clientList.entrySet().iterator(); clientIterator.hasNext();)
      {
        Player clientPlayer = ((CoreClient)((Map.Entry)clientIterator.next()).getValue()).GetPlayer();
        if ((clientPlayer != null) && (!clientPlayer.isOnline()))
        {
          clientIterator.remove();
          if (clientPlayer != null) {
            this._plugin.getServer().getPluginManager().callEvent(new ClientUnloadEvent(clientPlayer.getName()));
          }
        }
      }
    }
  }
  
  @EventHandler
  public void debug(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOWER) {}
  }
  
  public void addStoredProcedureLoginProcessor(ILoginProcessor processor)
  {
    this._loginProcessors.put(processor.getName(), processor);
  }
  
  public boolean hasRank(Player player, Rank rank)
  {
    CoreClient client = Get(player);
    if (client == null) {
      return false;
    }
    return client.GetRank().Has(rank);
  }
  
  public int getCachedClientAccountId(UUID uuid)
  {
    return ((AccountCache)this._accountCacheRepository.getElement(uuid.toString())).getId();
  }
}

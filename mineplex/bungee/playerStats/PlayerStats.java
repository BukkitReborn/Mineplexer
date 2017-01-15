package mineplex.bungee.playerStats;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import mineplex.playerCache.PlayerCache;
import mineplex.playerCache.PlayerInfo;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.event.EventHandler;

public class PlayerStats
  implements Listener, Runnable
{
  private Plugin _plugin;
  private PlayerStatsRepository _repository;
  private PlayerCache _playerCache = new PlayerCache();
  private HashSet<UUID> _retrievingPlayerInfo = new HashSet();
  
  public PlayerStats(Plugin plugin)
  {
    this._plugin = plugin;
    
    this._plugin.getProxy().getScheduler().schedule(this._plugin, this, 5L, 5L, TimeUnit.MINUTES);
    this._plugin.getProxy().getPluginManager().registerListener(this._plugin, this);
    
    this._repository = new PlayerStatsRepository();
    this._repository.initialize();
  }
  
  @EventHandler
  public void playerConnect(final PostLoginEvent event)
  {
    this._plugin.getProxy().getScheduler().runAsync(this._plugin, new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 16	mineplex/bungee/playerStats/PlayerStats$1:val$event	Lnet/md_5/bungee/api/event/PostLoginEvent;
        //   4: invokevirtual 26	net/md_5/bungee/api/event/PostLoginEvent:getPlayer	()Lnet/md_5/bungee/api/connection/ProxiedPlayer;
        //   7: invokeinterface 32 1 0
        //   12: invokeinterface 38 1 0
        //   17: invokevirtual 44	java/net/InetSocketAddress:getAddress	()Ljava/net/InetAddress;
        //   20: invokevirtual 49	java/net/InetAddress:getHostAddress	()Ljava/lang/String;
        //   23: astore_1
        //   24: aload_0
        //   25: getfield 16	mineplex/bungee/playerStats/PlayerStats$1:val$event	Lnet/md_5/bungee/api/event/PostLoginEvent;
        //   28: invokevirtual 26	net/md_5/bungee/api/event/PostLoginEvent:getPlayer	()Lnet/md_5/bungee/api/connection/ProxiedPlayer;
        //   31: invokeinterface 55 1 0
        //   36: astore_2
        //   37: aload_0
        //   38: getfield 16	mineplex/bungee/playerStats/PlayerStats$1:val$event	Lnet/md_5/bungee/api/event/PostLoginEvent;
        //   41: invokevirtual 26	net/md_5/bungee/api/event/PostLoginEvent:getPlayer	()Lnet/md_5/bungee/api/connection/ProxiedPlayer;
        //   44: invokeinterface 59 1 0
        //   49: astore_3
        //   50: aload_0
        //   51: getfield 16	mineplex/bungee/playerStats/PlayerStats$1:val$event	Lnet/md_5/bungee/api/event/PostLoginEvent;
        //   54: invokevirtual 26	net/md_5/bungee/api/event/PostLoginEvent:getPlayer	()Lnet/md_5/bungee/api/connection/ProxiedPlayer;
        //   57: invokeinterface 32 1 0
        //   62: invokeinterface 62 1 0
        //   67: istore 4
        //   69: aconst_null
        //   70: astore 5
        //   72: aload_0
        //   73: getfield 14	mineplex/bungee/playerStats/PlayerStats$1:this$0	Lmineplex/bungee/playerStats/PlayerStats;
        //   76: invokestatic 66	mineplex/bungee/playerStats/PlayerStats:access$1	(Lmineplex/bungee/playerStats/PlayerStats;)Lmineplex/bungee/playerStats/PlayerStatsRepository;
        //   79: aload_1
        //   80: invokevirtual 72	mineplex/bungee/playerStats/PlayerStatsRepository:getIp	(Ljava/lang/String;)Lmineplex/bungee/playerStats/data/IpInfo;
        //   83: astore 6
        //   85: iconst_0
        //   86: istore 7
        //   88: aload_0
        //   89: getfield 14	mineplex/bungee/playerStats/PlayerStats$1:this$0	Lmineplex/bungee/playerStats/PlayerStats;
        //   92: invokestatic 78	mineplex/bungee/playerStats/PlayerStats:access$2	(Lmineplex/bungee/playerStats/PlayerStats;)Lmineplex/playerCache/PlayerCache;
        //   95: aload_2
        //   96: invokevirtual 82	mineplex/playerCache/PlayerCache:getPlayer	(Ljava/util/UUID;)Lmineplex/playerCache/PlayerInfo;
        //   99: astore 5
        //   101: aload 5
        //   103: ifnonnull +18 -> 121
        //   106: iconst_1
        //   107: istore 7
        //   109: aload_0
        //   110: getfield 14	mineplex/bungee/playerStats/PlayerStats$1:this$0	Lmineplex/bungee/playerStats/PlayerStats;
        //   113: invokestatic 87	mineplex/bungee/playerStats/PlayerStats:access$0	(Lmineplex/bungee/playerStats/PlayerStats;)Ljava/util/HashSet;
        //   116: aload_2
        //   117: invokevirtual 91	java/util/HashSet:add	(Ljava/lang/Object;)Z
        //   120: pop
        //   121: iload 7
        //   123: ifne +34 -> 157
        //   126: aload 5
        //   128: invokevirtual 97	mineplex/playerCache/PlayerInfo:getVersion	()I
        //   131: iload 4
        //   133: if_icmpeq +9 -> 142
        //   136: iconst_1
        //   137: istore 7
        //   139: goto +18 -> 157
        //   142: aload 5
        //   144: invokevirtual 100	mineplex/playerCache/PlayerInfo:getName	()Ljava/lang/String;
        //   147: aload_3
        //   148: invokevirtual 101	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
        //   151: ifne +6 -> 157
        //   154: iconst_1
        //   155: istore 7
        //   157: iload 7
        //   159: ifeq +63 -> 222
        //   162: aload_0
        //   163: getfield 14	mineplex/bungee/playerStats/PlayerStats$1:this$0	Lmineplex/bungee/playerStats/PlayerStats;
        //   166: invokestatic 66	mineplex/bungee/playerStats/PlayerStats:access$1	(Lmineplex/bungee/playerStats/PlayerStats;)Lmineplex/bungee/playerStats/PlayerStatsRepository;
        //   169: aload_2
        //   170: aload_3
        //   171: iload 4
        //   173: invokevirtual 107	mineplex/bungee/playerStats/PlayerStatsRepository:getPlayer	(Ljava/util/UUID;Ljava/lang/String;I)Lmineplex/playerCache/PlayerInfo;
        //   176: astore 8
        //   178: aload 5
        //   180: ifnull +26 -> 206
        //   183: aload 5
        //   185: aload 8
        //   187: invokevirtual 100	mineplex/playerCache/PlayerInfo:getName	()Ljava/lang/String;
        //   190: invokevirtual 110	mineplex/playerCache/PlayerInfo:setName	(Ljava/lang/String;)V
        //   193: aload 5
        //   195: aload 8
        //   197: invokevirtual 97	mineplex/playerCache/PlayerInfo:getVersion	()I
        //   200: invokevirtual 114	mineplex/playerCache/PlayerInfo:setVersion	(I)V
        //   203: goto +7 -> 210
        //   206: aload 8
        //   208: astore 5
        //   210: aload_0
        //   211: getfield 14	mineplex/bungee/playerStats/PlayerStats$1:this$0	Lmineplex/bungee/playerStats/PlayerStats;
        //   214: invokestatic 78	mineplex/bungee/playerStats/PlayerStats:access$2	(Lmineplex/bungee/playerStats/PlayerStats;)Lmineplex/playerCache/PlayerCache;
        //   217: aload 5
        //   219: invokevirtual 118	mineplex/playerCache/PlayerCache:addPlayer	(Lmineplex/playerCache/PlayerInfo;)V
        //   222: aload 5
        //   224: aload_0
        //   225: getfield 14	mineplex/bungee/playerStats/PlayerStats$1:this$0	Lmineplex/bungee/playerStats/PlayerStats;
        //   228: invokestatic 66	mineplex/bungee/playerStats/PlayerStats:access$1	(Lmineplex/bungee/playerStats/PlayerStats;)Lmineplex/bungee/playerStats/PlayerStatsRepository;
        //   231: aload 5
        //   233: invokevirtual 122	mineplex/playerCache/PlayerInfo:getId	()I
        //   236: aload 6
        //   238: getfield 125	mineplex/bungee/playerStats/data/IpInfo:id	I
        //   241: invokevirtual 131	mineplex/bungee/playerStats/PlayerStatsRepository:updatePlayerStats	(II)I
        //   244: invokevirtual 135	mineplex/playerCache/PlayerInfo:setSessionId	(I)V
        //   247: goto +20 -> 267
        //   250: astore 9
        //   252: aload_0
        //   253: getfield 14	mineplex/bungee/playerStats/PlayerStats$1:this$0	Lmineplex/bungee/playerStats/PlayerStats;
        //   256: invokestatic 87	mineplex/bungee/playerStats/PlayerStats:access$0	(Lmineplex/bungee/playerStats/PlayerStats;)Ljava/util/HashSet;
        //   259: aload_2
        //   260: invokevirtual 138	java/util/HashSet:remove	(Ljava/lang/Object;)Z
        //   263: pop
        //   264: aload 9
        //   266: athrow
        //   267: aload_0
        //   268: getfield 14	mineplex/bungee/playerStats/PlayerStats$1:this$0	Lmineplex/bungee/playerStats/PlayerStats;
        //   271: invokestatic 87	mineplex/bungee/playerStats/PlayerStats:access$0	(Lmineplex/bungee/playerStats/PlayerStats;)Ljava/util/HashSet;
        //   274: aload_2
        //   275: invokevirtual 138	java/util/HashSet:remove	(Ljava/lang/Object;)Z
        //   278: pop
        //   279: return
        // Line number table:
        //   Java source line #43	-> byte code offset #0
        //   Java source line #44	-> byte code offset #24
        //   Java source line #45	-> byte code offset #37
        //   Java source line #46	-> byte code offset #50
        //   Java source line #50	-> byte code offset #69
        //   Java source line #51	-> byte code offset #72
        //   Java source line #53	-> byte code offset #85
        //   Java source line #55	-> byte code offset #88
        //   Java source line #57	-> byte code offset #101
        //   Java source line #59	-> byte code offset #106
        //   Java source line #60	-> byte code offset #109
        //   Java source line #63	-> byte code offset #121
        //   Java source line #65	-> byte code offset #126
        //   Java source line #66	-> byte code offset #136
        //   Java source line #67	-> byte code offset #142
        //   Java source line #68	-> byte code offset #154
        //   Java source line #71	-> byte code offset #157
        //   Java source line #74	-> byte code offset #162
        //   Java source line #76	-> byte code offset #178
        //   Java source line #78	-> byte code offset #183
        //   Java source line #79	-> byte code offset #193
        //   Java source line #80	-> byte code offset #203
        //   Java source line #82	-> byte code offset #206
        //   Java source line #84	-> byte code offset #210
        //   Java source line #87	-> byte code offset #222
        //   Java source line #88	-> byte code offset #247
        //   Java source line #90	-> byte code offset #250
        //   Java source line #91	-> byte code offset #252
        //   Java source line #92	-> byte code offset #264
        //   Java source line #91	-> byte code offset #267
        //   Java source line #93	-> byte code offset #279
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	280	0	this	1
        //   23	57	1	address	String
        //   36	239	2	uuid	UUID
        //   49	122	3	name	String
        //   67	105	4	version	int
        //   70	162	5	playerInfo	PlayerInfo
        //   83	154	6	ipInfo	mineplex.bungee.playerStats.data.IpInfo
        //   86	72	7	addOrUpdatePlayer	boolean
        //   176	31	8	updatedPlayerInfo	PlayerInfo
        //   250	15	9	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   69	250	250	finally
      }
    });
  }
  
  @EventHandler
  public void playerDisconnect(final PlayerDisconnectEvent event)
  {
    this._plugin.getProxy().getScheduler().runAsync(this._plugin, new Runnable()
    {
      public void run()
      {
        UUID uuid = event.getPlayer().getUniqueId();
        
        PlayerInfo playerInfo = null;
        
        playerInfo = PlayerStats.this._playerCache.getPlayer(uuid);
        
        int timeout = 5;
        while ((playerInfo == null) && (PlayerStats.this._retrievingPlayerInfo.contains(uuid)) && (timeout <= 5))
        {
          playerInfo = PlayerStats.this._playerCache.getPlayer(uuid);
          if (playerInfo != null) {
            break;
          }
          System.out.println("ERROR - Player disconnecting and isn't in cache... sleeping");
          try
          {
            Thread.sleep(500L);
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
          timeout++;
        }
        PlayerStats.this._repository.updatePlayerSession(playerInfo.getSessionId());
      }
    });
  }
  
  public void run()
  {
    this._playerCache.clean();
  }
}

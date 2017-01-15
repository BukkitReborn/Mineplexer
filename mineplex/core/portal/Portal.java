package mineplex.core.portal;

import java.util.HashSet;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilTabTitle;
import mineplex.core.portal.Commands.SendCommand;
import mineplex.core.portal.Commands.ServerCommand;
import mineplex.serverdata.Region;
import mineplex.serverdata.commands.ServerCommandManager;
import mineplex.serverdata.commands.ServerTransfer;
import mineplex.serverdata.commands.TransferCommand;
import mineplex.serverdata.data.MinecraftServer;
import mineplex.serverdata.servers.ServerManager;
import mineplex.serverdata.servers.ServerRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;

public class Portal
  extends MiniPlugin
{
  private static Portal instance;
  private ServerRepository _repository;
  private CoreClientManager _clientManager;
  private HashSet<String> _connectingPlayers = new HashSet();
  private Region _region;
  private String _serverName;
  private String _mineplexName;
  private String _website;
  
  public static Portal getInstance()
  {
    return instance;
  }
  
  public Portal(JavaPlugin plugin, CoreClientManager clientManager, String serverName, String mineplexName, String website)
  {
    super("Portal", plugin);
    instance = this;
    this._clientManager = clientManager;
    this._region = (plugin.getConfig().getBoolean("serverstatus.us") ? Region.US : Region.EU);
    this._serverName = serverName;
    this._repository = ServerManager.getServerRepository(this._region);
    
    this._mineplexName = mineplexName;
    this._website = website;
    
    Bukkit.getMessenger().registerOutgoingPluginChannel(getPlugin(), "BungeeCord");
    ServerCommandManager.getInstance().registerCommandType("TransferCommand", TransferCommand.class, new TransferHandler());
  }
  
  @EventHandler
  public void join(PlayerJoinEvent event)
  {
    Player player = event.getPlayer();
    String serverName = this._plugin.getConfig().getString("serverstatus.name");
    UtilTabTitle.setHeaderAndFooter(player, C.Bold + this._mineplexName + " Network   " + C.cGreen + serverName, "Visit " + C.cGreen + this._website + ChatColor.RESET + " for News, Forums and Shop");
  }
  
  public void sendAllPlayers(String serverName)
  {
    for (Player player : ) {
      sendPlayerToServer(player, serverName);
    }
  }
  
  public void sendPlayerToServer(Player player, String serverName)
  {
    sendPlayerToServer(player, serverName, true);
  }
  
  public void sendPlayerToServer(final Player player, final String serverName, boolean callEvent)
  {
    if (this._connectingPlayers.contains(player.getName())) {
      return;
    }
    if (callEvent)
    {
      ServerTransferEvent event = new ServerTransferEvent(player, serverName);
      Bukkit.getPluginManager().callEvent(event);
    }
    boolean override = serverName.equalsIgnoreCase("Lobby");
    final Rank playerRank = this._clientManager.Get(player).GetRank();
    if (override) {
      sendPlayer(player, serverName);
    } else {
      runAsync(new Runnable()
      {
        public void run()
        {
          final MinecraftServer server = Portal.this._repository.getServerStatus(serverName);
          if (server == null) {
            return;
          }
          Bukkit.getServer().getScheduler().runTask(Portal.this._plugin, new Runnable()
          {
            public void run()
            {
              if ((server.getPlayerCount() < server.getMaxPlayerCount()) || (this.val$playerRank.Has(Rank.ULTRA))) {
                Portal.this.sendPlayer(this.val$player, this.val$serverName);
              } else {
                UtilPlayer.message(this.val$player, F.main(Portal.this.getName(), C.cGold + this.val$serverName + C.cRed + " is full!"));
              }
            }
          });
        }
      });
    }
  }
  
  public static void transferPlayer(String playerName, String serverName)
  {
    ServerTransfer serverTransfer = new ServerTransfer(playerName, serverName);
    TransferCommand transferCommand = new TransferCommand(serverTransfer);
    transferCommand.publish();
  }
  
  public void doesServerExist(final String serverName, final Callback<Boolean> callback)
  {
    if (callback == null) {
      return;
    }
    Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        final boolean serverExists = ServerManager.getServerRepository(Portal.this._region).serverExists(serverName);
        Bukkit.getScheduler().runTask(Portal.this.getPlugin(), new Runnable()
        {
          public void run()
          {
            this.val$callback.run(Boolean.valueOf(serverExists));
          }
        });
      }
    });
  }
  
  public void addCommands()
  {
    addCommand(new ServerCommand(this));
    addCommand(new SendCommand(this));
  }
  
  public void sendToHub(Player player, String message)
  {
    if (message != null)
    {
      UtilPlayer.message(player, "  ");
      UtilPlayer.message(player, C.cGold + C.Bold + message);
      UtilPlayer.message(player, "  ");
    }
    player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 10.0F, 1.0F);
    sendPlayerToServer(player, "Lobby");
  }
  
  /* Error */
  private void sendPlayer(final Player player, String serverName)
  {
    // Byte code:
    //   0: new 361	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 363	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_3
    //   8: new 364	java/io/DataOutputStream
    //   11: dup
    //   12: aload_3
    //   13: invokespecial 366	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   16: astore 4
    //   18: aload 4
    //   20: ldc_w 369
    //   23: invokevirtual 371	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   26: aload 4
    //   28: aload_2
    //   29: invokevirtual 371	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   32: goto +43 -> 75
    //   35: astore 5
    //   37: aload 4
    //   39: invokevirtual 374	java/io/DataOutputStream:close	()V
    //   42: goto +48 -> 90
    //   45: astore 7
    //   47: aload 7
    //   49: invokevirtual 377	java/io/IOException:printStackTrace	()V
    //   52: goto +38 -> 90
    //   55: astore 6
    //   57: aload 4
    //   59: invokevirtual 374	java/io/DataOutputStream:close	()V
    //   62: goto +10 -> 72
    //   65: astore 7
    //   67: aload 7
    //   69: invokevirtual 377	java/io/IOException:printStackTrace	()V
    //   72: aload 6
    //   74: athrow
    //   75: aload 4
    //   77: invokevirtual 374	java/io/DataOutputStream:close	()V
    //   80: goto +10 -> 90
    //   83: astore 7
    //   85: aload 7
    //   87: invokevirtual 377	java/io/IOException:printStackTrace	()V
    //   90: aload_1
    //   91: aload_0
    //   92: invokevirtual 88	mineplex/core/portal/Portal:getPlugin	()Lorg/bukkit/plugin/java/JavaPlugin;
    //   95: ldc 92
    //   97: aload_3
    //   98: invokevirtual 382	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   101: invokeinterface 386 4 0
    //   106: aload_0
    //   107: getfield 40	mineplex/core/portal/Portal:_connectingPlayers	Ljava/util/HashSet;
    //   110: aload_1
    //   111: invokeinterface 227 1 0
    //   116: invokevirtual 390	java/util/HashSet:add	(Ljava/lang/Object;)Z
    //   119: pop
    //   120: aload_0
    //   121: invokevirtual 393	mineplex/core/portal/Portal:getScheduler	()Lorg/bukkit/scheduler/BukkitScheduler;
    //   124: aload_0
    //   125: invokevirtual 88	mineplex/core/portal/Portal:getPlugin	()Lorg/bukkit/plugin/java/JavaPlugin;
    //   128: new 394	mineplex/core/portal/Portal$3
    //   131: dup
    //   132: aload_0
    //   133: aload_1
    //   134: invokespecial 396	mineplex/core/portal/Portal$3:<init>	(Lmineplex/core/portal/Portal;Lorg/bukkit/entity/Player;)V
    //   137: ldc2_w 399
    //   140: invokeinterface 401 5 0
    //   145: pop
    //   146: aload_1
    //   147: aload_0
    //   148: invokevirtual 405	mineplex/core/portal/Portal:getName	()Ljava/lang/String;
    //   151: new 147	java/lang/StringBuilder
    //   154: dup
    //   155: ldc_w 406
    //   158: invokespecial 158	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   161: getstatic 343	mineplex/core/common/util/C:cGold	Ljava/lang/String;
    //   164: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: aload_0
    //   168: getfield 68	mineplex/core/portal/Portal:_serverName	Ljava/lang/String;
    //   171: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: getstatic 408	mineplex/core/common/util/C:cGray	Ljava/lang/String;
    //   177: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   180: ldc_w 411
    //   183: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: getstatic 343	mineplex/core/common/util/C:cGold	Ljava/lang/String;
    //   189: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: aload_2
    //   193: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   196: invokevirtual 170	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   199: invokestatic 413	mineplex/core/common/util/F:main	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   202: invokestatic 337	mineplex/core/common/util/UtilPlayer:message	(Lorg/bukkit/entity/Entity;Ljava/lang/String;)V
    //   205: return
    // Line number table:
    //   Java source line #197	-> byte code offset #0
    //   Java source line #198	-> byte code offset #8
    //   Java source line #200	-> byte code offset #18
    //   Java source line #201	-> byte code offset #26
    //   Java source line #202	-> byte code offset #32
    //   Java source line #203	-> byte code offset #35
    //   Java source line #206	-> byte code offset #37
    //   Java source line #207	-> byte code offset #42
    //   Java source line #208	-> byte code offset #45
    //   Java source line #209	-> byte code offset #47
    //   Java source line #204	-> byte code offset #55
    //   Java source line #206	-> byte code offset #57
    //   Java source line #207	-> byte code offset #62
    //   Java source line #208	-> byte code offset #65
    //   Java source line #209	-> byte code offset #67
    //   Java source line #211	-> byte code offset #72
    //   Java source line #206	-> byte code offset #75
    //   Java source line #207	-> byte code offset #80
    //   Java source line #208	-> byte code offset #83
    //   Java source line #209	-> byte code offset #85
    //   Java source line #212	-> byte code offset #90
    //   Java source line #213	-> byte code offset #106
    //   Java source line #214	-> byte code offset #120
    //   Java source line #220	-> byte code offset #137
    //   Java source line #214	-> byte code offset #140
    //   Java source line #221	-> byte code offset #146
    //   Java source line #222	-> byte code offset #205
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	206	0	this	Portal
    //   0	206	1	player	Player
    //   0	206	2	serverName	String
    //   7	91	3	b	java.io.ByteArrayOutputStream
    //   16	60	4	out	java.io.DataOutputStream
    //   35	1	5	localIOException1	java.io.IOException
    //   55	18	6	localObject	Object
    //   45	3	7	e	java.io.IOException
    //   65	3	7	e	java.io.IOException
    //   83	3	7	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   18	32	35	java/io/IOException
    //   37	42	45	java/io/IOException
    //   18	37	55	finally
    //   57	62	65	java/io/IOException
    //   75	80	83	java/io/IOException
  }
}

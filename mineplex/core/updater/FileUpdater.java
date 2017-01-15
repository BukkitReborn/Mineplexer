package mineplex.core.updater;

import java.io.File;
import java.io.FilenameFilter;
import mineplex.core.MiniPlugin;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.portal.Portal;
import mineplex.core.updater.event.RestartServerEvent;
import mineplex.core.updater.event.UpdateEvent;
import mineplex.serverdata.Region;
import mineplex.serverdata.commands.RestartCommand;
import mineplex.serverdata.commands.ServerCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class FileUpdater
  extends MiniPlugin
{
  private Portal _portal;
  private NautHashMap<String, String> _jarMd5Map = new NautHashMap();
  private String _serverName;
  private Region _region;
  private boolean _needUpdate;
  private boolean _enabled = true;
  
  public FileUpdater(JavaPlugin plugin, Portal portal, String serverName, Region region)
  {
    super("File Updater", plugin);
    
    this._portal = portal;
    this._serverName = serverName;
    this._region = region;
    
    GetPluginMd5s();
    if (new File("IgnoreUpdates.dat").exists()) {
      this._enabled = false;
    }
    ServerCommandManager.getInstance().registerCommandType("RestartCommand", RestartCommand.class, new RestartHandler(plugin, this._serverName, this._region));
  }
  
  public void addCommands()
  {
    addCommand(new RestartServerCommand(this));
  }
  
  @EventHandler
  public void tryToRestart(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOWER) {
      return;
    }
    if ((!this._needUpdate) || (!this._enabled)) {
      return;
    }
    RestartServerEvent restartEvent = new RestartServerEvent();
    
    getPluginManager().callEvent(restartEvent);
    if (!restartEvent.isCancelled())
    {
      for (Player player : Bukkit.getOnlinePlayers()) {
        player.sendMessage(F.main("Updater", C.cGold + this._serverName + C.cGray + " is restarting for an update."));
      }
      getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
      {
        public void run()
        {
          FileUpdater.this._portal.sendAllPlayers("Lobby");
        }
      }, 60L);
      
      getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
      {
        public void run()
        {
          FileUpdater.this.getPlugin().getServer().shutdown();
        }
      }, 100L);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void reflectMotd(ServerListPingEvent event)
  {
    if (this._needUpdate) {
      event.setMotd("Restarting soon");
    }
  }
  
  /* Error */
  @EventHandler
  public void CheckForNewFiles(UpdateEvent event)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 106	mineplex/core/updater/event/UpdateEvent:getType	()Lmineplex/core/updater/UpdateType;
    //   4: getstatic 245	mineplex/core/updater/UpdateType:MIN_01	Lmineplex/core/updater/UpdateType;
    //   7: if_acmpeq +4 -> 11
    //   10: return
    //   11: aload_0
    //   12: getfield 118	mineplex/core/updater/FileUpdater:_needUpdate	Z
    //   15: ifne +10 -> 25
    //   18: aload_0
    //   19: getfield 33	mineplex/core/updater/FileUpdater:_enabled	Z
    //   22: ifne +4 -> 26
    //   25: return
    //   26: ldc -8
    //   28: invokestatic 250	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   31: ldc_w 256
    //   34: invokevirtual 258	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   37: istore_2
    //   38: new 44	java/io/File
    //   41: dup
    //   42: new 158	java/lang/StringBuilder
    //   45: dup
    //   46: iload_2
    //   47: ifeq +9 -> 56
    //   50: ldc_w 262
    //   53: goto +37 -> 90
    //   56: new 158	java/lang/StringBuilder
    //   59: dup
    //   60: getstatic 264	java/io/File:separator	Ljava/lang/String;
    //   63: invokestatic 165	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   66: invokespecial 169	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   69: ldc_w 267
    //   72: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: getstatic 264	java/io/File:separator	Ljava/lang/String;
    //   78: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc_w 269
    //   84: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: invokevirtual 179	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   90: invokestatic 165	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   93: invokespecial 169	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   96: getstatic 264	java/io/File:separator	Ljava/lang/String;
    //   99: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: ldc_w 271
    //   105: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: invokevirtual 179	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   111: invokespecial 48	java/io/File:<init>	(Ljava/lang/String;)V
    //   114: astore_3
    //   115: aload_3
    //   116: invokevirtual 273	java/io/File:mkdirs	()Z
    //   119: pop
    //   120: new 276	mineplex/core/updater/FileUpdater$3
    //   123: dup
    //   124: aload_0
    //   125: invokespecial 278	mineplex/core/updater/FileUpdater$3:<init>	(Lmineplex/core/updater/FileUpdater;)V
    //   128: astore 4
    //   130: aload_3
    //   131: aload 4
    //   133: invokevirtual 279	java/io/File:listFiles	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   136: dup
    //   137: astore 8
    //   139: arraylength
    //   140: istore 7
    //   142: iconst_0
    //   143: istore 6
    //   145: goto +251 -> 396
    //   148: aload 8
    //   150: iload 6
    //   152: aaload
    //   153: astore 5
    //   155: aconst_null
    //   156: astore 9
    //   158: aload_0
    //   159: getfield 31	mineplex/core/updater/FileUpdater:_jarMd5Map	Lmineplex/core/common/util/NautHashMap;
    //   162: aload 5
    //   164: invokevirtual 283	java/io/File:getName	()Ljava/lang/String;
    //   167: invokevirtual 286	mineplex/core/common/util/NautHashMap:containsKey	(Ljava/lang/Object;)Z
    //   170: ifeq +203 -> 373
    //   173: new 290	java/io/FileInputStream
    //   176: dup
    //   177: aload 5
    //   179: invokespecial 292	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   182: astore 9
    //   184: aload 9
    //   186: invokestatic 295	org/apache/commons/codec/digest/DigestUtils:md5Hex	(Ljava/io/InputStream;)Ljava/lang/String;
    //   189: astore 10
    //   191: aload 10
    //   193: aload_0
    //   194: getfield 31	mineplex/core/updater/FileUpdater:_jarMd5Map	Lmineplex/core/common/util/NautHashMap;
    //   197: aload 5
    //   199: invokevirtual 283	java/io/File:getName	()Ljava/lang/String;
    //   202: invokevirtual 301	mineplex/core/common/util/NautHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   205: invokevirtual 305	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   208: ifne +165 -> 373
    //   211: getstatic 308	java/lang/System:out	Ljava/io/PrintStream;
    //   214: new 158	java/lang/StringBuilder
    //   217: dup
    //   218: aload 5
    //   220: invokevirtual 283	java/io/File:getName	()Ljava/lang/String;
    //   223: invokestatic 165	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   226: invokespecial 169	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   229: ldc_w 312
    //   232: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   235: aload_0
    //   236: getfield 31	mineplex/core/updater/FileUpdater:_jarMd5Map	Lmineplex/core/common/util/NautHashMap;
    //   239: aload 5
    //   241: invokevirtual 283	java/io/File:getName	()Ljava/lang/String;
    //   244: invokevirtual 301	mineplex/core/common/util/NautHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   247: checkcast 88	java/lang/String
    //   250: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: invokevirtual 179	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   256: invokevirtual 314	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   259: getstatic 308	java/lang/System:out	Ljava/io/PrintStream;
    //   262: new 158	java/lang/StringBuilder
    //   265: dup
    //   266: aload 5
    //   268: invokevirtual 283	java/io/File:getName	()Ljava/lang/String;
    //   271: invokestatic 165	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   274: invokespecial 169	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   277: ldc_w 319
    //   280: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: aload 10
    //   285: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   288: invokevirtual 179	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   291: invokevirtual 314	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   294: aload_0
    //   295: iconst_1
    //   296: putfield 118	mineplex/core/updater/FileUpdater:_needUpdate	Z
    //   299: goto +74 -> 373
    //   302: astore 10
    //   304: getstatic 308	java/lang/System:out	Ljava/io/PrintStream;
    //   307: aload_0
    //   308: invokevirtual 321	mineplex/core/updater/FileUpdater:getName	()Ljava/lang/String;
    //   311: ldc_w 322
    //   314: invokestatic 183	mineplex/core/common/util/F:main	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   317: invokevirtual 314	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   320: aload 10
    //   322: invokevirtual 324	java/lang/Exception:printStackTrace	()V
    //   325: aload 9
    //   327: ifnull +66 -> 393
    //   330: aload 9
    //   332: invokevirtual 329	java/io/FileInputStream:close	()V
    //   335: goto +58 -> 393
    //   338: astore 12
    //   340: aload 12
    //   342: invokevirtual 332	java/io/IOException:printStackTrace	()V
    //   345: goto +48 -> 393
    //   348: astore 11
    //   350: aload 9
    //   352: ifnull +18 -> 370
    //   355: aload 9
    //   357: invokevirtual 329	java/io/FileInputStream:close	()V
    //   360: goto +10 -> 370
    //   363: astore 12
    //   365: aload 12
    //   367: invokevirtual 332	java/io/IOException:printStackTrace	()V
    //   370: aload 11
    //   372: athrow
    //   373: aload 9
    //   375: ifnull +18 -> 393
    //   378: aload 9
    //   380: invokevirtual 329	java/io/FileInputStream:close	()V
    //   383: goto +10 -> 393
    //   386: astore 12
    //   388: aload 12
    //   390: invokevirtual 332	java/io/IOException:printStackTrace	()V
    //   393: iinc 6 1
    //   396: iload 6
    //   398: iload 7
    //   400: if_icmplt -252 -> 148
    //   403: return
    // Line number table:
    //   Java source line #111	-> byte code offset #0
    //   Java source line #112	-> byte code offset #10
    //   Java source line #114	-> byte code offset #11
    //   Java source line #115	-> byte code offset #25
    //   Java source line #117	-> byte code offset #26
    //   Java source line #119	-> byte code offset #38
    //   Java source line #121	-> byte code offset #115
    //   Java source line #123	-> byte code offset #120
    //   Java source line #136	-> byte code offset #130
    //   Java source line #138	-> byte code offset #155
    //   Java source line #142	-> byte code offset #158
    //   Java source line #144	-> byte code offset #173
    //   Java source line #145	-> byte code offset #184
    //   Java source line #147	-> byte code offset #191
    //   Java source line #149	-> byte code offset #211
    //   Java source line #150	-> byte code offset #259
    //   Java source line #151	-> byte code offset #294
    //   Java source line #154	-> byte code offset #299
    //   Java source line #155	-> byte code offset #302
    //   Java source line #157	-> byte code offset #304
    //   Java source line #158	-> byte code offset #320
    //   Java source line #162	-> byte code offset #325
    //   Java source line #166	-> byte code offset #330
    //   Java source line #167	-> byte code offset #335
    //   Java source line #168	-> byte code offset #338
    //   Java source line #170	-> byte code offset #340
    //   Java source line #161	-> byte code offset #348
    //   Java source line #162	-> byte code offset #350
    //   Java source line #166	-> byte code offset #355
    //   Java source line #167	-> byte code offset #360
    //   Java source line #168	-> byte code offset #363
    //   Java source line #170	-> byte code offset #365
    //   Java source line #173	-> byte code offset #370
    //   Java source line #162	-> byte code offset #373
    //   Java source line #166	-> byte code offset #378
    //   Java source line #167	-> byte code offset #383
    //   Java source line #168	-> byte code offset #386
    //   Java source line #170	-> byte code offset #388
    //   Java source line #136	-> byte code offset #393
    //   Java source line #175	-> byte code offset #403
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	404	0	this	FileUpdater
    //   0	404	1	event	UpdateEvent
    //   37	10	2	windows	boolean
    //   114	17	3	updateDir	File
    //   128	4	4	statsFilter	FilenameFilter
    //   153	114	5	f	File
    //   143	258	6	i	int
    //   140	261	7	j	int
    //   137	12	8	arrayOfFile	File[]
    //   156	223	9	fis	java.io.FileInputStream
    //   189	95	10	md5	String
    //   302	19	10	ex	Exception
    //   348	23	11	localObject	Object
    //   338	3	12	e	java.io.IOException
    //   363	3	12	e	java.io.IOException
    //   386	3	12	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   158	299	302	java/lang/Exception
    //   330	335	338	java/io/IOException
    //   158	325	348	finally
    //   355	360	363	java/io/IOException
    //   378	383	386	java/io/IOException
  }
  
  /* Error */
  private void GetPluginMd5s()
  {
    // Byte code:
    //   0: new 44	java/io/File
    //   3: dup
    //   4: ldc_w 354
    //   7: invokespecial 48	java/io/File:<init>	(Ljava/lang/String;)V
    //   10: astore_1
    //   11: aload_1
    //   12: invokevirtual 273	java/io/File:mkdirs	()Z
    //   15: pop
    //   16: new 356	mineplex/core/updater/FileUpdater$4
    //   19: dup
    //   20: aload_0
    //   21: invokespecial 358	mineplex/core/updater/FileUpdater$4:<init>	(Lmineplex/core/updater/FileUpdater;)V
    //   24: astore_2
    //   25: aload_1
    //   26: aload_2
    //   27: invokevirtual 279	java/io/File:listFiles	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   30: dup
    //   31: astore 6
    //   33: arraylength
    //   34: istore 5
    //   36: iconst_0
    //   37: istore 4
    //   39: goto +136 -> 175
    //   42: aload 6
    //   44: iload 4
    //   46: aaload
    //   47: astore_3
    //   48: aconst_null
    //   49: astore 7
    //   51: new 290	java/io/FileInputStream
    //   54: dup
    //   55: aload_3
    //   56: invokespecial 292	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   59: astore 7
    //   61: aload_0
    //   62: getfield 31	mineplex/core/updater/FileUpdater:_jarMd5Map	Lmineplex/core/common/util/NautHashMap;
    //   65: aload_3
    //   66: invokevirtual 283	java/io/File:getName	()Ljava/lang/String;
    //   69: aload 7
    //   71: invokestatic 295	org/apache/commons/codec/digest/DigestUtils:md5Hex	(Ljava/io/InputStream;)Ljava/lang/String;
    //   74: invokevirtual 359	mineplex/core/common/util/NautHashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   77: pop
    //   78: goto +74 -> 152
    //   81: astore 8
    //   83: getstatic 308	java/lang/System:out	Ljava/io/PrintStream;
    //   86: aload_0
    //   87: invokevirtual 321	mineplex/core/updater/FileUpdater:getName	()Ljava/lang/String;
    //   90: ldc_w 322
    //   93: invokestatic 183	mineplex/core/common/util/F:main	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   96: invokevirtual 314	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   99: aload 8
    //   101: invokevirtual 324	java/lang/Exception:printStackTrace	()V
    //   104: aload 7
    //   106: ifnull +66 -> 172
    //   109: aload 7
    //   111: invokevirtual 329	java/io/FileInputStream:close	()V
    //   114: goto +58 -> 172
    //   117: astore 10
    //   119: aload 10
    //   121: invokevirtual 332	java/io/IOException:printStackTrace	()V
    //   124: goto +48 -> 172
    //   127: astore 9
    //   129: aload 7
    //   131: ifnull +18 -> 149
    //   134: aload 7
    //   136: invokevirtual 329	java/io/FileInputStream:close	()V
    //   139: goto +10 -> 149
    //   142: astore 10
    //   144: aload 10
    //   146: invokevirtual 332	java/io/IOException:printStackTrace	()V
    //   149: aload 9
    //   151: athrow
    //   152: aload 7
    //   154: ifnull +18 -> 172
    //   157: aload 7
    //   159: invokevirtual 329	java/io/FileInputStream:close	()V
    //   162: goto +10 -> 172
    //   165: astore 10
    //   167: aload 10
    //   169: invokevirtual 332	java/io/IOException:printStackTrace	()V
    //   172: iinc 4 1
    //   175: iload 4
    //   177: iload 5
    //   179: if_icmplt -137 -> 42
    //   182: return
    // Line number table:
    //   Java source line #179	-> byte code offset #0
    //   Java source line #181	-> byte code offset #11
    //   Java source line #183	-> byte code offset #16
    //   Java source line #196	-> byte code offset #25
    //   Java source line #198	-> byte code offset #48
    //   Java source line #202	-> byte code offset #51
    //   Java source line #203	-> byte code offset #61
    //   Java source line #204	-> byte code offset #78
    //   Java source line #205	-> byte code offset #81
    //   Java source line #207	-> byte code offset #83
    //   Java source line #208	-> byte code offset #99
    //   Java source line #212	-> byte code offset #104
    //   Java source line #216	-> byte code offset #109
    //   Java source line #217	-> byte code offset #114
    //   Java source line #218	-> byte code offset #117
    //   Java source line #220	-> byte code offset #119
    //   Java source line #211	-> byte code offset #127
    //   Java source line #212	-> byte code offset #129
    //   Java source line #216	-> byte code offset #134
    //   Java source line #217	-> byte code offset #139
    //   Java source line #218	-> byte code offset #142
    //   Java source line #220	-> byte code offset #144
    //   Java source line #223	-> byte code offset #149
    //   Java source line #212	-> byte code offset #152
    //   Java source line #216	-> byte code offset #157
    //   Java source line #217	-> byte code offset #162
    //   Java source line #218	-> byte code offset #165
    //   Java source line #220	-> byte code offset #167
    //   Java source line #196	-> byte code offset #172
    //   Java source line #225	-> byte code offset #182
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	183	0	this	FileUpdater
    //   10	16	1	pluginDir	File
    //   24	3	2	statsFilter	FilenameFilter
    //   47	19	3	f	File
    //   37	143	4	i	int
    //   34	146	5	j	int
    //   31	12	6	arrayOfFile	File[]
    //   49	109	7	fis	java.io.FileInputStream
    //   81	19	8	ex	Exception
    //   127	23	9	localObject	Object
    //   117	3	10	e	java.io.IOException
    //   142	3	10	e	java.io.IOException
    //   165	3	10	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   51	78	81	java/lang/Exception
    //   109	114	117	java/io/IOException
    //   51	104	127	finally
    //   134	139	142	java/io/IOException
    //   157	162	165	java/io/IOException
  }
  
  public Region getRegion()
  {
    return this._region;
  }
}

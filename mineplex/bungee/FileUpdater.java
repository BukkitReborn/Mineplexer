package mineplex.bungee;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;

public class FileUpdater
  implements Runnable
{
  private Plugin _plugin;
  private HashMap<String, String> _jarMd5Map = new HashMap();
  private boolean _needUpdate;
  private boolean _enabled = true;
  private int _timeTilRestart = 5;
  
  public FileUpdater(Plugin plugin)
  {
    this._plugin = plugin;
    
    getPluginMd5s();
    if (new File("IgnoreUpdates.dat").exists()) {
      this._enabled = false;
    }
    this._plugin.getProxy().getScheduler().schedule(this._plugin, this, 2L, 2L, TimeUnit.MINUTES);
  }
  
  /* Error */
  public void checkForNewFiles()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 82	mineplex/bungee/FileUpdater:_needUpdate	Z
    //   4: ifne +10 -> 14
    //   7: aload_0
    //   8: getfield 29	mineplex/bungee/FileUpdater:_enabled	Z
    //   11: ifne +4 -> 15
    //   14: return
    //   15: ldc 84
    //   17: invokestatic 86	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   20: ldc 92
    //   22: invokevirtual 94	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   25: istore_1
    //   26: new 38	java/io/File
    //   29: dup
    //   30: new 100	java/lang/StringBuilder
    //   33: dup
    //   34: iload_1
    //   35: ifeq +8 -> 43
    //   38: ldc 102
    //   40: goto +35 -> 75
    //   43: new 100	java/lang/StringBuilder
    //   46: dup
    //   47: getstatic 104	java/io/File:separator	Ljava/lang/String;
    //   50: invokestatic 108	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   53: invokespecial 112	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   56: ldc 113
    //   58: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: getstatic 104	java/io/File:separator	Ljava/lang/String;
    //   64: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: ldc 119
    //   69: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: invokevirtual 121	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   75: invokestatic 108	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   78: invokespecial 112	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   81: getstatic 104	java/io/File:separator	Ljava/lang/String;
    //   84: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: ldc 125
    //   89: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: invokevirtual 121	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   95: invokespecial 42	java/io/File:<init>	(Ljava/lang/String;)V
    //   98: astore_2
    //   99: aload_2
    //   100: invokevirtual 127	java/io/File:mkdirs	()Z
    //   103: pop
    //   104: new 130	mineplex/bungee/FileUpdater$1
    //   107: dup
    //   108: aload_0
    //   109: invokespecial 132	mineplex/bungee/FileUpdater$1:<init>	(Lmineplex/bungee/FileUpdater;)V
    //   112: astore_3
    //   113: aload_2
    //   114: aload_3
    //   115: invokevirtual 135	java/io/File:listFiles	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   118: dup
    //   119: astore 7
    //   121: arraylength
    //   122: istore 6
    //   124: iconst_0
    //   125: istore 5
    //   127: goto +233 -> 360
    //   130: aload 7
    //   132: iload 5
    //   134: aaload
    //   135: astore 4
    //   137: aconst_null
    //   138: astore 8
    //   140: aload_0
    //   141: getfield 27	mineplex/bungee/FileUpdater:_jarMd5Map	Ljava/util/HashMap;
    //   144: aload 4
    //   146: invokevirtual 139	java/io/File:getName	()Ljava/lang/String;
    //   149: invokevirtual 142	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
    //   152: ifeq +185 -> 337
    //   155: new 146	java/io/FileInputStream
    //   158: dup
    //   159: aload 4
    //   161: invokespecial 148	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   164: astore 8
    //   166: aload 8
    //   168: invokestatic 151	org/apache/commons/codec/digest/DigestUtils:md5Hex	(Ljava/io/InputStream;)Ljava/lang/String;
    //   171: astore 9
    //   173: aload 9
    //   175: aload_0
    //   176: getfield 27	mineplex/bungee/FileUpdater:_jarMd5Map	Ljava/util/HashMap;
    //   179: aload 4
    //   181: invokevirtual 139	java/io/File:getName	()Ljava/lang/String;
    //   184: invokevirtual 157	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   187: invokevirtual 161	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   190: ifne +147 -> 337
    //   193: getstatic 164	java/lang/System:out	Ljava/io/PrintStream;
    //   196: new 100	java/lang/StringBuilder
    //   199: dup
    //   200: aload 4
    //   202: invokevirtual 139	java/io/File:getName	()Ljava/lang/String;
    //   205: invokestatic 108	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   208: invokespecial 112	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   211: ldc -88
    //   213: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   216: aload_0
    //   217: getfield 27	mineplex/bungee/FileUpdater:_jarMd5Map	Ljava/util/HashMap;
    //   220: aload 4
    //   222: invokevirtual 139	java/io/File:getName	()Ljava/lang/String;
    //   225: invokevirtual 157	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   228: checkcast 95	java/lang/String
    //   231: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: invokevirtual 121	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   237: invokevirtual 170	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   240: getstatic 164	java/lang/System:out	Ljava/io/PrintStream;
    //   243: new 100	java/lang/StringBuilder
    //   246: dup
    //   247: aload 4
    //   249: invokevirtual 139	java/io/File:getName	()Ljava/lang/String;
    //   252: invokestatic 108	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   255: invokespecial 112	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   258: ldc -81
    //   260: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: aload 9
    //   265: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: invokevirtual 121	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   271: invokevirtual 170	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   274: aload_0
    //   275: iconst_1
    //   276: putfield 82	mineplex/bungee/FileUpdater:_needUpdate	Z
    //   279: goto +58 -> 337
    //   282: astore 9
    //   284: aload 9
    //   286: invokevirtual 177	java/lang/Exception:printStackTrace	()V
    //   289: aload 8
    //   291: ifnull +66 -> 357
    //   294: aload 8
    //   296: invokevirtual 182	java/io/FileInputStream:close	()V
    //   299: goto +58 -> 357
    //   302: astore 11
    //   304: aload 11
    //   306: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   309: goto +48 -> 357
    //   312: astore 10
    //   314: aload 8
    //   316: ifnull +18 -> 334
    //   319: aload 8
    //   321: invokevirtual 182	java/io/FileInputStream:close	()V
    //   324: goto +10 -> 334
    //   327: astore 11
    //   329: aload 11
    //   331: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   334: aload 10
    //   336: athrow
    //   337: aload 8
    //   339: ifnull +18 -> 357
    //   342: aload 8
    //   344: invokevirtual 182	java/io/FileInputStream:close	()V
    //   347: goto +10 -> 357
    //   350: astore 11
    //   352: aload 11
    //   354: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   357: iinc 5 1
    //   360: iload 5
    //   362: iload 6
    //   364: if_icmplt -234 -> 130
    //   367: return
    // Line number table:
    //   Java source line #39	-> byte code offset #0
    //   Java source line #40	-> byte code offset #14
    //   Java source line #42	-> byte code offset #15
    //   Java source line #44	-> byte code offset #26
    //   Java source line #46	-> byte code offset #99
    //   Java source line #48	-> byte code offset #104
    //   Java source line #61	-> byte code offset #113
    //   Java source line #63	-> byte code offset #137
    //   Java source line #67	-> byte code offset #140
    //   Java source line #69	-> byte code offset #155
    //   Java source line #70	-> byte code offset #166
    //   Java source line #72	-> byte code offset #173
    //   Java source line #74	-> byte code offset #193
    //   Java source line #75	-> byte code offset #240
    //   Java source line #76	-> byte code offset #274
    //   Java source line #79	-> byte code offset #279
    //   Java source line #80	-> byte code offset #282
    //   Java source line #82	-> byte code offset #284
    //   Java source line #86	-> byte code offset #289
    //   Java source line #90	-> byte code offset #294
    //   Java source line #91	-> byte code offset #299
    //   Java source line #92	-> byte code offset #302
    //   Java source line #94	-> byte code offset #304
    //   Java source line #85	-> byte code offset #312
    //   Java source line #86	-> byte code offset #314
    //   Java source line #90	-> byte code offset #319
    //   Java source line #91	-> byte code offset #324
    //   Java source line #92	-> byte code offset #327
    //   Java source line #94	-> byte code offset #329
    //   Java source line #97	-> byte code offset #334
    //   Java source line #86	-> byte code offset #337
    //   Java source line #90	-> byte code offset #342
    //   Java source line #91	-> byte code offset #347
    //   Java source line #92	-> byte code offset #350
    //   Java source line #94	-> byte code offset #352
    //   Java source line #61	-> byte code offset #357
    //   Java source line #99	-> byte code offset #367
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	368	0	this	FileUpdater
    //   25	10	1	windows	boolean
    //   98	16	2	updateDir	File
    //   112	3	3	statsFilter	FilenameFilter
    //   135	113	4	f	File
    //   125	240	5	i	int
    //   122	243	6	j	int
    //   119	12	7	arrayOfFile	File[]
    //   138	205	8	fis	java.io.FileInputStream
    //   171	93	9	md5	String
    //   282	3	9	ex	Exception
    //   312	23	10	localObject	Object
    //   302	3	11	e	java.io.IOException
    //   327	3	11	e	java.io.IOException
    //   350	3	11	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   140	279	282	java/lang/Exception
    //   294	299	302	java/io/IOException
    //   140	289	312	finally
    //   319	324	327	java/io/IOException
    //   342	347	350	java/io/IOException
  }
  
  /* Error */
  private void getPluginMd5s()
  {
    // Byte code:
    //   0: new 38	java/io/File
    //   3: dup
    //   4: ldc -49
    //   6: invokespecial 42	java/io/File:<init>	(Ljava/lang/String;)V
    //   9: astore_1
    //   10: aload_1
    //   11: invokevirtual 127	java/io/File:mkdirs	()Z
    //   14: pop
    //   15: new 209	mineplex/bungee/FileUpdater$2
    //   18: dup
    //   19: aload_0
    //   20: invokespecial 211	mineplex/bungee/FileUpdater$2:<init>	(Lmineplex/bungee/FileUpdater;)V
    //   23: astore_2
    //   24: aload_1
    //   25: aload_2
    //   26: invokevirtual 135	java/io/File:listFiles	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   29: dup
    //   30: astore 6
    //   32: arraylength
    //   33: istore 5
    //   35: iconst_0
    //   36: istore 4
    //   38: goto +120 -> 158
    //   41: aload 6
    //   43: iload 4
    //   45: aaload
    //   46: astore_3
    //   47: aconst_null
    //   48: astore 7
    //   50: new 146	java/io/FileInputStream
    //   53: dup
    //   54: aload_3
    //   55: invokespecial 148	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   58: astore 7
    //   60: aload_0
    //   61: getfield 27	mineplex/bungee/FileUpdater:_jarMd5Map	Ljava/util/HashMap;
    //   64: aload_3
    //   65: invokevirtual 139	java/io/File:getName	()Ljava/lang/String;
    //   68: aload 7
    //   70: invokestatic 151	org/apache/commons/codec/digest/DigestUtils:md5Hex	(Ljava/io/InputStream;)Ljava/lang/String;
    //   73: invokevirtual 212	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   76: pop
    //   77: goto +58 -> 135
    //   80: astore 8
    //   82: aload 8
    //   84: invokevirtual 177	java/lang/Exception:printStackTrace	()V
    //   87: aload 7
    //   89: ifnull +66 -> 155
    //   92: aload 7
    //   94: invokevirtual 182	java/io/FileInputStream:close	()V
    //   97: goto +58 -> 155
    //   100: astore 10
    //   102: aload 10
    //   104: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   107: goto +48 -> 155
    //   110: astore 9
    //   112: aload 7
    //   114: ifnull +18 -> 132
    //   117: aload 7
    //   119: invokevirtual 182	java/io/FileInputStream:close	()V
    //   122: goto +10 -> 132
    //   125: astore 10
    //   127: aload 10
    //   129: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   132: aload 9
    //   134: athrow
    //   135: aload 7
    //   137: ifnull +18 -> 155
    //   140: aload 7
    //   142: invokevirtual 182	java/io/FileInputStream:close	()V
    //   145: goto +10 -> 155
    //   148: astore 10
    //   150: aload 10
    //   152: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   155: iinc 4 1
    //   158: iload 4
    //   160: iload 5
    //   162: if_icmplt -121 -> 41
    //   165: return
    // Line number table:
    //   Java source line #103	-> byte code offset #0
    //   Java source line #105	-> byte code offset #10
    //   Java source line #107	-> byte code offset #15
    //   Java source line #120	-> byte code offset #24
    //   Java source line #122	-> byte code offset #47
    //   Java source line #126	-> byte code offset #50
    //   Java source line #127	-> byte code offset #60
    //   Java source line #128	-> byte code offset #77
    //   Java source line #129	-> byte code offset #80
    //   Java source line #131	-> byte code offset #82
    //   Java source line #135	-> byte code offset #87
    //   Java source line #139	-> byte code offset #92
    //   Java source line #140	-> byte code offset #97
    //   Java source line #141	-> byte code offset #100
    //   Java source line #143	-> byte code offset #102
    //   Java source line #134	-> byte code offset #110
    //   Java source line #135	-> byte code offset #112
    //   Java source line #139	-> byte code offset #117
    //   Java source line #140	-> byte code offset #122
    //   Java source line #141	-> byte code offset #125
    //   Java source line #143	-> byte code offset #127
    //   Java source line #146	-> byte code offset #132
    //   Java source line #135	-> byte code offset #135
    //   Java source line #139	-> byte code offset #140
    //   Java source line #140	-> byte code offset #145
    //   Java source line #141	-> byte code offset #148
    //   Java source line #143	-> byte code offset #150
    //   Java source line #120	-> byte code offset #155
    //   Java source line #148	-> byte code offset #165
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	166	0	this	FileUpdater
    //   9	16	1	pluginDir	File
    //   23	3	2	statsFilter	FilenameFilter
    //   46	19	3	f	File
    //   36	127	4	i	int
    //   33	130	5	j	int
    //   30	12	6	arrayOfFile	File[]
    //   48	93	7	fis	java.io.FileInputStream
    //   80	3	8	ex	Exception
    //   110	23	9	localObject	Object
    //   100	3	10	e	java.io.IOException
    //   125	3	10	e	java.io.IOException
    //   148	3	10	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   50	77	80	java/lang/Exception
    //   92	97	100	java/io/IOException
    //   50	87	110	finally
    //   117	122	125	java/io/IOException
    //   140	145	148	java/io/IOException
  }
  
  public void run()
  {
    checkForNewFiles();
    if (this._needUpdate) {
      BungeeCord.getInstance().broadcast(ChatColor.RED + "Connection Node" + ChatColor.DARK_GRAY + ">" + ChatColor.YELLOW + "This connection node will be restarting in " + this._timeTilRestart + " minutes.");
    } else {
      return;
    }
    this._timeTilRestart -= 2;
    if ((this._timeTilRestart < 0) || (!this._enabled)) {
      BungeeCord.getInstance().stop();
    }
  }
}

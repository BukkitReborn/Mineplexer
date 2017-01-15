package mineplex.bungee.status;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;

public class InternetStatus
  implements Runnable
{
  private static boolean _connected = true;
  private Plugin _plugin;
  
  public static boolean isConnected()
  {
    return _connected;
  }
  
  public InternetStatus(Plugin plugin)
  {
    this._plugin = plugin;
    this._plugin.getProxy().getScheduler().schedule(this._plugin, this, 1L, 1L, TimeUnit.MINUTES);
    
    System.out.println("Initialized InternetStatus.");
  }
  
  public void run()
  {
    _connected = isOnline();
  }
  
  private boolean isOnline()
  {
    return (testUrl("www.google.com")) || 
      (testUrl("www.espn.com")) || 
      (testUrl("www.bing.com"));
  }
  
  /* Error */
  private boolean testUrl(String url)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aconst_null
    //   5: astore 4
    //   7: new 82	java/net/Socket
    //   10: dup
    //   11: aload_1
    //   12: bipush 80
    //   14: invokespecial 84	java/net/Socket:<init>	(Ljava/lang/String;I)V
    //   17: astore 5
    //   19: iconst_1
    //   20: istore_2
    //   21: aload 5
    //   23: ifnull +51 -> 74
    //   26: aload 5
    //   28: invokevirtual 87	java/net/Socket:close	()V
    //   31: goto +43 -> 74
    //   34: astore_3
    //   35: aload 5
    //   37: ifnull +8 -> 45
    //   40: aload 5
    //   42: invokevirtual 87	java/net/Socket:close	()V
    //   45: aload_3
    //   46: athrow
    //   47: astore 4
    //   49: aload_3
    //   50: ifnonnull +9 -> 59
    //   53: aload 4
    //   55: astore_3
    //   56: goto +15 -> 71
    //   59: aload_3
    //   60: aload 4
    //   62: if_acmpeq +9 -> 71
    //   65: aload_3
    //   66: aload 4
    //   68: invokevirtual 90	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   71: aload_3
    //   72: athrow
    //   73: astore_3
    //   74: iload_2
    //   75: ireturn
    // Line number table:
    //   Java source line #42	-> byte code offset #0
    //   Java source line #44	-> byte code offset #2
    //   Java source line #46	-> byte code offset #19
    //   Java source line #47	-> byte code offset #21
    //   Java source line #48	-> byte code offset #73
    //   Java source line #53	-> byte code offset #74
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	76	0	this	InternetStatus
    //   0	76	1	url	String
    //   1	74	2	reachable	boolean
    //   3	1	3	localObject1	Object
    //   34	16	3	localObject2	Object
    //   55	17	3	localObject3	Object
    //   73	1	3	localException	Exception
    //   73	1	3	localException1	Exception
    //   5	1	4	localObject4	Object
    //   47	20	4	localThrowable	Throwable
    //   17	24	5	socket	java.net.Socket
    // Exception table:
    //   from	to	target	type
    //   19	21	34	finally
    //   7	47	47	finally
    //   2	73	73	java/lang/Exception
  }
}

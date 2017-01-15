package mineplex.core.timing;

import java.io.PrintStream;
import java.util.Map.Entry;
import mineplex.core.common.util.NautHashMap;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TimingManager
  implements Listener
{
  private static TimingManager _instance;
  private JavaPlugin _plugin;
  private static NautHashMap<String, Long> _timingList = new NautHashMap();
  private static NautHashMap<String, TimeData> _totalList = new NautHashMap();
  private static Object _timingLock = new Object();
  private static Object _totalLock = new Object();
  public static boolean Debug = true;
  
  protected TimingManager(JavaPlugin plugin)
  {
    _instance = this;
    
    this._plugin = plugin;
    
    this._plugin.getServer().getPluginManager().registerEvents(this, this._plugin);
  }
  
  public static TimingManager Initialize(JavaPlugin plugin)
  {
    if (_instance == null) {
      _instance = new TimingManager(plugin);
    }
    return _instance;
  }
  
  public static TimingManager instance()
  {
    return _instance;
  }
  
  public static void startTotal(String title)
  {
    if (!Debug) {
      return;
    }
    synchronized (_totalLock)
    {
      if (_totalList.containsKey(title))
      {
        TimeData data = (TimeData)_totalList.get(title);
        data.LastMarker = System.currentTimeMillis();
        
        _totalList.put(title, data);
      }
      else
      {
        TimeData data = new TimeData(title, System.currentTimeMillis());
        _totalList.put(title, data);
      }
    }
  }
  
  public static void stopTotal(String title)
  {
    if (!Debug) {
      return;
    }
    synchronized (_totalLock)
    {
      if (_totalList.containsKey(title)) {
        ((TimeData)_totalList.get(title)).addTime();
      }
    }
  }
  
  public static void printTotal(String title)
  {
    if (!Debug) {
      return;
    }
    synchronized (_totalLock)
    {
      ((TimeData)_totalList.get(title)).printInfo();
    }
  }
  
  public static void endTotal(String title, boolean print)
  {
    if (!Debug) {
      return;
    }
    synchronized (_totalLock)
    {
      TimeData data = (TimeData)_totalList.remove(title);
      if ((data != null) && (print)) {
        data.printInfo();
      }
    }
  }
  
  public static void printTotals()
  {
    if (!Debug) {
      return;
    }
    synchronized (_totalLock)
    {
      for (Map.Entry<String, TimeData> entry : _totalList.entrySet()) {
        ((TimeData)entry.getValue()).printInfo();
      }
    }
  }
  
  public static void start(String title)
  {
    if (!Debug) {
      return;
    }
    synchronized (_timingLock)
    {
      _timingList.put(title, Long.valueOf(System.currentTimeMillis()));
    }
  }
  
  public static void stop(String title)
  {
    if (!Debug) {
      return;
    }
    synchronized (_timingLock)
    {
      System.out.println("]==[TIMING]==[" + title + " took " + (System.currentTimeMillis() - ((Long)_timingList.get(title)).longValue()) + "ms");
      _timingList.remove(title);
    }
  }
}

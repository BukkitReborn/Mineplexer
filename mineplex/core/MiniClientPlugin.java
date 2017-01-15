package mineplex.core;

import java.util.Collection;
import mineplex.core.account.event.ClientUnloadEvent;
import mineplex.core.common.util.NautHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MiniClientPlugin<DataType>
  extends MiniPlugin
{
  private static Object _clientDataLock = new Object();
  private NautHashMap<String, DataType> _clientData = new NautHashMap();
  
  public MiniClientPlugin(String moduleName, JavaPlugin plugin)
  {
    super(moduleName, plugin);
  }
  
  @EventHandler
  public void UnloadPlayer(ClientUnloadEvent event)
  {
    Object object = _clientDataLock;
    synchronized (object)
    {
      this._clientData.remove(event.GetName());
    }
  }
  
  public DataType Get(String name)
  {
    Object object = _clientDataLock;
    synchronized (object)
    {
      if (!this._clientData.containsKey(name)) {
        this._clientData.put(name, AddPlayer(name));
      }
      return (DataType)this._clientData.get(name);
    }
  }
  
  public DataType Get(Player player)
  {
    return (DataType)Get(player.getName());
  }
  
  protected Collection<DataType> GetValues()
  {
    return this._clientData.values();
  }
  
  protected void Set(Player player, DataType data)
  {
    Set(player.getName(), data);
  }
  
  protected void Set(String name, DataType data)
  {
    Object object = _clientDataLock;
    synchronized (object)
    {
      this._clientData.put(name, data);
    }
  }
  
  protected abstract DataType AddPlayer(String paramString);
}

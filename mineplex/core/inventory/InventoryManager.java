package mineplex.core.inventory;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import mineplex.core.MiniDbClientPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.inventory.command.GiveItemCommand;
import mineplex.core.inventory.data.Category;
import mineplex.core.inventory.data.InventoryRepository;
import mineplex.core.inventory.data.Item;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class InventoryManager
  extends MiniDbClientPlugin<ClientInventory>
{
  private static Object _inventoryLock = new Object();
  private InventoryRepository _repository;
  private NautHashMap<String, Item> _items = new NautHashMap();
  private NautHashMap<String, Category> _categories = new NautHashMap();
  private NautHashMap<Player, NautHashMap<String, NautHashMap<String, Integer>>> _inventoryQueue = new NautHashMap();
  
  public InventoryManager(JavaPlugin plugin, CoreClientManager clientManager)
  {
    super("Inventory Manager", plugin, clientManager);
    
    this._repository = new InventoryRepository(plugin);
    
    Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        InventoryManager.this.updateItems();
        InventoryManager.this.updateCategories();
      }
    }, 20L);
  }
  
  private void updateItems()
  {
    List<Item> items = this._repository.retrieveItems();
    synchronized (_inventoryLock)
    {
      for (Item item : items) {
        this._items.put(item.Name, item);
      }
    }
  }
  
  private void updateCategories()
  {
    List<Category> categories = this._repository.retrieveCategories();
    synchronized (_inventoryLock)
    {
      for (Category category : categories) {
        this._categories.put(category.Name, category);
      }
    }
  }
  
  public void addItemToInventory(Player player, String category, String item, int count)
  {
    if (this._items.containsKey(item)) {
      ((ClientInventory)Get(player)).addItem(new ClientItem((Item)this._items.get(item), count));
    }
    if (!this._inventoryQueue.containsKey(player)) {
      this._inventoryQueue.put(player, new NautHashMap());
    }
    if (!((NautHashMap)this._inventoryQueue.get(player)).containsKey(category)) {
      ((NautHashMap)this._inventoryQueue.get(player)).put(category, new NautHashMap());
    }
    int totalAmount = count;
    if (((NautHashMap)((NautHashMap)this._inventoryQueue.get(player)).get(category)).containsKey(item)) {
      totalAmount += ((Integer)((NautHashMap)((NautHashMap)this._inventoryQueue.get(player)).get(category)).get(item)).intValue();
    }
    ((NautHashMap)((NautHashMap)this._inventoryQueue.get(player)).get(category)).put(item, Integer.valueOf(totalAmount));
  }
  
  public void addItemToInventory(final Callback<Boolean> callback, final Player player, String category, final String item, final int count)
  {
    addItemToInventoryForOffline(new Callback()
    {
      public void run(Boolean success)
      {
        if (!success.booleanValue())
        {
          System.out.println("Add item to Inventory FAILED for " + player.getName());
          if (InventoryManager.this._items.containsKey(item)) {
            ((ClientInventory)InventoryManager.this.Get(player)).addItem(new ClientItem((Item)InventoryManager.this._items.get(item), -count));
          }
        }
        if (callback != null) {
          callback.run(success);
        }
      }
    }, player.getUniqueId(), category, item, count);
  }
  
  /* Error */
  public boolean validCategory(String category)
  {
    // Byte code:
    //   0: getstatic 25	mineplex/core/inventory/InventoryManager:_inventoryLock	Ljava/lang/Object;
    //   3: dup
    //   4: astore_2
    //   5: monitorenter
    //   6: aload_0
    //   7: getfield 40	mineplex/core/inventory/InventoryManager:_categories	Lmineplex/core/common/util/NautHashMap;
    //   10: aload_1
    //   11: invokevirtual 139	mineplex/core/common/util/NautHashMap:containsKey	(Ljava/lang/Object;)Z
    //   14: aload_2
    //   15: monitorexit
    //   16: ireturn
    //   17: aload_2
    //   18: monitorexit
    //   19: athrow
    // Line number table:
    //   Java source line #124	-> byte code offset #0
    //   Java source line #126	-> byte code offset #6
    //   Java source line #124	-> byte code offset #17
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	20	0	this	InventoryManager
    //   0	20	1	category	String
    //   4	14	2	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   6	16	17	finally
    //   17	19	17	finally
  }
  
  /* Error */
  public boolean validItem(String item)
  {
    // Byte code:
    //   0: getstatic 25	mineplex/core/inventory/InventoryManager:_inventoryLock	Ljava/lang/Object;
    //   3: dup
    //   4: astore_2
    //   5: monitorenter
    //   6: aload_0
    //   7: getfield 38	mineplex/core/inventory/InventoryManager:_items	Lmineplex/core/common/util/NautHashMap;
    //   10: aload_1
    //   11: invokevirtual 139	mineplex/core/common/util/NautHashMap:containsKey	(Ljava/lang/Object;)Z
    //   14: aload_2
    //   15: monitorexit
    //   16: ireturn
    //   17: aload_2
    //   18: monitorexit
    //   19: athrow
    // Line number table:
    //   Java source line #132	-> byte code offset #0
    //   Java source line #134	-> byte code offset #6
    //   Java source line #132	-> byte code offset #17
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	20	0	this	InventoryManager
    //   0	20	1	item	String
    //   4	14	2	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   6	16	17	finally
    //   17	19	17	finally
  }
  
  public Item getItem(String itemName)
  {
    Item item = null;
    for (Map.Entry<String, Item> entry : this._items.entrySet())
    {
      String name = (String)entry.getKey();
      if (name.equalsIgnoreCase(itemName)) {
        item = (Item)entry.getValue();
      }
    }
    return item;
  }
  
  public void addItemToInventoryForOffline(final Callback<Boolean> callback, final UUID uuid, final String category, final String item, final int count)
  {
    Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        synchronized (InventoryManager._inventoryLock)
        {
          if (!InventoryManager.this._categories.containsKey(category))
          {
            InventoryManager.this._repository.addCategory(category);
            System.out.println("InventoryManager Adding Category : " + category);
          }
        }
        InventoryManager.this.updateCategories();
        synchronized (InventoryManager._inventoryLock)
        {
          if (!InventoryManager.this._items.containsKey(item))
          {
            InventoryManager.this._repository.addItem(item, ((Category)InventoryManager.this._categories.get(category)).Id);
            System.out.println("InventoryManager Adding Item : " + item);
          }
        }
        InventoryManager.this.updateItems();
        synchronized (InventoryManager._inventoryLock)
        {
          final boolean success = InventoryManager.this._repository.incrementClientInventoryItem(InventoryManager.this.ClientManager.getCachedClientAccountId(uuid), ((Item)InventoryManager.this._items.get(item)).Id, count);
          if (callback != null) {
            Bukkit.getServer().getScheduler().runTask(InventoryManager.this.getPlugin(), new Runnable()
            {
              public void run()
              {
                this.val$callback.run(Boolean.valueOf(success));
              }
            });
          }
        }
      }
    });
  }
  
  @EventHandler
  public void updateInventoryQueue(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SEC) {
      return;
    }
    for (final Player player : this._inventoryQueue.keySet())
    {
      Iterator localIterator3;
      for (Iterator localIterator2 = ((NautHashMap)this._inventoryQueue.get(player)).keySet().iterator(); localIterator2.hasNext(); localIterator3.hasNext())
      {
        String category = (String)localIterator2.next();
        
        localIterator3 = ((NautHashMap)((NautHashMap)this._inventoryQueue.get(player)).get(category)).keySet().iterator(); continue;final String item = (String)localIterator3.next();
        
        final int count = ((Integer)((NautHashMap)((NautHashMap)this._inventoryQueue.get(player)).get(category)).get(item)).intValue();
        
        addItemToInventoryForOffline(new Callback()
        {
          public void run(Boolean success)
          {
            if (!success.booleanValue())
            {
              System.out.println("Add item to Inventory FAILED for " + player);
              if (InventoryManager.this._items.containsKey(item)) {
                ((ClientInventory)InventoryManager.this.Get(player)).addItem(new ClientItem((Item)InventoryManager.this._items.get(item), -count));
              }
            }
          }
        }, player.getUniqueId(), category, item, count);
      }
      ((NautHashMap)this._inventoryQueue.get(player)).clear();
    }
    this._inventoryQueue.clear();
  }
  
  protected ClientInventory AddPlayer(String player)
  {
    return new ClientInventory();
  }
  
  public void addCommands()
  {
    addCommand(new GiveItemCommand(this));
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    Set(playerName, this._repository.loadClientInformation(resultSet));
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return "SELECT items.name, ic.name as category, count FROM accountInventory AS ai INNER JOIN items ON items.id = ai.itemId INNER JOIN itemCategories AS ic ON ic.id = items.categoryId WHERE ai.accountId = '" + accountId + "';";
  }
}

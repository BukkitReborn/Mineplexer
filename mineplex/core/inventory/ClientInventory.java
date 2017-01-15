package mineplex.core.inventory;

import mineplex.core.common.util.NautHashMap;
import mineplex.core.inventory.data.Item;

public class ClientInventory
{
  public NautHashMap<String, ClientItem> Items = new NautHashMap();
  
  public void addItem(ClientItem item)
  {
    if (!this.Items.containsKey(item.Item.Name)) {
      this.Items.put(item.Item.Name, new ClientItem(item.Item, 0));
    }
    ((ClientItem)this.Items.get(item.Item.Name)).Count += item.Count;
  }
  
  public void removeItem(ClientItem item)
  {
    if (!this.Items.containsKey(item.Item.Name)) {
      return;
    }
    ((ClientItem)this.Items.get(item.Item.Name)).Count -= item.Count;
    if (((ClientItem)this.Items.get(item.Item.Name)).Count == 0) {
      this.Items.remove(item.Item.Name);
    }
  }
  
  public int getItemCount(String name)
  {
    return this.Items.containsKey(name) ? ((ClientItem)this.Items.get(name)).Count : 0;
  }
}

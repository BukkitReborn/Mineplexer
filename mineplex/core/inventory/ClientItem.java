package mineplex.core.inventory;

import mineplex.core.inventory.data.Item;

public class ClientItem
{
  public Item Item;
  public int Count;
  
  public ClientItem(Item item, int count)
  {
    this.Item = item;
    this.Count = count;
  }
}

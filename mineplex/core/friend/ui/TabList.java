package mineplex.core.friend.ui;

import java.util.HashSet;
import mineplex.core.common.util.NautHashMap;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class TabList
  implements Listener
{
  private static int MAX_SLOTS = 64;
  private static int COLUMN_SLOTS = 16;
  private static NautHashMap<Integer, String> _invisibleHolders = new NautHashMap();
  private NautHashMap<Integer, LineTracker> _tabSlots = new NautHashMap();
  private HashSet<Integer> _updatedSlots = new HashSet();
  private boolean _update;
  
  static
  {
    String spaces = "";
    for (int i = 0; i < MAX_SLOTS; i++)
    {
      int markerSymbol = i / COLUMN_SLOTS;
      String symbol = null;
      if (i % COLUMN_SLOTS == 0) {
        spaces = "";
      } else {
        spaces = spaces + " ";
      }
      if (markerSymbol == 0) {
        symbol = ChatColor.GREEN;
      } else if (markerSymbol == 1) {
        symbol = ChatColor.RED;
      } else if (markerSymbol == 2) {
        symbol = ChatColor.BLUE;
      } else if (markerSymbol == 3) {
        symbol = ChatColor.BLACK;
      }
      _invisibleHolders.put(Integer.valueOf(i), symbol + spaces);
    }
  }
  
  public TabList()
  {
    for (Integer i = Integer.valueOf(0); i.intValue() < MAX_SLOTS; i = Integer.valueOf(i.intValue() + 1)) {
      this._tabSlots.put(i, new LineTracker((String)_invisibleHolders.get(i)));
    }
  }
  
  public void set(int column, int row, String lineContent)
  {
    int index = row * 4 + column;
    if (index >= MAX_SLOTS) {
      return;
    }
    if ((lineContent == null) || (lineContent.isEmpty())) {
      lineContent = (String)_invisibleHolders.get(Integer.valueOf(index));
    }
    if (((LineTracker)this._tabSlots.get(Integer.valueOf(index))).setLine(lineContent))
    {
      this._updatedSlots.add(Integer.valueOf(index));
      this._update = true;
    }
  }
  
  public void refreshForPlayer(Player player)
  {
    EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
    
    int indexChanged = MAX_SLOTS;
    for (int i = 0; i < MAX_SLOTS; i++) {
      if ((indexChanged == MAX_SLOTS) && (this._updatedSlots.contains(Integer.valueOf(i)))) {
        indexChanged = i;
      } else if ((indexChanged != MAX_SLOTS) && (!this._updatedSlots.contains(Integer.valueOf(i)))) {
        ((LineTracker)this._tabSlots.get(Integer.valueOf(i))).removeLineForPlayer(entityPlayer);
      }
    }
    for (int i = indexChanged; i < MAX_SLOTS; i++) {
      ((LineTracker)this._tabSlots.get(Integer.valueOf(i))).displayLineToPlayer(entityPlayer);
    }
    this._update = false;
    this._updatedSlots.clear();
  }
  
  public boolean shouldUpdate()
  {
    return this._update;
  }
}

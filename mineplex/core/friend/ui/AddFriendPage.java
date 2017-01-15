package mineplex.core.friend.ui;

import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandCenter;
import mineplex.core.common.util.C;
import mineplex.core.common.util.Callback;
import mineplex.core.friend.FriendManager;
import mineplex.core.itemstack.ItemBuilder;
import net.minecraft.server.v1_7_R4.Container;
import net.minecraft.server.v1_7_R4.ContainerAnvil;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.Slot;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class AddFriendPage
  implements Listener
{
  private FriendManager _friends;
  private Player _player;
  private Inventory _currentInventory;
  
  private class AnvilContainer
    extends ContainerAnvil
  {
    private String n;
    
    public AnvilContainer(EntityHuman entity)
    {
      super(entity.world, 0, 0, 0, entity);
    }
    
    public boolean a(EntityHuman entityhuman)
    {
      return true;
    }
    
    public void a(String origString)
    {
      this.n = origString;
      AddFriendPage.this._itemName = origString;
      if (getSlot(2).hasItem())
      {
        ItemStack itemstack = getSlot(2).getItem();
        if (StringUtils.isBlank(origString)) {
          itemstack.t();
        } else {
          itemstack.c(this.n);
        }
      }
      e();
    }
  }
  
  private String _itemName = "";
  private boolean _searching;
  
  public AddFriendPage(FriendManager friends, Player player)
  {
    this._player = player;
    this._friends = friends;
    
    openInventory();
    friends.registerEvents(this);
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event)
  {
    if (event.getPlayer() == this._player) {
      unregisterListener();
    }
  }
  
  public void unregisterListener()
  {
    this._currentInventory.clear();
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event)
  {
    if (event.getRawSlot() < 3)
    {
      event.setCancelled(true);
      if (event.getRawSlot() == 2) {
        if ((this._itemName.length() > 1) && (!this._searching))
        {
          this._searching = true;
          final String name = this._itemName;
          
          CommandCenter.Instance.GetClientManager().checkPlayerName(this._player, this._itemName, new Callback()
          {
            public void run(String result)
            {
              AddFriendPage.this._searching = false;
              if (result != null)
              {
                AddFriendPage.this._friends.addFriend(AddFriendPage.this._player, result);
                AddFriendPage.this._player.playSound(AddFriendPage.this._player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.6F);
                
                AddFriendPage.this.unregisterListener();
                new FriendsGUI(AddFriendPage.this._friends, AddFriendPage.this._player);
              }
              else
              {
                AddFriendPage.this._currentInventory.setItem(
                  2, 
                  new ItemBuilder(Material.PAPER).setTitle(
                  C.cYellow + "0" + C.cGray + " matches for [" + C.cYellow + name + C.cGray + "]")
                  .build());
                AddFriendPage.this._player.playSound(AddFriendPage.this._player.getLocation(), Sound.ITEM_BREAK, 1.0F, 0.6F);
              }
            }
          });
        }
        else
        {
          this._player.playSound(this._player.getLocation(), Sound.ITEM_BREAK, 1.0F, 0.6F);
        }
      }
    }
    else if (event.isShiftClick())
    {
      event.setCancelled(true);
    }
  }
  
  public void openInventory()
  {
    this._player.closeInventory();
    
    EntityPlayer p = ((CraftPlayer)this._player).getHandle();
    
    AnvilContainer container = new AnvilContainer(p);
    int c = p.nextContainerCounter();
    
    PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(c, 8, "Repairing", 0, true);
    
    p.playerConnection.sendPacket(packet);
    
    p.activeContainer = container;
    
    p.activeContainer.windowId = c;
    
    p.activeContainer.addSlotListener(p);
    this._currentInventory = container.getBukkitView().getTopInventory();
    
    this._currentInventory.setItem(0, new ItemBuilder(Material.PAPER).setRawTitle("Friend's Name").build());
    this._currentInventory.setItem(2, new ItemBuilder(Material.PAPER).setRawTitle("Search").build());
  }
}

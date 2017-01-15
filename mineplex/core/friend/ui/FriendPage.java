package mineplex.core.friend.ui;

import mineplex.core.common.util.C;
import mineplex.core.itemstack.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum FriendPage
{
  FRIENDS(new ItemBuilder(Material.SKULL_ITEM, 1, (short)3).setTitle("Friends").build(), "Friends"),  FRIEND_REQUESTS(new ItemBuilder(Material.RED_ROSE).setTitle("Friend Requests").build(), "Friend Requests"),  DELETE_FRIENDS(new ItemBuilder(Material.TNT).setTitle("Delete Friends").build(), "Delete Friends"),  SEND_REQUEST(new ItemBuilder(Material.BOOK_AND_QUILL).setTitle("Send Friend Request").build(), "Send Friend Request"),  TOGGLE_DISPLAY(new ItemBuilder(Material.SIGN).setTitle(C.cGray + "Toggle friends to display in chat").build(), 
    "Toggle Display");
  
  private ItemStack _icon;
  private String _name;
  
  private FriendPage(ItemStack item, String name)
  {
    this._icon = item;
    this._name = name;
  }
  
  public String getName()
  {
    return this._name;
  }
  
  public ItemStack getIcon()
  {
    return this._icon;
  }
}

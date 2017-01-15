package mineplex.core.friend.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import mineplex.core.command.CommandCenter;
import mineplex.core.common.util.C;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilInv;
import mineplex.core.common.util.UtilTime;
import mineplex.core.friend.FriendManager;
import mineplex.core.friend.FriendStatusType;
import mineplex.core.friend.data.FriendData;
import mineplex.core.friend.data.FriendStatus;
import mineplex.core.itemstack.ItemBuilder;
import mineplex.core.itemstack.ItemLayout;
import mineplex.core.portal.Portal;
import mineplex.core.shop.item.IButton;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FriendsGUI
  implements Listener
{
  private NautHashMap<Integer, IButton> _buttonMap = new NautHashMap();
  private FriendPage _currentPage = FriendPage.FRIENDS;
  private FriendPage _previousPage;
  private Player _player;
  private FriendManager _plugin;
  private Inventory _inventory;
  private int _page;
  private Comparator<FriendStatus> _friendCompare = new Comparator()
  {
    public int compare(FriendStatus o1, FriendStatus o2)
    {
      if (o1.Online == o2.Online) {
        return o1.Name.compareToIgnoreCase(o2.Name);
      }
      if (o1.Online) {
        return -1;
      }
      return 1;
    }
  };
  
  public FriendsGUI(FriendManager plugin, Player player)
  {
    this._plugin = plugin;
    this._player = player;
    
    buildPage();
    
    this._plugin.registerEvents(this);
    
    getFriendData().setGui(this);
  }
  
  private void AddButton(int slot, ItemStack item, IButton button)
  {
    this._inventory.setItem(slot, item);
    this._buttonMap.put(Integer.valueOf(slot), button);
  }
  
  public void buildDeleteFriends()
  {
    ArrayList<FriendStatus> friends = new ArrayList();
    for (FriendStatus friend : getFriendData().getFriends()) {
      if (friend.Status == FriendStatusType.Accepted) {
        friends.add(friend);
      }
    }
    Collections.sort(friends, this._friendCompare);
    
    boolean pages = addPages(friends.size(), new Runnable()
    {
      public void run()
      {
        FriendsGUI.this.buildDeleteFriends();
      }
    });
    for (int i = 0; i < (pages ? 27 : 36); i++)
    {
      int friendSlot = this._page * 27 + i;
      int slot = i + 18;
      if (friendSlot >= friends.size())
      {
        ItemStack item = this._inventory.getItem(slot);
        if ((item == null) || (item.getType() == Material.AIR)) {
          break;
        }
        this._inventory.setItem(slot, new ItemStack(Material.AIR));
      }
      else
      {
        FriendStatus friend = (FriendStatus)friends.get(friendSlot);
        
        ItemBuilder builder = new ItemBuilder(Material.SKULL_ITEM, 1, (short)(friend.Online ? 3 : 0));
        
        builder.setTitle(C.cWhite + C.Bold + friend.Name);
        builder.setPlayerHead(friend.Name);
        
        builder.addLore(new String[] { C.cGray + C.Bold + "Status: " + (friend.Online ? C.cDGreen + "Online" : new StringBuilder(String.valueOf(C.cRed)).append("Offline").toString()) });
        if (friend.Online) {
          builder.addLore(new String[] { C.cGray + C.Bold + "Server: " + C.cYellow + friend.ServerName });
        } else {
          builder.addLore(new String[] { C.cGray + "Last seen " + UtilTime.MakeStr(friend.LastSeenOnline) + " ago" });
        }
        builder.addLore(new String[] { "" });
        
        builder.addLore(new String[] { C.cGray + "Left click to delete from friends" });
        
        final String name = friend.Name;
        
        AddButton(slot, builder.build(), new IButton()
        {
          public void onClick(Player player, ClickType clickType)
          {
            if (clickType.isLeftClick()) {
              CommandCenter.Instance.OnPlayerCommandPreprocess(new PlayerCommandPreprocessEvent(player, "/unfriend " + 
                name));
            }
          }
        });
      }
    }
  }
  
  private boolean addPages(int amount, final Runnable runnable)
  {
    if (amount > 36)
    {
      if (this._page > 0) {
        AddButton(45, new ItemBuilder(Material.SIGN).setTitle("Previous Page").build(), new IButton()
        {
          public void onClick(Player player, ClickType clickType)
          {
            Iterator<Integer> itel = FriendsGUI.this._buttonMap.keySet().iterator();
            while (itel.hasNext())
            {
              int slot = ((Integer)itel.next()).intValue();
              if (slot > 8)
              {
                itel.remove();
                FriendsGUI.this._inventory.setItem(slot, new ItemStack(Material.AIR));
              }
            }
            FriendsGUI.this._page -= 1;
            runnable.run();
          }
        });
      }
      if (amount > (this._page + 1) * 27) {
        AddButton(53, new ItemBuilder(Material.SIGN).setTitle("Next Page").build(), new IButton()
        {
          public void onClick(Player player, ClickType clickType)
          {
            Iterator<Integer> itel = FriendsGUI.this._buttonMap.keySet().iterator();
            while (itel.hasNext())
            {
              int slot = ((Integer)itel.next()).intValue();
              if (slot > 8)
              {
                itel.remove();
                FriendsGUI.this._inventory.setItem(slot, new ItemStack(Material.AIR));
              }
            }
            FriendsGUI.this._page += 1;
            runnable.run();
          }
        });
      }
      return true;
    }
    return false;
  }
  
  private void buildFriends()
  {
    ArrayList<FriendStatus> friends = new ArrayList();
    for (FriendStatus friend : getFriendData().getFriends()) {
      if (friend.Status == FriendStatusType.Accepted) {
        friends.add(friend);
      }
    }
    Collections.sort(friends, this._friendCompare);
    boolean pages = addPages(friends.size(), new Runnable()
    {
      public void run()
      {
        FriendsGUI.this.buildFriends();
      }
    });
    for (int i = 0; i < (pages ? 27 : 36); i++)
    {
      int friendSlot = this._page * 27 + i;
      int slot = i + 18;
      if (friendSlot >= friends.size())
      {
        ItemStack item = this._inventory.getItem(slot);
        if ((item == null) || (item.getType() == Material.AIR)) {
          break;
        }
        this._inventory.setItem(slot, new ItemStack(Material.AIR));
      }
      else
      {
        FriendStatus friend = (FriendStatus)friends.get(friendSlot);
        
        ItemBuilder builder = new ItemBuilder(Material.SKULL_ITEM, 1, (short)(friend.Online ? 3 : 0));
        
        builder.setTitle(C.cWhite + C.Bold + friend.Name);
        builder.setPlayerHead(friend.Name);
        
        builder.addLore(new String[] { C.cGray + C.Bold + "Status: " + (friend.Online ? C.cDGreen + "Online" : new StringBuilder(String.valueOf(C.cRed)).append("Offline").toString()) });
        if (friend.Online) {
          builder.addLore(new String[] { C.cGray + C.Bold + "Server: " + C.cYellow + friend.ServerName });
        } else {
          builder.addLore(new String[] { C.cGray + "Last seen " + UtilTime.MakeStr(friend.LastSeenOnline) + " ago" });
        }
        if (friend.Online)
        {
          builder.addLore(new String[] { "" });
          builder.addLore(new String[] { C.cGray + "Left click to teleport to their server" });
          
          final String serverName = friend.ServerName;
          
          AddButton(slot, builder.build(), new IButton()
          {
            public void onClick(Player player, ClickType clickType)
            {
              FriendsGUI.this._plugin.getPortal().sendPlayerToServer(player, serverName);
            }
          });
        }
        else
        {
          this._inventory.setItem(slot, builder.build());
        }
      }
    }
  }
  
  public void updateGui()
  {
    if (this._currentPage == FriendPage.FRIENDS) {
      buildFriends();
    } else if (this._currentPage == FriendPage.FRIEND_REQUESTS) {
      buildRequests();
    } else if (this._currentPage == FriendPage.DELETE_FRIENDS) {
      buildDeleteFriends();
    }
  }
  
  private void buildPage()
  {
    if (this._currentPage != this._previousPage)
    {
      this._inventory = Bukkit.createInventory(null, 54, this._currentPage.getName());
    }
    else
    {
      this._inventory.setItem(53, new ItemStack(Material.AIR));
      this._inventory.setItem(45, new ItemStack(Material.AIR));
    }
    this._page = 0;
    this._buttonMap.clear();
    
    ArrayList<Integer> itemSlots = new ItemLayout(new String[] { "OXOXOXOXO" }).getItemSlots();
    for (int i = 0; i < FriendPage.values().length; i++)
    {
      final FriendPage page = FriendPage.values()[i];
      
      ItemStack icon = page == this._currentPage ? 
      
        new ItemBuilder(page.getIcon()).addEnchantment(UtilInv.getDullEnchantment(), 1).build() : 
        
        page.getIcon();
      
      AddButton(((Integer)itemSlots.get(i)).intValue(), icon, new IButton()
      {
        public void onClick(Player player, ClickType clickType)
        {
          if ((FriendsGUI.this._currentPage != page) || (FriendsGUI.this._page != 0))
          {
            FriendsGUI.this._currentPage = page;
            FriendsGUI.this.buildPage();
          }
        }
      });
    }
    if (this._currentPage == FriendPage.FRIENDS)
    {
      buildFriends();
    }
    else if (this._currentPage == FriendPage.FRIEND_REQUESTS)
    {
      buildRequests();
    }
    else if (this._currentPage == FriendPage.DELETE_FRIENDS)
    {
      buildDeleteFriends();
    }
    else
    {
      if (this._currentPage == FriendPage.SEND_REQUEST)
      {
        unregisterListener();
        
        new AddFriendPage(this._plugin, this._player);
        
        return;
      }
      if (this._currentPage == FriendPage.TOGGLE_DISPLAY)
      {
        this._player.closeInventory();
        
        CommandCenter.Instance.OnPlayerCommandPreprocess(new PlayerCommandPreprocessEvent(this._player, "/friendsdisplay"));
        
        return;
      }
    }
    if (this._previousPage != this._currentPage)
    {
      this._previousPage = this._currentPage;
      
      EntityPlayer nmsPlayer = ((CraftPlayer)this._player).getHandle();
      if (nmsPlayer.activeContainer != nmsPlayer.defaultContainer)
      {
        CraftEventFactory.handleInventoryCloseEvent(nmsPlayer);
        nmsPlayer.m();
      }
      this._player.openInventory(this._inventory);
    }
  }
  
  private void buildRequests()
  {
    ArrayList<FriendStatus> friends = new ArrayList();
    for (FriendStatus friend : getFriendData().getFriends()) {
      if ((friend.Status == FriendStatusType.Sent) || (friend.Status == FriendStatusType.Pending)) {
        friends.add(friend);
      }
    }
    Collections.sort(friends, new Comparator()
    {
      public int compare(FriendStatus o1, FriendStatus o2)
      {
        if (o1.Status == o2.Status) {
          return o1.Name.compareToIgnoreCase(o2.Name);
        }
        if (o1.Status == FriendStatusType.Sent) {
          return -1;
        }
        return 1;
      }
    });
    boolean pages = addPages(friends.size(), new Runnable()
    {
      public void run()
      {
        FriendsGUI.this.buildRequests();
      }
    });
    for (int i = 0; i < (pages ? 27 : 36); i++)
    {
      int friendSlot = this._page * 27 + i;
      final int slot = i + 18;
      if (friendSlot >= friends.size())
      {
        ItemStack item = this._inventory.getItem(slot);
        if ((item == null) || (item.getType() == Material.AIR)) {
          break;
        }
        this._inventory.setItem(slot, new ItemStack(Material.AIR));
      }
      else
      {
        FriendStatus friend = (FriendStatus)friends.get(friendSlot);
        
        final boolean isSender = friend.Status == FriendStatusType.Sent;
        ItemBuilder builder;
        if (isSender)
        {
          ItemBuilder builder = new ItemBuilder(Material.ENDER_PEARL);
          
          builder.setTitle(C.cGray + "Friend request to " + C.cWhite + C.Bold + friend.Name);
        }
        else
        {
          builder = new ItemBuilder(Material.PAPER);
          
          builder.setTitle(C.cGray + "Friend request from " + C.cWhite + C.Bold + friend.Name);
        }
        builder.addLore(new String[] { "" });
        
        builder.addLore(new String[] {C.cGray + (isSender ? "C" : "Left c") + "lick to " + (isSender ? "cancel" : "accept") + 
          " friend request" });
        if (!isSender) {
          builder.addLore(new String[] { C.cGray + "Right click to refuse friend request" });
        }
        final String name = friend.Name;
        
        AddButton(slot, builder.build(), new IButton()
        {
          public void onClick(Player player, ClickType clickType)
          {
            if ((isSender) || (clickType.isRightClick()))
            {
              FriendsGUI.this._plugin.removeFriend(FriendsGUI.this._player, name);
              
              FriendsGUI.this._inventory.setItem(slot, new ItemStack(Material.AIR));
              FriendsGUI.this._buttonMap.remove(Integer.valueOf(slot));
            }
            else if ((!isSender) && (clickType.isLeftClick()))
            {
              FriendsGUI.this._plugin.addFriend(FriendsGUI.this._player, name);
              
              FriendsGUI.this._inventory.setItem(slot, new ItemStack(Material.AIR));
              FriendsGUI.this._buttonMap.remove(Integer.valueOf(slot));
            }
          }
        });
      }
    }
  }
  
  private FriendData getFriendData()
  {
    return (FriendData)this._plugin.Get(this._player);
  }
  
  @EventHandler
  public void OnInventoryClick(InventoryClickEvent event)
  {
    if ((this._inventory.getTitle().equals(event.getInventory().getTitle())) && (event.getWhoClicked() == this._player))
    {
      if (this._buttonMap.containsKey(Integer.valueOf(event.getRawSlot())))
      {
        if ((event.getWhoClicked() instanceof Player))
        {
          IButton button = (IButton)this._buttonMap.get(Integer.valueOf(event.getRawSlot()));
          
          button.onClick((Player)event.getWhoClicked(), event.getClick());
          
          this._player.playSound(this._player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.6F);
        }
      }
      else {
        this._player.playSound(this._player.getLocation(), Sound.ITEM_BREAK, 1.0F, 0.6F);
      }
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void OnInventoryClose(InventoryCloseEvent event)
  {
    if ((this._inventory.getTitle().equals(event.getInventory().getTitle())) && (event.getPlayer() == this._player)) {
      unregisterListener();
    }
  }
  
  private void unregisterListener()
  {
    FriendData data = getFriendData();
    if ((data != null) && (data.getGui() == this)) {
      data.setGui(null);
    }
    HandlerList.unregisterAll(this);
  }
}

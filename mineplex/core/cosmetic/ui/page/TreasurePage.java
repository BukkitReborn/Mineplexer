package mineplex.core.cosmetic.ui.page;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import mineplex.core.account.CoreClientManager;
import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.donation.DonationManager;
import mineplex.core.inventory.ClientInventory;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import mineplex.core.shop.page.ShopPageBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TreasurePage
  extends ShopPageBase<CosmeticManager, CosmeticShop>
{
  private static final int[] ROTATION_SLOTS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35, 34, 33, 32, 31, 30, 29, 28, 27, 18, 9 };
  private static final List<Integer> CHEST_SLOTS = Arrays.asList(new Integer[] { Integer.valueOf(21), Integer.valueOf(24), Integer.valueOf(20), Integer.valueOf(22), Integer.valueOf(23) });
  private static final List<ChatColor> CHEST_COLORS = Arrays.asList(new ChatColor[] { ChatColor.RED, ChatColor.GREEN, ChatColor.YELLOW, ChatColor.BLUE, ChatColor.AQUA, ChatColor.GOLD });
  private int _ticks;
  private Random _random;
  private short _rotationColorOne = 0;
  private short _rotationColorTwo = 0;
  private boolean _rotationForwardOne = true;
  private boolean _rotationForwardTwo = false;
  private int _currentIndexOne = 4;
  private int _currentIndexTwo = 4;
  public boolean _canSelectChest = false;
  private LinkedList<ChatColor> _colors;
  private LinkedList<Integer> _chestSlots;
  
  public TreasurePage(CosmeticManager plugin, CosmeticShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    super(plugin, shop, clientManager, donationManager, name, player, 36);
    this._random = new Random();
    
    this._colors = new LinkedList(CHEST_COLORS);
    this._chestSlots = new LinkedList(CHEST_SLOTS);
    Collections.shuffle(this._colors, this._random);
    Collections.shuffle(this._chestSlots, this._random);
  }
  
  protected void buildPage()
  {
    int treasureCount = ((ClientInventory)((CosmeticManager)getPlugin()).getInventoryManager().Get(getPlayer())).getItemCount("Treasure Chest");
    
    this._rotationColorOne = (this._ticks % 2 == 0 ? (short)this._random.nextInt(15) : this._rotationColorOne);
    this._rotationColorTwo = (this._ticks % 20 == 0 ? (short)this._random.nextInt(15) : this._rotationColorTwo);
    ItemStack borderPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)(this._canSelectChest ? 7 : 15));
    for (int row = 0; row < 4; row++) {
      if ((row == 0) || (row == 3))
      {
        for (int column = 0; column < 9; column++) {
          setItem(column, row, borderPane);
        }
      }
      else
      {
        setItem(0, row, borderPane);
        setItem(8, row, borderPane);
      }
    }
    if (this._ticks <= 21) {
      rotateBorderPanes();
    }
    if (this._ticks == 0)
    {
      getPlayer().playSound(getPlayer().getEyeLocation(), Sound.ANVIL_USE, 4.0F, 1.0F);
    }
    else if (this._ticks == 20)
    {
      getPlayer().playSound(getPlayer().getEyeLocation(), Sound.CHEST_OPEN, 4.0F, 1.0F);
    }
    else if ((this._ticks >= 30) && (this._ticks <= 120) && (this._ticks % 20 == 0))
    {
      ChatColor color = (ChatColor)this._colors.poll();
      String colorName = color.name().toLowerCase();
      colorName = colorName.substring(0, 1).toUpperCase() + colorName.substring(1);
      String chestName = color + colorName + " Chest";
      String[] lore = { ChatColor.RESET.toString() + ChatColor.WHITE + "Click to Open" };
      
      getPlayer().playSound(getPlayer().getEyeLocation(), Sound.NOTE_PLING, 4.0F, 1.0F);
      int slot = ((Integer)this._chestSlots.poll()).intValue();
      addButton(slot, new ShopItem(Material.CHEST, chestName, lore, 1, false), new IButton()
      {
        public void onClick(Player player, ClickType clickType)
        {
          if (TreasurePage.this._canSelectChest) {
            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
          }
        }
      });
    }
    else if (this._ticks == 140)
    {
      getPlayer().playSound(getPlayer().getEyeLocation(), Sound.LEVEL_UP, 4.0F, 1.0F);
      ItemStack is = new ItemStack(Material.BOOK);
      ItemMeta meta = is.getItemMeta();
      meta.setDisplayName(ChatColor.RESET.toString() + "Select a Chest");
      is.setItemMeta(meta);
      
      setItem(13, is);
      addGlow(13);
      
      this._canSelectChest = true;
    }
    this._ticks += 1;
  }
  
  public void rotateBorderPanes()
  {
    ItemStack whitePane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
    ItemStack paneOne = new ItemStack(Material.STAINED_GLASS_PANE, 1, this._rotationColorOne);
    ItemStack paneTwo = new ItemStack(Material.STAINED_GLASS_PANE, 1, this._rotationColorTwo);
    
    this._currentIndexOne = ((this._currentIndexOne + (this._rotationForwardOne ? 1 : -1)) % ROTATION_SLOTS.length);
    if (this._currentIndexOne < 0) {
      this._currentIndexOne += ROTATION_SLOTS.length;
    }
    this._currentIndexTwo = ((this._currentIndexTwo + (this._rotationForwardTwo ? 1 : -1)) % ROTATION_SLOTS.length);
    if (this._currentIndexTwo < 0) {
      this._currentIndexTwo += ROTATION_SLOTS.length;
    }
    if (this._currentIndexOne == this._currentIndexTwo)
    {
      setItem(ROTATION_SLOTS[this._currentIndexOne], whitePane);
    }
    else
    {
      setItem(ROTATION_SLOTS[this._currentIndexOne], paneOne);
      setItem(ROTATION_SLOTS[this._currentIndexTwo], paneTwo);
    }
  }
  
  public void update()
  {
    buildPage();
  }
}

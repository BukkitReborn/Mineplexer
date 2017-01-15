package mineplex.core.shop.page;

import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.C;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.donation.DonationManager;
import mineplex.core.server.util.TransactionResponse;
import mineplex.core.shop.ShopBase;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.SalesPackageBase;
import mineplex.core.shop.item.ShopItem;
import net.minecraft.server.v1_7_R4.IInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.scheduler.BukkitScheduler;

public class ConfirmationPage<PluginType extends MiniPlugin, ShopType extends ShopBase<PluginType>>
  extends ShopPageBase<PluginType, ShopType>
  implements Runnable
{
  private Runnable _runnable;
  private ShopPageBase<PluginType, ShopType> _returnPage;
  private SalesPackageBase _salesItem;
  private int _okSquareSlotStart;
  private boolean _processing;
  private int _progressCount;
  private ShopItem _progressItem;
  private int _taskId;
  
  public ConfirmationPage(PluginType plugin, ShopType shop, CoreClientManager clientManager, DonationManager donationManager, Runnable runnable, ShopPageBase<PluginType, ShopType> returnPage, SalesPackageBase salesItem, CurrencyType currencyType, Player player)
  {
    super(plugin, shop, clientManager, donationManager, "            Confirmation", player);
    
    this._runnable = runnable;
    this._returnPage = returnPage;
    this._salesItem = salesItem;
    setCurrencyType(currencyType);
    this._progressItem = new ShopItem(Material.LAPIS_BLOCK, (byte)11, ChatColor.BLUE + "Processing", null, 1, false, true);
    this._okSquareSlotStart = 27;
    if (getShop().canPlayerAttemptPurchase(player))
    {
      buildPage();
    }
    else
    {
      buildErrorPage(new String[] { ChatColor.RED + "You have attempted too many invalid transactions.", ChatColor.RED + "Please wait 10 seconds before retrying." });
      this._taskId = plugin.getScheduler().scheduleAsyncRepeatingTask(plugin.getPlugin(), this, 2L, 2L);
    }
  }
  
  protected void buildPage()
  {
    getInventory().setItem(22, new ShopItem(this._salesItem.GetDisplayMaterial(), (byte)0, this._salesItem.GetDisplayName(), this._salesItem.GetDescription(), 1, false, true).getHandle());
    
    IButton okClicked = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ConfirmationPage.this.okClicked(player);
      }
    };
    IButton cancelClicked = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ConfirmationPage.this.cancelClicked(player);
      }
    };
    buildSquareAt(this._okSquareSlotStart, new ShopItem(Material.EMERALD_BLOCK, (byte)0, ChatColor.GREEN + "OK", null, 1, false, true), okClicked);
    buildSquareAt(this._okSquareSlotStart + 6, new ShopItem(Material.REDSTONE_BLOCK, (byte)0, ChatColor.RED + "CANCEL", null, 1, false, true), cancelClicked);
    
    getInventory().setItem(4, new ShopItem(getCurrencyType().GetDisplayMaterial(), (byte)0, getCurrencyType().toString(), new String[] { C.cGray + this._salesItem.GetCost(getCurrencyType()) + " " + getCurrencyType().toString() + " will be deducted from your account balance." }, 1, false, true).getHandle());
  }
  
  protected void okClicked(Player player)
  {
    processTransaction();
  }
  
  protected void cancelClicked(Player player)
  {
    getPlugin().getScheduler().cancelTask(this._taskId);
    if (this._returnPage != null) {
      getShop().openPageForPlayer(player, this._returnPage);
    } else {
      player.closeInventory();
    }
  }
  
  private void buildSquareAt(int slot, ShopItem item, IButton button)
  {
    addButton(slot, item, button);
    addButton(slot + 1, item, button);
    addButton(slot + 2, item, button);
    
    slot += 9;
    
    addButton(slot, item, button);
    addButton(slot + 1, item, button);
    addButton(slot + 2, item, button);
    
    slot += 9;
    
    addButton(slot, item, button);
    addButton(slot + 1, item, button);
    addButton(slot + 2, item, button);
  }
  
  private void processTransaction()
  {
    for (int i = this._okSquareSlotStart; i < 54; i++)
    {
      getButtonMap().remove(Integer.valueOf(i));
      clear(i);
    }
    this._processing = true;
    if (this._salesItem.IsKnown()) {
      getDonationManager().PurchaseKnownSalesPackage(new Callback()
      {
        public void run(TransactionResponse response)
        {
          ConfirmationPage.this.showResultsPage(response);
        }
      }, getPlayer().getName(), getPlayer().getUniqueId(), this._salesItem.GetCost(getCurrencyType()), this._salesItem.GetSalesPackageId());
    } else {
      getDonationManager().PurchaseUnknownSalesPackage(new Callback()
      {
        public void run(TransactionResponse response)
        {
          ConfirmationPage.this.showResultsPage(response);
        }
      }, getPlayer().getName(), getClientManager().Get(getPlayer()).getAccountId(), this._salesItem.GetName(), getCurrencyType() == CurrencyType.Coins, this._salesItem.GetCost(getCurrencyType()), this._salesItem.OneTimePurchase());
    }
    this._taskId = getPlugin().getScheduler().scheduleAsyncRepeatingTask(getPlugin().getPlugin(), this, 2L, 2L);
  }
  
  private void showResultsPage(TransactionResponse response)
  {
    this._processing = false;
    switch (response)
    {
    case InsufficientFunds: 
      buildErrorPage(new String[] { ChatColor.RED + "There was an error processing your request." });
      getShop().addPlayerProcessError(getPlayer());
      break;
    case Success: 
      buildErrorPage(new String[] { ChatColor.RED + "You already own this package." });
      getShop().addPlayerProcessError(getPlayer());
      break;
    case AlreadyOwns: 
      buildErrorPage(new String[] { ChatColor.RED + "Your account has insufficient funds." });
      getShop().addPlayerProcessError(getPlayer());
      break;
    case Failed: 
      this._salesItem.Sold(getPlayer(), getCurrencyType());
      
      buildSuccessPage("Your purchase was successful.");
      if (this._runnable != null) {
        this._runnable.run();
      }
      break;
    }
    this._progressCount = 0;
  }
  
  private void buildErrorPage(String... message)
  {
    IButton returnButton = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ConfirmationPage.this.cancelClicked(player);
      }
    };
    ShopItem item = new ShopItem(Material.REDSTONE_BLOCK, (byte)0, ChatColor.RED + ChatColor.UNDERLINE + "ERROR", message, 1, false, true);
    for (int i = 0; i < getSize(); i++) {
      addButton(i, item, returnButton);
    }
    getPlayer().playSound(getPlayer().getLocation(), Sound.BLAZE_DEATH, 1.0F, 0.1F);
  }
  
  private void buildSuccessPage(String message)
  {
    IButton returnButton = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ConfirmationPage.this.cancelClicked(player);
      }
    };
    ShopItem item = new ShopItem(Material.EMERALD_BLOCK, (byte)0, ChatColor.GREEN + message, null, 1, false, true);
    for (int i = 0; i < getSize(); i++) {
      addButton(i, item, returnButton);
    }
    getPlayer().playSound(getPlayer().getLocation(), Sound.NOTE_PLING, 1.0F, 0.9F);
  }
  
  public void playerClosed()
  {
    super.playerClosed();
    
    Bukkit.getScheduler().cancelTask(this._taskId);
    if ((this._returnPage != null) && (getShop() != null)) {
      getShop().setCurrentPageForPlayer(getPlayer(), this._returnPage);
    }
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 289	mineplex/core/shop/page/ConfirmationPage:_processing	Z
    //   4: ifeq +55 -> 59
    //   7: aload_0
    //   8: getfield 382	mineplex/core/shop/page/ConfirmationPage:_progressCount	I
    //   11: bipush 9
    //   13: if_icmpne +28 -> 41
    //   16: bipush 45
    //   18: istore_1
    //   19: goto +11 -> 30
    //   22: aload_0
    //   23: iload_1
    //   24: invokevirtual 286	mineplex/core/shop/page/ConfirmationPage:clear	(I)V
    //   27: iinc 1 1
    //   30: iload_1
    //   31: bipush 54
    //   33: if_icmplt -11 -> 22
    //   36: aload_0
    //   37: iconst_0
    //   38: putfield 382	mineplex/core/shop/page/ConfirmationPage:_progressCount	I
    //   41: aload_0
    //   42: bipush 45
    //   44: aload_0
    //   45: getfield 382	mineplex/core/shop/page/ConfirmationPage:_progressCount	I
    //   48: iadd
    //   49: aload_0
    //   50: getfield 79	mineplex/core/shop/page/ConfirmationPage:_progressItem	Lmineplex/core/shop/item/ShopItem;
    //   53: invokevirtual 433	mineplex/core/shop/page/ConfirmationPage:setItem	(ILorg/bukkit/inventory/ItemStack;)V
    //   56: goto +98 -> 154
    //   59: aload_0
    //   60: getfield 382	mineplex/core/shop/page/ConfirmationPage:_progressCount	I
    //   63: bipush 20
    //   65: if_icmplt +89 -> 154
    //   68: invokestatic 427	org/bukkit/Bukkit:getScheduler	()Lorg/bukkit/scheduler/BukkitScheduler;
    //   71: aload_0
    //   72: getfield 127	mineplex/core/shop/page/ConfirmationPage:_taskId	I
    //   75: invokeinterface 253 2 0
    //   80: aload_0
    //   81: getfield 36	mineplex/core/shop/page/ConfirmationPage:_returnPage	Lmineplex/core/shop/page/ShopPageBase;
    //   84: ifnull +28 -> 112
    //   87: aload_0
    //   88: invokevirtual 83	mineplex/core/shop/page/ConfirmationPage:getShop	()Lmineplex/core/shop/ShopBase;
    //   91: ifnull +21 -> 112
    //   94: aload_0
    //   95: invokevirtual 83	mineplex/core/shop/page/ConfirmationPage:getShop	()Lmineplex/core/shop/ShopBase;
    //   98: aload_0
    //   99: invokevirtual 302	mineplex/core/shop/page/ConfirmationPage:getPlayer	()Lorg/bukkit/entity/Player;
    //   102: aload_0
    //   103: getfield 36	mineplex/core/shop/page/ConfirmationPage:_returnPage	Lmineplex/core/shop/page/ShopPageBase;
    //   106: invokevirtual 257	mineplex/core/shop/ShopBase:openPageForPlayer	(Lorg/bukkit/entity/Player;Lmineplex/core/shop/page/ShopPageBase;)V
    //   109: goto +41 -> 150
    //   112: aload_0
    //   113: invokevirtual 302	mineplex/core/shop/page/ConfirmationPage:getPlayer	()Lorg/bukkit/entity/Player;
    //   116: ifnull +34 -> 150
    //   119: aload_0
    //   120: invokevirtual 302	mineplex/core/shop/page/ConfirmationPage:getPlayer	()Lorg/bukkit/entity/Player;
    //   123: invokeinterface 261 1 0
    //   128: goto +22 -> 150
    //   131: astore_1
    //   132: aload_1
    //   133: invokevirtual 436	java/lang/Exception:printStackTrace	()V
    //   136: aload_0
    //   137: invokevirtual 441	mineplex/core/shop/page/ConfirmationPage:dispose	()V
    //   140: goto +14 -> 154
    //   143: astore_2
    //   144: aload_0
    //   145: invokevirtual 441	mineplex/core/shop/page/ConfirmationPage:dispose	()V
    //   148: aload_2
    //   149: athrow
    //   150: aload_0
    //   151: invokevirtual 441	mineplex/core/shop/page/ConfirmationPage:dispose	()V
    //   154: aload_0
    //   155: dup
    //   156: getfield 382	mineplex/core/shop/page/ConfirmationPage:_progressCount	I
    //   159: iconst_1
    //   160: iadd
    //   161: putfield 382	mineplex/core/shop/page/ConfirmationPage:_progressCount	I
    //   164: return
    // Line number table:
    //   Java source line #244	-> byte code offset #0
    //   Java source line #246	-> byte code offset #7
    //   Java source line #248	-> byte code offset #16
    //   Java source line #250	-> byte code offset #22
    //   Java source line #248	-> byte code offset #27
    //   Java source line #253	-> byte code offset #36
    //   Java source line #256	-> byte code offset #41
    //   Java source line #257	-> byte code offset #56
    //   Java source line #260	-> byte code offset #59
    //   Java source line #264	-> byte code offset #68
    //   Java source line #266	-> byte code offset #80
    //   Java source line #268	-> byte code offset #94
    //   Java source line #269	-> byte code offset #109
    //   Java source line #270	-> byte code offset #112
    //   Java source line #272	-> byte code offset #119
    //   Java source line #274	-> byte code offset #128
    //   Java source line #275	-> byte code offset #131
    //   Java source line #277	-> byte code offset #132
    //   Java source line #281	-> byte code offset #136
    //   Java source line #280	-> byte code offset #143
    //   Java source line #281	-> byte code offset #144
    //   Java source line #282	-> byte code offset #148
    //   Java source line #281	-> byte code offset #150
    //   Java source line #282	-> byte code offset #154
    //   Java source line #286	-> byte code offset #161
    //   Java source line #287	-> byte code offset #164
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	165	0	this	ConfirmationPage<PluginType, ShopType>
    //   18	13	1	i	int
    //   131	2	1	exception	Exception
    //   143	6	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   68	128	131	java/lang/Exception
    //   68	136	143	finally
  }
  
  public void dispose()
  {
    super.dispose();
    
    Bukkit.getScheduler().cancelTask(this._taskId);
  }
}

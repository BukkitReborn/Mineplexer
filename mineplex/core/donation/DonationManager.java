package mineplex.core.donation;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import mineplex.core.MiniDbClientPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.account.event.ClientWebResponseEvent;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.donation.command.CoinCommand;
import mineplex.core.donation.command.GemCommand;
import mineplex.core.donation.command.GoldCommand;
import mineplex.core.donation.command.ShardsCommand;
import mineplex.core.donation.repository.DonationRepository;
import mineplex.core.donation.repository.token.DonorTokenWrapper;
import mineplex.core.server.util.TransactionResponse;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class DonationManager
  extends MiniDbClientPlugin<Donor>
{
  private DonationRepository _repository;
  private NautHashMap<Player, NautHashMap<String, Integer>> _gemQueue = new NautHashMap();
  private NautHashMap<Player, NautHashMap<String, Integer>> _coinQueue = new NautHashMap();
  private NautHashMap<Player, NautHashMap<String, Integer>> _goldQueue = new NautHashMap();
  
  public DonationManager(JavaPlugin plugin, CoreClientManager clientManager, String webAddress)
  {
    super("Donation", plugin, clientManager);
    
    this._repository = new DonationRepository(plugin, webAddress);
  }
  
  public void addCommands()
  {
    addCommand(new GemCommand(this));
    addCommand(new CoinCommand(this));
    addCommand(new ShardsCommand(this));
    addCommand(new GoldCommand(this));
  }
  
  @EventHandler
  public void OnClientWebResponse(ClientWebResponseEvent event)
  {
    DonorTokenWrapper token = (DonorTokenWrapper)new Gson().fromJson(event.GetResponse(), DonorTokenWrapper.class);
    LoadDonor(token, event.getUniqueId());
  }
  
  private void LoadDonor(DonorTokenWrapper token, UUID uuid)
  {
    ((Donor)Get(token.Name)).loadToken(token.DonorToken);
  }
  
  public void PurchaseUnknownSalesPackage(final Callback<TransactionResponse> callback, String name, int accountId, final String packageName, final boolean coinPurchase, final int cost, boolean oneTimePurchase)
  {
    final Donor donor = Bukkit.getPlayerExact(name) != null ? (Donor)Get(name) : null;
    if (donor != null) {
      if ((oneTimePurchase) && (donor.OwnsUnknownPackage(packageName)))
      {
        if (callback != null) {
          callback.run(TransactionResponse.AlreadyOwns);
        }
        return;
      }
    }
    this._repository.PurchaseUnknownSalesPackage(new Callback()
    {
      public void run(TransactionResponse response)
      {
        if (response == TransactionResponse.Success) {
          if (donor != null)
          {
            donor.AddUnknownSalesPackagesOwned(packageName);
            donor.DeductCost(cost, coinPurchase ? CurrencyType.Coins : CurrencyType.Gems);
          }
        }
        if (callback != null) {
          callback.run(response);
        }
      }
    }, name, accountId, packageName, coinPurchase, cost);
  }
  
  public void PurchaseKnownSalesPackage(final Callback<TransactionResponse> callback, final String name, UUID uuid, int cost, final int salesPackageId)
  {
    this._repository.PurchaseKnownSalesPackage(new Callback()
    {
      public void run(TransactionResponse response)
      {
        if (response == TransactionResponse.Success)
        {
          Donor donor = (Donor)DonationManager.this.Get(name);
          if (donor != null) {
            donor.AddSalesPackagesOwned(salesPackageId);
          }
        }
        if (callback != null) {
          callback.run(response);
        }
      }
    }, name, uuid.toString(), cost, salesPackageId);
  }
  
  public void RewardGems(Callback<Boolean> callback, String caller, String name, UUID uuid, int amount)
  {
    RewardGems(callback, caller, name, uuid, amount, true);
  }
  
  public void RewardGems(final Callback<Boolean> callback, String caller, final String name, UUID uuid, final int amount, final boolean updateTotal)
  {
    this._repository.gemReward(new Callback()
    {
      public void run(Boolean success)
      {
        if (success.booleanValue()) {
          if (updateTotal)
          {
            Donor donor = (Donor)DonationManager.this.Get(name);
            if (donor != null) {
              donor.AddGems(amount);
            }
          }
        }
        if (callback != null) {
          callback.run(success);
        }
      }
    }, caller, name, uuid.toString(), amount);
  }
  
  public void RewardGemsLater(String caller, Player player, int amount)
  {
    if (!this._gemQueue.containsKey(player)) {
      this._gemQueue.put(player, new NautHashMap());
    }
    int totalAmount = amount;
    if (((NautHashMap)this._gemQueue.get(player)).containsKey(caller)) {
      totalAmount += ((Integer)((NautHashMap)this._gemQueue.get(player)).get(caller)).intValue();
    }
    ((NautHashMap)this._gemQueue.get(player)).put(caller, Integer.valueOf(totalAmount));
    
    Donor donor = (Donor)Get(player.getName());
    if (donor != null) {
      donor.AddGems(amount);
    }
  }
  
  @EventHandler
  public void UpdateGemQueue(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOWER) {
      return;
    }
    for (Player player : this._gemQueue.keySet())
    {
      String caller = null;
      int total = 0;
      for (String curCaller : ((NautHashMap)this._gemQueue.get(player)).keySet())
      {
        caller = curCaller;
        total += ((Integer)((NautHashMap)this._gemQueue.get(player)).get(curCaller)).intValue();
      }
      if (caller != null)
      {
        RewardGems(null, caller, player.getName(), player.getUniqueId(), total, false);
        
        System.out.println("Queue Added [" + player + "] with Gems [" + total + "] for [" + caller + "]");
        
        ((NautHashMap)this._gemQueue.get(player)).clear();
      }
    }
    this._gemQueue.clear();
  }
  
  public void RewardCoins(Callback<Boolean> callback, String caller, String name, int accountId, int amount)
  {
    RewardCoins(callback, caller, name, accountId, amount, true);
  }
  
  public void RewardCoins(final Callback<Boolean> callback, String caller, final String name, int accountId, final int amount, final boolean updateTotal)
  {
    this._repository.rewardCoins(new Callback()
    {
      public void run(Boolean success)
      {
        if (success.booleanValue()) {
          if (updateTotal)
          {
            Donor donor = (Donor)DonationManager.this.Get(name);
            if (donor != null) {
              donor.addCoins(amount);
            }
          }
        }
        if (callback != null) {
          callback.run(success);
        }
      }
    }, caller, name, accountId, amount);
  }
  
  public void RewardCoinsLater(String caller, Player player, int amount)
  {
    if (!this._coinQueue.containsKey(player)) {
      this._coinQueue.put(player, new NautHashMap());
    }
    int totalAmount = amount;
    if (((NautHashMap)this._coinQueue.get(player)).containsKey(caller)) {
      totalAmount += ((Integer)((NautHashMap)this._coinQueue.get(player)).get(caller)).intValue();
    }
    ((NautHashMap)this._coinQueue.get(player)).put(caller, Integer.valueOf(totalAmount));
    
    Donor donor = (Donor)Get(player.getName());
    if (donor != null) {
      donor.addCoins(amount);
    }
  }
  
  @EventHandler
  public void UpdateCoinQueue(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOWER) {
      return;
    }
    for (final Player player : this._coinQueue.keySet())
    {
      String tempCaller = null;
      int tempTotal = 0;
      for (String curCaller : ((NautHashMap)this._coinQueue.get(player)).keySet())
      {
        tempCaller = curCaller;
        tempTotal += ((Integer)((NautHashMap)this._coinQueue.get(player)).get(curCaller)).intValue();
      }
      final int total = tempTotal;
      final String caller = tempCaller;
      if (caller != null)
      {
        if ((player.isOnline()) && (player.isValid())) {
          RewardCoins(null, caller, player.getName(), this.ClientManager.Get(player).getAccountId(), total, false);
        } else {
          Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
          {
            public void run()
            {
              DonationManager.this.RewardCoins(null, caller, player.getName(), DonationManager.this.ClientManager.getCachedClientAccountId(player.getUniqueId()), total, false);
            }
          });
        }
        System.out.println("Queue Added [" + player + "] with Coins [" + total + "] for [" + caller + "]");
        
        ((NautHashMap)this._coinQueue.get(player)).clear();
      }
    }
    this._coinQueue.clear();
  }
  
  public void RewardGold(Callback<Boolean> callback, String caller, String name, int accountId, int amount)
  {
    RewardGold(callback, caller, name, accountId, amount, true);
  }
  
  public void RewardGold(final Callback<Boolean> callback, String caller, final String name, int accountId, final int amount, final boolean updateTotal)
  {
    this._repository.rewardGold(new Callback()
    {
      public void run(Boolean success)
      {
        if (success.booleanValue())
        {
          if (updateTotal)
          {
            Donor donor = (Donor)DonationManager.this.Get(name);
            if (donor != null) {
              donor.addGold(amount);
            }
          }
        }
        else {
          System.out.println("REWARD GOLD FAILED...");
        }
        if (callback != null) {
          callback.run(Boolean.valueOf(true));
        }
      }
    }, caller, name, accountId, amount);
  }
  
  public void RewardGoldLater(String caller, Player player, int amount)
  {
    if (!this._goldQueue.containsKey(player)) {
      this._goldQueue.put(player, new NautHashMap());
    }
    int totalAmount = amount;
    if (((NautHashMap)this._goldQueue.get(player)).containsKey(caller)) {
      totalAmount += ((Integer)((NautHashMap)this._goldQueue.get(player)).get(caller)).intValue();
    }
    ((NautHashMap)this._goldQueue.get(player)).put(caller, Integer.valueOf(totalAmount));
    
    Donor donor = (Donor)Get(player.getName());
    if (donor != null) {
      donor.addGold(amount);
    }
  }
  
  @EventHandler
  public void UpdateGoldQueue(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOWER) {
      return;
    }
    for (Player player : this._goldQueue.keySet())
    {
      String caller = null;
      int total = 0;
      for (String curCaller : ((NautHashMap)this._goldQueue.get(player)).keySet())
      {
        caller = curCaller;
        total += ((Integer)((NautHashMap)this._goldQueue.get(player)).get(curCaller)).intValue();
      }
      if (caller != null)
      {
        RewardGold(null, caller, player.getName(), this.ClientManager.Get(player).getAccountId(), total, false);
        
        System.out.println("Queue Added [" + player + "] with Gold [" + total + "] for [" + caller + "]");
        
        ((NautHashMap)this._goldQueue.get(player)).clear();
      }
    }
    this._goldQueue.clear();
  }
  
  public void applyKits(String playerName)
  {
    this._repository.applyKits(playerName);
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    ((Donor)Get(playerName)).addGold(this._repository.retrieveDonorInfo(resultSet).getGold());
  }
  
  protected Donor AddPlayer(String player)
  {
    return new Donor();
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return "SELECT gold FROM accounts WHERE id = '" + accountId + "';";
  }
}

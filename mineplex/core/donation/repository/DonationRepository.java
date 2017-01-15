package mineplex.core.donation.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import mineplex.core.common.util.Callback;
import mineplex.core.database.DBPool;
import mineplex.core.database.DatabaseRunnable;
import mineplex.core.database.RepositoryBase;
import mineplex.core.database.column.Column;
import mineplex.core.database.column.ColumnInt;
import mineplex.core.donation.Donor;
import mineplex.core.donation.repository.token.GemRewardToken;
import mineplex.core.donation.repository.token.PurchaseToken;
import mineplex.core.donation.repository.token.UnknownPurchaseToken;
import mineplex.core.server.remotecall.AsyncJsonWebCall;
import mineplex.core.server.remotecall.JsonWebCall;
import mineplex.core.server.util.TransactionResponse;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class DonationRepository
  extends RepositoryBase
{
  private static String CREATE_COIN_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS accountCoinTransactions (id INT NOT NULL AUTO_INCREMENT, accountId INT, reason VARCHAR(100), coins INT, PRIMARY KEY (id), FOREIGN KEY (accountId) REFERENCES accounts(id));";
  private static String CREATE_GEM_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS accountGemTransactions (id INT NOT NULL AUTO_INCREMENT, accountId INT, reason VARCHAR(100), gems INT, PRIMARY KEY (id), FOREIGN KEY (accountId) REFERENCES accounts(id));";
  private static String INSERT_COIN_TRANSACTION = "INSERT INTO accountCoinTransactions(accountId, reason, coins) VALUES(?, ?, ?);";
  private static String UPDATE_ACCOUNT_COINS = "UPDATE accounts SET coins = coins + ? WHERE id = ?;";
  private static String UPDATE_ACCOUNT_GOLD = "UPDATE accounts SET gold = gold + ? WHERE id = ?;";
  private static String UPDATE_NULL_ACCOUNT_GEMS_AND_COINS_ = "UPDATE accounts SET gems = ?, coins = ? WHERE id = ? AND gems IS NULL AND coins IS NULL;";
  private String _webAddress;
  
  public DonationRepository(JavaPlugin plugin, String webAddress)
  {
    super(plugin, DBPool.ACCOUNT);
    
    this._webAddress = webAddress;
  }
  
  public void PurchaseKnownSalesPackage(final Callback<TransactionResponse> callback, String name, String uuid, int cost, int salesPackageId)
  {
    final PurchaseToken token = new PurchaseToken();
    token.AccountName = name;
    token.UsingCredits = false;
    token.SalesPackageId = salesPackageId;
    
    final Callback<TransactionResponse> extraCallback = new Callback()
    {
      public void run(final TransactionResponse response)
      {
        Bukkit.getServer().getScheduler().runTask(DonationRepository.this.Plugin, new Runnable()
        {
          public void run()
          {
            this.val$callback.run(response);
          }
        });
      }
    };
    handleDatabaseCall(new DatabaseRunnable(new Runnable()
    {
      public void run()
      {
        new JsonWebCall(DonationRepository.this._webAddress + "PlayerAccount/PurchaseKnownSalesPackage.php").Execute(TransactionResponse.class, extraCallback, token);
      }
    }), "Error purchasing known sales package in DonationRepository : ");
  }
  
  public void PurchaseUnknownSalesPackage(final Callback<TransactionResponse> callback, String name, final int accountId, String packageName, final boolean coinPurchase, final int cost)
  {
    final UnknownPurchaseToken token = new UnknownPurchaseToken();
    token.AccountName = name;
    token.SalesPackageName = packageName;
    token.CoinPurchase = coinPurchase;
    token.Cost = cost;
    token.Premium = false;
    
    final Callback<TransactionResponse> extraCallback = new Callback()
    {
      public void run(final TransactionResponse response)
      {
        if (response == TransactionResponse.Success) {
          if (coinPurchase) {
            DonationRepository.this.executeUpdate(DonationRepository.UPDATE_ACCOUNT_COINS, new Column[] { new ColumnInt("coins", -cost), new ColumnInt("id", accountId) });
          }
        }
        Bukkit.getServer().getScheduler().runTask(DonationRepository.this.Plugin, new Runnable()
        {
          public void run()
          {
            this.val$callback.run(response);
          }
        });
      }
    };
    handleDatabaseCall(new DatabaseRunnable(new Runnable()
    {
      public void run()
      {
        new JsonWebCall(DonationRepository.this._webAddress + "PlayerAccount/PurchaseUnknownSalesPackage.php").Execute(TransactionResponse.class, extraCallback, token);
      }
    }), "Error purchasing unknown sales package in DonationRepository : ");
  }
  
  public void gemReward(final Callback<Boolean> callback, String giver, String name, String uuid, int greenGems)
  {
    final GemRewardToken token = new GemRewardToken();
    token.Source = giver;
    token.Name = name;
    token.Amount = greenGems;
    
    final Callback<Boolean> extraCallback = new Callback()
    {
      public void run(final Boolean response)
      {
        Bukkit.getServer().getScheduler().runTask(DonationRepository.this.Plugin, new Runnable()
        {
          public void run()
          {
            this.val$callback.run(response);
          }
        });
      }
    };
    handleDatabaseCall(new DatabaseRunnable(new Runnable()
    {
      public void run()
      {
        new JsonWebCall(DonationRepository.this._webAddress + "PlayerAccount/GemReward.php").Execute(Boolean.class, extraCallback, token);
      }
    }), "Error updating player gem amount in DonationRepository : ");
  }
  
  public void rewardCoins(final Callback<Boolean> callback, String giver, String name, final int accountId, final int coins)
  {
    final GemRewardToken token = new GemRewardToken();
    token.Source = giver;
    token.Name = name;
    token.Amount = coins;
    
    final Callback<Boolean> extraCallback = new Callback()
    {
      public void run(final Boolean response)
      {
        if (response.booleanValue()) {
          DonationRepository.this.executeUpdate(DonationRepository.UPDATE_ACCOUNT_COINS, new Column[] { new ColumnInt("coins", coins), new ColumnInt("id", accountId) });
        }
        Bukkit.getServer().getScheduler().runTask(DonationRepository.this.Plugin, new Runnable()
        {
          public void run()
          {
            this.val$callback.run(response);
          }
        });
      }
    };
    handleDatabaseCall(new DatabaseRunnable(new Runnable()
    {
      public void run()
      {
        new JsonWebCall(DonationRepository.this._webAddress + "PlayerAccount/CoinReward.php").Execute(Boolean.class, extraCallback, token);
      }
    }), "Error updating player coin amount in DonationRepository : ");
  }
  
  public void rewardGold(final Callback<Boolean> callback, String giver, String name, final int accountId, final int gold)
  {
    handleDatabaseCall(new DatabaseRunnable(new Runnable()
    {
      public void run()
      {
        if (DonationRepository.this.executeUpdate(DonationRepository.UPDATE_ACCOUNT_GOLD, new Column[] { new ColumnInt("gold", gold), new ColumnInt("id", accountId) }) < 1) {
          callback.run(Boolean.valueOf(false));
        } else {
          callback.run(Boolean.valueOf(true));
        }
      }
    }), "Error updating player gold amount in DonationRepository : ");
  }
  
  protected void initialize() {}
  
  protected void update() {}
  
  public void updateGemsAndCoins(final int accountId, final int gems, final int coins)
  {
    handleDatabaseCall(new DatabaseRunnable(new Runnable()
    {
      public void run()
      {
        DonationRepository.this.executeUpdate(DonationRepository.UPDATE_NULL_ACCOUNT_GEMS_AND_COINS_, new Column[] { new ColumnInt("gems", gems), new ColumnInt("coins", coins), new ColumnInt("id", accountId) });
      }
    }), "Error updating player's null gems and coins DonationRepository : ");
  }
  
  public void applyKits(String playerName)
  {
    new AsyncJsonWebCall(this._webAddress + "PlayerAccount/ApplyKits.php").Execute(playerName);
  }
  
  public Donor retrieveDonorInfo(ResultSet resultSet)
    throws SQLException
  {
    Donor donor = new Donor();
    while (resultSet.next()) {
      donor.addGold(resultSet.getInt(1));
    }
    return donor;
  }
}

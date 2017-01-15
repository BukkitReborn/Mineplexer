package mineplex.core.donation;

import java.util.ArrayList;
import java.util.List;
import mineplex.core.common.CurrencyType;
import mineplex.core.donation.repository.token.CoinTransactionToken;
import mineplex.core.donation.repository.token.DonorToken;
import mineplex.core.donation.repository.token.TransactionToken;

public class Donor
{
  private int _gems;
  private int _coins;
  private int _gold;
  private boolean _donated;
  private List<Integer> _salesPackagesOwned = new ArrayList();
  private List<String> _unknownSalesPackagesOwned = new ArrayList();
  private List<TransactionToken> _transactions = new ArrayList();
  private List<CoinTransactionToken> _coinTransactions = new ArrayList();
  private boolean _update = true;
  
  public void loadToken(DonorToken token)
  {
    this._gems = token.Gems;
    this._coins = token.Coins;
    this._donated = token.Donated;
    
    this._salesPackagesOwned = token.SalesPackages;
    this._unknownSalesPackagesOwned = token.UnknownSalesPackages;
    this._transactions = token.Transactions;
    this._coinTransactions = token.CoinRewards;
  }
  
  public int GetGems()
  {
    return this._gems;
  }
  
  public List<Integer> GetSalesPackagesOwned()
  {
    return this._salesPackagesOwned;
  }
  
  public List<String> GetUnknownSalesPackagesOwned()
  {
    return this._unknownSalesPackagesOwned;
  }
  
  public boolean Owns(Integer salesPackageId)
  {
    return (salesPackageId.intValue() == -1) || (this._salesPackagesOwned.contains(salesPackageId));
  }
  
  public void AddSalesPackagesOwned(int salesPackageId)
  {
    this._salesPackagesOwned.add(Integer.valueOf(salesPackageId));
  }
  
  public boolean HasDonated()
  {
    return this._donated;
  }
  
  public void DeductCost(int cost, CurrencyType currencyType)
  {
    switch (currencyType)
    {
    case Tokens: 
      this._gems -= cost;
      this._update = true;
      break;
    case Gems: 
      this._coins -= cost;
      this._update = true;
      break;
    }
  }
  
  public int GetBalance(CurrencyType currencyType)
  {
    switch (currencyType)
    {
    case Tokens: 
      return this._gems;
    case Gems: 
      return this._coins;
    case Coins: 
      return 0;
    }
    return 0;
  }
  
  public void AddGems(int gems)
  {
    this._gems += gems;
  }
  
  public boolean OwnsUnknownPackage(String packageName)
  {
    return this._unknownSalesPackagesOwned.contains(packageName);
  }
  
  public boolean Updated()
  {
    return this._update;
  }
  
  public void AddUnknownSalesPackagesOwned(String packageName)
  {
    this._unknownSalesPackagesOwned.add(packageName);
  }
  
  public List<TransactionToken> getTransactions()
  {
    return this._transactions;
  }
  
  public boolean OwnsUltraPackage()
  {
    for (String packageName : this._unknownSalesPackagesOwned) {
      if (packageName.contains("ULTRA")) {
        return true;
      }
    }
    return false;
  }
  
  public int getCoins()
  {
    return this._coins;
  }
  
  public void addCoins(int amount)
  {
    this._coins += amount;
  }
  
  public void addGold(int amount)
  {
    this._gold += amount;
  }
  
  public List<CoinTransactionToken> getCoinTransactions()
  {
    return this._coinTransactions;
  }
  
  public int getGold()
  {
    return this._gold;
  }
}

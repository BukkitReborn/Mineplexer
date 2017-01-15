package mineplex.core.donation.command;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.donation.DonationManager;
import org.bukkit.entity.Player;

public class ShardsCommand
  extends CommandBase<DonationManager>
{
  public ShardsCommand(DonationManager plugin)
  {
    super(plugin, Rank.ADMIN, new String[] { "shards" });
  }
  
  public void Execute(final Player caller, String[] args)
  {
    if (args.length < 2)
    {
      UtilPlayer.message(caller, F.main("Shards", "Missing Args: " + F.elem("/shards <player> <amount>")));
      return;
    }
    final String targetName = args[0];
    final String coinsString = args[1];
    Player target = UtilPlayer.searchExact(targetName);
    if (target == null) {
      ((DonationManager)this.Plugin).getClientManager().loadClientByName(targetName, new Runnable()
      {
        public void run()
        {
          CoreClient client = ((DonationManager)ShardsCommand.this.Plugin).getClientManager().Get(targetName);
          if (client != null) {
            ShardsCommand.this.rewardCoins(caller, null, targetName, client.getAccountId(), coinsString);
          } else {
            UtilPlayer.message(caller, F.main("Shards", "Could not find player " + F.name(targetName)));
          }
        }
      });
    } else {
      rewardCoins(caller, target, target.getName(), ((DonationManager)this.Plugin).getClientManager().Get(target).getAccountId(), coinsString);
    }
  }
  
  private void rewardCoins(Player caller, Player target, String targetName, int accountId, String coinsString)
  {
    try
    {
      int coins = Integer.parseInt(coinsString);
      rewardCoins(caller, target, targetName, accountId, coins);
    }
    catch (Exception e)
    {
      UtilPlayer.message(caller, F.main("Shards", "Invalid Shards Amount"));
    }
  }
  
  private void rewardCoins(final Player caller, final Player target, final String targetName, int accountId, final int coins)
  {
    ((DonationManager)this.Plugin).RewardCoins(new Callback()
    {
      public void run(Boolean completed)
      {
        if (completed.booleanValue())
        {
          UtilPlayer.message(caller, F.main("Shards", "You gave " + F.elem(new StringBuilder(String.valueOf(coins)).append(" Shards").toString()) + " to " + F.name(targetName) + "."));
          if (target != null) {
            UtilPlayer.message(target, F.main("Shards", F.name(caller.getName()) + " gave you " + F.elem(new StringBuilder(String.valueOf(coins)).append(" Shards").toString()) + "."));
          }
        }
        else
        {
          UtilPlayer.message(caller, F.main("Shards", "There was an error giving " + F.elem(new StringBuilder(String.valueOf(coins)).append("Shards").toString()) + " to " + F.name(targetName) + "."));
        }
      }
    }, caller.getName(), targetName, accountId, coins);
  }
}

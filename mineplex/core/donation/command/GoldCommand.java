package mineplex.core.donation.command;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import org.bukkit.entity.Player;

public class GoldCommand
  extends CommandBase<DonationManager>
{
  public GoldCommand(DonationManager plugin)
  {
    super(plugin, Rank.ADMIN, new String[] { "givegold" });
  }
  
  public void Execute(final Player caller, String[] args)
  {
    if ((args == null) || (args.length == 0))
    {
      UtilPlayer.message(caller, F.main("Gold", "Your Gold: " + F.elem(new StringBuilder().append(((Donor)((DonationManager)this.Plugin).Get(caller)).getGold()).toString())));
    }
    else if (args.length < 2)
    {
      UtilPlayer.message(caller, F.main("Gold", "Missing Args: " + F.elem("/gold <player> <amount>")));
      return;
    }
    final String targetName = args[0];
    final String goldString = args[1];
    Player target = UtilPlayer.searchExact(targetName);
    if (target == null) {
      ((DonationManager)this.Plugin).getClientManager().loadClientByName(targetName, new Runnable()
      {
        public void run()
        {
          CoreClient client = ((DonationManager)GoldCommand.this.Plugin).getClientManager().Get(targetName);
          if (client != null) {
            GoldCommand.this.rewardGold(caller, null, targetName, client.getAccountId(), goldString);
          } else {
            UtilPlayer.message(caller, F.main("Gold", "Could not find player " + F.name(targetName)));
          }
        }
      });
    } else {
      rewardGold(caller, target, target.getName(), ((DonationManager)this.Plugin).getClientManager().Get(target).getAccountId(), goldString);
    }
  }
  
  private void rewardGold(Player caller, Player target, String targetName, int accountId, String goldString)
  {
    try
    {
      int gold = Integer.parseInt(goldString);
      rewardGold(caller, target, targetName, accountId, gold);
    }
    catch (Exception e)
    {
      UtilPlayer.message(caller, F.main("Gold", "Invalid Gold Amount"));
    }
  }
  
  private void rewardGold(final Player caller, final Player target, final String targetName, int accountId, final int gold)
  {
    ((DonationManager)this.Plugin).RewardGold(new Callback()
    {
      public void run(Boolean completed)
      {
        UtilPlayer.message(caller, F.main("Gold", "You gave " + F.elem(new StringBuilder(String.valueOf(gold)).append(" Gold").toString()) + " to " + F.name(targetName) + "."));
        if (target != null) {
          UtilPlayer.message(target, F.main("Gold", F.name(caller.getName()) + " gave you " + F.elem(new StringBuilder(String.valueOf(gold)).append(" Gold").toString()) + "."));
        }
      }
    }, caller.getName(), targetName, accountId, gold);
  }
}

package mineplex.core.scoreboard.elements;

import java.util.ArrayList;
import mineplex.core.common.CurrencyType;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;

public class ScoreboardElementCoinCount
  extends ScoreboardElement
{
  public ArrayList<String> GetLines(ScoreboardManager manager, Player player)
  {
    ArrayList<String> output = new ArrayList();
    output.add(((Donor)manager.getDonation().Get(player)).GetBalance(CurrencyType.Coins));
    return output;
  }
}

package mineplex.core.scoreboard.elements;

import java.util.ArrayList;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;

public class ScoreboardElementRank
  extends ScoreboardElement
{
  public ArrayList<String> GetLines(ScoreboardManager manager, Player player)
  {
    ArrayList<String> output = new ArrayList();
    if (manager.getClients().Get(player).GetRank().Has(Rank.ULTRA)) {
      output.add(manager.getClients().Get(player).GetRank().Name);
    } else if ((((Donor)manager.getDonation().Get(player.getName())).OwnsUnknownPackage("SuperSmashMobs ULTRA")) || 
      (((Donor)manager.getDonation().Get(player.getName())).OwnsUnknownPackage("Survival Games ULTRA")) || 
      (((Donor)manager.getDonation().Get(player.getName())).OwnsUnknownPackage("Minigames ULTRA")) || 
      (((Donor)manager.getDonation().Get(player.getName())).OwnsUnknownPackage("CastleSiege ULTRA")) || 
      (((Donor)manager.getDonation().Get(player.getName())).OwnsUnknownPackage("Champions ULTRA"))) {
      output.add("Single Ultra");
    } else {
      output.add("No Rank");
    }
    return output;
  }
}

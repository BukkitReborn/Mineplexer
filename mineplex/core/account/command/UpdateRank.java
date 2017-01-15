package mineplex.core.account.command;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.account.repository.AccountRepository;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UUIDFetcher;
import mineplex.core.common.util.UtilPlayer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UpdateRank
  extends CommandBase<CoreClientManager>
{
  public UpdateRank(CoreClientManager plugin)
  {
    super(plugin, Rank.ADMIN, new Rank[] { Rank.JNR_DEV }, new String[] { "updateRank" });
  }
  
  public void Execute(final Player caller, String[] args)
  {
    boolean testServer = ((CoreClientManager)this.Plugin).getPlugin().getConfig().getString("serverstatus.group").equalsIgnoreCase("Testing");
    if ((((CoreClientManager)this.Plugin).Get(caller).GetRank() == Rank.JNR_DEV) && (!testServer))
    {
      F.main(((CoreClientManager)this.Plugin).getName(), F.elem(Rank.JNR_DEV.GetTag(true, true)) + "s are only permitted to set ranks on test servers!");
      return;
    }
    if (args == null)
    {
      UtilPlayer.message(caller, F.main(((CoreClientManager)this.Plugin).getName(), "/" + this.AliasUsed + " joeschmo MODERATOR"));
    }
    else
    {
      if (args.length == 0)
      {
        UtilPlayer.message(caller, F.main(((CoreClientManager)this.Plugin).getName(), "Player argument missing."));
        return;
      }
      final String playerName = args[0];
      Rank tempRank = null;
      try
      {
        tempRank = Rank.valueOf(args[1]);
      }
      catch (Exception ex)
      {
        UtilPlayer.message(caller, F.main(((CoreClientManager)this.Plugin).getName(), ChatColor.RED + ChatColor.BOLD + "Invalid rank!"));
        return;
      }
      final Rank rank = tempRank;
      if ((rank == Rank.ADMIN) || (rank == Rank.YOUTUBE) || (rank == Rank.TWITCH) || (rank == Rank.MODERATOR) || (rank == Rank.HELPER) || (rank == Rank.ALL) || (rank == Rank.MAPDEV) || (rank == Rank.SNR_MODERATOR) || (rank == Rank.JNR_DEV) || (rank == Rank.DEVELOPER))
      {
        if ((!testServer) && (rank.Has(Rank.ADMIN)) && (!((CoreClientManager)this.Plugin).hasRank(caller, Rank.LT)))
        {
          UtilPlayer.message(caller, F.main(((CoreClientManager)this.Plugin).getName(), ChatColor.RED + ChatColor.BOLD + "Insufficient privileges!"));
          return;
        }
        ((CoreClientManager)this.Plugin).getRepository().matchPlayerName(new Callback()
        {
          public void run(List<String> matches)
          {
            boolean matchedExact = false;
            for (String match : matches) {
              if (match.equalsIgnoreCase(playerName)) {
                matchedExact = true;
              }
            }
            if (matchedExact)
            {
              Iterator<String> matchIterator = matches.iterator();
              while (matchIterator.hasNext()) {
                if (!((String)matchIterator.next()).equalsIgnoreCase(playerName)) {
                  matchIterator.remove();
                }
              }
            }
            UtilPlayer.searchOffline(matches, new Callback()
            {
              public void run(final String target)
              {
                if (target == null) {
                  return;
                }
                UUID uuid = ((CoreClientManager)UpdateRank.this.Plugin).loadUUIDFromDB(this.val$playerName);
                if (uuid == null) {
                  uuid = UUIDFetcher.getUUIDOf(this.val$playerName);
                }
                ((CoreClientManager)UpdateRank.this.Plugin).getRepository().saveRank(new Callback()
                {
                  public void run(Rank rank)
                  {
                    this.val$caller.sendMessage(F.main(((CoreClientManager)UpdateRank.this.Plugin).getName(), target + "'s rank has been updated to " + rank.Name + "!"));
                  }
                }, target, uuid, this.val$rank, true);
              }
            }, caller, playerName, true);
          }
        }, playerName);
      }
    }
  }
}

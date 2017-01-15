package mineplex.core.portal.Commands;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.command.CommandCenter;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.portal.Portal;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerCommand
  extends CommandBase<Portal>
{
  public ServerCommand(Portal plugin)
  {
    super(plugin, Rank.ALL, new String[] { "server" });
  }
  
  public void Execute(final Player player, final String[] args)
  {
    final Rank playerRank = this.CommandCenter.GetClientManager().Get(player).GetRank();
    String serverName = ((Portal)this.Plugin).getPlugin().getConfig().getString("serverstatus.name");
    if ((args == null) || (args.length == 0))
    {
      UtilPlayer.message(player, F.main(((Portal)this.Plugin).getName(), C.cGray + "You are currently on server: " + C.cGold + serverName));
      return;
    }
    if (args.length == 1)
    {
      if (serverName.equalsIgnoreCase(args[0])) {
        UtilPlayer.message(player, F.main(((Portal)this.Plugin).getName(), "You are already on " + C.cGold + serverName + C.cGray + "!"));
      } else {
        ((Portal)this.Plugin).doesServerExist(args[0], new Callback()
        {
          public void run(Boolean serverExists)
          {
            if (!serverExists.booleanValue())
            {
              UtilPlayer.message(player, F.main(((Portal)ServerCommand.this.Plugin).getName(), "Server " + C.cGold + args[0] + C.cGray + " does not exist!"));
              return;
            }
            boolean deniedAccess = false;
            String servUp = args[0].toUpperCase();
            if (servUp.contains("HERO"))
            {
              if (playerRank.Has(Rank.HERO)) {
                ((Portal)ServerCommand.this.Plugin).sendPlayerToServer(player, args[0]);
              } else {
                deniedAccess = true;
              }
            }
            else if ((servUp.contains("ULTRA")) || (servUp.contains("BETA")))
            {
              if (playerRank.Has(Rank.ULTRA)) {
                ((Portal)ServerCommand.this.Plugin).sendPlayerToServer(player, args[0]);
              } else {
                deniedAccess = true;
              }
            }
            else if (servUp.contains("STAFF"))
            {
              if (playerRank.Has(Rank.HELPER)) {
                ((Portal)ServerCommand.this.Plugin).sendPlayerToServer(player, args[0]);
              } else {
                deniedAccess = true;
              }
            }
            else if (servUp.contains("TEST"))
            {
              if (playerRank.Has(Rank.MODERATOR)) {
                ((Portal)ServerCommand.this.Plugin).sendPlayerToServer(player, args[0]);
              } else {
                deniedAccess = true;
              }
            }
            else {
              ((Portal)ServerCommand.this.Plugin).sendPlayerToServer(player, args[0]);
            }
            if (deniedAccess) {
              UtilPlayer.message(player, F.main(((Portal)ServerCommand.this.Plugin).getName(), C.cRed + "You don't have permission to join " + C.cGold + args[0]));
            }
          }
        });
      }
    }
    else
    {
      UtilPlayer.message(player, F.main(((Portal)this.Plugin).getName(), C.cRed + "Your arguments are inappropriate for this command!"));
      return;
    }
  }
}

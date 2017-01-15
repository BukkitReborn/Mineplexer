package mineplex.core.portal;

import mineplex.serverdata.commands.CommandCallback;
import mineplex.serverdata.commands.ServerCommand;
import mineplex.serverdata.commands.ServerTransfer;
import mineplex.serverdata.commands.TransferCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TransferHandler
  implements CommandCallback
{
  public void run(ServerCommand command)
  {
    TransferCommand transferCommand;
    ServerTransfer transfer;
    Player player;
    if (((command instanceof TransferCommand)) && ((player = Bukkit.getPlayerExact((transfer = (transferCommand = (TransferCommand)command).getTransfer()).getPlayerName())) != null) && (player.isOnline())) {
      Portal.getInstance().sendPlayerToServer(player, transfer.getServerName());
    }
  }
}

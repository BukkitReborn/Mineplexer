package mineplex.core.inventory.command;

import java.util.UUID;
import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.inventory.data.Item;
import org.bukkit.entity.Player;

public class GiveItemCommand
  extends CommandBase<InventoryManager>
{
  public GiveItemCommand(InventoryManager plugin)
  {
    super(plugin, Rank.ADMIN, new String[] { "giveitem" });
  }
  
  public void Execute(final Player caller, String[] args)
  {
    if ((args == null) || (args.length < 3))
    {
      displayUsage(caller);
      return;
    }
    final String playerName = args[0];
    final int amount = Integer.parseInt(args[1]);
    String itemNameTemp = "";
    for (int i = 2; i < args.length; i++) {
      itemNameTemp = itemNameTemp + args[i] + " ";
    }
    itemNameTemp = itemNameTemp.trim();
    
    final String itemName = itemNameTemp;
    
    final Item item = ((InventoryManager)this.Plugin).getItem(itemName);
    Player player = UtilPlayer.searchExact(playerName);
    if (item == null)
    {
      UtilPlayer.message(caller, F.main("Item", "Item with the name " + F.item(itemName) + " not found!"));
    }
    else if (player != null)
    {
      ((InventoryManager)this.Plugin).addItemToInventory(player, item.Category, item.Name, amount);
      UtilPlayer.message(caller, F.main("Item", "You gave " + F.elem(new StringBuilder(String.valueOf(amount)).append(" ").append(itemName).toString()) + " to player " + F.name(playerName)));
      UtilPlayer.message(player, F.main("Item", F.name(caller.getName()) + " gave you " + F.elem(new StringBuilder(String.valueOf(amount)).append(" ").append(itemName).toString())));
    }
    else
    {
      ((InventoryManager)this.Plugin).getClientManager().loadClientByName(playerName, new Runnable()
      {
        public void run()
        {
          UUID uuid = ((InventoryManager)GiveItemCommand.this.Plugin).getClientManager().loadUUIDFromDB(playerName);
          if (uuid != null) {
            ((InventoryManager)GiveItemCommand.this.Plugin).addItemToInventoryForOffline(new Callback()
            {
              public void run(Boolean success)
              {
                UtilPlayer.message(this.val$caller, F.main("Item", "You gave " + F.elem(new StringBuilder(String.valueOf(this.val$amount)).append(" ").append(this.val$itemName).toString()) + " to offline player " + F.name(this.val$playerName)));
              }
            }, uuid, item.Category, item.Name, amount);
          } else {
            UtilPlayer.message(caller, F.main("Item", "Player " + F.name(playerName) + " does not exist!"));
          }
        }
      });
    }
  }
  
  private void displayUsage(Player caller)
  {
    UtilPlayer.message(caller, F.main("Item", "Usage: " + F.elem("/giveitem <playername> <amount> <item name>")));
  }
}

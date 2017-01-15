package mineplex.core.give;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import mineplex.core.MiniPlugin;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilInv;
import mineplex.core.common.util.UtilItem;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.give.commands.GiveCommand;
import mineplex.core.itemstack.ItemStackFactory;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Give
  extends MiniPlugin
{
  public static Give Instance;
  
  protected Give(JavaPlugin plugin)
  {
    super("Give Factory", plugin);
  }
  
  public static void Initialize(JavaPlugin plugin)
  {
    Instance = new Give(plugin);
  }
  
  public void addCommands()
  {
    addCommand(new GiveCommand(this));
  }
  
  public void parseInput(Player player, String[] args)
  {
    if (args.length == 0) {
      help(player);
    } else if (args.length == 1) {
      give(player, player.getName(), args[0], "1", "");
    } else if (args.length == 2) {
      give(player, args[0], args[1], "1", "");
    } else if (args.length == 3) {
      give(player, args[0], args[1], args[2], "");
    } else {
      give(player, args[0], args[1], args[2], args[3]);
    }
  }
  
  public void help(Player player)
  {
    UtilPlayer.message(player, F.main("Give", "Commands List;"));
  }
  
  public void give(Player player, String target, String itemNames, String amount, String enchants)
  {
    LinkedList<Map.Entry<Material, Byte>> itemList = new LinkedList();
    itemList = UtilItem.matchItem(player, itemNames, true);
    if (itemList.isEmpty()) {
      return;
    }
    LinkedList<Player> giveList = new LinkedList();
    if (target.equalsIgnoreCase("all"))
    {
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
      for (int i = 0; i < j; i++)
      {
        Player cur = arrayOfPlayer[i];
        giveList.add(cur);
      }
    }
    else
    {
      giveList = UtilPlayer.matchOnline(player, target, true);
      if (giveList.isEmpty()) {
        return;
      }
    }
    int count = 1;
    try
    {
      count = Integer.parseInt(amount);
      if (count < 1)
      {
        UtilPlayer.message(player, F.main("Give", "Invalid Amount [" + amount + "]. Defaulting to [1]."));
        count = 1;
      }
    }
    catch (Exception e)
    {
      UtilPlayer.message(player, F.main("Give", "Invalid Amount [" + amount + "]. Defaulting to [1]."));
    }
    Object enchs = new HashMap();
    if (enchants.length() > 0)
    {
      String[] arrayOfString1;
      int m = (arrayOfString1 = enchants.split(",")).length;
      for (int k = 0; k < m; k++)
      {
        String cur = arrayOfString1[k];
        try
        {
          String[] tokens = cur.split(":");
          ((HashMap)enchs).put(Enchantment.getByName(tokens[0]), Integer.valueOf(Integer.parseInt(tokens[1])));
        }
        catch (Exception e)
        {
          UtilPlayer.message(player, F.main("Give", "Invalid Enchantment [" + cur + "]."));
        }
      }
    }
    String givenList = "";
    for (Player cur : giveList) {
      givenList = givenList + cur.getName() + " ";
    }
    if (givenList.length() > 0) {
      givenList = givenList.substring(0, givenList.length() - 1);
    }
    for (Object curItem : itemList)
    {
      for (Player cur : giveList)
      {
        ItemStack stack = ItemStackFactory.Instance.CreateStack((Material)((Map.Entry)curItem).getKey(), ((Byte)((Map.Entry)curItem).getValue()).byteValue(), count);
        
        stack.addUnsafeEnchantments((Map)enchs);
        if (UtilInv.insert(cur, stack)) {
          if (!cur.equals(player)) {
            UtilPlayer.message(cur, F.main("Give", "You received " + F.item(new StringBuilder(String.valueOf(count)).append(" ").append(ItemStackFactory.Instance.GetName((Material)((Map.Entry)curItem).getKey(), ((Byte)((Map.Entry)curItem).getValue()).byteValue(), false)).toString()) + " from " + F.elem(player.getName()) + "."));
          }
        }
      }
      if (target.equalsIgnoreCase("all")) {
        UtilPlayer.message(player, F.main("Give", new StringBuilder("You gave ").append(F.item(new StringBuilder(String.valueOf(count)).append(" ").append(ItemStackFactory.Instance.GetName((Material)((Map.Entry)curItem).getKey(), ((Byte)((Map.Entry)curItem).getValue()).byteValue(), false)).toString())).append(" to ").append(F.elem("ALL")).toString()) + ".");
      } else if (giveList.size() > 1) {
        UtilPlayer.message(player, F.main("Give", "You gave " + F.item(new StringBuilder(String.valueOf(count)).append(" ").append(ItemStackFactory.Instance.GetName((Material)((Map.Entry)curItem).getKey(), ((Byte)((Map.Entry)curItem).getValue()).byteValue(), false)).toString()) + " to " + F.elem(givenList) + "."));
      } else {
        UtilPlayer.message(player, F.main("Give", "You gave " + F.item(new StringBuilder(String.valueOf(count)).append(" ").append(ItemStackFactory.Instance.GetName((Material)((Map.Entry)curItem).getKey(), ((Byte)((Map.Entry)curItem).getValue()).byteValue(), false)).toString()) + " to " + F.elem(((Player)giveList.getFirst()).getName()) + "."));
      }
    }
  }
}

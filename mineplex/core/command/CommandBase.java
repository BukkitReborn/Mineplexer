package mineplex.core.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import mineplex.core.MiniPlugin;
import mineplex.core.common.Rank;
import mineplex.core.common.util.UtilServer;
import mineplex.core.recharge.Recharge;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandBase<PluginType extends MiniPlugin>
  implements ICommand
{
  private Rank _requiredRank;
  private Rank[] _specificRank;
  private List<String> _aliases;
  protected PluginType Plugin;
  protected String AliasUsed;
  protected CommandCenter CommandCenter;
  
  public CommandBase(PluginType plugin, Rank requiredRank, String... aliases)
  {
    this.Plugin = plugin;
    this._requiredRank = requiredRank;
    this._aliases = Arrays.asList(aliases);
  }
  
  public CommandBase(PluginType plugin, Rank requiredRank, Rank[] specificRank, String... aliases)
  {
    this.Plugin = plugin;
    this._requiredRank = requiredRank;
    this._specificRank = specificRank;
    this._aliases = Arrays.asList(aliases);
  }
  
  public Collection<String> Aliases()
  {
    return this._aliases;
  }
  
  public void SetAliasUsed(String alias)
  {
    this.AliasUsed = alias;
  }
  
  public Rank GetRequiredRank()
  {
    return this._requiredRank;
  }
  
  public Rank[] GetSpecificRanks()
  {
    return this._specificRank;
  }
  
  public void SetCommandCenter(CommandCenter commandCenter)
  {
    this.CommandCenter = commandCenter;
  }
  
  protected void resetCommandCharge(Player caller)
  {
    Recharge.Instance.recharge(caller, "Command");
  }
  
  public List<String> onTabComplete(CommandSender sender, String commandLabel, String[] args)
  {
    return null;
  }
  
  protected List<String> getMatches(String start, List<String> possibleMatches)
  {
    ArrayList<String> matches = new ArrayList();
    for (String possibleMatch : possibleMatches) {
      if (possibleMatch.toLowerCase().startsWith(start.toLowerCase())) {
        matches.add(possibleMatch);
      }
    }
    return matches;
  }
  
  protected List<String> getMatches(String start, Enum[] numerators)
  {
    ArrayList<String> matches = new ArrayList();
    Enum[] arrayOfEnum;
    int j = (arrayOfEnum = numerators).length;
    for (int i = 0; i < j; i++)
    {
      Enum e = arrayOfEnum[i];
      String s = e.toString();
      if (s.toLowerCase().startsWith(start.toLowerCase())) {
        matches.add(s);
      }
    }
    return matches;
  }
  
  protected List<String> getPlayerMatches(Player sender, String start)
  {
    ArrayList<String> matches = new ArrayList();
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if ((sender.canSee(player)) && (player.getName().toLowerCase().startsWith(start.toLowerCase()))) {
        matches.add(player.getName());
      }
    }
    return matches;
  }
}

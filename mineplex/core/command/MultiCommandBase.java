package mineplex.core.command;

import java.util.ArrayList;
import java.util.List;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.NautHashMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class MultiCommandBase<PluginType extends MiniPlugin>
  extends CommandBase<PluginType>
{
  protected NautHashMap<String, ICommand> Commands = new NautHashMap();
  
  public MultiCommandBase(PluginType plugin, Rank rank, String... aliases)
  {
    super(plugin, rank, aliases);
  }
  
  public MultiCommandBase(PluginType plugin, Rank rank, Rank[] specificRanks, String... aliases)
  {
    super(plugin, rank, specificRanks, aliases);
  }
  
  public void AddCommand(ICommand command)
  {
    for (String commandRoot : command.Aliases())
    {
      this.Commands.put(commandRoot, command);
      command.SetCommandCenter(this.CommandCenter);
    }
  }
  
  public void Execute(Player caller, String[] args)
  {
    String commandName = null;
    String[] newArgs = null;
    if ((args != null) && (args.length > 0))
    {
      commandName = args[0];
      if (args.length > 1)
      {
        newArgs = new String[args.length - 1];
        for (int i = 0; i < newArgs.length; i++) {
          newArgs[i] = args[(i + 1)];
        }
      }
    }
    ICommand command;
    if (((command = (ICommand)this.Commands.get(commandName)) != null) && (this.CommandCenter.ClientManager.Get(caller).GetRank().Has(caller, command.GetRequiredRank(), command.GetSpecificRanks(), true)))
    {
      command.SetAliasUsed(commandName);
      command.Execute(caller, newArgs);
    }
    else
    {
      Help(caller, args);
    }
  }
  
  public List<String> onTabComplete(CommandSender sender, String commandLabel, String[] args)
  {
    if (args.length == 1)
    {
      ArrayList<String> possibleMatches = new ArrayList();
      for (ICommand command2 : this.Commands.values()) {
        possibleMatches.addAll(command2.Aliases());
      }
      return getMatches(args[0], possibleMatches);
    }
    String commandName;
    ICommand command;
    if ((args.length > 1) && ((command = (ICommand)this.Commands.get(commandName = args[0])) != null)) {
      return command.onTabComplete(sender, commandLabel, args);
    }
    return null;
  }
  
  protected abstract void Help(Player paramPlayer, String[] paramArrayOfString);
}

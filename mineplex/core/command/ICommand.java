package mineplex.core.command;

import java.util.Collection;
import java.util.List;
import mineplex.core.common.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract interface ICommand
{
  public abstract void SetCommandCenter(CommandCenter paramCommandCenter);
  
  public abstract void Execute(Player paramPlayer, String[] paramArrayOfString);
  
  public abstract Collection<String> Aliases();
  
  public abstract void SetAliasUsed(String paramString);
  
  public abstract Rank GetRequiredRank();
  
  public abstract Rank[] GetSpecificRanks();
  
  public abstract List<String> onTabComplete(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);
}

package mineplex.core.resourcepack;

import org.bukkit.entity.Player;

public abstract interface ResUnloadCheck
{
  public abstract boolean canSendUnload(Player paramPlayer);
}

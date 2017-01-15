package mineplex.core.friend.command;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.friend.FriendManager;
import mineplex.core.friend.ui.FriendsGUI;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.preferences.UserPreferences;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class FriendsDisplay
  extends CommandBase<FriendManager>
{
  public FriendsDisplay(FriendManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "friendsdisplay" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    UserPreferences preferences = (UserPreferences)((FriendManager)this.Plugin).getPreferenceManager().Get(caller);
    
    preferences.friendDisplayInventoryUI = (!preferences.friendDisplayInventoryUI);
    
    ((FriendManager)this.Plugin).getPreferenceManager().savePreferences(caller);
    
    caller.playSound(caller.getLocation(), Sound.NOTE_PLING, 1.0F, 1.6F);
    if (preferences.friendDisplayInventoryUI) {
      new FriendsGUI((FriendManager)this.Plugin, caller);
    } else {
      ((FriendManager)this.Plugin).showFriends(caller);
    }
  }
}

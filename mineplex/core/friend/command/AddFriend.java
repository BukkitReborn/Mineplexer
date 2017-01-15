package mineplex.core.friend.command;

import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.command.CommandCenter;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.friend.FriendManager;
import mineplex.core.friend.ui.FriendsGUI;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.preferences.UserPreferences;
import org.bukkit.entity.Player;

public class AddFriend
  extends CommandBase<FriendManager>
{
  public AddFriend(FriendManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "friends", "friend", "f" });
  }
  
  public void Execute(final Player caller, String[] args)
  {
    if ((args == null) || (args.length < 1))
    {
      if (((UserPreferences)((FriendManager)this.Plugin).getPreferenceManager().Get(caller)).friendDisplayInventoryUI) {
        new FriendsGUI((FriendManager)this.Plugin, caller);
      } else {
        ((FriendManager)this.Plugin).showFriends(caller);
      }
    }
    else {
      this.CommandCenter.GetClientManager().checkPlayerName(caller, args[0], new Callback()
      {
        public void run(String result)
        {
          if (result != null) {
            ((FriendManager)AddFriend.this.Plugin).addFriend(caller, result);
          }
        }
      });
    }
  }
}

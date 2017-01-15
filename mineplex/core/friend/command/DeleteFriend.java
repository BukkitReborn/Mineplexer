package mineplex.core.friend.command;

import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.command.CommandCenter;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.friend.FriendManager;
import org.bukkit.entity.Player;

public class DeleteFriend
  extends CommandBase<FriendManager>
{
  public DeleteFriend(FriendManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "unfriend" });
  }
  
  public void Execute(final Player caller, String[] args)
  {
    if (args == null) {
      F.main(((FriendManager)this.Plugin).getName(), "You need to include a player's name.");
    } else {
      this.CommandCenter.GetClientManager().checkPlayerName(caller, args[0], new Callback()
      {
        public void run(String result)
        {
          if (result != null) {
            ((FriendManager)DeleteFriend.this.Plugin).removeFriend(caller, result);
          }
        }
      });
    }
  }
}

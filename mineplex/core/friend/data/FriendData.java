package mineplex.core.friend.data;

import java.util.ArrayList;
import mineplex.core.friend.ui.FriendsGUI;

public class FriendData
{
  private ArrayList<FriendStatus> _friends = new ArrayList();
  private FriendsGUI _friendsPage;
  
  public ArrayList<FriendStatus> getFriends()
  {
    return this._friends;
  }
  
  public void setFriends(ArrayList<FriendStatus> newFriends)
  {
    this._friends = newFriends;
    updateGui();
  }
  
  private void updateGui()
  {
    if (this._friendsPage != null) {
      this._friendsPage.updateGui();
    }
  }
  
  public void setGui(FriendsGUI friendsPage)
  {
    this._friendsPage = friendsPage;
  }
  
  public FriendsGUI getGui()
  {
    return this._friendsPage;
  }
}

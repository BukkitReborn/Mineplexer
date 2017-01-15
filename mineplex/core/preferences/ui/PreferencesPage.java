package mineplex.core.preferences.ui;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.donation.DonationManager;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.preferences.UserPreferences;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import mineplex.core.shop.page.ShopPageBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class PreferencesPage
  extends ShopPageBase<PreferencesManager, PreferencesShop>
{
  private IButton _toggleHubGames;
  private IButton _toggleHubPlayers;
  private IButton _toggleChat;
  private IButton _togglePrivateChat;
  private IButton _toggleHubPartyRequests;
  private IButton _togglePendingFriendRequests;
  private IButton _toggleHubInvisibility;
  private IButton _toggleHubForcefield;
  private IButton _toggleHubIgnoreVelocity;
  private IButton _toggleMacReports;
  private boolean _hubGamesToggled;
  private boolean _hubPlayersToggled;
  private boolean _hubChatToggled;
  private boolean _hubPrivateChatToggled;
  private boolean _hubPartyRequestsToggled;
  private boolean _pendingFriendRequestsToggled;
  private boolean _hubInvisibilityToggled;
  private boolean _hubForcefieldToggled;
  private boolean _macReportsToggled;
  private boolean _hubIgnoreVelocityToggled;
  
  public PreferencesPage(PreferencesManager plugin, PreferencesShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    super(plugin, shop, clientManager, donationManager, name, player, 54);
    
    createButtons();
    buildPage();
  }
  
  private void createButtons()
  {
    this._toggleHubGames = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.toggleHubGames(player);
      }
    };
    this._toggleHubPlayers = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.toggleHubPlayers(player);
      }
    };
    this._toggleChat = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.toggleChat(player);
      }
    };
    this._togglePrivateChat = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.togglePrivateChat(player);
      }
    };
    this._toggleHubPartyRequests = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.toggleHubPartyRequests(player);
      }
    };
    this._togglePendingFriendRequests = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.togglePendingFriendRequests(player);
      }
    };
    this._toggleHubInvisibility = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.toggleHubInvisibility(player);
      }
    };
    this._toggleHubForcefield = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.toggleHubForcefield(player);
      }
    };
    this._toggleMacReports = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.toggleMacReports(player);
      }
    };
    this._toggleHubIgnoreVelocity = new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        PreferencesPage.this.toggleHubIgnoreVelocity(player);
      }
    };
  }
  
  private void buildPreference(int index, Material material, String name, boolean preference, IButton button)
  {
    buildPreference(index, material, (byte)0, name, preference, button);
  }
  
  private void buildPreference(int index, Material material, byte data, String name, boolean preference, IButton button)
  {
    String[] description = {
      preference ? ChatColor.GREEN + "Enabled" : new StringBuilder().append(ChatColor.RED).append("Disabled").toString(), 
      ChatColor.RED + " ", 
      ChatColor.RESET + "Click to " + (preference ? "Disable" : "Enable") };
    
    addButton(index, new ShopItem(material, data, (preference ? ChatColor.GREEN : ChatColor.RED) + name, description, 1, false, false), button);
    addButton(index + 9, new ShopItem(Material.INK_SACK, (byte)(preference ? 10 : 8), (preference ? ChatColor.GREEN : ChatColor.RED) + name, description, 1, false, false), button);
  }
  
  protected void toggleHubForcefield(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).HubForcefield = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).HubForcefield);
    this._hubForcefieldToggled = (!this._hubForcefieldToggled);
    buildPage();
  }
  
  protected void toggleHubInvisibility(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).Invisibility = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).Invisibility);
    this._hubInvisibilityToggled = (!this._hubInvisibilityToggled);
    buildPage();
  }
  
  protected void toggleHubPartyRequests(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).PartyRequests = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).PartyRequests);
    this._hubPartyRequestsToggled = (!this._hubPartyRequestsToggled);
    buildPage();
  }
  
  protected void togglePendingFriendRequests(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).PendingFriendRequests = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).PendingFriendRequests);
    this._pendingFriendRequestsToggled = (!this._pendingFriendRequestsToggled);
    buildPage();
  }
  
  protected void togglePrivateChat(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).PrivateMessaging = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).PrivateMessaging);
    this._hubPrivateChatToggled = (!this._hubPrivateChatToggled);
    buildPage();
  }
  
  protected void toggleChat(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).ShowChat = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).ShowChat);
    this._hubChatToggled = (!this._hubChatToggled);
    buildPage();
  }
  
  protected void toggleHubPlayers(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).ShowPlayers = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).ShowPlayers);
    this._hubPlayersToggled = (!this._hubPlayersToggled);
    buildPage();
  }
  
  protected void toggleHubGames(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).HubGames = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).HubGames);
    this._hubGamesToggled = (!this._hubGamesToggled);
    buildPage();
  }
  
  protected void toggleMacReports(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).ShowMacReports = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).ShowMacReports);
    this._macReportsToggled = (!this._macReportsToggled);
    buildPage();
  }
  
  protected void toggleHubIgnoreVelocity(Player player)
  {
    ((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).IgnoreVelocity = (!((UserPreferences)((PreferencesManager)getPlugin()).Get(player)).IgnoreVelocity);
    this._hubIgnoreVelocityToggled = (!this._hubIgnoreVelocityToggled);
    buildPage();
  }
  
  public void playerClosed()
  {
    super.playerClosed();
    if (preferencesChanged()) {
      ((PreferencesManager)getPlugin()).savePreferences(getPlayer());
    }
  }
  
  protected void buildPage()
  {
    clear();
    
    UserPreferences userPreferences = (UserPreferences)((PreferencesManager)getPlugin()).Get(getPlayer());
    int index = 9;
    
    buildPreference(index, Material.FIREBALL, "Hub Stacker", userPreferences.HubGames, this._toggleHubGames);
    index += 2;
    buildPreference(index, Material.EYE_OF_ENDER, "Hub Player Visibility", userPreferences.ShowPlayers, this._toggleHubPlayers);
    index += 2;
    buildPreference(index, Material.PAPER, "Player Chat", userPreferences.ShowChat, this._toggleChat);
    index += 2;
    buildPreference(index, Material.EMPTY_MAP, "Private Messaging", userPreferences.PrivateMessaging, this._togglePrivateChat);
    index += 2;
    buildPreference(index, Material.SKULL_ITEM, (byte)3, "Hub Party Requests", userPreferences.PartyRequests, this._toggleHubPartyRequests);
    
    buildPreference(40, Material.RED_ROSE, "Show Pending Friend Requests", userPreferences.PendingFriendRequests, this._togglePendingFriendRequests);
    if ((getClientManager().Get(getPlayer()).GetRank() == Rank.YOUTUBE) || (getClientManager().Get(getPlayer()).GetRank() == Rank.TWITCH))
    {
      buildPreference(38, Material.NETHER_STAR, "Hub Invisibility", userPreferences.Invisibility, this._toggleHubInvisibility);
      buildPreference(42, Material.SLIME_BALL, "Hub Forcefield", userPreferences.HubForcefield, this._toggleHubForcefield);
      buildPreference(44, Material.SADDLE, "Hub Ignore Velocity", userPreferences.IgnoreVelocity, this._toggleHubIgnoreVelocity);
    }
    if ((getClientManager().Get(getPlayer()).GetRank().Has(Rank.ADMIN)) || (getClientManager().Get(getPlayer()).GetRank() == Rank.JNR_DEV))
    {
      buildPreference(36, Material.NETHER_STAR, "Hub Invisibility", userPreferences.Invisibility, this._toggleHubInvisibility);
      buildPreference(38, Material.SLIME_BALL, "Hub Forcefield", userPreferences.HubForcefield, this._toggleHubForcefield);
      buildPreference(42, Material.PAPER, "Mac Reports", userPreferences.ShowMacReports, this._toggleMacReports);
      buildPreference(44, Material.SADDLE, "Hub Ignore Velocity", userPreferences.IgnoreVelocity, this._toggleHubIgnoreVelocity);
    }
    else if (getClientManager().Get(getPlayer()).GetRank().Has(Rank.MODERATOR))
    {
      buildPreference(38, Material.PAPER, "Mac Reports", userPreferences.ShowMacReports, this._toggleMacReports);
      buildPreference(42, Material.SADDLE, "Hub Ignore Velocity", userPreferences.IgnoreVelocity, this._toggleHubIgnoreVelocity);
    }
  }
  
  public boolean preferencesChanged()
  {
    return (this._hubGamesToggled) || (this._hubPlayersToggled) || (this._hubChatToggled) || (this._hubPrivateChatToggled) || (this._hubPartyRequestsToggled) || (this._hubInvisibilityToggled) || (this._hubForcefieldToggled) || (this._pendingFriendRequestsToggled);
  }
}

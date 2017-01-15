package mineplex.core.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import mineplex.core.MiniClientPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.chat.Chat;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.common.util.UtilTime.TimeUnit;
import mineplex.core.friend.FriendManager;
import mineplex.core.friend.data.FriendData;
import mineplex.core.friend.data.FriendStatus;
import mineplex.core.ignore.IgnoreManager;
import mineplex.core.message.commands.AdminCommand;
import mineplex.core.message.commands.AnnounceCommand;
import mineplex.core.message.commands.GlobalCommand;
import mineplex.core.message.commands.MessageAdminCommand;
import mineplex.core.message.commands.MessageCommand;
import mineplex.core.message.commands.ResendAdminCommand;
import mineplex.core.message.commands.ResendCommand;
import mineplex.core.message.redis.AnnouncementHandler;
import mineplex.core.message.redis.MessageHandler;
import mineplex.core.message.redis.RedisMessage;
import mineplex.core.message.redis.RedisMessageCallback;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.preferences.UserPreferences;
import mineplex.core.punish.Punish;
import mineplex.core.punish.PunishClient;
import mineplex.core.punish.Punishment;
import mineplex.core.punish.PunishmentSentence;
import mineplex.serverdata.commands.AnnouncementCommand;
import mineplex.serverdata.commands.ServerCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MessageManager
  extends MiniClientPlugin<ClientMessage>
{
  private CoreClientManager _clientManager;
  private FriendManager _friendsManager;
  private IgnoreManager _ignoreManager;
  private HashMap<UUID, BukkitRunnable> _messageTimeouts = new HashMap();
  private PreferencesManager _preferences;
  private Punish _punish;
  private Chat _chat;
  private ArrayList<String> _randomMessage;
  private String _serverName;
  
  public MessageManager(JavaPlugin plugin, CoreClientManager clientManager, PreferencesManager preferences, IgnoreManager ignoreManager, Punish punish, FriendManager friendManager, Chat chat)
  {
    super("Message", plugin);
    
    this._clientManager = clientManager;
    this._preferences = preferences;
    this._ignoreManager = ignoreManager;
    this._punish = punish;
    this._friendsManager = friendManager;
    this._chat = chat;
    this._serverName = getPlugin().getConfig().getString("serverstatus.name");
    
    MessageHandler messageHandler = new MessageHandler(this);
    
    ServerCommandManager.getInstance().registerCommandType("AnnouncementCommand", AnnouncementCommand.class, 
      new AnnouncementHandler());
    
    ServerCommandManager.getInstance().registerCommandType("RedisMessage", RedisMessage.class, messageHandler);
    ServerCommandManager.getInstance()
      .registerCommandType("RedisMessageCallback", RedisMessageCallback.class, messageHandler);
  }
  
  public void addCommands()
  {
    addCommand(new MessageCommand(this));
    addCommand(new ResendCommand(this));
    
    addCommand(new MessageAdminCommand(this));
    addCommand(new ResendAdminCommand(this));
    
    addCommand(new AnnounceCommand(this));
    addCommand(new GlobalCommand(this));
    
    addCommand(new AdminCommand(this));
  }
  
  protected ClientMessage AddPlayer(String player)
  {
    Set(player, new ClientMessage());
    return (ClientMessage)Get(player);
  }
  
  public boolean canMessage(Player from, Player to)
  {
    if (!canSenderMessageThem(from, to.getName())) {
      return false;
    }
    String canMessage = canReceiverMessageThem(from.getName(), to);
    if (canMessage != null)
    {
      from.sendMessage(canMessage);
      
      return false;
    }
    return true;
  }
  
  public String canReceiverMessageThem(String sender, Player target)
  {
    if (!((UserPreferences)this._preferences.Get(target)).PrivateMessaging) {
      return C.cPurple + target.getName() + " has private messaging disabled.";
    }
    if (this._ignoreManager.isIgnoring(target, sender)) {
      return F.main(this._ignoreManager.getName(), ChatColor.GRAY + "That player is ignoring you");
    }
    return null;
  }
  
  public boolean isMuted(Player sender)
  {
    PunishClient client = this._punish.GetClient(sender.getName());
    if ((client != null) && (client.IsMuted()))
    {
      Punishment punishment = client.GetPunishment(PunishmentSentence.Mute);
      
      sender.sendMessage(F.main(this._punish.getName(), "Shh, you're muted because " + 
      
        punishment.GetReason() + 
        
        " by " + 
        
        punishment.GetAdmin() + 
        
        " for " + 
        
        C.cGreen + 
        
        UtilTime.convertString(punishment.GetRemaining(), 1, UtilTime.TimeUnit.FIT) + "."));
      
      return true;
    }
    return false;
  }
  
  public boolean canSenderMessageThem(Player sender, String target)
  {
    if (isMuted(sender)) {
      return false;
    }
    if (this._ignoreManager.isIgnoring(sender, target))
    {
      sender.sendMessage(F.main(this._ignoreManager.getName(), ChatColor.GRAY + "You are ignoring that player"));
      
      return false;
    }
    return true;
  }
  
  public void DoMessage(Player from, Player to, String message)
  {
    PrivateMessageEvent pmEvent = new PrivateMessageEvent(from, to, message);
    Bukkit.getServer().getPluginManager().callEvent(pmEvent);
    if (pmEvent.isCancelled()) {
      return;
    }
    if (!canMessage(from, to)) {
      return;
    }
    if ((!GetClientManager().Get(from).GetRank().Has(Rank.HELPER)) && (((ClientMessage)Get(from)).LastTo != null) && 
      (!((ClientMessage)Get(from)).LastTo.equalsIgnoreCase(to.getName())))
    {
      long delta = System.currentTimeMillis() - ((ClientMessage)Get(from)).LastToTime;
      if ((((ClientMessage)Get(from)).SpamCounter > 3) && (delta < ((ClientMessage)Get(from)).SpamCounter * 1000))
      {
        from.sendMessage(F.main("Cooldown", "Try sending that message again in a few seconds"));
        ((ClientMessage)Get(from)).LastTo = to.getName();
        return;
      }
      if (delta < 8000L) {
        ((ClientMessage)Get(from)).SpamCounter += 1;
      }
    }
    message = this._chat.getFilteredMessage(from, message);
    
    UtilPlayer.message(from, C.cGold + "§l" + from.getName() + " > " + to.getName() + C.cYellow + " §l" + message);
    
    ((ClientMessage)Get(from)).LastTo = to.getName();
    ((ClientMessage)Get(from)).LastToTime = System.currentTimeMillis();
    if ((to.getName().equals("Chiss")) || (to.getName().equals("defek7")) || (to.getName().equals("Phinary")) || (to.getName().equals("fooify")))
    {
      UtilPlayer.message(from, C.cPurple + to.getName() + " is often AFK or minimized, due to plugin development.");
      UtilPlayer.message(from, C.cPurple + "Please be patient if he does not reply instantly.");
    }
    from.playSound(to.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
    to.playSound(to.getLocation(), Sound.NOTE_PIANO, 2.0F, 2.0F);
    
    UtilPlayer.message(to, C.cGold + "§l" + from.getName() + " > " + to.getName() + C.cYellow + " §l" + message);
  }
  
  public void DoMessageAdmin(Player from, Player to, String message)
  {
    UtilPlayer.message(from, C.cPurple + "-> " + F.rank(this._clientManager.Get(to).GetRank()) + " " + to.getName() + " " + 
      C.cPurple + message);
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player staff = arrayOfPlayer[i];
      if ((!to.equals(staff)) && (!from.equals(staff))) {
        if (this._clientManager.Get(staff).GetRank().Has(Rank.HELPER)) {
          UtilPlayer.message(staff, F.rank(this._clientManager.Get(from).GetRank()) + " " + from.getName() + C.cPurple + 
            " -> " + F.rank(this._clientManager.Get(to).GetRank()) + " " + to.getName() + " " + C.cPurple + message);
        }
      }
    }
    ((ClientMessage)Get(from)).LastAdminTo = to.getName();
    
    UtilPlayer.message(to, C.cPurple + "<- " + F.rank(this._clientManager.Get(from).GetRank()) + " " + from.getName() + " " + 
      C.cPurple + message);
    
    from.playSound(to.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
    to.playSound(to.getLocation(), Sound.NOTE_PIANO, 2.0F, 2.0F);
  }
  
  public void enable()
  {
    this._randomMessage = new ArrayList();
    this._randomMessage.clear();
    this._randomMessage.add("Hello, do you have any wild boars for purchase?");
    this._randomMessage.add("There's a snake in my boot!");
    this._randomMessage.add("Monk, I need a Monk!");
    this._randomMessage.add("Hi, I'm from planet minecraft, op me plz dooooood!");
    this._randomMessage.add("Somebody's poisoned the waterhole!");
    this._randomMessage.add("MORE ORBZ MORE ORBZ MORE ORBZ MORE ORBZ!");
    this._randomMessage.add("Chiss is a chiss and chiss chiss.");
    this._randomMessage.add("*_*");
    this._randomMessage.add("#swag");
    this._randomMessage.add("Everything went better then I thought.");
    this._randomMessage.add("HAVE A CHICKEN!");
    this._randomMessage.add("follow me, i have xrays");
    this._randomMessage.add("I'm making a java");
    this._randomMessage.add("Do you talk to strangers?  I have candy if it helps.");
    this._randomMessage.add("Solid 2.9/10");
    this._randomMessage.add("close your eyes to sleep");
    this._randomMessage.add("I crashed because my internet ran out.");
    this._randomMessage.add("I saw morgan freeman on a breaking bad ad on a bus.");
    this._randomMessage.add("Where is the volume control?");
    this._randomMessage.add("I saw you playing on youtube with that guy and stuff.");
    this._randomMessage.add("Your worms must be worse than useless.");
    this._randomMessage.add("meow");
    this._randomMessage.add("7");
    this._randomMessage.add("Don't you wish your girlfriend was hot like me?");
    this._randomMessage.add("how do you play mindcrafts?");
    this._randomMessage.add("7 cats meow meow meow meow meow meow meow");
    this._randomMessage.add("For King Jonalon!!!!!");
    this._randomMessage.add("Do you like apples?");
    this._randomMessage.add("I'm Happy Happy Happy.");
    this._randomMessage.add("kthxbye");
    this._randomMessage.add("i like pie.");
    this._randomMessage.add("Do you play Clash of Clans?");
    this._randomMessage.add("Mmm...Steak!");
    this._randomMessage.add("Poop! Poop everywhere!");
    this._randomMessage.add("I'm so forgetful. Like I was going to say somethin...wait what were we talking about?");
    this._randomMessage.add("Mmm...Steak!");
  }
  
  public CoreClientManager GetClientManager()
  {
    return this._clientManager;
  }
  
  public String GetRandomMessage()
  {
    if (this._randomMessage.isEmpty()) {
      return "meow";
    }
    return (String)this._randomMessage.get(UtilMath.r(this._randomMessage.size()));
  }
  
  public ArrayList<String> GetRandomMessages()
  {
    return this._randomMessage;
  }
  
  public void Help(Player caller)
  {
    Help(caller, null);
  }
  
  public void Help(Player caller, String message)
  {
    UtilPlayer.message(caller, F.main(this._moduleName, ChatColor.RED + "Err...something went wrong?"));
  }
  
  public void receiveMessage(Player to, RedisMessage globalMessage)
  {
    if (globalMessage.isStaffMessage())
    {
      UtilPlayer.message(to, C.cPurple + "<- " + globalMessage.getRank() + " " + globalMessage.getSender() + " " + 
        C.cPurple + globalMessage.getMessage());
      
      to.playSound(to.getLocation(), Sound.NOTE_PIANO, 2.0F, 2.0F);
      
      String toRank = F.rank(this._clientManager.Get(to).GetRank());
      
      RedisMessageCallback message = new RedisMessageCallback(globalMessage, true, to.getName(), 
      
        C.cPurple + "-> " + toRank + " " + to.getName() + " " + C.cPurple + globalMessage.getMessage());
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
      for (int i = 0; i < j; i++)
      {
        Player staff = arrayOfPlayer[i];
        if (!to.equals(staff)) {
          if (this._clientManager.Get(staff).GetRank().Has(Rank.HELPER)) {
            UtilPlayer.message(staff, 
            
              globalMessage.getRank() + " " + globalMessage.getSender() + C.cPurple + " " + message.getMessage());
          }
        }
      }
      message.publish();
    }
    else
    {
      String canMessage = canReceiverMessageThem(globalMessage.getSender(), to);
      if (canMessage != null)
      {
        RedisMessageCallback message = new RedisMessageCallback(globalMessage, false, null, canMessage);
        
        message.publish();
        
        return;
      }
      String message = C.cGold + "§l" + globalMessage.getSender() + " > " + to.getName() + C.cYellow + " §l" + 
        globalMessage.getMessage();
      
      UtilPlayer.message(to, message);
      
      to.playSound(to.getLocation(), Sound.NOTE_PIANO, 2.0F, 2.0F);
      
      RedisMessageCallback redisMessage = new RedisMessageCallback(globalMessage, false, to.getName(), message);
      
      redisMessage.publish();
    }
  }
  
  public void receiveMessageCallback(RedisMessageCallback message)
  {
    BukkitRunnable runnable = (BukkitRunnable)this._messageTimeouts.remove(message.getUUID());
    if (runnable != null) {
      runnable.cancel();
    }
    Player target = Bukkit.getPlayerExact(message.getTarget());
    if (target != null)
    {
      target.sendMessage(message.getMessage());
      
      target.playSound(target.getLocation(), Sound.NOTE_PIANO, 2.0F, 2.0F);
      if (message.getLastReplied() != null) {
        if (message.isStaffMessage()) {
          ((ClientMessage)Get(target)).LastAdminTo = message.getLastReplied();
        } else {
          ((ClientMessage)Get(target)).LastTo = message.getLastReplied();
        }
      }
      if ((message.isStaffMessage()) && (message.getLastReplied() != null))
      {
        String recevierRank = F.rank(this._clientManager.Get(target).GetRank());
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
        for (int i = 0; i < j; i++)
        {
          Player staff = arrayOfPlayer[i];
          if (!target.equals(staff)) {
            if (this._clientManager.Get(staff).GetRank().Has(Rank.HELPER)) {
              UtilPlayer.message(staff, 
              
                recevierRank + " " + target.getName() + " " + C.cPurple + message.getMessage());
            }
          }
        }
      }
    }
  }
  
  public void sendMessage(final Player sender, final String target, String message, final boolean isReply, final boolean adminMessage)
  {
    FriendData friends = (FriendData)this._friendsManager.Get(sender);
    FriendStatus friend = null;
    if (!adminMessage) {
      for (FriendStatus friendInfo : friends.getFriends())
      {
        if (((isReply) || (friendInfo.Online)) && (friendInfo.Name.equalsIgnoreCase(target)))
        {
          friend = friendInfo;
          break;
        }
        if ((!isReply) && (friend == null) && (friendInfo.Online) && 
          (friendInfo.Name.toLowerCase().startsWith(target.toLowerCase()))) {
          friend = friendInfo;
        }
      }
    }
    final FriendStatus friendInfo = friend;
    
    new BukkitRunnable()
    {
      final String newMessage;
      
      public void run()
      {
        new BukkitRunnable()
        {
          public void run()
          {
            MessageManager.this.sendMessage(this.val$sender, this.val$target, MessageManager.1.this.newMessage, this.val$adminMessage, this.val$isReply, this.val$friendInfo);
          }
        }.runTask(MessageManager.this.getPlugin());
      }
    }.runTaskAsynchronously(getPlugin());
  }
  
  private void sendMessage(final Player sender, String target, String message, final boolean adminMessage, boolean isReply, FriendStatus friend)
  {
    Player to = UtilPlayer.searchOnline(sender, target, (!adminMessage) && (friend == null) && (!isReply));
    if ((!adminMessage) && ((friend == null) || (!friend.Online)) && (to == null))
    {
      if (isReply) {
        UtilPlayer.message(sender, F.main(getName(), F.name(target) + " is no longer online."));
      }
      return;
    }
    if (to != null)
    {
      if (adminMessage) {
        DoMessageAdmin(sender, to, message);
      } else {
        DoMessage(sender, to, message);
      }
    }
    else
    {
      final String playerTarget = adminMessage ? target : friend.Name;
      if ((adminMessage) || (canSenderMessageThem(sender, playerTarget)))
      {
        RedisMessage globalMessage = new RedisMessage(this._serverName, 
        
          sender.getName(), 
          
          adminMessage ? null : friend.ServerName, 
          
          playerTarget, 
          
          message, 
          
          adminMessage ? F.rank(this._clientManager.Get(sender).GetRank()) : null);
        
        final UUID uuid = globalMessage.getUUID();
        
        BukkitRunnable runnable = new BukkitRunnable()
        {
          public void run()
          {
            MessageManager.this._messageTimeouts.remove(uuid);
            
            UtilPlayer.message(
              sender, 
              F.main((adminMessage ? "Admin " : "") + "Message", C.mBody + " Failed to send message to [" + 
              C.mElem + playerTarget + C.mBody + "]."));
          }
        };
        runnable.runTaskLater(getPlugin(), 40L);
        
        this._messageTimeouts.put(uuid, runnable);
        
        globalMessage.publish();
      }
    }
  }
}

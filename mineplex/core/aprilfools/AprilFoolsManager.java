package mineplex.core.aprilfools;

import java.util.Calendar;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTextMiddle;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseCow;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AprilFoolsManager
  extends MiniPlugin
{
  public static AprilFoolsManager Instance;
  private boolean _enabled;
  private DisguiseManager _disguiseManager;
  private CoreClientManager _clientManager;
  
  protected AprilFoolsManager(JavaPlugin plugin, CoreClientManager clientManager, DisguiseManager disguiseManager)
  {
    super("April Fools", plugin);
    this._disguiseManager = disguiseManager;
    this._clientManager = clientManager;
    Calendar c = Calendar.getInstance();
    this._enabled = ((c.get(2) == 3) && (c.get(5) == 1));
  }
  
  public static void Initialize(JavaPlugin plugin, CoreClientManager clientManager, DisguiseManager disguiseManager)
  {
    Instance = new AprilFoolsManager(plugin, clientManager, disguiseManager);
  }
  
  @EventHandler
  public void updateEnabled(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOW) {
      return;
    }
    Calendar c = Calendar.getInstance();
    this._enabled = ((c.get(2) == 3) && (c.get(5) == 1));
  }
  
  @EventHandler(priority=EventPriority.LOW)
  public void chatAdd(AsyncPlayerChatEvent event)
  {
    if (!this._enabled) {
      return;
    }
    String[] words = event.getMessage().split(" ");
    String out = "";
    String[] arrayOfString1;
    int j = (arrayOfString1 = words).length;
    for (int i = 0; i < j; i++)
    {
      String word = arrayOfString1[i];
      if (Math.random() > 0.85D)
      {
        out = out + "moo";
        for (int i = 0; i < UtilMath.r(2); i++) {
          out = out + "o";
        }
        out = out + " " + word + " ";
      }
      else if (Math.random() > 0.85D)
      {
        out = out + word + " ";
        out = out + "moo";
        for (int i = 0; i < UtilMath.r(2); i++) {
          out = out + "o";
        }
        out = out + " ";
      }
      else if (Math.random() > 0.99D)
      {
        out = out + "moo";
        for (int i = 3; i < word.length(); i++) {
          out = out + "o";
        }
        out = out + " ";
      }
      else
      {
        out = out + word + " ";
      }
    }
    event.setMessage(out);
  }
  
  @EventHandler
  public void updateText(UpdateEvent event)
  {
    if (!this._enabled) {
      return;
    }
    if (event.getType() != UpdateType.SLOW) {
      return;
    }
    if (Math.random() <= 0.99D) {
      return;
    }
    UtilTextMiddle.display("Moo", null, 5, 20, 5);
  }
  
  @EventHandler
  public void updateCow(UpdateEvent event)
  {
    if (!this._enabled) {
      return;
    }
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if (this._disguiseManager.getDisguise(player) != null)
      {
        if ((Math.random() > 0.8D) && ((this._disguiseManager.getDisguise(player) instanceof DisguiseCow))) {
          player.getWorld().playSound(player.getLocation(), Sound.COW_IDLE, (float)Math.random() + 0.5F, (float)Math.random() + 0.5F);
        }
      }
      else
      {
        DisguiseCow disguise = new DisguiseCow(player);
        disguise.setName(getName(player), this._clientManager.Get(player).GetRank());
        disguise.setCustomNameVisible(true);
        this._disguiseManager.disguise(disguise, new Player[0]);
      }
    }
  }
  
  public boolean isActive()
  {
    return this._enabled;
  }
  
  public String getName(Player player)
  {
    int index = 0;
    boolean hitVowel = false;
    for (int i = 0; (i < player.getName().length() - 2) && (i < 5); i++)
    {
      if ((player.getName().toLowerCase().charAt(i) == 'a') || (player.getName().toLowerCase().charAt(i) == 'e') || (player.getName().toLowerCase().charAt(i) == 'i') || (player.getName().toLowerCase().charAt(i) == 'o') || (player.getName().toLowerCase().charAt(i) == 'u')) {
        hitVowel = true;
      } else {
        if (hitVowel) {
          break;
        }
      }
      index = i + 1;
    }
    String name;
    if ((name = "Moo" + player.getName().substring(index, player.getName().length())).length() > 16) {
      name = name.substring(0, 16);
    }
    return name;
  }
  
  public void setEnabled(boolean b)
  {
    Calendar c = Calendar.getInstance();
    this._enabled = ((b) && (c.get(2) == 3) && (c.get(5) == 1));
  }
}

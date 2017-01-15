package mineplex.core.blood;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BloodEvent
  extends Event
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean _cancelled = false;
  private Player _player;
  private Location _loc;
  private int _particles;
  private double _velMult;
  private Sound _sound;
  private float _soundVol;
  private float _soundPitch;
  private Material _type;
  private byte _data;
  private int _ticks;
  private boolean _bloodStep;
  
  public BloodEvent(Player player, Location loc, int particles, double velMult, Sound sound, float soundVol, float soundPitch, Material type, byte data, int ticks, boolean bloodStep)
  {
    this._player = player;
    this._loc = loc;
    this._particles = particles;
    this._velMult = velMult;
    this._sound = sound;
    this._soundVol = soundVol;
    this._soundPitch = soundPitch;
    this._type = type;
    this._data = data;
    this._ticks = ticks;
    this._bloodStep = bloodStep;
  }
  
  public boolean isCancelled()
  {
    return this._cancelled;
  }
  
  public void setCancelled(boolean var)
  {
    this._cancelled = var;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public Player getPlayer()
  {
    return this._player;
  }
  
  public Location getLocation()
  {
    return this._loc;
  }
  
  public int getParticles()
  {
    return this._particles;
  }
  
  public double getVelocityMult()
  {
    return this._velMult;
  }
  
  public Sound getSound()
  {
    return this._sound;
  }
  
  public float getSoundVolume()
  {
    return this._soundVol;
  }
  
  public float getSoundPitch()
  {
    return this._soundPitch;
  }
  
  public Material getMaterial()
  {
    return this._type;
  }
  
  public byte getMaterialData()
  {
    return this._data;
  }
  
  public int getTicks()
  {
    return this._ticks;
  }
  
  public boolean getBloodStep()
  {
    return this._bloodStep;
  }
  
  public void setItem(Material mat, byte data)
  {
    this._type = mat;
    this._data = data;
  }
}

package mineplex.core.npc;

import java.util.Map;
import mineplex.core.common.util.C;
import mineplex.database.tables.records.NpcsRecord;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.Navigation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreature;
import org.bukkit.entity.LivingEntity;

public class Npc
{
  private final NpcManager _npcManager;
  private final NpcsRecord _databaseRecord;
  private final Location _location;
  private LivingEntity _entity;
  private int _failedAttempts = 0;
  private boolean _returning = false;
  private final String[] _info;
  private final Double _infoRadiusSquared;
  
  public Npc(NpcManager npcManager, NpcsRecord databaseRecord)
  {
    this._npcManager = npcManager;
    this._databaseRecord = databaseRecord;
    
    this._location = new Location(Bukkit.getWorld(getDatabaseRecord().getWorld()), getDatabaseRecord().getX().doubleValue(), getDatabaseRecord().getY().doubleValue(), getDatabaseRecord().getZ().doubleValue());
    if (getDatabaseRecord().getInfo() == null)
    {
      this._info = null;
    }
    else
    {
      String[] info = getDatabaseRecord().getInfo().split("\\r?\\n");
      for (int i = 0; i < info.length; i++)
      {
        ChatColor[] arrayOfChatColor;
        int j = (arrayOfChatColor = ChatColor.values()).length;
        for (int i = 0; i < j; i++)
        {
          ChatColor color = arrayOfChatColor[i];
          info[i] = info[i].replace("(" + color.name().toLowerCase() + ")", color.toString());
        }
        info[i] = ChatColor.translateAlternateColorCodes('&', info[i]);
      }
      this._info = new String[info.length + 2];
      for (int i = 0; i < this._info.length; i++) {
        if ((i == 0) || (i == this._info.length - 1)) {
          this._info[i] = (C.cGold + C.Strike + "=============================================");
        } else {
          this._info[i] = info[(i - 1)];
        }
      }
    }
    if (getDatabaseRecord().getInfoRadius() == null) {
      this._infoRadiusSquared = null;
    } else {
      this._infoRadiusSquared = Double.valueOf(getDatabaseRecord().getInfoRadius().doubleValue() * getDatabaseRecord().getInfoRadius().doubleValue());
    }
  }
  
  public void setEntity(LivingEntity entity)
  {
    if (this._entity != null) {
      getNpcManager()._npcMap.remove(this._entity.getUniqueId());
    }
    this._entity = entity;
    if (this._entity != null) {
      getNpcManager()._npcMap.put(this._entity.getUniqueId(), this);
    }
  }
  
  public LivingEntity getEntity()
  {
    return this._entity;
  }
  
  public NpcsRecord getDatabaseRecord()
  {
    return this._databaseRecord;
  }
  
  public int getFailedAttempts()
  {
    return this._failedAttempts;
  }
  
  public void setFailedAttempts(int failedAttempts)
  {
    this._failedAttempts = failedAttempts;
  }
  
  public int incrementFailedAttempts()
  {
    return ++this._failedAttempts;
  }
  
  public Location getLocation()
  {
    return this._location;
  }
  
  public double getRadius()
  {
    return getDatabaseRecord().getRadius().doubleValue();
  }
  
  public boolean isInRadius(Location location)
  {
    if (location.getWorld() != getLocation().getWorld()) {
      return false;
    }
    return location.distanceSquared(getLocation()) <= getRadius() * getRadius();
  }
  
  public void returnToPost()
  {
    if ((this._entity instanceof CraftCreature))
    {
      EntityCreature ec = ((CraftCreature)this._entity).getHandle();
      
      ec.getNavigation().a(getLocation().getX(), getLocation().getY(), getLocation().getZ(), 0.800000011920929D);
      
      this._returning = true;
    }
  }
  
  public boolean isReturning()
  {
    return this._returning;
  }
  
  public void clearGoals()
  {
    if ((this._entity instanceof CraftCreature))
    {
      this._returning = false;
      
      Location entityLocation = this._entity.getLocation();
      EntityCreature ec = ((CraftCreature)this._entity).getHandle();
      ec.getNavigation().a(entityLocation.getX(), entityLocation.getY(), entityLocation.getZ(), 0.800000011920929D);
    }
  }
  
  public NpcManager getNpcManager()
  {
    return this._npcManager;
  }
  
  public Chunk getChunk()
  {
    return getLocation().getChunk();
  }
  
  public String[] getInfo()
  {
    return this._info;
  }
  
  public Double getInfoRadiusSquared()
  {
    return this._infoRadiusSquared;
  }
}

package mineplex.core.hologram;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilPlayer;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Hologram
{
  private Packet _destroy1_7;
  private Packet _destroy1_8;
  
  public static enum HologramTarget
  {
    BLACKLIST,  WHITELIST;
  }
  
  private ArrayList<Map.Entry<Integer, Integer>> _entityIds = new ArrayList();
  private Entity _followEntity;
  private HologramManager _hologramManager;
  private String[] _hologramText = new String[0];
  private Vector _lastMovement;
  private Location _location;
  private boolean _makeDestroyPackets = true;
  private boolean _makeSpawnPackets = true;
  private Packet[] _packets1_7;
  private Packet[] _packets1_8;
  private HashSet<String> _playersInList = new HashSet();
  private ArrayList<Player> _playersTracking = new ArrayList();
  private boolean _removeEntityDeath;
  private HologramTarget _target = HologramTarget.BLACKLIST;
  private int _viewDistance = 70;
  protected Vector relativeToEntity;
  
  public Hologram(HologramManager hologramManager, Location location, String... text)
  {
    this._hologramManager = hologramManager;
    this._location = location.clone();
    setText(text);
  }
  
  public Hologram addPlayer(Player player)
  {
    return addPlayer(player.getName());
  }
  
  public Hologram addPlayer(String player)
  {
    this._playersInList.add(player);
    return this;
  }
  
  public boolean containsPlayer(Player player)
  {
    return this._playersInList.contains(player.getName());
  }
  
  public boolean containsPlayer(String player)
  {
    return this._playersInList.contains(player);
  }
  
  protected Packet getDestroyPacket(Player player)
  {
    if (this._makeDestroyPackets)
    {
      makeDestroyPacket();
      this._makeDestroyPackets = false;
    }
    return UtilPlayer.is1_8(player) ? this._destroy1_8 : this._destroy1_7;
  }
  
  public Entity getEntityFollowing()
  {
    return this._followEntity;
  }
  
  public HologramTarget getHologramTarget()
  {
    return this._target;
  }
  
  public Location getLocation()
  {
    return this._location.clone();
  }
  
  protected ArrayList<Player> getNearbyPlayers()
  {
    ArrayList<Player> nearbyPlayers = new ArrayList();
    for (Player player : getLocation().getWorld().getPlayers()) {
      if (isVisible(player)) {
        nearbyPlayers.add(player);
      }
    }
    return nearbyPlayers;
  }
  
  protected ArrayList<Player> getPlayersTracking()
  {
    return this._playersTracking;
  }
  
  protected Packet[] getSpawnPackets(Player player)
  {
    if (this._makeSpawnPackets)
    {
      makeSpawnPackets();
      this._makeSpawnPackets = false;
    }
    return UtilPlayer.is1_8(player) ? this._packets1_8 : this._packets1_7;
  }
  
  public String[] getText()
  {
    String[] reversed = new String[this._hologramText.length];
    for (int i = 0; i < reversed.length; i++) {
      reversed[i] = this._hologramText[(reversed.length - (i + 1))];
    }
    return reversed;
  }
  
  public int getViewDistance()
  {
    return this._viewDistance;
  }
  
  public boolean isInUse()
  {
    return this._lastMovement != null;
  }
  
  public boolean isRemoveOnEntityDeath()
  {
    return this._removeEntityDeath;
  }
  
  public boolean isVisible(Player player)
  {
    if (getLocation().getWorld() == player.getWorld()) {
      if ((getHologramTarget() == HologramTarget.WHITELIST) == containsPlayer(player)) {
        if (getLocation().distance(player.getLocation()) < getViewDistance()) {
          return true;
        }
      }
    }
    return false;
  }
  
  private void makeDestroyPacket()
  {
    int[] entityIds1_7 = new int[this._entityIds.size() * 2];
    int[] entityIds1_8 = new int[this._entityIds.size()];
    for (int i = 0; i < this._entityIds.size(); i++)
    {
      Map.Entry<Integer, Integer> entry = (Map.Entry)this._entityIds.get(i);
      
      entityIds1_7[(i * 2)] = ((Integer)entry.getKey()).intValue();
      entityIds1_7[(i * 2 + 1)] = ((Integer)entry.getValue()).intValue();
      
      entityIds1_8[i] = ((Integer)entry.getKey()).intValue();
    }
    this._destroy1_7 = new PacketPlayOutEntityDestroy(entityIds1_7);
    this._destroy1_8 = new PacketPlayOutEntityDestroy(entityIds1_8);
  }
  
  private void makeSpawnPackets()
  {
    this._packets1_7 = new Packet[this._hologramText.length * 3];
    this._packets1_8 = new Packet[this._hologramText.length * 1];
    if (this._entityIds.size() < this._hologramText.length)
    {
      this._makeDestroyPackets = true;
      for (int i = this._entityIds.size(); i < this._hologramText.length; i++) {
        this._entityIds.add(new AbstractMap.SimpleEntry(Integer.valueOf(UtilEnt.getNewEntityId()), Integer.valueOf(UtilEnt.getNewEntityId())));
      }
    }
    else
    {
      this._makeDestroyPackets = true;
      while (this._entityIds.size() > this._hologramText.length) {
        this._entityIds.remove(this._hologramText.length);
      }
    }
    for (int textRow = 0; textRow < this._hologramText.length; textRow++)
    {
      Map.Entry<Integer, Integer> entityIds = (Map.Entry)this._entityIds.get(textRow);
      
      Packet[] packets1_7 = makeSpawnPackets1_7(textRow, ((Integer)entityIds.getKey()).intValue(), ((Integer)entityIds.getValue()).intValue(), this._hologramText[textRow]);
      for (int i = 0; i < packets1_7.length; i++) {
        this._packets1_7[(textRow * 3 + i)] = packets1_7[i];
      }
      Packet[] packets1_8 = makeSpawnPackets1_8(textRow, ((Integer)entityIds.getKey()).intValue(), this._hologramText[textRow]);
      for (int i = 0; i < packets1_8.length; i++) {
        this._packets1_8[(textRow + i)] = packets1_8[i];
      }
    }
  }
  
  private Packet[] makeSpawnPackets1_7(int height, int witherId, int horseId, String horseName)
  {
    PacketPlayOutSpawnEntity spawnWitherSkull = new PacketPlayOutSpawnEntity();
    
    spawnWitherSkull.a = witherId;
    spawnWitherSkull.b = ((int)(getLocation().getX() * 32.0D));
    spawnWitherSkull.c = ((int)((getLocation().getY() + 54.6D + height * 0.285D) * 32.0D));
    spawnWitherSkull.d = ((int)(getLocation().getZ() * 32.0D));
    spawnWitherSkull.j = 66;
    
    PacketPlayOutSpawnEntityLiving spawnHorse = new PacketPlayOutSpawnEntityLiving();
    DataWatcher watcher = new DataWatcher(null);
    
    spawnHorse.a = horseId;
    spawnHorse.b = 100;
    spawnHorse.c = ((int)(getLocation().getX() * 32.0D));
    spawnHorse.d = ((int)((getLocation().getY() + 54.83D + height * 0.285D + 0.23D) * 32.0D));
    spawnHorse.e = ((int)(getLocation().getZ() * 32.0D));
    spawnHorse.l = watcher;
    
    watcher.a(0, Byte.valueOf((byte)0));
    watcher.a(1, Short.valueOf((short)300));
    watcher.a(10, horseName);
    watcher.a(11, Byte.valueOf((byte)1));
    watcher.a(12, Integer.valueOf(-1700000));
    
    PacketPlayOutAttachEntity attachEntity = new PacketPlayOutAttachEntity();
    
    attachEntity.b = horseId;
    attachEntity.c = witherId;
    
    return 
      new Packet[] {
      spawnWitherSkull, spawnHorse, attachEntity };
  }
  
  private Packet[] makeSpawnPackets1_8(int textRow, int entityId, String lineOfText)
  {
    PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving();
    DataWatcher watcher = new DataWatcher(null);
    
    packet.a = entityId;
    packet.b = 30;
    packet.c = ((int)(getLocation().getX() * 32.0D));
    packet.d = ((int)((getLocation().getY() + -2.1D + textRow * 0.285D) * 32.0D));
    packet.e = ((int)(getLocation().getZ() * 32.0D));
    packet.l = watcher;
    
    watcher.a(0, Byte.valueOf((byte)32));
    watcher.a(2, lineOfText);
    watcher.a(3, Byte.valueOf((byte)1));
    
    return 
      new Packet[] {
      packet };
  }
  
  public Hologram removePlayer(Player player)
  {
    return addPlayer(player.getName());
  }
  
  public Hologram removePlayer(String player)
  {
    this._playersInList.remove(player);
    return this;
  }
  
  public Hologram setFollowEntity(Entity entityToFollow)
  {
    this._followEntity = entityToFollow;
    this.relativeToEntity = (entityToFollow == null ? null : this._location.clone().subtract(entityToFollow.getLocation())
      .toVector());
    
    return this;
  }
  
  public Hologram setHologramTarget(HologramTarget newTarget)
  {
    this._target = newTarget;
    return this;
  }
  
  public Hologram setLocation(Location newLocation)
  {
    this._makeSpawnPackets = true;
    
    Location oldLocation = getLocation();
    this._location = newLocation.clone();
    if (getEntityFollowing() != null) {
      this.relativeToEntity = this._location.clone().subtract(getEntityFollowing().getLocation()).toVector();
    }
    if (isInUse())
    {
      ArrayList<Player> canSee = getNearbyPlayers();
      Iterator<Player> itel = this._playersTracking.iterator();
      while (itel.hasNext())
      {
        Player player = (Player)itel.next();
        if (!canSee.contains(player))
        {
          itel.remove();
          if (player.getWorld() == getLocation().getWorld()) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(getDestroyPacket(player));
          }
        }
      }
      itel = canSee.iterator();
      while (itel.hasNext())
      {
        Player player = (Player)itel.next();
        if (!this._playersTracking.contains(player))
        {
          this._playersTracking.add(player);
          itel.remove();
          Packet[] arrayOfPacket1;
          int j = (arrayOfPacket1 = getSpawnPackets(player)).length;
          for (int i = 0; i < j; i++)
          {
            Packet packet = arrayOfPacket1[i];
            
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
          }
        }
      }
      if (!canSee.isEmpty())
      {
        this._lastMovement.add(new Vector(newLocation.getX() - oldLocation.getX(), newLocation.getY() - oldLocation.getY(), 
          newLocation.getZ() - oldLocation.getZ()));
        
        int x = (int)Math.floor(32.0D * this._lastMovement.getX());
        int y = (int)Math.floor(32.0D * this._lastMovement.getY());
        int z = (int)Math.floor(32.0D * this._lastMovement.getZ());
        
        Packet[] packets1_7 = new Packet[this._hologramText.length];
        Packet[] packets1_8 = new Packet[this._hologramText.length];
        
        int i = 0;
        PacketPlayOutEntityTeleport teleportPacket;
        if ((x >= -128) && (x <= 127) && (y >= -128) && (y <= 127) && (z >= -128) && (z <= 127))
        {
          this._lastMovement.subtract(new Vector(x / 32.0D, y / 32.0D, z / 32.0D));
          for (Map.Entry<Integer, Integer> entityId : this._entityIds)
          {
            PacketPlayOutRelEntityMove relMove = new PacketPlayOutRelEntityMove();
            
            relMove.a = ((Integer)entityId.getKey()).intValue();
            relMove.b = ((byte)x);
            relMove.c = ((byte)y);
            relMove.d = ((byte)z);
            
            packets1_7[i] = relMove;
            packets1_8[i] = relMove;
            i++;
          }
        }
        else
        {
          x = (int)Math.floor(32.0D * newLocation.getX());
          z = (int)Math.floor(32.0D * newLocation.getZ());
          
          this._lastMovement = new Vector(newLocation.getX() - x / 32.0D, 0.0D, newLocation.getZ() - z / 32.0D);
          for (Map.Entry<Integer, Integer> entityId : this._entityIds)
          {
            for (int b = 0; b < 2; b++)
            {
              teleportPacket = new PacketPlayOutEntityTeleport();
              teleportPacket.a = ((Integer)entityId.getKey()).intValue();
              teleportPacket.b = x;
              teleportPacket.c = 
                ((int)Math.floor((oldLocation.getY() + (b == 0 ? 54.6D : -2.1D) + i * 0.285D) * 32.0D));
              teleportPacket.d = z;
              if (b == 0) {
                packets1_7[i] = teleportPacket;
              } else {
                packets1_8[i] = teleportPacket;
              }
            }
            i++;
          }
        }
        PacketPlayOutEntityTeleport localPacketPlayOutEntityTeleport1;
        for (??? = canSee.iterator(); ???.hasNext(); teleportPacket < localPacketPlayOutEntityTeleport1)
        {
          Player player = (Player)???.next();
          Packet[] arrayOfPacket2;
          localPacketPlayOutEntityTeleport1 = (arrayOfPacket2 = UtilPlayer.is1_8(player) ? packets1_8 : packets1_7).length;teleportPacket = 0; continue;Packet packet = arrayOfPacket2[teleportPacket];
          
          ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);teleportPacket++;
        }
      }
    }
    return this;
  }
  
  public Hologram setRemoveOnEntityDeath()
  {
    this._removeEntityDeath = true;
    return this;
  }
  
  public Hologram setText(String... newLines)
  {
    String[] newText = new String[newLines.length];
    for (int i = 0; i < newText.length; i++) {
      newText[i] = newLines[(newText.length - (i + 1))];
    }
    if (newText.equals(this._hologramText)) {
      return this;
    }
    this._makeSpawnPackets = true;
    if (isInUse())
    {
      int[] destroy1_7 = new int[0];
      int[] destroy1_8 = new int[0];
      
      ArrayList<Packet> packets1_7 = new ArrayList();
      ArrayList<Packet> packets1_8 = new ArrayList();
      if (this._hologramText.length != newText.length) {
        this._makeDestroyPackets = true;
      }
      DataWatcher watcher1_7;
      for (int i = 0; i < Math.max(this._hologramText.length, newText.length); i++) {
        if (i >= this._hologramText.length)
        {
          Map.Entry<Integer, Integer> entry = new AbstractMap.SimpleEntry(Integer.valueOf(UtilEnt.getNewEntityId()), Integer.valueOf(UtilEnt.getNewEntityId()));
          this._entityIds.add(entry);
          
          packets1_7.addAll(Arrays.asList(makeSpawnPackets1_7(i, ((Integer)entry.getKey()).intValue(), ((Integer)entry.getValue()).intValue(), newText[i])));
          
          packets1_8.addAll(Arrays.asList(makeSpawnPackets1_8(i, ((Integer)entry.getKey()).intValue(), newText[i])));
        }
        else if (i >= newText.length)
        {
          Map.Entry<Integer, Integer> entry = (Map.Entry)this._entityIds.remove(newText.length);
          
          destroy1_7 = Arrays.copyOf(destroy1_7, destroy1_7.length + 2);
          
          destroy1_7[(destroy1_7.length - 2)] = ((Integer)entry.getKey()).intValue();
          destroy1_7[(destroy1_7.length - 1)] = ((Integer)entry.getValue()).intValue();
          
          destroy1_8 = Arrays.copyOf(destroy1_8, destroy1_8.length + 1);
          destroy1_8[(destroy1_8.length - 1)] = ((Integer)entry.getKey()).intValue();
        }
        else if (!newText[i].equals(this._hologramText[i]))
        {
          entry = (Map.Entry)this._entityIds.get(i);
          PacketPlayOutEntityMetadata metadata1_7 = new PacketPlayOutEntityMetadata();
          
          metadata1_7.a = ((Integer)entry.getValue()).intValue();
          
          watcher1_7 = new DataWatcher(null);
          
          watcher1_7.a(0, Byte.valueOf((byte)0));
          watcher1_7.a(1, Short.valueOf((short)300));
          watcher1_7.a(10, newText[i]);
          watcher1_7.a(11, Byte.valueOf((byte)1));
          watcher1_7.a(12, Integer.valueOf(-1700000));
          
          metadata1_7.b = watcher1_7.c();
          
          packets1_7.add(metadata1_7);
          
          PacketPlayOutEntityMetadata metadata1_8 = new PacketPlayOutEntityMetadata();
          
          metadata1_8.a = ((Integer)entry.getKey()).intValue();
          
          DataWatcher watcher1_8 = new DataWatcher(null);
          
          watcher1_8.a(0, Byte.valueOf((byte)32));
          watcher1_8.a(2, newText[i]);
          watcher1_8.a(3, Byte.valueOf((byte)1));
          
          metadata1_8.b = watcher1_8.c();
          
          packets1_8.add(metadata1_8);
        }
      }
      if (destroy1_7.length > 0) {
        packets1_7.add(new PacketPlayOutEntityDestroy(destroy1_7));
      }
      if (destroy1_8.length > 0) {
        packets1_8.add(new PacketPlayOutEntityDestroy(destroy1_8));
      }
      for (Map.Entry<Integer, Integer> entry = this._playersTracking.iterator(); entry.hasNext(); watcher1_7.hasNext())
      {
        Player player = (Player)entry.next();
        
        watcher1_7 = (UtilPlayer.is1_8(player) ? packets1_8 : packets1_7).iterator(); continue;Packet packet = (Packet)watcher1_7.next();
        
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
      }
    }
    this._hologramText = newText;
    
    return this;
  }
  
  public Hologram setViewDistance(int newDistance)
  {
    this._viewDistance = newDistance;
    return setLocation(getLocation());
  }
  
  public Hologram start()
  {
    if (!isInUse())
    {
      this._hologramManager.addHologram(this);
      this._playersTracking.addAll(getNearbyPlayers());
      int j;
      int i;
      for (Iterator localIterator = this._playersTracking.iterator(); localIterator.hasNext(); i < j)
      {
        Player player = (Player)localIterator.next();
        Packet[] arrayOfPacket;
        j = (arrayOfPacket = getSpawnPackets(player)).length;i = 0; continue;Packet packet = arrayOfPacket[i];
        
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);i++;
      }
      this._lastMovement = new Vector();
    }
    return this;
  }
  
  public Hologram stop()
  {
    if (isInUse())
    {
      this._hologramManager.removeHologram(this);
      for (Player player : this._playersTracking) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(getDestroyPacket(player));
      }
      this._playersTracking.clear();
      this._lastMovement = null;
    }
    return this;
  }
}

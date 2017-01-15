package mineplex.core.disguise;

import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import mineplex.core.MiniPlugin;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.disguise.disguises.DisguiseBase;
import mineplex.core.disguise.disguises.DisguiseBlock;
import mineplex.core.disguise.disguises.DisguiseInsentient;
import mineplex.core.disguise.disguises.DisguiseLiving;
import mineplex.core.disguise.disguises.DisguisePlayer;
import mineplex.core.disguise.disguises.DisguiseRabbit;
import mineplex.core.packethandler.IPacketHandler;
import mineplex.core.packethandler.PacketHandler;
import mineplex.core.packethandler.PacketInfo;
import mineplex.core.packethandler.PacketVerifier;
import mineplex.core.timing.TimingManager;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import net.minecraft.server.v1_7_R4.ChunkAddEntityEvent;
import net.minecraft.server.v1_7_R4.ChunkSection;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EntityTracker;
import net.minecraft.server.v1_7_R4.EntityTrackerEntry;
import net.minecraft.server.v1_7_R4.IntHashMap;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayOutBed;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_7_R4.PacketPlayOutMapChunk;
import net.minecraft.server.v1_7_R4.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_7_R4.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class DisguiseManager
  extends MiniPlugin
  implements IPacketHandler
{
  private NautHashMap<Integer, DisguiseBase> _spawnPacketMap = new NautHashMap();
  private NautHashMap<Integer, PacketPlayOutEntityVelocity> _movePacketMap = new NautHashMap();
  private NautHashMap<Integer, PacketPlayOutEntityVelocity> _moveTempMap = new NautHashMap();
  private HashSet<Integer> _goingUp = new HashSet();
  private NautHashMap<String, DisguiseBase> _entityDisguiseMap = new NautHashMap();
  private NautHashMap<DisguiseBase, HashSet<Player>> _disguisePlayerMap = new NautHashMap();
  private HashSet<String> _blockedNames = new HashSet();
  private NautHashMap<Integer, Map.Entry<DisguiseBase, Player[]>> _futureDisguises = new NautHashMap();
  private NautHashMap<Integer, NautHashMap<Integer, Long>> _lastRabbitHop = new NautHashMap();
  private boolean _handlingPacket = false;
  private Field _attributesA;
  private Field _attributesB;
  private Field _soundB;
  private Field _soundC;
  private Field _soundD;
  private Field _bedA;
  private Field _bedB;
  private Field _bedD;
  private Field _xChunk;
  private Field _zChunk;
  private Field _eStatusId;
  private Field _eStatusState;
  private net.minecraft.server.v1_7_R4.Chunk _bedChunk;
  private boolean _bedPackets;
  
  public DisguiseManager(JavaPlugin plugin, PacketHandler packetHandler)
  {
    super("Disguise Manager", plugin);
    
    packetHandler.addPacketHandler(this);
    try
    {
      this._attributesA = PacketPlayOutUpdateAttributes.class.getDeclaredField("a");
      this._attributesA.setAccessible(true);
      this._attributesB = PacketPlayOutUpdateAttributes.class.getDeclaredField("b");
      this._attributesB.setAccessible(true);
      this._soundB = PacketPlayOutNamedSoundEffect.class.getDeclaredField("b");
      this._soundB.setAccessible(true);
      this._soundC = PacketPlayOutNamedSoundEffect.class.getDeclaredField("c");
      this._soundC.setAccessible(true);
      this._soundD = PacketPlayOutNamedSoundEffect.class.getDeclaredField("d");
      this._soundD.setAccessible(true);
      this._bedA = PacketPlayOutBed.class.getDeclaredField("a");
      this._bedA.setAccessible(true);
      this._bedB = PacketPlayOutBed.class.getDeclaredField("b");
      this._bedB.setAccessible(true);
      this._bedD = PacketPlayOutBed.class.getDeclaredField("d");
      this._bedD.setAccessible(true);
      this._eStatusId = PacketPlayOutEntityStatus.class.getDeclaredField("a");
      this._eStatusId.setAccessible(true);
      this._eStatusState = PacketPlayOutEntityStatus.class.getDeclaredField("b");
      this._eStatusState.setAccessible(true);
      
      this._bedChunk = new net.minecraft.server.v1_7_R4.Chunk(null, 0, 0);
      Field cSection = net.minecraft.server.v1_7_R4.Chunk.class.getDeclaredField("sections");
      cSection.setAccessible(true);
      
      ChunkSection chunkSection = new ChunkSection(0, false);
      net.minecraft.server.v1_7_R4.Block block = net.minecraft.server.v1_7_R4.Block.getById(Material.BED_BLOCK.getId());
      BlockFace[] arrayOfBlockFace;
      int j = (arrayOfBlockFace = new BlockFace[] { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH }).length;
      for (int i = 0; i < j; i++)
      {
        BlockFace face = arrayOfBlockFace[i];
        
        chunkSection.setTypeId(1 + face.getModX(), 0, 1 + face.getModZ(), block);
        chunkSection.setData(1 + face.getModX(), 0, 1 + face.getModZ(), face.ordinal());
        chunkSection.setSkyLight(1 + face.getModX(), 0, 1 + face.getModZ(), 0);
        chunkSection.setEmittedLight(1 + face.getModX(), 0, 1 + face.getModZ(), 0);
      }
      ChunkSection[] chunkSections = new ChunkSection[16];
      chunkSections[0] = chunkSection;
      cSection.set(this._bedChunk, chunkSections);
      
      this._bedChunk.world = ((CraftWorld)Bukkit.getWorlds().get(0)).getHandle();
      
      this._xChunk = net.minecraft.server.v1_7_R4.Chunk.class.getField("locX");
      this._xChunk.setAccessible(true);
      
      this._zChunk = net.minecraft.server.v1_7_R4.Chunk.class.getField("locZ");
      this._zChunk.setAccessible(true);
    }
    catch (IllegalArgumentException e)
    {
      e.printStackTrace();
    }
    catch (NoSuchFieldException e)
    {
      e.printStackTrace();
    }
    catch (SecurityException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
  }
  
  public void addFutureDisguise(DisguiseBase disguise, Player... players)
  {
    final int entityId = UtilEnt.getNewEntityId(false);
    
    this._futureDisguises.put(Integer.valueOf(entityId), new AbstractMap.SimpleEntry(disguise, players));
    
    Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
    {
      public void run()
      {
        if (DisguiseManager.this._futureDisguises.containsKey(Integer.valueOf(entityId)))
        {
          Map.Entry<DisguiseBase, Player[]> entry = (Map.Entry)DisguiseManager.this._futureDisguises.remove(Integer.valueOf(entityId));
          
          org.bukkit.entity.Entity entity = UtilEnt.getEntityById(entityId);
          if (entity != null)
          {
            ((DisguiseBase)entry.getKey()).setEntity(entity);
            
            DisguiseManager.this.disguise((DisguiseBase)entry.getKey(), (Player[])entry.getValue());
          }
        }
      }
    }, 4L);
  }
  
  public void addViewerToDisguise(DisguiseBase disguise, Player player, boolean reapply)
  {
    ((HashSet)this._disguisePlayerMap.get(disguise)).add(player);
    if (reapply) {
      refreshTrackers(disguise.GetEntity().getBukkitEntity(), 
        new Player[] {
        player });
    }
  }
  
  @EventHandler
  public void ChunkAddEntity(ChunkAddEntityEvent event)
  {
    DisguiseBase disguise = (DisguiseBase)this._entityDisguiseMap.get(event.GetEntity().getUniqueId().toString());
    if (disguise != null)
    {
      disguise.setEntity(event.GetEntity());
      this._spawnPacketMap.put(Integer.valueOf(event.GetEntity().getEntityId()), disguise);
      this._entityDisguiseMap.remove(event.GetEntity().getUniqueId().toString());
      if ((disguise instanceof DisguiseRabbit)) {
        this._lastRabbitHop.put(Integer.valueOf(disguise.GetEntityId()), new NautHashMap());
      }
    }
  }
  
  @EventHandler
  public void chunkJoin(PlayerJoinEvent event)
  {
    if (!this._bedPackets) {
      return;
    }
    chunkMove(event.getPlayer(), event.getPlayer().getLocation(), null);
  }
  
  private void chunkMove(Player player, Location newLoc, Location oldLoc)
  {
    for (Packet packet : getChunkMovePackets(player, newLoc, oldLoc)) {
      UtilPlayer.sendPacket(player, new Packet[] { packet });
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void chunkMove(PlayerMoveEvent event)
  {
    if (!this._bedPackets) {
      return;
    }
    Location to = event.getTo();
    Location from = event.getFrom();
    
    int x1 = getChunk(to.getX());
    int z1 = getChunk(to.getZ());
    int x2 = getChunk(from.getX());
    int z2 = getChunk(from.getZ());
    if ((x1 != x2) || (z1 != z2)) {
      chunkMove(event.getPlayer(), to, from);
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void chunkTeleport(PlayerTeleportEvent event)
  {
    if (!this._bedPackets) {
      return;
    }
    Location to = event.getTo();
    Location from = event.getFrom();
    if (to.getWorld() == from.getWorld())
    {
      int x1 = getChunk(to.getX());
      int z1 = getChunk(to.getZ());
      
      int x2 = getChunk(from.getX());
      int z2 = getChunk(from.getZ());
      if ((x1 != x2) || (z1 != z2))
      {
        final Player player = event.getPlayer();
        final Location prev = new Location(to.getWorld(), x1, 0.0D, z1);
        
        chunkMove(player, null, from);
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(this._plugin, new Runnable()
        {
          public void run()
          {
            if (!player.isOnline()) {
              return;
            }
            Location loc = player.getLocation();
            if (player.getWorld() != loc.getWorld()) {
              return;
            }
            int x2 = DisguiseManager.this.getChunk(loc.getX());
            int z2 = DisguiseManager.this.getChunk(loc.getZ());
            if ((prev.getBlockX() == x2) && (prev.getBlockZ() == z2) && (loc.getWorld() == prev.getWorld())) {
              DisguiseManager.this.chunkMove(player, loc, null);
            }
            DisguiseManager.this.refreshBedTrackers(player);
          }
        });
      }
    }
  }
  
  @EventHandler
  public void ChunkUnload(ChunkUnloadEvent event)
  {
    org.bukkit.entity.Entity[] arrayOfEntity;
    int j = (arrayOfEntity = event.getChunk().getEntities()).length;
    for (int i = 0; i < j; i++)
    {
      org.bukkit.entity.Entity entity = arrayOfEntity[i];
      if (this._spawnPacketMap.containsKey(Integer.valueOf(entity.getEntityId())))
      {
        this._entityDisguiseMap.put(entity.getUniqueId().toString(), (DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entity.getEntityId())));
        this._spawnPacketMap.remove(Integer.valueOf(entity.getEntityId()));
        this._lastRabbitHop.remove(Integer.valueOf(entity.getEntityId()));
      }
    }
  }
  
  public void clearDisguises()
  {
    this._spawnPacketMap.clear();
    this._movePacketMap.clear();
    this._moveTempMap.clear();
    this._goingUp.clear();
    this._entityDisguiseMap.clear();
    this._disguisePlayerMap.clear();
    if (this._bedPackets) {
      unregisterBedChunk();
    }
  }
  
  private boolean containsSpawnDisguise(Player owner, int entityId)
  {
    return (this._spawnPacketMap.containsKey(Integer.valueOf(entityId))) && (
      (((DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId))).Global) || ((this._disguisePlayerMap.containsKey((DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId)))) && 
      (((HashSet)this._disguisePlayerMap.get((DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId)))).contains(owner))));
  }
  
  public void disguise(DisguiseBase disguise, boolean refreshTrackers, Player... players)
  {
    if (!disguise.GetEntity().isAlive()) {
      return;
    }
    if ((!this._bedPackets) && ((disguise instanceof DisguisePlayer)) && (((DisguisePlayer)disguise).getSleepingDirection() != null))
    {
      this._bedPackets = true;
      for (Player player : Bukkit.getOnlinePlayers()) {
        UtilPlayer.sendPacket(player, getBedChunkLoadPackets(player, player.getLocation()));
      }
    }
    if (players.length != 0) {
      disguise.Global = false;
    }
    this._spawnPacketMap.put(Integer.valueOf(disguise.GetEntityId()), disguise);
    this._disguisePlayerMap.put(disguise, new HashSet());
    if ((disguise instanceof DisguiseRabbit)) {
      this._lastRabbitHop.put(Integer.valueOf(disguise.GetEntityId()), new NautHashMap());
    }
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = players).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      addViewerToDisguise(disguise, player, false);
    }
    if (((disguise.GetEntity() instanceof Player)) && ((disguise instanceof DisguisePlayer))) {
      if (!((Player)disguise.GetEntity()).getName().equalsIgnoreCase(((DisguisePlayer)disguise).getName())) {
        this._blockedNames.add(((Player)disguise.GetEntity()).getName());
      }
    }
    if (refreshTrackers) {
      refreshTrackers(disguise.GetEntity().getBukkitEntity(), 
        disguise.Global ? (Player[])Bukkit.getOnlinePlayers().toArray(new Player[0]) : players);
    }
  }
  
  public void disguise(DisguiseBase disguise, Player... players)
  {
    disguise(disguise, true, players);
  }
  
  public Packet[] getBedChunkLoadPackets(Player player, Location newLoc)
  {
    prepareChunk(newLoc);
    
    Packet[] packets = new Packet[2];
    
    packets[0] = new PacketPlayOutMapChunk(this._bedChunk, true, 0, UtilPlayer.is1_8(player) ? 48 : 0);
    
    packets[1] = new PacketPlayOutMapChunkBulk(Arrays.asList(new net.minecraft.server.v1_7_R4.Chunk[] { this._bedChunk }), UtilPlayer.is1_8(player) ? 48 : 0);
    
    return packets;
  }
  
  public Packet getBedChunkUnloadPacket(Player player, Location oldLoc)
  {
    prepareChunk(oldLoc);
    
    return new PacketPlayOutMapChunk(this._bedChunk, true, 0, UtilPlayer.is1_8(player) ? 48 : 0);
  }
  
  private Packet[] getBedPackets(Location recieving, DisguisePlayer playerDisguise)
  {
    try
    {
      PacketPlayOutBed bedPacket = new PacketPlayOutBed();
      
      this._bedA.set(bedPacket, Integer.valueOf(playerDisguise.GetEntityId()));
      
      int chunkX = getChunk(recieving.getX());
      int chunkZ = getChunk(recieving.getZ());
      
      this._bedB.set(bedPacket, Integer.valueOf(chunkX * 16 + 1 + playerDisguise.getSleepingDirection().getModX()));
      this._bedD.set(bedPacket, Integer.valueOf(chunkZ * 16 + 1 + playerDisguise.getSleepingDirection().getModZ()));
      
      PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(playerDisguise.GetEntity());
      
      teleportPacket.c += 11;
      
      return 
        new Packet[] {
        bedPacket, teleportPacket };
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return null;
  }
  
  private int getChunk(double block)
  {
    int chunk = (int)Math.floor(block / 16.0D) - 17;
    chunk -= chunk % 8;
    return chunk;
  }
  
  private ArrayList<Packet> getChunkMovePackets(Player player, Location newLoc, Location oldLoc)
  {
    ArrayList<Packet> packets = new ArrayList();
    if (newLoc != null)
    {
      packets.addAll(Arrays.asList(getBedChunkLoadPackets(player, newLoc)));
      
      EntityPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
      for (Map.Entry<DisguiseBase, HashSet<Player>> entry : this._disguisePlayerMap.entrySet()) {
        if ((((DisguiseBase)entry.getKey()).Global) || (((HashSet)entry.getValue()).contains(player)))
        {
          EntityTrackerEntry tracker = getEntityTracker(((DisguiseBase)entry.getKey()).GetEntity());
          if ((tracker != null) && (tracker.trackedPlayers.contains(nmsPlayer))) {
            if (((entry.getKey() instanceof DisguisePlayer)) && 
              (((DisguisePlayer)entry.getKey()).getSleepingDirection() != null)) {
              packets.addAll(Arrays.asList(getBedPackets(newLoc, (DisguisePlayer)entry.getKey())));
            }
          }
        }
      }
    }
    if (oldLoc != null) {
      packets.add(getBedChunkUnloadPacket(player, oldLoc));
    }
    return packets;
  }
  
  public DisguiseBase getDisguise(LivingEntity entity)
  {
    return (DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entity.getEntityId()));
  }
  
  private EntityTrackerEntry getEntityTracker(net.minecraft.server.v1_7_R4.Entity entity)
  {
    return (EntityTrackerEntry)((WorldServer)entity.world).tracker.trackedEntities.get(entity.getId());
  }
  
  public void handle(PacketInfo packetInfo)
  {
    if (this._handlingPacket) {
      return;
    }
    Packet packet = packetInfo.getPacket();
    Player owner = packetInfo.getPlayer();
    final PacketVerifier packetVerifier = packetInfo.getVerifier();
    if ((UtilPlayer.is1_8(owner)) && (
      ((packet instanceof PacketPlayOutRelEntityMoveLook)) || ((packet instanceof PacketPlayOutRelEntityMove))))
    {
      int entityId = -1;
      if ((packet instanceof PacketPlayOutRelEntityMoveLook))
      {
        entityId = ((PacketPlayOutRelEntityMoveLook)packet).a;
      }
      else if ((packet instanceof PacketPlayOutRelEntityMove))
      {
        PacketPlayOutRelEntityMove rPacket = (PacketPlayOutRelEntityMove)packet;
        if ((rPacket.b != 0) || (rPacket.c != 0) || (rPacket.d != 0)) {
          entityId = rPacket.a;
        }
      }
      if (this._lastRabbitHop.containsKey(Integer.valueOf(entityId)))
      {
        NautHashMap<Integer, Long> rabbitHops = (NautHashMap)this._lastRabbitHop.get(Integer.valueOf(entityId));
        if (rabbitHops != null)
        {
          long last = rabbitHops.containsKey(Integer.valueOf(owner.getEntityId())) ? System.currentTimeMillis() - 
            ((Long)rabbitHops.get(Integer.valueOf(owner.getEntityId()))).longValue() : 1000L;
          if ((last > 500L) || (last < 100L))
          {
            rabbitHops.put(Integer.valueOf(owner.getEntityId()), Long.valueOf(System.currentTimeMillis()));
            
            PacketPlayOutEntityStatus entityStatus = new PacketPlayOutEntityStatus();
            try
            {
              this._eStatusId.set(entityStatus, Integer.valueOf(entityId));
              this._eStatusState.set(entityStatus, Byte.valueOf((byte)1));
              
              handlePacket(entityStatus, packetVerifier);
            }
            catch (Exception ex)
            {
              ex.printStackTrace();
            }
          }
        }
      }
    }
    if ((packet instanceof PacketPlayOutPlayerInfo))
    {
      if (this._blockedNames.contains(((PacketPlayOutPlayerInfo)packet).username)) {
        packetInfo.setCancelled(true);
      }
    }
    else if (((packet instanceof PacketPlayOutSpawnEntity)) || ((packet instanceof PacketPlayOutSpawnEntityLiving)) || 
      ((packet instanceof PacketPlayOutNamedEntitySpawn)))
    {
      int entityId = -1;
      if ((packet instanceof PacketPlayOutSpawnEntity)) {
        entityId = ((PacketPlayOutSpawnEntity)packet).a;
      } else if ((packet instanceof PacketPlayOutSpawnEntityLiving)) {
        entityId = ((PacketPlayOutSpawnEntityLiving)packet).a;
      } else if ((packet instanceof PacketPlayOutNamedEntitySpawn)) {
        entityId = ((PacketPlayOutNamedEntitySpawn)packet).a;
      }
      if (this._futureDisguises.containsKey(Integer.valueOf(entityId)))
      {
        Map.Entry<DisguiseBase, Player[]> entry = (Map.Entry)this._futureDisguises.remove(Integer.valueOf(entityId));
        
        org.bukkit.entity.Entity entity = UtilEnt.getEntityById(entityId);
        if (entity != null)
        {
          ((DisguiseBase)entry.getKey()).setEntity(entity);
          
          disguise((DisguiseBase)entry.getKey(), false, (Player[])entry.getValue());
        }
      }
      if ((this._spawnPacketMap.containsKey(Integer.valueOf(entityId))) && (
        (((DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId))).Global) || 
        (((HashSet)this._disguisePlayerMap.get((DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId)))).contains(owner))))
      {
        packetInfo.setCancelled(true);
        
        handleSpawnPackets(packetInfo, (DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId)));
      }
    }
    else if ((packet instanceof PacketPlayOutUpdateAttributes))
    {
      int entityId = -1;
      try
      {
        entityId = ((Integer)this._attributesA.get((PacketPlayOutUpdateAttributes)packet)).intValue();
      }
      catch (IllegalArgumentException e)
      {
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
      if ((this._spawnPacketMap.containsKey(Integer.valueOf(entityId))) && 
        (owner.getEntityId() != entityId) && (
        (((DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId))).Global) || 
        (((HashSet)this._disguisePlayerMap.get((DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId)))).contains(owner)))) {
        if ((this._spawnPacketMap.get(Integer.valueOf(entityId)) instanceof DisguiseBlock)) {
          packetInfo.setCancelled(true);
        }
      }
    }
    else if ((packet instanceof PacketPlayOutAnimation))
    {
      int entityId = ((PacketPlayOutAnimation)packet).a;
      if ((containsSpawnDisguise(owner, entityId)) && (owner.getEntityId() != entityId)) {
        packetInfo.setCancelled(true);
      }
    }
    else if ((packet instanceof PacketPlayOutEntityMetadata))
    {
      int entityId = ((PacketPlayOutEntityMetadata)packet).a;
      if ((containsSpawnDisguise(owner, entityId)) && (owner.getEntityId() != entityId))
      {
        handlePacket(((DisguiseBase)this._spawnPacketMap.get(Integer.valueOf(entityId))).GetMetaDataPacket(), packetVerifier);
        packetInfo.setCancelled(true);
      }
    }
    else if ((packet instanceof PacketPlayOutEntityEquipment))
    {
      int entityId = ((PacketPlayOutEntityEquipment)packet).a;
      if ((containsSpawnDisguise(owner, entityId)) && ((this._spawnPacketMap.get(Integer.valueOf(entityId)) instanceof DisguiseInsentient))) {
        if ((!((DisguiseInsentient)this._spawnPacketMap.get(Integer.valueOf(entityId))).armorVisible()) && 
          (((PacketPlayOutEntityEquipment)packet).b != 0)) {
          packetInfo.setCancelled(true);
        }
      }
    }
    else if ((packet instanceof PacketPlayOutEntityVelocity))
    {
      PacketPlayOutEntityVelocity velocityPacket = (PacketPlayOutEntityVelocity)packet;
      if (velocityPacket.a == owner.getEntityId())
      {
        if (velocityPacket.c > 0) {
          this._goingUp.add(Integer.valueOf(velocityPacket.a));
        }
      }
      else
      {
        if ((velocityPacket.b == 0) && (velocityPacket.c == 0) && (velocityPacket.d == 0)) {
          return;
        }
        if (this._spawnPacketMap.containsKey(Integer.valueOf(velocityPacket.a))) {
          packetInfo.setCancelled(true);
        }
      }
    }
    else if ((packet instanceof PacketPlayOutRelEntityMove))
    {
      PacketPlayOutRelEntityMove movePacket = (PacketPlayOutRelEntityMove)packet;
      if (movePacket.a == owner.getEntityId()) {
        return;
      }
      if ((this._goingUp.contains(Integer.valueOf(movePacket.a))) && (movePacket.c != 0) && (movePacket.c < 20))
      {
        this._goingUp.remove(Integer.valueOf(movePacket.a));
        this._movePacketMap.remove(Integer.valueOf(movePacket.a));
      }
      if (!containsSpawnDisguise(owner, movePacket.a)) {
        return;
      }
      final PacketPlayOutEntityVelocity velocityPacket = new PacketPlayOutEntityVelocity();
      velocityPacket.a = movePacket.a;
      velocityPacket.b = (movePacket.b * 100);
      velocityPacket.c = (movePacket.c * 100);
      velocityPacket.d = (movePacket.d * 100);
      if (this._movePacketMap.containsKey(Integer.valueOf(movePacket.a)))
      {
        PacketPlayOutEntityVelocity lastVelocityPacket = (PacketPlayOutEntityVelocity)this._movePacketMap.get(Integer.valueOf(movePacket.a));
        
        velocityPacket.b = ((int)(0.8D * lastVelocityPacket.b));
        velocityPacket.c = ((int)(0.8D * lastVelocityPacket.c));
        velocityPacket.d = ((int)(0.8D * lastVelocityPacket.d));
      }
      this._movePacketMap.put(Integer.valueOf(movePacket.a), velocityPacket);
      
      packetVerifier.bypassProcess(velocityPacket);
      if ((this._goingUp.contains(Integer.valueOf(movePacket.a))) && (movePacket.c != 0) && (movePacket.c > 20)) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
        {
          public void run()
          {
            packetVerifier.bypassProcess(velocityPacket);
          }
        });
      }
      (this._spawnPacketMap.get(Integer.valueOf(movePacket.a)) instanceof DisguiseBlock);
    }
    else if ((packet instanceof PacketPlayOutRelEntityMoveLook))
    {
      PacketPlayOutRelEntityMoveLook movePacket = (PacketPlayOutRelEntityMoveLook)packet;
      if (movePacket.a == owner.getEntityId()) {
        return;
      }
      if ((this._goingUp.contains(Integer.valueOf(movePacket.a))) && (movePacket.c != 0) && (movePacket.c <= 20))
      {
        this._goingUp.remove(Integer.valueOf(movePacket.a));
        this._movePacketMap.remove(Integer.valueOf(movePacket.a));
      }
      if (!containsSpawnDisguise(owner, movePacket.a)) {
        return;
      }
      final PacketPlayOutEntityVelocity velocityPacket = new PacketPlayOutEntityVelocity();
      velocityPacket.a = movePacket.a;
      velocityPacket.b = (movePacket.b * 100);
      velocityPacket.c = (movePacket.c * 100);
      velocityPacket.d = (movePacket.d * 100);
      if (this._movePacketMap.containsKey(Integer.valueOf(movePacket.a)))
      {
        PacketPlayOutEntityVelocity lastVelocityPacket = (PacketPlayOutEntityVelocity)this._movePacketMap.get(Integer.valueOf(movePacket.a));
        
        velocityPacket.b = ((int)(0.8D * lastVelocityPacket.b));
        velocityPacket.c = ((int)(0.8D * lastVelocityPacket.c));
        velocityPacket.d = ((int)(0.8D * lastVelocityPacket.d));
      }
      this._movePacketMap.put(Integer.valueOf(movePacket.a), velocityPacket);
      
      packetVerifier.bypassProcess(velocityPacket);
      if ((this._goingUp.contains(Integer.valueOf(movePacket.a))) && (movePacket.c != 0) && (movePacket.c > 20)) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
        {
          public void run()
          {
            packetVerifier.bypassProcess(velocityPacket);
          }
        });
      }
    }
  }
  
  private void handlePacket(Packet packet, PacketVerifier verifier)
  {
    this._handlingPacket = true;
    verifier.process(packet);
    this._handlingPacket = false;
  }
  
  private void handleSpawnPackets(PacketInfo packetInfo, DisguiseBase disguise)
  {
    Player player = packetInfo.getPlayer();
    if ((!UtilPlayer.is1_8(player)) && ((disguise instanceof DisguiseRabbit))) {
      return;
    }
    final PacketVerifier packetVerifier = packetInfo.getVerifier();
    Packet packet;
    if ((disguise instanceof DisguisePlayer))
    {
      final DisguisePlayer pDisguise = (DisguisePlayer)disguise;
      
      handlePacket(pDisguise.getNewInfoPacket(true), packetVerifier);
      
      PacketPlayOutNamedEntitySpawn namePacket = pDisguise.spawnBeforePlayer(player.getLocation());
      
      namePacket.i.watch(0, Byte.valueOf((byte)32));
      
      handlePacket(namePacket, packetVerifier);
      if (pDisguise.getSleepingDirection() != null)
      {
        Packet[] arrayOfPacket;
        int j = (arrayOfPacket = getBedPackets(player.getLocation(), pDisguise)).length;
        for (int i = 0; i < j; i++)
        {
          Packet packet = arrayOfPacket[i];
          
          handlePacket(packet, packetVerifier);
        }
      }
      else
      {
        handlePacket(new PacketPlayOutEntityTeleport(pDisguise.GetEntity()), packetVerifier);
      }
      for (Iterator localIterator = pDisguise.getEquipmentPackets().iterator(); localIterator.hasNext();)
      {
        packet = (Packet)localIterator.next();
        
        handlePacket(packet, packetVerifier);
      }
      handlePacket(pDisguise.GetMetaDataPacket(), packetVerifier);
      
      Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
      {
        public void run()
        {
          DisguiseManager.this.handlePacket(pDisguise.getNewInfoPacket(false), packetVerifier);
        }
      }, 6L);
    }
    else
    {
      handlePacket(disguise.GetSpawnPacket(), packetVerifier);
      if ((disguise instanceof DisguiseLiving))
      {
        ArrayList<Packet> packets = ((DisguiseLiving)disguise).getEquipmentPackets();
        for (Packet packet : packets) {
          handlePacket(packet, packetVerifier);
        }
      }
    }
  }
  
  public boolean isDisguised(LivingEntity entity)
  {
    return this._spawnPacketMap.containsKey(Integer.valueOf(entity.getEntityId()));
  }
  
  @EventHandler
  public void PlayerQuit(PlayerQuitEvent event)
  {
    undisguise(event.getPlayer());
    for (DisguiseBase disguise : this._disguisePlayerMap.keySet()) {
      ((HashSet)this._disguisePlayerMap.get(disguise)).remove(event.getPlayer());
    }
    for (Integer disguise : this._lastRabbitHop.keySet()) {
      ((NautHashMap)this._lastRabbitHop.get(disguise)).remove(Integer.valueOf(event.getPlayer().getEntityId()));
    }
  }
  
  private void prepareChunk(Location loc)
  {
    int chunkX = getChunk(loc.getX());
    int chunkZ = getChunk(loc.getZ());
    try
    {
      this._xChunk.set(this._bedChunk, Integer.valueOf(chunkX));
      this._zChunk.set(this._bedChunk, Integer.valueOf(chunkZ));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  private void refreshBedTrackers(final Player player)
  {
    for (final DisguiseBase disguise : this._disguisePlayerMap.keySet()) {
      if (((disguise instanceof DisguisePlayer)) && (((DisguisePlayer)disguise).getSleepingDirection() != null))
      {
        final EntityTrackerEntry entityTracker = getEntityTracker(disguise.GetEntity());
        if (entityTracker != null) {
          if (entityTracker.trackedPlayers.contains(((CraftPlayer)player).getHandle()))
          {
            Packet destroyPacket = new PacketPlayOutEntityDestroy(
              new int[] {
              disguise.GetEntityId() });
            
            entityTracker.clear(((CraftPlayer)player).getHandle());
            
            UtilPlayer.sendPacket(player, new Packet[] { destroyPacket });
            
            final World world = disguise.GetEntity().getBukkitEntity().getWorld();
            
            Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
            {
              public void run()
              {
                try
                {
                  org.bukkit.entity.Entity entity = disguise.GetEntity().getBukkitEntity();
                  if ((entity.getWorld() == world) && (entity.isValid())) {
                    if ((player.isOnline()) && (player.getWorld() == entity.getWorld())) {
                      entityTracker.updatePlayer(((CraftPlayer)player).getHandle());
                    }
                  }
                }
                catch (Exception ex)
                {
                  ex.printStackTrace();
                }
              }
            }, 5L);
          }
        }
      }
    }
  }
  
  private void refreshTrackers(final org.bukkit.entity.Entity entity, final Player[] players)
  {
    final EntityTrackerEntry entityTracker = getEntityTracker(((CraftEntity)entity).getHandle());
    if (entityTracker != null)
    {
      Packet destroyPacket = new PacketPlayOutEntityDestroy(
        new int[] {
        entity.getEntityId() });
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = players).length;
      for (int i = 0; i < j; i++)
      {
        Player player = arrayOfPlayer[i];
        
        entityTracker.clear(((CraftPlayer)player).getHandle());
        
        UtilPlayer.sendPacket(player, new Packet[] { destroyPacket });
      }
      final World world = entity.getWorld();
      
      Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
      {
        public void run()
        {
          try
          {
            if ((entity.getWorld() == world) && (entity.isValid()))
            {
              Player[] arrayOfPlayer;
              int j = (arrayOfPlayer = players).length;
              for (int i = 0; i < j; i++)
              {
                Player player = arrayOfPlayer[i];
                if ((player.isOnline()) && (player.getWorld() == entity.getWorld())) {
                  entityTracker.updatePlayer(((CraftPlayer)player).getHandle());
                }
              }
            }
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
        }
      }, 5L);
    }
  }
  
  public void removeViewerToDisguise(DisguiseBase disguise, Player player)
  {
    ((HashSet)this._disguisePlayerMap.get(disguise)).remove(player);
    
    refreshTrackers(disguise.GetEntity().getBukkitEntity(), 
      new Player[] {
      player });
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void switchedWorld(PlayerChangedWorldEvent event)
  {
    if (!this._bedPackets) {
      return;
    }
    chunkMove(event.getPlayer(), event.getPlayer().getLocation(), null);
  }
  
  @EventHandler
  public void TeleportDisguises(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SEC) {
      return;
    }
    TimingManager.startTotal("Teleport Disguises");
    Iterator localIterator2;
    for (Iterator localIterator1 = Bukkit.getOnlinePlayers().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      Player player = (Player)localIterator1.next();
      
      localIterator2 = Bukkit.getOnlinePlayers().iterator(); continue;Player otherPlayer = (Player)localIterator2.next();
      if (player != otherPlayer) {
        if (otherPlayer.getLocation().subtract(0.0D, 0.5D, 0.0D).getBlock().getTypeId() != 0) {
          ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(
            ((CraftPlayer)otherPlayer).getHandle()));
        }
      }
    }
    TimingManager.stopTotal("Teleport Disguises");
  }
  
  public void undisguise(LivingEntity entity)
  {
    if (!this._spawnPacketMap.containsKey(Integer.valueOf(entity.getEntityId()))) {
      return;
    }
    this._lastRabbitHop.remove(Integer.valueOf(entity.getEntityId()));
    DisguiseBase disguise = (DisguiseBase)this._spawnPacketMap.remove(Integer.valueOf(entity.getEntityId()));
    Collection<? extends Player> players = disguise.Global ? Bukkit.getOnlinePlayers() : (Collection)this._disguisePlayerMap.remove(disguise);
    
    this._movePacketMap.remove(Integer.valueOf(entity.getEntityId()));
    this._moveTempMap.remove(Integer.valueOf(entity.getEntityId()));
    this._blockedNames.remove(((Player)entity).getName());
    
    refreshTrackers(entity, (Player[])players.toArray(new Player[0]));
    if ((this._bedPackets) && ((disguise instanceof DisguisePlayer)) && (((DisguisePlayer)disguise).getSleepingDirection() != null))
    {
      for (DisguiseBase dis : this._disguisePlayerMap.keySet()) {
        if (((dis instanceof DisguisePlayer)) && (((DisguisePlayer)dis).getSleepingDirection() != null)) {
          return;
        }
      }
      unregisterBedChunk();
    }
  }
  
  private void unregisterBedChunk()
  {
    this._bedPackets = false;
    for (Player player : Bukkit.getOnlinePlayers()) {
      chunkMove(player, null, player.getLocation());
    }
  }
  
  public void updateDisguise(DisguiseBase disguise)
  {
    Collection<? extends Player> players = disguise.Global ? Bukkit.getOnlinePlayers() : (Collection)this._disguisePlayerMap.get(disguise);
    for (Player player : players) {
      if (disguise.GetEntity() != ((CraftPlayer)player).getHandle())
      {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        
        entityPlayer.playerConnection.sendPacket(disguise.GetMetaDataPacket());
      }
    }
  }
  
  @EventHandler
  public void cleanDisguises(UpdateEvent event)
  {
    if ((event.getType() != UpdateType.SLOWER) || (this._disguisePlayerMap.isEmpty())) {
      return;
    }
    for (Iterator<DisguiseBase> disguiseIterator = this._disguisePlayerMap.keySet().iterator(); disguiseIterator.hasNext();)
    {
      DisguiseBase disguise = (DisguiseBase)disguiseIterator.next();
      if ((disguise.GetEntity() instanceof EntityPlayer))
      {
        EntityPlayer disguisedPlayer = (EntityPlayer)disguise.GetEntity();
        if ((Bukkit.getPlayerExact(disguisedPlayer.getName()) == null) || (!disguisedPlayer.isAlive()) || (!disguisedPlayer.valid)) {
          disguiseIterator.remove();
        } else {
          for (Iterator<Player> playerIterator = ((HashSet)this._disguisePlayerMap.get(disguise)).iterator(); playerIterator.hasNext();)
          {
            Player player = (Player)playerIterator.next();
            if ((!player.isOnline()) || (!player.isValid())) {
              playerIterator.remove();
            }
          }
        }
      }
    }
  }
}

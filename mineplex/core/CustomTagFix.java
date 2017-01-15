package mineplex.core;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.checks.moving.MovingData;
import fr.neatmonster.nocheatplus.hooks.NCPExemptionManager;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import mineplex.core.common.DummyEntity;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.event.CustomTagEvent;
import mineplex.core.packethandler.IPacketHandler;
import mineplex.core.packethandler.PacketHandler;
import mineplex.core.packethandler.PacketInfo;
import mineplex.core.packethandler.PacketVerifier;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.EnumEntitySize;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_7_R4.WatchableObject;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class CustomTagFix
  extends MiniPlugin
  implements IPacketHandler, NCPHook
{
  private NautHashMap<String, NautHashMap<Integer, Integer>> _entityMap = new NautHashMap();
  private NautHashMap<String, NautHashMap<Integer, String>> _entityNameMap = new NautHashMap();
  private NautHashMap<String, NautHashMap<Integer, Integer>> _entityVehicleMap = new NautHashMap();
  private HashSet<String> _loggedIn = new HashSet();
  private HashSet<Integer> _ignoreSkulls = new HashSet();
  private NautHashMap<UUID, Long> _exemptTimeMap = new NautHashMap();
  private NautHashMap<UUID, NautHashMap<CheckType, Long>> _doubleStrike = new NautHashMap();
  private Field _destroyId;
  
  public CustomTagFix(JavaPlugin plugin, PacketHandler packetHandler)
  {
    super("Custom Tag Fix", plugin);
    packetHandler.addPacketHandler(this);
    try
    {
      this._destroyId = PacketPlayOutEntityDestroy.class.getDeclaredField("a");
      this._destroyId.setAccessible(true);
    }
    catch (Exception exception)
    {
      System.out.println("Field exception in CustomTagFix : ");
      exception.printStackTrace();
    }
    NCPHookManager.addHook(CheckType.MOVING_SURVIVALFLY, this);
    NCPHookManager.addHook(CheckType.MOVING_PASSABLE, this);
    NCPHookManager.addHook(CheckType.ALL, this);
  }
  
  @EventHandler
  public void playerQuit(PlayerQuitEvent event)
  {
    this._entityMap.remove(event.getPlayer().getName());
    this._entityNameMap.remove(event.getPlayer().getName());
    this._entityVehicleMap.remove(event.getPlayer().getName());
    this._loggedIn.remove(event.getPlayer());
  }
  
  @EventHandler
  public void ncpExempt(PlayerToggleFlightEvent event)
  {
    long ignoreTime = System.currentTimeMillis() + 1500L;
    if (this._exemptTimeMap.containsKey(event.getPlayer().getUniqueId()))
    {
      this._exemptTimeMap.put(event.getPlayer().getUniqueId(), Long.valueOf(Math.max(ignoreTime, ((Long)this._exemptTimeMap.get(event.getPlayer().getUniqueId())).longValue())));
      return;
    }
    try
    {
      NCPExemptionManager.exemptPermanently(event.getPlayer());
    }
    catch (Exception localException) {}
    this._exemptTimeMap.put(event.getPlayer().getUniqueId(), Long.valueOf(ignoreTime));
  }
  
  @EventHandler
  public void ncpExemptVelocity(PlayerVelocityEvent event)
  {
    long ignoreTime = System.currentTimeMillis() + (event.getVelocity().length() * 2000.0D);
    if (this._exemptTimeMap.containsKey(event.getPlayer().getUniqueId()))
    {
      this._exemptTimeMap.put(event.getPlayer().getUniqueId(), Long.valueOf(Math.max(ignoreTime, ((Long)this._exemptTimeMap.get(event.getPlayer().getUniqueId())).longValue())));
      return;
    }
    this._exemptTimeMap.put(event.getPlayer().getUniqueId(), Long.valueOf(ignoreTime));
  }
  
  @EventHandler
  public void unexempt(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Iterator<Map.Entry<UUID, Long>> iterator = this._exemptTimeMap.entrySet().iterator(); iterator.hasNext();)
    {
      Map.Entry<UUID, Long> entry = (Map.Entry)iterator.next();
      if (System.currentTimeMillis() > ((Long)entry.getValue()).longValue()) {
        iterator.remove();
      }
    }
    for (Iterator<Map.Entry<UUID, NautHashMap<CheckType, Long>>> iterator = this._doubleStrike.entrySet().iterator(); iterator.hasNext();)
    {
      Map.Entry<UUID, NautHashMap<CheckType, Long>> entry = (Map.Entry)iterator.next();
      for (Iterator<Map.Entry<CheckType, Long>> innerIterator = ((NautHashMap)entry.getValue()).entrySet().iterator(); innerIterator.hasNext();)
      {
        Map.Entry<CheckType, Long> entry2 = (Map.Entry)innerIterator.next();
        if (System.currentTimeMillis() > ((Long)entry2.getValue()).longValue()) {
          innerIterator.remove();
        }
      }
      if ((entry.getValue() == null) || (((NautHashMap)entry.getValue()).size() == 0)) {
        iterator.remove();
      }
    }
  }
  
  @EventHandler
  public void cleanMap(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SEC) {
      return;
    }
    Iterator<String> iterator = this._loggedIn.iterator();
    while (iterator.hasNext())
    {
      String player = (String)iterator.next();
      if (Bukkit.getPlayerExact(player) == null)
      {
        iterator.remove();
        this._entityMap.remove(player);
        this._entityNameMap.remove(player);
        this._entityVehicleMap.remove(player);
      }
    }
    if (Bukkit.getServer().getOnlinePlayers().size() < this._loggedIn.size()) {
      System.out.println("PROBLEM - _loggedIn TOOOOOO BIIIIIGGGGG.");
    }
  }
  
  public void handle(PacketInfo packetInfo)
  {
    if (packetInfo.isCancelled()) {
      return;
    }
    Packet packet = packetInfo.getPacket();
    Player owner = packetInfo.getPlayer();
    PacketVerifier verifier = packetInfo.getVerifier();
    if ((owner.isOnline()) && (UtilPlayer.is1_8(owner)))
    {
      if ((owner.isOnline()) && (!this._entityMap.containsKey(owner.getName())))
      {
        this._entityMap.put(owner.getName(), new NautHashMap());
        this._entityNameMap.put(owner.getName(), new NautHashMap());
        this._entityVehicleMap.put(owner.getName(), new NautHashMap());
        this._loggedIn.add(owner.getName());
      }
      int newId;
      if ((packet instanceof PacketPlayOutSpawnEntityLiving))
      {
        PacketPlayOutSpawnEntityLiving spawnPacket = (PacketPlayOutSpawnEntityLiving)packet;
        if ((spawnPacket.b == 30) || (spawnPacket.l == null) || (spawnPacket.l.c() == null) || (spawnPacket.a == 777777))
        {
          if (spawnPacket.b == 30) {
            this._ignoreSkulls.add(Integer.valueOf(spawnPacket.a));
          }
          return;
        }
        for (WatchableObject watchable : spawnPacket.l.c()) {
          if (((watchable.a() == 11) || (watchable.a() == 3)) && ((watchable.b() instanceof Byte)) && (((Byte)watchable.b()).byteValue() == 1))
          {
            String entityName = spawnPacket.l.getString(10);
            if (entityName.isEmpty())
            {
              ((NautHashMap)this._entityNameMap.get(owner.getName())).remove(Integer.valueOf(spawnPacket.a));
              return;
            }
            if (((NautHashMap)this._entityMap.get(owner.getName())).containsKey(Integer.valueOf(spawnPacket.a))) {
              verifier.bypassProcess(new PacketPlayOutEntityDestroy(new int[] { ((Integer)((NautHashMap)this._entityMap.get(owner.getName())).get(Integer.valueOf(spawnPacket.a))).intValue() }));
            }
            newId = UtilEnt.getNewEntityId();
            sendProtocolPackets(owner, spawnPacket.a, newId, entityName, verifier);
            ((NautHashMap)this._entityMap.get(owner.getName())).put(Integer.valueOf(spawnPacket.a), Integer.valueOf(newId));
            ((NautHashMap)this._entityNameMap.get(owner.getName())).put(Integer.valueOf(spawnPacket.a), entityName);
            
            break;
          }
        }
      }
      else
      {
        String entityName;
        boolean nameShowing;
        int newId;
        if ((packet instanceof PacketPlayOutEntityMetadata))
        {
          PacketPlayOutEntityMetadata metaPacket = (PacketPlayOutEntityMetadata)packet;
          if ((!((NautHashMap)this._entityMap.get(owner.getName())).containsKey(Integer.valueOf(metaPacket.a))) && (metaPacket.a != 777777) && (!this._ignoreSkulls.contains(Integer.valueOf(metaPacket.a))))
          {
            entityName = "";
            nameShowing = false;
            for (WatchableObject watchable : metaPacket.b)
            {
              if (((watchable.a() == 11) || (watchable.a() == 3)) && ((watchable.b() instanceof Byte)) && (((Byte)watchable.b()).byteValue() == 1)) {
                nameShowing = true;
              }
              if (((watchable.a() == 10) || (watchable.a() == 2)) && ((watchable.b() instanceof String))) {
                entityName = (String)watchable.b();
              }
            }
            if ((nameShowing) && (!entityName.isEmpty()))
            {
              newId = UtilEnt.getNewEntityId();
              sendProtocolPackets(owner, metaPacket.a, newId, entityName, verifier);
              ((NautHashMap)this._entityMap.get(owner.getName())).put(Integer.valueOf(metaPacket.a), Integer.valueOf(newId));
              ((NautHashMap)this._entityNameMap.get(owner.getName())).put(Integer.valueOf(metaPacket.a), entityName);
            }
            else if (!entityName.isEmpty())
            {
              ((NautHashMap)this._entityNameMap.get(owner.getName())).remove(Integer.valueOf(metaPacket.a));
            }
          }
        }
        else if ((packet instanceof PacketPlayOutEntityDestroy))
        {
          try
          {
            String str1 = (newId = (int[])this._destroyId.get(packet)).length;
            for (entityName = 0; entityName < str1; entityName++)
            {
              int id = newId[entityName];
              if (((NautHashMap)this._entityMap.get(owner.getName())).containsKey(Integer.valueOf(id)))
              {
                verifier.bypassProcess(new PacketPlayOutEntityDestroy(new int[] { ((Integer)((NautHashMap)this._entityMap.get(owner.getName())).get(Integer.valueOf(id))).intValue() }));
                ((NautHashMap)this._entityMap.get(owner.getName())).remove(Integer.valueOf(id));
                ((NautHashMap)this._entityVehicleMap.get(owner.getName())).remove(Integer.valueOf(id));
                ((NautHashMap)this._entityNameMap.get(owner.getName())).remove(Integer.valueOf(id));
              }
            }
          }
          catch (Exception exception)
          {
            exception.printStackTrace();
          }
        }
        else if ((packet instanceof PacketPlayOutSpawnEntity))
        {
          PacketPlayOutSpawnEntity spawnPacket = (PacketPlayOutSpawnEntity)packet;
          if ((spawnPacket.j == 66) && (spawnPacket.a != 777777)) {
            this._ignoreSkulls.add(Integer.valueOf(spawnPacket.a));
          }
        }
      }
    }
  }
  
  private void sendProtocolPackets(final Player owner, final int entityId, final int newEntityId, String entityName, final PacketVerifier packetList)
  {
    CustomTagEvent event = new CustomTagEvent(owner, entityId, entityName);
    this._plugin.getServer().getPluginManager().callEvent(event);
    final String finalEntityName = event.getCustomName();
    
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
    {
      public void run()
      {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving();
        packet.a = newEntityId;
        packet.b = 30;
        packet.c = EnumEntitySize.SIZE_2.a(100.0D);
        packet.d = MathHelper.floor(2048.0D);
        packet.e = EnumEntitySize.SIZE_2.a(100.0D);
        packet.i = 0;
        packet.j = 0;
        packet.k = 0;
        
        double var2 = 3.9D;
        double var4 = 0.0D;
        double var6 = 0.0D;
        double var8 = 0.0D;
        if (var4 < -var2) {
          var4 = -var2;
        }
        if (var6 < -var2) {
          var6 = -var2;
        }
        if (var8 < -var2) {
          var8 = -var2;
        }
        if (var4 > var2) {
          var4 = var2;
        }
        if (var6 > var2) {
          var6 = var2;
        }
        if (var8 > var2) {
          var8 = var2;
        }
        packet.f = ((int)(var4 * 8000.0D));
        packet.g = ((int)(var6 * 8000.0D));
        packet.h = ((int)(var8 * 8000.0D));
        
        DataWatcher watcher = new DataWatcher(new DummyEntity(((CraftWorld)owner.getWorld()).getHandle()));
        
        watcher.a(0, Byte.valueOf((byte)0));
        watcher.a(1, Short.valueOf((short)300));
        watcher.a(2, "");
        watcher.a(3, Byte.valueOf((byte)0));
        watcher.a(4, Byte.valueOf((byte)0));
        watcher.a(7, Integer.valueOf(0));
        watcher.a(8, Byte.valueOf((byte)0));
        watcher.a(9, Byte.valueOf((byte)0));
        watcher.a(6, Float.valueOf(1.0F));
        watcher.a(10, Byte.valueOf((byte)0));
        
        int i1 = watcher.getInt(0);
        watcher.watch(0, Byte.valueOf((byte)(i1 | 0x20)));
        
        byte b1 = watcher.getByte(10);
        b1 = (byte)(b1 | 0x1);
        
        watcher.watch(10, Byte.valueOf(b1));
        
        watcher.watch(2, finalEntityName);
        watcher.watch(3, Byte.valueOf((byte)1));
        
        packet.l = watcher;
        
        packetList.bypassProcess(packet);
        
        PacketPlayOutAttachEntity vehiclePacket = new PacketPlayOutAttachEntity();
        vehiclePacket.a = 0;
        vehiclePacket.b = packet.a;
        vehiclePacket.c = entityId;
        
        packetList.bypassProcess(vehiclePacket);
      }
    });
  }
  
  public String getHookName()
  {
    return "Mineplex Hook";
  }
  
  public String getHookVersion()
  {
    return "Latest";
  }
  
  public boolean onCheckFailure(CheckType checkType, Player player, IViolationInfo violationInfo)
  {
    boolean failure = false;
    if (((checkType == CheckType.MOVING_SURVIVALFLY) || (checkType == CheckType.MOVING_PASSABLE)) && ((failure = this._exemptTimeMap.containsKey(player.getUniqueId())))) {
      MovingData.getData(player).clearFlyData();
    }
    if (!failure)
    {
      if ((!this._doubleStrike.containsKey(player.getUniqueId())) || (!((NautHashMap)this._doubleStrike.get(player.getUniqueId())).containsKey(checkType.getParent()))) {
        failure = true;
      }
      if (!this._doubleStrike.containsKey(player.getUniqueId())) {
        this._doubleStrike.put(player.getUniqueId(), new NautHashMap());
      }
      ((NautHashMap)this._doubleStrike.get(player.getUniqueId())).put(checkType.getParent(), Long.valueOf(System.currentTimeMillis() + 5000L));
    }
    return failure;
  }
}

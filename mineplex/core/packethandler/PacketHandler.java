package mineplex.core.packethandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import mineplex.core.MiniPlugin;
import mineplex.core.common.util.NautHashMap;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.IPacketVerifier;
import net.minecraft.server.v1_7_R4.PacketProcessor;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.util.com.google.common.collect.BiMap;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.ProtocolInjector;

public class PacketHandler
  extends MiniPlugin
{
  private NautHashMap<Player, PacketVerifier> _playerVerifierMap = new NautHashMap();
  private HashSet<IPacketHandler> _packetHandlers = new HashSet();
  
  public PacketHandler(JavaPlugin plugin)
  {
    super("PacketHandler", plugin);
    try
    {
      Class[] arrayOfClass;
      int j = (arrayOfClass = new Class[] { PacketPlayResourcePackStatus.class, PacketPlayUseEntity.class }).length;
      for (int i = 0; i < j; i++)
      {
        Class clss = arrayOfClass[i];
        
        Field field = clss.getDeclaredField("_packetHandler");
        
        field.setAccessible(true);
        field.set(null, this);
      }
      EnumProtocol.PLAY.a().put(Integer.valueOf(25), PacketPlayResourcePackStatus.class);
      EnumProtocol.PLAY.a().put(PacketPlayResourcePackStatus.class, Integer.valueOf(25));
      
      EnumProtocol.PLAY.a().put(Integer.valueOf(2), PacketPlayUseEntity.class);
      EnumProtocol.PLAY.a().put(PacketPlayUseEntity.class, Integer.valueOf(2));
      
      Method method = ProtocolInjector.class.getDeclaredMethod("addPacket", new Class[] { EnumProtocol.class, Boolean.TYPE, Integer.TYPE, Class.class });
      method.setAccessible(true);
      
      method.invoke(null, new Object[] { EnumProtocol.PLAY, Boolean.valueOf(true), Integer.valueOf(68), PacketPlayOutWorldBorder.class });
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    this._playerVerifierMap.put(event.getPlayer(), new PacketVerifier(event.getPlayer()));
    ((CraftPlayer)event.getPlayer()).getHandle().playerConnection.PacketVerifier.addPacketVerifier(
      (IPacketVerifier)this._playerVerifierMap.get(event.getPlayer()));
    for (IPacketHandler packetHandler : this._packetHandlers) {
      ((PacketVerifier)this._playerVerifierMap.get(event.getPlayer())).addPacketHandler(packetHandler);
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    ((CraftPlayer)event.getPlayer()).getHandle().playerConnection.PacketVerifier.clearVerifiers();
    ((PacketVerifier)this._playerVerifierMap.remove(event.getPlayer())).clearHandlers();
  }
  
  public PacketVerifier getPacketVerifier(Player player)
  {
    return (PacketVerifier)this._playerVerifierMap.get(player);
  }
  
  public void addPacketHandler(IPacketHandler packetHandler)
  {
    this._packetHandlers.add(packetHandler);
    for (PacketVerifier verifier : this._playerVerifierMap.values()) {
      verifier.addPacketHandler(packetHandler);
    }
  }
  
  public HashSet<IPacketHandler> getPacketHandlers()
  {
    return this._packetHandlers;
  }
  
  public void removePacketHandler(IPacketHandler packetHandler)
  {
    this._packetHandlers.remove(packetHandler);
    for (PacketVerifier verifier : this._playerVerifierMap.values()) {
      verifier.removePacketHandler(packetHandler);
    }
  }
}

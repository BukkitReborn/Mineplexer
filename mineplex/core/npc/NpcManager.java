package mineplex.core.npc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.sql.DataSource;
import mineplex.core.MiniPlugin;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.creature.event.CreatureKillEntitiesEvent;
import mineplex.core.database.DBPool;
import mineplex.core.npc.command.NpcCommand;
import mineplex.core.npc.event.NpcDamageByEntityEvent;
import mineplex.core.npc.event.NpcInteractEntityEvent;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import mineplex.database.tables.records.NpcsRecord;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class NpcManager
  extends MiniPlugin
{
  private final mineplex.core.creature.Creature _creature;
  
  private static String itemStackToYaml(ItemStack stack)
  {
    if ((stack == null) || (stack.getType() == Material.AIR)) {
      return null;
    }
    YamlConfiguration configuration = new YamlConfiguration();
    configuration.set("stack", stack);
    return configuration.saveToString();
  }
  
  private static ItemStack yamlToItemStack(String yaml)
  {
    if (yaml == null) {
      return null;
    }
    try
    {
      YamlConfiguration configuration = new YamlConfiguration();
      configuration.loadFromString(yaml);
      return configuration.getItemStack("stack");
    }
    catch (InvalidConfigurationException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private final List<Npc> _npcs = new ArrayList();
  final Map<UUID, Npc> _npcMap = new HashMap();
  private final Set<UUID> _npcDeletingPlayers = new HashSet();
  
  public NpcManager(JavaPlugin plugin, mineplex.core.creature.Creature creature)
  {
    super("NpcManager", plugin);
    
    this._creature = creature;
    
    this._plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(this._plugin, new Runnable()
    {
      public void run()
      {
        NpcManager.this.updateNpcLocations();
      }
    }, 0L, 5L);
    
    this._plugin.getServer().getPluginManager().registerEvents(this, this._plugin);
    try
    {
      loadNpcs();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void addCommands()
  {
    addCommand(new NpcCommand(this));
  }
  
  public void help(Player caller, String message)
  {
    UtilPlayer.message(caller, F.main(this._moduleName, "Commands List:"));
    UtilPlayer.message(caller, F.help("/npc add <type> [radius] [adult] [name]", "Create a new NPC.", Rank.DEVELOPER));
    UtilPlayer.message(caller, F.help("/npc del ", "Right click NPC to delete.", Rank.DEVELOPER));
    UtilPlayer.message(caller, F.help("/npc home", "Teleport NPCs to home locations.", Rank.DEVELOPER));
    UtilPlayer.message(caller, F.help("/npc clear", "Deletes all NPCs.", Rank.DEVELOPER));
    UtilPlayer.message(caller, F.help("/npc refresh", "Refresh NPCs from database.", Rank.DEVELOPER));
    if (message != null) {
      UtilPlayer.message(caller, F.main(this._moduleName, ChatColor.RED + message));
    }
  }
  
  public void help(Player caller)
  {
    help(caller, null);
  }
  
  private Npc getNpcByEntityUUID(UUID uuid)
  {
    if (uuid == null) {
      return null;
    }
    return (Npc)this._npcMap.get(uuid);
  }
  
  public Npc getNpcByEntity(Entity entity)
  {
    if (entity == null) {
      return null;
    }
    return getNpcByEntityUUID(entity.getUniqueId());
  }
  
  public boolean isNpc(Entity entity)
  {
    return getNpcByEntity(entity) != null;
  }
  
  public boolean isDetachedNpc(LivingEntity entity)
  {
    return (!isNpc(entity)) && (entity.getCustomName() != null) && (entity.getCustomName().startsWith(ChatColor.RESET.toString()));
  }
  
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
  public void onEntityDamage(EntityDamageEvent event)
  {
    if (((event.getEntity() instanceof LivingEntity)) && (isNpc(event.getEntity()))) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onEntityDamage(EntityDamageByEntityEvent event)
  {
    if (isNpc(event.getEntity()))
    {
      if (((event.getEntity() instanceof LivingEntity)) && ((event.getDamager() instanceof LivingEntity)))
      {
        NpcDamageByEntityEvent npcEvent = new NpcDamageByEntityEvent((LivingEntity)event.getEntity(), (LivingEntity)event.getDamager());
        
        Bukkit.getServer().getPluginManager().callEvent(npcEvent);
      }
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
  public void onCreatureKillEntities(CreatureKillEntitiesEvent event)
  {
    for (Iterator<Entity> entityIterator = event.GetEntities().iterator(); entityIterator.hasNext();) {
      if (isNpc((Entity)entityIterator.next())) {
        entityIterator.remove();
      }
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
  public void onEntityTarget(EntityTargetEvent event)
  {
    if (isNpc(event.getEntity())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
  public void onEntityCombust(EntityCombustEvent event)
  {
    if (isNpc(event.getEntity())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
  {
    if ((event.getRightClicked() instanceof LivingEntity)) {
      if (this._npcDeletingPlayers.remove(event.getPlayer().getUniqueId()))
      {
        try
        {
          if (deleteNpc(event.getRightClicked())) {
            event.getPlayer().sendMessage(F.main(getName(), "Deleted npc."));
          } else {
            event.getPlayer().sendMessage(F.main(getName(), "Failed to delete npc.  That one isn't in the list."));
          }
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      else if (isNpc(event.getRightClicked()))
      {
        NpcInteractEntityEvent npcEvent = new NpcInteractEntityEvent((LivingEntity)event.getRightClicked(), event.getPlayer());
        
        Bukkit.getServer().getPluginManager().callEvent(npcEvent);
        
        event.setCancelled(true);
      }
    }
  }
  
  /* Error */
  public Entity addNpc(Player player, EntityType entityType, double radius, boolean adult, String name, String entityMeta)
    throws SQLException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 8
    //   3: aconst_null
    //   4: astore 9
    //   6: getstatic 408	mineplex/core/database/DBPool:ACCOUNT	Ljavax/sql/DataSource;
    //   9: invokeinterface 414 1 0
    //   14: astore 10
    //   16: aload_1
    //   17: invokeinterface 420 1 0
    //   22: invokeinterface 424 1 0
    //   27: invokestatic 430	mineplex/core/npc/NpcManager:itemStackToYaml	(Lorg/bukkit/inventory/ItemStack;)Ljava/lang/String;
    //   30: astore 11
    //   32: aload_1
    //   33: invokeinterface 420 1 0
    //   38: invokeinterface 432 1 0
    //   43: invokestatic 430	mineplex/core/npc/NpcManager:itemStackToYaml	(Lorg/bukkit/inventory/ItemStack;)Ljava/lang/String;
    //   46: astore 12
    //   48: aload_1
    //   49: invokeinterface 420 1 0
    //   54: invokeinterface 435 1 0
    //   59: invokestatic 430	mineplex/core/npc/NpcManager:itemStackToYaml	(Lorg/bukkit/inventory/ItemStack;)Ljava/lang/String;
    //   62: astore 13
    //   64: aload_1
    //   65: invokeinterface 420 1 0
    //   70: invokeinterface 438 1 0
    //   75: invokestatic 430	mineplex/core/npc/NpcManager:itemStackToYaml	(Lorg/bukkit/inventory/ItemStack;)Ljava/lang/String;
    //   78: astore 14
    //   80: aload_1
    //   81: invokeinterface 420 1 0
    //   86: invokeinterface 441 1 0
    //   91: invokestatic 430	mineplex/core/npc/NpcManager:itemStackToYaml	(Lorg/bukkit/inventory/ItemStack;)Ljava/lang/String;
    //   94: astore 15
    //   96: aload 10
    //   98: invokestatic 444	org/jooq/impl/DSL:using	(Ljava/sql/Connection;)Lorg/jooq/DSLContext;
    //   101: getstatic 450	mineplex/database/Tables:npcs	Lmineplex/database/tables/Npcs;
    //   104: invokeinterface 456 2 0
    //   109: checkcast 462	mineplex/database/tables/records/NpcsRecord
    //   112: astore 16
    //   114: aload 16
    //   116: aload_0
    //   117: invokevirtual 464	mineplex/core/npc/NpcManager:getServerName	()Ljava/lang/String;
    //   120: invokevirtual 467	mineplex/database/tables/records/NpcsRecord:setServer	(Ljava/lang/String;)V
    //   123: aload 16
    //   125: aload 6
    //   127: invokevirtual 470	mineplex/database/tables/records/NpcsRecord:setName	(Ljava/lang/String;)V
    //   130: aload 16
    //   132: aload_1
    //   133: invokeinterface 473 1 0
    //   138: invokeinterface 477 1 0
    //   143: invokevirtual 480	mineplex/database/tables/records/NpcsRecord:setWorld	(Ljava/lang/String;)V
    //   146: aload 16
    //   148: aload_1
    //   149: invokeinterface 483 1 0
    //   154: invokevirtual 487	org/bukkit/Location:getX	()D
    //   157: invokestatic 493	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   160: invokevirtual 499	mineplex/database/tables/records/NpcsRecord:setX	(Ljava/lang/Double;)V
    //   163: aload 16
    //   165: aload_1
    //   166: invokeinterface 483 1 0
    //   171: invokevirtual 503	org/bukkit/Location:getY	()D
    //   174: invokestatic 493	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   177: invokevirtual 506	mineplex/database/tables/records/NpcsRecord:setY	(Ljava/lang/Double;)V
    //   180: aload 16
    //   182: aload_1
    //   183: invokeinterface 483 1 0
    //   188: invokevirtual 509	org/bukkit/Location:getZ	()D
    //   191: invokestatic 493	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   194: invokevirtual 512	mineplex/database/tables/records/NpcsRecord:setZ	(Ljava/lang/Double;)V
    //   197: aload 16
    //   199: dload_3
    //   200: invokestatic 493	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   203: invokevirtual 515	mineplex/database/tables/records/NpcsRecord:setRadius	(Ljava/lang/Double;)V
    //   206: aload 16
    //   208: aload_2
    //   209: invokevirtual 518	org/bukkit/entity/EntityType:name	()Ljava/lang/String;
    //   212: invokevirtual 523	mineplex/database/tables/records/NpcsRecord:setEntityType	(Ljava/lang/String;)V
    //   215: aload 16
    //   217: iload 5
    //   219: invokestatic 526	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   222: invokevirtual 531	mineplex/database/tables/records/NpcsRecord:setAdult	(Ljava/lang/Boolean;)V
    //   225: aload 16
    //   227: aload 11
    //   229: invokevirtual 535	mineplex/database/tables/records/NpcsRecord:setHelmet	(Ljava/lang/String;)V
    //   232: aload 16
    //   234: aload 12
    //   236: invokevirtual 538	mineplex/database/tables/records/NpcsRecord:setChestplate	(Ljava/lang/String;)V
    //   239: aload 16
    //   241: aload 13
    //   243: invokevirtual 541	mineplex/database/tables/records/NpcsRecord:setLeggings	(Ljava/lang/String;)V
    //   246: aload 16
    //   248: aload 14
    //   250: invokevirtual 544	mineplex/database/tables/records/NpcsRecord:setBoots	(Ljava/lang/String;)V
    //   253: aload 16
    //   255: aload 15
    //   257: invokevirtual 547	mineplex/database/tables/records/NpcsRecord:setInHand	(Ljava/lang/String;)V
    //   260: aload 16
    //   262: aload 7
    //   264: invokevirtual 550	mineplex/database/tables/records/NpcsRecord:setEntityMeta	(Ljava/lang/String;)V
    //   267: aload 16
    //   269: invokevirtual 553	mineplex/database/tables/records/NpcsRecord:insert	()I
    //   272: pop
    //   273: goto +13 -> 286
    //   276: astore 17
    //   278: aload 16
    //   280: invokevirtual 557	mineplex/database/tables/records/NpcsRecord:detach	()V
    //   283: aload 17
    //   285: athrow
    //   286: aload 16
    //   288: invokevirtual 557	mineplex/database/tables/records/NpcsRecord:detach	()V
    //   291: new 235	mineplex/core/npc/Npc
    //   294: dup
    //   295: aload_0
    //   296: aload 16
    //   298: invokespecial 560	mineplex/core/npc/Npc:<init>	(Lmineplex/core/npc/NpcManager;Lmineplex/database/tables/records/NpcsRecord;)V
    //   301: astore 17
    //   303: aload_0
    //   304: getfield 81	mineplex/core/npc/NpcManager:_npcs	Ljava/util/List;
    //   307: aload 17
    //   309: invokeinterface 563 2 0
    //   314: pop
    //   315: aload_0
    //   316: aload 17
    //   318: invokevirtual 566	mineplex/core/npc/NpcManager:spawnNpc	(Lmineplex/core/npc/Npc;)Lorg/bukkit/entity/Entity;
    //   321: aload 10
    //   323: ifnull +10 -> 333
    //   326: aload 10
    //   328: invokeinterface 570 1 0
    //   333: areturn
    //   334: astore 8
    //   336: aload 10
    //   338: ifnull +10 -> 348
    //   341: aload 10
    //   343: invokeinterface 570 1 0
    //   348: aload 8
    //   350: athrow
    //   351: astore 9
    //   353: aload 8
    //   355: ifnonnull +10 -> 365
    //   358: aload 9
    //   360: astore 8
    //   362: goto +17 -> 379
    //   365: aload 8
    //   367: aload 9
    //   369: if_acmpeq +10 -> 379
    //   372: aload 8
    //   374: aload 9
    //   376: invokevirtual 575	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   379: aload 8
    //   381: athrow
    // Line number table:
    //   Java source line #261	-> byte code offset #0
    //   Java source line #263	-> byte code offset #16
    //   Java source line #264	-> byte code offset #32
    //   Java source line #265	-> byte code offset #48
    //   Java source line #266	-> byte code offset #64
    //   Java source line #267	-> byte code offset #80
    //   Java source line #269	-> byte code offset #96
    //   Java source line #270	-> byte code offset #114
    //   Java source line #271	-> byte code offset #123
    //   Java source line #272	-> byte code offset #130
    //   Java source line #273	-> byte code offset #146
    //   Java source line #274	-> byte code offset #163
    //   Java source line #275	-> byte code offset #180
    //   Java source line #276	-> byte code offset #197
    //   Java source line #277	-> byte code offset #206
    //   Java source line #278	-> byte code offset #215
    //   Java source line #279	-> byte code offset #225
    //   Java source line #280	-> byte code offset #232
    //   Java source line #281	-> byte code offset #239
    //   Java source line #282	-> byte code offset #246
    //   Java source line #283	-> byte code offset #253
    //   Java source line #284	-> byte code offset #260
    //   Java source line #288	-> byte code offset #267
    //   Java source line #289	-> byte code offset #273
    //   Java source line #291	-> byte code offset #276
    //   Java source line #292	-> byte code offset #278
    //   Java source line #293	-> byte code offset #283
    //   Java source line #292	-> byte code offset #286
    //   Java source line #295	-> byte code offset #291
    //   Java source line #296	-> byte code offset #303
    //   Java source line #298	-> byte code offset #315
    //   Java source line #299	-> byte code offset #321
    //   Java source line #298	-> byte code offset #333
    //   Java source line #299	-> byte code offset #336
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	382	0	this	NpcManager
    //   0	382	1	player	Player
    //   0	382	2	entityType	EntityType
    //   0	382	3	radius	double
    //   0	382	5	adult	boolean
    //   0	382	6	name	String
    //   0	382	7	entityMeta	String
    //   1	1	8	localObject1	Object
    //   334	20	8	localObject2	Object
    //   360	20	8	localObject3	Object
    //   4	1	9	localObject4	Object
    //   351	24	9	localThrowable	Throwable
    //   14	328	10	connection	Connection
    //   30	198	11	helmet	String
    //   46	189	12	chestplate	String
    //   62	180	13	leggings	String
    //   78	171	14	boots	String
    //   94	162	15	inHand	String
    //   112	185	16	npcsRecord	NpcsRecord
    //   276	8	17	localObject5	Object
    //   301	16	17	npc	Npc
    // Exception table:
    //   from	to	target	type
    //   267	276	276	finally
    //   16	321	334	finally
    //   333	334	334	finally
    //   6	351	351	finally
  }
  
  public Entity spawnNpc(Npc npc)
  {
    LivingEntity entity = (LivingEntity)this._creature.SpawnEntity(npc.getLocation(), EntityType.valueOf(npc.getDatabaseRecord().getEntityType()));
    if (npc.getDatabaseRecord().getName() == null)
    {
      entity.setCustomNameVisible(false);
      entity.setCustomName(ChatColor.RESET.toString());
    }
    else
    {
      String name = npc.getDatabaseRecord().getName();
      ChatColor[] arrayOfChatColor;
      int j = (arrayOfChatColor = ChatColor.values()).length;
      for (int i = 0; i < j; i++)
      {
        ChatColor color = arrayOfChatColor[i];
        name = name.replace("(" + color.name().toLowerCase() + ")", color.toString());
      }
      name = ChatColor.translateAlternateColorCodes('&', name);
      
      entity.setCustomNameVisible(true);
      entity.setCustomName(ChatColor.RESET + name);
    }
    entity.setCanPickupItems(false);
    entity.setRemoveWhenFarAway(false);
    ((EntityInsentient)((CraftLivingEntity)entity).getHandle()).persistent = true;
    if ((entity instanceof Ageable))
    {
      if (npc.getDatabaseRecord().getAdult().booleanValue()) {
        ((Ageable)entity).setAdult();
      } else {
        ((Ageable)entity).setBaby();
      }
      ((Ageable)entity).setAgeLock(true);
    }
    if ((entity instanceof Zombie)) {
      ((Zombie)entity).setBaby(!npc.getDatabaseRecord().getAdult().booleanValue());
    }
    if (((entity instanceof Slime)) && (npc.getDatabaseRecord().getEntityMeta() != null)) {
      ((Slime)entity).setSize(Integer.parseInt(npc.getDatabaseRecord().getEntityMeta()));
    }
    if (((entity instanceof Skeleton)) && (npc.getDatabaseRecord().getEntityMeta() != null)) {
      ((Skeleton)entity).setSkeletonType(Skeleton.SkeletonType.valueOf(npc.getDatabaseRecord().getEntityMeta().toUpperCase()));
    }
    if (((entity instanceof Villager)) && (npc.getDatabaseRecord().getEntityMeta() != null)) {
      ((Villager)entity).setProfession(Villager.Profession.valueOf(npc.getDatabaseRecord().getEntityMeta().toUpperCase()));
    }
    if ((entity instanceof org.bukkit.entity.Creature)) {
      ((org.bukkit.entity.Creature)entity).setTarget(null);
    }
    if (npc.getDatabaseRecord().getRadius().doubleValue() == 0.0D)
    {
      UtilEnt.Vegetate(entity);
      UtilEnt.silence(entity, true);
      UtilEnt.ghost(entity, true, false);
      
      UtilEnt.addLookAtPlayerAI(entity, 10.0F);
    }
    if (npc.getDatabaseRecord().getHelmet() != null) {
      entity.getEquipment().setHelmet(yamlToItemStack(npc.getDatabaseRecord().getHelmet()));
    }
    if (npc.getDatabaseRecord().getChestplate() != null) {
      entity.getEquipment().setChestplate(yamlToItemStack(npc.getDatabaseRecord().getChestplate()));
    }
    if (npc.getDatabaseRecord().getLeggings() != null) {
      entity.getEquipment().setLeggings(yamlToItemStack(npc.getDatabaseRecord().getLeggings()));
    }
    if (npc.getDatabaseRecord().getBoots() != null) {
      entity.getEquipment().setBoots(yamlToItemStack(npc.getDatabaseRecord().getBoots()));
    }
    if (npc.getDatabaseRecord().getInHand() != null) {
      entity.getEquipment().setItemInHand(yamlToItemStack(npc.getDatabaseRecord().getInHand()));
    }
    npc.setEntity(entity);
    
    return entity;
  }
  
  public boolean deleteNpc(Entity entity)
    throws SQLException
  {
    Npc npc = getNpcByEntity(entity);
    if (npc != null) {
      try
      {
        Object localObject1 = null;Object localObject4 = null;
        Object localObject3;
        try
        {
          Connection connection = DBPool.ACCOUNT.getConnection();
          try
          {
            npc.getDatabaseRecord().attach(DSL.using(connection).configuration());
            npc.getDatabaseRecord().delete();
            
            entity.remove();
            this._npcMap.remove(entity.getUniqueId());
            this._npcs.remove(npc);
            if (connection != null) {
              connection.close();
            }
            return true;
          }
          finally
          {
            if (connection != null) {
              connection.close();
            }
          }
        }
        finally
        {
          if (localObject2 == null) {
            localObject3 = localThrowable;
          } else if (localObject3 != localThrowable) {
            ((Throwable)localObject3).addSuppressed(localThrowable);
          }
        }
        return false;
      }
      finally
      {
        npc.getDatabaseRecord().detach();
      }
    }
  }
  
  public void prepDeleteNpc(Player admin)
  {
    this._npcDeletingPlayers.add(admin.getUniqueId());
  }
  
  private void updateNpcLocations()
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = Bukkit.getWorlds().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      World world = (World)localIterator1.next();
      
      localIterator2 = world.getEntitiesByClass(LivingEntity.class).iterator(); continue;LivingEntity entity = (LivingEntity)localIterator2.next();
      
      Npc npc = getNpcByEntity(entity);
      if (npc != null)
      {
        entity.setTicksLived(1);
        ((EntityInsentient)((CraftLivingEntity)entity).getHandle()).persistent = true;
        UtilEnt.silence(entity, true);
        if (entity.getLocation().getChunk().isLoaded()) {
          if ((!entity.isDead()) && (entity.isValid()))
          {
            ItemStack[] arrayOfItemStack;
            int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
            for (int i = 0; i < j; i++)
            {
              ItemStack armor = arrayOfItemStack[i];
              if ((armor != null) && (armor.getType() != Material.AIR)) {
                armor.setDurability((short)0);
              }
            }
            if ((npc.getFailedAttempts() >= 10) || (npc.getDatabaseRecord().getRadius().doubleValue() == 0.0D))
            {
              Location location = npc.getLocation();
              location.setPitch(entity.getLocation().getPitch());
              location.setYaw(entity.getLocation().getYaw());
              entity.teleport(location);
              entity.setVelocity(new Vector(0, 0, 0));
              npc.setFailedAttempts(0);
            }
            else if ((!npc.isInRadius(entity.getLocation())) && ((npc.getEntity() instanceof CraftCreature)))
            {
              npc.returnToPost();
              npc.incrementFailedAttempts();
            }
            else if ((npc.getEntity() instanceof CraftCreature))
            {
              if (npc.isReturning()) {
                npc.clearGoals();
              }
              npc.setFailedAttempts(0);
            }
          }
        }
      }
    }
  }
  
  public void teleportNpcsHome()
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = Bukkit.getWorlds().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      World world = (World)localIterator1.next();
      
      localIterator2 = world.getEntitiesByClass(LivingEntity.class).iterator(); continue;LivingEntity entity = (LivingEntity)localIterator2.next();
      
      Npc npc = getNpcByEntity(entity);
      if (npc != null) {
        if (entity.getLocation().getChunk().isLoaded()) {
          if ((!entity.isDead()) && (entity.isValid()))
          {
            Location location = npc.getLocation();
            location.setPitch(entity.getLocation().getPitch());
            location.setYaw(entity.getLocation().getYaw());
            entity.teleport(location);
            entity.setVelocity(new Vector(0, 0, 0));
            
            npc.setFailedAttempts(0);
          }
        }
      }
    }
  }
  
  /* Error */
  public void loadNpcs()
    throws SQLException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 464	mineplex/core/npc/NpcManager:getServerName	()Ljava/lang/String;
    //   4: astore_1
    //   5: aconst_null
    //   6: astore_2
    //   7: aconst_null
    //   8: astore_3
    //   9: getstatic 408	mineplex/core/database/DBPool:ACCOUNT	Ljavax/sql/DataSource;
    //   12: invokeinterface 414 1 0
    //   17: astore 4
    //   19: aload 4
    //   21: invokestatic 444	org/jooq/impl/DSL:using	(Ljava/sql/Connection;)Lorg/jooq/DSLContext;
    //   24: getstatic 450	mineplex/database/Tables:npcs	Lmineplex/database/tables/Npcs;
    //   27: invokeinterface 907 2 0
    //   32: iconst_1
    //   33: anewarray 911	org/jooq/Condition
    //   36: dup
    //   37: iconst_0
    //   38: getstatic 450	mineplex/database/Tables:npcs	Lmineplex/database/tables/Npcs;
    //   41: getfield 913	mineplex/database/tables/Npcs:server	Lorg/jooq/TableField;
    //   44: aload_1
    //   45: invokeinterface 919 2 0
    //   50: aastore
    //   51: invokeinterface 925 2 0
    //   56: invokeinterface 931 1 0
    //   61: astore 5
    //   63: aload 5
    //   65: invokeinterface 937 1 0
    //   70: astore 7
    //   72: goto +64 -> 136
    //   75: aload 7
    //   77: invokeinterface 333 1 0
    //   82: checkcast 462	mineplex/database/tables/records/NpcsRecord
    //   85: astore 6
    //   87: aload 6
    //   89: invokevirtual 557	mineplex/database/tables/records/NpcsRecord:detach	()V
    //   92: new 235	mineplex/core/npc/Npc
    //   95: dup
    //   96: aload_0
    //   97: aload 6
    //   99: invokespecial 560	mineplex/core/npc/Npc:<init>	(Lmineplex/core/npc/NpcManager;Lmineplex/database/tables/records/NpcsRecord;)V
    //   102: astore 8
    //   104: aload_0
    //   105: getfield 81	mineplex/core/npc/NpcManager:_npcs	Ljava/util/List;
    //   108: aload 8
    //   110: invokeinterface 563 2 0
    //   115: pop
    //   116: aload 8
    //   118: invokevirtual 940	mineplex/core/npc/Npc:getChunk	()Lorg/bukkit/Chunk;
    //   121: invokeinterface 827 1 0
    //   126: ifeq +10 -> 136
    //   129: aload_0
    //   130: aload 8
    //   132: invokevirtual 566	mineplex/core/npc/NpcManager:spawnNpc	(Lmineplex/core/npc/Npc;)Lorg/bukkit/entity/Entity;
    //   135: pop
    //   136: aload 7
    //   138: invokeinterface 342 1 0
    //   143: ifne -68 -> 75
    //   146: aload 4
    //   148: ifnull +50 -> 198
    //   151: aload 4
    //   153: invokeinterface 570 1 0
    //   158: goto +40 -> 198
    //   161: astore_2
    //   162: aload 4
    //   164: ifnull +10 -> 174
    //   167: aload 4
    //   169: invokeinterface 570 1 0
    //   174: aload_2
    //   175: athrow
    //   176: astore_3
    //   177: aload_2
    //   178: ifnonnull +8 -> 186
    //   181: aload_3
    //   182: astore_2
    //   183: goto +13 -> 196
    //   186: aload_2
    //   187: aload_3
    //   188: if_acmpeq +8 -> 196
    //   191: aload_2
    //   192: aload_3
    //   193: invokevirtual 575	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   196: aload_2
    //   197: athrow
    //   198: return
    // Line number table:
    //   Java source line #484	-> byte code offset #0
    //   Java source line #486	-> byte code offset #5
    //   Java source line #486	-> byte code offset #9
    //   Java source line #488	-> byte code offset #19
    //   Java source line #489	-> byte code offset #24
    //   Java source line #490	-> byte code offset #38
    //   Java source line #491	-> byte code offset #56
    //   Java source line #488	-> byte code offset #61
    //   Java source line #493	-> byte code offset #63
    //   Java source line #495	-> byte code offset #87
    //   Java source line #497	-> byte code offset #92
    //   Java source line #498	-> byte code offset #104
    //   Java source line #500	-> byte code offset #116
    //   Java source line #501	-> byte code offset #129
    //   Java source line #493	-> byte code offset #136
    //   Java source line #503	-> byte code offset #146
    //   Java source line #504	-> byte code offset #198
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	199	0	this	NpcManager
    //   4	41	1	serverType	String
    //   6	1	2	localObject1	Object
    //   161	17	2	localObject2	Object
    //   182	15	2	localObject3	Object
    //   8	1	3	localObject4	Object
    //   176	17	3	localThrowable	Throwable
    //   17	151	4	connection	Connection
    //   61	3	5	result	org.jooq.Result<NpcsRecord>
    //   85	13	6	record	NpcsRecord
    //   70	67	7	localIterator	Iterator
    //   102	29	8	npc	Npc
    // Exception table:
    //   from	to	target	type
    //   19	146	161	finally
    //   9	176	176	finally
  }
  
  /* Error */
  public void clearNpcs(boolean deleteFromDatabase)
    throws SQLException
  {
    // Byte code:
    //   0: iload_1
    //   1: ifeq +122 -> 123
    //   4: aload_0
    //   5: invokevirtual 464	mineplex/core/npc/NpcManager:getServerName	()Ljava/lang/String;
    //   8: astore_2
    //   9: aconst_null
    //   10: astore_3
    //   11: aconst_null
    //   12: astore 4
    //   14: getstatic 408	mineplex/core/database/DBPool:ACCOUNT	Ljavax/sql/DataSource;
    //   17: invokeinterface 414 1 0
    //   22: astore 5
    //   24: aload 5
    //   26: invokestatic 444	org/jooq/impl/DSL:using	(Ljava/sql/Connection;)Lorg/jooq/DSLContext;
    //   29: getstatic 450	mineplex/database/Tables:npcs	Lmineplex/database/tables/Npcs;
    //   32: invokeinterface 947 2 0
    //   37: iconst_1
    //   38: anewarray 911	org/jooq/Condition
    //   41: dup
    //   42: iconst_0
    //   43: getstatic 450	mineplex/database/Tables:npcs	Lmineplex/database/tables/Npcs;
    //   46: getfield 913	mineplex/database/tables/Npcs:server	Lorg/jooq/TableField;
    //   49: aload_2
    //   50: invokeinterface 919 2 0
    //   55: aastore
    //   56: invokeinterface 950 2 0
    //   61: invokeinterface 955 1 0
    //   66: pop
    //   67: aload 5
    //   69: ifnull +54 -> 123
    //   72: aload 5
    //   74: invokeinterface 570 1 0
    //   79: goto +44 -> 123
    //   82: astore_3
    //   83: aload 5
    //   85: ifnull +10 -> 95
    //   88: aload 5
    //   90: invokeinterface 570 1 0
    //   95: aload_3
    //   96: athrow
    //   97: astore 4
    //   99: aload_3
    //   100: ifnonnull +9 -> 109
    //   103: aload 4
    //   105: astore_3
    //   106: goto +15 -> 121
    //   109: aload_3
    //   110: aload 4
    //   112: if_acmpeq +9 -> 121
    //   115: aload_3
    //   116: aload 4
    //   118: invokevirtual 575	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   121: aload_3
    //   122: athrow
    //   123: invokestatic 809	org/bukkit/Bukkit:getWorlds	()Ljava/util/List;
    //   126: invokeinterface 327 1 0
    //   131: astore_3
    //   132: goto +70 -> 202
    //   135: aload_3
    //   136: invokeinterface 333 1 0
    //   141: checkcast 478	org/bukkit/World
    //   144: astore_2
    //   145: aload_2
    //   146: ldc_w 260
    //   149: invokeinterface 812 2 0
    //   154: invokeinterface 816 1 0
    //   159: astore 5
    //   161: goto +31 -> 192
    //   164: aload 5
    //   166: invokeinterface 333 1 0
    //   171: checkcast 260	org/bukkit/entity/LivingEntity
    //   174: astore 4
    //   176: aload_0
    //   177: aload 4
    //   179: invokevirtual 257	mineplex/core/npc/NpcManager:isNpc	(Lorg/bukkit/entity/Entity;)Z
    //   182: ifeq +10 -> 192
    //   185: aload 4
    //   187: invokeinterface 960 1 0
    //   192: aload 5
    //   194: invokeinterface 342 1 0
    //   199: ifne -35 -> 164
    //   202: aload_3
    //   203: invokeinterface 342 1 0
    //   208: ifne -73 -> 135
    //   211: aload_0
    //   212: getfield 81	mineplex/core/npc/NpcManager:_npcs	Ljava/util/List;
    //   215: invokeinterface 961 1 0
    //   220: aload_0
    //   221: getfield 86	mineplex/core/npc/NpcManager:_npcMap	Ljava/util/Map;
    //   224: invokeinterface 964 1 0
    //   229: return
    // Line number table:
    //   Java source line #508	-> byte code offset #0
    //   Java source line #510	-> byte code offset #4
    //   Java source line #512	-> byte code offset #9
    //   Java source line #512	-> byte code offset #14
    //   Java source line #514	-> byte code offset #24
    //   Java source line #515	-> byte code offset #29
    //   Java source line #516	-> byte code offset #43
    //   Java source line #517	-> byte code offset #61
    //   Java source line #518	-> byte code offset #67
    //   Java source line #521	-> byte code offset #123
    //   Java source line #523	-> byte code offset #145
    //   Java source line #525	-> byte code offset #176
    //   Java source line #526	-> byte code offset #185
    //   Java source line #523	-> byte code offset #192
    //   Java source line #521	-> byte code offset #202
    //   Java source line #530	-> byte code offset #211
    //   Java source line #531	-> byte code offset #220
    //   Java source line #532	-> byte code offset #229
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	230	0	this	NpcManager
    //   0	230	1	deleteFromDatabase	boolean
    //   8	42	2	serverType	String
    //   144	2	2	world	World
    //   10	1	3	localObject1	Object
    //   82	18	3	localObject2	Object
    //   105	98	3	localObject3	Object
    //   12	1	4	localObject4	Object
    //   97	20	4	localThrowable	Throwable
    //   174	12	4	entity	LivingEntity
    //   22	171	5	connection	Connection
    // Exception table:
    //   from	to	target	type
    //   24	67	82	finally
    //   14	97	97	finally
  }
  
  @EventHandler
  public void onUpdate(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SEC) {
      return;
    }
    if (Bukkit.getOnlinePlayers().isEmpty()) {
      return;
    }
    Iterator localIterator2;
    for (Iterator localIterator1 = Bukkit.getWorlds().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      World world = (World)localIterator1.next();
      
      localIterator2 = world.getEntitiesByClass(LivingEntity.class).iterator(); continue;LivingEntity livingEntity = (LivingEntity)localIterator2.next();
      if (isDetachedNpc(livingEntity)) {
        livingEntity.remove();
      }
    }
    for (Npc npc : this._npcs) {
      if ((npc.getEntity() != null) && (!npc.getEntity().isValid()) && (npc.getChunk().isLoaded())) {
        spawnNpc(npc);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void onChunkLoad(ChunkLoadEvent event)
  {
    Entity[] arrayOfEntity;
    int j = (arrayOfEntity = event.getChunk().getEntities()).length;
    for (int i = 0; i < j; i++)
    {
      Entity entity = arrayOfEntity[i];
      if ((entity instanceof LivingEntity))
      {
        Npc npc = getNpcByEntity(entity);
        if (npc != null)
        {
          UtilEnt.silence(entity, true);
          UtilEnt.ghost(entity, true, false);
          if (npc.getDatabaseRecord().getRadius().doubleValue() == 0.0D)
          {
            UtilEnt.Vegetate(entity);
            UtilEnt.ghost(entity, true, false);
          }
        }
        if (isDetachedNpc((LivingEntity)entity)) {
          entity.remove();
        }
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  public void onEntityDeath(EntityDeathEvent event)
  {
    Npc npc = getNpcByEntity(event.getEntity());
    if (npc != null) {
      npc.setEntity(null);
    }
  }
  
  @EventHandler
  public void onUpdateNpcMessage(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    Iterator localIterator2;
    for (Iterator localIterator1 = Bukkit.getOnlinePlayers().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      Player player = (Player)localIterator1.next();
      
      localIterator2 = this._npcs.iterator(); continue;Npc npc = (Npc)localIterator2.next();
      if (npc.getInfo() != null) {
        if (npc.getInfoRadiusSquared() != null) {
          if (npc.getDatabaseRecord().getInfoDelay() != null) {
            if (npc.getEntity() != null) {
              if (npc.getEntity().getWorld() == player.getWorld()) {
                if (npc.getEntity().getLocation().distanceSquared(player.getLocation()) <= npc.getInfoRadiusSquared().doubleValue()) {
                  if (Recharge.Instance.use(player, npc.getEntity().getCustomName() + " Info", npc.getDatabaseRecord().getInfoDelay().intValue(), false, false))
                  {
                    player.sendMessage(npc.getInfo());
                    
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 2.0F);
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  @EventHandler
  public void onNpcInteract(PlayerInteractEntityEvent event)
  {
    Npc npc = getNpcByEntity(event.getRightClicked());
    if (npc == null) {
      return;
    }
    if (npc.getInfo() == null) {
      return;
    }
    if (!Recharge.Instance.use(event.getPlayer(), npc.getEntity().getCustomName() + " Info Click", 2000L, false, false)) {
      return;
    }
    event.getPlayer().sendMessage(npc.getInfo());
    
    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0F, 2.0F);
  }
  
  @EventHandler
  public void onNpcDamage(EntityDamageByEntityEvent event)
  {
    if (!(event.getDamager() instanceof Player)) {
      return;
    }
    Player player = (Player)event.getDamager();
    
    Npc npc = getNpcByEntity(event.getEntity());
    if (npc == null) {
      return;
    }
    if (npc.getInfo() == null) {
      return;
    }
    if (!Recharge.Instance.use(player, npc.getEntity().getCustomName() + " Info Click", 2000L, false, false)) {
      return;
    }
    player.sendMessage(npc.getInfo());
    
    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 2.0F);
  }
  
  public String getServerName()
  {
    String serverName = getPlugin().getClass().getSimpleName();
    if ((Bukkit.getMotd() != null) && (Bukkit.getMotd().equalsIgnoreCase("test"))) {
      serverName = serverName + "-Test";
    }
    return serverName;
  }
}

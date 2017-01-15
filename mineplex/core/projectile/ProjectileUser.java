package mineplex.core.projectile;

import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MovingObjectPosition;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ProjectileUser
{
  public ProjectileManager Throw;
  private org.bukkit.entity.Entity _thrown;
  private LivingEntity _thrower;
  private IThrown _callback;
  private long _expireTime;
  private boolean _hitPlayer = false;
  private boolean _hitBlock = false;
  private boolean _idle = false;
  private boolean _pickup = false;
  private Sound _sound = null;
  private float _soundVolume = 1.0F;
  private float _soundPitch = 1.0F;
  private UtilParticle.ParticleType _particle = null;
  private float _particleX = 0.0F;
  private float _particleY = 0.0F;
  private float _particleZ = 0.0F;
  private float _particleS = 0.0F;
  private int _particleC = 1;
  private Effect _effect = null;
  private int _effectData = 0;
  private UpdateType _effectRate = UpdateType.TICK;
  private double _hitboxGrow;
  
  public ProjectileUser(ProjectileManager throwInput, org.bukkit.entity.Entity thrown, LivingEntity thrower, IThrown callback, long expireTime, boolean hitPlayer, boolean hitBlock, boolean idle, boolean pickup, Sound sound, float soundVolume, float soundPitch, Effect effect, int effectData, UpdateType effectRate, UtilParticle.ParticleType particle, float particleX, float particleY, float particleZ, float particleS, int particleC, double hitboxMult)
  {
    this.Throw = throwInput;
    
    this._thrown = thrown;
    this._thrower = thrower;
    this._callback = callback;
    
    this._expireTime = expireTime;
    this._hitPlayer = hitPlayer;
    this._hitBlock = hitBlock;
    this._idle = idle;
    this._pickup = pickup;
    
    this._sound = sound;
    this._soundVolume = soundVolume;
    this._soundPitch = soundPitch;
    this._particle = particle;
    this._particleX = particleX;
    this._particleY = particleY;
    this._particleZ = particleZ;
    this._particleS = particleS;
    this._particleC = particleC;
    this._effect = effect;
    this._effectData = effectData;
    this._effectRate = effectRate;
    
    this._hitboxGrow = hitboxMult;
  }
  
  public void Effect(UpdateEvent event)
  {
    if (event.getType() != this._effectRate) {
      return;
    }
    if (this._sound != null) {
      this._thrown.getWorld().playSound(this._thrown.getLocation(), this._sound, this._soundVolume, this._soundPitch);
    }
    if (this._effect != null) {
      this._thrown.getWorld().playEffect(this._thrown.getLocation(), this._effect, this._effectData);
    }
    if (this._particle != null) {
      UtilParticle.PlayParticle(this._particle, this._thrown.getLocation(), this._particleX, this._particleY, this._particleZ, this._particleS, this._particleC, 
        UtilParticle.ViewDist.LONG, UtilServer.getPlayers());
    }
  }
  
  public boolean Collision()
  {
    if ((this._expireTime != -1L) && (System.currentTimeMillis() > this._expireTime))
    {
      this._callback.Expire(this);
      return true;
    }
    if (this._hitPlayer)
    {
      double distanceToEntity = 0.0D;
      LivingEntity victim = null;
      
      net.minecraft.server.v1_7_R4.Entity nmsEntity = ((CraftEntity)this._thrown).getHandle();
      Vec3D vec3d = Vec3D.a(nmsEntity.locX, nmsEntity.locY, nmsEntity.locZ);
      Vec3D vec3d1 = Vec3D.a(nmsEntity.locX + nmsEntity.motX, nmsEntity.locY + nmsEntity.motY, nmsEntity.locZ + nmsEntity.motZ);
      
      MovingObjectPosition finalObjectPosition = nmsEntity.world.rayTrace(vec3d, vec3d1, false, true, false);
      vec3d = Vec3D.a(nmsEntity.locX, nmsEntity.locY, nmsEntity.locZ);
      vec3d1 = Vec3D.a(nmsEntity.locX + nmsEntity.motX, nmsEntity.locY + nmsEntity.motY, nmsEntity.locZ + nmsEntity.motZ);
      if (finalObjectPosition != null) {
        vec3d1 = Vec3D.a(finalObjectPosition.pos.a, finalObjectPosition.pos.b, finalObjectPosition.pos.c);
      }
      for (Object entity : ((CraftWorld)this._thrown.getWorld()).getHandle().getEntities(((CraftEntity)this._thrown).getHandle(), 
        ((CraftEntity)this._thrown).getHandle().boundingBox.a(
        ((CraftEntity)this._thrown).getHandle().motX, 
        ((CraftEntity)this._thrown).getHandle().motY, 
        ((CraftEntity)this._thrown).getHandle().motZ).grow(this._hitboxGrow, this._hitboxGrow, this._hitboxGrow))) {
        if ((entity instanceof net.minecraft.server.v1_7_R4.Entity))
        {
          org.bukkit.entity.Entity bukkitEntity = ((net.minecraft.server.v1_7_R4.Entity)entity).getBukkitEntity();
          if ((bukkitEntity instanceof LivingEntity))
          {
            LivingEntity ent = (LivingEntity)bukkitEntity;
            if (!ent.equals(this._thrower)) {
              if ((!(ent instanceof Player)) || (
                (((Player)ent).getGameMode() != GameMode.CREATIVE) && (!UtilPlayer.isSpectator(ent))))
              {
                AxisAlignedBB axisalignedbb1 = ((CraftEntity)ent).getHandle().boundingBox.grow(1.0D, 1.0D, 1.0D);
                MovingObjectPosition entityCollisionPosition = axisalignedbb1.a(vec3d, vec3d1);
                if (entityCollisionPosition != null)
                {
                  if ((this._thrower instanceof Player)) {
                    ((Player)this._thrower).playSound(this._thrower.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.25F);
                  }
                  double d1 = vec3d.distanceSquared(entityCollisionPosition.pos);
                  if ((d1 < distanceToEntity) || (distanceToEntity == 0.0D))
                  {
                    victim = ent;
                    distanceToEntity = d1;
                  }
                }
              }
            }
          }
        }
      }
      if (victim != null)
      {
        finalObjectPosition = new MovingObjectPosition(((CraftLivingEntity)victim).getHandle());
        
        this._callback.Collide(victim, null, this);
        return true;
      }
      if (finalObjectPosition != null) {
        if (this._hitBlock)
        {
          Block block = this._thrown.getWorld().getBlockAt(finalObjectPosition.b, finalObjectPosition.c, finalObjectPosition.d);
          if ((!UtilBlock.airFoliage(block)) && (!block.isLiquid()))
          {
            nmsEntity.motX = ((float)(finalObjectPosition.pos.a - nmsEntity.locX));
            nmsEntity.motY = ((float)(finalObjectPosition.pos.b - nmsEntity.locY));
            nmsEntity.motZ = ((float)(finalObjectPosition.pos.c - nmsEntity.locZ));
            float f2 = MathHelper.sqrt(nmsEntity.motX * nmsEntity.motX + nmsEntity.motY * nmsEntity.motY + nmsEntity.motZ * nmsEntity.motZ);
            nmsEntity.locX -= nmsEntity.motX / f2 * 0.0500000007450581D;
            nmsEntity.locY -= nmsEntity.motY / f2 * 0.0500000007450581D;
            nmsEntity.locZ -= nmsEntity.motZ / f2 * 0.0500000007450581D;
            
            this._callback.Collide(null, block, this);
            return true;
          }
        }
      }
    }
    try
    {
      if (this._idle) {
        if ((this._thrown.getVelocity().length() < 0.2D) && 
          (!UtilBlock.airFoliage(this._thrown.getLocation().getBlock().getRelative(BlockFace.DOWN))))
        {
          this._callback.Idle(this);
          return true;
        }
      }
    }
    catch (Exception ex)
    {
      if (this._hitBlock) {
        return true;
      }
      if (this._idle) {
        return true;
      }
    }
    return false;
  }
  
  public LivingEntity GetThrower()
  {
    return this._thrower;
  }
  
  public org.bukkit.entity.Entity GetThrown()
  {
    return this._thrown;
  }
  
  public boolean CanPickup(LivingEntity thrower)
  {
    if (!thrower.equals(this._thrower)) {
      return false;
    }
    return this._pickup;
  }
}

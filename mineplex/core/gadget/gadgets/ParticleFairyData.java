package mineplex.core.gadget.gadgets;

import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParticleFairyData
{
  public Player Player;
  public Location Fairy;
  public Vector Direction;
  public Location Target;
  public double Speed;
  public long IdleTime;
  
  public ParticleFairyData(Player player)
  {
    this.Player = player;
    this.Direction = new Vector(1, 0, 0);
    this.Fairy = player.getEyeLocation();
    this.Target = getNewTarget();
    
    this.Speed = 0.2D;
    
    this.IdleTime = 0L;
  }
  
  public void Update()
  {
    if ((UtilMath.offset(this.Player.getEyeLocation(), this.Target) > 3.0D) || (UtilMath.offset(this.Fairy, this.Target) < 1.0D)) {
      this.Target = getNewTarget();
    }
    if (Math.random() > 0.98D) {
      this.IdleTime = (System.currentTimeMillis() + (Math.random() * 3000.0D));
    }
    if (UtilMath.offset(this.Player.getEyeLocation(), this.Fairy) < 3.0D)
    {
      if (this.IdleTime > System.currentTimeMillis()) {
        this.Speed = Math.max(0.0D, this.Speed - 0.005D);
      } else {
        this.Speed = Math.min(0.15D, this.Speed + 0.005D);
      }
    }
    else
    {
      this.IdleTime = 0L;
      
      this.Speed = Math.min(0.15D + UtilMath.offset(this.Player.getEyeLocation(), this.Fairy) * 0.05D, this.Speed + 0.02D);
    }
    this.Direction.add(UtilAlg.getTrajectory(this.Fairy, this.Target).multiply(0.15D));
    if (this.Direction.length() < 1.0D) {
      this.Speed *= this.Direction.length();
    }
    UtilAlg.Normalize(this.Direction);
    if (UtilMath.offset(this.Fairy, this.Target) > 0.1D) {
      this.Fairy.add(this.Direction.clone().multiply(this.Speed));
    }
    UtilParticle.PlayParticle(UtilParticle.ParticleType.FLAME, this.Fairy, 0.0F, 0.0F, 0.0F, 0.0F, 1, 
      UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    UtilParticle.PlayParticle(UtilParticle.ParticleType.LAVA, this.Fairy, 0.0F, 0.0F, 0.0F, 0.0F, 1, 
      UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    
    this.Fairy.getWorld().playSound(this.Fairy, Sound.CAT_PURREOW, 0.1F, 3.0F);
  }
  
  private Location getNewTarget()
  {
    return this.Player.getEyeLocation().add(Math.random() * 6.0D - 3.0D, Math.random() * 1.5D, Math.random() * 6.0D - 3.0D);
  }
}

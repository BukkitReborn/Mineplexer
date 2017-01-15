package mineplex.core.npc.event;

import org.bukkit.entity.LivingEntity;

public class NpcDamageByEntityEvent
  extends NpcEvent
{
  private LivingEntity _damager;
  
  public NpcDamageByEntityEvent(LivingEntity npc, LivingEntity damager)
  {
    super(npc);
    
    this._damager = damager;
  }
  
  public LivingEntity getDamager()
  {
    return this._damager;
  }
}

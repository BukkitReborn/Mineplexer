package mineplex.core.treasure;

import mineplex.core.common.util.UtilParticle.ParticleType;
import org.bukkit.Sound;

public enum TreasureStyle
{
  OLD(
    UtilParticle.ParticleType.EXPLODE, 
    UtilParticle.ParticleType.EXPLODE, 
    UtilParticle.ParticleType.ENCHANTMENT_TABLE, 
    Sound.FIZZ, 
    Sound.HORSE_ARMOR),  ANCIENT(
    UtilParticle.ParticleType.FLAME, 
    UtilParticle.ParticleType.LAVA, 
    UtilParticle.ParticleType.MOB_SPELL, 
    Sound.LAVA_POP, 
    Sound.EXPLODE),  MYTHICAL(
    UtilParticle.ParticleType.HAPPY_VILLAGER, 
    UtilParticle.ParticleType.LARGE_EXPLODE, 
    UtilParticle.ParticleType.INSTANT_SPELL, 
    Sound.PORTAL_TRAVEL, 
    Sound.ANVIL_LAND);
  
  private UtilParticle.ParticleType _secondaryParticle;
  private UtilParticle.ParticleType _chestSpawnParticle;
  private UtilParticle.ParticleType _hoverParticle;
  private Sound _sound;
  private Sound _chestSpawnSound;
  
  private TreasureStyle(UtilParticle.ParticleType secondaryParticle, UtilParticle.ParticleType chestSpawnParticle, UtilParticle.ParticleType hoverParticle, Sound sound, Sound chestSpawnSound)
  {
    this._secondaryParticle = secondaryParticle;
    this._chestSpawnParticle = chestSpawnParticle;
    this._hoverParticle = hoverParticle;
    this._sound = sound;
    this._chestSpawnSound = chestSpawnSound;
  }
  
  public UtilParticle.ParticleType getSecondaryParticle()
  {
    return this._secondaryParticle;
  }
  
  public UtilParticle.ParticleType getChestSpawnParticle()
  {
    return this._chestSpawnParticle;
  }
  
  public UtilParticle.ParticleType getHoverParticle()
  {
    return this._hoverParticle;
  }
  
  public Sound getSound()
  {
    return this._sound;
  }
  
  public Sound getChestSpawnSound()
  {
    return this._chestSpawnSound;
  }
}

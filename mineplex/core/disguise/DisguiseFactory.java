package mineplex.core.disguise;

import mineplex.core.disguise.disguises.DisguiseBase;
import mineplex.core.disguise.disguises.DisguiseBat;
import mineplex.core.disguise.disguises.DisguiseBlaze;
import mineplex.core.disguise.disguises.DisguiseCat;
import mineplex.core.disguise.disguises.DisguiseChicken;
import mineplex.core.disguise.disguises.DisguiseCow;
import mineplex.core.disguise.disguises.DisguiseCreeper;
import mineplex.core.disguise.disguises.DisguiseEnderman;
import mineplex.core.disguise.disguises.DisguiseHorse;
import mineplex.core.disguise.disguises.DisguiseIronGolem;
import mineplex.core.disguise.disguises.DisguiseMagmaCube;
import mineplex.core.disguise.disguises.DisguisePig;
import mineplex.core.disguise.disguises.DisguisePigZombie;
import mineplex.core.disguise.disguises.DisguisePlayer;
import mineplex.core.disguise.disguises.DisguiseSheep;
import mineplex.core.disguise.disguises.DisguiseSkeleton;
import mineplex.core.disguise.disguises.DisguiseSlime;
import mineplex.core.disguise.disguises.DisguiseSnowman;
import mineplex.core.disguise.disguises.DisguiseSpider;
import mineplex.core.disguise.disguises.DisguiseSquid;
import mineplex.core.disguise.disguises.DisguiseVillager;
import mineplex.core.disguise.disguises.DisguiseWitch;
import mineplex.core.disguise.disguises.DisguiseWolf;
import mineplex.core.disguise.disguises.DisguiseZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseFactory
{
  public static DisguiseBase createDisguise(Entity disguised, EntityType disguiseType)
  {
    switch (disguiseType)
    {
    case PIG: 
      return new DisguiseBat(disguised);
    case MINECART_TNT: 
      return new DisguiseBlaze(disguised);
    case SNOWMAN: 
      return new DisguiseCat(disguised);
    case SILVERFISH: 
      return new DisguiseChicken(disguised);
    case SHEEP: 
      return new DisguiseCow(disguised);
    case IRON_GOLEM: 
      return new DisguiseCreeper(disguised);
    case MINECART_FURNACE: 
      return new DisguiseEnderman(disguised);
    case SPLASH_POTION: 
      return new DisguiseHorse(disguised);
    case SPIDER: 
      return new DisguiseIronGolem(disguised);
    case MUSHROOM_COW: 
      return new DisguiseMagmaCube(disguised);
    case PLAYER: 
      return new DisguisePig(disguised);
    case MINECART_COMMAND: 
      return new DisguisePigZombie(disguised);
    case WITHER_SKULL: 
      return new DisguisePlayer(disguised);
    case PRIMED_TNT: 
      return new DisguiseSheep(disguised);
    case ITEM_FRAME: 
      return new DisguiseSkeleton(disguised);
    case MINECART: 
      return new DisguiseSlime(disguised);
    case SNOWBALL: 
      return new DisguiseSnowman(disguised);
    case LEASH_HITCH: 
      return new DisguiseSpider(disguised);
    case SKELETON: 
      return new DisguiseSquid(disguised);
    case SQUID: 
      return new DisguiseVillager(disguised);
    case PIG_ZOMBIE: 
      return new DisguiseWitch(disguised);
    case SLIME: 
      return new DisguiseWolf(disguised);
    case MAGMA_CUBE: 
      return new DisguiseZombie(disguised);
    }
    return null;
  }
}

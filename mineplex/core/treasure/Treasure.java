package mineplex.core.treasure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import mineplex.core.blockrestore.BlockRestore;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.hologram.HologramManager;
import mineplex.core.reward.Reward;
import mineplex.core.reward.RewardData;
import mineplex.core.reward.RewardRarity;
import mineplex.core.treasure.animation.Animation;
import mineplex.core.treasure.animation.BlockChangeAnimation;
import mineplex.core.treasure.animation.ChestOpenAnimation;
import mineplex.core.treasure.animation.ChestSpawnAnimation;
import mineplex.core.treasure.animation.LootLegendaryAnimation;
import mineplex.core.treasure.animation.LootMythicalAnimation;
import mineplex.core.treasure.animation.LootRareAnimation;
import mineplex.core.treasure.animation.LootUncommonAnimation;
import mineplex.core.treasure.animation.TreasureRemoveAnimation;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockAction;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

public class Treasure
{
  private BlockRestore _blockRestore;
  private List<BlockInfo> _chestBlockInfo = new ArrayList();
  private List<BlockInfo> _openedChestBlockInfo = new ArrayList();
  private List<BlockInfo> _otherBlockInfo = new ArrayList();
  private Player _player;
  private Random _random;
  private Block _centerBlock;
  private int _tickCount;
  private TreasureType _treasureType;
  private ChestData[] _chestData;
  private int _currentChest;
  private Reward[] _rewards;
  private int _currentReward;
  private boolean _finished;
  private int _finishedTickCount;
  private LinkedList<Animation> _animations;
  private HologramManager _hologramManager;
  
  public Treasure(Player player, Reward[] rewards, Block centerBlock, Block[] chestBlocks, TreasureType treasureType, BlockRestore blockRestore, HologramManager hologramManager)
  {
    this(player, new Random(), rewards, centerBlock, chestBlocks, treasureType, hologramManager);
    
    this._blockRestore = blockRestore;
  }
  
  public Treasure(Player player, Random seed, Reward[] rewards, Block centerBlock, Block[] chestBlocks, TreasureType treasureType, HologramManager hologramManager)
  {
    this._player = player;
    this._random = seed;
    
    this._treasureType = treasureType;
    
    this._centerBlock = centerBlock;
    this._animations = new LinkedList();
    this._hologramManager = hologramManager;
    
    this._currentChest = 0;
    this._currentReward = 0;
    this._rewards = rewards;
    
    this._chestData = new ChestData[chestBlocks.length];
    for (int i = 0; i < this._chestData.length; i++) {
      this._chestData[i] = new ChestData(chestBlocks[i]);
    }
    this._animations.add(new BlockChangeAnimation(this, this._otherBlockInfo));
  }
  
  public int getFinishedTickCount()
  {
    return this._finishedTickCount;
  }
  
  public void update()
  {
    if (this._finished) {
      this._finishedTickCount += 1;
    }
    if ((this._tickCount % 10 == 0) && (this._currentChest < this._chestData.length))
    {
      ChestSpawnAnimation chestSpawn = new ChestSpawnAnimation(this, this._chestData[this._currentChest].getBlock(), this._chestBlockInfo, this._centerBlock, this._currentChest);
      this._animations.add(chestSpawn);
      this._currentChest += 1;
    }
    if (this._tickCount == 1200) {
      for (BlockInfo blockInfo : this._chestBlockInfo)
      {
        Block block = blockInfo.getBlock();
        openChest(block, false);
      }
    }
    Block block = this._player.getTargetBlock(null, 3);
    if (block.getType() == this._treasureType.getMaterial())
    {
      ChestData data = getChestData(block);
      if ((!isFinished()) && (data != null) && (!data.isOpened()))
      {
        UtilParticle.ParticleType type = getTreasureType().getStyle().getHoverParticle();
        if (this._treasureType == TreasureType.OLD)
        {
          UtilParticle.PlayParticle(type, block.getLocation().add(0.5D, 0.5D, 0.5D), 0.0F, 0.0F, 0.0F, 1.0F, 4, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
        else if (this._treasureType == TreasureType.ANCIENT)
        {
          double yDif = 0.2D + 0.6D * Math.sin(3.141592653589793D * (this._tickCount / 10.0D));
          double xDif = 0.7D * Math.sin(3.141592653589793D * (this._tickCount / 5.0D));
          double zDif = 0.7D * Math.cos(3.141592653589793D * (this._tickCount / 5.0D));
          float red = 0.1F + (float)(0.4D * (1.0D + Math.cos(3.141592653589793D * (this._tickCount / 20.0D))));
          UtilParticle.PlayParticle(type, block.getLocation().add(0.5D + xDif, 0.5D + yDif, 0.5D + zDif), red, 0.2F, 0.2F, 1.0F, 0, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
        else
        {
          UtilParticle.PlayParticle(type, block.getLocation().add(0.5D, 0.5D, 0.5D), 0.5F, 0.5F, 0.5F, 0.2F, 0, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
      }
    }
    Object taskIterator = this._animations.iterator();
    while (((Iterator)taskIterator).hasNext())
    {
      Animation animation = (Animation)((Iterator)taskIterator).next();
      if (animation.isRunning()) {
        animation.run();
      } else {
        ((Iterator)taskIterator).remove();
      }
    }
    this._tickCount += 1;
  }
  
  public Block getCenterBlock()
  {
    return this._centerBlock;
  }
  
  public void setBlock(Block block, Material material, byte data)
  {
    block.setType(material);
    block.setData(data);
    block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
  }
  
  public void openChest(Block block)
  {
    openChest(block, true);
  }
  
  public void openChest(Block block, boolean swapList)
  {
    ChestData data = getChestData(block);
    if ((data != null) && (!data.isOpened()) && (this._currentReward < this._rewards.length))
    {
      Reward reward = this._rewards[this._currentReward];
      RewardData rewardData = reward.giveReward("Treasure", this._player);
      this._currentReward += 1;
      if (swapList)
      {
        BlockInfo info = getBlockInfo(block);
        this._chestBlockInfo.remove(info);
        this._openedChestBlockInfo.add(info);
      }
      data.setOpened(true);
      ChestOpenAnimation chestOpenTask = new ChestOpenAnimation(this, data, rewardData, this._hologramManager);
      this._animations.add(chestOpenTask);
      if (reward.getRarity() == RewardRarity.UNCOMMON)
      {
        this._animations.add(new LootUncommonAnimation(this, data.getBlock()));
      }
      else if (reward.getRarity() == RewardRarity.RARE)
      {
        this._animations.add(new LootRareAnimation(this, data.getBlock().getLocation().add(0.5D, 1.5D, 0.5D)));
        Bukkit.broadcastMessage(F.main("Treasure", F.name(this._player.getName()) + " found " + C.cPurple + "Rare " + rewardData.getFriendlyName()));
      }
      else if (reward.getRarity() == RewardRarity.LEGENDARY)
      {
        this._animations.add(new LootLegendaryAnimation(this, data.getBlock()));
        Bukkit.broadcastMessage(F.main("Treasure", F.name(this._player.getName()) + " found " + C.cGreen + "Legendary " + rewardData.getFriendlyName()));
      }
      else if (reward.getRarity() == RewardRarity.MYTHICAL)
      {
        this._animations.add(new LootMythicalAnimation(this, data.getBlock()));
        Bukkit.broadcastMessage(F.main("Treasure", F.name(this._player.getName()) + " found " + C.cRed + "Mythical " + rewardData.getFriendlyName()));
      }
      if (isFinished())
      {
        TreasureRemoveAnimation animation = new TreasureRemoveAnimation(this, this._openedChestBlockInfo, this._chestBlockInfo);
        this._animations.add(animation);
        this._finished = true;
      }
    }
  }
  
  public BlockInfo getBlockInfo(Block block)
  {
    for (BlockInfo blockInfo : this._chestBlockInfo) {
      if (blockInfo.getBlock().equals(block)) {
        return blockInfo;
      }
    }
    return null;
  }
  
  public void sendChestOpenPackets(Player... players)
  {
    ChestData[] arrayOfChestData;
    int j = (arrayOfChestData = this._chestData).length;
    for (int i = 0; i < j; i++)
    {
      ChestData data = arrayOfChestData[i];
      if (data.isOpened())
      {
        Block block = data.getBlock();
        PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(block.getX(), block.getY(), block.getZ(), CraftMagicNumbers.getBlock(block), 1, 1);
        Player[] arrayOfPlayer;
        int m = (arrayOfPlayer = players).length;
        for (int k = 0; k < m; k++)
        {
          Player player = arrayOfPlayer[k];
          
          ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
      }
    }
  }
  
  public ChestData getChestData(Block block)
  {
    ChestData[] arrayOfChestData;
    int j = (arrayOfChestData = this._chestData).length;
    for (int i = 0; i < j; i++)
    {
      ChestData data = arrayOfChestData[i];
      if (data.getBlock().equals(block)) {
        return data;
      }
    }
    return null;
  }
  
  public Player getPlayer()
  {
    return this._player;
  }
  
  public boolean isFinished()
  {
    return this._currentReward == this._rewards.length;
  }
  
  public void cleanup()
  {
    for (int i = this._currentReward; i < this._rewards.length; i++) {
      this._rewards[this._currentReward].giveReward("Treasure", this._player);
    }
    this._currentReward = this._rewards.length;
    
    resetBlockInfo(this._chestBlockInfo);
    resetBlockInfo(this._openedChestBlockInfo);
    resetBlockInfo(this._otherBlockInfo);
    for (Animation animation : this._animations) {
      animation.finish();
    }
    this._animations.clear();
  }
  
  public void resetBlockInfo(List<BlockInfo> blockInfoSet)
  {
    for (BlockInfo blockInfo : blockInfoSet) {
      resetBlockInfo(blockInfo);
    }
    blockInfoSet.clear();
  }
  
  public void resetBlockInfo(BlockInfo blockInfo)
  {
    if (blockInfo == null) {
      return;
    }
    Block block = blockInfo.getBlock();
    if (block.getType().equals(Material.CHEST)) {
      UtilParticle.PlayParticle(UtilParticle.ParticleType.LARGE_SMOKE, block.getLocation().add(0.5D, 0.5D, 0.5D), 0.5F, 0.5F, 0.5F, 0.1F, 10, 
        UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    }
    block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
    
    block.setTypeId(blockInfo.getId());
    block.setData(blockInfo.getData());
  }
  
  public boolean containsBlock(Block block)
  {
    for (BlockInfo info : this._chestBlockInfo) {
      if (info.getBlock().equals(block)) {
        return true;
      }
    }
    return false;
  }
  
  public TreasureType getTreasureType()
  {
    return this._treasureType;
  }
}

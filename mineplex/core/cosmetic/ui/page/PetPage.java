package mineplex.core.cosmetic.ui.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.C;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.PetSorter;
import mineplex.core.cosmetic.ui.button.ActivatePetButton;
import mineplex.core.cosmetic.ui.button.DeactivatePetButton;
import mineplex.core.cosmetic.ui.button.PetButton;
import mineplex.core.cosmetic.ui.button.RenamePetButton;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.pet.Pet;
import mineplex.core.pet.PetClient;
import mineplex.core.pet.PetExtra;
import mineplex.core.pet.PetFactory;
import mineplex.core.pet.PetManager;
import mineplex.core.pet.types.Elf;
import mineplex.core.pet.types.Pumpkin;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import mineplex.core.shop.page.AnvilContainer;
import mineplex.core.shop.page.ShopPageBase;
import net.minecraft.server.v1_7_R4.Container;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_7_R4.PacketPlayOutSetSlot;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class PetPage
  extends ShopPageBase<CosmeticManager, CosmeticShop>
{
  public PetPage(CosmeticManager plugin, CosmeticShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    super(plugin, shop, clientManager, donationManager, name, player, 54);
    
    buildPage();
  }
  
  protected void buildPage()
  {
    int slot = 19;
    
    List<Pet> pets = new ArrayList(((CosmeticManager)getPlugin()).getPetManager().GetFactory().GetPets());
    
    Collections.sort(pets, new PetSorter());
    for (Pet pet : pets)
    {
      List<String> itemLore = new ArrayList();
      if (pet.GetCost(CurrencyType.Coins) == -1)
      {
        if ((pet instanceof Pumpkin))
        {
          itemLore.add(C.cBlack);
          itemLore.add(ChatColor.RESET + C.cYellow + "Earned by defeating the Pumpkin King");
          itemLore.add(ChatColor.RESET + C.cYellow + "in the 2014 Halloween Horror Event.");
        }
        if ((pet instanceof Elf))
        {
          itemLore.add(C.cBlack);
          itemLore.add(ChatColor.RESET + C.cYellow + "Earned by defeating the Pumpkin King");
          itemLore.add(ChatColor.RESET + C.cYellow + "in the 2014 Christmas Chaos Event.");
        }
        if (pet.GetPetType() == EntityType.WITHER)
        {
          itemLore.add(C.cBlack);
          itemLore.add(ChatColor.RESET + C.cYellow + "Unlocked with Legend Rank");
        }
      }
      if (((Donor)getDonationManager().Get(getPlayer().getName())).OwnsUnknownPackage(pet.GetPetName()))
      {
        String petName = (String)((PetClient)((CosmeticManager)getPlugin()).getPetManager().Get(getPlayer())).GetPets().get(pet.GetPetType());
        if (petName == null) {
          petName = pet.GetName();
        }
        if ((((CosmeticManager)getPlugin()).getPetManager().hasActivePet(getPlayer().getName())) && (((CosmeticManager)getPlugin()).getPetManager().getActivePet(getPlayer().getName()).getType() == pet.GetPetType()))
        {
          addButton(slot, new ShopItem(Material.MONSTER_EGG, (byte)pet.GetPetType().getTypeId(), "Deactivate " + pet
            .GetPetName() + " (" + C.cWhite + petName + C.cGreen + ")", 
            (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new DeactivatePetButton(this, ((CosmeticManager)getPlugin()).getPetManager()));
          
          addGlow(slot);
        }
        else
        {
          addButton(slot, new ShopItem(Material.MONSTER_EGG, (byte)pet.GetPetType().getTypeId(), "Activate " + pet
            .GetPetName() + " (" + C.cWhite + petName + C.cGreen + ")", 
            (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new ActivatePetButton(pet, this));
        }
      }
      else
      {
        if (pet.GetCost(CurrencyType.Coins) > 0)
        {
          itemLore.add(C.cAqua + pet.GetCost(CurrencyType.Coins) + " Shards");
          itemLore.add(C.cBlack);
        }
        if (pet.GetCost(CurrencyType.Coins) == -1) {
          setItem(slot, new ShopItem(Material.INK_SACK, (byte)8, pet.GetPetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, true, false));
        } else if (((Donor)getDonationManager().Get(getPlayer().getName())).GetBalance(CurrencyType.Coins) >= pet.GetCost(CurrencyType.Coins)) {
          addButton(slot, new ShopItem(Material.INK_SACK, (byte)8, "Purchase " + pet.GetPetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new PetButton(pet, this));
        } else {
          setItem(slot, new ShopItem(Material.INK_SACK, (byte)8, "Purchase " + pet.GetPetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, true, false));
        }
      }
      slot++;
      if (slot == 26) {
        slot = 28;
      }
    }
    slot = 49;
    for (PetExtra petExtra : ((CosmeticManager)getPlugin()).getPetManager().GetFactory().GetPetExtras())
    {
      List<String> itemLore = new ArrayList();
      if (!((CosmeticManager)getPlugin()).getPetManager().hasActivePet(getPlayer().getName()))
      {
        itemLore.add(C.cWhite + "You must have an active pet to use this!");
        getInventory().setItem(slot, new ShopItem(petExtra.GetMaterial(), (byte)0, C.cRed + petExtra.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, true, false).getHandle());
      }
      else if (((CosmeticManager)getPlugin()).getPetManager().getActivePet(getPlayer().getName()).getType() != EntityType.WITHER)
      {
        addButton(slot, new ShopItem(petExtra.GetMaterial(), (byte)0, "Rename " + ((CosmeticManager)getPlugin()).getPetManager().getActivePet(getPlayer().getName()).getCustomName() + " for " + C.cAqua + petExtra.GetCost(CurrencyType.Coins) + C.cGreen + " Shards", (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new RenamePetButton(this));
      }
      slot++;
    }
    addButton(4, new ShopItem(Material.BED, C.cGray + " ? Go Back", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ((CosmeticShop)PetPage.this.getShop()).openPageForPlayer(PetPage.this.getPlayer(), new Menu((CosmeticManager)PetPage.this.getPlugin(), (CosmeticShop)PetPage.this.getShop(), PetPage.this.getClientManager(), PetPage.this.getDonationManager(), player));
      }
    });
  }
  
  public void purchasePet(Player player, Pet pet)
  {
    renamePet(player, pet, true);
  }
  
  public void renamePet(Player player, Pet pet, boolean petPurchase)
  {
    playAcceptSound(player);
    
    PetTagPage petTagPage = new PetTagPage((CosmeticManager)getPlugin(), (CosmeticShop)getShop(), getClientManager(), getDonationManager(), "Repairing", getPlayer(), pet, petPurchase);
    EntityPlayer entityPlayer = ((CraftPlayer)getPlayer()).getHandle();
    int containerCounter = entityPlayer.nextContainerCounter();
    entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerCounter, 8, "Repairing", 0, true));
    entityPlayer.activeContainer = new AnvilContainer(entityPlayer.inventory, petTagPage.getInventory());
    entityPlayer.activeContainer.windowId = containerCounter;
    entityPlayer.activeContainer.addSlotListener(entityPlayer);
    entityPlayer.playerConnection.sendPacket(new PacketPlayOutSetSlot(containerCounter, 0, new ItemStack(Items.NAME_TAG)));
    
    ((CosmeticShop)getShop()).setCurrentPageForPlayer(getPlayer(), petTagPage);
  }
  
  public void deactivatePet(Player player)
  {
    playAcceptSound(player);
    ((CosmeticManager)getPlugin()).getPetManager().RemovePet(player, true);
    refresh();
  }
}

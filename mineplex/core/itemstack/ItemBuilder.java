package mineplex.core.itemstack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.ItemMeta.Spigot;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;

public class ItemBuilder
{
  private int _amount;
  private Color _color;
  private short _data;
  
  private static ArrayList<String> split(String string, int maxLength)
  {
    String[] split = string.split(" ");
    string = "";
    ArrayList<String> newString = new ArrayList();
    for (int i = 0; i < split.length; i++)
    {
      string = string + (string.length() == 0 ? "" : " ") + split[i];
      if (ChatColor.stripColor(string).length() > maxLength)
      {
        newString.add((newString.size() > 0 ? ChatColor.getLastColors((String)newString.get(newString.size() - 1)) : "") + string);
        string = "";
      }
    }
    if (string.length() > 0) {
      newString.add((newString.size() > 0 ? ChatColor.getLastColors((String)newString.get(newString.size() - 1)) : "") + string);
    }
    return newString;
  }
  
  private final HashMap<Enchantment, Integer> _enchants = new HashMap();
  private final List<String> _lore = new ArrayList();
  private Material _mat;
  private String _title = null;
  private boolean _unbreakable;
  private String _playerHeadName = null;
  
  public ItemBuilder(ItemStack item)
  {
    this(item.getType(), item.getDurability());
    this._amount = item.getAmount();
    this._enchants.putAll(item.getEnchantments());
    item.getType();
    if (item.hasItemMeta())
    {
      ItemMeta meta = item.getItemMeta();
      if (meta.hasDisplayName()) {
        this._title = meta.getDisplayName();
      }
      if (meta.hasLore()) {
        this._lore.addAll(meta.getLore());
      }
      if ((meta instanceof LeatherArmorMeta)) {
        setColor(((LeatherArmorMeta)meta).getColor());
      }
      this._unbreakable = meta.spigot().isUnbreakable();
    }
  }
  
  public ItemBuilder(Material mat)
  {
    this(mat, 1);
  }
  
  public ItemBuilder(Material mat, int amount)
  {
    this(mat, amount, (short)0);
  }
  
  public ItemBuilder(Material mat, int amount, short data)
  {
    this._mat = mat;
    this._amount = amount;
    this._data = data;
  }
  
  public ItemBuilder(Material mat, short data)
  {
    this(mat, 1, data);
  }
  
  public ItemBuilder addEnchantment(Enchantment enchant, int level)
  {
    if (this._enchants.containsKey(enchant)) {
      this._enchants.remove(enchant);
    }
    this._enchants.put(enchant, Integer.valueOf(level));
    return this;
  }
  
  public ItemBuilder addLore(String... lores)
  {
    String[] arrayOfString;
    int j = (arrayOfString = lores).length;
    for (int i = 0; i < j; i++)
    {
      String lore = arrayOfString[i];
      
      this._lore.add(ChatColor.GRAY + lore);
    }
    return this;
  }
  
  public ItemBuilder addLore(String lore, int maxLength)
  {
    this._lore.addAll(split(lore, maxLength));
    return this;
  }
  
  public ItemBuilder addLores(List<String> lores)
  {
    this._lore.addAll(lores);
    return this;
  }
  
  public ItemBuilder addLores(List<String> lores, int maxLength)
  {
    for (String lore : lores) {
      addLore(lore, maxLength);
    }
    return this;
  }
  
  public ItemBuilder addLores(String[] description, int maxLength)
  {
    return addLores(Arrays.asList(description), maxLength);
  }
  
  public ItemStack build()
  {
    Material mat = this._mat;
    if (mat == null)
    {
      mat = Material.AIR;
      Bukkit.getLogger().warning("Null material!");
    }
    else if (mat == Material.AIR)
    {
      Bukkit.getLogger().warning("Air material!");
    }
    ItemStack item = new ItemStack(mat, this._amount, this._data);
    ItemMeta meta = item.getItemMeta();
    if (meta != null)
    {
      if (this._title != null) {
        meta.setDisplayName(this._title);
      }
      if (!this._lore.isEmpty()) {
        meta.setLore(this._lore);
      }
      if ((meta instanceof LeatherArmorMeta)) {
        ((LeatherArmorMeta)meta).setColor(this._color);
      } else if (((meta instanceof SkullMeta)) && (this._playerHeadName != null)) {
        ((SkullMeta)meta).setOwner(this._playerHeadName);
      }
      meta.spigot().setUnbreakable(isUnbreakable());
      item.setItemMeta(meta);
    }
    item.addUnsafeEnchantments(this._enchants);
    
    return item;
  }
  
  public ItemBuilder clone()
  {
    ItemBuilder newBuilder = new ItemBuilder(this._mat);
    
    newBuilder.setTitle(this._title);
    for (String lore : this._lore) {
      newBuilder.addLore(new String[] { lore });
    }
    for (Map.Entry<Enchantment, Integer> entry : this._enchants.entrySet()) {
      newBuilder.addEnchantment((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
    }
    newBuilder.setColor(this._color);
    
    return newBuilder;
  }
  
  public HashMap<Enchantment, Integer> getAllEnchantments()
  {
    return this._enchants;
  }
  
  public Color getColor()
  {
    return this._color;
  }
  
  public short getData()
  {
    return this._data;
  }
  
  public int getEnchantmentLevel(Enchantment enchant)
  {
    return ((Integer)this._enchants.get(enchant)).intValue();
  }
  
  public List<String> getLore()
  {
    return this._lore;
  }
  
  public String getTitle()
  {
    return this._title;
  }
  
  public Material getType()
  {
    return this._mat;
  }
  
  public boolean hasEnchantment(Enchantment enchant)
  {
    return this._enchants.containsKey(enchant);
  }
  
  public boolean isItem(ItemStack item)
  {
    ItemMeta meta = item.getItemMeta();
    if (item.getType() != getType()) {
      return false;
    }
    if ((!meta.hasDisplayName()) && (getTitle() != null)) {
      return false;
    }
    if (!meta.getDisplayName().equals(getTitle())) {
      return false;
    }
    if ((!meta.hasLore()) && (!getLore().isEmpty())) {
      return false;
    }
    if (meta.hasLore()) {
      for (String lore : meta.getLore()) {
        if (!getLore().contains(lore)) {
          return false;
        }
      }
    }
    for (Enchantment enchant : item.getEnchantments().keySet()) {
      if (!hasEnchantment(enchant)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean isUnbreakable()
  {
    return this._unbreakable;
  }
  
  public ItemBuilder setAmount(int amount)
  {
    this._amount = amount;
    return this;
  }
  
  public ItemBuilder setColor(Color color)
  {
    if (!this._mat.name().contains("LEATHER_")) {
      throw new IllegalArgumentException("Can only dye leather armor!");
    }
    this._color = color;
    return this;
  }
  
  public void setData(short newData)
  {
    this._data = newData;
  }
  
  public ItemBuilder setPotion(Potion potion)
  {
    if (this._mat != Material.POTION) {
      this._mat = Material.POTION;
    }
    return this;
  }
  
  public ItemBuilder setRawTitle(String title)
  {
    this._title = title;
    return this;
  }
  
  public ItemBuilder setTitle(String title)
  {
    this._title = 
    
      (((title.length() > 2) && (ChatColor.getLastColors(title.substring(0, 2)).length() == 0) ? ChatColor.WHITE : title == null ? null : "") + title);
    return this;
  }
  
  public ItemBuilder setTitle(String title, int maxLength)
  {
    if ((title != null) && (ChatColor.stripColor(title).length() > maxLength))
    {
      ArrayList<String> lores = split(title, maxLength);
      for (int i = 1; i < lores.size(); i++) {
        this._lore.add((String)lores.get(i));
      }
      title = (String)lores.get(0);
    }
    setTitle(title);
    return this;
  }
  
  public ItemBuilder setType(Material mat)
  {
    this._mat = mat;
    return this;
  }
  
  public ItemBuilder setUnbreakable(boolean setUnbreakable)
  {
    this._unbreakable = setUnbreakable;return this;
  }
  
  public ItemBuilder setPlayerHead(String playerName)
  {
    this._playerHeadName = playerName;
    return this;
  }
}

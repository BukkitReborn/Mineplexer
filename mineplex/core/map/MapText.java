package mineplex.core.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import mineplex.core.common.util.UtilServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapText
{
  private static HashMap<Character, BufferedImage> _characters = new HashMap();
  
  private void loadCharacters()
  {
    try
    {
      InputStream inputStream = getClass().getResourceAsStream("ascii.png");
      BufferedImage image = ImageIO.read(inputStream);
      
      char[] text = 
        {
        ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', 
        '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 
        'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', 
        '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
        't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~' };
      
      int x = 0;
      int y = 16;
      char[] arrayOfChar1;
      int j = (arrayOfChar1 = text).length;
      for (int i = 0; i < j; i++)
      {
        char c = arrayOfChar1[i];
        
        grab(Character.valueOf(c), image, x, y);
        if (x < 120)
        {
          x += 8;
        }
        else
        {
          x = 0;
          y += 8;
        }
      }
      inputStream.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  private static void grab(Character character, BufferedImage image, int imageX, int imageY)
  {
    BufferedImage newImage = image.getSubimage(imageX, imageY, 8, 8);
    
    int width = character.charValue() == ' ' ? 4 : 0;
    if (width == 0) {
      for (int x = 0; x < 8; x++)
      {
        width++;
        boolean foundNonTrans = false;
        for (int y = 0; y < 8; y++)
        {
          int pixel = newImage.getRGB(x, y);
          if (pixel >> 24 != 0)
          {
            foundNonTrans = true;
            break;
          }
        }
        if (!foundNonTrans) {
          break;
        }
      }
    }
    newImage = newImage.getSubimage(0, 0, width, 8);
    
    _characters.put(character, newImage);
  }
  
  private ArrayList<String> split(String text)
  {
    ArrayList<String> returns = new ArrayList();
    int lineWidth = 0;
    String current = "";
    String[] arrayOfString;
    int j = (arrayOfString = text.split("(?<= )")).length;
    for (int i = 0; i < j; i++)
    {
      String word = arrayOfString[i];
      
      int length = 0;
      char[] arrayOfChar;
      int m = (arrayOfChar = word.toCharArray()).length;
      for (int k = 0; k < m; k++)
      {
        char c = arrayOfChar[k];
        
        length += ((BufferedImage)_characters.get(Character.valueOf(c))).getWidth();
      }
      if (lineWidth + length >= 127)
      {
        lineWidth = 0;
        returns.add(current);
        current = "";
      }
      current = current + word;
      lineWidth += length;
    }
    returns.add(current);
    
    return returns;
  }
  
  public ItemStack getMap(boolean sendToServer, String... text)
  {
    if (_characters.isEmpty()) {
      loadCharacters();
    }
    BufferedImage image = new BufferedImage(128, 128, 1);
    Graphics g = image.getGraphics();
    int height = 1;
    String[] arrayOfString;
    int j = (arrayOfString = text).length;
    Object localObject;
    String line;
    for (int i = 0; i < j; i++)
    {
      String string = arrayOfString[i];
      for (localObject = split(string).iterator(); ((Iterator)localObject).hasNext();)
      {
        line = (String)((Iterator)localObject).next();
        
        int length = 1;
        char[] arrayOfChar;
        int m = (arrayOfChar = line.toCharArray()).length;
        for (int k = 0; k < m; k++)
        {
          char c = arrayOfChar[k];
          
          BufferedImage img = (BufferedImage)_characters.get(Character.valueOf(c));
          if (img == null)
          {
            System.out.print("Error: '" + c + "' has no image associated");
          }
          else
          {
            g.drawImage(img, length, height, null);
            
            length += img.getWidth();
          }
        }
        height += 8;
      }
    }
    MapView map = Bukkit.createMap((World)Bukkit.getWorlds().get(0));
    for (MapRenderer r : map.getRenderers()) {
      map.removeRenderer(r);
    }
    map.addRenderer(new ImageMapRenderer(image));
    
    ItemStack item = new ItemStack(Material.MAP);
    
    item.setDurability(map.getId());
    if (sendToServer)
    {
      line = (localObject = UtilServer.getPlayers()).length;
      for (String str1 = 0; str1 < line; str1++)
      {
        Player player = localObject[str1];
        player.sendMap(map);
      }
    }
    return item;
  }
}

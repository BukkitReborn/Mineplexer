package mineplex.core.map;

import java.awt.image.BufferedImage;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ImageMapRenderer
  extends MapRenderer
{
  private BufferedImage _image;
  private boolean _first = true;
  
  public ImageMapRenderer(BufferedImage image)
  {
    this._image = image;
  }
  
  public void render(MapView view, MapCanvas canvas, Player player)
  {
    if ((this._image != null) && (this._first))
    {
      canvas.drawImage(0, 0, this._image);
      
      this._first = false;
    }
  }
}

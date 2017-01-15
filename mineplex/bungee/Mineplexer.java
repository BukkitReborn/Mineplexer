package mineplex.bungee;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import mineplex.bungee.lobbyBalancer.LobbyBalancer;
import mineplex.bungee.motd.MotdManager;
import mineplex.bungee.playerCount.PlayerCount;
import mineplex.bungee.playerStats.PlayerStats;
import mineplex.bungee.playerTracker.PlayerTracker;
import net.md_5.bungee.api.plugin.Plugin;

public class Mineplexer
  extends Plugin
{
  @Override
  public void onEnable()
  {
    new MotdManager(this);
    new LobbyBalancer(this);
    PlayerCount playerCount = new PlayerCount(this);
    new FileUpdater(this);
    new PlayerStats(this);
    
    new PlayerTracker(this);
  }
}

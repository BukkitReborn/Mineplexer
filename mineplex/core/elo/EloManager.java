package mineplex.core.elo;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import mineplex.core.MiniDbClientPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.NautHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class EloManager
  extends MiniDbClientPlugin<EloClientData>
{
  private static Object _playerEloLock = new Object();
  private EloRepository _repository;
  private EloRatingSystem _ratingSystem;
  private NautHashMap<String, NautHashMap<String, Integer>> _playerElos;
  
  public EloManager(JavaPlugin plugin, CoreClientManager clientManager)
  {
    super("Elo Rating", plugin, clientManager);
    
    this._repository = new EloRepository(plugin);
    this._ratingSystem = new EloRatingSystem(new KFactor[] { new KFactor(0, 1200, 25.0D), new KFactor(1201, 1600, 20.0D), new KFactor(1601, 2000, 15.0D), new KFactor(2001, 2500, 10.0D) });
    this._playerElos = new NautHashMap();
  }
  
  public int getElo(UUID uuid, String gameType)
  {
    int elo = 1000;
    synchronized (_playerEloLock)
    {
      if (this._playerElos.containsKey(uuid.toString())) {
        if (((NautHashMap)this._playerElos.get(uuid.toString())).containsKey(gameType)) {
          elo = ((Integer)((NautHashMap)this._playerElos.get(uuid.toString())).get(gameType)).intValue();
        }
      }
    }
    return elo;
  }
  
  public EloTeam getNewRatings(EloTeam teamA, EloTeam teamB, GameResult result)
  {
    EloTeam newTeam = new EloTeam();
    
    System.out.println("Old " + result + " Team Rating:" + teamA.TotalElo);
    
    int newTotal = this._ratingSystem.getNewRating(teamA.TotalElo / teamA.getPlayers().size(), teamB.TotalElo / teamB.getPlayers().size(), result) * teamA.getPlayers().size();
    
    System.out.println("New " + result + " Team Rating:" + newTotal);
    for (EloPlayer player : teamA.getPlayers())
    {
      EloPlayer newPlayer = new EloPlayer();
      newPlayer.UniqueId = player.UniqueId;
      newPlayer.Rating = ((int)(player.Rating + player.Rating / teamA.TotalElo * (newTotal - teamA.TotalElo)));
      
      System.out.println("Old:");
      player.printInfo();
      
      System.out.println("New:");
      newPlayer.printInfo();
      
      newTeam.addPlayer(newPlayer);
    }
    return newTeam;
  }
  
  public void saveElo(UUID uuid, String gameType, int elo)
  {
    saveElo(uuid.toString(), gameType, elo);
  }
  
  public void saveElo(final String uuid, final String gameType, final int elo)
  {
    Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        EloManager.this._repository.saveElo(uuid, gameType, elo);
        synchronized (EloManager._playerEloLock)
        {
          if (EloManager.this._playerElos.containsKey(uuid)) {
            if (((NautHashMap)EloManager.this._playerElos.get(uuid)).containsKey(gameType)) {
              ((NautHashMap)EloManager.this._playerElos.get(uuid)).put(gameType, Integer.valueOf(elo));
            }
          }
        }
      }
    });
  }
  
  protected EloClientData AddPlayer(String player)
  {
    return new EloClientData();
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    Set(playerName, this._repository.loadClientInformation(resultSet));
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return "SELECT gameType, elo FROM eloRating WHERE uuid = '" + uuid + "';";
  }
}

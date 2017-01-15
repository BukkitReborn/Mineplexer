package mineplex.core.elo;

import java.util.ArrayList;
import java.util.List;

public class EloTeam
{
  private List<EloPlayer> _players = new ArrayList();
  public int TotalElo = 0;
  
  public void addPlayer(EloPlayer player)
  {
    this.TotalElo += player.Rating;
    
    this._players.add(player);
  }
  
  public List<EloPlayer> getPlayers()
  {
    return this._players;
  }
}

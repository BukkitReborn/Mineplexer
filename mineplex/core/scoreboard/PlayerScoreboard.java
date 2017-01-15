package mineplex.core.scoreboard;

import java.io.PrintStream;
import java.util.ArrayList;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilMath;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerScoreboard
{
  private ScoreboardManager _manager;
  private String _scoreboardData = "default";
  private Scoreboard _scoreboard;
  private Objective _sideObjective;
  private ArrayList<String> _currentLines = new ArrayList();
  private String[] _teamNames;
  
  public PlayerScoreboard(ScoreboardManager manager, Player player)
  {
    this._manager = manager;
  }
  
  private void addTeams(Player player)
  {
    Rank[] arrayOfRank;
    int j = (arrayOfRank = Rank.values()).length;
    for (int i = 0; i < j; i++)
    {
      Rank rank = arrayOfRank[i];
      if (rank != Rank.ALL) {
        this._scoreboard.registerNewTeam(rank.Name).setPrefix(rank.GetTag(true, true) + ChatColor.RESET + " ");
      } else {
        this._scoreboard.registerNewTeam(rank.Name).setPrefix("");
      }
    }
    this._scoreboard.registerNewTeam("Party").setPrefix(ChatColor.LIGHT_PURPLE + C.Bold + "Party" + ChatColor.RESET + " ");
    for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
      if (this._manager.getClients().Get(otherPlayer) != null)
      {
        String rankName = this._manager.getClients().Get(player).GetRank().Name;
        String otherRankName = this._manager.getClients().Get(otherPlayer).GetRank().Name;
        if ((!this._manager.getClients().Get(player).GetRank().Has(Rank.ULTRA)) && (((Donor)this._manager.getDonation().Get(player.getName())).OwnsUltraPackage())) {
          rankName = Rank.ULTRA.Name;
        }
        if ((!this._manager.getClients().Get(otherPlayer).GetRank().Has(Rank.ULTRA)) && (((Donor)this._manager.getDonation().Get(otherPlayer.getName())).OwnsUltraPackage())) {
          otherRankName = Rank.ULTRA.Name;
        }
        this._scoreboard.getTeam(otherRankName).addPlayer(otherPlayer);
        
        otherPlayer.getScoreboard().getTeam(rankName).addPlayer(player);
      }
    }
  }
  
  private ScoreboardData getData()
  {
    ScoreboardData data = this._manager.getData(this._scoreboardData, false);
    if (data != null) {
      return data;
    }
    this._scoreboardData = "default";
    return this._manager.getData(this._scoreboardData, false);
  }
  
  public void draw(ScoreboardManager manager, Player player)
  {
    ScoreboardData data = getData();
    if (data == null) {
      return;
    }
    for (int i = 0; i < data.getLines(manager, player).size(); i++)
    {
      String newLine = (String)data.getLines(manager, player).get(i);
      if (this._currentLines.size() > i)
      {
        String oldLine = (String)this._currentLines.get(i);
        if (oldLine.equals(newLine)) {}
      }
      else
      {
        Team team = this._scoreboard.getTeam(this._teamNames[i]);
        if (team == null)
        {
          System.out.println("Scoreboard Error: Line Team Not Found!");
          return;
        }
        team.setPrefix(newLine.substring(0, Math.min(newLine.length(), 16)));
        team.setSuffix(ChatColor.getLastColors(newLine) + newLine.substring(team.getPrefix().length(), Math.min(newLine.length(), 32)));
        
        this._sideObjective.getScore(this._teamNames[i]).setScore(15 - i);
      }
    }
    if (this._currentLines.size() > data.getLines(manager, player).size()) {
      for (int i = data.getLines(manager, player).size(); i < this._currentLines.size(); i++) {
        this._scoreboard.resetScores(this._teamNames[i]);
      }
    }
    this._currentLines = data.getLines(manager, player);
  }
  
  public void setTitle(String out)
  {
    this._sideObjective.setDisplayName(out);
  }
  
  public void assignScoreboard(Player player, ScoreboardData data)
  {
    this._scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    
    this._sideObjective = this._scoreboard.registerNewObjective(player.getName() + UtilMath.r(999999999), "dummy");
    this._sideObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
    this._sideObjective.setDisplayName(C.Bold + "   " + ScoreboardManager._mineplexName + "   ");
    
    addTeams(player);
    
    this._teamNames = new String[16];
    for (int i = 0; i < 16; i++)
    {
      String teamName = "§" + "1234567890abcdefghijklmnopqrstuvwxyz".toCharArray()[i] + ChatColor.RESET;
      
      this._teamNames[i] = teamName;
      
      Team team = this._scoreboard.registerNewTeam(teamName);
      team.addEntry(teamName);
    }
    player.setScoreboard(this._scoreboard);
  }
}

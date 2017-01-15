package mineplex.core.friend.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.database.DBPool;
import mineplex.core.database.RepositoryBase;
import mineplex.core.database.ResultSetCallable;
import mineplex.core.database.column.Column;
import mineplex.core.database.column.ColumnVarChar;
import mineplex.core.friend.FriendStatusType;
import mineplex.serverdata.Region;
import mineplex.serverdata.data.DataRepository;
import mineplex.serverdata.data.PlayerStatus;
import mineplex.serverdata.redis.RedisDataRepository;
import mineplex.serverdata.servers.ServerManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FriendRepository
  extends RepositoryBase
{
  private static String CREATE_FRIEND_TABLE = "CREATE TABLE IF NOT EXISTS accountFriend (id INT NOT NULL AUTO_INCREMENT, uuidSource VARCHAR(100), uuidTarget VARCHAR(100), status VARCHAR(100), PRIMARY KEY (id), UNIQUE INDEX uuidIndex (uuidSource, uuidTarget));";
  private static String RETRIEVE_MULTIPLE_FRIEND_RECORDS = "SELECT uuidSource, tA.Name, status, tA.lastLogin, now() FROM accountFriend INNER Join accounts AS fA ON fA.uuid = uuidSource INNER JOIN accounts AS tA ON tA.uuid = uuidTarget WHERE uuidSource IN ";
  private static String ADD_FRIEND_RECORD = "INSERT INTO accountFriend (uuidSource, uuidTarget, status, created) SELECT fA.uuid AS uuidSource, tA.uuid AS uuidTarget, ?, now() FROM accounts as fA LEFT JOIN accounts AS tA ON tA.name = ? WHERE fA.name = ?;";
  private static String UPDATE_MUTUAL_RECORD = "UPDATE accountFriend AS aF INNER JOIN accounts as fA ON aF.uuidSource = fA.uuid INNER JOIN accounts AS tA ON aF.uuidTarget = tA.uuid SET aF.status = ? WHERE tA.name = ? AND fA.name = ?;";
  private static String DELETE_FRIEND_RECORD = "DELETE aF FROM accountFriend AS aF INNER JOIN accounts as fA ON aF.uuidSource = fA.uuid INNER JOIN accounts AS tA ON aF.uuidTarget = tA.uuid WHERE fA.name = ? AND tA.name = ?;";
  private DataRepository<PlayerStatus> _repository;
  
  public FriendRepository(JavaPlugin plugin)
  {
    super(plugin, DBPool.ACCOUNT);
    
    this._repository = new RedisDataRepository(ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), 
      Region.currentRegion(), PlayerStatus.class, "playerStatus");
  }
  
  protected void initialize()
  {
    executeUpdate(CREATE_FRIEND_TABLE, new Column[0]);
  }
  
  protected void update() {}
  
  public boolean addFriend(Player caller, String name)
  {
    int rowsAffected = executeUpdate(ADD_FRIEND_RECORD, new Column[] { new ColumnVarChar("status", 100, "Sent"), new ColumnVarChar("name", 100, name), new ColumnVarChar("name", 100, caller.getName()) });
    if (rowsAffected > 0) {
      return executeUpdate(ADD_FRIEND_RECORD, new Column[] { new ColumnVarChar("status", 100, "Pending"), new ColumnVarChar("name", 100, caller.getName()), new ColumnVarChar("uuid", 100, name) }) > 0;
    }
    return false;
  }
  
  public boolean updateFriend(String caller, String name, String status)
  {
    return executeUpdate(UPDATE_MUTUAL_RECORD, new Column[] { new ColumnVarChar("status", 100, status), new ColumnVarChar("uuid", 100, name), new ColumnVarChar("name", 100, caller) }) > 0;
  }
  
  public boolean removeFriend(String caller, String name)
  {
    int rowsAffected = executeUpdate(DELETE_FRIEND_RECORD, new Column[] { new ColumnVarChar("name", 100, name), new ColumnVarChar("name", 100, caller) });
    if (rowsAffected > 0) {
      return executeUpdate(DELETE_FRIEND_RECORD, new Column[] { new ColumnVarChar("name", 100, caller), new ColumnVarChar("uuid", 100, name) }) > 0;
    }
    return false;
  }
  
  public NautHashMap<String, FriendData> getFriendsForAll(Player... players)
  {
    final NautHashMap<String, FriendData> friends = new NautHashMap();
    
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(RETRIEVE_MULTIPLE_FRIEND_RECORDS + "(");
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = players).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      
      stringBuilder.append("'" + player.getUniqueId() + "', ");
    }
    stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    stringBuilder.append(");");
    
    executeQuery(stringBuilder.toString(), new ResultSetCallable()
    {
      public void processResultSet(ResultSet resultSet)
        throws SQLException
      {
        Set<FriendData> friendDatas = new HashSet();
        String uuidSource;
        while (resultSet.next())
        {
          FriendStatus friend = new FriendStatus();
          
          uuidSource = resultSet.getString(1);
          friend.Name = resultSet.getString(2);
          friend.Status = ((FriendStatusType)Enum.valueOf(FriendStatusType.class, resultSet.getString(3)));
          friend.LastSeenOnline = (resultSet.getTimestamp(5).getTime() - resultSet.getTimestamp(4).getTime());
          if (!friends.containsKey(uuidSource)) {
            friends.put(uuidSource, new FriendData());
          }
          ((FriendData)friends.get(uuidSource)).getFriends().add(friend);
          
          friendDatas.add((FriendData)friends.get(uuidSource));
        }
        for (FriendData friendData : friendDatas) {
          FriendRepository.this.loadFriendStatuses(friendData);
        }
      }
    }, new Column[0]);
    
    return friends;
  }
  
  public FriendData loadClientInformation(ResultSet resultSet)
    throws SQLException
  {
    FriendData friendData = new FriendData();
    while (resultSet.next())
    {
      FriendStatus friend = new FriendStatus();
      
      friend.Name = resultSet.getString(1);
      friend.Status = ((FriendStatusType)Enum.valueOf(FriendStatusType.class, resultSet.getString(2)));
      friend.LastSeenOnline = (resultSet.getTimestamp(4).getTime() - resultSet.getTimestamp(3).getTime());
      friend.ServerName = null;
      friend.Online = (friend.ServerName != null);
      friendData.getFriends().add(friend);
    }
    loadFriendStatuses(friendData);
    
    return friendData;
  }
  
  public void loadFriendStatuses(FriendData friendData)
  {
    Set<String> friendNames = new HashSet();
    for (FriendStatus status : friendData.getFriends()) {
      friendNames.add(status.Name);
    }
    Collection<PlayerStatus> statuses = this._repository.getElements(friendNames);
    
    Object playerStatuses = new HashMap();
    for (PlayerStatus status : statuses) {
      ((Map)playerStatuses).put(status.getName(), status);
    }
    for (FriendStatus friend : friendData.getFriends())
    {
      PlayerStatus status = (PlayerStatus)((Map)playerStatuses).get(friend.Name);
      friend.Online = (status != null);
      friend.ServerName = (friend.Online ? status.getServer() : null);
    }
  }
  
  public String fetchPlayerServer(String playerName)
  {
    PlayerStatus status = (PlayerStatus)this._repository.getElement(playerName);
    
    return status == null ? null : status.getServer();
  }
}

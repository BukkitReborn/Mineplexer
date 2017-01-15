package mineplex.core.stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.database.DBPool;
import mineplex.core.database.RepositoryBase;
import mineplex.core.database.ResultSetCallable;
import mineplex.core.database.column.Column;
import mineplex.core.database.column.ColumnVarChar;
import mineplex.database.Tables;
import mineplex.database.tables.AccountStat;
import mineplex.database.tables.Accounts;
import mineplex.database.tables.Stats;
import org.bukkit.plugin.java.JavaPlugin;
import org.jooq.Batch;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Insert;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectOnStep;
import org.jooq.SelectSelectStep;
import org.jooq.TableField;
import org.jooq.Update;
import org.jooq.UpdateConditionStep;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;

public class StatsRepository
  extends RepositoryBase
{
  private static String CREATE_STAT_TABLE = "CREATE TABLE IF NOT EXISTS stats (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100), PRIMARY KEY (id), UNIQUE INDEX nameIndex (name));";
  private static String CREATE_STAT_RELATION_TABLE = "CREATE TABLE IF NOT EXISTS accountStats (id INT NOT NULL AUTO_INCREMENT, accountId INT NOT NULL, statId INT NOT NULL, value INT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (accountId) REFERENCES accounts(id), FOREIGN KEY (statId) REFERENCES stats(id), UNIQUE INDEX accountStatIndex (accountId, statId));";
  private static String RETRIEVE_STATS = "SELECT id, name FROM stats;";
  private static String INSERT_STAT = "INSERT INTO stats (name) VALUES (?);";
  
  public StatsRepository(JavaPlugin plugin)
  {
    super(plugin, DBPool.ACCOUNT);
  }
  
  protected void initialize() {}
  
  protected void update() {}
  
  public List<Stat> retrieveStats()
  {
    final List<Stat> stats = new ArrayList();
    
    executeQuery(RETRIEVE_STATS, new ResultSetCallable()
    {
      public void processResultSet(ResultSet resultSet)
        throws SQLException
      {
        while (resultSet.next())
        {
          Stat stat = new Stat();
          
          stat.Id = resultSet.getInt(1);
          stat.Name = resultSet.getString(2);
          
          stats.add(stat);
        }
      }
    }, new Column[0]);
    
    return stats;
  }
  
  public void addStat(String name)
  {
    executeUpdate(INSERT_STAT, new Column[] { new ColumnVarChar("name", 100, name) });
  }
  
  public void saveStats(NautHashMap<Integer, NautHashMap<Integer, Long>> uploadQueue)
  {
    try
    {
      DSLContext context = DSL.using(getConnectionPool(), SQLDialect.MYSQL);
      
      List<Update> updates = new ArrayList();
      List<Insert> inserts = new ArrayList();
      Iterator localIterator2;
      for (Iterator localIterator1 = uploadQueue.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
      {
        int accountId = ((Integer)localIterator1.next()).intValue();
        
        localIterator2 = ((NautHashMap)uploadQueue.get(Integer.valueOf(accountId))).keySet().iterator(); continue;Integer statId = (Integer)localIterator2.next();
        
        Update update = context
          .update(Tables.accountStat)
          .set(Tables.accountStat.value, Tables.accountStat.value.plus((Number)((NautHashMap)uploadQueue.get(Integer.valueOf(accountId))).get(statId)))
          .where(new Condition[] {Tables.accountStat.accountId.eq(Integer.valueOf(accountId)) })
          .and(Tables.accountStat.statId.eq(statId));
        
        updates.add(update);
        
        Insert insert = context
          .insertInto(Tables.accountStat)
          .set(Tables.accountStat.accountId, Integer.valueOf(accountId))
          .set(Tables.accountStat.statId, statId)
          .set(Tables.accountStat.value, (Long)((NautHashMap)uploadQueue.get(Integer.valueOf(accountId))).get(statId));
        
        inserts.add(insert);
      }
      int[] updateResult = context.batch(updates).execute();
      for (int i = 0; i < updateResult.length; i++) {
        if (updateResult[i] > 0) {
          inserts.set(i, null);
        }
      }
      inserts.removeAll(Collections.singleton(null));
      
      context.batch(inserts).execute();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public PlayerStats loadOfflinePlayerStats(String playerName)
  {
    PlayerStats playerStats = null;
    DSLContext context;
    synchronized (this)
    {
      context = DSL.using(getConnectionPool(), SQLDialect.MYSQL);
    }
    DSLContext context;
    Result<Record2<String, Long>> result = context.select(Tables.stats.name, Tables.accountStat.value).from(Tables.accountStat)
      .join(Tables.stats)
      .on(new Condition[] {Tables.stats.id.eq(Tables.accountStat.statId) })
      .where(new Condition[] {Tables.accountStat.accountId.eq(DSL.select(Tables.accounts.id)
      .from(Tables.accounts)
      .where(new Condition[] {Tables.accounts.name.eq(playerName) })) })
      .fetch();
    if (result.isNotEmpty())
    {
      playerStats = new PlayerStats();
      for (Record2<String, Long> record : result) {
        playerStats.addStat((String)record.value1(), ((Long)record.value2()).longValue());
      }
    }
    return playerStats;
  }
  
  public PlayerStats loadClientInformation(ResultSet resultSet)
    throws SQLException
  {
    PlayerStats playerStats = new PlayerStats();
    while (resultSet.next()) {
      playerStats.addStat(resultSet.getString(1), resultSet.getInt(2));
    }
    return playerStats;
  }
}

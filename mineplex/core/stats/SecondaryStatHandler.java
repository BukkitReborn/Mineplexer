package mineplex.core.stats;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import mineplex.core.account.ILoginProcessor;
import mineplex.core.database.DBPool;
import mineplex.database.Tables;
import mineplex.database.tables.AccountStat;
import org.jooq.Batch;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Insert;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.TransactionalRunnable;
import org.jooq.Update;
import org.jooq.UpdateConditionStep;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;

public class SecondaryStatHandler
  implements ILoginProcessor
{
  private StatsManager _statsManager;
  private StatsRepository _repository;
  
  public SecondaryStatHandler(StatsManager statsManager, StatsRepository repository)
  {
    this._statsManager = statsManager;
    this._repository = repository;
  }
  
  public String getName()
  {
    return "Secondary Stat Handler";
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    PlayerStats oldPlayerStats = (PlayerStats)this._statsManager.Get(playerName);
    PlayerStats newPlayerStats = this._repository.loadClientInformation(resultSet);
    if ((newPlayerStats.getStatsNames().size() == 0) && (oldPlayerStats.getStatsNames().size() != 0))
    {
      try
      {
        DSLContext context = DSL.using(DBPool.ACCOUNT, SQLDialect.MYSQL);
        
        final List<Insert> inserts = new ArrayList();
        for (String statName : oldPlayerStats.getStatsNames())
        {
          Integer statId = Integer.valueOf(this._statsManager.getStatId(statName));
          
          Insert insert = context
            .insertInto(Tables.accountStat)
            .set(Tables.accountStat.accountId, Integer.valueOf(accountId))
            .set(Tables.accountStat.statId, statId)
            .set(Tables.accountStat.value, Long.valueOf(Math.max(oldPlayerStats.getStat(statName), 0L)));
          
          inserts.add(insert);
        }
        context.transaction(new TransactionalRunnable()
        {
          public void run(Configuration config)
            throws Exception
          {
            DSL.using(config).batch(inserts).execute();
          }
        });
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      this._statsManager.replacePlayerHack(playerName, newPlayerStats);
      if (oldPlayerStats.getStatsNames().size() != 0) {
        try
        {
          final DSLContext context = DSL.using(DBPool.ACCOUNT, SQLDialect.MYSQL);
          final List<Update> updates = new ArrayList();
          final List<Insert> inserts = new ArrayList();
          boolean foundNegativeStat = false;
          boolean foundLessThanStat = false;
          for (String statName : oldPlayerStats.getStatsNames())
          {
            Integer statId = Integer.valueOf(this._statsManager.getStatId(statName));
            
            Insert insert = context
              .insertInto(Tables.accountStat)
              .set(Tables.accountStat.accountId, Integer.valueOf(accountId))
              .set(Tables.accountStat.statId, statId)
              .set(Tables.accountStat.value, Long.valueOf(Math.max(oldPlayerStats.getStat(statName), 0L)));
            
            inserts.add(insert);
            
            Update update = context
              .update(Tables.accountStat)
              .set(Tables.accountStat.value, Long.valueOf(Math.max(oldPlayerStats.getStat(statName), 0L)))
              .where(new Condition[] {Tables.accountStat.accountId.eq(Integer.valueOf(accountId)) })
              .and(Tables.accountStat.statId.eq(statId));
            
            updates.add(update);
            if (oldPlayerStats.getStat(statName) < 0L) {
              foundNegativeStat = true;
            } else if (newPlayerStats.getStat(statName) < oldPlayerStats.getStat(statName)) {
              foundLessThanStat = true;
            }
          }
          if ((foundNegativeStat) && (foundLessThanStat)) {
            context.transaction(new TransactionalRunnable()
            {
              public void run(Configuration config)
                throws Exception
              {
                int[] updateResult = context.batch(updates).execute();
                for (int i = 0; i < updateResult.length; i++) {
                  if (updateResult[i] > 0) {
                    inserts.set(i, null);
                  }
                }
                inserts.removeAll(Collections.singleton(null));
                
                context.batch(inserts).execute();
                
                System.out.println("Updating");
              }
            });
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return "SELECT stats.name, value FROM accountStat INNER JOIN stats ON stats.id = accountStat.statId WHERE accountStat.accountId = '" + accountId + "';";
  }
}

package org.jooq.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.AttachableInternal;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListenerProvider;
import org.jooq.Query;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DataAccessException;

class BatchCRUD
  implements Batch
{
  private static final long serialVersionUID = -2935544935267715011L;
  private final DSLContext create;
  private final Configuration configuration;
  private final TableRecord<?>[] records;
  private final Action action;
  
  BatchCRUD(Configuration configuration, Action action, TableRecord<?>[] records)
  {
    this.create = DSL.using(configuration);
    this.configuration = configuration;
    this.action = action;
    this.records = records;
  }
  
  public final int size()
  {
    return this.records.length;
  }
  
  public final int[] execute()
    throws DataAccessException
  {
    if (SettingsTools.executeStaticStatements(this.configuration.settings())) {
      return executeStatic();
    }
    return executePrepared();
  }
  
  private final int[] executePrepared()
  {
    Map<String, List<Query>> queries = new LinkedHashMap();
    QueryCollector collector = new QueryCollector(null);
    
    Configuration local = this.configuration.derive((ExecuteListenerProvider[])Utils.combine(this.configuration
      .executeListenerProviders(), new DefaultExecuteListenerProvider(collector)));
    
    local.data("org.jooq.configuration.omit-returning-clause", Boolean.valueOf(true));
    
    local.settings().setExecuteLogging(Boolean.valueOf(false));
    Configuration previous;
    String sql;
    for (int i = 0; i < this.records.length; i++)
    {
      previous = ((AttachableInternal)this.records[i]).configuration();
      try
      {
        this.records[i].attach(local);
        executeAction(i);
      }
      catch (QueryCollectorSignal e)
      {
        Query query = e.getQuery();
        sql = e.getSQL();
        if (query.isExecutable())
        {
          List<Query> list = (List)queries.get(sql);
          if (list == null)
          {
            list = new ArrayList();
            queries.put(sql, list);
          }
          list.add(query);
        }
      }
      finally
      {
        this.records[i].attach(previous);
      }
    }
    List<Integer> result = new ArrayList();
    for (Map.Entry<String, List<Query>> entry : queries.entrySet())
    {
      BatchBindStep batch = this.create.batch((Query)((List)entry.getValue()).get(0));
      for (sql = ((List)entry.getValue()).iterator(); sql.hasNext();)
      {
        query = (Query)sql.next();
        batch.bind(query.getBindValues().toArray());
      }
      Query query;
      int[] array = batch.execute();
      for (int i : array) {
        result.add(Integer.valueOf(i));
      }
    }
    int[] array = new int[result.size()];
    for (int i = 0; i < result.size(); i++) {
      array[i] = ((Integer)result.get(i)).intValue();
    }
    updateChangedFlag();
    return array;
  }
  
  private final int[] executeStatic()
  {
    List<Query> queries = new ArrayList();
    QueryCollector collector = new QueryCollector(null);
    
    Configuration local = this.configuration.derive((ExecuteListenerProvider[])Utils.combine(this.configuration
      .executeListenerProviders(), new DefaultExecuteListenerProvider(collector)));
    for (int i = 0; i < this.records.length; i++)
    {
      Configuration previous = ((AttachableInternal)this.records[i]).configuration();
      try
      {
        this.records[i].attach(local);
        executeAction(i);
      }
      catch (QueryCollectorSignal e)
      {
        Query query = e.getQuery();
        if (query.isExecutable()) {
          queries.add(query);
        }
      }
      finally
      {
        this.records[i].attach(previous);
      }
    }
    int[] result = this.create.batch(queries).execute();
    updateChangedFlag();
    return result;
  }
  
  private void executeAction(int i)
  {
    switch (this.action)
    {
    case STORE: 
      ((UpdatableRecord)this.records[i]).store();
      break;
    case INSERT: 
      this.records[i].insert();
      break;
    case UPDATE: 
      ((UpdatableRecord)this.records[i]).update();
      break;
    case DELETE: 
      ((UpdatableRecord)this.records[i]).delete();
    }
  }
  
  private final void updateChangedFlag()
  {
    for (TableRecord<?> record : this.records)
    {
      record.changed(this.action == Action.DELETE);
      if ((record instanceof AbstractRecord)) {
        ((AbstractRecord)record).fetched = (this.action != Action.DELETE);
      }
    }
  }
  
  static enum Action
  {
    STORE,  INSERT,  UPDATE,  DELETE;
    
    private Action() {}
  }
  
  private static class QueryCollector
    extends DefaultExecuteListener
  {
    private static final long serialVersionUID = 7399239846062763212L;
    
    public void renderEnd(ExecuteContext ctx)
    {
      throw new BatchCRUD.QueryCollectorSignal(ctx.sql(), ctx.query());
    }
  }
  
  private static class QueryCollectorSignal
    extends ControlFlowSignal
  {
    private static final long serialVersionUID = -9047250761846931903L;
    private final String sql;
    private final Query query;
    
    QueryCollectorSignal(String sql, Query query)
    {
      this.sql = sql;
      this.query = query;
    }
    
    String getSQL()
    {
      return this.sql;
    }
    
    Query getQuery()
    {
      return this.query;
    }
  }
}

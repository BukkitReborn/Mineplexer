package org.jooq.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Query;
import org.jooq.conf.ParamType;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;

class BatchSingle
  implements BatchBindStep
{
  private static final long serialVersionUID = 3793967258181493207L;
  private final DSLContext create;
  private final Configuration configuration;
  private final Query query;
  private final List<Object[]> allBindValues;
  
  public BatchSingle(Configuration configuration, Query query)
  {
    this.create = DSL.using(configuration);
    this.configuration = configuration;
    this.query = query;
    this.allBindValues = new ArrayList();
  }
  
  public final BatchSingle bind(Object... bindValues)
  {
    this.allBindValues.add(bindValues);
    return this;
  }
  
  public final BatchSingle bind(Object[][] bindValues)
  {
    for (Object[] v : bindValues) {
      bind(v);
    }
    return this;
  }
  
  public final int size()
  {
    return this.allBindValues.size();
  }
  
  public final int[] execute()
  {
    if (SettingsTools.executeStaticStatements(this.configuration.settings())) {
      return executeStatic();
    }
    return executePrepared();
  }
  
  private final int[] executePrepared()
  {
    ExecuteContext ctx = new DefaultExecuteContext(this.configuration, new Query[] { this.query });
    ExecuteListener listener = new ExecuteListeners(ctx);
    Connection connection = ctx.connection();
    
    DataType<?>[] paramTypes = Utils.dataTypes((Field[])this.query.getParams().values().toArray(new Field[0]));
    try
    {
      listener.renderStart(ctx);
      
      ctx.sql(this.create.render(this.query));
      listener.renderEnd(ctx);
      
      listener.prepareStart(ctx);
      ctx.statement(connection.prepareStatement(ctx.sql()));
      listener.prepareEnd(ctx);
      for (Object[] bindValues : this.allBindValues)
      {
        listener.bindStart(ctx);
        
        List<Field<?>> params = paramTypes.length > 0 ? Utils.fields(bindValues, paramTypes) : Utils.fields(bindValues);
        
        Utils.visitAll(new DefaultBindContext(this.configuration, ctx.statement()), params);
        
        listener.bindEnd(ctx);
        ctx.statement().addBatch();
      }
      listener.executeStart(ctx);
      int[] result = ctx.statement().executeBatch();
      
      int[] batchRows = ctx.batchRows();
      for (int i = 0; (i < batchRows.length) && (i < result.length); i++) {
        batchRows[i] = result[i];
      }
      listener.executeEnd(ctx);
      return result;
    }
    catch (ControlFlowSignal e)
    {
      throw e;
    }
    catch (RuntimeException e)
    {
      ctx.exception(e);
      listener.exception(ctx);
      throw ctx.exception();
    }
    catch (SQLException e)
    {
      ctx.sqlException(e);
      listener.exception(ctx);
      throw ctx.exception();
    }
    finally
    {
      Utils.safeClose(listener, ctx);
    }
  }
  
  private final int[] executeStatic()
  {
    List<Query> queries = new ArrayList();
    for (Object[] bindValues : this.allBindValues)
    {
      for (int i = 0; i < bindValues.length; i++) {
        this.query.bind(i + 1, bindValues[i]);
      }
      queries.add(this.create.query(this.query.getSQL(ParamType.INLINED)));
    }
    return this.create.batch(queries).execute();
  }
}

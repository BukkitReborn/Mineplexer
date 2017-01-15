package org.jooq.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jooq.Batch;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Query;
import org.jooq.exception.ControlFlowSignal;

class BatchMultiple
  implements Batch
{
  private static final long serialVersionUID = -7337667281292354043L;
  private final Configuration configuration;
  private final Query[] queries;
  
  public BatchMultiple(Configuration configuration, Query... queries)
  {
    this.configuration = configuration;
    this.queries = queries;
  }
  
  public final int size()
  {
    return this.queries.length;
  }
  
  public final int[] execute()
  {
    ExecuteContext ctx = new DefaultExecuteContext(this.configuration, this.queries);
    ExecuteListener listener = new ExecuteListeners(ctx);
    Connection connection = ctx.connection();
    try
    {
      ctx.statement(new SettingsEnabledPreparedStatement(connection));
      
      String[] batchSQL = ctx.batchSQL();
      for (int i = 0; i < this.queries.length; i++)
      {
        listener.renderStart(ctx);
        batchSQL[i] = DSL.using(this.configuration).renderInlined(this.queries[i]);
        listener.renderEnd(ctx);
      }
      for (String sql : batchSQL)
      {
        ctx.sql(sql);
        listener.prepareStart(ctx);
        ctx.statement().addBatch(sql);
        listener.prepareEnd(ctx);
        ctx.sql(null);
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
}

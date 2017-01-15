package org.jooq.tools;

import java.util.Arrays;
import java.util.logging.Level;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.VisitContext;
import org.jooq.VisitListenerProvider;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.DefaultVisitListener;
import org.jooq.impl.DefaultVisitListenerProvider;

public class LoggerListener
  extends DefaultExecuteListener
{
  private static final long serialVersionUID = 7399239846062763212L;
  private static final JooqLogger log = JooqLogger.getLogger(LoggerListener.class);
  private static final int maxLength = 2000;
  
  public void renderEnd(ExecuteContext ctx)
  {
    if (log.isDebugEnabled())
    {
      Configuration configuration = ctx.configuration();
      if (!log.isTraceEnabled()) {
        configuration = abbreviateBindVariables(configuration);
      }
      String[] batchSQL = ctx.batchSQL();
      if (ctx.query() != null)
      {
        log.debug("Executing query", ctx.sql());
        
        String inlined = DSL.using(configuration).renderInlined(ctx.query());
        if (!ctx.sql().equals(inlined)) {
          log.debug("-> with bind values", inlined);
        }
      }
      else
      {
        String inlined;
        if (ctx.routine() != null)
        {
          log.debug("Calling routine", ctx.sql());
          
          inlined = DSL.using(configuration).renderInlined(ctx.routine());
          if (!ctx.sql().equals(inlined)) {
            log.debug("-> with bind values", inlined);
          }
        }
        else if (!StringUtils.isBlank(ctx.sql()))
        {
          if (ctx.type() == ExecuteType.BATCH) {
            log.debug("Executing batch query", ctx.sql());
          } else {
            log.debug("Executing query", ctx.sql());
          }
        }
        else if ((batchSQL.length > 0) && 
          (batchSQL[(batchSQL.length - 1)] != null))
        {
          for (String sql : batchSQL) {
            log.debug("Executing batch query", sql);
          }
        }
      }
    }
  }
  
  public void recordEnd(ExecuteContext ctx)
  {
    if ((log.isTraceEnabled()) && (ctx.record() != null)) {
      logMultiline("Record fetched", ctx.record().toString(), Level.FINER);
    }
  }
  
  public void resultEnd(ExecuteContext ctx)
  {
    if (ctx.result() != null) {
      if (log.isTraceEnabled()) {
        logMultiline("Fetched result", ctx.result().format(500), Level.FINE);
      } else if (log.isDebugEnabled()) {
        logMultiline("Fetched result", ctx.result().format(5), Level.FINE);
      }
    }
  }
  
  public void executeEnd(ExecuteContext ctx)
  {
    if ((log.isDebugEnabled()) && (ctx.rows() >= 0)) {
      log.debug("Affected row(s)", Integer.valueOf(ctx.rows()));
    }
  }
  
  private void logMultiline(String comment, String message, Level level)
  {
    for (String line : message.split("\n"))
    {
      if (level == Level.FINE) {
        log.debug(comment, line);
      } else {
        log.trace(comment, line);
      }
      comment = "";
    }
  }
  
  private final Configuration abbreviateBindVariables(Configuration configuration)
  {
    VisitListenerProvider[] oldProviders = configuration.visitListenerProviders();
    VisitListenerProvider[] newProviders = new VisitListenerProvider[oldProviders.length + 1];
    System.arraycopy(oldProviders, 0, newProviders, 0, oldProviders.length);
    newProviders[(newProviders.length - 1)] = new DefaultVisitListenerProvider(new BindValueAbbreviator(null));
    
    return configuration.derive(newProviders);
  }
  
  private static class BindValueAbbreviator
    extends DefaultVisitListener
  {
    private boolean anyAbbreviations = false;
    
    public void visitStart(VisitContext context)
    {
      if (context.renderContext() != null)
      {
        QueryPart part = context.queryPart();
        if ((part instanceof Param))
        {
          Param<?> param = (Param)part;
          Object value = param.getValue();
          if (((value instanceof String)) && (((String)value).length() > 2000))
          {
            this.anyAbbreviations = true;
            context.queryPart(DSL.val(StringUtils.abbreviate((String)value, 2000)));
          }
          else if (((value instanceof byte[])) && (((byte[])value).length > 2000))
          {
            this.anyAbbreviations = true;
            context.queryPart(DSL.val(Arrays.copyOf((byte[])value, 2000)));
          }
        }
      }
    }
    
    public void visitEnd(VisitContext context)
    {
      if ((this.anyAbbreviations) && 
        (context.queryPartsLength() == 1)) {
        context.renderContext().sql(" -- Bind values may have been abbreviated for DEBUG logging. Use TRACE logging for very large bind variables.");
      }
    }
  }
}

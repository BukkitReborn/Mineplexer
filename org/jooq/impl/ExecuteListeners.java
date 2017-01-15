package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.conf.Settings;
import org.jooq.tools.LoggerListener;
import org.jooq.tools.StopWatchListener;

class ExecuteListeners
  implements ExecuteListener
{
  private static final long serialVersionUID = 7399239846062763212L;
  private final ExecuteListener[] listeners;
  private boolean resultStart;
  private boolean fetchEnd;
  
  ExecuteListeners(ExecuteContext ctx)
  {
    this.listeners = listeners(ctx);
    
    start(ctx);
  }
  
  private static ExecuteListener[] listeners(ExecuteContext ctx)
  {
    List<ExecuteListener> result = new ArrayList();
    if (!Boolean.FALSE.equals(ctx.configuration().settings().isExecuteLogging()))
    {
      result.add(new LoggerListener());
      result.add(new StopWatchListener());
    }
    for (ExecuteListenerProvider provider : ctx.configuration().executeListenerProviders()) {
      if (provider != null) {
        result.add(provider.provide());
      }
    }
    return (ExecuteListener[])result.toArray(new ExecuteListener[result.size()]);
  }
  
  public final void start(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.start(ctx);
    }
  }
  
  public final void renderStart(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.renderStart(ctx);
    }
  }
  
  public final void renderEnd(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.renderEnd(ctx);
    }
  }
  
  public final void prepareStart(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.prepareStart(ctx);
    }
  }
  
  public final void prepareEnd(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.prepareEnd(ctx);
    }
  }
  
  public final void bindStart(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.bindStart(ctx);
    }
  }
  
  public final void bindEnd(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.bindEnd(ctx);
    }
  }
  
  public final void executeStart(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.executeStart(ctx);
    }
  }
  
  public final void executeEnd(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.executeEnd(ctx);
    }
  }
  
  public final void fetchStart(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.fetchStart(ctx);
    }
  }
  
  public final void resultStart(ExecuteContext ctx)
  {
    this.resultStart = true;
    for (ExecuteListener listener : this.listeners) {
      listener.resultStart(ctx);
    }
  }
  
  public final void recordStart(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.recordStart(ctx);
    }
  }
  
  public final void recordEnd(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.recordEnd(ctx);
    }
  }
  
  public final void resultEnd(ExecuteContext ctx)
  {
    this.resultStart = false;
    for (ExecuteListener listener : this.listeners) {
      listener.resultEnd(ctx);
    }
    if (this.fetchEnd) {
      fetchEnd(ctx);
    }
  }
  
  public final void fetchEnd(ExecuteContext ctx)
  {
    if (this.resultStart) {
      this.fetchEnd = true;
    } else {
      for (ExecuteListener listener : this.listeners) {
        listener.fetchEnd(ctx);
      }
    }
  }
  
  public final void end(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.end(ctx);
    }
  }
  
  public final void exception(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.exception(ctx);
    }
  }
  
  public final void warning(ExecuteContext ctx)
  {
    for (ExecuteListener listener : this.listeners) {
      listener.warning(ctx);
    }
  }
}

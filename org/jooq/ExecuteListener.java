package org.jooq;

import java.io.Serializable;
import java.util.EventListener;

public abstract interface ExecuteListener
  extends EventListener, Serializable
{
  public abstract void start(ExecuteContext paramExecuteContext);
  
  public abstract void renderStart(ExecuteContext paramExecuteContext);
  
  public abstract void renderEnd(ExecuteContext paramExecuteContext);
  
  public abstract void prepareStart(ExecuteContext paramExecuteContext);
  
  public abstract void prepareEnd(ExecuteContext paramExecuteContext);
  
  public abstract void bindStart(ExecuteContext paramExecuteContext);
  
  public abstract void bindEnd(ExecuteContext paramExecuteContext);
  
  public abstract void executeStart(ExecuteContext paramExecuteContext);
  
  public abstract void executeEnd(ExecuteContext paramExecuteContext);
  
  public abstract void fetchStart(ExecuteContext paramExecuteContext);
  
  public abstract void resultStart(ExecuteContext paramExecuteContext);
  
  public abstract void recordStart(ExecuteContext paramExecuteContext);
  
  public abstract void recordEnd(ExecuteContext paramExecuteContext);
  
  public abstract void resultEnd(ExecuteContext paramExecuteContext);
  
  public abstract void fetchEnd(ExecuteContext paramExecuteContext);
  
  public abstract void end(ExecuteContext paramExecuteContext);
  
  public abstract void exception(ExecuteContext paramExecuteContext);
  
  public abstract void warning(ExecuteContext paramExecuteContext);
}

package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.ExecuteType;
import org.jooq.Record;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.exception.ControlFlowSignal;

class RecordDelegate<R extends Record>
{
  private final Configuration configuration;
  private final R record;
  private final RecordLifecycleType type;
  
  RecordDelegate(Configuration configuration, R record)
  {
    this(configuration, record, RecordLifecycleType.LOAD);
  }
  
  RecordDelegate(Configuration configuration, R record, RecordLifecycleType type)
  {
    this.configuration = configuration;
    this.record = record;
    this.type = type;
  }
  
  static final <R extends Record> RecordDelegate<R> delegate(Configuration configuration, R record)
  {
    return new RecordDelegate(configuration, record);
  }
  
  static final <R extends Record> RecordDelegate<R> delegate(Configuration configuration, R record, RecordLifecycleType type)
  {
    return new RecordDelegate(configuration, record, type);
  }
  
  final <E extends Exception> R operate(RecordOperation<R, E> operation)
    throws Exception
  {
    RecordListenerProvider[] providers = null;
    RecordListener[] listeners = null;
    DefaultRecordContext ctx = null;
    E exception = null;
    int i;
    if (this.configuration != null)
    {
      providers = this.configuration.recordListenerProviders();
      if ((providers != null) && (providers.length > 0))
      {
        listeners = new RecordListener[providers.length];
        ctx = new DefaultRecordContext(this.configuration, executeType(), new Record[] { this.record });
        for (i = 0; i < providers.length; i++) {
          listeners[i] = providers[i].provide();
        }
      }
    }
    RecordListener localRecordListener1;
    RecordListener listener;
    if (listeners != null)
    {
      i = listeners;int i = i.length;
      for (localRecordListener1 = 0; localRecordListener1 < i; localRecordListener1++)
      {
        listener = i[localRecordListener1];
        switch (this.type)
        {
        case LOAD: 
          listener.loadStart(ctx); break;
        case REFRESH: 
          listener.refreshStart(ctx); break;
        case STORE: 
          listener.storeStart(ctx); break;
        case INSERT: 
          listener.insertStart(ctx); break;
        case UPDATE: 
          listener.updateStart(ctx); break;
        case DELETE: 
          listener.deleteStart(ctx); break;
        default: 
          throw new IllegalStateException("Type not supported: " + this.type);
        }
      }
    }
    if (operation != null)
    {
      try
      {
        operation.operate(this.record);
      }
      catch (Exception e)
      {
        exception = e;
        if ((e instanceof ControlFlowSignal)) {
          break label372;
        }
      }
      if (ctx != null) {
        ctx.exception = e;
      }
      if (listeners != null)
      {
        RecordListener[] arrayOfRecordListener1 = listeners;localRecordListener1 = arrayOfRecordListener1.length;
        for (listener = 0; listener < localRecordListener1; listener++)
        {
          RecordListener listener = arrayOfRecordListener1[listener];
          listener.exception(ctx);
        }
      }
    }
    label372:
    if (Utils.attachRecords(this.configuration)) {
      this.record.attach(this.configuration);
    }
    if (listeners != null) {
      for (RecordListener listener : listeners) {
        switch (this.type)
        {
        case LOAD: 
          listener.loadEnd(ctx); break;
        case REFRESH: 
          listener.refreshEnd(ctx); break;
        case STORE: 
          listener.storeEnd(ctx); break;
        case INSERT: 
          listener.insertEnd(ctx); break;
        case UPDATE: 
          listener.updateEnd(ctx); break;
        case DELETE: 
          listener.deleteEnd(ctx); break;
        default: 
          throw new IllegalStateException("Type not supported: " + this.type);
        }
      }
    }
    if (exception != null) {
      throw exception;
    }
    return this.record;
  }
  
  private final ExecuteType executeType()
  {
    return (this.type == RecordLifecycleType.LOAD) || (this.type == RecordLifecycleType.REFRESH) ? ExecuteType.READ : ExecuteType.WRITE;
  }
  
  static enum RecordLifecycleType
  {
    LOAD,  REFRESH,  STORE,  INSERT,  UPDATE,  DELETE;
    
    private RecordLifecycleType() {}
  }
}

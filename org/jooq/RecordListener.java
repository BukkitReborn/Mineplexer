package org.jooq;

import java.util.EventListener;

public abstract interface RecordListener
  extends EventListener
{
  public abstract void storeStart(RecordContext paramRecordContext);
  
  public abstract void storeEnd(RecordContext paramRecordContext);
  
  public abstract void insertStart(RecordContext paramRecordContext);
  
  public abstract void insertEnd(RecordContext paramRecordContext);
  
  public abstract void updateStart(RecordContext paramRecordContext);
  
  public abstract void updateEnd(RecordContext paramRecordContext);
  
  public abstract void deleteStart(RecordContext paramRecordContext);
  
  public abstract void deleteEnd(RecordContext paramRecordContext);
  
  public abstract void loadStart(RecordContext paramRecordContext);
  
  public abstract void loadEnd(RecordContext paramRecordContext);
  
  public abstract void refreshStart(RecordContext paramRecordContext);
  
  public abstract void refreshEnd(RecordContext paramRecordContext);
  
  public abstract void exception(RecordContext paramRecordContext);
}

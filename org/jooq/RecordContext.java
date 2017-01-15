package org.jooq;

public abstract interface RecordContext
  extends Scope
{
  public abstract ExecuteType type();
  
  public abstract Record record();
  
  public abstract RecordType<?> recordType();
  
  public abstract Record[] batchRecords();
  
  public abstract Exception exception();
}

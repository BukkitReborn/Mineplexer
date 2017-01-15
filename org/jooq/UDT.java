package org.jooq;

public abstract interface UDT<R extends UDTRecord<R>>
  extends QueryPart
{
  public abstract Row fieldsRow();
  
  public abstract <T> Field<T> field(Field<T> paramField);
  
  public abstract Field<?> field(String paramString);
  
  public abstract Field<?> field(int paramInt);
  
  public abstract Field<?>[] fields();
  
  public abstract Schema getSchema();
  
  public abstract String getName();
  
  public abstract Class<R> getRecordType();
  
  public abstract R newRecord();
  
  public abstract DataType<R> getDataType();
}

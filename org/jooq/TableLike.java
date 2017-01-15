package org.jooq;

public abstract interface TableLike<R extends Record>
  extends QueryPart
{
  public abstract Row fieldsRow();
  
  public abstract <T> Field<T> field(Field<T> paramField);
  
  public abstract Field<?> field(String paramString);
  
  public abstract Field<?> field(int paramInt);
  
  public abstract Field<?>[] fields();
  
  @Support
  public abstract Table<R> asTable();
  
  @Support
  public abstract Table<R> asTable(String paramString);
  
  @Support
  public abstract Table<R> asTable(String paramString, String... paramVarArgs);
}

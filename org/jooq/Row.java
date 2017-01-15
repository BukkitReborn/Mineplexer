package org.jooq;

public abstract interface Row
  extends QueryPart
{
  public abstract int size();
  
  public abstract <T> Field<T> field(Field<T> paramField);
  
  public abstract Field<?> field(String paramString);
  
  public abstract Field<?> field(int paramInt);
  
  public abstract Field<?>[] fields();
  
  public abstract Field<?>[] fields(Field<?>... paramVarArgs);
  
  public abstract Field<?>[] fields(String... paramVarArgs);
  
  public abstract Field<?>[] fields(int... paramVarArgs);
  
  public abstract int indexOf(Field<?> paramField);
  
  public abstract int indexOf(String paramString);
  
  public abstract Class<?>[] types();
  
  public abstract Class<?> type(int paramInt);
  
  public abstract Class<?> type(String paramString);
  
  public abstract DataType<?>[] dataTypes();
  
  public abstract DataType<?> dataType(int paramInt);
  
  public abstract DataType<?> dataType(String paramString);
  
  @Support
  public abstract Condition isNull();
  
  @Support
  public abstract Condition isNotNull();
}

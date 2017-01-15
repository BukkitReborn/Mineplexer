package org.jooq;

public abstract interface Parameter<T>
  extends QueryPart
{
  public abstract String getName();
  
  public abstract Class<T> getType();
  
  public abstract Converter<?, T> getConverter();
  
  public abstract Binding<?, T> getBinding();
  
  public abstract DataType<T> getDataType();
  
  public abstract DataType<T> getDataType(Configuration paramConfiguration);
  
  public abstract boolean isDefaulted();
}

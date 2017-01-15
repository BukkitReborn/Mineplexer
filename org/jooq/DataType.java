package org.jooq;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract interface DataType<T>
  extends Serializable
{
  public abstract DataType<T> getSQLDataType();
  
  public abstract DataType<T> getDataType(Configuration paramConfiguration);
  
  public abstract int getSQLType();
  
  public abstract Class<T> getType();
  
  public abstract Class<T[]> getArrayType();
  
  public abstract DataType<T[]> getArrayDataType();
  
  public abstract <E extends EnumType> DataType<E> asEnumDataType(Class<E> paramClass);
  
  public abstract <U> DataType<U> asConvertedDataType(Converter<? super T, U> paramConverter);
  
  public abstract <U> DataType<U> asConvertedDataType(Binding<? super T, U> paramBinding);
  
  public abstract String getTypeName();
  
  public abstract String getTypeName(Configuration paramConfiguration);
  
  public abstract String getCastTypeName();
  
  public abstract String getCastTypeName(Configuration paramConfiguration);
  
  public abstract SQLDialect getDialect();
  
  public abstract T convert(Object paramObject);
  
  public abstract T[] convert(Object... paramVarArgs);
  
  public abstract List<T> convert(Collection<?> paramCollection);
  
  public abstract DataType<T> nullable(boolean paramBoolean);
  
  public abstract boolean nullable();
  
  public abstract DataType<T> defaulted(boolean paramBoolean);
  
  public abstract boolean defaulted();
  
  public abstract DataType<T> precision(int paramInt);
  
  public abstract DataType<T> precision(int paramInt1, int paramInt2);
  
  public abstract int precision();
  
  public abstract boolean hasPrecision();
  
  public abstract DataType<T> scale(int paramInt);
  
  public abstract int scale();
  
  public abstract boolean hasScale();
  
  public abstract DataType<T> length(int paramInt);
  
  public abstract int length();
  
  public abstract boolean hasLength();
  
  public abstract boolean isNumeric();
  
  public abstract boolean isString();
  
  public abstract boolean isDateTime();
  
  public abstract boolean isTemporal();
  
  public abstract boolean isInterval();
  
  public abstract boolean isBinary();
  
  public abstract boolean isLob();
  
  public abstract boolean isArray();
}

package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.DataType;

class ConvertedDataType<T, U>
  extends DefaultDataType<U>
{
  private static final long serialVersionUID = -2321926692580974126L;
  private final DataType<T> delegate;
  private final Binding<? super T, U> binding;
  
  ConvertedDataType(DataType<T> delegate, Binding<? super T, U> binding)
  {
    super(null, binding
    
      .converter().toType(), delegate
      .getTypeName(), delegate
      .getCastTypeName(), delegate
      .precision(), delegate
      .scale(), delegate
      .length(), delegate
      .nullable(), delegate
      .defaulted());
    
    this.delegate = delegate;
    this.binding = binding;
  }
  
  public int getSQLType()
  {
    return this.delegate.getSQLType();
  }
  
  public String getTypeName(Configuration configuration)
  {
    return this.delegate.getTypeName(configuration);
  }
  
  public String getCastTypeName(Configuration configuration)
  {
    return this.delegate.getCastTypeName(configuration);
  }
  
  public U convert(Object object)
  {
    if (this.binding.converter().toType().isInstance(object)) {
      return (U)object;
    }
    return (U)this.binding.converter().from(this.delegate.convert(object));
  }
  
  Binding<? super T, U> binding()
  {
    return this.binding;
  }
  
  Converter<? super T, U> converter()
  {
    return this.binding.converter();
  }
}

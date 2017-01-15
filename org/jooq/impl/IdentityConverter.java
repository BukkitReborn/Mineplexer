package org.jooq.impl;

import org.jooq.Converter;

class IdentityConverter<T>
  implements Converter<T, T>
{
  private static final long serialVersionUID = -1721687282753727624L;
  private final Class<T> type;
  
  IdentityConverter(Class<T> type)
  {
    this.type = type;
  }
  
  public final T from(T t)
  {
    return t;
  }
  
  public final T to(T t)
  {
    return t;
  }
  
  public final Class<T> fromType()
  {
    return this.type;
  }
  
  public final Class<T> toType()
  {
    return this.type;
  }
  
  public String toString()
  {
    return "IdentityConverter [" + this.type.getName() + "]";
  }
}

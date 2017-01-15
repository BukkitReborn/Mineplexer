package org.jooq;

import java.io.Serializable;

public abstract interface Converter<T, U>
  extends Serializable
{
  public abstract U from(T paramT);
  
  public abstract T to(U paramU);
  
  public abstract Class<T> fromType();
  
  public abstract Class<U> toType();
}

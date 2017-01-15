package org.jooq;

import java.sql.CallableStatement;

public abstract interface BindingRegisterContext<U>
  extends Scope
{
  public abstract CallableStatement statement();
  
  public abstract int index();
  
  public abstract <T> BindingRegisterContext<T> convert(Converter<T, U> paramConverter);
}

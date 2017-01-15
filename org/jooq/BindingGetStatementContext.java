package org.jooq;

import java.sql.CallableStatement;

public abstract interface BindingGetStatementContext<U>
  extends Scope
{
  public abstract CallableStatement statement();
  
  public abstract int index();
  
  public abstract void value(U paramU);
  
  public abstract <T> BindingGetStatementContext<T> convert(Converter<T, U> paramConverter);
}

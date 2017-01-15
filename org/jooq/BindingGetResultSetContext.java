package org.jooq;

import java.sql.ResultSet;

public abstract interface BindingGetResultSetContext<U>
  extends Scope
{
  public abstract ResultSet resultSet();
  
  public abstract int index();
  
  public abstract void value(U paramU);
  
  public abstract <T> BindingGetResultSetContext<T> convert(Converter<T, U> paramConverter);
}

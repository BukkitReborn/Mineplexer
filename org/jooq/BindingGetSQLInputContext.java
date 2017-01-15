package org.jooq;

import java.sql.SQLInput;

public abstract interface BindingGetSQLInputContext<U>
  extends Scope
{
  public abstract SQLInput input();
  
  public abstract void value(U paramU);
  
  public abstract <T> BindingGetSQLInputContext<T> convert(Converter<T, U> paramConverter);
}

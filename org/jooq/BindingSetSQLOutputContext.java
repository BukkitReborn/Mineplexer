package org.jooq;

import java.sql.SQLOutput;

public abstract interface BindingSetSQLOutputContext<U>
  extends Scope
{
  public abstract SQLOutput output();
  
  public abstract U value();
  
  public abstract <T> BindingSetSQLOutputContext<T> convert(Converter<T, U> paramConverter);
}

package org.jooq;

import java.sql.PreparedStatement;

public abstract interface BindingSetStatementContext<U>
  extends Scope
{
  public abstract PreparedStatement statement();
  
  public abstract int index();
  
  public abstract U value();
  
  public abstract <T> BindingSetStatementContext<T> convert(Converter<T, U> paramConverter);
}

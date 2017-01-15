package org.jooq.impl;

import java.sql.PreparedStatement;
import org.jooq.BindingSetStatementContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingSetStatementContext<U>
  extends AbstractScope
  implements BindingSetStatementContext<U>
{
  private final PreparedStatement statement;
  private final int index;
  private final U value;
  
  DefaultBindingSetStatementContext(Configuration configuration, PreparedStatement statement, int index, U value)
  {
    super(configuration);
    
    this.statement = statement;
    this.index = index;
    this.value = value;
  }
  
  private DefaultBindingSetStatementContext(AbstractScope other, PreparedStatement statement, int index, U value)
  {
    super(other);
    
    this.statement = statement;
    this.index = index;
    this.value = value;
  }
  
  public final PreparedStatement statement()
  {
    return this.statement;
  }
  
  public final int index()
  {
    return this.index;
  }
  
  public final U value()
  {
    return (U)this.value;
  }
  
  public final <T> BindingSetStatementContext<T> convert(Converter<T, U> converter)
  {
    return new DefaultBindingSetStatementContext(this, this.statement, this.index, converter.to(this.value));
  }
}

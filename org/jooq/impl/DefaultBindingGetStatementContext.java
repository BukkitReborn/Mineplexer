package org.jooq.impl;

import java.sql.CallableStatement;
import org.jooq.BindingGetStatementContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingGetStatementContext<U>
  extends AbstractScope
  implements BindingGetStatementContext<U>
{
  private final CallableStatement statement;
  private final int index;
  private U value;
  
  DefaultBindingGetStatementContext(Configuration configuration, CallableStatement statement, int index)
  {
    super(configuration);
    
    this.statement = statement;
    this.index = index;
  }
  
  private DefaultBindingGetStatementContext(AbstractScope scope, CallableStatement statement, int index)
  {
    super(scope);
    
    this.statement = statement;
    this.index = index;
  }
  
  public final CallableStatement statement()
  {
    return this.statement;
  }
  
  public final int index()
  {
    return this.index;
  }
  
  public void value(U v)
  {
    this.value = v;
  }
  
  final U value()
  {
    return (U)this.value;
  }
  
  public final <T> BindingGetStatementContext<T> convert(final Converter<T, U> converter)
  {
    new DefaultBindingGetStatementContext(this, this.statement, this.index, converter)
    {
      public void value(T v)
      {
        DefaultBindingGetStatementContext.this.value = converter.from(v);
      }
    };
  }
}

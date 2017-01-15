package org.jooq.impl;

import java.sql.SQLInput;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingGetSQLInputContext<U>
  extends AbstractScope
  implements BindingGetSQLInputContext<U>
{
  private final SQLInput input;
  private U value;
  
  DefaultBindingGetSQLInputContext(Configuration configuration, SQLInput input)
  {
    super(configuration);
    
    this.input = input;
  }
  
  private DefaultBindingGetSQLInputContext(AbstractScope scope, SQLInput input)
  {
    super(scope);
    
    this.input = input;
  }
  
  public final SQLInput input()
  {
    return this.input;
  }
  
  public void value(U v)
  {
    this.value = v;
  }
  
  final U value()
  {
    return (U)this.value;
  }
  
  public final <T> BindingGetSQLInputContext<T> convert(final Converter<T, U> converter)
  {
    new DefaultBindingGetSQLInputContext(this, this.input, converter)
    {
      public void value(T v)
      {
        DefaultBindingGetSQLInputContext.this.value = converter.from(v);
      }
    };
  }
}

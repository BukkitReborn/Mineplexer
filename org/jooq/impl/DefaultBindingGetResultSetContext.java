package org.jooq.impl;

import java.sql.ResultSet;
import org.jooq.BindingGetResultSetContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingGetResultSetContext<U>
  extends AbstractScope
  implements BindingGetResultSetContext<U>
{
  private final ResultSet resultSet;
  private int index;
  private U value;
  
  DefaultBindingGetResultSetContext(Configuration configuration, ResultSet resultSet, int index)
  {
    super(configuration);
    
    this.resultSet = resultSet;
    this.index = index;
  }
  
  private DefaultBindingGetResultSetContext(AbstractScope scope, ResultSet resultSet, int index)
  {
    super(scope);
    
    this.resultSet = resultSet;
    this.index = index;
  }
  
  public final ResultSet resultSet()
  {
    return this.resultSet;
  }
  
  public final int index()
  {
    return this.index;
  }
  
  final void index(int i)
  {
    this.index = i;
  }
  
  public void value(U v)
  {
    this.value = v;
  }
  
  final U value()
  {
    return (U)this.value;
  }
  
  public final <T> BindingGetResultSetContext<T> convert(final Converter<T, U> converter)
  {
    new DefaultBindingGetResultSetContext(this, this.resultSet, this.index, converter)
    {
      public void value(T v)
      {
        DefaultBindingGetResultSetContext.this.value = converter.from(v);
      }
    };
  }
}

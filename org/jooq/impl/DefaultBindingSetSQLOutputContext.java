package org.jooq.impl;

import java.sql.SQLOutput;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingSetSQLOutputContext<U>
  extends AbstractScope
  implements BindingSetSQLOutputContext<U>
{
  private final SQLOutput output;
  private final U value;
  
  DefaultBindingSetSQLOutputContext(Configuration configuration, SQLOutput output, U value)
  {
    super(configuration);
    
    this.output = output;
    this.value = value;
  }
  
  private DefaultBindingSetSQLOutputContext(AbstractScope other, SQLOutput output, U value)
  {
    super(other);
    
    this.output = output;
    this.value = value;
  }
  
  public final SQLOutput output()
  {
    return this.output;
  }
  
  public final U value()
  {
    return (U)this.value;
  }
  
  public final <T> BindingSetSQLOutputContext<T> convert(Converter<T, U> converter)
  {
    return new DefaultBindingSetSQLOutputContext(this, this.output, converter.to(this.value));
  }
}

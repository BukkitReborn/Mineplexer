package org.jooq.impl;

import java.sql.CallableStatement;
import org.jooq.BindingRegisterContext;
import org.jooq.Configuration;
import org.jooq.Converter;

class DefaultBindingRegisterContext<U>
  extends AbstractScope
  implements BindingRegisterContext<U>
{
  private final CallableStatement statement;
  private final int index;
  
  DefaultBindingRegisterContext(Configuration configuration, CallableStatement statement, int index)
  {
    super(configuration);
    
    this.statement = statement;
    this.index = index;
  }
  
  private DefaultBindingRegisterContext(AbstractScope scope, CallableStatement statement, int index)
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
  
  public final <T> BindingRegisterContext<T> convert(Converter<T, U> converter)
  {
    return new DefaultBindingRegisterContext(this, this.statement, this.index);
  }
}

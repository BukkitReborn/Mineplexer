package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Floor<T extends Number>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<T> argument;
  
  Floor(Field<T> argument)
  {
    super("floor", argument.getDataType(), new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<T> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect())
    {
    case SQLITE: 
      return DSL.round(this.argument.sub(Double.valueOf(0.499999999999999D)));
    }
    return DSL.field("{floor}({0})", getDataType(), new QueryPart[] { this.argument });
  }
}

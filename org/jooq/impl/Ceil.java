package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Ceil<T extends Number>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<T> argument;
  
  Ceil(Field<T> argument)
  {
    super("ceil", argument.getDataType(), new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<T> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case SQLITE: 
      return DSL.round(this.argument.add(Double.valueOf(0.499999999999999D)));
    case H2: 
      return DSL.field("{ceiling}({0})", getDataType(), new QueryPart[] { this.argument });
    }
    return DSL.field("{ceil}({0})", getDataType(), new QueryPart[] { this.argument });
  }
}

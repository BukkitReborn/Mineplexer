package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

class Radians
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<?> argument;
  
  Radians(Field<?> argument)
  {
    super("radians", SQLDataType.NUMERIC, new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<BigDecimal> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case FIREBIRD: 
    case SQLITE: 
      return this.argument.cast(BigDecimal.class).mul(DSL.pi()).div(DSL.inline(Integer.valueOf(180)));
    }
    return DSL.function("radians", SQLDataType.NUMERIC, new Field[] { this.argument });
  }
}

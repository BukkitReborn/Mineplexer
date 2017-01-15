package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Degrees
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<?> argument;
  
  Degrees(Field<?> argument)
  {
    super("degrees", SQLDataType.NUMERIC, new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<BigDecimal> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case FIREBIRD: 
    case SQLITE: 
      return this.argument.cast(BigDecimal.class).mul(DSL.inline(Integer.valueOf(180))).div(DSL.pi());
    }
    return DSL.field("{degrees}({0})", SQLDataType.NUMERIC, new QueryPart[] { this.argument });
  }
}

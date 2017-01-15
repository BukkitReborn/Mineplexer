package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Sqrt
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<? extends Number> argument;
  
  Sqrt(Field<? extends Number> argument)
  {
    super("sqrt", SQLDataType.NUMERIC, new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<BigDecimal> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case SQLITE: 
      return DSL.power(this.argument, Double.valueOf(0.5D));
    }
    return DSL.field("{sqrt}({0})", SQLDataType.NUMERIC, new QueryPart[] { this.argument });
  }
}

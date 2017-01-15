package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

class Rand
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  Rand()
  {
    super("rand", SQLDataType.NUMERIC, new Field[0]);
  }
  
  final Field<BigDecimal> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
    case POSTGRES: 
    case SQLITE: 
      return DSL.function("random", SQLDataType.NUMERIC, new Field[0]);
    }
    return DSL.function("rand", SQLDataType.NUMERIC, new Field[0]);
  }
}

package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

class Ln
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<? extends Number> argument;
  private final Integer base;
  
  Ln(Field<? extends Number> argument)
  {
    this(argument, null);
  }
  
  Ln(Field<? extends Number> argument, Integer base)
  {
    super("ln", SQLDataType.NUMERIC, new Field[] { argument });
    
    this.argument = argument;
    this.base = base;
  }
  
  final Field<BigDecimal> getFunction0(Configuration configuration)
  {
    if (this.base == null)
    {
      switch (configuration.dialect().family())
      {
      case H2: 
        return DSL.function("log", SQLDataType.NUMERIC, new Field[] { this.argument });
      }
      return DSL.function("ln", SQLDataType.NUMERIC, new Field[] { this.argument });
    }
    switch (configuration.dialect().family())
    {
    case H2: 
    case DERBY: 
    case HSQLDB: 
      return DSL.ln(this.argument).div(DSL.ln(DSL.inline(this.base)));
    }
    return DSL.function("log", SQLDataType.NUMERIC, new Field[] { DSL.inline(this.base), this.argument });
  }
}

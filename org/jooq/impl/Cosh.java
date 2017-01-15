package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

class Cosh
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<? extends Number> argument;
  
  Cosh(Field<? extends Number> argument)
  {
    super("cosh", SQLDataType.NUMERIC, new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<BigDecimal> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case CUBRID: 
    case HSQLDB: 
    case MARIADB: 
    case MYSQL: 
    case POSTGRES: 
      return DSL.exp(this.argument.mul(DSL.two())).add(DSL.one()).div(DSL.exp(this.argument).mul(DSL.two()));
    }
    return DSL.function("cosh", SQLDataType.NUMERIC, new Field[] { this.argument });
  }
}

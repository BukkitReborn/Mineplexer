package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

class Euler
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = -420788300355442056L;
  
  Euler()
  {
    super("e", SQLDataType.NUMERIC, new Field[0]);
  }
  
  final Field<BigDecimal> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case CUBRID: 
    case DERBY: 
    case FIREBIRD: 
    case H2: 
    case HSQLDB: 
    case MARIADB: 
    case MYSQL: 
    case POSTGRES: 
      return DSL.exp(DSL.one());
    case SQLITE: 
      return DSL.inline(Double.valueOf(2.718281828459045D), BigDecimal.class);
    }
    return DSL.function("e", getDataType(), new Field[0]);
  }
}

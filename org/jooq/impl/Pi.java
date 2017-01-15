package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

class Pi
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = -420788300355442056L;
  
  Pi()
  {
    super("pi", SQLDataType.NUMERIC, new Field[0]);
  }
  
  final Field<BigDecimal> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case SQLITE: 
      return DSL.inline(Double.valueOf(3.141592653589793D), BigDecimal.class);
    }
    return DSL.function("pi", getDataType(), new Field[0]);
  }
}

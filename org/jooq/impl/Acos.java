package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Acos
  extends AbstractFunction<BigDecimal>
{
  private static final long serialVersionUID = 3117002829857089691L;
  private final Field<? extends Number> arg;
  
  Acos(Field<? extends Number> arg)
  {
    super("acos", SQLDataType.NUMERIC, new Field[0]);
    
    this.arg = arg;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    }
    return DSL.field("{acos}({0})", getDataType(), new QueryPart[] { this.arg });
  }
}

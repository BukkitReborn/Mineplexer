package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Ascii
  extends AbstractFunction<Integer>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<?> string;
  
  Ascii(Field<?> string)
  {
    super("ascii", SQLDataType.INTEGER, new Field[] { string });
    
    this.string = string;
  }
  
  final Field<Integer> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect())
    {
    case FIREBIRD: 
      return DSL.field("{ascii_val}({0})", SQLDataType.INTEGER, new QueryPart[] { this.string });
    }
    return DSL.field("{ascii}({0})", SQLDataType.INTEGER, new QueryPart[] { this.string });
  }
}

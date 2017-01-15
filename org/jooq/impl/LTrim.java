package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class LTrim
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<String> argument;
  
  LTrim(Field<String> argument)
  {
    super("ltrim", SQLDataType.VARCHAR, new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<String> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect())
    {
    case FIREBIRD: 
      return DSL.field("{trim}({leading} {from} {0})", SQLDataType.VARCHAR, new QueryPart[] { this.argument });
    }
    return DSL.function("ltrim", SQLDataType.VARCHAR, new Field[] { this.argument });
  }
}

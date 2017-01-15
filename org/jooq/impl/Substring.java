package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

class Substring
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  Substring(Field<?>... arguments)
  {
    super("substring", SQLDataType.VARCHAR, arguments);
  }
  
  final Field<String> getFunction0(Configuration configuration)
  {
    String functionName = "substring";
    switch (configuration.dialect().family())
    {
    case FIREBIRD: 
      if (getArguments().length == 2) {
        return DSL.field("{substring}({0} {from} {1})", SQLDataType.VARCHAR, getArguments());
      }
      return DSL.field("{substring}({0} {from} {1} {for} {2})", SQLDataType.VARCHAR, getArguments());
    case DERBY: 
    case SQLITE: 
      functionName = "substr";
    }
    return DSL.function(functionName, SQLDataType.VARCHAR, getArguments());
  }
}

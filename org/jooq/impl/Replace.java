package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

class Replace
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  Replace(Field<?>... arguments)
  {
    super("replace", SQLDataType.VARCHAR, arguments);
  }
  
  final Field<String> getFunction0(Configuration configuration)
  {
    Field<?>[] args = getArguments();
    switch (configuration.dialect().family())
    {
    case FIREBIRD: 
    case HSQLDB: 
    case MARIADB: 
    case MYSQL: 
    case POSTGRES: 
    case SQLITE: 
      if (args.length == 2) {
        return DSL.function("replace", SQLDataType.VARCHAR, new Field[] { args[0], args[1], DSL.val("") });
      }
      return DSL.function("replace", SQLDataType.VARCHAR, args);
    }
    return DSL.function("replace", SQLDataType.VARCHAR, args);
  }
}

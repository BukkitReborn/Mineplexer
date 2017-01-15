package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

class CurrentUser
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  CurrentUser()
  {
    super("current_user", SQLDataType.VARCHAR, new Field[0]);
  }
  
  final Field<String> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
    case FIREBIRD: 
    case HSQLDB: 
    case POSTGRES: 
    case SQLITE: 
      return DSL.field("{current_user}", String.class);
    }
    return DSL.function("current_user", SQLDataType.VARCHAR, new Field[0]);
  }
}

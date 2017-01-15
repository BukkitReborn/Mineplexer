package org.jooq.impl;

import java.sql.Date;
import org.jooq.Configuration;
import org.jooq.Field;

class CurrentDate
  extends AbstractFunction<Date>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  CurrentDate()
  {
    super("current_user", SQLDataType.DATE, new Field[0]);
  }
  
  final Field<Date> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
    case FIREBIRD: 
    case HSQLDB: 
    case POSTGRES: 
    case SQLITE: 
      return DSL.field("{current_date}", SQLDataType.DATE);
    }
    return DSL.function("current_date", SQLDataType.DATE, new Field[0]);
  }
}

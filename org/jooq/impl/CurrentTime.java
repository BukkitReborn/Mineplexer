package org.jooq.impl;

import java.sql.Time;
import org.jooq.Configuration;
import org.jooq.Field;

class CurrentTime
  extends AbstractFunction<Time>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  CurrentTime()
  {
    super("current_time", SQLDataType.TIME, new Field[0]);
  }
  
  final Field<Time> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
    case FIREBIRD: 
    case HSQLDB: 
    case POSTGRES: 
    case SQLITE: 
      return DSL.field("{current_time}", SQLDataType.TIME);
    }
    return DSL.function("current_time", SQLDataType.TIME, new Field[0]);
  }
}

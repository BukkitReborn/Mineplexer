package org.jooq.impl;

import java.sql.Timestamp;
import org.jooq.Configuration;
import org.jooq.Field;

class CurrentTimestamp
  extends AbstractFunction<Timestamp>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  CurrentTimestamp()
  {
    super("current_timestamp", SQLDataType.TIMESTAMP, new Field[0]);
  }
  
  final Field<Timestamp> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
    case FIREBIRD: 
    case HSQLDB: 
    case POSTGRES: 
    case SQLITE: 
      return DSL.field("{current_timestamp}", SQLDataType.TIMESTAMP);
    }
    return DSL.function("current_timestamp", SQLDataType.TIMESTAMP, new Field[0]);
  }
}

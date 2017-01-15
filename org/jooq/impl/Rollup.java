package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Rollup
  extends AbstractFunction<Object>
{
  private static final long serialVersionUID = -5820608758939548704L;
  
  Rollup(Field<?>... fields)
  {
    super("rollup", SQLDataType.OTHER, fields);
  }
  
  final Field<Object> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect())
    {
    case CUBRID: 
    case MARIADB: 
    case MYSQL: 
      return DSL.field("{0} {with rollup}", new QueryPart[] { new QueryPartList(getArguments()) });
    }
    return DSL.function("rollup", Object.class, getArguments());
  }
}

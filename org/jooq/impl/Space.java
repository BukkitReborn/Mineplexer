package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Space
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -4239524454814412161L;
  private final Field<Integer> count;
  
  Space(Field<Integer> count)
  {
    super("space", SQLDataType.VARCHAR, new Field[] { count });
    
    this.count = count;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case CUBRID: 
    case MARIADB: 
    case MYSQL: 
    case H2: 
      return DSL.field("{space}({0})", getDataType(), new QueryPart[] { this.count });
    }
    return DSL.repeat(DSL.inline(" "), this.count);
  }
}

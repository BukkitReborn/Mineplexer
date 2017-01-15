package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Position
  extends AbstractFunction<Integer>
{
  private static final long serialVersionUID = 3544690069533526544L;
  private final Field<String> search;
  private final Field<?> in;
  
  Position(Field<String> search, Field<?> in)
  {
    super("position", SQLDataType.INTEGER, new Field[] { search, in });
    
    this.search = search;
    this.in = in;
  }
  
  final Field<Integer> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
      return DSL.field("{locate}({0}, {1})", SQLDataType.INTEGER, new QueryPart[] { this.search, this.in });
    }
    return DSL.field("{position}({0} {in} {1})", SQLDataType.INTEGER, new QueryPart[] { this.search, this.in });
  }
}

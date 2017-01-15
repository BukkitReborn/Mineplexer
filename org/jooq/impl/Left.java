package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Left
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = 2200760781944082146L;
  private Field<String> field;
  private Field<? extends Number> length;
  
  Left(Field<String> field, Field<? extends Number> length)
  {
    super("left", field.getDataType(), new Field[0]);
    
    this.field = field;
    this.length = length;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
    case SQLITE: 
      return DSL.substring(this.field, DSL.inline(Integer.valueOf(1)), this.length);
    }
    return DSL.field("{left}({0}, {1})", new QueryPart[] { this.field, this.length });
  }
}

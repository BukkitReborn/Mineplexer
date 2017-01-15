package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Right
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = 2200760781944082146L;
  private Field<String> field;
  private Field<? extends Number> length;
  
  Right(Field<String> field, Field<? extends Number> length)
  {
    super("right", field.getDataType(), new Field[0]);
    
    this.field = field;
    this.length = length;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
      return DSL.substring(this.field, this.field.length().add(DSL.one()).sub(this.length));
    case SQLITE: 
      return DSL.substring(this.field, this.length.neg());
    }
    return DSL.field("{right}({0}, {1})", new QueryPart[] { this.field, this.length });
  }
}

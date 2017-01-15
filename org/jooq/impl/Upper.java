package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Upper
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -9070564546827153434L;
  private final Field<String> field;
  
  Upper(Field<String> field)
  {
    super("upper", field.getDataType(), new Field[] { field });
    
    this.field = field;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    }
    return DSL.field("{upper}({0})", getDataType(), new QueryPart[] { this.field });
  }
}

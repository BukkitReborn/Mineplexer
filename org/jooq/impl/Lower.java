package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Lower
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -9070564546827153434L;
  private final Field<String> field;
  
  Lower(Field<String> field)
  {
    super("lower", field.getDataType(), new Field[] { field });
    
    this.field = field;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    }
    return DSL.field("{lower}({0})", getDataType(), new QueryPart[] { this.field });
  }
}

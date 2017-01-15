package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;

class FieldCondition
  extends AbstractCondition
{
  private static final long serialVersionUID = -9170915951443879057L;
  private final Field<Boolean> field;
  
  FieldCondition(Field<Boolean> field)
  {
    this.field = field;
  }
  
  public void accept(Context<?> ctx)
  {
    delegate(ctx.configuration()).accept(ctx);
  }
  
  private final QueryPartInternal delegate(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case CUBRID: 
    case FIREBIRD: 
      return (QueryPartInternal)DSL.condition("{0} = {1}", new QueryPart[] { this.field, DSL.inline(Boolean.valueOf(true)) });
    }
    return (QueryPartInternal)this.field;
  }
}

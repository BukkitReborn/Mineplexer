package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;

class NotField
  extends AbstractField<Boolean>
{
  private static final long serialVersionUID = 2921001862882237932L;
  private static final Clause[] CLAUSES = { Clause.CONDITION, Clause.CONDITION_NOT };
  private final Field<Boolean> field;
  
  NotField(Field<Boolean> field)
  {
    super("not", field.getDataType());
    this.field = field;
  }
  
  public final void accept(Context<?> ctx)
  {
    switch (ctx.family())
    {
    case CUBRID: 
    case FIREBIRD: 
      ctx.visit(DSL.field(DSL.not(DSL.condition(this.field))));
      break;
    case DERBY: 
    case H2: 
    case HSQLDB: 
    case MARIADB: 
    case MYSQL: 
    case POSTGRES: 
    case SQLITE: 
    default: 
      ctx.visit(DSL.field("{not}({0})", getDataType(), new QueryPart[] { this.field }));
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

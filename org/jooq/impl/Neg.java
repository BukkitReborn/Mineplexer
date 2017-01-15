package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SQLDialect;

class Neg<T>
  extends AbstractField<T>
{
  private static final long serialVersionUID = 7624782102883057433L;
  private final ExpressionOperator operator;
  private final Field<T> field;
  
  Neg(Field<T> field, ExpressionOperator operator)
  {
    super(operator.toSQL() + field.getName(), field.getDataType());
    
    this.operator = operator;
    this.field = field;
  }
  
  public final void accept(Context<?> ctx)
  {
    SQLDialect family = ctx.configuration().dialect().family();
    if (this.operator == ExpressionOperator.BIT_NOT) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB }).contains(family))
      {
        ctx.sql("(0 -").visit(this.field).sql(" - 1)"); return;
      }
    }
    if ((this.operator == ExpressionOperator.BIT_NOT) && (family == SQLDialect.FIREBIRD)) {
      ctx.keyword("bin_not(").visit(this.field).sql(")");
    } else {
      ctx.sql(this.operator.toSQL()).sql("(").visit(this.field).sql(")");
    }
  }
}

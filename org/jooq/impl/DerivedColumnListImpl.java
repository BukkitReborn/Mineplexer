package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.DerivedColumnList;
import org.jooq.Record;
import org.jooq.Select;

class DerivedColumnListImpl
  extends AbstractQueryPart
  implements DerivedColumnList
{
  private static final long serialVersionUID = -369633206858851863L;
  final String name;
  final String[] fieldNames;
  
  DerivedColumnListImpl(String name, String[] fieldNames)
  {
    this.name = name;
    this.fieldNames = fieldNames;
  }
  
  public final <R extends Record> CommonTableExpression<R> as(Select<R> select)
  {
    return new CommonTableExpressionImpl(this, select);
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(DSL.name(new String[] { this.name }));
    if ((this.fieldNames != null) && (this.fieldNames.length > 0))
    {
      ctx.sql("(");
      for (int i = 0; i < this.fieldNames.length; i++)
      {
        if (i > 0) {
          ctx.sql(", ");
        }
        ctx.visit(DSL.name(new String[] { this.fieldNames[i] }));
      }
      ctx.sql(")");
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
}

package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QuantifiedSelect;
import org.jooq.QueryPartInternal;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.SelectSelectStep;

class QuantifiedSelectImpl<R extends Record>
  extends AbstractQueryPart
  implements QuantifiedSelect<R>
{
  private static final long serialVersionUID = -1224570388944748450L;
  private final Quantifier quantifier;
  private final Select<R> query;
  private final Field<? extends Object[]> array;
  
  QuantifiedSelectImpl(Quantifier quantifier, Select<R> query)
  {
    this.quantifier = quantifier;
    this.query = query;
    this.array = null;
  }
  
  QuantifiedSelectImpl(Quantifier quantifier, Field<? extends Object[]> array)
  {
    this.quantifier = quantifier;
    this.query = null;
    this.array = array;
  }
  
  public final void accept(Context<?> ctx)
  {
    if (ctx.subquery()) {
      ctx.keyword(this.quantifier.toSQL()).sql(" (").formatIndentStart().formatNewLine().visit(delegate(ctx.configuration())).formatIndentEnd().formatNewLine().sql(")");
    } else {
      ctx.keyword(this.quantifier.toSQL()).sql(" (").subquery(true).formatIndentStart().formatNewLine().visit(delegate(ctx.configuration())).formatIndentEnd().formatNewLine().subquery(false).sql(")");
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return delegate(ctx.configuration()).clauses(ctx);
  }
  
  private final QueryPartInternal delegate(Configuration ctx)
  {
    if (this.query != null) {
      return (QueryPartInternal)this.query;
    }
    switch (ctx.dialect())
    {
    case POSTGRES: 
      return (QueryPartInternal)this.array;
    case H2: 
    case HSQLDB: 
      return (QueryPartInternal)create(ctx).select(new Field[0]).from(DSL.table(this.array));
    }
    if ((this.array instanceof Param))
    {
      Object[] values = (Object[])((Param)this.array).getValue();
      
      Select<Record1<Object>> select = null;
      for (Object value : values) {
        if (select == null) {
          select = DSL.select(DSL.val(value));
        } else {
          select = select.unionAll(DSL.select(DSL.val(value)));
        }
      }
      return (QueryPartInternal)select;
    }
    return (QueryPartInternal)DSL.select(new Field[0]).from(DSL.table(this.array));
  }
}

package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.tools.StringUtils;

class TableFieldImpl<R extends Record, T>
  extends AbstractField<T>
  implements TableField<R, T>
{
  private static final long serialVersionUID = -2211214195583539735L;
  private static final Clause[] CLAUSES = { Clause.FIELD, Clause.FIELD_REFERENCE };
  private final Table<R> table;
  
  TableFieldImpl(String name, DataType<T> type, Table<R> table, String comment, Binding<?, T> binding)
  {
    super(name, type, comment, binding);
    
    this.table = table;
  }
  
  public final Table<R> getTable()
  {
    return this.table;
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.data("org.jooq.configuration.omit-clause-event-emission", Boolean.valueOf(true));
    if (ctx.qualify())
    {
      ctx.visit(this.table);
      ctx.sql(".");
    }
    ctx.literal(getName());
    ctx.data("org.jooq.configuration.omit-clause-event-emission", null);
  }
  
  public boolean equals(Object that)
  {
    if (this == that) {
      return true;
    }
    if ((that instanceof TableField))
    {
      TableField<?, ?> other = (TableField)that;
      
      return (StringUtils.equals(getTable(), other.getTable())) && (StringUtils.equals(getName(), other.getName()));
    }
    return super.equals(that);
  }
}

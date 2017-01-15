package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Insert;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.Table;

class InsertSelectQueryImpl<R extends Record>
  extends AbstractQuery
  implements Insert<R>
{
  private static final long serialVersionUID = -1540775270159018516L;
  private static final Clause[] CLAUSES = { Clause.INSERT };
  private final Table<?> into;
  private final Field<?>[] fields;
  private final Select<?> select;
  
  InsertSelectQueryImpl(Configuration configuration, Table<?> into, Field<?>[] fields, Select<?> select)
  {
    super(configuration);
    
    this.into = into;
    this.fields = ((fields == null) || (fields.length == 0) ? into.fields() : fields);
    this.select = select;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.start(Clause.INSERT_INSERT_INTO).keyword("insert into").sql(" ").visit(this.into).sql(" (");
    
    boolean qualify = ctx.qualify();
    ctx.qualify(false);
    
    String separator = "";
    for (Field<?> field : this.fields)
    {
      ctx.sql(separator).visit(field);
      
      separator = ", ";
    }
    ctx.qualify(qualify);
    ctx.sql(")")
      .end(Clause.INSERT_INSERT_INTO)
      .formatSeparator()
      .start(Clause.INSERT_SELECT)
      .visit(this.select)
      .end(Clause.INSERT_SELECT)
      .start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE)
      .end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE)
      .start(Clause.INSERT_RETURNING)
      .end(Clause.INSERT_RETURNING);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

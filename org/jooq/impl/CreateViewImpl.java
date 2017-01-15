package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateViewAsStep;
import org.jooq.CreateViewFinalStep;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;

class CreateViewImpl<R extends Record>
  extends AbstractQuery
  implements CreateViewAsStep<R>, CreateViewFinalStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.CREATE_VIEW };
  private final Table<?> view;
  private final Field<?>[] fields;
  private Select<?> select;
  
  CreateViewImpl(Configuration configuration, Table<?> view, Field<?>[] fields)
  {
    super(configuration);
    
    this.view = view;
    this.fields = fields;
  }
  
  public final CreateViewFinalStep as(Select<? extends R> s)
  {
    this.select = s;
    return this;
  }
  
  public final void accept(Context<?> ctx)
  {
    boolean rename = (this.fields != null) && (this.fields.length > 0);
    boolean renameSupported = ctx.family() != SQLDialect.SQLITE;
    
    ctx.start(Clause.CREATE_VIEW_NAME)
      .keyword("create view")
      .sql(" ")
      .visit(this.view);
    if ((rename) && (renameSupported))
    {
      boolean qualify = ctx.qualify();
      
      ctx.sql("(")
        .qualify(false)
        .visit(new QueryPartList(this.fields))
        .qualify(qualify)
        .sql(")");
    }
    ctx.end(Clause.CREATE_VIEW_NAME).formatSeparator().keyword("as").formatSeparator().start(Clause.CREATE_VIEW_AS).visit((rename) && (!renameSupported) ? DSL.selectFrom(DSL.table(this.select).as("t", Utils.fieldNames(this.fields))) : this.select).end(Clause.CREATE_VIEW_AS);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

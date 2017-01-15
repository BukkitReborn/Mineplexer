package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateTableAsStep;
import org.jooq.CreateTableColumnStep;
import org.jooq.CreateTableFinalStep;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.Table;

class CreateTableImpl<R extends Record>
  extends AbstractQuery
  implements CreateTableAsStep<R>, CreateTableColumnStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private final Table<?> table;
  private Select<?> select;
  private final List<Field<?>> columnFields;
  private final List<DataType<?>> columnTypes;
  
  CreateTableImpl(Configuration configuration, Table<?> table)
  {
    super(configuration);
    
    this.table = table;
    this.columnFields = new ArrayList();
    this.columnTypes = new ArrayList();
  }
  
  public final CreateTableFinalStep as(Select<? extends R> s)
  {
    this.select = s;
    return this;
  }
  
  public final <T> CreateTableColumnStep column(Field<T> field, DataType<T> type)
  {
    this.columnFields.add(field);
    this.columnTypes.add(type);
    return this;
  }
  
  public final CreateTableColumnStep column(String field, DataType<?> type)
  {
    this.columnFields.add(DSL.fieldByName(type, new String[] { field }));
    this.columnTypes.add(type);
    return this;
  }
  
  public final void accept(Context<?> ctx)
  {
    if (this.select != null)
    {
      acceptCreateTableAsSelect(ctx);
    }
    else
    {
      ctx.start(Clause.CREATE_TABLE).start(Clause.CREATE_TABLE_NAME).keyword("create table").sql(" ").visit(this.table).end(Clause.CREATE_TABLE_NAME).start(Clause.CREATE_TABLE_COLUMNS).sql("(").formatIndentStart().formatNewLine();
      
      boolean qualify = ctx.qualify();
      ctx.qualify(false);
      for (int i = 0; i < this.columnFields.size(); i++)
      {
        ctx.visit((QueryPart)this.columnFields.get(i)).sql(" ").sql(((DataType)this.columnTypes.get(i)).getCastTypeName(ctx.configuration()));
        if (((DataType)this.columnTypes.get(i)).nullable()) {
          ctx.sql(" ").keyword("null");
        } else {
          ctx.sql(" ").keyword("not null");
        }
        if (i < this.columnFields.size() - 1) {
          ctx.sql(",").formatSeparator();
        }
      }
      ctx.qualify(qualify);
      ctx.formatIndentEnd()
        .formatNewLine()
        .sql(")")
        .end(Clause.CREATE_TABLE_COLUMNS)
        .end(Clause.CREATE_TABLE);
    }
  }
  
  private final void acceptCreateTableAsSelect(Context<?> ctx)
  {
    ctx.start(Clause.CREATE_TABLE).start(Clause.CREATE_TABLE_NAME).keyword("create table").sql(" ").visit(this.table).end(Clause.CREATE_TABLE_NAME).formatSeparator().keyword("as").formatSeparator().start(Clause.CREATE_TABLE_AS).visit(this.select).end(Clause.CREATE_TABLE_AS).end(Clause.CREATE_TABLE);
  }
  
  private final void acceptSelectInto(Context<?> ctx)
  {
    ctx.data("org.jooq.configuration.select-into-table", this.table);
    ctx.visit(this.select);
    ctx.data().remove("org.jooq.configuration.select-into-table");
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
}

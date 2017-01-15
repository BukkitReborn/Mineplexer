package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateIndexFinalStep;
import org.jooq.CreateIndexStep;
import org.jooq.Field;
import org.jooq.Table;

class CreateIndexImpl
  extends AbstractQuery
  implements CreateIndexStep, CreateIndexFinalStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.CREATE_INDEX };
  private final String index;
  private Table<?> table;
  private Field<?>[] fields;
  
  CreateIndexImpl(Configuration configuration, String index)
  {
    super(configuration);
    
    this.index = index;
  }
  
  public final CreateIndexFinalStep on(Table<?> t, Field<?>... f)
  {
    this.table = t;
    this.fields = f;
    
    return this;
  }
  
  public final CreateIndexFinalStep on(String tableName, String... fieldNames)
  {
    Field<?>[] f = new Field[fieldNames.length];
    for (int i = 0; i < f.length; i++) {
      f[i] = DSL.fieldByName(new String[] { fieldNames[i] });
    }
    return on(DSL.tableByName(new String[] { tableName }), f);
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.keyword("create index").sql(" ").visit(DSL.name(new String[] { this.index })).sql(" ").keyword("on").sql(" ").visit(this.table).sql("(").qualify(false).visit(new QueryPartList(this.fields)).qualify(true).sql(")");
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

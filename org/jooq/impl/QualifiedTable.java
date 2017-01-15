package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

class QualifiedTable
  extends AbstractTable<Record>
{
  private static final long serialVersionUID = 6937002867156868761L;
  private static final Clause[] CLAUSES = { Clause.TABLE, Clause.TABLE_REFERENCE };
  private final String[] sql;
  
  QualifiedTable(String... sql)
  {
    super(sql[(sql.length - 1)]);
    
    this.sql = sql;
  }
  
  public final void accept(Context<?> ctx)
  {
    String separator = "";
    for (String string : this.sql)
    {
      ctx.sql(separator);
      ctx.literal(string);
      
      separator = ".";
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final Class<? extends Record> getRecordType()
  {
    return RecordImpl.class;
  }
  
  public final Table<Record> as(String alias)
  {
    return new TableAlias(this, alias);
  }
  
  public final Table<Record> as(String alias, String... fieldAliases)
  {
    return new TableAlias(this, alias, fieldAliases);
  }
  
  final Fields<Record> fields0()
  {
    return new Fields(new Field[0]);
  }
}

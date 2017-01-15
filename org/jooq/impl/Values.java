package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.Table;

class Values<R extends Record>
  extends AbstractTable<R>
{
  private static final long serialVersionUID = -637982217747670311L;
  private final Row[] rows;
  
  Values(Row[] rows)
  {
    super("values");
    
    this.rows = assertNotEmpty(rows);
  }
  
  static Row[] assertNotEmpty(Row[] rows)
  {
    if ((rows == null) || (rows.length == 0)) {
      throw new IllegalArgumentException("Cannot create a VALUES() constructor with an empty set of rows");
    }
    return rows;
  }
  
  public final Class<? extends R> getRecordType()
  {
    return RecordImpl.class;
  }
  
  public final Table<R> as(String alias)
  {
    return new TableAlias(this, alias, true);
  }
  
  public final Table<R> as(String alias, String... fieldAliases)
  {
    return new TableAlias(this, alias, fieldAliases, true);
  }
  
  public final void accept(Context<?> ctx)
  {
    boolean subquery;
    switch (ctx.configuration().dialect().family())
    {
    case FIREBIRD: 
    case MARIADB: 
    case MYSQL: 
    case SQLITE: 
    case H2: 
      Select<Record> selects = null;
      subquery = ctx.subquery();
      for (Row row : this.rows)
      {
        Select<Record> select = create().select(row.fields());
        if (selects == null) {
          selects = select;
        } else {
          selects = selects.unionAll(select);
        }
      }
      ctx.formatIndentStart().formatNewLine().subquery(true).visit(selects).subquery(subquery).formatIndentEnd().formatNewLine();
      break;
    case CUBRID: 
    case DERBY: 
    case HSQLDB: 
    case POSTGRES: 
    default: 
      ctx.start(Clause.TABLE_VALUES).keyword("values").formatIndentLockStart();
      
      boolean firstRow = true;
      for (Row row : this.rows)
      {
        if (!firstRow) {
          ctx.sql(",").formatSeparator();
        }
        ctx.visit(row);
        firstRow = false;
      }
      ctx.formatIndentLockEnd().end(Clause.TABLE_VALUES);
      break;
    }
  }
  
  final Fields<R> fields0()
  {
    return new Fields(this.rows[0].fields());
  }
}

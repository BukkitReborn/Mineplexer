package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.SelectSelectStep;
import org.jooq.Table;

class Dual
  extends AbstractTable<Record>
{
  private static final long serialVersionUID = -7492790780048090156L;
  private static final Table<Record> FORCED_DUAL = DSL.select(new Field[] { DSL.inline("X").as("DUMMY") }).asTable("DUAL");
  static final String DUAL_HSQLDB = "select 1 as dual from information_schema.system_users limit 1";
  private final boolean force;
  
  Dual()
  {
    this(false);
  }
  
  Dual(boolean force)
  {
    super("dual", (Schema)null);
    
    this.force = force;
  }
  
  public final Class<? extends Record> getRecordType()
  {
    return RecordImpl.class;
  }
  
  public final Table<Record> as(String alias)
  {
    if (this.force) {
      return FORCED_DUAL.as(alias);
    }
    return new TableAlias(this, alias);
  }
  
  public final Table<Record> as(String alias, String... fieldAliases)
  {
    if (this.force) {
      return FORCED_DUAL.as(alias, fieldAliases);
    }
    return new TableAlias(this, alias, fieldAliases);
  }
  
  public boolean declaresTables()
  {
    return true;
  }
  
  public final void accept(Context<?> ctx)
  {
    if (this.force) {
      ctx.visit(FORCED_DUAL);
    } else {
      switch (ctx.configuration().dialect().family())
      {
      case POSTGRES: 
      case SQLITE: 
        break;
      case FIREBIRD: 
        ctx.literal("RDB$DATABASE");
        break;
      case HSQLDB: 
        ctx.sql("(").sql("select 1 as dual from information_schema.system_users limit 1").sql(") as dual");
        break;
      case CUBRID: 
        ctx.literal("db_root");
        break;
      case DERBY: 
        ctx.literal("SYSIBM").sql(".").literal("SYSDUMMY1");
        break;
      default: 
        ctx.keyword("dual");
      }
    }
  }
  
  final Fields<Record> fields0()
  {
    return new Fields(new Field[0]);
  }
}

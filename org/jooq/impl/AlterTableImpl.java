package org.jooq.impl;

import org.jooq.AlterTableAlterStep;
import org.jooq.AlterTableDropStep;
import org.jooq.AlterTableFinalStep;
import org.jooq.AlterTableStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Table;

class AlterTableImpl
  extends AbstractQuery
  implements AlterTableStep, AlterTableDropStep, AlterTableAlterStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.ALTER_TABLE };
  private final Table<?> table;
  private Field<?> add;
  private DataType<?> addType;
  private Field<?> alter;
  private DataType<?> alterType;
  private Field<?> alterDefault;
  private Field<?> drop;
  private boolean dropCascade;
  
  AlterTableImpl(Configuration configuration, Table<?> table)
  {
    super(configuration);
    
    this.table = table;
  }
  
  public final AlterTableImpl add(String field, DataType<?> type)
  {
    return add(DSL.fieldByName(type, new String[] { field }), type);
  }
  
  public final <T> AlterTableImpl add(Field<T> field, DataType<T> type)
  {
    this.add = field;
    this.addType = type;
    return this;
  }
  
  public final AlterTableImpl alter(String field)
  {
    return alter(DSL.fieldByName(new String[] { field }));
  }
  
  public final <T> AlterTableImpl alter(Field<T> field)
  {
    this.alter = field;
    return this;
  }
  
  public final AlterTableImpl set(DataType type)
  {
    this.alterType = type;
    return this;
  }
  
  public final AlterTableImpl defaultValue(Object literal)
  {
    return defaultValue(DSL.inline(literal));
  }
  
  public final AlterTableImpl defaultValue(Field expression)
  {
    this.alterDefault = expression;
    return this;
  }
  
  public final AlterTableImpl drop(String field)
  {
    return drop(DSL.fieldByName(new String[] { field }));
  }
  
  public final AlterTableImpl drop(Field<?> field)
  {
    this.drop = field;
    return this;
  }
  
  public final AlterTableFinalStep cascade()
  {
    this.dropCascade = true;
    return this;
  }
  
  public final AlterTableFinalStep restrict()
  {
    this.dropCascade = false;
    return this;
  }
  
  public final void accept(Context<?> ctx)
  {
    SQLDialect family = ctx.configuration().dialect().family();
    
    accept0(ctx);
  }
  
  private final void accept0(Context<?> ctx)
  {
    SQLDialect family = ctx.configuration().dialect().family();
    
    ctx.start(Clause.ALTER_TABLE_TABLE)
      .keyword("alter table").sql(" ").visit(this.table)
      .end(Clause.ALTER_TABLE_TABLE);
    if (this.add != null)
    {
      ctx.start(Clause.ALTER_TABLE_ADD).sql(" ").keyword("add").sql(" ").qualify(false).visit(this.add).sql(" ").qualify(true).keyword(this.addType.getCastTypeName(ctx.configuration()));
      if (!this.addType.nullable()) {
        ctx.sql(" ").keyword("not null");
      } else if (family != SQLDialect.FIREBIRD) {
        ctx.sql(" ").keyword("null");
      }
      ctx.end(Clause.ALTER_TABLE_ADD);
    }
    else if (this.alter != null)
    {
      ctx.start(Clause.ALTER_TABLE_ALTER);
      switch (family)
      {
      case MARIADB: 
      case MYSQL: 
        ctx.sql(" ").keyword("change column").sql(" ").qualify(false).visit(this.alter).qualify(true);
        break;
      default: 
        ctx.sql(" ").keyword("alter");
      }
      ctx.sql(" ").qualify(false).visit(this.alter).qualify(true);
      if (this.alterType != null)
      {
        switch (family)
        {
        case POSTGRES: 
          ctx.sql(" ").keyword("type");
        }
        ctx.sql(" ").keyword(this.alterType.getCastTypeName(ctx.configuration()));
        if (!this.alterType.nullable()) {
          ctx.sql(" ").keyword("not null");
        }
      }
      else if (this.alterDefault != null)
      {
        ctx.start(Clause.ALTER_TABLE_ALTER_DEFAULT);
        switch (family)
        {
        }
        ctx.sql(" ").keyword("set default");
        
        ctx.sql(" ").visit(this.alterDefault)
          .end(Clause.ALTER_TABLE_ALTER_DEFAULT);
      }
      ctx.end(Clause.ALTER_TABLE_ALTER);
    }
    else if (this.drop != null)
    {
      ctx.start(Clause.ALTER_TABLE_DROP);
      switch (family)
      {
      }
      ctx.sql(" ").keyword("drop");
      
      ctx.sql(" ")
        .qualify(false)
        .visit(this.drop)
        .qualify(true);
      if (this.dropCascade) {
        ctx.sql(" ").keyword("cascade");
      }
      ctx.end(Clause.ALTER_TABLE_DROP);
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

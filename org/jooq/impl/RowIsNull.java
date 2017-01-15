package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPartInternal;
import org.jooq.Row;
import org.jooq.SQLDialect;

class RowIsNull
  extends AbstractCondition
{
  private static final long serialVersionUID = -1806139685201770706L;
  private static final Clause[] CLAUSES_NULL = { Clause.CONDITION, Clause.CONDITION_IS_NULL };
  private static final Clause[] CLAUSES_NOT_NULL = { Clause.CONDITION, Clause.CONDITION_IS_NOT_NULL };
  private final Row row;
  private final boolean isNull;
  
  RowIsNull(Row row, boolean isNull)
  {
    this.row = row;
    this.isNull = isNull;
  }
  
  public final void accept(Context<?> ctx)
  {
    delegate(ctx.configuration()).accept(ctx);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return delegate(ctx.configuration()).clauses(ctx);
  }
  
  private final QueryPartInternal delegate(Configuration configuration)
  {
    if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE }).contains(configuration.dialect().family()))
    {
      List<Condition> conditions = new ArrayList();
      for (Field<?> field : this.row.fields()) {
        conditions.add(this.isNull ? field.isNull() : field.isNotNull());
      }
      Object result = new CombinedCondition(Operator.AND, conditions);
      return (QueryPartInternal)result;
    }
    return new Native(null);
  }
  
  private class Native
    extends AbstractCondition
  {
    private static final long serialVersionUID = -2977241780111574353L;
    
    private Native() {}
    
    public final void accept(Context<?> ctx)
    {
      ctx.visit(RowIsNull.this.row).sql(" ").keyword(RowIsNull.this.isNull ? "is null" : "is not null");
    }
    
    public final Clause[] clauses(Context<?> ctx)
    {
      return RowIsNull.this.isNull ? RowIsNull.CLAUSES_NULL : RowIsNull.CLAUSES_NOT_NULL;
    }
  }
}

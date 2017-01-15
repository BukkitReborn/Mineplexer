package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DeleteQuery;
import org.jooq.Operator;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;

class DeleteQueryImpl<R extends Record>
  extends AbstractQuery
  implements DeleteQuery<R>
{
  private static final long serialVersionUID = -1943687511774150929L;
  private static final Clause[] CLAUSES = { Clause.DELETE };
  private final Table<R> table;
  private final ConditionProviderImpl condition;
  
  DeleteQueryImpl(Configuration configuration, Table<R> table)
  {
    super(configuration);
    
    this.table = table;
    this.condition = new ConditionProviderImpl();
  }
  
  final Table<R> getFrom()
  {
    return this.table;
  }
  
  final Condition getWhere()
  {
    return this.condition.getWhere();
  }
  
  public final void addConditions(Collection<? extends Condition> conditions)
  {
    this.condition.addConditions(conditions);
  }
  
  public final void addConditions(Condition... conditions)
  {
    this.condition.addConditions(conditions);
  }
  
  public final void addConditions(Operator operator, Condition... conditions)
  {
    this.condition.addConditions(operator, conditions);
  }
  
  public final void addConditions(Operator operator, Collection<? extends Condition> conditions)
  {
    this.condition.addConditions(operator, conditions);
  }
  
  public final void accept(Context<?> ctx)
  {
    boolean declare = ctx.declareTables();
    
    ctx.start(Clause.DELETE_DELETE)
      .keyword("delete").sql(" ");
    if (Arrays.asList(new SQLDialect[] { SQLDialect.MARIADB, SQLDialect.MYSQL }).contains(ctx.configuration().dialect())) {
      if (((getFrom() instanceof TableAlias)) || (
        ((getFrom() instanceof TableImpl)) && (((TableImpl)getFrom()).getAliasedTable() != null))) {
        ctx.visit(getFrom()).sql(" ");
      }
    }
    ctx.keyword("from").sql(" ").declareTables(true).visit(getFrom()).declareTables(declare).end(Clause.DELETE_DELETE).start(Clause.DELETE_WHERE);
    if (!(getWhere() instanceof TrueCondition)) {
      ctx.formatSeparator().keyword("where").sql(" ").visit(getWhere());
    }
    ctx.end(Clause.DELETE_WHERE);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

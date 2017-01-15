package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.SQLDialect;
import org.jooq.SelectSelectStep;

class IsDistinctFrom<T>
  extends AbstractCondition
{
  private static final long serialVersionUID = 4568269684824736461L;
  private final Field<T> lhs;
  private final Field<T> rhs;
  private final Comparator comparator;
  private transient QueryPartInternal mySQLCondition;
  private transient QueryPartInternal sqliteCondition;
  private transient QueryPartInternal compareCondition;
  private transient QueryPartInternal caseExpression;
  
  IsDistinctFrom(Field<T> lhs, Field<T> rhs, Comparator comparator)
  {
    this.lhs = lhs;
    this.rhs = rhs;
    this.comparator = comparator;
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
    if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.DERBY }).contains(configuration.dialect().family())) {
      return this.comparator == Comparator.IS_DISTINCT_FROM ? (QueryPartInternal)DSL.notExists(DSL.select(this.lhs).intersect(DSL.select(this.rhs))) : (QueryPartInternal)DSL.exists(DSL.select(this.lhs).intersect(DSL.select(this.rhs)));
    }
    if (Arrays.asList(new Object[0]).contains(configuration.dialect().family()))
    {
      if (this.caseExpression == null) {
        if (this.comparator == Comparator.IS_DISTINCT_FROM) {
          this.caseExpression = ((QueryPartInternal)DSL.decode().when(this.lhs.isNull().and(this.rhs.isNull()), DSL.zero()).when(this.lhs.isNull().and(this.rhs.isNotNull()), DSL.one()).when(this.lhs.isNotNull().and(this.rhs.isNull()), DSL.one()).when(this.lhs.equal(this.rhs), DSL.zero()).otherwise(DSL.one()).equal(DSL.one()));
        } else {
          this.caseExpression = ((QueryPartInternal)DSL.decode().when(this.lhs.isNull().and(this.rhs.isNull()), DSL.one()).when(this.lhs.isNull().and(this.rhs.isNotNull()), DSL.zero()).when(this.lhs.isNotNull().and(this.rhs.isNull()), DSL.zero()).when(this.lhs.equal(this.rhs), DSL.one()).otherwise(DSL.zero()).equal(DSL.one()));
        }
      }
      return this.caseExpression;
    }
    if (Arrays.asList(new SQLDialect[] { SQLDialect.MARIADB, SQLDialect.MYSQL }).contains(configuration.dialect()))
    {
      if (this.mySQLCondition == null) {
        if (this.comparator == Comparator.IS_DISTINCT_FROM) {
          this.mySQLCondition = ((QueryPartInternal)DSL.condition("{not}({0} <=> {1})", new QueryPart[] { this.lhs, this.rhs }));
        } else {
          this.mySQLCondition = ((QueryPartInternal)DSL.condition("{0} <=> {1}", new QueryPart[] { this.lhs, this.rhs }));
        }
      }
      return this.mySQLCondition;
    }
    if (SQLDialect.SQLITE == configuration.dialect())
    {
      if (this.sqliteCondition == null) {
        if (this.comparator == Comparator.IS_DISTINCT_FROM) {
          this.sqliteCondition = ((QueryPartInternal)DSL.condition("{0} {is not} {1}", new QueryPart[] { this.lhs, this.rhs }));
        } else {
          this.sqliteCondition = ((QueryPartInternal)DSL.condition("{0} {is} {1}", new QueryPart[] { this.lhs, this.rhs }));
        }
      }
      return this.sqliteCondition;
    }
    if (this.compareCondition == null) {
      this.compareCondition = new CompareCondition(this.lhs, this.rhs, this.comparator);
    }
    return this.compareCondition;
  }
}

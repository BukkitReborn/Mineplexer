package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QuantifiedSelect;
import org.jooq.QueryPartInternal;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Row;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;

class RowSubqueryCondition
  extends AbstractCondition
{
  private static final long serialVersionUID = -1806139685201770706L;
  private static final Clause[] CLAUSES = { Clause.CONDITION, Clause.CONDITION_COMPARISON };
  private final Row left;
  private final Select<?> right;
  private final QuantifiedSelect<?> rightQuantified;
  private final Comparator comparator;
  
  RowSubqueryCondition(Row left, Select<?> right, Comparator comparator)
  {
    this.left = left;
    this.right = right;
    this.rightQuantified = null;
    this.comparator = comparator;
  }
  
  RowSubqueryCondition(Row left, QuantifiedSelect<?> right, Comparator comparator)
  {
    this.left = left;
    this.right = null;
    this.rightQuantified = right;
    this.comparator = comparator;
  }
  
  public final void accept(Context<?> ctx)
  {
    delegate(ctx).accept(ctx);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return delegate(ctx).clauses(ctx);
  }
  
  private final QueryPartInternal delegate(Context<?> ctx)
  {
    Configuration configuration = ctx.configuration();
    RenderContext render = (ctx instanceof RenderContext) ? (RenderContext)ctx : null;
    
    SQLDialect family = configuration.dialect().family();
    if (this.rightQuantified != null) {
      return new Native(null);
    }
    if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES }).contains(family)) {
      return new Native(null);
    }
    if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES }).contains(family)) {
      if (Arrays.asList(new Comparator[] { Comparator.EQUALS, Comparator.NOT_EQUALS }).contains(this.comparator)) {
        return new Native(null);
      }
    }
    if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES }).contains(family)) {
      if (Arrays.asList(new Comparator[] { Comparator.IN, Comparator.NOT_IN }).contains(this.comparator)) {
        return new Native(null);
      }
    }
    String table = render == null ? "t" : render.nextAlias();
    
    List<String> names = new ArrayList();
    for (int i = 0; i < this.left.size(); i++) {
      names.add(table + "_" + i);
    }
    Field<?>[] fields = new Field[names.size()];
    for (int i = 0; i < fields.length; i++) {
      fields[i] = DSL.fieldByName(new String[] { table, (String)names.get(i) });
    }
    Condition condition;
    Condition condition;
    Condition condition;
    Condition condition;
    Condition condition;
    switch (this.comparator)
    {
    case GREATER: 
      condition = ((RowN)this.left).gt(DSL.row(fields));
      break;
    case GREATER_OR_EQUAL: 
      condition = ((RowN)this.left).ge(DSL.row(fields));
      break;
    case LESS: 
      condition = ((RowN)this.left).lt(DSL.row(fields));
      break;
    case LESS_OR_EQUAL: 
      condition = ((RowN)this.left).le(DSL.row(fields));
      break;
    case IN: 
    case EQUALS: 
    case NOT_IN: 
    case NOT_EQUALS: 
    default: 
      condition = ((RowN)this.left).eq(DSL.row(fields));
    }
    Select<Record> subselect = DSL.select(new Field[0]).from(this.right.asTable(table, (String[])names.toArray(new String[0]))).where(new Condition[] { condition });
    switch (this.comparator)
    {
    case NOT_IN: 
    case NOT_EQUALS: 
      return (QueryPartInternal)DSL.notExists(subselect);
    }
    return (QueryPartInternal)DSL.exists(subselect);
  }
  
  private class Native
    extends AbstractCondition
  {
    private static final long serialVersionUID = -1552476981094856727L;
    
    private Native() {}
    
    public final void accept(Context<?> ctx)
    {
      boolean parentheses = RowSubqueryCondition.this.rightQuantified == null;
      
      boolean extraParentheses = Arrays.asList(new Object[0]).contains(ctx.configuration().dialect().family());
      boolean subquery = ctx.subquery();
      
      ctx.visit(RowSubqueryCondition.this.left)
        .sql(" ")
        .keyword(RowSubqueryCondition.this.comparator.toSQL())
        .sql(" ")
        .sql(parentheses ? "(" : "")
        .sql(extraParentheses ? "(" : "");
      ctx.data("org.jooq.configuration.row-value-expression-subquery", Boolean.valueOf(true));
      ctx.subquery(true)
        .visit(RowSubqueryCondition.this.right != null ? RowSubqueryCondition.this.right : RowSubqueryCondition.this.rightQuantified)
        .subquery(subquery);
      ctx.data("org.jooq.configuration.row-value-expression-subquery", null);
      ctx.sql(extraParentheses ? ")" : "")
        .sql(parentheses ? ")" : "");
    }
    
    public final Clause[] clauses(Context<?> ctx)
    {
      return RowSubqueryCondition.CLAUSES;
    }
  }
}

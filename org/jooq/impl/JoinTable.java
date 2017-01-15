package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.JoinType;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnConditionStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.UniqueKey;
import org.jooq.exception.DataAccessException;

class JoinTable
  extends AbstractTable<Record>
  implements TableOptionalOnStep, TableOnConditionStep
{
  private static final long serialVersionUID = 8377996833996498178L;
  private static final Clause[] CLAUSES = { Clause.TABLE, Clause.TABLE_JOIN };
  private final Table<?> lhs;
  private final Table<?> rhs;
  private final QueryPartList<Field<?>> rhsPartitionBy;
  private final JoinType type;
  private final ConditionProviderImpl condition;
  private final QueryPartList<Field<?>> using;
  
  JoinTable(TableLike<?> lhs, TableLike<?> rhs, JoinType type)
  {
    super("join");
    
    this.lhs = lhs.asTable();
    this.rhs = rhs.asTable();
    this.rhsPartitionBy = new QueryPartList();
    this.type = type;
    
    this.condition = new ConditionProviderImpl();
    this.using = new QueryPartList();
  }
  
  public final List<ForeignKey<Record, ?>> getReferences()
  {
    List<ForeignKey<?, ?>> result = new ArrayList();
    
    result.addAll(this.lhs.getReferences());
    result.addAll(this.rhs.getReferences());
    
    return result;
  }
  
  public final void accept(Context<?> ctx)
  {
    JoinType translatedType = translateType(ctx);
    Clause translatedClause = translateClause(translatedType);
    
    String keyword = translatedType.toSQL();
    
    toSQLTable(ctx, this.lhs);
    
    ctx.formatIndentStart()
      .formatSeparator()
      .start(translatedClause)
      .keyword(keyword)
      .sql(" ");
    
    toSQLTable(ctx, this.rhs);
    if (!this.rhsPartitionBy.isEmpty()) {
      ctx.formatSeparator().start(Clause.TABLE_JOIN_PARTITION_BY).keyword("partition by").sql(" (").visit(this.rhsPartitionBy).sql(")").end(Clause.TABLE_JOIN_PARTITION_BY);
    }
    if (!Arrays.asList(new JoinType[] { JoinType.CROSS_JOIN, JoinType.NATURAL_JOIN, JoinType.NATURAL_LEFT_OUTER_JOIN, JoinType.NATURAL_RIGHT_OUTER_JOIN, JoinType.CROSS_APPLY, JoinType.OUTER_APPLY }).contains(translatedType)) {
      toSQLJoinCondition(ctx);
    }
    ctx.end(translatedClause).formatIndentEnd();
  }
  
  private void toSQLTable(Context<?> ctx, Table<?> table)
  {
    boolean wrap = ((table instanceof JoinTable)) && ((table == this.rhs) || (Arrays.asList(new Object[0]).contains(ctx.configuration().dialect().family())));
    if (wrap) {
      ctx.sql("(").formatIndentStart().formatNewLine();
    }
    ctx.visit(table);
    if (wrap) {
      ctx.formatIndentEnd().formatNewLine().sql(")");
    }
  }
  
  final Clause translateClause(JoinType translatedType)
  {
    switch (translatedType)
    {
    case JOIN: 
      return Clause.TABLE_JOIN_INNER;
    case CROSS_JOIN: 
      return Clause.TABLE_JOIN_CROSS;
    case NATURAL_JOIN: 
      return Clause.TABLE_JOIN_NATURAL;
    case LEFT_OUTER_JOIN: 
      return Clause.TABLE_JOIN_OUTER_LEFT;
    case RIGHT_OUTER_JOIN: 
      return Clause.TABLE_JOIN_OUTER_RIGHT;
    case FULL_OUTER_JOIN: 
      return Clause.TABLE_JOIN_OUTER_FULL;
    case NATURAL_LEFT_OUTER_JOIN: 
      return Clause.TABLE_JOIN_NATURAL_OUTER_LEFT;
    case NATURAL_RIGHT_OUTER_JOIN: 
      return Clause.TABLE_JOIN_NATURAL_OUTER_RIGHT;
    case CROSS_APPLY: 
      return Clause.TABLE_JOIN_CROSS_APPLY;
    case OUTER_APPLY: 
      return Clause.TABLE_JOIN_OUTER_APPLY;
    }
    throw new IllegalArgumentException("Bad join type: " + translatedType);
  }
  
  final JoinType translateType(Context<?> context)
  {
    if (emulateCrossJoin(context)) {
      return JoinType.JOIN;
    }
    if (emulateNaturalJoin(context)) {
      return JoinType.JOIN;
    }
    if (emulateNaturalLeftOuterJoin(context)) {
      return JoinType.LEFT_OUTER_JOIN;
    }
    if (emulateNaturalRightOuterJoin(context)) {
      return JoinType.RIGHT_OUTER_JOIN;
    }
    return this.type;
  }
  
  private final boolean emulateCrossJoin(Context<?> context)
  {
    return (this.type == JoinType.CROSS_JOIN) && (Arrays.asList(new Object[0]).contains(context.configuration().dialect().family()));
  }
  
  private final boolean emulateNaturalJoin(Context<?> context)
  {
    if (this.type == JoinType.NATURAL_JOIN) {}
    return Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID }).contains(context.configuration().dialect().family());
  }
  
  private final boolean emulateNaturalLeftOuterJoin(Context<?> context)
  {
    if (this.type == JoinType.NATURAL_LEFT_OUTER_JOIN) {}
    return Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.H2 }).contains(context.family());
  }
  
  private final boolean emulateNaturalRightOuterJoin(Context<?> context)
  {
    if (this.type == JoinType.NATURAL_RIGHT_OUTER_JOIN) {}
    return Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.H2 }).contains(context.family());
  }
  
  private final void toSQLJoinCondition(Context<?> context)
  {
    Object localObject;
    Field<?> field;
    if (!this.using.isEmpty())
    {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.H2 }).contains(context.family()))
      {
        boolean first = true;
        for (localObject = this.using.iterator(); ((Iterator)localObject).hasNext();)
        {
          field = (Field)((Iterator)localObject).next();
          context.formatSeparator();
          if (first)
          {
            first = false;
            
            context.start(Clause.TABLE_JOIN_ON)
              .keyword("on");
          }
          else
          {
            context.keyword("and");
          }
          context.sql(" ").visit(this.lhs.field(field)).sql(" = ").visit(this.rhs.field(field));
        }
        context.end(Clause.TABLE_JOIN_ON);
      }
      else
      {
        context.formatSeparator().start(Clause.TABLE_JOIN_USING).keyword("using").sql("(");
        Utils.fieldNames(context, this.using);
        context.sql(")")
          .end(Clause.TABLE_JOIN_USING);
      }
    }
    else if ((emulateNaturalJoin(context)) || 
      (emulateNaturalLeftOuterJoin(context)) || 
      (emulateNaturalRightOuterJoin(context)))
    {
      boolean first = true;
      localObject = this.lhs.fields();field = localObject.length;
      for (Field<?> localField1 = 0; localField1 < field; localField1++)
      {
        Field<?> field = localObject[localField1];
        Field<?> other = this.rhs.field(field);
        if (other != null)
        {
          context.formatSeparator();
          if (first)
          {
            first = false;
            
            context.start(Clause.TABLE_JOIN_ON)
              .keyword("on");
          }
          else
          {
            context.keyword("and");
          }
          context.sql(" ").visit(field).sql(" = ").visit(other);
        }
      }
      context.end(Clause.TABLE_JOIN_ON);
    }
    else
    {
      context.formatSeparator().start(Clause.TABLE_JOIN_ON).keyword("on").sql(" ").visit(this.condition).end(Clause.TABLE_JOIN_ON);
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final Table<Record> as(String alias)
  {
    return new TableAlias(this, alias, true);
  }
  
  public final Table<Record> as(String alias, String... fieldAliases)
  {
    return new TableAlias(this, alias, fieldAliases, true);
  }
  
  public final Class<? extends Record> getRecordType()
  {
    return RecordImpl.class;
  }
  
  final Fields<Record> fields0()
  {
    Field<?>[] l = this.lhs.asTable().fields();
    Field<?>[] r = this.rhs.asTable().fields();
    Field<?>[] all = new Field[l.length + r.length];
    
    System.arraycopy(l, 0, all, 0, l.length);
    System.arraycopy(r, 0, all, l.length, r.length);
    
    return new Fields(all);
  }
  
  public final boolean declaresTables()
  {
    return true;
  }
  
  public final JoinTable on(Condition... conditions)
  {
    this.condition.addConditions(conditions);
    return this;
  }
  
  public final JoinTable on(Field<Boolean> c)
  {
    return on(new Condition[] { DSL.condition(c) });
  }
  
  public final JoinTable on(String sql)
  {
    and(sql);
    return this;
  }
  
  public final JoinTable on(String sql, Object... bindings)
  {
    and(sql, bindings);
    return this;
  }
  
  public final JoinTable on(String sql, QueryPart... parts)
  {
    and(sql, parts);
    return this;
  }
  
  public final JoinTable onKey()
    throws DataAccessException
  {
    List<?> leftToRight = this.lhs.getReferencesTo(this.rhs);
    List<?> rightToLeft = this.rhs.getReferencesTo(this.lhs);
    if ((leftToRight.size() == 1) && (rightToLeft.size() == 0)) {
      return onKey((ForeignKey)leftToRight.get(0));
    }
    if ((rightToLeft.size() == 1) && (leftToRight.size() == 0)) {
      return onKey((ForeignKey)rightToLeft.get(0));
    }
    throw onKeyException();
  }
  
  public final JoinTable onKey(TableField<?, ?>... keyFields)
    throws DataAccessException
  {
    if ((keyFields != null) && (keyFields.length > 0)) {
      if (keyFields[0].getTable().equals(this.lhs)) {
        for (ForeignKey<?, ?> key : this.lhs.getReferences()) {
          if (key.getFields().containsAll(Arrays.asList(keyFields))) {
            return onKey(key);
          }
        }
      } else if (keyFields[0].getTable().equals(this.rhs)) {
        for (ForeignKey<?, ?> key : this.rhs.getReferences()) {
          if (key.getFields().containsAll(Arrays.asList(keyFields))) {
            return onKey(key);
          }
        }
      }
    }
    throw onKeyException();
  }
  
  public final JoinTable onKey(ForeignKey<?, ?> key)
  {
    JoinTable result = this;
    
    TableField<?, ?>[] references = key.getFieldsArray();
    TableField<?, ?>[] referenced = key.getKey().getFieldsArray();
    for (int i = 0; i < references.length; i++)
    {
      Field f1 = references[i];
      Field f2 = referenced[i];
      
      result.and(f1.equal(f2));
    }
    return result;
  }
  
  private final DataAccessException onKeyException()
  {
    return new DataAccessException("Key ambiguous between tables " + this.lhs + " and " + this.rhs);
  }
  
  public final JoinTable using(Field<?>... fields)
  {
    return using(Arrays.asList(fields));
  }
  
  public final JoinTable using(Collection<? extends Field<?>> fields)
  {
    this.using.addAll(fields);
    return this;
  }
  
  public final JoinTable and(Condition c)
  {
    this.condition.addConditions(new Condition[] { c });
    return this;
  }
  
  public final JoinTable and(Field<Boolean> c)
  {
    return and(DSL.condition(c));
  }
  
  public final JoinTable and(String sql)
  {
    return and(DSL.condition(sql));
  }
  
  public final JoinTable and(String sql, Object... bindings)
  {
    return and(DSL.condition(sql, bindings));
  }
  
  public final JoinTable and(String sql, QueryPart... parts)
  {
    return and(DSL.condition(sql, parts));
  }
  
  public final JoinTable andNot(Condition c)
  {
    return and(c.not());
  }
  
  public final JoinTable andNot(Field<Boolean> c)
  {
    return andNot(DSL.condition(c));
  }
  
  public final JoinTable andExists(Select<?> select)
  {
    return and(DSL.exists(select));
  }
  
  public final JoinTable andNotExists(Select<?> select)
  {
    return and(DSL.notExists(select));
  }
  
  public final JoinTable or(Condition c)
  {
    this.condition.addConditions(Operator.OR, new Condition[] { c });
    return this;
  }
  
  public final JoinTable or(Field<Boolean> c)
  {
    return or(DSL.condition(c));
  }
  
  public final JoinTable or(String sql)
  {
    return or(DSL.condition(sql));
  }
  
  public final JoinTable or(String sql, Object... bindings)
  {
    return or(DSL.condition(sql, bindings));
  }
  
  public final JoinTable or(String sql, QueryPart... parts)
  {
    return or(DSL.condition(sql, parts));
  }
  
  public final JoinTable orNot(Condition c)
  {
    return or(c.not());
  }
  
  public final JoinTable orNot(Field<Boolean> c)
  {
    return orNot(DSL.condition(c));
  }
  
  public final JoinTable orExists(Select<?> select)
  {
    return or(DSL.exists(select));
  }
  
  public final JoinTable orNotExists(Select<?> select)
  {
    return or(DSL.notExists(select));
  }
}

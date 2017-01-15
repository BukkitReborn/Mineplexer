package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.jooq.AggregateFunction;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.OrderedAggregateFunction;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.WindowBeforeOverStep;
import org.jooq.WindowDefinition;
import org.jooq.WindowFinalStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOrderByStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.WindowRowsAndStep;
import org.jooq.WindowRowsStep;
import org.jooq.WindowSpecification;

class Function<T>
  extends AbstractField<T>
  implements OrderedAggregateFunction<T>, AggregateFunction<T>, WindowBeforeOverStep<T>, WindowIgnoreNullsStep<T>, WindowPartitionByStep<T>, WindowRowsStep<T>, WindowRowsAndStep<T>
{
  private static final long serialVersionUID = 347252741712134044L;
  private final Name name;
  private final Term term;
  private final QueryPartList<QueryPart> arguments;
  private final boolean distinct;
  private final SortFieldList withinGroupOrderBy;
  private final SortFieldList keepDenseRankOrderBy;
  private WindowSpecificationImpl windowSpecification;
  private WindowDefinitionImpl windowDefinition;
  private Name windowName;
  private boolean first;
  private boolean ignoreNulls;
  private boolean respectNulls;
  
  Function(String name, DataType<T> type, QueryPart... arguments)
  {
    this(name, false, type, arguments);
  }
  
  Function(Term term, DataType<T> type, QueryPart... arguments)
  {
    this(term, false, type, arguments);
  }
  
  Function(Name name, DataType<T> type, QueryPart... arguments)
  {
    this(name, false, type, arguments);
  }
  
  Function(String name, boolean distinct, DataType<T> type, QueryPart... arguments)
  {
    super(name, type);
    
    this.term = null;
    this.name = null;
    this.distinct = distinct;
    this.arguments = new QueryPartList(arguments);
    this.keepDenseRankOrderBy = new SortFieldList();
    this.withinGroupOrderBy = new SortFieldList();
  }
  
  Function(Term term, boolean distinct, DataType<T> type, QueryPart... arguments)
  {
    super(term.name().toLowerCase(), type);
    
    this.term = term;
    this.name = null;
    this.distinct = distinct;
    this.arguments = new QueryPartList(arguments);
    this.keepDenseRankOrderBy = new SortFieldList();
    this.withinGroupOrderBy = new SortFieldList();
  }
  
  Function(Name name, boolean distinct, DataType<T> type, QueryPart... arguments)
  {
    super(last(name.getName()), type);
    
    this.term = null;
    this.name = name;
    this.distinct = distinct;
    this.arguments = new QueryPartList(arguments);
    this.keepDenseRankOrderBy = new SortFieldList();
    this.withinGroupOrderBy = new SortFieldList();
  }
  
  private static String last(String... strings)
  {
    if ((strings != null) && (strings.length > 0)) {
      return strings[(strings.length - 1)];
    }
    return null;
  }
  
  public final void accept(Context<?> ctx)
  {
    if (this.term == Term.LIST_AGG) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL }).contains(ctx.configuration().dialect()))
      {
        toSQLGroupConcat(ctx); return;
      }
    }
    if (this.term == Term.LIST_AGG) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.POSTGRES }).contains(ctx.configuration().dialect()))
      {
        toSQLStringAgg(ctx);
        toSQLOverClause(ctx); return;
      }
    }
    toSQLArguments(ctx);
    toSQLKeepDenseRankOrderByClause(ctx);
    toSQLWithinGroupClause(ctx);
    toSQLOverClause(ctx);
  }
  
  private void toSQLStringAgg(Context<?> ctx)
  {
    toSQLFunctionName(ctx);
    ctx.sql("(");
    if (this.distinct) {
      ctx.keyword("distinct").sql(" ");
    }
    ctx.visit(((Field)this.arguments.get(0)).cast(String.class));
    if (this.arguments.size() > 1)
    {
      ctx.sql(", ");
      ctx.visit(this.arguments.get(1));
    }
    else
    {
      ctx.sql(", ''");
    }
    if (!this.withinGroupOrderBy.isEmpty()) {
      ctx.sql(" ").keyword("order by").sql(" ").visit(this.withinGroupOrderBy);
    }
    ctx.sql(")");
  }
  
  private final void toSQLGroupConcat(Context<?> ctx)
  {
    toSQLFunctionName(ctx);
    ctx.sql("(");
    if (this.distinct) {
      ctx.keyword("distinct").sql(" ");
    }
    ctx.visit(this.arguments.get(0));
    if (!this.withinGroupOrderBy.isEmpty()) {
      ctx.sql(" ").keyword("order by").sql(" ").visit(this.withinGroupOrderBy);
    }
    if (this.arguments.size() > 1) {
      ctx.sql(" ").keyword("separator").sql(" ").visit(this.arguments.get(1));
    }
    ctx.sql(")");
  }
  
  private final void toSQLOverClause(Context<?> ctx)
  {
    QueryPart window = window(ctx);
    if (window == null) {
      return;
    }
    if ((this.term == Term.ROW_NUMBER) && (ctx.configuration().dialect() == SQLDialect.HSQLDB)) {
      return;
    }
    ctx.sql(" ").keyword("over").sql(" (").visit(window).sql(")");
  }
  
  private final QueryPart window(Context<?> ctx)
  {
    if (this.windowSpecification != null) {
      return this.windowSpecification;
    }
    if (this.windowDefinition != null) {
      return this.windowDefinition;
    }
    if (this.windowName != null)
    {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.POSTGRES }).contains(ctx.configuration().dialect().family())) {
        return this.windowName;
      }
      Map<Object, Object> map = (Map)ctx.data("org.jooq.configuration.locally-scoped-data-map");
      QueryPartList<WindowDefinition> windows = (QueryPartList)map.get("org.jooq.configuration.local-window-definitions");
      if (windows != null) {
        for (WindowDefinition window : windows) {
          if (((WindowDefinitionImpl)window).getName().equals(this.windowName)) {
            return window;
          }
        }
      } else {
        return this.windowName;
      }
    }
    return null;
  }
  
  private void toSQLKeepDenseRankOrderByClause(Context<?> ctx)
  {
    if (!this.keepDenseRankOrderBy.isEmpty()) {
      ctx.sql(" ").keyword("keep").sql(" (").keyword("dense_rank").sql(" ").keyword(this.first ? "first" : "last").sql(" ").keyword("order by").sql(" ").visit(this.keepDenseRankOrderBy).sql(")");
    }
  }
  
  private final void toSQLWithinGroupClause(Context<?> ctx)
  {
    if (!this.withinGroupOrderBy.isEmpty()) {
      ctx.sql(" ").keyword("within group").sql(" (").keyword("order by").sql(" ").visit(this.withinGroupOrderBy).sql(")");
    }
  }
  
  private final void toSQLArguments(Context<?> ctx)
  {
    toSQLFunctionName(ctx);
    ctx.sql("(");
    if (this.distinct)
    {
      ctx.keyword("distinct");
      if ((ctx.configuration().dialect().family() == SQLDialect.POSTGRES) && (this.arguments.size() > 1)) {
        ctx.sql("(");
      } else {
        ctx.sql(" ");
      }
    }
    if (!this.arguments.isEmpty()) {
      ctx.visit(this.arguments);
    }
    if ((this.distinct) && 
      (ctx.configuration().dialect().family() == SQLDialect.POSTGRES) && (this.arguments.size() > 1)) {
      ctx.sql(")");
    }
    if (this.ignoreNulls) {
      ctx.sql(" ").keyword("ignore nulls");
    } else if (this.respectNulls) {
      ctx.sql(" ").keyword("respect nulls");
    }
    ctx.sql(")");
  }
  
  private final void toSQLFunctionName(Context<?> ctx)
  {
    if (this.name != null) {
      ctx.visit(this.name);
    } else if (this.term != null) {
      ctx.sql(this.term.translate(ctx.configuration().dialect()));
    } else {
      ctx.sql(getName());
    }
  }
  
  final QueryPartList<QueryPart> getArguments()
  {
    return this.arguments;
  }
  
  public final AggregateFunction<T> withinGroupOrderBy(Field<?>... fields)
  {
    this.withinGroupOrderBy.addAll(fields);
    return this;
  }
  
  public final AggregateFunction<T> withinGroupOrderBy(SortField<?>... fields)
  {
    this.withinGroupOrderBy.addAll(Arrays.asList(fields));
    return this;
  }
  
  public final AggregateFunction<T> withinGroupOrderBy(Collection<? extends SortField<?>> fields)
  {
    this.withinGroupOrderBy.addAll(fields);
    return this;
  }
  
  public final WindowPartitionByStep<T> over()
  {
    this.windowSpecification = new WindowSpecificationImpl();
    return this;
  }
  
  public final WindowFinalStep<T> over(WindowSpecification specification)
  {
    this.windowSpecification = ((WindowSpecificationImpl)specification);
    return this;
  }
  
  public final WindowFinalStep<T> over(WindowDefinition definition)
  {
    this.windowDefinition = ((WindowDefinitionImpl)definition);
    return this;
  }
  
  public final WindowFinalStep<T> over(String n)
  {
    return over(DSL.name(new String[] { n }));
  }
  
  public final WindowFinalStep<T> over(Name n)
  {
    this.windowName = n;
    return this;
  }
  
  public final WindowOrderByStep<T> partitionBy(Field<?>... fields)
  {
    this.windowSpecification.partitionBy(fields);
    return this;
  }
  
  public final WindowOrderByStep<T> partitionByOne()
  {
    this.windowSpecification.partitionByOne();
    return this;
  }
  
  public final WindowRowsStep<T> orderBy(Field<?>... fields)
  {
    this.windowSpecification.orderBy(fields);
    return this;
  }
  
  public final WindowRowsStep<T> orderBy(SortField<?>... fields)
  {
    this.windowSpecification.orderBy(fields);
    return this;
  }
  
  public final WindowRowsStep<T> orderBy(Collection<? extends SortField<?>> fields)
  {
    this.windowSpecification.orderBy(fields);
    return this;
  }
  
  public final WindowFinalStep<T> rowsUnboundedPreceding()
  {
    this.windowSpecification.rowsUnboundedPreceding();
    return this;
  }
  
  public final WindowFinalStep<T> rowsPreceding(int number)
  {
    this.windowSpecification.rowsPreceding(number);
    return this;
  }
  
  public final WindowFinalStep<T> rowsCurrentRow()
  {
    this.windowSpecification.rowsCurrentRow();
    return this;
  }
  
  public final WindowFinalStep<T> rowsUnboundedFollowing()
  {
    this.windowSpecification.rowsUnboundedFollowing();
    return this;
  }
  
  public final WindowFinalStep<T> rowsFollowing(int number)
  {
    this.windowSpecification.rowsFollowing(number);
    return this;
  }
  
  public final WindowRowsAndStep<T> rowsBetweenUnboundedPreceding()
  {
    this.windowSpecification.rowsBetweenUnboundedPreceding();
    return this;
  }
  
  public final WindowRowsAndStep<T> rowsBetweenPreceding(int number)
  {
    this.windowSpecification.rowsBetweenPreceding(number);
    return this;
  }
  
  public final WindowRowsAndStep<T> rowsBetweenCurrentRow()
  {
    this.windowSpecification.rowsBetweenCurrentRow();
    return this;
  }
  
  public final WindowRowsAndStep<T> rowsBetweenUnboundedFollowing()
  {
    this.windowSpecification.rowsBetweenUnboundedFollowing();
    return this;
  }
  
  public final WindowRowsAndStep<T> rowsBetweenFollowing(int number)
  {
    this.windowSpecification.rowsBetweenFollowing(number);
    return this;
  }
  
  public final WindowFinalStep<T> andUnboundedPreceding()
  {
    this.windowSpecification.andUnboundedPreceding();
    return this;
  }
  
  public final WindowFinalStep<T> andPreceding(int number)
  {
    this.windowSpecification.andPreceding(number);
    return this;
  }
  
  public final WindowFinalStep<T> andCurrentRow()
  {
    this.windowSpecification.andCurrentRow();
    return this;
  }
  
  public final WindowFinalStep<T> andUnboundedFollowing()
  {
    this.windowSpecification.andUnboundedFollowing();
    return this;
  }
  
  public final WindowFinalStep<T> andFollowing(int number)
  {
    this.windowSpecification.andFollowing(number);
    return this;
  }
}

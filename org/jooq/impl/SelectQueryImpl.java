package org.jooq.impl;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateTableAsStep;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.GroupField;
import org.jooq.JoinType;
import org.jooq.Operator;
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnStep;
import org.jooq.TablePartitionByStep;
import org.jooq.WindowDefinition;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.StringUtils;

class SelectQueryImpl<R extends Record>
  extends AbstractResultQuery<R>
  implements SelectQuery<R>
{
  private static final long serialVersionUID = 1646393178384872967L;
  private static final Clause[] CLAUSES = { Clause.SELECT };
  private final WithImpl with;
  private final SelectFieldList select;
  private Table<?> into;
  private String hint;
  private String option;
  private boolean distinct;
  private final QueryPartList<Field<?>> distinctOn;
  private boolean forUpdate;
  private final QueryPartList<Field<?>> forUpdateOf;
  private final TableList forUpdateOfTables;
  private ForUpdateMode forUpdateMode;
  private int forUpdateWait;
  private boolean forShare;
  private final TableList from;
  private final ConditionProviderImpl condition;
  private final ConditionProviderImpl connectBy;
  private boolean connectByNoCycle;
  private final ConditionProviderImpl connectByStartWith;
  private boolean grouping;
  private final QueryPartList<GroupField> groupBy;
  private final ConditionProviderImpl having;
  private final WindowList window;
  private final SortFieldList orderBy;
  private boolean orderBySiblings;
  private final QueryPartList<Field<?>> seek;
  private boolean seekBefore;
  private final Limit limit;
  private final List<CombineOperator> unionOp;
  private final List<QueryPartList<Select<?>>> union;
  private final SortFieldList unionOrderBy;
  private boolean unionOrderBySiblings;
  private final QueryPartList<Field<?>> unionSeek;
  private boolean unionSeekBefore;
  private final Limit unionLimit;
  
  SelectQueryImpl(WithImpl with, Configuration configuration)
  {
    this(with, configuration, null);
  }
  
  SelectQueryImpl(WithImpl with, Configuration configuration, boolean distinct)
  {
    this(with, configuration, null, distinct);
  }
  
  SelectQueryImpl(WithImpl with, Configuration configuration, TableLike<? extends R> from)
  {
    this(with, configuration, from, false);
  }
  
  SelectQueryImpl(WithImpl with, Configuration configuration, TableLike<? extends R> from, boolean distinct)
  {
    super(configuration);
    
    this.with = with;
    this.distinct = distinct;
    this.distinctOn = new QueryPartList();
    this.select = new SelectFieldList();
    this.from = new TableList();
    this.condition = new ConditionProviderImpl();
    this.connectBy = new ConditionProviderImpl();
    this.connectByStartWith = new ConditionProviderImpl();
    this.groupBy = new QueryPartList();
    this.having = new ConditionProviderImpl();
    this.window = new WindowList();
    this.orderBy = new SortFieldList();
    this.seek = new QueryPartList();
    this.limit = new Limit();
    this.unionOp = new ArrayList();
    this.union = new ArrayList();
    this.unionOrderBy = new SortFieldList();
    this.unionSeek = new QueryPartList();
    this.unionLimit = new Limit();
    if (from != null) {
      this.from.add(from.asTable());
    }
    this.forUpdateOf = new QueryPartList();
    this.forUpdateOfTables = new TableList();
  }
  
  public final int fetchCount()
    throws DataAccessException
  {
    return DSL.using(configuration()).fetchCount(this);
  }
  
  public final <T> Field<T> asField()
  {
    if (getSelect().size() != 1) {
      throw new IllegalStateException("Can only use single-column ResultProviderQuery as a field");
    }
    return new ScalarSubquery(this, ((Field)getSelect().get(0)).getDataType());
  }
  
  public final <T> Field<T> asField(String alias)
  {
    return asField().as(alias);
  }
  
  public final Row fieldsRow()
  {
    return asTable().fieldsRow();
  }
  
  public final <T> Field<T> field(Field<T> field)
  {
    return asTable().field(field);
  }
  
  public final Field<?> field(String string)
  {
    return asTable().field(string);
  }
  
  public final Field<?> field(int index)
  {
    return asTable().field(index);
  }
  
  public final Field<?>[] fields()
  {
    return asTable().fields();
  }
  
  public final Table<R> asTable()
  {
    return new DerivedTable(this).as("alias_" + Utils.hash(this));
  }
  
  public final Table<R> asTable(String alias)
  {
    return new DerivedTable(this).as(alias);
  }
  
  public final Table<R> asTable(String alias, String... fieldAliases)
  {
    return new DerivedTable(this).as(alias, fieldAliases);
  }
  
  protected final Field<?>[] getFields(ResultSetMetaData meta)
  {
    List<Field<?>> fields = getSelect();
    if (fields.isEmpty())
    {
      Configuration configuration = configuration();
      return new MetaDataFieldProvider(configuration, meta).getFields();
    }
    return Utils.fieldArray(fields);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final void accept(Context<?> context)
  {
    SQLDialect dialect = context.dialect();
    SQLDialect family = context.family();
    
    Object renderTrailingLimit = context.data("org.jooq.configuration.render-trailing-limit-if-applicable");
    try
    {
      if (renderTrailingLimit != null) {
        context.data().remove("org.jooq.configuration.render-trailing-limit-if-applicable");
      }
      if ((this.into != null) && 
        (context.data("org.jooq.configuration.omit-into-clause") == null)) {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE }).contains(family))
        {
          context.data("org.jooq.configuration.omit-into-clause", Boolean.valueOf(true));
          context.visit(DSL.createTable(this.into).as(this));
          context.data().remove("org.jooq.configuration.omit-into-clause");
          
          return;
        }
      }
      if (this.with != null) {
        context.visit(this.with).formatSeparator();
      }
      pushWindow(context);
      
      Boolean wrapDerivedTables = (Boolean)context.data("org.jooq.configuration.wrap-derived-tables-in-parentheses");
      if (Boolean.TRUE.equals(wrapDerivedTables)) {
        context.sql("(").formatIndentStart().formatNewLine().data("org.jooq.configuration.wrap-derived-tables-in-parentheses", null);
      }
      switch (dialect)
      {
      }
      toSQLReferenceLimitDefault(context);
      if (this.forUpdate) {
        if (!Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID }).contains(family))
        {
          context.formatSeparator().keyword("for update");
          if (!this.forUpdateOf.isEmpty())
          {
            context.sql(" ").keyword("of").sql(" ");
            Utils.fieldNames(context, this.forUpdateOf);
          }
          else if (!this.forUpdateOfTables.isEmpty())
          {
            context.sql(" ").keyword("of").sql(" ");
            switch (family)
            {
            case DERBY: 
              this.forUpdateOfTables.toSQLFieldNames(context);
              break;
            default: 
              Utils.tableNames(context, this.forUpdateOfTables);
            }
          }
          if (family == SQLDialect.FIREBIRD) {
            context.sql(" ").keyword("with lock");
          }
          if (this.forUpdateMode == null) {
            break label619;
          }
          context.sql(" ");
          context.keyword(this.forUpdateMode.toSQL());
          if (this.forUpdateMode != ForUpdateMode.WAIT) {
            break label619;
          }
          context.sql(" ");
          context.sql(this.forUpdateWait);
          break label619;
        }
      }
      if (this.forShare) {
        switch (dialect)
        {
        case MARIADB: 
        case MYSQL: 
          context.formatSeparator().keyword("lock in share mode");
          break;
        default: 
          context.formatSeparator().keyword("for share");
        }
      }
      label619:
      if (!StringUtils.isBlank(this.option)) {
        context.formatSeparator().sql(this.option);
      }
      if (Boolean.TRUE.equals(wrapDerivedTables)) {
        context.formatIndentEnd().formatNewLine().sql(")").data("org.jooq.configuration.wrap-derived-tables-in-parentheses", Boolean.valueOf(true));
      }
    }
    finally
    {
      if (renderTrailingLimit != null) {
        context.data("org.jooq.configuration.render-trailing-limit-if-applicable", renderTrailingLimit);
      }
    }
  }
  
  private final void pushWindow(Context<?> context)
  {
    if (!getWindow().isEmpty()) {
      ((Map)context.data("org.jooq.configuration.locally-scoped-data-map")).put("org.jooq.configuration.local-window-definitions", getWindow());
    }
  }
  
  private void toSQLReferenceLimitDefault(Context<?> context)
  {
    Object data = context.data("org.jooq.configuration.render-trailing-limit-if-applicable");
    
    context.data("org.jooq.configuration.render-trailing-limit-if-applicable", Boolean.valueOf(true));
    toSQLReference0(context);
    if (data == null) {
      context.data().remove("org.jooq.configuration.render-trailing-limit-if-applicable");
    } else {
      context.data("org.jooq.configuration.render-trailing-limit-if-applicable", data);
    }
  }
  
  private final void toSQLReference0(Context<?> context)
  {
    toSQLReference0(context, null, null);
  }
  
  private final void toSQLReference0(Context<?> context, Field<?>[] originalFields, Field<?>[] alternativeFields)
  {
    SQLDialect dialect = context.dialect();
    SQLDialect family = dialect.family();
    
    int unionOpSize = this.unionOp.size();
    
    boolean wrapQueryExpressionInDerivedTable = false;
    boolean wrapQueryExpressionBodyInDerivedTable = false;
    if (unionOpSize > 0) {
      for (int i = unionOpSize - 1; i >= 0; i--)
      {
        switch ((CombineOperator)this.unionOp.get(i))
        {
        case EXCEPT: 
          context.start(Clause.SELECT_EXCEPT); break;
        case INTERSECT: 
          context.start(Clause.SELECT_INTERSECT); break;
        case UNION: 
          context.start(Clause.SELECT_UNION); break;
        case UNION_ALL: 
          context.start(Clause.SELECT_UNION_ALL);
        }
        unionParenthesis(context, "(");
      }
    }
    context.start(Clause.SELECT_SELECT).keyword("select").sql(" ");
    if (!StringUtils.isBlank(this.hint)) {
      context.sql(this.hint).sql(" ");
    }
    if (!this.distinctOn.isEmpty()) {
      context.keyword("distinct on").sql(" (").visit(this.distinctOn).sql(") ");
    } else if (this.distinct) {
      context.keyword("distinct").sql(" ");
    }
    context.declareFields(true);
    if (alternativeFields != null)
    {
      if ((wrapQueryExpressionBodyInDerivedTable) && (originalFields.length < alternativeFields.length)) {
        context.visit(new SelectFieldList((Field[])Arrays.copyOf(alternativeFields, alternativeFields.length - 1)));
      } else {
        context.visit(new SelectFieldList(alternativeFields));
      }
    }
    else if ((context.subquery()) && (dialect == SQLDialect.H2) && (context.data("org.jooq.configuration.row-value-expression-subquery") != null))
    {
      Object data = context.data("org.jooq.configuration.row-value-expression-subquery");
      try
      {
        context.data("org.jooq.configuration.row-value-expression-subquery", null);
        context.sql("(")
          .visit(getSelect1())
          .sql(")");
      }
      finally
      {
        context.data("org.jooq.configuration.row-value-expression-subquery", data);
      }
    }
    else
    {
      context.visit(getSelect1());
    }
    context.declareFields(false).end(Clause.SELECT_SELECT);
    if (!Arrays.asList(new Object[0]).contains(family))
    {
      context.start(Clause.SELECT_INTO);
      
      Table<?> actualInto = (Table)context.data("org.jooq.configuration.select-into-table");
      if (actualInto == null) {
        actualInto = this.into;
      }
      if ((actualInto != null) && 
        (context.data("org.jooq.configuration.omit-into-clause") == null)) {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.HSQLDB, SQLDialect.POSTGRES }).contains(family)) {
          context.formatSeparator().keyword("into").sql(" ").visit(actualInto);
        }
      }
      context.end(Clause.SELECT_INTO);
    }
    context.start(Clause.SELECT_FROM).declareTables(true);
    
    boolean hasFrom = false;
    if (!hasFrom)
    {
      DefaultConfiguration c = new DefaultConfiguration(dialect);
      String renderedFrom = new DefaultRenderContext(c).render(getFrom());
      hasFrom = !renderedFrom.isEmpty();
    }
    if (hasFrom) {
      context.formatSeparator().keyword("from").sql(" ").visit(getFrom());
    }
    context.declareTables(false).end(Clause.SELECT_FROM);
    
    context.start(Clause.SELECT_WHERE);
    if (!(getWhere().getWhere() instanceof TrueCondition)) {
      context.formatSeparator().keyword("where").sql(" ").visit(getWhere());
    }
    context.end(Clause.SELECT_WHERE);
    
    context.start(Clause.SELECT_START_WITH);
    if (!(getConnectByStartWith().getWhere() instanceof TrueCondition)) {
      context.formatSeparator().keyword("start with").sql(" ").visit(getConnectByStartWith());
    }
    context.end(Clause.SELECT_START_WITH);
    context.start(Clause.SELECT_CONNECT_BY);
    if (!(getConnectBy().getWhere() instanceof TrueCondition))
    {
      context.formatSeparator().keyword("connect by");
      if (this.connectByNoCycle) {
        context.sql(" ").keyword("nocycle");
      }
      context.sql(" ").visit(getConnectBy());
    }
    context.end(Clause.SELECT_CONNECT_BY);
    
    context.start(Clause.SELECT_GROUP_BY);
    if (this.grouping)
    {
      context.formatSeparator().keyword("group by").sql(" ");
      if (getGroupBy().isEmpty())
      {
        if (Arrays.asList(new Object[0]).contains(dialect)) {
          context.sql("empty_grouping_dummy_table.dual");
        } else if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE }).contains(dialect)) {
          context.sql("1");
        } else {
          context.sql("()");
        }
      }
      else {
        context.visit(getGroupBy());
      }
    }
    context.end(Clause.SELECT_GROUP_BY);
    
    context.start(Clause.SELECT_HAVING);
    if (!(getHaving().getWhere() instanceof TrueCondition)) {
      context.formatSeparator().keyword("having").sql(" ").visit(getHaving());
    }
    context.end(Clause.SELECT_HAVING);
    
    context.start(Clause.SELECT_WINDOW);
    if (!getWindow().isEmpty()) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.POSTGRES }).contains(family)) {
        context.formatSeparator().keyword("window").sql(" ").declareWindows(true).visit(getWindow()).declareWindows(false);
      }
    }
    context.end(Clause.SELECT_WINDOW);
    
    toSQLOrderBy(context, originalFields, alternativeFields, false, wrapQueryExpressionBodyInDerivedTable, this.orderBy, this.limit);
    if (unionOpSize > 0)
    {
      unionParenthesis(context, ")");
      for (int i = 0; i < unionOpSize; i++)
      {
        CombineOperator op = (CombineOperator)this.unionOp.get(i);
        for (Select<?> other : (QueryPartList)this.union.get(i))
        {
          context.formatSeparator().keyword(op.toSQL(dialect)).sql(" ");
          
          unionParenthesis(context, "(");
          context.visit(other);
          unionParenthesis(context, ")");
        }
        if (i < unionOpSize - 1) {
          unionParenthesis(context, ")");
        }
        switch ((CombineOperator)this.unionOp.get(i))
        {
        case EXCEPT: 
          context.end(Clause.SELECT_EXCEPT); break;
        case INTERSECT: 
          context.end(Clause.SELECT_INTERSECT); break;
        case UNION: 
          context.end(Clause.SELECT_UNION); break;
        case UNION_ALL: 
          context.end(Clause.SELECT_UNION_ALL);
        }
      }
    }
    boolean qualify = context.qualify();
    try
    {
      context.qualify(false);
      toSQLOrderBy(context, originalFields, alternativeFields, wrapQueryExpressionInDerivedTable, wrapQueryExpressionBodyInDerivedTable, this.unionOrderBy, this.unionLimit);
    }
    finally
    {
      context.qualify(qualify);
    }
  }
  
  private final void toSQLOrderBy(Context<?> context, Field<?>[] originalFields, Field<?>[] alternativeFields, boolean wrapQueryExpressionInDerivedTable, boolean wrapQueryExpressionBodyInDerivedTable, SortFieldList actualOrderBy, Limit actualLimit)
  {
    context.start(Clause.SELECT_ORDER_BY);
    if (!actualOrderBy.isEmpty())
    {
      context.formatSeparator().keyword("order").sql(this.orderBySiblings ? " " : "").keyword(this.orderBySiblings ? "siblings" : "").sql(" ").keyword("by").sql(" ");
      
      context.visit(actualOrderBy);
    }
    context.end(Clause.SELECT_ORDER_BY);
    if ((context.data().containsKey("org.jooq.configuration.render-trailing-limit-if-applicable")) && (actualLimit.isApplicable())) {
      context.visit(actualLimit);
    }
  }
  
  private final void unionParenthesis(Context<?> ctx, String parenthesis)
  {
    if (")".equals(parenthesis)) {
      ctx.formatIndentEnd().formatNewLine();
    } else if ("(".equals(parenthesis)) {
      switch (ctx.family())
      {
      case DERBY: 
      case MARIADB: 
      case MYSQL: 
      case SQLITE: 
        ctx.formatNewLine().keyword("select").sql(" *").formatSeparator().keyword("from").sql(" ");
      }
    }
    switch (ctx.family())
    {
    case FIREBIRD: 
      break;
    default: 
      ctx.sql(parenthesis);
    }
    if ("(".equals(parenthesis)) {
      ctx.formatIndentStart().formatNewLine();
    } else if (")".equals(parenthesis)) {
      switch (ctx.family())
      {
      case DERBY: 
      case MARIADB: 
      case MYSQL: 
      case SQLITE: 
        ctx.sql(" x");
      }
    }
  }
  
  public final void addSelect(Collection<? extends Field<?>> fields)
  {
    getSelect0().addAll(fields);
  }
  
  public final void addSelect(Field<?>... fields)
  {
    addSelect(Arrays.asList(fields));
  }
  
  public final void setDistinct(boolean distinct)
  {
    this.distinct = distinct;
  }
  
  public final void addDistinctOn(Field<?>... fields)
  {
    addDistinctOn(Arrays.asList(fields));
  }
  
  public final void addDistinctOn(Collection<? extends Field<?>> fields)
  {
    this.distinctOn.addAll(fields);
  }
  
  public final void setInto(Table<?> into)
  {
    this.into = into;
  }
  
  public final void addLimit(int numberOfRows)
  {
    getLimit().setNumberOfRows(numberOfRows);
  }
  
  public final void addLimit(Param<Integer> numberOfRows)
  {
    getLimit().setNumberOfRows(numberOfRows);
  }
  
  public final void addLimit(int offset, int numberOfRows)
  {
    getLimit().setOffset(offset);
    getLimit().setNumberOfRows(numberOfRows);
  }
  
  public final void addLimit(int offset, Param<Integer> numberOfRows)
  {
    getLimit().setOffset(offset);
    getLimit().setNumberOfRows(numberOfRows);
  }
  
  public final void addLimit(Param<Integer> offset, int numberOfRows)
  {
    getLimit().setOffset(offset);
    getLimit().setNumberOfRows(numberOfRows);
  }
  
  public final void addLimit(Param<Integer> offset, Param<Integer> numberOfRows)
  {
    getLimit().setOffset(offset);
    getLimit().setNumberOfRows(numberOfRows);
  }
  
  public final void setForUpdate(boolean forUpdate)
  {
    this.forUpdate = forUpdate;
    this.forShare = false;
  }
  
  public final void setForUpdateOf(Field<?>... fields)
  {
    setForUpdateOf(Arrays.asList(fields));
  }
  
  public final void setForUpdateOf(Collection<? extends Field<?>> fields)
  {
    setForUpdate(true);
    this.forUpdateOf.clear();
    this.forUpdateOfTables.clear();
    this.forUpdateOf.addAll(fields);
  }
  
  public final void setForUpdateOf(Table<?>... tables)
  {
    setForUpdate(true);
    this.forUpdateOf.clear();
    this.forUpdateOfTables.clear();
    this.forUpdateOfTables.addAll(Arrays.asList(tables));
  }
  
  public final void setForUpdateNoWait()
  {
    setForUpdate(true);
    this.forUpdateMode = ForUpdateMode.NOWAIT;
    this.forUpdateWait = 0;
  }
  
  public final void setForShare(boolean forShare)
  {
    this.forUpdate = false;
    this.forShare = forShare;
    this.forUpdateOf.clear();
    this.forUpdateOfTables.clear();
    this.forUpdateMode = null;
    this.forUpdateWait = 0;
  }
  
  public final List<Field<?>> getSelect()
  {
    return getSelect1();
  }
  
  final SelectFieldList getSelect0()
  {
    return this.select;
  }
  
  final SelectFieldList getSelect1()
  {
    if (getSelect0().isEmpty())
    {
      SelectFieldList result = new SelectFieldList();
      if (knownTableSource()) {
        for (TableLike<?> table : getFrom()) {
          for (Field<?> field : table.asTable().fields()) {
            result.add(field);
          }
        }
      }
      if (getFrom().isEmpty()) {
        result.add(DSL.one());
      }
      return result;
    }
    return getSelect0();
  }
  
  private final boolean knownTableSource()
  {
    for (Table<?> table : getFrom()) {
      if (!knownTable(table)) {
        return false;
      }
    }
    return true;
  }
  
  private final boolean knownTable(Table<?> table)
  {
    return table.fieldsRow().size() > 0;
  }
  
  public final Class<? extends R> getRecordType()
  {
    if ((getFrom().size() == 1) && (getSelect0().isEmpty())) {
      return ((Table)getFrom().get(0)).asTable().getRecordType();
    }
    return RecordImpl.class;
  }
  
  final TableList getFrom()
  {
    return this.from;
  }
  
  final void setGrouping()
  {
    this.grouping = true;
  }
  
  final QueryPartList<GroupField> getGroupBy()
  {
    return this.groupBy;
  }
  
  final ConditionProviderImpl getWhere()
  {
    if ((getOrderBy().isEmpty()) || (getSeek().isEmpty())) {
      return this.condition;
    }
    SortFieldList o = getOrderBy();
    Condition c = null;
    if ((!o.nulls()) || (
    
      (o.size() > 1) && (o.uniform())))
    {
      if ((((SortField)o.get(0)).getOrder() == SortOrder.ASC ^ this.seekBefore)) {
        c = DSL.row(o.fields()).gt(DSL.row(getSeek()));
      } else {
        c = DSL.row(o.fields()).lt(DSL.row(getSeek()));
      }
    }
    else
    {
      ConditionProviderImpl or = new ConditionProviderImpl();
      for (int i = 0; i < o.size(); i++)
      {
        ConditionProviderImpl and = new ConditionProviderImpl();
        for (int j = 0; j < i; j++)
        {
          SortFieldImpl<?> s = (SortFieldImpl)o.get(j);
          and.addConditions(new Condition[] { s.getField().eq((Field)getSeek().get(j)) });
        }
        SortFieldImpl<?> s = (SortFieldImpl)o.get(i);
        if ((s.getOrder() == SortOrder.ASC ^ this.seekBefore)) {
          and.addConditions(new Condition[] { s.getField().gt((Field)getSeek().get(i)) });
        } else {
          and.addConditions(new Condition[] { s.getField().lt((Field)getSeek().get(i)) });
        }
        or.addConditions(Operator.OR, new Condition[] { and });
      }
      c = or;
    }
    ConditionProviderImpl result = new ConditionProviderImpl();
    result.addConditions(new Condition[] { this.condition, c });
    return result;
  }
  
  final ConditionProviderImpl getConnectBy()
  {
    return this.connectBy;
  }
  
  final ConditionProviderImpl getConnectByStartWith()
  {
    return this.connectByStartWith;
  }
  
  final ConditionProviderImpl getHaving()
  {
    return this.having;
  }
  
  final QueryPartList<WindowDefinition> getWindow()
  {
    return this.window;
  }
  
  final SortFieldList getOrderBy()
  {
    return this.unionOp.size() == 0 ? this.orderBy : this.unionOrderBy;
  }
  
  final QueryPartList<Field<?>> getSeek()
  {
    return this.unionOp.size() == 0 ? this.seek : this.unionSeek;
  }
  
  final Limit getLimit()
  {
    return this.unionOp.size() == 0 ? this.limit : this.unionLimit;
  }
  
  public final void addOrderBy(Collection<? extends SortField<?>> fields)
  {
    getOrderBy().addAll(fields);
  }
  
  public final void addOrderBy(Field<?>... fields)
  {
    getOrderBy().addAll(fields);
  }
  
  public final void addOrderBy(SortField<?>... fields)
  {
    addOrderBy(Arrays.asList(fields));
  }
  
  public final void addOrderBy(int... fieldIndexes)
  {
    Field<?>[] fields = new Field[fieldIndexes.length];
    for (int i = 0; i < fieldIndexes.length; i++) {
      fields[i] = DSL.inline(Integer.valueOf(fieldIndexes[i]));
    }
    addOrderBy(fields);
  }
  
  public final void setOrderBySiblings(boolean orderBySiblings)
  {
    if (this.unionOp.size() == 0) {
      this.orderBySiblings = orderBySiblings;
    } else {
      this.unionOrderBySiblings = orderBySiblings;
    }
  }
  
  public final void addSeekAfter(Field<?>... fields)
  {
    addSeekAfter(Arrays.asList(fields));
  }
  
  public final void addSeekAfter(Collection<? extends Field<?>> fields)
  {
    if (this.unionOp.size() == 0) {
      this.seekBefore = false;
    } else {
      this.unionSeekBefore = false;
    }
    getSeek().addAll(fields);
  }
  
  public final void addSeekBefore(Field<?>... fields)
  {
    addSeekBefore(Arrays.asList(fields));
  }
  
  public final void addSeekBefore(Collection<? extends Field<?>> fields)
  {
    if (this.unionOp.size() == 0) {
      this.seekBefore = true;
    } else {
      this.unionSeekBefore = true;
    }
    getSeek().addAll(fields);
  }
  
  public final void addConditions(Condition... conditions)
  {
    this.condition.addConditions(conditions);
  }
  
  public final void addConditions(Collection<? extends Condition> conditions)
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
  
  final void setConnectByNoCycle(boolean connectByNoCycle)
  {
    this.connectByNoCycle = connectByNoCycle;
  }
  
  final void setStartWith(Condition condition)
  {
    this.connectByStartWith.addConditions(new Condition[] { condition });
  }
  
  final void setHint(String hint)
  {
    this.hint = hint;
  }
  
  final void setOption(String option)
  {
    this.option = option;
  }
  
  final boolean isForUpdate()
  {
    return this.forUpdate;
  }
  
  public final void addFrom(Collection<? extends TableLike<?>> f)
  {
    for (TableLike<?> provider : f) {
      getFrom().add(provider.asTable());
    }
  }
  
  public final void addFrom(TableLike<?> f)
  {
    addFrom(Arrays.asList(new TableLike[] { f }));
  }
  
  public final void addFrom(TableLike<?>... f)
  {
    addFrom(Arrays.asList(f));
  }
  
  public final void addConnectBy(Condition c)
  {
    getConnectBy().addConditions(new Condition[] { c });
  }
  
  public final void addConnectByNoCycle(Condition c)
  {
    getConnectBy().addConditions(new Condition[] { c });
    setConnectByNoCycle(true);
  }
  
  public final void setConnectByStartWith(Condition c)
  {
    setStartWith(c);
  }
  
  public final void addGroupBy(Collection<? extends GroupField> fields)
  {
    setGrouping();
    getGroupBy().addAll(fields);
  }
  
  public final void addGroupBy(GroupField... fields)
  {
    addGroupBy(Arrays.asList(fields));
  }
  
  public final void addHaving(Condition... conditions)
  {
    addHaving(Arrays.asList(conditions));
  }
  
  public final void addHaving(Collection<? extends Condition> conditions)
  {
    getHaving().addConditions(conditions);
  }
  
  public final void addHaving(Operator operator, Condition... conditions)
  {
    getHaving().addConditions(operator, conditions);
  }
  
  public final void addHaving(Operator operator, Collection<? extends Condition> conditions)
  {
    getHaving().addConditions(operator, conditions);
  }
  
  public final void addWindow(WindowDefinition... definitions)
  {
    addWindow(Arrays.asList(definitions));
  }
  
  public final void addWindow(Collection<? extends WindowDefinition> definitions)
  {
    getWindow().addAll(definitions);
  }
  
  private final Select<R> combine(CombineOperator op, Select<? extends R> other)
  {
    int index = this.unionOp.size() - 1;
    if ((index == -1) || (this.unionOp.get(index) != op) || (op == CombineOperator.EXCEPT))
    {
      this.unionOp.add(op);
      this.union.add(new QueryPartList());
      
      index++;
    }
    ((QueryPartList)this.union.get(index)).add(other);
    return this;
  }
  
  public final Select<R> union(Select<? extends R> other)
  {
    return combine(CombineOperator.UNION, other);
  }
  
  public final Select<R> unionAll(Select<? extends R> other)
  {
    return combine(CombineOperator.UNION_ALL, other);
  }
  
  public final Select<R> except(Select<? extends R> other)
  {
    return combine(CombineOperator.EXCEPT, other);
  }
  
  public final Select<R> intersect(Select<? extends R> other)
  {
    return combine(CombineOperator.INTERSECT, other);
  }
  
  public final void addJoin(TableLike<?> table, Condition... conditions)
  {
    addJoin(table, JoinType.JOIN, conditions);
  }
  
  public final void addJoin(TableLike<?> table, JoinType type, Condition... conditions)
  {
    addJoin0(table, type, conditions, null);
  }
  
  private final void addJoin0(TableLike<?> table, JoinType type, Condition[] conditions, Field<?>[] partitionBy)
  {
    int index = getFrom().size() - 1;
    Table<?> joined = null;
    switch (type)
    {
    case JOIN: 
      joined = ((Table)getFrom().get(index)).join(table).on(conditions);
      break;
    case LEFT_OUTER_JOIN: 
      TablePartitionByStep p = ((Table)getFrom().get(index)).leftOuterJoin(table);
      TableOnStep o = p;
      
      joined = o.on(conditions);
      break;
    case RIGHT_OUTER_JOIN: 
      TablePartitionByStep p = ((Table)getFrom().get(index)).rightOuterJoin(table);
      TableOnStep o = p;
      
      joined = o.on(conditions);
      break;
    case FULL_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).fullOuterJoin(table).on(conditions);
      break;
    case CROSS_JOIN: 
      joined = ((Table)getFrom().get(index)).crossJoin(table);
      break;
    case NATURAL_JOIN: 
      joined = ((Table)getFrom().get(index)).naturalJoin(table);
      break;
    case NATURAL_LEFT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).naturalLeftOuterJoin(table);
      break;
    case NATURAL_RIGHT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).naturalRightOuterJoin(table);
    }
    getFrom().set(index, joined);
  }
  
  public final void addJoinOnKey(TableLike<?> table, JoinType type)
    throws DataAccessException
  {
    int index = getFrom().size() - 1;
    Table<?> joined = null;
    switch (type)
    {
    case JOIN: 
      joined = ((Table)getFrom().get(index)).join(table).onKey();
      break;
    case LEFT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).leftOuterJoin(table).onKey();
      break;
    case RIGHT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).rightOuterJoin(table).onKey();
      break;
    case FULL_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).fullOuterJoin(table).onKey();
      break;
    default: 
      throw new IllegalArgumentException("JoinType " + type + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
    }
    getFrom().set(index, joined);
  }
  
  public final void addJoinOnKey(TableLike<?> table, JoinType type, TableField<?, ?>... keyFields)
    throws DataAccessException
  {
    int index = getFrom().size() - 1;
    Table<?> joined = null;
    switch (type)
    {
    case JOIN: 
      joined = ((Table)getFrom().get(index)).join(table).onKey(keyFields);
      break;
    case LEFT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).leftOuterJoin(table).onKey(keyFields);
      break;
    case RIGHT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).rightOuterJoin(table).onKey(keyFields);
      break;
    case FULL_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).fullOuterJoin(table).onKey(keyFields);
      break;
    default: 
      throw new IllegalArgumentException("JoinType " + type + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
    }
    getFrom().set(index, joined);
  }
  
  public final void addJoinOnKey(TableLike<?> table, JoinType type, ForeignKey<?, ?> key)
  {
    int index = getFrom().size() - 1;
    Table<?> joined = null;
    switch (type)
    {
    case JOIN: 
      joined = ((Table)getFrom().get(index)).join(table).onKey(key);
      break;
    case LEFT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).leftOuterJoin(table).onKey(key);
      break;
    case RIGHT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).rightOuterJoin(table).onKey(key);
      break;
    case FULL_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).fullOuterJoin(table).onKey(key);
      break;
    default: 
      throw new IllegalArgumentException("JoinType " + type + " is not supported with the addJoinOnKey() method. Use INNER or OUTER JOINs only");
    }
    getFrom().set(index, joined);
  }
  
  public final void addJoinUsing(TableLike<?> table, Collection<? extends Field<?>> fields)
  {
    addJoinUsing(table, JoinType.JOIN, fields);
  }
  
  public final void addJoinUsing(TableLike<?> table, JoinType type, Collection<? extends Field<?>> fields)
  {
    int index = getFrom().size() - 1;
    Table<?> joined = null;
    switch (type)
    {
    case JOIN: 
      joined = ((Table)getFrom().get(index)).join(table).using(fields);
      break;
    case LEFT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).leftOuterJoin(table).using(fields);
      break;
    case RIGHT_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).rightOuterJoin(table).using(fields);
      break;
    case FULL_OUTER_JOIN: 
      joined = ((Table)getFrom().get(index)).fullOuterJoin(table).using(fields);
      break;
    default: 
      throw new IllegalArgumentException("JoinType " + type + " is not supported with the addJoinUsing() method. Use INNER or OUTER JOINs only");
    }
    getFrom().set(index, joined);
  }
  
  public final void addHint(String h)
  {
    setHint(h);
  }
  
  public final void addOption(String o)
  {
    setOption(o);
  }
  
  private static enum ForUpdateMode
  {
    WAIT("wait"),  NOWAIT("nowait"),  SKIP_LOCKED("skip locked");
    
    private final String sql;
    
    private ForUpdateMode(String sql)
    {
      this.sql = sql;
    }
    
    public final String toSQL()
    {
      return this.sql;
    }
  }
}

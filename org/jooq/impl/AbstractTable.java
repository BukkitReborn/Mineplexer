package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DivideByOnStep;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.JoinType;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.Row;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.TablePartitionByStep;
import org.jooq.UniqueKey;
import org.jooq.tools.StringUtils;

abstract class AbstractTable<R extends Record>
  extends AbstractQueryPart
  implements Table<R>
{
  private static final long serialVersionUID = 3155496238969274871L;
  private static final Clause[] CLAUSES = { Clause.TABLE };
  private final Schema schema;
  private final String name;
  private final String comment;
  
  AbstractTable(String name)
  {
    this(name, null, null);
  }
  
  AbstractTable(String name, Schema schema)
  {
    this(name, schema, null);
  }
  
  AbstractTable(String name, Schema schema, String comment)
  {
    this.schema = schema;
    this.name = name;
    this.comment = comment;
  }
  
  public Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  abstract Fields<R> fields0();
  
  public final RecordType<R> recordType()
  {
    return fields0();
  }
  
  public final R newRecord()
  {
    return DSL.using(new DefaultConfiguration()).newRecord(this);
  }
  
  public final Row fieldsRow()
  {
    return new RowImpl(fields0());
  }
  
  public final <T> Field<T> field(Field<T> field)
  {
    return fieldsRow().field(field);
  }
  
  public final Field<?> field(String string)
  {
    return fieldsRow().field(string);
  }
  
  public final Field<?> field(int index)
  {
    return fieldsRow().field(index);
  }
  
  public final Field<?>[] fields()
  {
    return fieldsRow().fields();
  }
  
  public final Table<R> asTable()
  {
    return this;
  }
  
  public final Table<R> asTable(String alias)
  {
    return as(alias);
  }
  
  public final Table<R> asTable(String alias, String... fieldAliases)
  {
    return as(alias, fieldAliases);
  }
  
  public final Schema getSchema()
  {
    return this.schema;
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final String getComment()
  {
    return this.comment;
  }
  
  public Identity<R, ? extends Number> getIdentity()
  {
    return null;
  }
  
  public UniqueKey<R> getPrimaryKey()
  {
    return null;
  }
  
  public TableField<R, ? extends Number> getRecordVersion()
  {
    return null;
  }
  
  public TableField<R, ? extends Date> getRecordTimestamp()
  {
    return null;
  }
  
  public List<UniqueKey<R>> getKeys()
  {
    return Collections.emptyList();
  }
  
  public final <O extends Record> List<ForeignKey<O, R>> getReferencesFrom(Table<O> other)
  {
    return other.getReferencesTo(this);
  }
  
  public List<ForeignKey<R, ?>> getReferences()
  {
    return Collections.emptyList();
  }
  
  public final <O extends Record> List<ForeignKey<R, O>> getReferencesTo(Table<O> other)
  {
    List<ForeignKey<R, O>> result = new ArrayList();
    for (ForeignKey<R, ?> reference : getReferences()) {
      if (other.equals(reference.getKey().getTable()))
      {
        result.add(reference);
      }
      else if ((other instanceof TableImpl))
      {
        Table<O> aliased = ((TableImpl)other).getAliasedTable();
        if ((aliased != null) && (aliased.equals(reference.getKey().getTable()))) {
          result.add(reference);
        }
      }
      else if ((other instanceof TableAlias))
      {
        Table<O> aliased = ((TableAlias)other).getAliasedTable();
        if ((aliased != null) && (aliased.equals(reference.getKey().getTable()))) {
          result.add(reference);
        }
      }
    }
    return Collections.unmodifiableList(result);
  }
  
  protected static final <R extends Record, T> TableField<R, T> createField(String name, DataType<T> type, Table<R> table)
  {
    return createField(name, type, table, null, null, null);
  }
  
  protected static final <R extends Record, T> TableField<R, T> createField(String name, DataType<T> type, Table<R> table, String comment)
  {
    return createField(name, type, table, comment, null, null);
  }
  
  protected static final <R extends Record, T, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Converter<T, U> converter)
  {
    return createField(name, type, table, comment, converter, null);
  }
  
  protected static final <R extends Record, T, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Binding<T, U> binding)
  {
    return createField(name, type, table, comment, null, binding);
  }
  
  protected static final <R extends Record, T, X, U> TableField<R, U> createField(String name, DataType<T> type, Table<R> table, String comment, Converter<X, U> converter, Binding<T, X> binding)
  {
    Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
    
    DataType<U> actualType = (converter == null) && (binding == null) ? type : type.asConvertedDataType(actualBinding);
    
    TableFieldImpl<R, U> tableField = new TableFieldImpl(name, actualType, table, comment, actualBinding);
    if ((table instanceof TableImpl)) {
      ((TableImpl)table).fields0().add(tableField);
    }
    return tableField;
  }
  
  public final Table<R> useIndex(String... indexes)
  {
    return new HintedTable(this, "use index", indexes);
  }
  
  public final Table<R> useIndexForJoin(String... indexes)
  {
    return new HintedTable(this, "use index for join", indexes);
  }
  
  public final Table<R> useIndexForOrderBy(String... indexes)
  {
    return new HintedTable(this, "use index for order by", indexes);
  }
  
  public final Table<R> useIndexForGroupBy(String... indexes)
  {
    return new HintedTable(this, "use index for group by", indexes);
  }
  
  public final Table<R> ignoreIndex(String... indexes)
  {
    return new HintedTable(this, "ignore index", indexes);
  }
  
  public final Table<R> ignoreIndexForJoin(String... indexes)
  {
    return new HintedTable(this, "ignore index for join", indexes);
  }
  
  public final Table<R> ignoreIndexForOrderBy(String... indexes)
  {
    return new HintedTable(this, "ignore index for order by", indexes);
  }
  
  public final Table<R> ignoreIndexForGroupBy(String... indexes)
  {
    return new HintedTable(this, "ignore index for group by", indexes);
  }
  
  public final Table<R> forceIndex(String... indexes)
  {
    return new HintedTable(this, "force index", indexes);
  }
  
  public final Table<R> forceIndexForJoin(String... indexes)
  {
    return new HintedTable(this, "force index for join", indexes);
  }
  
  public final Table<R> forceIndexForOrderBy(String... indexes)
  {
    return new HintedTable(this, "force index for order by", indexes);
  }
  
  public final Table<R> forceIndexForGroupBy(String... indexes)
  {
    return new HintedTable(this, "force index for group by", indexes);
  }
  
  public final DivideByOnStep divideBy(Table<?> divisor)
  {
    return new DivideBy(this, divisor);
  }
  
  public final TableOptionalOnStep join(TableLike<?> table, JoinType type)
  {
    return new JoinTable(this, table, type);
  }
  
  public final TableOnStep join(TableLike<?> table)
  {
    return join(table, JoinType.JOIN);
  }
  
  public final TableOnStep join(String sql)
  {
    return join(DSL.table(sql));
  }
  
  public final TableOnStep join(String sql, Object... bindings)
  {
    return join(DSL.table(sql, bindings));
  }
  
  public final TableOnStep join(String sql, QueryPart... parts)
  {
    return join(DSL.table(sql, parts));
  }
  
  public final TablePartitionByStep leftOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.LEFT_OUTER_JOIN);
  }
  
  public final TablePartitionByStep leftOuterJoin(String sql)
  {
    return leftOuterJoin(DSL.table(sql));
  }
  
  public final TablePartitionByStep leftOuterJoin(String sql, Object... bindings)
  {
    return leftOuterJoin(DSL.table(sql, bindings));
  }
  
  public final TablePartitionByStep leftOuterJoin(String sql, QueryPart... parts)
  {
    return leftOuterJoin(DSL.table(sql, parts));
  }
  
  public final TablePartitionByStep rightOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.RIGHT_OUTER_JOIN);
  }
  
  public final TablePartitionByStep rightOuterJoin(String sql)
  {
    return rightOuterJoin(DSL.table(sql));
  }
  
  public final TablePartitionByStep rightOuterJoin(String sql, Object... bindings)
  {
    return rightOuterJoin(DSL.table(sql, bindings));
  }
  
  public final TablePartitionByStep rightOuterJoin(String sql, QueryPart... parts)
  {
    return rightOuterJoin(DSL.table(sql, parts));
  }
  
  public final TableOnStep fullOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.FULL_OUTER_JOIN);
  }
  
  public final TableOnStep fullOuterJoin(String sql)
  {
    return fullOuterJoin(DSL.table(sql));
  }
  
  public final TableOnStep fullOuterJoin(String sql, Object... bindings)
  {
    return fullOuterJoin(DSL.table(sql, bindings));
  }
  
  public final TableOnStep fullOuterJoin(String sql, QueryPart... parts)
  {
    return fullOuterJoin(DSL.table(sql, parts));
  }
  
  public final Table<Record> crossJoin(TableLike<?> table)
  {
    return join(table, JoinType.CROSS_JOIN);
  }
  
  public final Table<Record> crossJoin(String sql)
  {
    return crossJoin(DSL.table(sql));
  }
  
  public final Table<Record> crossJoin(String sql, Object... bindings)
  {
    return crossJoin(DSL.table(sql, bindings));
  }
  
  public final Table<Record> crossJoin(String sql, QueryPart... parts)
  {
    return crossJoin(DSL.table(sql, parts));
  }
  
  public final Table<Record> naturalJoin(TableLike<?> table)
  {
    return join(table, JoinType.NATURAL_JOIN);
  }
  
  public final Table<Record> naturalJoin(String sql)
  {
    return naturalJoin(DSL.table(sql));
  }
  
  public final Table<Record> naturalJoin(String sql, Object... bindings)
  {
    return naturalJoin(DSL.table(sql, bindings));
  }
  
  public final Table<Record> naturalJoin(String sql, QueryPart... parts)
  {
    return naturalJoin(DSL.table(sql, parts));
  }
  
  public final Table<Record> naturalLeftOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.NATURAL_LEFT_OUTER_JOIN);
  }
  
  public final Table<Record> naturalLeftOuterJoin(String sql)
  {
    return naturalLeftOuterJoin(DSL.table(sql));
  }
  
  public final Table<Record> naturalLeftOuterJoin(String sql, Object... bindings)
  {
    return naturalLeftOuterJoin(DSL.table(sql, bindings));
  }
  
  public final Table<Record> naturalLeftOuterJoin(String sql, QueryPart... parts)
  {
    return naturalLeftOuterJoin(DSL.table(sql, parts));
  }
  
  public final Table<Record> naturalRightOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.NATURAL_RIGHT_OUTER_JOIN);
  }
  
  public final Table<Record> naturalRightOuterJoin(String sql)
  {
    return naturalRightOuterJoin(DSL.table(sql));
  }
  
  public final Table<Record> naturalRightOuterJoin(String sql, Object... bindings)
  {
    return naturalRightOuterJoin(DSL.table(sql, bindings));
  }
  
  public final Table<Record> naturalRightOuterJoin(String sql, QueryPart... parts)
  {
    return naturalRightOuterJoin(DSL.table(sql, parts));
  }
  
  public boolean equals(Object that)
  {
    if (this == that) {
      return true;
    }
    if ((that instanceof AbstractTable))
    {
      if (StringUtils.equals(this.name, ((AbstractTable)that).name)) {
        return super.equals(that);
      }
      return false;
    }
    return false;
  }
  
  public int hashCode()
  {
    return this.name.hashCode();
  }
}

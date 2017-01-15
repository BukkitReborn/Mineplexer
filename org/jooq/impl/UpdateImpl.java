package org.jooq.impl;

import java.util.Collection;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.Result;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.UpdateConditionStep;
import org.jooq.UpdateFromStep;
import org.jooq.UpdateQuery;
import org.jooq.UpdateResultStep;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.UpdateWhereStep;

final class UpdateImpl<R extends Record>
  extends AbstractDelegatingQuery<UpdateQuery<R>>
  implements UpdateSetFirstStep<R>, UpdateSetMoreStep<R>, UpdateConditionStep<R>, UpdateResultStep<R>
{
  private static final long serialVersionUID = -2444876472650065331L;
  
  UpdateImpl(Configuration configuration, Table<R> table)
  {
    super(new UpdateQueryImpl(configuration, table));
  }
  
  public final <T> UpdateImpl<R> set(Field<T> field, T value)
  {
    ((UpdateQuery)getDelegate()).addValue(field, value);
    return this;
  }
  
  public final <T> UpdateImpl<R> set(Field<T> field, Field<T> value)
  {
    ((UpdateQuery)getDelegate()).addValue(field, value);
    return this;
  }
  
  public final <T> UpdateImpl<R> set(Field<T> field, Select<? extends Record1<T>> value)
  {
    return set(field, value.asField());
  }
  
  public final UpdateImpl<R> set(Map<? extends Field<?>, ?> map)
  {
    ((UpdateQuery)getDelegate()).addValues(map);
    return this;
  }
  
  public final UpdateImpl<R> set(Record record)
  {
    return set(Utils.map(record));
  }
  
  public final <T1> UpdateFromStep<R> set(Row1<T1> row, Row1<T1> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2> UpdateFromStep<R> set(Row2<T1, T2> row, Row2<T1, T2> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3> UpdateFromStep<R> set(Row3<T1, T2, T3> row, Row3<T1, T2, T3> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4> UpdateFromStep<R> set(Row4<T1, T2, T3, T4> row, Row4<T1, T2, T3, T4> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5> UpdateFromStep<R> set(Row5<T1, T2, T3, T4, T5> row, Row5<T1, T2, T3, T4, T5> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6> UpdateFromStep<R> set(Row6<T1, T2, T3, T4, T5, T6> row, Row6<T1, T2, T3, T4, T5, T6> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7> UpdateFromStep<R> set(Row7<T1, T2, T3, T4, T5, T6, T7> row, Row7<T1, T2, T3, T4, T5, T6, T7> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8> UpdateFromStep<R> set(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row, Row8<T1, T2, T3, T4, T5, T6, T7, T8> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> UpdateFromStep<R> set(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> UpdateFromStep<R> set(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> UpdateFromStep<R> set(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> UpdateFromStep<R> set(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> UpdateFromStep<R> set(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> UpdateFromStep<R> set(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> UpdateFromStep<R> set(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> UpdateFromStep<R> set(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> UpdateFromStep<R> set(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> UpdateFromStep<R> set(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> UpdateFromStep<R> set(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> UpdateFromStep<R> set(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> UpdateFromStep<R> set(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> UpdateFromStep<R> set(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> value)
  {
    ((UpdateQuery)getDelegate()).addValues(row, value);
    return this;
  }
  
  public final <T1> UpdateFromStep<R> set(Row1<T1> row, Select<? extends Record1<T1>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2> UpdateFromStep<R> set(Row2<T1, T2> row, Select<? extends Record2<T1, T2>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3> UpdateFromStep<R> set(Row3<T1, T2, T3> row, Select<? extends Record3<T1, T2, T3>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4> UpdateFromStep<R> set(Row4<T1, T2, T3, T4> row, Select<? extends Record4<T1, T2, T3, T4>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5> UpdateFromStep<R> set(Row5<T1, T2, T3, T4, T5> row, Select<? extends Record5<T1, T2, T3, T4, T5>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6> UpdateFromStep<R> set(Row6<T1, T2, T3, T4, T5, T6> row, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7> UpdateFromStep<R> set(Row7<T1, T2, T3, T4, T5, T6, T7> row, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8> UpdateFromStep<R> set(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> UpdateFromStep<R> set(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> UpdateFromStep<R> set(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> UpdateFromStep<R> set(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row, Select<? extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> UpdateFromStep<R> set(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row, Select<? extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> UpdateFromStep<R> set(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> UpdateFromStep<R> set(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> UpdateFromStep<R> set(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row, Select<? extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> UpdateFromStep<R> set(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row, Select<? extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> UpdateFromStep<R> set(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row, Select<? extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> UpdateFromStep<R> set(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row, Select<? extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> UpdateFromStep<R> set(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row, Select<? extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> UpdateFromStep<R> set(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row, Select<? extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> UpdateFromStep<R> set(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row, Select<? extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> UpdateFromStep<R> set(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row, Select<? extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select)
  {
    ((UpdateQuery)getDelegate()).addValues(row, select);
    return this;
  }
  
  public final UpdateWhereStep<R> from(TableLike<?> table)
  {
    ((UpdateQuery)getDelegate()).addFrom(table);
    return this;
  }
  
  public final UpdateWhereStep<R> from(TableLike<?>... tables)
  {
    ((UpdateQuery)getDelegate()).addFrom(tables);
    return this;
  }
  
  public final UpdateWhereStep<R> from(Collection<? extends TableLike<?>> tables)
  {
    ((UpdateQuery)getDelegate()).addFrom(tables);
    return this;
  }
  
  public final UpdateWhereStep<R> from(String sql)
  {
    return from(DSL.table(sql));
  }
  
  public final UpdateWhereStep<R> from(String sql, Object... bindings)
  {
    return from(DSL.table(sql, bindings));
  }
  
  public final UpdateWhereStep<R> from(String sql, QueryPart... parts)
  {
    return from(DSL.table(sql, parts));
  }
  
  public final UpdateImpl<R> where(Condition... conditions)
  {
    ((UpdateQuery)getDelegate()).addConditions(conditions);
    return this;
  }
  
  public final UpdateImpl<R> where(Collection<? extends Condition> conditions)
  {
    ((UpdateQuery)getDelegate()).addConditions(conditions);
    return this;
  }
  
  public final UpdateImpl<R> where(Field<Boolean> condition)
  {
    return where(new Condition[] { DSL.condition(condition) });
  }
  
  public final UpdateImpl<R> where(String sql)
  {
    return where(new Condition[] { DSL.condition(sql) });
  }
  
  public final UpdateImpl<R> where(String sql, Object... bindings)
  {
    return where(new Condition[] { DSL.condition(sql, bindings) });
  }
  
  public final UpdateImpl<R> where(String sql, QueryPart... parts)
  {
    return where(new Condition[] { DSL.condition(sql, parts) });
  }
  
  public final UpdateImpl<R> whereExists(Select<?> select)
  {
    return andExists(select);
  }
  
  public final UpdateImpl<R> whereNotExists(Select<?> select)
  {
    return andNotExists(select);
  }
  
  public final UpdateImpl<R> and(Condition condition)
  {
    ((UpdateQuery)getDelegate()).addConditions(new Condition[] { condition });
    return this;
  }
  
  public final UpdateImpl<R> and(Field<Boolean> condition)
  {
    return and(DSL.condition(condition));
  }
  
  public final UpdateImpl<R> and(String sql)
  {
    return and(DSL.condition(sql));
  }
  
  public final UpdateImpl<R> and(String sql, Object... bindings)
  {
    return and(DSL.condition(sql, bindings));
  }
  
  public final UpdateImpl<R> and(String sql, QueryPart... parts)
  {
    return and(DSL.condition(sql, parts));
  }
  
  public final UpdateImpl<R> andNot(Condition condition)
  {
    return and(condition.not());
  }
  
  public final UpdateImpl<R> andNot(Field<Boolean> condition)
  {
    return andNot(DSL.condition(condition));
  }
  
  public final UpdateImpl<R> andExists(Select<?> select)
  {
    return and(DSL.exists(select));
  }
  
  public final UpdateImpl<R> andNotExists(Select<?> select)
  {
    return and(DSL.notExists(select));
  }
  
  public final UpdateImpl<R> or(Condition condition)
  {
    ((UpdateQuery)getDelegate()).addConditions(Operator.OR, new Condition[] { condition });
    return this;
  }
  
  public final UpdateImpl<R> or(Field<Boolean> condition)
  {
    return or(DSL.condition(condition));
  }
  
  public final UpdateImpl<R> or(String sql)
  {
    return or(DSL.condition(sql));
  }
  
  public final UpdateImpl<R> or(String sql, Object... bindings)
  {
    return or(DSL.condition(sql, bindings));
  }
  
  public final UpdateImpl<R> or(String sql, QueryPart... parts)
  {
    return or(DSL.condition(sql, parts));
  }
  
  public final UpdateImpl<R> orNot(Condition condition)
  {
    return or(condition.not());
  }
  
  public final UpdateImpl<R> orNot(Field<Boolean> condition)
  {
    return orNot(DSL.condition(condition));
  }
  
  public final UpdateImpl<R> orExists(Select<?> select)
  {
    return or(DSL.exists(select));
  }
  
  public final UpdateImpl<R> orNotExists(Select<?> select)
  {
    return or(DSL.notExists(select));
  }
  
  public final UpdateImpl<R> returning()
  {
    ((UpdateQuery)getDelegate()).setReturning();
    return this;
  }
  
  public final UpdateImpl<R> returning(Field<?>... f)
  {
    ((UpdateQuery)getDelegate()).setReturning(f);
    return this;
  }
  
  public final UpdateImpl<R> returning(Collection<? extends Field<?>> f)
  {
    ((UpdateQuery)getDelegate()).setReturning(f);
    return this;
  }
  
  public final Result<R> fetch()
  {
    ((UpdateQuery)getDelegate()).execute();
    return ((UpdateQuery)getDelegate()).getReturnedRecords();
  }
  
  public final R fetchOne()
  {
    ((UpdateQuery)getDelegate()).execute();
    return ((UpdateQuery)getDelegate()).getReturnedRecord();
  }
}

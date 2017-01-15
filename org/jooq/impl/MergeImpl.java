package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeMatchedDeleteStep;
import org.jooq.MergeMatchedSetMoreStep;
import org.jooq.MergeMatchedSetStep;
import org.jooq.MergeNotMatchedSetMoreStep;
import org.jooq.MergeNotMatchedSetStep;
import org.jooq.MergeNotMatchedValuesStep1;
import org.jooq.MergeNotMatchedValuesStep10;
import org.jooq.MergeNotMatchedValuesStep11;
import org.jooq.MergeNotMatchedValuesStep12;
import org.jooq.MergeNotMatchedValuesStep13;
import org.jooq.MergeNotMatchedValuesStep14;
import org.jooq.MergeNotMatchedValuesStep15;
import org.jooq.MergeNotMatchedValuesStep16;
import org.jooq.MergeNotMatchedValuesStep17;
import org.jooq.MergeNotMatchedValuesStep18;
import org.jooq.MergeNotMatchedValuesStep19;
import org.jooq.MergeNotMatchedValuesStep2;
import org.jooq.MergeNotMatchedValuesStep20;
import org.jooq.MergeNotMatchedValuesStep21;
import org.jooq.MergeNotMatchedValuesStep22;
import org.jooq.MergeNotMatchedValuesStep3;
import org.jooq.MergeNotMatchedValuesStep4;
import org.jooq.MergeNotMatchedValuesStep5;
import org.jooq.MergeNotMatchedValuesStep6;
import org.jooq.MergeNotMatchedValuesStep7;
import org.jooq.MergeNotMatchedValuesStep8;
import org.jooq.MergeNotMatchedValuesStep9;
import org.jooq.MergeNotMatchedValuesStepN;
import org.jooq.MergeOnConditionStep;
import org.jooq.MergeOnStep;
import org.jooq.MergeUsingStep;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.UniqueKey;
import org.jooq.exception.SQLDialectNotSupportedException;

class MergeImpl<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>
  extends AbstractQuery
  implements MergeUsingStep<R>, MergeKeyStep1<R, T1>, MergeKeyStep2<R, T1, T2>, MergeKeyStep3<R, T1, T2, T3>, MergeKeyStep4<R, T1, T2, T3, T4>, MergeKeyStep5<R, T1, T2, T3, T4, T5>, MergeKeyStep6<R, T1, T2, T3, T4, T5, T6>, MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7>, MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>, MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>, MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, MergeOnStep<R>, MergeOnConditionStep<R>, MergeMatchedSetMoreStep<R>, MergeMatchedDeleteStep<R>, MergeNotMatchedSetMoreStep<R>, MergeNotMatchedValuesStep1<R, T1>, MergeNotMatchedValuesStep2<R, T1, T2>, MergeNotMatchedValuesStep3<R, T1, T2, T3>, MergeNotMatchedValuesStep4<R, T1, T2, T3, T4>, MergeNotMatchedValuesStep5<R, T1, T2, T3, T4, T5>, MergeNotMatchedValuesStep6<R, T1, T2, T3, T4, T5, T6>, MergeNotMatchedValuesStep7<R, T1, T2, T3, T4, T5, T6, T7>, MergeNotMatchedValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>, MergeNotMatchedValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>, MergeNotMatchedValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, MergeNotMatchedValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, MergeNotMatchedValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, MergeNotMatchedValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, MergeNotMatchedValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, MergeNotMatchedValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, MergeNotMatchedValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, MergeNotMatchedValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, MergeNotMatchedValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, MergeNotMatchedValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, MergeNotMatchedValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, MergeNotMatchedValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, MergeNotMatchedValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, MergeNotMatchedValuesStepN<R>
{
  private static final long serialVersionUID = -8835479296876774391L;
  private static final Clause[] CLAUSES = { Clause.MERGE };
  private final Table<R> table;
  private final ConditionProviderImpl on;
  private TableLike<?> using;
  private Condition matchedWhere;
  private Condition matchedDeleteWhere;
  private Condition notMatchedWhere;
  private boolean matchedClause;
  private FieldMapForUpdate matchedUpdate;
  private boolean notMatchedClause;
  private FieldMapForInsert notMatchedInsert;
  private boolean h2Style;
  private QueryPartList<Field<?>> h2Fields;
  private QueryPartList<Field<?>> h2Keys;
  private QueryPartList<Field<?>> h2Values;
  private Select<?> h2Select;
  
  MergeImpl(Configuration configuration, Table<R> table)
  {
    this(configuration, table, null);
  }
  
  MergeImpl(Configuration configuration, Table<R> table, Collection<? extends Field<?>> fields)
  {
    super(configuration);
    
    this.table = table;
    this.on = new ConditionProviderImpl();
    if (fields != null)
    {
      this.h2Style = true;
      this.h2Fields = new QueryPartList(fields);
    }
  }
  
  QueryPartList<Field<?>> getH2Fields()
  {
    if (this.h2Fields == null) {
      this.h2Fields = new QueryPartList(this.table.fields());
    }
    return this.h2Fields;
  }
  
  QueryPartList<Field<?>> getH2Keys()
  {
    if (this.h2Keys == null) {
      this.h2Keys = new QueryPartList();
    }
    return this.h2Keys;
  }
  
  QueryPartList<Field<?>> getH2Values()
  {
    if (this.h2Values == null) {
      this.h2Values = new QueryPartList();
    }
    return this.h2Values;
  }
  
  public final MergeImpl select(Select select)
  {
    this.h2Style = true;
    this.h2Select = select;
    return this;
  }
  
  public final MergeImpl key(Field<?>... k)
  {
    return key(Arrays.asList(k));
  }
  
  public final MergeImpl key(Collection<? extends Field<?>> keys)
  {
    this.h2Style = true;
    getH2Keys().addAll(keys);
    return this;
  }
  
  public final MergeImpl values(T1 value1)
  {
    return values(new Object[] { value1 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2)
  {
    return values(new Object[] { value1, value2 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3)
  {
    return values(new Object[] { value1, value2, value3 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4)
  {
    return values(new Object[] { value1, value2, value3, value4 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5)
  {
    return values(new Object[] { value1, value2, value3, value4, value5 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20, T21 value21)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21 });
  }
  
  public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20, T21 value21, T22 value22)
  {
    return values(new Object[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22 });
  }
  
  public final MergeImpl values(Field<T1> value1)
  {
    return values(new Field[] { value1 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2)
  {
    return values(new Field[] { value1, value2 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3)
  {
    return values(new Field[] { value1, value2, value3 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4)
  {
    return values(new Field[] { value1, value2, value3, value4 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5)
  {
    return values(new Field[] { value1, value2, value3, value4, value5 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20, Field<T21> value21)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21 });
  }
  
  public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20, Field<T21> value21, Field<T22> value22)
  {
    return values(new Field[] { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22 });
  }
  
  public final MergeImpl values(Object... values)
  {
    if (this.using == null)
    {
      this.h2Style = true;
      getH2Values().addAll(Utils.fields(values, (Field[])getH2Fields().toArray(new Field[0])));
    }
    else
    {
      Field<?>[] fields = (Field[])this.notMatchedInsert.keySet().toArray(new Field[0]);
      this.notMatchedInsert.putValues(Utils.fields(values, fields));
    }
    return this;
  }
  
  public final MergeImpl values(Field<?>... values)
  {
    return values((Object[])values);
  }
  
  public final MergeImpl values(Collection<?> values)
  {
    return values(values.toArray());
  }
  
  public final MergeImpl using(TableLike<?> u)
  {
    this.using = u;
    return this;
  }
  
  public final MergeImpl usingDual()
  {
    this.using = create().selectOne();
    return this;
  }
  
  public final MergeImpl on(Condition... conditions)
  {
    this.on.addConditions(conditions);
    return this;
  }
  
  public final MergeOnConditionStep<R> on(Field<Boolean> condition)
  {
    return on(new Condition[] { DSL.condition(condition) });
  }
  
  public final MergeImpl on(String sql)
  {
    return on(new Condition[] { DSL.condition(sql) });
  }
  
  public final MergeImpl on(String sql, Object... bindings)
  {
    return on(new Condition[] { DSL.condition(sql, bindings) });
  }
  
  public final MergeImpl on(String sql, QueryPart... parts)
  {
    return on(new Condition[] { DSL.condition(sql, parts) });
  }
  
  public final MergeImpl and(Condition condition)
  {
    this.on.addConditions(new Condition[] { condition });
    return this;
  }
  
  public final MergeImpl and(Field<Boolean> condition)
  {
    return and(DSL.condition(condition));
  }
  
  public final MergeImpl and(String sql)
  {
    return and(DSL.condition(sql));
  }
  
  public final MergeImpl and(String sql, Object... bindings)
  {
    return and(DSL.condition(sql, bindings));
  }
  
  public final MergeImpl and(String sql, QueryPart... parts)
  {
    return and(DSL.condition(sql, parts));
  }
  
  public final MergeImpl andNot(Condition condition)
  {
    return and(condition.not());
  }
  
  public final MergeImpl andNot(Field<Boolean> condition)
  {
    return and(DSL.condition(condition));
  }
  
  public final MergeImpl andExists(Select<?> select)
  {
    return and(DSL.exists(select));
  }
  
  public final MergeImpl andNotExists(Select<?> select)
  {
    return and(DSL.notExists(select));
  }
  
  public final MergeImpl or(Condition condition)
  {
    this.on.addConditions(Operator.OR, new Condition[] { condition });
    return this;
  }
  
  public final MergeImpl or(Field<Boolean> condition)
  {
    return and(DSL.condition(condition));
  }
  
  public final MergeImpl or(String sql)
  {
    return or(DSL.condition(sql));
  }
  
  public final MergeImpl or(String sql, Object... bindings)
  {
    return or(DSL.condition(sql, bindings));
  }
  
  public final MergeImpl or(String sql, QueryPart... parts)
  {
    return or(DSL.condition(sql, parts));
  }
  
  public final MergeImpl orNot(Condition condition)
  {
    return or(condition.not());
  }
  
  public final MergeImpl orNot(Field<Boolean> condition)
  {
    return and(DSL.condition(condition));
  }
  
  public final MergeImpl orExists(Select<?> select)
  {
    return or(DSL.exists(select));
  }
  
  public final MergeImpl orNotExists(Select<?> select)
  {
    return or(DSL.notExists(select));
  }
  
  public final MergeImpl whenMatchedThenUpdate()
  {
    this.matchedClause = true;
    this.matchedUpdate = new FieldMapForUpdate(Clause.MERGE_SET_ASSIGNMENT);
    
    this.notMatchedClause = false;
    return this;
  }
  
  public final <T> MergeImpl set(Field<T> field, T value)
  {
    return set(field, Utils.field(value, field));
  }
  
  public final <T> MergeImpl set(Field<T> field, Field<T> value)
  {
    if (this.matchedClause) {
      this.matchedUpdate.put(field, DSL.nullSafe(value));
    } else if (this.notMatchedClause) {
      this.notMatchedInsert.put(field, DSL.nullSafe(value));
    } else {
      throw new IllegalStateException("Cannot call where() on the current state of the MERGE statement");
    }
    return this;
  }
  
  public final <T> MergeImpl set(Field<T> field, Select<? extends Record1<T>> value)
  {
    return set(field, value.asField());
  }
  
  public final MergeImpl set(Map<? extends Field<?>, ?> map)
  {
    if (this.matchedClause) {
      this.matchedUpdate.set(map);
    } else if (this.notMatchedClause) {
      this.notMatchedInsert.set(map);
    } else {
      throw new IllegalStateException("Cannot call where() on the current state of the MERGE statement");
    }
    return this;
  }
  
  public final MergeImpl set(Record record)
  {
    return set(Utils.map(record));
  }
  
  public final MergeImpl whenNotMatchedThenInsert()
  {
    return whenNotMatchedThenInsert(Collections.emptyList());
  }
  
  public final <T1> MergeImpl whenNotMatchedThenInsert(Field<T1> field1)
  {
    return whenNotMatchedThenInsert(new Field[] { field1 });
  }
  
  public final <T1, T2> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2 });
  }
  
  public final <T1, T2, T3> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3 });
  }
  
  public final <T1, T2, T3, T4> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4 });
  }
  
  public final <T1, T2, T3, T4, T5> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5 });
  }
  
  public final <T1, T2, T3, T4, T5, T6> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return whenNotMatchedThenInsert(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  public final MergeImpl whenNotMatchedThenInsert(Field<?>... fields)
  {
    return whenNotMatchedThenInsert(Arrays.asList(fields));
  }
  
  public final MergeImpl whenNotMatchedThenInsert(Collection<? extends Field<?>> fields)
  {
    this.notMatchedClause = true;
    this.notMatchedInsert = new FieldMapForInsert();
    this.notMatchedInsert.putFields(fields);
    
    this.matchedClause = false;
    return this;
  }
  
  public final MergeImpl where(Condition condition)
  {
    if (this.matchedClause) {
      this.matchedWhere = condition;
    } else if (this.notMatchedClause) {
      this.notMatchedWhere = condition;
    } else {
      throw new IllegalStateException("Cannot call where() on the current state of the MERGE statement");
    }
    return this;
  }
  
  public final MergeMatchedDeleteStep<R> where(Field<Boolean> condition)
  {
    return where(DSL.condition(condition));
  }
  
  public final MergeImpl deleteWhere(Condition condition)
  {
    this.matchedDeleteWhere = condition;
    return this;
  }
  
  public final MergeImpl deleteWhere(Field<Boolean> condition)
  {
    return deleteWhere(DSL.condition(condition));
  }
  
  private final QueryPart getStandardMerge(Configuration config)
  {
    switch (config.dialect().family())
    {
    case CUBRID: 
    case HSQLDB: 
      Table<?> src;
      Table<?> src;
      if (this.h2Select != null)
      {
        List<Field<?>> v = new ArrayList();
        Row row = this.h2Select.fieldsRow();
        for (int i = 0; i < row.size(); i++) {
          v.add(row.field(i).as("s" + (i + 1)));
        }
        src = create(config).select(v).from(this.h2Select).asTable("src");
      }
      else
      {
        List<Field<?>> v = new ArrayList();
        for (int i = 0; i < getH2Values().size(); i++) {
          v.add(((Field)getH2Values().get(i)).as("s" + (i + 1)));
        }
        src = create(config).select(v).asTable("src");
      }
      Set<Field<?>> onFields = new HashSet();
      Condition condition = null;
      if (getH2Keys().isEmpty())
      {
        UniqueKey<?> key = this.table.getPrimaryKey();
        if (key != null)
        {
          onFields.addAll(key.getFields());
          for (int i = 0; i < key.getFields().size(); i++)
          {
            Condition rhs = ((TableField)key.getFields().get(i)).equal(src.field(i));
            if (condition == null) {
              condition = rhs;
            } else {
              condition = condition.and(rhs);
            }
          }
        }
        else
        {
          throw new IllegalStateException("Cannot omit KEY() clause on a non-Updatable Table");
        }
      }
      else
      {
        for (int i = 0; i < getH2Keys().size(); i++)
        {
          int matchIndex = getH2Fields().indexOf(getH2Keys().get(i));
          if (matchIndex == -1) {
            throw new IllegalStateException("Fields in KEY() clause must be part of the fields specified in MERGE INTO table (...)");
          }
          onFields.addAll(getH2Keys());
          Condition rhs = ((Field)getH2Keys().get(i)).equal(src.field(matchIndex));
          if (condition == null) {
            condition = rhs;
          } else {
            condition = condition.and(rhs);
          }
        }
      }
      Map<Field<?>, Field<?>> update = new LinkedHashMap();
      Map<Field<?>, Field<?>> insert = new LinkedHashMap();
      for (int i = 0; i < src.fieldsRow().size(); i++)
      {
        if (!onFields.contains(getH2Fields().get(i))) {
          update.put(getH2Fields().get(i), src.field(i));
        }
        insert.put(getH2Fields().get(i), src.field(i));
      }
      return create(config).mergeInto(this.table).using(src).on(new Condition[] { condition }).whenMatchedThenUpdate().set(update).whenNotMatchedThenInsert().set(insert);
    }
    throw new SQLDialectNotSupportedException("The H2-specific MERGE syntax is not supported in dialect : " + config.dialect());
  }
  
  public final void accept(Context<?> ctx)
  {
    if (this.h2Style)
    {
      if (ctx.configuration().dialect() == SQLDialect.H2) {
        toSQLH2(ctx);
      } else {
        ctx.visit(getStandardMerge(ctx.configuration()));
      }
    }
    else {
      toSQLStandard(ctx);
    }
  }
  
  private final void toSQLH2(Context<?> ctx)
  {
    ctx.keyword("merge into").sql(" ").declareTables(true).visit(this.table).formatSeparator();
    
    ctx.sql("(");
    Utils.fieldNames(ctx, getH2Fields());
    ctx.sql(")");
    if (!getH2Keys().isEmpty())
    {
      ctx.sql(" ").keyword("key").sql(" (");
      Utils.fieldNames(ctx, getH2Keys());
      ctx.sql(")");
    }
    if (this.h2Select != null) {
      ctx.sql(" ").visit(this.h2Select);
    } else {
      ctx.sql(" ").keyword("values").sql(" (").visit(getH2Values()).sql(")");
    }
  }
  
  private final void toSQLStandard(Context<?> ctx)
  {
    ctx.start(Clause.MERGE_MERGE_INTO).keyword("merge into").sql(" ").declareTables(true).visit(this.table).declareTables(false).end(Clause.MERGE_MERGE_INTO).formatSeparator().start(Clause.MERGE_USING).declareTables(true).keyword("using").sql(" ").formatIndentStart().formatNewLine();
    ctx.data("org.jooq.configuration.wrap-derived-tables-in-parentheses", Boolean.valueOf(true));
    ctx.visit(this.using);
    ctx.data("org.jooq.configuration.wrap-derived-tables-in-parentheses", null);
    ctx.formatIndentEnd()
      .declareTables(false);
    
    boolean onParentheses = false;
    ctx.end(Clause.MERGE_USING)
      .formatSeparator()
      .start(Clause.MERGE_ON)
      
      .keyword("on").sql(onParentheses ? " (" : " ")
      .visit(this.on)
      .sql(onParentheses ? ")" : "")
      .end(Clause.MERGE_ON)
      .start(Clause.MERGE_WHEN_MATCHED_THEN_UPDATE)
      .start(Clause.MERGE_SET);
    if (this.matchedUpdate != null) {
      ctx.formatSeparator().keyword("when matched then update set").formatIndentStart().formatSeparator().visit(this.matchedUpdate).formatIndentEnd();
    }
    ctx.end(Clause.MERGE_SET).start(Clause.MERGE_WHERE);
    if (this.matchedWhere != null) {
      ctx.formatSeparator().keyword("where").sql(" ").visit(this.matchedWhere);
    }
    ctx.end(Clause.MERGE_WHERE).start(Clause.MERGE_DELETE_WHERE);
    if (this.matchedDeleteWhere != null) {
      ctx.formatSeparator().keyword("delete where").sql(" ").visit(this.matchedDeleteWhere);
    }
    ctx.end(Clause.MERGE_DELETE_WHERE).end(Clause.MERGE_WHEN_MATCHED_THEN_UPDATE).start(Clause.MERGE_WHEN_NOT_MATCHED_THEN_INSERT);
    if (this.notMatchedInsert != null)
    {
      ctx.formatSeparator().keyword("when not matched then insert").sql(" ");
      this.notMatchedInsert.toSQLReferenceKeys(ctx);
      ctx.formatSeparator()
        .start(Clause.MERGE_VALUES)
        .keyword("values").sql(" ")
        .visit(this.notMatchedInsert)
        .end(Clause.MERGE_VALUES);
    }
    ctx.start(Clause.MERGE_WHERE);
    if (this.notMatchedWhere != null) {
      ctx.formatSeparator().keyword("where").sql(" ").visit(this.notMatchedWhere);
    }
    ctx.end(Clause.MERGE_WHERE).end(Clause.MERGE_WHEN_NOT_MATCHED_THEN_INSERT);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

package org.jooq.impl;

import java.util.Collection;
import org.jooq.Field;
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

class RecordImpl<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>
  extends AbstractRecord
  implements Record1<T1>, Record2<T1, T2>, Record3<T1, T2, T3>, Record4<T1, T2, T3, T4>, Record5<T1, T2, T3, T4, T5>, Record6<T1, T2, T3, T4, T5, T6>, Record7<T1, T2, T3, T4, T5, T6, T7>, Record8<T1, T2, T3, T4, T5, T6, T7, T8>, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>
{
  private static final long serialVersionUID = -2201346180421463830L;
  
  public RecordImpl(Field<?>... fields)
  {
    super(fields);
  }
  
  public RecordImpl(Collection<? extends Field<?>> fields)
  {
    super(fields);
  }
  
  public RowImpl<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> fieldsRow()
  {
    return this.fields;
  }
  
  public final RowImpl<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> valuesRow()
  {
    return new RowImpl(Utils.fields(intoArray(), this.fields.fields()));
  }
  
  public final Field<T1> field1()
  {
    return this.fields.field(0);
  }
  
  public final Field<T2> field2()
  {
    return this.fields.field(1);
  }
  
  public final Field<T3> field3()
  {
    return this.fields.field(2);
  }
  
  public final Field<T4> field4()
  {
    return this.fields.field(3);
  }
  
  public final Field<T5> field5()
  {
    return this.fields.field(4);
  }
  
  public final Field<T6> field6()
  {
    return this.fields.field(5);
  }
  
  public final Field<T7> field7()
  {
    return this.fields.field(6);
  }
  
  public final Field<T8> field8()
  {
    return this.fields.field(7);
  }
  
  public final Field<T9> field9()
  {
    return this.fields.field(8);
  }
  
  public final Field<T10> field10()
  {
    return this.fields.field(9);
  }
  
  public final Field<T11> field11()
  {
    return this.fields.field(10);
  }
  
  public final Field<T12> field12()
  {
    return this.fields.field(11);
  }
  
  public final Field<T13> field13()
  {
    return this.fields.field(12);
  }
  
  public final Field<T14> field14()
  {
    return this.fields.field(13);
  }
  
  public final Field<T15> field15()
  {
    return this.fields.field(14);
  }
  
  public final Field<T16> field16()
  {
    return this.fields.field(15);
  }
  
  public final Field<T17> field17()
  {
    return this.fields.field(16);
  }
  
  public final Field<T18> field18()
  {
    return this.fields.field(17);
  }
  
  public final Field<T19> field19()
  {
    return this.fields.field(18);
  }
  
  public final Field<T20> field20()
  {
    return this.fields.field(19);
  }
  
  public final Field<T21> field21()
  {
    return this.fields.field(20);
  }
  
  public final Field<T22> field22()
  {
    return this.fields.field(21);
  }
  
  public final T1 value1()
  {
    return (T1)getValue(0);
  }
  
  public final T2 value2()
  {
    return (T2)getValue(1);
  }
  
  public final T3 value3()
  {
    return (T3)getValue(2);
  }
  
  public final T4 value4()
  {
    return (T4)getValue(3);
  }
  
  public final T5 value5()
  {
    return (T5)getValue(4);
  }
  
  public final T6 value6()
  {
    return (T6)getValue(5);
  }
  
  public final T7 value7()
  {
    return (T7)getValue(6);
  }
  
  public final T8 value8()
  {
    return (T8)getValue(7);
  }
  
  public final T9 value9()
  {
    return (T9)getValue(8);
  }
  
  public final T10 value10()
  {
    return (T10)getValue(9);
  }
  
  public final T11 value11()
  {
    return (T11)getValue(10);
  }
  
  public final T12 value12()
  {
    return (T12)getValue(11);
  }
  
  public final T13 value13()
  {
    return (T13)getValue(12);
  }
  
  public final T14 value14()
  {
    return (T14)getValue(13);
  }
  
  public final T15 value15()
  {
    return (T15)getValue(14);
  }
  
  public final T16 value16()
  {
    return (T16)getValue(15);
  }
  
  public final T17 value17()
  {
    return (T17)getValue(16);
  }
  
  public final T18 value18()
  {
    return (T18)getValue(17);
  }
  
  public final T19 value19()
  {
    return (T19)getValue(18);
  }
  
  public final T20 value20()
  {
    return (T20)getValue(19);
  }
  
  public final T21 value21()
  {
    return (T21)getValue(20);
  }
  
  public final T22 value22()
  {
    return (T22)getValue(21);
  }
  
  public final RecordImpl value1(T1 value)
  {
    setValue(0, value);
    return this;
  }
  
  public final RecordImpl value2(T2 value)
  {
    setValue(1, value);
    return this;
  }
  
  public final RecordImpl value3(T3 value)
  {
    setValue(2, value);
    return this;
  }
  
  public final RecordImpl value4(T4 value)
  {
    setValue(3, value);
    return this;
  }
  
  public final RecordImpl value5(T5 value)
  {
    setValue(4, value);
    return this;
  }
  
  public final RecordImpl value6(T6 value)
  {
    setValue(5, value);
    return this;
  }
  
  public final RecordImpl value7(T7 value)
  {
    setValue(6, value);
    return this;
  }
  
  public final RecordImpl value8(T8 value)
  {
    setValue(7, value);
    return this;
  }
  
  public final RecordImpl value9(T9 value)
  {
    setValue(8, value);
    return this;
  }
  
  public final RecordImpl value10(T10 value)
  {
    setValue(9, value);
    return this;
  }
  
  public final RecordImpl value11(T11 value)
  {
    setValue(10, value);
    return this;
  }
  
  public final RecordImpl value12(T12 value)
  {
    setValue(11, value);
    return this;
  }
  
  public final RecordImpl value13(T13 value)
  {
    setValue(12, value);
    return this;
  }
  
  public final RecordImpl value14(T14 value)
  {
    setValue(13, value);
    return this;
  }
  
  public final RecordImpl value15(T15 value)
  {
    setValue(14, value);
    return this;
  }
  
  public final RecordImpl value16(T16 value)
  {
    setValue(15, value);
    return this;
  }
  
  public final RecordImpl value17(T17 value)
  {
    setValue(16, value);
    return this;
  }
  
  public final RecordImpl value18(T18 value)
  {
    setValue(17, value);
    return this;
  }
  
  public final RecordImpl value19(T19 value)
  {
    setValue(18, value);
    return this;
  }
  
  public final RecordImpl value20(T20 value)
  {
    setValue(19, value);
    return this;
  }
  
  public final RecordImpl value21(T21 value)
  {
    setValue(20, value);
    return this;
  }
  
  public final RecordImpl value22(T22 value)
  {
    setValue(21, value);
    return this;
  }
  
  public final RecordImpl values(T1 t1)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21)
  {
    return null;
  }
  
  public final RecordImpl values(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22)
  {
    return null;
  }
}

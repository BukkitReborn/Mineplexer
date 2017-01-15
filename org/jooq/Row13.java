package org.jooq;

import java.util.Collection;

public abstract interface Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
  extends Row
{
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  public abstract Field<T4> field4();
  
  public abstract Field<T5> field5();
  
  public abstract Field<T6> field6();
  
  public abstract Field<T7> field7();
  
  public abstract Field<T8> field8();
  
  public abstract Field<T9> field9();
  
  public abstract Field<T10> field10();
  
  public abstract Field<T11> field11();
  
  public abstract Field<T12> field12();
  
  public abstract Field<T13> field13();
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition equal(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition equal(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition eq(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition eq(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition notEqual(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition ne(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition ne(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition lessThan(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition lt(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition lt(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition lessOrEqual(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition le(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition le(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition greaterThan(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition gt(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition gt(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition greaterOrEqual(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract Condition ge(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract Condition ge(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> between(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> between(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> between(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition between(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow131, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow132);
  
  @Support
  public abstract Condition between(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord131, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord132);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> betweenSymmetric(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> betweenSymmetric(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition betweenSymmetric(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow131, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow132);
  
  @Support
  public abstract Condition betweenSymmetric(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord131, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord132);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetween(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetween(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetween(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition notBetween(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow131, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow132);
  
  @Support
  public abstract Condition notBetween(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord131, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord132);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12, T13 paramT13);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetweenSymmetric(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13);
  
  @Support
  public abstract BetweenAndStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> notBetweenSymmetric(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord13);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow131, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow132);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord131, Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRecord132);
  
  @Support
  public abstract Condition in(Collection<? extends Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramResult);
  
  @Support
  public abstract Condition in(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramResult);
  
  @Support
  public abstract Condition notIn(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
}

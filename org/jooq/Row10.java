package org.jooq;

import java.util.Collection;

public abstract interface Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
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
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition equal(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition equal(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition eq(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition eq(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition notEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition ne(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition ne(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition lessThan(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition lt(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition lt(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition lessOrEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition le(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition le(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition greaterThan(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition gt(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition gt(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition greaterOrEqual(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract Condition ge(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract Condition ge(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> between(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition between(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow101, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow102);
  
  @Support
  public abstract Condition between(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord101, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord102);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> betweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition betweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow101, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow102);
  
  @Support
  public abstract Condition betweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord101, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord102);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetween(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition notBetween(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow101, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow102);
  
  @Support
  public abstract Condition notBetween(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord101, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord102);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10);
  
  @Support
  public abstract BetweenAndStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> notBetweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord10);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow101, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow102);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord101, Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRecord102);
  
  @Support
  public abstract Condition in(Collection<? extends Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramResult);
  
  @Support
  public abstract Condition in(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramResult);
  
  @Support
  public abstract Condition notIn(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
}

package org.jooq;

import java.util.Collection;

public abstract interface Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
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
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition equal(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition equal(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition eq(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition eq(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition notEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition ne(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition ne(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition lessThan(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition lt(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition lt(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition lessOrEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition le(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition le(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition greaterThan(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition gt(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition gt(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition greaterOrEqual(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract Condition ge(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract Condition ge(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> between(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition between(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow91, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow92);
  
  @Support
  public abstract Condition between(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord91, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord92);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> betweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition betweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow91, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow92);
  
  @Support
  public abstract Condition betweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord91, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord92);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetween(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition notBetween(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow91, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow92);
  
  @Support
  public abstract Condition notBetween(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord91, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord92);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9);
  
  @Support
  public abstract BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> notBetweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord9);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow91, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow92);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord91, Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRecord92);
  
  @Support
  public abstract Condition in(Collection<? extends Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramResult);
  
  @Support
  public abstract Condition in(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramResult);
  
  @Support
  public abstract Condition notIn(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
}

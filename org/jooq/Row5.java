package org.jooq;

import java.util.Collection;

public abstract interface Row5<T1, T2, T3, T4, T5>
  extends Row
{
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  public abstract Field<T4> field4();
  
  public abstract Field<T5> field5();
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition equal(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition equal(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition eq(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition eq(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition notEqual(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition ne(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition ne(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition lessThan(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition lt(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition lt(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition lessOrEqual(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition le(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition le(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition greaterThan(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition gt(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition gt(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition greaterOrEqual(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition ge(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition ge(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record5<T1, T2, T3, T4, T5>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> between(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> between(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> between(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition between(Row5<T1, T2, T3, T4, T5> paramRow51, Row5<T1, T2, T3, T4, T5> paramRow52);
  
  @Support
  public abstract Condition between(Record5<T1, T2, T3, T4, T5> paramRecord51, Record5<T1, T2, T3, T4, T5> paramRecord52);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> betweenSymmetric(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition betweenSymmetric(Row5<T1, T2, T3, T4, T5> paramRow51, Row5<T1, T2, T3, T4, T5> paramRow52);
  
  @Support
  public abstract Condition betweenSymmetric(Record5<T1, T2, T3, T4, T5> paramRecord51, Record5<T1, T2, T3, T4, T5> paramRecord52);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> notBetween(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition notBetween(Row5<T1, T2, T3, T4, T5> paramRow51, Row5<T1, T2, T3, T4, T5> paramRow52);
  
  @Support
  public abstract Condition notBetween(Record5<T1, T2, T3, T4, T5> paramRecord51, Record5<T1, T2, T3, T4, T5> paramRecord52);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract BetweenAndStep5<T1, T2, T3, T4, T5> notBetweenSymmetric(Record5<T1, T2, T3, T4, T5> paramRecord5);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row5<T1, T2, T3, T4, T5> paramRow51, Row5<T1, T2, T3, T4, T5> paramRow52);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record5<T1, T2, T3, T4, T5> paramRecord51, Record5<T1, T2, T3, T4, T5> paramRecord52);
  
  @Support
  public abstract Condition in(Collection<? extends Row5<T1, T2, T3, T4, T5>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record5<T1, T2, T3, T4, T5>> paramResult);
  
  @Support
  public abstract Condition in(Row5<T1, T2, T3, T4, T5>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record5<T1, T2, T3, T4, T5>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row5<T1, T2, T3, T4, T5>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record5<T1, T2, T3, T4, T5>> paramResult);
  
  @Support
  public abstract Condition notIn(Row5<T1, T2, T3, T4, T5>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record5<T1, T2, T3, T4, T5>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
}

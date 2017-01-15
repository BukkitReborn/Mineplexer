package org.jooq;

import java.util.Collection;

public abstract interface Row4<T1, T2, T3, T4>
  extends Row
{
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  public abstract Field<T4> field4();
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition equal(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition equal(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition eq(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition eq(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition notEqual(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition ne(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition ne(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition lessThan(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition lt(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition lt(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition lessOrEqual(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition le(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition le(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition greaterThan(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition gt(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition gt(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition greaterOrEqual(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition ge(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition ge(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record4<T1, T2, T3, T4>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> between(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> between(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> between(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition between(Row4<T1, T2, T3, T4> paramRow41, Row4<T1, T2, T3, T4> paramRow42);
  
  @Support
  public abstract Condition between(Record4<T1, T2, T3, T4> paramRecord41, Record4<T1, T2, T3, T4> paramRecord42);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> betweenSymmetric(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition betweenSymmetric(Row4<T1, T2, T3, T4> paramRow41, Row4<T1, T2, T3, T4> paramRow42);
  
  @Support
  public abstract Condition betweenSymmetric(Record4<T1, T2, T3, T4> paramRecord41, Record4<T1, T2, T3, T4> paramRecord42);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> notBetween(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> notBetween(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> notBetween(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition notBetween(Row4<T1, T2, T3, T4> paramRow41, Row4<T1, T2, T3, T4> paramRow42);
  
  @Support
  public abstract Condition notBetween(Record4<T1, T2, T3, T4> paramRecord41, Record4<T1, T2, T3, T4> paramRecord42);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract BetweenAndStep4<T1, T2, T3, T4> notBetweenSymmetric(Record4<T1, T2, T3, T4> paramRecord4);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row4<T1, T2, T3, T4> paramRow41, Row4<T1, T2, T3, T4> paramRow42);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record4<T1, T2, T3, T4> paramRecord41, Record4<T1, T2, T3, T4> paramRecord42);
  
  @Support
  public abstract Condition in(Collection<? extends Row4<T1, T2, T3, T4>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record4<T1, T2, T3, T4>> paramResult);
  
  @Support
  public abstract Condition in(Row4<T1, T2, T3, T4>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record4<T1, T2, T3, T4>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row4<T1, T2, T3, T4>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record4<T1, T2, T3, T4>> paramResult);
  
  @Support
  public abstract Condition notIn(Row4<T1, T2, T3, T4>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record4<T1, T2, T3, T4>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
}

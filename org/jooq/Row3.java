package org.jooq;

import java.util.Collection;

public abstract interface Row3<T1, T2, T3>
  extends Row
{
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition equal(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition equal(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition eq(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition eq(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition notEqual(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition ne(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition ne(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition lessThan(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition lt(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition lt(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition lessOrEqual(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition le(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition le(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition greaterThan(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition gt(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition gt(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition greaterOrEqual(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition ge(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition ge(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record3<T1, T2, T3>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> between(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> between(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> between(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition between(Row3<T1, T2, T3> paramRow31, Row3<T1, T2, T3> paramRow32);
  
  @Support
  public abstract Condition between(Record3<T1, T2, T3> paramRecord31, Record3<T1, T2, T3> paramRecord32);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> betweenSymmetric(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> betweenSymmetric(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition betweenSymmetric(Row3<T1, T2, T3> paramRow31, Row3<T1, T2, T3> paramRow32);
  
  @Support
  public abstract Condition betweenSymmetric(Record3<T1, T2, T3> paramRecord31, Record3<T1, T2, T3> paramRecord32);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> notBetween(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> notBetween(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> notBetween(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition notBetween(Row3<T1, T2, T3> paramRow31, Row3<T1, T2, T3> paramRow32);
  
  @Support
  public abstract Condition notBetween(Record3<T1, T2, T3> paramRecord31, Record3<T1, T2, T3> paramRecord32);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Record3<T1, T2, T3> paramRecord3);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row3<T1, T2, T3> paramRow31, Row3<T1, T2, T3> paramRow32);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record3<T1, T2, T3> paramRecord31, Record3<T1, T2, T3> paramRecord32);
  
  @Support
  public abstract Condition in(Collection<? extends Row3<T1, T2, T3>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record3<T1, T2, T3>> paramResult);
  
  @Support
  public abstract Condition in(Row3<T1, T2, T3>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record3<T1, T2, T3>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row3<T1, T2, T3>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record3<T1, T2, T3>> paramResult);
  
  @Support
  public abstract Condition notIn(Row3<T1, T2, T3>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record3<T1, T2, T3>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record3<T1, T2, T3>> paramSelect);
}

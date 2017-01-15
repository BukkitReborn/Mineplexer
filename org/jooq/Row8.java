package org.jooq;

import java.util.Collection;

public abstract interface Row8<T1, T2, T3, T4, T5, T6, T7, T8>
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
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition equal(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition equal(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition eq(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition eq(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition notEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition ne(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition ne(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition lessThan(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition lt(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition lt(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition lessOrEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition le(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition le(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition greaterThan(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition gt(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition gt(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition greaterOrEqual(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition ge(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition ge(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> between(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition between(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow81, Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow82);
  
  @Support
  public abstract Condition between(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord81, Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord82);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> betweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition betweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow81, Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow82);
  
  @Support
  public abstract Condition betweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord81, Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord82);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetween(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition notBetween(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow81, Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow82);
  
  @Support
  public abstract Condition notBetween(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord81, Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord82);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8> notBetweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow81, Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow82);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord81, Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord82);
  
  @Support
  public abstract Condition in(Collection<? extends Row8<T1, T2, T3, T4, T5, T6, T7, T8>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramResult);
  
  @Support
  public abstract Condition in(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record8<T1, T2, T3, T4, T5, T6, T7, T8>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row8<T1, T2, T3, T4, T5, T6, T7, T8>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramResult);
  
  @Support
  public abstract Condition notIn(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record8<T1, T2, T3, T4, T5, T6, T7, T8>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
}

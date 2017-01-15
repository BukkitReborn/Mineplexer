package org.jooq;

import java.util.Collection;

public abstract interface Row7<T1, T2, T3, T4, T5, T6, T7>
  extends Row
{
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  public abstract Field<T4> field4();
  
  public abstract Field<T5> field5();
  
  public abstract Field<T6> field6();
  
  public abstract Field<T7> field7();
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition equal(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition equal(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition eq(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition eq(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition notEqual(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition ne(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition ne(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition lessThan(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition lt(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition lt(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition lessOrEqual(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition le(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition le(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition greaterThan(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition gt(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition gt(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition greaterOrEqual(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract Condition ge(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract Condition ge(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> between(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition between(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow71, Row7<T1, T2, T3, T4, T5, T6, T7> paramRow72);
  
  @Support
  public abstract Condition between(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord71, Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord72);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> betweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition betweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow71, Row7<T1, T2, T3, T4, T5, T6, T7> paramRow72);
  
  @Support
  public abstract Condition betweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord71, Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord72);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetween(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition notBetween(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow71, Row7<T1, T2, T3, T4, T5, T6, T7> paramRow72);
  
  @Support
  public abstract Condition notBetween(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord71, Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord72);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7);
  
  @Support
  public abstract BetweenAndStep7<T1, T2, T3, T4, T5, T6, T7> notBetweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord7);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow71, Row7<T1, T2, T3, T4, T5, T6, T7> paramRow72);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord71, Record7<T1, T2, T3, T4, T5, T6, T7> paramRecord72);
  
  @Support
  public abstract Condition in(Collection<? extends Row7<T1, T2, T3, T4, T5, T6, T7>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramResult);
  
  @Support
  public abstract Condition in(Row7<T1, T2, T3, T4, T5, T6, T7>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record7<T1, T2, T3, T4, T5, T6, T7>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row7<T1, T2, T3, T4, T5, T6, T7>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramResult);
  
  @Support
  public abstract Condition notIn(Row7<T1, T2, T3, T4, T5, T6, T7>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record7<T1, T2, T3, T4, T5, T6, T7>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
}

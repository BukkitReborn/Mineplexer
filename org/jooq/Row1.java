package org.jooq;

import java.util.Collection;

public abstract interface Row1<T1>
  extends Row
{
  public abstract Field<T1> field1();
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row1<T1> paramRow1);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record1<T1>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition equal(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition equal(T1 paramT1);
  
  @Support
  public abstract Condition equal(Field<T1> paramField);
  
  @Support
  public abstract Condition equal(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition eq(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition eq(T1 paramT1);
  
  @Support
  public abstract Condition eq(Field<T1> paramField);
  
  @Support
  public abstract Condition eq(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition notEqual(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition notEqual(T1 paramT1);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition ne(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition ne(T1 paramT1);
  
  @Support
  public abstract Condition ne(Field<T1> paramField);
  
  @Support
  public abstract Condition ne(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition lessThan(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition lessThan(T1 paramT1);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition lt(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition lt(T1 paramT1);
  
  @Support
  public abstract Condition lt(Field<T1> paramField);
  
  @Support
  public abstract Condition lt(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition lessOrEqual(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition le(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition le(T1 paramT1);
  
  @Support
  public abstract Condition le(Field<T1> paramField);
  
  @Support
  public abstract Condition le(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition greaterThan(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition gt(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition gt(T1 paramT1);
  
  @Support
  public abstract Condition gt(Field<T1> paramField);
  
  @Support
  public abstract Condition gt(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition greaterOrEqual(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition ge(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition ge(T1 paramT1);
  
  @Support
  public abstract Condition ge(Field<T1> paramField);
  
  @Support
  public abstract Condition ge(Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record1<T1>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep1<T1> between(T1 paramT1);
  
  @Support
  public abstract BetweenAndStep1<T1> between(Field<T1> paramField);
  
  @Support
  public abstract BetweenAndStep1<T1> between(Row1<T1> paramRow1);
  
  @Support
  public abstract BetweenAndStep1<T1> between(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition between(Row1<T1> paramRow11, Row1<T1> paramRow12);
  
  @Support
  public abstract Condition between(Record1<T1> paramRecord11, Record1<T1> paramRecord12);
  
  @Support
  public abstract BetweenAndStep1<T1> betweenSymmetric(T1 paramT1);
  
  @Support
  public abstract BetweenAndStep1<T1> betweenSymmetric(Field<T1> paramField);
  
  @Support
  public abstract BetweenAndStep1<T1> betweenSymmetric(Row1<T1> paramRow1);
  
  @Support
  public abstract BetweenAndStep1<T1> betweenSymmetric(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition betweenSymmetric(Row1<T1> paramRow11, Row1<T1> paramRow12);
  
  @Support
  public abstract Condition betweenSymmetric(Record1<T1> paramRecord11, Record1<T1> paramRecord12);
  
  @Support
  public abstract BetweenAndStep1<T1> notBetween(T1 paramT1);
  
  @Support
  public abstract BetweenAndStep1<T1> notBetween(Field<T1> paramField);
  
  @Support
  public abstract BetweenAndStep1<T1> notBetween(Row1<T1> paramRow1);
  
  @Support
  public abstract BetweenAndStep1<T1> notBetween(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition notBetween(Row1<T1> paramRow11, Row1<T1> paramRow12);
  
  @Support
  public abstract Condition notBetween(Record1<T1> paramRecord11, Record1<T1> paramRecord12);
  
  @Support
  public abstract BetweenAndStep1<T1> notBetweenSymmetric(T1 paramT1);
  
  @Support
  public abstract BetweenAndStep1<T1> notBetweenSymmetric(Field<T1> paramField);
  
  @Support
  public abstract BetweenAndStep1<T1> notBetweenSymmetric(Row1<T1> paramRow1);
  
  @Support
  public abstract BetweenAndStep1<T1> notBetweenSymmetric(Record1<T1> paramRecord1);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row1<T1> paramRow11, Row1<T1> paramRow12);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record1<T1> paramRecord11, Record1<T1> paramRecord12);
  
  @Support
  public abstract Condition in(Collection<? extends Row1<T1>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record1<T1>> paramResult);
  
  @Support
  public abstract Condition in(Row1<T1>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record1<T1>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record1<T1>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row1<T1>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record1<T1>> paramResult);
  
  @Support
  public abstract Condition notIn(Row1<T1>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record1<T1>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record1<T1>> paramSelect);
}

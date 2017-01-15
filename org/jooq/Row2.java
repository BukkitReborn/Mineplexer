package org.jooq;

import java.util.Collection;

public abstract interface Row2<T1, T2>
  extends Row
{
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition equal(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition equal(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition eq(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition eq(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition notEqual(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition ne(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition ne(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition lessThan(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition lt(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition lt(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition lessOrEqual(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition le(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition le(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition greaterThan(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition gt(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition gt(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition greaterOrEqual(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition ge(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition ge(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record2<T1, T2>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> between(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> between(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> between(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> between(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition between(Row2<T1, T2> paramRow21, Row2<T1, T2> paramRow22);
  
  @Support
  public abstract Condition between(Record2<T1, T2> paramRecord21, Record2<T1, T2> paramRecord22);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> betweenSymmetric(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> betweenSymmetric(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> betweenSymmetric(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition betweenSymmetric(Row2<T1, T2> paramRow21, Row2<T1, T2> paramRow22);
  
  @Support
  public abstract Condition betweenSymmetric(Record2<T1, T2> paramRecord21, Record2<T1, T2> paramRecord22);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> notBetween(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> notBetween(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> notBetween(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> notBetween(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition notBetween(Row2<T1, T2> paramRow21, Row2<T1, T2> paramRow22);
  
  @Support
  public abstract Condition notBetween(Record2<T1, T2> paramRecord21, Record2<T1, T2> paramRecord22);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> notBetweenSymmetric(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> notBetweenSymmetric(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract BetweenAndStep2<T1, T2> notBetweenSymmetric(Record2<T1, T2> paramRecord2);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row2<T1, T2> paramRow21, Row2<T1, T2> paramRow22);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record2<T1, T2> paramRecord21, Record2<T1, T2> paramRecord22);
  
  @Support
  public abstract Condition in(Collection<? extends Row2<T1, T2>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record2<T1, T2>> paramResult);
  
  @Support
  public abstract Condition in(Row2<T1, T2>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record2<T1, T2>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row2<T1, T2>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record2<T1, T2>> paramResult);
  
  @Support
  public abstract Condition notIn(Row2<T1, T2>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record2<T1, T2>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support
  public abstract Condition overlaps(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition overlaps(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition overlaps(Row2<T1, T2> paramRow2);
}

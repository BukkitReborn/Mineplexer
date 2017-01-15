package org.jooq;

import java.util.Collection;

public abstract interface RowN
  extends Row
{
  @Support
  public abstract Condition compare(Comparator paramComparator, RowN paramRowN);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record paramRecord);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Object... paramVarArgs);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(RowN paramRowN);
  
  @Support
  public abstract Condition equal(Record paramRecord);
  
  @Support
  public abstract Condition equal(Object... paramVarArgs);
  
  @Support
  public abstract Condition equal(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition equal(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(RowN paramRowN);
  
  @Support
  public abstract Condition eq(Record paramRecord);
  
  @Support
  public abstract Condition eq(Object... paramVarArgs);
  
  @Support
  public abstract Condition eq(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition eq(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(RowN paramRowN);
  
  @Support
  public abstract Condition notEqual(Record paramRecord);
  
  @Support
  public abstract Condition notEqual(Object... paramVarArgs);
  
  @Support
  public abstract Condition notEqual(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(RowN paramRowN);
  
  @Support
  public abstract Condition ne(Record paramRecord);
  
  @Support
  public abstract Condition ne(Object... paramVarArgs);
  
  @Support
  public abstract Condition ne(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition ne(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(RowN paramRowN);
  
  @Support
  public abstract Condition lessThan(Record paramRecord);
  
  @Support
  public abstract Condition lessThan(Object... paramVarArgs);
  
  @Support
  public abstract Condition lessThan(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(RowN paramRowN);
  
  @Support
  public abstract Condition lt(Record paramRecord);
  
  @Support
  public abstract Condition lt(Object... paramVarArgs);
  
  @Support
  public abstract Condition lt(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition lt(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(RowN paramRowN);
  
  @Support
  public abstract Condition lessOrEqual(Record paramRecord);
  
  @Support
  public abstract Condition lessOrEqual(Object... paramVarArgs);
  
  @Support
  public abstract Condition lessOrEqual(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(RowN paramRowN);
  
  @Support
  public abstract Condition le(Record paramRecord);
  
  @Support
  public abstract Condition le(Object... paramVarArgs);
  
  @Support
  public abstract Condition le(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition le(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(RowN paramRowN);
  
  @Support
  public abstract Condition greaterThan(Record paramRecord);
  
  @Support
  public abstract Condition greaterThan(Object... paramVarArgs);
  
  @Support
  public abstract Condition greaterThan(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(RowN paramRowN);
  
  @Support
  public abstract Condition gt(Record paramRecord);
  
  @Support
  public abstract Condition gt(Object... paramVarArgs);
  
  @Support
  public abstract Condition gt(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition gt(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(RowN paramRowN);
  
  @Support
  public abstract Condition greaterOrEqual(Record paramRecord);
  
  @Support
  public abstract Condition greaterOrEqual(Object... paramVarArgs);
  
  @Support
  public abstract Condition greaterOrEqual(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(RowN paramRowN);
  
  @Support
  public abstract Condition ge(Record paramRecord);
  
  @Support
  public abstract Condition ge(Object... paramVarArgs);
  
  @Support
  public abstract Condition ge(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition ge(Select<? extends Record> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStepN between(Object... paramVarArgs);
  
  @Support
  public abstract BetweenAndStepN between(Field<?>... paramVarArgs);
  
  @Support
  public abstract BetweenAndStepN between(RowN paramRowN);
  
  @Support
  public abstract BetweenAndStepN between(Record paramRecord);
  
  @Support
  public abstract Condition between(RowN paramRowN1, RowN paramRowN2);
  
  @Support
  public abstract Condition between(Record paramRecord1, Record paramRecord2);
  
  @Support
  public abstract BetweenAndStepN betweenSymmetric(Object... paramVarArgs);
  
  @Support
  public abstract BetweenAndStepN betweenSymmetric(Field<?>... paramVarArgs);
  
  @Support
  public abstract BetweenAndStepN betweenSymmetric(RowN paramRowN);
  
  @Support
  public abstract BetweenAndStepN betweenSymmetric(Record paramRecord);
  
  @Support
  public abstract Condition betweenSymmetric(RowN paramRowN1, RowN paramRowN2);
  
  @Support
  public abstract Condition betweenSymmetric(Record paramRecord1, Record paramRecord2);
  
  @Support
  public abstract BetweenAndStepN notBetween(Object... paramVarArgs);
  
  @Support
  public abstract BetweenAndStepN notBetween(Field<?>... paramVarArgs);
  
  @Support
  public abstract BetweenAndStepN notBetween(RowN paramRowN);
  
  @Support
  public abstract BetweenAndStepN notBetween(Record paramRecord);
  
  @Support
  public abstract Condition notBetween(RowN paramRowN1, RowN paramRowN2);
  
  @Support
  public abstract Condition notBetween(Record paramRecord1, Record paramRecord2);
  
  @Support
  public abstract BetweenAndStepN notBetweenSymmetric(Object... paramVarArgs);
  
  @Support
  public abstract BetweenAndStepN notBetweenSymmetric(Field<?>... paramVarArgs);
  
  @Support
  public abstract BetweenAndStepN notBetweenSymmetric(RowN paramRowN);
  
  @Support
  public abstract BetweenAndStepN notBetweenSymmetric(Record paramRecord);
  
  @Support
  public abstract Condition notBetweenSymmetric(RowN paramRowN1, RowN paramRowN2);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record paramRecord1, Record paramRecord2);
  
  @Support
  public abstract Condition in(Collection<? extends RowN> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record> paramResult);
  
  @Support
  public abstract Condition in(RowN... paramVarArgs);
  
  @Support
  public abstract Condition in(Record... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends RowN> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record> paramResult);
  
  @Support
  public abstract Condition notIn(RowN... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record> paramSelect);
}

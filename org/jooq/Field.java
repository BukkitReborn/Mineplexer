package org.jooq;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public abstract interface Field<T>
  extends GroupField
{
  public abstract String getName();
  
  public abstract String getComment();
  
  public abstract Converter<?, T> getConverter();
  
  public abstract Binding<?, T> getBinding();
  
  public abstract Class<T> getType();
  
  public abstract DataType<T> getDataType();
  
  public abstract DataType<T> getDataType(Configuration paramConfiguration);
  
  @Support
  public abstract Field<T> as(String paramString);
  
  @Support
  public abstract Field<T> as(Field<?> paramField);
  
  public abstract boolean equals(Object paramObject);
  
  @Support
  public abstract <Z> Field<Z> cast(Field<Z> paramField);
  
  @Support
  public abstract <Z> Field<Z> cast(DataType<Z> paramDataType);
  
  @Support
  public abstract <Z> Field<Z> cast(Class<Z> paramClass);
  
  @Support
  public abstract <Z> Field<Z> coerce(Field<Z> paramField);
  
  @Support
  public abstract <Z> Field<Z> coerce(DataType<Z> paramDataType);
  
  @Support
  public abstract <Z> Field<Z> coerce(Class<Z> paramClass);
  
  @Support
  public abstract SortField<T> asc();
  
  @Support
  public abstract SortField<T> desc();
  
  @Support
  public abstract SortField<T> sort(SortOrder paramSortOrder);
  
  @Support
  public abstract SortField<Integer> sortAsc(Collection<T> paramCollection);
  
  @Support
  public abstract SortField<Integer> sortAsc(T... paramVarArgs);
  
  @Support
  public abstract SortField<Integer> sortDesc(Collection<T> paramCollection);
  
  @Support
  public abstract SortField<Integer> sortDesc(T... paramVarArgs);
  
  @Support
  public abstract <Z> SortField<Z> sort(Map<T, Z> paramMap);
  
  @Support
  public abstract Field<T> neg();
  
  @Support
  public abstract Field<T> add(Number paramNumber);
  
  @Support
  public abstract Field<T> add(Field<?> paramField);
  
  @Support
  public abstract Field<T> plus(Number paramNumber);
  
  @Support
  public abstract Field<T> plus(Field<?> paramField);
  
  @Support
  public abstract Field<T> sub(Number paramNumber);
  
  @Support
  public abstract Field<T> sub(Field<?> paramField);
  
  @Support
  public abstract Field<T> subtract(Number paramNumber);
  
  @Support
  public abstract Field<T> subtract(Field<?> paramField);
  
  @Support
  public abstract Field<T> minus(Number paramNumber);
  
  @Support
  public abstract Field<T> minus(Field<?> paramField);
  
  @Support
  public abstract Field<T> mul(Number paramNumber);
  
  @Support
  public abstract Field<T> mul(Field<? extends Number> paramField);
  
  @Support
  public abstract Field<T> multiply(Number paramNumber);
  
  @Support
  public abstract Field<T> multiply(Field<? extends Number> paramField);
  
  @Support
  public abstract Field<T> div(Number paramNumber);
  
  @Support
  public abstract Field<T> div(Field<? extends Number> paramField);
  
  @Support
  public abstract Field<T> divide(Number paramNumber);
  
  @Support
  public abstract Field<T> divide(Field<? extends Number> paramField);
  
  @Support
  public abstract Field<T> mod(Number paramNumber);
  
  @Support
  public abstract Field<T> mod(Field<? extends Number> paramField);
  
  @Support
  public abstract Field<T> modulo(Number paramNumber);
  
  @Support
  public abstract Field<T> modulo(Field<? extends Number> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitNot();
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitAnd(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitAnd(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitNand(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitNand(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitOr(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitOr(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitNor(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitNor(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitXor(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitXor(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitXNor(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> bitXNor(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> shl(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> shl(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> shr(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<T> shr(Field<T> paramField);
  
  @Support
  public abstract Condition isNull();
  
  @Support
  public abstract Condition isNotNull();
  
  @Support
  public abstract Condition isDistinctFrom(T paramT);
  
  @Support
  public abstract Condition isDistinctFrom(Field<T> paramField);
  
  @Support
  public abstract Condition isNotDistinctFrom(T paramT);
  
  @Support
  public abstract Condition isNotDistinctFrom(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition likeRegex(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition likeRegex(Field<String> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition notLikeRegex(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition notLikeRegex(Field<String> paramField);
  
  @Support
  public abstract Condition like(Field<String> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition like(Field<String> paramField, char paramChar);
  
  @Support
  public abstract Condition like(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition like(String paramString, char paramChar);
  
  @Support
  public abstract Condition likeIgnoreCase(Field<String> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition likeIgnoreCase(Field<String> paramField, char paramChar);
  
  @Support
  public abstract Condition likeIgnoreCase(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition likeIgnoreCase(String paramString, char paramChar);
  
  @Support
  public abstract Condition notLike(Field<String> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition notLike(Field<String> paramField, char paramChar);
  
  @Support
  public abstract Condition notLike(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition notLike(String paramString, char paramChar);
  
  @Support
  public abstract Condition notLikeIgnoreCase(Field<String> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition notLikeIgnoreCase(Field<String> paramField, char paramChar);
  
  @Support
  public abstract Condition notLikeIgnoreCase(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition notLikeIgnoreCase(String paramString, char paramChar);
  
  @Support
  public abstract Condition contains(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition contains(Field<T> paramField);
  
  @Support
  public abstract Condition startsWith(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition startsWith(Field<T> paramField);
  
  @Support
  public abstract Condition endsWith(T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Condition endsWith(Field<T> paramField);
  
  @Support
  public abstract Condition in(Collection<?> paramCollection);
  
  public abstract Condition in(Result<? extends Record1<T>> paramResult);
  
  @Support
  public abstract Condition in(T... paramVarArgs);
  
  @Support
  public abstract Condition in(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record1<T>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<?> paramCollection);
  
  public abstract Condition notIn(Result<? extends Record1<T>> paramResult);
  
  @Support
  public abstract Condition notIn(T... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record1<T>> paramSelect);
  
  @Support
  public abstract Condition between(T paramT1, T paramT2);
  
  @Support
  public abstract Condition between(Field<T> paramField1, Field<T> paramField2);
  
  @Support
  public abstract Condition betweenSymmetric(T paramT1, T paramT2);
  
  @Support
  public abstract Condition betweenSymmetric(Field<T> paramField1, Field<T> paramField2);
  
  @Support
  public abstract Condition notBetween(T paramT1, T paramT2);
  
  @Support
  public abstract Condition notBetween(Field<T> paramField1, Field<T> paramField2);
  
  @Support
  public abstract Condition notBetweenSymmetric(T paramT1, T paramT2);
  
  @Support
  public abstract Condition notBetweenSymmetric(Field<T> paramField1, Field<T> paramField2);
  
  @Support
  public abstract BetweenAndStep<T> between(T paramT);
  
  @Support
  public abstract BetweenAndStep<T> between(Field<T> paramField);
  
  @Support
  public abstract BetweenAndStep<T> betweenSymmetric(T paramT);
  
  @Support
  public abstract BetweenAndStep<T> betweenSymmetric(Field<T> paramField);
  
  @Support
  public abstract BetweenAndStep<T> notBetween(T paramT);
  
  @Support
  public abstract BetweenAndStep<T> notBetween(Field<T> paramField);
  
  @Support
  public abstract BetweenAndStep<T> notBetweenSymmetric(T paramT);
  
  @Support
  public abstract BetweenAndStep<T> notBetweenSymmetric(Field<T> paramField);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T paramT);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T> paramField);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record1<T>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(T paramT);
  
  @Support
  public abstract Condition equal(Field<T> paramField);
  
  @Support
  public abstract Condition equal(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(T paramT);
  
  @Support
  public abstract Condition eq(Field<T> paramField);
  
  @Support
  public abstract Condition eq(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(T paramT);
  
  @Support
  public abstract Condition notEqual(Field<T> paramField);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(T paramT);
  
  @Support
  public abstract Condition ne(Field<T> paramField);
  
  @Support
  public abstract Condition ne(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(T paramT);
  
  @Support
  public abstract Condition lessThan(Field<T> paramField);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(T paramT);
  
  @Support
  public abstract Condition lt(Field<T> paramField);
  
  @Support
  public abstract Condition lt(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(T paramT);
  
  @Support
  public abstract Condition lessOrEqual(Field<T> paramField);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(T paramT);
  
  @Support
  public abstract Condition le(Field<T> paramField);
  
  @Support
  public abstract Condition le(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(T paramT);
  
  @Support
  public abstract Condition greaterThan(Field<T> paramField);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(T paramT);
  
  @Support
  public abstract Condition gt(Field<T> paramField);
  
  @Support
  public abstract Condition gt(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(T paramT);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T> paramField);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(T paramT);
  
  @Support
  public abstract Condition ge(Field<T> paramField);
  
  @Support
  public abstract Condition ge(Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record1<T>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition isTrue();
  
  @Support
  public abstract Condition isFalse();
  
  @Support
  public abstract Condition equalIgnoreCase(String paramString);
  
  @Support
  public abstract Condition equalIgnoreCase(Field<String> paramField);
  
  @Support
  public abstract Condition notEqualIgnoreCase(String paramString);
  
  @Support
  public abstract Condition notEqualIgnoreCase(Field<String> paramField);
  
  @Support
  public abstract Field<Integer> sign();
  
  @Support
  public abstract Field<T> abs();
  
  @Support
  public abstract Field<T> round();
  
  @Support
  public abstract Field<T> round(int paramInt);
  
  @Support
  public abstract Field<T> floor();
  
  @Support
  public abstract Field<T> ceil();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> sqrt();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> exp();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> ln();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> log(int paramInt);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> pow(Number paramNumber);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> power(Number paramNumber);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> acos();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> asin();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> atan();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> atan2(Number paramNumber);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> atan2(Field<? extends Number> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> cos();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> sin();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> tan();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> cot();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> sinh();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> cosh();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> tanh();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> coth();
  
  @Support
  public abstract Field<BigDecimal> deg();
  
  @Support
  public abstract Field<BigDecimal> rad();
  
  @Support
  public abstract Field<Integer> count();
  
  @Support
  public abstract Field<Integer> countDistinct();
  
  @Support
  public abstract Field<T> max();
  
  @Support
  public abstract Field<T> min();
  
  @Support
  public abstract Field<BigDecimal> sum();
  
  @Support
  public abstract Field<BigDecimal> avg();
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract Field<BigDecimal> median();
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> stddevPop();
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> stddevSamp();
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> varPop();
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<BigDecimal> varSamp();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<Integer> countOver();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<T> maxOver();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<T> minOver();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<BigDecimal> sumOver();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<BigDecimal> avgOver();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> firstValue();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lastValue();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lead();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lead(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lead(int paramInt, T paramT);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lead(int paramInt, Field<T> paramField);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lag();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lag(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lag(int paramInt, T paramT);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowIgnoreNullsStep<T> lag(int paramInt, Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<BigDecimal> stddevPopOver();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<BigDecimal> stddevSampOver();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<BigDecimal> varPopOver();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<BigDecimal> varSampOver();
  
  @Support
  public abstract Field<String> upper();
  
  @Support
  public abstract Field<String> lower();
  
  @Support
  public abstract Field<String> trim();
  
  @Support
  public abstract Field<String> rtrim();
  
  @Support
  public abstract Field<String> ltrim();
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> rpad(Field<? extends Number> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> rpad(int paramInt);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> rpad(Field<? extends Number> paramField, Field<String> paramField1);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> rpad(int paramInt, char paramChar);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> lpad(Field<? extends Number> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> lpad(int paramInt);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> lpad(Field<? extends Number> paramField, Field<String> paramField1);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> lpad(int paramInt, char paramChar);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> repeat(Number paramNumber);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<String> repeat(Field<? extends Number> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<String> replace(Field<String> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<String> replace(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<String> replace(Field<String> paramField1, Field<String> paramField2);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Field<String> replace(String paramString1, String paramString2);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<Integer> position(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<Integer> position(Field<String> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Field<Integer> ascii();
  
  @Support
  public abstract Field<String> concat(Field<?>... paramVarArgs);
  
  @Support
  public abstract Field<String> concat(String... paramVarArgs);
  
  @Support
  public abstract Field<String> substring(int paramInt);
  
  @Support
  public abstract Field<String> substring(Field<? extends Number> paramField);
  
  @Support
  public abstract Field<String> substring(int paramInt1, int paramInt2);
  
  @Support
  public abstract Field<String> substring(Field<? extends Number> paramField1, Field<? extends Number> paramField2);
  
  @Support
  public abstract Field<Integer> length();
  
  @Support
  public abstract Field<Integer> charLength();
  
  @Support
  public abstract Field<Integer> bitLength();
  
  @Support
  public abstract Field<Integer> octetLength();
  
  @Support
  public abstract Field<Integer> extract(DatePart paramDatePart);
  
  @Support
  public abstract Field<T> greatest(T... paramVarArgs);
  
  @Support
  public abstract Field<T> greatest(Field<?>... paramVarArgs);
  
  @Support
  public abstract Field<T> least(T... paramVarArgs);
  
  @Support
  public abstract Field<T> least(Field<?>... paramVarArgs);
  
  @Support
  public abstract Field<T> nvl(T paramT);
  
  @Support
  public abstract Field<T> nvl(Field<T> paramField);
  
  @Support
  public abstract <Z> Field<Z> nvl2(Z paramZ1, Z paramZ2);
  
  @Support
  public abstract <Z> Field<Z> nvl2(Field<Z> paramField1, Field<Z> paramField2);
  
  @Support
  public abstract Field<T> nullif(T paramT);
  
  @Support
  public abstract Field<T> nullif(Field<T> paramField);
  
  @Support
  public abstract <Z> Field<Z> decode(T paramT, Z paramZ);
  
  @Support
  public abstract <Z> Field<Z> decode(T paramT, Z paramZ, Object... paramVarArgs);
  
  @Support
  public abstract <Z> Field<Z> decode(Field<T> paramField, Field<Z> paramField1);
  
  @Support
  public abstract <Z> Field<Z> decode(Field<T> paramField, Field<Z> paramField1, Field<?>... paramVarArgs);
  
  @Support
  public abstract Field<T> coalesce(T paramT, T... paramVarArgs);
  
  @Support
  public abstract Field<T> coalesce(Field<T> paramField, Field<?>... paramVarArgs);
}

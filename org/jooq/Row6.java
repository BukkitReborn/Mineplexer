package org.jooq;

import java.util.Collection;

public abstract interface Row6<T1, T2, T3, T4, T5, T6>
  extends Row
{
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  public abstract Field<T4> field4();
  
  public abstract Field<T5> field5();
  
  public abstract Field<T6> field6();
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support
  public abstract Condition compare(Comparator paramComparator, QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition equal(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition equal(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition equal(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition equal(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition equal(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition equal(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition eq(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition eq(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition eq(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition eq(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition eq(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition eq(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition notEqual(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition notEqual(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition notEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition notEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition notEqual(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition notEqual(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ne(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition ne(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition ne(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition ne(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition ne(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ne(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessThan(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition lessThan(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition lessThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition lessThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition lessThan(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessThan(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lt(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition lt(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition lt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition lt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition lt(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lt(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition lessOrEqual(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition lessOrEqual(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition lessOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition lessOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition lessOrEqual(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition lessOrEqual(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition le(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition le(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition le(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition le(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition le(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition le(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterThan(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition greaterThan(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition greaterThan(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition greaterThan(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition greaterThan(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterThan(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition gt(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition gt(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition gt(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition gt(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition gt(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition gt(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition greaterOrEqual(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition greaterOrEqual(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition greaterOrEqual(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition greaterOrEqual(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition greaterOrEqual(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition greaterOrEqual(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract Condition ge(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition ge(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition ge(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition ge(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition ge(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Condition ge(QuantifiedSelect<? extends Record6<T1, T2, T3, T4, T5, T6>> paramQuantifiedSelect);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> between(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition between(Row6<T1, T2, T3, T4, T5, T6> paramRow61, Row6<T1, T2, T3, T4, T5, T6> paramRow62);
  
  @Support
  public abstract Condition between(Record6<T1, T2, T3, T4, T5, T6> paramRecord61, Record6<T1, T2, T3, T4, T5, T6> paramRecord62);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> betweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition betweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> paramRow61, Row6<T1, T2, T3, T4, T5, T6> paramRow62);
  
  @Support
  public abstract Condition betweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> paramRecord61, Record6<T1, T2, T3, T4, T5, T6> paramRecord62);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetween(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition notBetween(Row6<T1, T2, T3, T4, T5, T6> paramRow61, Row6<T1, T2, T3, T4, T5, T6> paramRow62);
  
  @Support
  public abstract Condition notBetween(Record6<T1, T2, T3, T4, T5, T6> paramRecord61, Record6<T1, T2, T3, T4, T5, T6> paramRecord62);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract BetweenAndStep6<T1, T2, T3, T4, T5, T6> notBetweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
  
  @Support
  public abstract Condition notBetweenSymmetric(Row6<T1, T2, T3, T4, T5, T6> paramRow61, Row6<T1, T2, T3, T4, T5, T6> paramRow62);
  
  @Support
  public abstract Condition notBetweenSymmetric(Record6<T1, T2, T3, T4, T5, T6> paramRecord61, Record6<T1, T2, T3, T4, T5, T6> paramRecord62);
  
  @Support
  public abstract Condition in(Collection<? extends Row6<T1, T2, T3, T4, T5, T6>> paramCollection);
  
  @Support
  public abstract Condition in(Result<? extends Record6<T1, T2, T3, T4, T5, T6>> paramResult);
  
  @Support
  public abstract Condition in(Row6<T1, T2, T3, T4, T5, T6>... paramVarArgs);
  
  @Support
  public abstract Condition in(Record6<T1, T2, T3, T4, T5, T6>... paramVarArgs);
  
  @Support
  public abstract Condition in(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support
  public abstract Condition notIn(Collection<? extends Row6<T1, T2, T3, T4, T5, T6>> paramCollection);
  
  @Support
  public abstract Condition notIn(Result<? extends Record6<T1, T2, T3, T4, T5, T6>> paramResult);
  
  @Support
  public abstract Condition notIn(Row6<T1, T2, T3, T4, T5, T6>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Record6<T1, T2, T3, T4, T5, T6>... paramVarArgs);
  
  @Support
  public abstract Condition notIn(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
}

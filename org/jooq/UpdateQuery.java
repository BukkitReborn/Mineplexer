package org.jooq;

import java.util.Collection;

public abstract interface UpdateQuery<R extends Record>
  extends StoreQuery<R>, ConditionProvider, Update<R>
{
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1> void addValues(Row1<T1> paramRow11, Row1<T1> paramRow12);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2> void addValues(Row2<T1, T2> paramRow21, Row2<T1, T2> paramRow22);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3> void addValues(Row3<T1, T2, T3> paramRow31, Row3<T1, T2, T3> paramRow32);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> paramRow41, Row4<T1, T2, T3, T4> paramRow42);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> paramRow51, Row5<T1, T2, T3, T4, T5> paramRow52);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> paramRow61, Row6<T1, T2, T3, T4, T5, T6> paramRow62);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow71, Row7<T1, T2, T3, T4, T5, T6, T7> paramRow72);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow81, Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow82);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow91, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow92);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow101, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow102);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> paramRow111, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> paramRow112);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> paramRow121, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> paramRow122);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow131, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow132);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> paramRow141, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> paramRow142);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> paramRow151, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> paramRow152);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> paramRow161, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> paramRow162);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> paramRow171, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> paramRow172);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> paramRow181, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> paramRow182);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> paramRow191, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> paramRow192);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> paramRow201, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> paramRow202);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> paramRow211, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> paramRow212);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> paramRow221, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> paramRow222);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1> void addValues(Row1<T1> paramRow1, Select<? extends Record1<T1>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2> void addValues(Row2<T1, T2> paramRow2, Select<? extends Record2<T1, T2>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3> void addValues(Row3<T1, T2, T3> paramRow3, Select<? extends Record3<T1, T2, T3>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> paramRow4, Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> paramRow5, Select<? extends Record5<T1, T2, T3, T4, T5>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> paramRow6, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> paramRow7, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> paramRow9, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> paramRow10, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> paramRow11, Select<? extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> paramRow12, Select<? extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> paramRow13, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> paramRow14, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> paramRow15, Select<? extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> paramRow16, Select<? extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> paramRow17, Select<? extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> paramRow18, Select<? extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> paramRow19, Select<? extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> paramRow20, Select<? extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> paramRow21, Select<? extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> paramSelect);
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> paramRow22, Select<? extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> paramSelect);
  
  @Support({SQLDialect.POSTGRES})
  public abstract void addFrom(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.POSTGRES})
  public abstract void addFrom(TableLike<?>... paramVarArgs);
  
  @Support({SQLDialect.POSTGRES})
  public abstract void addFrom(Collection<? extends TableLike<?>> paramCollection);
  
  @Support
  public abstract void addConditions(Condition... paramVarArgs);
  
  @Support
  public abstract void addConditions(Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract void addConditions(Operator paramOperator, Condition... paramVarArgs);
  
  @Support
  public abstract void addConditions(Operator paramOperator, Collection<? extends Condition> paramCollection);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract void setReturning();
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract void setReturning(Identity<R, ? extends Number> paramIdentity);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract void setReturning(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract void setReturning(Collection<? extends Field<?>> paramCollection);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract R getReturnedRecord();
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract Result<R> getReturnedRecords();
}

package org.jooq;

import java.util.Collection;

public abstract interface SelectOrderByStep<R extends Record>
  extends SelectLimitStep<R>
{
  @Support
  public abstract <T1> SelectSeekStep1<R, T1> orderBy(Field<T1> paramField);
  
  @Support
  public abstract <T1, T2> SelectSeekStep2<R, T1, T2> orderBy(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract <T1, T2, T3> SelectSeekStep3<R, T1, T2, T3> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract <T1, T2, T3, T4> SelectSeekStep4<R, T1, T2, T3, T4> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract <T1, T2, T3, T4, T5> SelectSeekStep5<R, T1, T2, T3, T4, T5> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6> SelectSeekStep6<R, T1, T2, T3, T4, T5, T6> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7> SelectSeekStep7<R, T1, T2, T3, T4, T5, T6, T7> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8> SelectSeekStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSeekStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSeekStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSeekStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSeekStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSeekStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSeekStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSeekStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSeekStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSeekStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSeekStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSeekStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSeekStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSeekStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19, Field<T21> paramField20);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSeekStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> orderBy(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19, Field<T21> paramField20, Field<T22> paramField21);
  
  @Support
  public abstract SelectSeekStepN<R> orderBy(Field<?>... paramVarArgs);
  
  @Support
  public abstract <T1> SelectSeekStep1<R, T1> orderBy(SortField<T1> paramSortField);
  
  @Support
  public abstract <T1, T2> SelectSeekStep2<R, T1, T2> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1);
  
  @Support
  public abstract <T1, T2, T3> SelectSeekStep3<R, T1, T2, T3> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2);
  
  @Support
  public abstract <T1, T2, T3, T4> SelectSeekStep4<R, T1, T2, T3, T4> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3);
  
  @Support
  public abstract <T1, T2, T3, T4, T5> SelectSeekStep5<R, T1, T2, T3, T4, T5> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6> SelectSeekStep6<R, T1, T2, T3, T4, T5, T6> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7> SelectSeekStep7<R, T1, T2, T3, T4, T5, T6, T7> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8> SelectSeekStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSeekStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSeekStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSeekStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSeekStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSeekStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSeekStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSeekStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13, SortField<T15> paramSortField14);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSeekStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13, SortField<T15> paramSortField14, SortField<T16> paramSortField15);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSeekStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13, SortField<T15> paramSortField14, SortField<T16> paramSortField15, SortField<T17> paramSortField16);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSeekStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13, SortField<T15> paramSortField14, SortField<T16> paramSortField15, SortField<T17> paramSortField16, SortField<T18> paramSortField17);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSeekStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13, SortField<T15> paramSortField14, SortField<T16> paramSortField15, SortField<T17> paramSortField16, SortField<T18> paramSortField17, SortField<T19> paramSortField18);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSeekStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13, SortField<T15> paramSortField14, SortField<T16> paramSortField15, SortField<T17> paramSortField16, SortField<T18> paramSortField17, SortField<T19> paramSortField18, SortField<T20> paramSortField19);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSeekStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13, SortField<T15> paramSortField14, SortField<T16> paramSortField15, SortField<T17> paramSortField16, SortField<T18> paramSortField17, SortField<T19> paramSortField18, SortField<T20> paramSortField19, SortField<T21> paramSortField20);
  
  @Support
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSeekStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> orderBy(SortField<T1> paramSortField, SortField<T2> paramSortField1, SortField<T3> paramSortField2, SortField<T4> paramSortField3, SortField<T5> paramSortField4, SortField<T6> paramSortField5, SortField<T7> paramSortField6, SortField<T8> paramSortField7, SortField<T9> paramSortField8, SortField<T10> paramSortField9, SortField<T11> paramSortField10, SortField<T12> paramSortField11, SortField<T13> paramSortField12, SortField<T14> paramSortField13, SortField<T15> paramSortField14, SortField<T16> paramSortField15, SortField<T17> paramSortField16, SortField<T18> paramSortField17, SortField<T19> paramSortField18, SortField<T20> paramSortField19, SortField<T21> paramSortField20, SortField<T22> paramSortField21);
  
  @Support
  public abstract SelectSeekStepN<R> orderBy(SortField<?>... paramVarArgs);
  
  @Support
  public abstract SelectSeekStepN<R> orderBy(Collection<? extends SortField<?>> paramCollection);
  
  @Support
  public abstract SelectLimitStep<R> orderBy(int... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectLimitStep<R> orderSiblingsBy(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectLimitStep<R> orderSiblingsBy(SortField<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectLimitStep<R> orderSiblingsBy(Collection<? extends SortField<?>> paramCollection);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectLimitStep<R> orderSiblingsBy(int... paramVarArgs);
}

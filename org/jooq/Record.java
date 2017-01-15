package org.jooq;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.MappingException;

public abstract interface Record
  extends Attachable, Comparable<Record>
{
  public abstract Row fieldsRow();
  
  public abstract <T> Field<T> field(Field<T> paramField);
  
  public abstract Field<?> field(String paramString);
  
  public abstract Field<?> field(int paramInt);
  
  public abstract Field<?>[] fields();
  
  public abstract Field<?>[] fields(Field<?>... paramVarArgs);
  
  public abstract Field<?>[] fields(String... paramVarArgs);
  
  public abstract Field<?>[] fields(int... paramVarArgs);
  
  public abstract Row valuesRow();
  
  public abstract <T> T getValue(Field<T> paramField)
    throws IllegalArgumentException;
  
  @Deprecated
  public abstract <T> T getValue(Field<T> paramField, T paramT)
    throws IllegalArgumentException;
  
  public abstract <T> T getValue(Field<?> paramField, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  @Deprecated
  public abstract <T> T getValue(Field<?> paramField, Class<? extends T> paramClass, T paramT)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <T, U> U getValue(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  @Deprecated
  public abstract <T, U> U getValue(Field<T> paramField, Converter<? super T, U> paramConverter, U paramU)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract Object getValue(String paramString)
    throws IllegalArgumentException;
  
  @Deprecated
  public abstract Object getValue(String paramString, Object paramObject)
    throws IllegalArgumentException;
  
  public abstract <T> T getValue(String paramString, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  @Deprecated
  public abstract <T> T getValue(String paramString, Class<? extends T> paramClass, T paramT)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <U> U getValue(String paramString, Converter<?, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  @Deprecated
  public abstract <U> U getValue(String paramString, Converter<?, U> paramConverter, U paramU)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract Object getValue(int paramInt)
    throws IllegalArgumentException;
  
  @Deprecated
  public abstract Object getValue(int paramInt, Object paramObject)
    throws IllegalArgumentException;
  
  public abstract <T> T getValue(int paramInt, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  @Deprecated
  public abstract <T> T getValue(int paramInt, Class<? extends T> paramClass, T paramT)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <U> U getValue(int paramInt, Converter<?, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  @Deprecated
  public abstract <U> U getValue(int paramInt, Converter<?, U> paramConverter, U paramU)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <T> void setValue(Field<T> paramField, T paramT);
  
  public abstract <T, U> void setValue(Field<T> paramField, U paramU, Converter<T, ? super U> paramConverter);
  
  public abstract int size();
  
  public abstract Record original();
  
  public abstract <T> T original(Field<T> paramField);
  
  public abstract Object original(int paramInt);
  
  public abstract Object original(String paramString);
  
  public abstract boolean changed();
  
  public abstract boolean changed(Field<?> paramField);
  
  public abstract boolean changed(int paramInt);
  
  public abstract boolean changed(String paramString);
  
  public abstract void changed(boolean paramBoolean);
  
  public abstract void changed(Field<?> paramField, boolean paramBoolean);
  
  public abstract void changed(int paramInt, boolean paramBoolean);
  
  public abstract void changed(String paramString, boolean paramBoolean);
  
  public abstract void reset();
  
  public abstract void reset(Field<?> paramField);
  
  public abstract void reset(int paramInt);
  
  public abstract void reset(String paramString);
  
  public abstract Object[] intoArray();
  
  public abstract List<Object> intoList();
  
  public abstract Map<String, Object> intoMap();
  
  public abstract Record into(Field<?>... paramVarArgs);
  
  public abstract <T1> Record1<T1> into(Field<T1> paramField);
  
  public abstract <T1, T2> Record2<T1, T2> into(Field<T1> paramField, Field<T2> paramField1);
  
  public abstract <T1, T2, T3> Record3<T1, T2, T3> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  public abstract <T1, T2, T3, T4> Record4<T1, T2, T3, T4> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  public abstract <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  public abstract <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19, Field<T21> paramField20);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19, Field<T21> paramField20, Field<T22> paramField21);
  
  public abstract <E> E into(Class<? extends E> paramClass)
    throws MappingException;
  
  public abstract <E> E into(E paramE)
    throws MappingException;
  
  public abstract <R extends Record> R into(Table<R> paramTable);
  
  public abstract ResultSet intoResultSet();
  
  public abstract <E> E map(RecordMapper<Record, E> paramRecordMapper);
  
  public abstract void from(Object paramObject)
    throws MappingException;
  
  public abstract void from(Object paramObject, Field<?>... paramVarArgs)
    throws MappingException;
  
  public abstract void from(Object paramObject, String... paramVarArgs)
    throws MappingException;
  
  public abstract void from(Object paramObject, int... paramVarArgs)
    throws MappingException;
  
  public abstract void fromMap(Map<String, ?> paramMap);
  
  public abstract void fromMap(Map<String, ?> paramMap, Field<?>... paramVarArgs);
  
  public abstract void fromMap(Map<String, ?> paramMap, String... paramVarArgs);
  
  public abstract void fromMap(Map<String, ?> paramMap, int... paramVarArgs);
  
  public abstract void fromArray(Object... paramVarArgs);
  
  public abstract void fromArray(Object[] paramArrayOfObject, Field<?>... paramVarArgs);
  
  public abstract void fromArray(Object[] paramArrayOfObject, String... paramVarArgs);
  
  public abstract void fromArray(Object[] paramArrayOfObject, int... paramVarArgs);
  
  public abstract int hashCode();
  
  public abstract boolean equals(Object paramObject);
  
  public abstract int compareTo(Record paramRecord);
}

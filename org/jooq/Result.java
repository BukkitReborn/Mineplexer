package org.jooq;

import java.io.OutputStream;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.IOException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public abstract interface Result<R extends Record>
  extends List<R>, Attachable
{
  public abstract RecordType<R> recordType();
  
  public abstract Row fieldsRow();
  
  public abstract <T> Field<T> field(Field<T> paramField);
  
  public abstract Field<?> field(String paramString);
  
  public abstract Field<?> field(int paramInt);
  
  public abstract Field<?>[] fields();
  
  public abstract <T> T getValue(int paramInt, Field<T> paramField)
    throws IndexOutOfBoundsException, IllegalArgumentException;
  
  @Deprecated
  public abstract <T> T getValue(int paramInt, Field<T> paramField, T paramT)
    throws IndexOutOfBoundsException, IllegalArgumentException;
  
  public abstract Object getValue(int paramInt1, int paramInt2)
    throws IndexOutOfBoundsException, IllegalArgumentException;
  
  @Deprecated
  public abstract Object getValue(int paramInt1, int paramInt2, Object paramObject)
    throws IndexOutOfBoundsException, IllegalArgumentException;
  
  public abstract Object getValue(int paramInt, String paramString)
    throws IndexOutOfBoundsException, IllegalArgumentException;
  
  @Deprecated
  public abstract Object getValue(int paramInt, String paramString, Object paramObject)
    throws IndexOutOfBoundsException, IllegalArgumentException;
  
  public abstract <T> List<T> getValues(Field<T> paramField)
    throws IllegalArgumentException;
  
  public abstract <T> List<T> getValues(Field<?> paramField, Class<? extends T> paramClass)
    throws IllegalArgumentException;
  
  public abstract <T, U> List<U> getValues(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws IllegalArgumentException;
  
  public abstract List<?> getValues(int paramInt)
    throws IllegalArgumentException;
  
  public abstract <T> List<T> getValues(int paramInt, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <U> List<U> getValues(int paramInt, Converter<?, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract List<?> getValues(String paramString)
    throws IllegalArgumentException;
  
  public abstract <T> List<T> getValues(String paramString, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <U> List<U> getValues(String paramString, Converter<?, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract boolean isEmpty();
  
  public abstract boolean isNotEmpty();
  
  public abstract String format();
  
  public abstract String format(int paramInt);
  
  public abstract String formatHTML();
  
  public abstract String formatCSV();
  
  public abstract String formatCSV(char paramChar);
  
  public abstract String formatCSV(char paramChar, String paramString);
  
  public abstract String formatJSON();
  
  public abstract String formatXML();
  
  public abstract String formatInsert();
  
  public abstract String formatInsert(Table<?> paramTable, Field<?>... paramVarArgs);
  
  public abstract void format(OutputStream paramOutputStream)
    throws IOException;
  
  public abstract void format(OutputStream paramOutputStream, int paramInt)
    throws IOException;
  
  public abstract void formatHTML(OutputStream paramOutputStream)
    throws IOException;
  
  public abstract void formatCSV(OutputStream paramOutputStream)
    throws IOException;
  
  public abstract void formatCSV(OutputStream paramOutputStream, char paramChar)
    throws IOException;
  
  public abstract void formatCSV(OutputStream paramOutputStream, char paramChar, String paramString)
    throws IOException;
  
  public abstract void formatJSON(OutputStream paramOutputStream)
    throws IOException;
  
  public abstract void formatXML(OutputStream paramOutputStream)
    throws IOException;
  
  public abstract void formatInsert(OutputStream paramOutputStream)
    throws IOException;
  
  public abstract void formatInsert(OutputStream paramOutputStream, Table<?> paramTable, Field<?>... paramVarArgs)
    throws IOException;
  
  public abstract void format(Writer paramWriter)
    throws IOException;
  
  public abstract void format(Writer paramWriter, int paramInt)
    throws IOException;
  
  public abstract void formatHTML(Writer paramWriter)
    throws IOException;
  
  public abstract void formatCSV(Writer paramWriter)
    throws IOException;
  
  public abstract void formatCSV(Writer paramWriter, char paramChar)
    throws IOException;
  
  public abstract void formatCSV(Writer paramWriter, char paramChar, String paramString)
    throws IOException;
  
  public abstract void formatJSON(Writer paramWriter)
    throws IOException;
  
  public abstract void formatXML(Writer paramWriter)
    throws IOException;
  
  public abstract void formatInsert(Writer paramWriter)
    throws IOException;
  
  public abstract void formatInsert(Writer paramWriter, Table<?> paramTable, Field<?>... paramVarArgs)
    throws IOException;
  
  public abstract Document intoXML();
  
  public abstract <H extends ContentHandler> H intoXML(H paramH)
    throws SAXException;
  
  public abstract List<Map<String, Object>> intoMaps();
  
  public abstract <K> Map<K, R> intoMap(Field<K> paramField)
    throws IllegalArgumentException, InvalidResultException;
  
  public abstract <K, V> Map<K, V> intoMap(Field<K> paramField, Field<V> paramField1)
    throws IllegalArgumentException, InvalidResultException;
  
  public abstract <K, E> Map<K, E> intoMap(Field<K> paramField, Class<? extends E> paramClass)
    throws IllegalArgumentException, InvalidResultException, MappingException;
  
  public abstract <K, E> Map<K, E> intoMap(Field<K> paramField, RecordMapper<? super R, E> paramRecordMapper)
    throws IllegalArgumentException, InvalidResultException, MappingException;
  
  public abstract Map<Record, R> intoMap(Field<?>[] paramArrayOfField)
    throws IllegalArgumentException, InvalidResultException;
  
  public abstract <E> Map<List<?>, E> intoMap(Field<?>[] paramArrayOfField, Class<? extends E> paramClass)
    throws IllegalArgumentException, InvalidResultException, MappingException;
  
  public abstract <E> Map<List<?>, E> intoMap(Field<?>[] paramArrayOfField, RecordMapper<? super R, E> paramRecordMapper)
    throws IllegalArgumentException, InvalidResultException, MappingException;
  
  public abstract <S extends Record> Map<S, R> intoMap(Table<S> paramTable)
    throws IllegalArgumentException, InvalidResultException;
  
  public abstract <E, S extends Record> Map<S, E> intoMap(Table<S> paramTable, Class<? extends E> paramClass)
    throws IllegalArgumentException, InvalidResultException, MappingException;
  
  public abstract <E, S extends Record> Map<S, E> intoMap(Table<S> paramTable, RecordMapper<? super R, E> paramRecordMapper)
    throws IllegalArgumentException, InvalidResultException, MappingException;
  
  public abstract <K> Map<K, Result<R>> intoGroups(Field<K> paramField)
    throws IllegalArgumentException;
  
  public abstract <K, V> Map<K, List<V>> intoGroups(Field<K> paramField, Field<V> paramField1)
    throws IllegalArgumentException;
  
  public abstract <K, E> Map<K, List<E>> intoGroups(Field<K> paramField, Class<? extends E> paramClass)
    throws IllegalArgumentException, MappingException;
  
  public abstract <K, E> Map<K, List<E>> intoGroups(Field<K> paramField, RecordMapper<? super R, E> paramRecordMapper)
    throws IllegalArgumentException, MappingException;
  
  public abstract Map<Record, Result<R>> intoGroups(Field<?>[] paramArrayOfField)
    throws IllegalArgumentException;
  
  public abstract <E> Map<Record, List<E>> intoGroups(Field<?>[] paramArrayOfField, Class<? extends E> paramClass)
    throws IllegalArgumentException, MappingException;
  
  public abstract <E> Map<Record, List<E>> intoGroups(Field<?>[] paramArrayOfField, RecordMapper<? super R, E> paramRecordMapper)
    throws IllegalArgumentException, MappingException;
  
  public abstract <S extends Record> Map<S, Result<R>> intoGroups(Table<S> paramTable)
    throws IllegalArgumentException;
  
  public abstract <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> paramTable, Class<? extends E> paramClass)
    throws IllegalArgumentException, MappingException;
  
  public abstract <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> paramTable, RecordMapper<? super R, E> paramRecordMapper)
    throws IllegalArgumentException, MappingException;
  
  public abstract Object[][] intoArray();
  
  public abstract Object[] intoArray(int paramInt)
    throws IllegalArgumentException;
  
  public abstract <T> T[] intoArray(int paramInt, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <U> U[] intoArray(int paramInt, Converter<?, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract Object[] intoArray(String paramString)
    throws IllegalArgumentException;
  
  public abstract <T> T[] intoArray(String paramString, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <U> U[] intoArray(String paramString, Converter<?, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <T> T[] intoArray(Field<T> paramField)
    throws IllegalArgumentException;
  
  public abstract <T> T[] intoArray(Field<?> paramField, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <T, U> U[] intoArray(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract Set<?> intoSet(int paramInt)
    throws IllegalArgumentException;
  
  public abstract <T> Set<T> intoSet(int paramInt, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <U> Set<U> intoSet(int paramInt, Converter<?, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract Set<?> intoSet(String paramString)
    throws IllegalArgumentException;
  
  public abstract <T> Set<T> intoSet(String paramString, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <U> Set<U> intoSet(String paramString, Converter<?, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <T> Set<T> intoSet(Field<T> paramField)
    throws IllegalArgumentException;
  
  public abstract <T> Set<T> intoSet(Field<?> paramField, Class<? extends T> paramClass)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract <T, U> Set<U> intoSet(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract Result<Record> into(Field<?>... paramVarArgs);
  
  public abstract <T1> Result<Record1<T1>> into(Field<T1> paramField);
  
  public abstract <T1, T2> Result<Record2<T1, T2>> into(Field<T1> paramField, Field<T2> paramField1);
  
  public abstract <T1, T2, T3> Result<Record3<T1, T2, T3>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  public abstract <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  public abstract <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  public abstract <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19, Field<T21> paramField20);
  
  public abstract <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> into(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11, Field<T13> paramField12, Field<T14> paramField13, Field<T15> paramField14, Field<T16> paramField15, Field<T17> paramField16, Field<T18> paramField17, Field<T19> paramField18, Field<T20> paramField19, Field<T21> paramField20, Field<T22> paramField21);
  
  public abstract <E> List<E> into(Class<? extends E> paramClass)
    throws MappingException;
  
  public abstract <Z extends Record> Result<Z> into(Table<Z> paramTable)
    throws MappingException;
  
  public abstract <H extends RecordHandler<? super R>> H into(H paramH);
  
  public abstract ResultSet intoResultSet();
  
  public abstract <E> List<E> map(RecordMapper<? super R, E> paramRecordMapper);
  
  public abstract <T extends Comparable<? super T>> Result<R> sortAsc(Field<T> paramField)
    throws IllegalArgumentException;
  
  public abstract <T extends Comparable<? super T>> Result<R> sortDesc(Field<T> paramField)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortAsc(int paramInt)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortDesc(int paramInt)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortAsc(String paramString)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortDesc(String paramString)
    throws IllegalArgumentException;
  
  public abstract <T> Result<R> sortAsc(Field<T> paramField, Comparator<? super T> paramComparator)
    throws IllegalArgumentException;
  
  public abstract <T> Result<R> sortDesc(Field<T> paramField, Comparator<? super T> paramComparator)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortAsc(int paramInt, Comparator<?> paramComparator)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortDesc(int paramInt, Comparator<?> paramComparator)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortAsc(String paramString, Comparator<?> paramComparator)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortDesc(String paramString, Comparator<?> paramComparator)
    throws IllegalArgumentException;
  
  public abstract Result<R> sortAsc(Comparator<? super R> paramComparator);
  
  public abstract Result<R> sortDesc(Comparator<? super R> paramComparator);
  
  public abstract Result<R> intern(Field<?>... paramVarArgs);
  
  public abstract Result<R> intern(int... paramVarArgs);
  
  public abstract Result<R> intern(String... paramVarArgs);
  
  public abstract void attach(Configuration paramConfiguration);
  
  public abstract void detach();
}

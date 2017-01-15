package org.jooq.impl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Attachable;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;

abstract class AbstractRecord
  extends AbstractStore
  implements Record
{
  private static final long serialVersionUID = -6052512608911220404L;
  final RowImpl fields;
  final Object[] values;
  final Object[] originals;
  final BitSet changed;
  boolean fetched;
  
  AbstractRecord(Collection<? extends org.jooq.Field<?>> fields)
  {
    this(new RowImpl(fields));
  }
  
  AbstractRecord(org.jooq.Field<?>... fields)
  {
    this(new RowImpl(fields));
  }
  
  AbstractRecord(RowImpl fields)
  {
    int size = fields.size();
    
    this.fields = fields;
    this.values = new Object[size];
    this.originals = new Object[size];
    this.changed = new BitSet(size);
  }
  
  final List<Attachable> getAttachables()
  {
    List<Attachable> result = null;
    
    int size = size();
    for (int i = 0; i < size; i++) {
      if ((this.values[i] instanceof Attachable))
      {
        if (result == null) {
          result = new ArrayList();
        }
        result.add((Attachable)this.values[i]);
      }
    }
    return result == null ? Collections.emptyList() : result;
  }
  
  public final <T> org.jooq.Field<T> field(org.jooq.Field<T> field)
  {
    return fieldsRow().field(field);
  }
  
  public final org.jooq.Field<?> field(String name)
  {
    return fieldsRow().field(name);
  }
  
  public final org.jooq.Field<?> field(int index)
  {
    return (index >= 0) && (index < this.fields.size()) ? this.fields.field(index) : null;
  }
  
  public final org.jooq.Field<?>[] fields()
  {
    return this.fields.fields();
  }
  
  public final org.jooq.Field<?>[] fields(org.jooq.Field<?>... f)
  {
    return this.fields.fields(f);
  }
  
  public final org.jooq.Field<?>[] fields(String... fieldNames)
  {
    return this.fields.fields(fieldNames);
  }
  
  public final org.jooq.Field<?>[] fields(int... fieldIndexes)
  {
    return this.fields.fields(fieldIndexes);
  }
  
  public final int size()
  {
    return this.fields.size();
  }
  
  public final <T> T getValue(org.jooq.Field<T> field)
  {
    return (T)getValue(Utils.indexOrFail(fieldsRow(), field));
  }
  
  @Deprecated
  public final <T> T getValue(org.jooq.Field<T> field, T defaultValue)
  {
    T result = getValue(field);
    return result != null ? result : defaultValue;
  }
  
  public final <T> T getValue(org.jooq.Field<?> field, Class<? extends T> type)
  {
    return (T)Convert.convert(getValue(field), type);
  }
  
  @Deprecated
  public final <T> T getValue(org.jooq.Field<?> field, Class<? extends T> type, T defaultValue)
  {
    T result = getValue(field, type);
    return result == null ? defaultValue : result;
  }
  
  public final <T, U> U getValue(org.jooq.Field<T> field, Converter<? super T, U> converter)
  {
    return (U)converter.from(getValue(field));
  }
  
  @Deprecated
  public final <T, U> U getValue(org.jooq.Field<T> field, Converter<? super T, U> converter, U defaultValue)
  {
    U result = getValue(field, converter);
    return result == null ? defaultValue : result;
  }
  
  public final Object getValue(int index)
  {
    return this.values[safeIndex(index)];
  }
  
  @Deprecated
  public final Object getValue(int index, Object defaultValue)
  {
    Object result = getValue(index);
    return result == null ? defaultValue : result;
  }
  
  public final <T> T getValue(int index, Class<? extends T> type)
  {
    return (T)Convert.convert(getValue(index), type);
  }
  
  @Deprecated
  public final <T> T getValue(int index, Class<? extends T> type, T defaultValue)
  {
    T result = getValue(index, type);
    return result == null ? defaultValue : result;
  }
  
  public final <U> U getValue(int index, Converter<?, U> converter)
  {
    return (U)Convert.convert(getValue(index), converter);
  }
  
  @Deprecated
  public final <U> U getValue(int index, Converter<?, U> converter, U defaultValue)
  {
    U result = getValue(index, converter);
    return result == null ? defaultValue : result;
  }
  
  public final Object getValue(String fieldName)
  {
    return getValue(Utils.indexOrFail(fieldsRow(), fieldName));
  }
  
  @Deprecated
  public final Object getValue(String fieldName, Object defaultValue)
  {
    return getValue(Utils.indexOrFail(fieldsRow(), fieldName), defaultValue);
  }
  
  public final <T> T getValue(String fieldName, Class<? extends T> type)
  {
    return (T)Convert.convert(getValue(fieldName), type);
  }
  
  @Deprecated
  public final <T> T getValue(String fieldName, Class<? extends T> type, T defaultValue)
  {
    T result = getValue(fieldName, type);
    return result == null ? defaultValue : result;
  }
  
  public final <U> U getValue(String fieldName, Converter<?, U> converter)
  {
    return (U)Convert.convert(getValue(fieldName), converter);
  }
  
  @Deprecated
  public final <U> U getValue(String fieldName, Converter<?, U> converter, U defaultValue)
  {
    U result = getValue(fieldName, converter);
    return result == null ? defaultValue : result;
  }
  
  protected final void setValue(int index, Object value)
  {
    setValue(index, field(index), value);
  }
  
  public final <T> void setValue(org.jooq.Field<T> field, T value)
  {
    setValue(Utils.indexOrFail(this.fields, field), field, value);
  }
  
  private final <T> void setValue(int index, org.jooq.Field<T> field, T value)
  {
    UniqueKey<?> key = getPrimaryKey();
    if ((key == null) || (!key.getFields().contains(field)))
    {
      this.changed.set(index);
    }
    else if (this.changed.get(index))
    {
      this.changed.set(index);
    }
    else if (SettingsTools.updatablePrimaryKeys(Utils.settings(this)))
    {
      this.changed.set(index);
    }
    else if (this.originals[index] == null)
    {
      this.changed.set(index);
    }
    else
    {
      this.changed.set(index, (this.changed.get(index)) || (!StringUtils.equals(this.values[index], value)));
      if (this.changed.get(index)) {
        changed(true);
      }
    }
    this.values[index] = value;
  }
  
  public final <T, U> void setValue(org.jooq.Field<T> field, U value, Converter<T, ? super U> converter)
  {
    setValue(field, converter.to(value));
  }
  
  final void setValues(org.jooq.Field<?>[] fields, AbstractRecord record)
  {
    this.fetched = record.fetched;
    for (org.jooq.Field<?> field : fields)
    {
      int targetIndex = Utils.indexOrFail(fieldsRow(), field);
      int sourceIndex = Utils.indexOrFail(record.fieldsRow(), field);
      
      this.values[targetIndex] = record.getValue(sourceIndex);
      this.originals[targetIndex] = record.original(sourceIndex);
      this.changed.set(targetIndex, record.changed(sourceIndex));
    }
  }
  
  final void intern0(int fieldIndex)
  {
    safeIndex(fieldIndex);
    if (field(fieldIndex).getType() == String.class)
    {
      this.values[fieldIndex] = ((String)this.values[fieldIndex]).intern();
      this.originals[fieldIndex] = ((String)this.originals[fieldIndex]).intern();
    }
  }
  
  final int safeIndex(int index)
  {
    if ((index >= 0) && (index < this.values.length)) {
      return index;
    }
    throw new IllegalArgumentException("No field at index " + index + " in Record type " + fieldsRow());
  }
  
  UniqueKey<?> getPrimaryKey()
  {
    return null;
  }
  
  public Record original()
  {
    Utils.newRecord(this.fetched, getClass(), this.fields.fields.fields, configuration()).operate(new RecordOperation()
    {
      public AbstractRecord operate(AbstractRecord record)
        throws RuntimeException
      {
        for (int i = 0; i < AbstractRecord.this.originals.length; i++)
        {
          record.values[i] = AbstractRecord.this.originals[i];
          record.originals[i] = AbstractRecord.this.originals[i];
        }
        return record;
      }
    });
  }
  
  public final <T> T original(org.jooq.Field<T> field)
  {
    return (T)original(Utils.indexOrFail(fieldsRow(), field));
  }
  
  public final Object original(int fieldIndex)
  {
    return this.originals[safeIndex(fieldIndex)];
  }
  
  public final Object original(String fieldName)
  {
    return original(Utils.indexOrFail(fieldsRow(), fieldName));
  }
  
  public final boolean changed()
  {
    return !this.changed.isEmpty();
  }
  
  public final boolean changed(org.jooq.Field<?> field)
  {
    return changed(Utils.indexOrFail(fieldsRow(), field));
  }
  
  public final boolean changed(int fieldIndex)
  {
    return this.changed.get(safeIndex(fieldIndex));
  }
  
  public final boolean changed(String fieldName)
  {
    return changed(Utils.indexOrFail(fieldsRow(), fieldName));
  }
  
  public final void changed(boolean c)
  {
    this.changed.set(0, this.values.length, c);
    if (!c) {
      System.arraycopy(this.values, 0, this.originals, 0, this.values.length);
    }
  }
  
  public final void changed(org.jooq.Field<?> field, boolean c)
  {
    changed(Utils.indexOrFail(fieldsRow(), field), c);
  }
  
  public final void changed(int fieldIndex, boolean c)
  {
    safeIndex(fieldIndex);
    
    this.changed.set(fieldIndex, c);
    if (!c) {
      this.originals[fieldIndex] = this.values[fieldIndex];
    }
  }
  
  public final void changed(String fieldName, boolean c)
  {
    changed(Utils.indexOrFail(fieldsRow(), fieldName), c);
  }
  
  public final void reset()
  {
    this.changed.clear();
    
    System.arraycopy(this.originals, 0, this.values, 0, this.originals.length);
  }
  
  public final void reset(org.jooq.Field<?> field)
  {
    reset(Utils.indexOrFail(fieldsRow(), field));
  }
  
  public final void reset(int fieldIndex)
  {
    safeIndex(fieldIndex);
    
    this.changed.clear(fieldIndex);
    this.values[fieldIndex] = this.originals[fieldIndex];
  }
  
  public final void reset(String fieldName)
  {
    reset(Utils.indexOrFail(fieldsRow(), fieldName));
  }
  
  public final Object[] intoArray()
  {
    return (Object[])into(Object[].class);
  }
  
  public final List<Object> intoList()
  {
    return Arrays.asList(intoArray());
  }
  
  public final Map<String, Object> intoMap()
  {
    Map<String, Object> map = new LinkedHashMap();
    
    int size = this.fields.size();
    for (int i = 0; i < size; i++)
    {
      org.jooq.Field<?> field = this.fields.field(i);
      if (map.put(field.getName(), getValue(i)) != null) {
        throw new InvalidResultException("Field " + field.getName() + " is not unique in Record : " + this);
      }
    }
    return map;
  }
  
  public final Record into(org.jooq.Field<?>... f)
  {
    return Utils.newRecord(this.fetched, Record.class, f, configuration()).operate(new TransferRecordState(f));
  }
  
  public final <T1> Record1<T1> into(org.jooq.Field<T1> field1)
  {
    return (Record1)into(new org.jooq.Field[] { field1 });
  }
  
  public final <T1, T2> Record2<T1, T2> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2)
  {
    return (Record2)into(new org.jooq.Field[] { field1, field2 });
  }
  
  public final <T1, T2, T3> Record3<T1, T2, T3> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3)
  {
    return (Record3)into(new org.jooq.Field[] { field1, field2, field3 });
  }
  
  public final <T1, T2, T3, T4> Record4<T1, T2, T3, T4> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4)
  {
    return (Record4)into(new org.jooq.Field[] { field1, field2, field3, field4 });
  }
  
  public final <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5)
  {
    return (Record5)into(new org.jooq.Field[] { field1, field2, field3, field4, field5 });
  }
  
  public final <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6)
  {
    return (Record6)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7)
  {
    return (Record7)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8)
  {
    return (Record8)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9)
  {
    return (Record9)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10)
  {
    return (Record10)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11)
  {
    return (Record11)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12)
  {
    return (Record12)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13)
  {
    return (Record13)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14)
  {
    return (Record14)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14, org.jooq.Field<T15> field15)
  {
    return (Record15)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14, org.jooq.Field<T15> field15, org.jooq.Field<T16> field16)
  {
    return (Record16)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14, org.jooq.Field<T15> field15, org.jooq.Field<T16> field16, org.jooq.Field<T17> field17)
  {
    return (Record17)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14, org.jooq.Field<T15> field15, org.jooq.Field<T16> field16, org.jooq.Field<T17> field17, org.jooq.Field<T18> field18)
  {
    return (Record18)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14, org.jooq.Field<T15> field15, org.jooq.Field<T16> field16, org.jooq.Field<T17> field17, org.jooq.Field<T18> field18, org.jooq.Field<T19> field19)
  {
    return (Record19)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14, org.jooq.Field<T15> field15, org.jooq.Field<T16> field16, org.jooq.Field<T17> field17, org.jooq.Field<T18> field18, org.jooq.Field<T19> field19, org.jooq.Field<T20> field20)
  {
    return (Record20)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14, org.jooq.Field<T15> field15, org.jooq.Field<T16> field16, org.jooq.Field<T17> field17, org.jooq.Field<T18> field18, org.jooq.Field<T19> field19, org.jooq.Field<T20> field20, org.jooq.Field<T21> field21)
  {
    return (Record21)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> into(org.jooq.Field<T1> field1, org.jooq.Field<T2> field2, org.jooq.Field<T3> field3, org.jooq.Field<T4> field4, org.jooq.Field<T5> field5, org.jooq.Field<T6> field6, org.jooq.Field<T7> field7, org.jooq.Field<T8> field8, org.jooq.Field<T9> field9, org.jooq.Field<T10> field10, org.jooq.Field<T11> field11, org.jooq.Field<T12> field12, org.jooq.Field<T13> field13, org.jooq.Field<T14> field14, org.jooq.Field<T15> field15, org.jooq.Field<T16> field16, org.jooq.Field<T17> field17, org.jooq.Field<T18> field18, org.jooq.Field<T19> field19, org.jooq.Field<T20> field20, org.jooq.Field<T21> field21, org.jooq.Field<T22> field22)
  {
    return (Record22)into(new org.jooq.Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  public final <E> E into(Class<? extends E> type)
  {
    return (E)Utils.configuration(this).recordMapperProvider().provide(this.fields.fields, type).map(this);
  }
  
  public final <E> E into(E object)
  {
    if (object == null) {
      throw new NullPointerException("Cannot copy Record into null");
    }
    Class<E> type = object.getClass();
    try
    {
      return (E)new DefaultRecordMapper(this.fields.fields, type, object, configuration()).map(this);
    }
    catch (MappingException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new MappingException("An error ocurred when mapping record to " + type, e);
    }
  }
  
  public final <R extends Record> R into(Table<R> table)
  {
    return Utils.newRecord(this.fetched, table, configuration()).operate(new TransferRecordState(table.fields()));
  }
  
  final <R extends Record> R intoRecord(Class<R> type)
  {
    return Utils.newRecord(this.fetched, type, fields(), configuration()).operate(new TransferRecordState(null));
  }
  
  private class TransferRecordState<R extends Record>
    implements RecordOperation<R, MappingException>
  {
    private final org.jooq.Field<?>[] targetFields;
    
    TransferRecordState()
    {
      this.targetFields = targetFields;
    }
    
    public R operate(R target)
      throws MappingException
    {
      AbstractRecord source = AbstractRecord.this;
      try
      {
        AbstractRecord t;
        int targetIndex;
        org.jooq.Field<?> targetField;
        if ((target instanceof AbstractRecord))
        {
          t = (AbstractRecord)target;
          for (targetIndex = 0; targetIndex < (this.targetFields != null ? this.targetFields.length : t.size()); targetIndex++)
          {
            targetField = this.targetFields != null ? this.targetFields[targetIndex] : t.field(targetIndex);
            int sourceIndex = AbstractRecord.this.fields.indexOf(targetField);
            if (sourceIndex >= 0)
            {
              DataType<?> targetType = targetField.getDataType();
              
              t.values[targetIndex] = targetType.convert(AbstractRecord.this.values[sourceIndex]);
              t.originals[targetIndex] = targetType.convert(AbstractRecord.this.originals[sourceIndex]);
              t.changed.set(targetIndex, AbstractRecord.this.changed.get(sourceIndex));
            }
          }
        }
        else
        {
          t = target.fields();targetIndex = t.length;
          for (targetField = 0; targetField < targetIndex; targetField++)
          {
            org.jooq.Field<?> targetField = t[targetField];
            org.jooq.Field<?> sourceField = AbstractRecord.this.field(targetField);
            if (sourceField != null) {
              Utils.setValue(target, targetField, source, sourceField);
            }
          }
        }
        return target;
      }
      catch (Exception e)
      {
        throw new MappingException("An error ocurred when mapping record to " + target, e);
      }
    }
  }
  
  public final ResultSet intoResultSet()
  {
    ResultImpl<Record> result = new ResultImpl(configuration(), this.fields.fields.fields);
    result.add(this);
    return result.intoResultSet();
  }
  
  public final <E> E map(RecordMapper<Record, E> mapper)
  {
    return (E)mapper.map(this);
  }
  
  public final void from(Object source)
  {
    if (source == null) {
      return;
    }
    if ((source instanceof Map)) {
      fromMap((Map)source);
    } else if ((source instanceof Object[])) {
      fromArray((Object[])source);
    } else {
      from(source, fields());
    }
  }
  
  public final void from(Object source, org.jooq.Field<?>... f)
  {
    if (source == null) {
      return;
    }
    if ((source instanceof Map))
    {
      fromMap((Map)source, f);
    }
    else if ((source instanceof Object[]))
    {
      fromArray((Object[])source, f);
    }
    else
    {
      Class<?> type = source.getClass();
      try
      {
        boolean useAnnotations = Utils.hasColumnAnnotations(configuration(), type);
        for (org.jooq.Field<?> field : f)
        {
          Method method;
          List<java.lang.reflect.Field> members;
          Method method;
          if (useAnnotations)
          {
            List<java.lang.reflect.Field> members = Utils.getAnnotatedMembers(configuration(), type, field.getName());
            method = Utils.getAnnotatedGetter(configuration(), type, field.getName());
          }
          else
          {
            members = Utils.getMatchingMembers(configuration(), type, field.getName());
            method = Utils.getMatchingGetter(configuration(), type, field.getName());
          }
          if (method != null) {
            Utils.setValue(this, field, method.invoke(source, new Object[0]));
          } else if (members.size() > 0) {
            from(source, (java.lang.reflect.Field)members.get(0), field);
          }
        }
      }
      catch (Exception e)
      {
        throw new MappingException("An error ocurred when mapping record from " + type, e);
      }
    }
    Utils.resetChangedOnNotNull(this);
  }
  
  public final void from(Object source, String... fieldNames)
  {
    from(source, fields(fieldNames));
  }
  
  public final void from(Object source, int... fieldIndexes)
  {
    from(source, fields(fieldIndexes));
  }
  
  public final void fromMap(Map<String, ?> map)
  {
    from(map, fields());
  }
  
  public final void fromMap(Map<String, ?> map, org.jooq.Field<?>... f)
  {
    for (int i = 0; i < f.length; i++)
    {
      String name = f[i].getName();
      if (map.containsKey(name)) {
        Utils.setValue(this, f[i], map.get(name));
      }
    }
  }
  
  public final void fromMap(Map<String, ?> map, String... fieldNames)
  {
    fromMap(map, fields(fieldNames));
  }
  
  public final void fromMap(Map<String, ?> map, int... fieldIndexes)
  {
    fromMap(map, fields(fieldIndexes));
  }
  
  public final void fromArray(Object... array)
  {
    fromArray(array, fields());
  }
  
  public final void fromArray(Object[] array, org.jooq.Field<?>... f)
  {
    Fields accept = new Fields(f);
    int size = this.fields.size();
    for (int i = 0; (i < size) && (i < array.length); i++)
    {
      org.jooq.Field field = this.fields.field(i);
      if (accept.field(field) != null) {
        Utils.setValue(this, field, array[i]);
      }
    }
  }
  
  public final void fromArray(Object[] array, String... fieldNames)
  {
    fromArray(array, fields(fieldNames));
  }
  
  public final void fromArray(Object[] array, int... fieldIndexes)
  {
    fromArray(array, fields(fieldIndexes));
  }
  
  protected final void from(Record source)
  {
    for (org.jooq.Field<?> field : this.fields.fields.fields)
    {
      org.jooq.Field<?> sourceField = source.field(field);
      if (sourceField != null) {
        Utils.setValue(this, field, source, sourceField);
      }
    }
  }
  
  private final void from(Object source, java.lang.reflect.Field member, org.jooq.Field<?> field)
    throws IllegalAccessException
  {
    Class<?> mType = member.getType();
    if (mType.isPrimitive())
    {
      if (mType == Byte.TYPE) {
        Utils.setValue(this, field, Byte.valueOf(member.getByte(source)));
      } else if (mType == Short.TYPE) {
        Utils.setValue(this, field, Short.valueOf(member.getShort(source)));
      } else if (mType == Integer.TYPE) {
        Utils.setValue(this, field, Integer.valueOf(member.getInt(source)));
      } else if (mType == Long.TYPE) {
        Utils.setValue(this, field, Long.valueOf(member.getLong(source)));
      } else if (mType == Float.TYPE) {
        Utils.setValue(this, field, Float.valueOf(member.getFloat(source)));
      } else if (mType == Double.TYPE) {
        Utils.setValue(this, field, Double.valueOf(member.getDouble(source)));
      } else if (mType == Boolean.TYPE) {
        Utils.setValue(this, field, Boolean.valueOf(member.getBoolean(source)));
      } else if (mType == Character.TYPE) {
        Utils.setValue(this, field, Character.valueOf(member.getChar(source)));
      }
    }
    else {
      Utils.setValue(this, field, member.get(source));
    }
  }
  
  public String toString()
  {
    Result<AbstractRecord> result = new ResultImpl(configuration(), this.fields.fields.fields);
    result.add(this);
    return result.toString();
  }
  
  public int compareTo(Record that)
  {
    if (that == null) {
      throw new NullPointerException();
    }
    if (size() != that.size()) {
      throw new ClassCastException(String.format("Trying to compare incomparable records (wrong degree):\n%s\n%s", new Object[] { this, that }));
    }
    Class<?>[] thisTypes = fieldsRow().types();
    Class<?>[] thatTypes = that.fieldsRow().types();
    if (!Arrays.asList(thisTypes).equals(Arrays.asList(thatTypes))) {
      throw new ClassCastException(String.format("Trying to compare incomparable records (type mismatch):\n%s\n%s", new Object[] { this, that }));
    }
    for (int i = 0; i < size(); i++)
    {
      Object thisValue = getValue(i);
      Object thatValue = that.getValue(i);
      if ((thisValue != null) || (thatValue != null))
      {
        if (thisValue == null) {
          return 1;
        }
        if (thatValue == null) {
          return -1;
        }
        if ((thisValue.getClass().isArray()) && (thatValue.getClass().isArray()))
        {
          if (thisValue.getClass() == byte[].class)
          {
            int compare = compare((byte[])thisValue, (byte[])thatValue);
            if (compare != 0) {
              return compare;
            }
          }
          else if (!thisValue.getClass().getComponentType().isPrimitive())
          {
            int compare = compare((Object[])thisValue, (Object[])thatValue);
            if (compare != 0) {
              return compare;
            }
          }
          else
          {
            throw new ClassCastException(String.format("Unsupported data type in natural ordering: %s", new Object[] { thisValue.getClass() }));
          }
        }
        else
        {
          int compare = ((Comparable)thisValue).compareTo(thatValue);
          if (compare != 0) {
            return compare;
          }
        }
      }
    }
    return 0;
  }
  
  final int compare(byte[] array1, byte[] array2)
  {
    int length = Math.min(array1.length, array2.length);
    for (int i = 0; i < length; i++)
    {
      int v1 = array1[i] & 0xFF;
      int v2 = array2[i] & 0xFF;
      if (v1 != v2) {
        return v1 < v2 ? -1 : 1;
      }
    }
    return array1.length - array2.length;
  }
  
  final int compare(Object[] array1, Object[] array2)
  {
    int length = Math.min(array1.length, array2.length);
    for (int i = 0; i < length; i++)
    {
      int compare = ((Comparable)array1[i]).compareTo(array2[i]);
      if (compare != 0) {
        return compare;
      }
    }
    return array1.length - array2.length;
  }
}

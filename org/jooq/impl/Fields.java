package org.jooq.impl;

import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordType;

class Fields<R extends Record>
  extends AbstractQueryPart
  implements RecordType<R>
{
  private static final long serialVersionUID = -6911012275707591576L;
  Field<?>[] fields;
  
  Fields(Field<?>... fields)
  {
    this.fields = fields;
  }
  
  Fields(Collection<? extends Field<?>> fields)
  {
    this.fields = ((Field[])fields.toArray(new Field[fields.size()]));
  }
  
  public final int size()
  {
    return this.fields.length;
  }
  
  public final <T> Field<T> field(Field<T> field)
  {
    if (field == null) {
      return null;
    }
    Field[] arrayOfField1 = this.fields;int i = arrayOfField1.length;
    for (Field<?> localField1 = 0; localField1 < i; localField1++)
    {
      f = arrayOfField1[localField1];
      if (f.equals(field)) {
        return f;
      }
    }
    String name = field.getName();
    Field[] arrayOfField2 = this.fields;localField1 = arrayOfField2.length;
    for (Field<?> f = 0; f < localField1; f++)
    {
      Field<?> f1 = arrayOfField2[f];
      if (f1.getName().equals(name)) {
        return f1;
      }
    }
    return null;
  }
  
  public final Field<?> field(String name)
  {
    if (name == null) {
      return null;
    }
    for (Field<?> f : this.fields) {
      if (f.getName().equals(name)) {
        return f;
      }
    }
    return null;
  }
  
  public final Field<?> field(int index)
  {
    if ((index >= 0) && (index < this.fields.length)) {
      return this.fields[index];
    }
    return null;
  }
  
  public final Field<?>[] fields()
  {
    return this.fields;
  }
  
  public final Field<?>[] fields(Field<?>... f)
  {
    Field<?>[] result = new Field[f.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = field(f[i]);
    }
    return result;
  }
  
  public final Field<?>[] fields(String... f)
  {
    Field<?>[] result = new Field[f.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = field(f[i]);
    }
    return result;
  }
  
  public final Field<?>[] fields(int... f)
  {
    Field<?>[] result = new Field[f.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = field(f[i]);
    }
    return result;
  }
  
  public final int indexOf(Field<?> field)
  {
    Field<?> compareWith = field(field);
    if (compareWith != null)
    {
      int size = this.fields.length;
      for (int i = 0; i < size; i++) {
        if (this.fields[i].equals(compareWith)) {
          return i;
        }
      }
    }
    return -1;
  }
  
  public final int indexOf(String fieldName)
  {
    return indexOf(field(fieldName));
  }
  
  public final Class<?>[] types()
  {
    int size = this.fields.length;
    Class<?>[] result = new Class[size];
    for (int i = 0; i < size; i++) {
      result[i] = field(i).getType();
    }
    return result;
  }
  
  public final Class<?> type(int fieldIndex)
  {
    return (fieldIndex >= 0) && (fieldIndex < size()) ? field(fieldIndex).getType() : null;
  }
  
  public final Class<?> type(String fieldName)
  {
    return type(Utils.indexOrFail(this, fieldName));
  }
  
  public final DataType<?>[] dataTypes()
  {
    int size = this.fields.length;
    DataType<?>[] result = new DataType[size];
    for (int i = 0; i < size; i++) {
      result[i] = field(i).getDataType();
    }
    return result;
  }
  
  public final DataType<?> dataType(int fieldIndex)
  {
    return (fieldIndex >= 0) && (fieldIndex < size()) ? field(fieldIndex).getDataType() : null;
  }
  
  public final DataType<?> dataType(String fieldName)
  {
    return dataType(Utils.indexOrFail(this, fieldName));
  }
  
  final int[] indexesOf(Field<?>... f)
  {
    int[] result = new int[f.length];
    for (int i = 0; i < f.length; i++) {
      result[i] = Utils.indexOrFail(this, f[i]);
    }
    return result;
  }
  
  final int[] indexesOf(String... fieldNames)
  {
    int[] result = new int[fieldNames.length];
    for (int i = 0; i < fieldNames.length; i++) {
      result[i] = Utils.indexOrFail(this, fieldNames[i]);
    }
    return result;
  }
  
  public final void accept(Context<?> ctx)
  {
    new QueryPartList(this.fields).accept(ctx);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  final void add(Field<?> f)
  {
    Field<?>[] result = new Field[this.fields.length + 1];
    
    System.arraycopy(this.fields, 0, result, 0, this.fields.length);
    result[this.fields.length] = f;
    
    this.fields = result;
  }
}

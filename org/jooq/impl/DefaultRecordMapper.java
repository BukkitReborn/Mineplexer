package org.jooq.impl;

import java.beans.ConstructorProperties;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.jooq.Attachable;
import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordType;
import org.jooq.exception.MappingException;
import org.jooq.tools.Convert;
import org.jooq.tools.reflect.Reflect;

public class DefaultRecordMapper<R extends Record, E>
  implements RecordMapper<R, E>
{
  private final org.jooq.Field<?>[] fields;
  private final Class<? extends E> type;
  private final Configuration configuration;
  private transient E instance;
  private RecordMapper<R, E> delegate;
  
  public DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type)
  {
    this(rowType, type, null, null);
  }
  
  DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type, Configuration configuration)
  {
    this(rowType, type, null, configuration);
  }
  
  DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type, E instance)
  {
    this(rowType, type, instance, null);
  }
  
  DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type, E instance, Configuration configuration)
  {
    this.fields = rowType.fields();
    this.type = type;
    this.instance = instance;
    this.configuration = configuration;
    
    init();
  }
  
  private final void init()
  {
    if (this.type.isArray())
    {
      this.delegate = new ArrayMapper(null);
      return;
    }
    if ((this.type.isPrimitive()) || (DefaultDataType.types().contains(this.type)))
    {
      this.delegate = new ValueTypeMapper(null);
      return;
    }
    if (Modifier.isAbstract(this.type.getModifiers()))
    {
      this.delegate = new ProxyMapper();
      return;
    }
    if (AbstractRecord.class.isAssignableFrom(this.type))
    {
      this.delegate = new RecordToRecordMapper(null);
      return;
    }
    try
    {
      this.delegate = new MutablePOJOMapper(this.type.getDeclaredConstructor(new Class[0]));
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Constructor<E>[] constructors = (Constructor[])this.type.getDeclaredConstructors();
      for (Constructor<E> constructor : constructors)
      {
        ConstructorProperties properties = (ConstructorProperties)constructor.getAnnotation(ConstructorProperties.class);
        if (properties != null)
        {
          this.delegate = new ImmutablePOJOMapperWithConstructorProperties(constructor, properties);
          return;
        }
      }
      for (Constructor<E> constructor : constructors)
      {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes.length == this.fields.length)
        {
          this.delegate = new ImmutablePOJOMapper(constructor, parameterTypes);
          return;
        }
      }
      throw new MappingException("No matching constructor found on type " + this.type + " for record " + this);
    }
  }
  
  public final E map(R record)
  {
    if (record == null) {
      return null;
    }
    try
    {
      return (E)attach(this.delegate.map(record), record);
    }
    catch (MappingException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new MappingException("An error ocurred when mapping record to " + this.type, e);
    }
  }
  
  private class ArrayMapper
    implements RecordMapper<R, E>
  {
    private ArrayMapper() {}
    
    public final E map(R record)
    {
      int size = record.size();
      Class<?> componentType = DefaultRecordMapper.this.type.getComponentType();
      
      Object[] result = (Object[])(DefaultRecordMapper.this.instance != null ? DefaultRecordMapper.this.instance : Array.newInstance(componentType, size));
      if (size > result.length) {
        result = (Object[])Array.newInstance(componentType, size);
      }
      for (int i = 0; i < size; i++) {
        result[i] = Convert.convert(record.getValue(i), componentType);
      }
      return result;
    }
  }
  
  private class ValueTypeMapper
    implements RecordMapper<R, E>
  {
    private ValueTypeMapper() {}
    
    public final E map(R record)
    {
      int size = record.size();
      if (size != 1) {
        throw new MappingException("Cannot map multi-column record of degree " + size + " to value type " + DefaultRecordMapper.this.type);
      }
      return (E)record.getValue(0, DefaultRecordMapper.this.type);
    }
  }
  
  private class ProxyMapper
    implements RecordMapper<R, E>
  {
    private final DefaultRecordMapper<R, E>.MutablePOJOMapper localDelegate;
    
    ProxyMapper()
    {
      this.localDelegate = new DefaultRecordMapper.MutablePOJOMapper(DefaultRecordMapper.this, null);
    }
    
    public final E map(R record)
    {
      E previous = DefaultRecordMapper.this.instance;
      try
      {
        DefaultRecordMapper.this.instance = Reflect.on(HashMap.class).create().as(DefaultRecordMapper.this.type);
        return (E)this.localDelegate.map(record);
      }
      finally
      {
        DefaultRecordMapper.this.instance = previous;
      }
    }
  }
  
  private class RecordToRecordMapper
    implements RecordMapper<R, AbstractRecord>
  {
    private RecordToRecordMapper() {}
    
    public final AbstractRecord map(R record)
    {
      try
      {
        if ((record instanceof AbstractRecord)) {
          return (AbstractRecord)((AbstractRecord)record).intoRecord(DefaultRecordMapper.this.type);
        }
        throw new MappingException("Cannot map record " + record + " to type " + DefaultRecordMapper.this.type);
      }
      catch (Exception e)
      {
        throw new MappingException("An error ocurred when mapping record to " + DefaultRecordMapper.this.type, e);
      }
    }
  }
  
  private class MutablePOJOMapper
    implements RecordMapper<R, E>
  {
    private final Constructor<? extends E> constructor;
    private final boolean useAnnotations;
    private final List<java.lang.reflect.Field>[] members;
    private final List<Method>[] methods;
    
    MutablePOJOMapper()
    {
      this.constructor = ((Constructor)Reflect.accessible(constructor));
      this.useAnnotations = Utils.hasColumnAnnotations(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type);
      this.members = new List[DefaultRecordMapper.this.fields.length];
      this.methods = new List[DefaultRecordMapper.this.fields.length];
      for (int i = 0; i < DefaultRecordMapper.this.fields.length; i++)
      {
        org.jooq.Field<?> field = DefaultRecordMapper.this.fields[i];
        if (this.useAnnotations)
        {
          this.members[i] = Utils.getAnnotatedMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, field.getName());
          this.methods[i] = Utils.getAnnotatedSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, field.getName());
        }
        else
        {
          this.members[i] = Utils.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, field.getName());
          this.methods[i] = Utils.getMatchingSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, field.getName());
        }
      }
    }
    
    public final E map(R record)
    {
      try
      {
        E result = DefaultRecordMapper.this.instance != null ? DefaultRecordMapper.this.instance : this.constructor.newInstance(new Object[0]);
        for (int i = 0; i < DefaultRecordMapper.this.fields.length; i++)
        {
          for (java.lang.reflect.Field member : this.members[i]) {
            if ((member.getModifiers() & 0x10) == 0) {
              map(record, result, member, i);
            }
          }
          for (Method method : this.methods[i]) {
            method.invoke(result, new Object[] { record.getValue(i, method.getParameterTypes()[0]) });
          }
        }
        return result;
      }
      catch (Exception e)
      {
        throw new MappingException("An error ocurred when mapping record to " + DefaultRecordMapper.this.type, e);
      }
    }
    
    private final void map(Record record, Object result, java.lang.reflect.Field member, int index)
      throws IllegalAccessException
    {
      Class<?> mType = member.getType();
      if (mType.isPrimitive())
      {
        if (mType == Byte.TYPE) {
          member.setByte(result, ((Byte)record.getValue(index, Byte.TYPE)).byteValue());
        } else if (mType == Short.TYPE) {
          member.setShort(result, ((Short)record.getValue(index, Short.TYPE)).shortValue());
        } else if (mType == Integer.TYPE) {
          member.setInt(result, ((Integer)record.getValue(index, Integer.TYPE)).intValue());
        } else if (mType == Long.TYPE) {
          member.setLong(result, ((Long)record.getValue(index, Long.TYPE)).longValue());
        } else if (mType == Float.TYPE) {
          member.setFloat(result, ((Float)record.getValue(index, Float.TYPE)).floatValue());
        } else if (mType == Double.TYPE) {
          member.setDouble(result, ((Double)record.getValue(index, Double.TYPE)).doubleValue());
        } else if (mType == Boolean.TYPE) {
          member.setBoolean(result, ((Boolean)record.getValue(index, Boolean.TYPE)).booleanValue());
        } else if (mType == Character.TYPE) {
          member.setChar(result, ((Character)record.getValue(index, Character.TYPE)).charValue());
        }
      }
      else {
        member.set(result, record.getValue(index, mType));
      }
    }
  }
  
  private class ImmutablePOJOMapper
    implements RecordMapper<R, E>
  {
    private final Constructor<E> constructor;
    private final Class<?>[] parameterTypes;
    
    public ImmutablePOJOMapper(Class<?>[] constructor)
    {
      this.constructor = ((Constructor)Reflect.accessible(constructor));
      this.parameterTypes = parameterTypes;
    }
    
    public final E map(R record)
    {
      try
      {
        Object[] converted = Convert.convert(record.intoArray(), this.parameterTypes);
        return (E)this.constructor.newInstance(converted);
      }
      catch (Exception e)
      {
        throw new MappingException("An error ocurred when mapping record to " + DefaultRecordMapper.this.type, e);
      }
    }
  }
  
  private class ImmutablePOJOMapperWithConstructorProperties
    implements RecordMapper<R, E>
  {
    private final Constructor<E> constructor;
    private final Class<?>[] parameterTypes;
    private final Object[] parameterValues;
    private final List<String> propertyNames;
    private final boolean useAnnotations;
    private final List<java.lang.reflect.Field>[] members;
    private final Method[] methods;
    
    ImmutablePOJOMapperWithConstructorProperties(ConstructorProperties constructor)
    {
      this.constructor = constructor;
      this.propertyNames = Arrays.asList(properties.value());
      this.useAnnotations = Utils.hasColumnAnnotations(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type);
      this.parameterTypes = constructor.getParameterTypes();
      this.parameterValues = new Object[this.parameterTypes.length];
      this.members = new List[DefaultRecordMapper.this.fields.length];
      this.methods = new Method[DefaultRecordMapper.this.fields.length];
      for (int i = 0; i < DefaultRecordMapper.this.fields.length; i++)
      {
        org.jooq.Field<?> field = DefaultRecordMapper.this.fields[i];
        if (this.useAnnotations)
        {
          this.members[i] = Utils.getAnnotatedMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, field.getName());
          this.methods[i] = Utils.getAnnotatedGetter(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, field.getName());
        }
        else
        {
          this.members[i] = Utils.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, field.getName());
          this.methods[i] = Utils.getMatchingGetter(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, field.getName());
        }
      }
    }
    
    public final E map(R record)
    {
      try
      {
        for (int i = 0; i < DefaultRecordMapper.this.fields.length; i++)
        {
          for (java.lang.reflect.Field member : this.members[i])
          {
            int index = this.propertyNames.indexOf(member.getName());
            if (index >= 0) {
              this.parameterValues[index] = record.getValue(i);
            }
          }
          if (this.methods[i] != null)
          {
            String name = Utils.getPropertyName(this.methods[i].getName());
            int index = this.propertyNames.indexOf(name);
            if (index >= 0) {
              this.parameterValues[index] = record.getValue(i);
            }
          }
        }
        Object[] converted = Convert.convert(this.parameterValues, this.parameterTypes);
        return (E)((Constructor)Reflect.accessible(this.constructor)).newInstance(converted);
      }
      catch (Exception e)
      {
        throw new MappingException("An error ocurred when mapping record to " + DefaultRecordMapper.this.type, e);
      }
    }
  }
  
  private static <E> E attach(E attachable, Record record)
  {
    if (((attachable instanceof Attachable)) && ((record instanceof AttachableInternal)))
    {
      Attachable a = (Attachable)attachable;
      AttachableInternal r = (AttachableInternal)record;
      if (Utils.attachRecords(r.configuration())) {
        a.attach(r.configuration());
      }
    }
    return attachable;
  }
}

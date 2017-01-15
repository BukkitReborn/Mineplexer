package org.jooq.tools;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.Converter;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.exception.DataTypeException;
import org.jooq.tools.jdbc.MockArray;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.Unsigned;

public final class Convert
{
  public static final Set<String> TRUE_VALUES;
  public static final Set<String> FALSE_VALUES;
  private static final Pattern UUID_PATTERN = Pattern.compile("(\\p{XDigit}{8})-?(\\p{XDigit}{4})-?(\\p{XDigit}{4})-?(\\p{XDigit}{4})-?(\\p{XDigit}{12})");
  
  static
  {
    Set<String> trueValues = new HashSet();
    Set<String> falseValues = new HashSet();
    
    trueValues.add("1");
    trueValues.add("1.0");
    trueValues.add("y");
    trueValues.add("Y");
    trueValues.add("yes");
    trueValues.add("YES");
    trueValues.add("true");
    trueValues.add("TRUE");
    trueValues.add("on");
    trueValues.add("ON");
    trueValues.add("enabled");
    trueValues.add("ENABLED");
    
    falseValues.add("0");
    falseValues.add("0.0");
    falseValues.add("n");
    falseValues.add("N");
    falseValues.add("no");
    falseValues.add("NO");
    falseValues.add("false");
    falseValues.add("FALSE");
    falseValues.add("off");
    falseValues.add("OFF");
    falseValues.add("disabled");
    falseValues.add("DISABLED");
    
    TRUE_VALUES = Collections.unmodifiableSet(trueValues);
    FALSE_VALUES = Collections.unmodifiableSet(falseValues);
  }
  
  public static final Object[] convert(Object[] values, Field<?>[] fields)
  {
    if (values != null)
    {
      Object[] result = new Object[values.length];
      for (int i = 0; i < values.length; i++) {
        if ((values[i] instanceof Field)) {
          result[i] = values[i];
        } else {
          result[i] = convert(values[i], fields[i].getType());
        }
      }
      return result;
    }
    return null;
  }
  
  public static final Object[] convert(Object[] values, Class<?>[] types)
  {
    if (values != null)
    {
      Object[] result = new Object[values.length];
      for (int i = 0; i < values.length; i++) {
        if ((values[i] instanceof Field)) {
          result[i] = values[i];
        } else {
          result[i] = convert(values[i], types[i]);
        }
      }
      return result;
    }
    return null;
  }
  
  public static final <U> U[] convertArray(Object[] from, Converter<?, U> converter)
    throws DataTypeException
  {
    if (from == null) {
      return null;
    }
    Object[] arrayOfT = convertArray(from, converter.fromType());
    Object[] arrayOfU = (Object[])java.lang.reflect.Array.newInstance(converter.toType(), from.length);
    for (int i = 0; i < arrayOfT.length; i++) {
      arrayOfU[i] = convert(arrayOfT[i], converter);
    }
    return (Object[])arrayOfU;
  }
  
  public static final Object[] convertArray(Object[] from, Class<?> toClass)
    throws DataTypeException
  {
    if (from == null) {
      return null;
    }
    if (!toClass.isArray()) {
      return convertArray(from, java.lang.reflect.Array.newInstance(toClass, 0).getClass());
    }
    if (toClass == from.getClass()) {
      return from;
    }
    Class<?> toComponentType = toClass.getComponentType();
    if (from.length == 0) {
      return Arrays.copyOf(from, from.length, toClass);
    }
    if ((from[0] != null) && (from[0].getClass() == toComponentType)) {
      return Arrays.copyOf(from, from.length, toClass);
    }
    Object[] result = (Object[])java.lang.reflect.Array.newInstance(toComponentType, from.length);
    for (int i = 0; i < from.length; i++) {
      result[i] = convert(from[i], toComponentType);
    }
    return result;
  }
  
  public static final <U> U convert(Object from, Converter<?, U> converter)
    throws DataTypeException
  {
    return (U)convert0(from, converter);
  }
  
  private static final <T, U> U convert0(Object from, Converter<T, U> converter)
    throws DataTypeException
  {
    ConvertAll<T> all = new ConvertAll(converter.fromType());
    return (U)converter.from(all.from(from));
  }
  
  public static final <T> T convert(Object from, Class<? extends T> toClass)
    throws DataTypeException
  {
    return (T)convert(from, new ConvertAll(toClass));
  }
  
  public static final <T> List<T> convert(Collection<?> collection, Class<? extends T> type)
    throws DataTypeException
  {
    return convert(collection, new ConvertAll(type));
  }
  
  public static final <U> List<U> convert(Collection<?> collection, Converter<?, U> converter)
    throws DataTypeException
  {
    return convert0(collection, converter);
  }
  
  private static final <T, U> List<U> convert0(Collection<?> collection, Converter<T, U> converter)
    throws DataTypeException
  {
    ConvertAll<T> all = new ConvertAll(converter.fromType());
    List<U> result = new ArrayList(collection.size());
    for (Object o : collection) {
      result.add(convert(all.from(o), converter));
    }
    return result;
  }
  
  private static class ConvertAll<U>
    implements Converter<Object, U>
  {
    private static final long serialVersionUID = 2508560107067092501L;
    private final Class<? extends U> toClass;
    
    ConvertAll(Class<? extends U> toClass)
    {
      this.toClass = toClass;
    }
    
    public U from(Object from)
    {
      if (from == null)
      {
        if (this.toClass.isPrimitive())
        {
          if (this.toClass == Character.TYPE) {
            return Character.valueOf('\000');
          }
          return (U)Convert.convert(Integer.valueOf(0), this.toClass);
        }
        return null;
      }
      Class<?> fromClass = from.getClass();
      if (this.toClass == fromClass) {
        return (U)from;
      }
      if (this.toClass.isAssignableFrom(fromClass)) {
        return (U)from;
      }
      if (fromClass == byte[].class) {
        return (U)Convert.convert(Arrays.toString((byte[])from), this.toClass);
      }
      if (fromClass.isArray())
      {
        if (this.toClass == java.sql.Array.class) {
          return new MockArray(null, (Object[])from, fromClass);
        }
        return Convert.convertArray((Object[])from, this.toClass);
      }
      if (this.toClass == String.class)
      {
        if ((from instanceof EnumType)) {
          return ((EnumType)from).getLiteral();
        }
        return from.toString();
      }
      if ((this.toClass == Byte.class) || (this.toClass == Byte.TYPE))
      {
        if (Number.class.isAssignableFrom(fromClass)) {
          return Byte.valueOf(((Number)from).byteValue());
        }
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        }
        try
        {
          return Byte.valueOf(new BigDecimal(from.toString().trim()).byteValue());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if ((this.toClass == Short.class) || (this.toClass == Short.TYPE))
      {
        if (Number.class.isAssignableFrom(fromClass)) {
          return Short.valueOf(((Number)from).shortValue());
        }
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? Short.valueOf((short)1) : Short.valueOf((short)0);
        }
        try
        {
          return Short.valueOf(new BigDecimal(from.toString().trim()).shortValue());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if ((this.toClass == Integer.class) || (this.toClass == Integer.TYPE))
      {
        if (Number.class.isAssignableFrom(fromClass)) {
          return Integer.valueOf(((Number)from).intValue());
        }
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? Integer.valueOf(1) : Integer.valueOf(0);
        }
        try
        {
          return Integer.valueOf(new BigDecimal(from.toString().trim()).intValue());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if ((this.toClass == Long.class) || (this.toClass == Long.TYPE))
      {
        if (Number.class.isAssignableFrom(fromClass)) {
          return Long.valueOf(((Number)from).longValue());
        }
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? Long.valueOf(1L) : Long.valueOf(0L);
        }
        if (java.util.Date.class.isAssignableFrom(fromClass)) {
          return Long.valueOf(((java.util.Date)from).getTime());
        }
        try
        {
          return Long.valueOf(new BigDecimal(from.toString().trim()).longValue());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if (this.toClass == UByte.class) {
        try
        {
          if (Number.class.isAssignableFrom(fromClass)) {
            return Unsigned.ubyte(((Number)from).shortValue());
          }
          if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
            return ((Boolean)from).booleanValue() ? Unsigned.ubyte(1) : Unsigned.ubyte(0);
          }
          return Unsigned.ubyte(new BigDecimal(from.toString().trim()).shortValue());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if (this.toClass == UShort.class) {
        try
        {
          if (Number.class.isAssignableFrom(fromClass)) {
            return Unsigned.ushort(((Number)from).intValue());
          }
          if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
            return ((Boolean)from).booleanValue() ? Unsigned.ushort(1) : Unsigned.ushort(0);
          }
          return Unsigned.ushort(new BigDecimal(from.toString().trim()).intValue());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if (this.toClass == UInteger.class) {
        try
        {
          if (Number.class.isAssignableFrom(fromClass)) {
            return Unsigned.uint(((Number)from).longValue());
          }
          if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
            return ((Boolean)from).booleanValue() ? Unsigned.uint(1) : Unsigned.uint(0);
          }
          return Unsigned.uint(new BigDecimal(from.toString().trim()).longValue());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if (this.toClass == ULong.class)
      {
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? Unsigned.ulong(1L) : Unsigned.ulong(0L);
        }
        if (java.util.Date.class.isAssignableFrom(fromClass)) {
          return Unsigned.ulong(((java.util.Date)from).getTime());
        }
        try
        {
          return Unsigned.ulong(new BigDecimal(from.toString().trim()).toBigInteger().toString());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if ((this.toClass == Float.class) || (this.toClass == Float.TYPE))
      {
        if (Number.class.isAssignableFrom(fromClass)) {
          return Float.valueOf(((Number)from).floatValue());
        }
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? Float.valueOf(1.0F) : Float.valueOf(0.0F);
        }
        try
        {
          return Float.valueOf(from.toString().trim());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if ((this.toClass == Double.class) || (this.toClass == Double.TYPE))
      {
        if (Number.class.isAssignableFrom(fromClass)) {
          return Double.valueOf(((Number)from).doubleValue());
        }
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? Double.valueOf(1.0D) : Double.valueOf(0.0D);
        }
        try
        {
          return Double.valueOf(from.toString().trim());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if (this.toClass == BigDecimal.class)
      {
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? BigDecimal.ONE : BigDecimal.ZERO;
        }
        try
        {
          return new BigDecimal(from.toString().trim());
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if (this.toClass == BigInteger.class)
      {
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? BigInteger.ONE : BigInteger.ZERO;
        }
        try
        {
          return new BigDecimal(from.toString().trim()).toBigInteger();
        }
        catch (NumberFormatException e)
        {
          return null;
        }
      }
      if ((this.toClass == Boolean.class) || (this.toClass == Boolean.TYPE))
      {
        String s = from.toString().toLowerCase().trim();
        if (Convert.TRUE_VALUES.contains(s)) {
          return Boolean.TRUE;
        }
        if (Convert.FALSE_VALUES.contains(s)) {
          return Boolean.FALSE;
        }
        return this.toClass == Boolean.class ? null : Boolean.valueOf(false);
      }
      if ((this.toClass == Character.class) || (this.toClass == Character.TYPE))
      {
        if ((fromClass == Boolean.class) || (fromClass == Boolean.TYPE)) {
          return ((Boolean)from).booleanValue() ? Character.valueOf('1') : Character.valueOf('0');
        }
        if (from.toString().length() < 1) {
          return null;
        }
        return Character.valueOf(from.toString().charAt(0));
      }
      if ((fromClass == String.class) && (this.toClass == URI.class)) {
        try
        {
          return new URI(from.toString());
        }
        catch (URISyntaxException e)
        {
          return null;
        }
      }
      if ((fromClass == String.class) && (this.toClass == URL.class)) {
        try
        {
          return new URI(from.toString()).toURL();
        }
        catch (Exception e)
        {
          return null;
        }
      }
      if ((fromClass == String.class) && (this.toClass == File.class)) {
        try
        {
          return new File(from.toString());
        }
        catch (Exception e)
        {
          return null;
        }
      }
      if (java.util.Date.class.isAssignableFrom(fromClass)) {
        return (U)toDate(((java.util.Date)from).getTime(), this.toClass);
      }
      if (((fromClass == Long.class) || (fromClass == Long.TYPE)) && (java.util.Date.class.isAssignableFrom(this.toClass))) {
        return (U)toDate(((Long)from).longValue(), this.toClass);
      }
      if ((fromClass == String.class) && (this.toClass == java.sql.Date.class)) {
        try
        {
          return java.sql.Date.valueOf((String)from);
        }
        catch (IllegalArgumentException e)
        {
          return null;
        }
      }
      if ((fromClass == String.class) && (this.toClass == Time.class)) {
        try
        {
          return Time.valueOf((String)from);
        }
        catch (IllegalArgumentException e)
        {
          return null;
        }
      }
      if ((fromClass == String.class) && (this.toClass == Timestamp.class)) {
        try
        {
          return Timestamp.valueOf((String)from);
        }
        catch (IllegalArgumentException e)
        {
          return null;
        }
      }
      if ((fromClass == String.class) && (Enum.class.isAssignableFrom(this.toClass))) {
        try
        {
          return Enum.valueOf(this.toClass, (String)from);
        }
        catch (IllegalArgumentException e)
        {
          return null;
        }
      }
      if ((fromClass == String.class) && (this.toClass == UUID.class)) {
        try
        {
          return parseUUID((String)from);
        }
        catch (IllegalArgumentException e)
        {
          return null;
        }
      }
      if (Record.class.isAssignableFrom(fromClass))
      {
        Record record = (Record)from;
        return (U)record.into(this.toClass);
      }
      throw fail(from, this.toClass);
    }
    
    public Object to(U to)
    {
      return to;
    }
    
    public Class<Object> fromType()
    {
      return Object.class;
    }
    
    public Class<U> toType()
    {
      return this.toClass;
    }
    
    private static <X> X toDate(long time, Class<X> toClass)
    {
      if (toClass == java.sql.Date.class) {
        return new java.sql.Date(time);
      }
      if (toClass == Time.class) {
        return new Time(time);
      }
      if (toClass == Timestamp.class) {
        return new Timestamp(time);
      }
      if (toClass == java.util.Date.class) {
        return new java.util.Date(time);
      }
      if (toClass == Calendar.class)
      {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
      }
      throw fail(Long.valueOf(time), toClass);
    }
    
    private static final UUID parseUUID(String string)
    {
      if (string == null) {
        return null;
      }
      if (string.contains("-")) {
        return UUID.fromString(string);
      }
      return UUID.fromString(Convert.UUID_PATTERN.matcher(string).replaceAll("$1-$2-$3-$4-$5"));
    }
    
    private static DataTypeException fail(Object from, Class<?> toClass)
    {
      return new DataTypeException("Cannot convert from " + from + " (" + from.getClass() + ") to " + toClass);
    }
  }
}

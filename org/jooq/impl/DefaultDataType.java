package org.jooq.impl;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.Binding;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.UDTRecord;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.Convert;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.Interval;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

public class DefaultDataType<T>
  implements DataType<T>
{
  private static final long serialVersionUID = 4155588654449505119L;
  private static final Pattern NORMALISE_PATTERN = Pattern.compile("\"|\\.|\\s|\\(\\w+(\\s*,\\s*\\w+)*\\)|(NOT\\s*NULL)?");
  private static final Pattern TYPE_NAME_PATTERN = Pattern.compile("\\([^\\)]*\\)");
  private static final Map<String, DataType<?>>[] TYPES_BY_NAME;
  private static final Map<Class<?>, DataType<?>>[] TYPES_BY_TYPE;
  private static final Map<DataType<?>, DataType<?>>[] TYPES_BY_SQL_DATATYPE;
  private static final Map<Class<?>, DataType<?>> SQL_DATATYPES_BY_TYPE;
  private static final int LONG_PRECISION = String.valueOf(Long.MAX_VALUE).length();
  private static final int INTEGER_PRECISION = String.valueOf(Integer.MAX_VALUE).length();
  private static final int SHORT_PRECISION = String.valueOf(32767).length();
  private static final int BYTE_PRECISION = String.valueOf(127).length();
  private final SQLDialect dialect;
  private final DataType<T> sqlDataType;
  private final Class<T> type;
  private final Class<T[]> arrayType;
  private final String castTypeName;
  private final String castTypeBase;
  private final String typeName;
  private final boolean nullable;
  private final boolean defaulted;
  private final int precision;
  private final int scale;
  private final int length;
  
  static
  {
    TYPES_BY_SQL_DATATYPE = new Map[SQLDialect.values().length];
    TYPES_BY_NAME = new Map[SQLDialect.values().length];
    TYPES_BY_TYPE = new Map[SQLDialect.values().length];
    for (SQLDialect dialect : SQLDialect.values())
    {
      TYPES_BY_SQL_DATATYPE[dialect.ordinal()] = new LinkedHashMap();
      TYPES_BY_NAME[dialect.ordinal()] = new LinkedHashMap();
      TYPES_BY_TYPE[dialect.ordinal()] = new LinkedHashMap();
    }
    SQL_DATATYPES_BY_TYPE = new LinkedHashMap();
    try
    {
      Class.forName(SQLDataType.class.getName());
    }
    catch (Exception localException1) {}
  }
  
  public DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, String typeName)
  {
    this(dialect, sqlDataType, sqlDataType.getType(), typeName, typeName, sqlDataType.precision(), sqlDataType.scale(), sqlDataType.length(), sqlDataType.nullable(), sqlDataType.defaulted());
  }
  
  public DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, String typeName, String castTypeName)
  {
    this(dialect, sqlDataType, sqlDataType.getType(), typeName, castTypeName, sqlDataType.precision(), sqlDataType.scale(), sqlDataType.length(), sqlDataType.nullable(), sqlDataType.defaulted());
  }
  
  public DefaultDataType(SQLDialect dialect, Class<T> type, String typeName)
  {
    this(dialect, null, type, typeName, typeName, 0, 0, 0, true, false);
  }
  
  public DefaultDataType(SQLDialect dialect, Class<T> type, String typeName, String castTypeName)
  {
    this(dialect, null, type, typeName, castTypeName, 0, 0, 0, true, false);
  }
  
  DefaultDataType(SQLDialect dialect, Class<T> type, String typeName, String castTypeName, int precision, int scale, int length, boolean nullable, boolean defaulted)
  {
    this(dialect, null, type, typeName, castTypeName, precision, scale, length, nullable, defaulted);
  }
  
  DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, Class<T> type, String typeName, String castTypeName, int precision, int scale, int length, boolean nullable, boolean defaulted)
  {
    this.dialect = dialect;
    
    this.sqlDataType = (dialect == null ? this : sqlDataType);
    this.type = type;
    this.typeName = typeName;
    this.castTypeName = castTypeName;
    this.castTypeBase = TYPE_NAME_PATTERN.matcher(castTypeName).replaceAll("").trim();
    this.arrayType = Array.newInstance(type, 0).getClass();
    if (precision == 0) {
      if ((type == Long.class) || (type == ULong.class)) {
        precision = LONG_PRECISION;
      } else if ((type == Integer.class) || (type == UInteger.class)) {
        precision = INTEGER_PRECISION;
      } else if ((type == Short.class) || (type == UShort.class)) {
        precision = SHORT_PRECISION;
      } else if ((type == Byte.class) || (type == UByte.class)) {
        precision = BYTE_PRECISION;
      }
    }
    this.nullable = nullable;
    this.defaulted = defaulted;
    this.precision = precision;
    this.scale = scale;
    this.length = length;
    
    int ordinal = dialect == null ? SQLDialect.SQL99.ordinal() : dialect.ordinal();
    if (!TYPES_BY_NAME[ordinal].containsKey(typeName.toUpperCase()))
    {
      String normalised = normalise(typeName);
      if (TYPES_BY_NAME[ordinal].get(normalised) == null) {
        TYPES_BY_NAME[ordinal].put(normalised, this);
      }
    }
    if (TYPES_BY_TYPE[ordinal].get(type) == null) {
      TYPES_BY_TYPE[ordinal].put(type, this);
    }
    if (TYPES_BY_SQL_DATATYPE[ordinal].get(sqlDataType) == null) {
      TYPES_BY_SQL_DATATYPE[ordinal].put(sqlDataType, this);
    }
    if ((dialect == null) && 
      (SQL_DATATYPES_BY_TYPE.get(type) == null)) {
      SQL_DATATYPES_BY_TYPE.put(type, this);
    }
  }
  
  public final DataType<T> nullable(boolean n)
  {
    return new DefaultDataType(this.dialect, this.sqlDataType, this.type, this.typeName, this.castTypeName, this.precision, this.scale, this.length, n, this.defaulted);
  }
  
  public final boolean nullable()
  {
    return this.nullable;
  }
  
  public final DataType<T> defaulted(boolean d)
  {
    return new DefaultDataType(this.dialect, this.sqlDataType, this.type, this.typeName, this.castTypeName, this.precision, this.scale, this.length, this.nullable, d);
  }
  
  public final boolean defaulted()
  {
    return this.defaulted;
  }
  
  public final DataType<T> precision(int p)
  {
    return precision(p, this.scale);
  }
  
  public final DataType<T> precision(int p, int s)
  {
    if ((this.precision == p) && (this.scale == s)) {
      return this;
    }
    return new DefaultDataType(this.dialect, this.sqlDataType, this.type, this.typeName, this.castTypeName, p, s, this.length, this.nullable, this.defaulted);
  }
  
  public final int precision()
  {
    return this.precision;
  }
  
  public final boolean hasPrecision()
  {
    return (this.type == BigInteger.class) || (this.type == BigDecimal.class);
  }
  
  public final DataType<T> scale(int s)
  {
    if (this.scale == s) {
      return this;
    }
    return new DefaultDataType(this.dialect, this.sqlDataType, this.type, this.typeName, this.castTypeName, this.precision, s, this.length, this.nullable, this.defaulted);
  }
  
  public final int scale()
  {
    return this.scale;
  }
  
  public final boolean hasScale()
  {
    return this.type == BigDecimal.class;
  }
  
  public final DataType<T> length(int l)
  {
    if (this.length == l) {
      return this;
    }
    return new DefaultDataType(this.dialect, this.sqlDataType, this.type, this.typeName, this.castTypeName, this.precision, this.scale, l, this.nullable, this.defaulted);
  }
  
  public final int length()
  {
    return this.length;
  }
  
  public final boolean hasLength()
  {
    return (this.type == byte[].class) || (this.type == String.class);
  }
  
  public final DataType<T> getSQLDataType()
  {
    return this.sqlDataType;
  }
  
  public final DataType<T> getDataType(Configuration configuration)
  {
    if (getDialect() == null)
    {
      DataType<?> dataType = (DataType)TYPES_BY_SQL_DATATYPE[configuration.dialect().family().ordinal()].get(length(0).precision(0, 0));
      if (dataType != null) {
        return dataType.length(this.length).precision(this.precision, this.scale);
      }
    }
    else
    {
      if (getDialect().family() == configuration.dialect().family()) {
        return this;
      }
      if (getSQLDataType() == null) {
        return this;
      }
      getSQLDataType().getDataType(configuration);
    }
    return this;
  }
  
  public int getSQLType()
  {
    if (this.type == Blob.class) {
      return 2004;
    }
    if (this.type == Boolean.class) {
      return 16;
    }
    if (this.type == BigInteger.class) {
      return -5;
    }
    if (this.type == BigDecimal.class) {
      return 3;
    }
    if (this.type == Byte.class) {
      return -6;
    }
    if (this.type == byte[].class) {
      return 2004;
    }
    if (this.type == Clob.class) {
      return 2005;
    }
    if (this.type == java.sql.Date.class) {
      return 91;
    }
    if (this.type == Double.class) {
      return 8;
    }
    if (this.type == Float.class) {
      return 6;
    }
    if (this.type == Integer.class) {
      return 4;
    }
    if (this.type == Long.class) {
      return -5;
    }
    if (this.type == Short.class) {
      return 5;
    }
    if (this.type == String.class) {
      return 12;
    }
    if (this.type == Time.class) {
      return 92;
    }
    if (this.type == Timestamp.class) {
      return 93;
    }
    if (this.type.isArray()) {
      return 2003;
    }
    if (EnumType.class.isAssignableFrom(this.type)) {
      return 12;
    }
    if (UDTRecord.class.isAssignableFrom(this.type)) {
      return 2002;
    }
    if (Result.class.isAssignableFrom(this.type))
    {
      switch (this.dialect.family())
      {
      case H2: 
        return -10;
      }
      return 1111;
    }
    return 1111;
  }
  
  public final Class<T> getType()
  {
    return this.type;
  }
  
  public final Class<T[]> getArrayType()
  {
    return this.arrayType;
  }
  
  public final String getTypeName()
  {
    return this.typeName;
  }
  
  public String getTypeName(Configuration configuration)
  {
    return getDataType(configuration).getTypeName();
  }
  
  public final String getCastTypeName()
  {
    if ((this.length != 0) && (hasLength())) {
      return this.castTypeBase + "(" + this.length + ")";
    }
    if ((this.precision != 0) && (hasPrecision()))
    {
      if ((this.scale != 0) && (hasScale())) {
        return this.castTypeBase + "(" + this.precision + ", " + this.scale + ")";
      }
      return this.castTypeBase + "(" + this.precision + ")";
    }
    return this.castTypeName;
  }
  
  public String getCastTypeName(Configuration configuration)
  {
    return getDataType(configuration).getCastTypeName();
  }
  
  public final DataType<T[]> getArrayDataType()
  {
    return new ArrayDataType(this);
  }
  
  public final <E extends EnumType> DataType<E> asEnumDataType(Class<E> enumDataType)
  {
    String enumTypeName = ((EnumType[])enumDataType.getEnumConstants())[0].getName();
    return new DefaultDataType(this.dialect, enumDataType, enumTypeName, enumTypeName);
  }
  
  public final <U> DataType<U> asConvertedDataType(Converter<? super T, U> converter)
  {
    return asConvertedDataType(DefaultBinding.newBinding(converter, this, null));
  }
  
  public final <U> DataType<U> asConvertedDataType(Binding<? super T, U> binding)
  {
    return new ConvertedDataType(this, binding);
  }
  
  public final SQLDialect getDialect()
  {
    return this.dialect;
  }
  
  public T convert(Object object)
  {
    if (object == null) {
      return null;
    }
    if (object.getClass() == this.type) {
      return (T)object;
    }
    return (T)Convert.convert(object, this.type);
  }
  
  public final T[] convert(Object... objects)
  {
    return (Object[])Convert.convertArray(objects, this.type);
  }
  
  public final List<T> convert(Collection<?> objects)
  {
    return Convert.convert(objects, this.type);
  }
  
  public static DataType<Object> getDefaultDataType(String typeName)
  {
    return new DefaultDataType(SQLDialect.SQL99, Object.class, typeName, typeName);
  }
  
  public static DataType<Object> getDefaultDataType(SQLDialect dialect, String typeName)
  {
    return new DefaultDataType(dialect, Object.class, typeName, typeName);
  }
  
  public static DataType<?> getDataType(SQLDialect dialect, String typeName)
  {
    DataType<?> result = (DataType)TYPES_BY_NAME[dialect.ordinal()].get(typeName.toUpperCase());
    if (result == null)
    {
      typeName = normalise(typeName);
      result = (DataType)TYPES_BY_NAME[dialect.ordinal()].get(typeName);
    }
    if (result == null) {
      result = (DataType)TYPES_BY_NAME[SQLDialect.SQL99.ordinal()].get(typeName);
    }
    if (result == null) {
      throw new SQLDialectNotSupportedException("Type " + typeName + " is not supported in dialect " + dialect, false);
    }
    return result;
  }
  
  public static <T> DataType<T> getDataType(SQLDialect dialect, Class<T> type)
  {
    return getDataType(dialect, type, null);
  }
  
  public static <T> DataType<T> getDataType(SQLDialect dialect, Class<T> type, DataType<T> fallbackDataType)
  {
    type = Reflect.wrapper(type);
    if ((byte[].class != type) && (type.isArray())) {
      return getDataType(dialect, type.getComponentType()).getArrayDataType();
    }
    DataType<?> result = null;
    if (dialect != null) {
      result = (DataType)TYPES_BY_TYPE[dialect.ordinal()].get(type);
    }
    if (result == null) {
      if ((EnumType.class.isAssignableFrom(type)) || 
        (UDTRecord.class.isAssignableFrom(type))) {
        for (SQLDialect d : SQLDialect.values())
        {
          result = (DataType)TYPES_BY_TYPE[d.ordinal()].get(type);
          if (result != null) {
            break;
          }
        }
      }
    }
    if (result == null)
    {
      if (SQL_DATATYPES_BY_TYPE.get(type) != null) {
        return (DataType)SQL_DATATYPES_BY_TYPE.get(type);
      }
      if (fallbackDataType != null) {
        return fallbackDataType;
      }
      throw new SQLDialectNotSupportedException("Type " + type + " is not supported in dialect " + dialect);
    }
    return result;
  }
  
  public final boolean isNumeric()
  {
    return (Number.class.isAssignableFrom(this.type)) && (!isInterval());
  }
  
  public final boolean isString()
  {
    return this.type == String.class;
  }
  
  public final boolean isDateTime()
  {
    return java.util.Date.class.isAssignableFrom(this.type);
  }
  
  public final boolean isTemporal()
  {
    return (isDateTime()) || (isInterval());
  }
  
  public final boolean isInterval()
  {
    return Interval.class.isAssignableFrom(this.type);
  }
  
  public final boolean isLob()
  {
    DataType<T> t = getSQLDataType();
    return (t == SQLDataType.BLOB) || (t == SQLDataType.CLOB) || (t == SQLDataType.NCLOB);
  }
  
  public final boolean isBinary()
  {
    return this.type == byte[].class;
  }
  
  public final boolean isArray()
  {
    return (!isBinary()) && (this.type.isArray());
  }
  
  public String toString()
  {
    return getCastTypeName() + " (" + this.type.getName() + ")";
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.dialect == null ? 0 : this.dialect.hashCode());
    result = 31 * result + this.length;
    result = 31 * result + this.precision;
    result = 31 * result + this.scale;
    result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
    result = 31 * result + (this.typeName == null ? 0 : this.typeName.hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    DefaultDataType<?> other = (DefaultDataType)obj;
    if (this.dialect != other.dialect) {
      return false;
    }
    if (this.length != other.length) {
      return false;
    }
    if (this.precision != other.precision) {
      return false;
    }
    if (this.scale != other.scale) {
      return false;
    }
    if (this.type == null)
    {
      if (other.type != null) {
        return false;
      }
    }
    else if (!this.type.equals(other.type)) {
      return false;
    }
    if (this.typeName == null)
    {
      if (other.typeName != null) {
        return false;
      }
    }
    else if (!this.typeName.equals(other.typeName)) {
      return false;
    }
    return true;
  }
  
  public static String normalise(String typeName)
  {
    return NORMALISE_PATTERN.matcher(typeName.toUpperCase()).replaceAll("");
  }
  
  public static DataType<?> getDataType(SQLDialect dialect, String t, int p, int s)
    throws SQLDialectNotSupportedException
  {
    DataType<?> result = getDataType(dialect, t);
    if (result.getType() == BigDecimal.class) {
      result = getDataType(dialect, getNumericClass(p, s));
    }
    return result;
  }
  
  public static Class<?> getType(SQLDialect dialect, String t, int p, int s)
    throws SQLDialectNotSupportedException
  {
    return getDataType(dialect, t, p, s).getType();
  }
  
  private static Class<?> getNumericClass(int precision, int scale)
  {
    if ((scale == 0) && (precision != 0))
    {
      if (precision < BYTE_PRECISION) {
        return Byte.class;
      }
      if (precision < SHORT_PRECISION) {
        return Short.class;
      }
      if (precision < INTEGER_PRECISION) {
        return Integer.class;
      }
      if (precision < LONG_PRECISION) {
        return Long.class;
      }
      return BigInteger.class;
    }
    return BigDecimal.class;
  }
  
  static Collection<Class<?>> types()
  {
    return Collections.unmodifiableCollection(SQL_DATATYPES_BY_TYPE.keySet());
  }
  
  static Collection<DataType<?>> dataTypes()
  {
    return Collections.unmodifiableCollection(SQL_DATATYPES_BY_TYPE.values());
  }
}

package org.jooq.util.postgres;

import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class PostgresDSL
  extends DSL
{
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayAppend(T[] array, T value)
  {
    return arrayAppend(val(array), val(value));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayAppend(T[] array, Field<T> value)
  {
    return arrayAppend(val(array), value);
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayAppend(Field<T[]> array, T value)
  {
    return arrayAppend(array, val(value));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayAppend(Field<T[]> array, Field<T> value)
  {
    return field("{array_append}({0}, {1})", nullSafe(array).getDataType(), new QueryPart[] { nullSafe(array), nullSafe(value) });
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayPrepend(T value, T[] array)
  {
    return arrayPrepend(val(value), val(array));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayPrepend(Field<T> value, T[] array)
  {
    return arrayPrepend(value, val(array));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayPrepend(T value, Field<T[]> array)
  {
    return arrayPrepend(val(value), array);
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayPrepend(Field<T> value, Field<T[]> array)
  {
    return field("{array_prepend}({0}, {1})", nullSafe(array).getDataType(), new QueryPart[] { nullSafe(value), nullSafe(array) });
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayCat(T[] array1, T[] array2)
  {
    return arrayCat(val(array1), val(array2));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayCat(T[] array1, Field<T[]> array2)
  {
    return arrayCat(val(array1), array2);
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayCat(Field<T[]> array1, T[] array2)
  {
    return arrayCat(array1, val(array2));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static <T> Field<T[]> arrayCat(Field<T[]> array1, Field<T[]> array2)
  {
    return field("{array_cat}({0}, {1})", nullSafe(array1).getDataType(), new QueryPart[] { nullSafe(array1), nullSafe(array2) });
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<String> arrayToString(Object[] array, String delimiter)
  {
    return arrayToString(val(array), val(delimiter));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<String> arrayToString(Object[] array, Field<String> delimiter)
  {
    return arrayToString(val(array), delimiter);
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<String> arrayToString(Field<? extends Object[]> array, String delimiter)
  {
    return arrayToString(array, val(delimiter));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<String> arrayToString(Field<? extends Object[]> array, Field<String> delimiter)
  {
    return field("{array_to_string}({0}, {1})", SQLDataType.VARCHAR, new QueryPart[] { nullSafe(array), nullSafe(delimiter) });
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<String[]> stringToArray(String string, String delimiter)
  {
    return stringToArray(val(string), val(delimiter));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<String[]> stringToArray(String string, Field<String> delimiter)
  {
    return stringToArray(val(string), delimiter);
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<String[]> stringToArray(Field<String> string, String delimiter)
  {
    return stringToArray(string, val(delimiter));
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<String[]> stringToArray(Field<String> string, Field<String> delimiter)
  {
    return field("{string_to_array}({0}, {1})", SQLDataType.VARCHAR.getArrayDataType(), new QueryPart[] { nullSafe(string), nullSafe(delimiter) });
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Table<Record> only(Table<?> table)
  {
    return table("{only} {0}", new QueryPart[] { table });
  }
  
  @Support({org.jooq.SQLDialect.POSTGRES})
  public static Field<Long> oid(Table<?> table)
  {
    return field("{0}.oid", Long.class, new QueryPart[] { table });
  }
}

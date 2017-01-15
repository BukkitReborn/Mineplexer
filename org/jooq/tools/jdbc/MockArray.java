package org.jooq.tools.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;

public class MockArray<T>
  implements java.sql.Array
{
  private final SQLDialect dialect;
  private final T[] array;
  private final Class<? extends T[]> type;
  
  public MockArray(SQLDialect dialect, T[] array, Class<? extends T[]> type)
  {
    this.dialect = dialect;
    this.array = array;
    this.type = type;
  }
  
  public String getBaseTypeName()
  {
    return DefaultDataType.getDataType(this.dialect, this.type.getComponentType()).getTypeName();
  }
  
  public int getBaseType()
  {
    return DefaultDataType.getDataType(this.dialect, this.type.getComponentType()).getSQLType();
  }
  
  public T[] getArray()
  {
    return this.array;
  }
  
  public T[] getArray(Map<String, Class<?>> map)
  {
    return this.array;
  }
  
  public T[] getArray(long index, int count)
    throws SQLException
  {
    if (index - 1L > 2147483647L) {
      throw new SQLException("Cannot access array indexes beyond Integer.MAX_VALUE");
    }
    return this.array == null ? null : Arrays.asList(this.array).subList((int)index - 1, (int)index - 1 + count).toArray((Object[])java.lang.reflect.Array.newInstance(this.array.getClass().getComponentType(), count));
  }
  
  public T[] getArray(long index, int count, Map<String, Class<?>> map)
    throws SQLException
  {
    return getArray(index, count);
  }
  
  public ResultSet getResultSet()
  {
    return getResultSet0(this.array);
  }
  
  public ResultSet getResultSet(Map<String, Class<?>> map)
  {
    return getResultSet();
  }
  
  public ResultSet getResultSet(long index, int count)
    throws SQLException
  {
    return getResultSet0(getArray(index, count));
  }
  
  public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map)
    throws SQLException
  {
    return getResultSet(index, count);
  }
  
  private ResultSet getResultSet0(T[] a)
  {
    DSLContext create = DSL.using(this.dialect);
    
    Field<Long> index = DSL.fieldByName(Long.class, new String[] { "INDEX" });
    Field<T> value = DSL.fieldByName(this.type.getComponentType(), new String[] { "VALUE" });
    Result<Record2<Long, T>> result = create.newResult(index, value);
    for (int i = 0; i < a.length; i++)
    {
      Record2<Long, T> record = create.newRecord(index, value);
      
      record.setValue(index, Long.valueOf(i + 1L));
      record.setValue(value, a[i]);
      
      result.add(record);
    }
    return new MockResultSet(result);
  }
  
  public void free() {}
}

package org.jooq.tools.jdbc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;

public class MockResultSet
  extends JDBC41ResultSet
  implements ResultSet, Serializable
{
  private static final long serialVersionUID = -2292216936424437750L;
  private final int maxRows;
  Result<?> result;
  private transient int index;
  private transient boolean wasNull;
  
  public MockResultSet(Result<?> result)
  {
    this(result, 0);
  }
  
  public MockResultSet(Result<?> result, int maxRows)
  {
    this.result = result;
    this.maxRows = maxRows;
  }
  
  public <T> T unwrap(Class<T> iface)
    throws SQLException
  {
    return null;
  }
  
  public boolean isWrapperFor(Class<?> iface)
    throws SQLException
  {
    return false;
  }
  
  private int size()
  {
    if (this.maxRows == 0) {
      return this.result.size();
    }
    return Math.min(this.maxRows, this.result.size());
  }
  
  void checkNotClosed()
    throws SQLException
  {
    if (this.result == null) {
      throw new SQLException("ResultSet is already closed");
    }
  }
  
  private void checkInRange()
    throws SQLException
  {
    checkNotClosed();
    if ((this.index <= 0) || (this.index > this.result.size())) {
      throw new SQLException("ResultSet index is at an illegal position : " + this.index);
    }
  }
  
  private void checkField(String columnLabel)
    throws SQLException
  {
    if (this.result.field(columnLabel) == null) {
      throw new SQLException("Unknown column label : " + columnLabel);
    }
  }
  
  private void checkField(int columnIndex)
    throws SQLException
  {
    if (this.result.field(columnIndex - 1) == null) {
      throw new SQLException("Unknown column index : " + columnIndex);
    }
  }
  
  public boolean next()
    throws SQLException
  {
    return relative(1);
  }
  
  public boolean previous()
    throws SQLException
  {
    return relative(-1);
  }
  
  public boolean absolute(int row)
    throws SQLException
  {
    if (size() > 0)
    {
      if (row > 0)
      {
        if (row <= size())
        {
          this.index = row;
          return true;
        }
        afterLast();
        return false;
      }
      if (row == 0)
      {
        beforeFirst();
        return false;
      }
      if (-row <= size())
      {
        this.index = (size() + 1 + row);
        return true;
      }
      beforeFirst();
      return false;
    }
    return false;
  }
  
  public boolean relative(int rows)
    throws SQLException
  {
    checkNotClosed();
    
    this.index += rows;
    try
    {
      return (this.index > 0) && (this.index <= size());
    }
    finally
    {
      this.index = Math.max(this.index, 0);
      this.index = Math.min(this.index, size() + 1);
    }
  }
  
  public int getRow()
    throws SQLException
  {
    return this.index > size() ? 0 : this.index;
  }
  
  public void beforeFirst()
    throws SQLException
  {
    checkNotClosed();
    this.index = 0;
  }
  
  public void afterLast()
    throws SQLException
  {
    checkNotClosed();
    this.index = (size() + 1);
  }
  
  public boolean first()
    throws SQLException
  {
    return absolute(1);
  }
  
  public boolean last()
    throws SQLException
  {
    checkNotClosed();
    return absolute(size());
  }
  
  public boolean isFirst()
    throws SQLException
  {
    checkNotClosed();
    
    return (size() > 0) && (this.index == 1);
  }
  
  public boolean isBeforeFirst()
    throws SQLException
  {
    checkNotClosed();
    
    return (size() > 0) && (this.index == 0);
  }
  
  public boolean isLast()
    throws SQLException
  {
    checkNotClosed();
    
    return (size() > 0) && (this.index == size());
  }
  
  public boolean isAfterLast()
    throws SQLException
  {
    checkNotClosed();
    
    return (size() > 0) && (this.index > size());
  }
  
  public void close()
    throws SQLException
  {
    checkNotClosed();
    
    this.result = null;
    this.index = 0;
  }
  
  public boolean isClosed()
    throws SQLException
  {
    return this.result == null;
  }
  
  public SQLWarning getWarnings()
    throws SQLException
  {
    return null;
  }
  
  public void clearWarnings()
    throws SQLException
  {}
  
  public String getCursorName()
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("jOOQ ResultSets don't have a cursor name");
  }
  
  public int findColumn(String columnLabel)
    throws SQLException
  {
    checkNotClosed();
    
    Field<?> field = this.result.field(columnLabel);
    if (field == null) {
      throw new SQLException("No such column : " + columnLabel);
    }
    return this.result.fieldsRow().indexOf(field) + 1;
  }
  
  public void setFetchDirection(int direction)
    throws SQLException
  {
    if (direction != 1000) {
      throw new SQLException("Fetch direction can only be FETCH_FORWARD");
    }
  }
  
  public int getFetchDirection()
    throws SQLException
  {
    return 1000;
  }
  
  public void setFetchSize(int rows)
    throws SQLException
  {}
  
  public int getFetchSize()
    throws SQLException
  {
    return 0;
  }
  
  public int getType()
    throws SQLException
  {
    return 1004;
  }
  
  public int getConcurrency()
    throws SQLException
  {
    return 1007;
  }
  
  public int getHoldability()
    throws SQLException
  {
    return 2;
  }
  
  public boolean wasNull()
    throws SQLException
  {
    checkNotClosed();
    
    return this.wasNull;
  }
  
  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    return new MockResultSetMetaData(this);
  }
  
  public Statement getStatement()
    throws SQLException
  {
    return null;
  }
  
  private <T> T getValue(String columnLabel, Class<T> type)
    throws SQLException
  {
    checkInRange();
    checkField(columnLabel);
    
    T value = ((Record)this.result.get(this.index - 1)).getValue(columnLabel, type);
    this.wasNull = (value == null);
    return value;
  }
  
  private <T> T getValue(int columnIndex, Class<T> type)
    throws SQLException
  {
    checkInRange();
    checkField(columnIndex);
    
    T value = ((Record)this.result.get(this.index - 1)).getValue(columnIndex - 1, type);
    this.wasNull = (value == null);
    return value;
  }
  
  public String getString(int columnIndex)
    throws SQLException
  {
    return (String)getValue(columnIndex, String.class);
  }
  
  public String getString(String columnLabel)
    throws SQLException
  {
    return (String)getValue(columnLabel, String.class);
  }
  
  public String getNString(int columnIndex)
    throws SQLException
  {
    return getString(columnIndex);
  }
  
  public String getNString(String columnLabel)
    throws SQLException
  {
    return getString(columnLabel);
  }
  
  public boolean getBoolean(int columnIndex)
    throws SQLException
  {
    Boolean value = (Boolean)getValue(columnIndex, Boolean.class);
    return this.wasNull ? false : value.booleanValue();
  }
  
  public boolean getBoolean(String columnLabel)
    throws SQLException
  {
    Boolean value = (Boolean)getValue(columnLabel, Boolean.class);
    return this.wasNull ? false : value.booleanValue();
  }
  
  public byte getByte(int columnIndex)
    throws SQLException
  {
    Byte value = (Byte)getValue(columnIndex, Byte.class);
    return this.wasNull ? 0 : value.byteValue();
  }
  
  public byte getByte(String columnLabel)
    throws SQLException
  {
    Byte value = (Byte)getValue(columnLabel, Byte.class);
    return this.wasNull ? 0 : value.byteValue();
  }
  
  public short getShort(int columnIndex)
    throws SQLException
  {
    Short value = (Short)getValue(columnIndex, Short.class);
    return this.wasNull ? 0 : value.shortValue();
  }
  
  public short getShort(String columnLabel)
    throws SQLException
  {
    Short value = (Short)getValue(columnLabel, Short.class);
    return this.wasNull ? 0 : value.shortValue();
  }
  
  public int getInt(int columnIndex)
    throws SQLException
  {
    Integer value = (Integer)getValue(columnIndex, Integer.class);
    return this.wasNull ? 0 : value.intValue();
  }
  
  public int getInt(String columnLabel)
    throws SQLException
  {
    Integer value = (Integer)getValue(columnLabel, Integer.class);
    return this.wasNull ? 0 : value.intValue();
  }
  
  public long getLong(int columnIndex)
    throws SQLException
  {
    Long value = (Long)getValue(columnIndex, Long.class);
    return this.wasNull ? 0L : value.longValue();
  }
  
  public long getLong(String columnLabel)
    throws SQLException
  {
    Long value = (Long)getValue(columnLabel, Long.class);
    return this.wasNull ? 0L : value.longValue();
  }
  
  public float getFloat(int columnIndex)
    throws SQLException
  {
    Float value = (Float)getValue(columnIndex, Float.class);
    return this.wasNull ? 0.0F : value.floatValue();
  }
  
  public float getFloat(String columnLabel)
    throws SQLException
  {
    Float value = (Float)getValue(columnLabel, Float.class);
    return this.wasNull ? 0.0F : value.floatValue();
  }
  
  public double getDouble(int columnIndex)
    throws SQLException
  {
    Double value = (Double)getValue(columnIndex, Double.class);
    return this.wasNull ? 0.0D : value.doubleValue();
  }
  
  public double getDouble(String columnLabel)
    throws SQLException
  {
    Double value = (Double)getValue(columnLabel, Double.class);
    return this.wasNull ? 0.0D : value.doubleValue();
  }
  
  public BigDecimal getBigDecimal(int columnIndex)
    throws SQLException
  {
    return (BigDecimal)getValue(columnIndex, BigDecimal.class);
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(int columnIndex, int scale)
    throws SQLException
  {
    return (BigDecimal)getValue(columnIndex, BigDecimal.class);
  }
  
  public BigDecimal getBigDecimal(String columnLabel)
    throws SQLException
  {
    return (BigDecimal)getValue(columnLabel, BigDecimal.class);
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(String columnLabel, int scale)
    throws SQLException
  {
    return (BigDecimal)getValue(columnLabel, BigDecimal.class);
  }
  
  public byte[] getBytes(int columnIndex)
    throws SQLException
  {
    return (byte[])getValue(columnIndex, byte[].class);
  }
  
  public byte[] getBytes(String columnLabel)
    throws SQLException
  {
    return (byte[])getValue(columnLabel, byte[].class);
  }
  
  public Date getDate(int columnIndex)
    throws SQLException
  {
    return (Date)getValue(columnIndex, Date.class);
  }
  
  public Date getDate(int columnIndex, Calendar cal)
    throws SQLException
  {
    return (Date)getValue(columnIndex, Date.class);
  }
  
  public Date getDate(String columnLabel)
    throws SQLException
  {
    return (Date)getValue(columnLabel, Date.class);
  }
  
  public Date getDate(String columnLabel, Calendar cal)
    throws SQLException
  {
    return (Date)getValue(columnLabel, Date.class);
  }
  
  public Time getTime(int columnIndex)
    throws SQLException
  {
    return (Time)getValue(columnIndex, Time.class);
  }
  
  public Time getTime(int columnIndex, Calendar cal)
    throws SQLException
  {
    return (Time)getValue(columnIndex, Time.class);
  }
  
  public Time getTime(String columnLabel)
    throws SQLException
  {
    return (Time)getValue(columnLabel, Time.class);
  }
  
  public Time getTime(String columnLabel, Calendar cal)
    throws SQLException
  {
    return (Time)getValue(columnLabel, Time.class);
  }
  
  public Timestamp getTimestamp(int columnIndex)
    throws SQLException
  {
    return (Timestamp)getValue(columnIndex, Timestamp.class);
  }
  
  public Timestamp getTimestamp(int columnIndex, Calendar cal)
    throws SQLException
  {
    return (Timestamp)getValue(columnIndex, Timestamp.class);
  }
  
  public Timestamp getTimestamp(String columnLabel)
    throws SQLException
  {
    return (Timestamp)getValue(columnLabel, Timestamp.class);
  }
  
  public Timestamp getTimestamp(String columnLabel, Calendar cal)
    throws SQLException
  {
    return (Timestamp)getValue(columnLabel, Timestamp.class);
  }
  
  public InputStream getAsciiStream(int columnIndex)
    throws SQLException
  {
    byte[] bytes = getBytes(columnIndex);
    return this.wasNull ? null : new ByteArrayInputStream(bytes);
  }
  
  public InputStream getAsciiStream(String columnLabel)
    throws SQLException
  {
    byte[] bytes = getBytes(columnLabel);
    return this.wasNull ? null : new ByteArrayInputStream(bytes);
  }
  
  @Deprecated
  public InputStream getUnicodeStream(int columnIndex)
    throws SQLException
  {
    String string = getString(columnIndex);
    return this.wasNull ? null : new ByteArrayInputStream(string.getBytes());
  }
  
  @Deprecated
  public InputStream getUnicodeStream(String columnLabel)
    throws SQLException
  {
    String string = getString(columnLabel);
    return this.wasNull ? null : new ByteArrayInputStream(string.getBytes());
  }
  
  public Reader getCharacterStream(int columnIndex)
    throws SQLException
  {
    String string = getString(columnIndex);
    return this.wasNull ? null : new StringReader(string);
  }
  
  public Reader getCharacterStream(String columnLabel)
    throws SQLException
  {
    String string = getString(columnLabel);
    return this.wasNull ? null : new StringReader(string);
  }
  
  public Reader getNCharacterStream(int columnIndex)
    throws SQLException
  {
    return getCharacterStream(columnIndex);
  }
  
  public Reader getNCharacterStream(String columnLabel)
    throws SQLException
  {
    return getCharacterStream(columnLabel);
  }
  
  public InputStream getBinaryStream(int columnIndex)
    throws SQLException
  {
    byte[] bytes = getBytes(columnIndex);
    return this.wasNull ? null : new ByteArrayInputStream(bytes);
  }
  
  public InputStream getBinaryStream(String columnLabel)
    throws SQLException
  {
    byte[] bytes = getBytes(columnLabel);
    return this.wasNull ? null : new ByteArrayInputStream(bytes);
  }
  
  public Ref getRef(int columnIndex)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type");
  }
  
  public Ref getRef(String columnLabel)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type");
  }
  
  public RowId getRowId(int columnIndex)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type");
  }
  
  public RowId getRowId(String columnLabel)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type");
  }
  
  public SQLXML getSQLXML(int columnIndex)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type");
  }
  
  public SQLXML getSQLXML(String columnLabel)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type");
  }
  
  public Blob getBlob(int columnIndex)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type. Use getBytes() instead");
  }
  
  public Blob getBlob(String columnLabel)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type. Use getBytes() instead");
  }
  
  public Clob getClob(int columnIndex)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type. Use getString() instead");
  }
  
  public Clob getClob(String columnLabel)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type. Use getString() instead");
  }
  
  public NClob getNClob(int columnIndex)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type. Use getString() instead");
  }
  
  public NClob getNClob(String columnLabel)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Unsupported data type. Use getString() instead");
  }
  
  public Array getArray(int columnIndex)
    throws SQLException
  {
    return (Array)getValue(columnIndex, Array.class);
  }
  
  public Array getArray(String columnLabel)
    throws SQLException
  {
    return (Array)getValue(columnLabel, Array.class);
  }
  
  public URL getURL(int columnIndex)
    throws SQLException
  {
    return (URL)getValue(columnIndex, URL.class);
  }
  
  public URL getURL(String columnLabel)
    throws SQLException
  {
    return (URL)getValue(columnLabel, URL.class);
  }
  
  public Object getObject(int columnIndex)
    throws SQLException
  {
    return getValue(columnIndex, Object.class);
  }
  
  public Object getObject(int columnIndex, Map<String, Class<?>> map)
    throws SQLException
  {
    return getValue(columnIndex, Object.class);
  }
  
  public Object getObject(String columnLabel)
    throws SQLException
  {
    return getValue(columnLabel, Object.class);
  }
  
  public Object getObject(String columnLabel, Map<String, Class<?>> map)
    throws SQLException
  {
    return getValue(columnLabel, Object.class);
  }
  
  public boolean rowUpdated()
    throws SQLException
  {
    return false;
  }
  
  public boolean rowInserted()
    throws SQLException
  {
    return false;
  }
  
  public boolean rowDeleted()
    throws SQLException
  {
    return false;
  }
  
  public void updateNull(int columnIndex)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBoolean(int columnIndex, boolean x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateByte(int columnIndex, byte x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateShort(int columnIndex, short x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateInt(int columnIndex, int x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateLong(int columnIndex, long x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateFloat(int columnIndex, float x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateDouble(int columnIndex, double x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBigDecimal(int columnIndex, BigDecimal x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateString(int columnIndex, String x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBytes(int columnIndex, byte[] x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateDate(int columnIndex, Date x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateTime(int columnIndex, Time x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateTimestamp(int columnIndex, Timestamp x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateAsciiStream(int columnIndex, InputStream x, int length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBinaryStream(int columnIndex, InputStream x, int length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateCharacterStream(int columnIndex, Reader x, int length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateObject(int columnIndex, Object x, int scaleOrLength)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateObject(int columnIndex, Object x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNull(String columnLabel)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBoolean(String columnLabel, boolean x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateByte(String columnLabel, byte x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateShort(String columnLabel, short x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateInt(String columnLabel, int x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateLong(String columnLabel, long x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateFloat(String columnLabel, float x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateDouble(String columnLabel, double x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBigDecimal(String columnLabel, BigDecimal x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateString(String columnLabel, String x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBytes(String columnLabel, byte[] x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateDate(String columnLabel, Date x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateTime(String columnLabel, Time x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateTimestamp(String columnLabel, Timestamp x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateAsciiStream(String columnLabel, InputStream x, int length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBinaryStream(String columnLabel, InputStream x, int length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateCharacterStream(String columnLabel, Reader reader, int length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateRef(int columnIndex, Ref x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateRef(String columnLabel, Ref x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateRowId(int columnIndex, RowId x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateRowId(String columnLabel, RowId x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBlob(int columnIndex, Blob x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBlob(String columnLabel, Blob x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateClob(int columnIndex, Clob x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateClob(String columnLabel, Clob x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateArray(int columnIndex, Array x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateArray(String columnLabel, Array x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNString(int columnIndex, String nString)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNString(String columnLabel, String nString)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNClob(int columnIndex, NClob nClob)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNClob(String columnLabel, NClob nClob)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateSQLXML(int columnIndex, SQLXML xmlObject)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateSQLXML(String columnLabel, SQLXML xmlObject)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNCharacterStream(int columnIndex, Reader x, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNCharacterStream(String columnLabel, Reader reader, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateAsciiStream(int columnIndex, InputStream x, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBinaryStream(int columnIndex, InputStream x, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateCharacterStream(int columnIndex, Reader x, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateAsciiStream(String columnLabel, InputStream x, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBinaryStream(String columnLabel, InputStream x, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateCharacterStream(String columnLabel, Reader reader, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBlob(int columnIndex, InputStream inputStream, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBlob(String columnLabel, InputStream inputStream, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateClob(int columnIndex, Reader reader, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateClob(String columnLabel, Reader reader, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNClob(int columnIndex, Reader reader, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNClob(String columnLabel, Reader reader, long length)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNCharacterStream(int columnIndex, Reader x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNCharacterStream(String columnLabel, Reader reader)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateAsciiStream(int columnIndex, InputStream x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBinaryStream(int columnIndex, InputStream x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateCharacterStream(int columnIndex, Reader x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateAsciiStream(String columnLabel, InputStream x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBinaryStream(String columnLabel, InputStream x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateCharacterStream(String columnLabel, Reader reader)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBlob(int columnIndex, InputStream inputStream)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateBlob(String columnLabel, InputStream inputStream)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateClob(int columnIndex, Reader reader)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateClob(String columnLabel, Reader reader)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNClob(int columnIndex, Reader reader)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateNClob(String columnLabel, Reader reader)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateObject(String columnLabel, Object x, int scaleOrLength)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateObject(String columnLabel, Object x)
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void insertRow()
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void updateRow()
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void deleteRow()
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void refreshRow()
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void cancelRowUpdates()
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void moveToInsertRow()
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public void moveToCurrentRow()
    throws SQLException
  {
    throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
  }
  
  public String toString()
  {
    return this.result == null ? "null" : this.result.toString();
  }
}

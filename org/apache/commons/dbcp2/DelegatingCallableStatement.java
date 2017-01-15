package org.apache.commons.dbcp2;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class DelegatingCallableStatement
  extends DelegatingPreparedStatement
  implements CallableStatement
{
  public DelegatingCallableStatement(DelegatingConnection<?> c, CallableStatement s)
  {
    super(c, s);
  }
  
  public void registerOutParameter(int parameterIndex, int sqlType)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).registerOutParameter(parameterIndex, sqlType);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void registerOutParameter(int parameterIndex, int sqlType, int scale)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).registerOutParameter(parameterIndex, sqlType, scale);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public boolean wasNull()
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).wasNull();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public String getString(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getString(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public boolean getBoolean(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBoolean(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public byte getByte(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getByte(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public short getShort(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getShort(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getInt(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getInt(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public long getLong(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getLong(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0L;
  }
  
  public float getFloat(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getFloat(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0.0F;
  }
  
  public double getDouble(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getDouble(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0.0D;
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(int parameterIndex, int scale)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBigDecimal(parameterIndex, scale);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public byte[] getBytes(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBytes(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Date getDate(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getDate(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Time getTime(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getTime(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Timestamp getTimestamp(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getTimestamp(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Object getObject(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getObject(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public BigDecimal getBigDecimal(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBigDecimal(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Object getObject(int i, Map<String, Class<?>> map)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getObject(i, map);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Ref getRef(int i)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getRef(i);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Blob getBlob(int i)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBlob(i);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Clob getClob(int i)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getClob(i);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Array getArray(int i)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getArray(i);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Date getDate(int parameterIndex, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getDate(parameterIndex, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Time getTime(int parameterIndex, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getTime(parameterIndex, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Timestamp getTimestamp(int parameterIndex, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getTimestamp(parameterIndex, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void registerOutParameter(int paramIndex, int sqlType, String typeName)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).registerOutParameter(paramIndex, sqlType, typeName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void registerOutParameter(String parameterName, int sqlType)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).registerOutParameter(parameterName, sqlType);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void registerOutParameter(String parameterName, int sqlType, int scale)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).registerOutParameter(parameterName, sqlType, scale);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void registerOutParameter(String parameterName, int sqlType, String typeName)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).registerOutParameter(parameterName, sqlType, typeName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public URL getURL(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getURL(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void setURL(String parameterName, URL val)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setURL(parameterName, val);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNull(String parameterName, int sqlType)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setNull(parameterName, sqlType);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBoolean(String parameterName, boolean x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBoolean(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setByte(String parameterName, byte x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setByte(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setShort(String parameterName, short x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setShort(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setInt(String parameterName, int x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setInt(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setLong(String parameterName, long x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setLong(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setFloat(String parameterName, float x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setFloat(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setDouble(String parameterName, double x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setDouble(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBigDecimal(String parameterName, BigDecimal x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBigDecimal(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setString(String parameterName, String x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setString(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBytes(String parameterName, byte[] x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBytes(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setDate(String parameterName, Date x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setDate(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTime(String parameterName, Time x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setTime(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTimestamp(String parameterName, Timestamp x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setTimestamp(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setAsciiStream(String parameterName, InputStream x, int length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setAsciiStream(parameterName, x, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBinaryStream(String parameterName, InputStream x, int length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBinaryStream(parameterName, x, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setObject(String parameterName, Object x, int targetSqlType, int scale)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setObject(parameterName, x, targetSqlType, scale);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setObject(String parameterName, Object x, int targetSqlType)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setObject(parameterName, x, targetSqlType);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setObject(String parameterName, Object x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setObject(parameterName, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setCharacterStream(String parameterName, Reader reader, int length)
    throws SQLException
  {
    checkOpen();((CallableStatement)getDelegate()).setCharacterStream(parameterName, reader, length);
  }
  
  public void setDate(String parameterName, Date x, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setDate(parameterName, x, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTime(String parameterName, Time x, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setTime(parameterName, x, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setTimestamp(parameterName, x, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNull(String parameterName, int sqlType, String typeName)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setNull(parameterName, sqlType, typeName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public String getString(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getString(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public boolean getBoolean(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBoolean(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public byte getByte(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getByte(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public short getShort(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getShort(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getInt(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getInt(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public long getLong(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getLong(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0L;
  }
  
  public float getFloat(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getFloat(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0.0F;
  }
  
  public double getDouble(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getDouble(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0.0D;
  }
  
  public byte[] getBytes(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBytes(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Date getDate(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getDate(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Time getTime(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getTime(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Timestamp getTimestamp(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getTimestamp(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Object getObject(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getObject(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public BigDecimal getBigDecimal(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBigDecimal(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Object getObject(String parameterName, Map<String, Class<?>> map)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getObject(parameterName, map);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Ref getRef(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getRef(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Blob getBlob(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getBlob(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Clob getClob(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getClob(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Array getArray(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getArray(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Date getDate(String parameterName, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getDate(parameterName, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Time getTime(String parameterName, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getTime(parameterName, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Timestamp getTimestamp(String parameterName, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getTimestamp(parameterName, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public URL getURL(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getURL(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public RowId getRowId(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getRowId(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public RowId getRowId(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getRowId(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void setRowId(String parameterName, RowId value)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setRowId(parameterName, value);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNString(String parameterName, String value)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setNString(parameterName, value);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNCharacterStream(String parameterName, Reader reader, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setNCharacterStream(parameterName, reader, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNClob(String parameterName, NClob value)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setNClob(parameterName, value);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setClob(String parameterName, Reader reader, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setClob(parameterName, reader, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBlob(String parameterName, InputStream inputStream, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBlob(parameterName, inputStream, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNClob(String parameterName, Reader reader, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setNClob(parameterName, reader, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public NClob getNClob(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getNClob(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public NClob getNClob(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getNClob(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void setSQLXML(String parameterName, SQLXML value)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setSQLXML(parameterName, value);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public SQLXML getSQLXML(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getSQLXML(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public SQLXML getSQLXML(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getSQLXML(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public String getNString(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getNString(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public String getNString(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getNString(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Reader getNCharacterStream(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getNCharacterStream(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Reader getNCharacterStream(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getNCharacterStream(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Reader getCharacterStream(int parameterIndex)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getCharacterStream(parameterIndex);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Reader getCharacterStream(String parameterName)
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((CallableStatement)getDelegate()).getCharacterStream(parameterName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void setBlob(String parameterName, Blob blob)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBlob(parameterName, blob);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setClob(String parameterName, Clob clob)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setClob(parameterName, clob);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setAsciiStream(String parameterName, InputStream inputStream, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setAsciiStream(parameterName, inputStream, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBinaryStream(String parameterName, InputStream inputStream, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBinaryStream(parameterName, inputStream, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setCharacterStream(String parameterName, Reader reader, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setCharacterStream(parameterName, reader, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setAsciiStream(String parameterName, InputStream inputStream)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setAsciiStream(parameterName, inputStream);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBinaryStream(String parameterName, InputStream inputStream)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBinaryStream(parameterName, inputStream);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setCharacterStream(String parameterName, Reader reader)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setCharacterStream(parameterName, reader);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNCharacterStream(String parameterName, Reader reader)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setNCharacterStream(parameterName, reader);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setClob(String parameterName, Reader reader)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setClob(parameterName, reader);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBlob(String parameterName, InputStream inputStream)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setBlob(parameterName, inputStream);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNClob(String parameterName, Reader reader)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((CallableStatement)getDelegate()).setNClob(parameterName, reader);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public <T> T getObject(int parameterIndex, Class<T> type)
    throws SQLException
  {
    checkOpen();
    try
    {
      return (T)((CallableStatement)getDelegate()).getObject(parameterIndex, type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public <T> T getObject(String parameterName, Class<T> type)
    throws SQLException
  {
    checkOpen();
    try
    {
      return (T)((CallableStatement)getDelegate()).getObject(parameterName, type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
}

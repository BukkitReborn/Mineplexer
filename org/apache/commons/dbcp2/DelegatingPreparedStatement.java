package org.apache.commons.dbcp2;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class DelegatingPreparedStatement
  extends DelegatingStatement
  implements PreparedStatement
{
  public DelegatingPreparedStatement(DelegatingConnection<?> c, PreparedStatement s)
  {
    super(c, s);
  }
  
  public ResultSet executeQuery()
    throws SQLException
  {
    checkOpen();
    if (getConnectionInternal() != null) {
      getConnectionInternal().setLastUsed();
    }
    try
    {
      return DelegatingResultSet.wrapResultSet(this, ((PreparedStatement)getDelegate()).executeQuery());
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public int executeUpdate()
    throws SQLException
  {
    checkOpen();
    if (getConnectionInternal() != null) {
      getConnectionInternal().setLastUsed();
    }
    try
    {
      return ((PreparedStatement)getDelegate()).executeUpdate();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public void setNull(int parameterIndex, int sqlType)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setNull(parameterIndex, sqlType);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBoolean(int parameterIndex, boolean x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBoolean(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setByte(int parameterIndex, byte x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setByte(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setShort(int parameterIndex, short x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setShort(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setInt(int parameterIndex, int x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setInt(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setLong(int parameterIndex, long x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setLong(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setFloat(int parameterIndex, float x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setFloat(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setDouble(int parameterIndex, double x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setDouble(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBigDecimal(int parameterIndex, BigDecimal x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBigDecimal(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setString(int parameterIndex, String x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setString(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBytes(int parameterIndex, byte[] x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBytes(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setDate(int parameterIndex, Date x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setDate(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTime(int parameterIndex, Time x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setTime(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTimestamp(int parameterIndex, Timestamp x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setTimestamp(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setAsciiStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setAsciiStream(parameterIndex, x, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  @Deprecated
  public void setUnicodeStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setUnicodeStream(parameterIndex, x, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBinaryStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBinaryStream(parameterIndex, x, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void clearParameters()
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).clearParameters();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setObject(int parameterIndex, Object x, int targetSqlType, int scale)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setObject(parameterIndex, x, targetSqlType, scale);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setObject(int parameterIndex, Object x, int targetSqlType)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setObject(parameterIndex, x, targetSqlType);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setObject(int parameterIndex, Object x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setObject(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public boolean execute()
    throws SQLException
  {
    checkOpen();
    if (getConnectionInternal() != null) {
      getConnectionInternal().setLastUsed();
    }
    try
    {
      return ((PreparedStatement)getDelegate()).execute();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public void addBatch()
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).addBatch();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setCharacterStream(int parameterIndex, Reader reader, int length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setCharacterStream(parameterIndex, reader, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setRef(int i, Ref x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setRef(i, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBlob(int i, Blob x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBlob(i, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setClob(int i, Clob x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setClob(i, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setArray(int i, Array x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setArray(i, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((PreparedStatement)getDelegate()).getMetaData();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public void setDate(int parameterIndex, Date x, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setDate(parameterIndex, x, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTime(int parameterIndex, Time x, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setTime(parameterIndex, x, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setTimestamp(parameterIndex, x, cal);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNull(int paramIndex, int sqlType, String typeName)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setNull(paramIndex, sqlType, typeName);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public String toString()
  {
    Statement statement = getDelegate();
    return statement == null ? "NULL" : statement.toString();
  }
  
  public void setURL(int parameterIndex, URL x)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setURL(parameterIndex, x);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public ParameterMetaData getParameterMetaData()
    throws SQLException
  {
    checkOpen();
    try
    {
      return ((PreparedStatement)getDelegate()).getParameterMetaData();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public void setRowId(int parameterIndex, RowId value)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setRowId(parameterIndex, value);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNString(int parameterIndex, String value)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setNString(parameterIndex, value);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNCharacterStream(int parameterIndex, Reader value, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setNCharacterStream(parameterIndex, value, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNClob(int parameterIndex, NClob value)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setNClob(parameterIndex, value);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setClob(int parameterIndex, Reader reader, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setClob(parameterIndex, reader, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBlob(int parameterIndex, InputStream inputStream, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBlob(parameterIndex, inputStream, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNClob(int parameterIndex, Reader reader, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setNClob(parameterIndex, reader, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setSQLXML(int parameterIndex, SQLXML value)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setSQLXML(parameterIndex, value);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setAsciiStream(int parameterIndex, InputStream inputStream, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setAsciiStream(parameterIndex, inputStream, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBinaryStream(int parameterIndex, InputStream inputStream, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBinaryStream(parameterIndex, inputStream, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setCharacterStream(int parameterIndex, Reader reader, long length)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setCharacterStream(parameterIndex, reader, length);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setAsciiStream(int parameterIndex, InputStream inputStream)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setAsciiStream(parameterIndex, inputStream);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBinaryStream(int parameterIndex, InputStream inputStream)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBinaryStream(parameterIndex, inputStream);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setCharacterStream(int parameterIndex, Reader reader)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setCharacterStream(parameterIndex, reader);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNCharacterStream(int parameterIndex, Reader reader)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setNCharacterStream(parameterIndex, reader);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setClob(int parameterIndex, Reader reader)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setClob(parameterIndex, reader);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setBlob(int parameterIndex, InputStream inputStream)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setBlob(parameterIndex, inputStream);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNClob(int parameterIndex, Reader reader)
    throws SQLException
  {
    checkOpen();
    try
    {
      ((PreparedStatement)getDelegate()).setNClob(parameterIndex, reader);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
}

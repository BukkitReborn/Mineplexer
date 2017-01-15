package org.jooq.impl;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
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
import org.jooq.tools.jdbc.DefaultStatement;

class SettingsEnabledPreparedStatement
  extends DefaultStatement
  implements PreparedStatement
{
  private final Connection connection;
  private final MethodType methodType;
  private final String sql;
  private int autoGeneratedKeys;
  private int[] columnIndexes;
  private String[] columnNames;
  
  private SettingsEnabledPreparedStatement(Connection connection, String sql, MethodType type, Statement statement)
  {
    super(statement);
    
    this.connection = connection;
    this.methodType = type;
    this.sql = sql;
  }
  
  private SettingsEnabledPreparedStatement(Connection connection, String sql, MethodType type)
    throws SQLException
  {
    this(connection, sql, type, connection.createStatement());
  }
  
  SettingsEnabledPreparedStatement(Connection connection)
    throws SQLException
  {
    this(connection, null, MethodType.BATCH);
  }
  
  SettingsEnabledPreparedStatement(Connection connection, String sql)
    throws SQLException
  {
    this(connection, sql, MethodType.SQL);
  }
  
  SettingsEnabledPreparedStatement(Connection connection, String sql, int resultSetType, int resultSetConcurrency)
    throws SQLException
  {
    this(connection, sql, MethodType.SQL_RST_RSC, connection.createStatement(resultSetType, resultSetConcurrency));
  }
  
  SettingsEnabledPreparedStatement(Connection connection, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
    throws SQLException
  {
    this(connection, sql, MethodType.SQL_RST_RSC_RSH, connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
  }
  
  SettingsEnabledPreparedStatement(Connection connection, String sql, int autoGeneratedKeys)
    throws SQLException
  {
    this(connection, sql, MethodType.SQL_AGK);
    
    this.autoGeneratedKeys = autoGeneratedKeys;
  }
  
  SettingsEnabledPreparedStatement(Connection connection, String sql, int[] columnIndexes)
    throws SQLException
  {
    this(connection, sql, MethodType.SQL_CI);
    
    this.columnIndexes = columnIndexes;
  }
  
  SettingsEnabledPreparedStatement(Connection connection, String sql, String[] columnNames)
    throws SQLException
  {
    this(connection, sql, MethodType.SQL_CN);
    
    this.columnNames = columnNames;
  }
  
  private static enum MethodType
  {
    SQL,  SQL_RST_RSC,  SQL_RST_RSC_RSH,  SQL_AGK,  SQL_CI,  SQL_CN,  BATCH;
    
    private MethodType() {}
  }
  
  public final Connection getConnection()
    throws SQLException
  {
    return this.connection;
  }
  
  public final ResultSet executeQuery()
    throws SQLException
  {
    return getDelegate().executeQuery(this.sql);
  }
  
  public final int executeUpdate()
    throws SQLException
  {
    switch (this.methodType)
    {
    case SQL_AGK: 
      return getDelegate().executeUpdate(this.sql, this.autoGeneratedKeys);
    case SQL_CI: 
      return getDelegate().executeUpdate(this.sql, this.columnIndexes);
    case SQL_CN: 
      return getDelegate().executeUpdate(this.sql, this.columnNames);
    }
    return getDelegate().executeUpdate(this.sql);
  }
  
  public final boolean execute()
    throws SQLException
  {
    switch (this.methodType)
    {
    case SQL_AGK: 
      return getDelegate().execute(this.sql, this.autoGeneratedKeys);
    case SQL_CI: 
      return getDelegate().execute(this.sql, this.columnIndexes);
    case SQL_CN: 
      return getDelegate().execute(this.sql, this.columnNames);
    }
    return getDelegate().execute(this.sql);
  }
  
  public final void addBatch()
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot batch execute statements on PreparedStatementProxy");
  }
  
  public final ResultSetMetaData getMetaData()
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot fetch ResultSetMetaData early on PreparedStatementProxy");
  }
  
  public final void clearParameters()
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot operate on bind values on a PreparedStatementProxy");
  }
  
  public final ParameterMetaData getParameterMetaData()
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot operate on bind values on a PreparedStatementProxy");
  }
  
  public final void setNull(int parameterIndex, int sqlType)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBoolean(int parameterIndex, boolean x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setByte(int parameterIndex, byte x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setShort(int parameterIndex, short x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setInt(int parameterIndex, int x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setLong(int parameterIndex, long x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setFloat(int parameterIndex, float x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setDouble(int parameterIndex, double x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBigDecimal(int parameterIndex, BigDecimal x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setString(int parameterIndex, String x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBytes(int parameterIndex, byte[] x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setDate(int parameterIndex, Date x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setTime(int parameterIndex, Time x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setTimestamp(int parameterIndex, Timestamp x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setAsciiStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setUnicodeStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBinaryStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setObject(int parameterIndex, Object x, int targetSqlType)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setObject(int parameterIndex, Object x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setCharacterStream(int parameterIndex, Reader reader, int length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setRef(int parameterIndex, Ref x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBlob(int parameterIndex, Blob x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setClob(int parameterIndex, Clob x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setArray(int parameterIndex, Array x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setDate(int parameterIndex, Date x, Calendar cal)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setTime(int parameterIndex, Time x, Calendar cal)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setNull(int parameterIndex, int sqlType, String typeName)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setURL(int parameterIndex, URL x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setRowId(int parameterIndex, RowId x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setNString(int parameterIndex, String value)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setNCharacterStream(int parameterIndex, Reader value, long length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setNClob(int parameterIndex, NClob value)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setClob(int parameterIndex, Reader reader, long length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBlob(int parameterIndex, InputStream inputStream, long length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setNClob(int parameterIndex, Reader reader, long length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setSQLXML(int parameterIndex, SQLXML xmlObject)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setAsciiStream(int parameterIndex, InputStream x, long length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBinaryStream(int parameterIndex, InputStream x, long length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setCharacterStream(int parameterIndex, Reader reader, long length)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setAsciiStream(int parameterIndex, InputStream x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBinaryStream(int parameterIndex, InputStream x)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setCharacterStream(int parameterIndex, Reader reader)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setNCharacterStream(int parameterIndex, Reader value)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setClob(int parameterIndex, Reader reader)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setBlob(int parameterIndex, InputStream inputStream)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
  
  public final void setNClob(int parameterIndex, Reader reader)
    throws SQLException
  {
    throw new UnsupportedOperationException("Cannot set a bind value on a PreparedStatementProxy");
  }
}

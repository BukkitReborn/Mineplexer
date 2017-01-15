package org.jooq.tools.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLXML;
import java.sql.Statement;
import org.jooq.SQLDialect;

public class JDBCUtils
{
  public static final SQLDialect dialect(Connection connection)
  {
    SQLDialect result = SQLDialect.SQL99;
    if (connection != null) {
      try
      {
        DatabaseMetaData m = connection.getMetaData();
        
        String url = m.getURL();
        result = dialect(url);
      }
      catch (SQLException localSQLException) {}
    }
    if (result == SQLDialect.SQL99) {}
    return result;
  }
  
  public static final SQLDialect dialect(String url)
  {
    if (url == null) {
      return SQLDialect.SQL99;
    }
    if (url.startsWith("jdbc:cubrid:")) {
      return SQLDialect.CUBRID;
    }
    if (url.startsWith("jdbc:derby:")) {
      return SQLDialect.DERBY;
    }
    if (url.startsWith("jdbc:firebirdsql:")) {
      return SQLDialect.FIREBIRD;
    }
    if (url.startsWith("jdbc:h2:")) {
      return SQLDialect.H2;
    }
    if (url.startsWith("jdbc:hsqldb:")) {
      return SQLDialect.HSQLDB;
    }
    if (url.startsWith("jdbc:mariadb:")) {
      return SQLDialect.MARIADB;
    }
    if ((url.startsWith("jdbc:mysql:")) || 
      (url.startsWith("jdbc:google:"))) {
      return SQLDialect.MYSQL;
    }
    if (url.startsWith("jdbc:postgresql:")) {
      return SQLDialect.POSTGRES;
    }
    if (url.startsWith("jdbc:sqlite:")) {
      return SQLDialect.SQLITE;
    }
    return SQLDialect.SQL99;
  }
  
  public static final String driver(String url)
  {
    switch (dialect(url).family())
    {
    case CUBRID: 
      return "cubrid.jdbc.driver.CUBRIDDriver";
    case DERBY: 
      return "org.apache.derby.jdbc.ClientDriver";
    case FIREBIRD: 
      return "org.firebirdsql.jdbc.FBDriver";
    case H2: 
      return "org.h2.Driver";
    case HSQLDB: 
      return "org.hsqldb.jdbcDriver";
    case MARIADB: 
      return "org.mariadb.jdbc.Driver";
    case MYSQL: 
      return "com.mysql.jdbc.Driver";
    case POSTGRES: 
      return "org.postgresql.Driver";
    case SQLITE: 
      return "org.sqlite.JDBC";
    }
    return "java.sql.Driver";
  }
  
  public static final void safeClose(Connection connection)
  {
    if (connection != null) {
      try
      {
        connection.close();
      }
      catch (Exception localException) {}
    }
  }
  
  public static final void safeClose(Statement statement)
  {
    if (statement != null) {
      try
      {
        statement.close();
      }
      catch (Exception localException) {}
    }
  }
  
  public static final void safeClose(ResultSet resultSet)
  {
    if (resultSet != null) {
      try
      {
        resultSet.close();
      }
      catch (Exception localException) {}
    }
  }
  
  public static final void safeClose(ResultSet resultSet, PreparedStatement statement)
  {
    safeClose(resultSet);
    safeClose(statement);
  }
  
  public static final void safeFree(Blob blob)
  {
    if (blob != null) {
      try
      {
        blob.free();
      }
      catch (Exception localException) {}catch (AbstractMethodError localAbstractMethodError) {}
    }
  }
  
  public static final void safeFree(Clob clob)
  {
    if (clob != null) {
      try
      {
        clob.free();
      }
      catch (Exception localException) {}catch (AbstractMethodError localAbstractMethodError) {}
    }
  }
  
  public static final void safeFree(SQLXML xml)
  {
    if (xml != null) {
      try
      {
        xml.free();
      }
      catch (Exception localException) {}catch (AbstractMethodError localAbstractMethodError) {}
    }
  }
  
  public static final void safeFree(Array array)
  {
    if (array != null) {
      try
      {
        array.free();
      }
      catch (Exception localException) {}catch (AbstractMethodError localAbstractMethodError) {}
    }
  }
  
  public static final <T> T wasNull(SQLInput stream, T value)
    throws SQLException
  {
    return (value == null) || (stream.wasNull()) ? null : value;
  }
  
  public static final <T extends Number> T wasNull(SQLInput stream, T value)
    throws SQLException
  {
    return (value == null) || ((value.intValue() == 0) && (stream.wasNull())) ? null : value;
  }
  
  public static final Boolean wasNull(SQLInput stream, Boolean value)
    throws SQLException
  {
    return (value == null) || ((!value.booleanValue()) && (stream.wasNull())) ? null : value;
  }
  
  public static final <T> T wasNull(ResultSet rs, T value)
    throws SQLException
  {
    return (value == null) || (rs.wasNull()) ? null : value;
  }
  
  public static final <T extends Number> T wasNull(ResultSet rs, T value)
    throws SQLException
  {
    return (value == null) || ((value.intValue() == 0) && (rs.wasNull())) ? null : value;
  }
  
  public static final Boolean wasNull(ResultSet rs, Boolean value)
    throws SQLException
  {
    return (value == null) || ((!value.booleanValue()) && (rs.wasNull())) ? null : value;
  }
  
  public static final <T> T wasNull(CallableStatement statement, T value)
    throws SQLException
  {
    return (value == null) || (statement.wasNull()) ? null : value;
  }
  
  public static final <T extends Number> T wasNull(CallableStatement statement, T value)
    throws SQLException
  {
    return (value == null) || ((value.intValue() == 0) && (statement.wasNull())) ? null : value;
  }
  
  public static final Boolean wasNull(CallableStatement statement, Boolean value)
    throws SQLException
  {
    return (value == null) || ((!value.booleanValue()) && (statement.wasNull())) ? null : value;
  }
}

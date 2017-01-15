package org.jooq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

public abstract interface ExecuteContext
  extends Scope
{
  public abstract Connection connection();
  
  public abstract ExecuteType type();
  
  public abstract Query query();
  
  public abstract Query[] batchQueries();
  
  public abstract Routine<?> routine();
  
  public abstract String sql();
  
  public abstract void sql(String paramString);
  
  public abstract String[] batchSQL();
  
  public abstract void connectionProvider(ConnectionProvider paramConnectionProvider);
  
  public abstract PreparedStatement statement();
  
  public abstract void statement(PreparedStatement paramPreparedStatement);
  
  public abstract ResultSet resultSet();
  
  public abstract void resultSet(ResultSet paramResultSet);
  
  public abstract Record record();
  
  public abstract void record(Record paramRecord);
  
  public abstract int rows();
  
  public abstract void rows(int paramInt);
  
  public abstract int[] batchRows();
  
  public abstract Result<?> result();
  
  public abstract void result(Result<?> paramResult);
  
  public abstract RuntimeException exception();
  
  public abstract void exception(RuntimeException paramRuntimeException);
  
  public abstract SQLException sqlException();
  
  public abstract void sqlException(SQLException paramSQLException);
  
  public abstract SQLWarning sqlWarning();
  
  public abstract void sqlWarning(SQLWarning paramSQLWarning);
}

package org.jooq;

import java.io.Serializable;
import java.sql.SQLException;

public abstract interface Binding<T, U>
  extends Serializable
{
  public abstract Converter<T, U> converter();
  
  public abstract void sql(BindingSQLContext<U> paramBindingSQLContext)
    throws SQLException;
  
  public abstract void register(BindingRegisterContext<U> paramBindingRegisterContext)
    throws SQLException;
  
  public abstract void set(BindingSetStatementContext<U> paramBindingSetStatementContext)
    throws SQLException;
  
  public abstract void set(BindingSetSQLOutputContext<U> paramBindingSetSQLOutputContext)
    throws SQLException;
  
  public abstract void get(BindingGetResultSetContext<U> paramBindingGetResultSetContext)
    throws SQLException;
  
  public abstract void get(BindingGetStatementContext<U> paramBindingGetStatementContext)
    throws SQLException;
  
  public abstract void get(BindingGetSQLInputContext<U> paramBindingGetSQLInputContext)
    throws SQLException;
}

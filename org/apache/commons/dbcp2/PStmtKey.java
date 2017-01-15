package org.apache.commons.dbcp2;

public class PStmtKey
{
  private final String _sql;
  private final Integer _resultSetType;
  private final Integer _resultSetConcurrency;
  private final String _catalog;
  private final Integer _autoGeneratedKeys;
  private final PoolingConnection.StatementType _stmtType;
  
  public PStmtKey(String sql)
  {
    this(sql, null, PoolingConnection.StatementType.PREPARED_STATEMENT, null);
  }
  
  public PStmtKey(String sql, String catalog)
  {
    this(sql, catalog, PoolingConnection.StatementType.PREPARED_STATEMENT, null);
  }
  
  public PStmtKey(String sql, String catalog, int autoGeneratedKeys)
  {
    this(sql, catalog, PoolingConnection.StatementType.PREPARED_STATEMENT, Integer.valueOf(autoGeneratedKeys));
  }
  
  public PStmtKey(String sql, String catalog, PoolingConnection.StatementType stmtType, Integer autoGeneratedKeys)
  {
    this._sql = sql;
    this._catalog = catalog;
    this._stmtType = stmtType;
    this._autoGeneratedKeys = autoGeneratedKeys;
    this._resultSetType = null;
    this._resultSetConcurrency = null;
  }
  
  public PStmtKey(String sql, int resultSetType, int resultSetConcurrency)
  {
    this(sql, null, resultSetType, resultSetConcurrency, PoolingConnection.StatementType.PREPARED_STATEMENT);
  }
  
  public PStmtKey(String sql, String catalog, int resultSetType, int resultSetConcurrency)
  {
    this(sql, catalog, resultSetType, resultSetConcurrency, PoolingConnection.StatementType.PREPARED_STATEMENT);
  }
  
  public PStmtKey(String sql, String catalog, int resultSetType, int resultSetConcurrency, PoolingConnection.StatementType stmtType)
  {
    this._sql = sql;
    this._catalog = catalog;
    this._resultSetType = Integer.valueOf(resultSetType);
    this._resultSetConcurrency = Integer.valueOf(resultSetConcurrency);
    this._stmtType = stmtType;
    this._autoGeneratedKeys = null;
  }
  
  public String getSql()
  {
    return this._sql;
  }
  
  public Integer getResultSetType()
  {
    return this._resultSetType;
  }
  
  public Integer getResultSetConcurrency()
  {
    return this._resultSetConcurrency;
  }
  
  public Integer getAutoGeneratedKeys()
  {
    return this._autoGeneratedKeys;
  }
  
  public String getCatalog()
  {
    return this._catalog;
  }
  
  public PoolingConnection.StatementType getStmtType()
  {
    return this._stmtType;
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
    PStmtKey other = (PStmtKey)obj;
    if (this._catalog == null)
    {
      if (other._catalog != null) {
        return false;
      }
    }
    else if (!this._catalog.equals(other._catalog)) {
      return false;
    }
    if (this._resultSetConcurrency == null)
    {
      if (other._resultSetConcurrency != null) {
        return false;
      }
    }
    else if (!this._resultSetConcurrency.equals(other._resultSetConcurrency)) {
      return false;
    }
    if (this._resultSetType == null)
    {
      if (other._resultSetType != null) {
        return false;
      }
    }
    else if (!this._resultSetType.equals(other._resultSetType)) {
      return false;
    }
    if (this._autoGeneratedKeys == null)
    {
      if (other._autoGeneratedKeys != null) {
        return false;
      }
    }
    else if (!this._autoGeneratedKeys.equals(other._autoGeneratedKeys)) {
      return false;
    }
    if (this._sql == null)
    {
      if (other._sql != null) {
        return false;
      }
    }
    else if (!this._sql.equals(other._sql)) {
      return false;
    }
    if (this._stmtType != other._stmtType) {
      return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this._catalog == null ? 0 : this._catalog.hashCode());
    result = 31 * result + (this._resultSetConcurrency == null ? 0 : this._resultSetConcurrency.hashCode());
    result = 31 * result + (this._resultSetType == null ? 0 : this._resultSetType.hashCode());
    result = 31 * result + (this._sql == null ? 0 : this._sql.hashCode());
    result = 31 * result + (this._autoGeneratedKeys == null ? 0 : this._autoGeneratedKeys.hashCode());
    result = 31 * result + this._stmtType.hashCode();
    return result;
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    buf.append("PStmtKey: sql=");
    buf.append(this._sql);
    buf.append(", catalog=");
    buf.append(this._catalog);
    buf.append(", resultSetType=");
    buf.append(this._resultSetType);
    buf.append(", resultSetConcurrency=");
    buf.append(this._resultSetConcurrency);
    buf.append(", autoGeneratedKeys=");
    buf.append(this._autoGeneratedKeys);
    buf.append(", statmentType=");
    buf.append(this._stmtType);
    return buf.toString();
  }
}
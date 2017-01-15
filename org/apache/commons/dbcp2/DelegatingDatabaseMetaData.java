package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

public class DelegatingDatabaseMetaData
  implements DatabaseMetaData
{
  private final DatabaseMetaData _meta;
  private final DelegatingConnection<?> _conn;
  
  public DelegatingDatabaseMetaData(DelegatingConnection<?> c, DatabaseMetaData m)
  {
    this._conn = c;
    this._meta = m;
  }
  
  public DatabaseMetaData getDelegate()
  {
    return this._meta;
  }
  
  public DatabaseMetaData getInnermostDelegate()
  {
    DatabaseMetaData m = this._meta;
    while ((m != null) && ((m instanceof DelegatingDatabaseMetaData)))
    {
      m = ((DelegatingDatabaseMetaData)m).getDelegate();
      if (this == m) {
        return null;
      }
    }
    return m;
  }
  
  protected void handleException(SQLException e)
    throws SQLException
  {
    if (this._conn != null) {
      this._conn.handleException(e);
    } else {
      throw e;
    }
  }
  
  public boolean allProceduresAreCallable()
    throws SQLException
  {
    try
    {
      return this._meta.allProceduresAreCallable();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean allTablesAreSelectable()
    throws SQLException
  {
    try
    {
      return this._meta.allTablesAreSelectable();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean dataDefinitionCausesTransactionCommit()
    throws SQLException
  {
    try
    {
      return this._meta.dataDefinitionCausesTransactionCommit();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean dataDefinitionIgnoredInTransactions()
    throws SQLException
  {
    try
    {
      return this._meta.dataDefinitionIgnoredInTransactions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean deletesAreDetected(int type)
    throws SQLException
  {
    try
    {
      return this._meta.deletesAreDetected(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean doesMaxRowSizeIncludeBlobs()
    throws SQLException
  {
    try
    {
      return this._meta.doesMaxRowSizeIncludeBlobs();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getBestRowIdentifier(catalog, schema, table, scope, nullable));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public String getCatalogSeparator()
    throws SQLException
  {
    try
    {
      return this._meta.getCatalogSeparator();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public String getCatalogTerm()
    throws SQLException
  {
    try
    {
      return this._meta.getCatalogTerm();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getCatalogs()
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getCatalogs());
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getColumnPrivileges(catalog, schema, table, columnNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public Connection getConnection()
    throws SQLException
  {
    return this._conn;
  }
  
  public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getCrossReference(parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema, foreignTable));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public int getDatabaseMajorVersion()
    throws SQLException
  {
    try
    {
      return this._meta.getDatabaseMajorVersion();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getDatabaseMinorVersion()
    throws SQLException
  {
    try
    {
      return this._meta.getDatabaseMinorVersion();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public String getDatabaseProductName()
    throws SQLException
  {
    try
    {
      return this._meta.getDatabaseProductName();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public String getDatabaseProductVersion()
    throws SQLException
  {
    try
    {
      return this._meta.getDatabaseProductVersion();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public int getDefaultTransactionIsolation()
    throws SQLException
  {
    try
    {
      return this._meta.getDefaultTransactionIsolation();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getDriverMajorVersion()
  {
    return this._meta.getDriverMajorVersion();
  }
  
  public int getDriverMinorVersion()
  {
    return this._meta.getDriverMinorVersion();
  }
  
  public String getDriverName()
    throws SQLException
  {
    try
    {
      return this._meta.getDriverName();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public String getDriverVersion()
    throws SQLException
  {
    try
    {
      return this._meta.getDriverVersion();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getExportedKeys(String catalog, String schema, String table)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getExportedKeys(catalog, schema, table));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public String getExtraNameCharacters()
    throws SQLException
  {
    try
    {
      return this._meta.getExtraNameCharacters();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public String getIdentifierQuoteString()
    throws SQLException
  {
    try
    {
      return this._meta.getIdentifierQuoteString();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getImportedKeys(String catalog, String schema, String table)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getImportedKeys(catalog, schema, table));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getIndexInfo(catalog, schema, table, unique, approximate));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public int getJDBCMajorVersion()
    throws SQLException
  {
    try
    {
      return this._meta.getJDBCMajorVersion();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getJDBCMinorVersion()
    throws SQLException
  {
    try
    {
      return this._meta.getJDBCMinorVersion();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxBinaryLiteralLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxBinaryLiteralLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxCatalogNameLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxCatalogNameLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxCharLiteralLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxCharLiteralLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxColumnNameLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxColumnNameLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxColumnsInGroupBy()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxColumnsInGroupBy();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxColumnsInIndex()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxColumnsInIndex();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxColumnsInOrderBy()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxColumnsInOrderBy();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxColumnsInSelect()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxColumnsInSelect();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxColumnsInTable()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxColumnsInTable();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxConnections()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxConnections();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxCursorNameLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxCursorNameLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxIndexLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxIndexLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxProcedureNameLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxProcedureNameLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxRowSize()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxRowSize();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxSchemaNameLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxSchemaNameLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxStatementLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxStatementLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxStatements()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxStatements();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxTableNameLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxTableNameLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxTablesInSelect()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxTablesInSelect();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxUserNameLength()
    throws SQLException
  {
    try
    {
      return this._meta.getMaxUserNameLength();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public String getNumericFunctions()
    throws SQLException
  {
    try
    {
      return this._meta.getNumericFunctions();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getPrimaryKeys(String catalog, String schema, String table)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getPrimaryKeys(catalog, schema, table));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public String getProcedureTerm()
    throws SQLException
  {
    try
    {
      return this._meta.getProcedureTerm();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getProcedures(catalog, schemaPattern, procedureNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public int getResultSetHoldability()
    throws SQLException
  {
    try
    {
      return this._meta.getResultSetHoldability();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public String getSQLKeywords()
    throws SQLException
  {
    try
    {
      return this._meta.getSQLKeywords();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public int getSQLStateType()
    throws SQLException
  {
    try
    {
      return this._meta.getSQLStateType();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public String getSchemaTerm()
    throws SQLException
  {
    try
    {
      return this._meta.getSchemaTerm();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getSchemas()
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getSchemas());
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public String getSearchStringEscape()
    throws SQLException
  {
    try
    {
      return this._meta.getSearchStringEscape();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public String getStringFunctions()
    throws SQLException
  {
    try
    {
      return this._meta.getStringFunctions();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getSuperTables(catalog, schemaPattern, tableNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getSuperTypes(catalog, schemaPattern, typeNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public String getSystemFunctions()
    throws SQLException
  {
    try
    {
      return this._meta.getSystemFunctions();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getTablePrivileges(catalog, schemaPattern, tableNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getTableTypes()
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getTableTypes());
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getTables(catalog, schemaPattern, tableNamePattern, types));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public String getTimeDateFunctions()
    throws SQLException
  {
    try
    {
      return this._meta.getTimeDateFunctions();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getTypeInfo()
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getTypeInfo());
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getUDTs(catalog, schemaPattern, typeNamePattern, types));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public String getURL()
    throws SQLException
  {
    try
    {
      return this._meta.getURL();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public String getUserName()
    throws SQLException
  {
    try
    {
      return this._meta.getUserName();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getVersionColumns(String catalog, String schema, String table)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getVersionColumns(catalog, schema, table));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public boolean insertsAreDetected(int type)
    throws SQLException
  {
    try
    {
      return this._meta.insertsAreDetected(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean isCatalogAtStart()
    throws SQLException
  {
    try
    {
      return this._meta.isCatalogAtStart();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean isReadOnly()
    throws SQLException
  {
    try
    {
      return this._meta.isReadOnly();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean locatorsUpdateCopy()
    throws SQLException
  {
    try
    {
      return this._meta.locatorsUpdateCopy();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean nullPlusNonNullIsNull()
    throws SQLException
  {
    try
    {
      return this._meta.nullPlusNonNullIsNull();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean nullsAreSortedAtEnd()
    throws SQLException
  {
    try
    {
      return this._meta.nullsAreSortedAtEnd();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean nullsAreSortedAtStart()
    throws SQLException
  {
    try
    {
      return this._meta.nullsAreSortedAtStart();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean nullsAreSortedHigh()
    throws SQLException
  {
    try
    {
      return this._meta.nullsAreSortedHigh();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean nullsAreSortedLow()
    throws SQLException
  {
    try
    {
      return this._meta.nullsAreSortedLow();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean othersDeletesAreVisible(int type)
    throws SQLException
  {
    try
    {
      return this._meta.othersDeletesAreVisible(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean othersInsertsAreVisible(int type)
    throws SQLException
  {
    try
    {
      return this._meta.othersInsertsAreVisible(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean othersUpdatesAreVisible(int type)
    throws SQLException
  {
    try
    {
      return this._meta.othersUpdatesAreVisible(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean ownDeletesAreVisible(int type)
    throws SQLException
  {
    try
    {
      return this._meta.ownDeletesAreVisible(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean ownInsertsAreVisible(int type)
    throws SQLException
  {
    try
    {
      return this._meta.ownInsertsAreVisible(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean ownUpdatesAreVisible(int type)
    throws SQLException
  {
    try
    {
      return this._meta.ownUpdatesAreVisible(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean storesLowerCaseIdentifiers()
    throws SQLException
  {
    try
    {
      return this._meta.storesLowerCaseIdentifiers();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean storesLowerCaseQuotedIdentifiers()
    throws SQLException
  {
    try
    {
      return this._meta.storesLowerCaseQuotedIdentifiers();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean storesMixedCaseIdentifiers()
    throws SQLException
  {
    try
    {
      return this._meta.storesMixedCaseIdentifiers();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean storesMixedCaseQuotedIdentifiers()
    throws SQLException
  {
    try
    {
      return this._meta.storesMixedCaseQuotedIdentifiers();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean storesUpperCaseIdentifiers()
    throws SQLException
  {
    try
    {
      return this._meta.storesUpperCaseIdentifiers();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean storesUpperCaseQuotedIdentifiers()
    throws SQLException
  {
    try
    {
      return this._meta.storesUpperCaseQuotedIdentifiers();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsANSI92EntryLevelSQL()
    throws SQLException
  {
    try
    {
      return this._meta.supportsANSI92EntryLevelSQL();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsANSI92FullSQL()
    throws SQLException
  {
    try
    {
      return this._meta.supportsANSI92FullSQL();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsANSI92IntermediateSQL()
    throws SQLException
  {
    try
    {
      return this._meta.supportsANSI92IntermediateSQL();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsAlterTableWithAddColumn()
    throws SQLException
  {
    try
    {
      return this._meta.supportsAlterTableWithAddColumn();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsAlterTableWithDropColumn()
    throws SQLException
  {
    try
    {
      return this._meta.supportsAlterTableWithDropColumn();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsBatchUpdates()
    throws SQLException
  {
    try
    {
      return this._meta.supportsBatchUpdates();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsCatalogsInDataManipulation()
    throws SQLException
  {
    try
    {
      return this._meta.supportsCatalogsInDataManipulation();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsCatalogsInIndexDefinitions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsCatalogsInIndexDefinitions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsCatalogsInPrivilegeDefinitions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsCatalogsInPrivilegeDefinitions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsCatalogsInProcedureCalls()
    throws SQLException
  {
    try
    {
      return this._meta.supportsCatalogsInProcedureCalls();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsCatalogsInTableDefinitions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsCatalogsInTableDefinitions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsColumnAliasing()
    throws SQLException
  {
    try
    {
      return this._meta.supportsColumnAliasing();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsConvert()
    throws SQLException
  {
    try
    {
      return this._meta.supportsConvert();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsConvert(int fromType, int toType)
    throws SQLException
  {
    try
    {
      return this._meta.supportsConvert(fromType, toType);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsCoreSQLGrammar()
    throws SQLException
  {
    try
    {
      return this._meta.supportsCoreSQLGrammar();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsCorrelatedSubqueries()
    throws SQLException
  {
    try
    {
      return this._meta.supportsCorrelatedSubqueries();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsDataDefinitionAndDataManipulationTransactions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsDataDefinitionAndDataManipulationTransactions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsDataManipulationTransactionsOnly()
    throws SQLException
  {
    try
    {
      return this._meta.supportsDataManipulationTransactionsOnly();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsDifferentTableCorrelationNames()
    throws SQLException
  {
    try
    {
      return this._meta.supportsDifferentTableCorrelationNames();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsExpressionsInOrderBy()
    throws SQLException
  {
    try
    {
      return this._meta.supportsExpressionsInOrderBy();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsExtendedSQLGrammar()
    throws SQLException
  {
    try
    {
      return this._meta.supportsExtendedSQLGrammar();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsFullOuterJoins()
    throws SQLException
  {
    try
    {
      return this._meta.supportsFullOuterJoins();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsGetGeneratedKeys()
    throws SQLException
  {
    try
    {
      return this._meta.supportsGetGeneratedKeys();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsGroupBy()
    throws SQLException
  {
    try
    {
      return this._meta.supportsGroupBy();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsGroupByBeyondSelect()
    throws SQLException
  {
    try
    {
      return this._meta.supportsGroupByBeyondSelect();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsGroupByUnrelated()
    throws SQLException
  {
    try
    {
      return this._meta.supportsGroupByUnrelated();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsIntegrityEnhancementFacility()
    throws SQLException
  {
    try
    {
      return this._meta.supportsIntegrityEnhancementFacility();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsLikeEscapeClause()
    throws SQLException
  {
    try
    {
      return this._meta.supportsLikeEscapeClause();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsLimitedOuterJoins()
    throws SQLException
  {
    try
    {
      return this._meta.supportsLimitedOuterJoins();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsMinimumSQLGrammar()
    throws SQLException
  {
    try
    {
      return this._meta.supportsMinimumSQLGrammar();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsMixedCaseIdentifiers()
    throws SQLException
  {
    try
    {
      return this._meta.supportsMixedCaseIdentifiers();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsMixedCaseQuotedIdentifiers()
    throws SQLException
  {
    try
    {
      return this._meta.supportsMixedCaseQuotedIdentifiers();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsMultipleOpenResults()
    throws SQLException
  {
    try
    {
      return this._meta.supportsMultipleOpenResults();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsMultipleResultSets()
    throws SQLException
  {
    try
    {
      return this._meta.supportsMultipleResultSets();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsMultipleTransactions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsMultipleTransactions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsNamedParameters()
    throws SQLException
  {
    try
    {
      return this._meta.supportsNamedParameters();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsNonNullableColumns()
    throws SQLException
  {
    try
    {
      return this._meta.supportsNonNullableColumns();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsOpenCursorsAcrossCommit()
    throws SQLException
  {
    try
    {
      return this._meta.supportsOpenCursorsAcrossCommit();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsOpenCursorsAcrossRollback()
    throws SQLException
  {
    try
    {
      return this._meta.supportsOpenCursorsAcrossRollback();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsOpenStatementsAcrossCommit()
    throws SQLException
  {
    try
    {
      return this._meta.supportsOpenStatementsAcrossCommit();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsOpenStatementsAcrossRollback()
    throws SQLException
  {
    try
    {
      return this._meta.supportsOpenStatementsAcrossRollback();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsOrderByUnrelated()
    throws SQLException
  {
    try
    {
      return this._meta.supportsOrderByUnrelated();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsOuterJoins()
    throws SQLException
  {
    try
    {
      return this._meta.supportsOuterJoins();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsPositionedDelete()
    throws SQLException
  {
    try
    {
      return this._meta.supportsPositionedDelete();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsPositionedUpdate()
    throws SQLException
  {
    try
    {
      return this._meta.supportsPositionedUpdate();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsResultSetConcurrency(int type, int concurrency)
    throws SQLException
  {
    try
    {
      return this._meta.supportsResultSetConcurrency(type, concurrency);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsResultSetHoldability(int holdability)
    throws SQLException
  {
    try
    {
      return this._meta.supportsResultSetHoldability(holdability);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsResultSetType(int type)
    throws SQLException
  {
    try
    {
      return this._meta.supportsResultSetType(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSavepoints()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSavepoints();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSchemasInDataManipulation()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSchemasInDataManipulation();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSchemasInIndexDefinitions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSchemasInIndexDefinitions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSchemasInPrivilegeDefinitions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSchemasInPrivilegeDefinitions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSchemasInProcedureCalls()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSchemasInProcedureCalls();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSchemasInTableDefinitions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSchemasInTableDefinitions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSelectForUpdate()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSelectForUpdate();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsStatementPooling()
    throws SQLException
  {
    try
    {
      return this._meta.supportsStatementPooling();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsStoredProcedures()
    throws SQLException
  {
    try
    {
      return this._meta.supportsStoredProcedures();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSubqueriesInComparisons()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSubqueriesInComparisons();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSubqueriesInExists()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSubqueriesInExists();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSubqueriesInIns()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSubqueriesInIns();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsSubqueriesInQuantifieds()
    throws SQLException
  {
    try
    {
      return this._meta.supportsSubqueriesInQuantifieds();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsTableCorrelationNames()
    throws SQLException
  {
    try
    {
      return this._meta.supportsTableCorrelationNames();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsTransactionIsolationLevel(int level)
    throws SQLException
  {
    try
    {
      return this._meta.supportsTransactionIsolationLevel(level);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsTransactions()
    throws SQLException
  {
    try
    {
      return this._meta.supportsTransactions();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsUnion()
    throws SQLException
  {
    try
    {
      return this._meta.supportsUnion();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsUnionAll()
    throws SQLException
  {
    try
    {
      return this._meta.supportsUnionAll();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean updatesAreDetected(int type)
    throws SQLException
  {
    try
    {
      return this._meta.updatesAreDetected(type);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean usesLocalFilePerTable()
    throws SQLException
  {
    try
    {
      return this._meta.usesLocalFilePerTable();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean usesLocalFiles()
    throws SQLException
  {
    try
    {
      return this._meta.usesLocalFiles();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean isWrapperFor(Class<?> iface)
    throws SQLException
  {
    if (iface.isAssignableFrom(getClass())) {
      return true;
    }
    if (iface.isAssignableFrom(this._meta.getClass())) {
      return true;
    }
    return this._meta.isWrapperFor(iface);
  }
  
  public <T> T unwrap(Class<T> iface)
    throws SQLException
  {
    if (iface.isAssignableFrom(getClass())) {
      return (T)iface.cast(this);
    }
    if (iface.isAssignableFrom(this._meta.getClass())) {
      return (T)iface.cast(this._meta);
    }
    return (T)this._meta.unwrap(iface);
  }
  
  public RowIdLifetime getRowIdLifetime()
    throws SQLException
  {
    try
    {
      return this._meta.getRowIdLifetime();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public ResultSet getSchemas(String catalog, String schemaPattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getSchemas(catalog, schemaPattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public boolean autoCommitFailureClosesAllResultSets()
    throws SQLException
  {
    try
    {
      return this._meta.autoCommitFailureClosesAllResultSets();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean supportsStoredFunctionsUsingCallSyntax()
    throws SQLException
  {
    try
    {
      return this._meta.supportsStoredFunctionsUsingCallSyntax();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public ResultSet getClientInfoProperties()
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getClientInfoProperties());
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getFunctions(catalog, schemaPattern, functionNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this._conn, this._meta.getPseudoColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public boolean generatedKeyAlwaysReturned()
    throws SQLException
  {
    this._conn.checkOpen();
    try
    {
      return this._meta.generatedKeyAlwaysReturned();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
}

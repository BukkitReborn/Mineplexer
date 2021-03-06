package org.jooq.tools.jdbc;

public class MockExecuteContext
{
  private final String[] sql;
  private final Object[][] bindings;
  private final int autoGeneratedKeys;
  private final int[] columnIndexes;
  private final String[] columnNames;
  private final int[] outParameterTypes;
  
  public MockExecuteContext(String[] sql, Object[][] bindings)
  {
    this(sql, bindings, 2, null, null, null);
  }
  
  public MockExecuteContext(String[] sql, Object[][] bindings, int autoGeneratedKeys)
  {
    this(sql, bindings, autoGeneratedKeys, null, null, null);
  }
  
  public MockExecuteContext(String[] sql, Object[][] bindings, int[] columnIndexes)
  {
    this(sql, bindings, 1, columnIndexes, null, null);
  }
  
  public MockExecuteContext(String[] sql, Object[][] bindings, String[] columnNames)
  {
    this(sql, bindings, 1, null, columnNames, null);
  }
  
  MockExecuteContext(String[] sql, Object[][] bindings, int autoGeneratedKeys, int[] columnIndexes, String[] columnNames, int[] outParameterTypes)
  {
    this.sql = sql;
    this.bindings = bindings;
    this.autoGeneratedKeys = autoGeneratedKeys;
    this.columnIndexes = columnIndexes;
    this.columnNames = columnNames;
    this.outParameterTypes = outParameterTypes;
  }
  
  public boolean batch()
  {
    return (batchSingle()) || (batchMultiple());
  }
  
  public boolean batchSingle()
  {
    return this.bindings.length > 1;
  }
  
  public boolean batchMultiple()
  {
    return this.sql.length > 1;
  }
  
  public String[] batchSQL()
  {
    return this.sql;
  }
  
  public Object[][] batchBindings()
  {
    return this.bindings;
  }
  
  public String sql()
  {
    return this.sql[0];
  }
  
  public Object[] bindings()
  {
    return (this.bindings != null) && (this.bindings.length > 0) ? this.bindings[0] : new Object[0];
  }
  
  public int autoGeneratedKeys()
  {
    return this.autoGeneratedKeys;
  }
  
  public int[] columnIndexes()
  {
    return this.columnIndexes;
  }
  
  public String[] columnNames()
  {
    return this.columnNames;
  }
  
  public int[] outParameterTypes()
  {
    return this.outParameterTypes == null ? new int[0] : (int[])this.outParameterTypes.clone();
  }
}

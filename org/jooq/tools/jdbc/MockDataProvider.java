package org.jooq.tools.jdbc;

import java.sql.SQLException;

public abstract interface MockDataProvider
{
  public abstract MockResult[] execute(MockExecuteContext paramMockExecuteContext)
    throws SQLException;
}

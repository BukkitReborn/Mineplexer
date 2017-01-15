package org.jooq.impl;

import java.sql.Statement;
import org.jooq.tools.jdbc.DefaultStatement;

class ProviderEnabledStatement
  extends DefaultStatement
{
  private final ProviderEnabledConnection connection;
  
  ProviderEnabledStatement(ProviderEnabledConnection connection, Statement statement)
  {
    super(statement);
    
    this.connection = connection;
  }
  
  /* Error */
  public final void close()
    throws java.sql.SQLException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 3	org/jooq/impl/ProviderEnabledStatement:getDelegate	()Ljava/sql/Statement;
    //   4: invokeinterface 4 1 0
    //   9: aload_0
    //   10: getfield 2	org/jooq/impl/ProviderEnabledStatement:connection	Lorg/jooq/impl/ProviderEnabledConnection;
    //   13: invokevirtual 5	org/jooq/impl/ProviderEnabledConnection:close	()V
    //   16: goto +13 -> 29
    //   19: astore_1
    //   20: aload_0
    //   21: getfield 2	org/jooq/impl/ProviderEnabledStatement:connection	Lorg/jooq/impl/ProviderEnabledConnection;
    //   24: invokevirtual 5	org/jooq/impl/ProviderEnabledConnection:close	()V
    //   27: aload_1
    //   28: athrow
    //   29: return
    // Line number table:
    //   Java source line #71	-> byte code offset #0
    //   Java source line #74	-> byte code offset #9
    //   Java source line #75	-> byte code offset #16
    //   Java source line #74	-> byte code offset #19
    //   Java source line #76	-> byte code offset #29
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	30	0	this	ProviderEnabledStatement
    //   19	9	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	9	19	finally
  }
}

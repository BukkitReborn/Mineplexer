package org.apache.commons.dbcp2.datasources;

import javax.sql.PooledConnection;

final class PooledConnectionAndInfo
{
  private final PooledConnection pooledConnection;
  private final String password;
  private final String username;
  private final UserPassKey upkey;
  
  PooledConnectionAndInfo(PooledConnection pc, String username, String password)
  {
    this.pooledConnection = pc;
    this.username = username;
    this.password = password;
    this.upkey = new UserPassKey(username, password);
  }
  
  PooledConnection getPooledConnection()
  {
    return this.pooledConnection;
  }
  
  UserPassKey getUserPassKey()
  {
    return this.upkey;
  }
  
  String getPassword()
  {
    return this.password;
  }
  
  String getUsername()
  {
    return this.username;
  }
}

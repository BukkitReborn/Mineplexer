package org.apache.commons.dbcp2.datasources;

import java.io.Serializable;

class PoolKey
  implements Serializable
{
  private static final long serialVersionUID = 2252771047542484533L;
  private final String datasourceName;
  private final String username;
  
  PoolKey(String datasourceName, String username)
  {
    this.datasourceName = datasourceName;
    this.username = username;
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof PoolKey))
    {
      PoolKey pk = (PoolKey)obj;
      return (null == this.datasourceName ? null == pk.datasourceName : this.datasourceName.equals(pk.datasourceName)) && (null == this.username ? null == pk.username : this.username.equals(pk.username));
    }
    return false;
  }
  
  public int hashCode()
  {
    int h = 0;
    if (this.datasourceName != null) {
      h += this.datasourceName.hashCode();
    }
    if (this.username != null) {
      h = 29 * h + this.username.hashCode();
    }
    return h;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer(50);
    sb.append("PoolKey(");
    sb.append(this.username).append(", ").append(this.datasourceName);
    sb.append(')');
    return sb.toString();
  }
}

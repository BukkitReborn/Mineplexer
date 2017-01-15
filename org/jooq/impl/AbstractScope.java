package org.jooq.impl;

import java.util.HashMap;
import java.util.Map;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.conf.Settings;

abstract class AbstractScope
  implements Scope
{
  private final Configuration configuration;
  private final Map<Object, Object> data;
  
  AbstractScope(Configuration configuration)
  {
    this.data = new HashMap();
    if (configuration == null) {
      configuration = new DefaultConfiguration();
    }
    this.configuration = configuration;
  }
  
  AbstractScope(AbstractScope scope)
  {
    this.data = scope.data;
    this.configuration = scope.configuration;
  }
  
  public final Configuration configuration()
  {
    return this.configuration;
  }
  
  public final Settings settings()
  {
    return Utils.settings(configuration());
  }
  
  public final SQLDialect dialect()
  {
    return Utils.configuration(configuration()).dialect();
  }
  
  public final SQLDialect family()
  {
    return dialect().family();
  }
  
  public final Map<Object, Object> data()
  {
    return this.data;
  }
  
  public final Object data(Object key)
  {
    return this.data.get(key);
  }
  
  public final Object data(Object key, Object value)
  {
    return this.data.put(key, value);
  }
}

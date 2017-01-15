package org.jooq;

import java.util.Map;
import org.jooq.conf.Settings;

public abstract interface Scope
{
  public abstract Configuration configuration();
  
  public abstract Settings settings();
  
  public abstract SQLDialect dialect();
  
  public abstract SQLDialect family();
  
  public abstract Map<Object, Object> data();
  
  public abstract Object data(Object paramObject);
  
  public abstract Object data(Object paramObject1, Object paramObject2);
}

package org.apache.http.impl.cookie;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieSpec;

@NotThreadSafe
public abstract class AbstractCookieSpec
  implements CookieSpec
{
  private final Map<String, CookieAttributeHandler> attribHandlerMap;
  
  public AbstractCookieSpec()
  {
    this.attribHandlerMap = new HashMap(10);
  }
  
  public void registerAttribHandler(String name, CookieAttributeHandler handler)
  {
    if (name == null) {
      throw new IllegalArgumentException("Attribute name may not be null");
    }
    if (handler == null) {
      throw new IllegalArgumentException("Attribute handler may not be null");
    }
    this.attribHandlerMap.put(name, handler);
  }
  
  protected CookieAttributeHandler findAttribHandler(String name)
  {
    return (CookieAttributeHandler)this.attribHandlerMap.get(name);
  }
  
  protected CookieAttributeHandler getAttribHandler(String name)
  {
    CookieAttributeHandler handler = findAttribHandler(name);
    if (handler == null) {
      throw new IllegalStateException("Handler not registered for " + name + " attribute.");
    }
    return handler;
  }
  
  protected Collection<CookieAttributeHandler> getAttribHandlers()
  {
    return this.attribHandlerMap.values();
  }
}

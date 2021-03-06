package org.apache.http.impl.client;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;

@Immutable
public class DefaultConnectionKeepAliveStrategy
  implements ConnectionKeepAliveStrategy
{
  public long getKeepAliveDuration(HttpResponse response, HttpContext context)
  {
    if (response == null) {
      throw new IllegalArgumentException("HTTP response may not be null");
    }
    HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));
    while (it.hasNext())
    {
      HeaderElement he = it.nextElement();
      String param = he.getName();
      String value = he.getValue();
      if ((value != null) && (param.equalsIgnoreCase("timeout"))) {
        try
        {
          return Long.parseLong(value) * 1000L;
        }
        catch (NumberFormatException ignore) {}
      }
    }
    return -1L;
  }
}

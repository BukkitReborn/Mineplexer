package redis.clients.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import redis.clients.util.SafeEncoder;

public class ScanParams
{
  private List<byte[]> params = new ArrayList();
  public static final String SCAN_POINTER_START = String.valueOf(0);
  
  public void match(String pattern)
  {
    this.params.add(Protocol.Keyword.MATCH.raw);
    this.params.add(SafeEncoder.encode(pattern));
  }
  
  public void count(int count)
  {
    this.params.add(Protocol.Keyword.COUNT.raw);
    this.params.add(Protocol.toByteArray(count));
  }
  
  public Collection<byte[]> getParams()
  {
    return Collections.unmodifiableCollection(this.params);
  }
}

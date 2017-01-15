package redis.clients.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import redis.clients.util.SafeEncoder;

public class ZParams
{
  public static enum Aggregate
  {
    SUM,  MIN,  MAX;
    
    public final byte[] raw;
    
    private Aggregate()
    {
      this.raw = SafeEncoder.encode(name());
    }
  }
  
  private List<byte[]> params = new ArrayList();
  
  public ZParams weights(int... weights)
  {
    this.params.add(Protocol.Keyword.WEIGHTS.raw);
    for (int weight : weights) {
      this.params.add(Protocol.toByteArray(weight));
    }
    return this;
  }
  
  public Collection<byte[]> getParams()
  {
    return Collections.unmodifiableCollection(this.params);
  }
  
  public ZParams aggregate(Aggregate aggregate)
  {
    this.params.add(Protocol.Keyword.AGGREGATE.raw);
    this.params.add(aggregate.raw);
    return this;
  }
}

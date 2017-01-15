package redis.clients.jedis;

import java.net.URI;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.util.Pool;

public class JedisPool
  extends Pool<Jedis>
{
  public JedisPool(GenericObjectPoolConfig poolConfig, String host)
  {
    this(poolConfig, host, 6379, 2000, null, 0, null);
  }
  
  public JedisPool(String host, int port)
  {
    this(new GenericObjectPoolConfig(), host, port, 2000, null, 0, null);
  }
  
  public JedisPool(String host)
  {
    URI uri = URI.create(host);
    if ((uri.getScheme() != null) && (uri.getScheme().equals("redis")))
    {
      String h = uri.getHost();
      int port = uri.getPort();
      String password = uri.getUserInfo().split(":", 2)[1];
      int database = Integer.parseInt(uri.getPath().split("/", 2)[1]);
      this.internalPool = new GenericObjectPool(new JedisFactory(h, port, 2000, password, database, null), new GenericObjectPoolConfig());
    }
    else
    {
      this.internalPool = new GenericObjectPool(new JedisFactory(host, 6379, 2000, null, 0, null), new GenericObjectPoolConfig());
    }
  }
  
  public JedisPool(URI uri)
  {
    String h = uri.getHost();
    int port = uri.getPort();
    String password = uri.getUserInfo().split(":", 2)[1];
    int database = Integer.parseInt(uri.getPath().split("/", 2)[1]);
    this.internalPool = new GenericObjectPool(new JedisFactory(h, port, 2000, password, database, null), new GenericObjectPoolConfig());
  }
  
  public JedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password)
  {
    this(poolConfig, host, port, timeout, password, 0, null);
  }
  
  public JedisPool(GenericObjectPoolConfig poolConfig, String host, int port)
  {
    this(poolConfig, host, port, 2000, null, 0, null);
  }
  
  public JedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout)
  {
    this(poolConfig, host, port, timeout, null, 0, null);
  }
  
  public JedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database)
  {
    this(poolConfig, host, port, timeout, password, database, null);
  }
  
  public JedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, String clientName)
  {
    super(poolConfig, new JedisFactory(host, port, timeout, password, database, clientName));
  }
  
  public void returnBrokenResource(Jedis resource)
  {
    if (resource != null) {
      returnBrokenResourceObject(resource);
    }
  }
  
  public void returnResource(Jedis resource)
  {
    if (resource != null)
    {
      resource.resetState();
      returnResourceObject(resource);
    }
  }
}

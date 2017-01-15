package redis.clients.jedis;

import java.net.URI;
import redis.clients.util.ShardInfo;

public class JedisShardInfo
  extends ShardInfo<Jedis>
{
  private int timeout;
  private String host;
  private int port;
  
  public String toString()
  {
    return this.host + ":" + this.port + "*" + getWeight();
  }
  
  private String password = null;
  private String name = null;
  
  public String getHost()
  {
    return this.host;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public JedisShardInfo(String host)
  {
    super(1);
    URI uri = URI.create(host);
    if ((uri.getScheme() != null) && (uri.getScheme().equals("redis")))
    {
      this.host = uri.getHost();
      this.port = uri.getPort();
      this.password = uri.getUserInfo().split(":", 2)[1];
    }
    else
    {
      this.host = host;
      this.port = 6379;
    }
  }
  
  public JedisShardInfo(String host, String name)
  {
    this(host, 6379, name);
  }
  
  public JedisShardInfo(String host, int port)
  {
    this(host, port, 2000);
  }
  
  public JedisShardInfo(String host, int port, String name)
  {
    this(host, port, 2000, name);
  }
  
  public JedisShardInfo(String host, int port, int timeout)
  {
    this(host, port, timeout, 1);
  }
  
  public JedisShardInfo(String host, int port, int timeout, String name)
  {
    this(host, port, timeout, 1);
    this.name = name;
  }
  
  public JedisShardInfo(String host, int port, int timeout, int weight)
  {
    super(weight);
    this.host = host;
    this.port = port;
    this.timeout = timeout;
  }
  
  public JedisShardInfo(URI uri)
  {
    super(1);
    this.host = uri.getHost();
    this.port = uri.getPort();
    this.password = uri.getUserInfo().split(":", 2)[1];
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String auth)
  {
    this.password = auth;
  }
  
  public int getTimeout()
  {
    return this.timeout;
  }
  
  public void setTimeout(int timeout)
  {
    this.timeout = timeout;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public Jedis createResource()
  {
    return new Jedis(this);
  }
}

package redis.clients.jedis;

public class HostAndPort
{
  public static final String LOCALHOST_STR = "localhost";
  private String host;
  private int port;
  
  public HostAndPort(String host, int port)
  {
    this.host = host;
    this.port = port;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof HostAndPort))
    {
      HostAndPort hp = (HostAndPort)obj;
      
      String thisHost = convertHost(this.host);
      String hpHost = convertHost(hp.host);
      return (this.port == hp.port) && (thisHost.equals(hpHost));
    }
    return false;
  }
  
  public String toString()
  {
    return this.host + ":" + this.port;
  }
  
  private String convertHost(String host)
  {
    if (host.equals("127.0.0.1")) {
      return "localhost";
    }
    if (host.equals("::1")) {
      return "localhost";
    }
    return host;
  }
}

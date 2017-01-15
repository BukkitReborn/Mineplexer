package redis.clients.jedis;

import redis.clients.jedis.exceptions.JedisAskDataException;
import redis.clients.jedis.exceptions.JedisClusterException;
import redis.clients.jedis.exceptions.JedisClusterMaxRedirectionsException;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisMovedDataException;
import redis.clients.jedis.exceptions.JedisRedirectionException;
import redis.clients.util.JedisClusterCRC16;

public abstract class JedisClusterCommand<T>
{
  private JedisClusterConnectionHandler connectionHandler;
  private int commandTimeout;
  private int redirections;
  
  public JedisClusterCommand(JedisClusterConnectionHandler connectionHandler, int timeout, int maxRedirections)
  {
    this.connectionHandler = connectionHandler;
    this.commandTimeout = timeout;
    this.redirections = maxRedirections;
  }
  
  public abstract T execute(Jedis paramJedis);
  
  public T run(String key)
  {
    if (key == null) {
      throw new JedisClusterException("No way to dispatch this command to Redis Cluster.");
    }
    return (T)runWithRetries(key, this.redirections, false, false);
  }
  
  private T runWithRetries(String key, int redirections, boolean tryRandomNode, boolean asking)
  {
    if (redirections <= 0) {
      throw new JedisClusterMaxRedirectionsException("Too many Cluster redirections?");
    }
    Jedis connection = null;
    try
    {
      if (tryRandomNode) {
        connection = this.connectionHandler.getConnection();
      } else {
        connection = this.connectionHandler.getConnectionFromSlot(JedisClusterCRC16.getSlot(key));
      }
      if (asking)
      {
        connection.asking();
        
        asking = false;
      }
      return (T)execute(connection);
    }
    catch (JedisConnectionException jce)
    {
      if (tryRandomNode) {
        throw jce;
      }
      releaseConnection(connection, true);
      connection = null;
      
      return (T)runWithRetries(key, redirections--, true, asking);
    }
    catch (JedisRedirectionException jre)
    {
      Object localObject2;
      if ((jre instanceof JedisAskDataException)) {
        asking = true;
      } else if (!(jre instanceof JedisMovedDataException)) {}
      this.connectionHandler.assignSlotToNode(jre.getSlot(), jre.getTargetNode());
      
      releaseConnection(connection, false);
      connection = null;
      
      return (T)runWithRetries(key, redirections - 1, false, asking);
    }
    finally
    {
      releaseConnection(connection, false);
    }
  }
  
  private void releaseConnection(Jedis connection, boolean broken)
  {
    if (connection != null) {
      if (broken) {
        this.connectionHandler.returnBrokenConnection(connection);
      } else {
        this.connectionHandler.returnConnection(connection);
      }
    }
  }
}

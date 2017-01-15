package redis.clients.jedis;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.RedisInputStream;
import redis.clients.util.RedisOutputStream;
import redis.clients.util.SafeEncoder;

public class Connection
  implements Closeable
{
  private String host;
  private int port = 6379;
  private Socket socket;
  private RedisOutputStream outputStream;
  private RedisInputStream inputStream;
  private int pipelinedCommands = 0;
  private int timeout = 2000;
  
  public Socket getSocket()
  {
    return this.socket;
  }
  
  public int getTimeout()
  {
    return this.timeout;
  }
  
  public void setTimeout(int timeout)
  {
    this.timeout = timeout;
  }
  
  public void setTimeoutInfinite()
  {
    try
    {
      if (!isConnected()) {
        connect();
      }
      this.socket.setKeepAlive(true);
      this.socket.setSoTimeout(0);
    }
    catch (SocketException ex)
    {
      throw new JedisException(ex);
    }
  }
  
  public void rollbackTimeout()
  {
    try
    {
      this.socket.setSoTimeout(this.timeout);
      this.socket.setKeepAlive(false);
    }
    catch (SocketException ex)
    {
      throw new JedisException(ex);
    }
  }
  
  public Connection(String host)
  {
    this.host = host;
  }
  
  protected void flush()
  {
    try
    {
      this.outputStream.flush();
    }
    catch (IOException e)
    {
      throw new JedisConnectionException(e);
    }
  }
  
  protected Connection sendCommand(Protocol.Command cmd, String... args)
  {
    byte[][] bargs = new byte[args.length][];
    for (int i = 0; i < args.length; i++) {
      bargs[i] = SafeEncoder.encode(args[i]);
    }
    return sendCommand(cmd, bargs);
  }
  
  protected Connection sendCommand(Protocol.Command cmd, byte[]... args)
  {
    connect();
    Protocol.sendCommand(this.outputStream, cmd, args);
    this.pipelinedCommands += 1;
    return this;
  }
  
  protected Connection sendCommand(Protocol.Command cmd)
  {
    connect();
    Protocol.sendCommand(this.outputStream, cmd, new byte[0][]);
    this.pipelinedCommands += 1;
    return this;
  }
  
  public Connection(String host, int port)
  {
    this.host = host;
    this.port = port;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public void setHost(String host)
  {
    this.host = host;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public void setPort(int port)
  {
    this.port = port;
  }
  
  public Connection() {}
  
  public void connect()
  {
    if (!isConnected()) {
      try
      {
        this.socket = new Socket();
        
        this.socket.setReuseAddress(true);
        this.socket.setKeepAlive(true);
        
        this.socket.setTcpNoDelay(true);
        
        this.socket.setSoLinger(true, 0);
        
        this.socket.connect(new InetSocketAddress(this.host, this.port), this.timeout);
        this.socket.setSoTimeout(this.timeout);
        this.outputStream = new RedisOutputStream(this.socket.getOutputStream());
        this.inputStream = new RedisInputStream(this.socket.getInputStream());
      }
      catch (IOException ex)
      {
        throw new JedisConnectionException(ex);
      }
    }
  }
  
  public void close()
  {
    disconnect();
  }
  
  public void disconnect()
  {
    if (isConnected()) {
      try
      {
        this.inputStream.close();
        this.outputStream.close();
        if (!this.socket.isClosed()) {
          this.socket.close();
        }
      }
      catch (IOException ex)
      {
        throw new JedisConnectionException(ex);
      }
    }
  }
  
  public boolean isConnected()
  {
    return (this.socket != null) && (this.socket.isBound()) && (!this.socket.isClosed()) && (this.socket.isConnected()) && (!this.socket.isInputShutdown()) && (!this.socket.isOutputShutdown());
  }
  
  protected String getStatusCodeReply()
  {
    flush();
    this.pipelinedCommands -= 1;
    byte[] resp = (byte[])Protocol.read(this.inputStream);
    if (null == resp) {
      return null;
    }
    return SafeEncoder.encode(resp);
  }
  
  public String getBulkReply()
  {
    byte[] result = getBinaryBulkReply();
    if (null != result) {
      return SafeEncoder.encode(result);
    }
    return null;
  }
  
  public byte[] getBinaryBulkReply()
  {
    flush();
    this.pipelinedCommands -= 1;
    return (byte[])Protocol.read(this.inputStream);
  }
  
  public Long getIntegerReply()
  {
    flush();
    this.pipelinedCommands -= 1;
    return (Long)Protocol.read(this.inputStream);
  }
  
  public List<String> getMultiBulkReply()
  {
    return (List)BuilderFactory.STRING_LIST.build(getBinaryMultiBulkReply());
  }
  
  public List<byte[]> getBinaryMultiBulkReply()
  {
    flush();
    this.pipelinedCommands -= 1;
    return (List)Protocol.read(this.inputStream);
  }
  
  public void resetPipelinedCount()
  {
    this.pipelinedCommands = 0;
  }
  
  public List<Object> getRawObjectMultiBulkReply()
  {
    return (List)Protocol.read(this.inputStream);
  }
  
  public List<Object> getObjectMultiBulkReply()
  {
    flush();
    this.pipelinedCommands -= 1;
    return getRawObjectMultiBulkReply();
  }
  
  public List<Long> getIntegerMultiBulkReply()
  {
    flush();
    this.pipelinedCommands -= 1;
    return (List)Protocol.read(this.inputStream);
  }
  
  public List<Object> getAll()
  {
    return getAll(0);
  }
  
  public List<Object> getAll(int except)
  {
    List<Object> all = new ArrayList();
    flush();
    while (this.pipelinedCommands > except)
    {
      try
      {
        all.add(Protocol.read(this.inputStream));
      }
      catch (JedisDataException e)
      {
        all.add(e);
      }
      this.pipelinedCommands -= 1;
    }
    return all;
  }
  
  public Object getOne()
  {
    flush();
    this.pipelinedCommands -= 1;
    return Protocol.read(this.inputStream);
  }
}

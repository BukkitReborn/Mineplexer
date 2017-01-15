package redis.clients.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisInputStream
  extends FilterInputStream
{
  protected final byte[] buf;
  protected int count;
  protected int limit;
  
  public RedisInputStream(InputStream in, int size)
  {
    super(in);
    if (size <= 0) {
      throw new IllegalArgumentException("Buffer size <= 0");
    }
    this.buf = new byte[size];
  }
  
  public RedisInputStream(InputStream in)
  {
    this(in, 8192);
  }
  
  public byte readByte()
    throws IOException
  {
    if (this.count == this.limit) {
      fill();
    }
    return this.buf[(this.count++)];
  }
  
  public String readLine()
  {
    StringBuilder sb = new StringBuilder();
    try
    {
      for (;;)
      {
        if (this.count == this.limit) {
          fill();
        }
        if (this.limit == -1) {
          break;
        }
        int b = this.buf[(this.count++)];
        if (b == 13)
        {
          if (this.count == this.limit) {
            fill();
          }
          if (this.limit == -1)
          {
            sb.append((char)b);
            break;
          }
          byte c = this.buf[(this.count++)];
          if (c == 10) {
            break;
          }
          sb.append((char)b);
          sb.append((char)c);
        }
        else
        {
          sb.append((char)b);
        }
      }
    }
    catch (IOException e)
    {
      throw new JedisConnectionException(e);
    }
    String reply = sb.toString();
    if (reply.length() == 0) {
      throw new JedisConnectionException("It seems like server has closed the connection.");
    }
    return reply;
  }
  
  public int read(byte[] b, int off, int len)
    throws IOException
  {
    if (this.count == this.limit)
    {
      fill();
      if (this.limit == -1) {
        return -1;
      }
    }
    int length = Math.min(this.limit - this.count, len);
    System.arraycopy(this.buf, this.count, b, off, length);
    this.count += length;
    return length;
  }
  
  private void fill()
    throws IOException
  {
    this.limit = this.in.read(this.buf);
    this.count = 0;
  }
}

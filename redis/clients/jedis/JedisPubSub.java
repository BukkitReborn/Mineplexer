package redis.clients.jedis;

import java.util.Arrays;
import java.util.List;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

public abstract class JedisPubSub
{
  private int subscribedChannels = 0;
  private volatile Client client;
  
  public abstract void onMessage(String paramString1, String paramString2);
  
  public abstract void onPMessage(String paramString1, String paramString2, String paramString3);
  
  public abstract void onSubscribe(String paramString, int paramInt);
  
  public abstract void onUnsubscribe(String paramString, int paramInt);
  
  public abstract void onPUnsubscribe(String paramString, int paramInt);
  
  public abstract void onPSubscribe(String paramString, int paramInt);
  
  public void unsubscribe()
  {
    if (this.client == null) {
      throw new JedisConnectionException("JedisPubSub was not subscribed to a Jedis instance.");
    }
    this.client.unsubscribe();
    this.client.flush();
  }
  
  public void unsubscribe(String... channels)
  {
    if (this.client == null) {
      throw new JedisConnectionException("JedisPubSub is not subscribed to a Jedis instance.");
    }
    this.client.unsubscribe(channels);
    this.client.flush();
  }
  
  public void subscribe(String... channels)
  {
    if (this.client == null) {
      throw new JedisConnectionException("JedisPubSub is not subscribed to a Jedis instance.");
    }
    this.client.subscribe(channels);
    this.client.flush();
  }
  
  public void psubscribe(String... patterns)
  {
    if (this.client == null) {
      throw new JedisConnectionException("JedisPubSub is not subscribed to a Jedis instance.");
    }
    this.client.psubscribe(patterns);
    this.client.flush();
  }
  
  public void punsubscribe()
  {
    if (this.client == null) {
      throw new JedisConnectionException("JedisPubSub is not subscribed to a Jedis instance.");
    }
    this.client.punsubscribe();
    this.client.flush();
  }
  
  public void punsubscribe(String... patterns)
  {
    if (this.client == null) {
      throw new JedisConnectionException("JedisPubSub is not subscribed to a Jedis instance.");
    }
    this.client.punsubscribe(patterns);
    this.client.flush();
  }
  
  public boolean isSubscribed()
  {
    return this.subscribedChannels > 0;
  }
  
  public void proceedWithPatterns(Client client, String... patterns)
  {
    this.client = client;
    client.psubscribe(patterns);
    client.flush();
    process(client);
  }
  
  public void proceed(Client client, String... channels)
  {
    this.client = client;
    client.subscribe(channels);
    client.flush();
    process(client);
  }
  
  private void process(Client client)
  {
    do
    {
      List<Object> reply = client.getRawObjectMultiBulkReply();
      Object firstObj = reply.get(0);
      if (!(firstObj instanceof byte[])) {
        throw new JedisException("Unknown message type: " + firstObj);
      }
      byte[] resp = (byte[])firstObj;
      if (Arrays.equals(Protocol.Keyword.SUBSCRIBE.raw, resp))
      {
        this.subscribedChannels = ((Long)reply.get(2)).intValue();
        byte[] bchannel = (byte[])reply.get(1);
        String strchannel = bchannel == null ? null : SafeEncoder.encode(bchannel);
        
        onSubscribe(strchannel, this.subscribedChannels);
      }
      else if (Arrays.equals(Protocol.Keyword.UNSUBSCRIBE.raw, resp))
      {
        this.subscribedChannels = ((Long)reply.get(2)).intValue();
        byte[] bchannel = (byte[])reply.get(1);
        String strchannel = bchannel == null ? null : SafeEncoder.encode(bchannel);
        
        onUnsubscribe(strchannel, this.subscribedChannels);
      }
      else if (Arrays.equals(Protocol.Keyword.MESSAGE.raw, resp))
      {
        byte[] bchannel = (byte[])reply.get(1);
        byte[] bmesg = (byte[])reply.get(2);
        String strchannel = bchannel == null ? null : SafeEncoder.encode(bchannel);
        
        String strmesg = bmesg == null ? null : SafeEncoder.encode(bmesg);
        
        onMessage(strchannel, strmesg);
      }
      else if (Arrays.equals(Protocol.Keyword.PMESSAGE.raw, resp))
      {
        byte[] bpattern = (byte[])reply.get(1);
        byte[] bchannel = (byte[])reply.get(2);
        byte[] bmesg = (byte[])reply.get(3);
        String strpattern = bpattern == null ? null : SafeEncoder.encode(bpattern);
        
        String strchannel = bchannel == null ? null : SafeEncoder.encode(bchannel);
        
        String strmesg = bmesg == null ? null : SafeEncoder.encode(bmesg);
        
        onPMessage(strpattern, strchannel, strmesg);
      }
      else if (Arrays.equals(Protocol.Keyword.PSUBSCRIBE.raw, resp))
      {
        this.subscribedChannels = ((Long)reply.get(2)).intValue();
        byte[] bpattern = (byte[])reply.get(1);
        String strpattern = bpattern == null ? null : SafeEncoder.encode(bpattern);
        
        onPSubscribe(strpattern, this.subscribedChannels);
      }
      else if (Arrays.equals(Protocol.Keyword.PUNSUBSCRIBE.raw, resp))
      {
        this.subscribedChannels = ((Long)reply.get(2)).intValue();
        byte[] bpattern = (byte[])reply.get(1);
        String strpattern = bpattern == null ? null : SafeEncoder.encode(bpattern);
        
        onPUnsubscribe(strpattern, this.subscribedChannels);
      }
      else
      {
        throw new JedisException("Unknown message type: " + firstObj);
      }
    } while (isSubscribed());
    this.client = null;
    
    client.resetPipelinedCount();
  }
  
  public int getSubscribedChannels()
  {
    return this.subscribedChannels;
  }
}

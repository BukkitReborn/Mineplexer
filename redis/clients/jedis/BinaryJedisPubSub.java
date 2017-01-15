package redis.clients.jedis;

import java.util.Arrays;
import java.util.List;
import redis.clients.jedis.exceptions.JedisException;

public abstract class BinaryJedisPubSub
{
  private int subscribedChannels = 0;
  private Client client;
  
  public abstract void onMessage(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract void onPMessage(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract void onSubscribe(byte[] paramArrayOfByte, int paramInt);
  
  public abstract void onUnsubscribe(byte[] paramArrayOfByte, int paramInt);
  
  public abstract void onPUnsubscribe(byte[] paramArrayOfByte, int paramInt);
  
  public abstract void onPSubscribe(byte[] paramArrayOfByte, int paramInt);
  
  public void unsubscribe()
  {
    this.client.unsubscribe();
    this.client.flush();
  }
  
  public void unsubscribe(byte[]... channels)
  {
    this.client.unsubscribe(channels);
    this.client.flush();
  }
  
  public void subscribe(byte[]... channels)
  {
    this.client.subscribe(channels);
    this.client.flush();
  }
  
  public void psubscribe(byte[]... patterns)
  {
    this.client.psubscribe(patterns);
    this.client.flush();
  }
  
  public void punsubscribe()
  {
    this.client.punsubscribe();
    this.client.flush();
  }
  
  public void punsubscribe(byte[]... patterns)
  {
    this.client.punsubscribe(patterns);
    this.client.flush();
  }
  
  public boolean isSubscribed()
  {
    return this.subscribedChannels > 0;
  }
  
  public void proceedWithPatterns(Client client, byte[]... patterns)
  {
    this.client = client;
    client.psubscribe(patterns);
    process(client);
  }
  
  public void proceed(Client client, byte[]... channels)
  {
    this.client = client;
    client.subscribe(channels);
    process(client);
  }
  
  private void process(Client client)
  {
    do
    {
      List<Object> reply = client.getObjectMultiBulkReply();
      Object firstObj = reply.get(0);
      if (!(firstObj instanceof byte[])) {
        throw new JedisException("Unknown message type: " + firstObj);
      }
      byte[] resp = (byte[])firstObj;
      if (Arrays.equals(Protocol.Keyword.SUBSCRIBE.raw, resp))
      {
        this.subscribedChannels = ((Long)reply.get(2)).intValue();
        byte[] bchannel = (byte[])reply.get(1);
        onSubscribe(bchannel, this.subscribedChannels);
      }
      else if (Arrays.equals(Protocol.Keyword.UNSUBSCRIBE.raw, resp))
      {
        this.subscribedChannels = ((Long)reply.get(2)).intValue();
        byte[] bchannel = (byte[])reply.get(1);
        onUnsubscribe(bchannel, this.subscribedChannels);
      }
      else if (Arrays.equals(Protocol.Keyword.MESSAGE.raw, resp))
      {
        byte[] bchannel = (byte[])reply.get(1);
        byte[] bmesg = (byte[])reply.get(2);
        onMessage(bchannel, bmesg);
      }
      else if (Arrays.equals(Protocol.Keyword.PMESSAGE.raw, resp))
      {
        byte[] bpattern = (byte[])reply.get(1);
        byte[] bchannel = (byte[])reply.get(2);
        byte[] bmesg = (byte[])reply.get(3);
        onPMessage(bpattern, bchannel, bmesg);
      }
      else if (Arrays.equals(Protocol.Keyword.PSUBSCRIBE.raw, resp))
      {
        this.subscribedChannels = ((Long)reply.get(2)).intValue();
        byte[] bpattern = (byte[])reply.get(1);
        onPSubscribe(bpattern, this.subscribedChannels);
      }
      else if (Arrays.equals(Protocol.Keyword.PUNSUBSCRIBE.raw, resp))
      {
        this.subscribedChannels = ((Long)reply.get(2)).intValue();
        byte[] bpattern = (byte[])reply.get(1);
        onPUnsubscribe(bpattern, this.subscribedChannels);
      }
      else
      {
        throw new JedisException("Unknown message type: " + firstObj);
      }
    } while (isSubscribed());
  }
  
  public int getSubscribedChannels()
  {
    return this.subscribedChannels;
  }
}

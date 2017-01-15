package redis.clients.jedis;

import java.util.List;

public abstract interface AdvancedBinaryJedisCommands
{
  public abstract List<byte[]> configGet(byte[] paramArrayOfByte);
  
  public abstract byte[] configSet(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract String slowlogReset();
  
  public abstract Long slowlogLen();
  
  public abstract List<byte[]> slowlogGetBinary();
  
  public abstract List<byte[]> slowlogGetBinary(long paramLong);
  
  public abstract Long objectRefcount(byte[] paramArrayOfByte);
  
  public abstract byte[] objectEncoding(byte[] paramArrayOfByte);
  
  public abstract Long objectIdletime(byte[] paramArrayOfByte);
}

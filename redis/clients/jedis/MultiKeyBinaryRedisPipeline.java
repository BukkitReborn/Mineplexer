package redis.clients.jedis;

import java.util.List;
import java.util.Set;

public abstract interface MultiKeyBinaryRedisPipeline
{
  public abstract Response<Long> del(byte[]... paramVarArgs);
  
  public abstract Response<List<byte[]>> blpop(byte[]... paramVarArgs);
  
  public abstract Response<List<byte[]>> brpop(byte[]... paramVarArgs);
  
  public abstract Response<Set<byte[]>> keys(byte[] paramArrayOfByte);
  
  public abstract Response<List<byte[]>> mget(byte[]... paramVarArgs);
  
  public abstract Response<String> mset(byte[]... paramVarArgs);
  
  public abstract Response<Long> msetnx(byte[]... paramVarArgs);
  
  public abstract Response<String> rename(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> renamenx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<byte[]> rpoplpush(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Set<byte[]>> sdiff(byte[]... paramVarArgs);
  
  public abstract Response<Long> sdiffstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Set<byte[]>> sinter(byte[]... paramVarArgs);
  
  public abstract Response<Long> sinterstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> smove(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<Long> sort(byte[] paramArrayOfByte1, SortingParams paramSortingParams, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> sort(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Set<byte[]>> sunion(byte[]... paramVarArgs);
  
  public abstract Response<Long> sunionstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<String> watch(byte[]... paramVarArgs);
  
  public abstract Response<Long> zinterstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> zinterstore(byte[] paramArrayOfByte, ZParams paramZParams, byte[]... paramVarArgs);
  
  public abstract Response<Long> zunionstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> zunionstore(byte[] paramArrayOfByte, ZParams paramZParams, byte[]... paramVarArgs);
  
  public abstract Response<byte[]> brpoplpush(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt);
  
  public abstract Response<Long> publish(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<byte[]> randomKeyBinary();
  
  public abstract Response<Long> bitop(BitOP paramBitOP, byte[] paramArrayOfByte, byte[]... paramVarArgs);
}

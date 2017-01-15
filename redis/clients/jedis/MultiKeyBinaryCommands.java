package redis.clients.jedis;

import java.util.List;
import java.util.Set;

public abstract interface MultiKeyBinaryCommands
{
  public abstract Long del(byte[]... paramVarArgs);
  
  public abstract List<byte[]> blpop(int paramInt, byte[]... paramVarArgs);
  
  public abstract List<byte[]> brpop(int paramInt, byte[]... paramVarArgs);
  
  public abstract List<byte[]> blpop(byte[]... paramVarArgs);
  
  public abstract List<byte[]> brpop(byte[]... paramVarArgs);
  
  public abstract Set<byte[]> keys(byte[] paramArrayOfByte);
  
  public abstract List<byte[]> mget(byte[]... paramVarArgs);
  
  public abstract String mset(byte[]... paramVarArgs);
  
  public abstract Long msetnx(byte[]... paramVarArgs);
  
  public abstract String rename(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Long renamenx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract byte[] rpoplpush(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Set<byte[]> sdiff(byte[]... paramVarArgs);
  
  public abstract Long sdiffstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Set<byte[]> sinter(byte[]... paramVarArgs);
  
  public abstract Long sinterstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Long smove(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Long sort(byte[] paramArrayOfByte1, SortingParams paramSortingParams, byte[] paramArrayOfByte2);
  
  public abstract Long sort(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Set<byte[]> sunion(byte[]... paramVarArgs);
  
  public abstract Long sunionstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract String watch(byte[]... paramVarArgs);
  
  public abstract String unwatch();
  
  public abstract Long zinterstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Long zinterstore(byte[] paramArrayOfByte, ZParams paramZParams, byte[]... paramVarArgs);
  
  public abstract Long zunionstore(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Long zunionstore(byte[] paramArrayOfByte, ZParams paramZParams, byte[]... paramVarArgs);
  
  public abstract byte[] brpoplpush(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt);
  
  public abstract Long publish(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract void subscribe(BinaryJedisPubSub paramBinaryJedisPubSub, byte[]... paramVarArgs);
  
  public abstract void psubscribe(BinaryJedisPubSub paramBinaryJedisPubSub, byte[]... paramVarArgs);
  
  public abstract byte[] randomBinaryKey();
  
  public abstract Long bitop(BitOP paramBitOP, byte[] paramArrayOfByte, byte[]... paramVarArgs);
}

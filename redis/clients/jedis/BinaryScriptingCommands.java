package redis.clients.jedis;

import java.util.List;

public abstract interface BinaryScriptingCommands
{
  public abstract Object eval(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[]... paramVarArgs);
  
  public abstract Object eval(byte[] paramArrayOfByte, int paramInt, byte[]... paramVarArgs);
  
  public abstract Object eval(byte[] paramArrayOfByte, List<byte[]> paramList1, List<byte[]> paramList2);
  
  public abstract Object eval(byte[] paramArrayOfByte);
  
  public abstract Object evalsha(byte[] paramArrayOfByte);
  
  public abstract Object evalsha(byte[] paramArrayOfByte, List<byte[]> paramList1, List<byte[]> paramList2);
  
  public abstract Object evalsha(byte[] paramArrayOfByte, int paramInt, byte[]... paramVarArgs);
  
  public abstract List<Long> scriptExists(byte[]... paramVarArgs);
  
  public abstract byte[] scriptLoad(byte[] paramArrayOfByte);
  
  public abstract String scriptFlush();
  
  public abstract String scriptKill();
}

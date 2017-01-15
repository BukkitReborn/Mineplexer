package redis.clients.jedis;

import java.util.List;

public abstract interface ScriptingCommands
{
  public abstract Object eval(String paramString, int paramInt, String... paramVarArgs);
  
  public abstract Object eval(String paramString, List<String> paramList1, List<String> paramList2);
  
  public abstract Object eval(String paramString);
  
  public abstract Object evalsha(String paramString);
  
  public abstract Object evalsha(String paramString, List<String> paramList1, List<String> paramList2);
  
  public abstract Object evalsha(String paramString, int paramInt, String... paramVarArgs);
  
  public abstract Boolean scriptExists(String paramString);
  
  public abstract List<Boolean> scriptExists(String... paramVarArgs);
  
  public abstract String scriptLoad(String paramString);
}

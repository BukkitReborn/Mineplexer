package redis.clients.jedis;

import java.util.List;
import java.util.Map;

public abstract interface SentinelCommands
{
  public abstract List<Map<String, String>> sentinelMasters();
  
  public abstract List<String> sentinelGetMasterAddrByName(String paramString);
  
  public abstract Long sentinelReset(String paramString);
  
  public abstract List<Map<String, String>> sentinelSlaves(String paramString);
  
  public abstract String sentinelFailover(String paramString);
  
  public abstract String sentinelMonitor(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract String sentinelRemove(String paramString);
  
  public abstract String sentinelSet(String paramString, Map<String, String> paramMap);
}

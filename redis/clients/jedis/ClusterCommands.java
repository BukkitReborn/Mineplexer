package redis.clients.jedis;

import java.util.List;

public abstract interface ClusterCommands
{
  public abstract String clusterNodes();
  
  public abstract String clusterMeet(String paramString, int paramInt);
  
  public abstract String clusterAddSlots(int... paramVarArgs);
  
  public abstract String clusterDelSlots(int... paramVarArgs);
  
  public abstract String clusterInfo();
  
  public abstract List<String> clusterGetKeysInSlot(int paramInt1, int paramInt2);
  
  public abstract String clusterSetSlotNode(int paramInt, String paramString);
  
  public abstract String clusterSetSlotMigrating(int paramInt, String paramString);
  
  public abstract String clusterSetSlotImporting(int paramInt, String paramString);
}

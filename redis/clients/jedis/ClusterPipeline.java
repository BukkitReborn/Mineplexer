package redis.clients.jedis;

import java.util.List;

public abstract interface ClusterPipeline
{
  public abstract Response<String> clusterNodes();
  
  public abstract Response<String> clusterMeet(String paramString, int paramInt);
  
  public abstract Response<String> clusterAddSlots(int... paramVarArgs);
  
  public abstract Response<String> clusterDelSlots(int... paramVarArgs);
  
  public abstract Response<String> clusterInfo();
  
  public abstract Response<List<String>> clusterGetKeysInSlot(int paramInt1, int paramInt2);
  
  public abstract Response<String> clusterSetSlotNode(int paramInt, String paramString);
  
  public abstract Response<String> clusterSetSlotMigrating(int paramInt, String paramString);
  
  public abstract Response<String> clusterSetSlotImporting(int paramInt, String paramString);
}

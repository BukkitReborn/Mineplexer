package mineplex.serverdata.servers;

import java.util.Collection;
import mineplex.serverdata.data.DedicatedServer;
import mineplex.serverdata.data.MinecraftServer;
import mineplex.serverdata.data.ServerGroup;

public abstract interface ServerRepository
{
  public abstract Collection<MinecraftServer> getServerStatuses();
  
  public abstract Collection<MinecraftServer> getServerStatusesByPrefix(String paramString);
  
  public abstract Collection<MinecraftServer> getServersByGroup(String paramString);
  
  public abstract MinecraftServer getServerStatus(String paramString);
  
  public abstract void updataServerStatus(MinecraftServer paramMinecraftServer, int paramInt);
  
  public abstract void removeServerStatus(MinecraftServer paramMinecraftServer);
  
  public abstract boolean serverExists(String paramString);
  
  public abstract Collection<DedicatedServer> getDedicatedServers();
  
  public abstract Collection<ServerGroup> getServerGroups(Collection<MinecraftServer> paramCollection);
  
  public abstract ServerGroup getServerGroup(String paramString);
  
  public abstract Collection<MinecraftServer> getDeadServers();
  
  public abstract void updateServerGroup(ServerGroup paramServerGroup);
  
  public abstract void removeServerGroup(ServerGroup paramServerGroup);
}

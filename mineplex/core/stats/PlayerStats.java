package mineplex.core.stats;

import java.util.Set;
import mineplex.core.common.util.NautHashMap;

public class PlayerStats
{
  private NautHashMap<String, Long> _statHash = new NautHashMap();
  
  public long addStat(String statName, long value)
  {
    if (!this._statHash.containsKey(statName)) {
      this._statHash.put(statName, Long.valueOf(0L));
    }
    this._statHash.put(statName, Long.valueOf(((Long)this._statHash.get(statName)).longValue() + value));
    
    return ((Long)this._statHash.get(statName)).longValue();
  }
  
  public long getStat(String statName)
  {
    return this._statHash.containsKey(statName) ? ((Long)this._statHash.get(statName)).longValue() : 0L;
  }
  
  public Set<String> getStatsNames()
  {
    return this._statHash.keySet();
  }
}

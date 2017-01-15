package org.apache.commons.pool2.impl;

import java.io.PrintWriter;

public class AbandonedConfig
{
  private boolean removeAbandonedOnBorrow = false;
  
  public boolean getRemoveAbandonedOnBorrow()
  {
    return this.removeAbandonedOnBorrow;
  }
  
  public void setRemoveAbandonedOnBorrow(boolean removeAbandonedOnBorrow)
  {
    this.removeAbandonedOnBorrow = removeAbandonedOnBorrow;
  }
  
  private boolean removeAbandonedOnMaintenance = false;
  
  public boolean getRemoveAbandonedOnMaintenance()
  {
    return this.removeAbandonedOnMaintenance;
  }
  
  public void setRemoveAbandonedOnMaintenance(boolean removeAbandonedOnMaintenance)
  {
    this.removeAbandonedOnMaintenance = removeAbandonedOnMaintenance;
  }
  
  private int removeAbandonedTimeout = 300;
  
  public int getRemoveAbandonedTimeout()
  {
    return this.removeAbandonedTimeout;
  }
  
  public void setRemoveAbandonedTimeout(int removeAbandonedTimeout)
  {
    this.removeAbandonedTimeout = removeAbandonedTimeout;
  }
  
  private boolean logAbandoned = false;
  
  public boolean getLogAbandoned()
  {
    return this.logAbandoned;
  }
  
  public void setLogAbandoned(boolean logAbandoned)
  {
    this.logAbandoned = logAbandoned;
  }
  
  private PrintWriter logWriter = new PrintWriter(System.out);
  
  public PrintWriter getLogWriter()
  {
    return this.logWriter;
  }
  
  public void setLogWriter(PrintWriter logWriter)
  {
    this.logWriter = logWriter;
  }
  
  private boolean useUsageTracking = false;
  
  public boolean getUseUsageTracking()
  {
    return this.useUsageTracking;
  }
  
  public void setUseUsageTracking(boolean useUsageTracking)
  {
    this.useUsageTracking = useUsageTracking;
  }
}

package org.jooq.tools;

public final class StopWatch
{
  private static final JooqLogger log = JooqLogger.getLogger(StopWatch.class);
  private long start;
  private long split;
  
  public StopWatch()
  {
    this.start = System.nanoTime();
    this.split = this.start;
  }
  
  public void splitTrace(String message)
  {
    if (log.isTraceEnabled()) {
      log.trace(message, splitMessage());
    }
  }
  
  public void splitDebug(String message)
  {
    if (log.isDebugEnabled()) {
      log.debug(message, splitMessage());
    }
  }
  
  public void splitInfo(String message)
  {
    if (log.isInfoEnabled()) {
      log.info(message, splitMessage());
    }
  }
  
  private String splitMessage()
  {
    long temp = this.split;
    this.split = System.nanoTime();
    if (temp == this.start) {
      return "Total: " + format(this.split - this.start);
    }
    return "Total: " + format(this.split - this.start) + ", +" + format(this.split - temp);
  }
  
  public static String format(long nanoTime)
  {
    if (nanoTime > 60000000000L) {
      return formatHours(nanoTime / 1000000000L);
    }
    if (nanoTime > 1000000000L) {
      return nanoTime / 1000000L / 1000.0D + "s";
    }
    return nanoTime / 1000L / 1000.0D + "ms";
  }
  
  public static String formatHours(long seconds)
  {
    long s = seconds % 60L;
    long m = seconds / 60L % 60L;
    long h = seconds / 3600L;
    
    StringBuilder sb = new StringBuilder();
    if (h != 0L) {
      if (h < 10L)
      {
        sb.append("0");
        sb.append(h);
        sb.append(":");
      }
      else
      {
        sb.append(h);
        sb.append(":");
      }
    }
    if (m < 10L)
    {
      sb.append("0");
      sb.append(m);
      sb.append(":");
    }
    else
    {
      sb.append(m);
      sb.append(":");
    }
    if (s < 10L)
    {
      sb.append("0");
      sb.append(s);
    }
    else
    {
      sb.append(s);
    }
    return sb.toString();
  }
}

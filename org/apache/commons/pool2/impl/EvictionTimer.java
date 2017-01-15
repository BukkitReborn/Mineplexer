package org.apache.commons.pool2.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Timer;
import java.util.TimerTask;

class EvictionTimer
{
  private static Timer _timer;
  private static int _usageCount;
  
  static synchronized void schedule(TimerTask task, long delay, long period)
  {
    if (null == _timer)
    {
      ClassLoader ccl = (ClassLoader)AccessController.doPrivileged(new PrivilegedGetTccl(null));
      try
      {
        AccessController.doPrivileged(new PrivilegedSetTccl(EvictionTimer.class.getClassLoader()));
        
        _timer = new Timer("commons-pool-EvictionTimer", true);
      }
      finally
      {
        AccessController.doPrivileged(new PrivilegedSetTccl(ccl));
      }
    }
    _usageCount += 1;
    _timer.schedule(task, delay, period);
  }
  
  static synchronized void cancel(TimerTask task)
  {
    task.cancel();
    _usageCount -= 1;
    if (_usageCount == 0)
    {
      _timer.cancel();
      _timer = null;
    }
  }
  
  private static class PrivilegedGetTccl
    implements PrivilegedAction<ClassLoader>
  {
    public ClassLoader run()
    {
      return Thread.currentThread().getContextClassLoader();
    }
  }
  
  private static class PrivilegedSetTccl
    implements PrivilegedAction<Void>
  {
    private final ClassLoader cl;
    
    PrivilegedSetTccl(ClassLoader cl)
    {
      this.cl = cl;
    }
    
    public Void run()
    {
      Thread.currentThread().setContextClassLoader(this.cl);
      return null;
    }
  }
}

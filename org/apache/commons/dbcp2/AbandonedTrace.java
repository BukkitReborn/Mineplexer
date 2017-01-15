package org.apache.commons.dbcp2;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.pool2.TrackedUse;

public class AbandonedTrace
  implements TrackedUse
{
  private final List<WeakReference<AbandonedTrace>> traceList = new ArrayList();
  private volatile long lastUsed = 0L;
  
  public AbandonedTrace()
  {
    init(null);
  }
  
  public AbandonedTrace(AbandonedTrace parent)
  {
    init(parent);
  }
  
  private void init(AbandonedTrace parent)
  {
    if (parent != null) {
      parent.addTrace(this);
    }
  }
  
  public long getLastUsed()
  {
    return this.lastUsed;
  }
  
  protected void setLastUsed()
  {
    this.lastUsed = System.currentTimeMillis();
  }
  
  protected void setLastUsed(long time)
  {
    this.lastUsed = time;
  }
  
  protected void addTrace(AbandonedTrace trace)
  {
    synchronized (this.traceList)
    {
      this.traceList.add(new WeakReference(trace));
    }
    setLastUsed();
  }
  
  protected void clearTrace()
  {
    synchronized (this.traceList)
    {
      this.traceList.clear();
    }
  }
  
  protected List<AbandonedTrace> getTrace()
  {
    int size = this.traceList.size();
    if (size == 0) {
      return Collections.emptyList();
    }
    ArrayList<AbandonedTrace> result = new ArrayList(size);
    synchronized (this.traceList)
    {
      Iterator<WeakReference<AbandonedTrace>> iter = this.traceList.iterator();
      while (iter.hasNext())
      {
        WeakReference<AbandonedTrace> ref = (WeakReference)iter.next();
        if (ref.get() == null) {
          iter.remove();
        } else {
          result.add(ref.get());
        }
      }
    }
    return result;
  }
  
  protected void removeTrace(AbandonedTrace trace)
  {
    synchronized (this.traceList)
    {
      Iterator<WeakReference<AbandonedTrace>> iter = this.traceList.iterator();
      while (iter.hasNext())
      {
        WeakReference<AbandonedTrace> ref = (WeakReference)iter.next();
        if (trace.equals(ref.get()))
        {
          iter.remove();
          break;
        }
        if (ref.get() == null) {
          iter.remove();
        }
      }
    }
  }
}

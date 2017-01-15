package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Attachable;
import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.DSLContext;

abstract class AbstractStore
  implements AttachableInternal
{
  private static final long serialVersionUID = -2989496800221194411L;
  private Configuration configuration;
  
  AbstractStore()
  {
    this(null);
  }
  
  AbstractStore(Configuration configuration)
  {
    this.configuration = configuration;
  }
  
  abstract List<Attachable> getAttachables();
  
  public final void attach(Configuration c)
  {
    this.configuration = c;
    
    List<Attachable> attachables = getAttachables();
    int size = attachables.size();
    for (int i = 0; i < size; i++) {
      ((Attachable)attachables.get(i)).attach(c);
    }
  }
  
  public final void detach()
  {
    attach(null);
  }
  
  public final Configuration configuration()
  {
    return this.configuration;
  }
  
  protected final DSLContext create()
  {
    return DSL.using(configuration());
  }
  
  abstract int size();
  
  abstract Object getValue(int paramInt);
  
  public int hashCode()
  {
    int hashCode = 1;
    for (int i = 0; i < size(); i++)
    {
      Object obj = getValue(i);
      if (obj == null) {
        hashCode = 31 * hashCode;
      } else if (obj.getClass().isArray()) {
        hashCode = 31 * hashCode;
      } else {
        hashCode = 31 * hashCode + obj.hashCode();
      }
    }
    return hashCode;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if ((obj instanceof AbstractStore))
    {
      AbstractStore that = (AbstractStore)obj;
      if (size() == that.size())
      {
        for (int i = 0; i < size(); i++)
        {
          Object thisValue = getValue(i);
          Object thatValue = that.getValue(i);
          if ((thisValue != null) || (thatValue != null))
          {
            if ((thisValue == null) || (thatValue == null)) {
              return false;
            }
            if ((thisValue.getClass().isArray()) && (thatValue.getClass().isArray()))
            {
              if ((thisValue.getClass() == byte[].class) && (thatValue.getClass() == byte[].class))
              {
                if (!Arrays.equals((byte[])thisValue, (byte[])thatValue)) {
                  return false;
                }
              }
              else if ((!thisValue.getClass().getComponentType().isPrimitive()) && 
                (!thatValue.getClass().getComponentType().isPrimitive()))
              {
                if (!Arrays.equals((Object[])thisValue, (Object[])thatValue)) {
                  return false;
                }
              }
              else {
                return false;
              }
            }
            else if (!thisValue.equals(thatValue)) {
              return false;
            }
          }
        }
        return true;
      }
    }
    return false;
  }
}

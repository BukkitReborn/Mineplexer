package org.jooq.types;

import java.io.ObjectStreamException;

public final class UInteger
  extends UNumber
  implements Comparable<UInteger>
{
  private static final Class<UInteger> CLASS = UInteger.class;
  private static final String CLASS_NAME = CLASS.getName();
  private static final String PRECACHE_PROPERTY = CLASS_NAME + ".precacheSize";
  private static final int DEFAULT_PRECACHE_SIZE = 256;
  private static final long serialVersionUID = -6821055240959745390L;
  private static final UInteger[] VALUES = mkValues();
  public static final long MIN_VALUE = 0L;
  public static final long MAX_VALUE = 4294967295L;
  private final long value;
  
  private static final int getPrecacheSize()
  {
    String prop = null;
    try
    {
      prop = System.getProperty(PRECACHE_PROPERTY);
    }
    catch (SecurityException e)
    {
      return 256;
    }
    if (prop == null) {
      return 256;
    }
    if (prop.length() <= 0) {
      return 256;
    }
    try
    {
      propParsed = Long.parseLong(prop);
    }
    catch (NumberFormatException e)
    {
      long propParsed;
      return 256;
    }
    long propParsed;
    if (propParsed < 0L) {
      return 0;
    }
    if (propParsed > 2147483647L) {
      return Integer.MAX_VALUE;
    }
    return (int)propParsed;
  }
  
  private static final UInteger[] mkValues()
  {
    int precacheSize = getPrecacheSize();
    if (precacheSize <= 0) {
      return null;
    }
    UInteger[] ret = new UInteger[precacheSize];
    for (int i = 0; i < precacheSize; i++) {
      ret[i] = new UInteger(i);
    }
    return ret;
  }
  
  private UInteger(long value, boolean unused)
  {
    this.value = value;
  }
  
  private static UInteger getCached(long value)
  {
    if ((VALUES != null) && (value < VALUES.length)) {
      return VALUES[((int)value)];
    }
    return null;
  }
  
  private static UInteger valueOfUnchecked(long value)
  {
    UInteger cached;
    if ((cached = getCached(value)) != null) {
      return cached;
    }
    return new UInteger(value, true);
  }
  
  public static UInteger valueOf(String value)
    throws NumberFormatException
  {
    return valueOfUnchecked(rangeCheck(Long.parseLong(value)));
  }
  
  public static UInteger valueOf(int value)
  {
    return valueOfUnchecked(value & 0xFFFFFFFF);
  }
  
  public static UInteger valueOf(long value)
    throws NumberFormatException
  {
    return valueOfUnchecked(rangeCheck(value));
  }
  
  private UInteger(long value)
    throws NumberFormatException
  {
    this.value = rangeCheck(value);
  }
  
  private UInteger(int value)
  {
    this.value = (value & 0xFFFFFFFF);
  }
  
  private UInteger(String value)
    throws NumberFormatException
  {
    this.value = rangeCheck(Long.parseLong(value));
  }
  
  private static long rangeCheck(long value)
    throws NumberFormatException
  {
    if ((value < 0L) || (value > 4294967295L)) {
      throw new NumberFormatException("Value is out of range : " + value);
    }
    return value;
  }
  
  private Object readResolve()
    throws ObjectStreamException
  {
    rangeCheck(this.value);
    UInteger cached;
    if ((cached = getCached(this.value)) != null) {
      return cached;
    }
    return this;
  }
  
  public int intValue()
  {
    return (int)this.value;
  }
  
  public long longValue()
  {
    return this.value;
  }
  
  public float floatValue()
  {
    return (float)this.value;
  }
  
  public double doubleValue()
  {
    return this.value;
  }
  
  public int hashCode()
  {
    return Long.valueOf(this.value).hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if ((obj instanceof UInteger)) {
      return this.value == ((UInteger)obj).value;
    }
    return false;
  }
  
  public String toString()
  {
    return Long.valueOf(this.value).toString();
  }
  
  public int compareTo(UInteger o)
  {
    return this.value == o.value ? 0 : this.value < o.value ? -1 : 1;
  }
}

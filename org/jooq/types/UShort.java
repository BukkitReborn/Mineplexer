package org.jooq.types;

public final class UShort
  extends UNumber
  implements Comparable<UShort>
{
  private static final long serialVersionUID = -6821055240959745390L;
  public static final int MIN_VALUE = 0;
  public static final int MAX_VALUE = 65535;
  private final int value;
  
  public static UShort valueOf(String value)
    throws NumberFormatException
  {
    return new UShort(value);
  }
  
  public static UShort valueOf(short value)
  {
    return new UShort(value);
  }
  
  public static UShort valueOf(int value)
    throws NumberFormatException
  {
    return new UShort(value);
  }
  
  private UShort(int value)
    throws NumberFormatException
  {
    this.value = value;
    rangeCheck();
  }
  
  private UShort(short value)
  {
    this.value = (value & 0xFFFF);
  }
  
  private UShort(String value)
    throws NumberFormatException
  {
    this.value = Integer.parseInt(value);
    rangeCheck();
  }
  
  private void rangeCheck()
    throws NumberFormatException
  {
    if ((this.value < 0) || (this.value > 65535)) {
      throw new NumberFormatException("Value is out of range : " + this.value);
    }
  }
  
  public int intValue()
  {
    return this.value;
  }
  
  public long longValue()
  {
    return this.value;
  }
  
  public float floatValue()
  {
    return this.value;
  }
  
  public double doubleValue()
  {
    return this.value;
  }
  
  public int hashCode()
  {
    return Integer.valueOf(this.value).hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if ((obj instanceof UShort)) {
      return this.value == ((UShort)obj).value;
    }
    return false;
  }
  
  public String toString()
  {
    return Integer.valueOf(this.value).toString();
  }
  
  public int compareTo(UShort o)
  {
    return this.value == o.value ? 0 : this.value < o.value ? -1 : 1;
  }
}

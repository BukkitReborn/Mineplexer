package org.jooq.types;

import java.math.BigInteger;

public final class ULong
  extends UNumber
  implements Comparable<ULong>
{
  private static final long serialVersionUID = -6821055240959745390L;
  public static final BigInteger MIN_VALUE = BigInteger.ZERO;
  public static final BigInteger MAX_VALUE = new BigInteger("18446744073709551615");
  public static final BigInteger MAX_VALUE_LONG = new BigInteger("9223372036854775808");
  private final BigInteger value;
  
  public static ULong valueOf(String value)
    throws NumberFormatException
  {
    return new ULong(value);
  }
  
  public static ULong valueOf(long value)
  {
    return new ULong(value);
  }
  
  public static ULong valueOf(BigInteger value)
    throws NumberFormatException
  {
    return new ULong(value);
  }
  
  private ULong(BigInteger value)
    throws NumberFormatException
  {
    this.value = value;
    rangeCheck();
  }
  
  private ULong(long value)
  {
    if (value >= 0L) {
      this.value = BigInteger.valueOf(value);
    } else {
      this.value = BigInteger.valueOf(value & 0x7FFFFFFFFFFFFFFF).add(MAX_VALUE_LONG);
    }
  }
  
  private ULong(String value)
    throws NumberFormatException
  {
    this.value = new BigInteger(value);
    rangeCheck();
  }
  
  private void rangeCheck()
    throws NumberFormatException
  {
    if ((this.value.compareTo(MIN_VALUE) < 0) || (this.value.compareTo(MAX_VALUE) > 0)) {
      throw new NumberFormatException("Value is out of range : " + this.value);
    }
  }
  
  public int intValue()
  {
    return this.value.intValue();
  }
  
  public long longValue()
  {
    return this.value.longValue();
  }
  
  public float floatValue()
  {
    return this.value.floatValue();
  }
  
  public double doubleValue()
  {
    return this.value.doubleValue();
  }
  
  public int hashCode()
  {
    return this.value.hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if ((obj instanceof ULong)) {
      return this.value.equals(((ULong)obj).value);
    }
    return false;
  }
  
  public String toString()
  {
    return this.value.toString();
  }
  
  public int compareTo(ULong o)
  {
    return this.value.compareTo(o.value);
  }
}

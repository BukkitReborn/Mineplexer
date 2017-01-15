package org.jooq.types;

import java.io.ObjectStreamException;
import java.math.BigInteger;

public final class UByte
  extends UNumber
  implements Comparable<UByte>
{
  private static final long serialVersionUID = -6821055240959745390L;
  private static final UByte[] VALUES = ;
  public static final short MIN_VALUE = 0;
  public static final short MAX_VALUE = 255;
  private final short value;
  
  private static final UByte[] mkValues()
  {
    UByte[] ret = new UByte['Ā'];
    for (int i = -128; i <= 127; i++) {
      ret[(i & 0xFF)] = new UByte((byte)i);
    }
    return ret;
  }
  
  public static UByte valueOf(String value)
    throws NumberFormatException
  {
    return valueOfUnchecked(rangeCheck(Short.parseShort(value)));
  }
  
  public static UByte valueOf(byte value)
  {
    return valueOfUnchecked((short)(value & 0xFF));
  }
  
  private static UByte valueOfUnchecked(short value)
    throws NumberFormatException
  {
    return VALUES[(value & 0xFF)];
  }
  
  public static UByte valueOf(short value)
    throws NumberFormatException
  {
    return valueOfUnchecked(rangeCheck(value));
  }
  
  public static UByte valueOf(int value)
    throws NumberFormatException
  {
    return valueOfUnchecked(rangeCheck(value));
  }
  
  public static UByte valueOf(long value)
    throws NumberFormatException
  {
    return valueOfUnchecked(rangeCheck(value));
  }
  
  private UByte(long value)
    throws NumberFormatException
  {
    this.value = rangeCheck(value);
  }
  
  private UByte(int value)
    throws NumberFormatException
  {
    this.value = rangeCheck(value);
  }
  
  private UByte(short value)
    throws NumberFormatException
  {
    this.value = rangeCheck(value);
  }
  
  private UByte(byte value)
  {
    this.value = ((short)(value & 0xFF));
  }
  
  private UByte(String value)
    throws NumberFormatException
  {
    this.value = rangeCheck(Short.parseShort(value));
  }
  
  private static short rangeCheck(short value)
    throws NumberFormatException
  {
    if ((value < 0) || (value > 255)) {
      throw new NumberFormatException("Value is out of range : " + value);
    }
    return value;
  }
  
  private static short rangeCheck(int value)
    throws NumberFormatException
  {
    if ((value < 0) || (value > 255)) {
      throw new NumberFormatException("Value is out of range : " + value);
    }
    return (short)value;
  }
  
  private static short rangeCheck(long value)
    throws NumberFormatException
  {
    if ((value < 0L) || (value > 255L)) {
      throw new NumberFormatException("Value is out of range : " + value);
    }
    return (short)(int)value;
  }
  
  private Object readResolve()
    throws ObjectStreamException
  {
    return valueOf(this.value);
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
    return Short.valueOf(this.value).hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if ((obj instanceof UByte)) {
      return this.value == ((UByte)obj).value;
    }
    return false;
  }
  
  public String toString()
  {
    return Short.valueOf(this.value).toString();
  }
  
  public int compareTo(UByte o)
  {
    return this.value == o.value ? 0 : this.value < o.value ? -1 : 1;
  }
  
  public BigInteger toBigInteger()
  {
    return BigInteger.valueOf(this.value);
  }
}

package org.jooq.util.postgres;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.jooq.exception.DataTypeException;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.DayToSecond;
import org.jooq.types.YearToMonth;

public class PostgresUtils
{
  private static final String POSTGRESQL_HEX_STRING_PREFIX = "\\x";
  private static final int PG_OBJECT_INIT = 0;
  private static final int PG_OBJECT_BEFORE_VALUE = 1;
  private static final int PG_OBJECT_QUOTED_VALUE = 2;
  private static final int PG_OBJECT_UNQUOTED_VALUE = 3;
  private static final int PG_OBJECT_AFTER_VALUE = 4;
  private static final int PG_OBJECT_END = 5;
  
  public static byte[] toBytes(String string)
  {
    if (string.startsWith("\\x")) {
      return toBytesFromHexEncoding(string);
    }
    return toBytesFromOctalEncoding(string);
  }
  
  private static byte[] toBytesFromOctalEncoding(String string)
  {
    Reader reader = new StringReader(string);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try
    {
      convertOctalToBytes(reader, bytes);
      return bytes.toByteArray();
    }
    catch (IOException x)
    {
      throw new DataTypeException("failed to parse octal hex string: " + x.getMessage(), x);
    }
  }
  
  private static void convertOctalToBytes(Reader reader, OutputStream bytes)
    throws IOException
  {
    int ch;
    while ((ch = reader.read()) != -1) {
      if (ch == 92)
      {
        ch = reader.read();
        if (ch == -1) {
          throw new DataTypeException("unexpected end of stream after initial backslash");
        }
        if (ch == 92)
        {
          bytes.write(92);
        }
        else
        {
          int val = octalValue(ch);
          ch = reader.read();
          if (ch == -1) {
            throw new DataTypeException("unexpected end of octal value");
          }
          val <<= 3;
          val += octalValue(ch);
          ch = reader.read();
          if (ch == -1) {
            throw new DataTypeException("unexpected end of octal value");
          }
          val <<= 3;
          val += octalValue(ch);
          bytes.write(val);
        }
      }
      else
      {
        bytes.write(ch);
      }
    }
  }
  
  private static byte[] toBytesFromHexEncoding(String string)
  {
    String hex = string.substring("\\x".length());
    
    StringReader input = new StringReader(hex);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream(hex.length() / 2);
    try
    {
      int hexDigit;
      while ((hexDigit = input.read()) != -1)
      {
        int byteValue = hexValue(hexDigit) << 4;
        if ((hexDigit = input.read()) == -1) {
          break;
        }
        byteValue += hexValue(hexDigit);
        bytes.write(byteValue);
      }
    }
    catch (IOException e)
    {
      throw new DataTypeException("Error while decoding hex string", e);
    }
    int hexDigit;
    input.close();
    return bytes.toByteArray();
  }
  
  private static int hexValue(int hexDigit)
  {
    if ((hexDigit >= 48) && (hexDigit <= 57)) {
      return hexDigit - 48;
    }
    if ((hexDigit >= 97) && (hexDigit <= 102)) {
      return hexDigit - 97 + 10;
    }
    if ((hexDigit >= 65) && (hexDigit <= 70)) {
      return hexDigit - 65 + 10;
    }
    throw new DataTypeException("unknown postgresql character format for hexValue: " + hexDigit);
  }
  
  private static int octalValue(int octalDigit)
  {
    if ((octalDigit < 48) || (octalDigit > 55)) {
      throw new DataTypeException("unknown postgresql character format for octalValue: " + octalDigit);
    }
    return octalDigit - 48;
  }
  
  public static Object toPGInterval(DayToSecond interval)
  {
    return Reflect.on("org.postgresql.util.PGInterval").create(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(interval.getSign() * interval.getDays()), Integer.valueOf(interval.getSign() * interval.getHours()), Integer.valueOf(interval.getSign() * interval.getMinutes()), Double.valueOf(interval.getSign() * interval.getSeconds() + interval.getSign() * interval.getNano() / 1.0E9D) }).get();
  }
  
  public static Object toPGInterval(YearToMonth interval)
  {
    return Reflect.on("org.postgresql.util.PGInterval").create(new Object[] { Integer.valueOf(interval.getSign() * interval.getYears()), Integer.valueOf(interval.getSign() * interval.getMonths()), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Double.valueOf(0.0D) }).get();
  }
  
  public static DayToSecond toDayToSecond(Object pgInterval)
  {
    boolean negative = pgInterval.toString().contains("-");
    
    Reflect i = Reflect.on(pgInterval);
    if (negative) {
      i.call("scale", new Object[] { Integer.valueOf(-1) });
    }
    Double seconds = (Double)i.call("getSeconds").get();
    
    DayToSecond result = new DayToSecond(((Integer)i.call("getDays").get()).intValue(), ((Integer)i.call("getHours").get()).intValue(), ((Integer)i.call("getMinutes").get()).intValue(), seconds.intValue(), (int)(1.0E9D * (seconds.doubleValue() - seconds.intValue())));
    if (negative) {
      result = result.neg();
    }
    return result;
  }
  
  public static YearToMonth toYearToMonth(Object pgInterval)
  {
    boolean negative = pgInterval.toString().contains("-");
    
    Reflect i = Reflect.on(pgInterval);
    if (negative) {
      i.call("scale", new Object[] { Integer.valueOf(-1) });
    }
    YearToMonth result = new YearToMonth(((Integer)i.call("getYears").get()).intValue(), ((Integer)i.call("getMonths").get()).intValue());
    if (negative) {
      result = result.neg();
    }
    return result;
  }
  
  public static List<String> toPGObject(String input)
  {
    List<String> values = new ArrayList();
    int i = 0;
    int state = 0;
    StringBuilder sb = null;
    while (i < input.length())
    {
      char c = input.charAt(i);
      switch (state)
      {
      case 0: 
        if (c == '(') {
          state = 1;
        }
        break;
      case 1: 
        sb = new StringBuilder();
        if (c == ',')
        {
          values.add(null);
          state = 1;
        }
        else if (c == ')')
        {
          values.add(null);
          state = 5;
        }
        else if (c == '"')
        {
          state = 2;
        }
        else if (((c == 'n') || (c == 'N')) && (i + 4 < input.length()) && 
          (input.substring(i, i + 4).equalsIgnoreCase("null")))
        {
          values.add(null);
          i += 3;
          state = 4;
        }
        else
        {
          sb.append(c);
          state = 3;
        }
        break;
      case 2: 
        if (c == '"')
        {
          if (input.charAt(i + 1) == '"')
          {
            sb.append(c);
            i++;
          }
          else
          {
            values.add(sb.toString());
            state = 4;
          }
        }
        else if (c == '\\')
        {
          if (input.charAt(i + 1) == '\\')
          {
            sb.append(c);
            i++;
          }
          else
          {
            sb.append(c);
          }
        }
        else {
          sb.append(c);
        }
        break;
      case 3: 
        if (c == ')')
        {
          values.add(sb.toString());
          state = 5;
        }
        else if (c == ',')
        {
          values.add(sb.toString());
          state = 1;
        }
        else
        {
          sb.append(c);
        }
        break;
      case 4: 
        if (c == ')') {
          state = 5;
        } else if (c == ',') {
          state = 1;
        }
        break;
      }
      i++;
    }
    return values;
  }
  
  public static String toPGArrayString(Object[] value)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    
    String separator = "";
    for (Object o : value)
    {
      sb.append(separator);
      if (o == null)
      {
        sb.append(o);
      }
      else
      {
        sb.append("\"");
        sb.append(o.toString().replace("\\", "\\\\").replace("\"", "\\\""));
        sb.append("\"");
      }
      separator = ", ";
    }
    sb.append("}");
    return sb.toString();
  }
}

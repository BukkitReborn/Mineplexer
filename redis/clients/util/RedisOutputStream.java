package redis.clients.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class RedisOutputStream
  extends FilterOutputStream
{
  protected final byte[] buf;
  protected int count;
  
  public RedisOutputStream(OutputStream out)
  {
    this(out, 8192);
  }
  
  public RedisOutputStream(OutputStream out, int size)
  {
    super(out);
    if (size <= 0) {
      throw new IllegalArgumentException("Buffer size <= 0");
    }
    this.buf = new byte[size];
  }
  
  private void flushBuffer()
    throws IOException
  {
    if (this.count > 0)
    {
      this.out.write(this.buf, 0, this.count);
      this.count = 0;
    }
  }
  
  public void write(byte b)
    throws IOException
  {
    this.buf[(this.count++)] = b;
    if (this.count == this.buf.length) {
      flushBuffer();
    }
  }
  
  public void write(byte[] b)
    throws IOException
  {
    write(b, 0, b.length);
  }
  
  public void write(byte[] b, int off, int len)
    throws IOException
  {
    if (len >= this.buf.length)
    {
      flushBuffer();
      this.out.write(b, off, len);
    }
    else
    {
      if (len >= this.buf.length - this.count) {
        flushBuffer();
      }
      System.arraycopy(b, off, this.buf, this.count, len);
      this.count += len;
    }
  }
  
  public void writeAsciiCrLf(String in)
    throws IOException
  {
    int size = in.length();
    for (int i = 0; i != size; i++)
    {
      this.buf[(this.count++)] = ((byte)in.charAt(i));
      if (this.count == this.buf.length) {
        flushBuffer();
      }
    }
    writeCrLf();
  }
  
  public static boolean isSurrogate(char ch)
  {
    return (ch >= 55296) && (ch <= 57343);
  }
  
  public static int utf8Length(String str)
  {
    int strLen = str.length();int utfLen = 0;
    for (int i = 0; i != strLen; i++)
    {
      char c = str.charAt(i);
      if (c < '')
      {
        utfLen++;
      }
      else if (c < 'ࠀ')
      {
        utfLen += 2;
      }
      else if (isSurrogate(c))
      {
        i++;
        utfLen += 4;
      }
      else
      {
        utfLen += 3;
      }
    }
    return utfLen;
  }
  
  public void writeCrLf()
    throws IOException
  {
    if (2 >= this.buf.length - this.count) {
      flushBuffer();
    }
    this.buf[(this.count++)] = 13;
    this.buf[(this.count++)] = 10;
  }
  
  public void writeUtf8CrLf(String str)
    throws IOException
  {
    int strLen = str.length();
    for (int i = 0; i < strLen; i++)
    {
      char c = str.charAt(i);
      if (c >= '') {
        break;
      }
      this.buf[(this.count++)] = ((byte)c);
      if (this.count == this.buf.length) {
        flushBuffer();
      }
    }
    for (; i < strLen; i++)
    {
      char c = str.charAt(i);
      if (c < '')
      {
        this.buf[(this.count++)] = ((byte)c);
        if (this.count == this.buf.length) {
          flushBuffer();
        }
      }
      else if (c < 'ࠀ')
      {
        if (2 >= this.buf.length - this.count) {
          flushBuffer();
        }
        this.buf[(this.count++)] = ((byte)(0xC0 | c >> '\006'));
        this.buf[(this.count++)] = ((byte)(0x80 | c & 0x3F));
      }
      else if (isSurrogate(c))
      {
        if (4 >= this.buf.length - this.count) {
          flushBuffer();
        }
        int uc = Character.toCodePoint(c, str.charAt(i++));
        this.buf[(this.count++)] = ((byte)(0xF0 | uc >> 18));
        this.buf[(this.count++)] = ((byte)(0x80 | uc >> 12 & 0x3F));
        this.buf[(this.count++)] = ((byte)(0x80 | uc >> 6 & 0x3F));
        this.buf[(this.count++)] = ((byte)(0x80 | uc & 0x3F));
      }
      else
      {
        if (3 >= this.buf.length - this.count) {
          flushBuffer();
        }
        this.buf[(this.count++)] = ((byte)(0xE0 | c >> '\f'));
        this.buf[(this.count++)] = ((byte)(0x80 | c >> '\006' & 0x3F));
        this.buf[(this.count++)] = ((byte)(0x80 | c & 0x3F));
      }
    }
    writeCrLf();
  }
  
  private static final int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };
  private static final byte[] DigitTens = { 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57 };
  private static final byte[] DigitOnes = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 };
  private static final byte[] digits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
  
  public void writeIntCrLf(int value)
    throws IOException
  {
    if (value < 0)
    {
      write((byte)45);
      value = -value;
    }
    int size = 0;
    while (value > sizeTable[size]) {
      size++;
    }
    size++;
    if (size >= this.buf.length - this.count) {
      flushBuffer();
    }
    int charPos = this.count + size;
    while (value >= 65536)
    {
      int q = value / 100;
      int r = value - ((q << 6) + (q << 5) + (q << 2));
      value = q;
      this.buf[(--charPos)] = DigitOnes[r];
      this.buf[(--charPos)] = DigitTens[r];
    }
    for (;;)
    {
      int q = value * 52429 >>> 19;
      int r = value - ((q << 3) + (q << 1));
      this.buf[(--charPos)] = digits[r];
      value = q;
      if (value == 0) {
        break;
      }
    }
    this.count += size;
    
    writeCrLf();
  }
  
  public void flush()
    throws IOException
  {
    flushBuffer();
    this.out.flush();
  }
}

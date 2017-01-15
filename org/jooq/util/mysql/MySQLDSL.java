package org.jooq.util.mysql;

import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.impl.DSL;

public class MySQLDSL
  extends DSL
{
  public static Field<String> decode(String cryptString, String keyString)
  {
    return decode(val(cryptString), val(keyString));
  }
  
  public static Field<byte[]> decode(byte[] cryptString, byte[] keyString)
  {
    return decode(val(cryptString), val(keyString));
  }
  
  public static <T> Field<T> decode(Field<T> cryptString, Field<T> keyString)
  {
    return function("decode", cryptString.getType(), new Field[] { cryptString, keyString });
  }
  
  public static Field<String> encode(String string, String keyString)
  {
    return encode(val(string), val(keyString));
  }
  
  public static Field<byte[]> encode(byte[] string, byte[] keyString)
  {
    return encode(val(string), val(keyString));
  }
  
  public static <T> Field<T> encode(Field<T> string, Field<T> keyString)
  {
    return function("encode", string.getType(), new Field[] { string, keyString });
  }
  
  public static Field<String> aesDecrypt(String cryptString, String keyString)
  {
    return aesDecrypt(val(cryptString), val(keyString));
  }
  
  public static Field<byte[]> aesDecrypt(byte[] cryptString, byte[] keyString)
  {
    return aesDecrypt(val(cryptString), val(keyString));
  }
  
  public static <T> Field<T> aesDecrypt(Field<T> cryptString, Field<T> keyString)
  {
    return function("aes_decrypt", cryptString.getType(), new Field[] { cryptString, keyString });
  }
  
  public static Field<String> aesEncrypt(String string, String keyString)
  {
    return aesEncrypt(val(string), val(keyString));
  }
  
  public static Field<byte[]> aesEncrypt(byte[] string, byte[] keyString)
  {
    return aesEncrypt(val(string), val(keyString));
  }
  
  public static <T> Field<T> aesEncrypt(Field<T> string, Field<T> keyString)
  {
    return function("aes_encrypt", string.getType(), new Field[] { string, keyString });
  }
  
  public static Field<String> desDecrypt(String cryptString)
  {
    return desDecrypt(val(cryptString));
  }
  
  public static Field<byte[]> desDecrypt(byte[] cryptString)
  {
    return desDecrypt(val(cryptString));
  }
  
  public static <T> Field<T> desDecrypt(Field<T> cryptString)
  {
    return function("des_decrypt", cryptString.getType(), new Field[] { cryptString });
  }
  
  public static Field<String> desDecrypt(String cryptString, String keyString)
  {
    return desDecrypt(val(cryptString), val(keyString));
  }
  
  public static Field<byte[]> desDecrypt(byte[] cryptString, byte[] keyString)
  {
    return desDecrypt(val(cryptString), val(keyString));
  }
  
  public static <T> Field<T> desDecrypt(Field<T> cryptString, Field<T> keyString)
  {
    return function("des_decrypt", cryptString.getType(), new Field[] { cryptString, keyString });
  }
  
  public static Field<String> desEncrypt(String string)
  {
    return desEncrypt(val(string));
  }
  
  public static Field<byte[]> desEncrypt(byte[] string)
  {
    return desEncrypt(val(string));
  }
  
  public static <T> Field<T> desEncrypt(Field<T> string)
  {
    return function("des_encrypt", string.getType(), new Field[] { string });
  }
  
  public static Field<String> desEncrypt(String string, String keyString)
  {
    return desEncrypt(val(string), val(keyString));
  }
  
  public static Field<byte[]> desEncrypt(byte[] string, byte[] keyString)
  {
    return desEncrypt(val(string), val(keyString));
  }
  
  public static <T> Field<T> desEncrypt(Field<T> string, Field<T> keyString)
  {
    return function("des_encrypt", string.getType(), new Field[] { string, keyString });
  }
  
  public static Field<String> compress(String string)
  {
    return compress(val(string));
  }
  
  public static Field<byte[]> compress(byte[] string)
  {
    return compress(val(string));
  }
  
  public static <T> Field<T> compress(Field<T> string)
  {
    return function("compress", string.getType(), new Field[] { string });
  }
  
  public static Field<String> uncompress(String string)
  {
    return uncompress(val(string));
  }
  
  public static Field<byte[]> uncompress(byte[] string)
  {
    return uncompress(val(string));
  }
  
  public static <T> Field<T> uncompress(Field<T> string)
  {
    return function("uncompress", string.getType(), new Field[] { string });
  }
  
  public static Field<Integer> uncompressedLength(String string)
  {
    return uncompressedLength(val(string));
  }
  
  public static Field<Integer> uncompressedLength(byte[] string)
  {
    return uncompressedLength(val(string));
  }
  
  public static <T> Field<Integer> uncompressedLength(Field<T> string)
  {
    return function("uncompressed_length", Integer.class, new Field[] { string });
  }
  
  public static Field<String> sha1(String string)
  {
    return sha1(val(string));
  }
  
  public static Field<byte[]> sha1(byte[] string)
  {
    return sha1(val(string));
  }
  
  public static <T> Field<T> sha1(Field<T> string)
  {
    return function("sha1", string.getType(), new Field[] { string });
  }
  
  public static Field<String> sha2(String string, int hashLength)
  {
    return sha2(val(string), val(Integer.valueOf(hashLength)));
  }
  
  public static Field<byte[]> sha2(byte[] string, int hashLength)
  {
    return sha2(val(string), val(Integer.valueOf(hashLength)));
  }
  
  public static <T> Field<T> sha2(Field<T> string, Field<Integer> hashLength)
  {
    return function("sha2", string.getType(), new Field[] { string, hashLength });
  }
  
  public static Field<String> password(String string)
  {
    return password(val(string));
  }
  
  public static Field<byte[]> password(byte[] string)
  {
    return password(val(string));
  }
  
  public static <T> Field<T> password(Field<T> string)
  {
    return function("password", string.getType(), new Field[] { string });
  }
  
  public static <E extends Enum<E>,  extends EnumType> E enumType(Class<E> type, int index)
  {
    if (index <= 0) {
      return null;
    }
    E[] values = (Enum[])type.getEnumConstants();
    if (index > values.length) {
      return null;
    }
    return values[(index - 1)];
  }
}

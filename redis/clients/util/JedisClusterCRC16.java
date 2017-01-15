package redis.clients.util;

public class JedisClusterCRC16
{
  public static final int polynomial = 4129;
  
  public static int getSlot(String key)
  {
    int s = key.indexOf("{");
    if (s > -1)
    {
      int e = key.indexOf("}", s + 1);
      if ((e > -1) && (e != s + 1)) {
        key = key.substring(s + 1, e);
      }
    }
    return getCRC16(key) % 16384;
  }
  
  private static int getCRC16(String key)
  {
    int crc = 0;
    for (byte b : key.getBytes()) {
      for (int i = 0; i < 8; i++)
      {
        boolean bit = (b >> 7 - i & 0x1) == 1;
        boolean c15 = (crc >> 15 & 0x1) == 1;
        crc <<= 1;
        if ((c15 ^ bit)) {
          crc ^= 0x1021;
        }
      }
    }
    return crc &= 0xFFFF;
  }
}

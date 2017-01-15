package redis.clients.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract interface Hashing
{
  public static final Hashing MURMUR_HASH = new MurmurHash();
  public static final ThreadLocal<MessageDigest> md5Holder = new ThreadLocal();
  public static final Hashing MD5 = new Hashing()
  {
    public long hash(String key)
    {
      return hash(SafeEncoder.encode(key));
    }
    
    public long hash(byte[] key)
    {
      try
      {
        if (md5Holder.get() == null) {
          md5Holder.set(MessageDigest.getInstance("MD5"));
        }
      }
      catch (NoSuchAlgorithmException e)
      {
        throw new IllegalStateException("++++ no md5 algorythm found");
      }
      MessageDigest md5 = (MessageDigest)md5Holder.get();
      
      md5.reset();
      md5.update(key);
      byte[] bKey = md5.digest();
      long res = (bKey[3] & 0xFF) << 24 | (bKey[2] & 0xFF) << 16 | (bKey[1] & 0xFF) << 8 | bKey[0] & 0xFF;
      
      return res;
    }
  };
  
  public abstract long hash(String paramString);
  
  public abstract long hash(byte[] paramArrayOfByte);
}

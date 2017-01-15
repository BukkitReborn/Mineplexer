package redis.clients.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class JedisByteHashMap
  implements Map<byte[], byte[]>, Cloneable, Serializable
{
  private static final long serialVersionUID = -6971431362627219416L;
  private Map<ByteArrayWrapper, byte[]> internalMap;
  
  public JedisByteHashMap()
  {
    this.internalMap = new HashMap();
  }
  
  public void clear()
  {
    this.internalMap.clear();
  }
  
  public boolean containsKey(Object key)
  {
    if ((key instanceof byte[])) {
      return this.internalMap.containsKey(new ByteArrayWrapper((byte[])key));
    }
    return this.internalMap.containsKey(key);
  }
  
  public boolean containsValue(Object value)
  {
    return this.internalMap.containsValue(value);
  }
  
  public Set<Map.Entry<byte[], byte[]>> entrySet()
  {
    Iterator<Map.Entry<ByteArrayWrapper, byte[]>> iterator = this.internalMap.entrySet().iterator();
    
    HashSet<Map.Entry<byte[], byte[]>> hashSet = new HashSet();
    while (iterator.hasNext())
    {
      Map.Entry<ByteArrayWrapper, byte[]> entry = (Map.Entry)iterator.next();
      hashSet.add(new JedisByteEntry(((ByteArrayWrapper)entry.getKey()).data, (byte[])entry.getValue()));
    }
    return hashSet;
  }
  
  public byte[] get(Object key)
  {
    if ((key instanceof byte[])) {
      return (byte[])this.internalMap.get(new ByteArrayWrapper((byte[])key));
    }
    return (byte[])this.internalMap.get(key);
  }
  
  public boolean isEmpty()
  {
    return this.internalMap.isEmpty();
  }
  
  public Set<byte[]> keySet()
  {
    Set<byte[]> keySet = new HashSet();
    Iterator<ByteArrayWrapper> iterator = this.internalMap.keySet().iterator();
    while (iterator.hasNext()) {
      keySet.add(((ByteArrayWrapper)iterator.next()).data);
    }
    return keySet;
  }
  
  public byte[] put(byte[] key, byte[] value)
  {
    return (byte[])this.internalMap.put(new ByteArrayWrapper(key), value);
  }
  
  public void putAll(Map<? extends byte[], ? extends byte[]> m)
  {
    Iterator<?> iterator = m.entrySet().iterator();
    while (iterator.hasNext())
    {
      Map.Entry<? extends byte[], ? extends byte[]> next = (Map.Entry)iterator.next();
      
      this.internalMap.put(new ByteArrayWrapper((byte[])next.getKey()), next.getValue());
    }
  }
  
  public byte[] remove(Object key)
  {
    if ((key instanceof byte[])) {
      return (byte[])this.internalMap.remove(new ByteArrayWrapper((byte[])key));
    }
    return (byte[])this.internalMap.remove(key);
  }
  
  public int size()
  {
    return this.internalMap.size();
  }
  
  public Collection<byte[]> values()
  {
    return this.internalMap.values();
  }
  
  private static final class ByteArrayWrapper
  {
    private final byte[] data;
    
    public ByteArrayWrapper(byte[] data)
    {
      if (data == null) {
        throw new NullPointerException();
      }
      this.data = data;
    }
    
    public boolean equals(Object other)
    {
      if (!(other instanceof ByteArrayWrapper)) {
        return false;
      }
      return Arrays.equals(this.data, ((ByteArrayWrapper)other).data);
    }
    
    public int hashCode()
    {
      return Arrays.hashCode(this.data);
    }
  }
  
  private static final class JedisByteEntry
    implements Map.Entry<byte[], byte[]>
  {
    private byte[] value;
    private byte[] key;
    
    public JedisByteEntry(byte[] key, byte[] value)
    {
      this.key = key;
      this.value = value;
    }
    
    public byte[] getKey()
    {
      return this.key;
    }
    
    public byte[] getValue()
    {
      return this.value;
    }
    
    public byte[] setValue(byte[] value)
    {
      this.value = value;
      return value;
    }
  }
}

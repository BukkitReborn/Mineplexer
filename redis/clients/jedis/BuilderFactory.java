package redis.clients.jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.util.SafeEncoder;

public class BuilderFactory
{
  public static final Builder<Double> DOUBLE = new Builder()
  {
    public Double build(Object data)
    {
      String asString = (String)BuilderFactory.STRING.build(data);
      return asString == null ? null : Double.valueOf(asString);
    }
    
    public String toString()
    {
      return "double";
    }
  };
  public static final Builder<Boolean> BOOLEAN = new Builder()
  {
    public Boolean build(Object data)
    {
      return Boolean.valueOf(((Long)data).longValue() == 1L);
    }
    
    public String toString()
    {
      return "boolean";
    }
  };
  public static final Builder<byte[]> BYTE_ARRAY = new Builder()
  {
    public byte[] build(Object data)
    {
      return (byte[])data;
    }
    
    public String toString()
    {
      return "byte[]";
    }
  };
  public static final Builder<Long> LONG = new Builder()
  {
    public Long build(Object data)
    {
      return (Long)data;
    }
    
    public String toString()
    {
      return "long";
    }
  };
  public static final Builder<String> STRING = new Builder()
  {
    public String build(Object data)
    {
      return data == null ? null : SafeEncoder.encode((byte[])data);
    }
    
    public String toString()
    {
      return "string";
    }
  };
  public static final Builder<List<String>> STRING_LIST = new Builder()
  {
    public List<String> build(Object data)
    {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List)data;
      ArrayList<String> result = new ArrayList(l.size());
      for (byte[] barray : l) {
        if (barray == null) {
          result.add(null);
        } else {
          result.add(SafeEncoder.encode(barray));
        }
      }
      return result;
    }
    
    public String toString()
    {
      return "List<String>";
    }
  };
  public static final Builder<Map<String, String>> STRING_MAP = new Builder()
  {
    public Map<String, String> build(Object data)
    {
      List<byte[]> flatHash = (List)data;
      Map<String, String> hash = new HashMap();
      Iterator<byte[]> iterator = flatHash.iterator();
      while (iterator.hasNext()) {
        hash.put(SafeEncoder.encode((byte[])iterator.next()), SafeEncoder.encode((byte[])iterator.next()));
      }
      return hash;
    }
    
    public String toString()
    {
      return "Map<String, String>";
    }
  };
  public static final Builder<Set<String>> STRING_SET = new Builder()
  {
    public Set<String> build(Object data)
    {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List)data;
      Set<String> result = new HashSet(l.size());
      for (byte[] barray : l) {
        if (barray == null) {
          result.add(null);
        } else {
          result.add(SafeEncoder.encode(barray));
        }
      }
      return result;
    }
    
    public String toString()
    {
      return "Set<String>";
    }
  };
  public static final Builder<List<byte[]>> BYTE_ARRAY_LIST = new Builder()
  {
    public List<byte[]> build(Object data)
    {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List)data;
      
      return l;
    }
    
    public String toString()
    {
      return "List<byte[]>";
    }
  };
  public static final Builder<Set<byte[]>> BYTE_ARRAY_ZSET = new Builder()
  {
    public Set<byte[]> build(Object data)
    {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List)data;
      Set<byte[]> result = new LinkedHashSet(l);
      for (byte[] barray : l) {
        if (barray == null) {
          result.add(null);
        } else {
          result.add(barray);
        }
      }
      return result;
    }
    
    public String toString()
    {
      return "ZSet<byte[]>";
    }
  };
  public static final Builder<Map<byte[], byte[]>> BYTE_ARRAY_MAP = new Builder()
  {
    public Map<byte[], byte[]> build(Object data)
    {
      List<byte[]> flatHash = (List)data;
      Map<byte[], byte[]> hash = new HashMap();
      Iterator<byte[]> iterator = flatHash.iterator();
      while (iterator.hasNext()) {
        hash.put(iterator.next(), iterator.next());
      }
      return hash;
    }
    
    public String toString()
    {
      return "Map<byte[], byte[]>";
    }
  };
  public static final Builder<Set<String>> STRING_ZSET = new Builder()
  {
    public Set<String> build(Object data)
    {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List)data;
      Set<String> result = new LinkedHashSet(l.size());
      for (byte[] barray : l) {
        if (barray == null) {
          result.add(null);
        } else {
          result.add(SafeEncoder.encode(barray));
        }
      }
      return result;
    }
    
    public String toString()
    {
      return "ZSet<String>";
    }
  };
  public static final Builder<Set<Tuple>> TUPLE_ZSET = new Builder()
  {
    public Set<Tuple> build(Object data)
    {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List)data;
      Set<Tuple> result = new LinkedHashSet(l.size());
      Iterator<byte[]> iterator = l.iterator();
      while (iterator.hasNext()) {
        result.add(new Tuple(SafeEncoder.encode((byte[])iterator.next()), Double.valueOf(SafeEncoder.encode((byte[])iterator.next()))));
      }
      return result;
    }
    
    public String toString()
    {
      return "ZSet<Tuple>";
    }
  };
  public static final Builder<Set<Tuple>> TUPLE_ZSET_BINARY = new Builder()
  {
    public Set<Tuple> build(Object data)
    {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List)data;
      Set<Tuple> result = new LinkedHashSet(l.size());
      Iterator<byte[]> iterator = l.iterator();
      while (iterator.hasNext()) {
        result.add(new Tuple((byte[])iterator.next(), Double.valueOf(SafeEncoder.encode((byte[])iterator.next()))));
      }
      return result;
    }
    
    public String toString()
    {
      return "ZSet<Tuple>";
    }
  };
}

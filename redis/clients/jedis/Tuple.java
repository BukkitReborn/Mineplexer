package redis.clients.jedis;

import java.util.Arrays;
import redis.clients.util.SafeEncoder;

public class Tuple
  implements Comparable<Tuple>
{
  private byte[] element;
  private Double score;
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result;
    if (null != this.element) {
      for (byte b : this.element) {
        result = 31 * result + b;
      }
    }
    long temp = Double.doubleToLongBits(this.score.doubleValue());
    result = 31 * result + (int)(temp ^ temp >>> 32);
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Tuple other = (Tuple)obj;
    if (this.element == null)
    {
      if (other.element != null) {
        return false;
      }
    }
    else if (!Arrays.equals(this.element, other.element)) {
      return false;
    }
    return true;
  }
  
  public int compareTo(Tuple other)
  {
    if (Arrays.equals(this.element, other.element)) {
      return 0;
    }
    return this.score.doubleValue() < other.getScore() ? -1 : 1;
  }
  
  public Tuple(String element, Double score)
  {
    this.element = SafeEncoder.encode(element);
    this.score = score;
  }
  
  public Tuple(byte[] element, Double score)
  {
    this.element = element;
    this.score = score;
  }
  
  public String getElement()
  {
    if (null != this.element) {
      return SafeEncoder.encode(this.element);
    }
    return null;
  }
  
  public byte[] getBinaryElement()
  {
    return this.element;
  }
  
  public double getScore()
  {
    return this.score.doubleValue();
  }
  
  public String toString()
  {
    return '[' + Arrays.toString(this.element) + ',' + this.score + ']';
  }
}

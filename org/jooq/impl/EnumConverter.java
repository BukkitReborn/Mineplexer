package org.jooq.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jooq.Converter;
import org.jooq.tools.Convert;

public class EnumConverter<T, U extends Enum<U>>
  implements Converter<T, U>
{
  private static final long serialVersionUID = -6094337837408829491L;
  private final Class<T> fromType;
  private final Class<U> toType;
  private final Map<T, U> lookup;
  private final EnumType enumType;
  
  public EnumConverter(Class<T> fromType, Class<U> toType)
  {
    this.fromType = fromType;
    this.toType = toType;
    this.enumType = (Number.class.isAssignableFrom(fromType) ? EnumType.ORDINAL : EnumType.STRING);
    
    this.lookup = new LinkedHashMap();
    for (U u : (Enum[])toType.getEnumConstants()) {
      this.lookup.put(to(u), u);
    }
  }
  
  public final U from(T databaseObject)
  {
    return (Enum)this.lookup.get(databaseObject);
  }
  
  public T to(U userObject)
  {
    if (userObject == null) {
      return null;
    }
    if (this.enumType == EnumType.ORDINAL) {
      return (T)Convert.convert(Integer.valueOf(userObject.ordinal()), this.fromType);
    }
    return (T)Convert.convert(userObject.name(), this.fromType);
  }
  
  public final Class<T> fromType()
  {
    return this.fromType;
  }
  
  public final Class<U> toType()
  {
    return this.toType;
  }
  
  static enum EnumType
  {
    ORDINAL,  STRING;
    
    private EnumType() {}
  }
  
  public String toString()
  {
    return "EnumConverter [ from : " + this.fromType.getName() + ", to : " + this.toType.getName() + " ]";
  }
}

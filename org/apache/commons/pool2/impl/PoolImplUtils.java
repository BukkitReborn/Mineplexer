package org.apache.commons.pool2.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.apache.commons.pool2.PooledObjectFactory;

class PoolImplUtils
{
  static Class<?> getFactoryType(Class<? extends PooledObjectFactory> factory)
  {
    return (Class)getGenericType(PooledObjectFactory.class, factory);
  }
  
  private static <T> Object getGenericType(Class<T> type, Class<? extends T> clazz)
  {
    Type[] interfaces = clazz.getGenericInterfaces();
    for (Type iface : interfaces) {
      if ((iface instanceof ParameterizedType))
      {
        ParameterizedType pi = (ParameterizedType)iface;
        if (((pi.getRawType() instanceof Class)) && 
          (type.isAssignableFrom((Class)pi.getRawType()))) {
          return getTypeParameter(clazz, pi.getActualTypeArguments()[0]);
        }
      }
    }
    Class<? extends T> superClazz = clazz.getSuperclass();
    
    Object result = getGenericType(type, superClazz);
    if ((result instanceof Class)) {
      return result;
    }
    if ((result instanceof Integer))
    {
      ParameterizedType superClassType = (ParameterizedType)clazz.getGenericSuperclass();
      
      return getTypeParameter(clazz, superClassType.getActualTypeArguments()[((Integer)result).intValue()]);
    }
    return null;
  }
  
  private static Object getTypeParameter(Class<?> clazz, Type argType)
  {
    if ((argType instanceof Class)) {
      return argType;
    }
    TypeVariable<?>[] tvs = clazz.getTypeParameters();
    for (int i = 0; i < tvs.length; i++) {
      if (tvs[i].equals(argType)) {
        return Integer.valueOf(i);
      }
    }
    return null;
  }
}

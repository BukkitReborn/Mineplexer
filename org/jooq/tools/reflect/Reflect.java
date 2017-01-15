package org.jooq.tools.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class Reflect
{
  private final Object object;
  private final boolean isClass;
  
  public static Reflect on(String name)
    throws ReflectException
  {
    return on(forName(name));
  }
  
  public static Reflect on(Class<?> clazz)
  {
    return new Reflect(clazz);
  }
  
  public static Reflect on(Object object)
  {
    return new Reflect(object);
  }
  
  public static <T extends AccessibleObject> T accessible(T accessible)
  {
    if (accessible == null) {
      return null;
    }
    if ((accessible instanceof Member))
    {
      Member member = (Member)accessible;
      if ((Modifier.isPublic(member.getModifiers())) && 
        (Modifier.isPublic(member.getDeclaringClass().getModifiers()))) {
        return accessible;
      }
    }
    if (!accessible.isAccessible()) {
      accessible.setAccessible(true);
    }
    return accessible;
  }
  
  private Reflect(Class<?> type)
  {
    this.object = type;
    this.isClass = true;
  }
  
  private Reflect(Object object)
  {
    this.object = object;
    this.isClass = false;
  }
  
  public <T> T get()
  {
    return (T)this.object;
  }
  
  public Reflect set(String name, Object value)
    throws ReflectException
  {
    try
    {
      Field field = type().getField(name);
      field.set(this.object, unwrap(value));
      return this;
    }
    catch (Exception e1)
    {
      try
      {
        ((Field)accessible(type().getDeclaredField(name))).set(this.object, unwrap(value));
        return this;
      }
      catch (Exception e2)
      {
        throw new ReflectException(e2);
      }
    }
  }
  
  public <T> T get(String name)
    throws ReflectException
  {
    return (T)field(name).get();
  }
  
  public Reflect field(String name)
    throws ReflectException
  {
    try
    {
      Field field = type().getField(name);
      return on(field.get(this.object));
    }
    catch (Exception e1)
    {
      try
      {
        return on(((Field)accessible(type().getDeclaredField(name))).get(this.object));
      }
      catch (Exception e2)
      {
        throw new ReflectException(e2);
      }
    }
  }
  
  public Map<String, Reflect> fields()
  {
    Map<String, Reflect> result = new LinkedHashMap();
    for (Field field : type().getFields()) {
      if ((!this.isClass ^ Modifier.isStatic(field.getModifiers())))
      {
        String name = field.getName();
        result.put(name, field(name));
      }
    }
    return result;
  }
  
  public Reflect call(String name)
    throws ReflectException
  {
    return call(name, new Object[0]);
  }
  
  public Reflect call(String name, Object... args)
    throws ReflectException
  {
    Class<?>[] types = types(args);
    try
    {
      Method method = type().getMethod(name, types);
      return on(method, this.object, args);
    }
    catch (NoSuchMethodException e)
    {
      for (Method method : type().getMethods()) {
        if ((method.getName().equals(name)) && (match(method.getParameterTypes(), types))) {
          return on(method, this.object, args);
        }
      }
      throw new ReflectException(e);
    }
  }
  
  public Reflect create()
    throws ReflectException
  {
    return create(new Object[0]);
  }
  
  public Reflect create(Object... args)
    throws ReflectException
  {
    Class<?>[] types = types(args);
    try
    {
      Constructor<?> constructor = type().getConstructor(types);
      return on(constructor, args);
    }
    catch (NoSuchMethodException e)
    {
      for (Constructor<?> constructor : type().getConstructors()) {
        if (match(constructor.getParameterTypes(), types)) {
          return on(constructor, args);
        }
      }
      throw new ReflectException(e);
    }
  }
  
  public <P> P as(Class<P> proxyType)
  {
    final boolean isMap = this.object instanceof Map;
    InvocationHandler handler = new InvocationHandler()
    {
      public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
      {
        String name = method.getName();
        try
        {
          return Reflect.on(Reflect.this.object).call(name, args).get();
        }
        catch (ReflectException e)
        {
          if (isMap)
          {
            Map<String, Object> map = (Map)Reflect.this.object;
            int length = args == null ? 0 : args.length;
            if ((length == 0) && (name.startsWith("get"))) {
              return map.get(Reflect.property(name.substring(3)));
            }
            if ((length == 0) && (name.startsWith("is"))) {
              return map.get(Reflect.property(name.substring(2)));
            }
            if ((length == 1) && (name.startsWith("set")))
            {
              map.put(Reflect.property(name.substring(3)), args[0]);
              return null;
            }
          }
          throw e;
        }
      }
    };
    return (P)Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[] { proxyType }, handler);
  }
  
  private static String property(String string)
  {
    int length = string.length();
    if (length == 0) {
      return "";
    }
    if (length == 1) {
      return string.toLowerCase();
    }
    return string.substring(0, 1).toLowerCase() + string.substring(1);
  }
  
  private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes)
  {
    if (declaredTypes.length == actualTypes.length)
    {
      for (int i = 0; i < actualTypes.length; i++) {
        if (!wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i]))) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public int hashCode()
  {
    return this.object.hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof Reflect)) {
      return this.object.equals(((Reflect)obj).get());
    }
    return false;
  }
  
  public String toString()
  {
    return this.object.toString();
  }
  
  private static Reflect on(Constructor<?> constructor, Object... args)
    throws ReflectException
  {
    try
    {
      return on(constructor.newInstance(args));
    }
    catch (Exception e)
    {
      throw new ReflectException(e);
    }
  }
  
  private static Reflect on(Method method, Object object, Object... args)
    throws ReflectException
  {
    try
    {
      accessible(method);
      if (method.getReturnType() == Void.TYPE)
      {
        method.invoke(object, args);
        return on(object);
      }
      return on(method.invoke(object, args));
    }
    catch (Exception e)
    {
      throw new ReflectException(e);
    }
  }
  
  private static Object unwrap(Object object)
  {
    if ((object instanceof Reflect)) {
      return ((Reflect)object).get();
    }
    return object;
  }
  
  private static Class<?>[] types(Object... values)
  {
    if (values == null) {
      return new Class[0];
    }
    Class<?>[] result = new Class[values.length];
    for (int i = 0; i < values.length; i++)
    {
      Object value = values[i];
      result[i] = (value == null ? Object.class : value.getClass());
    }
    return result;
  }
  
  private static Class<?> forName(String name)
    throws ReflectException
  {
    try
    {
      return Class.forName(name);
    }
    catch (Exception e)
    {
      throw new ReflectException(e);
    }
  }
  
  public Class<?> type()
  {
    if (this.isClass) {
      return (Class)this.object;
    }
    return this.object.getClass();
  }
  
  public static Class<?> wrapper(Class<?> type)
  {
    if (type == null) {
      return null;
    }
    if (type.isPrimitive())
    {
      if (Boolean.TYPE == type) {
        return Boolean.class;
      }
      if (Integer.TYPE == type) {
        return Integer.class;
      }
      if (Long.TYPE == type) {
        return Long.class;
      }
      if (Short.TYPE == type) {
        return Short.class;
      }
      if (Byte.TYPE == type) {
        return Byte.class;
      }
      if (Double.TYPE == type) {
        return Double.class;
      }
      if (Float.TYPE == type) {
        return Float.class;
      }
      if (Character.TYPE == type) {
        return Character.class;
      }
      if (Void.TYPE == type) {
        return Void.class;
      }
    }
    return type;
  }
}

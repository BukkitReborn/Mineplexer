package org.jooq;

public class Converters<T, U>
  implements Converter<T, U>
{
  private static final long serialVersionUID = -4307758248063822630L;
  final Converter[] chain;
  
  public static <T, U> Converter<T, U> of()
  {
    return new Converters(new Converter[0]);
  }
  
  public static <T, U> Converter<T, U> of(Converter<T, U> converter)
  {
    return new Converters(new Converter[] { converter });
  }
  
  public static <T, X1, U> Converter<T, U> of(Converter<T, X1> c1, Converter<X1, U> c2)
  {
    return new Converters(new Converter[] { c1, c2 });
  }
  
  public static <T, X1, X2, U> Converter<T, U> of(Converter<T, X1> c1, Converter<X1, X2> c2, Converter<X2, U> c3)
  {
    return new Converters(new Converter[] { c1, c2, c3 });
  }
  
  public static <T, X1, X2, X3, U> Converter<T, U> of(Converter<T, X1> c1, Converter<X1, X2> c2, Converter<X2, X3> c3, Converter<X3, U> c4)
  {
    return new Converters(new Converter[] { c1, c2, c3, c4 });
  }
  
  Converters(Converter... chain)
  {
    this.chain = (chain == null ? new Converter[0] : chain);
  }
  
  public final U from(T t)
  {
    Object result = t;
    for (int i = 0; i < this.chain.length; i++) {
      result = this.chain[i].from(result);
    }
    return (U)result;
  }
  
  public final T to(U u)
  {
    Object result = u;
    for (int i = this.chain.length - 1; i >= 0; i--) {
      result = this.chain[i].to(result);
    }
    return (T)result;
  }
  
  public final Class<T> fromType()
  {
    return this.chain[0].fromType();
  }
  
  public final Class<U> toType()
  {
    return this.chain[(this.chain.length - 1)].toType();
  }
}

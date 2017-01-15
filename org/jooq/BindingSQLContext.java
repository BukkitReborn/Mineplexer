package org.jooq;

public abstract interface BindingSQLContext<U>
  extends Scope
{
  public abstract RenderContext render();
  
  public abstract U value();
  
  public abstract String variable();
  
  public abstract <T> BindingSQLContext<T> convert(Converter<T, U> paramConverter);
}

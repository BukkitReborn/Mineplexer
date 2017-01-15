package org.jooq.impl;

import org.jooq.BindingSQLContext;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.RenderContext;

class DefaultBindingSQLContext<U>
  extends AbstractScope
  implements BindingSQLContext<U>
{
  private final RenderContext render;
  private final U value;
  private final String variable;
  
  DefaultBindingSQLContext(Configuration configuration, RenderContext render, U value)
  {
    this(configuration, render, value, "?");
  }
  
  DefaultBindingSQLContext(Configuration configuration, RenderContext render, U value, String variable)
  {
    super(configuration);
    
    this.render = render;
    this.value = value;
    this.variable = variable;
  }
  
  private DefaultBindingSQLContext(AbstractScope other, RenderContext render, U value, String variable)
  {
    super(other);
    
    this.render = render;
    this.value = value;
    this.variable = variable;
  }
  
  public final RenderContext render()
  {
    return this.render;
  }
  
  public final U value()
  {
    return (U)this.value;
  }
  
  public final String variable()
  {
    return this.variable;
  }
  
  public <T> BindingSQLContext<T> convert(Converter<T, U> converter)
  {
    return new DefaultBindingSQLContext(this, this.render, converter.to(this.value), this.variable);
  }
}

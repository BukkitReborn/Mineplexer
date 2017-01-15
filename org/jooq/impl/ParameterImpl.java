package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Parameter;
import org.jooq.tools.StringUtils;

class ParameterImpl<T>
  extends AbstractQueryPart
  implements Parameter<T>
{
  private static final long serialVersionUID = -5277225593751085577L;
  private final String name;
  private final DataType<T> type;
  private final Binding<?, T> binding;
  private final boolean isDefaulted;
  
  ParameterImpl(String name, DataType<T> type, boolean isDefaulted, Binding<?, T> binding)
  {
    this.name = name;
    this.isDefaulted = isDefaulted;
    this.type = type;
    
    this.binding = ((type instanceof ConvertedDataType) ? ((ConvertedDataType)type)
    
      .binding() : binding != null ? binding : new DefaultBinding(new IdentityConverter(type
      .getType()), type.isLob()));
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final Converter<?, T> getConverter()
  {
    return this.binding.converter();
  }
  
  public final Binding<?, T> getBinding()
  {
    return this.binding;
  }
  
  public final DataType<T> getDataType()
  {
    return this.type;
  }
  
  public final DataType<T> getDataType(Configuration configuration)
  {
    return this.type.getDataType(configuration);
  }
  
  public final Class<T> getType()
  {
    return this.type.getType();
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.literal(getName());
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  public final boolean isDefaulted()
  {
    return this.isDefaulted;
  }
  
  public int hashCode()
  {
    return this.name != null ? this.name.hashCode() : 0;
  }
  
  public boolean equals(Object that)
  {
    if (this == that) {
      return true;
    }
    if ((that instanceof ParameterImpl)) {
      return StringUtils.equals(this.name, ((ParameterImpl)that).name);
    }
    return super.equals(that);
  }
}

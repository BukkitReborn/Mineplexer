package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Param;
import org.jooq.conf.ParamType;
import org.jooq.tools.StringUtils;

abstract class AbstractParam<T>
  extends AbstractField<T>
  implements Param<T>
{
  private static final long serialVersionUID = 1311856649676227970L;
  private static final Clause[] CLAUSES = { Clause.FIELD, Clause.FIELD_VALUE };
  private final String paramName;
  T value;
  private boolean inline;
  
  AbstractParam(T value, DataType<T> type)
  {
    this(value, type, null);
  }
  
  AbstractParam(T value, DataType<T> type, String paramName)
  {
    super(name(value, paramName), type);
    
    this.paramName = paramName;
    this.value = value;
  }
  
  private static String name(Object value, String paramName)
  {
    return paramName == null ? String.valueOf(value) : paramName;
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final void setValue(T value)
  {
    setConverted(value);
  }
  
  public final void setConverted(Object value)
  {
    this.value = getDataType().convert(value);
  }
  
  public final T getValue()
  {
    return (T)this.value;
  }
  
  public final String getParamName()
  {
    return this.paramName;
  }
  
  public final void setInline(boolean inline)
  {
    this.inline = inline;
  }
  
  public final boolean isInline()
  {
    return this.inline;
  }
  
  final boolean isInline(Context<?> context)
  {
    return (isInline()) || (context.paramType() == ParamType.INLINED) || ((context.paramType() == ParamType.NAMED_OR_INLINED) && (StringUtils.isBlank(this.paramName)));
  }
}

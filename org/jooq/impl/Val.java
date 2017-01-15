package org.jooq.impl;

import java.sql.SQLException;
import org.jooq.Binding;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.RenderContext;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.StringUtils;

class Val<T>
  extends AbstractParam<T>
{
  private static final long serialVersionUID = 6807729087019209084L;
  
  Val(T value, DataType<T> type)
  {
    super(value, type);
  }
  
  Val(T value, DataType<T> type, String paramName)
  {
    super(value, type, paramName);
  }
  
  public void accept(Context<?> ctx)
  {
    if ((ctx instanceof RenderContext))
    {
      ParamType paramType = ctx.paramType();
      if (isInline(ctx)) {
        ctx.paramType(ParamType.INLINED);
      }
      try
      {
        getBinding().sql(new DefaultBindingSQLContext(ctx.configuration(), (RenderContext)ctx, this.value, getBindVariable(ctx)));
      }
      catch (SQLException e)
      {
        throw new DataAccessException("Error while generating SQL for Binding", e);
      }
      ctx.paramType(paramType);
    }
    else if (!isInline(ctx))
    {
      ctx.bindValue(this.value, this);
    }
  }
  
  final String getBindVariable(Context<?> ctx)
  {
    if ((ctx.paramType() == ParamType.NAMED) || (ctx.paramType() == ParamType.NAMED_OR_INLINED))
    {
      int index = ctx.nextIndex();
      if (StringUtils.isBlank(getParamName())) {
        return ":" + index;
      }
      return ":" + getParamName();
    }
    return "?";
  }
}

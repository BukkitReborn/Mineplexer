package org.jooq.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QueryPartInternal;
import org.jooq.tools.StringUtils;

class ParamCollector
  extends AbstractBindContext
{
  final Map<String, Param<?>> result = new LinkedHashMap();
  private final boolean includeInlinedParams;
  
  ParamCollector(Configuration configuration, boolean includeInlinedParams)
  {
    super(configuration, null);
    
    this.includeInlinedParams = includeInlinedParams;
  }
  
  protected final void bindInternal(QueryPartInternal internal)
  {
    if ((internal instanceof Param))
    {
      Param<?> param = (Param)internal;
      if ((this.includeInlinedParams) || (!param.isInline()))
      {
        String i = String.valueOf(nextIndex());
        if (StringUtils.isBlank(param.getParamName())) {
          this.result.put(i, param);
        } else {
          this.result.put(param.getParamName(), param);
        }
      }
    }
    else
    {
      super.bindInternal(internal);
    }
  }
  
  protected final BindContext bindValue0(Object value, Field<?> field)
    throws SQLException
  {
    throw new UnsupportedOperationException();
  }
}

package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;

class FieldMapForUpdate
  extends AbstractQueryPartMap<Field<?>, Field<?>>
{
  private static final long serialVersionUID = -6139709404698673799L;
  private final Clause assignmentClause;
  
  FieldMapForUpdate(Clause assignmentClause)
  {
    this.assignmentClause = assignmentClause;
  }
  
  public final void accept(Context<?> ctx)
  {
    String separator;
    boolean restoreQualify;
    boolean supportsQualify;
    if (size() > 0)
    {
      separator = "";
      
      restoreQualify = ctx.qualify();
      supportsQualify = Arrays.asList(tmp27_21).contains(ctx.configuration().dialect()) ? false : restoreQualify;
      for (Map.Entry<Field<?>, Field<?>> entry : entrySet())
      {
        ctx.sql(separator);
        if (!"".equals(separator)) {
          ctx.formatNewLine();
        }
        ctx.start(this.assignmentClause).qualify(supportsQualify).visit((QueryPart)entry.getKey()).qualify(restoreQualify).sql(" = ").visit((QueryPart)entry.getValue()).end(this.assignmentClause);
        
        separator = ", ";
      }
    }
    else
    {
      ctx.sql("[ no fields are updated ]");
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  final void set(Map<? extends Field<?>, ?> map)
  {
    for (Map.Entry<? extends Field<?>, ?> entry : map.entrySet())
    {
      Field<?> field = (Field)entry.getKey();
      Object value = entry.getValue();
      
      put((QueryPart)entry.getKey(), Utils.field(value, field));
    }
  }
}

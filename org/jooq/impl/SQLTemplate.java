package org.jooq.impl;

import java.util.List;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.Template;

class SQLTemplate
  implements Template
{
  private final String sql;
  
  SQLTemplate(String sql)
  {
    this.sql = sql;
  }
  
  public final QueryPart transform(Object... input)
  {
    return new SQLTemplateQueryPart(this.sql, input);
  }
  
  private static class SQLTemplateQueryPart
    extends AbstractQueryPart
  {
    private static final long serialVersionUID = -7514156096865122018L;
    private static final Clause[] CLAUSES = { Clause.TEMPLATE };
    private final String sql;
    private final List<QueryPart> substitutes;
    
    SQLTemplateQueryPart(String sql, Object... input)
    {
      this.sql = sql;
      this.substitutes = Utils.queryParts(input);
    }
    
    public final void accept(Context<?> ctx)
    {
      Utils.renderAndBind(ctx, this.sql, this.substitutes);
    }
    
    public final Clause[] clauses(Context<?> ctx)
    {
      return CLAUSES;
    }
    
    public String toString()
    {
      return this.sql;
    }
  }
}

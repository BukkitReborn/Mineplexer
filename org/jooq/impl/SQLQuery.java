package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;

class SQLQuery
  extends AbstractQuery
{
  private static final long serialVersionUID = 1740879770879469220L;
  private final QueryPart delegate;
  
  public SQLQuery(Configuration configuration, QueryPart delegate)
  {
    super(configuration);
    
    this.delegate = delegate;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(this.delegate);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    if ((this.delegate instanceof QueryPartInternal)) {
      return ((QueryPartInternal)this.delegate).clauses(ctx);
    }
    return null;
  }
}

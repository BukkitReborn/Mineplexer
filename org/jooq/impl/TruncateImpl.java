package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TruncateCascadeStep;
import org.jooq.TruncateFinalStep;
import org.jooq.TruncateIdentityStep;

class TruncateImpl<R extends Record>
  extends AbstractQuery
  implements TruncateIdentityStep<R>
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.TRUNCATE };
  private final Table<R> table;
  private Boolean cascade;
  private Boolean restartIdentity;
  
  public TruncateImpl(Configuration configuration, Table<R> table)
  {
    super(configuration);
    
    this.table = table;
  }
  
  public final TruncateFinalStep<R> cascade()
  {
    this.cascade = Boolean.valueOf(true);
    return this;
  }
  
  public final TruncateFinalStep<R> restrict()
  {
    this.cascade = Boolean.valueOf(false);
    return this;
  }
  
  public final TruncateCascadeStep<R> restartIdentity()
  {
    this.restartIdentity = Boolean.valueOf(true);
    return this;
  }
  
  public final TruncateCascadeStep<R> continueIdentity()
  {
    this.restartIdentity = Boolean.valueOf(false);
    return this;
  }
  
  public final void accept(Context<?> ctx)
  {
    switch (ctx.configuration().dialect().family())
    {
    case FIREBIRD: 
    case SQLITE: 
      ctx.visit(create(ctx).delete(this.table));
      break;
    default: 
      ctx.start(Clause.TRUNCATE_TRUNCATE).keyword("truncate table").sql(" ").visit(this.table);
      if (this.restartIdentity != null) {
        ctx.formatSeparator().keyword(this.restartIdentity.booleanValue() ? "restart identity" : "continue identity");
      }
      if (this.cascade != null) {
        ctx.formatSeparator().keyword(this.cascade.booleanValue() ? "cascade" : "restrict");
      }
      ctx.end(Clause.TRUNCATE_TRUNCATE);
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

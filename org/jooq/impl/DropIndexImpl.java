package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropIndexFinalStep;
import org.jooq.SQLDialect;

class DropIndexImpl
  extends AbstractQuery
  implements DropIndexFinalStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.DROP_INDEX };
  private final String index;
  private final boolean ifExists;
  
  DropIndexImpl(Configuration configuration, String index)
  {
    this(configuration, index, false);
  }
  
  DropIndexImpl(Configuration configuration, String index, boolean ifExists)
  {
    super(configuration);
    
    this.index = index;
    this.ifExists = ifExists;
  }
  
  private final boolean supportsIfExists(Context<?> ctx)
  {
    return !Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD }).contains(ctx.family());
  }
  
  public final void accept(Context<?> ctx)
  {
    if ((this.ifExists) && (!supportsIfExists(ctx)))
    {
      Utils.executeImmediateBegin(ctx, DropStatementType.INDEX);
      accept0(ctx);
      Utils.executeImmediateEnd(ctx, DropStatementType.INDEX);
    }
    else
    {
      accept0(ctx);
    }
  }
  
  private void accept0(Context<?> ctx)
  {
    ctx.keyword("drop index").sql(" ");
    if ((this.ifExists) && (supportsIfExists(ctx))) {
      ctx.keyword("if exists").sql(" ");
    }
    ctx.visit(DSL.name(new String[] { this.index }));
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

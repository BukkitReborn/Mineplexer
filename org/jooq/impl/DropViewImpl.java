package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropViewFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Table;

class DropViewImpl
  extends AbstractQuery
  implements DropViewFinalStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.DROP_VIEW };
  private final Table<?> table;
  private final boolean ifExists;
  
  DropViewImpl(Configuration configuration, Table<?> table)
  {
    this(configuration, table, false);
  }
  
  DropViewImpl(Configuration configuration, Table<?> table, boolean ifExists)
  {
    super(configuration);
    
    this.table = table;
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
      Utils.executeImmediateBegin(ctx, DropStatementType.VIEW);
      accept0(ctx);
      Utils.executeImmediateEnd(ctx, DropStatementType.VIEW);
    }
    else
    {
      accept0(ctx);
    }
  }
  
  private void accept0(Context<?> ctx)
  {
    ctx.start(Clause.DROP_VIEW_TABLE).keyword("drop view").sql(" ");
    if ((this.ifExists) && (supportsIfExists(ctx))) {
      ctx.keyword("if exists").sql(" ");
    }
    ctx.visit(this.table);
    
    ctx.end(Clause.DROP_VIEW_TABLE);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

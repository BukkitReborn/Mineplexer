package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropSequenceFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Sequence;

class DropSequenceImpl
  extends AbstractQuery
  implements DropSequenceFinalStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.DROP_SEQUENCE };
  private final Sequence<?> sequence;
  private final boolean ifExists;
  
  DropSequenceImpl(Configuration configuration, Sequence<?> sequence)
  {
    this(configuration, sequence, false);
  }
  
  DropSequenceImpl(Configuration configuration, Sequence<?> sequence, boolean ifExists)
  {
    super(configuration);
    
    this.sequence = sequence;
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
      Utils.executeImmediateBegin(ctx, DropStatementType.SEQUENCE);
      accept0(ctx);
      Utils.executeImmediateEnd(ctx, DropStatementType.SEQUENCE);
    }
    else
    {
      accept0(ctx);
    }
  }
  
  private void accept0(Context<?> ctx)
  {
    ctx.start(Clause.DROP_SEQUENCE_SEQUENCE).keyword("drop sequence").sql(" ");
    if ((this.ifExists) && (supportsIfExists(ctx))) {
      ctx.keyword("if exists").sql(" ");
    }
    ctx.visit(this.sequence).end(Clause.DROP_SEQUENCE_SEQUENCE);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

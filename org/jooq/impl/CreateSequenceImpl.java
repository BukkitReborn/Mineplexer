package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateSequenceFinalStep;
import org.jooq.Sequence;

class CreateSequenceImpl
  extends AbstractQuery
  implements CreateSequenceFinalStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.CREATE_SEQUENCE };
  private final Sequence<?> sequence;
  
  CreateSequenceImpl(Configuration configuration, Sequence<?> sequence)
  {
    super(configuration);
    
    this.sequence = sequence;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.start(Clause.CREATE_SEQUENCE_SEQUENCE).keyword("create sequence").sql(" ").visit(this.sequence);
    
    ctx.end(Clause.CREATE_SEQUENCE_SEQUENCE);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

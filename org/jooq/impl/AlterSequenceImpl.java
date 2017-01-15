package org.jooq.impl;

import org.jooq.AlterSequenceFinalStep;
import org.jooq.AlterSequenceRestartStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Sequence;

class AlterSequenceImpl<T extends Number>
  extends AbstractQuery
  implements AlterSequenceRestartStep<T>, AlterSequenceFinalStep
{
  private static final long serialVersionUID = 8904572826501186329L;
  private static final Clause[] CLAUSES = { Clause.ALTER_SEQUENCE };
  private final Sequence<T> sequence;
  private T restartWith;
  
  AlterSequenceImpl(Configuration configuration, Sequence<T> sequence)
  {
    super(configuration);
    
    this.sequence = sequence;
  }
  
  public final AlterSequenceFinalStep restart()
  {
    return this;
  }
  
  public final AlterSequenceFinalStep restartWith(T value)
  {
    this.restartWith = value;
    return this;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.start(Clause.ALTER_SEQUENCE_SEQUENCE).keyword("alter sequence").sql(" ").visit(this.sequence).end(Clause.ALTER_SEQUENCE_SEQUENCE).start(Clause.ALTER_SEQUENCE_RESTART);
    
    T with = this.restartWith;
    if (with == null) {
      ctx.sql(" ").keyword("restart");
    } else {
      ctx.sql(" ").keyword("restart with").sql(" ").sql(with.toString());
    }
    ctx.end(Clause.ALTER_SEQUENCE_RESTART);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}

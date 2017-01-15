package org.jooq;

public abstract interface InsertOnDuplicateSetMoreStep<R extends Record>
  extends InsertOnDuplicateSetStep<R>, InsertFinalStep<R>
{}

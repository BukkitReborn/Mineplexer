package org.jooq;

public abstract interface MergeMatchedSetMoreStep<R extends Record>
  extends MergeMatchedSetStep<R>, MergeMatchedWhereStep<R>
{}

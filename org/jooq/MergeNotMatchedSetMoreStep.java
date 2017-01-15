package org.jooq;

public abstract interface MergeNotMatchedSetMoreStep<R extends Record>
  extends MergeNotMatchedSetStep<R>, MergeNotMatchedWhereStep<R>
{}

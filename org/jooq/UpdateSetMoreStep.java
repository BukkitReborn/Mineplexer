package org.jooq;

public abstract interface UpdateSetMoreStep<R extends Record>
  extends UpdateSetStep<R>, UpdateFromStep<R>
{}

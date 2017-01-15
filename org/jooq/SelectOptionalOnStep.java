package org.jooq;

public abstract interface SelectOptionalOnStep<R extends Record>
  extends SelectJoinStep<R>, SelectJoinPartitionByStep<R>
{}

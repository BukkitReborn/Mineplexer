package org.jooq;

public abstract interface RecordMapper<R extends Record, E>
{
  public abstract E map(R paramR);
}

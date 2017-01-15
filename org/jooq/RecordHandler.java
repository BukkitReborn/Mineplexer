package org.jooq;

public abstract interface RecordHandler<R extends Record>
{
  public abstract void next(R paramR);
}

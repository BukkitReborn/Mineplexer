package org.jooq.impl;

import org.jooq.Record;

abstract interface RecordOperation<R extends Record, E extends Exception>
{
  public abstract R operate(R paramR)
    throws Exception;
}

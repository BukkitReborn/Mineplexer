package org.jooq;

import java.sql.SQLData;

public abstract interface UDTRecord<R extends UDTRecord<R>>
  extends Record, SQLData
{
  public abstract UDT<R> getUDT();
}

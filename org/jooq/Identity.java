package org.jooq;

import java.io.Serializable;

public abstract interface Identity<R extends Record, T>
  extends Serializable
{
  public abstract Table<R> getTable();
  
  public abstract TableField<R, T> getField();
}

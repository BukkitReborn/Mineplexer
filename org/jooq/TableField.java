package org.jooq;

public abstract interface TableField<R extends Record, T>
  extends Field<T>
{
  public abstract Table<R> getTable();
}

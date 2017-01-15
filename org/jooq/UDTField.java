package org.jooq;

public abstract interface UDTField<R extends UDTRecord<R>, T>
  extends Field<T>
{
  public abstract UDT<R> getUDT();
}

package org.jooq;

public abstract interface FieldLike
{
  public abstract <T> Field<T> asField();
  
  public abstract <T> Field<T> asField(String paramString);
}

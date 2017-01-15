package org.jooq;

public abstract interface CreateTableColumnStep
  extends CreateTableFinalStep
{
  @Support
  public abstract <T> CreateTableColumnStep column(Field<T> paramField, DataType<T> paramDataType);
  
  @Support
  public abstract CreateTableColumnStep column(String paramString, DataType<?> paramDataType);
}

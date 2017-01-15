package org.jooq;

import java.io.Serializable;
import java.util.List;

public abstract interface Key<R extends Record>
  extends Serializable
{
  public abstract Table<R> getTable();
  
  public abstract List<TableField<R, ?>> getFields();
  
  public abstract TableField<R, ?>[] getFieldsArray();
}

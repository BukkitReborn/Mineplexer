package org.jooq.impl;

import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;

class IdentityImpl<R extends Record, T>
  implements Identity<R, T>
{
  private static final long serialVersionUID = 162853300137140844L;
  private final Table<R> table;
  private final TableField<R, T> field;
  
  IdentityImpl(Table<R> table, TableField<R, T> field)
  {
    this.table = table;
    this.field = field;
  }
  
  public final Table<R> getTable()
  {
    return this.table;
  }
  
  public final TableField<R, T> getField()
  {
    return this.field;
  }
  
  public int hashCode()
  {
    return toString().hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if ((obj instanceof Identity)) {
      return toString().equals(obj.toString());
    }
    return false;
  }
  
  public String toString()
  {
    return this.field.toString();
  }
}

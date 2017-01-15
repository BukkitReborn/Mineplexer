package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Key;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;

abstract class AbstractKey<R extends Record>
  implements Key<R>
{
  private static final long serialVersionUID = 8176874459141379340L;
  private final Table<R> table;
  private final TableField<R, ?>[] fields;
  
  AbstractKey(Table<R> table, TableField<R, ?>... fields)
  {
    this.table = table;
    this.fields = fields;
  }
  
  public final Table<R> getTable()
  {
    return this.table;
  }
  
  public final List<TableField<R, ?>> getFields()
  {
    return Arrays.asList(this.fields);
  }
  
  public final TableField<R, ?>[] getFieldsArray()
  {
    return this.fields;
  }
}

package org.jooq.impl;

import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;

public abstract class AbstractKeys
{
  protected static <R extends Record, T> Identity<R, T> createIdentity(Table<R> table, TableField<R, T> field)
  {
    return new IdentityImpl(table, field);
  }
  
  protected static <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, TableField<R, ?>... fields)
  {
    return new UniqueKeyImpl(table, fields);
  }
  
  protected static <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(UniqueKey<U> key, Table<R> table, TableField<R, ?>... fields)
  {
    ForeignKey<R, U> result = new ReferenceImpl(key, table, fields);
    if ((key instanceof UniqueKeyImpl)) {
      ((UniqueKeyImpl)key).references.add(result);
    }
    return result;
  }
}

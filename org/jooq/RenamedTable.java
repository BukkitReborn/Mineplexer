package org.jooq;

import org.jooq.impl.TableImpl;

class RenamedTable<R extends Record>
  extends TableImpl<R>
{
  private static final long serialVersionUID = -309012919785933903L;
  
  RenamedTable(Table<R> delegate, String rename)
  {
    super(rename, delegate.getSchema());
    for (Field<?> field : delegate.fields()) {
      createField(field.getName(), field.getDataType(), this);
    }
  }
}

package org.jooq.impl;

import java.util.List;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Row;
import org.jooq.Table;

class TableList
  extends QueryPartList<Table<?>>
{
  private static final long serialVersionUID = -8545559185481762229L;
  
  TableList() {}
  
  TableList(List<? extends Table<?>> wrappedList)
  {
    super(wrappedList);
  }
  
  protected void toSQLEmptyList(Context<?> ctx)
  {
    ctx.visit(new Dual());
  }
  
  public final boolean declaresTables()
  {
    return true;
  }
  
  final void toSQLFieldNames(Context<?> ctx)
  {
    String separator = "";
    for (Table<?> table : this) {
      for (Field<?> field : table.fieldsRow().fields())
      {
        ctx.sql(separator);
        ctx.literal(field.getName());
        
        separator = ", ";
      }
    }
  }
}

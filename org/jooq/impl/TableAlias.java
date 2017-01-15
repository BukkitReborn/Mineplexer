package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.Table;

class TableAlias<R extends Record>
  extends AbstractTable<R>
{
  private static final long serialVersionUID = -8417114874567698325L;
  private final Alias<Table<R>> alias;
  private final Fields<R> aliasedFields;
  
  TableAlias(Table<R> table, String alias)
  {
    this(table, alias, null, false);
  }
  
  TableAlias(Table<R> table, String alias, boolean wrapInParentheses)
  {
    this(table, alias, null, wrapInParentheses);
  }
  
  TableAlias(Table<R> table, String alias, String[] fieldAliases)
  {
    this(table, alias, fieldAliases, false);
  }
  
  TableAlias(Table<R> table, String alias, String[] fieldAliases, boolean wrapInParentheses)
  {
    super(alias, table.getSchema());
    
    this.alias = new Alias(table, alias, fieldAliases, wrapInParentheses);
    this.aliasedFields = init(fieldAliases);
  }
  
  private final Fields<R> init(String[] fieldAliases)
  {
    List<Field<?>> result = new ArrayList();
    Row row = ((Table)this.alias.wrapped()).fieldsRow();
    int size = row.size();
    for (int i = 0; i < size; i++)
    {
      Field<?> field = row.field(i);
      String name = field.getName();
      if ((fieldAliases != null) && (fieldAliases.length > i)) {
        name = fieldAliases[i];
      }
      result.add(new TableFieldImpl(name, field.getDataType(), this, field.getComment(), field.getBinding()));
    }
    return new Fields(result);
  }
  
  Table<R> getAliasedTable()
  {
    if (this.alias != null) {
      return (Table)this.alias.wrapped();
    }
    return null;
  }
  
  public final List<ForeignKey<R, ?>> getReferences()
  {
    return ((Table)this.alias.wrapped()).getReferences();
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(this.alias);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  public final Table<R> as(String as)
  {
    return ((Table)this.alias.wrapped()).as(as);
  }
  
  public final Table<R> as(String as, String... fieldAliases)
  {
    return ((Table)this.alias.wrapped()).as(as, fieldAliases);
  }
  
  public final boolean declaresTables()
  {
    return true;
  }
  
  final Fields<R> fields0()
  {
    return this.aliasedFields;
  }
  
  public Class<? extends R> getRecordType()
  {
    return ((Table)this.alias.wrapped()).getRecordType();
  }
}

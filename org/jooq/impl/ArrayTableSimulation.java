package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectSelectStep;
import org.jooq.Table;

class ArrayTableSimulation
  extends AbstractTable<Record>
{
  private static final long serialVersionUID = 2392515064450536343L;
  private final Object[] array;
  private final Fields<Record> field;
  private final String alias;
  private final String fieldAlias;
  private transient Table<Record> table;
  
  ArrayTableSimulation(Object[] array)
  {
    this(array, "array_table", null);
  }
  
  ArrayTableSimulation(Object[] array, String alias)
  {
    this(array, alias, null);
  }
  
  ArrayTableSimulation(Object[] array, String alias, String fieldAlias)
  {
    super(alias);
    
    this.array = array;
    this.alias = alias;
    this.fieldAlias = (fieldAlias == null ? "COLUMN_VALUE" : fieldAlias);
    this.field = new Fields(new Field[] { DSL.fieldByName(DSL.getDataType(array.getClass().getComponentType()), new String[] { alias, this.fieldAlias }) });
  }
  
  public final Class<? extends Record> getRecordType()
  {
    return RecordImpl.class;
  }
  
  public final Table<Record> as(String as)
  {
    return new ArrayTableSimulation(this.array, as);
  }
  
  public final Table<Record> as(String as, String... fieldAliases)
  {
    if (fieldAliases == null) {
      return new ArrayTableSimulation(this.array, as);
    }
    if (fieldAliases.length == 1) {
      return new ArrayTableSimulation(this.array, as, fieldAliases[0]);
    }
    throw new IllegalArgumentException("Array table simulations can only have a single field alias");
  }
  
  public final boolean declaresTables()
  {
    return true;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(table(ctx.configuration()));
  }
  
  final Fields<Record> fields0()
  {
    return this.field;
  }
  
  private final Table<Record> table(Configuration configuration)
  {
    if (this.table == null)
    {
      Select<Record> select = null;
      for (Object element : this.array)
      {
        Field<?> val = DSL.val(element, this.field.fields[0].getDataType());
        Select<Record> subselect = DSL.using(configuration).select(val.as("COLUMN_VALUE")).select(new Field[0]);
        if (select == null) {
          select = subselect;
        } else {
          select = select.unionAll(subselect);
        }
      }
      if (select == null) {
        select = DSL.using(configuration).select(DSL.one().as("COLUMN_VALUE")).select(new Field[0]).where(new Condition[] { DSL.falseCondition() });
      }
      this.table = select.asTable(this.alias);
    }
    return this.table;
  }
}

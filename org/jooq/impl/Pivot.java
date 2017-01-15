package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.PivotForStep;
import org.jooq.PivotInStep;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectHavingStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;

class Pivot<T>
  extends AbstractTable<Record>
  implements PivotForStep, PivotInStep<T>
{
  private static final long serialVersionUID = -7918219502110473521L;
  private final Table<?> table;
  private final SelectFieldList aggregateFunctions;
  private Field<T> on;
  private SelectFieldList in;
  
  Pivot(Table<?> table, Field<?>... aggregateFunctions)
  {
    super("pivot");
    
    this.table = table;
    this.aggregateFunctions = new SelectFieldList(aggregateFunctions);
  }
  
  public final Class<? extends Record> getRecordType()
  {
    return RecordImpl.class;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(pivot(ctx.configuration()));
  }
  
  private Table<?> pivot(Configuration configuration)
  {
    switch (configuration.dialect())
    {
    }
    return new DefaultPivotTable(null);
  }
  
  private class DefaultPivotTable
    extends Pivot<T>.DialectPivotTable
  {
    private static final long serialVersionUID = -5930286639571867314L;
    
    private DefaultPivotTable()
    {
      super();
    }
    
    public final void accept(Context<?> ctx)
    {
      ctx.declareTables(true).visit(select(ctx.configuration())).declareTables(false);
    }
    
    private Table<Record> select(Configuration configuration)
    {
      List<Field<?>> groupingFields = new ArrayList();
      List<Field<?>> aliasedGroupingFields = new ArrayList();
      List<Field<?>> aggregatedFields = new ArrayList();
      
      Table<?> pivot = Pivot.this.table.as("pivot_outer");
      for (Object localObject1 = Pivot.this.aggregateFunctions.iterator(); ((Iterator)localObject1).hasNext();)
      {
        field = (Field)((Iterator)localObject1).next();
        if ((field instanceof Function)) {
          for (QueryPart argument : ((Function)field).getArguments()) {
            if ((argument instanceof Field)) {
              aggregatedFields.add((Field)argument);
            }
          }
        }
      }
      localObject1 = Pivot.this.table.fields();Field<?> field = localObject1.length;
      Field<?> field;
      for (Field<?> localField1 = 0; localField1 < field; localField1++)
      {
        field = localObject1[localField1];
        if ((!aggregatedFields.contains(field)) && 
          (!Pivot.this.on.equals(field)))
        {
          aliasedGroupingFields.add(pivot.field(field));
          groupingFields.add(field);
        }
      }
      Object aggregationSelects = new ArrayList();
      for (field = Pivot.this.in.iterator(); field.hasNext();)
      {
        inField = (Field)field.next();
        for (Field<?> aggregateFunction : Pivot.this.aggregateFunctions)
        {
          Condition join = DSL.trueCondition();
          for (Field<?> field : groupingFields) {
            join = join.and(Pivot.this.condition(pivot, field));
          }
          Object aggregateSelect = DSL.using(configuration).select(aggregateFunction).from(Pivot.this.table).where(new Condition[] { Pivot.this.on.equal((Field)inField) }).and(join);
          
          ((List)aggregationSelects).add(((Select)aggregateSelect).asField(((Field)inField).getName() + "_" + aggregateFunction.getName()));
        }
      }
      Object inField;
      Table<Record> select = DSL.using(configuration).select(aliasedGroupingFields).select((Collection)aggregationSelects).from(pivot).where(new Condition[] { pivot.field(Pivot.this.on).in((Field[])Pivot.this.in.toArray(new Field[0])) }).groupBy(aliasedGroupingFields).asTable();
      
      return select;
    }
  }
  
  private abstract class DialectPivotTable
    extends AbstractTable<Record>
  {
    private static final long serialVersionUID = 2662639259338694177L;
    
    DialectPivotTable()
    {
      super();
    }
    
    public final Class<? extends Record> getRecordType()
    {
      return RecordImpl.class;
    }
    
    public final Table<Record> as(String as)
    {
      return new TableAlias(this, as);
    }
    
    public final Table<Record> as(String as, String... fieldAliases)
    {
      return new TableAlias(this, as, fieldAliases);
    }
    
    final Fields<Record> fields0()
    {
      return Pivot.this.fields0();
    }
  }
  
  private <Z> Condition condition(Table<?> pivot, Field<Z> field)
  {
    return field.equal(pivot.field(field));
  }
  
  public final boolean declaresTables()
  {
    return true;
  }
  
  public final Table<Record> as(String alias)
  {
    return new TableAlias(this, alias, true);
  }
  
  public final Table<Record> as(String alias, String... fieldAliases)
  {
    return new TableAlias(this, alias, fieldAliases, true);
  }
  
  final Fields<Record> fields0()
  {
    return new Fields(new Field[0]);
  }
}

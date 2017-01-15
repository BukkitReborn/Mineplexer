package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertQuery;
import org.jooq.Merge;
import org.jooq.MergeMatchedSetStep;
import org.jooq.MergeNotMatchedStep;
import org.jooq.MergeNotMatchedValuesStepN;
import org.jooq.MergeOnConditionStep;
import org.jooq.MergeOnStep;
import org.jooq.MergeUsingStep;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.exception.SQLDialectNotSupportedException;

class InsertQueryImpl<R extends Record>
  extends AbstractStoreQuery<R>
  implements InsertQuery<R>
{
  private static final long serialVersionUID = 4466005417945353842L;
  private static final Clause[] CLAUSES = { Clause.INSERT };
  private final FieldMapForUpdate updateMap;
  private final FieldMapsForInsert insertMaps;
  private boolean defaultValues;
  private boolean onDuplicateKeyUpdate;
  private boolean onDuplicateKeyIgnore;
  
  InsertQueryImpl(Configuration configuration, Table<R> into)
  {
    super(configuration, into);
    
    this.updateMap = new FieldMapForUpdate(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE_ASSIGNMENT);
    this.insertMaps = new FieldMapsForInsert();
  }
  
  public final void newRecord()
  {
    this.insertMaps.newRecord();
  }
  
  protected final FieldMapForInsert getValues()
  {
    return this.insertMaps.getMap();
  }
  
  public final void addRecord(R record)
  {
    newRecord();
    setRecord(record);
  }
  
  public final void onDuplicateKeyUpdate(boolean flag)
  {
    this.onDuplicateKeyIgnore = false;
    this.onDuplicateKeyUpdate = flag;
  }
  
  public final void onDuplicateKeyIgnore(boolean flag)
  {
    this.onDuplicateKeyUpdate = false;
    this.onDuplicateKeyIgnore = flag;
  }
  
  public final <T> void addValueForUpdate(Field<T> field, T value)
  {
    this.updateMap.put(field, Utils.field(value, field));
  }
  
  public final <T> void addValueForUpdate(Field<T> field, Field<T> value)
  {
    this.updateMap.put(field, Utils.field(value, field));
  }
  
  public final void addValuesForUpdate(Map<? extends Field<?>, ?> map)
  {
    this.updateMap.set(map);
  }
  
  public final void setDefaultValues()
  {
    this.defaultValues = true;
  }
  
  public final void addValues(Map<? extends Field<?>, ?> map)
  {
    this.insertMaps.getMap().set(map);
  }
  
  final void accept0(Context<?> ctx)
  {
    if (this.onDuplicateKeyUpdate)
    {
      switch (ctx.configuration().dialect().family())
      {
      case CUBRID: 
      case MARIADB: 
      case MYSQL: 
        toSQLInsert(ctx);
        ctx.formatSeparator()
          .start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE)
          .keyword("on duplicate key update")
          .sql(" ")
          .visit(this.updateMap)
          .end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
        
        break;
      case H2: 
        throw new SQLDialectNotSupportedException("The ON DUPLICATE KEY UPDATE clause cannot be emulated for " + ctx.configuration().dialect());
      case HSQLDB: 
        ctx.visit(toMerge(ctx.configuration()));
        break;
      default: 
        throw new SQLDialectNotSupportedException("The ON DUPLICATE KEY UPDATE clause cannot be emulated for " + ctx.configuration().dialect());
      }
    }
    else if (this.onDuplicateKeyIgnore)
    {
      switch (ctx.configuration().dialect().family())
      {
      case MARIADB: 
      case MYSQL: 
        toSQLInsert(ctx);
        ctx.start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE)
          .end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
        break;
      case CUBRID: 
        FieldMapForUpdate update = new FieldMapForUpdate(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE_ASSIGNMENT);
        Field<?> field = getInto().field(0);
        update.put(field, field);
        
        toSQLInsert(ctx);
        ctx.formatSeparator()
          .start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE)
          .keyword("on duplicate key update")
          .sql(" ")
          .visit(update)
          .end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
        
        break;
      case H2: 
        throw new SQLDialectNotSupportedException("The ON DUPLICATE KEY IGNORE clause cannot be emulated for " + ctx.configuration().dialect());
      case HSQLDB: 
        ctx.visit(toMerge(ctx.configuration()));
        break;
      default: 
        throw new SQLDialectNotSupportedException("The ON DUPLICATE KEY IGNORE clause cannot be emulated for " + ctx.configuration().dialect());
      }
    }
    else
    {
      toSQLInsert(ctx);
      ctx.start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE)
        .end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
    }
    ctx.start(Clause.INSERT_RETURNING);
    toSQLReturning(ctx);
    ctx.end(Clause.INSERT_RETURNING);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  private final void toSQLInsert(Context<?> ctx)
  {
    boolean declareTables = ctx.declareTables();
    if (this.onDuplicateKeyIgnore) {}
    ctx.start(Clause.INSERT_INSERT_INTO).keyword("insert").sql(" ").keyword(Arrays.asList(tmp47_41).contains(ctx.configuration().dialect()) ? "ignore " : "").keyword("into").sql(" ").declareTables(true).visit(getInto()).declareTables(declareTables);
    if (this.insertMaps.isExecutable())
    {
      ctx.sql(" ");
      ((FieldMapForInsert)this.insertMaps.insertMaps.get(0)).toSQLReferenceKeys(ctx);
    }
    ctx.end(Clause.INSERT_INSERT_INTO);
    if (this.defaultValues) {
      switch (ctx.configuration().dialect().family())
      {
      case MARIADB: 
      case MYSQL: 
      case DERBY: 
        ctx.sql(" ").keyword("values").sql("(");
        
        int count = getInto().fields().length;
        String separator = "";
        for (int i = 0; i < count; i++)
        {
          ctx.sql(separator);
          ctx.keyword("default");
          separator = ", ";
        }
        ctx.sql(")");
        break;
      case H2: 
      case HSQLDB: 
      default: 
        ctx.sql(" ").keyword("default values");
      }
    } else {
      ctx.visit(this.insertMaps);
    }
  }
  
  private final Merge<R> toMerge(Configuration configuration)
  {
    Table<R> i = getInto();
    if (i.getPrimaryKey() != null)
    {
      Condition condition = null;
      List<Field<?>> key = new ArrayList();
      for (Field<?> f : i.getPrimaryKey().getFields())
      {
        Field<Object> field = f;
        Field<Object> value = (Field)this.insertMaps.getMap().get(field);
        
        key.add(value);
        Condition other = field.equal(value);
        if (condition == null) {
          condition = other;
        } else {
          condition = condition.and(other);
        }
      }
      Object on = create(configuration).mergeInto(i).usingDual().on(new Condition[] { condition });
      
      MergeNotMatchedStep<R> notMatched = (MergeNotMatchedStep<R>)on;
      if (this.onDuplicateKeyUpdate) {
        notMatched = ((MergeOnConditionStep)on).whenMatchedThenUpdate().set(this.updateMap);
      }
      return notMatched.whenNotMatchedThenInsert(this.insertMaps.getMap().keySet()).values(this.insertMaps.getMap().values());
    }
    throw new IllegalStateException("The ON DUPLICATE KEY IGNORE/UPDATE clause cannot be simulated when inserting into non-updatable tables : " + getInto());
  }
  
  public final boolean isExecutable()
  {
    return (this.insertMaps.isExecutable()) || (this.defaultValues);
  }
}

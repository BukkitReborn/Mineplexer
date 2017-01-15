package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;

class FieldMapsForInsert
  extends AbstractQueryPart
{
  private static final long serialVersionUID = -6227074228534414225L;
  final List<FieldMapForInsert> insertMaps;
  
  FieldMapsForInsert()
  {
    this.insertMaps = new ArrayList();
    this.insertMaps.add(null);
  }
  
  public final void accept(Context<?> ctx)
  {
    if (!isExecutable()) {
      ctx.sql("[ no fields are inserted ]");
    } else if ((this.insertMaps.size() == 1) || (this.insertMaps.get(1) == null)) {
      ctx.formatSeparator().start(Clause.INSERT_VALUES).keyword("values").sql(" ").visit((QueryPart)this.insertMaps.get(0)).end(Clause.INSERT_VALUES);
    } else {
      switch (ctx.family())
      {
      case FIREBIRD: 
      case SQLITE: 
        ctx.formatSeparator().start(Clause.INSERT_SELECT);
        ctx.visit(insertSelect(ctx));
        ctx.end(Clause.INSERT_SELECT);
        
        break;
      default: 
        ctx.formatSeparator().start(Clause.INSERT_VALUES).keyword("values").sql(" ");
        toSQL92Values(ctx);
        ctx.end(Clause.INSERT_VALUES);
      }
    }
  }
  
  private final Select<Record> insertSelect(Context<?> context)
  {
    Select<Record> select = null;
    for (FieldMapForInsert map : this.insertMaps) {
      if (map != null)
      {
        Select<Record> iteration = DSL.using(context.configuration()).select(map.values());
        if (select == null) {
          select = iteration;
        } else {
          select = select.unionAll(iteration);
        }
      }
    }
    return select;
  }
  
  private final void toSQL92Values(Context<?> context)
  {
    context.visit((QueryPart)this.insertMaps.get(0));
    
    int i = 0;
    for (FieldMapForInsert map : this.insertMaps)
    {
      if ((map != null) && (i > 0))
      {
        context.sql(", ");
        context.visit(map);
      }
      i++;
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  final boolean isExecutable()
  {
    return (!this.insertMaps.isEmpty()) && (this.insertMaps.get(0) != null);
  }
  
  public final FieldMapForInsert getMap()
  {
    if (this.insertMaps.get(index()) == null) {
      this.insertMaps.set(index(), new FieldMapForInsert());
    }
    return (FieldMapForInsert)this.insertMaps.get(index());
  }
  
  public final void newRecord()
  {
    if (this.insertMaps.get(index()) != null) {
      this.insertMaps.add(null);
    }
  }
  
  private final int index()
  {
    return this.insertMaps.size() - 1;
  }
}

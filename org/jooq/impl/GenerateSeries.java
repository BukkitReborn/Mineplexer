package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Table;

class GenerateSeries
  extends AbstractTable<Record1<Integer>>
{
  private static final long serialVersionUID = 2385574114457239818L;
  private final Field<Integer> from;
  private final Field<Integer> to;
  
  GenerateSeries(Field<Integer> from, Field<Integer> to)
  {
    super("generate_series");
    
    this.from = from;
    this.to = to;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(delegate(ctx.configuration()));
  }
  
  private final QueryPart delegate(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case CUBRID: 
      Field<Integer> level = this.from.add(DSL.level()).sub(DSL.one());
      return DSL.table("({select} {0} {as} {1} {from} {2} {connect by} {level} <= {3})", new QueryPart[] { level, 
      
        DSL.name(new String[] { "generate_series" }), new Dual(), this.to
        
        .add(DSL.one()).sub(this.from) });
    }
    return DSL.table("{generate_series}({0}, {1})", new QueryPart[] { this.from, this.to });
  }
  
  public final Class<? extends Record1<Integer>> getRecordType()
  {
    return RecordImpl.class;
  }
  
  public final Table<Record1<Integer>> as(String alias)
  {
    return new TableAlias(this, alias);
  }
  
  public final Table<Record1<Integer>> as(String alias, String... fieldAliases)
  {
    return new TableAlias(this, alias, fieldAliases);
  }
  
  final Fields<Record1<Integer>> fields0()
  {
    return new Fields(new Field[] { DSL.fieldByName(Integer.class, new String[] { "generate_series" }) });
  }
}

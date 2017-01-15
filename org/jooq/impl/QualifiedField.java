package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;

class QualifiedField<T>
  extends AbstractField<T>
{
  private static final long serialVersionUID = 6937002867156868761L;
  private final String[] sql;
  
  QualifiedField(DataType<T> type, String... sql)
  {
    super(sql[(sql.length - 1)], type);
    
    this.sql = sql;
  }
  
  public final void accept(Context<?> ctx)
  {
    if (ctx.qualify())
    {
      String separator = "";
      for (String string : this.sql)
      {
        ctx.sql(separator);
        ctx.literal(string);
        
        separator = ".";
      }
    }
    else
    {
      ctx.literal(this.sql[(this.sql.length - 1)]);
    }
  }
}

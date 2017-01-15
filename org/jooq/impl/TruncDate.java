package org.jooq.impl;

import java.util.Date;
import org.jooq.Configuration;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.QueryPart;

class TruncDate<T extends Date>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -4617792768119885313L;
  private final Field<T> date;
  private final DatePart part;
  
  TruncDate(Field<T> date, DatePart part)
  {
    super("trunc", date.getDataType(), new Field[0]);
    
    this.date = date;
    this.part = part;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    String keyword = null;
    switch (configuration.dialect().family())
    {
    case CUBRID: 
    case HSQLDB: 
      switch (this.part)
      {
      case YEAR: 
        keyword = "YY"; break;
      case MONTH: 
        keyword = "MM"; break;
      case DAY: 
        keyword = "DD"; break;
      case HOUR: 
        keyword = "HH"; break;
      case MINUTE: 
        keyword = "MI"; break;
      case SECOND: 
        keyword = "SS"; break;
      default: 
        throwUnsupported();
      }
      return DSL.field("{trunc}({0}, {1})", getDataType(), new QueryPart[] { this.date, DSL.inline(keyword) });
    case POSTGRES: 
      switch (this.part)
      {
      case YEAR: 
        keyword = "year"; break;
      case MONTH: 
        keyword = "month"; break;
      case DAY: 
        keyword = "day"; break;
      case HOUR: 
        keyword = "hour"; break;
      case MINUTE: 
        keyword = "minute"; break;
      case SECOND: 
        keyword = "second"; break;
      default: 
        throwUnsupported();
      }
      return DSL.field("{date_trunc}({0}, {1})", getDataType(), new QueryPart[] { DSL.inline(keyword), this.date });
    }
    return DSL.field("{trunc}({0}, {1})", getDataType(), new QueryPart[] { this.date, DSL.inline(this.part.name()) });
  }
  
  private final void throwUnsupported()
  {
    throw new UnsupportedOperationException("Unknown date part : " + this.part);
  }
}

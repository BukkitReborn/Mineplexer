package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.exception.SQLDialectNotSupportedException;

class Extract
  extends AbstractFunction<Integer>
{
  private static final long serialVersionUID = 3748640920856031034L;
  private final Field<?> field;
  private final DatePart datePart;
  
  Extract(Field<?> field, DatePart datePart)
  {
    super("extract", SQLDataType.INTEGER, new Field[] { field });
    
    this.field = field;
    this.datePart = datePart;
  }
  
  final Field<Integer> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case SQLITE: 
      switch (this.datePart)
      {
      case YEAR: 
        return DSL.field("{strftime}('%Y', {0})", SQLDataType.INTEGER, new QueryPart[] { this.field });
      case MONTH: 
        return DSL.field("{strftime}('%m', {0})", SQLDataType.INTEGER, new QueryPart[] { this.field });
      case DAY: 
        return DSL.field("{strftime}('%d', {0})", SQLDataType.INTEGER, new QueryPart[] { this.field });
      case HOUR: 
        return DSL.field("{strftime}('%H', {0})", SQLDataType.INTEGER, new QueryPart[] { this.field });
      case MINUTE: 
        return DSL.field("{strftime}('%M', {0})", SQLDataType.INTEGER, new QueryPart[] { this.field });
      case SECOND: 
        return DSL.field("{strftime}('%S', {0})", SQLDataType.INTEGER, new QueryPart[] { this.field });
      }
      throw new SQLDialectNotSupportedException("DatePart not supported: " + this.datePart);
    case DERBY: 
      switch (this.datePart)
      {
      case YEAR: 
        return DSL.function("year", SQLDataType.INTEGER, new Field[] { this.field });
      case MONTH: 
        return DSL.function("month", SQLDataType.INTEGER, new Field[] { this.field });
      case DAY: 
        return DSL.function("day", SQLDataType.INTEGER, new Field[] { this.field });
      case HOUR: 
        return DSL.function("hour", SQLDataType.INTEGER, new Field[] { this.field });
      case MINUTE: 
        return DSL.function("minute", SQLDataType.INTEGER, new Field[] { this.field });
      case SECOND: 
        return DSL.function("second", SQLDataType.INTEGER, new Field[] { this.field });
      }
      throw new SQLDialectNotSupportedException("DatePart not supported: " + this.datePart);
    }
    return DSL.field("{extract}({" + this.datePart.toSQL() + " from} {0})", SQLDataType.INTEGER, new QueryPart[] { this.field });
  }
}

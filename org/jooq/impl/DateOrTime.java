package org.jooq.impl;

import java.sql.Time;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;

class DateOrTime<T extends java.util.Date>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -6729613078727690134L;
  private final Field<?> field;
  
  DateOrTime(Field<?> field, DataType<T> dataType)
  {
    super(name(dataType), dataType, new Field[] { field });
    
    this.field = field;
  }
  
  private static String name(DataType<?> dataType)
  {
    return dataType.getType() == Time.class ? "time" : dataType.getType() == java.sql.Date.class ? "date" : "timestamp";
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case MYSQL: 
    case MARIADB: 
      return DSL.field("{" + name(getDataType()) + "}({0})", getDataType(), new QueryPart[] { this.field });
    }
    return this.field.cast(getDataType());
  }
}

package org.jooq.impl;

import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;

class Least<T>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  Least(DataType<T> type, Field<?>... arguments)
  {
    super("least", type, arguments);
  }
  
  final Field<T> getFunction0(Configuration configuration)
  {
    if (getArguments().length == 1) {
      return getArguments()[0];
    }
    switch (configuration.dialect().family())
    {
    case DERBY: 
      Field<T> first = getArguments()[0];
      Field<T> other = getArguments()[1];
      if (getArguments().length > 2)
      {
        Field<?>[] remaining = new Field[getArguments().length - 2];
        System.arraycopy(getArguments(), 2, remaining, 0, remaining.length);
        
        return DSL.decode().when(first.lessThan(other), DSL.least(first, remaining)).otherwise(DSL.least(other, remaining));
      }
      return DSL.decode().when(first.lessThan(other), first).otherwise(other);
    case FIREBIRD: 
      return DSL.function("minvalue", getDataType(), getArguments());
    case SQLITE: 
      return DSL.function("min", getDataType(), getArguments());
    }
    return DSL.function("least", getDataType(), getArguments());
  }
}

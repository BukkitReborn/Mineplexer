package org.jooq.impl;

import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;

class Greatest<T>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -7273879239726265322L;
  
  Greatest(DataType<T> type, Field<?>... arguments)
  {
    super("greatest", type, arguments);
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
        
        return DSL.decode().when(first.greaterThan(other), DSL.greatest(first, remaining)).otherwise(DSL.greatest(other, remaining));
      }
      return DSL.decode().when(first.greaterThan(other), first).otherwise(other);
    case FIREBIRD: 
      return DSL.function("maxvalue", getDataType(), getArguments());
    case SQLITE: 
      return DSL.function("max", getDataType(), getArguments());
    }
    return DSL.function("greatest", getDataType(), getArguments());
  }
}

package org.jooq.impl;

import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.Configuration;
import org.jooq.Field;

class Sign
  extends AbstractFunction<Integer>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<?> argument;
  
  Sign(Field<?> argument)
  {
    super("sign", SQLDataType.INTEGER, new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<Integer> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect())
    {
    case SQLITE: 
      return DSL.decode().when(this.argument.greaterThan(DSL.zero()), DSL.one()).when(this.argument.lessThan(DSL.zero()), DSL.one().neg()).otherwise(DSL.zero());
    }
    return DSL.function("sign", getDataType(), new Field[] { this.argument });
  }
}

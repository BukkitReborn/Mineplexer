package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.Configuration;
import org.jooq.Field;

class Round<T extends Number>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<T> argument;
  private final int decimals;
  
  Round(Field<T> argument)
  {
    this(argument, 0);
  }
  
  Round(Field<T> argument, int decimals)
  {
    super("round", argument.getDataType(), new Field[] { argument });
    
    this.argument = argument;
    this.decimals = decimals;
  }
  
  final Field<T> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
      if (this.decimals == 0) {
        return DSL.decode().when(this.argument.sub(DSL.floor(this.argument)).lessThan(Double.valueOf(0.5D)), DSL.floor(this.argument)).otherwise(DSL.ceil(this.argument));
      }
      Field<BigDecimal> factor = DSL.val(BigDecimal.ONE.movePointRight(this.decimals));
      Field<T> mul = this.argument.mul(factor);
      
      return DSL.decode().when(mul.sub(DSL.floor(mul)).lessThan(Double.valueOf(0.5D)), DSL.floor(mul).div(factor)).otherwise(DSL.ceil(mul).div(factor));
    case POSTGRES: 
      if (this.decimals == 0) {
        return DSL.function("round", getDataType(), new Field[] { this.argument });
      }
      return DSL.function("round", getDataType(), new Field[] { this.argument.cast(BigDecimal.class), DSL.val(Integer.valueOf(this.decimals)) });
    }
    if (this.decimals == 0) {
      return DSL.function("round", getDataType(), new Field[] { this.argument });
    }
    return DSL.function("round", getDataType(), new Field[] { this.argument, DSL.val(Integer.valueOf(this.decimals)) });
  }
}

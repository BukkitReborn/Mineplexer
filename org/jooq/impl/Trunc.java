package org.jooq.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Trunc<T>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = 4291348230758816484L;
  private final Field<T> field;
  private final Field<Integer> decimals;
  
  Trunc(Field<T> field, Field<Integer> decimals)
  {
    super("trunc", field.getDataType(), new Field[0]);
    
    this.field = field;
    this.decimals = decimals;
  }
  
  final Field<T> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
      Integer decimalsVal = (Integer)Utils.extractVal(this.decimals);
      Field<BigDecimal> power;
      Field<BigDecimal> power;
      if (decimalsVal != null) {
        power = DSL.inline(BigDecimal.TEN.pow(decimalsVal.intValue(), MathContext.DECIMAL128));
      } else {
        power = DSL.power(DSL.inline(BigDecimal.TEN), this.decimals);
      }
      return DSL.decode().when(this.field.sign().greaterOrEqual(DSL.zero()), this.field.mul(power).floor().div(power)).otherwise(this.field
        .mul(power).ceil().div(power));
    case H2: 
    case MARIADB: 
    case MYSQL: 
      return DSL.field("{truncate}({0}, {1})", this.field.getDataType(), new QueryPart[] { this.field, this.decimals });
    case POSTGRES: 
      return DSL.field("{trunc}({0}, {1})", SQLDataType.NUMERIC, new QueryPart[] { this.field.cast(BigDecimal.class), this.decimals }).cast(this.field.getDataType());
    }
    return DSL.field("{trunc}({0}, {1})", this.field.getDataType(), new QueryPart[] { this.field, this.decimals });
  }
}

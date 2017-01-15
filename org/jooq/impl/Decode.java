package org.jooq.impl;

import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.Configuration;
import org.jooq.Field;

class Decode<T, Z>
  extends AbstractFunction<Z>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<T> field;
  private final Field<T> search;
  private final Field<Z> result;
  private final Field<?>[] more;
  
  public Decode(Field<T> field, Field<T> search, Field<Z> result, Field<?>[] more)
  {
    super("decode", result.getDataType(), Utils.combine(field, search, result, more));
    
    this.field = field;
    this.search = search;
    this.result = result;
    this.more = more;
  }
  
  final Field<Z> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    }
    CaseConditionStep<Z> when = DSL.decode().when(this.field.isNotDistinctFrom(this.search), this.result);
    for (int i = 0; i < this.more.length; i += 2) {
      if (i + 1 < this.more.length) {
        when = when.when(this.field.isNotDistinctFrom(this.more[i]), this.more[(i + 1)]);
      } else {
        return when.otherwise(this.more[i]);
      }
    }
    return when;
  }
}

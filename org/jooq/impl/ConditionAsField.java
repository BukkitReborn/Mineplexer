package org.jooq.impl;

import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class ConditionAsField
  extends AbstractFunction<Boolean>
{
  private static final long serialVersionUID = -5921673852489483721L;
  private final Condition condition;
  
  ConditionAsField(Condition condition)
  {
    super(condition.toString(), SQLDataType.BOOLEAN, new Field[0]);
    
    this.condition = condition;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case CUBRID: 
    case FIREBIRD: 
      return DSL.decode().when(this.condition, DSL.inline(Boolean.valueOf(true))).when(DSL.not(this.condition), DSL.inline(Boolean.valueOf(false))).otherwise(DSL.inline((Boolean)null));
    case DERBY: 
    case H2: 
    case HSQLDB: 
    case MARIADB: 
    case MYSQL: 
    case POSTGRES: 
    case SQLITE: 
      return this.condition;
    }
    return this.condition;
  }
}

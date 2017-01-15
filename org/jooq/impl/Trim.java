package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

class Trim
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<String> argument;
  
  Trim(Field<String> argument)
  {
    super("trim", SQLDataType.VARCHAR, new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<String> getFunction0(Configuration configuration)
  {
    return DSL.function("trim", SQLDataType.VARCHAR, new Field[] { this.argument });
  }
}

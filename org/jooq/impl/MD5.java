package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class MD5
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<String> argument;
  
  MD5(Field<String> argument)
  {
    super("md5", SQLDataType.VARCHAR, new Field[] { argument });
    
    this.argument = argument;
  }
  
  final Field<String> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    }
    return DSL.field("{md5}({0})", SQLDataType.VARCHAR, new QueryPart[] { this.argument });
  }
}

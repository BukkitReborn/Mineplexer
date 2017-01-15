package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

class Lpad
  extends AbstractFunction<String>
{
  private static final long serialVersionUID = -7273879239726265322L;
  private final Field<String> field;
  private final Field<? extends Number> length;
  private final Field<String> character;
  
  Lpad(Field<String> field, Field<? extends Number> length)
  {
    this(field, length, null);
  }
  
  Lpad(Field<String> field, Field<? extends Number> length, Field<String> character)
  {
    super("lpad", SQLDataType.VARCHAR, new Field[] { field, length, character });
    
    this.field = field;
    this.length = length;
    this.character = (character == null ? DSL.inline(" ") : character);
  }
  
  final Field<String> getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case SQLITE: 
      return DSL.field("substr(replace(replace(substr(quote(zeroblob((({1} - length({0}) - 1 + length({2})) / length({2}) + 1) / 2)), 3), '''', ''), '0', {2}), 1, ({1} - length({0}))) || {0}", String.class, new QueryPart[] { this.field, this.length, this.character });
    case FIREBIRD: 
      return DSL.field("cast(lpad({0}, {1}, {2}) as varchar(4000))", SQLDataType.VARCHAR, new QueryPart[] { this.field, this.length, this.character });
    }
    return DSL.function("lpad", SQLDataType.VARCHAR, new Field[] { this.field, this.length, this.character });
  }
}

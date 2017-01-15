package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Package;
import org.jooq.Schema;
import org.jooq.tools.StringUtils;

public class PackageImpl
  extends AbstractQueryPart
  implements Package
{
  private static final long serialVersionUID = 7466890004995197675L;
  private final Schema schema;
  private final String name;
  
  public PackageImpl(String name, Schema schema)
  {
    this.schema = schema;
    this.name = name;
  }
  
  public final Schema getSchema()
  {
    return this.schema;
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.literal(getName());
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  public int hashCode()
  {
    return this.name != null ? this.name.hashCode() : 0;
  }
  
  public boolean equals(Object that)
  {
    if (this == that) {
      return true;
    }
    if ((that instanceof PackageImpl)) {
      return (StringUtils.equals(this.schema, ((PackageImpl)that).getSchema())) && (StringUtils.equals(this.name, ((PackageImpl)that).name));
    }
    return super.equals(that);
  }
}

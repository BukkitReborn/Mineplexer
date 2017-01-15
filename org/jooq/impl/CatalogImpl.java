package org.jooq.impl;

import java.util.Collections;
import java.util.List;
import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Schema;
import org.jooq.tools.StringUtils;

public class CatalogImpl
  extends AbstractQueryPart
  implements Catalog
{
  private static final long serialVersionUID = -3650318934053960244L;
  private static final Clause[] CLAUSES = { Clause.CATALOG, Clause.CATALOG_REFERENCE };
  private final String catalogName;
  
  public CatalogImpl(String name)
  {
    this.catalogName = name;
  }
  
  public final String getName()
  {
    return this.catalogName;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.literal(getName());
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final Schema getSchema(String name)
  {
    for (Schema schema : getSchemas()) {
      if (schema.getName().equals(name)) {
        return schema;
      }
    }
    return null;
  }
  
  public List<Schema> getSchemas()
  {
    return Collections.emptyList();
  }
  
  public int hashCode()
  {
    return getName() != null ? getName().hashCode() : 0;
  }
  
  public boolean equals(Object that)
  {
    if (this == that) {
      return true;
    }
    if ((that instanceof CatalogImpl)) {
      return StringUtils.equals(getName(), ((CatalogImpl)that).getName());
    }
    return super.equals(that);
  }
}

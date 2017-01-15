package org.jooq.impl;

import java.util.Collections;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.UDT;
import org.jooq.tools.StringUtils;

public class SchemaImpl
  extends AbstractQueryPart
  implements Schema
{
  private static final long serialVersionUID = -8101463810207566546L;
  private static final Clause[] CLAUSES = { Clause.SCHEMA, Clause.SCHEMA_REFERENCE };
  private final String schemaName;
  
  public SchemaImpl(String name)
  {
    this.schemaName = name;
  }
  
  public final String getName()
  {
    return this.schemaName;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.literal(getName());
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final Table<?> getTable(String name)
  {
    for (Table<?> table : getTables()) {
      if (table.getName().equals(name)) {
        return table;
      }
    }
    return null;
  }
  
  public final UDT<?> getUDT(String name)
  {
    for (UDT<?> udt : getUDTs()) {
      if (udt.getName().equals(name)) {
        return udt;
      }
    }
    return null;
  }
  
  public final Sequence<?> getSequence(String name)
  {
    for (Sequence<?> sequence : getSequences()) {
      if (sequence.getName().equals(name)) {
        return sequence;
      }
    }
    return null;
  }
  
  public List<Table<?>> getTables()
  {
    return Collections.emptyList();
  }
  
  public List<UDT<?>> getUDTs()
  {
    return Collections.emptyList();
  }
  
  public List<Sequence<?>> getSequences()
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
    if ((that instanceof SchemaImpl)) {
      return StringUtils.equals(getName(), ((SchemaImpl)that).getName());
    }
    return super.equals(that);
  }
}

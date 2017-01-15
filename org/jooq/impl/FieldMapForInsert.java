package org.jooq.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;

class FieldMapForInsert
  extends AbstractQueryPartMap<Field<?>, Field<?>>
{
  private static final long serialVersionUID = -2192833491610583485L;
  private static final Clause[] CLAUSES = { Clause.FIELD_ROW };
  
  public final void accept(Context<?> ctx)
  {
    boolean indent = size() > 1;
    
    ctx.sql("(");
    if (indent) {
      ctx.formatIndentStart();
    }
    String separator = "";
    for (Field<?> field : values())
    {
      ctx.sql(separator);
      if (indent) {
        ctx.formatNewLine();
      }
      ctx.visit(field);
      separator = ", ";
    }
    if (indent) {
      ctx.formatIndentEnd().formatNewLine();
    }
    ctx.sql(")");
  }
  
  final void toSQLReferenceKeys(Context<?> ctx)
  {
    boolean indent = size() > 1;
    
    ctx.sql("(");
    if (indent) {
      ctx.formatIndentStart();
    }
    boolean qualify = ctx.qualify();
    ctx.qualify(false);
    
    String separator = "";
    for (Field<?> field : keySet())
    {
      ctx.sql(separator);
      if (indent) {
        ctx.formatNewLine();
      }
      ctx.visit(field);
      separator = ", ";
    }
    ctx.qualify(qualify);
    if (indent) {
      ctx.formatIndentEnd().formatNewLine();
    }
    ctx.sql(")");
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  final void putFields(Collection<? extends Field<?>> fields)
  {
    for (Field<?> field : fields) {
      put(field, null);
    }
  }
  
  final void putValues(Collection<? extends Field<?>> values)
  {
    if (values.size() != size()) {
      throw new IllegalArgumentException("The number of values must match the number of fields: " + this);
    }
    Iterator<? extends Field<?>> it = values.iterator();
    for (Map.Entry<Field<?>, Field<?>> entry : entrySet()) {
      entry.setValue(it.next());
    }
  }
  
  final void set(Map<? extends Field<?>, ?> map)
  {
    for (Map.Entry<? extends Field<?>, ?> entry : map.entrySet())
    {
      Field<?> field = (Field)entry.getKey();
      Object value = entry.getValue();
      
      put((QueryPart)entry.getKey(), Utils.field(value, field));
    }
  }
}

package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.DerivedColumnList;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.tools.StringUtils;

class NameImpl
  extends AbstractQueryPart
  implements Name
{
  private static final long serialVersionUID = 8562325639223483938L;
  private String[] qualifiedName;
  
  NameImpl(String[] qualifiedName)
  {
    this.qualifiedName = qualifiedName;
  }
  
  public final void accept(Context<?> ctx)
  {
    String separator = "";
    for (String name : this.qualifiedName) {
      if (!StringUtils.isEmpty(name))
      {
        ctx.sql(separator).literal(name);
        separator = ".";
      }
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  public final String[] getName()
  {
    return this.qualifiedName;
  }
  
  public final WindowDefinition as(WindowSpecification window)
  {
    return new WindowDefinitionImpl(this, window);
  }
  
  public final <R extends Record> CommonTableExpression<R> as(Select<R> select)
  {
    return fields(new String[0]).as(select);
  }
  
  public final DerivedColumnList fields(String... fieldNames)
  {
    if (this.qualifiedName.length != 1) {
      throw new IllegalStateException("Cannot create a DerivedColumnList from a qualified name : " + Arrays.asList(this.qualifiedName));
    }
    return new DerivedColumnListImpl(this.qualifiedName[0], fieldNames);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(getName());
  }
  
  public boolean equals(Object that)
  {
    if (this == that) {
      return true;
    }
    if ((that instanceof NameImpl)) {
      return Arrays.equals(getName(), ((NameImpl)that).getName());
    }
    return super.equals(that);
  }
}

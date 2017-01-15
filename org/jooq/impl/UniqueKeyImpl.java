package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;

class UniqueKeyImpl<R extends Record>
  extends AbstractKey<R>
  implements UniqueKey<R>
{
  private static final long serialVersionUID = 162853300137140844L;
  final List<ForeignKey<?, R>> references;
  
  UniqueKeyImpl(Table<R> table, TableField<R, ?>... fields)
  {
    super(table, fields);
    
    this.references = new ArrayList();
  }
  
  public final List<ForeignKey<?, R>> getReferences()
  {
    return Collections.unmodifiableList(this.references);
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("UNIQUE KEY (");
    
    String s1 = "";
    for (Field<?> field : getFields())
    {
      sb.append(s1);
      sb.append(field);
      
      s1 = ", ";
    }
    sb.append(")");
    return sb.toString();
  }
}

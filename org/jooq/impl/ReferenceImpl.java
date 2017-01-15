package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.AttachableInternal;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.RowN;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.exception.DetachedException;

class ReferenceImpl<R extends Record, O extends Record>
  extends AbstractKey<R>
  implements ForeignKey<R, O>
{
  private static final long serialVersionUID = 3636724364192618701L;
  private final UniqueKey<O> key;
  
  ReferenceImpl(UniqueKey<O> key, Table<R> table, TableField<R, ?>... fields)
  {
    super(table, fields);
    
    this.key = key;
  }
  
  public final UniqueKey<O> getKey()
  {
    return this.key;
  }
  
  public final O fetchParent(R record)
  {
    return Utils.filterOne(fetchParents(new Record[] { record }));
  }
  
  public final Result<O> fetchParents(R... records)
  {
    return fetchParents(Utils.list(records));
  }
  
  public final Result<R> fetchChildren(O record)
  {
    return fetchChildren(Utils.list(new Record[] { record }));
  }
  
  public final Result<R> fetchChildren(O... records)
  {
    return fetchChildren(Utils.list(records));
  }
  
  public final Result<O> fetchParents(Collection<? extends R> records)
  {
    if ((records == null) || (records.size() == 0)) {
      return new ResultImpl(new DefaultConfiguration(), this.key.getFields());
    }
    return fetch(records, this.key.getTable(), this.key.getFieldsArray(), getFieldsArray());
  }
  
  public final Result<R> fetchChildren(Collection<? extends O> records)
  {
    if ((records == null) || (records.size() == 0)) {
      return new ResultImpl(new DefaultConfiguration(), getFields());
    }
    return fetch(records, getTable(), getFieldsArray(), this.key.getFieldsArray());
  }
  
  private static <R1 extends Record, R2 extends Record> Result<R1> fetch(Collection<? extends R2> records, Table<R1> table, TableField<R1, ?>[] fields1, TableField<R2, ?>[] fields2)
  {
    if (fields1.length == 1) {
      return extractDSLContext(records).selectFrom(table).where(new Condition[] { fields1[0].in(extractValues(records, fields2[0])) }).fetch();
    }
    return extractDSLContext(records).selectFrom(table).where(new Condition[] { DSL.row(fields1).in(extractRows(records, fields2)) }).fetch();
  }
  
  private static <R extends Record> List<Object> extractValues(Collection<? extends R> records, TableField<R, ?> field2)
  {
    List<Object> result = new ArrayList();
    for (R record : records) {
      result.add(record.getValue(field2));
    }
    return result;
  }
  
  private static <R extends Record> List<RowN> extractRows(Collection<? extends R> records, TableField<R, ?>[] fields)
  {
    List<RowN> rows = new ArrayList();
    for (R record : records)
    {
      Object[] values = new Object[fields.length];
      for (int i = 0; i < fields.length; i++) {
        values[i] = record.getValue(fields[i]);
      }
      rows.add(DSL.row(values));
    }
    return rows;
  }
  
  private static <R extends Record> DSLContext extractDSLContext(Collection<? extends R> records)
    throws DetachedException
  {
    R first = (Record)Utils.first(records);
    if ((first instanceof AttachableInternal)) {
      return DSL.using(((AttachableInternal)first).configuration());
    }
    throw new DetachedException("Supply at least one attachable record");
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("FOREIGN KEY (");
    
    String s1 = "";
    for (Iterator localIterator = getFields().iterator(); localIterator.hasNext();)
    {
      field = (Field)localIterator.next();
      sb.append(s1);
      sb.append(field);
      
      s1 = ", ";
    }
    Field<?> field;
    sb.append(") REFERENCES ");
    sb.append(this.key.getTable());
    sb.append("(");
    
    String s2 = "";
    for (Field<?> field : getFields())
    {
      sb.append(s2);
      sb.append(field);
      
      s2 = ", ";
    }
    sb.append(")");
    return sb.toString();
  }
}

package org.jooq.impl;

import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import org.jooq.Binding;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Row;
import org.jooq.Schema;
import org.jooq.UDT;
import org.jooq.UDTRecord;

public class UDTRecordImpl<R extends UDTRecord<R>>
  extends AbstractRecord
  implements UDTRecord<R>
{
  private static final long serialVersionUID = 5671315498175872799L;
  private final UDT<R> udt;
  
  public UDTRecordImpl(UDT<R> udt)
  {
    super(udt.fields());
    
    this.udt = udt;
  }
  
  public final UDT<R> getUDT()
  {
    return this.udt;
  }
  
  public Row fieldsRow()
  {
    return this.fields;
  }
  
  public Row valuesRow()
  {
    return new RowImpl(Utils.fields(intoArray(), this.fields.fields.fields()));
  }
  
  public final String getSQLTypeName()
    throws SQLException
  {
    StringBuilder sb = new StringBuilder();
    
    Configuration configuration = DefaultExecuteContext.localConfiguration();
    if (configuration != null)
    {
      Schema schema = Utils.getMappedSchema(configuration, getUDT().getSchema());
      if (schema != null)
      {
        sb.append(schema.getName());
        sb.append(".");
      }
    }
    sb.append(getUDT().getName());
    return sb.toString();
  }
  
  public final void readSQL(SQLInput stream, String typeName)
    throws SQLException
  {
    Configuration configuration = DefaultExecuteContext.localConfiguration();
    for (Field<?> field : getUDT().fields()) {
      setValue(configuration, stream, field);
    }
  }
  
  private final <T> void setValue(Configuration configuration, SQLInput stream, Field<T> field)
    throws SQLException
  {
    DefaultBindingGetSQLInputContext<T> out = new DefaultBindingGetSQLInputContext(configuration, stream);
    field.getBinding().get(out);
    setValue(field, out.value());
  }
  
  public final void writeSQL(SQLOutput stream)
    throws SQLException
  {
    for (Field<?> field : getUDT().fields()) {
      setValue(stream, field);
    }
  }
  
  private final <T> void setValue(SQLOutput stream, Field<T> field)
    throws SQLException
  {
    field.getBinding().set(new DefaultBindingSetSQLOutputContext(DefaultExecuteContext.localConfiguration(), stream, getValue(field)));
  }
  
  public String toString()
  {
    StringBuilder result = new StringBuilder();
    String separator = "";
    
    result.append(create().render(getUDT()));
    result.append("(");
    
    Object[] array = intoArray();
    if (array != null) {
      for (Object o : array)
      {
        result.append(separator);
        result.append(o);
        
        separator = ", ";
      }
    }
    result.append(")");
    
    return result.toString();
  }
}

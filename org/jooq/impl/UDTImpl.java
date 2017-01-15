package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Row;
import org.jooq.Schema;
import org.jooq.UDT;
import org.jooq.UDTField;
import org.jooq.UDTRecord;

public class UDTImpl<R extends UDTRecord<R>>
  extends AbstractQueryPart
  implements UDT<R>
{
  private static final long serialVersionUID = -2208672099190913126L;
  private final Schema schema;
  private final String name;
  private final Fields<R> fields;
  private transient DataType<R> type;
  
  public UDTImpl(String name, Schema schema)
  {
    this.fields = new Fields(new Field[0]);
    this.name = name;
    this.schema = schema;
  }
  
  public final Schema getSchema()
  {
    return this.schema;
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final Row fieldsRow()
  {
    return new RowImpl(this.fields);
  }
  
  public final <T> Field<T> field(Field<T> field)
  {
    return fieldsRow().field(field);
  }
  
  public final Field<?> field(String string)
  {
    return fieldsRow().field(string);
  }
  
  public final Field<?> field(int index)
  {
    return fieldsRow().field(index);
  }
  
  public final Field<?>[] fields()
  {
    return fieldsRow().fields();
  }
  
  final Fields<R> fields0()
  {
    return this.fields;
  }
  
  public Class<R> getRecordType()
  {
    throw new UnsupportedOperationException();
  }
  
  public final R newRecord()
  {
    return DSL.using(new DefaultConfiguration()).newRecord(this);
  }
  
  public final DataType<R> getDataType()
  {
    if (this.type == null) {
      this.type = new UDTDataType(this);
    }
    return this.type;
  }
  
  public final void accept(Context<?> ctx)
  {
    Schema mappedSchema = Utils.getMappedSchema(ctx.configuration(), getSchema());
    if (mappedSchema != null)
    {
      ctx.visit(mappedSchema);
      ctx.sql(".");
    }
    ctx.visit(DSL.name(new String[] { getName() }));
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  protected static final <R extends UDTRecord<R>, T> UDTField<R, T> createField(String name, DataType<T> type, UDT<R> udt)
  {
    return createField(name, type, udt, "", null, null);
  }
  
  protected static final <R extends UDTRecord<R>, T> UDTField<R, T> createField(String name, DataType<T> type, UDT<R> udt, String comment)
  {
    return createField(name, type, udt, comment, null, null);
  }
  
  protected static final <R extends UDTRecord<R>, T, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Converter<T, U> converter)
  {
    return createField(name, type, udt, comment, converter, null);
  }
  
  protected static final <R extends UDTRecord<R>, T, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Binding<T, U> binding)
  {
    return createField(name, type, udt, comment, null, binding);
  }
  
  protected static final <R extends UDTRecord<R>, T, X, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Converter<X, U> converter, Binding<T, X> binding)
  {
    Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
    
    DataType<U> actualType = (converter == null) && (binding == null) ? type : type.asConvertedDataType(actualBinding);
    
    UDTFieldImpl<R, U> udtField = new UDTFieldImpl(name, actualType, udt, comment, actualBinding);
    
    return udtField;
  }
  
  public int hashCode()
  {
    return this.name.hashCode();
  }
}

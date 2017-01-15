package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.UDTRecord;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.util.h2.H2DataType;

class ArrayTable
  extends AbstractTable<Record>
{
  private static final long serialVersionUID = 2380426377794577041L;
  private final Field<?> array;
  private final Fields<Record> field;
  private final String alias;
  
  ArrayTable(Field<?> array)
  {
    this(array, "array_table");
  }
  
  ArrayTable(Field<?> array, String alias)
  {
    super(alias);
    Class<?> arrayType;
    Class<?> arrayType;
    if (array.getDataType().getType().isArray()) {
      arrayType = array.getDataType().getType().getComponentType();
    } else {
      arrayType = Object.class;
    }
    this.array = array;
    this.alias = alias;
    this.field = init(alias, arrayType);
    
    init(alias, arrayType);
  }
  
  ArrayTable(Field<?> array, String alias, String[] fieldAliases)
  {
    super(alias);
    
    throw new UnsupportedOperationException("This constructor is not yet implemented");
  }
  
  private static final Fields<Record> init(String alias, Class<?> arrayType)
  {
    List<Field<?>> result = new ArrayList();
    if (UDTRecord.class.isAssignableFrom(arrayType)) {
      try
      {
        UDTRecord<?> record = (UDTRecord)arrayType.newInstance();
        for (Field<?> f : record.fields()) {
          result.add(DSL.fieldByName(f.getDataType(), new String[] { alias, f.getName() }));
        }
      }
      catch (Exception e)
      {
        throw new DataTypeException("Bad UDT Type : " + arrayType, e);
      }
    } else {
      result.add(DSL.fieldByName(DSL.getDataType(arrayType), new String[] { alias, "COLUMN_VALUE" }));
    }
    return new Fields(result);
  }
  
  public final Class<? extends Record> getRecordType()
  {
    return RecordImpl.class;
  }
  
  public final Table<Record> as(String as)
  {
    return new ArrayTable(this.array, as);
  }
  
  public final Table<Record> as(String as, String... fieldAliases)
  {
    return new ArrayTable(this.array, as, fieldAliases);
  }
  
  public final boolean declaresTables()
  {
    return true;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(table(ctx.configuration()));
  }
  
  private final Table<Record> table(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case H2: 
      return new H2ArrayTable(null).as(this.alias);
    case HSQLDB: 
    case POSTGRES: 
      return new PostgresHSQLDBTable(null).as(this.alias);
    }
    if ((this.array.getDataType().getType().isArray()) && ((this.array instanceof Param))) {
      return simulate();
    }
    throw new SQLDialectNotSupportedException("ARRAY TABLE is not supported for " + configuration.dialect());
  }
  
  private class PostgresHSQLDBTable
    extends ArrayTable.DialectArrayTable
  {
    private static final long serialVersionUID = 6989279597964488457L;
    
    private PostgresHSQLDBTable()
    {
      super();
    }
    
    public final void accept(Context<?> ctx)
    {
      ctx.sql("(").keyword("select").sql(" * ").keyword("from").sql(" ").keyword("unnest").sql("(").visit(ArrayTable.this.array).sql(") ").keyword("as").sql(" ").literal(ArrayTable.this.alias).sql("(").literal("COLUMN_VALUE").sql("))");
    }
  }
  
  private class H2ArrayTable
    extends ArrayTable.DialectArrayTable
  {
    private static final long serialVersionUID = 8679404596822098711L;
    
    private H2ArrayTable()
    {
      super();
    }
    
    public final void accept(Context<?> ctx)
    {
      ctx.keyword("table(").sql("COLUMN_VALUE ");
      if (ArrayTable.this.array.getDataType().getType() == Object[].class) {
        ctx.keyword(H2DataType.VARCHAR.getTypeName());
      } else {
        ctx.keyword(ArrayTable.this.array.getDataType().getTypeName());
      }
      ctx.sql(" = ").visit(ArrayTable.this.array).sql(")");
    }
  }
  
  private abstract class DialectArrayTable
    extends AbstractTable<Record>
  {
    private static final long serialVersionUID = 2662639259338694177L;
    
    DialectArrayTable()
    {
      super();
    }
    
    public final Class<? extends Record> getRecordType()
    {
      return RecordImpl.class;
    }
    
    public final Table<Record> as(String as)
    {
      return new TableAlias(this, as);
    }
    
    public final Table<Record> as(String as, String... fieldAliases)
    {
      return new TableAlias(this, as, fieldAliases);
    }
    
    final Fields<Record> fields0()
    {
      return ArrayTable.this.fields0();
    }
  }
  
  private final ArrayTableSimulation simulate()
  {
    return new ArrayTableSimulation((Object[])((Param)this.array).getValue(), this.alias);
  }
  
  final Fields<Record> fields0()
  {
    return this.field;
  }
}

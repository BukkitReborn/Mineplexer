package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.exception.SQLDialectNotSupportedException;

public class SequenceImpl<T extends Number>
  extends AbstractQueryPart
  implements Sequence<T>
{
  private static final long serialVersionUID = 6224349401603636427L;
  private static final Clause[] CLAUSES = { Clause.SEQUENCE, Clause.SEQUENCE_REFERENCE };
  final String name;
  final boolean nameIsPlainSQL;
  final Schema schema;
  final DataType<T> type;
  
  public SequenceImpl(String name, Schema schema, DataType<T> type)
  {
    this(name, schema, type, false);
  }
  
  SequenceImpl(String name, Schema schema, DataType<T> type, boolean nameIsPlainSQL)
  {
    this.name = name;
    this.schema = schema;
    this.type = type;
    this.nameIsPlainSQL = nameIsPlainSQL;
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final Schema getSchema()
  {
    return this.schema;
  }
  
  public final DataType<T> getDataType()
  {
    return this.type;
  }
  
  public final Field<T> currval()
  {
    return new SequenceFunction("currval");
  }
  
  public final Field<T> nextval()
  {
    return new SequenceFunction("nextval");
  }
  
  private class SequenceFunction
    extends AbstractFunction<T>
  {
    private static final long serialVersionUID = 2292275568395094887L;
    private final String method;
    
    SequenceFunction(String method)
    {
      super(SequenceImpl.this.type, new Field[0]);
      
      this.method = method;
    }
    
    final Field<T> getFunction0(Configuration configuration)
    {
      SQLDialect family = configuration.dialect().family();
      switch (SequenceImpl.1.$SwitchMap$org$jooq$SQLDialect[family.ordinal()])
      {
      case 1: 
        String field = this.method + "('" + getQualifiedName(configuration) + "')";
        return DSL.field(field, getDataType());
      case 2: 
        String field = this.method + "(" + getQualifiedName(configuration, true) + ")";
        return DSL.field(field, getDataType());
      case 3: 
      case 4: 
      case 5: 
        if ("nextval".equals(this.method))
        {
          String field = "next value for " + getQualifiedName(configuration);
          return DSL.field(field, getDataType());
        }
        if (family == SQLDialect.FIREBIRD) {
          return DSL.field("gen_id(" + getQualifiedName(configuration) + ", 0)", getDataType());
        }
        throw new SQLDialectNotSupportedException("The sequence's current value functionality is not supported for the " + family + " dialect.");
      case 6: 
        String field = getQualifiedName(configuration) + ".";
        if ("nextval".equals(this.method)) {
          field = field + "next_value";
        } else {
          field = field + "current_value";
        }
        return DSL.field(field, getDataType());
      }
      String field = getQualifiedName(configuration) + "." + this.method;
      return DSL.field(field, getDataType());
    }
    
    private final String getQualifiedName(Configuration configuration)
    {
      return getQualifiedName(configuration, false);
    }
    
    private final String getQualifiedName(Configuration configuration, boolean asStringLiterals)
    {
      RenderContext local = create(configuration).renderContext();
      SequenceImpl.this.accept0(local, asStringLiterals);
      return local.render();
    }
  }
  
  public final void accept(Context<?> ctx)
  {
    accept0(ctx, false);
  }
  
  private final void accept0(Context<?> ctx, boolean asStringLiterals)
  {
    Schema mappedSchema = Utils.getMappedSchema(ctx.configuration(), this.schema);
    if ((mappedSchema != null) && (ctx.family() != SQLDialect.CUBRID)) {
      if (asStringLiterals) {
        ctx.visit(DSL.inline(mappedSchema.getName())).sql(", ");
      } else {
        ctx.visit(mappedSchema).sql(".");
      }
    }
    if (asStringLiterals) {
      ctx.visit(DSL.inline(this.name));
    } else if (this.nameIsPlainSQL) {
      ctx.sql(this.name);
    } else {
      ctx.literal(this.name);
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public int hashCode()
  {
    return this.name.hashCode();
  }
}

package org.jooq.impl;

import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.RenderContext;
import org.jooq.Schema;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.exception.SQLDialectNotSupportedException;

class UDTConstant<R extends UDTRecord<R>>
  extends AbstractParam<R>
{
  private static final long serialVersionUID = 6807729087019209084L;
  
  UDTConstant(R value)
  {
    super(value, value.getUDT().getDataType());
  }
  
  public void accept(Context<?> ctx)
  {
    if ((ctx instanceof RenderContext)) {
      toSQL0((RenderContext)ctx);
    } else {
      bind0((BindContext)ctx);
    }
  }
  
  final void toSQL0(RenderContext context)
  {
    switch (context.configuration().dialect().family())
    {
    case POSTGRES: 
      toSQLInline(context);
      return;
    }
    toSQLInline(context);
  }
  
  private void toSQLInline(RenderContext context)
  {
    context.sql(getInlineConstructor(context));
    context.sql("(");
    
    String separator = "";
    for (Field<?> field : ((UDTRecord)this.value).fields())
    {
      context.sql(separator);
      context.visit(DSL.val(((UDTRecord)this.value).getValue(field), field));
      separator = ", ";
    }
    context.sql(")");
  }
  
  private String getInlineConstructor(RenderContext context)
  {
    switch (context.configuration().dialect().family())
    {
    case POSTGRES: 
      return "ROW";
    }
    UDT<?> udt = ((UDTRecord)this.value).getUDT();
    Schema mappedSchema = Utils.getMappedSchema(context.configuration(), udt.getSchema());
    if (mappedSchema != null) {
      return mappedSchema + "." + udt.getName();
    }
    return udt.getName();
  }
  
  final void bind0(BindContext context)
  {
    switch (context.configuration().dialect().family())
    {
    case POSTGRES: 
      for (Field<?> field : ((UDTRecord)this.value).fields()) {
        context.visit(DSL.val(((UDTRecord)this.value).getValue(field)));
      }
      break;
    default: 
      throw new SQLDialectNotSupportedException("UDTs not supported in dialect " + context.configuration().dialect());
    }
  }
}

package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.RenderContext.CastMode;

class Cast<T>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -6776617606751542856L;
  private final Field<?> field;
  
  public Cast(Field<?> field, DataType<T> type)
  {
    super("cast", type, new Field[0]);
    
    this.field = field;
  }
  
  private final DataType<T> getSQLDataType()
  {
    return getDataType().getSQLDataType();
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    case DERBY: 
      return new CastDerby(null);
    }
    return new Native(null);
  }
  
  private class CastDerby
    extends Cast<T>.Native
  {
    private static final long serialVersionUID = -8737153188122391258L;
    
    private CastDerby()
    {
      super(null);
    }
    
    private final Field<Boolean> asDecodeNumberToBoolean()
    {
      return DSL.decode().value(Cast.this.field).when(DSL.inline(Integer.valueOf(0)), DSL.inline(Boolean.valueOf(false))).when(DSL.inline((Integer)null), DSL.inline((Boolean)null)).otherwise(DSL.inline(Boolean.valueOf(true)));
    }
    
    private final Field<Boolean> asDecodeVarcharToBoolean()
    {
      Field<String> s = Cast.this.field;
      
      return DSL.decode().when(s.equal(DSL.inline("0")), DSL.inline(Boolean.valueOf(false))).when(DSL.lower(s).equal(DSL.inline("false")), DSL.inline(Boolean.valueOf(false))).when(DSL.lower(s).equal(DSL.inline("f")), DSL.inline(Boolean.valueOf(false))).when(s.isNull(), DSL.inline((Boolean)null)).otherwise(DSL.inline(Boolean.valueOf(true)));
    }
    
    public final void accept(Context<?> ctx)
    {
      RenderContext.CastMode castMode = ctx.castMode();
      if ((Cast.this.field.getDataType().isNumeric()) && 
        (SQLDataType.VARCHAR.equals(Cast.this.getSQLDataType())))
      {
        ctx.keyword("trim").sql("(").keyword("cast").sql("(").keyword("cast").sql("(").castMode(RenderContext.CastMode.NEVER).visit(Cast.this.field).castMode(castMode).sql(" ").keyword("as").sql(" char(38))").sql(" ").keyword("as").sql(" ").keyword(Cast.this.getDataType(ctx.configuration()).getCastTypeName(ctx.configuration())).sql("))");
        
        return;
      }
      if (Cast.this.field.getDataType().isString()) {
        if (Arrays.asList(new DataType[] { SQLDataType.FLOAT, SQLDataType.DOUBLE, SQLDataType.REAL }).contains(Cast.this.getSQLDataType()))
        {
          ctx.keyword("cast").sql("(").keyword("cast").sql("(").castMode(RenderContext.CastMode.NEVER).visit(Cast.this.field).castMode(castMode).sql(" ").keyword("as").sql(" ").keyword("decimal").sql(") ").keyword("as").sql(" ").keyword(Cast.this.getDataType(ctx.configuration()).getCastTypeName(ctx.configuration())).sql(")");
          
          return;
        }
      }
      if ((Cast.this.field.getDataType().isNumeric()) && 
        (SQLDataType.BOOLEAN.equals(Cast.this.getSQLDataType())))
      {
        ctx.visit(asDecodeNumberToBoolean());
        return;
      }
      if ((Cast.this.field.getDataType().isString()) && 
        (SQLDataType.BOOLEAN.equals(Cast.this.getSQLDataType())))
      {
        ctx.visit(asDecodeVarcharToBoolean());
        return;
      }
      super.accept(ctx);
    }
  }
  
  private class Native
    extends AbstractQueryPart
  {
    private static final long serialVersionUID = -8497561014419483312L;
    
    private Native() {}
    
    public void accept(Context<?> ctx)
    {
      RenderContext.CastMode castMode = ctx.castMode();
      
      ctx.keyword("cast").sql("(")
        .castMode(RenderContext.CastMode.NEVER)
        .visit(Cast.this.field)
        .castMode(castMode)
        .sql(" ").keyword("as").sql(" ")
        .keyword(Cast.this.getDataType(ctx.configuration()).getCastTypeName(ctx.configuration()))
        .sql(")");
    }
    
    public final Clause[] clauses(Context<?> ctx)
    {
      return null;
    }
  }
}

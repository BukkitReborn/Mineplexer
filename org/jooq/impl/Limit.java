package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.RenderContext.CastMode;
import org.jooq.conf.ParamType;

class Limit
  extends AbstractQueryPart
{
  private static final long serialVersionUID = 2053741242981425602L;
  private Field<Integer> numberOfRows;
  private Field<Integer> offset;
  private Field<Integer> offsetOrZero = DSL.inline(Integer.valueOf(0));
  private Field<Integer> offsetPlusOne = DSL.inline(Integer.valueOf(1));
  private boolean rendersParams;
  
  public final void accept(Context<?> context)
  {
    ParamType paramType = context.paramType();
    RenderContext.CastMode castMode = context.castMode();
    switch (context.configuration().dialect())
    {
    case MARIADB: 
    case MYSQL: 
    case H2: 
    case HSQLDB: 
    case POSTGRES: 
    case SQLITE: 
      context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("limit").sql(" ").visit(this.numberOfRows);
      if (!offsetZero()) {
        context.sql(" ").keyword("offset").sql(" ").visit(this.offsetOrZero);
      }
      context.castMode(castMode);
      
      break;
    case CUBRID: 
      context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("limit").sql(" ").visit(this.offsetOrZero).sql(", ").visit(this.numberOfRows).castMode(castMode);
      
      break;
    case FIREBIRD: 
      context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("rows").sql(" ").visit(getLowerRownum().add(DSL.inline(Integer.valueOf(1)))).sql(" ").keyword("to").sql(" ").visit(getUpperRownum()).castMode(castMode);
      
      break;
    case DERBY: 
      context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("offset").sql(" ").visit(this.offsetOrZero).sql(" ").keyword("rows fetch next").sql(" ").visit(this.numberOfRows).sql(" ").keyword("rows only").castMode(castMode);
      
      break;
    default: 
      context.castMode(RenderContext.CastMode.NEVER).formatSeparator().keyword("limit").sql(" ").visit(this.numberOfRows);
      if (!offsetZero()) {
        context.sql(" ").keyword("offset").sql(" ").visit(this.offsetOrZero);
      }
      context.castMode(castMode);
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  final boolean offsetZero()
  {
    return this.offset == null;
  }
  
  final Field<Integer> getLowerRownum()
  {
    return this.offsetOrZero;
  }
  
  final Field<Integer> getUpperRownum()
  {
    return this.offsetOrZero.add(this.numberOfRows);
  }
  
  final boolean isApplicable()
  {
    return (this.offset != null) || (this.numberOfRows != null);
  }
  
  final boolean rendersParams()
  {
    return this.rendersParams;
  }
  
  final void setOffset(int offset)
  {
    if (offset != 0)
    {
      this.offset = DSL.val(Integer.valueOf(offset));
      this.offsetOrZero = this.offset;
      this.offsetPlusOne = DSL.val(Integer.valueOf(offset + 1));
    }
  }
  
  final void setOffset(Param<Integer> offset)
  {
    this.offset = offset;
    this.offsetOrZero = offset;
    this.rendersParams = true;
  }
  
  final void setNumberOfRows(int numberOfRows)
  {
    this.numberOfRows = DSL.val(Integer.valueOf(numberOfRows));
  }
  
  final void setNumberOfRows(Param<Integer> numberOfRows)
  {
    this.numberOfRows = numberOfRows;
    this.rendersParams = true;
  }
}

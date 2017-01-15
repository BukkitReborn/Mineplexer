package org.jooq;

import java.sql.PreparedStatement;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;

public abstract interface Context<C extends Context<C>>
  extends Scope
{
  public abstract C visit(QueryPart paramQueryPart)
    throws DataAccessException;
  
  public abstract C start(Clause paramClause);
  
  public abstract C end(Clause paramClause);
  
  public abstract boolean declareFields();
  
  public abstract C declareFields(boolean paramBoolean);
  
  public abstract boolean declareTables();
  
  public abstract C declareTables(boolean paramBoolean);
  
  public abstract boolean declareWindows();
  
  public abstract C declareWindows(boolean paramBoolean);
  
  public abstract boolean declareCTE();
  
  public abstract C declareCTE(boolean paramBoolean);
  
  public abstract boolean subquery();
  
  public abstract C subquery(boolean paramBoolean);
  
  public abstract int nextIndex();
  
  public abstract int peekIndex();
  
  public abstract PreparedStatement statement();
  
  public abstract BindContext bindValue(Object paramObject, Field<?> paramField)
    throws DataAccessException;
  
  public abstract String peekAlias();
  
  public abstract String nextAlias();
  
  public abstract String render();
  
  public abstract String render(QueryPart paramQueryPart);
  
  public abstract C keyword(String paramString);
  
  public abstract C sql(String paramString);
  
  public abstract C sql(String paramString, boolean paramBoolean);
  
  public abstract C sql(char paramChar);
  
  public abstract C sql(int paramInt);
  
  public abstract C format(boolean paramBoolean);
  
  public abstract boolean format();
  
  public abstract C formatNewLine();
  
  public abstract C formatNewLineAfterPrintMargin();
  
  public abstract C formatSeparator();
  
  public abstract C formatIndentStart();
  
  public abstract C formatIndentStart(int paramInt);
  
  public abstract C formatIndentLockStart();
  
  public abstract C formatIndentEnd();
  
  public abstract C formatIndentEnd(int paramInt);
  
  public abstract C formatIndentLockEnd();
  
  public abstract C formatPrintMargin(int paramInt);
  
  public abstract C literal(String paramString);
  
  public abstract boolean qualify();
  
  public abstract C qualify(boolean paramBoolean);
  
  public abstract ParamType paramType();
  
  public abstract C paramType(ParamType paramParamType);
  
  public abstract RenderContext.CastMode castMode();
  
  public abstract C castMode(RenderContext.CastMode paramCastMode);
  
  @Deprecated
  public abstract Boolean cast();
  
  @Deprecated
  public abstract C castModeSome(SQLDialect... paramVarArgs);
}

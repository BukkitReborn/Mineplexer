package org.jooq;

import org.jooq.conf.ParamType;

public abstract interface RenderContext
  extends Context<RenderContext>
{
  public abstract String peekAlias();
  
  public abstract String nextAlias();
  
  public abstract String render();
  
  public abstract String render(QueryPart paramQueryPart);
  
  public abstract RenderContext keyword(String paramString);
  
  public abstract RenderContext sql(String paramString);
  
  public abstract RenderContext sql(String paramString, boolean paramBoolean);
  
  public abstract RenderContext sql(char paramChar);
  
  public abstract RenderContext sql(int paramInt);
  
  @Deprecated
  public abstract RenderContext sql(QueryPart paramQueryPart);
  
  public abstract RenderContext format(boolean paramBoolean);
  
  public abstract boolean format();
  
  public abstract RenderContext formatNewLine();
  
  public abstract RenderContext formatNewLineAfterPrintMargin();
  
  public abstract RenderContext formatSeparator();
  
  public abstract RenderContext formatIndentStart();
  
  public abstract RenderContext formatIndentStart(int paramInt);
  
  public abstract RenderContext formatIndentLockStart();
  
  public abstract RenderContext formatIndentEnd();
  
  public abstract RenderContext formatIndentEnd(int paramInt);
  
  public abstract RenderContext formatIndentLockEnd();
  
  public abstract RenderContext formatPrintMargin(int paramInt);
  
  public abstract RenderContext literal(String paramString);
  
  @Deprecated
  public abstract boolean inline();
  
  @Deprecated
  public abstract RenderContext inline(boolean paramBoolean);
  
  public abstract boolean qualify();
  
  public abstract RenderContext qualify(boolean paramBoolean);
  
  @Deprecated
  public abstract boolean namedParams();
  
  @Deprecated
  public abstract RenderContext namedParams(boolean paramBoolean);
  
  public abstract ParamType paramType();
  
  public abstract RenderContext paramType(ParamType paramParamType);
  
  public abstract CastMode castMode();
  
  public abstract RenderContext castMode(CastMode paramCastMode);
  
  @Deprecated
  public abstract Boolean cast();
  
  @Deprecated
  public abstract RenderContext castModeSome(SQLDialect... paramVarArgs);
  
  public static enum CastMode
  {
    ALWAYS,  NEVER,  SOME,  DEFAULT;
    
    private CastMode() {}
  }
}

package org.jooq;

public abstract interface VisitContext
  extends Scope
{
  public abstract Clause clause();
  
  public abstract Clause[] clauses();
  
  public abstract int clausesLength();
  
  public abstract QueryPart queryPart();
  
  public abstract void queryPart(QueryPart paramQueryPart);
  
  public abstract QueryPart[] queryParts();
  
  public abstract int queryPartsLength();
  
  public abstract Context<?> context();
  
  public abstract RenderContext renderContext();
  
  public abstract BindContext bindContext()
    throws UnsupportedOperationException;
}

package org.jooq;

import java.util.EventListener;

public abstract interface VisitListener
  extends EventListener
{
  public abstract void clauseStart(VisitContext paramVisitContext);
  
  public abstract void clauseEnd(VisitContext paramVisitContext);
  
  public abstract void visitStart(VisitContext paramVisitContext);
  
  public abstract void visitEnd(VisitContext paramVisitContext);
}

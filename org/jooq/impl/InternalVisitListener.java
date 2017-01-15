package org.jooq.impl;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.VisitContext;

class InternalVisitListener
  extends DefaultVisitListener
{
  private Deque<Object> stack = new LinkedList();
  
  public void clauseStart(VisitContext ctx)
  {
    if (ctx.clause() == Clause.SELECT)
    {
      this.stack.push(ctx.context().data("org.jooq.configuration.locally-scoped-data-map"));
      ctx.context().data("org.jooq.configuration.locally-scoped-data-map", new HashMap());
    }
  }
  
  public void clauseEnd(VisitContext ctx)
  {
    if (ctx.clause() == Clause.SELECT) {
      ctx.context().data("org.jooq.configuration.locally-scoped-data-map", this.stack.pop());
    }
  }
}

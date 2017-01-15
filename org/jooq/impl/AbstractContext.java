package org.jooq.impl;

import java.sql.PreparedStatement;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import org.jooq.BindContext;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.RenderContext.CastMode;
import org.jooq.SQLDialect;
import org.jooq.VisitContext;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;

abstract class AbstractContext<C extends Context<C>>
  extends AbstractScope
  implements Context<C>
{
  final PreparedStatement stmt;
  boolean declareFields;
  boolean declareTables;
  boolean declareWindows;
  boolean declareCTE;
  boolean subquery;
  int index;
  final VisitListener[] visitListeners;
  private final Deque<Clause> visitClauses;
  private final AbstractContext<C>.DefaultVisitContext visitContext;
  private final Deque<QueryPart> visitParts;
  ParamType paramType = ParamType.INDEXED;
  boolean qualify = true;
  RenderContext.CastMode castMode = RenderContext.CastMode.DEFAULT;
  
  AbstractContext(Configuration configuration, PreparedStatement stmt)
  {
    super(configuration);
    
    this.stmt = stmt;
    this.visitClauses = new ArrayDeque();
    
    VisitListenerProvider[] providers = configuration.visitListenerProviders();
    
    this.visitListeners = new VisitListener[providers.length + 1];
    this.visitContext = new DefaultVisitContext(null);
    this.visitParts = new ArrayDeque();
    for (int i = 0; i < providers.length; i++) {
      this.visitListeners[i] = providers[i].provide();
    }
    this.visitListeners[providers.length] = new InternalVisitListener();
  }
  
  public final C visit(QueryPart part)
  {
    if (part != null)
    {
      Clause[] clauses = this.visitListeners.length > 0 ? clause(part) : null;
      if (clauses != null) {
        for (int i = 0; i < clauses.length; i++) {
          start(clauses[i]);
        }
      }
      QueryPart original = part;
      QueryPart replacement = start(part);
      if (original == replacement) {
        visit0(original);
      } else {
        visit0(replacement);
      }
      end(replacement);
      if (clauses != null) {
        for (int i = clauses.length - 1; i >= 0; i--) {
          end(clauses[i]);
        }
      }
    }
    return this;
  }
  
  private final Clause[] clause(QueryPart part)
  {
    if (((part instanceof QueryPartInternal)) && (data("org.jooq.configuration.omit-clause-event-emission") == null)) {
      return ((QueryPartInternal)part).clauses(this);
    }
    return null;
  }
  
  public final C start(Clause clause)
  {
    if (clause != null)
    {
      this.visitClauses.addLast(clause);
      for (VisitListener listener : this.visitListeners) {
        listener.clauseStart(this.visitContext);
      }
    }
    return this;
  }
  
  public final C end(Clause clause)
  {
    if (clause != null)
    {
      for (VisitListener listener : this.visitListeners) {
        listener.clauseEnd(this.visitContext);
      }
      if (this.visitClauses.removeLast() != clause) {
        throw new IllegalStateException("Mismatch between visited clauses!");
      }
    }
    return this;
  }
  
  private final QueryPart start(QueryPart part)
  {
    this.visitParts.addLast(part);
    for (VisitListener listener : this.visitListeners) {
      listener.visitStart(this.visitContext);
    }
    return (QueryPart)this.visitParts.peekLast();
  }
  
  private final void end(QueryPart part)
  {
    for (VisitListener listener : this.visitListeners) {
      listener.visitEnd(this.visitContext);
    }
    if (this.visitParts.removeLast() != part) {
      throw new RuntimeException("Mismatch between visited query parts");
    }
  }
  
  private class DefaultVisitContext
    implements VisitContext
  {
    private DefaultVisitContext() {}
    
    public final Map<Object, Object> data()
    {
      return AbstractContext.this.data();
    }
    
    public final Object data(Object key)
    {
      return AbstractContext.this.data(key);
    }
    
    public final Object data(Object key, Object value)
    {
      return AbstractContext.this.data(key, value);
    }
    
    public final Configuration configuration()
    {
      return AbstractContext.this.configuration();
    }
    
    public final Settings settings()
    {
      return Utils.settings(configuration());
    }
    
    public final SQLDialect dialect()
    {
      return Utils.configuration(configuration()).dialect();
    }
    
    public final SQLDialect family()
    {
      return dialect().family();
    }
    
    public final Clause clause()
    {
      return (Clause)AbstractContext.this.visitClauses.peekLast();
    }
    
    public final Clause[] clauses()
    {
      return (Clause[])AbstractContext.this.visitClauses.toArray(new Clause[AbstractContext.this.visitClauses.size()]);
    }
    
    public final int clausesLength()
    {
      return AbstractContext.this.visitClauses.size();
    }
    
    public final QueryPart queryPart()
    {
      return (QueryPart)AbstractContext.this.visitParts.peekLast();
    }
    
    public final void queryPart(QueryPart part)
    {
      AbstractContext.this.visitParts.pollLast();
      AbstractContext.this.visitParts.addLast(part);
    }
    
    public final QueryPart[] queryParts()
    {
      return (QueryPart[])AbstractContext.this.visitParts.toArray(new QueryPart[AbstractContext.this.visitParts.size()]);
    }
    
    public final int queryPartsLength()
    {
      return AbstractContext.this.visitParts.size();
    }
    
    public final Context<?> context()
    {
      return AbstractContext.this;
    }
    
    public final RenderContext renderContext()
    {
      return (context() instanceof RenderContext) ? (RenderContext)context() : null;
    }
    
    public final BindContext bindContext()
    {
      return (context() instanceof BindContext) ? (BindContext)context() : null;
    }
  }
  
  private final C visit0(QueryPart part)
  {
    if (part != null)
    {
      QueryPartInternal internal = (QueryPartInternal)part;
      if ((declareFields()) && (!internal.declaresFields()))
      {
        declareFields(false);
        visit0(internal);
        declareFields(true);
      }
      else if ((declareTables()) && (!internal.declaresTables()))
      {
        declareTables(false);
        visit0(internal);
        declareTables(true);
      }
      else if ((declareWindows()) && (!internal.declaresWindows()))
      {
        declareWindows(false);
        visit0(internal);
        declareWindows(true);
      }
      else if ((declareCTE()) && (!internal.declaresCTE()))
      {
        declareCTE(false);
        visit0(internal);
        declareCTE(true);
      }
      else
      {
        visit0(internal);
      }
    }
    return this;
  }
  
  protected abstract void visit0(QueryPartInternal paramQueryPartInternal);
  
  public final boolean declareFields()
  {
    return this.declareFields;
  }
  
  public final C declareFields(boolean d)
  {
    this.declareFields = d;
    return this;
  }
  
  public final boolean declareTables()
  {
    return this.declareTables;
  }
  
  public final C declareTables(boolean d)
  {
    this.declareTables = d;
    return this;
  }
  
  public final boolean declareWindows()
  {
    return this.declareWindows;
  }
  
  public final C declareWindows(boolean d)
  {
    this.declareWindows = d;
    return this;
  }
  
  public final boolean declareCTE()
  {
    return this.declareCTE;
  }
  
  public final C declareCTE(boolean d)
  {
    this.declareCTE = d;
    return this;
  }
  
  public final boolean subquery()
  {
    return this.subquery;
  }
  
  public final C subquery(boolean s)
  {
    this.subquery = s;
    return this;
  }
  
  public final int nextIndex()
  {
    return ++this.index;
  }
  
  public final int peekIndex()
  {
    return this.index + 1;
  }
  
  public final ParamType paramType()
  {
    return this.paramType;
  }
  
  public final C paramType(ParamType p)
  {
    this.paramType = (p == null ? ParamType.INDEXED : p);
    return this;
  }
  
  public final boolean qualify()
  {
    return this.qualify;
  }
  
  public final C qualify(boolean q)
  {
    this.qualify = q;
    return this;
  }
  
  public final RenderContext.CastMode castMode()
  {
    return this.castMode;
  }
  
  public final C castMode(RenderContext.CastMode mode)
  {
    this.castMode = mode;
    return this;
  }
  
  @Deprecated
  public final Boolean cast()
  {
    switch (this.castMode)
    {
    case ALWAYS: 
      return Boolean.valueOf(true);
    case NEVER: 
      return Boolean.valueOf(false);
    }
    return null;
  }
  
  @Deprecated
  public final C castModeSome(SQLDialect... dialects)
  {
    return this;
  }
  
  public final PreparedStatement statement()
  {
    return this.stmt;
  }
  
  void toString(StringBuilder sb)
  {
    sb.append("bind index   [");
    sb.append(this.index);
    sb.append("]");
    sb.append("\ndeclaring    [");
    if (this.declareFields) {
      sb.append("fields");
    } else if (this.declareTables) {
      sb.append("tables");
    } else if (this.declareWindows) {
      sb.append("windows");
    } else {
      sb.append("-");
    }
    sb.append("]\nsubquery     [");
    sb.append(this.subquery);
    sb.append("]");
  }
}

package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.CaseWhenStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;

class CaseWhenStepImpl<V, T>
  extends AbstractFunction<T>
  implements CaseWhenStep<V, T>
{
  private static final long serialVersionUID = -3817194006479624228L;
  private final Field<V> value;
  private final List<Field<V>> compareValues;
  private final List<Field<T>> results;
  private Field<T> otherwise;
  
  CaseWhenStepImpl(Field<V> value, Field<V> compareValue, Field<T> result)
  {
    super("case", result.getDataType(), new Field[0]);
    
    this.value = value;
    this.compareValues = new ArrayList();
    this.results = new ArrayList();
    
    when(compareValue, result);
  }
  
  public final Field<T> otherwise(T result)
  {
    return otherwise(Utils.field(result));
  }
  
  public final Field<T> otherwise(Field<T> result)
  {
    this.otherwise = result;
    
    return this;
  }
  
  public final CaseWhenStep<V, T> when(V compareValue, T result)
  {
    return when(Utils.field(compareValue), Utils.field(result));
  }
  
  public final CaseWhenStep<V, T> when(V compareValue, Field<T> result)
  {
    return when(Utils.field(compareValue), result);
  }
  
  public final CaseWhenStep<V, T> when(Field<V> compareValue, T result)
  {
    return when(compareValue, Utils.field(result));
  }
  
  public final CaseWhenStep<V, T> when(Field<V> compareValue, Field<T> result)
  {
    this.compareValues.add(compareValue);
    this.results.add(result);
    
    return this;
  }
  
  final QueryPart getFunction0(Configuration configuration)
  {
    switch (configuration.dialect().family())
    {
    }
    return new Native(null);
  }
  
  private abstract class Base
    extends AbstractQueryPart
  {
    private static final long serialVersionUID = 6146002888421945901L;
    
    private Base() {}
    
    public final Clause[] clauses(Context<?> ctx)
    {
      return null;
    }
  }
  
  private class Native
    extends CaseWhenStepImpl<V, T>.Base
  {
    private static final long serialVersionUID = 7564667836130498156L;
    
    private Native()
    {
      super(null);
    }
    
    public final void accept(Context<?> ctx)
    {
      ctx.formatIndentLockStart().keyword("case");
      
      int size = CaseWhenStepImpl.this.compareValues.size();
      switch (CaseWhenStepImpl.1.$SwitchMap$org$jooq$SQLDialect[ctx.configuration().dialect().ordinal()])
      {
      case 1: 
        ctx.formatIndentLockStart();
        for (int i = 0; i < size; i++)
        {
          if (i > 0) {
            ctx.formatNewLine();
          }
          ctx.sql(" ").keyword("when").sql(" ");
          ctx.visit(CaseWhenStepImpl.this.value.equal((Field)CaseWhenStepImpl.this.compareValues.get(i)));
          ctx.sql(" ").keyword("then").sql(" ");
          ctx.visit((QueryPart)CaseWhenStepImpl.this.results.get(i));
        }
        break;
      default: 
        ctx.sql(" ").visit(CaseWhenStepImpl.this.value).formatIndentLockStart();
        for (int i = 0; i < size; i++)
        {
          if (i > 0) {
            ctx.formatNewLine();
          }
          ctx.sql(" ").keyword("when").sql(" ");
          ctx.visit((QueryPart)CaseWhenStepImpl.this.compareValues.get(i));
          ctx.sql(" ").keyword("then").sql(" ");
          ctx.visit((QueryPart)CaseWhenStepImpl.this.results.get(i));
        }
      }
      if (CaseWhenStepImpl.this.otherwise != null) {
        ctx.formatNewLine().sql(" ").keyword("else").sql(" ").visit(CaseWhenStepImpl.this.otherwise);
      }
      ctx.formatIndentLockEnd();
      if ((size > 1) || (CaseWhenStepImpl.this.otherwise != null)) {
        ctx.formatSeparator();
      } else {
        ctx.sql(" ");
      }
      ctx.keyword("end").formatIndentLockEnd();
    }
  }
}

package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Operator;
import org.jooq.QueryPart;

class CombinedCondition
  extends AbstractCondition
{
  private static final long serialVersionUID = -7373293246207052549L;
  private static final Clause[] CLAUSES_AND = { Clause.CONDITION, Clause.CONDITION_AND };
  private static final Clause[] CLAUSES_OR = { Clause.CONDITION, Clause.CONDITION_OR };
  private final Operator operator;
  private final List<Condition> conditions;
  
  CombinedCondition(Operator operator, Collection<? extends Condition> conditions)
  {
    if (operator == null) {
      throw new IllegalArgumentException("The argument 'operator' must not be null");
    }
    if (conditions == null) {
      throw new IllegalArgumentException("The argument 'conditions' must not be null");
    }
    for (Condition condition : conditions) {
      if (condition == null) {
        throw new IllegalArgumentException("The argument 'conditions' must not contain null");
      }
    }
    this.operator = operator;
    this.conditions = new ArrayList();
    
    init(operator, conditions);
  }
  
  private final void init(Operator op, Collection<? extends Condition> cond)
  {
    for (Condition condition : cond) {
      if ((condition instanceof CombinedCondition))
      {
        CombinedCondition combinedCondition = (CombinedCondition)condition;
        if (combinedCondition.operator == op) {
          this.conditions.addAll(combinedCondition.conditions);
        } else {
          this.conditions.add(condition);
        }
      }
      else
      {
        this.conditions.add(condition);
      }
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return this.operator == Operator.AND ? CLAUSES_AND : CLAUSES_OR;
  }
  
  public final void accept(Context<?> ctx)
  {
    if (this.conditions.isEmpty())
    {
      ctx.visit(DSL.trueCondition());
    }
    else if (this.conditions.size() == 1)
    {
      ctx.visit((QueryPart)this.conditions.get(0));
    }
    else
    {
      ctx.sql("(").formatIndentStart().formatNewLine();
      
      String operatorName = this.operator.name().toLowerCase() + " ";
      String separator = "";
      for (int i = 0; i < this.conditions.size(); i++)
      {
        if (i > 0) {
          ctx.formatSeparator();
        }
        ctx.keyword(separator);
        ctx.visit((QueryPart)this.conditions.get(i));
        separator = operatorName;
      }
      ctx.formatIndentEnd().formatNewLine().sql(")");
    }
  }
}

package org.jooq.impl;

import org.jooq.CommonTableExpression;

class CommonTableExpressionList
  extends QueryPartList<CommonTableExpression<?>>
{
  private static final long serialVersionUID = 4284724883554582081L;
  
  public final boolean declaresCTE()
  {
    return true;
  }
}

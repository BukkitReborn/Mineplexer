package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.WindowSpecificationFinalStep;
import org.jooq.WindowSpecificationOrderByStep;
import org.jooq.WindowSpecificationPartitionByStep;
import org.jooq.WindowSpecificationRowsAndStep;

class WindowSpecificationImpl
  extends AbstractQueryPart
  implements WindowSpecificationPartitionByStep, WindowSpecificationRowsAndStep
{
  private static final long serialVersionUID = 2996016924769376361L;
  private final QueryPartList<Field<?>> partitionBy;
  private final SortFieldList orderBy;
  private Integer rowsStart;
  private Integer rowsEnd;
  private boolean partitionByOne;
  
  WindowSpecificationImpl()
  {
    this.partitionBy = new QueryPartList();
    this.orderBy = new SortFieldList();
  }
  
  public final void accept(Context<?> ctx)
  {
    String glue = "";
    if (!this.partitionBy.isEmpty()) {
      if (this.partitionByOne)
      {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID }).contains(ctx.configuration().dialect())) {}
      }
      else
      {
        ctx.sql(glue).keyword("partition by").sql(" ").visit(this.partitionBy);
        
        glue = " ";
      }
    }
    if (!this.orderBy.isEmpty())
    {
      ctx.sql(glue).keyword("order by").sql(" ").visit(this.orderBy);
      
      glue = " ";
    }
    if (this.rowsStart != null)
    {
      ctx.sql(glue);
      ctx.keyword("rows").sql(" ");
      if (this.rowsEnd != null)
      {
        ctx.keyword("between").sql(" ");
        toSQLRows(ctx, this.rowsStart);
        
        ctx.sql(" ").keyword("and").sql(" ");
        toSQLRows(ctx, this.rowsEnd);
      }
      else
      {
        toSQLRows(ctx, this.rowsStart);
      }
      glue = " ";
    }
  }
  
  private final void toSQLRows(Context<?> ctx, Integer rows)
  {
    if (rows.intValue() == Integer.MIN_VALUE)
    {
      ctx.keyword("unbounded preceding");
    }
    else if (rows.intValue() == Integer.MAX_VALUE)
    {
      ctx.keyword("unbounded following");
    }
    else if (rows.intValue() < 0)
    {
      ctx.sql(-rows.intValue());
      ctx.sql(" ").keyword("preceding");
    }
    else if (rows.intValue() > 0)
    {
      ctx.sql(rows.intValue());
      ctx.sql(" ").keyword("following");
    }
    else
    {
      ctx.keyword("current row");
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  public final WindowSpecificationPartitionByStep partitionBy(Field<?>... fields)
  {
    return partitionBy(Arrays.asList(fields));
  }
  
  public final WindowSpecificationPartitionByStep partitionBy(Collection<? extends Field<?>> fields)
  {
    this.partitionBy.addAll(fields);
    return this;
  }
  
  public final WindowSpecificationOrderByStep partitionByOne()
  {
    this.partitionByOne = true;
    this.partitionBy.add(DSL.one());
    return null;
  }
  
  public final WindowSpecificationOrderByStep orderBy(Field<?>... fields)
  {
    this.orderBy.addAll(fields);
    return this;
  }
  
  public final WindowSpecificationOrderByStep orderBy(SortField<?>... fields)
  {
    return orderBy(Arrays.asList(fields));
  }
  
  public final WindowSpecificationOrderByStep orderBy(Collection<? extends SortField<?>> fields)
  {
    this.orderBy.addAll(fields);
    return this;
  }
  
  public final WindowSpecificationFinalStep rowsUnboundedPreceding()
  {
    this.rowsStart = Integer.valueOf(Integer.MIN_VALUE);
    return this;
  }
  
  public final WindowSpecificationFinalStep rowsPreceding(int number)
  {
    this.rowsStart = Integer.valueOf(-number);
    return this;
  }
  
  public final WindowSpecificationFinalStep rowsCurrentRow()
  {
    this.rowsStart = Integer.valueOf(0);
    return this;
  }
  
  public final WindowSpecificationFinalStep rowsUnboundedFollowing()
  {
    this.rowsStart = Integer.valueOf(Integer.MAX_VALUE);
    return this;
  }
  
  public final WindowSpecificationFinalStep rowsFollowing(int number)
  {
    this.rowsStart = Integer.valueOf(number);
    return this;
  }
  
  public final WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding()
  {
    rowsUnboundedPreceding();
    return this;
  }
  
  public final WindowSpecificationRowsAndStep rowsBetweenPreceding(int number)
  {
    rowsPreceding(number);
    return this;
  }
  
  public final WindowSpecificationRowsAndStep rowsBetweenCurrentRow()
  {
    rowsCurrentRow();
    return this;
  }
  
  public final WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing()
  {
    rowsUnboundedFollowing();
    return this;
  }
  
  public final WindowSpecificationRowsAndStep rowsBetweenFollowing(int number)
  {
    rowsFollowing(number);
    return this;
  }
  
  public final WindowSpecificationFinalStep andUnboundedPreceding()
  {
    this.rowsEnd = Integer.valueOf(Integer.MIN_VALUE);
    return this;
  }
  
  public final WindowSpecificationFinalStep andPreceding(int number)
  {
    this.rowsEnd = Integer.valueOf(-number);
    return this;
  }
  
  public final WindowSpecificationFinalStep andCurrentRow()
  {
    this.rowsEnd = Integer.valueOf(0);
    return this;
  }
  
  public final WindowSpecificationFinalStep andUnboundedFollowing()
  {
    this.rowsEnd = Integer.valueOf(Integer.MAX_VALUE);
    return this;
  }
  
  public final WindowSpecificationFinalStep andFollowing(int number)
  {
    this.rowsEnd = Integer.valueOf(number);
    return this;
  }
}

package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.AggregateFunction;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.GroupConcatSeparatorStep;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.SortField;
import org.jooq.WindowDefinition;
import org.jooq.WindowFinalStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.WindowSpecification;

class GroupConcat
  extends AbstractFunction<String>
  implements GroupConcatOrderByStep
{
  private static final long serialVersionUID = -6884415527559632960L;
  private final Field<?> field;
  private final boolean distinct;
  private final SortFieldList orderBy;
  private String separator;
  
  GroupConcat(Field<?> field)
  {
    this(field, false);
  }
  
  GroupConcat(Field<?> field, boolean distinct)
  {
    super("group_concat", SQLDataType.VARCHAR, new Field[0]);
    
    this.field = field;
    this.distinct = distinct;
    this.orderBy = new SortFieldList();
  }
  
  final Field<String> getFunction0(Configuration configuration)
  {
    Function<String> result;
    Function<String> result;
    if (this.separator == null)
    {
      result = new Function(Term.LIST_AGG, this.distinct, SQLDataType.VARCHAR, new QueryPart[] { this.field });
    }
    else
    {
      Field<String> literal = DSL.inline(this.separator);
      result = new Function(Term.LIST_AGG, this.distinct, SQLDataType.VARCHAR, new QueryPart[] { this.field, literal });
    }
    return result.withinGroupOrderBy(this.orderBy);
  }
  
  public final WindowPartitionByStep<String> over()
  {
    throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
  }
  
  public final WindowFinalStep<String> over(WindowSpecification specification)
  {
    throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
  }
  
  public final WindowFinalStep<String> over(WindowDefinition definition)
  {
    throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
  }
  
  public final WindowFinalStep<String> over(Name name)
  {
    throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
  }
  
  public final WindowFinalStep<String> over(String name)
  {
    throw new UnsupportedOperationException("OVER() not supported on GROUP_CONCAT aggregate function");
  }
  
  public final AggregateFunction<String> separator(String s)
  {
    this.separator = s;
    return this;
  }
  
  public final GroupConcatSeparatorStep orderBy(Field<?>... fields)
  {
    this.orderBy.addAll(fields);
    return this;
  }
  
  public final GroupConcatSeparatorStep orderBy(SortField<?>... fields)
  {
    this.orderBy.addAll(Arrays.asList(fields));
    return this;
  }
  
  public final GroupConcatSeparatorStep orderBy(Collection<? extends SortField<?>> fields)
  {
    this.orderBy.addAll(fields);
    return this;
  }
}

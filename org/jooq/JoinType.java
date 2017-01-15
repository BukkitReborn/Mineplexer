package org.jooq;

public enum JoinType
{
  JOIN("join"),  CROSS_JOIN("cross join"),  LEFT_OUTER_JOIN("left outer join"),  RIGHT_OUTER_JOIN("right outer join"),  FULL_OUTER_JOIN("full outer join"),  NATURAL_JOIN("natural join"),  NATURAL_LEFT_OUTER_JOIN("natural left outer join"),  NATURAL_RIGHT_OUTER_JOIN("natural right outer join"),  CROSS_APPLY("cross apply"),  OUTER_APPLY("outer apply");
  
  private final String sql;
  
  private JoinType(String sql)
  {
    this.sql = sql;
  }
  
  public final String toSQL()
  {
    return this.sql;
  }
}

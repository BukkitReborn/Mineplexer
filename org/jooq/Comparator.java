package org.jooq;

public enum Comparator
{
  IN("in", false, true),  NOT_IN("not in", false, true),  EQUALS("=", true, true),  NOT_EQUALS("<>", true, true),  LESS("<", true, true),  LESS_OR_EQUAL("<=", true, true),  GREATER(">", true, true),  GREATER_OR_EQUAL(">=", true, true),  IS_DISTINCT_FROM("is distinct from", false, false),  IS_NOT_DISTINCT_FROM("is not distinct from", false, false),  LIKE("like", false, false),  NOT_LIKE("not like", false, false),  LIKE_IGNORE_CASE("ilike", false, false),  NOT_LIKE_IGNORE_CASE("not ilike", false, false);
  
  private final String sql;
  private final boolean supportsQuantifier;
  private final boolean supportsSubselect;
  
  private Comparator(String sql, boolean supportsQuantifier, boolean supportsSubselect)
  {
    this.sql = sql;
    this.supportsQuantifier = supportsQuantifier;
    this.supportsSubselect = supportsSubselect;
  }
  
  public String toSQL()
  {
    return this.sql;
  }
  
  public boolean supportsQuantifier()
  {
    return this.supportsQuantifier;
  }
  
  public boolean supportsSubselect()
  {
    return this.supportsSubselect;
  }
}

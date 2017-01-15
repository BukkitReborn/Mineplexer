package org.jooq;

import java.util.EnumSet;
import java.util.Set;

public enum SQLDialect
{
  SQL99(null, false),  CUBRID("CUBRID", false),  DERBY("Derby", false),  FIREBIRD("Firebird", false),  H2("H2", false),  HSQLDB("HSQLDB", false),  MARIADB("MariaDB", false),  MYSQL("MySQL", false),  POSTGRES("Postgres", false),  SQLITE("SQLite", false);
  
  private static final SQLDialect[] FAMILIES;
  private final String name;
  private final boolean commercial;
  private final SQLDialect family;
  
  static
  {
    Set<SQLDialect> set = EnumSet.noneOf(SQLDialect.class);
    for (SQLDialect dialect : values()) {
      set.add(dialect.family());
    }
    FAMILIES = (SQLDialect[])set.toArray(new SQLDialect[set.size()]);
  }
  
  private SQLDialect(String name, boolean commercial)
  {
    this(name, commercial, null);
  }
  
  private SQLDialect(String name, boolean commercial, SQLDialect family)
  {
    this.name = name;
    this.commercial = commercial;
    this.family = family;
  }
  
  public final boolean commercial()
  {
    return this.commercial;
  }
  
  public final SQLDialect family()
  {
    return this.family == null ? this : this.family;
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final String getNameLC()
  {
    return this.name == null ? null : this.name.toLowerCase();
  }
  
  public final String getNameUC()
  {
    return this.name == null ? null : this.name.toUpperCase();
  }
  
  public static final SQLDialect[] families()
  {
    return (SQLDialect[])FAMILIES.clone();
  }
}

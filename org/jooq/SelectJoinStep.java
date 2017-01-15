package org.jooq;

public abstract interface SelectJoinStep<R extends Record>
  extends SelectWhereStep<R>
{
  @Support
  public abstract SelectOptionalOnStep<R> join(TableLike<?> paramTableLike, JoinType paramJoinType);
  
  @Support
  public abstract SelectOnStep<R> join(TableLike<?> paramTableLike);
  
  @Support
  public abstract SelectOnStep<R> join(String paramString);
  
  @Support
  public abstract SelectOnStep<R> join(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectOnStep<R> join(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract SelectJoinStep<R> crossJoin(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract SelectJoinStep<R> crossJoin(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract SelectJoinStep<R> crossJoin(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract SelectJoinStep<R> crossJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectJoinPartitionByStep<R> leftOuterJoin(TableLike<?> paramTableLike);
  
  @Support
  public abstract SelectJoinPartitionByStep<R> leftOuterJoin(String paramString);
  
  @Support
  public abstract SelectJoinPartitionByStep<R> leftOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectJoinPartitionByStep<R> leftOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectJoinPartitionByStep<R> rightOuterJoin(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectJoinPartitionByStep<R> rightOuterJoin(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectJoinPartitionByStep<R> rightOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectJoinPartitionByStep<R> rightOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract SelectOnStep<R> fullOuterJoin(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract SelectOnStep<R> fullOuterJoin(String paramString);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract SelectOnStep<R> fullOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract SelectOnStep<R> fullOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectJoinStep<R> naturalJoin(TableLike<?> paramTableLike);
  
  @Support
  public abstract SelectJoinStep<R> naturalJoin(String paramString);
  
  @Support
  public abstract SelectJoinStep<R> naturalJoin(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectJoinStep<R> naturalJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectJoinStep<R> naturalLeftOuterJoin(TableLike<?> paramTableLike);
  
  @Support
  public abstract SelectJoinStep<R> naturalLeftOuterJoin(String paramString);
  
  @Support
  public abstract SelectJoinStep<R> naturalLeftOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectJoinStep<R> naturalLeftOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectJoinStep<R> naturalRightOuterJoin(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectJoinStep<R> naturalRightOuterJoin(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectJoinStep<R> naturalRightOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectJoinStep<R> naturalRightOuterJoin(String paramString, QueryPart... paramVarArgs);
}

package org.jooq;

import java.util.Date;
import java.util.List;

public abstract interface Table<R extends Record>
  extends TableLike<R>
{
  public abstract Schema getSchema();
  
  public abstract String getName();
  
  public abstract String getComment();
  
  public abstract RecordType<R> recordType();
  
  public abstract Class<? extends R> getRecordType();
  
  public abstract R newRecord();
  
  public abstract Identity<R, ? extends Number> getIdentity();
  
  public abstract UniqueKey<R> getPrimaryKey();
  
  public abstract TableField<R, ? extends Number> getRecordVersion();
  
  public abstract TableField<R, ? extends Date> getRecordTimestamp();
  
  public abstract List<UniqueKey<R>> getKeys();
  
  public abstract <O extends Record> List<ForeignKey<O, R>> getReferencesFrom(Table<O> paramTable);
  
  public abstract List<ForeignKey<R, ?>> getReferences();
  
  public abstract <O extends Record> List<ForeignKey<R, O>> getReferencesTo(Table<O> paramTable);
  
  @Support
  public abstract Table<R> as(String paramString);
  
  @Support
  public abstract Table<R> as(String paramString, String... paramVarArgs);
  
  @Support
  public abstract TableOptionalOnStep join(TableLike<?> paramTableLike, JoinType paramJoinType);
  
  @Support
  public abstract TableOnStep join(TableLike<?> paramTableLike);
  
  @Support
  public abstract TableOnStep join(String paramString);
  
  @Support
  public abstract TableOnStep join(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract TableOnStep join(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract TablePartitionByStep leftOuterJoin(TableLike<?> paramTableLike);
  
  @Support
  public abstract TablePartitionByStep leftOuterJoin(String paramString);
  
  @Support
  public abstract TablePartitionByStep leftOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract TablePartitionByStep leftOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract TablePartitionByStep rightOuterJoin(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract TablePartitionByStep rightOuterJoin(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract TablePartitionByStep rightOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract TablePartitionByStep rightOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract TableOnStep fullOuterJoin(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract TableOnStep fullOuterJoin(String paramString);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract TableOnStep fullOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract TableOnStep fullOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Table<Record> crossJoin(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Table<Record> crossJoin(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Table<Record> crossJoin(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Table<Record> crossJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract Table<Record> naturalJoin(TableLike<?> paramTableLike);
  
  @Support
  public abstract Table<Record> naturalJoin(String paramString);
  
  @Support
  public abstract Table<Record> naturalJoin(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract Table<Record> naturalJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract Table<Record> naturalLeftOuterJoin(TableLike<?> paramTableLike);
  
  @Support
  public abstract Table<Record> naturalLeftOuterJoin(String paramString);
  
  @Support
  public abstract Table<Record> naturalLeftOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract Table<Record> naturalLeftOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Table<Record> naturalRightOuterJoin(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Table<Record> naturalRightOuterJoin(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Table<Record> naturalRightOuterJoin(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract Table<Record> naturalRightOuterJoin(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> useIndex(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> useIndexForJoin(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> useIndexForOrderBy(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> useIndexForGroupBy(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> ignoreIndex(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> ignoreIndexForJoin(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> ignoreIndexForOrderBy(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> ignoreIndexForGroupBy(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> forceIndex(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> forceIndexForJoin(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> forceIndexForOrderBy(String... paramVarArgs);
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract Table<R> forceIndexForGroupBy(String... paramVarArgs);
  
  @Support
  public abstract DivideByOnStep divideBy(Table<?> paramTable);
}

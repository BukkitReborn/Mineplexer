package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public abstract interface SelectQuery<R extends Record>
  extends Select<R>, ConditionProvider
{
  @Support
  public abstract void addSelect(Field<?>... paramVarArgs);
  
  @Support
  public abstract void addSelect(Collection<? extends Field<?>> paramCollection);
  
  @Support
  public abstract void setDistinct(boolean paramBoolean);
  
  @Support({SQLDialect.POSTGRES})
  public abstract void addDistinctOn(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.POSTGRES})
  public abstract void addDistinctOn(Collection<? extends Field<?>> paramCollection);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract void setInto(Table<?> paramTable);
  
  @Support
  public abstract void addFrom(TableLike<?> paramTableLike);
  
  @Support
  public abstract void addFrom(TableLike<?>... paramVarArgs);
  
  @Support
  public abstract void addFrom(Collection<? extends TableLike<?>> paramCollection);
  
  @Support
  public abstract void addJoin(TableLike<?> paramTableLike, Condition... paramVarArgs);
  
  @Support
  public abstract void addJoin(TableLike<?> paramTableLike, JoinType paramJoinType, Condition... paramVarArgs);
  
  @Support
  public abstract void addJoinUsing(TableLike<?> paramTableLike, Collection<? extends Field<?>> paramCollection);
  
  @Support
  public abstract void addJoinUsing(TableLike<?> paramTableLike, JoinType paramJoinType, Collection<? extends Field<?>> paramCollection);
  
  @Support
  public abstract void addJoinOnKey(TableLike<?> paramTableLike, JoinType paramJoinType)
    throws DataAccessException;
  
  @Support
  public abstract void addJoinOnKey(TableLike<?> paramTableLike, JoinType paramJoinType, TableField<?, ?>... paramVarArgs)
    throws DataAccessException;
  
  @Support
  public abstract void addJoinOnKey(TableLike<?> paramTableLike, JoinType paramJoinType, ForeignKey<?, ?> paramForeignKey);
  
  @Support
  public abstract void addGroupBy(GroupField... paramVarArgs);
  
  @Support
  public abstract void addGroupBy(Collection<? extends GroupField> paramCollection);
  
  @Support
  public abstract void addHaving(Condition... paramVarArgs);
  
  @Support
  public abstract void addHaving(Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract void addHaving(Operator paramOperator, Condition... paramVarArgs);
  
  @Support
  public abstract void addHaving(Operator paramOperator, Collection<? extends Condition> paramCollection);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract void addWindow(WindowDefinition... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract void addWindow(Collection<? extends WindowDefinition> paramCollection);
  
  @Support
  public abstract void addHint(String paramString);
  
  @Support
  public abstract void addOption(String paramString);
  
  @Support({SQLDialect.CUBRID})
  public abstract void addConnectBy(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract void addConnectByNoCycle(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract void setConnectByStartWith(Condition paramCondition);
  
  @Support
  public abstract void addConditions(Condition... paramVarArgs);
  
  @Support
  public abstract void addConditions(Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract void addConditions(Operator paramOperator, Condition... paramVarArgs);
  
  @Support
  public abstract void addConditions(Operator paramOperator, Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract void addOrderBy(Field<?>... paramVarArgs);
  
  @Support
  public abstract void addOrderBy(SortField<?>... paramVarArgs);
  
  @Support
  public abstract void addOrderBy(Collection<? extends SortField<?>> paramCollection);
  
  @Support
  public abstract void addOrderBy(int... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract void setOrderBySiblings(boolean paramBoolean);
  
  @Support
  public abstract void addSeekAfter(Field<?>... paramVarArgs);
  
  @Support
  public abstract void addSeekAfter(Collection<? extends Field<?>> paramCollection);
  
  @Support
  public abstract void addSeekBefore(Field<?>... paramVarArgs);
  
  @Support
  public abstract void addSeekBefore(Collection<? extends Field<?>> paramCollection);
  
  @Support
  public abstract void addLimit(int paramInt);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract void addLimit(Param<Integer> paramParam);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract void addLimit(int paramInt1, int paramInt2);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract void addLimit(Param<Integer> paramParam, int paramInt);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract void addLimit(int paramInt, Param<Integer> paramParam);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract void addLimit(Param<Integer> paramParam1, Param<Integer> paramParam2);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract void setForUpdate(boolean paramBoolean);
  
  @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract void setForUpdateOf(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract void setForUpdateOf(Collection<? extends Field<?>> paramCollection);
  
  @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract void setForUpdateOf(Table<?>... paramVarArgs);
  
  @Support({SQLDialect.POSTGRES})
  public abstract void setForUpdateNoWait();
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract void setForShare(boolean paramBoolean);
}

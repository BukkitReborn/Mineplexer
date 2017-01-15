package org.jooq;

import java.util.Collection;
import java.util.Map;

public abstract interface InsertQuery<R extends Record>
  extends StoreQuery<R>, Insert<R>
{
  @Support
  public abstract void newRecord();
  
  @Support
  public abstract void addRecord(R paramR);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract void onDuplicateKeyUpdate(boolean paramBoolean);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract void onDuplicateKeyIgnore(boolean paramBoolean);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract <T> void addValueForUpdate(Field<T> paramField, T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract <T> void addValueForUpdate(Field<T> paramField1, Field<T> paramField2);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract void addValuesForUpdate(Map<? extends Field<?>, ?> paramMap);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract void setDefaultValues();
  
  @Support
  public abstract void setReturning();
  
  @Support
  public abstract void setReturning(Identity<R, ? extends Number> paramIdentity);
  
  @Support
  public abstract void setReturning(Field<?>... paramVarArgs);
  
  @Support
  public abstract void setReturning(Collection<? extends Field<?>> paramCollection);
  
  @Support
  public abstract R getReturnedRecord();
  
  @Support
  public abstract Result<R> getReturnedRecords();
}

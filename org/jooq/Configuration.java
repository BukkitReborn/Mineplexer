package org.jooq;

import java.io.Serializable;
import java.util.Map;
import org.jooq.conf.Settings;

public abstract interface Configuration
  extends Serializable
{
  public abstract Map<Object, Object> data();
  
  public abstract Object data(Object paramObject);
  
  public abstract Object data(Object paramObject1, Object paramObject2);
  
  public abstract ConnectionProvider connectionProvider();
  
  public abstract TransactionProvider transactionProvider();
  
  public abstract RecordMapperProvider recordMapperProvider();
  
  public abstract RecordListenerProvider[] recordListenerProviders();
  
  public abstract ExecuteListenerProvider[] executeListenerProviders();
  
  public abstract VisitListenerProvider[] visitListenerProviders();
  
  @Deprecated
  public abstract SchemaMapping schemaMapping();
  
  public abstract SQLDialect dialect();
  
  public abstract SQLDialect family();
  
  public abstract Settings settings();
  
  public abstract Configuration set(ConnectionProvider paramConnectionProvider);
  
  public abstract Configuration set(TransactionProvider paramTransactionProvider);
  
  public abstract Configuration set(RecordMapperProvider paramRecordMapperProvider);
  
  public abstract Configuration set(RecordListenerProvider... paramVarArgs);
  
  public abstract Configuration set(ExecuteListenerProvider... paramVarArgs);
  
  public abstract Configuration set(VisitListenerProvider... paramVarArgs);
  
  public abstract Configuration set(SQLDialect paramSQLDialect);
  
  public abstract Configuration set(Settings paramSettings);
  
  public abstract Configuration derive();
  
  public abstract Configuration derive(ConnectionProvider paramConnectionProvider);
  
  public abstract Configuration derive(TransactionProvider paramTransactionProvider);
  
  public abstract Configuration derive(RecordMapperProvider paramRecordMapperProvider);
  
  public abstract Configuration derive(RecordListenerProvider... paramVarArgs);
  
  public abstract Configuration derive(ExecuteListenerProvider... paramVarArgs);
  
  public abstract Configuration derive(VisitListenerProvider... paramVarArgs);
  
  public abstract Configuration derive(SQLDialect paramSQLDialect);
  
  public abstract Configuration derive(Settings paramSettings);
}

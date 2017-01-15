package org.jooq.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import javax.xml.bind.JAXB;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.ExecuteListenerProvider;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapperProvider;
import org.jooq.SQLDialect;
import org.jooq.SchemaMapping;
import org.jooq.TransactionProvider;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;

public class DefaultConfiguration
  implements Configuration
{
  private static final long serialVersionUID = 8193158984283234708L;
  private SQLDialect dialect;
  private Settings settings;
  private ConcurrentHashMap<Object, Object> data;
  private transient ConnectionProvider connectionProvider;
  private transient TransactionProvider transactionProvider;
  private transient RecordMapperProvider recordMapperProvider;
  private transient RecordListenerProvider[] recordListenerProviders;
  private transient ExecuteListenerProvider[] executeListenerProviders;
  private transient VisitListenerProvider[] visitListenerProviders;
  private SchemaMapping mapping;
  
  public DefaultConfiguration()
  {
    this(SQLDialect.SQL99);
  }
  
  DefaultConfiguration(SQLDialect dialect)
  {
    this(null, null, null, null, null, null, dialect, 
    
      SettingsTools.defaultSettings(), null);
  }
  
  DefaultConfiguration(Configuration configuration)
  {
    this(configuration
      .connectionProvider(), configuration
      .transactionProvider(), configuration
      .recordMapperProvider(), configuration
      .recordListenerProviders(), configuration
      .executeListenerProviders(), configuration
      .visitListenerProviders(), configuration
      .dialect(), configuration
      .settings(), configuration
      .data());
  }
  
  @Deprecated
  DefaultConfiguration(ConnectionProvider connectionProvider, ExecuteListenerProvider[] executeListenerProviders, SQLDialect dialect, Settings settings, Map<Object, Object> data)
  {
    this(connectionProvider, null, null, null, executeListenerProviders, null, dialect, settings, data);
  }
  
  @Deprecated
  DefaultConfiguration(ConnectionProvider connectionProvider, RecordMapperProvider recordMapperProvider, ExecuteListenerProvider[] executeListenerProviders, SQLDialect dialect, Settings settings, Map<Object, Object> data)
  {
    this(connectionProvider, null, recordMapperProvider, null, executeListenerProviders, null, dialect, settings, data);
  }
  
  @Deprecated
  DefaultConfiguration(ConnectionProvider connectionProvider, RecordMapperProvider recordMapperProvider, RecordListenerProvider[] recordListenerProviders, ExecuteListenerProvider[] executeListenerProviders, VisitListenerProvider[] visitListenerProviders, SQLDialect dialect, Settings settings, Map<Object, Object> data)
  {
    this(connectionProvider, null, recordMapperProvider, recordListenerProviders, executeListenerProviders, visitListenerProviders, dialect, settings, data);
  }
  
  DefaultConfiguration(ConnectionProvider connectionProvider, TransactionProvider transactionProvider, RecordMapperProvider recordMapperProvider, RecordListenerProvider[] recordListenerProviders, ExecuteListenerProvider[] executeListenerProviders, VisitListenerProvider[] visitListenerProviders, SQLDialect dialect, Settings settings, Map<Object, Object> data)
  {
    set(connectionProvider);
    set(transactionProvider);
    set(recordMapperProvider);
    set(recordListenerProviders);
    set(executeListenerProviders);
    set(visitListenerProviders);
    set(dialect);
    set(settings);
    
    this.data = (data != null ? new ConcurrentHashMap(data) : new ConcurrentHashMap());
  }
  
  public final Configuration derive()
  {
    return new DefaultConfiguration(this);
  }
  
  public final Configuration derive(Connection newConnection)
  {
    return derive(new DefaultConnectionProvider(newConnection));
  }
  
  public final Configuration derive(DataSource newDataSource)
  {
    return derive(new DataSourceConnectionProvider(newDataSource));
  }
  
  public final Configuration derive(ConnectionProvider newConnectionProvider)
  {
    return new DefaultConfiguration(newConnectionProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.dialect, this.settings, this.data);
  }
  
  public final Configuration derive(TransactionProvider newTransactionProvider)
  {
    return new DefaultConfiguration(this.connectionProvider, newTransactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.dialect, this.settings, this.data);
  }
  
  public final Configuration derive(RecordMapperProvider newRecordMapperProvider)
  {
    return new DefaultConfiguration(this.connectionProvider, this.transactionProvider, newRecordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.dialect, this.settings, this.data);
  }
  
  public final Configuration derive(RecordListenerProvider... newRecordListenerProviders)
  {
    return new DefaultConfiguration(this.connectionProvider, this.transactionProvider, this.recordMapperProvider, newRecordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.dialect, this.settings, this.data);
  }
  
  public final Configuration derive(ExecuteListenerProvider... newExecuteListenerProviders)
  {
    return new DefaultConfiguration(this.connectionProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, newExecuteListenerProviders, this.visitListenerProviders, this.dialect, this.settings, this.data);
  }
  
  public final Configuration derive(VisitListenerProvider... newVisitListenerProviders)
  {
    return new DefaultConfiguration(this.connectionProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, newVisitListenerProviders, this.dialect, this.settings, this.data);
  }
  
  public final Configuration derive(SQLDialect newDialect)
  {
    return new DefaultConfiguration(this.connectionProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, newDialect, this.settings, this.data);
  }
  
  public final Configuration derive(Settings newSettings)
  {
    return new DefaultConfiguration(this.connectionProvider, this.transactionProvider, this.recordMapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.visitListenerProviders, this.dialect, newSettings, this.data);
  }
  
  public final Configuration set(Connection newConnection)
  {
    return set(new DefaultConnectionProvider(newConnection));
  }
  
  public final Configuration set(DataSource newDataSource)
  {
    return set(new DataSourceConnectionProvider(newDataSource));
  }
  
  public final Configuration set(ConnectionProvider newConnectionProvider)
  {
    this.connectionProvider = (newConnectionProvider != null ? newConnectionProvider : new NoConnectionProvider());
    
    return this;
  }
  
  public final Configuration set(TransactionProvider newTransactionProvider)
  {
    this.transactionProvider = (newTransactionProvider != null ? newTransactionProvider : new NoTransactionProvider());
    
    return this;
  }
  
  public final Configuration set(RecordMapperProvider newRecordMapperProvider)
  {
    this.recordMapperProvider = newRecordMapperProvider;
    return this;
  }
  
  public final Configuration set(RecordListenerProvider... newRecordListenerProviders)
  {
    this.recordListenerProviders = (newRecordListenerProviders != null ? newRecordListenerProviders : new RecordListenerProvider[0]);
    
    return this;
  }
  
  public final Configuration set(ExecuteListenerProvider... newExecuteListenerProviders)
  {
    this.executeListenerProviders = (newExecuteListenerProviders != null ? newExecuteListenerProviders : new ExecuteListenerProvider[0]);
    
    return this;
  }
  
  public final Configuration set(VisitListenerProvider... newVisitListenerProviders)
  {
    this.visitListenerProviders = (newVisitListenerProviders != null ? newVisitListenerProviders : new VisitListenerProvider[0]);
    
    return this;
  }
  
  public final Configuration set(SQLDialect newDialect)
  {
    this.dialect = newDialect;
    return this;
  }
  
  public final Configuration set(Settings newSettings)
  {
    this.settings = (newSettings != null ? SettingsTools.clone(newSettings) : SettingsTools.defaultSettings());
    
    this.mapping = new SchemaMapping(this);
    return this;
  }
  
  public final void setConnection(Connection newConnection)
  {
    set(newConnection);
  }
  
  public final void setDataSource(DataSource newDataSource)
  {
    set(newDataSource);
  }
  
  public final void setConnectionProvider(ConnectionProvider newConnectionProvider)
  {
    set(newConnectionProvider);
  }
  
  public final void setTransactionProvider(TransactionProvider newTransactionProvider)
  {
    set(newTransactionProvider);
  }
  
  public final void setRecordMapperProvider(RecordMapperProvider newRecordMapperProvider)
  {
    set(newRecordMapperProvider);
  }
  
  public final void setRecordListenerProvider(RecordListenerProvider... newRecordListenerProviders)
  {
    set(newRecordListenerProviders);
  }
  
  public final void setExecuteListenerProvider(ExecuteListenerProvider... newExecuteListenerProviders)
  {
    set(newExecuteListenerProviders);
  }
  
  public final void setVisitListenerProvider(VisitListenerProvider... newVisitListenerProviders)
  {
    set(newVisitListenerProviders);
  }
  
  public final void setSQLDialect(SQLDialect newDialect)
  {
    set(newDialect);
  }
  
  public final void setSettings(Settings newSettings)
  {
    set(newSettings);
  }
  
  public final ConnectionProvider connectionProvider()
  {
    ConnectionProvider transactional = (ConnectionProvider)data("org.jooq.configuration.default-transaction-provider-connection-provider");
    return transactional == null ? this.connectionProvider : transactional;
  }
  
  public final TransactionProvider transactionProvider()
  {
    if ((this.transactionProvider instanceof NoTransactionProvider)) {
      return new DefaultTransactionProvider(this.connectionProvider);
    }
    return this.transactionProvider;
  }
  
  public final RecordMapperProvider recordMapperProvider()
  {
    return this.recordMapperProvider != null ? this.recordMapperProvider : new DefaultRecordMapperProvider(this);
  }
  
  public final RecordListenerProvider[] recordListenerProviders()
  {
    return this.recordListenerProviders;
  }
  
  public final ExecuteListenerProvider[] executeListenerProviders()
  {
    return this.executeListenerProviders;
  }
  
  public final VisitListenerProvider[] visitListenerProviders()
  {
    return this.visitListenerProviders;
  }
  
  public final SQLDialect dialect()
  {
    return this.dialect;
  }
  
  public final SQLDialect family()
  {
    return this.dialect.family();
  }
  
  public final Settings settings()
  {
    return this.settings;
  }
  
  public final ConcurrentHashMap<Object, Object> data()
  {
    return this.data;
  }
  
  public final Object data(Object key)
  {
    return this.data.get(key);
  }
  
  public final Object data(Object key, Object value)
  {
    return this.data.put(key, value);
  }
  
  @Deprecated
  public final SchemaMapping schemaMapping()
  {
    return this.mapping;
  }
  
  public String toString()
  {
    StringWriter writer = new StringWriter();
    JAXB.marshal(this.settings, writer);
    
    return "DefaultConfiguration [\n\tconnected=" + ((this.connectionProvider != null) && (!(this.connectionProvider instanceof NoConnectionProvider))) + ",\n\ttransactional=" + ((this.transactionProvider != null) && (!(this.transactionProvider instanceof NoTransactionProvider))) + ",\n\tdialect=" + this.dialect + ",\n\tdata=" + this.data + ",\n\tsettings=\n\t\t" + writer.toString().trim().replace("\n", "\n\t\t") + "\n]";
  }
  
  private void writeObject(ObjectOutputStream oos)
    throws IOException
  {
    oos.defaultWriteObject();
    
    oos.writeObject((this.connectionProvider instanceof Serializable) ? this.connectionProvider : null);
    
    oos.writeObject((this.transactionProvider instanceof Serializable) ? this.transactionProvider : null);
    
    oos.writeObject((this.recordMapperProvider instanceof Serializable) ? this.recordMapperProvider : null);
    
    oos.writeObject(cloneSerializables(this.executeListenerProviders));
    oos.writeObject(cloneSerializables(this.recordListenerProviders));
    oos.writeObject(cloneSerializables(this.visitListenerProviders));
  }
  
  private <E> E[] cloneSerializables(E[] array)
  {
    E[] clone = (Object[])array.clone();
    for (int i = 0; i < clone.length; i++) {
      if (!(clone[i] instanceof Serializable)) {
        clone[i] = null;
      }
    }
    return clone;
  }
  
  private void readObject(ObjectInputStream ois)
    throws IOException, ClassNotFoundException
  {
    ois.defaultReadObject();
    
    this.connectionProvider = ((ConnectionProvider)ois.readObject());
    this.transactionProvider = ((TransactionProvider)ois.readObject());
    this.recordMapperProvider = ((RecordMapperProvider)ois.readObject());
    this.executeListenerProviders = ((ExecuteListenerProvider[])ois.readObject());
    this.recordListenerProviders = ((RecordListenerProvider[])ois.readObject());
    this.visitListenerProviders = ((VisitListenerProvider[])ois.readObject());
  }
}

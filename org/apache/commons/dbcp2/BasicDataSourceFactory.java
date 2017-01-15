package org.apache.commons.dbcp2;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class BasicDataSourceFactory
  implements ObjectFactory
{
  private static final String PROP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";
  private static final String PROP_DEFAULTREADONLY = "defaultReadOnly";
  private static final String PROP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";
  private static final String PROP_DEFAULTCATALOG = "defaultCatalog";
  private static final String PROP_CACHESTATE = "cacheState";
  private static final String PROP_DRIVERCLASSNAME = "driverClassName";
  private static final String PROP_LIFO = "lifo";
  private static final String PROP_MAXTOTAL = "maxTotal";
  private static final String PROP_MAXIDLE = "maxIdle";
  private static final String PROP_MINIDLE = "minIdle";
  private static final String PROP_INITIALSIZE = "initialSize";
  private static final String PROP_MAXWAITMILLIS = "maxWaitMillis";
  private static final String PROP_TESTONCREATE = "testOnCreate";
  private static final String PROP_TESTONBORROW = "testOnBorrow";
  private static final String PROP_TESTONRETURN = "testOnReturn";
  private static final String PROP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";
  private static final String PROP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";
  private static final String PROP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";
  private static final String PROP_SOFTMINEVICTABLEIDLETIMEMILLIS = "softMinEvictableIdleTimeMillis";
  private static final String PROP_EVICTIONPOLICYCLASSNAME = "evictionPolicyClassName";
  private static final String PROP_TESTWHILEIDLE = "testWhileIdle";
  private static final String PROP_PASSWORD = "password";
  private static final String PROP_URL = "url";
  private static final String PROP_USERNAME = "username";
  private static final String PROP_VALIDATIONQUERY = "validationQuery";
  private static final String PROP_VALIDATIONQUERY_TIMEOUT = "validationQueryTimeout";
  private static final String PROP_JMX_NAME = "jmxName";
  private static final String PROP_CONNECTIONINITSQLS = "connectionInitSqls";
  private static final String PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED = "accessToUnderlyingConnectionAllowed";
  private static final String PROP_REMOVEABANDONEDONBORROW = "removeAbandonedOnBorrow";
  private static final String PROP_REMOVEABANDONEDONMAINTENANCE = "removeAbandonedOnMaintenance";
  private static final String PROP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
  private static final String PROP_LOGABANDONED = "logAbandoned";
  private static final String PROP_POOLPREPAREDSTATEMENTS = "poolPreparedStatements";
  private static final String PROP_MAXOPENPREPAREDSTATEMENTS = "maxOpenPreparedStatements";
  private static final String PROP_CONNECTIONPROPERTIES = "connectionProperties";
  private static final String PROP_MAXCONNLIFETIMEMILLIS = "maxConnLifetimeMillis";
  private static final String PROP_ROLLBACK_ON_RETURN = "rollbackOnReturn";
  private static final String PROP_ENABLE_AUTOCOMMIT_ON_RETURN = "enableAutoCommitOnReturn";
  private static final String PROP_DEFAULT_QUERYTIMEOUT = "defaultQueryTimeout";
  private static final String[] ALL_PROPERTIES = { "defaultAutoCommit", "defaultReadOnly", "defaultTransactionIsolation", "defaultCatalog", "cacheState", "driverClassName", "lifo", "maxTotal", "maxIdle", "minIdle", "initialSize", "maxWaitMillis", "testOnCreate", "testOnBorrow", "testOnReturn", "timeBetweenEvictionRunsMillis", "numTestsPerEvictionRun", "minEvictableIdleTimeMillis", "softMinEvictableIdleTimeMillis", "evictionPolicyClassName", "testWhileIdle", "password", "url", "username", "validationQuery", "validationQueryTimeout", "connectionInitSqls", "accessToUnderlyingConnectionAllowed", "removeAbandonedOnBorrow", "removeAbandonedOnMaintenance", "removeAbandonedTimeout", "logAbandoned", "poolPreparedStatements", "maxOpenPreparedStatements", "connectionProperties", "maxConnLifetimeMillis", "rollbackOnReturn", "enableAutoCommitOnReturn", "defaultQueryTimeout" };
  
  public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
    throws Exception
  {
    if ((obj == null) || (!(obj instanceof Reference))) {
      return null;
    }
    Reference ref = (Reference)obj;
    if (!"javax.sql.DataSource".equals(ref.getClassName())) {
      return null;
    }
    Properties properties = new Properties();
    for (String propertyName : ALL_PROPERTIES)
    {
      RefAddr ra = ref.get(propertyName);
      if (ra != null)
      {
        String propertyValue = ra.getContent().toString();
        properties.setProperty(propertyName, propertyValue);
      }
    }
    return createDataSource(properties);
  }
  
  public static BasicDataSource createDataSource(Properties properties)
    throws Exception
  {
    BasicDataSource dataSource = new BasicDataSource();
    String value = null;
    
    value = properties.getProperty("defaultAutoCommit");
    if (value != null) {
      dataSource.setDefaultAutoCommit(Boolean.valueOf(value));
    }
    value = properties.getProperty("defaultReadOnly");
    if (value != null) {
      dataSource.setDefaultReadOnly(Boolean.valueOf(value));
    }
    value = properties.getProperty("defaultTransactionIsolation");
    if (value != null)
    {
      int level = -1;
      if ("NONE".equalsIgnoreCase(value)) {
        level = 0;
      } else if ("READ_COMMITTED".equalsIgnoreCase(value)) {
        level = 2;
      } else if ("READ_UNCOMMITTED".equalsIgnoreCase(value)) {
        level = 1;
      } else if ("REPEATABLE_READ".equalsIgnoreCase(value)) {
        level = 4;
      } else if ("SERIALIZABLE".equalsIgnoreCase(value)) {
        level = 8;
      } else {
        try
        {
          level = Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
          System.err.println("Could not parse defaultTransactionIsolation: " + value);
          System.err.println("WARNING: defaultTransactionIsolation not set");
          System.err.println("using default value of database driver");
          level = -1;
        }
      }
      dataSource.setDefaultTransactionIsolation(level);
    }
    value = properties.getProperty("defaultCatalog");
    if (value != null) {
      dataSource.setDefaultCatalog(value);
    }
    value = properties.getProperty("cacheState");
    if (value != null) {
      dataSource.setCacheState(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("driverClassName");
    if (value != null) {
      dataSource.setDriverClassName(value);
    }
    value = properties.getProperty("lifo");
    if (value != null) {
      dataSource.setLifo(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("maxTotal");
    if (value != null) {
      dataSource.setMaxTotal(Integer.parseInt(value));
    }
    value = properties.getProperty("maxIdle");
    if (value != null) {
      dataSource.setMaxIdle(Integer.parseInt(value));
    }
    value = properties.getProperty("minIdle");
    if (value != null) {
      dataSource.setMinIdle(Integer.parseInt(value));
    }
    value = properties.getProperty("initialSize");
    if (value != null) {
      dataSource.setInitialSize(Integer.parseInt(value));
    }
    value = properties.getProperty("maxWaitMillis");
    if (value != null) {
      dataSource.setMaxWaitMillis(Long.parseLong(value));
    }
    value = properties.getProperty("testOnCreate");
    if (value != null) {
      dataSource.setTestOnCreate(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("testOnBorrow");
    if (value != null) {
      dataSource.setTestOnBorrow(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("testOnReturn");
    if (value != null) {
      dataSource.setTestOnReturn(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("timeBetweenEvictionRunsMillis");
    if (value != null) {
      dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(value));
    }
    value = properties.getProperty("numTestsPerEvictionRun");
    if (value != null) {
      dataSource.setNumTestsPerEvictionRun(Integer.parseInt(value));
    }
    value = properties.getProperty("minEvictableIdleTimeMillis");
    if (value != null) {
      dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(value));
    }
    value = properties.getProperty("softMinEvictableIdleTimeMillis");
    if (value != null) {
      dataSource.setSoftMinEvictableIdleTimeMillis(Long.parseLong(value));
    }
    value = properties.getProperty("evictionPolicyClassName");
    if (value != null) {
      dataSource.setEvictionPolicyClassName(value);
    }
    value = properties.getProperty("testWhileIdle");
    if (value != null) {
      dataSource.setTestWhileIdle(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("password");
    if (value != null) {
      dataSource.setPassword(value);
    }
    value = properties.getProperty("url");
    if (value != null) {
      dataSource.setUrl(value);
    }
    value = properties.getProperty("username");
    if (value != null) {
      dataSource.setUsername(value);
    }
    value = properties.getProperty("validationQuery");
    if (value != null) {
      dataSource.setValidationQuery(value);
    }
    value = properties.getProperty("validationQueryTimeout");
    if (value != null) {
      dataSource.setValidationQueryTimeout(Integer.parseInt(value));
    }
    value = properties.getProperty("accessToUnderlyingConnectionAllowed");
    if (value != null) {
      dataSource.setAccessToUnderlyingConnectionAllowed(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("removeAbandonedOnBorrow");
    if (value != null) {
      dataSource.setRemoveAbandonedOnBorrow(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("removeAbandonedOnMaintenance");
    if (value != null) {
      dataSource.setRemoveAbandonedOnMaintenance(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("removeAbandonedTimeout");
    if (value != null) {
      dataSource.setRemoveAbandonedTimeout(Integer.parseInt(value));
    }
    value = properties.getProperty("logAbandoned");
    if (value != null) {
      dataSource.setLogAbandoned(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("poolPreparedStatements");
    if (value != null) {
      dataSource.setPoolPreparedStatements(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("maxOpenPreparedStatements");
    if (value != null) {
      dataSource.setMaxOpenPreparedStatements(Integer.parseInt(value));
    }
    value = properties.getProperty("connectionInitSqls");
    if (value != null)
    {
      StringTokenizer tokenizer = new StringTokenizer(value, ";");
      
      Collection<String> tokens = new ArrayList(tokenizer.countTokens());
      while (tokenizer.hasMoreTokens()) {
        tokens.add(tokenizer.nextToken());
      }
      dataSource.setConnectionInitSqls(tokens);
    }
    value = properties.getProperty("connectionProperties");
    if (value != null)
    {
      Properties p = getProperties(value);
      Enumeration<?> e = p.propertyNames();
      while (e.hasMoreElements())
      {
        String propertyName = (String)e.nextElement();
        dataSource.addConnectionProperty(propertyName, p.getProperty(propertyName));
      }
    }
    value = properties.getProperty("maxConnLifetimeMillis");
    if (value != null) {
      dataSource.setMaxConnLifetimeMillis(Long.parseLong(value));
    }
    value = properties.getProperty("jmxName");
    if (value != null) {
      dataSource.setJmxName(value);
    }
    value = properties.getProperty("enableAutoCommitOnReturn");
    if (value != null) {
      dataSource.setEnableAutoCommitOnReturn(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("rollbackOnReturn");
    if (value != null) {
      dataSource.setRollbackOnReturn(Boolean.valueOf(value).booleanValue());
    }
    value = properties.getProperty("defaultQueryTimeout");
    if (value != null) {
      dataSource.setDefaultQueryTimeout(Integer.valueOf(value));
    }
    if (dataSource.getInitialSize() > 0) {
      dataSource.getLogWriter();
    }
    return dataSource;
  }
  
  private static Properties getProperties(String propText)
    throws Exception
  {
    Properties p = new Properties();
    if (propText != null) {
      p.load(new ByteArrayInputStream(propText.replace(';', '\n').getBytes(StandardCharsets.ISO_8859_1)));
    }
    return p;
  }
}

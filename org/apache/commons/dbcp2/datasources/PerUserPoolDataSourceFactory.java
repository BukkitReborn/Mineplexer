package org.apache.commons.dbcp2.datasources;

import java.io.IOException;
import java.util.Map;
import javax.naming.RefAddr;
import javax.naming.Reference;

public class PerUserPoolDataSourceFactory
  extends InstanceKeyDataSourceFactory
{
  private static final String PER_USER_POOL_CLASSNAME = PerUserPoolDataSource.class.getName();
  
  protected boolean isCorrectClass(String className)
  {
    return PER_USER_POOL_CLASSNAME.equals(className);
  }
  
  protected InstanceKeyDataSource getNewInstance(Reference ref)
    throws IOException, ClassNotFoundException
  {
    PerUserPoolDataSource pupds = new PerUserPoolDataSource();
    RefAddr ra = ref.get("defaultMaxTotal");
    if ((ra != null) && (ra.getContent() != null)) {
      pupds.setDefaultMaxTotal(Integer.parseInt(ra.getContent().toString()));
    }
    ra = ref.get("defaultMaxIdle");
    if ((ra != null) && (ra.getContent() != null)) {
      pupds.setDefaultMaxIdle(Integer.parseInt(ra.getContent().toString()));
    }
    ra = ref.get("defaultMaxWaitMillis");
    if ((ra != null) && (ra.getContent() != null)) {
      pupds.setDefaultMaxWaitMillis(Integer.parseInt(ra.getContent().toString()));
    }
    ra = ref.get("perUserDefaultAutoCommit");
    if ((ra != null) && (ra.getContent() != null))
    {
      byte[] serialized = (byte[])ra.getContent();
      pupds.setPerUserDefaultAutoCommit((Map)deserialize(serialized));
    }
    ra = ref.get("perUserDefaultTransactionIsolation");
    if ((ra != null) && (ra.getContent() != null))
    {
      byte[] serialized = (byte[])ra.getContent();
      pupds.setPerUserDefaultTransactionIsolation((Map)deserialize(serialized));
    }
    ra = ref.get("perUserMaxTotal");
    if ((ra != null) && (ra.getContent() != null))
    {
      byte[] serialized = (byte[])ra.getContent();
      pupds.setPerUserMaxTotal((Map)deserialize(serialized));
    }
    ra = ref.get("perUserMaxIdle");
    if ((ra != null) && (ra.getContent() != null))
    {
      byte[] serialized = (byte[])ra.getContent();
      pupds.setPerUserMaxIdle((Map)deserialize(serialized));
    }
    ra = ref.get("perUserMaxWaitMillis");
    if ((ra != null) && (ra.getContent() != null))
    {
      byte[] serialized = (byte[])ra.getContent();
      pupds.setPerUserMaxWaitMillis((Map)deserialize(serialized));
    }
    ra = ref.get("perUserDefaultReadOnly");
    if ((ra != null) && (ra.getContent() != null))
    {
      byte[] serialized = (byte[])ra.getContent();
      pupds.setPerUserDefaultReadOnly((Map)deserialize(serialized));
    }
    return pupds;
  }
}

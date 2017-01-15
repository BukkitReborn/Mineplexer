package org.apache.commons.dbcp2.datasources;

import javax.naming.RefAddr;
import javax.naming.Reference;

public class SharedPoolDataSourceFactory
  extends InstanceKeyDataSourceFactory
{
  private static final String SHARED_POOL_CLASSNAME = SharedPoolDataSource.class.getName();
  
  protected boolean isCorrectClass(String className)
  {
    return SHARED_POOL_CLASSNAME.equals(className);
  }
  
  protected InstanceKeyDataSource getNewInstance(Reference ref)
  {
    SharedPoolDataSource spds = new SharedPoolDataSource();
    RefAddr ra = ref.get("maxTotal");
    if ((ra != null) && (ra.getContent() != null)) {
      spds.setMaxTotal(Integer.parseInt(ra.getContent().toString()));
    }
    return spds;
  }
}

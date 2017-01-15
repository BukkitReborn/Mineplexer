package org.jooq.tools.json;

import java.util.List;
import java.util.Map;

public abstract interface ContainerFactory
{
  public abstract Map createObjectContainer();
  
  public abstract List createArrayContainer();
}

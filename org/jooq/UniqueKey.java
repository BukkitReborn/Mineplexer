package org.jooq;

import java.util.List;

public abstract interface UniqueKey<R extends Record>
  extends Key<R>
{
  public abstract List<ForeignKey<?, R>> getReferences();
}

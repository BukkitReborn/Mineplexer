package org.jooq;

import java.util.List;

public abstract interface Schema
  extends QueryPart
{
  public abstract String getName();
  
  public abstract List<Table<?>> getTables();
  
  public abstract Table<?> getTable(String paramString);
  
  public abstract List<UDT<?>> getUDTs();
  
  public abstract UDT<?> getUDT(String paramString);
  
  public abstract List<Sequence<?>> getSequences();
  
  public abstract Sequence<?> getSequence(String paramString);
}

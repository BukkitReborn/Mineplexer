package org.jooq;

public abstract interface AlterSequenceRestartStep<T extends Number>
{
  @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract AlterSequenceFinalStep restart();
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract AlterSequenceFinalStep restartWith(T paramT);
}

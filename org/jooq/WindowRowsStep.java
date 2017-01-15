package org.jooq;

public abstract interface WindowRowsStep<T>
  extends WindowFinalStep<T>
{
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> rowsUnboundedPreceding();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> rowsPreceding(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> rowsCurrentRow();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> rowsUnboundedFollowing();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> rowsFollowing(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowRowsAndStep<T> rowsBetweenUnboundedPreceding();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowRowsAndStep<T> rowsBetweenPreceding(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowRowsAndStep<T> rowsBetweenCurrentRow();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowRowsAndStep<T> rowsBetweenUnboundedFollowing();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowRowsAndStep<T> rowsBetweenFollowing(int paramInt);
}

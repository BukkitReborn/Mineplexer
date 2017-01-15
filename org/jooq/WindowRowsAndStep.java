package org.jooq;

public abstract interface WindowRowsAndStep<T>
{
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> andUnboundedPreceding();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> andPreceding(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> andCurrentRow();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> andUnboundedFollowing();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> andFollowing(int paramInt);
}

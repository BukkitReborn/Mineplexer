package org.jooq;

public abstract interface WindowSpecificationRowsAndStep
{
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep andUnboundedPreceding();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep andPreceding(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep andCurrentRow();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep andUnboundedFollowing();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep andFollowing(int paramInt);
}

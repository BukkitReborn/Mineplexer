package org.jooq;

public abstract interface WindowSpecificationRowsStep
  extends WindowSpecificationFinalStep
{
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep rowsUnboundedPreceding();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep rowsPreceding(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep rowsCurrentRow();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep rowsUnboundedFollowing();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationFinalStep rowsFollowing(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationRowsAndStep rowsBetweenPreceding(int paramInt);
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationRowsAndStep rowsBetweenCurrentRow();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing();
  
  @Support({SQLDialect.POSTGRES})
  public abstract WindowSpecificationRowsAndStep rowsBetweenFollowing(int paramInt);
}

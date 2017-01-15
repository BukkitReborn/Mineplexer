package org.apache.commons.pool2;

public abstract interface SwallowedExceptionListener
{
  public abstract void onSwallowException(Exception paramException);
}

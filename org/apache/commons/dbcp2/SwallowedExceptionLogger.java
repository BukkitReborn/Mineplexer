package org.apache.commons.dbcp2;

import org.apache.commons.logging.Log;
import org.apache.commons.pool2.SwallowedExceptionListener;

public class SwallowedExceptionLogger
  implements SwallowedExceptionListener
{
  private final Log log;
  
  public SwallowedExceptionLogger(Log log)
  {
    this.log = log;
  }
  
  public void onSwallowException(Exception e)
  {
    this.log.warn(Utils.getMessage("swallowedExceptionLogger.onSwallowedException"), e);
  }
}

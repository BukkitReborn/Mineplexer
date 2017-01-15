package org.jooq.tools;

import java.util.logging.Level;
import org.slf4j.LoggerFactory;

public final class JooqLogger
{
  private org.slf4j.Logger slf4j;
  private org.apache.log4j.Logger log4j;
  private java.util.logging.Logger util;
  private boolean supportsTrace = true;
  private boolean supportsDebug = true;
  private boolean supportsInfo = true;
  
  public static JooqLogger getLogger(Class<?> clazz)
  {
    JooqLogger result = new JooqLogger();
    try
    {
      result.slf4j = LoggerFactory.getLogger(clazz);
    }
    catch (Throwable e1)
    {
      try
      {
        result.log4j = org.apache.log4j.Logger.getLogger(clazz);
      }
      catch (Throwable e2)
      {
        result.util = java.util.logging.Logger.getLogger(clazz.getName());
      }
    }
    try
    {
      result.isInfoEnabled();
    }
    catch (Throwable e)
    {
      result.supportsInfo = false;
    }
    try
    {
      result.isDebugEnabled();
    }
    catch (Throwable e)
    {
      result.supportsDebug = false;
    }
    try
    {
      result.isTraceEnabled();
    }
    catch (Throwable e)
    {
      result.supportsTrace = false;
    }
    return result;
  }
  
  public boolean isTraceEnabled()
  {
    if (!this.supportsTrace) {
      return false;
    }
    if (this.slf4j != null) {
      return this.slf4j.isTraceEnabled();
    }
    if (this.log4j != null) {
      return this.log4j.isTraceEnabled();
    }
    return this.util.isLoggable(Level.FINER);
  }
  
  public void trace(Object message)
  {
    trace(message, null);
  }
  
  public void trace(Object message, Object details)
  {
    if (this.slf4j != null) {
      this.slf4j.trace(getMessage(message, details));
    } else if (this.log4j != null) {
      this.log4j.trace(getMessage(message, details));
    } else {
      this.util.finer("" + getMessage(message, details));
    }
  }
  
  public void trace(Object message, Throwable throwable)
  {
    trace(message, null, throwable);
  }
  
  public void trace(Object message, Object details, Throwable throwable)
  {
    if (this.slf4j != null) {
      this.slf4j.trace(getMessage(message, details), throwable);
    } else if (this.log4j != null) {
      this.log4j.trace(getMessage(message, details), throwable);
    } else {
      this.util.log(Level.FINER, "" + getMessage(message, details), throwable);
    }
  }
  
  public boolean isDebugEnabled()
  {
    if (!this.supportsDebug) {
      return false;
    }
    if (this.slf4j != null) {
      return this.slf4j.isDebugEnabled();
    }
    if (this.log4j != null) {
      return this.log4j.isDebugEnabled();
    }
    return this.util.isLoggable(Level.FINE);
  }
  
  public void debug(Object message)
  {
    debug(message, null);
  }
  
  public void debug(Object message, Object details)
  {
    if (this.slf4j != null) {
      this.slf4j.debug(getMessage(message, details));
    } else if (this.log4j != null) {
      this.log4j.debug(getMessage(message, details));
    } else {
      this.util.fine("" + getMessage(message, details));
    }
  }
  
  public void debug(Object message, Throwable throwable)
  {
    debug(message, null, throwable);
  }
  
  public void debug(Object message, Object details, Throwable throwable)
  {
    if (this.slf4j != null) {
      this.slf4j.debug(getMessage(message, details), throwable);
    } else if (this.log4j != null) {
      this.log4j.debug(getMessage(message, details), throwable);
    } else {
      this.util.log(Level.FINE, "" + getMessage(message, details), throwable);
    }
  }
  
  public boolean isInfoEnabled()
  {
    if (!this.supportsInfo) {
      return false;
    }
    if (this.slf4j != null) {
      return this.slf4j.isInfoEnabled();
    }
    if (this.log4j != null) {
      return this.log4j.isInfoEnabled();
    }
    return this.util.isLoggable(Level.INFO);
  }
  
  public void info(Object message)
  {
    info(message, null);
  }
  
  public void info(Object message, Object details)
  {
    if (this.slf4j != null) {
      this.slf4j.info(getMessage(message, details));
    } else if (this.log4j != null) {
      this.log4j.info(getMessage(message, details));
    } else {
      this.util.info("" + getMessage(message, details));
    }
  }
  
  public void info(Object message, Throwable throwable)
  {
    info(message, null, throwable);
  }
  
  public void info(Object message, Object details, Throwable throwable)
  {
    if (this.slf4j != null) {
      this.slf4j.info(getMessage(message, details), throwable);
    } else if (this.log4j != null) {
      this.log4j.info(getMessage(message, details), throwable);
    } else {
      this.util.log(Level.INFO, "" + getMessage(message, details), throwable);
    }
  }
  
  public void warn(Object message)
  {
    warn(message, null);
  }
  
  public void warn(Object message, Object details)
  {
    if (this.slf4j != null) {
      this.slf4j.warn(getMessage(message, details));
    } else if (this.log4j != null) {
      this.log4j.warn(getMessage(message, details));
    } else {
      this.util.warning("" + getMessage(message, details));
    }
  }
  
  public void warn(Object message, Throwable throwable)
  {
    warn(message, null, throwable);
  }
  
  public void warn(Object message, Object details, Throwable throwable)
  {
    if (this.slf4j != null) {
      this.slf4j.warn(getMessage(message, details), throwable);
    } else if (this.log4j != null) {
      this.log4j.warn(getMessage(message, details), throwable);
    } else {
      this.util.log(Level.WARNING, "" + getMessage(message, details), throwable);
    }
  }
  
  public void error(Object message)
  {
    error(message, null);
  }
  
  public void error(Object message, Object details)
  {
    if (this.slf4j != null) {
      this.slf4j.error(getMessage(message, details));
    } else if (this.log4j != null) {
      this.log4j.error(getMessage(message, details));
    } else {
      this.util.severe("" + getMessage(message, details));
    }
  }
  
  public void error(Object message, Throwable throwable)
  {
    error(message, null, throwable);
  }
  
  public void error(Object message, Object details, Throwable throwable)
  {
    if (this.slf4j != null) {
      this.slf4j.error(getMessage(message, details), throwable);
    } else if (this.log4j != null) {
      this.log4j.error(getMessage(message, details), throwable);
    } else {
      this.util.log(Level.SEVERE, "" + getMessage(message, details), throwable);
    }
  }
  
  private String getMessage(Object message, Object details)
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append(StringUtils.rightPad("" + message, 25));
    if (details != null)
    {
      sb.append(": ");
      sb.append(details);
    }
    return sb.toString();
  }
}

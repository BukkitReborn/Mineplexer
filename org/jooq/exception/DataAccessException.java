package org.jooq.exception;

public class DataAccessException
  extends RuntimeException
{
  private static final long serialVersionUID = 491834858363345767L;
  
  public DataAccessException(String message)
  {
    super(message);
  }
  
  public DataAccessException(String message, Throwable cause)
  {
    super(message, cause);
  }
}

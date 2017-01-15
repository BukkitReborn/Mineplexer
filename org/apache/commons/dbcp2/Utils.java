package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public final class Utils
{
  private static final ResourceBundle messages = ResourceBundle.getBundle(Utils.class.getPackage().getName() + ".LocalStrings");
  public static final boolean IS_SECURITY_ENABLED = System.getSecurityManager() != null;
  
  public static void closeQuietly(ResultSet rset)
  {
    if (rset != null) {
      try
      {
        rset.close();
      }
      catch (Exception e) {}
    }
  }
  
  public static void closeQuietly(Connection conn)
  {
    if (conn != null) {
      try
      {
        conn.close();
      }
      catch (Exception e) {}
    }
  }
  
  public static void closeQuietly(Statement stmt)
  {
    if (stmt != null) {
      try
      {
        stmt.close();
      }
      catch (Exception e) {}
    }
  }
  
  public static String getMessage(String key)
  {
    return getMessage(key, (Object[])null);
  }
  
  public static String getMessage(String key, Object... args)
  {
    String msg = messages.getString(key);
    if ((args == null) || (args.length == 0)) {
      return msg;
    }
    MessageFormat mf = new MessageFormat(msg);
    return mf.format(args, new StringBuffer(), null).toString();
  }
}

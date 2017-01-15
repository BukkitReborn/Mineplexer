package org.jooq.tools.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;

public class MockFileDatabase
  implements MockDataProvider
{
  private static final JooqLogger log = JooqLogger.getLogger(MockFileDatabase.class);
  private final LineNumberReader in;
  private final Map<String, List<MockResult>> matchExactly;
  private final Map<Pattern, List<MockResult>> matchPattern;
  private final DSLContext create;
  
  public MockFileDatabase(File file)
    throws IOException
  {
    this(file, "UTF-8");
  }
  
  public MockFileDatabase(File file, String encoding)
    throws IOException
  {
    this(new FileInputStream(file), encoding);
  }
  
  public MockFileDatabase(InputStream stream)
    throws IOException
  {
    this(stream, "UTF-8");
  }
  
  public MockFileDatabase(InputStream stream, String encoding)
    throws IOException
  {
    this(new InputStreamReader(stream, encoding));
  }
  
  public MockFileDatabase(Reader reader)
    throws IOException
  {
    this(new LineNumberReader(reader));
  }
  
  public MockFileDatabase(String string)
    throws IOException
  {
    this(new StringReader(string));
  }
  
  private MockFileDatabase(LineNumberReader reader)
    throws IOException
  {
    this.in = reader;
    this.matchExactly = new LinkedHashMap();
    this.matchPattern = new LinkedHashMap();
    this.create = DSL.using(SQLDialect.SQL99);
    
    load();
  }
  
  private void load()
    throws FileNotFoundException, IOException
  {
    new Object()
    {
      private StringBuilder currentSQL = new StringBuilder();
      private StringBuilder currentResult = new StringBuilder();
      private String previousSQL = null;
      
      /* Error */
      private void load()
        throws FileNotFoundException, IOException
      {
        // Byte code:
        //   0: aload_0
        //   1: invokespecial 9	org/jooq/tools/jdbc/MockFileDatabase$1:readLine	()Ljava/lang/String;
        //   4: astore_1
        //   5: aload_1
        //   6: ifnonnull +33 -> 39
        //   9: aload_0
        //   10: getfield 7	org/jooq/tools/jdbc/MockFileDatabase$1:currentResult	Ljava/lang/StringBuilder;
        //   13: invokevirtual 10	java/lang/StringBuilder:length	()I
        //   16: ifle +248 -> 264
        //   19: aload_0
        //   20: ldc 11
        //   22: invokespecial 12	org/jooq/tools/jdbc/MockFileDatabase$1:loadOneResult	(Ljava/lang/String;)V
        //   25: aload_0
        //   26: new 4	java/lang/StringBuilder
        //   29: dup
        //   30: invokespecial 5	java/lang/StringBuilder:<init>	()V
        //   33: putfield 7	org/jooq/tools/jdbc/MockFileDatabase$1:currentResult	Ljava/lang/StringBuilder;
        //   36: goto +228 -> 264
        //   39: aload_1
        //   40: ldc 13
        //   42: invokevirtual 14	java/lang/String:startsWith	(Ljava/lang/String;)Z
        //   45: ifeq +6 -> 51
        //   48: goto -48 -> 0
        //   51: aload_1
        //   52: ldc 15
        //   54: invokevirtual 14	java/lang/String:startsWith	(Ljava/lang/String;)Z
        //   57: ifeq +29 -> 86
        //   60: aload_0
        //   61: getfield 7	org/jooq/tools/jdbc/MockFileDatabase$1:currentResult	Ljava/lang/StringBuilder;
        //   64: aload_1
        //   65: iconst_2
        //   66: invokevirtual 16	java/lang/String:substring	(I)Ljava/lang/String;
        //   69: invokevirtual 17	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   72: pop
        //   73: aload_0
        //   74: getfield 7	org/jooq/tools/jdbc/MockFileDatabase$1:currentResult	Ljava/lang/StringBuilder;
        //   77: ldc 18
        //   79: invokevirtual 17	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   82: pop
        //   83: goto +178 -> 261
        //   86: aload_1
        //   87: ldc 19
        //   89: invokevirtual 14	java/lang/String:startsWith	(Ljava/lang/String;)Z
        //   92: ifeq +22 -> 114
        //   95: aload_0
        //   96: aload_1
        //   97: invokespecial 12	org/jooq/tools/jdbc/MockFileDatabase$1:loadOneResult	(Ljava/lang/String;)V
        //   100: aload_0
        //   101: new 4	java/lang/StringBuilder
        //   104: dup
        //   105: invokespecial 5	java/lang/StringBuilder:<init>	()V
        //   108: putfield 7	org/jooq/tools/jdbc/MockFileDatabase$1:currentResult	Ljava/lang/StringBuilder;
        //   111: goto +150 -> 261
        //   114: aload_1
        //   115: ldc 20
        //   117: invokevirtual 21	java/lang/String:endsWith	(Ljava/lang/String;)Z
        //   120: ifeq +105 -> 225
        //   123: aload_0
        //   124: getfield 6	org/jooq/tools/jdbc/MockFileDatabase$1:currentSQL	Ljava/lang/StringBuilder;
        //   127: aload_1
        //   128: iconst_0
        //   129: aload_1
        //   130: invokevirtual 22	java/lang/String:length	()I
        //   133: iconst_1
        //   134: isub
        //   135: invokevirtual 23	java/lang/String:substring	(II)Ljava/lang/String;
        //   138: invokevirtual 17	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   141: pop
        //   142: aload_0
        //   143: getfield 2	org/jooq/tools/jdbc/MockFileDatabase$1:this$0	Lorg/jooq/tools/jdbc/MockFileDatabase;
        //   146: invokestatic 24	org/jooq/tools/jdbc/MockFileDatabase:access$000	(Lorg/jooq/tools/jdbc/MockFileDatabase;)Ljava/util/Map;
        //   149: aload_0
        //   150: getfield 8	org/jooq/tools/jdbc/MockFileDatabase$1:previousSQL	Ljava/lang/String;
        //   153: invokeinterface 25 2 0
        //   158: ifne +21 -> 179
        //   161: aload_0
        //   162: getfield 2	org/jooq/tools/jdbc/MockFileDatabase$1:this$0	Lorg/jooq/tools/jdbc/MockFileDatabase;
        //   165: invokestatic 24	org/jooq/tools/jdbc/MockFileDatabase:access$000	(Lorg/jooq/tools/jdbc/MockFileDatabase;)Ljava/util/Map;
        //   168: aload_0
        //   169: getfield 8	org/jooq/tools/jdbc/MockFileDatabase$1:previousSQL	Ljava/lang/String;
        //   172: aconst_null
        //   173: invokeinterface 26 3 0
        //   178: pop
        //   179: aload_0
        //   180: aload_0
        //   181: getfield 6	org/jooq/tools/jdbc/MockFileDatabase$1:currentSQL	Ljava/lang/StringBuilder;
        //   184: invokevirtual 27	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   187: putfield 8	org/jooq/tools/jdbc/MockFileDatabase$1:previousSQL	Ljava/lang/String;
        //   190: aload_0
        //   191: new 4	java/lang/StringBuilder
        //   194: dup
        //   195: invokespecial 5	java/lang/StringBuilder:<init>	()V
        //   198: putfield 6	org/jooq/tools/jdbc/MockFileDatabase$1:currentSQL	Ljava/lang/StringBuilder;
        //   201: invokestatic 28	org/jooq/tools/jdbc/MockFileDatabase:access$100	()Lorg/jooq/tools/JooqLogger;
        //   204: invokevirtual 29	org/jooq/tools/JooqLogger:isDebugEnabled	()Z
        //   207: ifeq +54 -> 261
        //   210: invokestatic 28	org/jooq/tools/jdbc/MockFileDatabase:access$100	()Lorg/jooq/tools/JooqLogger;
        //   213: ldc 30
        //   215: aload_0
        //   216: getfield 8	org/jooq/tools/jdbc/MockFileDatabase$1:previousSQL	Ljava/lang/String;
        //   219: invokevirtual 31	org/jooq/tools/JooqLogger:debug	(Ljava/lang/Object;Ljava/lang/Object;)V
        //   222: goto +39 -> 261
        //   225: aload_0
        //   226: getfield 7	org/jooq/tools/jdbc/MockFileDatabase$1:currentResult	Ljava/lang/StringBuilder;
        //   229: invokevirtual 10	java/lang/StringBuilder:length	()I
        //   232: ifle +20 -> 252
        //   235: aload_0
        //   236: ldc 11
        //   238: invokespecial 12	org/jooq/tools/jdbc/MockFileDatabase$1:loadOneResult	(Ljava/lang/String;)V
        //   241: aload_0
        //   242: new 4	java/lang/StringBuilder
        //   245: dup
        //   246: invokespecial 5	java/lang/StringBuilder:<init>	()V
        //   249: putfield 7	org/jooq/tools/jdbc/MockFileDatabase$1:currentResult	Ljava/lang/StringBuilder;
        //   252: aload_0
        //   253: getfield 6	org/jooq/tools/jdbc/MockFileDatabase$1:currentSQL	Ljava/lang/StringBuilder;
        //   256: aload_1
        //   257: invokevirtual 17	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   260: pop
        //   261: goto -261 -> 0
        //   264: aload_0
        //   265: getfield 2	org/jooq/tools/jdbc/MockFileDatabase$1:this$0	Lorg/jooq/tools/jdbc/MockFileDatabase;
        //   268: invokestatic 32	org/jooq/tools/jdbc/MockFileDatabase:access$200	(Lorg/jooq/tools/jdbc/MockFileDatabase;)Ljava/io/LineNumberReader;
        //   271: ifnull +39 -> 310
        //   274: aload_0
        //   275: getfield 2	org/jooq/tools/jdbc/MockFileDatabase$1:this$0	Lorg/jooq/tools/jdbc/MockFileDatabase;
        //   278: invokestatic 32	org/jooq/tools/jdbc/MockFileDatabase:access$200	(Lorg/jooq/tools/jdbc/MockFileDatabase;)Ljava/io/LineNumberReader;
        //   281: invokevirtual 33	java/io/LineNumberReader:close	()V
        //   284: goto +26 -> 310
        //   287: astore_2
        //   288: aload_0
        //   289: getfield 2	org/jooq/tools/jdbc/MockFileDatabase$1:this$0	Lorg/jooq/tools/jdbc/MockFileDatabase;
        //   292: invokestatic 32	org/jooq/tools/jdbc/MockFileDatabase:access$200	(Lorg/jooq/tools/jdbc/MockFileDatabase;)Ljava/io/LineNumberReader;
        //   295: ifnull +13 -> 308
        //   298: aload_0
        //   299: getfield 2	org/jooq/tools/jdbc/MockFileDatabase$1:this$0	Lorg/jooq/tools/jdbc/MockFileDatabase;
        //   302: invokestatic 32	org/jooq/tools/jdbc/MockFileDatabase:access$200	(Lorg/jooq/tools/jdbc/MockFileDatabase;)Ljava/io/LineNumberReader;
        //   305: invokevirtual 33	java/io/LineNumberReader:close	()V
        //   308: aload_2
        //   309: athrow
        //   310: return
        // Line number table:
        //   Java source line #176	-> byte code offset #0
        //   Java source line #179	-> byte code offset #5
        //   Java source line #183	-> byte code offset #9
        //   Java source line #184	-> byte code offset #19
        //   Java source line #185	-> byte code offset #25
        //   Java source line #192	-> byte code offset #39
        //   Java source line #193	-> byte code offset #48
        //   Java source line #197	-> byte code offset #51
        //   Java source line #198	-> byte code offset #60
        //   Java source line #199	-> byte code offset #73
        //   Java source line #203	-> byte code offset #86
        //   Java source line #204	-> byte code offset #95
        //   Java source line #205	-> byte code offset #100
        //   Java source line #209	-> byte code offset #114
        //   Java source line #210	-> byte code offset #123
        //   Java source line #212	-> byte code offset #142
        //   Java source line #213	-> byte code offset #161
        //   Java source line #216	-> byte code offset #179
        //   Java source line #217	-> byte code offset #190
        //   Java source line #219	-> byte code offset #201
        //   Java source line #220	-> byte code offset #210
        //   Java source line #229	-> byte code offset #225
        //   Java source line #230	-> byte code offset #235
        //   Java source line #231	-> byte code offset #241
        //   Java source line #234	-> byte code offset #252
        //   Java source line #236	-> byte code offset #261
        //   Java source line #239	-> byte code offset #264
        //   Java source line #240	-> byte code offset #274
        //   Java source line #239	-> byte code offset #287
        //   Java source line #240	-> byte code offset #298
        //   Java source line #243	-> byte code offset #310
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	311	0	this	1
        //   4	253	1	line	String
        //   287	22	2	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   0	264	287	finally
      }
      
      private void loadOneResult(String line)
      {
        List<MockResult> results = (List)MockFileDatabase.this.matchExactly.get(this.previousSQL);
        if (results == null)
        {
          results = new ArrayList();
          MockFileDatabase.this.matchExactly.put(this.previousSQL, results);
        }
        MockResult mock = parse(line);
        results.add(mock);
        if (MockFileDatabase.log.isDebugEnabled())
        {
          String comment = "Loaded Result";
          for (String l : mock.data.format(5).split("\n"))
          {
            MockFileDatabase.log.debug(comment, l);
            comment = "";
          }
        }
      }
      
      private MockResult parse(String rowString)
      {
        int rows = 0;
        if (rowString.startsWith("@ rows:")) {
          rows = Integer.parseInt(rowString.substring(7).trim());
        }
        return new MockResult(rows, MockFileDatabase.this.create.fetchFromTXT(this.currentResult.toString()));
      }
      
      private String readLine()
        throws IOException
      {
        for (;;)
        {
          String line = MockFileDatabase.this.in.readLine();
          if (line == null) {
            return line;
          }
          line = line.trim();
          if (line.length() > 0) {
            return line;
          }
        }
      }
    }.load();
  }
  
  public MockResult[] execute(MockExecuteContext ctx)
    throws SQLException
  {
    if (ctx.batch()) {
      throw new SQLFeatureNotSupportedException("Not yet supported");
    }
    String sql = ctx.sql();
    String inlined = null;
    
    List<MockResult> list = (List)this.matchExactly.get(sql);
    if (list == null)
    {
      inlined = this.create.query(sql, ctx.bindings()).toString();
      list = (List)this.matchExactly.get(inlined);
    }
    if (list == null) {
      for (Map.Entry<Pattern, List<MockResult>> entry : this.matchPattern.entrySet()) {
        if ((((Pattern)entry.getKey()).matcher(sql).matches()) || 
          (((Pattern)entry.getKey()).matcher(inlined).matches())) {
          list = (List)entry.getValue();
        }
      }
    }
    if (list == null) {
      throw new SQLException("Invalid SQL: " + sql);
    }
    return (MockResult[])list.toArray(new MockResult[list.size()]);
  }
}

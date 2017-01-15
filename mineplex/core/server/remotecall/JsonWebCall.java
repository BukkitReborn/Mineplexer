package mineplex.core.server.remotecall;

import mineplex.core.common.util.Callback;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

public class JsonWebCall
{
  private String _url;
  private PoolingClientConnectionManager _connectionManager;
  
  public JsonWebCall(String url)
  {
    this._url = url;
    
    SchemeRegistry schemeRegistry = new SchemeRegistry();
    schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
    
    this._connectionManager = new PoolingClientConnectionManager(schemeRegistry);
    this._connectionManager.setMaxTotal(200);
    this._connectionManager.setDefaultMaxPerRoute(20);
  }
  
  /* Error */
  public String ExecuteReturnStream(Object argument)
  {
    // Byte code:
    //   0: new 60	org/apache/http/impl/client/DefaultHttpClient
    //   3: dup
    //   4: aload_0
    //   5: getfield 42	mineplex/core/server/remotecall/JsonWebCall:_connectionManager	Lorg/apache/http/impl/conn/PoolingClientConnectionManager;
    //   8: invokespecial 62	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/conn/ClientConnectionManager;)V
    //   11: astore_2
    //   12: aconst_null
    //   13: astore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: new 65	org/bukkit/craftbukkit/libs/com/google/gson/Gson
    //   20: dup
    //   21: invokespecial 67	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
    //   24: astore 6
    //   26: new 68	org/apache/http/client/methods/HttpPost
    //   29: dup
    //   30: aload_0
    //   31: getfield 15	mineplex/core/server/remotecall/JsonWebCall:_url	Ljava/lang/String;
    //   34: invokespecial 70	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   37: astore 7
    //   39: aload_1
    //   40: ifnull +41 -> 81
    //   43: new 72	org/apache/http/entity/StringEntity
    //   46: dup
    //   47: aload 6
    //   49: aload_1
    //   50: invokevirtual 74	org/bukkit/craftbukkit/libs/com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   53: invokespecial 77	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   56: astore 8
    //   58: aload 8
    //   60: new 78	org/apache/http/message/BasicHeader
    //   63: dup
    //   64: ldc 80
    //   66: ldc 82
    //   68: invokespecial 84	org/apache/http/message/BasicHeader:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   71: invokevirtual 87	org/apache/http/entity/StringEntity:setContentType	(Lorg/apache/http/Header;)V
    //   74: aload 7
    //   76: aload 8
    //   78: invokevirtual 91	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   81: aload_2
    //   82: aload 7
    //   84: invokeinterface 95 2 0
    //   89: astore 5
    //   91: aload 5
    //   93: ifnull +185 -> 278
    //   96: aload 5
    //   98: invokeinterface 101 1 0
    //   103: invokeinterface 107 1 0
    //   108: astore_3
    //   109: aload_0
    //   110: aload_3
    //   111: invokevirtual 113	mineplex/core/server/remotecall/JsonWebCall:convertStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   114: astore 4
    //   116: goto +162 -> 278
    //   119: astore 5
    //   121: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   124: new 123	java/lang/StringBuilder
    //   127: dup
    //   128: ldc 125
    //   130: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   133: aload 5
    //   135: invokevirtual 128	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   138: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   144: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   147: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   150: new 123	java/lang/StringBuilder
    //   153: dup
    //   154: ldc -110
    //   156: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   159: aload 4
    //   161: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   167: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   170: aload 5
    //   172: invokevirtual 148	java/lang/Exception:getStackTrace	()[Ljava/lang/StackTraceElement;
    //   175: dup
    //   176: astore 9
    //   178: arraylength
    //   179: istore 8
    //   181: iconst_0
    //   182: istore 7
    //   184: goto +21 -> 205
    //   187: aload 9
    //   189: iload 7
    //   191: aaload
    //   192: astore 6
    //   194: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   197: aload 6
    //   199: invokevirtual 152	java/io/PrintStream:println	(Ljava/lang/Object;)V
    //   202: iinc 7 1
    //   205: iload 7
    //   207: iload 8
    //   209: if_icmplt -22 -> 187
    //   212: aload_2
    //   213: invokeinterface 155 1 0
    //   218: invokeinterface 159 1 0
    //   223: aload_3
    //   224: ifnull +83 -> 307
    //   227: aload_3
    //   228: invokevirtual 164	java/io/InputStream:close	()V
    //   231: goto +76 -> 307
    //   234: astore 11
    //   236: aload 11
    //   238: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   241: goto +66 -> 307
    //   244: astore 10
    //   246: aload_2
    //   247: invokeinterface 155 1 0
    //   252: invokeinterface 159 1 0
    //   257: aload_3
    //   258: ifnull +17 -> 275
    //   261: aload_3
    //   262: invokevirtual 164	java/io/InputStream:close	()V
    //   265: goto +10 -> 275
    //   268: astore 11
    //   270: aload 11
    //   272: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   275: aload 10
    //   277: athrow
    //   278: aload_2
    //   279: invokeinterface 155 1 0
    //   284: invokeinterface 159 1 0
    //   289: aload_3
    //   290: ifnull +17 -> 307
    //   293: aload_3
    //   294: invokevirtual 164	java/io/InputStream:close	()V
    //   297: goto +10 -> 307
    //   300: astore 11
    //   302: aload 11
    //   304: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   307: aload 4
    //   309: areturn
    // Line number table:
    //   Java source line #44	-> byte code offset #0
    //   Java source line #45	-> byte code offset #12
    //   Java source line #46	-> byte code offset #14
    //   Java source line #52	-> byte code offset #17
    //   Java source line #53	-> byte code offset #26
    //   Java source line #55	-> byte code offset #39
    //   Java source line #57	-> byte code offset #43
    //   Java source line #58	-> byte code offset #58
    //   Java source line #59	-> byte code offset #74
    //   Java source line #62	-> byte code offset #81
    //   Java source line #64	-> byte code offset #91
    //   Java source line #66	-> byte code offset #96
    //   Java source line #67	-> byte code offset #109
    //   Java source line #69	-> byte code offset #116
    //   Java source line #70	-> byte code offset #119
    //   Java source line #72	-> byte code offset #121
    //   Java source line #73	-> byte code offset #147
    //   Java source line #75	-> byte code offset #170
    //   Java source line #77	-> byte code offset #194
    //   Java source line #75	-> byte code offset #202
    //   Java source line #82	-> byte code offset #212
    //   Java source line #84	-> byte code offset #223
    //   Java source line #88	-> byte code offset #227
    //   Java source line #89	-> byte code offset #231
    //   Java source line #90	-> byte code offset #234
    //   Java source line #92	-> byte code offset #236
    //   Java source line #81	-> byte code offset #244
    //   Java source line #82	-> byte code offset #246
    //   Java source line #84	-> byte code offset #257
    //   Java source line #88	-> byte code offset #261
    //   Java source line #89	-> byte code offset #265
    //   Java source line #90	-> byte code offset #268
    //   Java source line #92	-> byte code offset #270
    //   Java source line #95	-> byte code offset #275
    //   Java source line #82	-> byte code offset #278
    //   Java source line #84	-> byte code offset #289
    //   Java source line #88	-> byte code offset #293
    //   Java source line #89	-> byte code offset #297
    //   Java source line #90	-> byte code offset #300
    //   Java source line #92	-> byte code offset #302
    //   Java source line #97	-> byte code offset #307
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	310	0	this	JsonWebCall
    //   0	310	1	argument	Object
    //   11	268	2	httpClient	org.apache.http.client.HttpClient
    //   13	281	3	in	java.io.InputStream
    //   15	293	4	result	String
    //   89	8	5	response	org.apache.http.HttpResponse
    //   119	52	5	ex	Exception
    //   24	24	6	gson	org.bukkit.craftbukkit.libs.com.google.gson.Gson
    //   192	6	6	trace	StackTraceElement
    //   37	169	7	request	org.apache.http.client.methods.HttpPost
    //   56	152	8	params	org.apache.http.entity.StringEntity
    //   176	12	9	arrayOfStackTraceElement	StackTraceElement[]
    //   244	32	10	localObject	Object
    //   234	3	11	e	java.io.IOException
    //   268	3	11	e	java.io.IOException
    //   300	3	11	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   17	116	119	java/lang/Exception
    //   227	231	234	java/io/IOException
    //   17	212	244	finally
    //   261	265	268	java/io/IOException
    //   293	297	300	java/io/IOException
  }
  
  public void Execute()
  {
    Execute(null);
  }
  
  /* Error */
  public void Execute(Object argument)
  {
    // Byte code:
    //   0: new 60	org/apache/http/impl/client/DefaultHttpClient
    //   3: dup
    //   4: aload_0
    //   5: getfield 42	mineplex/core/server/remotecall/JsonWebCall:_connectionManager	Lorg/apache/http/impl/conn/PoolingClientConnectionManager;
    //   8: invokespecial 62	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/conn/ClientConnectionManager;)V
    //   11: astore_2
    //   12: aconst_null
    //   13: astore_3
    //   14: new 65	org/bukkit/craftbukkit/libs/com/google/gson/Gson
    //   17: dup
    //   18: invokespecial 67	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
    //   21: astore 4
    //   23: new 68	org/apache/http/client/methods/HttpPost
    //   26: dup
    //   27: aload_0
    //   28: getfield 15	mineplex/core/server/remotecall/JsonWebCall:_url	Ljava/lang/String;
    //   31: invokespecial 70	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   34: astore 5
    //   36: aload_1
    //   37: ifnull +41 -> 78
    //   40: new 72	org/apache/http/entity/StringEntity
    //   43: dup
    //   44: aload 4
    //   46: aload_1
    //   47: invokevirtual 74	org/bukkit/craftbukkit/libs/com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   50: invokespecial 77	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   53: astore 6
    //   55: aload 6
    //   57: new 78	org/apache/http/message/BasicHeader
    //   60: dup
    //   61: ldc 80
    //   63: ldc 82
    //   65: invokespecial 84	org/apache/http/message/BasicHeader:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   68: invokevirtual 87	org/apache/http/entity/StringEntity:setContentType	(Lorg/apache/http/Header;)V
    //   71: aload 5
    //   73: aload 6
    //   75: invokevirtual 91	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   78: aload_2
    //   79: aload 5
    //   81: invokeinterface 95 2 0
    //   86: pop
    //   87: goto +139 -> 226
    //   90: astore 4
    //   92: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   95: new 123	java/lang/StringBuilder
    //   98: dup
    //   99: ldc -51
    //   101: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   104: aload 4
    //   106: invokevirtual 128	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   109: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   118: aload 4
    //   120: invokevirtual 148	java/lang/Exception:getStackTrace	()[Ljava/lang/StackTraceElement;
    //   123: dup
    //   124: astore 8
    //   126: arraylength
    //   127: istore 7
    //   129: iconst_0
    //   130: istore 6
    //   132: goto +21 -> 153
    //   135: aload 8
    //   137: iload 6
    //   139: aaload
    //   140: astore 5
    //   142: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   145: aload 5
    //   147: invokevirtual 152	java/io/PrintStream:println	(Ljava/lang/Object;)V
    //   150: iinc 6 1
    //   153: iload 6
    //   155: iload 7
    //   157: if_icmplt -22 -> 135
    //   160: aload_2
    //   161: invokeinterface 155 1 0
    //   166: invokeinterface 159 1 0
    //   171: aload_3
    //   172: ifnull +83 -> 255
    //   175: aload_3
    //   176: invokevirtual 164	java/io/InputStream:close	()V
    //   179: goto +76 -> 255
    //   182: astore 10
    //   184: aload 10
    //   186: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   189: goto +66 -> 255
    //   192: astore 9
    //   194: aload_2
    //   195: invokeinterface 155 1 0
    //   200: invokeinterface 159 1 0
    //   205: aload_3
    //   206: ifnull +17 -> 223
    //   209: aload_3
    //   210: invokevirtual 164	java/io/InputStream:close	()V
    //   213: goto +10 -> 223
    //   216: astore 10
    //   218: aload 10
    //   220: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   223: aload 9
    //   225: athrow
    //   226: aload_2
    //   227: invokeinterface 155 1 0
    //   232: invokeinterface 159 1 0
    //   237: aload_3
    //   238: ifnull +17 -> 255
    //   241: aload_3
    //   242: invokevirtual 164	java/io/InputStream:close	()V
    //   245: goto +10 -> 255
    //   248: astore 10
    //   250: aload 10
    //   252: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   255: return
    // Line number table:
    //   Java source line #107	-> byte code offset #0
    //   Java source line #108	-> byte code offset #12
    //   Java source line #112	-> byte code offset #14
    //   Java source line #113	-> byte code offset #23
    //   Java source line #115	-> byte code offset #36
    //   Java source line #117	-> byte code offset #40
    //   Java source line #118	-> byte code offset #55
    //   Java source line #119	-> byte code offset #71
    //   Java source line #122	-> byte code offset #78
    //   Java source line #123	-> byte code offset #87
    //   Java source line #124	-> byte code offset #90
    //   Java source line #126	-> byte code offset #92
    //   Java source line #128	-> byte code offset #118
    //   Java source line #130	-> byte code offset #142
    //   Java source line #128	-> byte code offset #150
    //   Java source line #135	-> byte code offset #160
    //   Java source line #137	-> byte code offset #171
    //   Java source line #141	-> byte code offset #175
    //   Java source line #142	-> byte code offset #179
    //   Java source line #143	-> byte code offset #182
    //   Java source line #145	-> byte code offset #184
    //   Java source line #134	-> byte code offset #192
    //   Java source line #135	-> byte code offset #194
    //   Java source line #137	-> byte code offset #205
    //   Java source line #141	-> byte code offset #209
    //   Java source line #142	-> byte code offset #213
    //   Java source line #143	-> byte code offset #216
    //   Java source line #145	-> byte code offset #218
    //   Java source line #148	-> byte code offset #223
    //   Java source line #135	-> byte code offset #226
    //   Java source line #137	-> byte code offset #237
    //   Java source line #141	-> byte code offset #241
    //   Java source line #142	-> byte code offset #245
    //   Java source line #143	-> byte code offset #248
    //   Java source line #145	-> byte code offset #250
    //   Java source line #149	-> byte code offset #255
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	256	0	this	JsonWebCall
    //   0	256	1	argument	Object
    //   11	216	2	httpClient	org.apache.http.client.HttpClient
    //   13	229	3	in	java.io.InputStream
    //   21	24	4	gson	org.bukkit.craftbukkit.libs.com.google.gson.Gson
    //   90	29	4	ex	Exception
    //   34	46	5	request	org.apache.http.client.methods.HttpPost
    //   140	6	5	trace	StackTraceElement
    //   53	105	6	params	org.apache.http.entity.StringEntity
    //   127	31	7	localStringEntity1	org.apache.http.entity.StringEntity
    //   124	12	8	arrayOfStackTraceElement	StackTraceElement[]
    //   192	32	9	localObject	Object
    //   182	3	10	e	java.io.IOException
    //   216	3	10	e	java.io.IOException
    //   248	3	10	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   14	87	90	java/lang/Exception
    //   175	179	182	java/io/IOException
    //   14	160	192	finally
    //   209	213	216	java/io/IOException
    //   241	245	248	java/io/IOException
  }
  
  public <T> T Execute(Class<T> returnClass)
  {
    return (T)Execute(returnClass, null);
  }
  
  /* Error */
  public <T> T Execute(java.lang.reflect.Type returnType, Object argument)
  {
    // Byte code:
    //   0: new 60	org/apache/http/impl/client/DefaultHttpClient
    //   3: dup
    //   4: aload_0
    //   5: getfield 42	mineplex/core/server/remotecall/JsonWebCall:_connectionManager	Lorg/apache/http/impl/conn/PoolingClientConnectionManager;
    //   8: invokespecial 62	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/conn/ClientConnectionManager;)V
    //   11: astore_3
    //   12: aconst_null
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aconst_null
    //   19: astore 6
    //   21: new 65	org/bukkit/craftbukkit/libs/com/google/gson/Gson
    //   24: dup
    //   25: invokespecial 67	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
    //   28: astore 8
    //   30: new 68	org/apache/http/client/methods/HttpPost
    //   33: dup
    //   34: aload_0
    //   35: getfield 15	mineplex/core/server/remotecall/JsonWebCall:_url	Ljava/lang/String;
    //   38: invokespecial 70	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   41: astore 9
    //   43: aload_2
    //   44: ifnull +41 -> 85
    //   47: new 72	org/apache/http/entity/StringEntity
    //   50: dup
    //   51: aload 8
    //   53: aload_2
    //   54: invokevirtual 74	org/bukkit/craftbukkit/libs/com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   57: invokespecial 77	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   60: astore 10
    //   62: aload 10
    //   64: new 78	org/apache/http/message/BasicHeader
    //   67: dup
    //   68: ldc 80
    //   70: ldc 82
    //   72: invokespecial 84	org/apache/http/message/BasicHeader:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   75: invokevirtual 87	org/apache/http/entity/StringEntity:setContentType	(Lorg/apache/http/Header;)V
    //   78: aload 9
    //   80: aload 10
    //   82: invokevirtual 91	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   85: aload_3
    //   86: aload 9
    //   88: invokeinterface 95 2 0
    //   93: astore 7
    //   95: aload 7
    //   97: ifnull +206 -> 303
    //   100: aload 7
    //   102: invokeinterface 101 1 0
    //   107: invokeinterface 107 1 0
    //   112: astore 4
    //   114: aload_0
    //   115: aload 4
    //   117: invokevirtual 113	mineplex/core/server/remotecall/JsonWebCall:convertStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   120: astore 6
    //   122: new 65	org/bukkit/craftbukkit/libs/com/google/gson/Gson
    //   125: dup
    //   126: invokespecial 67	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
    //   129: aload 6
    //   131: aload_1
    //   132: invokevirtual 219	org/bukkit/craftbukkit/libs/com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/reflect/Type;)Ljava/lang/Object;
    //   135: astore 5
    //   137: goto +166 -> 303
    //   140: astore 7
    //   142: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   145: new 123	java/lang/StringBuilder
    //   148: dup
    //   149: ldc 125
    //   151: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   154: aload 7
    //   156: invokevirtual 128	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   159: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   165: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   168: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   171: new 123	java/lang/StringBuilder
    //   174: dup
    //   175: ldc -110
    //   177: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   180: aload 6
    //   182: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   188: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   191: aload 7
    //   193: invokevirtual 148	java/lang/Exception:getStackTrace	()[Ljava/lang/StackTraceElement;
    //   196: dup
    //   197: astore 11
    //   199: arraylength
    //   200: istore 10
    //   202: iconst_0
    //   203: istore 9
    //   205: goto +21 -> 226
    //   208: aload 11
    //   210: iload 9
    //   212: aaload
    //   213: astore 8
    //   215: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   218: aload 8
    //   220: invokevirtual 152	java/io/PrintStream:println	(Ljava/lang/Object;)V
    //   223: iinc 9 1
    //   226: iload 9
    //   228: iload 10
    //   230: if_icmplt -22 -> 208
    //   233: aload_3
    //   234: invokeinterface 155 1 0
    //   239: invokeinterface 159 1 0
    //   244: aload 4
    //   246: ifnull +88 -> 334
    //   249: aload 4
    //   251: invokevirtual 164	java/io/InputStream:close	()V
    //   254: goto +80 -> 334
    //   257: astore 13
    //   259: aload 13
    //   261: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   264: goto +70 -> 334
    //   267: astore 12
    //   269: aload_3
    //   270: invokeinterface 155 1 0
    //   275: invokeinterface 159 1 0
    //   280: aload 4
    //   282: ifnull +18 -> 300
    //   285: aload 4
    //   287: invokevirtual 164	java/io/InputStream:close	()V
    //   290: goto +10 -> 300
    //   293: astore 13
    //   295: aload 13
    //   297: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   300: aload 12
    //   302: athrow
    //   303: aload_3
    //   304: invokeinterface 155 1 0
    //   309: invokeinterface 159 1 0
    //   314: aload 4
    //   316: ifnull +18 -> 334
    //   319: aload 4
    //   321: invokevirtual 164	java/io/InputStream:close	()V
    //   324: goto +10 -> 334
    //   327: astore 13
    //   329: aload 13
    //   331: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   334: aload 5
    //   336: areturn
    // Line number table:
    //   Java source line #158	-> byte code offset #0
    //   Java source line #159	-> byte code offset #12
    //   Java source line #160	-> byte code offset #15
    //   Java source line #161	-> byte code offset #18
    //   Java source line #167	-> byte code offset #21
    //   Java source line #168	-> byte code offset #30
    //   Java source line #170	-> byte code offset #43
    //   Java source line #172	-> byte code offset #47
    //   Java source line #173	-> byte code offset #62
    //   Java source line #174	-> byte code offset #78
    //   Java source line #177	-> byte code offset #85
    //   Java source line #179	-> byte code offset #95
    //   Java source line #181	-> byte code offset #100
    //   Java source line #183	-> byte code offset #114
    //   Java source line #184	-> byte code offset #122
    //   Java source line #186	-> byte code offset #137
    //   Java source line #187	-> byte code offset #140
    //   Java source line #189	-> byte code offset #142
    //   Java source line #190	-> byte code offset #168
    //   Java source line #192	-> byte code offset #191
    //   Java source line #194	-> byte code offset #215
    //   Java source line #192	-> byte code offset #223
    //   Java source line #199	-> byte code offset #233
    //   Java source line #201	-> byte code offset #244
    //   Java source line #205	-> byte code offset #249
    //   Java source line #206	-> byte code offset #254
    //   Java source line #207	-> byte code offset #257
    //   Java source line #209	-> byte code offset #259
    //   Java source line #198	-> byte code offset #267
    //   Java source line #199	-> byte code offset #269
    //   Java source line #201	-> byte code offset #280
    //   Java source line #205	-> byte code offset #285
    //   Java source line #206	-> byte code offset #290
    //   Java source line #207	-> byte code offset #293
    //   Java source line #209	-> byte code offset #295
    //   Java source line #212	-> byte code offset #300
    //   Java source line #199	-> byte code offset #303
    //   Java source line #201	-> byte code offset #314
    //   Java source line #205	-> byte code offset #319
    //   Java source line #206	-> byte code offset #324
    //   Java source line #207	-> byte code offset #327
    //   Java source line #209	-> byte code offset #329
    //   Java source line #214	-> byte code offset #334
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	337	0	this	JsonWebCall
    //   0	337	1	returnType	java.lang.reflect.Type
    //   0	337	2	argument	Object
    //   11	293	3	httpClient	org.apache.http.client.HttpClient
    //   13	307	4	in	java.io.InputStream
    //   16	319	5	returnData	T
    //   19	162	6	result	String
    //   93	8	7	response	org.apache.http.HttpResponse
    //   140	52	7	ex	Exception
    //   28	24	8	gson	org.bukkit.craftbukkit.libs.com.google.gson.Gson
    //   213	6	8	trace	StackTraceElement
    //   41	186	9	request	org.apache.http.client.methods.HttpPost
    //   60	169	10	params	org.apache.http.entity.StringEntity
    //   197	12	11	arrayOfStackTraceElement	StackTraceElement[]
    //   267	34	12	localObject	Object
    //   257	3	13	e	java.io.IOException
    //   293	3	13	e	java.io.IOException
    //   327	3	13	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   21	137	140	java/lang/Exception
    //   249	254	257	java/io/IOException
    //   21	233	267	finally
    //   285	290	293	java/io/IOException
    //   319	324	327	java/io/IOException
  }
  
  /* Error */
  public <T> T Execute(Class<T> returnClass, Object argument)
  {
    // Byte code:
    //   0: new 60	org/apache/http/impl/client/DefaultHttpClient
    //   3: dup
    //   4: aload_0
    //   5: getfield 42	mineplex/core/server/remotecall/JsonWebCall:_connectionManager	Lorg/apache/http/impl/conn/PoolingClientConnectionManager;
    //   8: invokespecial 62	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/conn/ClientConnectionManager;)V
    //   11: astore_3
    //   12: aconst_null
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: aconst_null
    //   19: astore 6
    //   21: new 65	org/bukkit/craftbukkit/libs/com/google/gson/Gson
    //   24: dup
    //   25: invokespecial 67	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
    //   28: astore 8
    //   30: new 68	org/apache/http/client/methods/HttpPost
    //   33: dup
    //   34: aload_0
    //   35: getfield 15	mineplex/core/server/remotecall/JsonWebCall:_url	Ljava/lang/String;
    //   38: invokespecial 70	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   41: astore 9
    //   43: aload_2
    //   44: ifnull +41 -> 85
    //   47: new 72	org/apache/http/entity/StringEntity
    //   50: dup
    //   51: aload 8
    //   53: aload_2
    //   54: invokevirtual 74	org/bukkit/craftbukkit/libs/com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   57: invokespecial 77	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   60: astore 10
    //   62: aload 10
    //   64: new 78	org/apache/http/message/BasicHeader
    //   67: dup
    //   68: ldc 80
    //   70: ldc 82
    //   72: invokespecial 84	org/apache/http/message/BasicHeader:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   75: invokevirtual 87	org/apache/http/entity/StringEntity:setContentType	(Lorg/apache/http/Header;)V
    //   78: aload 9
    //   80: aload 10
    //   82: invokevirtual 91	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   85: aload_3
    //   86: aload 9
    //   88: invokeinterface 95 2 0
    //   93: astore 7
    //   95: aload 7
    //   97: ifnull +206 -> 303
    //   100: aload 7
    //   102: invokeinterface 101 1 0
    //   107: invokeinterface 107 1 0
    //   112: astore 4
    //   114: aload_0
    //   115: aload 4
    //   117: invokevirtual 113	mineplex/core/server/remotecall/JsonWebCall:convertStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   120: astore 6
    //   122: new 65	org/bukkit/craftbukkit/libs/com/google/gson/Gson
    //   125: dup
    //   126: invokespecial 67	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
    //   129: aload 6
    //   131: aload_1
    //   132: invokevirtual 230	org/bukkit/craftbukkit/libs/com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   135: astore 5
    //   137: goto +166 -> 303
    //   140: astore 7
    //   142: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   145: new 123	java/lang/StringBuilder
    //   148: dup
    //   149: ldc 125
    //   151: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   154: aload 7
    //   156: invokevirtual 128	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   159: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   165: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   168: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   171: new 123	java/lang/StringBuilder
    //   174: dup
    //   175: ldc -110
    //   177: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   180: aload 6
    //   182: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   188: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   191: aload 7
    //   193: invokevirtual 148	java/lang/Exception:getStackTrace	()[Ljava/lang/StackTraceElement;
    //   196: dup
    //   197: astore 11
    //   199: arraylength
    //   200: istore 10
    //   202: iconst_0
    //   203: istore 9
    //   205: goto +21 -> 226
    //   208: aload 11
    //   210: iload 9
    //   212: aaload
    //   213: astore 8
    //   215: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   218: aload 8
    //   220: invokevirtual 152	java/io/PrintStream:println	(Ljava/lang/Object;)V
    //   223: iinc 9 1
    //   226: iload 9
    //   228: iload 10
    //   230: if_icmplt -22 -> 208
    //   233: aload_3
    //   234: invokeinterface 155 1 0
    //   239: invokeinterface 159 1 0
    //   244: aload 4
    //   246: ifnull +88 -> 334
    //   249: aload 4
    //   251: invokevirtual 164	java/io/InputStream:close	()V
    //   254: goto +80 -> 334
    //   257: astore 13
    //   259: aload 13
    //   261: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   264: goto +70 -> 334
    //   267: astore 12
    //   269: aload_3
    //   270: invokeinterface 155 1 0
    //   275: invokeinterface 159 1 0
    //   280: aload 4
    //   282: ifnull +18 -> 300
    //   285: aload 4
    //   287: invokevirtual 164	java/io/InputStream:close	()V
    //   290: goto +10 -> 300
    //   293: astore 13
    //   295: aload 13
    //   297: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   300: aload 12
    //   302: athrow
    //   303: aload_3
    //   304: invokeinterface 155 1 0
    //   309: invokeinterface 159 1 0
    //   314: aload 4
    //   316: ifnull +18 -> 334
    //   319: aload 4
    //   321: invokevirtual 164	java/io/InputStream:close	()V
    //   324: goto +10 -> 334
    //   327: astore 13
    //   329: aload 13
    //   331: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   334: aload 5
    //   336: areturn
    // Line number table:
    //   Java source line #219	-> byte code offset #0
    //   Java source line #220	-> byte code offset #12
    //   Java source line #221	-> byte code offset #15
    //   Java source line #222	-> byte code offset #18
    //   Java source line #228	-> byte code offset #21
    //   Java source line #229	-> byte code offset #30
    //   Java source line #231	-> byte code offset #43
    //   Java source line #233	-> byte code offset #47
    //   Java source line #234	-> byte code offset #62
    //   Java source line #235	-> byte code offset #78
    //   Java source line #238	-> byte code offset #85
    //   Java source line #240	-> byte code offset #95
    //   Java source line #242	-> byte code offset #100
    //   Java source line #244	-> byte code offset #114
    //   Java source line #245	-> byte code offset #122
    //   Java source line #247	-> byte code offset #137
    //   Java source line #248	-> byte code offset #140
    //   Java source line #250	-> byte code offset #142
    //   Java source line #251	-> byte code offset #168
    //   Java source line #253	-> byte code offset #191
    //   Java source line #255	-> byte code offset #215
    //   Java source line #253	-> byte code offset #223
    //   Java source line #260	-> byte code offset #233
    //   Java source line #262	-> byte code offset #244
    //   Java source line #266	-> byte code offset #249
    //   Java source line #267	-> byte code offset #254
    //   Java source line #268	-> byte code offset #257
    //   Java source line #270	-> byte code offset #259
    //   Java source line #259	-> byte code offset #267
    //   Java source line #260	-> byte code offset #269
    //   Java source line #262	-> byte code offset #280
    //   Java source line #266	-> byte code offset #285
    //   Java source line #267	-> byte code offset #290
    //   Java source line #268	-> byte code offset #293
    //   Java source line #270	-> byte code offset #295
    //   Java source line #273	-> byte code offset #300
    //   Java source line #260	-> byte code offset #303
    //   Java source line #262	-> byte code offset #314
    //   Java source line #266	-> byte code offset #319
    //   Java source line #267	-> byte code offset #324
    //   Java source line #268	-> byte code offset #327
    //   Java source line #270	-> byte code offset #329
    //   Java source line #275	-> byte code offset #334
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	337	0	this	JsonWebCall
    //   0	337	1	returnClass	Class<T>
    //   0	337	2	argument	Object
    //   11	293	3	httpClient	org.apache.http.client.HttpClient
    //   13	307	4	in	java.io.InputStream
    //   16	319	5	returnData	T
    //   19	162	6	result	String
    //   93	8	7	response	org.apache.http.HttpResponse
    //   140	52	7	ex	Exception
    //   28	24	8	gson	org.bukkit.craftbukkit.libs.com.google.gson.Gson
    //   213	6	8	trace	StackTraceElement
    //   41	186	9	request	org.apache.http.client.methods.HttpPost
    //   60	169	10	params	org.apache.http.entity.StringEntity
    //   197	12	11	arrayOfStackTraceElement	StackTraceElement[]
    //   267	34	12	localObject	Object
    //   257	3	13	e	java.io.IOException
    //   293	3	13	e	java.io.IOException
    //   327	3	13	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   21	137	140	java/lang/Exception
    //   249	254	257	java/io/IOException
    //   21	233	267	finally
    //   285	290	293	java/io/IOException
    //   319	324	327	java/io/IOException
  }
  
  public <T> void Execute(Class<T> callbackClass, Callback<T> callback)
  {
    Execute(callbackClass, callback, null);
  }
  
  /* Error */
  public <T> void Execute(Class<T> callbackClass, Callback<T> callback, Object argument)
  {
    // Byte code:
    //   0: new 60	org/apache/http/impl/client/DefaultHttpClient
    //   3: dup
    //   4: aload_0
    //   5: getfield 42	mineplex/core/server/remotecall/JsonWebCall:_connectionManager	Lorg/apache/http/impl/conn/PoolingClientConnectionManager;
    //   8: invokespecial 62	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/conn/ClientConnectionManager;)V
    //   11: astore 4
    //   13: aconst_null
    //   14: astore 5
    //   16: aconst_null
    //   17: astore 6
    //   19: new 65	org/bukkit/craftbukkit/libs/com/google/gson/Gson
    //   22: dup
    //   23: invokespecial 67	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
    //   26: astore 8
    //   28: new 68	org/apache/http/client/methods/HttpPost
    //   31: dup
    //   32: aload_0
    //   33: getfield 15	mineplex/core/server/remotecall/JsonWebCall:_url	Ljava/lang/String;
    //   36: invokespecial 70	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   39: astore 9
    //   41: aload_3
    //   42: ifnull +41 -> 83
    //   45: new 72	org/apache/http/entity/StringEntity
    //   48: dup
    //   49: aload 8
    //   51: aload_3
    //   52: invokevirtual 74	org/bukkit/craftbukkit/libs/com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   55: invokespecial 77	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   58: astore 10
    //   60: aload 10
    //   62: new 78	org/apache/http/message/BasicHeader
    //   65: dup
    //   66: ldc 80
    //   68: ldc 82
    //   70: invokespecial 84	org/apache/http/message/BasicHeader:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   73: invokevirtual 87	org/apache/http/entity/StringEntity:setContentType	(Lorg/apache/http/Header;)V
    //   76: aload 9
    //   78: aload 10
    //   80: invokevirtual 91	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   83: aload 4
    //   85: aload 9
    //   87: invokeinterface 95 2 0
    //   92: astore 7
    //   94: aload 7
    //   96: ifnull +182 -> 278
    //   99: aload_2
    //   100: ifnull +178 -> 278
    //   103: aload 7
    //   105: invokeinterface 101 1 0
    //   110: invokeinterface 107 1 0
    //   115: astore 5
    //   117: aload_0
    //   118: aload 5
    //   120: invokevirtual 113	mineplex/core/server/remotecall/JsonWebCall:convertStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   123: astore 6
    //   125: aload_2
    //   126: new 65	org/bukkit/craftbukkit/libs/com/google/gson/Gson
    //   129: dup
    //   130: invokespecial 67	org/bukkit/craftbukkit/libs/com/google/gson/Gson:<init>	()V
    //   133: aload 6
    //   135: aload_1
    //   136: invokevirtual 230	org/bukkit/craftbukkit/libs/com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   139: invokeinterface 245 2 0
    //   144: goto +134 -> 278
    //   147: astore 7
    //   149: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   152: new 123	java/lang/StringBuilder
    //   155: dup
    //   156: ldc 125
    //   158: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   161: aload 7
    //   163: invokevirtual 128	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   166: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   172: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   175: aload 7
    //   177: invokevirtual 148	java/lang/Exception:getStackTrace	()[Ljava/lang/StackTraceElement;
    //   180: invokestatic 250	mineplex/core/common/util/UtilSystem:printStackTrace	([Ljava/lang/StackTraceElement;)V
    //   183: getstatic 117	java/lang/System:out	Ljava/io/PrintStream;
    //   186: new 123	java/lang/StringBuilder
    //   189: dup
    //   190: ldc -110
    //   192: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   195: aload 6
    //   197: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   203: invokevirtual 141	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   206: aload 4
    //   208: invokeinterface 155 1 0
    //   213: invokeinterface 159 1 0
    //   218: aload 5
    //   220: ifnull +90 -> 310
    //   223: aload 5
    //   225: invokevirtual 164	java/io/InputStream:close	()V
    //   228: goto +82 -> 310
    //   231: astore 12
    //   233: aload 12
    //   235: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   238: goto +72 -> 310
    //   241: astore 11
    //   243: aload 4
    //   245: invokeinterface 155 1 0
    //   250: invokeinterface 159 1 0
    //   255: aload 5
    //   257: ifnull +18 -> 275
    //   260: aload 5
    //   262: invokevirtual 164	java/io/InputStream:close	()V
    //   265: goto +10 -> 275
    //   268: astore 12
    //   270: aload 12
    //   272: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   275: aload 11
    //   277: athrow
    //   278: aload 4
    //   280: invokeinterface 155 1 0
    //   285: invokeinterface 159 1 0
    //   290: aload 5
    //   292: ifnull +18 -> 310
    //   295: aload 5
    //   297: invokevirtual 164	java/io/InputStream:close	()V
    //   300: goto +10 -> 310
    //   303: astore 12
    //   305: aload 12
    //   307: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   310: return
    // Line number table:
    //   Java source line #285	-> byte code offset #0
    //   Java source line #286	-> byte code offset #13
    //   Java source line #287	-> byte code offset #16
    //   Java source line #293	-> byte code offset #19
    //   Java source line #294	-> byte code offset #28
    //   Java source line #296	-> byte code offset #41
    //   Java source line #298	-> byte code offset #45
    //   Java source line #299	-> byte code offset #60
    //   Java source line #300	-> byte code offset #76
    //   Java source line #303	-> byte code offset #83
    //   Java source line #305	-> byte code offset #94
    //   Java source line #307	-> byte code offset #103
    //   Java source line #309	-> byte code offset #117
    //   Java source line #310	-> byte code offset #125
    //   Java source line #312	-> byte code offset #144
    //   Java source line #313	-> byte code offset #147
    //   Java source line #315	-> byte code offset #149
    //   Java source line #316	-> byte code offset #175
    //   Java source line #317	-> byte code offset #183
    //   Java source line #321	-> byte code offset #206
    //   Java source line #323	-> byte code offset #218
    //   Java source line #327	-> byte code offset #223
    //   Java source line #328	-> byte code offset #228
    //   Java source line #329	-> byte code offset #231
    //   Java source line #331	-> byte code offset #233
    //   Java source line #320	-> byte code offset #241
    //   Java source line #321	-> byte code offset #243
    //   Java source line #323	-> byte code offset #255
    //   Java source line #327	-> byte code offset #260
    //   Java source line #328	-> byte code offset #265
    //   Java source line #329	-> byte code offset #268
    //   Java source line #331	-> byte code offset #270
    //   Java source line #334	-> byte code offset #275
    //   Java source line #321	-> byte code offset #278
    //   Java source line #323	-> byte code offset #290
    //   Java source line #327	-> byte code offset #295
    //   Java source line #328	-> byte code offset #300
    //   Java source line #329	-> byte code offset #303
    //   Java source line #331	-> byte code offset #305
    //   Java source line #335	-> byte code offset #310
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	311	0	this	JsonWebCall
    //   0	311	1	callbackClass	Class<T>
    //   0	311	2	callback	Callback<T>
    //   0	311	3	argument	Object
    //   11	268	4	httpClient	org.apache.http.client.HttpClient
    //   14	282	5	in	java.io.InputStream
    //   17	179	6	result	String
    //   92	12	7	response	org.apache.http.HttpResponse
    //   147	29	7	ex	Exception
    //   26	24	8	gson	org.bukkit.craftbukkit.libs.com.google.gson.Gson
    //   39	47	9	request	org.apache.http.client.methods.HttpPost
    //   58	21	10	params	org.apache.http.entity.StringEntity
    //   241	35	11	localObject	Object
    //   231	3	12	e	java.io.IOException
    //   268	3	12	e	java.io.IOException
    //   303	3	12	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   19	144	147	java/lang/Exception
    //   223	228	231	java/io/IOException
    //   19	206	241	finally
    //   260	265	268	java/io/IOException
    //   295	300	303	java/io/IOException
  }
  
  /* Error */
  protected String convertStreamToString(java.io.InputStream is)
  {
    // Byte code:
    //   0: new 255	java/io/BufferedReader
    //   3: dup
    //   4: new 257	java/io/InputStreamReader
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 259	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   12: invokespecial 262	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   15: astore_2
    //   16: new 123	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 265	java/lang/StringBuilder:<init>	()V
    //   23: astore_3
    //   24: aconst_null
    //   25: astore 4
    //   27: goto +29 -> 56
    //   30: aload_3
    //   31: new 123	java/lang/StringBuilder
    //   34: dup
    //   35: aload 4
    //   37: invokestatic 266	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   40: invokespecial 127	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   43: ldc_w 269
    //   46: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   52: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: aload_2
    //   57: invokevirtual 271	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   60: dup
    //   61: astore 4
    //   63: ifnonnull -33 -> 30
    //   66: goto +46 -> 112
    //   69: astore 5
    //   71: aload 5
    //   73: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   76: aload_1
    //   77: invokevirtual 164	java/io/InputStream:close	()V
    //   80: goto +46 -> 126
    //   83: astore 7
    //   85: aload 7
    //   87: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   90: goto +36 -> 126
    //   93: astore 6
    //   95: aload_1
    //   96: invokevirtual 164	java/io/InputStream:close	()V
    //   99: goto +10 -> 109
    //   102: astore 7
    //   104: aload 7
    //   106: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   109: aload 6
    //   111: athrow
    //   112: aload_1
    //   113: invokevirtual 164	java/io/InputStream:close	()V
    //   116: goto +10 -> 126
    //   119: astore 7
    //   121: aload 7
    //   123: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   126: aload_3
    //   127: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: areturn
    // Line number table:
    //   Java source line #339	-> byte code offset #0
    //   Java source line #340	-> byte code offset #16
    //   Java source line #342	-> byte code offset #24
    //   Java source line #344	-> byte code offset #27
    //   Java source line #345	-> byte code offset #30
    //   Java source line #344	-> byte code offset #56
    //   Java source line #347	-> byte code offset #66
    //   Java source line #348	-> byte code offset #71
    //   Java source line #351	-> byte code offset #76
    //   Java source line #352	-> byte code offset #80
    //   Java source line #353	-> byte code offset #85
    //   Java source line #349	-> byte code offset #93
    //   Java source line #351	-> byte code offset #95
    //   Java source line #352	-> byte code offset #99
    //   Java source line #353	-> byte code offset #104
    //   Java source line #355	-> byte code offset #109
    //   Java source line #351	-> byte code offset #112
    //   Java source line #352	-> byte code offset #116
    //   Java source line #353	-> byte code offset #121
    //   Java source line #356	-> byte code offset #126
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	131	0	this	JsonWebCall
    //   0	131	1	is	java.io.InputStream
    //   15	42	2	reader	java.io.BufferedReader
    //   23	104	3	sb	StringBuilder
    //   25	37	4	line	String
    //   69	3	5	e	java.io.IOException
    //   93	17	6	localObject	Object
    //   83	3	7	e	java.io.IOException
    //   102	3	7	e	java.io.IOException
    //   119	3	7	e	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   27	66	69	java/io/IOException
    //   76	80	83	java/io/IOException
    //   27	76	93	finally
    //   95	99	102	java/io/IOException
    //   112	116	119	java/io/IOException
  }
}

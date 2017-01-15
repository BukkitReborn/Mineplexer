package org.jooq.impl;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Constants;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

class DefaultRenderContext
  extends AbstractContext<RenderContext>
  implements RenderContext
{
  private static final JooqLogger log = JooqLogger.getLogger(DefaultRenderContext.class);
  private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[A-Za-z][A-Za-z0-9_]*");
  private static final Pattern NEWLINE = Pattern.compile("[\\n\\r]");
  private int printMargin = 80;
  
  DefaultRenderContext(Configuration configuration)
  {
    super(configuration, null);
    
    Settings settings = configuration.settings();
    
    this.sql = new StringBuilder();
    this.cachedRenderKeywordStyle = settings.getRenderKeywordStyle();
    this.cachedRenderFormatted = Boolean.TRUE.equals(settings.isRenderFormatted());
    this.cachedRenderNameStyle = settings.getRenderNameStyle();
  }
  
  DefaultRenderContext(RenderContext context)
  {
    this(context.configuration());
    
    paramType(context.paramType());
    qualify(context.qualify());
    castMode(context.castMode());
    declareFields(context.declareFields());
    declareTables(context.declareTables());
    data().putAll(context.data());
  }
  
  public final BindContext bindValue(Object value, Field<?> field)
    throws DataAccessException
  {
    throw new UnsupportedOperationException();
  }
  
  public final String peekAlias()
  {
    return "alias_" + (this.alias + 1);
  }
  
  public final String nextAlias()
  {
    return "alias_" + ++this.alias;
  }
  
  public final String render()
  {
    return this.sql.toString();
  }
  
  public final String render(QueryPart part)
  {
    return ((RenderContext)new DefaultRenderContext(this).visit(part)).render();
  }
  
  public final RenderContext keyword(String keyword)
  {
    if (RenderKeywordStyle.UPPER == this.cachedRenderKeywordStyle) {
      return sql(keyword.toUpperCase());
    }
    return sql(keyword.toLowerCase());
  }
  
  public final RenderContext sql(String s)
  {
    return sql(s, (s == null) || (!this.cachedRenderFormatted));
  }
  
  public final RenderContext sql(String s, boolean literal)
  {
    if (literal) {
      this.sql.append(s);
    } else {
      this.sql.append(NEWLINE.matcher(s).replaceAll("$0" + indentation()));
    }
    return this;
  }
  
  public final RenderContext sql(char c)
  {
    this.sql.append(c);
    return this;
  }
  
  public final RenderContext sql(int i)
  {
    this.sql.append(i);
    return this;
  }
  
  public final RenderContext formatNewLine()
  {
    if (this.cachedRenderFormatted)
    {
      this.sql.append("\n");
      this.sql.append(indentation());
    }
    return this;
  }
  
  public final RenderContext formatNewLineAfterPrintMargin()
  {
    if ((this.cachedRenderFormatted) && (this.printMargin > 0) && 
      (this.sql.length() - this.sql.lastIndexOf("\n") > this.printMargin)) {
      formatNewLine();
    }
    return this;
  }
  
  private final String indentation()
  {
    return StringUtils.leftPad("", this.indent, " ");
  }
  
  public final RenderContext format(boolean format)
  {
    this.cachedRenderFormatted = format;
    return this;
  }
  
  public final boolean format()
  {
    return this.cachedRenderFormatted;
  }
  
  public final RenderContext formatSeparator()
  {
    if (this.cachedRenderFormatted) {
      formatNewLine();
    } else {
      this.sql.append(" ");
    }
    return this;
  }
  
  public final RenderContext formatIndentStart()
  {
    return formatIndentStart(2);
  }
  
  public final RenderContext formatIndentEnd()
  {
    return formatIndentEnd(2);
  }
  
  public final RenderContext formatIndentStart(int i)
  {
    if (this.cachedRenderFormatted) {
      this.indent += i;
    }
    return this;
  }
  
  public final RenderContext formatIndentEnd(int i)
  {
    if (this.cachedRenderFormatted) {
      this.indent -= i;
    }
    return this;
  }
  
  private Stack<Integer> indentLock()
  {
    if (this.indentLock == null) {
      this.indentLock = new Stack();
    }
    return this.indentLock;
  }
  
  public final RenderContext formatIndentLockStart()
  {
    if (this.cachedRenderFormatted)
    {
      indentLock().push(Integer.valueOf(this.indent));
      String[] lines = this.sql.toString().split("[\\n\\r]");
      this.indent = lines[(lines.length - 1)].length();
    }
    return this;
  }
  
  public final RenderContext formatIndentLockEnd()
  {
    if (this.cachedRenderFormatted) {
      this.indent = ((Integer)indentLock().pop()).intValue();
    }
    return this;
  }
  
  public final RenderContext formatPrintMargin(int margin)
  {
    this.printMargin = margin;
    return this;
  }
  
  public final RenderContext literal(String literal)
  {
    if (literal == null) {
      return this;
    }
    SQLDialect family = family();
    if (((family == SQLDialect.SQLITE) || (RenderNameStyle.QUOTED != this.cachedRenderNameStyle)) && ((family != SQLDialect.SQLITE) || 
    
      (!SQLITE_KEYWORDS.contains(literal.toUpperCase())))) {
      if (family != SQLDialect.SQLITE) {
        break label74;
      }
    }
    label74:
    boolean needsQuote = !IDENTIFIER_PATTERN.matcher(literal).matches();
    if (!needsQuote)
    {
      if (RenderNameStyle.LOWER == this.cachedRenderNameStyle) {
        literal = literal.toLowerCase();
      } else if (RenderNameStyle.UPPER == this.cachedRenderNameStyle) {
        literal = literal.toUpperCase();
      }
      sql(literal);
    }
    else
    {
      String[][] quotes = (String[][])Identifiers.QUOTES.get(family);
      
      sql(quotes[0][0]);
      sql(StringUtils.replace(literal, quotes[1][0], quotes[2][0]));
      sql(quotes[1][0]);
    }
    return this;
  }
  
  @Deprecated
  public final RenderContext sql(QueryPart part)
  {
    return (RenderContext)visit(part);
  }
  
  protected final void visit0(QueryPartInternal internal)
  {
    checkForceInline(internal);
    internal.accept(this);
  }
  
  private final void checkForceInline(QueryPart part)
    throws DefaultRenderContext.ForceInlineSignal
  {
    if (this.paramType == ParamType.INLINED) {
      return;
    }
    if ((part instanceof Param))
    {
      if (((Param)part).isInline()) {
        return;
      }
      switch (configuration().dialect().family())
      {
      case SQLITE: 
        checkForceInline(999);
        return;
      }
      return;
    }
  }
  
  private final void checkForceInline(int max)
    throws DefaultRenderContext.ForceInlineSignal
  {
    if ((Boolean.TRUE.equals(data("org.jooq.configuration.count-bind-values"))) && 
      (++this.params > max)) {
      throw new ForceInlineSignal();
    }
  }
  
  @Deprecated
  public final boolean inline()
  {
    return this.paramType == ParamType.INLINED;
  }
  
  @Deprecated
  public final boolean namedParams()
  {
    return this.paramType == ParamType.NAMED;
  }
  
  @Deprecated
  public final RenderContext inline(boolean i)
  {
    this.paramType = (i ? ParamType.INLINED : ParamType.INDEXED);
    return this;
  }
  
  @Deprecated
  public final RenderContext namedParams(boolean r)
  {
    this.paramType = (r ? ParamType.NAMED : ParamType.INDEXED);
    return this;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append("rendering    [");
    sb.append(render());
    sb.append("]\n");
    sb.append("parameters   [");
    sb.append(this.paramType);
    sb.append("]\n");
    
    toString(sb);
    return sb.toString();
  }
  
  private static final Set<String> SQLITE_KEYWORDS = new HashSet();
  private final StringBuilder sql;
  private int params;
  private int alias;
  private int indent;
  private Stack<Integer> indentLock;
  RenderKeywordStyle cachedRenderKeywordStyle;
  RenderNameStyle cachedRenderNameStyle;
  boolean cachedRenderFormatted;
  
  static
  {
    SQLITE_KEYWORDS.addAll(Arrays.asList(new String[] { "ABORT", "ACTION", "ADD", "AFTER", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ATTACH", "AUTOINCREMENT", "BEFORE", "BEGIN", "BETWEEN", "BY", "CASCADE", "CASE", "CAST", "CHECK", "COLLATE", "COLUMN", "COMMIT", "CONFLICT", "CONSTRAINT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "DATABASE", "DEFAULT", "DEFERRABLE", "DEFERRED", "DELETE", "DESC", "DETACH", "DISTINCT", "DROP", "EACH", "ELSE", "END", "ESCAPE", "EXCEPT", "EXCLUSIVE", "EXISTS", "EXPLAIN", "FAIL", "FOR", "FOREIGN", "FROM", "FULL", "GLOB", "GROUP", "HAVING", "IF", "IGNORE", "IMMEDIATE", "IN", "INDEX", "INDEXED", "INITIALLY", "INNER", "INSERT", "INSTEAD", "INTERSECT", "INTO", "IS", "ISNULL", "JOIN", "KEY", "LEFT", "LIKE", "LIMIT", "MATCH", "NATURAL", "NO", "NOT", "NOTNULL", "NULL", "OF", "OFFSET", "ON", "OR", "ORDER", "OUTER", "PLAN", "PRAGMA", "PRIMARY", "QUERY", "RAISE", "REFERENCES", "REGEXP", "REINDEX", "RELEASE", "RENAME", "REPLACE", "RESTRICT", "RIGHT", "ROLLBACK", "ROW", "SAVEPOINT", "SELECT", "SET", "TABLE", "TEMP", "TEMPORARY", "THEN", "TO", "TRANSACTION", "TRIGGER", "UNION", "UNIQUE", "UPDATE", "USING", "VACUUM", "VALUES", "VIEW", "VIRTUAL", "WHEN", "WHERE" }));
    if (!Boolean.getBoolean("org.jooq.no-logo"))
    {
      JooqLogger l = JooqLogger.getLogger(Constants.class);
      
      String message = "Thank you for using jOOQ 3.5.2";
      
      l.info("\n                                      \n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@  @@        @@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@        @@@@@@@@@@\n@@@@@@@@@@@@@@@@  @@  @@    @@@@@@@@@@\n@@@@@@@@@@  @@@@  @@  @@    @@@@@@@@@@\n@@@@@@@@@@        @@        @@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@        @@        @@@@@@@@@@\n@@@@@@@@@@    @@  @@  @@@@  @@@@@@@@@@\n@@@@@@@@@@    @@  @@  @@@@  @@@@@@@@@@\n@@@@@@@@@@        @@  @  @  @@@@@@@@@@\n@@@@@@@@@@        @@        @@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@  @@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  " + message + "\n                                      ");
    }
  }
  
  class ForceInlineSignal
    extends ControlFlowSignal
  {
    private static final long serialVersionUID = -9131368742983295195L;
    
    public ForceInlineSignal()
    {
      if (DefaultRenderContext.log.isDebugEnabled()) {
        DefaultRenderContext.log.debug("Re-render query", "Forcing bind variable inlining as " + DefaultRenderContext.this.configuration().dialect() + " does not support " + DefaultRenderContext.this.params + " bind variables (or more) in a single query");
      }
    }
  }
}

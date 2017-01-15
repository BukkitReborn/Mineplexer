package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.exception.DataAccessException;

abstract class AbstractBindContext
  extends AbstractContext<BindContext>
  implements BindContext
{
  AbstractBindContext(Configuration configuration, PreparedStatement stmt)
  {
    super(configuration, stmt);
  }
  
  @Deprecated
  public final BindContext bind(Collection<? extends QueryPart> parts)
  {
    return (BindContext)Utils.visitAll(this, parts);
  }
  
  @Deprecated
  public final BindContext bind(QueryPart[] parts)
  {
    return (BindContext)Utils.visitAll(this, parts);
  }
  
  @Deprecated
  public final BindContext bind(QueryPart part)
  {
    return (BindContext)visit(part);
  }
  
  protected void visit0(QueryPartInternal internal)
  {
    bindInternal(internal);
  }
  
  @Deprecated
  public final BindContext bindValues(Object... values)
  {
    if (values == null) {
      bindValues(new Object[] { null });
    } else {
      for (Object value : values)
      {
        Class<?> type = value == null ? Object.class : value.getClass();
        bindValue(value, DSL.val(value, type));
      }
    }
    return this;
  }
  
  @Deprecated
  public final BindContext bindValue(Object value, Class<?> type)
  {
    try
    {
      return bindValue0(value, DSL.val(value, type));
    }
    catch (SQLException e)
    {
      throw Utils.translate(null, e);
    }
  }
  
  public final BindContext bindValue(Object value, Field<?> field)
    throws DataAccessException
  {
    try
    {
      return bindValue0(value, field);
    }
    catch (SQLException e)
    {
      throw Utils.translate(null, e);
    }
  }
  
  public final String peekAlias()
  {
    return null;
  }
  
  public final String nextAlias()
  {
    return null;
  }
  
  public final String render()
  {
    return null;
  }
  
  public final String render(QueryPart part)
  {
    return null;
  }
  
  public final BindContext keyword(String keyword)
  {
    return this;
  }
  
  public final BindContext sql(String sql)
  {
    return this;
  }
  
  public final BindContext sql(String sql, boolean literal)
  {
    return this;
  }
  
  public final BindContext sql(char sql)
  {
    return this;
  }
  
  public final BindContext sql(int sql)
  {
    return this;
  }
  
  public final BindContext format(boolean format)
  {
    return this;
  }
  
  public final boolean format()
  {
    return false;
  }
  
  public final BindContext formatNewLine()
  {
    return this;
  }
  
  public final BindContext formatNewLineAfterPrintMargin()
  {
    return this;
  }
  
  public final BindContext formatSeparator()
  {
    return this;
  }
  
  public final BindContext formatIndentStart()
  {
    return this;
  }
  
  public final BindContext formatIndentStart(int indent)
  {
    return this;
  }
  
  public final BindContext formatIndentLockStart()
  {
    return this;
  }
  
  public final BindContext formatIndentEnd()
  {
    return this;
  }
  
  public final BindContext formatIndentEnd(int indent)
  {
    return this;
  }
  
  public final BindContext formatIndentLockEnd()
  {
    return this;
  }
  
  public final BindContext formatPrintMargin(int margin)
  {
    return this;
  }
  
  public final BindContext literal(String literal)
  {
    return this;
  }
  
  protected void bindInternal(QueryPartInternal internal)
  {
    internal.accept(this);
  }
  
  protected BindContext bindValue0(Object value, Field<?> field)
    throws SQLException
  {
    return this;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    toString(sb);
    return sb.toString();
  }
}

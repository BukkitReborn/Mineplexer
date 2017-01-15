package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jooq.BindContext;
import org.jooq.Binding;
import org.jooq.Configuration;
import org.jooq.Field;

class DefaultBindContext
  extends AbstractBindContext
{
  DefaultBindContext(Configuration configuration, PreparedStatement stmt)
  {
    super(configuration, stmt);
  }
  
  protected final BindContext bindValue0(Object value, Field<?> field)
    throws SQLException
  {
    field.getBinding().set(new DefaultBindingSetStatementContext(
      configuration(), this.stmt, nextIndex(), value));
    
    return this;
  }
}

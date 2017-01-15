package org.jooq.impl;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.JooqLogger;

class MetaDataFieldProvider
  implements Serializable
{
  private static final long serialVersionUID = -8482521025536063609L;
  private static final JooqLogger log = JooqLogger.getLogger(MetaDataFieldProvider.class);
  private final Fields<Record> fields;
  
  MetaDataFieldProvider(Configuration configuration, ResultSetMetaData meta)
  {
    this.fields = init(configuration, meta);
  }
  
  private Fields<Record> init(Configuration configuration, ResultSetMetaData meta)
  {
    List<Field<?>> fieldList = new ArrayList();
    int columnCount = 0;
    try
    {
      columnCount = meta.getColumnCount();
    }
    catch (SQLException e)
    {
      log.info("Cannot fetch column count for cursor : " + e.getMessage());
      fieldList.add(DSL.field("dummy"));
    }
    try
    {
      for (int i = 1; i <= columnCount; i++)
      {
        String name = meta.getColumnLabel(i);
        int precision = meta.getPrecision(i);
        int scale = meta.getScale(i);
        DataType<?> dataType = SQLDataType.OTHER;
        String type = meta.getColumnTypeName(i);
        try
        {
          dataType = DefaultDataType.getDataType(configuration.dialect().family(), type, precision, scale);
          if (dataType.hasPrecision()) {
            dataType = dataType.precision(precision);
          }
          if (dataType.hasScale()) {
            dataType = dataType.scale(scale);
          }
          if (dataType.hasLength()) {
            dataType = dataType.length(precision);
          }
        }
        catch (SQLDialectNotSupportedException ignore)
        {
          log.debug("Not supported by dialect", ignore.getMessage());
        }
        fieldList.add(DSL.field(name, dataType));
      }
    }
    catch (SQLException e)
    {
      throw Utils.translate(null, e);
    }
    return new Fields(fieldList);
  }
  
  final Field<?>[] getFields()
  {
    return this.fields.fields();
  }
  
  public String toString()
  {
    return this.fields.toString();
  }
}

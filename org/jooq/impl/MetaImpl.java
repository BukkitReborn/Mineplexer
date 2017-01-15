package org.jooq.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Meta;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.JooqLogger;

class MetaImpl
  implements Meta, Serializable
{
  private static final long serialVersionUID = 3582980783173033809L;
  private static final JooqLogger log = JooqLogger.getLogger(MetaImpl.class);
  private final DSLContext create;
  private final Configuration configuration;
  private volatile transient DatabaseMetaData meta;
  
  MetaImpl(Configuration configuration)
  {
    this.create = DSL.using(configuration);
    this.configuration = configuration;
  }
  
  private final DatabaseMetaData meta()
  {
    if (this.meta == null)
    {
      ConnectionProvider provider = this.configuration.connectionProvider();
      Connection connection = null;
      try
      {
        connection = provider.acquire();
        this.meta = connection.getMetaData();
      }
      catch (SQLException e)
      {
        throw new DataAccessException("Error while accessing DatabaseMetaData", e);
      }
      finally
      {
        if (connection != null) {
          provider.release(connection);
        }
      }
    }
    return this.meta;
  }
  
  public final List<Catalog> getCatalogs()
  {
    try
    {
      List<Catalog> result = new ArrayList();
      if (!Arrays.asList(new SQLDialect[] { SQLDialect.MYSQL, SQLDialect.MARIADB }).contains(this.configuration.dialect().family()))
      {
        Result<Record> catalogs = this.create.fetch(
          meta().getCatalogs(), new DataType[] { SQLDataType.VARCHAR });
        for (String name : catalogs.getValues(0, String.class)) {
          result.add(new MetaCatalog(name));
        }
      }
      if (result.isEmpty()) {
        result.add(new MetaCatalog(""));
      }
      return result;
    }
    catch (SQLException e)
    {
      throw new DataAccessException("Error while accessing DatabaseMetaData", e);
    }
  }
  
  public final List<Schema> getSchemas()
  {
    List<Schema> result = new ArrayList();
    for (Catalog catalog : getCatalogs()) {
      result.addAll(catalog.getSchemas());
    }
    return result;
  }
  
  public final List<Table<?>> getTables()
  {
    List<Table<?>> result = new ArrayList();
    for (Schema schema : getSchemas()) {
      result.addAll(schema.getTables());
    }
    return result;
  }
  
  public final List<UniqueKey<?>> getPrimaryKeys()
  {
    List<UniqueKey<?>> result = new ArrayList();
    for (Table<?> table : getTables())
    {
      UniqueKey<?> pk = table.getPrimaryKey();
      if (pk != null) {
        result.add(pk);
      }
    }
    return result;
  }
  
  private class MetaCatalog
    extends CatalogImpl
  {
    private static final long serialVersionUID = -2821093577201327275L;
    
    MetaCatalog(String name)
    {
      super();
    }
    
    public final List<Schema> getSchemas()
    {
      try
      {
        List<Schema> result = new ArrayList();
        if (!Arrays.asList(new SQLDialect[] { SQLDialect.MYSQL, SQLDialect.MARIADB }).contains(MetaImpl.this.configuration.dialect().family()))
        {
          Result<Record> schemas = MetaImpl.this.create.fetch(MetaImpl.this
            .meta().getSchemas(), new DataType[] { SQLDataType.VARCHAR });
          for (String name : schemas.getValues(0, String.class)) {
            result.add(new MetaImpl.MetaSchema(MetaImpl.this, name));
          }
        }
        else
        {
          Result<Record> schemas = MetaImpl.this.create.fetch(MetaImpl.this
            .meta().getCatalogs(), new DataType[] { SQLDataType.VARCHAR });
          for (String name : schemas.getValues(0, String.class)) {
            result.add(new MetaImpl.MetaSchema(MetaImpl.this, name));
          }
        }
        if (result.isEmpty()) {
          result.add(new MetaImpl.MetaSchema(MetaImpl.this, ""));
        }
        return result;
      }
      catch (SQLException e)
      {
        throw new DataAccessException("Error while accessing DatabaseMetaData", e);
      }
    }
  }
  
  private class MetaSchema
    extends SchemaImpl
  {
    private static final long serialVersionUID = -2621899850912554198L;
    private volatile transient Map<Name, Result<Record>> columnCache;
    
    MetaSchema(String name)
    {
      super();
    }
    
    public final synchronized List<Table<?>> getTables()
    {
      try
      {
        String[] types = null;
        switch (MetaImpl.1.$SwitchMap$org$jooq$SQLDialect[MetaImpl.this.configuration.dialect().family().ordinal()])
        {
        case 1: 
          types = new String[] { "TABLE", "VIEW", "SYSTEM_TABLE", "SYSTEM_VIEW", "MATERIALIZED VIEW" };
          break;
        case 2: 
          types = new String[] { "TABLE", "VIEW" };
        }
        ResultSet rs;
        ResultSet rs;
        if (!Arrays.asList(new SQLDialect[] { SQLDialect.MYSQL, SQLDialect.MARIADB }).contains(MetaImpl.this.configuration.dialect().family())) {
          rs = MetaImpl.this.meta().getTables(null, getName(), "%", types);
        } else {
          rs = MetaImpl.this.meta().getTables(getName(), null, "%", types);
        }
        List<Table<?>> result = new ArrayList();
        Result<Record> tables = MetaImpl.this.create.fetch(rs, new DataType[] { SQLDataType.VARCHAR, SQLDataType.VARCHAR, SQLDataType.VARCHAR, SQLDataType.VARCHAR });
        for (Record table : tables)
        {
          String schema = (String)table.getValue(1, String.class);
          String name = (String)table.getValue(2, String.class);
          
          result.add(new MetaImpl.MetaTable(MetaImpl.this, name, this, getColumns(schema, name)));
        }
        return result;
      }
      catch (SQLException e)
      {
        throw new DataAccessException("Error while accessing DatabaseMetaData", e);
      }
    }
    
    private final Result<Record> getColumns(String schema, String table)
      throws SQLException
    {
      Field<String> tableSchem;
      Field<String> tableName;
      if ((this.columnCache == null) && (MetaImpl.this.configuration.dialect() != SQLDialect.SQLITE))
      {
        Result<Record> columns = getColumns0(schema, "%");
        
        tableSchem = columns.field(1);
        tableName = columns.field(2);
        
        Map<Record, Result<Record>> groups = columns.intoGroups(new Field[] { tableSchem, tableName });
        
        this.columnCache = new LinkedHashMap();
        for (Map.Entry<Record, Result<Record>> entry : groups.entrySet())
        {
          Record key = (Record)entry.getKey();
          Result<Record> value = (Result)entry.getValue();
          this.columnCache.put(DSL.name(new String[] { (String)key.getValue(tableSchem), (String)key.getValue(tableName) }), value);
        }
      }
      if (this.columnCache != null) {
        return (Result)this.columnCache.get(DSL.name(new String[] { schema, table }));
      }
      return getColumns0(schema, table);
    }
    
    private final Result<Record> getColumns0(String schema, String table)
      throws SQLException
    {
      ResultSet rs;
      ResultSet rs;
      if (!Arrays.asList(new SQLDialect[] { SQLDialect.MYSQL, SQLDialect.MARIADB }).contains(MetaImpl.this.configuration.dialect().family())) {
        rs = MetaImpl.this.meta().getColumns(null, schema, table, "%");
      } else {
        rs = MetaImpl.this.meta().getColumns(schema, null, table, "%");
      }
      return MetaImpl.this.create.fetch(rs, new Class[] { String.class, String.class, String.class, String.class, Integer.TYPE, String.class, Integer.TYPE, String.class, Integer.TYPE });
    }
  }
  
  private class MetaTable
    extends TableImpl<Record>
  {
    private static final long serialVersionUID = 4843841667753000233L;
    
    MetaTable(Schema name, Result<Record> schema)
    {
      super(schema);
      if (columns != null) {
        init(columns);
      }
    }
    
    public final List<UniqueKey<Record>> getKeys()
    {
      return Collections.unmodifiableList(Arrays.asList(new UniqueKey[] { getPrimaryKey() }));
    }
    
    public final UniqueKey<Record> getPrimaryKey()
    {
      SQLDialect family = MetaImpl.this.configuration.dialect().family();
      
      String schema = getSchema() == null ? null : getSchema().getName();
      try
      {
        ResultSet rs;
        ResultSet rs;
        if (!Arrays.asList(new SQLDialect[] { SQLDialect.MYSQL, SQLDialect.MARIADB }).contains(family)) {
          rs = MetaImpl.this.meta().getPrimaryKeys(null, schema, getName());
        } else {
          rs = MetaImpl.this.meta().getPrimaryKeys(schema, null, getName());
        }
        Result<Record> result = MetaImpl.this.create.fetch(rs, new Class[] { String.class, String.class, String.class, String.class, Integer.TYPE, String.class });
        
        result.sortAsc(4);
        return createPrimaryKey(result, 3);
      }
      catch (SQLException e)
      {
        throw new DataAccessException("Error while accessing DatabaseMetaData", e);
      }
    }
    
    private final UniqueKey<Record> createPrimaryKey(Result<Record> result, int columnName)
    {
      if (result.size() > 0)
      {
        TableField<Record, ?>[] fields = new TableField[result.size()];
        for (int i = 0; i < fields.length; i++) {
          fields[i] = ((TableField)field((String)((Record)result.get(i)).getValue(columnName, String.class)));
        }
        return AbstractKeys.createUniqueKey(this, fields);
      }
      return null;
    }
    
    private final void init(Result<Record> columns)
    {
      for (Record column : columns)
      {
        String columnName = (String)column.getValue(3, String.class);
        String typeName = (String)column.getValue(5, String.class);
        int precision = ((Integer)column.getValue(6, Integer.TYPE)).intValue();
        int scale = ((Integer)column.getValue(8, Integer.TYPE)).intValue();
        
        DataType<?> type = null;
        try
        {
          type = DefaultDataType.getDataType(MetaImpl.this.configuration.dialect(), typeName, precision, scale);
          
          type = type.precision(precision, scale);
          type = type.length(precision);
        }
        catch (SQLDialectNotSupportedException e)
        {
          type = SQLDataType.OTHER;
        }
        createField(columnName, type, this);
      }
    }
  }
}

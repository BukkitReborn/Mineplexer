package org.jooq;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.JAXB;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

@Deprecated
public class SchemaMapping
  implements Serializable
{
  private static final long serialVersionUID = 8269660159338710470L;
  private static final JooqLogger log = JooqLogger.getLogger(SchemaMapping.class);
  private static volatile boolean loggedDeprecation = false;
  private final Configuration configuration;
  private volatile transient Map<String, Schema> schemata;
  private volatile transient Map<String, Table<?>> tables;
  
  public SchemaMapping(Configuration configuration)
  {
    this.configuration = configuration;
  }
  
  private final RenderMapping mapping()
  {
    return SettingsTools.getRenderMapping(this.configuration.settings());
  }
  
  private final boolean renderSchema()
  {
    return Boolean.TRUE.equals(this.configuration.settings().isRenderSchema());
  }
  
  private static void logDeprecation()
  {
    if (!loggedDeprecation)
    {
      loggedDeprecation = true;
      log.warn("DEPRECATION", "org.jooq.SchemaMapping is deprecated as of jOOQ 2.0.5. Consider using jOOQ's runtime configuration org.jooq.conf.Settings instead");
    }
  }
  
  public void use(Schema schema)
  {
    use(schema.getName());
  }
  
  public void use(String schemaName)
  {
    logDeprecation();
    
    mapping().setDefaultSchema(schemaName);
  }
  
  public void add(String inputSchema, String outputSchema)
  {
    logDeprecation();
    
    MappedSchema schema = null;
    for (MappedSchema s : mapping().getSchemata()) {
      if (inputSchema.equals(s.getInput()))
      {
        schema = s;
        break;
      }
    }
    if (schema == null)
    {
      schema = new MappedSchema().withInput(inputSchema);
      mapping().getSchemata().add(schema);
    }
    schema.setOutput(outputSchema);
  }
  
  public void add(String inputSchema, Schema outputSchema)
  {
    add(inputSchema, outputSchema.getName());
  }
  
  public void add(Schema inputSchema, Schema outputSchema)
  {
    add(inputSchema.getName(), outputSchema.getName());
  }
  
  public void add(Schema inputSchema, String outputSchema)
  {
    add(inputSchema.getName(), outputSchema);
  }
  
  public void add(Table<?> inputTable, Table<?> outputTable)
  {
    add(inputTable, outputTable.getName());
  }
  
  public void add(Table<?> inputTable, String outputTable)
  {
    logDeprecation();
    
    MappedSchema schema = null;
    MappedTable table = null;
    for (MappedSchema s : mapping().getSchemata()) {
      if (inputTable.getSchema().getName().equals(s.getInput()))
      {
        for (MappedTable t : s.getTables()) {
          if (inputTable.getName().equals(t.getInput()))
          {
            table = t;
            break;
          }
        }
        schema = s;
        break;
      }
    }
    if (schema == null)
    {
      schema = new MappedSchema().withInput(inputTable.getSchema().getName());
      mapping().getSchemata().add(schema);
    }
    if (table == null)
    {
      table = new MappedTable().withInput(inputTable.getName());
      schema.getTables().add(table);
    }
    table.setOutput(outputTable);
  }
  
  public Schema map(Schema schema)
  {
    if (!renderSchema()) {
      return null;
    }
    Schema result = null;
    if (schema != null)
    {
      String schemaName = schema.getName();
      if (!StringUtils.isEmpty(schemaName))
      {
        if (!getSchemata().containsKey(schemaName)) {
          synchronized (this)
          {
            if (!getSchemata().containsKey(schemaName))
            {
              Schema mapped = schema;
              for (MappedSchema s : mapping().getSchemata()) {
                if (schemaName.equals(s.getInput()))
                {
                  if ((StringUtils.isBlank(s.getOutput())) || (s.getOutput().equals(s.getInput()))) {
                    break;
                  }
                  mapped = new RenamedSchema(schema, s.getOutput()); break;
                }
              }
              getSchemata().put(schemaName, mapped);
            }
          }
        }
        result = (Schema)getSchemata().get(schemaName);
        if (result.getName().equals(mapping().getDefaultSchema())) {
          result = null;
        }
      }
    }
    return result;
  }
  
  public <R extends Record> Table<R> map(Table<R> table)
  {
    Table<R> result = null;
    if (table != null)
    {
      Schema schema = table.getSchema();
      
      String schemaName = schema == null ? "" : schema.getName();
      String tableName = table.getName();
      String key = schemaName + "." + tableName;
      if (!getTables().containsKey(key)) {
        synchronized (this)
        {
          if (!getTables().containsKey(key))
          {
            Table<R> mapped = table;
            for (MappedSchema s : mapping().getSchemata()) {
              if (schemaName.equals(s.getInput())) {
                for (MappedTable t : s.getTables()) {
                  if (tableName.equals(t.getInput()))
                  {
                    if ((StringUtils.isBlank(t.getOutput())) || (t.getOutput().equals(t.getInput()))) {
                      break label265;
                    }
                    mapped = new RenamedTable(table, t.getOutput());
                    break label265;
                  }
                }
              }
            }
            label265:
            getTables().put(key, mapped);
          }
        }
      }
      result = (Table)getTables().get(key);
    }
    return result;
  }
  
  public void setDefaultSchema(String schema)
  {
    use(schema);
  }
  
  public void setSchemaMapping(Map<String, String> schemaMap)
  {
    for (Map.Entry<String, String> entry : schemaMap.entrySet()) {
      add((String)entry.getKey(), (String)entry.getValue());
    }
  }
  
  private final Map<String, Schema> getSchemata()
  {
    if (this.schemata == null) {
      synchronized (this)
      {
        if (this.schemata == null) {
          this.schemata = new HashMap();
        }
      }
    }
    return this.schemata;
  }
  
  private final Map<String, Table<?>> getTables()
  {
    if (this.tables == null) {
      synchronized (this)
      {
        if (this.tables == null) {
          this.tables = new HashMap();
        }
      }
    }
    return this.tables;
  }
  
  public String toString()
  {
    StringWriter writer = new StringWriter();
    JAXB.marshal(mapping(), writer);
    return writer.toString();
  }
}

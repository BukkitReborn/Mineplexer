package org.jooq.conf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="MappedSchema", propOrder={})
public class MappedSchema
  extends SettingsBase
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 350L;
  @XmlElement(required=true)
  protected String input;
  protected String output;
  @XmlElementWrapper(name="tables")
  @XmlElement(name="table")
  protected List<MappedTable> tables;
  
  public String getInput()
  {
    return this.input;
  }
  
  public void setInput(String value)
  {
    this.input = value;
  }
  
  public String getOutput()
  {
    return this.output;
  }
  
  public void setOutput(String value)
  {
    this.output = value;
  }
  
  public List<MappedTable> getTables()
  {
    if (this.tables == null) {
      this.tables = new ArrayList();
    }
    return this.tables;
  }
  
  public void setTables(List<MappedTable> tables)
  {
    this.tables = tables;
  }
  
  public MappedSchema withInput(String value)
  {
    setInput(value);
    return this;
  }
  
  public MappedSchema withOutput(String value)
  {
    setOutput(value);
    return this;
  }
  
  public MappedSchema withTables(MappedTable... values)
  {
    if (values != null) {
      for (MappedTable value : values) {
        getTables().add(value);
      }
    }
    return this;
  }
  
  public MappedSchema withTables(Collection<MappedTable> values)
  {
    if (values != null) {
      getTables().addAll(values);
    }
    return this;
  }
  
  public MappedSchema withTables(List<MappedTable> tables)
  {
    setTables(tables);
    return this;
  }
}

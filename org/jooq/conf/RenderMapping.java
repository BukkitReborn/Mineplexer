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
@XmlType(name="RenderMapping", propOrder={})
public class RenderMapping
  extends SettingsBase
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 350L;
  protected String defaultSchema;
  @XmlElementWrapper(name="schemata")
  @XmlElement(name="schema")
  protected List<MappedSchema> schemata;
  
  public String getDefaultSchema()
  {
    return this.defaultSchema;
  }
  
  public void setDefaultSchema(String value)
  {
    this.defaultSchema = value;
  }
  
  public List<MappedSchema> getSchemata()
  {
    if (this.schemata == null) {
      this.schemata = new ArrayList();
    }
    return this.schemata;
  }
  
  public void setSchemata(List<MappedSchema> schemata)
  {
    this.schemata = schemata;
  }
  
  public RenderMapping withDefaultSchema(String value)
  {
    setDefaultSchema(value);
    return this;
  }
  
  public RenderMapping withSchemata(MappedSchema... values)
  {
    if (values != null) {
      for (MappedSchema value : values) {
        getSchemata().add(value);
      }
    }
    return this;
  }
  
  public RenderMapping withSchemata(Collection<MappedSchema> values)
  {
    if (values != null) {
      getSchemata().addAll(values);
    }
    return this;
  }
  
  public RenderMapping withSchemata(List<MappedSchema> schemata)
  {
    setSchemata(schemata);
    return this;
  }
}

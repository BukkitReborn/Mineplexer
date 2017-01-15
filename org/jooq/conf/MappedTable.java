package org.jooq.conf;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="MappedTable", propOrder={})
public class MappedTable
  extends SettingsBase
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 350L;
  @XmlElement(required=true)
  protected String input;
  @XmlElement(required=true)
  protected String output;
  
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
  
  public MappedTable withInput(String value)
  {
    setInput(value);
    return this;
  }
  
  public MappedTable withOutput(String value)
  {
    setOutput(value);
    return this;
  }
}

package org.jooq.conf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RenderKeywordStyle")
@XmlEnum
public enum RenderKeywordStyle
{
  LOWER,  UPPER;
  
  private RenderKeywordStyle() {}
  
  public String value()
  {
    return name();
  }
  
  public static RenderKeywordStyle fromValue(String v)
  {
    return valueOf(v);
  }
}

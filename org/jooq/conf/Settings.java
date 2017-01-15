package org.jooq.conf;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Settings", propOrder={})
public class Settings
  extends SettingsBase
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 350L;
  @XmlElement(defaultValue="true")
  protected Boolean renderSchema;
  protected RenderMapping renderMapping;
  @XmlElement(defaultValue="QUOTED")
  protected RenderNameStyle renderNameStyle;
  @XmlElement(defaultValue="LOWER")
  protected RenderKeywordStyle renderKeywordStyle;
  @XmlElement(defaultValue="false")
  protected Boolean renderFormatted;
  @XmlElement(defaultValue="false")
  protected Boolean renderScalarSubqueriesForStoredFunctions;
  @XmlElement(defaultValue="DEFAULT")
  protected BackslashEscaping backslashEscaping;
  @XmlElement(defaultValue="INDEXED")
  protected ParamType paramType;
  @XmlElement(defaultValue="PREPARED_STATEMENT")
  protected StatementType statementType;
  @XmlElement(defaultValue="true")
  protected Boolean executeLogging;
  @XmlElement(defaultValue="false")
  protected Boolean executeWithOptimisticLocking;
  @XmlElement(defaultValue="true")
  protected Boolean attachRecords;
  @XmlElement(defaultValue="false")
  protected Boolean updatablePrimaryKeys;
  @XmlElement(defaultValue="true")
  protected Boolean reflectionCaching;
  
  public Settings()
  {
    this.renderSchema = Boolean.valueOf(true);
    
    this.renderNameStyle = RenderNameStyle.QUOTED;
    
    this.renderKeywordStyle = RenderKeywordStyle.LOWER;
    
    this.renderFormatted = Boolean.valueOf(false);
    
    this.renderScalarSubqueriesForStoredFunctions = Boolean.valueOf(false);
    this.backslashEscaping = BackslashEscaping.DEFAULT;
    
    this.paramType = ParamType.INDEXED;
    
    this.statementType = StatementType.PREPARED_STATEMENT;
    
    this.executeLogging = Boolean.valueOf(true);
    
    this.executeWithOptimisticLocking = Boolean.valueOf(false);
    
    this.attachRecords = Boolean.valueOf(true);
    
    this.updatablePrimaryKeys = Boolean.valueOf(false);
    
    this.reflectionCaching = Boolean.valueOf(true);
  }
  
  @XmlElement(defaultValue="true")
  protected Boolean fetchWarnings = Boolean.valueOf(true);
  
  public Boolean isRenderSchema()
  {
    return this.renderSchema;
  }
  
  public void setRenderSchema(Boolean value)
  {
    this.renderSchema = value;
  }
  
  public RenderMapping getRenderMapping()
  {
    return this.renderMapping;
  }
  
  public void setRenderMapping(RenderMapping value)
  {
    this.renderMapping = value;
  }
  
  public RenderNameStyle getRenderNameStyle()
  {
    return this.renderNameStyle;
  }
  
  public void setRenderNameStyle(RenderNameStyle value)
  {
    this.renderNameStyle = value;
  }
  
  public RenderKeywordStyle getRenderKeywordStyle()
  {
    return this.renderKeywordStyle;
  }
  
  public void setRenderKeywordStyle(RenderKeywordStyle value)
  {
    this.renderKeywordStyle = value;
  }
  
  public Boolean isRenderFormatted()
  {
    return this.renderFormatted;
  }
  
  public void setRenderFormatted(Boolean value)
  {
    this.renderFormatted = value;
  }
  
  public Boolean isRenderScalarSubqueriesForStoredFunctions()
  {
    return this.renderScalarSubqueriesForStoredFunctions;
  }
  
  public void setRenderScalarSubqueriesForStoredFunctions(Boolean value)
  {
    this.renderScalarSubqueriesForStoredFunctions = value;
  }
  
  public BackslashEscaping getBackslashEscaping()
  {
    return this.backslashEscaping;
  }
  
  public void setBackslashEscaping(BackslashEscaping value)
  {
    this.backslashEscaping = value;
  }
  
  public ParamType getParamType()
  {
    return this.paramType;
  }
  
  public void setParamType(ParamType value)
  {
    this.paramType = value;
  }
  
  public StatementType getStatementType()
  {
    return this.statementType;
  }
  
  public void setStatementType(StatementType value)
  {
    this.statementType = value;
  }
  
  public Boolean isExecuteLogging()
  {
    return this.executeLogging;
  }
  
  public void setExecuteLogging(Boolean value)
  {
    this.executeLogging = value;
  }
  
  public Boolean isExecuteWithOptimisticLocking()
  {
    return this.executeWithOptimisticLocking;
  }
  
  public void setExecuteWithOptimisticLocking(Boolean value)
  {
    this.executeWithOptimisticLocking = value;
  }
  
  public Boolean isAttachRecords()
  {
    return this.attachRecords;
  }
  
  public void setAttachRecords(Boolean value)
  {
    this.attachRecords = value;
  }
  
  public Boolean isUpdatablePrimaryKeys()
  {
    return this.updatablePrimaryKeys;
  }
  
  public void setUpdatablePrimaryKeys(Boolean value)
  {
    this.updatablePrimaryKeys = value;
  }
  
  public Boolean isReflectionCaching()
  {
    return this.reflectionCaching;
  }
  
  public void setReflectionCaching(Boolean value)
  {
    this.reflectionCaching = value;
  }
  
  public Boolean isFetchWarnings()
  {
    return this.fetchWarnings;
  }
  
  public void setFetchWarnings(Boolean value)
  {
    this.fetchWarnings = value;
  }
  
  public Settings withRenderSchema(Boolean value)
  {
    setRenderSchema(value);
    return this;
  }
  
  public Settings withRenderMapping(RenderMapping value)
  {
    setRenderMapping(value);
    return this;
  }
  
  public Settings withRenderNameStyle(RenderNameStyle value)
  {
    setRenderNameStyle(value);
    return this;
  }
  
  public Settings withRenderKeywordStyle(RenderKeywordStyle value)
  {
    setRenderKeywordStyle(value);
    return this;
  }
  
  public Settings withRenderFormatted(Boolean value)
  {
    setRenderFormatted(value);
    return this;
  }
  
  public Settings withRenderScalarSubqueriesForStoredFunctions(Boolean value)
  {
    setRenderScalarSubqueriesForStoredFunctions(value);
    return this;
  }
  
  public Settings withBackslashEscaping(BackslashEscaping value)
  {
    setBackslashEscaping(value);
    return this;
  }
  
  public Settings withParamType(ParamType value)
  {
    setParamType(value);
    return this;
  }
  
  public Settings withStatementType(StatementType value)
  {
    setStatementType(value);
    return this;
  }
  
  public Settings withExecuteLogging(Boolean value)
  {
    setExecuteLogging(value);
    return this;
  }
  
  public Settings withExecuteWithOptimisticLocking(Boolean value)
  {
    setExecuteWithOptimisticLocking(value);
    return this;
  }
  
  public Settings withAttachRecords(Boolean value)
  {
    setAttachRecords(value);
    return this;
  }
  
  public Settings withUpdatablePrimaryKeys(Boolean value)
  {
    setUpdatablePrimaryKeys(value);
    return this;
  }
  
  public Settings withReflectionCaching(Boolean value)
  {
    setReflectionCaching(value);
    return this;
  }
  
  public Settings withFetchWarnings(Boolean value)
  {
    setFetchWarnings(value);
    return this;
  }
}

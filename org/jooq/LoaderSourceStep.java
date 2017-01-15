package org.jooq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import org.xml.sax.InputSource;

public abstract interface LoaderSourceStep<R extends TableRecord<R>>
{
  @Support
  public abstract LoaderCSVStep<R> loadCSV(File paramFile)
    throws FileNotFoundException;
  
  @Support
  public abstract LoaderCSVStep<R> loadCSV(String paramString);
  
  @Support
  public abstract LoaderCSVStep<R> loadCSV(InputStream paramInputStream);
  
  @Support
  public abstract LoaderCSVStep<R> loadCSV(Reader paramReader);
  
  @Support
  public abstract LoaderXMLStep<R> loadXML(File paramFile)
    throws FileNotFoundException;
  
  @Support
  public abstract LoaderXMLStep<R> loadXML(String paramString);
  
  @Support
  public abstract LoaderXMLStep<R> loadXML(InputStream paramInputStream);
  
  @Support
  public abstract LoaderXMLStep<R> loadXML(Reader paramReader);
  
  @Support
  public abstract LoaderXMLStep<R> loadXML(InputSource paramInputSource);
  
  @Support
  public abstract LoaderJSONStep<R> loadJSON(File paramFile)
    throws FileNotFoundException;
  
  @Support
  public abstract LoaderJSONStep<R> loadJSON(String paramString);
  
  @Support
  public abstract LoaderJSONStep<R> loadJSON(InputStream paramInputStream);
  
  @Support
  public abstract LoaderJSONStep<R> loadJSON(Reader paramReader);
}

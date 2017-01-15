package org.jooq.impl;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import org.jooq.tools.json.ContainerFactory;
import org.jooq.tools.json.JSONParser;
import org.jooq.tools.json.ParseException;

class JSONReader
  implements Closeable
{
  private final BufferedReader br;
  private final JSONParser parser;
  private String[] fieldMetaData;
  private List<String[]> records;
  
  public JSONReader(Reader reader)
  {
    this.br = new BufferedReader(reader);
    this.parser = new JSONParser();
  }
  
  public List<String[]> readAll()
    throws IOException
  {
    if (this.records != null) {
      return this.records;
    }
    try
    {
      LinkedHashMap jsonRoot = getJsonRoot();
      readFields(jsonRoot);
      this.records = readRecords(jsonRoot);
    }
    catch (ParseException ex)
    {
      throw new RuntimeException(ex);
    }
    return this.records;
  }
  
  public String[] getFields()
    throws IOException
  {
    if (this.fieldMetaData == null) {
      readAll();
    }
    return this.fieldMetaData;
  }
  
  public void close()
    throws IOException
  {
    this.br.close();
  }
  
  private List<String[]> readRecords(LinkedHashMap jsonRoot)
  {
    LinkedList jsonRecords = (LinkedList)jsonRoot.get("records");
    this.records = new ArrayList();
    for (Object record : jsonRecords)
    {
      LinkedList values = (LinkedList)record;
      List<String> v = new ArrayList();
      for (Object value : values)
      {
        String asString = value == null ? null : String.valueOf(value);
        v.add(asString);
      }
      this.records.add(v.toArray(new String[v.size()]));
    }
    return this.records;
  }
  
  private LinkedHashMap getJsonRoot()
    throws IOException, ParseException
  {
    Object parse = this.parser.parse(this.br, new ContainerFactory()
    {
      public LinkedHashMap createObjectContainer()
      {
        return new LinkedHashMap();
      }
      
      public List createArrayContainer()
      {
        return new LinkedList();
      }
    });
    return (LinkedHashMap)parse;
  }
  
  private void readFields(LinkedHashMap jsonRoot)
  {
    if (this.fieldMetaData != null) {
      return;
    }
    LinkedList fieldEntries = (LinkedList)jsonRoot.get("fields");
    this.fieldMetaData = new String[fieldEntries.size()];
    int i = 0;
    for (Object key : fieldEntries)
    {
      this.fieldMetaData[i] = ((String)((LinkedHashMap)key).get("name"));
      i++;
    }
  }
}

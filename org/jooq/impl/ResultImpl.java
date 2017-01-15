package org.jooq.impl;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.InsertValuesStepN;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.exception.InvalidResultException;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.MockResultSet;
import org.jooq.tools.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

class ResultImpl<R extends Record>
  implements Result<R>, AttachableInternal
{
  private static final long serialVersionUID = 6416154375799578362L;
  private Configuration configuration;
  private final Fields<R> fields;
  private final List<R> records;
  
  ResultImpl(Configuration configuration, Collection<? extends Field<?>> fields)
  {
    this(configuration, new Fields(fields));
  }
  
  ResultImpl(Configuration configuration, Field<?>... fields)
  {
    this(configuration, new Fields(fields));
  }
  
  ResultImpl(Configuration configuration, Fields<R> fields)
  {
    this.configuration = configuration;
    this.fields = fields;
    this.records = new ArrayList();
  }
  
  public final void attach(Configuration c)
  {
    this.configuration = c;
    for (R record : this.records) {
      if (record != null) {
        record.attach(c);
      }
    }
  }
  
  public final void detach()
  {
    attach(null);
  }
  
  public final Configuration configuration()
  {
    return this.configuration;
  }
  
  public final RecordType<R> recordType()
  {
    return this.fields;
  }
  
  public final Row fieldsRow()
  {
    return new RowImpl(this.fields);
  }
  
  public final <T> Field<T> field(Field<T> field)
  {
    return this.fields.field(field);
  }
  
  public final Field<?> field(String name)
  {
    return this.fields.field(name);
  }
  
  public final Field<?> field(int index)
  {
    return this.fields.field(index);
  }
  
  public final Field<?>[] fields()
  {
    return (Field[])this.fields.fields().clone();
  }
  
  public final boolean isEmpty()
  {
    return this.records.isEmpty();
  }
  
  public final boolean isNotEmpty()
  {
    return !this.records.isEmpty();
  }
  
  public final <T> T getValue(int index, Field<T> field)
  {
    return (T)get(index).getValue(field);
  }
  
  @Deprecated
  public final <T> T getValue(int index, Field<T> field, T defaultValue)
  {
    return (T)get(index).getValue(field, defaultValue);
  }
  
  public final Object getValue(int index, int fieldIndex)
  {
    return get(index).getValue(fieldIndex);
  }
  
  @Deprecated
  public final Object getValue(int index, int fieldIndex, Object defaultValue)
  {
    return get(index).getValue(fieldIndex, defaultValue);
  }
  
  public final Object getValue(int index, String fieldName)
  {
    return get(index).getValue(fieldName);
  }
  
  @Deprecated
  public final Object getValue(int index, String fieldName, Object defaultValue)
  {
    return get(index).getValue(fieldName, defaultValue);
  }
  
  public final <T> List<T> getValues(Field<T> field)
  {
    return getValues(Utils.indexOrFail(fieldsRow(), field));
  }
  
  public final <T> List<T> getValues(Field<?> field, Class<? extends T> type)
  {
    return Convert.convert(getValues(field), type);
  }
  
  public final <T, U> List<U> getValues(Field<T> field, Converter<? super T, U> converter)
  {
    return Convert.convert(getValues(field), converter);
  }
  
  public final List<?> getValues(int fieldIndex)
  {
    List<Object> result = new ArrayList(size());
    for (R record : this) {
      result.add(record.getValue(fieldIndex));
    }
    return result;
  }
  
  public final <T> List<T> getValues(int fieldIndex, Class<? extends T> type)
  {
    return Convert.convert(getValues(fieldIndex), type);
  }
  
  public final <U> List<U> getValues(int fieldIndex, Converter<?, U> converter)
  {
    return Convert.convert(getValues(fieldIndex), converter);
  }
  
  public final List<?> getValues(String fieldName)
  {
    return getValues(field(fieldName));
  }
  
  public final <T> List<T> getValues(String fieldName, Class<? extends T> type)
  {
    return Convert.convert(getValues(fieldName), type);
  }
  
  public final <U> List<U> getValues(String fieldName, Converter<?, U> converter)
  {
    return Convert.convert(getValues(fieldName), converter);
  }
  
  final void addRecord(R record)
  {
    this.records.add(record);
  }
  
  public final String format()
  {
    StringWriter writer = new StringWriter();
    format(writer);
    return writer.toString();
  }
  
  public final void format(OutputStream stream)
  {
    format(new OutputStreamWriter(stream));
  }
  
  public final void format(Writer writer)
  {
    format(writer, 50);
  }
  
  public final String format(int maxRecords)
  {
    StringWriter writer = new StringWriter();
    format(writer, maxRecords);
    return writer.toString();
  }
  
  public final void format(OutputStream stream, int maxRecords)
  {
    format(new OutputStreamWriter(stream), maxRecords);
  }
  
  public final void format(Writer writer, int maxRecords)
  {
    try
    {
      int COL_MIN_WIDTH = 4;
      int COL_MAX_WIDTH = 50;
      
      int NUM_COL_MAX_WIDTH = 100;
      
      int MAX_RECORDS = Math.min(50, maxRecords);
      
      int[] decimalPlaces = new int[this.fields.fields.length];
      int[] widths = new int[this.fields.fields.length];
      for (int index = 0; index < this.fields.fields.length; index++) {
        if (Number.class.isAssignableFrom(this.fields.fields[index].getType()))
        {
          List<Integer> decimalPlacesList = new ArrayList();
          
          decimalPlacesList.add(Integer.valueOf(0));
          for (int i = 0; i < Math.min(MAX_RECORDS, size()); i++)
          {
            String value = format0(getValue(i, index), get(i).changed(index));
            decimalPlacesList.add(Integer.valueOf(getDecimalPlaces(value)));
          }
          decimalPlaces[index] = ((Integer)Collections.max(decimalPlacesList)).intValue();
        }
      }
      for (int index = 0; index < this.fields.fields.length; index++)
      {
        boolean isNumCol = Number.class.isAssignableFrom(this.fields.fields[index].getType());
        
        int colMaxWidth = isNumCol ? 100 : 50;
        
        List<Integer> widthList = new ArrayList();
        
        widthList.add(Integer.valueOf(Math.min(colMaxWidth, Math.max(4, this.fields.fields[index].getName().length()))));
        for (int i = 0; i < Math.min(MAX_RECORDS, size()); i++)
        {
          String value = format0(getValue(i, index), get(i).changed(index));
          if (isNumCol) {
            value = alignNumberValue(Integer.valueOf(decimalPlaces[index]), value);
          }
          widthList.add(Integer.valueOf(Math.min(colMaxWidth, value.length())));
        }
        widths[index] = ((Integer)Collections.max(widthList)).intValue();
      }
      writer.append("+");
      for (int index = 0; index < this.fields.fields.length; index++)
      {
        writer.append(StringUtils.rightPad("", widths[index], "-"));
        writer.append("+");
      }
      writer.append("\n|");
      for (int index = 0; index < this.fields.fields.length; index++)
      {
        String padded;
        String padded;
        if (Number.class.isAssignableFrom(this.fields.fields[index].getType())) {
          padded = StringUtils.leftPad(this.fields.fields[index].getName(), widths[index]);
        } else {
          padded = StringUtils.rightPad(this.fields.fields[index].getName(), widths[index]);
        }
        writer.append(StringUtils.abbreviate(padded, widths[index]));
        writer.append("|");
      }
      writer.append("\n+");
      for (int index = 0; index < this.fields.fields.length; index++)
      {
        writer.append(StringUtils.rightPad("", widths[index], "-"));
        writer.append("+");
      }
      for (int i = 0; i < Math.min(maxRecords, size()); i++)
      {
        writer.append("\n|");
        for (int index = 0; index < this.fields.fields.length; index++)
        {
          String value = format0(getValue(i, index), get(i).changed(index)).replace("\n", "{lf}").replace("\r", "{cr}");
          String padded;
          String padded;
          if (Number.class.isAssignableFrom(this.fields.fields[index].getType()))
          {
            value = alignNumberValue(Integer.valueOf(decimalPlaces[index]), value);
            
            padded = StringUtils.leftPad(value, widths[index]);
          }
          else
          {
            padded = StringUtils.rightPad(value, widths[index]);
          }
          writer.append(StringUtils.abbreviate(padded, widths[index]));
          writer.append("|");
        }
      }
      if (size() > 0)
      {
        writer.append("\n+");
        for (int index = 0; index < this.fields.fields.length; index++)
        {
          writer.append(StringUtils.rightPad("", widths[index], "-"));
          writer.append("+");
        }
      }
      if (maxRecords < size())
      {
        writer.append("\n|...");
        writer.append("" + (size() - maxRecords));
        writer.append(" record(s) truncated...");
      }
    }
    catch (java.io.IOException e)
    {
      throw new org.jooq.exception.IOException("Exception while writing TEXT", e);
    }
  }
  
  private static final String alignNumberValue(Integer columnDecimalPlaces, String value)
  {
    if ((!"{null}".equals(value)) && (columnDecimalPlaces.intValue() != 0))
    {
      int decimalPlaces = getDecimalPlaces(value);
      int rightPadSize = value.length() + columnDecimalPlaces.intValue() - decimalPlaces;
      if (decimalPlaces == 0) {
        value = StringUtils.rightPad(value, rightPadSize + 1);
      } else {
        value = StringUtils.rightPad(value, rightPadSize);
      }
    }
    return value;
  }
  
  private static final int getDecimalPlaces(String value)
  {
    int decimalPlaces = 0;
    
    int dotIndex = value.indexOf(".");
    if (dotIndex != -1) {
      decimalPlaces = value.length() - dotIndex - 1;
    }
    return decimalPlaces;
  }
  
  public final String formatHTML()
  {
    StringWriter writer = new StringWriter();
    formatHTML(writer);
    return writer.toString();
  }
  
  public final void formatHTML(OutputStream stream)
  {
    formatHTML(new OutputStreamWriter(stream));
  }
  
  public final void formatHTML(Writer writer)
  {
    try
    {
      writer.append("<table>");
      writer.append("<thead>");
      writer.append("<tr>");
      for (Field<?> field : this.fields.fields)
      {
        writer.append("<th>");
        writer.append(field.getName());
        writer.append("</th>");
      }
      writer.append("</tr>");
      writer.append("</thead>");
      writer.append("<tbody>");
      for (??? = iterator(); ((Iterator)???).hasNext();)
      {
        Record record = (Record)((Iterator)???).next();
        writer.append("<tr>");
        for (int index = 0; index < this.fields.fields.length; index++)
        {
          writer.append("<td>");
          writer.append(format0(record.getValue(index), false));
          writer.append("</td>");
        }
        writer.append("</tr>");
      }
      writer.append("</tbody>");
      writer.append("</table>");
    }
    catch (java.io.IOException e)
    {
      throw new org.jooq.exception.IOException("Exception while writing HTML", e);
    }
  }
  
  public final String formatCSV()
  {
    StringWriter writer = new StringWriter();
    formatCSV(writer);
    return writer.toString();
  }
  
  public final void formatCSV(OutputStream stream)
  {
    formatCSV(new OutputStreamWriter(stream));
  }
  
  public final void formatCSV(Writer writer)
  {
    formatCSV(writer, ',', "");
  }
  
  public final String formatCSV(char delimiter)
  {
    StringWriter writer = new StringWriter();
    formatCSV(writer, delimiter);
    return writer.toString();
  }
  
  public final void formatCSV(OutputStream stream, char delimiter)
  {
    formatCSV(new OutputStreamWriter(stream), delimiter);
  }
  
  public final void formatCSV(Writer writer, char delimiter)
  {
    formatCSV(writer, delimiter, "");
  }
  
  public final String formatCSV(char delimiter, String nullString)
  {
    StringWriter writer = new StringWriter();
    formatCSV(writer, delimiter, nullString);
    return writer.toString();
  }
  
  public final void formatCSV(OutputStream stream, char delimiter, String nullString)
  {
    formatCSV(new OutputStreamWriter(stream), delimiter, nullString);
  }
  
  public final void formatCSV(Writer writer, char delimiter, String nullString)
  {
    try
    {
      String sep1 = "";
      for (Field<?> field : this.fields.fields)
      {
        writer.append(sep1);
        writer.append(formatCSV0(field.getName(), ""));
        
        sep1 = Character.toString(delimiter);
      }
      writer.append("\n");
      for (??? = iterator(); ((Iterator)???).hasNext();)
      {
        Record record = (Record)((Iterator)???).next();
        String sep2 = "";
        for (int index = 0; index < this.fields.fields.length; index++)
        {
          writer.append(sep2);
          writer.append(formatCSV0(record.getValue(index), nullString));
          
          sep2 = Character.toString(delimiter);
        }
        writer.append("\n");
      }
    }
    catch (java.io.IOException e)
    {
      throw new org.jooq.exception.IOException("Exception while writing CSV", e);
    }
  }
  
  private final String formatCSV0(Object value, String nullString)
  {
    if ((value == null) || ("".equals(value)))
    {
      if (StringUtils.isEmpty(nullString)) {
        return "\"\"";
      }
      return nullString;
    }
    String result = format0(value, false);
    if (StringUtils.containsAny(result, new char[] { ',', ';', '\t', '"', '\n', '\r', '\'', '\\' })) {
      return "\"" + result.replace("\\", "\\\\").replace("\"", "\"\"") + "\"";
    }
    return result;
  }
  
  private static final String format0(Object value, boolean changed)
  {
    String formatted = changed ? "*" : "";
    if (value == null) {
      formatted = formatted + "{null}";
    } else if (value.getClass() == byte[].class) {
      formatted = formatted + Arrays.toString((byte[])value);
    } else if (value.getClass().isArray()) {
      formatted = formatted + Arrays.toString((Object[])value);
    } else if ((value instanceof EnumType)) {
      formatted = formatted + ((EnumType)value).getLiteral();
    } else {
      formatted = formatted + value.toString();
    }
    return formatted;
  }
  
  public final String formatJSON()
  {
    StringWriter writer = new StringWriter();
    formatJSON(writer);
    return writer.toString();
  }
  
  public final void formatJSON(OutputStream stream)
  {
    formatJSON(new OutputStreamWriter(stream));
  }
  
  public final void formatJSON(Writer writer)
  {
    try
    {
      List<Map<String, String>> f = new ArrayList();
      List<List<Object>> r = new ArrayList();
      for (Field<?> field : this.fields.fields)
      {
        Map<String, String> fieldMap = new LinkedHashMap();
        fieldMap.put("name", field.getName());
        fieldMap.put("type", field.getDataType().getTypeName().toUpperCase());
        
        f.add(fieldMap);
      }
      for (??? = iterator(); ((Iterator)???).hasNext();)
      {
        Record record = (Record)((Iterator)???).next();
        Object list = new ArrayList();
        for (int index = 0; index < this.fields.fields.length; index++) {
          ((List)list).add(record.getValue(index));
        }
        r.add(list);
      }
      Object map = new LinkedHashMap();
      
      ((Map)map).put("fields", f);
      ((Map)map).put("records", r);
      
      writer.append(JSONObject.toJSONString((Map)map));
    }
    catch (java.io.IOException e)
    {
      throw new org.jooq.exception.IOException("Exception while writing JSON", e);
    }
  }
  
  public final String formatXML()
  {
    StringWriter writer = new StringWriter();
    formatXML(writer);
    return writer.toString();
  }
  
  public final void formatXML(OutputStream stream)
  {
    formatXML(new OutputStreamWriter(stream));
  }
  
  public final void formatXML(Writer writer)
  {
    try
    {
      writer.append("<result xmlns=\"http://www.jooq.org/xsd/jooq-export-2.6.0.xsd\">");
      writer.append("<fields>");
      for (Field<?> field : this.fields.fields)
      {
        writer.append("<field name=\"");
        writer.append(escapeXML(field.getName()));
        writer.append("\" ");
        writer.append("type=\"");
        writer.append(field.getDataType().getTypeName().toUpperCase());
        writer.append("\"/>");
      }
      writer.append("</fields>");
      writer.append("<records>");
      for (??? = iterator(); ((Iterator)???).hasNext();)
      {
        Record record = (Record)((Iterator)???).next();
        writer.append("<record>");
        for (int index = 0; index < this.fields.fields.length; index++)
        {
          Object value = record.getValue(index);
          
          writer.append("<value field=\"");
          writer.append(escapeXML(this.fields.fields[index].getName()));
          writer.append("\"");
          if (value == null)
          {
            writer.append("/>");
          }
          else
          {
            writer.append(">");
            writer.append(escapeXML(format0(value, false)));
            writer.append("</value>");
          }
        }
        writer.append("</record>");
      }
      writer.append("</records>");
      writer.append("</result>");
    }
    catch (java.io.IOException e)
    {
      throw new org.jooq.exception.IOException("Exception while writing XML", e);
    }
  }
  
  public final String formatInsert()
  {
    StringWriter writer = new StringWriter();
    formatInsert(writer);
    return writer.toString();
  }
  
  public final void formatInsert(OutputStream stream)
  {
    formatInsert(new OutputStreamWriter(stream));
  }
  
  public final void formatInsert(Writer writer)
  {
    Table<?> table = null;
    if ((this.records.size() > 0) && ((this.records.get(0) instanceof TableRecord))) {
      table = ((TableRecord)this.records.get(0)).getTable();
    }
    if (table == null) {
      table = DSL.tableByName(new String[] { "UNKNOWN_TABLE" });
    }
    formatInsert(writer, table, fields());
  }
  
  public final String formatInsert(Table<?> table, Field<?>... f)
  {
    StringWriter writer = new StringWriter();
    formatInsert(writer, table, f);
    return writer.toString();
  }
  
  public final void formatInsert(OutputStream stream, Table<?> table, Field<?>... f)
  {
    formatInsert(new OutputStreamWriter(stream), table, f);
  }
  
  public final void formatInsert(Writer writer, Table<?> table, Field<?>... f)
  {
    DSLContext ctx = DSL.using(configuration());
    try
    {
      for (R record : this)
      {
        writer.append(ctx.renderInlined(DSL.insertInto(table, f).values(record.intoArray())));
        writer.append(";\n");
      }
    }
    catch (java.io.IOException e)
    {
      throw new org.jooq.exception.IOException("Exception while writing INSERTs", e);
    }
  }
  
  public final Document intoXML()
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      
      Element eResult = document.createElement("result");
      eResult.setAttribute("xmlns", "http://www.jooq.org/xsd/jooq-export-2.6.0.xsd");
      document.appendChild(eResult);
      
      Element eFields = document.createElement("fields");
      eResult.appendChild(eFields);
      for (Field<?> field : this.fields.fields)
      {
        Element eField = document.createElement("field");
        eField.setAttribute("name", field.getName());
        eField.setAttribute("type", field.getDataType().getTypeName().toUpperCase());
        eFields.appendChild(eField);
      }
      Element eRecords = document.createElement("records");
      eResult.appendChild(eRecords);
      for (Record record : this)
      {
        Element eRecord = document.createElement("record");
        eRecords.appendChild(eRecord);
        for (int index = 0; index < this.fields.fields.length; index++)
        {
          Field<?> field = this.fields.fields[index];
          Object value = record.getValue(index);
          
          Element eValue = document.createElement("value");
          eValue.setAttribute("field", field.getName());
          eRecord.appendChild(eValue);
          if (value != null) {
            eValue.setTextContent(format0(value, false));
          }
        }
      }
      return document;
    }
    catch (ParserConfigurationException ignore)
    {
      throw new RuntimeException(ignore);
    }
  }
  
  public final <H extends ContentHandler> H intoXML(H handler)
    throws SAXException
  {
    Attributes empty = new AttributesImpl();
    
    handler.startDocument();
    handler.startPrefixMapping("", "http://www.jooq.org/xsd/jooq-export-2.6.0.xsd");
    handler.startElement("", "", "result", empty);
    handler.startElement("", "", "fields", empty);
    for (Field<?> field : this.fields.fields)
    {
      AttributesImpl attrs = new AttributesImpl();
      attrs.addAttribute("", "", "name", "CDATA", field.getName());
      attrs.addAttribute("", "", "type", "CDATA", field.getDataType().getTypeName().toUpperCase());
      
      handler.startElement("", "", "field", attrs);
      handler.endElement("", "", "field");
    }
    handler.endElement("", "", "fields");
    handler.startElement("", "", "records", empty);
    for (??? = iterator(); ((Iterator)???).hasNext();)
    {
      Record record = (Record)((Iterator)???).next();
      handler.startElement("", "", "record", empty);
      for (int index = 0; index < this.fields.fields.length; index++)
      {
        Field<?> field = this.fields.fields[index];
        Object value = record.getValue(index);
        
        AttributesImpl attrs = new AttributesImpl();
        attrs.addAttribute("", "", "field", "CDATA", field.getName());
        
        handler.startElement("", "", "value", attrs);
        if (value != null)
        {
          char[] chars = format0(value, false).toCharArray();
          handler.characters(chars, 0, chars.length);
        }
        handler.endElement("", "", "value");
      }
      handler.endElement("", "", "record");
    }
    handler.endElement("", "", "records");
    handler.endPrefixMapping("");
    handler.endDocument();
    return handler;
  }
  
  private final String escapeXML(String string)
  {
    return StringUtils.replaceEach(string, new String[] { "\"", "'", "<", ">", "&" }, new String[] { "&quot;", "&apos;", "&lt;", "&gt;", "&amp;" });
  }
  
  public final List<Map<String, Object>> intoMaps()
  {
    List<Map<String, Object>> list = new ArrayList();
    for (R record : this) {
      list.add(record.intoMap());
    }
    return list;
  }
  
  public final <K> Map<K, R> intoMap(Field<K> key)
  {
    int index = Utils.indexOrFail(fieldsRow(), key);
    Map<K, R> map = new LinkedHashMap();
    for (R record : this) {
      if (map.put(record.getValue(index), record) != null) {
        throw new InvalidResultException("Key " + key + " is not unique in Result for " + this);
      }
    }
    return map;
  }
  
  public final <K, V> Map<K, V> intoMap(Field<K> key, Field<V> value)
  {
    int kIndex = Utils.indexOrFail(fieldsRow(), key);
    int vIndex = Utils.indexOrFail(fieldsRow(), value);
    
    Map<K, V> map = new LinkedHashMap();
    for (R record : this) {
      if (map.put(record.getValue(kIndex), record.getValue(vIndex)) != null) {
        throw new InvalidResultException("Key " + key + " is not unique in Result for " + this);
      }
    }
    return map;
  }
  
  public final Map<Record, R> intoMap(Field<?>[] keys)
  {
    if (keys == null) {
      keys = new Field[0];
    }
    Map<Record, R> map = new LinkedHashMap();
    for (R record : this)
    {
      RecordImpl key = new RecordImpl(keys);
      for (Field<?> field : keys) {
        Utils.copyValue(key, field, record, field);
      }
      if (map.put(key, record) != null) {
        throw new InvalidResultException("Key list " + Arrays.asList(keys) + " is not unique in Result for " + this);
      }
    }
    return map;
  }
  
  public final <E> Map<List<?>, E> intoMap(Field<?>[] keys, Class<? extends E> type)
  {
    return intoMap(keys, Utils.configuration(this).recordMapperProvider().provide(this.fields, type));
  }
  
  public final <E> Map<List<?>, E> intoMap(Field<?>[] keys, RecordMapper<? super R, E> mapper)
  {
    if (keys == null) {
      keys = new Field[0];
    }
    Map<List<?>, E> map = new LinkedHashMap();
    for (R record : this)
    {
      List<Object> keyValueList = new ArrayList();
      for (Field<?> key : keys) {
        keyValueList.add(record.getValue(key));
      }
      if (map.put(keyValueList, mapper.map(record)) != null) {
        throw new InvalidResultException("Key list " + keyValueList + " is not unique in Result for " + this);
      }
    }
    return map;
  }
  
  public final <S extends Record> Map<S, R> intoMap(Table<S> table)
  {
    Map<S, R> map = new LinkedHashMap();
    for (R record : this)
    {
      S key = record.into(table);
      if (map.put(key, record) != null) {
        throw new InvalidResultException("Key list " + key + " is not unique in Result for " + this);
      }
    }
    return map;
  }
  
  public final <E, S extends Record> Map<S, E> intoMap(Table<S> table, Class<? extends E> type)
  {
    return intoMap(table, Utils.configuration(this).recordMapperProvider().provide(this.fields, type));
  }
  
  public final <E, S extends Record> Map<S, E> intoMap(Table<S> table, RecordMapper<? super R, E> mapper)
  {
    Map<S, E> map = new LinkedHashMap();
    for (R record : this)
    {
      S key = record.into(table);
      if (map.put(key, mapper.map(record)) != null) {
        throw new InvalidResultException("Key list " + key + " is not unique in Result for " + this);
      }
    }
    return map;
  }
  
  public final <K, E> Map<K, E> intoMap(Field<K> key, Class<? extends E> type)
  {
    return intoMap(key, Utils.configuration(this).recordMapperProvider().provide(this.fields, type));
  }
  
  public final <K, E> Map<K, E> intoMap(Field<K> key, RecordMapper<? super R, E> mapper)
  {
    int index = Utils.indexOrFail(fieldsRow(), key);
    Map<K, E> map = new LinkedHashMap();
    for (R record : this) {
      if (map.put(record.getValue(index), mapper.map(record)) != null) {
        throw new InvalidResultException("Key " + key + " is not unique in Result for " + this);
      }
    }
    return map;
  }
  
  public final <K> Map<K, Result<R>> intoGroups(Field<K> key)
  {
    int index = Utils.indexOrFail(fieldsRow(), key);
    Map<K, Result<R>> map = new LinkedHashMap();
    for (R record : this)
    {
      K val = record.getValue(index);
      Result<R> result = (Result)map.get(val);
      if (result == null)
      {
        result = new ResultImpl(this.configuration, this.fields);
        map.put(val, result);
      }
      result.add(record);
    }
    return map;
  }
  
  public final <K, V> Map<K, List<V>> intoGroups(Field<K> key, Field<V> value)
  {
    int kIndex = Utils.indexOrFail(fieldsRow(), key);
    int vIndex = Utils.indexOrFail(fieldsRow(), value);
    
    Map<K, List<V>> map = new LinkedHashMap();
    for (R record : this)
    {
      K k = record.getValue(kIndex);
      V v = record.getValue(vIndex);
      List<V> result = (List)map.get(k);
      if (result == null)
      {
        result = new ArrayList();
        map.put(k, result);
      }
      result.add(v);
    }
    return map;
  }
  
  public final <K, E> Map<K, List<E>> intoGroups(Field<K> key, Class<? extends E> type)
  {
    return intoGroups(key, Utils.configuration(this).recordMapperProvider().provide(this.fields, type));
  }
  
  public final <K, E> Map<K, List<E>> intoGroups(Field<K> key, RecordMapper<? super R, E> mapper)
  {
    int index = Utils.indexOrFail(fieldsRow(), key);
    Map<K, List<E>> map = new LinkedHashMap();
    for (R record : this)
    {
      K keyVal = record.getValue(index);
      
      List<E> list = (List)map.get(keyVal);
      if (list == null)
      {
        list = new ArrayList();
        map.put(keyVal, list);
      }
      list.add(mapper.map(record));
    }
    return map;
  }
  
  public final Map<Record, Result<R>> intoGroups(Field<?>[] keys)
  {
    if (keys == null) {
      keys = new Field[0];
    }
    Map<Record, Result<R>> map = new LinkedHashMap();
    for (R record : this)
    {
      RecordImpl key = new RecordImpl(keys);
      for (Field<?> field : keys) {
        Utils.copyValue(key, field, record, field);
      }
      Object result = (Result)map.get(key);
      if (result == null)
      {
        result = new ResultImpl(configuration(), this.fields);
        map.put(key, result);
      }
      ((Result)result).add(record);
    }
    return map;
  }
  
  public final <E> Map<Record, List<E>> intoGroups(Field<?>[] keys, Class<? extends E> type)
  {
    return intoGroups(keys, Utils.configuration(this).recordMapperProvider().provide(this.fields, type));
  }
  
  public final <E> Map<Record, List<E>> intoGroups(Field<?>[] keys, RecordMapper<? super R, E> mapper)
  {
    if (keys == null) {
      keys = new Field[0];
    }
    Map<Record, List<E>> map = new LinkedHashMap();
    for (R record : this)
    {
      RecordImpl key = new RecordImpl(keys);
      for (Field<?> field : keys) {
        Utils.copyValue(key, field, record, field);
      }
      Object list = (List)map.get(key);
      if (list == null)
      {
        list = new ArrayList();
        map.put(key, list);
      }
      ((List)list).add(mapper.map(record));
    }
    return map;
  }
  
  public final <S extends Record> Map<S, Result<R>> intoGroups(Table<S> table)
  {
    Map<S, Result<R>> map = new LinkedHashMap();
    for (R record : this)
    {
      S key = record.into(table);
      
      Result<R> result = (Result)map.get(key);
      if (result == null)
      {
        result = new ResultImpl(configuration(), this.fields);
        map.put(key, result);
      }
      result.add(record);
    }
    return map;
  }
  
  public final <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> table, Class<? extends E> type)
  {
    return intoGroups(table, Utils.configuration(this).recordMapperProvider().provide(this.fields, type));
  }
  
  public final <E, S extends Record> Map<S, List<E>> intoGroups(Table<S> table, RecordMapper<? super R, E> mapper)
  {
    Map<S, List<E>> map = new LinkedHashMap();
    for (R record : this)
    {
      S key = record.into(table);
      
      List<E> list = (List)map.get(key);
      if (list == null)
      {
        list = new ArrayList();
        map.put(key, list);
      }
      list.add(mapper.map(record));
    }
    return map;
  }
  
  public final Object[][] intoArray()
  {
    int size = size();
    Object[][] array = new Object[size][];
    for (int i = 0; i < size; i++) {
      array[i] = get(i).intoArray();
    }
    return array;
  }
  
  public final Object[] intoArray(int fieldIndex)
  {
    Class<?> type = this.fields.fields[fieldIndex].getType();
    List<?> list = getValues(fieldIndex);
    return list.toArray((Object[])Array.newInstance(type, list.size()));
  }
  
  public final <T> T[] intoArray(int fieldIndex, Class<? extends T> type)
  {
    return (Object[])Convert.convertArray(intoArray(fieldIndex), type);
  }
  
  public final <U> U[] intoArray(int fieldIndex, Converter<?, U> converter)
  {
    return Convert.convertArray(intoArray(fieldIndex), converter);
  }
  
  public final Object[] intoArray(String fieldName)
  {
    Class<?> type = field(fieldName).getType();
    List<?> list = getValues(fieldName);
    return list.toArray((Object[])Array.newInstance(type, list.size()));
  }
  
  public final <T> T[] intoArray(String fieldName, Class<? extends T> type)
  {
    return (Object[])Convert.convertArray(intoArray(fieldName), type);
  }
  
  public final <U> U[] intoArray(String fieldName, Converter<?, U> converter)
  {
    return Convert.convertArray(intoArray(fieldName), converter);
  }
  
  public final <T> T[] intoArray(Field<T> field)
  {
    return getValues(field).toArray((Object[])Array.newInstance(field.getType(), 0));
  }
  
  public final <T> T[] intoArray(Field<?> field, Class<? extends T> type)
  {
    return (Object[])Convert.convertArray(intoArray(field), type);
  }
  
  public final <T, U> U[] intoArray(Field<T> field, Converter<? super T, U> converter)
  {
    return Convert.convertArray(intoArray(field), converter);
  }
  
  public final Set<?> intoSet(int fieldIndex)
  {
    return new LinkedHashSet(getValues(fieldIndex));
  }
  
  public final <T> Set<T> intoSet(int fieldIndex, Class<? extends T> type)
  {
    return new LinkedHashSet(getValues(fieldIndex, type));
  }
  
  public final <U> Set<U> intoSet(int fieldIndex, Converter<?, U> converter)
  {
    return new LinkedHashSet(getValues(fieldIndex, converter));
  }
  
  public final Set<?> intoSet(String fieldName)
  {
    return new LinkedHashSet(getValues(fieldName));
  }
  
  public final <T> Set<T> intoSet(String fieldName, Class<? extends T> type)
  {
    return new LinkedHashSet(getValues(fieldName, type));
  }
  
  public final <U> Set<U> intoSet(String fieldName, Converter<?, U> converter)
  {
    return new LinkedHashSet(getValues(fieldName, converter));
  }
  
  public final <T> Set<T> intoSet(Field<T> field)
  {
    return new LinkedHashSet(getValues(field));
  }
  
  public final <T> Set<T> intoSet(Field<?> field, Class<? extends T> type)
  {
    return new LinkedHashSet(getValues(field, type));
  }
  
  public final <T, U> Set<U> intoSet(Field<T> field, Converter<? super T, U> converter)
  {
    return new LinkedHashSet(getValues(field, converter));
  }
  
  public final Result<Record> into(Field<?>... f)
  {
    Result<Record> result = new ResultImpl(Utils.configuration(this), f);
    for (Record record : this) {
      result.add(record.into(f));
    }
    return result;
  }
  
  public final <T1> Result<Record1<T1>> into(Field<T1> field1)
  {
    return into(new Field[] { field1 });
  }
  
  public final <T1, T2> Result<Record2<T1, T2>> into(Field<T1> field1, Field<T2> field2)
  {
    return into(new Field[] { field1, field2 });
  }
  
  public final <T1, T2, T3> Result<Record3<T1, T2, T3>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return into(new Field[] { field1, field2, field3 });
  }
  
  public final <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return into(new Field[] { field1, field2, field3, field4 });
  }
  
  public final <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return into(new Field[] { field1, field2, field3, field4, field5 });
  }
  
  public final <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return into(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  public final <E> List<E> into(Class<? extends E> type)
  {
    List<E> list = new ArrayList(size());
    RecordMapper<R, E> mapper = Utils.configuration(this).recordMapperProvider().provide(this.fields, type);
    for (R record : this) {
      list.add(mapper.map(record));
    }
    return list;
  }
  
  public final <Z extends Record> Result<Z> into(Table<Z> table)
  {
    Result<Z> list = new ResultImpl(configuration(), table.fields());
    for (R record : this) {
      list.add(record.into(table));
    }
    return list;
  }
  
  public final <H extends RecordHandler<? super R>> H into(H handler)
  {
    for (R record : this) {
      handler.next(record);
    }
    return handler;
  }
  
  public final ResultSet intoResultSet()
  {
    return new MockResultSet(this);
  }
  
  public final <E> List<E> map(RecordMapper<? super R, E> mapper)
  {
    List<E> result = new ArrayList();
    for (R record : this) {
      result.add(mapper.map(record));
    }
    return result;
  }
  
  public final <T extends Comparable<? super T>> Result<R> sortAsc(Field<T> field)
  {
    return sortAsc(field, new NaturalComparator(null));
  }
  
  public final Result<R> sortAsc(int fieldIndex)
  {
    return sortAsc(fieldIndex, new NaturalComparator(null));
  }
  
  public final Result<R> sortAsc(String fieldName)
  {
    return sortAsc(fieldName, new NaturalComparator(null));
  }
  
  public final <T> Result<R> sortAsc(Field<T> field, Comparator<? super T> comparator)
  {
    return sortAsc(Utils.indexOrFail(fieldsRow(), field), comparator);
  }
  
  public final Result<R> sortAsc(int fieldIndex, Comparator<?> comparator)
  {
    return sortAsc(new RecordComparator(fieldIndex, comparator));
  }
  
  public final Result<R> sortAsc(String fieldName, Comparator<?> comparator)
  {
    return sortAsc(Utils.indexOrFail(fieldsRow(), fieldName), comparator);
  }
  
  public final Result<R> sortAsc(Comparator<? super R> comparator)
  {
    Collections.sort(this, comparator);
    return this;
  }
  
  public final <T extends Comparable<? super T>> Result<R> sortDesc(Field<T> field)
  {
    return sortAsc(field, Collections.reverseOrder(new NaturalComparator(null)));
  }
  
  public final Result<R> sortDesc(int fieldIndex)
  {
    return sortAsc(fieldIndex, Collections.reverseOrder(new NaturalComparator(null)));
  }
  
  public final Result<R> sortDesc(String fieldName)
  {
    return sortAsc(fieldName, Collections.reverseOrder(new NaturalComparator(null)));
  }
  
  public final <T> Result<R> sortDesc(Field<T> field, Comparator<? super T> comparator)
  {
    return sortAsc(field, Collections.reverseOrder(comparator));
  }
  
  public final Result<R> sortDesc(int fieldIndex, Comparator<?> comparator)
  {
    return sortAsc(fieldIndex, Collections.reverseOrder(comparator));
  }
  
  public final Result<R> sortDesc(String fieldName, Comparator<?> comparator)
  {
    return sortAsc(fieldName, Collections.reverseOrder(comparator));
  }
  
  public final Result<R> sortDesc(Comparator<? super R> comparator)
  {
    return sortAsc(Collections.reverseOrder(comparator));
  }
  
  public final Result<R> intern(Field<?>... f)
  {
    return intern(this.fields.indexesOf(f));
  }
  
  public final Result<R> intern(int... fieldIndexes)
  {
    int fieldIndex;
    for (fieldIndex : fieldIndexes) {
      if (this.fields.fields[fieldIndex].getType() == String.class) {
        for (Record record : this) {
          ((AbstractRecord)record).intern0(fieldIndex);
        }
      }
    }
    return this;
  }
  
  public final Result<R> intern(String... fieldNames)
  {
    return intern(this.fields.indexesOf(fieldNames));
  }
  
  private static class RecordComparator<T, R extends Record>
    implements Comparator<R>
  {
    private final Comparator<? super T> comparator;
    private final int fieldIndex;
    
    RecordComparator(int fieldIndex, Comparator<? super T> comparator)
    {
      this.fieldIndex = fieldIndex;
      this.comparator = comparator;
    }
    
    public int compare(R record1, R record2)
    {
      return this.comparator.compare(record1.getValue(this.fieldIndex), record2.getValue(this.fieldIndex));
    }
  }
  
  private static class NaturalComparator<T extends Comparable<? super T>>
    implements Comparator<T>
  {
    public int compare(T o1, T o2)
    {
      if ((o1 == null) && (o2 == null)) {
        return 0;
      }
      if (o1 == null) {
        return -1;
      }
      if (o2 == null) {
        return 1;
      }
      return o1.compareTo(o2);
    }
  }
  
  public String toString()
  {
    return format();
  }
  
  public int hashCode()
  {
    return this.records.hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if ((obj instanceof ResultImpl))
    {
      ResultImpl<R> other = (ResultImpl)obj;
      return this.records.equals(other.records);
    }
    return false;
  }
  
  public final int size()
  {
    return this.records.size();
  }
  
  public final boolean contains(Object o)
  {
    return this.records.contains(o);
  }
  
  public final Object[] toArray()
  {
    return this.records.toArray();
  }
  
  public final <T> T[] toArray(T[] a)
  {
    return this.records.toArray(a);
  }
  
  public final boolean add(R e)
  {
    return this.records.add(e);
  }
  
  public final boolean remove(Object o)
  {
    return this.records.remove(o);
  }
  
  public final boolean containsAll(Collection<?> c)
  {
    return this.records.containsAll(c);
  }
  
  public final boolean addAll(Collection<? extends R> c)
  {
    return this.records.addAll(c);
  }
  
  public final boolean addAll(int index, Collection<? extends R> c)
  {
    return this.records.addAll(index, c);
  }
  
  public final boolean removeAll(Collection<?> c)
  {
    return this.records.removeAll(c);
  }
  
  public final boolean retainAll(Collection<?> c)
  {
    return this.records.retainAll(c);
  }
  
  public final void clear()
  {
    this.records.clear();
  }
  
  public final R get(int index)
  {
    return (Record)this.records.get(index);
  }
  
  public final R set(int index, R element)
  {
    return (Record)this.records.set(index, element);
  }
  
  public final void add(int index, R element)
  {
    this.records.add(index, element);
  }
  
  public final R remove(int index)
  {
    return (Record)this.records.remove(index);
  }
  
  public final int indexOf(Object o)
  {
    return this.records.indexOf(o);
  }
  
  public final int lastIndexOf(Object o)
  {
    return this.records.lastIndexOf(o);
  }
  
  public final Iterator<R> iterator()
  {
    return this.records.iterator();
  }
  
  public final ListIterator<R> listIterator()
  {
    return this.records.listIterator();
  }
  
  public final ListIterator<R> listIterator(int index)
  {
    return this.records.listIterator(index);
  }
  
  public final List<R> subList(int fromIndex, int toIndex)
  {
    return this.records.subList(fromIndex, toIndex);
  }
}

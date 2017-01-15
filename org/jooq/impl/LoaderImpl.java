package org.jooq.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.InsertQuery;
import org.jooq.Loader;
import org.jooq.LoaderCSVOptionsStep;
import org.jooq.LoaderCSVStep;
import org.jooq.LoaderError;
import org.jooq.LoaderJSONOptionsStep;
import org.jooq.LoaderJSONStep;
import org.jooq.LoaderOptionsStep;
import org.jooq.LoaderXMLStep;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.StringUtils;
import org.jooq.tools.csv.CSVReader;
import org.xml.sax.InputSource;

class LoaderImpl<R extends TableRecord<R>>
  implements LoaderOptionsStep<R>, LoaderXMLStep<R>, LoaderCSVStep<R>, LoaderCSVOptionsStep<R>, LoaderJSONStep<R>, LoaderJSONOptionsStep<R>, Loader<R>
{
  private static final int ON_DUPLICATE_KEY_ERROR = 0;
  private static final int ON_DUPLICATE_KEY_IGNORE = 1;
  private static final int ON_DUPLICATE_KEY_UPDATE = 2;
  private static final int ON_ERROR_ABORT = 0;
  private static final int ON_ERROR_IGNORE = 1;
  private static final int COMMIT_NONE = 0;
  private static final int COMMIT_AFTER = 1;
  private static final int COMMIT_ALL = 2;
  private static final int CONTENT_CSV = 0;
  private static final int CONTENT_XML = 1;
  private static final int CONTENT_JSON = 2;
  private final DSLContext create;
  private final Configuration configuration;
  private final Table<R> table;
  private int onDuplicate = 0;
  private int onError = 0;
  private int commit = 0;
  private int commitAfter = 1;
  private int content = 0;
  private BufferedReader data;
  private int ignoreRows = 1;
  private char quote = '"';
  private char separator = ',';
  private String nullString = null;
  private Field<?>[] fields;
  private boolean[] primaryKey;
  private int ignored;
  private int processed;
  private int stored;
  private final List<LoaderError> errors;
  
  LoaderImpl(Configuration configuration, Table<R> table)
  {
    this.create = DSL.using(configuration);
    this.configuration = configuration;
    this.table = table;
    this.errors = new ArrayList();
  }
  
  public final LoaderImpl<R> onDuplicateKeyError()
  {
    this.onDuplicate = 0;
    return this;
  }
  
  public final LoaderImpl<R> onDuplicateKeyIgnore()
  {
    if (this.table.getPrimaryKey() == null) {
      throw new IllegalStateException("ON DUPLICATE KEY IGNORE only works on tables with explicit primary keys. Table is not updatable : " + this.table);
    }
    this.onDuplicate = 1;
    return this;
  }
  
  public final LoaderImpl<R> onDuplicateKeyUpdate()
  {
    if (this.table.getPrimaryKey() == null) {
      throw new IllegalStateException("ON DUPLICATE KEY UPDATE only works on tables with explicit primary keys. Table is not updatable : " + this.table);
    }
    this.onDuplicate = 2;
    return this;
  }
  
  public final LoaderImpl<R> onErrorIgnore()
  {
    this.onError = 1;
    return this;
  }
  
  public final LoaderImpl<R> onErrorAbort()
  {
    this.onError = 0;
    return this;
  }
  
  public final LoaderImpl<R> commitEach()
  {
    this.commit = 1;
    return this;
  }
  
  public final LoaderImpl<R> commitAfter(int number)
  {
    this.commit = 1;
    this.commitAfter = number;
    return this;
  }
  
  public final LoaderImpl<R> commitAll()
  {
    this.commit = 2;
    return this;
  }
  
  public final LoaderImpl<R> commitNone()
  {
    this.commit = 0;
    return this;
  }
  
  public final LoaderImpl<R> loadCSV(File file)
    throws FileNotFoundException
  {
    this.content = 0;
    this.data = new BufferedReader(new FileReader(file));
    return this;
  }
  
  public final LoaderImpl<R> loadCSV(String csv)
  {
    this.content = 0;
    this.data = new BufferedReader(new StringReader(csv));
    return this;
  }
  
  public final LoaderImpl<R> loadCSV(InputStream stream)
  {
    this.content = 0;
    this.data = new BufferedReader(new InputStreamReader(stream));
    return this;
  }
  
  public final LoaderImpl<R> loadCSV(Reader reader)
  {
    this.content = 0;
    this.data = new BufferedReader(reader);
    return this;
  }
  
  public final LoaderImpl<R> loadXML(File file)
    throws FileNotFoundException
  {
    this.content = 1;
    throw new UnsupportedOperationException("This is not yet implemented");
  }
  
  public final LoaderImpl<R> loadXML(String xml)
  {
    this.content = 1;
    throw new UnsupportedOperationException("This is not yet implemented");
  }
  
  public final LoaderImpl<R> loadXML(InputStream stream)
  {
    this.content = 1;
    throw new UnsupportedOperationException("This is not yet implemented");
  }
  
  public final LoaderImpl<R> loadXML(Reader reader)
  {
    this.content = 1;
    throw new UnsupportedOperationException("This is not yet implemented");
  }
  
  public final LoaderImpl<R> loadXML(InputSource source)
  {
    this.content = 1;
    throw new UnsupportedOperationException("This is not yet implemented");
  }
  
  public final LoaderImpl<R> fields(Field<?>... f)
  {
    this.fields = f;
    this.primaryKey = new boolean[f.length];
    if (this.table.getPrimaryKey() != null) {
      for (int i = 0; i < this.fields.length; i++) {
        if ((this.fields[i] != null) && 
          (this.table.getPrimaryKey().getFields().contains(this.fields[i]))) {
          this.primaryKey[i] = true;
        }
      }
    }
    return this;
  }
  
  public final LoaderImpl<R> fields(Collection<? extends Field<?>> f)
  {
    return fields((Field[])f.toArray(new Field[f.size()]));
  }
  
  public final LoaderImpl<R> ignoreRows(int number)
  {
    this.ignoreRows = number;
    return this;
  }
  
  public final LoaderImpl<R> quote(char q)
  {
    this.quote = q;
    return this;
  }
  
  public final LoaderImpl<R> separator(char s)
  {
    this.separator = s;
    return this;
  }
  
  public final LoaderImpl<R> nullString(String n)
  {
    this.nullString = n;
    return this;
  }
  
  public final LoaderJSONStep<R> loadJSON(File file)
    throws FileNotFoundException
  {
    this.content = 2;
    this.data = new BufferedReader(new FileReader(file));
    return this;
  }
  
  public final LoaderJSONStep<R> loadJSON(String json)
  {
    this.content = 2;
    this.data = new BufferedReader(new StringReader(json));
    return this;
  }
  
  public final LoaderJSONStep<R> loadJSON(InputStream stream)
  {
    this.content = 2;
    this.data = new BufferedReader(new InputStreamReader(stream));
    return this;
  }
  
  public final LoaderJSONStep<R> loadJSON(Reader reader)
  {
    this.content = 2;
    this.data = new BufferedReader(reader);
    return this;
  }
  
  public final LoaderImpl<R> execute()
    throws IOException
  {
    if (this.content == 0)
    {
      executeCSV();
    }
    else
    {
      if (this.content == 1) {
        throw new UnsupportedOperationException();
      }
      if (this.content == 2) {
        executeJSON();
      } else {
        throw new IllegalStateException();
      }
    }
    return this;
  }
  
  private void executeJSON()
    throws IOException
  {
    JSONReader reader = new JSONReader(this.data);
    try
    {
      List<String[]> allRecords = reader.readAll();
      executeSQL(allRecords.iterator());
    }
    catch (SQLException e)
    {
      throw Utils.translate(null, e);
    }
    finally
    {
      reader.close();
    }
  }
  
  private final void executeCSV()
    throws IOException
  {
    CSVReader reader = new CSVReader(this.data, this.separator, this.quote, this.ignoreRows);
    try
    {
      executeSQL(reader);
    }
    catch (SQLException e)
    {
      throw Utils.translate(null, e);
    }
    finally
    {
      reader.close();
    }
  }
  
  private void executeSQL(Iterator<String[]> reader)
    throws SQLException
  {
    String[] row;
    label476:
    while ((reader.hasNext()) && ((row = (String[])reader.next()) != null))
    {
      for (int i = 0; i < row.length; i++) {
        if (StringUtils.equals(this.nullString, row[i])) {
          row[i] = null;
        }
      }
      this.processed += 1;
      InsertQuery<R> insert = this.create.insertQuery(this.table);
      for (int i = 0; i < row.length; i++) {
        if ((i < this.fields.length) && (this.fields[i] != null)) {
          addValue0(insert, this.fields[i], row[i]);
        }
      }
      if (this.onDuplicate == 2)
      {
        insert.onDuplicateKeyUpdate(true);
        for (int i = 0; i < row.length; i++) {
          if ((i < this.fields.length) && (this.fields[i] != null) && (this.primaryKey[i] == 0)) {
            addValueForUpdate0(insert, this.fields[i], row[i]);
          }
        }
      }
      else if (this.onDuplicate == 1)
      {
        SelectQuery<R> select = this.create.selectQuery(this.table);
        for (int i = 0; i < row.length; i++) {
          if ((i < this.fields.length) && (this.primaryKey[i] != 0)) {
            select.addConditions(new Condition[] { getCondition(this.fields[i], row[i]) });
          }
        }
        try
        {
          if (this.create.fetchExists(select))
          {
            this.ignored += 1;
            continue;
          }
        }
        catch (DataAccessException e)
        {
          this.errors.add(new LoaderErrorImpl(e, row, this.processed - 1, select));
        }
      }
      else if (this.onDuplicate != 0) {}
      try
      {
        insert.execute();
        this.stored += 1;
        if ((this.commit == 1) && 
          (this.processed % this.commitAfter == 0)) {
          this.configuration.connectionProvider().acquire().commit();
        }
      }
      catch (DataAccessException e)
      {
        this.errors.add(new LoaderErrorImpl(e, row, this.processed - 1, insert));
        this.ignored += 1;
        if (this.onError != 0) {
          break label476;
        }
      }
      break;
    }
    try
    {
      if (this.commit == 2)
      {
        if (!this.errors.isEmpty())
        {
          this.stored = 0;
          this.configuration.connectionProvider().acquire().rollback();
        }
        else
        {
          this.configuration.connectionProvider().acquire().commit();
        }
      }
      else if ((this.commit == 1) && 
        (this.processed % this.commitAfter != 0)) {
        this.configuration.connectionProvider().acquire().commit();
      }
    }
    catch (DataAccessException e)
    {
      this.errors.add(new LoaderErrorImpl(e, null, this.processed - 1, null));
    }
  }
  
  private <T> void addValue0(InsertQuery<R> insert, Field<T> field, String row)
  {
    insert.addValue(field, field.getDataType().convert(row));
  }
  
  private <T> void addValueForUpdate0(InsertQuery<R> insert, Field<T> field, String row)
  {
    insert.addValueForUpdate(field, field.getDataType().convert(row));
  }
  
  private <T> Condition getCondition(Field<T> field, String string)
  {
    return field.equal(field.getDataType().convert(string));
  }
  
  public final List<LoaderError> errors()
  {
    return this.errors;
  }
  
  public final int processed()
  {
    return this.processed;
  }
  
  public final int ignored()
  {
    return this.ignored;
  }
  
  public final int stored()
  {
    return this.stored;
  }
}

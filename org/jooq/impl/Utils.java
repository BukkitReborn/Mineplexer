package org.jooq.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import org.jooq.Attachable;
import org.jooq.AttachableInternal;
import org.jooq.BindContext;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.Context;
import org.jooq.Cursor;
import org.jooq.DataType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.SchemaMapping;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.conf.BackslashEscaping;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.InvalidResultException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.reflect.Reflect;

final class Utils
{
  static final JooqLogger log = JooqLogger.getLogger(Utils.class);
  static final String DATA_OMIT_RETURNING_CLAUSE = "org.jooq.configuration.omit-returning-clause";
  static final String DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY = "org.jooq.configuration.row-value-expression-subquery";
  static final String DATA_LOCK_ROWS_FOR_UPDATE = "org.jooq.configuration.lock-rows-for-update";
  static final String DATA_COUNT_BIND_VALUES = "org.jooq.configuration.count-bind-values";
  static final String DATA_FORCE_STATIC_STATEMENT = "org.jooq.configuration.force-static-statement";
  static final String DATA_OMIT_CLAUSE_EVENT_EMISSION = "org.jooq.configuration.omit-clause-event-emission";
  static final String DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES = "org.jooq.configuration.wrap-derived-tables-in-parentheses";
  static final String DATA_LOCALLY_SCOPED_DATA_MAP = "org.jooq.configuration.locally-scoped-data-map";
  static final String DATA_WINDOW_DEFINITIONS = "org.jooq.configuration.local-window-definitions";
  static final String DATA_DEFAULT_TRANSACTION_PROVIDER_AUTOCOMMIT = "org.jooq.configuration.default-transaction-provider-autocommit";
  static final String DATA_DEFAULT_TRANSACTION_PROVIDER_SAVEPOINTS = "org.jooq.configuration.default-transaction-provider-savepoints";
  static final String DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION = "org.jooq.configuration.default-transaction-provider-connection-provider";
  static final String DATA_OVERRIDE_ALIASES_IN_ORDER_BY = "org.jooq.configuration.override-aliases-in-order-by";
  static final String DATA_UNALIAS_ALIASES_IN_ORDER_BY = "org.jooq.configuration.unalias-aliases-in-order-by";
  static final String DATA_SELECT_INTO_TABLE = "org.jooq.configuration.select-into-table";
  static final String DATA_OMIT_INTO_CLAUSE = "org.jooq.configuration.omit-into-clause";
  static final String DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE = "org.jooq.configuration.render-trailing-limit-if-applicable";
  static final String DATA_REFLECTION_CACHE_GET_ANNOTATED_GETTER = new String("org.jooq.configuration.reflection-cache.get-annotated-getter");
  static final String DATA_REFLECTION_CACHE_GET_ANNOTATED_MEMBERS = new String("org.jooq.configuration.reflection-cache.get-annotated-members");
  static final String DATA_REFLECTION_CACHE_GET_ANNOTATED_SETTERS = new String("org.jooq.configuration.reflection-cache.get-annotated-setters");
  static final String DATA_REFLECTION_CACHE_GET_MATCHING_GETTER = new String("org.jooq.configuration.reflection-cache.get-matching-getter");
  static final String DATA_REFLECTION_CACHE_GET_MATCHING_MEMBERS = new String("org.jooq.configuration.reflection-cache.get-matching-members");
  static final String DATA_REFLECTION_CACHE_GET_MATCHING_SETTERS = new String("org.jooq.configuration.reflection-cache.get-matching-setters");
  static final String DATA_REFLECTION_CACHE_HAS_COLUMN_ANNOTATIONS = new String("org.jooq.configuration.reflection-cache.has-column-annotations");
  static final char ESCAPE = '!';
  private static Boolean isJPAAvailable;
  private static int maxConsumedExceptions = 256;
  private static final Pattern DASH_PATTERN = Pattern.compile("(-+)");
  private static final Pattern PLUS_PATTERN = Pattern.compile("\\+(-+)(?=\\+)");
  private static final String WHITESPACE = " \t\n\013\f\r";
  private static final String[] JDBC_ESCAPE_PREFIXES = { "{fn ", "{d ", "{t ", "{ts " };
  
  static final List<Row> rows(Result<?> result)
  {
    List<Row> rows = new ArrayList();
    for (Record record : result) {
      rows.add(record.valuesRow());
    }
    return rows;
  }
  
  static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<R> type)
  {
    return newRecord(fetched, type, null);
  }
  
  static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<R> type, org.jooq.Field<?>[] fields)
  {
    return newRecord(fetched, type, fields, null);
  }
  
  static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, org.jooq.Table<R> type)
  {
    return newRecord(fetched, type, null);
  }
  
  static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, org.jooq.Table<R> type, Configuration configuration)
  {
    return newRecord(fetched, type.getRecordType(), type.fields(), configuration);
  }
  
  static final <R extends UDTRecord<R>> RecordDelegate<R> newRecord(boolean fetched, UDT<R> type)
  {
    return newRecord(fetched, type, null);
  }
  
  static final <R extends UDTRecord<R>> RecordDelegate<R> newRecord(boolean fetched, UDT<R> type, Configuration configuration)
  {
    return newRecord(fetched, type.getRecordType(), type.fields(), configuration);
  }
  
  static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<R> type, org.jooq.Field<?>[] fields, Configuration configuration)
  {
    try
    {
      R record;
      R record;
      if ((type == RecordImpl.class) || (type == Record.class)) {
        record = new RecordImpl(fields);
      } else {
        record = (Record)((Constructor)Reflect.accessible(type.getDeclaredConstructor(new Class[0]))).newInstance(new Object[0]);
      }
      if ((record instanceof AbstractRecord)) {
        ((AbstractRecord)record).fetched = fetched;
      }
      return new RecordDelegate(configuration, record);
    }
    catch (Exception e)
    {
      throw new IllegalStateException("Could not construct new record", e);
    }
  }
  
  static void resetChangedOnNotNull(Record record)
  {
    int size = record.size();
    for (int i = 0; i < size; i++) {
      if ((record.getValue(i) == null) && 
        (!record.field(i).getDataType().nullable())) {
        record.changed(i, false);
      }
    }
  }
  
  static final Configuration getConfiguration(Attachable attachable)
  {
    if ((attachable instanceof AttachableInternal)) {
      return ((AttachableInternal)attachable).configuration();
    }
    return null;
  }
  
  static Configuration configuration(Attachable attachable)
  {
    return configuration((attachable instanceof AttachableInternal) ? ((AttachableInternal)attachable)
      .configuration() : null);
  }
  
  static Configuration configuration(Configuration configuration)
  {
    return configuration != null ? configuration : new DefaultConfiguration();
  }
  
  static Settings settings(Attachable attachable)
  {
    return configuration(attachable).settings();
  }
  
  static Settings settings(Configuration configuration)
  {
    return configuration(configuration).settings();
  }
  
  static final boolean attachRecords(Configuration configuration)
  {
    if (configuration != null)
    {
      Settings settings = configuration.settings();
      if (settings != null) {
        return !Boolean.FALSE.equals(settings.isAttachRecords());
      }
    }
    return true;
  }
  
  static final org.jooq.Field<?>[] fieldArray(Collection<? extends org.jooq.Field<?>> fields)
  {
    return fields == null ? null : (org.jooq.Field[])fields.toArray(new org.jooq.Field[fields.size()]);
  }
  
  static final Class<?>[] types(org.jooq.Field<?>[] fields)
  {
    return types(dataTypes(fields));
  }
  
  static final Class<?>[] types(DataType<?>[] types)
  {
    if (types == null) {
      return null;
    }
    Class<?>[] result = new Class[types.length];
    for (int i = 0; i < types.length; i++) {
      if (types[i] != null) {
        result[i] = types[i].getType();
      } else {
        result[i] = Object.class;
      }
    }
    return result;
  }
  
  static final Class<?>[] types(Object[] values)
  {
    if (values == null) {
      return null;
    }
    Class<?>[] result = new Class[values.length];
    for (int i = 0; i < values.length; i++) {
      if ((values[i] instanceof org.jooq.Field)) {
        result[i] = ((org.jooq.Field)values[i]).getType();
      } else if (values[i] != null) {
        result[i] = values[i].getClass();
      } else {
        result[i] = Object.class;
      }
    }
    return result;
  }
  
  static final DataType<?>[] dataTypes(org.jooq.Field<?>[] fields)
  {
    if (fields == null) {
      return null;
    }
    DataType<?>[] result = new DataType[fields.length];
    for (int i = 0; i < fields.length; i++) {
      if (fields[i] != null) {
        result[i] = fields[i].getDataType();
      } else {
        result[i] = DSL.getDataType(Object.class);
      }
    }
    return result;
  }
  
  static final DataType<?>[] dataTypes(Class<?>[] types)
  {
    if (types == null) {
      return null;
    }
    DataType<?>[] result = new DataType[types.length];
    for (int i = 0; i < types.length; i++) {
      if (types[i] != null) {
        result[i] = DSL.getDataType(types[i]);
      } else {
        result[i] = DSL.getDataType(Object.class);
      }
    }
    return result;
  }
  
  static final DataType<?>[] dataTypes(Object[] values)
  {
    return dataTypes(types(values));
  }
  
  static final String[] fieldNames(int length)
  {
    String[] result = new String[length];
    for (int i = 0; i < length; i++) {
      result[i] = ("v" + i);
    }
    return result;
  }
  
  static final String[] fieldNames(org.jooq.Field<?>[] fields)
  {
    String[] result = new String[fields.length];
    for (int i = 0; i < fields.length; i++) {
      result[i] = fields[i].getName();
    }
    return result;
  }
  
  static final org.jooq.Field<?>[] fields(int length)
  {
    org.jooq.Field<?>[] result = new org.jooq.Field[length];
    String[] names = fieldNames(length);
    for (int i = 0; i < length; i++) {
      result[i] = DSL.fieldByName(new String[] { names[i] });
    }
    return result;
  }
  
  static final org.jooq.Field<?>[] aliasedFields(org.jooq.Field<?>[] fields, String[] aliases)
  {
    org.jooq.Field<?>[] result = new org.jooq.Field[fields.length];
    for (int i = 0; i < fields.length; i++) {
      result[i] = fields[i].as(aliases[i]);
    }
    return result;
  }
  
  static final org.jooq.Field<?>[] fieldsByName(String[] fieldNames)
  {
    return fieldsByName(null, fieldNames);
  }
  
  static final org.jooq.Field<?>[] fieldsByName(String tableName, String[] fieldNames)
  {
    org.jooq.Field<?>[] result = new org.jooq.Field[fieldNames.length];
    for (int i = 0; i < fieldNames.length; i++) {
      if (tableName == null) {
        result[i] = DSL.fieldByName(new String[] { fieldNames[i] });
      } else {
        result[i] = DSL.fieldByName(new String[] { tableName, fieldNames[i] });
      }
    }
    return result;
  }
  
  static final Name[] names(String[] names)
  {
    Name[] result = new Name[names.length];
    for (int i = 0; i < names.length; i++) {
      result[i] = DSL.name(new String[] { names[i] });
    }
    return result;
  }
  
  static final <T> org.jooq.Field<T> field(T value)
  {
    if ((value instanceof org.jooq.Field)) {
      return (org.jooq.Field)value;
    }
    return DSL.val(value);
  }
  
  static final <T> org.jooq.Field<T> field(Object value, org.jooq.Field<T> field)
  {
    if ((value instanceof org.jooq.Field)) {
      return (org.jooq.Field)value;
    }
    return DSL.val(value, field);
  }
  
  static final <T> org.jooq.Field<T> field(Object value, Class<T> type)
  {
    if ((value instanceof org.jooq.Field)) {
      return (org.jooq.Field)value;
    }
    return DSL.val(value, type);
  }
  
  static final <T> org.jooq.Field<T> field(Object value, DataType<T> type)
  {
    if ((value instanceof org.jooq.Field)) {
      return (org.jooq.Field)value;
    }
    return DSL.val(value, type);
  }
  
  static final List<org.jooq.Field<?>> fields(Object[] values)
  {
    List<org.jooq.Field<?>> result = new ArrayList();
    if (values != null) {
      for (Object value : values) {
        result.add(field(value));
      }
    }
    return result;
  }
  
  static final List<org.jooq.Field<?>> fields(Object[] values, org.jooq.Field<?> field)
  {
    List<org.jooq.Field<?>> result = new ArrayList();
    if ((values != null) && (field != null)) {
      for (int i = 0; i < values.length; i++) {
        result.add(field(values[i], field));
      }
    }
    return result;
  }
  
  static final List<org.jooq.Field<?>> fields(Object[] values, org.jooq.Field<?>[] fields)
  {
    List<org.jooq.Field<?>> result = new ArrayList();
    if ((values != null) && (fields != null)) {
      for (int i = 0; (i < values.length) && (i < fields.length); i++) {
        result.add(field(values[i], fields[i]));
      }
    }
    return result;
  }
  
  static final List<org.jooq.Field<?>> fields(Object[] values, Class<?> type)
  {
    List<org.jooq.Field<?>> result = new ArrayList();
    if ((values != null) && (type != null)) {
      for (int i = 0; i < values.length; i++) {
        result.add(field(values[i], type));
      }
    }
    return result;
  }
  
  static final List<org.jooq.Field<?>> fields(Object[] values, Class<?>[] types)
  {
    List<org.jooq.Field<?>> result = new ArrayList();
    if ((values != null) && (types != null)) {
      for (int i = 0; (i < values.length) && (i < types.length); i++) {
        result.add(field(values[i], types[i]));
      }
    }
    return result;
  }
  
  static final List<org.jooq.Field<?>> fields(Object[] values, DataType<?> type)
  {
    List<org.jooq.Field<?>> result = new ArrayList();
    if ((values != null) && (type != null)) {
      for (int i = 0; i < values.length; i++) {
        result.add(field(values[i], type));
      }
    }
    return result;
  }
  
  static final List<org.jooq.Field<?>> fields(Object[] values, DataType<?>[] types)
  {
    List<org.jooq.Field<?>> result = new ArrayList();
    if ((values != null) && (types != null)) {
      for (int i = 0; (i < values.length) && (i < types.length); i++) {
        result.add(field(values[i], types[i]));
      }
    }
    return result;
  }
  
  static final <T> List<org.jooq.Field<T>> inline(T[] values)
  {
    List<org.jooq.Field<T>> result = new ArrayList();
    if (values != null) {
      for (T value : values) {
        result.add(DSL.inline(value));
      }
    }
    return result;
  }
  
  static final List<org.jooq.Field<?>> unqualify(List<? extends org.jooq.Field<?>> fields)
  {
    QueryPartList<org.jooq.Field<?>> result = new QueryPartList();
    for (org.jooq.Field<?> field : fields) {
      result.add(DSL.fieldByName(new String[] { field.getName() }));
    }
    return result;
  }
  
  static final int indexOrFail(Row row, org.jooq.Field<?> field)
  {
    int result = row.indexOf(field);
    if (result < 0) {
      throw new IllegalArgumentException("Field (" + field + ") is not contained in Row " + row);
    }
    return result;
  }
  
  static final int indexOrFail(Row row, String fieldName)
  {
    int result = row.indexOf(fieldName);
    if (result < 0) {
      throw new IllegalArgumentException("Field (" + fieldName + ") is not contained in Row " + row);
    }
    return result;
  }
  
  static final int indexOrFail(RecordType<?> row, org.jooq.Field<?> field)
  {
    int result = row.indexOf(field);
    if (result < 0) {
      throw new IllegalArgumentException("Field (" + field + ") is not contained in RecordType " + row);
    }
    return result;
  }
  
  static final int indexOrFail(RecordType<?> row, String fieldName)
  {
    int result = row.indexOf(fieldName);
    if (result < 0) {
      throw new IllegalArgumentException("Field (" + fieldName + ") is not contained in RecordType " + row);
    }
    return result;
  }
  
  static final <T> T[] array(T... array)
  {
    return array;
  }
  
  static final <T> List<T> list(T... array)
  {
    return array == null ? Collections.emptyList() : Arrays.asList(array);
  }
  
  static final Map<org.jooq.Field<?>, Object> map(Record record)
  {
    Map<org.jooq.Field<?>, Object> result = new LinkedHashMap();
    int size = record.size();
    for (int i = 0; i < size; i++) {
      result.put(record.field(i), record.getValue(i));
    }
    return result;
  }
  
  static final <T> T first(Iterable<? extends T> iterable)
  {
    if (iterable == null) {
      return null;
    }
    Iterator<? extends T> iterator = iterable.iterator();
    if (iterator.hasNext()) {
      return (T)iterator.next();
    }
    return null;
  }
  
  static final <R extends Record> R filterOne(List<R> list)
    throws InvalidResultException
  {
    int size = list.size();
    if (size == 1) {
      return (Record)list.get(0);
    }
    if (size > 1) {
      throw new InvalidResultException("Too many rows selected : " + size);
    }
    return null;
  }
  
  static final <R extends Record> R fetchOne(Cursor<R> cursor)
    throws InvalidResultException
  {
    try
    {
      R record = cursor.fetchOne();
      if (cursor.hasNext()) {
        throw new InvalidResultException("Cursor returned more than one result");
      }
      return record;
    }
    finally
    {
      cursor.close();
    }
  }
  
  static final <C extends Context<? super C>> C visitAll(C ctx, Collection<? extends QueryPart> parts)
  {
    if (parts != null) {
      for (QueryPart part : parts) {
        ctx.visit(part);
      }
    }
    return ctx;
  }
  
  static final <C extends Context<? super C>> C visitAll(C ctx, QueryPart[] parts)
  {
    if (parts != null) {
      for (QueryPart part : parts) {
        ctx.visit(part);
      }
    }
    return ctx;
  }
  
  static final void renderAndBind(Context<?> ctx, String sql, List<QueryPart> substitutes)
  {
    RenderContext render = (RenderContext)((ctx instanceof RenderContext) ? ctx : null);
    BindContext bind = (BindContext)((ctx instanceof BindContext) ? ctx : null);
    
    int substituteIndex = 0;
    char[] sqlChars = sql.toCharArray();
    if (render == null) {
      render = new DefaultRenderContext(bind.configuration());
    }
    SQLDialect dialect = render.configuration().dialect();
    SQLDialect family = dialect.family();
    String[][] quotes = (String[][])Identifiers.QUOTES.get(family);
    
    boolean needsBackslashEscaping = needsBackslashEscaping(ctx.configuration());
    for (int i = 0; i < sqlChars.length; i++) {
      if (peek(sqlChars, i, "--"))
      {
        while ((i < sqlChars.length) && (sqlChars[i] != '\r') && (sqlChars[i] != '\n')) {
          render.sql(sqlChars[(i++)]);
        }
        if (i < sqlChars.length) {
          render.sql(sqlChars[i]);
        }
      }
      else if (peek(sqlChars, i, "/*"))
      {
        while (!peek(sqlChars, i, "*/")) {
          render.sql(sqlChars[(i++)]);
        }
        render.sql(sqlChars[(i++)]);
        render.sql(sqlChars[i]);
      }
      else if (sqlChars[i] == '\'')
      {
        render.sql(sqlChars[(i++)]);
        for (;;)
        {
          if ((sqlChars[i] == '\\') && (needsBackslashEscaping)) {
            render.sql(sqlChars[(i++)]);
          } else if (peek(sqlChars, i, "''")) {
            render.sql(sqlChars[(i++)]);
          } else {
            if (peek(sqlChars, i, "'")) {
              break;
            }
          }
          render.sql(sqlChars[(i++)]);
        }
        render.sql(sqlChars[i]);
      }
      else if (peekAny(sqlChars, i, quotes[0]))
      {
        int delimiter = 0;
        for (int d = 0; d < quotes[0].length; d++) {
          if (peek(sqlChars, i, quotes[0][d]))
          {
            delimiter = d;
            break;
          }
        }
        for (int d = 0; d < quotes[0][delimiter].length(); d++) {
          render.sql(sqlChars[(i++)]);
        }
        for (;;)
        {
          if (peek(sqlChars, i, quotes[2][delimiter])) {
            for (int d = 0; d < quotes[2][delimiter].length(); d++) {
              render.sql(sqlChars[(i++)]);
            }
          } else {
            if (peek(sqlChars, i, quotes[1][delimiter])) {
              break;
            }
          }
          render.sql(sqlChars[(i++)]);
        }
        for (int d = 0; d < quotes[1][delimiter].length(); d++)
        {
          if (d > 0) {
            i++;
          }
          render.sql(sqlChars[i]);
        }
      }
      else if ((sqlChars[i] == '?') && (substituteIndex < substitutes.size()))
      {
        QueryPart substitute = (QueryPart)substitutes.get(substituteIndex++);
        if ((render.paramType() == ParamType.INLINED) || (render.paramType() == ParamType.NAMED) || (render.paramType() == ParamType.NAMED_OR_INLINED)) {
          render.visit(substitute);
        } else {
          render.sql(sqlChars[i]);
        }
        if (bind != null) {
          bind.visit(substitute);
        }
      }
      else if (sqlChars[i] == '{')
      {
        if (peekAny(sqlChars, i, JDBC_ESCAPE_PREFIXES, true))
        {
          render.sql(sqlChars[i]);
        }
        else
        {
          i++;int start = i;
          while ((i < sqlChars.length) && (sqlChars[i] != '}')) {
            i++;
          }
          int end = i;
          
          String token = sql.substring(start, end);
          try
          {
            QueryPart substitute = (QueryPart)substitutes.get(Integer.valueOf(token).intValue());
            render.visit(substitute);
            if (bind != null) {
              bind.visit(substitute);
            }
          }
          catch (NumberFormatException e)
          {
            render.keyword(token);
          }
        }
      }
      else
      {
        render.sql(sqlChars[i]);
      }
    }
  }
  
  static final boolean needsBackslashEscaping(Configuration configuration)
  {
    BackslashEscaping escaping = SettingsTools.getBackslashEscaping(configuration.settings());
    return (escaping == BackslashEscaping.ON) || ((escaping == BackslashEscaping.DEFAULT) && (EnumSet.of(SQLDialect.MARIADB, SQLDialect.MYSQL).contains(configuration.dialect().family())));
  }
  
  static final boolean peek(char[] sqlChars, int index, String peek)
  {
    return peek(sqlChars, index, peek, false);
  }
  
  static final boolean peek(char[] sqlChars, int index, String peek, boolean anyWhitespace)
  {
    char[] peekArray = peek.toCharArray();
    label96:
    for (int i = 0; i < peekArray.length; i++)
    {
      if (index + i >= sqlChars.length) {
        return false;
      }
      if (sqlChars[(index + i)] != peekArray[i])
      {
        if ((anyWhitespace) && (peekArray[i] == ' ')) {
          for (int j = 0; j < " \t\n\013\f\r".length(); j++) {
            if (sqlChars[(index + i)] == " \t\n\013\f\r".charAt(j)) {
              break label96;
            }
          }
        }
        return false;
      }
    }
    return true;
  }
  
  static final boolean peekAny(char[] sqlChars, int index, String[] peekAny)
  {
    return peekAny(sqlChars, index, peekAny, false);
  }
  
  static final boolean peekAny(char[] sqlChars, int index, String[] peekAny, boolean anyWhitespace)
  {
    for (String peek : peekAny) {
      if (peek(sqlChars, index, peek, anyWhitespace)) {
        return true;
      }
    }
    return false;
  }
  
  static final List<QueryPart> queryParts(Object... substitutes)
  {
    if (substitutes == null) {
      return queryParts(new Object[] { null });
    }
    List<QueryPart> result = new ArrayList();
    for (Object substitute : substitutes) {
      if ((substitute instanceof QueryPart))
      {
        result.add((QueryPart)substitute);
      }
      else
      {
        Class<Object> type = substitute != null ? substitute.getClass() : Object.class;
        result.add(new Val(substitute, DSL.getDataType(type)));
      }
    }
    return result;
  }
  
  static final void fieldNames(Context<?> context, Fields<?> fields)
  {
    fieldNames(context, list(fields.fields));
  }
  
  static final void fieldNames(Context<?> context, org.jooq.Field<?>... fields)
  {
    fieldNames(context, list(fields));
  }
  
  static final void fieldNames(Context<?> context, Collection<? extends org.jooq.Field<?>> list)
  {
    String separator = "";
    for (org.jooq.Field<?> field : list)
    {
      context.sql(separator).literal(field.getName());
      
      separator = ", ";
    }
  }
  
  static final void tableNames(Context<?> context, org.jooq.Table<?>... list)
  {
    tableNames(context, list(list));
  }
  
  static final void tableNames(Context<?> context, Collection<? extends org.jooq.Table<?>> list)
  {
    String separator = "";
    for (org.jooq.Table<?> table : list)
    {
      context.sql(separator).literal(table.getName());
      
      separator = ", ";
    }
  }
  
  static final <T> T[] combine(T[] array, T value)
  {
    T[] result = (Object[])Array.newInstance(array.getClass().getComponentType(), array.length + 1);
    
    System.arraycopy(array, 0, result, 0, array.length);
    result[array.length] = value;
    
    return result;
  }
  
  static final org.jooq.Field<?>[] combine(org.jooq.Field<?> field, org.jooq.Field<?>... fields)
  {
    if (fields == null) {
      return new org.jooq.Field[] { field };
    }
    org.jooq.Field<?>[] result = new org.jooq.Field[fields.length + 1];
    result[0] = field;
    System.arraycopy(fields, 0, result, 1, fields.length);
    
    return result;
  }
  
  static final org.jooq.Field<?>[] combine(org.jooq.Field<?> field1, org.jooq.Field<?> field2, org.jooq.Field<?>... fields)
  {
    if (fields == null) {
      return new org.jooq.Field[] { field1, field2 };
    }
    org.jooq.Field<?>[] result = new org.jooq.Field[fields.length + 2];
    result[0] = field1;
    result[1] = field2;
    System.arraycopy(fields, 0, result, 2, fields.length);
    
    return result;
  }
  
  static final org.jooq.Field<?>[] combine(org.jooq.Field<?> field1, org.jooq.Field<?> field2, org.jooq.Field<?> field3, org.jooq.Field<?>... fields)
  {
    if (fields == null) {
      return new org.jooq.Field[] { field1, field2, field3 };
    }
    org.jooq.Field<?>[] result = new org.jooq.Field[fields.length + 3];
    result[0] = field1;
    result[1] = field2;
    result[2] = field3;
    System.arraycopy(fields, 0, result, 3, fields.length);
    return result;
  }
  
  static final DataAccessException translate(String sql, SQLException e)
  {
    String message = "SQL [" + sql + "]; " + e.getMessage();
    return new DataAccessException(message, e);
  }
  
  static final void safeClose(ExecuteListener listener, ExecuteContext ctx)
  {
    safeClose(listener, ctx, false);
  }
  
  static final void safeClose(ExecuteListener listener, ExecuteContext ctx, boolean keepStatement)
  {
    safeClose(listener, ctx, keepStatement, true);
  }
  
  static final void safeClose(ExecuteListener listener, ExecuteContext ctx, boolean keepStatement, boolean keepResultSet)
  {
    JDBCUtils.safeClose(ctx.resultSet());
    ctx.resultSet(null);
    
    PreparedStatement statement = ctx.statement();
    if (statement != null) {
      consumeWarnings(ctx, listener);
    }
    if (!keepStatement) {
      if (statement != null)
      {
        JDBCUtils.safeClose(statement);
        ctx.statement(null);
      }
      else
      {
        Connection connection = DefaultExecuteContext.localConnection();
        if (connection != null) {
          ctx.configuration().connectionProvider().release(connection);
        }
      }
    }
    if (keepResultSet) {
      listener.end(ctx);
    }
    DefaultExecuteContext.clean();
  }
  
  static final <T> void setValue(Record target, org.jooq.Field<T> targetField, Record source, org.jooq.Field<?> sourceField)
  {
    setValue(target, targetField, source.getValue(sourceField));
  }
  
  static final <T> void setValue(Record target, org.jooq.Field<T> targetField, Object value)
  {
    target.setValue(targetField, targetField.getDataType().convert(value));
  }
  
  static final <T> void copyValue(AbstractRecord target, org.jooq.Field<T> targetField, Record source, org.jooq.Field<?> sourceField)
  {
    DataType<T> targetType = targetField.getDataType();
    
    int targetIndex = indexOrFail(target.fieldsRow(), targetField);
    int sourceIndex = indexOrFail(source.fieldsRow(), sourceField);
    
    target.values[targetIndex] = targetType.convert(source.getValue(sourceIndex));
    target.originals[targetIndex] = targetType.convert(source.original(sourceIndex));
    target.changed.set(targetIndex, source.changed(sourceIndex));
  }
  
  static final Schema getMappedSchema(Configuration configuration, Schema schema)
  {
    SchemaMapping mapping = configuration.schemaMapping();
    if (mapping != null) {
      return mapping.map(schema);
    }
    return schema;
  }
  
  static final <R extends Record> org.jooq.Table<R> getMappedTable(Configuration configuration, org.jooq.Table<R> table)
  {
    SchemaMapping mapping = configuration.schemaMapping();
    if (mapping != null) {
      return mapping.map(table);
    }
    return table;
  }
  
  static final int hash(Object object)
  {
    return 0x7FFFFFF & object.hashCode();
  }
  
  static final org.jooq.Field<String> escapeForLike(Object value)
  {
    return escapeForLike(value, new DefaultConfiguration());
  }
  
  static final org.jooq.Field<String> escapeForLike(Object value, Configuration configuration)
  {
    if ((value != null) && (value.getClass() == String.class)) {
      return DSL.val(DSL.escape("" + value, '!'));
    }
    return DSL.val("" + value);
  }
  
  static final org.jooq.Field<String> escapeForLike(org.jooq.Field<?> field)
  {
    return escapeForLike(field, new DefaultConfiguration());
  }
  
  static final org.jooq.Field<String> escapeForLike(org.jooq.Field<?> field, Configuration configuration)
  {
    if (DSL.nullSafe(field).getDataType().isString()) {
      return DSL.escape(field, '!');
    }
    return field.cast(String.class);
  }
  
  static final boolean isVal(org.jooq.Field<?> field)
  {
    return field instanceof Param;
  }
  
  static final <T> T extractVal(org.jooq.Field<T> field)
  {
    if (isVal(field)) {
      return (T)((Param)field).getValue();
    }
    return null;
  }
  
  static final void addConditions(ConditionProvider query, Record record, org.jooq.Field<?>... keys)
  {
    for (org.jooq.Field<?> field : keys) {
      addCondition(query, record, field);
    }
  }
  
  static final <T> void addCondition(ConditionProvider provider, Record record, org.jooq.Field<T> field)
  {
    if (SettingsTools.updatablePrimaryKeys(settings(record))) {
      provider.addConditions(new Condition[] { condition(field, record.original(field)) });
    } else {
      provider.addConditions(new Condition[] { condition(field, record.getValue(field)) });
    }
  }
  
  static final <T> Condition condition(org.jooq.Field<T> field, T value)
  {
    return value == null ? field.isNull() : field.eq(value);
  }
  
  static class Cache
  {
    static final <V> V run(Configuration configuration, CachedOperation<V> operation, String type, Object... keys)
    {
      if (configuration == null) {
        configuration = new DefaultConfiguration();
      }
      if (!SettingsTools.reflectionCaching(configuration.settings())) {
        return (V)operation.call();
      }
      Map<Object, Object> cache = (Map)configuration.data(type);
      if (cache == null) {
        synchronized (type)
        {
          cache = (Map)configuration.data(type);
          if (cache == null)
          {
            cache = new ConcurrentHashMap();
            configuration.data(type, cache);
          }
        }
      }
      Object key = key(keys);
      Object result = cache.get(key);
      if (result == null) {
        synchronized (cache)
        {
          result = cache.get(key);
          if (result == null)
          {
            result = operation.call();
            cache.put(key, result == null ? NULL : result);
          }
        }
      }
      return result == NULL ? null : result;
    }
    
    private static final Object NULL = new Object();
    
    private static final Object key(Object... key)
    {
      if ((key == null) || (key.length == 0)) {
        return key;
      }
      if (key.length == 1) {
        return key[0];
      }
      return new Key(key);
    }
    
    private static class Key
    {
      private final Object[] key;
      
      Key(Object[] key)
      {
        this.key = key;
      }
      
      public int hashCode()
      {
        return Arrays.hashCode(this.key);
      }
      
      public boolean equals(Object obj)
      {
        if ((obj instanceof Key)) {
          return Arrays.equals(this.key, ((Key)obj).key);
        }
        return false;
      }
      
      public String toString()
      {
        return Arrays.asList(this.key).toString();
      }
    }
    
    static abstract interface CachedOperation<V>
    {
      public abstract V call();
    }
  }
  
  private static final boolean isJPAAvailable()
  {
    if (isJPAAvailable == null) {
      try
      {
        Class.forName(Column.class.getName());
        isJPAAvailable = Boolean.valueOf(true);
      }
      catch (Throwable e)
      {
        isJPAAvailable = Boolean.valueOf(false);
      }
    }
    return isJPAAvailable.booleanValue();
  }
  
  static final boolean hasColumnAnnotations(Configuration configuration, Class<?> type)
  {
    ((Boolean)Cache.run(configuration, new Utils.Cache.CachedOperation()
    {
      public Boolean call()
      {
        if (!Utils.access$000()) {
          return Boolean.valueOf(false);
        }
        if ((this.val$type.getAnnotation(Entity.class) != null) || 
          (this.val$type.getAnnotation(javax.persistence.Table.class) != null)) {
          return Boolean.valueOf(true);
        }
        for (java.lang.reflect.Field member : Utils.getInstanceMembers(this.val$type)) {
          if (member.getAnnotation(Column.class) != null) {
            return Boolean.valueOf(true);
          }
        }
        for (Method method : Utils.getInstanceMethods(this.val$type)) {
          if (method.getAnnotation(Column.class) != null) {
            return Boolean.valueOf(true);
          }
        }
        return Boolean.valueOf(false);
      }
    }, DATA_REFLECTION_CACHE_HAS_COLUMN_ANNOTATIONS, new Object[] { type })).booleanValue();
  }
  
  static final List<java.lang.reflect.Field> getAnnotatedMembers(Configuration configuration, Class<?> type, final String name)
  {
    (List)Cache.run(configuration, new Utils.Cache.CachedOperation()
    {
      public List<java.lang.reflect.Field> call()
      {
        List<java.lang.reflect.Field> result = new ArrayList();
        for (java.lang.reflect.Field member : Utils.getInstanceMembers(this.val$type))
        {
          Column annotation = (Column)member.getAnnotation(Column.class);
          if ((annotation != null) && 
            (name.equals(annotation.name()))) {
            result.add(Reflect.accessible(member));
          }
        }
        return result;
      }
    }, DATA_REFLECTION_CACHE_GET_ANNOTATED_MEMBERS, new Object[] { type, name });
  }
  
  static final List<java.lang.reflect.Field> getMatchingMembers(Configuration configuration, final Class<?> type, String name)
  {
    (List)Cache.run(configuration, new Utils.Cache.CachedOperation()
    {
      public List<java.lang.reflect.Field> call()
      {
        List<java.lang.reflect.Field> result = new ArrayList();
        
        String camelCaseLC = StringUtils.toCamelCaseLC(this.val$name);
        for (java.lang.reflect.Field member : Utils.getInstanceMembers(type)) {
          if (this.val$name.equals(member.getName())) {
            result.add(Reflect.accessible(member));
          } else if (camelCaseLC.equals(member.getName())) {
            result.add(Reflect.accessible(member));
          }
        }
        return result;
      }
    }, DATA_REFLECTION_CACHE_GET_MATCHING_MEMBERS, new Object[] { type, name });
  }
  
  static final List<Method> getAnnotatedSetters(Configuration configuration, Class<?> type, final String name)
  {
    (List)Cache.run(configuration, new Utils.Cache.CachedOperation()
    {
      public List<Method> call()
      {
        List<Method> result = new ArrayList();
        for (Method method : Utils.getInstanceMethods(this.val$type))
        {
          Column annotation = (Column)method.getAnnotation(Column.class);
          if ((annotation != null) && (name.equals(annotation.name()))) {
            if (method.getParameterTypes().length == 1)
            {
              result.add(Reflect.accessible(method));
            }
            else if (method.getParameterTypes().length == 0)
            {
              String m = method.getName();
              
              String suffix = m.startsWith("is") ? m.substring(2) : m.startsWith("get") ? m.substring(3) : null;
              if (suffix != null) {
                try
                {
                  Method setter = this.val$type.getMethod("set" + suffix, new Class[] { method.getReturnType() });
                  if (setter.getAnnotation(Column.class) == null) {
                    result.add(Reflect.accessible(setter));
                  }
                }
                catch (NoSuchMethodException localNoSuchMethodException) {}
              }
            }
          }
        }
        return result;
      }
    }, DATA_REFLECTION_CACHE_GET_ANNOTATED_SETTERS, new Object[] { type, name });
  }
  
  static final Method getAnnotatedGetter(Configuration configuration, Class<?> type, final String name)
  {
    (Method)Cache.run(configuration, new Utils.Cache.CachedOperation()
    {
      public Method call()
      {
        for (Method method : Utils.getInstanceMethods(this.val$type))
        {
          Column annotation = (Column)method.getAnnotation(Column.class);
          if ((annotation != null) && (name.equals(annotation.name())))
          {
            if (method.getParameterTypes().length == 0) {
              return (Method)Reflect.accessible(method);
            }
            if (method.getParameterTypes().length == 1)
            {
              String m = method.getName();
              if (m.startsWith("set"))
              {
                try
                {
                  Method getter = this.val$type.getMethod("get" + m.substring(3), new Class[0]);
                  if (getter.getAnnotation(Column.class) == null) {
                    return (Method)Reflect.accessible(getter);
                  }
                }
                catch (NoSuchMethodException localNoSuchMethodException) {}
                try
                {
                  Method getter = this.val$type.getMethod("is" + m.substring(3), new Class[0]);
                  if (getter.getAnnotation(Column.class) == null) {
                    return (Method)Reflect.accessible(getter);
                  }
                }
                catch (NoSuchMethodException localNoSuchMethodException1) {}
              }
            }
          }
        }
        return null;
      }
    }, DATA_REFLECTION_CACHE_GET_ANNOTATED_GETTER, new Object[] { type, name });
  }
  
  static final List<Method> getMatchingSetters(Configuration configuration, final Class<?> type, String name)
  {
    (List)Cache.run(configuration, new Utils.Cache.CachedOperation()
    {
      public List<Method> call()
      {
        List<Method> result = new ArrayList();
        
        String camelCase = StringUtils.toCamelCase(this.val$name);
        String camelCaseLC = StringUtils.toLC(camelCase);
        for (Method method : Utils.getInstanceMethods(type))
        {
          Class<?>[] parameterTypes = method.getParameterTypes();
          if (parameterTypes.length == 1) {
            if (this.val$name.equals(method.getName())) {
              result.add(Reflect.accessible(method));
            } else if (camelCaseLC.equals(method.getName())) {
              result.add(Reflect.accessible(method));
            } else if (("set" + this.val$name).equals(method.getName())) {
              result.add(Reflect.accessible(method));
            } else if (("set" + camelCase).equals(method.getName())) {
              result.add(Reflect.accessible(method));
            }
          }
        }
        return result;
      }
    }, DATA_REFLECTION_CACHE_GET_MATCHING_SETTERS, new Object[] { type, name });
  }
  
  static final Method getMatchingGetter(Configuration configuration, final Class<?> type, String name)
  {
    (Method)Cache.run(configuration, new Utils.Cache.CachedOperation()
    {
      public Method call()
      {
        String camelCase = StringUtils.toCamelCase(this.val$name);
        String camelCaseLC = StringUtils.toLC(camelCase);
        for (Method method : Utils.getInstanceMethods(type)) {
          if (method.getParameterTypes().length == 0)
          {
            if (this.val$name.equals(method.getName())) {
              return (Method)Reflect.accessible(method);
            }
            if (camelCaseLC.equals(method.getName())) {
              return (Method)Reflect.accessible(method);
            }
            if (("get" + this.val$name).equals(method.getName())) {
              return (Method)Reflect.accessible(method);
            }
            if (("get" + camelCase).equals(method.getName())) {
              return (Method)Reflect.accessible(method);
            }
            if (("is" + this.val$name).equals(method.getName())) {
              return (Method)Reflect.accessible(method);
            }
            if (("is" + camelCase).equals(method.getName())) {
              return (Method)Reflect.accessible(method);
            }
          }
        }
        return null;
      }
    }, DATA_REFLECTION_CACHE_GET_MATCHING_GETTER, new Object[] { type, name });
  }
  
  private static final List<Method> getInstanceMethods(Class<?> type)
  {
    List<Method> result = new ArrayList();
    for (Method method : type.getMethods()) {
      if ((method.getModifiers() & 0x8) == 0) {
        result.add(method);
      }
    }
    return result;
  }
  
  private static final List<java.lang.reflect.Field> getInstanceMembers(Class<?> type)
  {
    List<java.lang.reflect.Field> result = new ArrayList();
    for (java.lang.reflect.Field field : type.getFields()) {
      if ((field.getModifiers() & 0x8) == 0) {
        result.add(field);
      }
    }
    return result;
  }
  
  static final String getPropertyName(String methodName)
  {
    String name = methodName;
    if ((name.startsWith("is")) && (name.length() > 2)) {
      name = name.substring(2, 3).toLowerCase() + name.substring(3);
    } else if ((name.startsWith("get")) && (name.length() > 3)) {
      name = name.substring(3, 4).toLowerCase() + name.substring(4);
    } else if ((name.startsWith("set")) && (name.length() > 3)) {
      name = name.substring(3, 4).toLowerCase() + name.substring(4);
    }
    return name;
  }
  
  static final void consumeExceptions(Configuration configuration, PreparedStatement stmt, SQLException previous) {}
  
  static final void consumeWarnings(ExecuteContext ctx, ExecuteListener listener)
  {
    if (!Boolean.FALSE.equals(ctx.settings().isFetchWarnings())) {
      try
      {
        ctx.sqlWarning(ctx.statement().getWarnings());
      }
      catch (SQLException e)
      {
        ctx.sqlWarning(new SQLWarning("Could not fetch SQLWarning", e));
      }
    }
    if (ctx.sqlWarning() != null) {
      listener.warning(ctx);
    }
  }
  
  static void consumeResultSets(ExecuteContext ctx, ExecuteListener listener, List<Result<Record>> results, Intern intern)
    throws SQLException
  {
    boolean anyResults = false;
    while (ctx.resultSet() != null)
    {
      anyResults = true;
      
      org.jooq.Field<?>[] fields = new MetaDataFieldProvider(ctx.configuration(), ctx.resultSet().getMetaData()).getFields();
      Cursor<Record> c = new CursorImpl(ctx, listener, fields, intern != null ? intern.internIndexes(fields) : null, true, false);
      results.add(c.fetch());
      if (ctx.statement().getMoreResults()) {
        ctx.resultSet(ctx.statement().getResultSet());
      } else {
        ctx.resultSet(null);
      }
    }
    if (anyResults) {
      ctx.statement().getMoreResults(3);
    }
  }
  
  static List<String[]> parseTXT(String string, String nullLiteral)
  {
    String[] strings = string.split("[\\r\\n]+");
    if (strings.length < 2) {
      throw new DataAccessException("String must contain at least two lines");
    }
    boolean formatted = string.charAt(0) == '+';
    if (formatted) {
      return parseTXTLines(nullLiteral, strings, PLUS_PATTERN, 0, 1, 3, strings.length - 1);
    }
    return parseTXTLines(nullLiteral, strings, DASH_PATTERN, 1, 0, 2, strings.length);
  }
  
  private static List<String[]> parseTXTLines(String nullLiteral, String[] strings, Pattern pattern, int matchLine, int headerLine, int dataLineStart, int dataLineEnd)
  {
    List<int[]> positions = new ArrayList();
    Matcher m = pattern.matcher(strings[matchLine]);
    while (m.find()) {
      positions.add(new int[] { m.start(1), m.end(1) });
    }
    List<String[]> result = new ArrayList();
    parseTXTLine(positions, result, strings[headerLine], nullLiteral);
    for (int j = dataLineStart; j < dataLineEnd; j++) {
      parseTXTLine(positions, result, strings[j], nullLiteral);
    }
    return result;
  }
  
  private static void parseTXTLine(List<int[]> positions, List<String[]> result, String string, String nullLiteral)
  {
    String[] fields = new String[positions.size()];
    result.add(fields);
    int length = string.length();
    for (int i = 0; i < fields.length; i++)
    {
      int[] position = (int[])positions.get(i);
      if (position[0] < length) {
        fields[i] = string.substring(position[0], Math.min(position[1], length)).trim();
      } else {
        fields[i] = null;
      }
      if (StringUtils.equals(fields[i], nullLiteral)) {
        fields[i] = null;
      }
    }
  }
  
  static void executeImmediateBegin(Context<?> ctx, DropStatementType type)
  {
    switch (ctx.family())
    {
    case FIREBIRD: 
      ctx.keyword("execute block").formatSeparator().keyword("as").formatSeparator().keyword("begin").formatIndentStart().formatSeparator().keyword("execute statement").sql(" '");
      
      break;
    }
  }
  
  static void executeImmediateEnd(Context<?> ctx, DropStatementType type)
  {
    switch (ctx.family())
    {
    case FIREBIRD: 
      ctx.sql("';").formatSeparator().keyword("when").sql(" sqlcode -607 ").keyword("do").formatIndentStart().formatSeparator().keyword("begin end").formatIndentEnd().formatIndentEnd().formatSeparator().keyword("end");
      
      break;
    }
  }
}

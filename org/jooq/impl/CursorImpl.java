package org.jooq.impl;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.jooq.Binding;
import org.jooq.Cursor;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.RecordType;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Table;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBC41ResultSet;
import org.jooq.tools.jdbc.JDBCUtils;

class CursorImpl<R extends Record>
  implements Cursor<R>
{
  private static final JooqLogger log = JooqLogger.getLogger(CursorImpl.class);
  private final ExecuteContext ctx;
  private final ExecuteListener listener;
  private final Field<?>[] fields;
  private final boolean[] intern;
  private final boolean keepResultSet;
  private final boolean keepStatement;
  private final Class<? extends R> type;
  private boolean isClosed;
  private transient CursorImpl<R>.CursorResultSet rs;
  private transient DefaultBindingGetResultSetContext<?> rsContext;
  private transient Iterator<R> iterator;
  private transient int rows;
  
  CursorImpl(ExecuteContext ctx, ExecuteListener listener, Field<?>[] fields, int[] internIndexes, boolean keepStatement, boolean keepResultSet)
  {
    this(ctx, listener, fields, internIndexes, keepStatement, keepResultSet, RecordImpl.class);
  }
  
  CursorImpl(ExecuteContext ctx, ExecuteListener listener, Field<?>[] fields, int[] internIndexes, boolean keepStatement, boolean keepResultSet, Class<? extends R> type)
  {
    this.ctx = ctx;
    this.listener = (listener != null ? listener : new ExecuteListeners(ctx));
    this.fields = fields;
    this.type = type;
    this.keepStatement = keepStatement;
    this.keepResultSet = keepResultSet;
    this.rs = new CursorResultSet();
    this.rsContext = new DefaultBindingGetResultSetContext(ctx.configuration(), this.rs, 0);
    this.intern = new boolean[fields.length];
    if (internIndexes != null) {
      for (int i : internIndexes) {
        this.intern[i] = true;
      }
    }
  }
  
  public final RecordType<R> recordType()
  {
    return new RowImpl(this.fields).fields;
  }
  
  public final Row fieldsRow()
  {
    return new RowImpl(this.fields);
  }
  
  public final <T> Field<T> field(Field<T> field)
  {
    return fieldsRow().field(field);
  }
  
  public final Field<?> field(String name)
  {
    return fieldsRow().field(name);
  }
  
  public final Field<?> field(int index)
  {
    return (index >= 0) && (index < this.fields.length) ? this.fields[index] : null;
  }
  
  public final Field<?>[] fields()
  {
    return (Field[])this.fields.clone();
  }
  
  public final Iterator<R> iterator()
  {
    if (this.iterator == null)
    {
      this.iterator = new CursorIterator();
      this.listener.fetchStart(this.ctx);
    }
    return this.iterator;
  }
  
  public final boolean hasNext()
  {
    return iterator().hasNext();
  }
  
  public final Result<R> fetch()
  {
    return fetch(Integer.MAX_VALUE);
  }
  
  public final R fetchOne()
  {
    Result<R> result = fetch(1);
    if (result.size() == 1) {
      return (Record)result.get(0);
    }
    return null;
  }
  
  public final Result<R> fetch(int number)
  {
    iterator();
    
    ResultImpl<R> result = new ResultImpl(this.ctx.configuration(), this.fields);
    
    this.ctx.result(result);
    this.listener.resultStart(this.ctx);
    for (int i = 0; (i < number) && (iterator().hasNext()); i++) {
      result.addRecord((Record)iterator().next());
    }
    this.ctx.result(result);
    this.listener.resultEnd(this.ctx);
    
    return result;
  }
  
  public final <H extends RecordHandler<? super R>> H fetchOneInto(H handler)
  {
    handler.next(fetchOne());
    return handler;
  }
  
  public final <H extends RecordHandler<? super R>> H fetchInto(H handler)
  {
    while (hasNext()) {
      fetchOneInto(handler);
    }
    return handler;
  }
  
  public final <E> E fetchOne(RecordMapper<? super R, E> mapper)
  {
    R record = fetchOne();
    return record == null ? null : mapper.map(record);
  }
  
  public final <E> List<E> fetch(RecordMapper<? super R, E> mapper)
  {
    return fetch().map(mapper);
  }
  
  public final <E> E fetchOneInto(Class<? extends E> clazz)
  {
    R record = fetchOne();
    return record == null ? null : record.into(clazz);
  }
  
  public final <E> List<E> fetchInto(Class<? extends E> clazz)
  {
    return fetch().into(clazz);
  }
  
  public final <Z extends Record> Z fetchOneInto(Table<Z> table)
  {
    return fetchOne().into(table);
  }
  
  public final <Z extends Record> Result<Z> fetchInto(Table<Z> table)
  {
    return fetch().into(table);
  }
  
  public final void close()
  {
    JDBCUtils.safeClose(this.rs);
    this.rs = null;
    this.isClosed = true;
  }
  
  public final boolean isClosed()
  {
    return this.isClosed;
  }
  
  public final ResultSet resultSet()
  {
    return this.rs;
  }
  
  final class CursorResultSet
    extends JDBC41ResultSet
    implements ResultSet
  {
    CursorResultSet() {}
    
    public final <T> T unwrap(Class<T> iface)
      throws SQLException
    {
      return (T)CursorImpl.this.ctx.resultSet().unwrap(iface);
    }
    
    public final boolean isWrapperFor(Class<?> iface)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().isWrapperFor(iface);
    }
    
    public final Statement getStatement()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getStatement();
    }
    
    public final SQLWarning getWarnings()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getWarnings();
    }
    
    public final void clearWarnings()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().clearWarnings();
    }
    
    public final String getCursorName()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getCursorName();
    }
    
    public final ResultSetMetaData getMetaData()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getMetaData();
    }
    
    public final int findColumn(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().findColumn(columnLabel);
    }
    
    public final void setFetchDirection(int direction)
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().setFetchDirection(direction);
    }
    
    public final int getFetchDirection()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getFetchDirection();
    }
    
    public final void setFetchSize(int rows)
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().setFetchSize(rows);
    }
    
    public final int getFetchSize()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getFetchSize();
    }
    
    public final int getType()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getType();
    }
    
    public final int getConcurrency()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getConcurrency();
    }
    
    public final int getHoldability()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getHoldability();
    }
    
    public final boolean isBeforeFirst()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().isBeforeFirst();
    }
    
    public final boolean isAfterLast()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().isAfterLast();
    }
    
    public final boolean isFirst()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().isFirst();
    }
    
    public final boolean isLast()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().isLast();
    }
    
    public final boolean next()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().next();
    }
    
    public final boolean previous()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().previous();
    }
    
    public final void beforeFirst()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().beforeFirst();
    }
    
    public final void afterLast()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().afterLast();
    }
    
    public final boolean first()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().first();
    }
    
    public final boolean last()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().last();
    }
    
    public final int getRow()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getRow();
    }
    
    public final boolean absolute(int row)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().absolute(row);
    }
    
    public final boolean relative(int r)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().relative(r);
    }
    
    public final void moveToInsertRow()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().moveToInsertRow();
    }
    
    public final void moveToCurrentRow()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().moveToCurrentRow();
    }
    
    public final void close()
      throws SQLException
    {
      CursorImpl.this.ctx.rows(CursorImpl.this.rows);
      CursorImpl.this.listener.fetchEnd(CursorImpl.this.ctx);
      
      Utils.safeClose(CursorImpl.this.listener, CursorImpl.this.ctx, CursorImpl.this.keepStatement, CursorImpl.this.keepResultSet);
    }
    
    public final boolean isClosed()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().isClosed();
    }
    
    public final boolean wasNull()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().wasNull();
    }
    
    public final Array getArray(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getArray(columnIndex);
    }
    
    public final Array getArray(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getArray(columnLabel);
    }
    
    public final InputStream getAsciiStream(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getAsciiStream(columnIndex);
    }
    
    public final InputStream getAsciiStream(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getAsciiStream(columnLabel);
    }
    
    public final BigDecimal getBigDecimal(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBigDecimal(columnIndex);
    }
    
    @Deprecated
    public final BigDecimal getBigDecimal(int columnIndex, int scale)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBigDecimal(columnIndex, scale);
    }
    
    public final BigDecimal getBigDecimal(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBigDecimal(columnLabel);
    }
    
    @Deprecated
    public final BigDecimal getBigDecimal(String columnLabel, int scale)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBigDecimal(columnLabel, scale);
    }
    
    public final InputStream getBinaryStream(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBinaryStream(columnIndex);
    }
    
    public final InputStream getBinaryStream(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBinaryStream(columnLabel);
    }
    
    public final Blob getBlob(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBlob(columnIndex);
    }
    
    public final Blob getBlob(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBlob(columnLabel);
    }
    
    public final boolean getBoolean(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBoolean(columnIndex);
    }
    
    public final boolean getBoolean(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBoolean(columnLabel);
    }
    
    public final byte getByte(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getByte(columnIndex);
    }
    
    public final byte getByte(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getByte(columnLabel);
    }
    
    public final byte[] getBytes(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBytes(columnIndex);
    }
    
    public final byte[] getBytes(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getBytes(columnLabel);
    }
    
    public final Reader getCharacterStream(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getCharacterStream(columnIndex);
    }
    
    public final Reader getCharacterStream(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getCharacterStream(columnLabel);
    }
    
    public final Clob getClob(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getClob(columnIndex);
    }
    
    public final Clob getClob(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getClob(columnLabel);
    }
    
    public final Date getDate(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getDate(columnIndex);
    }
    
    public final Date getDate(int columnIndex, Calendar cal)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getDate(columnIndex, cal);
    }
    
    public final Date getDate(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getDate(columnLabel);
    }
    
    public final Date getDate(String columnLabel, Calendar cal)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getDate(columnLabel, cal);
    }
    
    public final double getDouble(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getDouble(columnIndex);
    }
    
    public final double getDouble(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getDouble(columnLabel);
    }
    
    public final float getFloat(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getFloat(columnIndex);
    }
    
    public final float getFloat(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getFloat(columnLabel);
    }
    
    public final int getInt(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getInt(columnIndex);
    }
    
    public final int getInt(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getInt(columnLabel);
    }
    
    public final long getLong(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getLong(columnIndex);
    }
    
    public final long getLong(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getLong(columnLabel);
    }
    
    public final Reader getNCharacterStream(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getNCharacterStream(columnIndex);
    }
    
    public final Reader getNCharacterStream(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getNCharacterStream(columnLabel);
    }
    
    public final NClob getNClob(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getNClob(columnIndex);
    }
    
    public final NClob getNClob(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getNClob(columnLabel);
    }
    
    public final String getNString(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getNString(columnIndex);
    }
    
    public final String getNString(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getNString(columnLabel);
    }
    
    public final Object getObject(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getObject(columnIndex);
    }
    
    public final Object getObject(int columnIndex, Map<String, Class<?>> map)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getObject(columnIndex, map);
    }
    
    public final Object getObject(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getObject(columnLabel);
    }
    
    public final Object getObject(String columnLabel, Map<String, Class<?>> map)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getObject(columnLabel, map);
    }
    
    public final Ref getRef(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getRef(columnIndex);
    }
    
    public final Ref getRef(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getRef(columnLabel);
    }
    
    public final RowId getRowId(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getRowId(columnIndex);
    }
    
    public final RowId getRowId(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getRowId(columnLabel);
    }
    
    public final short getShort(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getShort(columnIndex);
    }
    
    public final short getShort(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getShort(columnLabel);
    }
    
    public final SQLXML getSQLXML(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getSQLXML(columnIndex);
    }
    
    public final SQLXML getSQLXML(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getSQLXML(columnLabel);
    }
    
    public final String getString(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getString(columnIndex);
    }
    
    public final String getString(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getString(columnLabel);
    }
    
    public final Time getTime(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getTime(columnIndex);
    }
    
    public final Time getTime(int columnIndex, Calendar cal)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getTime(columnIndex, cal);
    }
    
    public final Time getTime(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getTime(columnLabel);
    }
    
    public final Time getTime(String columnLabel, Calendar cal)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getTime(columnLabel, cal);
    }
    
    public final Timestamp getTimestamp(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getTimestamp(columnIndex);
    }
    
    public final Timestamp getTimestamp(int columnIndex, Calendar cal)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getTimestamp(columnIndex, cal);
    }
    
    public final Timestamp getTimestamp(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getTimestamp(columnLabel);
    }
    
    public final Timestamp getTimestamp(String columnLabel, Calendar cal)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getTimestamp(columnLabel, cal);
    }
    
    @Deprecated
    public final InputStream getUnicodeStream(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getUnicodeStream(columnIndex);
    }
    
    @Deprecated
    public final InputStream getUnicodeStream(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getUnicodeStream(columnLabel);
    }
    
    public final URL getURL(int columnIndex)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getURL(columnIndex);
    }
    
    public final URL getURL(String columnLabel)
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().getURL(columnLabel);
    }
    
    private final void logUpdate(int columnIndex, Object x)
      throws SQLException
    {
      if (CursorImpl.log.isDebugEnabled()) {
        CursorImpl.log.debug("Updating Result", "Updating Result position " + getRow() + ":" + columnIndex + " with value " + x);
      }
    }
    
    private final void logUpdate(String columnLabel, Object x)
      throws SQLException
    {
      if (CursorImpl.log.isDebugEnabled()) {
        CursorImpl.log.debug("Updating Result", "Updating Result position " + getRow() + ":" + columnLabel + " with value " + x);
      }
    }
    
    public final boolean rowUpdated()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().rowUpdated();
    }
    
    public final boolean rowInserted()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().rowInserted();
    }
    
    public final boolean rowDeleted()
      throws SQLException
    {
      return CursorImpl.this.ctx.resultSet().rowDeleted();
    }
    
    public final void insertRow()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().insertRow();
    }
    
    public final void updateRow()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().updateRow();
    }
    
    public final void deleteRow()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().deleteRow();
    }
    
    public final void refreshRow()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().refreshRow();
    }
    
    public final void cancelRowUpdates()
      throws SQLException
    {
      CursorImpl.this.ctx.resultSet().cancelRowUpdates();
    }
    
    public final void updateNull(int columnIndex)
      throws SQLException
    {
      logUpdate(columnIndex, null);
      CursorImpl.this.ctx.resultSet().updateNull(columnIndex);
    }
    
    public final void updateNull(String columnLabel)
      throws SQLException
    {
      logUpdate(columnLabel, null);
      CursorImpl.this.ctx.resultSet().updateNull(columnLabel);
    }
    
    public final void updateArray(int columnIndex, Array x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateArray(columnIndex, x);
    }
    
    public final void updateArray(String columnLabel, Array x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateArray(columnLabel, x);
    }
    
    public final void updateAsciiStream(int columnIndex, InputStream x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateAsciiStream(columnIndex, x);
    }
    
    public final void updateAsciiStream(int columnIndex, InputStream x, int length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateAsciiStream(columnIndex, x, length);
    }
    
    public final void updateAsciiStream(int columnIndex, InputStream x, long length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateAsciiStream(columnIndex, x, length);
    }
    
    public final void updateAsciiStream(String columnLabel, InputStream x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateAsciiStream(columnLabel, x);
    }
    
    public final void updateAsciiStream(String columnLabel, InputStream x, int length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateAsciiStream(columnLabel, x, length);
    }
    
    public final void updateAsciiStream(String columnLabel, InputStream x, long length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateAsciiStream(columnLabel, x, length);
    }
    
    public final void updateBigDecimal(int columnIndex, BigDecimal x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateBigDecimal(columnIndex, x);
    }
    
    public final void updateBigDecimal(String columnLabel, BigDecimal x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateBigDecimal(columnLabel, x);
    }
    
    public final void updateBinaryStream(int columnIndex, InputStream x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateBinaryStream(columnIndex, x);
    }
    
    public final void updateBinaryStream(int columnIndex, InputStream x, int length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateBinaryStream(columnIndex, x, length);
    }
    
    public final void updateBinaryStream(int columnIndex, InputStream x, long length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateBinaryStream(columnIndex, x, length);
    }
    
    public final void updateBinaryStream(String columnLabel, InputStream x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateBinaryStream(columnLabel, x);
    }
    
    public final void updateBinaryStream(String columnLabel, InputStream x, int length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateBinaryStream(columnLabel, x, length);
    }
    
    public final void updateBinaryStream(String columnLabel, InputStream x, long length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateBinaryStream(columnLabel, x, length);
    }
    
    public final void updateBlob(int columnIndex, Blob x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateBlob(columnIndex, x);
    }
    
    public final void updateBlob(int columnIndex, InputStream x, long length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateBlob(columnIndex, x, length);
    }
    
    public final void updateBlob(String columnLabel, Blob x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateBlob(columnLabel, x);
    }
    
    public final void updateBlob(int columnIndex, InputStream x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateBlob(columnIndex, x);
    }
    
    public final void updateBlob(String columnLabel, InputStream x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateBlob(columnLabel, x);
    }
    
    public final void updateBlob(String columnLabel, InputStream x, long length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateBlob(columnLabel, x, length);
    }
    
    public final void updateBoolean(int columnIndex, boolean x)
      throws SQLException
    {
      logUpdate(columnIndex, Boolean.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateBoolean(columnIndex, x);
    }
    
    public final void updateBoolean(String columnLabel, boolean x)
      throws SQLException
    {
      logUpdate(columnLabel, Boolean.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateBoolean(columnLabel, x);
    }
    
    public final void updateByte(int columnIndex, byte x)
      throws SQLException
    {
      logUpdate(columnIndex, Byte.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateByte(columnIndex, x);
    }
    
    public final void updateByte(String columnLabel, byte x)
      throws SQLException
    {
      logUpdate(columnLabel, Byte.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateByte(columnLabel, x);
    }
    
    public final void updateBytes(int columnIndex, byte[] x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateBytes(columnIndex, x);
    }
    
    public final void updateBytes(String columnLabel, byte[] x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateBytes(columnLabel, x);
    }
    
    public final void updateCharacterStream(int columnIndex, Reader x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateCharacterStream(columnIndex, x);
    }
    
    public final void updateCharacterStream(int columnIndex, Reader x, int length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateCharacterStream(columnIndex, x, length);
    }
    
    public final void updateCharacterStream(int columnIndex, Reader x, long length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateCharacterStream(columnIndex, x, length);
    }
    
    public final void updateCharacterStream(String columnLabel, Reader x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateCharacterStream(columnLabel, x);
    }
    
    public final void updateCharacterStream(String columnLabel, Reader x, int length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateCharacterStream(columnLabel, x, length);
    }
    
    public final void updateCharacterStream(String columnLabel, Reader x, long length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateCharacterStream(columnLabel, x, length);
    }
    
    public final void updateClob(int columnIndex, Clob x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateClob(columnIndex, x);
    }
    
    public final void updateClob(int columnIndex, Reader x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateClob(columnIndex, x);
    }
    
    public final void updateClob(int columnIndex, Reader x, long length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateClob(columnIndex, x, length);
    }
    
    public final void updateClob(String columnLabel, Clob x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateClob(columnLabel, x);
    }
    
    public final void updateClob(String columnLabel, Reader x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateClob(columnLabel, x);
    }
    
    public final void updateClob(String columnLabel, Reader x, long length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateClob(columnLabel, x, length);
    }
    
    public final void updateDate(int columnIndex, Date x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateDate(columnIndex, x);
    }
    
    public final void updateDate(String columnLabel, Date x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateDate(columnLabel, x);
    }
    
    public final void updateDouble(int columnIndex, double x)
      throws SQLException
    {
      logUpdate(columnIndex, Double.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateDouble(columnIndex, x);
    }
    
    public final void updateDouble(String columnLabel, double x)
      throws SQLException
    {
      logUpdate(columnLabel, Double.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateDouble(columnLabel, x);
    }
    
    public final void updateFloat(int columnIndex, float x)
      throws SQLException
    {
      logUpdate(columnIndex, Float.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateFloat(columnIndex, x);
    }
    
    public final void updateFloat(String columnLabel, float x)
      throws SQLException
    {
      logUpdate(columnLabel, Float.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateFloat(columnLabel, x);
    }
    
    public final void updateInt(int columnIndex, int x)
      throws SQLException
    {
      logUpdate(columnIndex, Integer.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateInt(columnIndex, x);
    }
    
    public final void updateInt(String columnLabel, int x)
      throws SQLException
    {
      logUpdate(columnLabel, Integer.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateInt(columnLabel, x);
    }
    
    public final void updateLong(int columnIndex, long x)
      throws SQLException
    {
      logUpdate(columnIndex, Long.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateLong(columnIndex, x);
    }
    
    public final void updateLong(String columnLabel, long x)
      throws SQLException
    {
      logUpdate(columnLabel, Long.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateLong(columnLabel, x);
    }
    
    public final void updateNCharacterStream(int columnIndex, Reader x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateNCharacterStream(columnIndex, x);
    }
    
    public final void updateNCharacterStream(int columnIndex, Reader x, long length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateNCharacterStream(columnIndex, x, length);
    }
    
    public final void updateNCharacterStream(String columnLabel, Reader x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateNCharacterStream(columnLabel, x);
    }
    
    public final void updateNCharacterStream(String columnLabel, Reader x, long length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateNCharacterStream(columnLabel, x, length);
    }
    
    public final void updateNClob(int columnIndex, NClob x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateNClob(columnIndex, x);
    }
    
    public final void updateNClob(int columnIndex, Reader x, long length)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateNClob(columnIndex, x, length);
    }
    
    public final void updateNClob(int columnIndex, Reader x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateNClob(columnIndex, x);
    }
    
    public final void updateNClob(String columnLabel, NClob x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateNClob(columnLabel, x);
    }
    
    public final void updateNClob(String columnLabel, Reader x, long length)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateNClob(columnLabel, x, length);
    }
    
    public final void updateNClob(String columnLabel, Reader x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateNClob(columnLabel, x);
    }
    
    public final void updateNString(int columnIndex, String x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateNString(columnIndex, x);
    }
    
    public final void updateNString(String columnLabel, String x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateNString(columnLabel, x);
    }
    
    public final void updateObject(int columnIndex, Object x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateObject(columnIndex, x);
    }
    
    public final void updateObject(int columnIndex, Object x, int scaleOrLength)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateObject(columnIndex, x, scaleOrLength);
    }
    
    public final void updateObject(String columnLabel, Object x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateObject(columnLabel, x);
    }
    
    public final void updateObject(String columnLabel, Object x, int scaleOrLength)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateObject(columnLabel, x, scaleOrLength);
    }
    
    public final void updateRef(int columnIndex, Ref x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateRef(columnIndex, x);
    }
    
    public final void updateRef(String columnLabel, Ref x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateRef(columnLabel, x);
    }
    
    public final void updateRowId(int columnIndex, RowId x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateRowId(columnIndex, x);
    }
    
    public final void updateRowId(String columnLabel, RowId x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateRowId(columnLabel, x);
    }
    
    public final void updateShort(int columnIndex, short x)
      throws SQLException
    {
      logUpdate(columnIndex, Short.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateShort(columnIndex, x);
    }
    
    public final void updateShort(String columnLabel, short x)
      throws SQLException
    {
      logUpdate(columnLabel, Short.valueOf(x));
      CursorImpl.this.ctx.resultSet().updateShort(columnLabel, x);
    }
    
    public final void updateSQLXML(int columnIndex, SQLXML x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateSQLXML(columnIndex, x);
    }
    
    public final void updateSQLXML(String columnLabel, SQLXML x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateSQLXML(columnLabel, x);
    }
    
    public final void updateString(int columnIndex, String x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateString(columnIndex, x);
    }
    
    public final void updateString(String columnLabel, String x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateString(columnLabel, x);
    }
    
    public final void updateTime(int columnIndex, Time x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateTime(columnIndex, x);
    }
    
    public final void updateTime(String columnLabel, Time x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateTime(columnLabel, x);
    }
    
    public final void updateTimestamp(int columnIndex, Timestamp x)
      throws SQLException
    {
      logUpdate(columnIndex, x);
      CursorImpl.this.ctx.resultSet().updateTimestamp(columnIndex, x);
    }
    
    public final void updateTimestamp(String columnLabel, Timestamp x)
      throws SQLException
    {
      logUpdate(columnLabel, x);
      CursorImpl.this.ctx.resultSet().updateTimestamp(columnLabel, x);
    }
  }
  
  final class CursorIterator
    implements Iterator<R>
  {
    private R next;
    private Boolean hasNext;
    private final CursorImpl<R>.CursorIterator.CursorRecordInitialiser initialiser;
    
    CursorIterator()
    {
      this.initialiser = new CursorRecordInitialiser(null);
    }
    
    public final boolean hasNext()
    {
      if (this.hasNext == null)
      {
        this.next = fetchOne();
        this.hasNext = Boolean.valueOf(this.next != null);
      }
      return this.hasNext.booleanValue();
    }
    
    public final R next()
    {
      if (!hasNext()) {
        throw new NoSuchElementException("There are no more records to fetch from this Cursor");
      }
      R result = this.next;
      this.hasNext = null;
      this.next = null;
      return result;
    }
    
    private final R fetchOne()
    {
      AbstractRecord record = null;
      try
      {
        if ((!CursorImpl.this.isClosed) && (CursorImpl.this.rs.next()))
        {
          if (Boolean.TRUE.equals(CursorImpl.this.ctx.data("org.jooq.configuration.lock-rows-for-update")))
          {
            CursorImpl.this.rs.updateObject(1, CursorImpl.this.rs.getObject(1));
            CursorImpl.this.rs.updateRow();
          }
          record = (AbstractRecord)Utils.newRecord(true, CursorImpl.this.type, CursorImpl.this.fields, CursorImpl.this.ctx.configuration()).operate(this.initialiser);
          
          CursorImpl.access$108(CursorImpl.this);
        }
      }
      catch (ControlFlowSignal e)
      {
        throw e;
      }
      catch (RuntimeException e)
      {
        CursorImpl.this.ctx.exception(e);
        CursorImpl.this.listener.exception(CursorImpl.this.ctx);
        throw CursorImpl.this.ctx.exception();
      }
      catch (SQLException e)
      {
        CursorImpl.this.ctx.sqlException(e);
        CursorImpl.this.listener.exception(CursorImpl.this.ctx);
        throw CursorImpl.this.ctx.exception();
      }
      if (record == null) {
        CursorImpl.this.close();
      }
      return record;
    }
    
    public final void remove()
    {
      throw new UnsupportedOperationException();
    }
    
    private class CursorRecordInitialiser
      implements RecordOperation<AbstractRecord, SQLException>
    {
      private CursorRecordInitialiser() {}
      
      public AbstractRecord operate(AbstractRecord record)
        throws SQLException
      {
        CursorImpl.this.ctx.record(record);
        CursorImpl.this.listener.recordStart(CursorImpl.this.ctx);
        for (int i = 0; i < CursorImpl.this.fields.length; i++)
        {
          setValue(record, CursorImpl.this.fields[i], i);
          if (CursorImpl.this.intern[i] != 0) {
            record.intern0(i);
          }
        }
        CursorImpl.this.ctx.record(record);
        CursorImpl.this.listener.recordEnd(CursorImpl.this.ctx);
        
        return record;
      }
      
      private final <T> void setValue(AbstractRecord record, Field<T> field, int index)
        throws SQLException
      {
        CursorImpl.this.rsContext.index(index + 1);
        field.getBinding().get(CursorImpl.this.rsContext);
        T value = CursorImpl.this.rsContext.value();
        
        record.values[index] = value;
        record.originals[index] = value;
      }
    }
  }
}

package org.jooq.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.Converters;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.Convert;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.jdbc.MockArray;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.DayToSecond;
import org.jooq.types.Interval;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UNumber;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;
import org.jooq.util.postgres.PostgresUtils;

public class DefaultBinding<T, U>
  implements Binding<T, U>
{
  static final JooqLogger log = JooqLogger.getLogger(DefaultBinding.class);
  private static final char[] HEX = "0123456789abcdef".toCharArray();
  private static final long serialVersionUID = -198499389344950496L;
  final Class<T> type;
  final Converter<T, U> converter;
  @Deprecated
  final boolean isLob;
  
  public DefaultBinding(Converter<T, U> converter)
  {
    this(converter, false);
  }
  
  DefaultBinding(Converter<T, U> converter, boolean isLob)
  {
    this.type = converter.fromType();
    this.converter = converter;
    this.isLob = isLob;
  }
  
  static <T, X, U> Binding<T, U> newBinding(final Converter<X, U> converter, DataType<T> type, Binding<T, X> binding)
  {
    Binding<T, U> theBinding;
    Binding<T, U> theBinding;
    if ((converter == null) && (binding == null))
    {
      theBinding = new DefaultBinding(new IdentityConverter(type.getType()), type.isLob());
    }
    else
    {
      Binding<T, U> theBinding;
      if (converter == null)
      {
        theBinding = binding;
      }
      else
      {
        Binding<T, U> theBinding;
        if (binding == null) {
          theBinding = new DefaultBinding(converter, type.isLob());
        } else {
          theBinding = new Binding()
          {
            private static final long serialVersionUID = 8912340791845209886L;
            final Converter<T, U> theConverter = Converters.of(this.val$binding.converter(), converter);
            
            public Converter<T, U> converter()
            {
              return this.theConverter;
            }
            
            public void sql(BindingSQLContext<U> ctx)
              throws SQLException
            {
              this.val$binding.sql(ctx.convert(converter));
            }
            
            public void register(BindingRegisterContext<U> ctx)
              throws SQLException
            {
              this.val$binding.register(ctx.convert(converter));
            }
            
            public void set(BindingSetStatementContext<U> ctx)
              throws SQLException
            {
              this.val$binding.set(ctx.convert(converter));
            }
            
            public void set(BindingSetSQLOutputContext<U> ctx)
              throws SQLException
            {
              this.val$binding.set(ctx.convert(converter));
            }
            
            public void get(BindingGetResultSetContext<U> ctx)
              throws SQLException
            {
              this.val$binding.get(ctx.convert(converter));
            }
            
            public void get(BindingGetStatementContext<U> ctx)
              throws SQLException
            {
              this.val$binding.get(ctx.convert(converter));
            }
            
            public void get(BindingGetSQLInputContext<U> ctx)
              throws SQLException
            {
              this.val$binding.get(ctx.convert(converter));
            }
          };
        }
      }
    }
    return theBinding;
  }
  
  public Converter<T, U> converter()
  {
    return this.converter;
  }
  
  public void sql(BindingSQLContext<U> ctx)
  {
    T converted = this.converter.to(ctx.value());
    switch (ctx.render().castMode())
    {
    case NEVER: 
      toSQL(ctx, converted);
      return;
    case ALWAYS: 
      toSQLCast(ctx, converted);
      return;
    }
    if (shouldCast(ctx, converted)) {
      toSQLCast(ctx, converted);
    } else {
      toSQL(ctx, converted);
    }
  }
  
  private final boolean shouldCast(BindingSQLContext<U> ctx, T converted)
  {
    if (ctx.render().paramType() != ParamType.INLINED) {
      if (!(converted instanceof EnumType)) {
        switch (ctx.family())
        {
        case DERBY: 
        case FIREBIRD: 
        case H2: 
        case HSQLDB: 
        case CUBRID: 
        case POSTGRES: 
          return true;
        }
      }
    }
    if (Interval.class.isAssignableFrom(this.type)) {
      switch (ctx.family())
      {
      case POSTGRES: 
        return true;
      }
    }
    return false;
  }
  
  private final void toSQLCast(BindingSQLContext<U> ctx, T converted)
  {
    DataType<T> dataType = DefaultDataType.getDataType(ctx.dialect(), this.type);
    DataType<T> sqlDataType = dataType.getSQLDataType();
    SQLDialect family = ctx.family();
    if ((converted != null) && (this.type == BigDecimal.class)) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HSQLDB }).contains(family))
      {
        int scale = ((BigDecimal)converted).scale();
        int precision = scale + ((BigDecimal)converted).precision();
        if (family == SQLDialect.FIREBIRD) {
          precision = Math.min(precision, 18);
        }
        toSQLCast(ctx, converted, dataType, 0, precision, scale);
        return;
      }
    }
    if (SQLDataType.OTHER == sqlDataType)
    {
      if (converted != null) {
        toSQLCast(ctx, converted, DefaultDataType.getDataType(family, converted.getClass()), 0, 0, 0);
      } else if (Arrays.asList(new Object[0]).contains(family)) {
        ctx.render().sql(ctx.variable());
      } else {
        toSQLCast(ctx, converted, DefaultDataType.getDataType(family, String.class), 0, 0, 0);
      }
    }
    else if ((family == SQLDialect.POSTGRES) && ((sqlDataType == null) || (!sqlDataType.isTemporal())))
    {
      toSQL(ctx, converted);
    }
    else
    {
      if ((sqlDataType == SQLDataType.VARCHAR) || (sqlDataType == SQLDataType.CHAR)) {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.FIREBIRD }).contains(family))
        {
          toSQLCast(ctx, converted, dataType, getValueLength(converted), 0, 0); return;
        }
      }
      toSQLCast(ctx, converted, dataType, dataType.length(), dataType.precision(), dataType.scale());
    }
  }
  
  private final int getValueLength(T value)
  {
    String string = (String)value;
    if (string == null) {
      return 1;
    }
    int length = string.length();
    for (int i = 0; i < length; i++) {
      if (string.charAt(i) > '') {
        return Math.min(32672, 4 * length);
      }
    }
    return Math.min(32672, length);
  }
  
  private final void toSQLCast(BindingSQLContext<U> ctx, T converted, DataType<?> dataType, int length, int precision, int scale)
  {
    ctx.render().keyword("cast").sql("(");
    toSQL(ctx, converted);
    ctx.render().sql(" ").keyword("as").sql(" ")
      .sql(dataType.length(length).precision(precision, scale).getCastTypeName(ctx.configuration()))
      .sql(")");
  }
  
  private final void toSQL(BindingSQLContext<U> ctx, Object val)
  {
    SQLDialect family = ctx.family();
    RenderContext render = ctx.render();
    if (render.paramType() == ParamType.INLINED)
    {
      if (val == null)
      {
        render.keyword("null");
      }
      else if (this.type == Boolean.class)
      {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.FIREBIRD, SQLDialect.SQLITE }).contains(family)) {
          render.sql(((Boolean)val).booleanValue() ? "1" : "0");
        } else {
          render.keyword(((Boolean)val).toString());
        }
      }
      else if (this.type == byte[].class)
      {
        byte[] binary = (byte[])val;
        if (Arrays.asList(new Object[0]).contains(family)) {
          render.sql("0x").sql(convertBytesToHex(binary));
        } else if (Arrays.asList(new SQLDialect[] { SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE }).contains(family)) {
          render.sql("X'").sql(convertBytesToHex(binary)).sql("'");
        } else if (Arrays.asList(new Object[0]).contains(family)) {
          render.keyword("hextoraw('").sql(convertBytesToHex(binary)).sql("')");
        } else if (family == SQLDialect.POSTGRES) {
          render.sql("E'").sql(convertBytesToPostgresOctal(binary)).keyword("'::bytea");
        } else {
          render.sql("X'").sql(convertBytesToHex(binary)).sql("'");
        }
      }
      else if (Interval.class.isAssignableFrom(this.type))
      {
        render.sql("'").sql(escape(val, render)).sql("'");
      }
      else if (Number.class.isAssignableFrom(this.type))
      {
        render.sql(((Number)val).toString());
      }
      else if (this.type == java.sql.Date.class)
      {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.SQLITE }).contains(family)) {
          render.sql("'").sql(escape(val, render)).sql("'");
        } else if (family == SQLDialect.DERBY) {
          render.keyword("date('").sql(escape(val, render)).sql("')");
        } else if (family == SQLDialect.MYSQL) {
          render.keyword("{d '").sql(escape(val, render)).sql("'}");
        } else {
          render.keyword("date '").sql(escape(val, render)).sql("'");
        }
      }
      else if (this.type == Timestamp.class)
      {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.SQLITE }).contains(family)) {
          render.sql("'").sql(escape(val, render)).sql("'");
        } else if (family == SQLDialect.DERBY) {
          render.keyword("timestamp('").sql(escape(val, render)).sql("')");
        } else if (family == SQLDialect.CUBRID) {
          render.keyword("datetime '").sql(escape(val, render)).sql("'");
        } else if (family == SQLDialect.MYSQL) {
          render.keyword("{ts '").sql(escape(val, render)).sql("'}");
        } else {
          render.keyword("timestamp '").sql(escape(val, render)).sql("'");
        }
      }
      else if (this.type == Time.class)
      {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.SQLITE }).contains(family)) {
          render.sql("'").sql(new SimpleDateFormat("HH:mm:ss").format((Time)val)).sql("'");
        } else if (family == SQLDialect.DERBY) {
          render.keyword("time").sql("('").sql(escape(val, render)).sql("')");
        } else if (family == SQLDialect.MYSQL) {
          render.keyword("{t '").sql(escape(val, render)).sql("'}");
        } else {
          render.keyword("time").sql(" '").sql(escape(val, render)).sql("'");
        }
      }
      else if (this.type.isArray())
      {
        String separator = "";
        if (family == SQLDialect.H2)
        {
          render.sql("(");
          for (Object o : (Object[])val)
          {
            render.sql(separator);
            new DefaultBinding(new IdentityConverter(this.type.getComponentType()), this.isLob).sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.render(), o));
            separator = ", ";
          }
          render.sql(")");
        }
        else
        {
          render.keyword("ARRAY");
          render.sql("[");
          for (Object o : (Object[])val)
          {
            render.sql(separator);
            new DefaultBinding(new IdentityConverter(this.type.getComponentType()), this.isLob).sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.render(), o));
            separator = ", ";
          }
          render.sql("]");
          if ((family == SQLDialect.POSTGRES) && (EnumType.class.isAssignableFrom(this.type.getComponentType()))) {
            pgRenderEnumCast(render, this.type);
          }
        }
      }
      else if (EnumType.class.isAssignableFrom(this.type))
      {
        String literal = ((EnumType)val).getLiteral();
        if (literal == null) {
          new DefaultBinding(new IdentityConverter(String.class), this.isLob).sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.render(), literal));
        } else {
          new DefaultBinding(new IdentityConverter(String.class), this.isLob).sql(new DefaultBindingSQLContext(ctx.configuration(), ctx.render(), literal));
        }
      }
      else if (UDTRecord.class.isAssignableFrom(this.type))
      {
        render.sql("[UDT]");
      }
      else
      {
        render.sql("'").sql(escape(val, render), true).sql("'");
      }
    }
    else if (family == SQLDialect.POSTGRES)
    {
      if ((EnumType.class.isAssignableFrom(this.type)) || (
        (this.type.isArray()) && (EnumType.class.isAssignableFrom(this.type.getComponentType()))))
      {
        render.sql(ctx.variable());
        pgRenderEnumCast(render, this.type);
      }
      else if ((this.type.isArray()) && (byte[].class != this.type))
      {
        render.sql(ctx.variable());
        render.sql("::");
        render.keyword(DefaultDataType.getDataType(family, this.type).getCastTypeName(render.configuration()));
      }
      else
      {
        render.sql(ctx.variable());
      }
    }
    else {
      render.sql(ctx.variable());
    }
  }
  
  private final String escape(Object val, Context<?> context)
  {
    String result = val.toString();
    if (Utils.needsBackslashEscaping(context.configuration())) {
      result = result.replace("\\", "\\\\");
    }
    return result.replace("'", "''");
  }
  
  private static final String convertBytesToHex(byte[] value)
  {
    return convertBytesToHex(value, value.length);
  }
  
  private static final String convertBytesToHex(byte[] value, int len)
  {
    char[] buff = new char[len + len];
    char[] hex = HEX;
    for (int i = 0; i < len; i++)
    {
      int c = value[i] & 0xFF;
      buff[(i + i)] = hex[(c >> 4)];
      buff[(i + i + 1)] = hex[(c & 0xF)];
    }
    return new String(buff);
  }
  
  private static final String convertBytesToPostgresOctal(byte[] binary)
  {
    StringBuilder sb = new StringBuilder();
    for (byte b : binary)
    {
      sb.append("\\\\");
      sb.append(StringUtils.leftPad(Integer.toOctalString(b & 0xFF), 3, '0'));
    }
    return sb.toString();
  }
  
  public void register(BindingRegisterContext<U> ctx)
    throws SQLException
  {
    Configuration configuration = ctx.configuration();
    int sqlType = DefaultDataType.getDataType(ctx.dialect(), this.type).getSQLType();
    switch (configuration.dialect().family())
    {
    }
    ctx.statement().registerOutParameter(ctx.index(), sqlType);
  }
  
  public void set(BindingSetStatementContext<U> ctx)
    throws SQLException
  {
    Configuration configuration = ctx.configuration();
    SQLDialect dialect = ctx.dialect();
    T value = this.converter.to(ctx.value());
    if (log.isTraceEnabled()) {
      if ((value != null) && (value.getClass().isArray()) && (value.getClass() != byte[].class)) {
        log.trace("Binding variable " + ctx.index(), Arrays.asList((Object[])value) + " (" + this.type + ")");
      } else {
        log.trace("Binding variable " + ctx.index(), value + " (" + this.type + ")");
      }
    }
    if (value == null)
    {
      int sqlType = DefaultDataType.getDataType(dialect, this.type).getSQLType();
      if (UDTRecord.class.isAssignableFrom(this.type))
      {
        String typeName = ((UDTRecord)Utils.newRecord(false, this.type).operate(null)).getUDT().getName();
        ctx.statement().setNull(ctx.index(), sqlType, typeName);
      }
      else if ((Arrays.asList(new SQLDialect[] { SQLDialect.POSTGRES }).contains(configuration.dialect())) && (sqlType == 2004))
      {
        ctx.statement().setNull(ctx.index(), -2);
      }
      else if (sqlType != 1111)
      {
        ctx.statement().setNull(ctx.index(), sqlType);
      }
      else
      {
        ctx.statement().setObject(ctx.index(), null);
      }
    }
    else
    {
      Class<?> actualType = this.type;
      if (actualType == Object.class) {
        actualType = value.getClass();
      }
      if (actualType == Blob.class) {
        ctx.statement().setBlob(ctx.index(), (Blob)value);
      } else if (actualType == Boolean.class) {
        ctx.statement().setBoolean(ctx.index(), ((Boolean)value).booleanValue());
      } else if (actualType == BigDecimal.class)
      {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.SQLITE }).contains(dialect.family())) {
          ctx.statement().setString(ctx.index(), value.toString());
        } else {
          ctx.statement().setBigDecimal(ctx.index(), (BigDecimal)value);
        }
      }
      else if (actualType == BigInteger.class)
      {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.SQLITE }).contains(dialect.family())) {
          ctx.statement().setString(ctx.index(), value.toString());
        } else {
          ctx.statement().setBigDecimal(ctx.index(), new BigDecimal((BigInteger)value));
        }
      }
      else if (actualType == Byte.class) {
        ctx.statement().setByte(ctx.index(), ((Byte)value).byteValue());
      } else if (actualType == byte[].class) {
        ctx.statement().setBytes(ctx.index(), (byte[])value);
      } else if (actualType == Clob.class) {
        ctx.statement().setClob(ctx.index(), (Clob)value);
      } else if (actualType == Double.class) {
        ctx.statement().setDouble(ctx.index(), ((Double)value).doubleValue());
      } else if (actualType == Float.class) {
        ctx.statement().setFloat(ctx.index(), ((Float)value).floatValue());
      } else if (actualType == Integer.class) {
        ctx.statement().setInt(ctx.index(), ((Integer)value).intValue());
      } else if (actualType == Long.class) {
        ctx.statement().setLong(ctx.index(), ((Long)value).longValue());
      } else if (actualType == Short.class) {
        ctx.statement().setShort(ctx.index(), ((Short)value).shortValue());
      } else if (actualType == String.class) {
        ctx.statement().setString(ctx.index(), (String)value);
      } else if (actualType == java.sql.Date.class)
      {
        if (dialect == SQLDialect.SQLITE) {
          ctx.statement().setString(ctx.index(), ((java.sql.Date)value).toString());
        } else {
          ctx.statement().setDate(ctx.index(), (java.sql.Date)value);
        }
      }
      else if (actualType == Time.class)
      {
        if (dialect == SQLDialect.SQLITE) {
          ctx.statement().setString(ctx.index(), ((Time)value).toString());
        } else {
          ctx.statement().setTime(ctx.index(), (Time)value);
        }
      }
      else if (actualType == Timestamp.class)
      {
        if (dialect == SQLDialect.SQLITE) {
          ctx.statement().setString(ctx.index(), ((Timestamp)value).toString());
        } else {
          ctx.statement().setTimestamp(ctx.index(), (Timestamp)value);
        }
      }
      else if (actualType == YearToMonth.class)
      {
        if (dialect == SQLDialect.POSTGRES) {
          ctx.statement().setObject(ctx.index(), PostgresUtils.toPGInterval((YearToMonth)value));
        } else {
          ctx.statement().setString(ctx.index(), value.toString());
        }
      }
      else if (actualType == DayToSecond.class)
      {
        if (dialect == SQLDialect.POSTGRES) {
          ctx.statement().setObject(ctx.index(), PostgresUtils.toPGInterval((DayToSecond)value));
        } else {
          ctx.statement().setString(ctx.index(), value.toString());
        }
      }
      else if (actualType == UByte.class) {
        ctx.statement().setShort(ctx.index(), ((UByte)value).shortValue());
      } else if (actualType == UShort.class) {
        ctx.statement().setInt(ctx.index(), ((UShort)value).intValue());
      } else if (actualType == UInteger.class) {
        ctx.statement().setLong(ctx.index(), ((UInteger)value).longValue());
      } else if (actualType == ULong.class) {
        ctx.statement().setBigDecimal(ctx.index(), new BigDecimal(value.toString()));
      } else if (actualType == UUID.class) {
        switch (dialect.family())
        {
        case H2: 
        case POSTGRES: 
          ctx.statement().setObject(ctx.index(), value);
          break;
        default: 
          ctx.statement().setString(ctx.index(), value.toString());
          break;
        }
      } else if (actualType.isArray()) {
        switch (dialect)
        {
        case POSTGRES: 
          ctx.statement().setString(ctx.index(), PostgresUtils.toPGArrayString((Object[])value));
          break;
        case HSQLDB: 
          Object[] a = (Object[])value;
          Class<?> t = actualType;
          if (actualType == UUID[].class)
          {
            a = Convert.convertArray(a, String[].class);
            t = String[].class;
          }
          ctx.statement().setArray(ctx.index(), new MockArray(dialect, a, t));
          break;
        case H2: 
          ctx.statement().setObject(ctx.index(), value);
          break;
        case CUBRID: 
        default: 
          throw new SQLDialectNotSupportedException("Cannot bind ARRAY types in dialect " + dialect);
        }
      } else if (EnumType.class.isAssignableFrom(actualType)) {
        ctx.statement().setString(ctx.index(), ((EnumType)value).getLiteral());
      } else {
        ctx.statement().setObject(ctx.index(), value);
      }
    }
  }
  
  public void set(BindingSetSQLOutputContext<U> ctx)
    throws SQLException
  {
    T value = this.converter.to(ctx.value());
    if (value == null) {
      ctx.output().writeObject(null);
    } else if (this.type == Blob.class) {
      ctx.output().writeBlob((Blob)value);
    } else if (this.type == Boolean.class) {
      ctx.output().writeBoolean(((Boolean)value).booleanValue());
    } else if (this.type == BigInteger.class) {
      ctx.output().writeBigDecimal(new BigDecimal((BigInteger)value));
    } else if (this.type == BigDecimal.class) {
      ctx.output().writeBigDecimal((BigDecimal)value);
    } else if (this.type == Byte.class) {
      ctx.output().writeByte(((Byte)value).byteValue());
    } else if (this.type == byte[].class)
    {
      if (this.isLob)
      {
        Blob blob = null;
        try
        {
          blob = (Blob)Reflect.on("oracle.sql.BLOB").call("createTemporary", new Object[] { Reflect.on(ctx.output()).call("getSTRUCT").call("getJavaSqlConnection").get(), Boolean.valueOf(false), Reflect.on("oracle.sql.BLOB").get("DURATION_SESSION") }).get();
          
          blob.setBytes(1L, (byte[])value);
          ctx.output().writeBlob(blob);
        }
        finally
        {
          DefaultExecuteContext.register(blob);
        }
      }
      else
      {
        ctx.output().writeBytes((byte[])value);
      }
    }
    else if (this.type == Clob.class) {
      ctx.output().writeClob((Clob)value);
    } else if (this.type == java.sql.Date.class) {
      ctx.output().writeDate((java.sql.Date)value);
    } else if (this.type == Double.class) {
      ctx.output().writeDouble(((Double)value).doubleValue());
    } else if (this.type == Float.class) {
      ctx.output().writeFloat(((Float)value).floatValue());
    } else if (this.type == Integer.class) {
      ctx.output().writeInt(((Integer)value).intValue());
    } else if (this.type == Long.class) {
      ctx.output().writeLong(((Long)value).longValue());
    } else if (this.type == Short.class) {
      ctx.output().writeShort(((Short)value).shortValue());
    } else if (this.type == String.class)
    {
      if (this.isLob)
      {
        Clob clob = null;
        try
        {
          clob = (Clob)Reflect.on("oracle.sql.CLOB").call("createTemporary", new Object[] { Reflect.on(ctx.output()).call("getSTRUCT").call("getJavaSqlConnection").get(), Boolean.valueOf(false), Reflect.on("oracle.sql.CLOB").get("DURATION_SESSION") }).get();
          
          clob.setString(1L, (String)value);
          ctx.output().writeClob(clob);
        }
        finally
        {
          DefaultExecuteContext.register(clob);
        }
      }
      else
      {
        ctx.output().writeString((String)value);
      }
    }
    else if (this.type == Time.class) {
      ctx.output().writeTime((Time)value);
    } else if (this.type == Timestamp.class) {
      ctx.output().writeTimestamp((Timestamp)value);
    } else if (this.type == YearToMonth.class) {
      ctx.output().writeString(value.toString());
    } else if (this.type == DayToSecond.class) {
      ctx.output().writeString(value.toString());
    } else if (UNumber.class.isAssignableFrom(this.type)) {
      ctx.output().writeString(value.toString());
    } else if (this.type == UUID.class) {
      ctx.output().writeString(value.toString());
    } else if (EnumType.class.isAssignableFrom(this.type)) {
      ctx.output().writeString(((EnumType)value).getLiteral());
    } else if (UDTRecord.class.isAssignableFrom(this.type)) {
      ctx.output().writeObject((UDTRecord)value);
    } else {
      throw new UnsupportedOperationException("Type " + this.type + " is not supported");
    }
  }
  
  public void get(BindingGetResultSetContext<U> ctx)
    throws SQLException
  {
    T result = null;
    if (this.type == Blob.class)
    {
      result = ctx.resultSet().getBlob(ctx.index());
    }
    else if (this.type == Boolean.class)
    {
      result = JDBCUtils.wasNull(ctx.resultSet(), Boolean.valueOf(ctx.resultSet().getBoolean(ctx.index())));
    }
    else if (this.type == BigInteger.class)
    {
      if (ctx.configuration().dialect() == SQLDialect.SQLITE)
      {
        result = Convert.convert(ctx.resultSet().getString(ctx.index()), BigInteger.class);
      }
      else
      {
        BigDecimal b = ctx.resultSet().getBigDecimal(ctx.index());
        result = b == null ? null : b.toBigInteger();
      }
    }
    else if (this.type == BigDecimal.class)
    {
      if (ctx.configuration().dialect() == SQLDialect.SQLITE) {
        result = Convert.convert(ctx.resultSet().getString(ctx.index()), BigDecimal.class);
      } else {
        result = ctx.resultSet().getBigDecimal(ctx.index());
      }
    }
    else if (this.type == Byte.class)
    {
      result = JDBCUtils.wasNull(ctx.resultSet(), Byte.valueOf(ctx.resultSet().getByte(ctx.index())));
    }
    else if (this.type == byte[].class)
    {
      result = ctx.resultSet().getBytes(ctx.index());
    }
    else if (this.type == Clob.class)
    {
      result = ctx.resultSet().getClob(ctx.index());
    }
    else if (this.type == java.sql.Date.class)
    {
      result = getDate(ctx.configuration().dialect(), ctx.resultSet(), ctx.index());
    }
    else if (this.type == Double.class)
    {
      result = JDBCUtils.wasNull(ctx.resultSet(), Double.valueOf(ctx.resultSet().getDouble(ctx.index())));
    }
    else if (this.type == Float.class)
    {
      result = JDBCUtils.wasNull(ctx.resultSet(), Float.valueOf(ctx.resultSet().getFloat(ctx.index())));
    }
    else if (this.type == Integer.class)
    {
      result = JDBCUtils.wasNull(ctx.resultSet(), Integer.valueOf(ctx.resultSet().getInt(ctx.index())));
    }
    else if (this.type == Long.class)
    {
      result = JDBCUtils.wasNull(ctx.resultSet(), Long.valueOf(ctx.resultSet().getLong(ctx.index())));
    }
    else if (this.type == Short.class)
    {
      result = JDBCUtils.wasNull(ctx.resultSet(), Short.valueOf(ctx.resultSet().getShort(ctx.index())));
    }
    else if (this.type == String.class)
    {
      result = ctx.resultSet().getString(ctx.index());
    }
    else if (this.type == Time.class)
    {
      result = getTime(ctx.configuration().dialect(), ctx.resultSet(), ctx.index());
    }
    else if (this.type == Timestamp.class)
    {
      result = getTimestamp(ctx.configuration().dialect(), ctx.resultSet(), ctx.index());
    }
    else if (this.type == YearToMonth.class)
    {
      if (ctx.configuration().dialect() == SQLDialect.POSTGRES)
      {
        Object object = ctx.resultSet().getObject(ctx.index());
        result = object == null ? null : PostgresUtils.toYearToMonth(object);
      }
      else
      {
        String string = ctx.resultSet().getString(ctx.index());
        result = string == null ? null : YearToMonth.valueOf(string);
      }
    }
    else if (this.type == DayToSecond.class)
    {
      if (ctx.configuration().dialect() == SQLDialect.POSTGRES)
      {
        Object object = ctx.resultSet().getObject(ctx.index());
        result = object == null ? null : PostgresUtils.toDayToSecond(object);
      }
      else
      {
        String string = ctx.resultSet().getString(ctx.index());
        result = string == null ? null : DayToSecond.valueOf(string);
      }
    }
    else if (this.type == UByte.class)
    {
      result = Convert.convert(ctx.resultSet().getString(ctx.index()), UByte.class);
    }
    else if (this.type == UShort.class)
    {
      result = Convert.convert(ctx.resultSet().getString(ctx.index()), UShort.class);
    }
    else if (this.type == UInteger.class)
    {
      result = Convert.convert(ctx.resultSet().getString(ctx.index()), UInteger.class);
    }
    else if (this.type == ULong.class)
    {
      result = Convert.convert(ctx.resultSet().getString(ctx.index()), ULong.class);
    }
    else if (this.type == UUID.class)
    {
      switch (ctx.configuration().dialect().family())
      {
      case H2: 
      case POSTGRES: 
        result = ctx.resultSet().getObject(ctx.index());
        break;
      default: 
        result = Convert.convert(ctx.resultSet().getString(ctx.index()), UUID.class);
        break;
      }
    }
    else if (this.type.isArray())
    {
      switch (ctx.configuration().dialect())
      {
      case POSTGRES: 
        result = pgGetArray(ctx, ctx.resultSet(), this.type, ctx.index());
        break;
      default: 
        result = convertArray(ctx.resultSet().getArray(ctx.index()), this.type);
        break;
      }
    }
    else if (EnumType.class.isAssignableFrom(this.type))
    {
      result = getEnumType(this.type, ctx.resultSet().getString(ctx.index()));
    }
    else if (UDTRecord.class.isAssignableFrom(this.type))
    {
      switch (ctx.configuration().dialect())
      {
      case POSTGRES: 
        result = pgNewUDTRecord(this.type, ctx.resultSet().getObject(ctx.index()));
        break;
      default: 
        result = ctx.resultSet().getObject(ctx.index(), DataTypes.udtRecords());
        break;
      }
    }
    else if (Result.class.isAssignableFrom(this.type))
    {
      ResultSet nested = (ResultSet)ctx.resultSet().getObject(ctx.index());
      result = DSL.using(ctx.configuration()).fetch(nested);
    }
    else
    {
      result = unlob(ctx.resultSet().getObject(ctx.index()));
    }
    ctx.value(this.converter.from(result));
  }
  
  public void get(BindingGetStatementContext<U> ctx)
    throws SQLException
  {
    T result = null;
    if (this.type == Blob.class)
    {
      result = ctx.statement().getBlob(ctx.index());
    }
    else if (this.type == Boolean.class)
    {
      result = JDBCUtils.wasNull(ctx.statement(), Boolean.valueOf(ctx.statement().getBoolean(ctx.index())));
    }
    else if (this.type == BigInteger.class)
    {
      BigDecimal d = ctx.statement().getBigDecimal(ctx.index());
      result = d == null ? null : d.toBigInteger();
    }
    else if (this.type == BigDecimal.class)
    {
      result = ctx.statement().getBigDecimal(ctx.index());
    }
    else if (this.type == Byte.class)
    {
      result = JDBCUtils.wasNull(ctx.statement(), Byte.valueOf(ctx.statement().getByte(ctx.index())));
    }
    else if (this.type == byte[].class)
    {
      result = ctx.statement().getBytes(ctx.index());
    }
    else if (this.type == Clob.class)
    {
      result = ctx.statement().getClob(ctx.index());
    }
    else if (this.type == java.sql.Date.class)
    {
      result = ctx.statement().getDate(ctx.index());
    }
    else if (this.type == Double.class)
    {
      result = JDBCUtils.wasNull(ctx.statement(), Double.valueOf(ctx.statement().getDouble(ctx.index())));
    }
    else if (this.type == Float.class)
    {
      result = JDBCUtils.wasNull(ctx.statement(), Float.valueOf(ctx.statement().getFloat(ctx.index())));
    }
    else if (this.type == Integer.class)
    {
      result = JDBCUtils.wasNull(ctx.statement(), Integer.valueOf(ctx.statement().getInt(ctx.index())));
    }
    else if (this.type == Long.class)
    {
      result = JDBCUtils.wasNull(ctx.statement(), Long.valueOf(ctx.statement().getLong(ctx.index())));
    }
    else if (this.type == Short.class)
    {
      result = JDBCUtils.wasNull(ctx.statement(), Short.valueOf(ctx.statement().getShort(ctx.index())));
    }
    else if (this.type == String.class)
    {
      result = ctx.statement().getString(ctx.index());
    }
    else if (this.type == Time.class)
    {
      result = ctx.statement().getTime(ctx.index());
    }
    else if (this.type == Timestamp.class)
    {
      result = ctx.statement().getTimestamp(ctx.index());
    }
    else if (this.type == YearToMonth.class)
    {
      if (ctx.configuration().dialect() == SQLDialect.POSTGRES)
      {
        Object object = ctx.statement().getObject(ctx.index());
        result = object == null ? null : PostgresUtils.toYearToMonth(object);
      }
      else
      {
        String string = ctx.statement().getString(ctx.index());
        result = string == null ? null : YearToMonth.valueOf(string);
      }
    }
    else if (this.type == DayToSecond.class)
    {
      if (ctx.configuration().dialect() == SQLDialect.POSTGRES)
      {
        Object object = ctx.statement().getObject(ctx.index());
        result = object == null ? null : PostgresUtils.toDayToSecond(object);
      }
      else
      {
        String string = ctx.statement().getString(ctx.index());
        result = string == null ? null : DayToSecond.valueOf(string);
      }
    }
    else if (this.type == UByte.class)
    {
      String string = ctx.statement().getString(ctx.index());
      result = string == null ? null : UByte.valueOf(string);
    }
    else if (this.type == UShort.class)
    {
      String string = ctx.statement().getString(ctx.index());
      result = string == null ? null : UShort.valueOf(string);
    }
    else if (this.type == UInteger.class)
    {
      String string = ctx.statement().getString(ctx.index());
      result = string == null ? null : UInteger.valueOf(string);
    }
    else if (this.type == ULong.class)
    {
      String string = ctx.statement().getString(ctx.index());
      result = string == null ? null : ULong.valueOf(string);
    }
    else if (this.type == UUID.class)
    {
      switch (ctx.configuration().dialect().family())
      {
      case H2: 
      case POSTGRES: 
        result = ctx.statement().getObject(ctx.index());
        break;
      default: 
        result = Convert.convert(ctx.statement().getString(ctx.index()), UUID.class);
        break;
      }
    }
    else if (this.type.isArray())
    {
      result = convertArray(ctx.statement().getObject(ctx.index()), this.type);
    }
    else if (EnumType.class.isAssignableFrom(this.type))
    {
      result = getEnumType(this.type, ctx.statement().getString(ctx.index()));
    }
    else if (UDTRecord.class.isAssignableFrom(this.type))
    {
      switch (ctx.configuration().dialect())
      {
      case POSTGRES: 
        result = pgNewUDTRecord(this.type, ctx.statement().getObject(ctx.index()));
        break;
      default: 
        result = ctx.statement().getObject(ctx.index(), DataTypes.udtRecords());
        break;
      }
    }
    else if (Result.class.isAssignableFrom(this.type))
    {
      ResultSet nested = (ResultSet)ctx.statement().getObject(ctx.index());
      result = DSL.using(ctx.configuration()).fetch(nested);
    }
    else
    {
      result = ctx.statement().getObject(ctx.index());
    }
    ctx.value(this.converter.from(result));
  }
  
  public void get(BindingGetSQLInputContext<U> ctx)
    throws SQLException
  {
    T result = null;
    if (this.type == Blob.class)
    {
      result = ctx.input().readBlob();
    }
    else if (this.type == Boolean.class)
    {
      result = JDBCUtils.wasNull(ctx.input(), Boolean.valueOf(ctx.input().readBoolean()));
    }
    else if (this.type == BigInteger.class)
    {
      BigDecimal d = ctx.input().readBigDecimal();
      result = d == null ? null : d.toBigInteger();
    }
    else if (this.type == BigDecimal.class)
    {
      result = ctx.input().readBigDecimal();
    }
    else if (this.type == Byte.class)
    {
      result = JDBCUtils.wasNull(ctx.input(), Byte.valueOf(ctx.input().readByte()));
    }
    else if (this.type == byte[].class)
    {
      if (this.isLob)
      {
        Blob blob = null;
        try
        {
          blob = ctx.input().readBlob();
          result = blob == null ? null : blob.getBytes(1L, (int)blob.length());
        }
        finally
        {
          JDBCUtils.safeFree(blob);
        }
      }
      else
      {
        result = ctx.input().readBytes();
      }
    }
    else if (this.type == Clob.class)
    {
      result = ctx.input().readClob();
    }
    else if (this.type == java.sql.Date.class)
    {
      result = ctx.input().readDate();
    }
    else if (this.type == Double.class)
    {
      result = JDBCUtils.wasNull(ctx.input(), Double.valueOf(ctx.input().readDouble()));
    }
    else if (this.type == Float.class)
    {
      result = JDBCUtils.wasNull(ctx.input(), Float.valueOf(ctx.input().readFloat()));
    }
    else if (this.type == Integer.class)
    {
      result = JDBCUtils.wasNull(ctx.input(), Integer.valueOf(ctx.input().readInt()));
    }
    else if (this.type == Long.class)
    {
      result = JDBCUtils.wasNull(ctx.input(), Long.valueOf(ctx.input().readLong()));
    }
    else if (this.type == Short.class)
    {
      result = JDBCUtils.wasNull(ctx.input(), Short.valueOf(ctx.input().readShort()));
    }
    else if (this.type == String.class)
    {
      result = ctx.input().readString();
    }
    else if (this.type == Time.class)
    {
      result = ctx.input().readTime();
    }
    else if (this.type == Timestamp.class)
    {
      result = ctx.input().readTimestamp();
    }
    else if (this.type == YearToMonth.class)
    {
      String string = ctx.input().readString();
      result = string == null ? null : YearToMonth.valueOf(string);
    }
    else if (this.type == DayToSecond.class)
    {
      String string = ctx.input().readString();
      result = string == null ? null : DayToSecond.valueOf(string);
    }
    else if (this.type == UByte.class)
    {
      String string = ctx.input().readString();
      result = string == null ? null : UByte.valueOf(string);
    }
    else if (this.type == UShort.class)
    {
      String string = ctx.input().readString();
      result = string == null ? null : UShort.valueOf(string);
    }
    else if (this.type == UInteger.class)
    {
      String string = ctx.input().readString();
      result = string == null ? null : UInteger.valueOf(string);
    }
    else if (this.type == ULong.class)
    {
      String string = ctx.input().readString();
      result = string == null ? null : ULong.valueOf(string);
    }
    else if (this.type == UUID.class)
    {
      result = Convert.convert(ctx.input().readString(), UUID.class);
    }
    else if (this.type.isArray())
    {
      java.sql.Array array = ctx.input().readArray();
      result = array == null ? null : array.getArray();
    }
    else if (EnumType.class.isAssignableFrom(this.type))
    {
      result = getEnumType(this.type, ctx.input().readString());
    }
    else if (UDTRecord.class.isAssignableFrom(this.type))
    {
      result = ctx.input().readObject();
    }
    else
    {
      result = unlob(ctx.input().readObject());
    }
    ctx.value(this.converter.from(result));
  }
  
  private static Object unlob(Object object)
    throws SQLException
  {
    Object localObject1;
    if ((object instanceof Blob))
    {
      Blob blob = (Blob)object;
      try
      {
        return blob.getBytes(1L, (int)blob.length());
      }
      finally
      {
        JDBCUtils.safeFree(blob);
      }
    }
    if ((object instanceof Clob))
    {
      Clob clob = (Clob)object;
      try
      {
        return clob.getSubString(1L, (int)clob.length());
      }
      finally
      {
        JDBCUtils.safeFree(clob);
      }
    }
    return object;
  }
  
  private static final <T> T getEnumType(Class<T> type, String literal)
    throws SQLException
  {
    try
    {
      Object[] list = (Object[])type.getMethod("values", new Class[0]).invoke(type, new Object[0]);
      for (Object e : list)
      {
        String l = ((EnumType)e).getLiteral();
        if (l.equals(literal)) {
          return (T)e;
        }
      }
    }
    catch (Exception e)
    {
      throw new SQLException("Unknown enum literal found : " + literal);
    }
    return null;
  }
  
  private static final Object[] convertArray(Object array, Class<? extends Object[]> type)
    throws SQLException
  {
    if ((array instanceof Object[])) {
      return (Object[])Convert.convert(array, type);
    }
    if ((array instanceof java.sql.Array)) {
      return convertArray((java.sql.Array)array, type);
    }
    return null;
  }
  
  private static final Object[] convertArray(java.sql.Array array, Class<? extends Object[]> type)
    throws SQLException
  {
    if (array != null) {
      return (Object[])Convert.convert(array.getArray(), type);
    }
    return null;
  }
  
  private static final java.sql.Date getDate(SQLDialect dialect, ResultSet rs, int index)
    throws SQLException
  {
    if (dialect == SQLDialect.SQLITE)
    {
      String date = rs.getString(index);
      if (date != null) {
        return new java.sql.Date(parse("yyyy-MM-dd", date));
      }
      return null;
    }
    if (dialect == SQLDialect.CUBRID)
    {
      java.sql.Date date = rs.getDate(index);
      if (date != null)
      {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(14, 0);
        date = new java.sql.Date(cal.getTimeInMillis());
      }
      return date;
    }
    return rs.getDate(index);
  }
  
  private static final Time getTime(SQLDialect dialect, ResultSet rs, int index)
    throws SQLException
  {
    if (dialect == SQLDialect.SQLITE)
    {
      String time = rs.getString(index);
      if (time != null) {
        return new Time(parse("HH:mm:ss", time));
      }
      return null;
    }
    if (dialect == SQLDialect.CUBRID)
    {
      Time time = rs.getTime(index);
      if (time != null)
      {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        cal.set(14, 0);
        time = new Time(cal.getTimeInMillis());
      }
      return time;
    }
    return rs.getTime(index);
  }
  
  private static final Timestamp getTimestamp(SQLDialect dialect, ResultSet rs, int index)
    throws SQLException
  {
    if (dialect == SQLDialect.SQLITE)
    {
      String timestamp = rs.getString(index);
      if (timestamp != null) {
        return new Timestamp(parse("yyyy-MM-dd HH:mm:ss", timestamp));
      }
      return null;
    }
    return rs.getTimestamp(index);
  }
  
  private static final long parse(String pattern, String date)
    throws SQLException
  {
    try
    {
      return Long.valueOf(date).longValue();
    }
    catch (NumberFormatException e)
    {
      return new SimpleDateFormat(pattern).parse(date).getTime();
    }
    catch (ParseException e)
    {
      throw new SQLException("Could not parse date " + date, e);
    }
  }
  
  private static final <T> T pgFromString(Class<T> type, String string)
    throws SQLException
  {
    if (string == null) {
      return null;
    }
    if (type != Blob.class)
    {
      if (type == Boolean.class) {
        return Boolean.valueOf(string);
      }
      if (type == BigInteger.class) {
        return new BigInteger(string);
      }
      if (type == BigDecimal.class) {
        return new BigDecimal(string);
      }
      if (type == Byte.class) {
        return Byte.valueOf(string);
      }
      if (type == byte[].class) {
        return PostgresUtils.toBytes(string);
      }
      if (type != Clob.class)
      {
        if (type == java.sql.Date.class)
        {
          SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
          return new java.sql.Date(pgParseDate(string, f).getTime());
        }
        if (type == Double.class) {
          return Double.valueOf(string);
        }
        if (type == Float.class) {
          return Float.valueOf(string);
        }
        if (type == Integer.class) {
          return Integer.valueOf(string);
        }
        if (type == Long.class) {
          return Long.valueOf(string);
        }
        if (type == Short.class) {
          return Short.valueOf(string);
        }
        if (type == String.class) {
          return string;
        }
        if (type == Time.class)
        {
          SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
          return new Time(pgParseDate(string, f).getTime());
        }
        if (type == Timestamp.class)
        {
          SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          return new Timestamp(pgParseDate(string, f).getTime());
        }
        if (type == UByte.class) {
          return UByte.valueOf(string);
        }
        if (type == UShort.class) {
          return UShort.valueOf(string);
        }
        if (type == UInteger.class) {
          return UInteger.valueOf(string);
        }
        if (type == ULong.class) {
          return ULong.valueOf(string);
        }
        if (type == UUID.class) {
          return UUID.fromString(string);
        }
        if (type.isArray()) {
          return pgNewArray(type, string);
        }
        if (EnumType.class.isAssignableFrom(type)) {
          return (T)getEnumType(type, string);
        }
        if (UDTRecord.class.isAssignableFrom(type)) {
          return pgNewUDTRecord(type, string);
        }
      }
    }
    throw new UnsupportedOperationException("Class " + type + " is not supported");
  }
  
  private static final java.util.Date pgParseDate(String string, SimpleDateFormat f)
    throws SQLException
  {
    try
    {
      return f.parse(string);
    }
    catch (ParseException e)
    {
      throw new SQLException(e);
    }
  }
  
  private static final UDTRecord<?> pgNewUDTRecord(Class<?> type, Object object)
    throws SQLException
  {
    if (object == null) {
      return null;
    }
    (UDTRecord)Utils.newRecord(true, type).operate(new RecordOperation()
    {
      public UDTRecord<?> operate(UDTRecord<?> record)
        throws SQLException
      {
        List<String> values = PostgresUtils.toPGObject(this.val$object.toString());
        
        Row row = record.fieldsRow();
        for (int i = 0; i < row.size(); i++) {
          DefaultBinding.pgSetValue(record, row.field(i), (String)values.get(i));
        }
        return record;
      }
    });
  }
  
  /* Error */
  private static final <T> T pgGetArray(org.jooq.Scope ctx, ResultSet rs, Class<T> type, int index)
    throws SQLException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aload_1
    //   4: iload_3
    //   5: invokeinterface 307 2 0
    //   10: astore 4
    //   12: aload 4
    //   14: ifnonnull +14 -> 28
    //   17: aconst_null
    //   18: astore 5
    //   20: aload 4
    //   22: invokestatic 424	org/jooq/tools/jdbc/JDBCUtils:safeFree	(Ljava/sql/Array;)V
    //   25: aload 5
    //   27: areturn
    //   28: aload 4
    //   30: aload_2
    //   31: invokestatic 308	org/jooq/impl/DefaultBinding:convertArray	(Ljava/sql/Array;Ljava/lang/Class;)[Ljava/lang/Object;
    //   34: astore 5
    //   36: aload 4
    //   38: invokestatic 424	org/jooq/tools/jdbc/JDBCUtils:safeFree	(Ljava/sql/Array;)V
    //   41: aload 5
    //   43: areturn
    //   44: astore 5
    //   46: new 425	java/util/ArrayList
    //   49: dup
    //   50: invokespecial 426	java/util/ArrayList:<init>	()V
    //   53: astore 6
    //   55: aconst_null
    //   56: astore 7
    //   58: aload 4
    //   60: invokeinterface 427 1 0
    //   65: astore 7
    //   67: aload 7
    //   69: invokeinterface 428 1 0
    //   74: ifeq +61 -> 135
    //   77: new 429	org/jooq/impl/DefaultBindingGetResultSetContext
    //   80: dup
    //   81: aload_0
    //   82: invokeinterface 430 1 0
    //   87: aload 7
    //   89: iconst_2
    //   90: invokespecial 431	org/jooq/impl/DefaultBindingGetResultSetContext:<init>	(Lorg/jooq/Configuration;Ljava/sql/ResultSet;I)V
    //   93: astore 8
    //   95: new 8	org/jooq/impl/DefaultBinding
    //   98: dup
    //   99: new 9	org/jooq/impl/IdentityConverter
    //   102: dup
    //   103: aload_2
    //   104: invokevirtual 118	java/lang/Class:getComponentType	()Ljava/lang/Class;
    //   107: invokespecial 11	org/jooq/impl/IdentityConverter:<init>	(Ljava/lang/Class;)V
    //   110: iconst_0
    //   111: invokespecial 2	org/jooq/impl/DefaultBinding:<init>	(Lorg/jooq/Converter;Z)V
    //   114: aload 8
    //   116: invokevirtual 432	org/jooq/impl/DefaultBinding:get	(Lorg/jooq/BindingGetResultSetContext;)V
    //   119: aload 6
    //   121: aload 8
    //   123: invokevirtual 433	org/jooq/impl/DefaultBindingGetResultSetContext:value	()Ljava/lang/Object;
    //   126: invokeinterface 434 2 0
    //   131: pop
    //   132: goto -65 -> 67
    //   135: aload 7
    //   137: invokestatic 435	org/jooq/tools/jdbc/JDBCUtils:safeClose	(Ljava/sql/ResultSet;)V
    //   140: goto +61 -> 201
    //   143: astore 8
    //   145: aconst_null
    //   146: astore 9
    //   148: aload_1
    //   149: iload_3
    //   150: invokeinterface 279 2 0
    //   155: astore 9
    //   157: goto +5 -> 162
    //   160: astore 10
    //   162: getstatic 159	org/jooq/impl/DefaultBinding:log	Lorg/jooq/tools/JooqLogger;
    //   165: ldc_w 436
    //   168: aload 9
    //   170: aload 8
    //   172: invokevirtual 437	org/jooq/tools/JooqLogger:error	(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Throwable;)V
    //   175: aconst_null
    //   176: astore 10
    //   178: aload 7
    //   180: invokestatic 435	org/jooq/tools/jdbc/JDBCUtils:safeClose	(Ljava/sql/ResultSet;)V
    //   183: aload 4
    //   185: invokestatic 424	org/jooq/tools/jdbc/JDBCUtils:safeFree	(Ljava/sql/Array;)V
    //   188: aload 10
    //   190: areturn
    //   191: astore 11
    //   193: aload 7
    //   195: invokestatic 435	org/jooq/tools/jdbc/JDBCUtils:safeClose	(Ljava/sql/ResultSet;)V
    //   198: aload 11
    //   200: athrow
    //   201: aload 6
    //   203: invokeinterface 438 1 0
    //   208: aload_2
    //   209: invokestatic 345	org/jooq/impl/DefaultBinding:convertArray	(Ljava/lang/Object;Ljava/lang/Class;)[Ljava/lang/Object;
    //   212: astore 8
    //   214: aload 4
    //   216: invokestatic 424	org/jooq/tools/jdbc/JDBCUtils:safeFree	(Ljava/sql/Array;)V
    //   219: aload 8
    //   221: areturn
    //   222: astore 12
    //   224: aload 4
    //   226: invokestatic 424	org/jooq/tools/jdbc/JDBCUtils:safeFree	(Ljava/sql/Array;)V
    //   229: aload 12
    //   231: athrow
    // Line number table:
    //   Java source line #2038	-> byte code offset #0
    //   Java source line #2041	-> byte code offset #3
    //   Java source line #2042	-> byte code offset #12
    //   Java source line #2043	-> byte code offset #17
    //   Java source line #2088	-> byte code offset #20
    //   Java source line #2048	-> byte code offset #28
    //   Java source line #2088	-> byte code offset #36
    //   Java source line #2052	-> byte code offset #44
    //   Java source line #2053	-> byte code offset #46
    //   Java source line #2054	-> byte code offset #55
    //   Java source line #2058	-> byte code offset #58
    //   Java source line #2060	-> byte code offset #67
    //   Java source line #2061	-> byte code offset #77
    //   Java source line #2062	-> byte code offset #95
    //   Java source line #2063	-> byte code offset #119
    //   Java source line #2064	-> byte code offset #132
    //   Java source line #2080	-> byte code offset #135
    //   Java source line #2081	-> byte code offset #140
    //   Java source line #2068	-> byte code offset #143
    //   Java source line #2069	-> byte code offset #145
    //   Java source line #2071	-> byte code offset #148
    //   Java source line #2073	-> byte code offset #157
    //   Java source line #2075	-> byte code offset #162
    //   Java source line #2076	-> byte code offset #175
    //   Java source line #2080	-> byte code offset #178
    //   Java source line #2088	-> byte code offset #183
    //   Java source line #2080	-> byte code offset #191
    //   Java source line #2083	-> byte code offset #201
    //   Java source line #2088	-> byte code offset #214
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	232	0	ctx	org.jooq.Scope
    //   0	232	1	rs	ResultSet
    //   0	232	2	type	Class<T>
    //   0	232	3	index	int
    //   1	224	4	array	java.sql.Array
    //   18	24	5	localObject1	Object
    //   44	3	5	e	Exception
    //   53	149	6	result	List<Object>
    //   56	138	7	arrayRs	ResultSet
    //   93	29	8	out	DefaultBindingGetResultSetContext<T>
    //   143	77	8	fatal	Exception
    //   146	23	9	string	String
    //   160	29	10	localSQLException	SQLException
    //   191	8	11	localObject2	Object
    //   222	8	12	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   28	36	44	java/lang/Exception
    //   58	135	143	java/lang/Exception
    //   148	157	160	java/sql/SQLException
    //   58	135	191	finally
    //   143	178	191	finally
    //   191	193	191	finally
    //   3	20	222	finally
    //   28	36	222	finally
    //   44	183	222	finally
    //   191	214	222	finally
    //   222	224	222	finally
  }
  
  private static final Object[] pgNewArray(Class<?> type, String string)
    throws SQLException
  {
    if (string == null) {
      return null;
    }
    try
    {
      Class<?> component = type.getComponentType();
      String values = string.replaceAll("^\\{(.*)\\}$", "$1");
      if ("".equals(values)) {
        return (Object[])java.lang.reflect.Array.newInstance(component, 0);
      }
      String[] split = values.split(",");
      Object[] result = (Object[])java.lang.reflect.Array.newInstance(component, split.length);
      for (int i = 0; i < split.length; i++) {
        result[i] = pgFromString(type.getComponentType(), split[i]);
      }
      return result;
    }
    catch (Exception e)
    {
      throw new SQLException(e);
    }
  }
  
  private static final <T> void pgSetValue(UDTRecord<?> record, Field<T> field, String value)
    throws SQLException
  {
    record.setValue(field, pgFromString(field.getType(), value));
  }
  
  private static final void pgRenderEnumCast(RenderContext render, Class<?> type)
  {
    Class<?> enumType = type.isArray() ? type.getComponentType() : type;
    
    EnumType e = (EnumType)enumType.getEnumConstants()[0];
    Schema schema = e.getSchema();
    if (schema != null)
    {
      render.sql("::");
      
      schema = DSL.using(render.configuration()).map(schema);
      if ((schema != null) && (Boolean.TRUE.equals(render.configuration().settings().isRenderSchema())))
      {
        render.visit(schema);
        render.sql(".");
      }
      render.visit(DSL.name(new String[] { e.getName() }));
    }
    if (type.isArray()) {
      render.sql("[]");
    }
  }
}

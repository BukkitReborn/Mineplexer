package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;
import org.jooq.util.cubrid.CUBRIDDataType;
import org.jooq.util.derby.DerbyDataType;
import org.jooq.util.firebird.FirebirdDataType;
import org.jooq.util.h2.H2DataType;
import org.jooq.util.hsqldb.HSQLDBDataType;
import org.jooq.util.mariadb.MariaDBDataType;
import org.jooq.util.mysql.MySQLDataType;
import org.jooq.util.postgres.PostgresDataType;
import org.jooq.util.sqlite.SQLiteDataType;

public final class SQLDataType
{
  public static final DataType<String> VARCHAR = new DefaultDataType(null, String.class, "varchar");
  public static final DataType<String> CHAR = new DefaultDataType(null, String.class, "char");
  public static final DataType<String> LONGVARCHAR = new DefaultDataType(null, String.class, "longvarchar");
  public static final DataType<String> CLOB = new DefaultDataType(null, String.class, "clob");
  public static final DataType<String> NVARCHAR = new DefaultDataType(null, String.class, "nvarchar");
  public static final DataType<String> NCHAR = new DefaultDataType(null, String.class, "nchar");
  public static final DataType<String> LONGNVARCHAR = new DefaultDataType(null, String.class, "longnvarchar");
  public static final DataType<String> NCLOB = new DefaultDataType(null, String.class, "nclob");
  public static final DataType<Boolean> BOOLEAN = new DefaultDataType(null, Boolean.class, "boolean");
  public static final DataType<Boolean> BIT = new DefaultDataType(null, Boolean.class, "bit");
  public static final DataType<Byte> TINYINT = new DefaultDataType(null, Byte.class, "tinyint");
  public static final DataType<Short> SMALLINT = new DefaultDataType(null, Short.class, "smallint");
  public static final DataType<Integer> INTEGER = new DefaultDataType(null, Integer.class, "integer");
  public static final DataType<Long> BIGINT = new DefaultDataType(null, Long.class, "bigint");
  public static final DataType<BigInteger> DECIMAL_INTEGER = new DefaultDataType(null, BigInteger.class, "decimal_integer");
  public static final DataType<UByte> TINYINTUNSIGNED = new DefaultDataType(null, UByte.class, "tinyintunsigned");
  public static final DataType<UShort> SMALLINTUNSIGNED = new DefaultDataType(null, UShort.class, "smallintunsigned");
  public static final DataType<UInteger> INTEGERUNSIGNED = new DefaultDataType(null, UInteger.class, "integerunsigned");
  public static final DataType<ULong> BIGINTUNSIGNED = new DefaultDataType(null, ULong.class, "bigintunsigned");
  public static final DataType<Double> DOUBLE = new DefaultDataType(null, Double.class, "double");
  public static final DataType<Double> FLOAT = new DefaultDataType(null, Double.class, "float");
  public static final DataType<Float> REAL = new DefaultDataType(null, Float.class, "real");
  public static final DataType<BigDecimal> NUMERIC = new DefaultDataType(null, BigDecimal.class, "numeric");
  public static final DataType<BigDecimal> DECIMAL = new DefaultDataType(null, BigDecimal.class, "decimal");
  public static final DataType<Date> DATE = new DefaultDataType(null, Date.class, "date");
  public static final DataType<Timestamp> TIMESTAMP = new DefaultDataType(null, Timestamp.class, "timestamp");
  public static final DataType<Time> TIME = new DefaultDataType(null, Time.class, "time");
  public static final DataType<YearToMonth> INTERVALYEARTOMONTH = new DefaultDataType(null, YearToMonth.class, "interval year to month");
  public static final DataType<DayToSecond> INTERVALDAYTOSECOND = new DefaultDataType(null, DayToSecond.class, "interval day to second");
  public static final DataType<byte[]> BINARY = new DefaultDataType(null, byte[].class, "binary");
  public static final DataType<byte[]> VARBINARY = new DefaultDataType(null, byte[].class, "varbinary");
  public static final DataType<byte[]> LONGVARBINARY = new DefaultDataType(null, byte[].class, "longvarbinary");
  public static final DataType<byte[]> BLOB = new DefaultDataType(null, byte[].class, "blob");
  public static final DataType<Object> OTHER = new DefaultDataType(null, Object.class, "other");
  public static final DataType<Result<Record>> RESULT = new DefaultDataType(null, Result.class, "result");
  public static final DataType<UUID> UUID = new DefaultDataType(null, UUID.class, "uuid");
  
  static
  {
    try
    {
      Class.forName(CUBRIDDataType.class.getName());
      Class.forName(DerbyDataType.class.getName());
      Class.forName(FirebirdDataType.class.getName());
      Class.forName(H2DataType.class.getName());
      Class.forName(HSQLDBDataType.class.getName());
      Class.forName(MariaDBDataType.class.getName());
      Class.forName(MySQLDataType.class.getName());
      Class.forName(PostgresDataType.class.getName());
      Class.forName(SQLiteDataType.class.getName());
    }
    catch (Exception localException) {}
  }
}

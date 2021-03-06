package org.jooq.util.mariadb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

public class MariaDBDataType
{
  public static final DataType<Byte> TINYINT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.TINYINT, "tinyint", "signed");
  public static final DataType<UByte> TINYINTUNSIGNED = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.TINYINTUNSIGNED, "tinyintunsigned", "unsigned");
  public static final DataType<Short> SMALLINT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.SMALLINT, "smallint", "signed");
  public static final DataType<UShort> SMALLINTUNSIGNED = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.SMALLINTUNSIGNED, "smallintunsigned", "unsigned");
  public static final DataType<Integer> INT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.INTEGER, "int", "signed");
  public static final DataType<UInteger> INTUNSIGNED = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.INTEGERUNSIGNED, "intunsigned", "unsigned");
  public static final DataType<Integer> MEDIUMINT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.INTEGER, "mediumint", "signed");
  public static final DataType<UInteger> MEDIUMINTUNSIGNED = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.INTEGERUNSIGNED, "mediumintunsigned", "unsigned");
  public static final DataType<Integer> INTEGER = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.INTEGER, "integer", "signed");
  public static final DataType<UInteger> INTEGERUNSIGNED = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.INTEGERUNSIGNED, "integerunsigned", "unsigned");
  public static final DataType<Long> BIGINT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BIGINT, "bigint", "signed");
  public static final DataType<ULong> BIGINTUNSIGNED = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BIGINTUNSIGNED, "bigintunsigned", "unsigned");
  public static final DataType<Double> DOUBLE = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.DOUBLE, "double", "decimal");
  public static final DataType<Double> FLOAT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.FLOAT, "float", "decimal");
  public static final DataType<Float> REAL = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.REAL, "real", "decimal");
  public static final DataType<Boolean> BOOLEAN = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BOOLEAN, "boolean", "unsigned");
  public static final DataType<Boolean> BOOL = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BOOLEAN, "bool", "unsigned");
  public static final DataType<Boolean> BIT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BIT, "bit", "unsigned");
  public static final DataType<BigDecimal> DECIMAL = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.DECIMAL, "decimal", "decimal");
  public static final DataType<BigDecimal> DEC = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.DECIMAL, "dec", "decimal");
  public static final DataType<String> VARCHAR = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.VARCHAR, "varchar", "char");
  public static final DataType<String> CHAR = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.CHAR, "char", "char");
  public static final DataType<String> TEXT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.CLOB, "text", "char");
  public static final DataType<byte[]> BLOB = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BLOB, "blob", "binary");
  public static final DataType<byte[]> BINARY = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BINARY, "binary", "binary");
  public static final DataType<byte[]> VARBINARY = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.VARBINARY, "varbinary", "binary");
  public static final DataType<Date> DATE = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.DATE, "date", "date");
  public static final DataType<Time> TIME = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.TIME, "time", "time");
  public static final DataType<Timestamp> TIMESTAMP = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.TIMESTAMP, "timestamp", "datetime");
  public static final DataType<Timestamp> DATETIME = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.TIMESTAMP, "datetime", "datetime");
  protected static final DataType<String> __NCHAR = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.NCHAR, "char", "char");
  protected static final DataType<String> __NCLOB = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.NCLOB, "clob", "char");
  protected static final DataType<String> __LONGNVARCHAR = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.LONGNVARCHAR, "varchar", "char");
  protected static final DataType<BigDecimal> __NUMERIC = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.NUMERIC, "decimal", "decimal");
  protected static final DataType<String> __NVARCHAR = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.NVARCHAR, "varchar", "char");
  protected static final DataType<String> __LONGVARCHAR = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.LONGVARCHAR, "varchar", "char");
  protected static final DataType<byte[]> __LONGVARBINARY = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.LONGVARBINARY, "varbinary", "binary");
  protected static final DataType<BigInteger> __BIGINTEGER = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal");
  protected static final DataType<UUID> __UUID = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.UUID, "varchar", "char");
  public static final DataType<String> TINYTEXT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.CLOB, "tinytext", "char");
  public static final DataType<String> MEDIUMTEXT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.CLOB, "mediumtext", "char");
  public static final DataType<String> LONGTEXT = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.CLOB, "longtext", "char");
  public static final DataType<String> ENUM = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.VARCHAR, "enum", "char");
  public static final DataType<String> SET = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.VARCHAR, "set", "char");
  public static final DataType<byte[]> TINYBLOB = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BLOB, "tinyblob", "binary");
  public static final DataType<byte[]> MEDIUMBLOB = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BLOB, "mediumblob", "binary");
  public static final DataType<byte[]> LONGBLOB = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.BLOB, "longblob", "binary");
  public static final DataType<Date> YEAR = new DefaultDataType(SQLDialect.MARIADB, SQLDataType.DATE, "year", "date");
}

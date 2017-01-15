package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.jooq.AggregateFunction;
import org.jooq.AlterSequenceRestartStep;
import org.jooq.AlterTableStep;
import org.jooq.Case;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.CreateIndexStep;
import org.jooq.CreateSequenceFinalStep;
import org.jooq.CreateTableAsStep;
import org.jooq.CreateViewAsStep;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.DeleteWhereStep;
import org.jooq.DropIndexFinalStep;
import org.jooq.DropSequenceFinalStep;
import org.jooq.DropTableStep;
import org.jooq.DropViewFinalStep;
import org.jooq.Field;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.GroupField;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep11;
import org.jooq.InsertValuesStep12;
import org.jooq.InsertValuesStep13;
import org.jooq.InsertValuesStep14;
import org.jooq.InsertValuesStep15;
import org.jooq.InsertValuesStep16;
import org.jooq.InsertValuesStep17;
import org.jooq.InsertValuesStep18;
import org.jooq.InsertValuesStep19;
import org.jooq.InsertValuesStep2;
import org.jooq.InsertValuesStep20;
import org.jooq.InsertValuesStep21;
import org.jooq.InsertValuesStep22;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.InsertValuesStep7;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.jooq.InsertValuesStepN;
import org.jooq.Keyword;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeKeyStepN;
import org.jooq.MergeUsingStep;
import org.jooq.Name;
import org.jooq.OrderedAggregateFunction;
import org.jooq.Param;
import org.jooq.QuantifiedSelect;
import org.jooq.Query;
import org.jooq.QueryPart;
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
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.Template;
import org.jooq.TruncateIdentityStep;
import org.jooq.UDTRecord;
import org.jooq.UpdateSetFirstStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOverStep;
import org.jooq.WindowSpecificationFinalStep;
import org.jooq.WindowSpecificationOrderByStep;
import org.jooq.WindowSpecificationRowsAndStep;
import org.jooq.WindowSpecificationRowsStep;
import org.jooq.WithAsStep;
import org.jooq.WithStep;
import org.jooq.conf.Settings;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.Convert;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.types.DayToSecond;

public class DSL
{
  public static DSLContext using(SQLDialect dialect)
  {
    return new DefaultDSLContext(dialect, null);
  }
  
  public static DSLContext using(SQLDialect dialect, Settings settings)
  {
    return new DefaultDSLContext(dialect, settings);
  }
  
  public static DSLContext using(String url)
  {
    try
    {
      Connection connection = DriverManager.getConnection(url);
      return using(new DefaultConnectionProvider(connection, true), JDBCUtils.dialect(connection));
    }
    catch (SQLException e)
    {
      throw Utils.translate("Error when initialising Connection", e);
    }
  }
  
  public static DSLContext using(String url, String username, String password)
  {
    try
    {
      Connection connection = DriverManager.getConnection(url, username, password);
      return using(new DefaultConnectionProvider(connection, true), JDBCUtils.dialect(connection));
    }
    catch (SQLException e)
    {
      throw Utils.translate("Error when initialising Connection", e);
    }
  }
  
  public static DSLContext using(String url, Properties properties)
  {
    try
    {
      Connection connection = DriverManager.getConnection(url, properties);
      return using(new DefaultConnectionProvider(connection, true), JDBCUtils.dialect(connection));
    }
    catch (SQLException e)
    {
      throw Utils.translate("Error when initialising Connection", e);
    }
  }
  
  public static DSLContext using(Connection connection)
  {
    return new DefaultDSLContext(connection, JDBCUtils.dialect(connection), null);
  }
  
  public static DSLContext using(Connection connection, SQLDialect dialect)
  {
    return new DefaultDSLContext(connection, dialect, null);
  }
  
  public static DSLContext using(Connection connection, Settings settings)
  {
    return new DefaultDSLContext(connection, JDBCUtils.dialect(connection), settings);
  }
  
  public static DSLContext using(Connection connection, SQLDialect dialect, Settings settings)
  {
    return new DefaultDSLContext(connection, dialect, settings);
  }
  
  public static DSLContext using(DataSource datasource, SQLDialect dialect)
  {
    return new DefaultDSLContext(datasource, dialect);
  }
  
  public static DSLContext using(DataSource datasource, SQLDialect dialect, Settings settings)
  {
    return new DefaultDSLContext(datasource, dialect, settings);
  }
  
  public static DSLContext using(ConnectionProvider connectionProvider, SQLDialect dialect)
  {
    return new DefaultDSLContext(connectionProvider, dialect);
  }
  
  public static DSLContext using(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings)
  {
    return new DefaultDSLContext(connectionProvider, dialect, settings);
  }
  
  public static DSLContext using(Configuration configuration)
  {
    return new DefaultDSLContext(configuration);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static WithAsStep with(String alias)
  {
    return new WithImpl(null, false).with(alias);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static WithAsStep with(String alias, String... fieldAliases)
  {
    return new WithImpl(null, false).with(alias, fieldAliases);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static WithStep with(CommonTableExpression<?>... tables)
  {
    return new WithImpl(null, false).with(tables);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static WithAsStep withRecursive(String alias)
  {
    return new WithImpl(null, true).with(alias);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static WithAsStep withRecursive(String alias, String... fieldAliases)
  {
    return new WithImpl(null, true).with(alias, fieldAliases);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static WithStep withRecursive(CommonTableExpression<?>... tables)
  {
    return new WithImpl(null, true).with(tables);
  }
  
  @Support
  public static <R extends Record> SelectWhereStep<R> selectFrom(Table<R> table)
  {
    return new SelectImpl(null, new DefaultConfiguration()).from(table);
  }
  
  @Support
  public static SelectSelectStep<Record> select(Collection<? extends Field<?>> fields)
  {
    return new SelectImpl(null, new DefaultConfiguration()).select(fields);
  }
  
  @Support
  public static SelectSelectStep<Record> select(Field<?>... fields)
  {
    return new SelectImpl(null, new DefaultConfiguration()).select(fields);
  }
  
  @Support
  public static <T1> SelectSelectStep<Record1<T1>> select(Field<T1> field1)
  {
    return select(new Field[] { field1 });
  }
  
  @Support
  public static <T1, T2> SelectSelectStep<Record2<T1, T2>> select(Field<T1> field1, Field<T2> field2)
  {
    return select(new Field[] { field1, field2 });
  }
  
  @Support
  public static <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return select(new Field[] { field1, field2, field3 });
  }
  
  @Support
  public static <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return select(new Field[] { field1, field2, field3, field4 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return select(new Field[] { field1, field2, field3, field4, field5 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  @Support
  public static SelectSelectStep<Record> selectDistinct(Collection<? extends Field<?>> fields)
  {
    return new SelectImpl(null, new DefaultConfiguration(), true).select(fields);
  }
  
  @Support
  public static SelectSelectStep<Record> selectDistinct(Field<?>... fields)
  {
    return new SelectImpl(null, new DefaultConfiguration(), true).select(fields);
  }
  
  @Support
  public static <T1> SelectSelectStep<Record1<T1>> selectDistinct(Field<T1> field1)
  {
    return selectDistinct(new Field[] { field1 });
  }
  
  @Support
  public static <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(Field<T1> field1, Field<T2> field2)
  {
    return selectDistinct(new Field[] { field1, field2 });
  }
  
  @Support
  public static <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return selectDistinct(new Field[] { field1, field2, field3 });
  }
  
  @Support
  public static <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  @Support
  public static SelectSelectStep<Record1<Integer>> selectZero()
  {
    return new SelectImpl(null, new DefaultConfiguration()).select(new Field[] { zero().as("zero") });
  }
  
  @Support
  public static SelectSelectStep<Record1<Integer>> selectOne()
  {
    return new SelectImpl(null, new DefaultConfiguration()).select(new Field[] { one().as("one") });
  }
  
  @Support
  public static SelectSelectStep<Record1<Integer>> selectCount()
  {
    return new SelectImpl(null, new DefaultConfiguration()).select(new Field[] { count() });
  }
  
  @Support
  public static <R extends Record> InsertSetStep<R> insertInto(Table<R> into)
  {
    return using(new DefaultConfiguration()).insertInto(into);
  }
  
  @Support
  public static <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> into, Field<T1> field1)
  {
    return (InsertValuesStep1)insertInto(into, new Field[] { field1 });
  }
  
  @Support
  public static <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2)
  {
    return (InsertValuesStep2)insertInto(into, new Field[] { field1, field2 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return (InsertValuesStep3)insertInto(into, new Field[] { field1, field2, field3 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return (InsertValuesStep4)insertInto(into, new Field[] { field1, field2, field3, field4 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return (InsertValuesStep5)insertInto(into, new Field[] { field1, field2, field3, field4, field5 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return (InsertValuesStep6)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return (InsertValuesStep7)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return (InsertValuesStep8)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return (InsertValuesStep9)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return (InsertValuesStep10)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return (InsertValuesStep11)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return (InsertValuesStep12)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return (InsertValuesStep13)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return (InsertValuesStep14)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return (InsertValuesStep15)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return (InsertValuesStep16)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return (InsertValuesStep17)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return (InsertValuesStep18)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return (InsertValuesStep19)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return (InsertValuesStep20)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return (InsertValuesStep21)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  @Support
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return (InsertValuesStep22)insertInto(into, new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  @Support
  public static <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Field<?>... fields)
  {
    return using(new DefaultConfiguration()).insertInto(into, fields);
  }
  
  @Support
  public static <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Collection<? extends Field<?>> fields)
  {
    return using(new DefaultConfiguration()).insertInto(into, fields);
  }
  
  @Support
  public static <R extends Record> UpdateSetFirstStep<R> update(Table<R> table)
  {
    return using(new DefaultConfiguration()).update(table);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public static <R extends Record> MergeUsingStep<R> mergeInto(Table<R> table)
  {
    return using(new DefaultConfiguration()).mergeInto(table);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> table, Field<T1> field1)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Field<?>... fields)
  {
    return using(new DefaultConfiguration()).mergeInto(table, fields);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public static <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Collection<? extends Field<?>> fields)
  {
    return using(new DefaultConfiguration()).mergeInto(table, fields);
  }
  
  @Support
  public static <R extends Record> DeleteWhereStep<R> delete(Table<R> table)
  {
    return using(new DefaultConfiguration()).delete(table);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static CreateTableAsStep<Record> createTable(String tableName)
  {
    return createTable(tableByName(new String[] { tableName }));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static CreateTableAsStep<Record> createTable(Table<?> table)
  {
    return using(new DefaultConfiguration()).createTable(table);
  }
  
  @Support
  public static CreateViewAsStep createView(String viewName, String... fieldNames)
  {
    return createView(tableByName(new String[] { viewName }), Utils.fieldsByName(viewName, fieldNames));
  }
  
  @Support
  public static CreateViewAsStep createView(Table<?> view, Field<?>... fields)
  {
    return using(new DefaultConfiguration()).createView(view, fields);
  }
  
  @Support
  public static CreateIndexStep createIndex(String index)
  {
    return using(new DefaultConfiguration()).createIndex(index);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static CreateSequenceFinalStep createSequence(Sequence<?> sequence)
  {
    return using(new DefaultConfiguration()).createSequence(sequence);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static CreateSequenceFinalStep createSequence(String sequence)
  {
    return using(new DefaultConfiguration()).createSequence(sequence);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T extends Number> AlterSequenceRestartStep<T> alterSequence(Sequence<T> sequence)
  {
    return using(new DefaultConfiguration()).alterSequence(sequence);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static AlterSequenceRestartStep<BigInteger> alterSequence(String sequence)
  {
    return using(new DefaultConfiguration()).alterSequence(sequence);
  }
  
  @Support
  public static AlterTableStep alterTable(Table<?> table)
  {
    return using(new DefaultConfiguration()).alterTable(table);
  }
  
  @Support
  public static AlterTableStep alterTable(String table)
  {
    return using(new DefaultConfiguration()).alterTable(table);
  }
  
  @Support
  public static DropViewFinalStep dropView(Table<?> table)
  {
    return using(new DefaultConfiguration()).dropView(table);
  }
  
  @Support
  public static DropViewFinalStep dropView(String table)
  {
    return using(new DefaultConfiguration()).dropView(table);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static DropViewFinalStep dropViewIfExists(Table<?> table)
  {
    return using(new DefaultConfiguration()).dropViewIfExists(table);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static DropViewFinalStep dropViewIfExists(String table)
  {
    return using(new DefaultConfiguration()).dropViewIfExists(table);
  }
  
  @Support
  public static DropTableStep dropTable(Table<?> table)
  {
    return using(new DefaultConfiguration()).dropTable(table);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static DropTableStep dropTableIfExists(String table)
  {
    return using(new DefaultConfiguration()).dropTableIfExists(table);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static DropTableStep dropTableIfExists(Table<?> table)
  {
    return using(new DefaultConfiguration()).dropTableIfExists(table);
  }
  
  @Support
  public static DropTableStep dropTable(String table)
  {
    return using(new DefaultConfiguration()).dropTable(table);
  }
  
  @Support
  public static DropIndexFinalStep dropIndex(String index)
  {
    return using(new DefaultConfiguration()).dropIndex(index);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static DropIndexFinalStep dropIndexIfExists(String index)
  {
    return using(new DefaultConfiguration()).dropIndexIfExists(index);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T extends Number> DropSequenceFinalStep dropSequence(Sequence<?> sequence)
  {
    return using(new DefaultConfiguration()).dropSequence(sequence);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T extends Number> DropSequenceFinalStep dropSequence(String sequence)
  {
    return using(new DefaultConfiguration()).dropSequence(sequence);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T extends Number> DropSequenceFinalStep dropSequenceIfExists(Sequence<?> sequence)
  {
    return using(new DefaultConfiguration()).dropSequenceIfExists(sequence);
  }
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T extends Number> DropSequenceFinalStep dropSequenceIfExists(String sequence)
  {
    return using(new DefaultConfiguration()).dropSequenceIfExists(sequence);
  }
  
  @Support
  public static <R extends Record> TruncateIdentityStep<R> truncate(Table<R> table)
  {
    return using(new DefaultConfiguration()).truncate(table);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <R extends Record> QuantifiedSelect<R> all(Select<R> select)
  {
    return new QuantifiedSelectImpl(Quantifier.ALL, select);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <T> QuantifiedSelect<Record1<T>> all(T... array)
  {
    return all(val(array));
  }
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T> QuantifiedSelect<Record1<T>> all(Field<T[]> array)
  {
    return new QuantifiedSelectImpl(Quantifier.ALL, array);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <R extends Record> QuantifiedSelect<R> any(Select<R> select)
  {
    return new QuantifiedSelectImpl(Quantifier.ANY, select);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <T> QuantifiedSelect<Record1<T>> any(T... array)
  {
    return any(val(array));
  }
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T> QuantifiedSelect<Record1<T>> any(Field<T[]> array)
  {
    return new QuantifiedSelectImpl(Quantifier.ANY, array);
  }
  
  @Support
  public static <R extends Record> Table<R> table(Select<R> select)
  {
    return select.asTable();
  }
  
  @Support
  public static <R extends Record> Table<R> table(Result<R> result)
  {
    int size = result.size();
    
    RowN[] rows = new RowN[size];
    for (int i = 0; i < size; i++) {
      rows[i] = ((RowN)((Record)result.get(i)).valuesRow());
    }
    Field<?>[] fields = result.fields();
    String[] columns = new String[fields.length];
    for (int i = 0; i < fields.length; i++) {
      columns[i] = fields[i].getName();
    }
    return values(rows).as("v", columns);
  }
  
  @Support
  public static Table<?> table(List<?> list)
  {
    return table(list.toArray());
  }
  
  @Support
  public static Table<?> table(Object[] array)
  {
    return table(val(array));
  }
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static Table<?> table(Field<?> cursor)
  {
    return unnest(cursor);
  }
  
  @Support
  public static Table<?> unnest(List<?> list)
  {
    return unnest(list.toArray());
  }
  
  @Support
  public static Table<?> unnest(Object[] array)
  {
    return unnest(val(array));
  }
  
  @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static Table<?> unnest(Field<?> cursor)
  {
    if (cursor == null) {
      throw new IllegalArgumentException();
    }
    if (cursor.getType() == Result.class) {
      return new FunctionTable(cursor);
    }
    if ((cursor.getType().isArray()) && (cursor.getType() != byte[].class)) {
      return new ArrayTable(cursor);
    }
    throw new SQLDialectNotSupportedException("Converting arbitrary types into array tables is currently not supported");
  }
  
  @Support
  public static Table<Record> dual()
  {
    return new Dual(true);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static Table<Record1<Integer>> generateSeries(int from, int to)
  {
    return generateSeries(val(Integer.valueOf(from)), val(Integer.valueOf(to)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static Table<Record1<Integer>> generateSeries(int from, Field<Integer> to)
  {
    return generateSeries(val(Integer.valueOf(from)), nullSafe(to));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static Table<Record1<Integer>> generateSeries(Field<Integer> from, int to)
  {
    return new GenerateSeries(nullSafe(from), val(Integer.valueOf(to)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static Table<Record1<Integer>> generateSeries(Field<Integer> from, Field<Integer> to)
  {
    return new GenerateSeries(nullSafe(from), nullSafe(to));
  }
  
  @Support({SQLDialect.POSTGRES})
  public static <R extends Record> Table<R> lateral(TableLike<R> table)
  {
    return new Lateral(table.asTable());
  }
  
  public static Keyword keyword(String keyword)
  {
    return new KeywordImpl(keyword);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Object> defaultValue()
  {
    return defaultValue(Object.class);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T> Field<T> defaultValue(Class<T> type)
  {
    return defaultValue(getDataType(type));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T> Field<T> defaultValue(DataType<T> type)
  {
    return new SQLField(type, keyword("default"));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T> Field<T> defaultValue(Field<T> field)
  {
    return new SQLField(field.getDataType(), keyword("default"));
  }
  
  public static Name name(String... qualifiedName)
  {
    return new NameImpl(qualifiedName);
  }
  
  @Support
  public static Schema schemaByName(String name)
  {
    return new SchemaImpl(name);
  }
  
  @Support
  public static Sequence<BigInteger> sequenceByName(String... qualifiedName)
  {
    return sequenceByName(BigInteger.class, qualifiedName);
  }
  
  @Support
  public static <T extends Number> Sequence<T> sequenceByName(Class<T> type, String... qualifiedName)
  {
    return sequenceByName(getDataType(type), qualifiedName);
  }
  
  @Support
  public static <T extends Number> Sequence<T> sequenceByName(DataType<T> type, String... qualifiedName)
  {
    if (qualifiedName == null) {
      throw new NullPointerException();
    }
    if ((qualifiedName.length < 1) || (qualifiedName.length > 2)) {
      throw new IllegalArgumentException("Must provide a qualified name of length 1 or 2 : " + name(qualifiedName));
    }
    String name = qualifiedName[(qualifiedName.length - 1)];
    Schema schema = qualifiedName.length == 2 ? schemaByName(qualifiedName[0]) : null;
    
    return new SequenceImpl(name, schema, type);
  }
  
  @Support
  public static Table<Record> tableByName(String... qualifiedName)
  {
    return new QualifiedTable(qualifiedName);
  }
  
  @Support
  public static Field<Object> fieldByName(String... qualifiedName)
  {
    return fieldByName(Object.class, qualifiedName);
  }
  
  @Support
  public static <T> Field<T> fieldByName(Class<T> type, String... qualifiedName)
  {
    return fieldByName(getDataType(type), qualifiedName);
  }
  
  @Support
  public static <T> Field<T> fieldByName(DataType<T> type, String... qualifiedName)
  {
    return new QualifiedField(type, qualifiedName);
  }
  
  static Template template(String sql)
  {
    return new SQLTemplate(sql);
  }
  
  @Support
  public static QueryPart queryPart(String sql)
  {
    return queryPart(template(sql), new Object[0]);
  }
  
  @Support
  public static QueryPart queryPart(String sql, QueryPart... parts)
  {
    return queryPart(template(sql), (Object[])parts);
  }
  
  @Support
  public static QueryPart queryPart(String sql, Object... bindings)
  {
    return queryPart(template(sql), bindings);
  }
  
  @Support
  static QueryPart queryPart(Template template, Object... parameters)
  {
    return template.transform(parameters);
  }
  
  @Support
  public static Query query(String sql)
  {
    return using(new DefaultConfiguration()).query(sql);
  }
  
  @Support
  public static Query query(String sql, Object... bindings)
  {
    return using(new DefaultConfiguration()).query(sql, bindings);
  }
  
  @Support
  public static Query query(String sql, QueryPart... parts)
  {
    return using(new DefaultConfiguration()).query(sql, parts);
  }
  
  @Support
  public static ResultQuery<Record> resultQuery(String sql)
  {
    return using(new DefaultConfiguration()).resultQuery(sql);
  }
  
  @Support
  public static ResultQuery<Record> resultQuery(String sql, Object... bindings)
  {
    return using(new DefaultConfiguration()).resultQuery(sql, bindings);
  }
  
  @Support
  public static ResultQuery<Record> resultQuery(String sql, QueryPart... parts)
  {
    return using(new DefaultConfiguration()).resultQuery(sql, parts);
  }
  
  @Support
  public static Table<Record> table(String sql)
  {
    return table(sql, new Object[0]);
  }
  
  @Support
  public static Table<Record> table(String sql, Object... bindings)
  {
    return table(template(sql), bindings);
  }
  
  @Support
  public static Table<Record> table(String sql, QueryPart... parts)
  {
    return table(template(sql), (Object[])parts);
  }
  
  @Support
  static Table<Record> table(Template template, Object... parameters)
  {
    return new SQLTable(queryPart(template, parameters));
  }
  
  @Support
  public static Sequence<BigInteger> sequence(String sql)
  {
    return sequence(sql, BigInteger.class);
  }
  
  @Support
  public static <T extends Number> Sequence<T> sequence(String sql, Class<T> type)
  {
    return sequence(sql, getDataType(type));
  }
  
  @Support
  public static <T extends Number> Sequence<T> sequence(String sql, DataType<T> type)
  {
    return new SequenceImpl(sql, null, type, true);
  }
  
  @Support
  public static Field<Object> field(String sql)
  {
    return field(template(sql), new Object[0]);
  }
  
  @Support
  public static Field<Object> field(String sql, Object... bindings)
  {
    return field(template(sql), Object.class, bindings);
  }
  
  @Support
  public static <T> Field<T> field(String sql, Class<T> type)
  {
    return field(template(sql), type, new Object[0]);
  }
  
  @Support
  public static <T> Field<T> field(String sql, Class<T> type, Object... bindings)
  {
    return field(template(sql), getDataType(type), bindings);
  }
  
  @Support
  public static <T> Field<T> field(String sql, DataType<T> type)
  {
    return field(template(sql), type, new Object[0]);
  }
  
  @Support
  public static <T> Field<T> field(String sql, DataType<T> type, Object... bindings)
  {
    return field(template(sql), type, bindings);
  }
  
  public static Field<Object> field(String sql, QueryPart... parts)
  {
    return field(template(sql), (Object[])parts);
  }
  
  public static <T> Field<T> field(String sql, Class<T> type, QueryPart... parts)
  {
    return field(template(sql), type, (Object[])parts);
  }
  
  public static <T> Field<T> field(String sql, DataType<T> type, QueryPart... parts)
  {
    return field(template(sql), type, (Object[])parts);
  }
  
  @Support
  static Field<Object> field(Template template, Object... parameters)
  {
    return field(template, Object.class, parameters);
  }
  
  @Support
  static <T> Field<T> field(Template template, Class<T> type, Object... parameters)
  {
    return field(template, getDataType(type), parameters);
  }
  
  @Support
  static <T> Field<T> field(Template template, DataType<T> type, Object... parameters)
  {
    return new SQLField(type, queryPart(template, parameters));
  }
  
  @Support
  public static <T> Field<T> function(String name, Class<T> type, Field<?>... arguments)
  {
    return function(name, getDataType(type), nullSafe(arguments));
  }
  
  @Support
  public static <T> Field<T> function(String name, DataType<T> type, Field<?>... arguments)
  {
    return new Function(name, type, nullSafe(arguments));
  }
  
  @Support
  public static <T> Field<T> function(Name name, Class<T> type, Field<?>... arguments)
  {
    return function(name, getDataType(type), nullSafe(arguments));
  }
  
  @Support
  public static <T> Field<T> function(Name name, DataType<T> type, Field<?>... arguments)
  {
    return new Function(name, type, nullSafe(arguments));
  }
  
  @Support
  public static Condition condition(String sql)
  {
    return condition(template(sql), new Object[0]);
  }
  
  @Support
  public static Condition condition(String sql, Object... bindings)
  {
    return condition(template(sql), bindings);
  }
  
  @Support
  public static Condition condition(String sql, QueryPart... parts)
  {
    return condition(template(sql), (Object[])parts);
  }
  
  @Support
  static Condition condition(Template template, Object... parameters)
  {
    return new SQLCondition(queryPart(template, parameters));
  }
  
  @Support
  public static Condition condition(Boolean value)
  {
    return condition(Utils.field(value, Boolean.class));
  }
  
  @Support
  public static Condition condition(Field<Boolean> field)
  {
    return new FieldCondition(field);
  }
  
  @Support
  public static Condition trueCondition()
  {
    return new TrueCondition();
  }
  
  @Support
  public static Condition falseCondition()
  {
    return new FalseCondition();
  }
  
  @Support
  public static Condition exists(Select<?> query)
  {
    return new ExistsCondition(query, true);
  }
  
  @Support
  public static Condition notExists(Select<?> query)
  {
    return new ExistsCondition(query, false);
  }
  
  @Support
  public static Condition not(Condition condition)
  {
    return condition.not();
  }
  
  @Support
  public static Field<Boolean> not(Boolean value)
  {
    return not(Utils.field(value, Boolean.class));
  }
  
  @Support
  public static Field<Boolean> not(Field<Boolean> field)
  {
    return new NotField(field);
  }
  
  @Support
  public static Field<Boolean> field(Condition condition)
  {
    return new ConditionAsField(condition);
  }
  
  @Support
  public static <T> Field<T> field(Select<? extends Record1<T>> select)
  {
    return select.asField();
  }
  
  @Support
  public static Case decode()
  {
    return new CaseImpl();
  }
  
  @Support
  public static <Z, T> Field<Z> decode(T value, T search, Z result)
  {
    return decode(value, search, result, new Object[0]);
  }
  
  @Support
  public static <Z, T> Field<Z> decode(T value, T search, Z result, Object... more)
  {
    return decode(Utils.field(value), Utils.field(search), Utils.field(result), (Field[])Utils.fields(more).toArray(new Field[0]));
  }
  
  @Support
  public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result)
  {
    return decode(nullSafe(value), nullSafe(search), nullSafe(result), new Field[0]);
  }
  
  @Support
  public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result, Field<?>... more)
  {
    return new Decode(nullSafe(value), nullSafe(search), nullSafe(result), nullSafe(more));
  }
  
  @Support
  public static <T> Field<T> coerce(Object value, Field<T> as)
  {
    return Utils.field(value).coerce(as);
  }
  
  @Support
  public static <T> Field<T> coerce(Object value, Class<T> as)
  {
    return Utils.field(value).coerce(as);
  }
  
  @Support
  public static <T> Field<T> coerce(Object value, DataType<T> as)
  {
    return Utils.field(value).coerce(as);
  }
  
  @Support
  public static <T> Field<T> coerce(Field<?> field, Field<T> as)
  {
    return nullSafe(field).coerce(as);
  }
  
  @Support
  public static <T> Field<T> coerce(Field<?> field, Class<T> as)
  {
    return nullSafe(field).coerce(as);
  }
  
  @Support
  public static <T> Field<T> coerce(Field<?> field, DataType<T> as)
  {
    return nullSafe(field).coerce(as);
  }
  
  @Support
  public static <T> Field<T> cast(Object value, Field<T> as)
  {
    return Utils.field(value, as).cast(as);
  }
  
  @Support
  public static <T> Field<T> cast(Field<?> field, Field<T> as)
  {
    return nullSafe(field).cast(as);
  }
  
  @Support
  public static <T> Field<T> castNull(Field<T> as)
  {
    return NULL().cast(as);
  }
  
  @Support
  public static <T> Field<T> cast(Object value, Class<T> type)
  {
    return Utils.field(value, type).cast(type);
  }
  
  @Support
  public static <T> Field<T> cast(Field<?> field, Class<T> type)
  {
    return nullSafe(field).cast(type);
  }
  
  @Support
  public static <T> Field<T> castNull(DataType<T> type)
  {
    return NULL().cast(type);
  }
  
  @Support
  public static <T> Field<T> cast(Object value, DataType<T> type)
  {
    return Utils.field(value, type).cast(type);
  }
  
  @Support
  public static <T> Field<T> cast(Field<?> field, DataType<T> type)
  {
    return nullSafe(field).cast(type);
  }
  
  @Support
  public static <T> Field<T> castNull(Class<T> type)
  {
    return NULL().cast(type);
  }
  
  static <T> Field<T>[] castAll(Class<T> type, Field<?>... fields)
  {
    Field<?>[] castFields = new Field[fields.length];
    for (int i = 0; i < fields.length; i++) {
      castFields[i] = fields[i].cast(type);
    }
    return (Field[])castFields;
  }
  
  @Support
  public static <T> Field<T> coalesce(T value, T... values)
  {
    return coalesce(Utils.field(value), (Field[])Utils.fields(values).toArray(new Field[0]));
  }
  
  @Support
  public static <T> Field<T> coalesce(Field<T> field, Field<?>... fields)
  {
    return new Coalesce(nullSafeDataType(field), nullSafe(Utils.combine(field, fields)));
  }
  
  @Support
  public static <T> Field<T> isnull(T value, T defaultValue)
  {
    return nvl(value, defaultValue);
  }
  
  @Support
  public static <T> Field<T> isnull(T value, Field<T> defaultValue)
  {
    return nvl(value, defaultValue);
  }
  
  @Support
  public static <T> Field<T> isnull(Field<T> value, T defaultValue)
  {
    return nvl(value, defaultValue);
  }
  
  @Support
  public static <T> Field<T> isnull(Field<T> value, Field<T> defaultValue)
  {
    return nvl(value, defaultValue);
  }
  
  @Support
  public static <T> Field<T> nvl(T value, T defaultValue)
  {
    return nvl(Utils.field(value), Utils.field(defaultValue));
  }
  
  @Support
  public static <T> Field<T> nvl(T value, Field<T> defaultValue)
  {
    return nvl(Utils.field(value), nullSafe(defaultValue));
  }
  
  @Support
  public static <T> Field<T> nvl(Field<T> value, T defaultValue)
  {
    return nvl(nullSafe(value), Utils.field(defaultValue));
  }
  
  @Support
  public static <T> Field<T> nvl(Field<T> value, Field<T> defaultValue)
  {
    return new Nvl(nullSafe(value), nullSafe(defaultValue));
  }
  
  @Support
  public static <Z> Field<Z> nvl2(Field<?> value, Z valueIfNotNull, Z valueIfNull)
  {
    return nvl2(nullSafe(value), Utils.field(valueIfNotNull), Utils.field(valueIfNull));
  }
  
  @Support
  public static <Z> Field<Z> nvl2(Field<?> value, Z valueIfNotNull, Field<Z> valueIfNull)
  {
    return nvl2(nullSafe(value), Utils.field(valueIfNotNull), nullSafe(valueIfNull));
  }
  
  @Support
  public static <Z> Field<Z> nvl2(Field<?> value, Field<Z> valueIfNotNull, Z valueIfNull)
  {
    return nvl2(nullSafe(value), nullSafe(valueIfNotNull), Utils.field(valueIfNull));
  }
  
  @Support
  public static <Z> Field<Z> nvl2(Field<?> value, Field<Z> valueIfNotNull, Field<Z> valueIfNull)
  {
    return new Nvl2(nullSafe(value), nullSafe(valueIfNotNull), nullSafe(valueIfNull));
  }
  
  @Support
  public static <T> Field<T> nullif(T value, T other)
  {
    return nullif(Utils.field(value), Utils.field(other));
  }
  
  @Support
  public static <T> Field<T> nullif(T value, Field<T> other)
  {
    return nullif(Utils.field(value), nullSafe(other));
  }
  
  @Support
  public static <T> Field<T> nullif(Field<T> value, T other)
  {
    return nullif(nullSafe(value), Utils.field(other));
  }
  
  @Support
  public static <T> Field<T> nullif(Field<T> value, Field<T> other)
  {
    return new NullIf(nullSafe(value), nullSafe(other));
  }
  
  @Support
  public static Field<String> upper(String value)
  {
    return upper(Utils.field(value));
  }
  
  @Support
  public static Field<String> upper(Field<String> field)
  {
    return new Upper(nullSafe(field));
  }
  
  @Support
  public static Field<String> lower(String value)
  {
    return lower(Utils.field(value, String.class));
  }
  
  @Support
  public static Field<String> lower(Field<String> field)
  {
    return new Lower(nullSafe(field));
  }
  
  @Support
  public static Field<String> trim(String value)
  {
    return trim(Utils.field(value, String.class));
  }
  
  @Support
  public static Field<String> trim(Field<String> field)
  {
    return new Trim(nullSafe(field));
  }
  
  @Support
  public static Field<String> rtrim(String value)
  {
    return rtrim(Utils.field(value));
  }
  
  @Support
  public static Field<String> rtrim(Field<String> field)
  {
    return new RTrim(nullSafe(field));
  }
  
  @Support
  public static Field<String> ltrim(String value)
  {
    return ltrim(Utils.field(value, String.class));
  }
  
  @Support
  public static Field<String> ltrim(Field<String> field)
  {
    return new LTrim(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> rpad(Field<String> field, int length)
  {
    return rpad(nullSafe(field), Utils.field(Integer.valueOf(length)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> rpad(Field<String> field, Field<? extends Number> length)
  {
    return new Rpad(nullSafe(field), nullSafe(length));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> rpad(Field<String> field, int length, char character)
  {
    return rpad(field, length, Character.toString(character));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> rpad(Field<String> field, int length, String character)
  {
    return rpad(nullSafe(field), Utils.field(Integer.valueOf(length)), Utils.field(character, String.class));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> rpad(Field<String> field, Field<? extends Number> length, Field<String> character)
  {
    return new Rpad(nullSafe(field), nullSafe(length), nullSafe(character));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> lpad(Field<String> field, int length)
  {
    return lpad(nullSafe(field), Utils.field(Integer.valueOf(length)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> lpad(Field<String> field, Field<? extends Number> length)
  {
    return new Lpad(nullSafe(field), nullSafe(length));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> lpad(Field<String> field, int length, char character)
  {
    return lpad(field, length, Character.toString(character));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> lpad(Field<String> field, int length, String character)
  {
    return lpad(nullSafe(field), Utils.field(Integer.valueOf(length)), Utils.field(character, String.class));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> lpad(Field<String> field, Field<? extends Number> length, Field<String> character)
  {
    return new Lpad(nullSafe(field), nullSafe(length), nullSafe(character));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> repeat(String field, int count)
  {
    return repeat(Utils.field(field, String.class), Utils.field(Integer.valueOf(count)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> repeat(String field, Field<? extends Number> count)
  {
    return repeat(Utils.field(field, String.class), nullSafe(count));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> repeat(Field<String> field, int count)
  {
    return repeat(nullSafe(field), Utils.field(Integer.valueOf(count)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> repeat(Field<String> field, Field<? extends Number> count)
  {
    return new Repeat(nullSafe(field), nullSafe(count));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> space(int value)
  {
    return space(val(Integer.valueOf(value)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> space(Field<Integer> value)
  {
    return new Space(nullSafe(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<String> reverse(String value)
  {
    return reverse(val(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<String> reverse(Field<String> field)
  {
    return new Reverse(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static String escape(String value, char escape)
  {
    String esc = "" + escape;
    return value.replace(esc, esc + esc).replace("%", esc + "%").replace("_", esc + "_");
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> escape(Field<String> field, char escape)
  {
    Field<String> replace = field;
    
    String esc = "" + escape;
    replace = replace(replace, inline(esc), inline(esc + esc));
    replace = replace(replace, inline("%"), inline(esc + "%"));
    replace = replace(replace, inline("_"), inline(esc + "_"));
    
    return replace;
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> replace(Field<String> field, String search)
  {
    return replace(nullSafe(field), Utils.field(search, String.class));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> replace(Field<String> field, Field<String> search)
  {
    return new Replace(new Field[] { nullSafe(field), nullSafe(search) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> replace(Field<String> field, String search, String replace)
  {
    return replace(nullSafe(field), Utils.field(search, String.class), Utils.field(replace, String.class));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<String> replace(Field<String> field, Field<String> search, Field<String> replace)
  {
    return new Replace(new Field[] { nullSafe(field), nullSafe(search), nullSafe(replace) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<Integer> position(String in, String search)
  {
    return position(Utils.field(in, String.class), Utils.field(search, String.class));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<Integer> position(String in, Field<String> search)
  {
    return position(Utils.field(in, String.class), nullSafe(search));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<Integer> position(Field<String> in, String search)
  {
    return position(nullSafe(in), Utils.field(search, String.class));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<Integer> position(Field<String> in, Field<String> search)
  {
    return new Position(nullSafe(search), nullSafe(in));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<Integer> ascii(String field)
  {
    return ascii(Utils.field(field, String.class));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<Integer> ascii(Field<String> field)
  {
    return new Ascii(nullSafe(field));
  }
  
  @Support
  public static Field<String> concat(Field<String> field, String value)
  {
    return concat(new Field[] { nullSafe(field), Utils.field(value, String.class) });
  }
  
  @Support
  public static Field<String> concat(String value, Field<String> field)
  {
    return concat(new Field[] { Utils.field(value, String.class), nullSafe(field) });
  }
  
  @Support
  public static Field<String> concat(String... values)
  {
    return concat((Field[])Utils.fields(values).toArray(new Field[0]));
  }
  
  @Support
  public static Field<String> concat(Field<?>... fields)
  {
    return new Concat(nullSafe(fields));
  }
  
  @Support
  public static Field<String> substring(Field<String> field, int startingPosition)
  {
    return substring(nullSafe(field), Utils.field(Integer.valueOf(startingPosition)));
  }
  
  @Support
  public static Field<String> substring(Field<String> field, Field<? extends Number> startingPosition)
  {
    return new Substring(new Field[] { nullSafe(field), nullSafe(startingPosition) });
  }
  
  @Support
  public static Field<String> substring(Field<String> field, int startingPosition, int length)
  {
    return substring(nullSafe(field), Utils.field(Integer.valueOf(startingPosition)), Utils.field(Integer.valueOf(length)));
  }
  
  @Support
  public static Field<String> substring(Field<String> field, Field<? extends Number> startingPosition, Field<? extends Number> length)
  {
    return new Substring(new Field[] { nullSafe(field), nullSafe(startingPosition), nullSafe(length) });
  }
  
  @Support
  public static Field<String> mid(Field<String> field, int startingPosition, int length)
  {
    return substring(nullSafe(field), Utils.field(Integer.valueOf(startingPosition)), Utils.field(Integer.valueOf(length)));
  }
  
  @Support
  public static Field<String> mid(Field<String> field, Field<? extends Number> startingPosition, Field<? extends Number> length)
  {
    return substring(nullSafe(field), nullSafe(startingPosition), nullSafe(length));
  }
  
  @Support
  public static Field<String> left(String field, int length)
  {
    return left(Utils.field(field), Utils.field(Integer.valueOf(length)));
  }
  
  @Support
  public static Field<String> left(String field, Field<? extends Number> length)
  {
    return left(Utils.field(field), nullSafe(length));
  }
  
  @Support
  public static Field<String> left(Field<String> field, int length)
  {
    return left(nullSafe(field), Utils.field(Integer.valueOf(length)));
  }
  
  @Support
  public static Field<String> left(Field<String> field, Field<? extends Number> length)
  {
    return new Left(field, length);
  }
  
  @Support
  public static Field<String> right(String field, int length)
  {
    return right(Utils.field(field), Utils.field(Integer.valueOf(length)));
  }
  
  @Support
  public static Field<String> right(String field, Field<? extends Number> length)
  {
    return right(Utils.field(field), nullSafe(length));
  }
  
  @Support
  public static Field<String> right(Field<String> field, int length)
  {
    return right(nullSafe(field), Utils.field(Integer.valueOf(length)));
  }
  
  @Support
  public static Field<String> right(Field<String> field, Field<? extends Number> length)
  {
    return new Right(field, length);
  }
  
  @Support
  public static Field<Integer> length(String value)
  {
    return length(Utils.field(value, String.class));
  }
  
  @Support
  public static Field<Integer> length(Field<String> field)
  {
    return charLength(field);
  }
  
  @Support
  public static Field<Integer> charLength(String value)
  {
    return charLength(Utils.field(value));
  }
  
  @Support
  public static Field<Integer> charLength(Field<String> field)
  {
    return new Function(Term.CHAR_LENGTH, SQLDataType.INTEGER, new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static Field<Integer> bitLength(String value)
  {
    return bitLength(Utils.field(value));
  }
  
  @Support
  public static Field<Integer> bitLength(Field<String> field)
  {
    return new Function(Term.BIT_LENGTH, SQLDataType.INTEGER, new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static Field<Integer> octetLength(String value)
  {
    return octetLength(Utils.field(value, String.class));
  }
  
  @Support
  public static Field<Integer> octetLength(Field<String> field)
  {
    return new Function(Term.OCTET_LENGTH, SQLDataType.INTEGER, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public static Field<String> md5(String string)
  {
    return md5(Utils.field(string));
  }
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
  public static Field<String> md5(Field<String> string)
  {
    return new MD5(nullSafe(string));
  }
  
  @Support
  public static Field<java.sql.Date> currentDate()
  {
    return new CurrentDate();
  }
  
  @Support
  public static Field<Time> currentTime()
  {
    return new CurrentTime();
  }
  
  @Support
  public static Field<Timestamp> currentTimestamp()
  {
    return new CurrentTimestamp();
  }
  
  @Support
  public static Field<Integer> dateDiff(java.sql.Date date1, java.sql.Date date2)
  {
    return dateDiff(Utils.field(date1), Utils.field(date2));
  }
  
  @Support
  public static Field<Integer> dateDiff(Field<java.sql.Date> date1, java.sql.Date date2)
  {
    return dateDiff(nullSafe(date1), Utils.field(date2));
  }
  
  @Support
  public static Field<java.sql.Date> dateAdd(java.sql.Date date, Number interval)
  {
    return dateAdd(Utils.field(date), Utils.field(interval));
  }
  
  @Support
  public static Field<java.sql.Date> dateAdd(Field<java.sql.Date> date, Field<? extends Number> interval)
  {
    return nullSafe(date).add(interval);
  }
  
  @Support
  public static Field<java.sql.Date> dateAdd(java.sql.Date date, Number interval, DatePart datePart)
  {
    return new DateAdd(Utils.field(date), Utils.field(interval), datePart);
  }
  
  @Support
  public static Field<java.sql.Date> dateAdd(java.sql.Date date, Field<? extends Number> interval, DatePart datePart)
  {
    return new DateAdd(Utils.field(date), nullSafe(interval), datePart);
  }
  
  @Support
  public static Field<java.sql.Date> dateAdd(Field<java.sql.Date> date, Number interval, DatePart datePart)
  {
    return new DateAdd(nullSafe(date), Utils.field(interval), datePart);
  }
  
  @Support
  public static Field<java.sql.Date> dateAdd(Field<java.sql.Date> date, Field<? extends Number> interval, DatePart datePart)
  {
    return new DateAdd(nullSafe(date), nullSafe(interval), datePart);
  }
  
  @Support
  public static Field<Integer> dateDiff(java.sql.Date date1, Field<java.sql.Date> date2)
  {
    return dateDiff(Utils.field(date1), nullSafe(date2));
  }
  
  @Support
  public static Field<Integer> dateDiff(Field<java.sql.Date> date1, Field<java.sql.Date> date2)
  {
    return new DateDiff(nullSafe(date1), nullSafe(date2));
  }
  
  @Support
  public static Field<Timestamp> timestampAdd(Timestamp timestamp, Number interval)
  {
    return timestampAdd(Utils.field(timestamp), Utils.field(interval));
  }
  
  @Support
  public static Field<Timestamp> timestampAdd(Field<Timestamp> timestamp, Field<? extends Number> interval)
  {
    return nullSafe(timestamp).add(interval);
  }
  
  @Support
  public static Field<Timestamp> timestampAdd(Timestamp date, Number interval, DatePart datePart)
  {
    return new DateAdd(Utils.field(date), Utils.field(interval), datePart);
  }
  
  @Support
  public static Field<Timestamp> timestampAdd(Timestamp date, Field<? extends Number> interval, DatePart datePart)
  {
    return new DateAdd(Utils.field(date), nullSafe(interval), datePart);
  }
  
  @Support
  public static Field<Timestamp> timestampAdd(Field<Timestamp> date, Number interval, DatePart datePart)
  {
    return new DateAdd(nullSafe(date), Utils.field(interval), datePart);
  }
  
  @Support
  public static Field<Timestamp> timestampAdd(Field<Timestamp> date, Field<? extends Number> interval, DatePart datePart)
  {
    return new DateAdd(nullSafe(date), nullSafe(interval), datePart);
  }
  
  @Support
  public static Field<DayToSecond> timestampDiff(Timestamp timestamp1, Timestamp timestamp2)
  {
    return timestampDiff(Utils.field(timestamp1), Utils.field(timestamp2));
  }
  
  @Support
  public static Field<DayToSecond> timestampDiff(Field<Timestamp> timestamp1, Timestamp timestamp2)
  {
    return timestampDiff(nullSafe(timestamp1), Utils.field(timestamp2));
  }
  
  @Support
  public static Field<DayToSecond> timestampDiff(Timestamp timestamp1, Field<Timestamp> timestamp2)
  {
    return timestampDiff(Utils.field(timestamp1), nullSafe(timestamp2));
  }
  
  @Support
  public static Field<DayToSecond> timestampDiff(Field<Timestamp> timestamp1, Field<Timestamp> timestamp2)
  {
    return new TimestampDiff(nullSafe(timestamp1), nullSafe(timestamp2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static Field<java.sql.Date> trunc(java.sql.Date date)
  {
    return trunc(date, DatePart.DAY);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static Field<java.sql.Date> trunc(java.sql.Date date, DatePart part)
  {
    return trunc(Utils.field(date), part);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static Field<Timestamp> trunc(Timestamp timestamp)
  {
    return trunc(timestamp, DatePart.DAY);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static Field<Timestamp> trunc(Timestamp timestamp, DatePart part)
  {
    return trunc(Utils.field(timestamp), part);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T extends java.util.Date> Field<T> trunc(Field<T> date)
  {
    return trunc(date, DatePart.DAY);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static <T extends java.util.Date> Field<T> trunc(Field<T> date, DatePart part)
  {
    return new TruncDate(date, part);
  }
  
  @Support
  public static Field<Integer> extract(java.util.Date value, DatePart datePart)
  {
    return extract(Utils.field(value), datePart);
  }
  
  @Support
  public static Field<Integer> extract(Field<? extends java.util.Date> field, DatePart datePart)
  {
    return new Extract(nullSafe(field), datePart);
  }
  
  @Support
  public static Field<Integer> year(java.util.Date value)
  {
    return extract(value, DatePart.YEAR);
  }
  
  @Support
  public static Field<Integer> year(Field<? extends java.util.Date> field)
  {
    return extract(field, DatePart.YEAR);
  }
  
  @Support
  public static Field<Integer> month(java.util.Date value)
  {
    return extract(value, DatePart.MONTH);
  }
  
  @Support
  public static Field<Integer> month(Field<? extends java.util.Date> field)
  {
    return extract(field, DatePart.MONTH);
  }
  
  @Support
  public static Field<Integer> day(java.util.Date value)
  {
    return extract(value, DatePart.DAY);
  }
  
  @Support
  public static Field<Integer> day(Field<? extends java.util.Date> field)
  {
    return extract(field, DatePart.DAY);
  }
  
  @Support
  public static Field<Integer> hour(java.util.Date value)
  {
    return extract(value, DatePart.HOUR);
  }
  
  @Support
  public static Field<Integer> hour(Field<? extends java.util.Date> field)
  {
    return extract(field, DatePart.HOUR);
  }
  
  @Support
  public static Field<Integer> minute(java.util.Date value)
  {
    return extract(value, DatePart.MINUTE);
  }
  
  @Support
  public static Field<Integer> minute(Field<? extends java.util.Date> field)
  {
    return extract(field, DatePart.MINUTE);
  }
  
  @Support
  public static Field<Integer> second(java.util.Date value)
  {
    return extract(value, DatePart.SECOND);
  }
  
  @Support
  public static Field<Integer> second(Field<? extends java.util.Date> field)
  {
    return extract(field, DatePart.SECOND);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<java.sql.Date> date(String value)
  {
    return Utils.field(Convert.convert(value, java.sql.Date.class), java.sql.Date.class);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<java.sql.Date> date(java.util.Date value)
  {
    return date(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<java.sql.Date> date(Field<? extends java.util.Date> field)
  {
    return new DateOrTime(field, SQLDataType.DATE);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Time> time(String value)
  {
    return Utils.field(Convert.convert(value, Time.class), Time.class);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Time> time(java.util.Date value)
  {
    return time(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Time> time(Field<? extends java.util.Date> field)
  {
    return new DateOrTime(field, SQLDataType.TIME);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Timestamp> timestamp(String value)
  {
    return Utils.field(Convert.convert(value, Timestamp.class), Timestamp.class);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Timestamp> timestamp(java.util.Date value)
  {
    return timestamp(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Timestamp> timestamp(Field<? extends java.util.Date> field)
  {
    return new DateOrTime(field, SQLDataType.TIMESTAMP);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public static GroupField rollup(Field<?>... fields)
  {
    return new Rollup(nullSafe(fields));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Integer> bitCount(Number value)
  {
    return bitCount(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<Integer> bitCount(Field<? extends Number> field)
  {
    return new BitCount(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNot(T value)
  {
    return bitNot(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNot(Field<T> field)
  {
    return new Neg(nullSafe(field), ExpressionOperator.BIT_NOT);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitAnd(T value1, T value2)
  {
    return bitAnd(Utils.field(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitAnd(T value1, Field<T> value2)
  {
    return bitAnd(Utils.field(value1), nullSafe(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitAnd(Field<T> value1, T value2)
  {
    return bitAnd(nullSafe(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitAnd(Field<T> field1, Field<T> field2)
  {
    return new Expression(ExpressionOperator.BIT_AND, nullSafe(field1), new Field[] { nullSafe(field2) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNand(T value1, T value2)
  {
    return bitNand(Utils.field(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNand(T value1, Field<T> value2)
  {
    return bitNand(Utils.field(value1), nullSafe(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNand(Field<T> value1, T value2)
  {
    return bitNand(nullSafe(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNand(Field<T> field1, Field<T> field2)
  {
    return new Expression(ExpressionOperator.BIT_NAND, nullSafe(field1), new Field[] { nullSafe(field2) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitOr(T value1, T value2)
  {
    return bitOr(Utils.field(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitOr(T value1, Field<T> value2)
  {
    return bitOr(Utils.field(value1), nullSafe(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitOr(Field<T> value1, T value2)
  {
    return bitOr(nullSafe(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitOr(Field<T> field1, Field<T> field2)
  {
    return new Expression(ExpressionOperator.BIT_OR, nullSafe(field1), new Field[] { nullSafe(field2) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNor(T value1, T value2)
  {
    return bitNor(Utils.field(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNor(T value1, Field<T> value2)
  {
    return bitNor(Utils.field(value1), nullSafe(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNor(Field<T> value1, T value2)
  {
    return bitNor(nullSafe(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitNor(Field<T> field1, Field<T> field2)
  {
    return new Expression(ExpressionOperator.BIT_NOR, nullSafe(field1), new Field[] { nullSafe(field2) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitXor(T value1, T value2)
  {
    return bitXor(Utils.field(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitXor(T value1, Field<T> value2)
  {
    return bitXor(Utils.field(value1), nullSafe(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitXor(Field<T> value1, T value2)
  {
    return bitXor(nullSafe(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitXor(Field<T> field1, Field<T> field2)
  {
    return new Expression(ExpressionOperator.BIT_XOR, nullSafe(field1), new Field[] { nullSafe(field2) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitXNor(T value1, T value2)
  {
    return bitXNor(Utils.field(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitXNor(T value1, Field<T> value2)
  {
    return bitXNor(Utils.field(value1), nullSafe(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitXNor(Field<T> value1, T value2)
  {
    return bitXNor(nullSafe(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> bitXNor(Field<T> field1, Field<T> field2)
  {
    return new Expression(ExpressionOperator.BIT_XNOR, nullSafe(field1), new Field[] { nullSafe(field2) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> shl(T value1, T value2)
  {
    return shl(Utils.field(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> shl(T value1, Field<T> value2)
  {
    return shl(Utils.field(value1), nullSafe(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> shl(Field<T> value1, T value2)
  {
    return shl(nullSafe(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> shl(Field<T> field1, Field<T> field2)
  {
    return new Expression(ExpressionOperator.SHL, nullSafe(field1), new Field[] { nullSafe(field2) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> shr(T value1, T value2)
  {
    return shr(Utils.field(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> shr(T value1, Field<T> value2)
  {
    return shr(Utils.field(value1), nullSafe(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> shr(Field<T> value1, T value2)
  {
    return shr(nullSafe(value1), Utils.field(value2));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static <T extends Number> Field<T> shr(Field<T> field1, Field<T> field2)
  {
    return new Expression(ExpressionOperator.SHR, nullSafe(field1), new Field[] { nullSafe(field2) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static Field<BigDecimal> rand()
  {
    return new Rand();
  }
  
  @Support
  public static <T> Field<T> greatest(T value, T... values)
  {
    return greatest(Utils.field(value), (Field[])Utils.fields(values).toArray(new Field[0]));
  }
  
  @Support
  public static <T> Field<T> greatest(Field<T> field, Field<?>... others)
  {
    return new Greatest(nullSafeDataType(field), nullSafe(Utils.combine(field, others)));
  }
  
  @Support
  public static <T> Field<T> least(T value, T... values)
  {
    return least(Utils.field(value), (Field[])Utils.fields(values).toArray(new Field[0]));
  }
  
  @Support
  public static <T> Field<T> least(Field<T> field, Field<?>... others)
  {
    return new Least(nullSafeDataType(field), nullSafe(Utils.combine(field, others)));
  }
  
  @Support
  public static Field<Integer> sign(Number value)
  {
    return sign(Utils.field(value));
  }
  
  @Support
  public static Field<Integer> sign(Field<? extends Number> field)
  {
    return new Sign(nullSafe(field));
  }
  
  @Support
  public static <T extends Number> Field<T> abs(T value)
  {
    return abs(Utils.field(value));
  }
  
  @Support
  public static <T extends Number> Field<T> abs(Field<T> field)
  {
    return function("abs", nullSafeDataType(field), new Field[] { nullSafe(field) });
  }
  
  @Support
  public static <T extends Number> Field<T> round(T value)
  {
    return round(Utils.field(value));
  }
  
  @Support
  public static <T extends Number> Field<T> round(Field<T> field)
  {
    return new Round(nullSafe(field));
  }
  
  @Support
  public static <T extends Number> Field<T> round(T value, int decimals)
  {
    return round(Utils.field(value), decimals);
  }
  
  @Support
  public static <T extends Number> Field<T> round(Field<T> field, int decimals)
  {
    return new Round(nullSafe(field), decimals);
  }
  
  @Support
  public static <T extends Number> Field<T> floor(T value)
  {
    return floor(Utils.field(value));
  }
  
  @Support
  public static <T extends Number> Field<T> floor(Field<T> field)
  {
    return new Floor(nullSafe(field));
  }
  
  @Support
  public static <T extends Number> Field<T> ceil(T value)
  {
    return ceil(Utils.field(value));
  }
  
  @Support
  public static <T extends Number> Field<T> ceil(Field<T> field)
  {
    return new Ceil(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <T extends Number> Field<T> trunc(T number)
  {
    return trunc(Utils.field(number), inline(Integer.valueOf(0)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <T extends Number> Field<T> trunc(T number, int decimals)
  {
    return trunc(Utils.field(number), inline(Integer.valueOf(decimals)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <T extends Number> Field<T> trunc(Field<T> number, int decimals)
  {
    return trunc(nullSafe(number), inline(Integer.valueOf(decimals)));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <T extends Number> Field<T> trunc(T number, Field<Integer> decimals)
  {
    return trunc(Utils.field(number), nullSafe(decimals));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static <T extends Number> Field<T> trunc(Field<T> number, Field<Integer> decimals)
  {
    return new Trunc(nullSafe(number), nullSafe(decimals));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> sqrt(Number value)
  {
    return sqrt(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> sqrt(Field<? extends Number> field)
  {
    return new Sqrt(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> exp(Number value)
  {
    return exp(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> exp(Field<? extends Number> field)
  {
    return function("exp", SQLDataType.NUMERIC, new Field[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> ln(Number value)
  {
    return ln(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> ln(Field<? extends Number> field)
  {
    return new Ln(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> log(Number value, int base)
  {
    return log(Utils.field(value), base);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> log(Field<? extends Number> field, int base)
  {
    return new Ln(nullSafe(field), Integer.valueOf(base));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> power(Number value, Number exponent)
  {
    return power(Utils.field(value), Utils.field(exponent));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> power(Field<? extends Number> field, Number exponent)
  {
    return power(nullSafe(field), Utils.field(exponent));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> power(Number value, Field<? extends Number> exponent)
  {
    return power(Utils.field(value), nullSafe(exponent));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> power(Field<? extends Number> field, Field<? extends Number> exponent)
  {
    return new Power(nullSafe(field), nullSafe(exponent));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> acos(Number value)
  {
    return acos(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> acos(Field<? extends Number> field)
  {
    return new Acos(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> asin(Number value)
  {
    return asin(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> asin(Field<? extends Number> field)
  {
    return new Asin(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> atan(Number value)
  {
    return atan(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> atan(Field<? extends Number> field)
  {
    return new Atan(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> atan2(Number x, Number y)
  {
    return atan2(Utils.field(x), Utils.field(y));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> atan2(Number x, Field<? extends Number> y)
  {
    return atan2(Utils.field(x), nullSafe(y));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> atan2(Field<? extends Number> x, Number y)
  {
    return atan2(nullSafe(x), Utils.field(y));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> atan2(Field<? extends Number> x, Field<? extends Number> y)
  {
    return new Function(Term.ATAN2, SQLDataType.NUMERIC, new QueryPart[] { nullSafe(x), nullSafe(y) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> cos(Number value)
  {
    return cos(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> cos(Field<? extends Number> field)
  {
    return function("cos", SQLDataType.NUMERIC, new Field[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> sin(Number value)
  {
    return sin(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> sin(Field<? extends Number> field)
  {
    return function("sin", SQLDataType.NUMERIC, new Field[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> tan(Number value)
  {
    return tan(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> tan(Field<? extends Number> field)
  {
    return function("tan", SQLDataType.NUMERIC, new Field[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> cot(Number value)
  {
    return cot(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> cot(Field<? extends Number> field)
  {
    return new Cot(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> sinh(Number value)
  {
    return sinh(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> sinh(Field<? extends Number> field)
  {
    return new Sinh(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> cosh(Number value)
  {
    return cosh(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> cosh(Field<? extends Number> field)
  {
    return new Cosh(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> tanh(Number value)
  {
    return tanh(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> tanh(Field<? extends Number> field)
  {
    return new Tanh(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> coth(Number value)
  {
    return coth(Utils.field(value));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<BigDecimal> coth(Field<? extends Number> field)
  {
    field = nullSafe(field);
    return exp(field.mul(Integer.valueOf(2))).add(Integer.valueOf(1)).div(exp(field.mul(Integer.valueOf(2))).sub(Integer.valueOf(1)));
  }
  
  @Support
  public static Field<BigDecimal> deg(Number value)
  {
    return deg(Utils.field(value));
  }
  
  @Support
  public static Field<BigDecimal> deg(Field<? extends Number> field)
  {
    return new Degrees(nullSafe(field));
  }
  
  @Support
  public static Field<BigDecimal> rad(Number value)
  {
    return rad(Utils.field(value));
  }
  
  @Support
  public static Field<BigDecimal> rad(Field<? extends Number> field)
  {
    return new Radians(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID})
  public static Field<Integer> level()
  {
    return field("level", Integer.class);
  }
  
  @Support({SQLDialect.CUBRID})
  public static Field<Boolean> connectByIsCycle()
  {
    return field("connect_by_iscycle", Boolean.class);
  }
  
  @Support({SQLDialect.CUBRID})
  public static Field<Boolean> connectByIsLeaf()
  {
    return field("connect_by_isleaf", Boolean.class);
  }
  
  @Support({SQLDialect.CUBRID})
  public static <T> Field<T> connectByRoot(Field<T> field)
  {
    return field("{connect_by_root} {0}", nullSafe(field).getDataType(), new QueryPart[] { field });
  }
  
  @Support({SQLDialect.CUBRID})
  public static Field<String> sysConnectByPath(Field<?> field, String separator)
  {
    return field("{sys_connect_by_path}({0}, {1})", String.class, new QueryPart[] { field, inline(separator) });
  }
  
  @Support({SQLDialect.CUBRID})
  public static <T> Field<T> prior(Field<T> field)
  {
    return new Prior(field);
  }
  
  @Support({SQLDialect.CUBRID})
  public static Field<Integer> rownum()
  {
    return field("rownum", Integer.class);
  }
  
  @Support
  public static AggregateFunction<Integer> count()
  {
    return count(field("*", Integer.class));
  }
  
  @Support
  public static AggregateFunction<Integer> count(Field<?> field)
  {
    return new Function("count", SQLDataType.INTEGER, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<Integer> count(Table<?> table)
  {
    return new Function("count", SQLDataType.INTEGER, new QueryPart[] { tableByName(new String[] { table.getName() }) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static AggregateFunction<Integer> countDistinct(Field<?> field)
  {
    return new Function("count", true, SQLDataType.INTEGER, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<Integer> countDistinct(Table<?> table)
  {
    return new Function("count", true, SQLDataType.INTEGER, new QueryPart[] { tableByName(new String[] { table.getName() }) });
  }
  
  @Support({SQLDialect.HSQLDB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static AggregateFunction<Integer> countDistinct(Field<?>... fields)
  {
    return new Function("count", true, SQLDataType.INTEGER, nullSafe(fields));
  }
  
  @Support
  public static <T> AggregateFunction<T> max(Field<T> field)
  {
    return new Function("max", nullSafeDataType(field), new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static <T> AggregateFunction<T> maxDistinct(Field<T> field)
  {
    return new Function("max", true, nullSafeDataType(field), new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static <T> AggregateFunction<T> min(Field<T> field)
  {
    return new Function("min", nullSafeDataType(field), new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static <T> AggregateFunction<T> minDistinct(Field<T> field)
  {
    return new Function("min", true, nullSafeDataType(field), new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static AggregateFunction<BigDecimal> sum(Field<? extends Number> field)
  {
    return new Function("sum", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static AggregateFunction<BigDecimal> sumDistinct(Field<? extends Number> field)
  {
    return new Function("sum", true, SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static AggregateFunction<BigDecimal> avg(Field<? extends Number> field)
  {
    return new Function("avg", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support
  public static AggregateFunction<BigDecimal> avgDistinct(Field<? extends Number> field)
  {
    return new Function("avg", true, SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public static AggregateFunction<BigDecimal> median(Field<? extends Number> field)
  {
    return new Function("median", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> stddevPop(Field<? extends Number> field)
  {
    return new Function(Term.STDDEV_POP, SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> stddevSamp(Field<? extends Number> field)
  {
    return new Function(Term.STDDEV_SAMP, SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> varPop(Field<? extends Number> field)
  {
    return new Function(Term.VAR_POP, SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> varSamp(Field<? extends Number> field)
  {
    return new Function(Term.VAR_SAMP, SQLDataType.NUMERIC, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrSlope(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_slope", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrIntercept(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_intercept", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrCount(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_count", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrR2(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_r2", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrAvgX(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_avgx", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrAvgY(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_avgy", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrSXX(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_sxx", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrSYY(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_syy", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.POSTGRES})
  public static AggregateFunction<BigDecimal> regrSXY(Field<? extends Number> y, Field<? extends Number> x)
  {
    return new Function("regr_sxy", SQLDataType.NUMERIC, new QueryPart[] { nullSafe(y), nullSafe(x) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static OrderedAggregateFunction<String> listAgg(Field<?> field)
  {
    return new Function(Term.LIST_AGG, SQLDataType.VARCHAR, new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static OrderedAggregateFunction<String> listAgg(Field<?> field, String separator)
  {
    return new Function(Term.LIST_AGG, SQLDataType.VARCHAR, new QueryPart[] { nullSafe(field), inline(separator) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static GroupConcatOrderByStep groupConcat(Field<?> field)
  {
    return new GroupConcat(nullSafe(field));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public static AggregateFunction<String> groupConcat(Field<?> field, String separator)
  {
    return new GroupConcat(nullSafe(field)).separator(separator);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static GroupConcatOrderByStep groupConcatDistinct(Field<?> field)
  {
    return new GroupConcat(nullSafe(field), true);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowSpecificationOrderByStep partitionBy(Field<?>... fields)
  {
    return new WindowSpecificationImpl().partitionBy(fields);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowSpecificationOrderByStep partitionBy(Collection<? extends Field<?>> fields)
  {
    return new WindowSpecificationImpl().partitionBy(fields);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowSpecificationOrderByStep orderBy(Field<?>... fields)
  {
    return new WindowSpecificationImpl().orderBy(fields);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowSpecificationRowsStep orderBy(SortField<?>... fields)
  {
    return new WindowSpecificationImpl().orderBy(fields);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowSpecificationRowsStep orderBy(Collection<? extends SortField<?>> fields)
  {
    return new WindowSpecificationImpl().orderBy(fields);
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationFinalStep rowsUnboundedPreceding()
  {
    return new WindowSpecificationImpl().rowsUnboundedPreceding();
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationFinalStep rowsPreceding(int number)
  {
    return new WindowSpecificationImpl().rowsPreceding(number);
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationFinalStep rowsCurrentRow()
  {
    return new WindowSpecificationImpl().rowsCurrentRow();
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationFinalStep rowsUnboundedFollowing()
  {
    return new WindowSpecificationImpl().rowsUnboundedFollowing();
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationFinalStep rowsFollowing(int number)
  {
    return new WindowSpecificationImpl().rowsFollowing(number);
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding()
  {
    return new WindowSpecificationImpl().rowsBetweenUnboundedPreceding();
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationRowsAndStep rowsBetweenPreceding(int number)
  {
    return new WindowSpecificationImpl().rowsBetweenPreceding(number);
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationRowsAndStep rowsBetweenCurrentRow()
  {
    return new WindowSpecificationImpl().rowsBetweenCurrentRow();
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing()
  {
    return new WindowSpecificationImpl().rowsBetweenUnboundedFollowing();
  }
  
  @Support({SQLDialect.POSTGRES})
  public static WindowSpecificationRowsAndStep rowsBetweenFollowing(int number)
  {
    return new WindowSpecificationImpl().rowsBetweenFollowing(number);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public static WindowOverStep<Integer> rowNumber()
  {
    return new Function(Term.ROW_NUMBER, SQLDataType.INTEGER, new QueryPart[0]);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowOverStep<Integer> rank()
  {
    return new Function("rank", SQLDataType.INTEGER, new QueryPart[0]);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowOverStep<Integer> denseRank()
  {
    return new Function("dense_rank", SQLDataType.INTEGER, new QueryPart[0]);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowOverStep<BigDecimal> percentRank()
  {
    return new Function("percent_rank", SQLDataType.NUMERIC, new QueryPart[0]);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowOverStep<BigDecimal> cumeDist()
  {
    return new Function("cume_dist", SQLDataType.NUMERIC, new QueryPart[0]);
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static WindowOverStep<Integer> ntile(int number)
  {
    return new Function("ntile", SQLDataType.INTEGER, new QueryPart[] { inline(Integer.valueOf(number)) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> firstValue(Field<T> field)
  {
    return new Function("first_value", nullSafeDataType(field), new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lastValue(Field<T> field)
  {
    return new Function("last_value", nullSafeDataType(field), new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field)
  {
    return new Function("lead", nullSafeDataType(field), new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset)
  {
    return new Function("lead", nullSafeDataType(field), new QueryPart[] { nullSafe(field), inline(Integer.valueOf(offset)) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset, T defaultValue)
  {
    return lead(nullSafe(field), offset, Utils.field(defaultValue));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field, int offset, Field<T> defaultValue)
  {
    return new Function("lead", nullSafeDataType(field), new QueryPart[] { nullSafe(field), inline(Integer.valueOf(offset)), nullSafe(defaultValue) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field)
  {
    return new Function("lag", nullSafeDataType(field), new QueryPart[] { nullSafe(field) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset)
  {
    return new Function("lag", nullSafeDataType(field), new QueryPart[] { nullSafe(field), inline(Integer.valueOf(offset)) });
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset, T defaultValue)
  {
    return lag(nullSafe(field), offset, Utils.field(defaultValue));
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field, int offset, Field<T> defaultValue)
  {
    return new Function("lag", nullSafeDataType(field), new QueryPart[] { nullSafe(field), inline(Integer.valueOf(offset)), nullSafe(defaultValue) });
  }
  
  @Support
  public static Param<Object> param(String name)
  {
    return param(name, Object.class);
  }
  
  @Support
  public static <T> Param<T> param(String name, Class<T> type)
  {
    return param(name, DefaultDataType.getDataType(null, type));
  }
  
  @Support
  public static <T> Param<T> param(String name, DataType<T> type)
  {
    return new Val(null, type, name);
  }
  
  @Support
  public static <T> Param<T> param(String name, T value)
  {
    return new Val(value, Utils.field(value).getDataType(), name);
  }
  
  @Support
  public static <T> Param<T> value(T value)
  {
    return val(value);
  }
  
  @Support
  public static <T> Param<T> value(Object value, Class<T> type)
  {
    return val(value, type);
  }
  
  @Support
  public static <T> Param<T> value(Object value, Field<T> field)
  {
    return val(value, field);
  }
  
  @Support
  public static <T> Param<T> value(Object value, DataType<T> type)
  {
    return val(value, type);
  }
  
  @Support
  public static <T> Param<T> inline(T value)
  {
    Param<T> val = val(value);
    val.setInline(true);
    return val;
  }
  
  @Support
  public static Param<String> inline(char character)
  {
    return inline("" + character);
  }
  
  @Support
  public static Param<String> inline(Character character)
  {
    return inline("" + character);
  }
  
  @Support
  public static Param<String> inline(CharSequence character)
  {
    return inline("" + character);
  }
  
  @Support
  public static <T> Param<T> inline(Object value, Class<T> type)
  {
    Param<T> val = val(value, type);
    val.setInline(true);
    return val;
  }
  
  @Support
  public static <T> Param<T> inline(Object value, Field<T> field)
  {
    Param<T> val = val(value, field);
    val.setInline(true);
    return val;
  }
  
  @Support
  public static <T> Param<T> inline(Object value, DataType<T> type)
  {
    Param<T> val = val(value, type);
    val.setInline(true);
    return val;
  }
  
  @Support
  public static <T> Param<T> val(T value)
  {
    Class<?> type = value == null ? Object.class : value.getClass();
    return val(value, getDataType(type));
  }
  
  @Support
  public static <T> Param<T> val(Object value, Class<T> type)
  {
    return val(value, getDataType(type));
  }
  
  @Support
  public static <T> Param<T> val(Object value, Field<T> field)
  {
    return val(value, nullSafeDataType(field));
  }
  
  @Support
  public static <T> Param<T> val(Object value, DataType<T> type)
  {
    if ((value instanceof UDTRecord)) {
      return new UDTConstant((UDTRecord)value);
    }
    T converted = type.convert(value);
    return new Val(converted, mostSpecific(converted, type));
  }
  
  private static <T> DataType<T> mostSpecific(T value, DataType<T> dataType)
  {
    if ((value != null) && (!(dataType instanceof ConvertedDataType)))
    {
      Class<T> valueType = value.getClass();
      Class<T> coercionType = dataType.getType();
      if ((valueType != coercionType) && (coercionType.isAssignableFrom(valueType))) {
        return DefaultDataType.getDataType(null, valueType, dataType);
      }
    }
    return dataType;
  }
  
  @Support
  public static <T1> Row1<T1> row(T1 t1)
  {
    return row(Utils.field(t1));
  }
  
  @Support
  public static <T1, T2> Row2<T1, T2> row(T1 t1, T2 t2)
  {
    return row(Utils.field(t1), Utils.field(t2));
  }
  
  @Support
  public static <T1, T2, T3> Row3<T1, T2, T3> row(T1 t1, T2 t2, T3 t3)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3));
  }
  
  @Support
  public static <T1, T2, T3, T4> Row4<T1, T2, T3, T4> row(T1 t1, T2 t2, T3 t3, T4 t4)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5> Row5<T1, T2, T3, T4, T5> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6> Row6<T1, T2, T3, T4, T5, T6> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7> Row7<T1, T2, T3, T4, T5, T6, T7> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8> Row8<T1, T2, T3, T4, T5, T6, T7, T8> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14), Utils.field(t15));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14), Utils.field(t15), Utils.field(t16));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14), Utils.field(t15), Utils.field(t16), Utils.field(t17));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14), Utils.field(t15), Utils.field(t16), Utils.field(t17), Utils.field(t18));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14), Utils.field(t15), Utils.field(t16), Utils.field(t17), Utils.field(t18), Utils.field(t19));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14), Utils.field(t15), Utils.field(t16), Utils.field(t17), Utils.field(t18), Utils.field(t19), Utils.field(t20));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14), Utils.field(t15), Utils.field(t16), Utils.field(t17), Utils.field(t18), Utils.field(t19), Utils.field(t20), Utils.field(t21));
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22)
  {
    return row(Utils.field(t1), Utils.field(t2), Utils.field(t3), Utils.field(t4), Utils.field(t5), Utils.field(t6), Utils.field(t7), Utils.field(t8), Utils.field(t9), Utils.field(t10), Utils.field(t11), Utils.field(t12), Utils.field(t13), Utils.field(t14), Utils.field(t15), Utils.field(t16), Utils.field(t17), Utils.field(t18), Utils.field(t19), Utils.field(t20), Utils.field(t21), Utils.field(t22));
  }
  
  @Support
  public static RowN row(Object... values)
  {
    return row((Field[])Utils.fields(values).toArray(new Field[0]));
  }
  
  @Support
  public static <T1> Row1<T1> row(Field<T1> t1)
  {
    return new RowImpl(new Field[] { t1 });
  }
  
  @Support
  public static <T1, T2> Row2<T1, T2> row(Field<T1> t1, Field<T2> t2)
  {
    return new RowImpl(new Field[] { t1, t2 });
  }
  
  @Support
  public static <T1, T2, T3> Row3<T1, T2, T3> row(Field<T1> t1, Field<T2> t2, Field<T3> t3)
  {
    return new RowImpl(new Field[] { t1, t2, t3 });
  }
  
  @Support
  public static <T1, T2, T3, T4> Row4<T1, T2, T3, T4> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5> Row5<T1, T2, T3, T4, T5> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6> Row6<T1, T2, T3, T4, T5, T6> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7> Row7<T1, T2, T3, T4, T5, T6, T7> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8> Row8<T1, T2, T3, T4, T5, T6, T7, T8> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row(Field<T1> t1, Field<T2> t2, Field<T3> t3, Field<T4> t4, Field<T5> t5, Field<T6> t6, Field<T7> t7, Field<T8> t8, Field<T9> t9, Field<T10> t10, Field<T11> t11, Field<T12> t12, Field<T13> t13, Field<T14> t14, Field<T15> t15, Field<T16> t16, Field<T17> t17, Field<T18> t18, Field<T19> t19, Field<T20> t20, Field<T21> t21, Field<T22> t22)
  {
    return new RowImpl(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  @Support
  public static RowN row(Field<?>... values)
  {
    return new RowImpl(values);
  }
  
  @Support
  public static RowN row(Collection<?> values)
  {
    Collection<Field<?>> fields = new ArrayList();
    for (Object o : values) {
      fields.add((o instanceof Field) ? (Field)o : val(o));
    }
    return new RowImpl(fields);
  }
  
  @Support
  public static Table<Record> values(RowN... rows)
  {
    Values.assertNotEmpty(rows);
    int size = rows[0].size();
    
    String[] columns = new String[size];
    for (int i = 0; i < size; i++) {
      columns[i] = ("c" + i);
    }
    return new Values(rows).as("v", columns);
  }
  
  @Support
  public static <T1> Table<Record1<T1>> values(Row1<T1>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1" });
  }
  
  @Support
  public static <T1, T2> Table<Record2<T1, T2>> values(Row2<T1, T2>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2" });
  }
  
  @Support
  public static <T1, T2, T3> Table<Record3<T1, T2, T3>> values(Row3<T1, T2, T3>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3" });
  }
  
  @Support
  public static <T1, T2, T3, T4> Table<Record4<T1, T2, T3, T4>> values(Row4<T1, T2, T3, T4>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5> Table<Record5<T1, T2, T3, T4, T5>> values(Row5<T1, T2, T3, T4, T5>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6> Table<Record6<T1, T2, T3, T4, T5, T6>> values(Row6<T1, T2, T3, T4, T5, T6>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7> Table<Record7<T1, T2, T3, T4, T5, T6, T7>> values(Row7<T1, T2, T3, T4, T5, T6, T7>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8> Table<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> values(Row8<T1, T2, T3, T4, T5, T6, T7, T8>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Table<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> values(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Table<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> values(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Table<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> values(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Table<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> values(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Table<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> values(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Table<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> values(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Table<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> values(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Table<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> values(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Table<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> values(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Table<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> values(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Table<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> values(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Table<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> values(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19", "c20" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Table<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> values(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19", "c20", "c21" });
  }
  
  @Support
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Table<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> values(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>... rows)
  {
    return new Values(rows).as("v", new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19", "c20", "c21", "c22" });
  }
  
  static Field<?> NULL()
  {
    return field("null");
  }
  
  protected static <T> Field<T> nullSafe(Field<T> field)
  {
    return field == null ? val(null) : field;
  }
  
  protected static Field<?>[] nullSafe(Field<?>... fields)
  {
    if (fields == null) {
      return new Field[0];
    }
    Field<?>[] result = new Field[fields.length];
    for (int i = 0; i < fields.length; i++) {
      result[i] = nullSafe(fields[i]);
    }
    return result;
  }
  
  protected static <T> DataType<T> nullSafeDataType(Field<T> field)
  {
    return field == null ? SQLDataType.OTHER : field.getDataType();
  }
  
  @Support
  public static Field<Integer> zero()
  {
    return inline(Integer.valueOf(0));
  }
  
  @Support
  public static Field<Integer> one()
  {
    return inline(Integer.valueOf(1));
  }
  
  @Support
  public static Field<Integer> two()
  {
    return inline(Integer.valueOf(2));
  }
  
  @Support
  public static Field<BigDecimal> pi()
  {
    return new Pi();
  }
  
  @Support
  public static Field<BigDecimal> e()
  {
    return new Euler();
  }
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public static Field<String> currentUser()
  {
    return new CurrentUser();
  }
  
  @Support
  public static <T> DataType<T> getDataType(Class<T> type)
  {
    return DefaultDataType.getDataType(SQLDialect.SQL99, type);
  }
  
  protected DSL()
  {
    throw new UnsupportedOperationException();
  }
  
  private DSL(Connection connection, SQLDialect dialect)
  {
    throw new UnsupportedOperationException();
  }
  
  private DSL(Connection connection, SQLDialect dialect, Settings settings)
  {
    throw new UnsupportedOperationException();
  }
  
  private DSL(DataSource datasource, SQLDialect dialect)
  {
    throw new UnsupportedOperationException();
  }
  
  private DSL(DataSource datasource, SQLDialect dialect, Settings settings)
  {
    throw new UnsupportedOperationException();
  }
  
  private DSL(SQLDialect dialect)
  {
    throw new UnsupportedOperationException();
  }
  
  private DSL(SQLDialect dialect, Settings settings)
  {
    throw new UnsupportedOperationException();
  }
}

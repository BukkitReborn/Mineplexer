package org.jooq.impl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.exception.DataTypeException;
import org.jooq.types.DayToSecond;
import org.jooq.types.Interval;
import org.jooq.types.YearToMonth;

class Expression<T>
  extends AbstractFunction<T>
{
  private static final long serialVersionUID = -5522799070693019771L;
  private final Field<T> lhs;
  private final QueryPartList<Field<?>> rhs;
  private final ExpressionOperator operator;
  
  Expression(ExpressionOperator operator, Field<T> lhs, Field<?>... rhs)
  {
    super(operator.toSQL(), lhs.getDataType(), Utils.combine(lhs, rhs));
    
    this.operator = operator;
    this.lhs = lhs;
    this.rhs = new QueryPartList(rhs);
  }
  
  public final Field<T> add(Field<?> value)
  {
    if (this.operator == ExpressionOperator.ADD)
    {
      this.rhs.add(value);
      return this;
    }
    return super.add(value);
  }
  
  public final Field<T> mul(Field<? extends Number> value)
  {
    if (this.operator == ExpressionOperator.MULTIPLY)
    {
      this.rhs.add(value);
      return this;
    }
    return super.mul(value);
  }
  
  final Field<T> getFunction0(Configuration configuration)
  {
    SQLDialect family = configuration.dialect().family();
    if (ExpressionOperator.BIT_AND == this.operator) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB }).contains(family)) {
        return DSL.function("bitand", getDataType(), getArguments());
      }
    }
    if ((ExpressionOperator.BIT_AND == this.operator) && (SQLDialect.FIREBIRD == family)) {
      return DSL.function("bin_and", getDataType(), getArguments());
    }
    if (ExpressionOperator.BIT_XOR == this.operator) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB }).contains(family)) {
        return DSL.function("bitxor", getDataType(), getArguments());
      }
    }
    if ((ExpressionOperator.BIT_XOR == this.operator) && (SQLDialect.FIREBIRD == family)) {
      return DSL.function("bin_xor", getDataType(), getArguments());
    }
    if (ExpressionOperator.BIT_OR == this.operator) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB }).contains(family)) {
        return DSL.function("bitor", getDataType(), getArguments());
      }
    }
    if ((ExpressionOperator.BIT_OR == this.operator) && (SQLDialect.FIREBIRD == family)) {
      return DSL.function("bin_or", getDataType(), getArguments());
    }
    if (ExpressionOperator.BIT_XOR == this.operator) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.SQLITE }).contains(family)) {
        return DSL.bitAnd(
          DSL.bitNot(DSL.bitAnd(lhsAsNumber(), rhsAsNumber())), 
          DSL.bitOr(lhsAsNumber(), rhsAsNumber()));
      }
    }
    if (ExpressionOperator.SHL == this.operator) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB }).contains(family)) {
        return this.lhs.mul(DSL.power(DSL.two(), rhsAsNumber()).cast(this.lhs));
      }
    }
    if (ExpressionOperator.SHR == this.operator) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.HSQLDB }).contains(family)) {
        return this.lhs.div(DSL.power(DSL.two(), rhsAsNumber()).cast(this.lhs));
      }
    }
    if ((ExpressionOperator.SHL == this.operator) && (SQLDialect.FIREBIRD == family)) {
      return DSL.function("bin_shl", getDataType(), getArguments());
    }
    if ((ExpressionOperator.SHR == this.operator) && (SQLDialect.FIREBIRD == family)) {
      return DSL.function("bin_shr", getDataType(), getArguments());
    }
    if (ExpressionOperator.BIT_NAND == this.operator) {
      return DSL.bitNot(DSL.bitAnd(lhsAsNumber(), rhsAsNumber()));
    }
    if (ExpressionOperator.BIT_NOR == this.operator) {
      return DSL.bitNot(DSL.bitOr(lhsAsNumber(), rhsAsNumber()));
    }
    if (ExpressionOperator.BIT_XNOR == this.operator) {
      return DSL.bitNot(DSL.bitXor(lhsAsNumber(), rhsAsNumber()));
    }
    if ((Arrays.asList(new ExpressionOperator[] { ExpressionOperator.ADD, ExpressionOperator.SUBTRACT }).contains(this.operator)) && 
      (this.lhs.getDataType().isDateTime()) && (
      (((Field)this.rhs.get(0)).getDataType().isNumeric()) || 
      (((Field)this.rhs.get(0)).getDataType().isInterval()))) {
      return new DateExpression();
    }
    return new DefaultExpression(null);
  }
  
  private final Field<Number> lhsAsNumber()
  {
    return this.lhs;
  }
  
  private final Field<Number> rhsAsNumber()
  {
    return (Field)this.rhs.get(0);
  }
  
  private final YearToMonth rhsAsYTM()
  {
    try
    {
      return (YearToMonth)((Param)this.rhs.get(0)).getValue();
    }
    catch (ClassCastException e)
    {
      throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + this.rhs.get(0));
    }
  }
  
  private final DayToSecond rhsAsDTS()
  {
    try
    {
      return (DayToSecond)((Param)this.rhs.get(0)).getValue();
    }
    catch (ClassCastException e)
    {
      throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + this.rhs.get(0));
    }
  }
  
  private final Interval rhsAsInterval()
  {
    try
    {
      return (Interval)((Param)this.rhs.get(0)).getValue();
    }
    catch (ClassCastException e)
    {
      throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + this.rhs.get(0));
    }
  }
  
  private class DateExpression
    extends AbstractFunction<T>
  {
    private static final long serialVersionUID = 3160679741902222262L;
    
    DateExpression()
    {
      super(Expression.this.lhs.getDataType(), new Field[0]);
    }
    
    final Field<T> getFunction0(Configuration configuration)
    {
      if (((Field)Expression.this.rhs.get(0)).getDataType().isInterval()) {
        return getIntervalExpression(configuration);
      }
      return getNumberExpression(configuration);
    }
    
    private final Field<T> getIntervalExpression(Configuration configuration)
    {
      SQLDialect dialect = configuration.dialect();
      int sign = Expression.this.operator == ExpressionOperator.ADD ? 1 : -1;
      switch (Expression.1.$SwitchMap$org$jooq$SQLDialect[dialect.family().ordinal()])
      {
      case 1: 
      case 2: 
      case 3: 
        Interval interval = Expression.this.rhsAsInterval();
        if (Expression.this.operator == ExpressionOperator.SUBTRACT) {
          interval = interval.neg();
        }
        if (((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class) {
          return DSL.field("{date_add}({0}, {interval} {1} {year_month})", getDataType(), new QueryPart[] { Expression.this.lhs, Utils.field(interval, String.class) });
        }
        if (dialect == SQLDialect.CUBRID) {
          return DSL.field("{date_add}({0}, {interval} {1} {day_millisecond})", getDataType(), new QueryPart[] { Expression.this.lhs, Utils.field(interval, String.class) });
        }
        return DSL.field("{date_add}({0}, {interval} {1} {day_microsecond})", getDataType(), new QueryPart[] { Expression.this.lhs, Utils.field(interval, String.class) });
      case 4: 
      case 5: 
        Field<T> result;
        Field<T> result;
        if (((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class) {
          result = DSL.field("{fn {timestampadd}({sql_tsi_month}, {0}, {1}) }", 
            getDataType(), new QueryPart[] { DSL.val(Integer.valueOf(sign * Expression.this.rhsAsYTM().intValue())), Expression.this.lhs });
        } else {
          result = DSL.field("{fn {timestampadd}({sql_tsi_second}, {0}, {1}) }", 
            getDataType(), new QueryPart[] { DSL.val(Long.valueOf(sign * Expression.this.rhsAsDTS().getTotalSeconds())), Expression.this.lhs });
        }
        return castNonTimestamps(configuration, result);
      case 6: 
        if (((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class) {
          return DSL.field("{dateadd}({month}, {0}, {1})", getDataType(), new QueryPart[] { DSL.val(Integer.valueOf(sign * Expression.this.rhsAsYTM().intValue())), Expression.this.lhs });
        }
        return DSL.field("{dateadd}({millisecond}, {0}, {1})", getDataType(), new QueryPart[] { DSL.val(Long.valueOf(sign * Expression.this.rhsAsDTS().getTotalMilli())), Expression.this.lhs });
      case 7: 
        if (((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class) {
          return DSL.field("{dateadd}('month', {0}, {1})", getDataType(), new QueryPart[] { DSL.val(Integer.valueOf(sign * Expression.this.rhsAsYTM().intValue())), Expression.this.lhs });
        }
        return DSL.field("{dateadd}('ms', {0}, {1})", getDataType(), new QueryPart[] { DSL.val(Long.valueOf(sign * Expression.this.rhsAsDTS().getTotalMilli())), Expression.this.lhs });
      case 8: 
        boolean ytm = ((Field)Expression.this.rhs.get(0)).getType() == YearToMonth.class;
        Field<?> interval = DSL.val(Double.valueOf(ytm ? Expression.this.rhsAsYTM().intValue() : Expression.this.rhsAsDTS().getTotalSeconds()));
        if (sign < 0) {
          interval = interval.neg();
        }
        interval = interval.concat(new Field[] { DSL.inline(ytm ? " months" : " seconds") });
        return DSL.field("{datetime}({0}, {1})", getDataType(), new QueryPart[] { Expression.this.lhs, interval });
      }
      return new Expression.DefaultExpression(Expression.this, null);
    }
    
    private final Field<T> castNonTimestamps(Configuration configuration, Field<T> result)
    {
      if (getDataType().getType() != Timestamp.class) {
        return DSL.field("{cast}({0} {as} " + getDataType().getCastTypeName(configuration) + ")", getDataType(), new QueryPart[] { result });
      }
      return result;
    }
    
    private final Field<T> getNumberExpression(Configuration configuration)
    {
      switch (Expression.1.$SwitchMap$org$jooq$SQLDialect[configuration.dialect().family().ordinal()])
      {
      case 6: 
        if (Expression.this.operator == ExpressionOperator.ADD) {
          return DSL.field("{dateadd}(day, {0}, {1})", getDataType(), new QueryPart[] { Expression.this.rhsAsNumber(), Expression.this.lhs });
        }
        return DSL.field("{dateadd}(day, {0}, {1})", getDataType(), new QueryPart[] { Expression.this.rhsAsNumber().neg(), Expression.this.lhs });
      case 5: 
        if (Expression.this.operator == ExpressionOperator.ADD) {
          return Expression.this.lhs.add(DSL.field("{0} day", new QueryPart[] { Expression.this.rhsAsNumber() }));
        }
        return Expression.this.lhs.sub(DSL.field("{0} day", new QueryPart[] { Expression.this.rhsAsNumber() }));
      case 4: 
        Field<T> result;
        Field<T> result;
        if (Expression.this.operator == ExpressionOperator.ADD) {
          result = DSL.field("{fn {timestampadd}({sql_tsi_day}, {0}, {1}) }", getDataType(), new QueryPart[] { Expression.this.rhsAsNumber(), Expression.this.lhs });
        } else {
          result = DSL.field("{fn {timestampadd}({sql_tsi_day}, {0}, {1}) }", getDataType(), new QueryPart[] { Expression.this.rhsAsNumber().neg(), Expression.this.lhs });
        }
        return castNonTimestamps(configuration, result);
      case 1: 
      case 2: 
      case 3: 
        if (Expression.this.operator == ExpressionOperator.ADD) {
          return DSL.field("{date_add}({0}, {interval} {1} {day})", getDataType(), new QueryPart[] { Expression.this.lhs, Expression.this.rhsAsNumber() });
        }
        return DSL.field("{date_add}({0}, {interval} {1} {day})", getDataType(), new QueryPart[] { Expression.this.lhs, Expression.this.rhsAsNumber().neg() });
      case 9: 
        if (Expression.this.operator == ExpressionOperator.ADD) {
          return new DateAdd(Expression.this.lhs, Expression.this.rhsAsNumber(), DatePart.DAY);
        }
        return new DateAdd(Expression.this.lhs, Expression.this.rhsAsNumber().neg(), DatePart.DAY);
      case 8: 
        if (Expression.this.operator == ExpressionOperator.ADD) {
          return DSL.field("{datetime}({0}, {1})", getDataType(), new QueryPart[] { Expression.this.lhs, Expression.this.rhsAsNumber().concat(new Field[] { DSL.inline(" day") }) });
        }
        return DSL.field("{datetime}({0}, {1})", getDataType(), new QueryPart[] { Expression.this.lhs, Expression.this.rhsAsNumber().neg().concat(new Field[] { DSL.inline(" day") }) });
      }
      return new Expression.DefaultExpression(Expression.this, null);
    }
  }
  
  private class DefaultExpression
    extends AbstractField<T>
  {
    private static final long serialVersionUID = -5105004317793995419L;
    
    private DefaultExpression()
    {
      super(Expression.this.lhs.getDataType());
    }
    
    public final void accept(Context<?> ctx)
    {
      String op = Expression.this.operator.toSQL();
      if ((Expression.this.operator == ExpressionOperator.BIT_XOR) && (ctx.configuration().dialect() == SQLDialect.POSTGRES)) {
        op = "#";
      }
      ctx.sql("(");
      ctx.visit(Expression.this.lhs);
      for (Field<?> field : Expression.this.rhs) {
        ctx.sql(" ").sql(op).sql(" ").visit(field);
      }
      ctx.sql(")");
    }
  }
}

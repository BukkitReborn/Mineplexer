package org.jooq.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jooq.AggregateFunction;
import org.jooq.BetweenAndStep;
import org.jooq.Binding;
import org.jooq.Case;
import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.QuantifiedSelect;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.tools.Convert;
import org.jooq.tools.StringUtils;

abstract class AbstractField<T>
  extends AbstractQueryPart
  implements Field<T>
{
  private static final long serialVersionUID = 2884811923648354905L;
  private static final Clause[] CLAUSES = { Clause.FIELD };
  private final String name;
  private final String comment;
  private final DataType<T> dataType;
  private final Binding<?, T> binding;
  
  AbstractField(String name, DataType<T> type)
  {
    this(name, type, null, null);
  }
  
  AbstractField(String name, DataType<T> type, String comment, Binding<?, T> binding)
  {
    this.name = name;
    this.comment = StringUtils.defaultString(comment);
    this.dataType = type;
    
    this.binding = ((type instanceof ConvertedDataType) ? ((ConvertedDataType)type)
    
      .binding() : binding != null ? binding : new DefaultBinding(new IdentityConverter(type
      .getType()), type.isLob()));
  }
  
  public abstract void accept(Context<?> paramContext);
  
  public Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public Field<T> as(String alias)
  {
    return new FieldAlias(this, alias);
  }
  
  public final Field<T> as(Field<?> otherField)
  {
    return as(otherField.getName());
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public final String getComment()
  {
    return this.comment;
  }
  
  public final Converter<?, T> getConverter()
  {
    return this.binding.converter();
  }
  
  public final Binding<?, T> getBinding()
  {
    return this.binding;
  }
  
  public final DataType<T> getDataType()
  {
    return this.dataType;
  }
  
  public final DataType<T> getDataType(Configuration configuration)
  {
    return this.dataType.getDataType(configuration);
  }
  
  public final Class<T> getType()
  {
    return this.dataType.getType();
  }
  
  public final <Z> Field<Z> cast(Field<Z> field)
  {
    return cast(field.getDataType());
  }
  
  public final <Z> Field<Z> cast(DataType<Z> type)
  {
    if (getDataType().equals(type)) {
      return this;
    }
    return new Cast(this, type);
  }
  
  public final <Z> Field<Z> cast(Class<Z> type)
  {
    if (getType() == type) {
      return this;
    }
    return cast(DefaultDataType.getDataType(null, type));
  }
  
  public final <Z> Field<Z> coerce(Field<Z> field)
  {
    return coerce(field.getDataType());
  }
  
  public final <Z> Field<Z> coerce(DataType<Z> type)
  {
    if (getDataType().equals(type)) {
      return this;
    }
    return new Coerce(this, type);
  }
  
  public final <Z> Field<Z> coerce(Class<Z> type)
  {
    return coerce(DefaultDataType.getDataType(null, type));
  }
  
  public final SortField<T> asc()
  {
    return sort(SortOrder.ASC);
  }
  
  public final SortField<T> desc()
  {
    return sort(SortOrder.DESC);
  }
  
  public final SortField<T> sort(SortOrder order)
  {
    return new SortFieldImpl(this, order);
  }
  
  public final SortField<Integer> sortAsc(Collection<T> sortList)
  {
    Map<T, Integer> map = new LinkedHashMap();
    
    int i = 0;
    for (T value : sortList) {
      map.put(value, Integer.valueOf(i++));
    }
    return sort(map);
  }
  
  public final SortField<Integer> sortAsc(T... sortList)
  {
    return sortAsc(Arrays.asList(sortList));
  }
  
  public final SortField<Integer> sortDesc(Collection<T> sortList)
  {
    Map<T, Integer> map = new LinkedHashMap();
    
    int i = 0;
    for (T value : sortList) {
      map.put(value, Integer.valueOf(i--));
    }
    return sort(map);
  }
  
  public final SortField<Integer> sortDesc(T... sortList)
  {
    return sortDesc(Arrays.asList(sortList));
  }
  
  public final <Z> SortField<Z> sort(Map<T, Z> sortMap)
  {
    CaseValueStep<T> decode = DSL.decode().value(this);
    CaseWhenStep<T, Z> result = null;
    for (Map.Entry<T, Z> entry : sortMap.entrySet()) {
      if (result == null) {
        result = decode.when(entry.getKey(), DSL.inline(entry.getValue()));
      } else {
        result.when(entry.getKey(), DSL.inline(entry.getValue()));
      }
    }
    if (result == null) {
      return null;
    }
    return result.asc();
  }
  
  public final Field<T> neg()
  {
    return new Neg(this, ExpressionOperator.SUBTRACT);
  }
  
  public final Field<T> add(Number value)
  {
    return add(Utils.field(value));
  }
  
  public Field<T> add(Field<?> value)
  {
    return new Expression(ExpressionOperator.ADD, this, new Field[] { DSL.nullSafe(value) });
  }
  
  public final Field<T> sub(Number value)
  {
    return sub(Utils.field(value));
  }
  
  public final Field<T> sub(Field<?> value)
  {
    return new Expression(ExpressionOperator.SUBTRACT, this, new Field[] { DSL.nullSafe(value) });
  }
  
  public final Field<T> mul(Number value)
  {
    return mul(Utils.field(value));
  }
  
  public Field<T> mul(Field<? extends Number> value)
  {
    return new Expression(ExpressionOperator.MULTIPLY, this, new Field[] { DSL.nullSafe(value) });
  }
  
  public final Field<T> div(Number value)
  {
    return div(Utils.field(value));
  }
  
  public final Field<T> div(Field<? extends Number> value)
  {
    return new Expression(ExpressionOperator.DIVIDE, this, new Field[] { DSL.nullSafe(value) });
  }
  
  public final Field<T> mod(Number value)
  {
    return mod(Utils.field(value));
  }
  
  public final Field<T> mod(Field<? extends Number> value)
  {
    return new Mod(this, DSL.nullSafe(value));
  }
  
  public final Field<T> plus(Number value)
  {
    return add(value);
  }
  
  public final Field<T> plus(Field<?> value)
  {
    return add(value);
  }
  
  public final Field<T> subtract(Number value)
  {
    return sub(value);
  }
  
  public final Field<T> subtract(Field<?> value)
  {
    return sub(value);
  }
  
  public final Field<T> minus(Number value)
  {
    return sub(value);
  }
  
  public final Field<T> minus(Field<?> value)
  {
    return sub(value);
  }
  
  public final Field<T> multiply(Number value)
  {
    return mul(value);
  }
  
  public final Field<T> multiply(Field<? extends Number> value)
  {
    return mul(value);
  }
  
  public final Field<T> divide(Number value)
  {
    return div(value);
  }
  
  public final Field<T> divide(Field<? extends Number> value)
  {
    return div(value);
  }
  
  public final Field<T> modulo(Number value)
  {
    return mod(value);
  }
  
  public final Field<T> modulo(Field<? extends Number> value)
  {
    return mod(value);
  }
  
  public final Field<T> bitNot()
  {
    return DSL.bitNot(this);
  }
  
  public final Field<T> bitAnd(T value)
  {
    return DSL.bitAnd(this, DSL.val(value, this));
  }
  
  public final Field<T> bitAnd(Field<T> value)
  {
    return DSL.bitAnd(this, value);
  }
  
  public final Field<T> bitNand(T value)
  {
    return DSL.bitNand(this, DSL.val(value, this));
  }
  
  public final Field<T> bitNand(Field<T> value)
  {
    return DSL.bitNand(this, value);
  }
  
  public final Field<T> bitOr(T value)
  {
    return DSL.bitOr(this, DSL.val(value, this));
  }
  
  public final Field<T> bitOr(Field<T> value)
  {
    return DSL.bitOr(this, value);
  }
  
  public final Field<T> bitNor(T value)
  {
    return DSL.bitNor(this, DSL.val(value, this));
  }
  
  public final Field<T> bitNor(Field<T> value)
  {
    return DSL.bitNor(this, value);
  }
  
  public final Field<T> bitXor(T value)
  {
    return DSL.bitXor(this, DSL.val(value, this));
  }
  
  public final Field<T> bitXor(Field<T> value)
  {
    return DSL.bitXor(this, value);
  }
  
  public final Field<T> bitXNor(T value)
  {
    return DSL.bitXNor(this, DSL.val(value, this));
  }
  
  public final Field<T> bitXNor(Field<T> value)
  {
    return DSL.bitXNor(this, value);
  }
  
  public final Field<T> shl(T value)
  {
    return DSL.shl(this, DSL.val(value, this));
  }
  
  public final Field<T> shl(Field<T> value)
  {
    return DSL.shl(this, value);
  }
  
  public final Field<T> shr(T value)
  {
    return DSL.shr(this, DSL.val(value, this));
  }
  
  public final Field<T> shr(Field<T> value)
  {
    return DSL.shr(this, value);
  }
  
  public final Condition isNull()
  {
    return new IsNull(this, true);
  }
  
  public final Condition isNotNull()
  {
    return new IsNull(this, false);
  }
  
  public final Condition isDistinctFrom(T value)
  {
    return isDistinctFrom(Utils.field(value, this));
  }
  
  public final Condition isDistinctFrom(Field<T> field)
  {
    return compare(Comparator.IS_DISTINCT_FROM, field);
  }
  
  public final Condition isNotDistinctFrom(T value)
  {
    return isNotDistinctFrom(Utils.field(value, this));
  }
  
  public final Condition isNotDistinctFrom(Field<T> field)
  {
    return compare(Comparator.IS_NOT_DISTINCT_FROM, field);
  }
  
  public final Condition isTrue()
  {
    Class<?> type = getType();
    if (type == String.class) {
      return in(Utils.inline(Convert.TRUE_VALUES.toArray(new String[Convert.TRUE_VALUES.size()])));
    }
    if (Number.class.isAssignableFrom(type)) {
      return equal(DSL.inline((Number)getDataType().convert(Integer.valueOf(1))));
    }
    if (Boolean.class.isAssignableFrom(type)) {
      return equal(DSL.inline(Boolean.valueOf(true)));
    }
    return cast(String.class).in(Convert.TRUE_VALUES);
  }
  
  public final Condition isFalse()
  {
    Class<?> type = getType();
    if (type == String.class) {
      return in(Utils.inline(Convert.FALSE_VALUES.toArray(new String[Convert.FALSE_VALUES.size()])));
    }
    if (Number.class.isAssignableFrom(type)) {
      return equal(DSL.inline((Number)getDataType().convert(Integer.valueOf(0))));
    }
    if (Boolean.class.isAssignableFrom(type)) {
      return equal(DSL.inline(Boolean.valueOf(false)));
    }
    return cast(String.class).in(Utils.inline(Convert.FALSE_VALUES.toArray(new String[Convert.FALSE_VALUES.size()])));
  }
  
  public final Condition like(String value)
  {
    return like(Utils.field(value, String.class));
  }
  
  public final Condition like(String value, char escape)
  {
    return like(Utils.field(value, String.class), escape);
  }
  
  public final Condition like(Field<String> field)
  {
    return new CompareCondition(this, DSL.nullSafe(field), Comparator.LIKE);
  }
  
  public final Condition like(Field<String> field, char escape)
  {
    return new CompareCondition(this, DSL.nullSafe(field), Comparator.LIKE, Character.valueOf(escape));
  }
  
  public final Condition likeIgnoreCase(String value)
  {
    return likeIgnoreCase(Utils.field(value, String.class));
  }
  
  public final Condition likeIgnoreCase(String value, char escape)
  {
    return likeIgnoreCase(Utils.field(value, String.class), escape);
  }
  
  public final Condition likeIgnoreCase(Field<String> field)
  {
    return new CompareCondition(this, DSL.nullSafe(field), Comparator.LIKE_IGNORE_CASE);
  }
  
  public final Condition likeIgnoreCase(Field<String> field, char escape)
  {
    return new CompareCondition(this, DSL.nullSafe(field), Comparator.LIKE_IGNORE_CASE, Character.valueOf(escape));
  }
  
  public final Condition likeRegex(String pattern)
  {
    return likeRegex(Utils.field(pattern, String.class));
  }
  
  public final Condition likeRegex(Field<String> pattern)
  {
    return new RegexpLike(this, DSL.nullSafe(pattern));
  }
  
  public final Condition notLike(String value)
  {
    return notLike(Utils.field(value, String.class));
  }
  
  public final Condition notLike(String value, char escape)
  {
    return notLike(Utils.field(value, String.class), escape);
  }
  
  public final Condition notLike(Field<String> field)
  {
    return new CompareCondition(this, DSL.nullSafe(field), Comparator.NOT_LIKE);
  }
  
  public final Condition notLike(Field<String> field, char escape)
  {
    return new CompareCondition(this, DSL.nullSafe(field), Comparator.NOT_LIKE, Character.valueOf(escape));
  }
  
  public final Condition notLikeIgnoreCase(String value)
  {
    return notLikeIgnoreCase(Utils.field(value, String.class));
  }
  
  public final Condition notLikeIgnoreCase(String value, char escape)
  {
    return notLikeIgnoreCase(Utils.field(value, String.class), escape);
  }
  
  public final Condition notLikeIgnoreCase(Field<String> field)
  {
    return new CompareCondition(this, DSL.nullSafe(field), Comparator.NOT_LIKE_IGNORE_CASE);
  }
  
  public final Condition notLikeIgnoreCase(Field<String> field, char escape)
  {
    return new CompareCondition(this, DSL.nullSafe(field), Comparator.NOT_LIKE_IGNORE_CASE, Character.valueOf(escape));
  }
  
  public final Condition notLikeRegex(String pattern)
  {
    return likeRegex(pattern).not();
  }
  
  public final Condition notLikeRegex(Field<String> pattern)
  {
    return likeRegex(pattern).not();
  }
  
  public final Condition contains(T value)
  {
    return new Contains(this, value);
  }
  
  public final Condition contains(Field<T> value)
  {
    return new Contains(this, value);
  }
  
  public final Condition startsWith(T value)
  {
    Field<String> concat = DSL.concat(new Field[] { Utils.escapeForLike(value), DSL.inline("%") });
    return like(concat, '!');
  }
  
  public final Condition startsWith(Field<T> value)
  {
    Field<String> concat = DSL.concat(new Field[] { Utils.escapeForLike(value), DSL.inline("%") });
    return like(concat, '!');
  }
  
  public final Condition endsWith(T value)
  {
    Field<String> concat = DSL.concat(new Field[] { DSL.inline("%"), Utils.escapeForLike(value) });
    return like(concat, '!');
  }
  
  public final Condition endsWith(Field<T> value)
  {
    Field<String> concat = DSL.concat(new Field[] { DSL.inline("%"), Utils.escapeForLike(value) });
    return like(concat, '!');
  }
  
  private final boolean isAccidentalSelect(T[] values)
  {
    return (values != null) && (values.length == 1) && ((values[0] instanceof Select));
  }
  
  private final boolean isAccidentalCollection(T[] values)
  {
    return (values != null) && (values.length == 1) && ((values[0] instanceof Collection));
  }
  
  public final Condition in(T... values)
  {
    if (isAccidentalSelect(values)) {
      return in((Select)values[0]);
    }
    if (isAccidentalCollection(values)) {
      return in((Collection)values[0]);
    }
    return in((Field[])Utils.fields(values, this).toArray(new Field[0]));
  }
  
  public final Condition in(Field<?>... values)
  {
    return new InCondition(this, DSL.nullSafe(values), Comparator.IN);
  }
  
  public final Condition in(Collection<?> values)
  {
    List<Field<?>> fields = new ArrayList();
    for (Object value : values) {
      fields.add(Utils.field(value, this));
    }
    return in((Field[])fields.toArray(new Field[0]));
  }
  
  public final Condition in(Result<? extends Record1<T>> result)
  {
    return in(result.getValues(0, getType()));
  }
  
  public final Condition in(Select<? extends Record1<T>> query)
  {
    return compare(Comparator.IN, query);
  }
  
  public final Condition notIn(T... values)
  {
    if (isAccidentalSelect(values)) {
      return notIn((Select)values[0]);
    }
    if (isAccidentalCollection(values)) {
      return notIn((Collection)values[0]);
    }
    return notIn((Field[])Utils.fields(values, this).toArray(new Field[0]));
  }
  
  public final Condition notIn(Field<?>... values)
  {
    return new InCondition(this, DSL.nullSafe(values), Comparator.NOT_IN);
  }
  
  public final Condition notIn(Collection<?> values)
  {
    List<Field<?>> fields = new ArrayList();
    for (Object value : values) {
      fields.add(Utils.field(value, this));
    }
    return notIn((Field[])fields.toArray(new Field[0]));
  }
  
  public final Condition notIn(Result<? extends Record1<T>> result)
  {
    return notIn(result.getValues(0, getType()));
  }
  
  public final Condition notIn(Select<? extends Record1<T>> query)
  {
    return compare(Comparator.NOT_IN, query);
  }
  
  public final Condition between(T minValue, T maxValue)
  {
    return between(Utils.field(minValue, this), Utils.field(maxValue, this));
  }
  
  public final Condition between(Field<T> minValue, Field<T> maxValue)
  {
    return between(DSL.nullSafe(minValue)).and(DSL.nullSafe(maxValue));
  }
  
  public final Condition betweenSymmetric(T minValue, T maxValue)
  {
    return betweenSymmetric(Utils.field(minValue, this), Utils.field(maxValue, this));
  }
  
  public final Condition betweenSymmetric(Field<T> minValue, Field<T> maxValue)
  {
    return betweenSymmetric(DSL.nullSafe(minValue)).and(DSL.nullSafe(maxValue));
  }
  
  public final Condition notBetween(T minValue, T maxValue)
  {
    return notBetween(Utils.field(minValue, this), Utils.field(maxValue, this));
  }
  
  public final Condition notBetween(Field<T> minValue, Field<T> maxValue)
  {
    return notBetween(DSL.nullSafe(minValue)).and(DSL.nullSafe(maxValue));
  }
  
  public final Condition notBetweenSymmetric(T minValue, T maxValue)
  {
    return notBetweenSymmetric(Utils.field(minValue, this), Utils.field(maxValue, this));
  }
  
  public final Condition notBetweenSymmetric(Field<T> minValue, Field<T> maxValue)
  {
    return notBetweenSymmetric(DSL.nullSafe(minValue)).and(DSL.nullSafe(maxValue));
  }
  
  public final BetweenAndStep<T> between(T minValue)
  {
    return between(Utils.field(minValue, this));
  }
  
  public final BetweenAndStep<T> between(Field<T> minValue)
  {
    return new BetweenCondition(this, DSL.nullSafe(minValue), false, false);
  }
  
  public final BetweenAndStep<T> betweenSymmetric(T minValue)
  {
    return betweenSymmetric(Utils.field(minValue, this));
  }
  
  public final BetweenAndStep<T> betweenSymmetric(Field<T> minValue)
  {
    return new BetweenCondition(this, DSL.nullSafe(minValue), false, true);
  }
  
  public final BetweenAndStep<T> notBetween(T minValue)
  {
    return notBetween(Utils.field(minValue, this));
  }
  
  public final BetweenAndStep<T> notBetween(Field<T> minValue)
  {
    return new BetweenCondition(this, DSL.nullSafe(minValue), true, false);
  }
  
  public final BetweenAndStep<T> notBetweenSymmetric(T minValue)
  {
    return notBetweenSymmetric(Utils.field(minValue, this));
  }
  
  public final BetweenAndStep<T> notBetweenSymmetric(Field<T> minValue)
  {
    return new BetweenCondition(this, DSL.nullSafe(minValue), true, true);
  }
  
  public final Condition eq(T value)
  {
    return equal(value);
  }
  
  public final Condition eq(Field<T> field)
  {
    return equal(field);
  }
  
  public final Condition eq(Select<? extends Record1<T>> query)
  {
    return equal(query);
  }
  
  public final Condition eq(QuantifiedSelect<? extends Record1<T>> query)
  {
    return equal(query);
  }
  
  public final Condition ne(T value)
  {
    return notEqual(value);
  }
  
  public final Condition ne(Field<T> field)
  {
    return notEqual(field);
  }
  
  public final Condition ne(Select<? extends Record1<T>> query)
  {
    return notEqual(query);
  }
  
  public final Condition ne(QuantifiedSelect<? extends Record1<T>> query)
  {
    return notEqual(query);
  }
  
  public final Condition lt(T value)
  {
    return lessThan(value);
  }
  
  public final Condition lt(Field<T> field)
  {
    return lessThan(field);
  }
  
  public final Condition lt(Select<? extends Record1<T>> query)
  {
    return lessThan(query);
  }
  
  public final Condition lt(QuantifiedSelect<? extends Record1<T>> query)
  {
    return lessThan(query);
  }
  
  public final Condition le(T value)
  {
    return lessOrEqual(value);
  }
  
  public final Condition le(Field<T> field)
  {
    return lessOrEqual(field);
  }
  
  public final Condition le(Select<? extends Record1<T>> query)
  {
    return lessOrEqual(query);
  }
  
  public final Condition le(QuantifiedSelect<? extends Record1<T>> query)
  {
    return lessOrEqual(query);
  }
  
  public final Condition gt(T value)
  {
    return greaterThan(value);
  }
  
  public final Condition gt(Field<T> field)
  {
    return greaterThan(field);
  }
  
  public final Condition gt(Select<? extends Record1<T>> query)
  {
    return greaterThan(query);
  }
  
  public final Condition gt(QuantifiedSelect<? extends Record1<T>> query)
  {
    return greaterThan(query);
  }
  
  public final Condition ge(T value)
  {
    return greaterOrEqual(value);
  }
  
  public final Condition ge(Field<T> field)
  {
    return greaterOrEqual(field);
  }
  
  public final Condition ge(Select<? extends Record1<T>> query)
  {
    return greaterOrEqual(query);
  }
  
  public final Condition ge(QuantifiedSelect<? extends Record1<T>> query)
  {
    return greaterOrEqual(query);
  }
  
  public final Condition equal(T value)
  {
    return equal(Utils.field(value, this));
  }
  
  public final Condition equal(Field<T> field)
  {
    return compare(Comparator.EQUALS, DSL.nullSafe(field));
  }
  
  public final Condition equalIgnoreCase(String value)
  {
    return equalIgnoreCase(Utils.field(value, String.class));
  }
  
  public final Condition equalIgnoreCase(Field<String> value)
  {
    return DSL.lower(cast(String.class)).equal(DSL.lower(value));
  }
  
  public final Condition equal(Select<? extends Record1<T>> query)
  {
    return compare(Comparator.EQUALS, query);
  }
  
  public final Condition equal(QuantifiedSelect<? extends Record1<T>> query)
  {
    return compare(Comparator.EQUALS, query);
  }
  
  public final Condition notEqual(T value)
  {
    return notEqual(Utils.field(value, this));
  }
  
  public final Condition notEqual(Field<T> field)
  {
    return compare(Comparator.NOT_EQUALS, DSL.nullSafe(field));
  }
  
  public final Condition notEqualIgnoreCase(String value)
  {
    return notEqualIgnoreCase(Utils.field(value, String.class));
  }
  
  public final Condition notEqualIgnoreCase(Field<String> value)
  {
    return DSL.lower(cast(String.class)).notEqual(DSL.lower(value));
  }
  
  public final Condition notEqual(Select<? extends Record1<T>> query)
  {
    return compare(Comparator.NOT_EQUALS, query);
  }
  
  public final Condition notEqual(QuantifiedSelect<? extends Record1<T>> query)
  {
    return compare(Comparator.NOT_EQUALS, query);
  }
  
  public final Condition lessThan(T value)
  {
    return lessThan(Utils.field(value, this));
  }
  
  public final Condition lessThan(Field<T> field)
  {
    return compare(Comparator.LESS, DSL.nullSafe(field));
  }
  
  public final Condition lessThan(Select<? extends Record1<T>> query)
  {
    return compare(Comparator.LESS, query);
  }
  
  public final Condition lessThan(QuantifiedSelect<? extends Record1<T>> query)
  {
    return compare(Comparator.LESS, query);
  }
  
  public final Condition lessOrEqual(T value)
  {
    return lessOrEqual(Utils.field(value, this));
  }
  
  public final Condition lessOrEqual(Field<T> field)
  {
    return compare(Comparator.LESS_OR_EQUAL, DSL.nullSafe(field));
  }
  
  public final Condition lessOrEqual(Select<? extends Record1<T>> query)
  {
    return compare(Comparator.LESS_OR_EQUAL, query);
  }
  
  public final Condition lessOrEqual(QuantifiedSelect<? extends Record1<T>> query)
  {
    return compare(Comparator.LESS_OR_EQUAL, query);
  }
  
  public final Condition greaterThan(T value)
  {
    return greaterThan(Utils.field(value, this));
  }
  
  public final Condition greaterThan(Field<T> field)
  {
    return compare(Comparator.GREATER, DSL.nullSafe(field));
  }
  
  public final Condition greaterThan(Select<? extends Record1<T>> query)
  {
    return compare(Comparator.GREATER, query);
  }
  
  public final Condition greaterThan(QuantifiedSelect<? extends Record1<T>> query)
  {
    return compare(Comparator.GREATER, query);
  }
  
  public final Condition greaterOrEqual(T value)
  {
    return greaterOrEqual(Utils.field(value, this));
  }
  
  public final Condition greaterOrEqual(Field<T> field)
  {
    return compare(Comparator.GREATER_OR_EQUAL, DSL.nullSafe(field));
  }
  
  public final Condition greaterOrEqual(Select<? extends Record1<T>> query)
  {
    return compare(Comparator.GREATER_OR_EQUAL, query);
  }
  
  public final Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T>> query)
  {
    return compare(Comparator.GREATER_OR_EQUAL, query);
  }
  
  public final Condition compare(Comparator comparator, T value)
  {
    return compare(comparator, Utils.field(value, this));
  }
  
  public final Condition compare(Comparator comparator, Field<T> field)
  {
    switch (comparator)
    {
    case IS_DISTINCT_FROM: 
    case IS_NOT_DISTINCT_FROM: 
      return new IsDistinctFrom(this, DSL.nullSafe(field), comparator);
    }
    return new CompareCondition(this, DSL.nullSafe(field), comparator);
  }
  
  public final Condition compare(Comparator comparator, Select<? extends Record1<T>> query)
  {
    return compare(comparator, new ScalarSubquery(query, getDataType()));
  }
  
  public final Condition compare(Comparator comparator, QuantifiedSelect<? extends Record1<T>> query)
  {
    return new QuantifiedComparisonCondition(query, this, comparator);
  }
  
  private final <Z extends Number> Field<Z> numeric()
  {
    if (getDataType().isNumeric()) {
      return this;
    }
    return cast(BigDecimal.class);
  }
  
  private final Field<String> varchar()
  {
    if (getDataType().isString()) {
      return this;
    }
    return cast(String.class);
  }
  
  private final <Z extends Date> Field<Z> date()
  {
    if (getDataType().isTemporal()) {
      return this;
    }
    return cast(Timestamp.class);
  }
  
  @Deprecated
  public final Field<Integer> sign()
  {
    return DSL.sign(numeric());
  }
  
  @Deprecated
  public final Field<T> abs()
  {
    return DSL.abs(numeric());
  }
  
  @Deprecated
  public final Field<T> round()
  {
    return DSL.round(numeric());
  }
  
  @Deprecated
  public final Field<T> round(int decimals)
  {
    return DSL.round(numeric(), decimals);
  }
  
  @Deprecated
  public final Field<T> floor()
  {
    return DSL.floor(numeric());
  }
  
  @Deprecated
  public final Field<T> ceil()
  {
    return DSL.ceil(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> sqrt()
  {
    return DSL.sqrt(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> exp()
  {
    return DSL.exp(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> ln()
  {
    return DSL.ln(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> log(int base)
  {
    return DSL.log(numeric(), base);
  }
  
  public final Field<BigDecimal> pow(Number exponent)
  {
    return DSL.power(numeric(), exponent);
  }
  
  @Deprecated
  public final Field<BigDecimal> power(Number exponent)
  {
    return pow(exponent);
  }
  
  @Deprecated
  public final Field<BigDecimal> acos()
  {
    return DSL.acos(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> asin()
  {
    return DSL.asin(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> atan()
  {
    return DSL.atan(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> atan2(Number y)
  {
    return DSL.atan2(numeric(), y);
  }
  
  @Deprecated
  public final Field<BigDecimal> atan2(Field<? extends Number> y)
  {
    return DSL.atan2(numeric(), y);
  }
  
  @Deprecated
  public final Field<BigDecimal> cos()
  {
    return DSL.cos(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> sin()
  {
    return DSL.sin(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> tan()
  {
    return DSL.tan(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> cot()
  {
    return DSL.cot(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> sinh()
  {
    return DSL.sinh(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> cosh()
  {
    return DSL.cosh(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> tanh()
  {
    return DSL.tanh(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> coth()
  {
    return DSL.coth(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> deg()
  {
    return DSL.deg(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> rad()
  {
    return DSL.rad(numeric());
  }
  
  @Deprecated
  public final Field<Integer> count()
  {
    return DSL.count(this);
  }
  
  @Deprecated
  public final Field<Integer> countDistinct()
  {
    return DSL.countDistinct(this);
  }
  
  @Deprecated
  public final Field<T> max()
  {
    return DSL.max(this);
  }
  
  @Deprecated
  public final Field<T> min()
  {
    return DSL.min(this);
  }
  
  @Deprecated
  public final Field<BigDecimal> sum()
  {
    return DSL.sum(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> avg()
  {
    return DSL.avg(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> median()
  {
    return DSL.median(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> stddevPop()
  {
    return DSL.stddevPop(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> stddevSamp()
  {
    return DSL.stddevSamp(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> varPop()
  {
    return DSL.varPop(numeric());
  }
  
  @Deprecated
  public final Field<BigDecimal> varSamp()
  {
    return DSL.varSamp(numeric());
  }
  
  @Deprecated
  public final WindowPartitionByStep<Integer> countOver()
  {
    return DSL.count(this).over();
  }
  
  @Deprecated
  public final WindowPartitionByStep<T> maxOver()
  {
    return DSL.max(this).over();
  }
  
  @Deprecated
  public final WindowPartitionByStep<T> minOver()
  {
    return DSL.min(this).over();
  }
  
  @Deprecated
  public final WindowPartitionByStep<BigDecimal> sumOver()
  {
    return DSL.sum(numeric()).over();
  }
  
  @Deprecated
  public final WindowPartitionByStep<BigDecimal> avgOver()
  {
    return DSL.avg(numeric()).over();
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> firstValue()
  {
    return DSL.firstValue(this);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lastValue()
  {
    return DSL.lastValue(this);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lead()
  {
    return DSL.lead(this);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lead(int offset)
  {
    return DSL.lead(this, offset);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lead(int offset, T defaultValue)
  {
    return DSL.lead(this, offset, defaultValue);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lead(int offset, Field<T> defaultValue)
  {
    return DSL.lead(this, offset, defaultValue);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lag()
  {
    return DSL.lag(this);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lag(int offset)
  {
    return DSL.lag(this, offset);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lag(int offset, T defaultValue)
  {
    return DSL.lag(this, offset, defaultValue);
  }
  
  @Deprecated
  public final WindowIgnoreNullsStep<T> lag(int offset, Field<T> defaultValue)
  {
    return DSL.lag(this, offset, defaultValue);
  }
  
  @Deprecated
  public final WindowPartitionByStep<BigDecimal> stddevPopOver()
  {
    return DSL.stddevPop(numeric()).over();
  }
  
  @Deprecated
  public final WindowPartitionByStep<BigDecimal> stddevSampOver()
  {
    return DSL.stddevSamp(numeric()).over();
  }
  
  @Deprecated
  public final WindowPartitionByStep<BigDecimal> varPopOver()
  {
    return DSL.varPop(numeric()).over();
  }
  
  @Deprecated
  public final WindowPartitionByStep<BigDecimal> varSampOver()
  {
    return DSL.varSamp(numeric()).over();
  }
  
  @Deprecated
  public final Field<String> upper()
  {
    return DSL.upper(varchar());
  }
  
  @Deprecated
  public final Field<String> lower()
  {
    return DSL.lower(varchar());
  }
  
  @Deprecated
  public final Field<String> trim()
  {
    return DSL.trim(varchar());
  }
  
  @Deprecated
  public final Field<String> rtrim()
  {
    return DSL.rtrim(varchar());
  }
  
  @Deprecated
  public final Field<String> ltrim()
  {
    return DSL.ltrim(varchar());
  }
  
  @Deprecated
  public final Field<String> rpad(Field<? extends Number> length)
  {
    return DSL.rpad(varchar(), length);
  }
  
  @Deprecated
  public final Field<String> rpad(int length)
  {
    return DSL.rpad(varchar(), length);
  }
  
  @Deprecated
  public final Field<String> rpad(Field<? extends Number> length, Field<String> character)
  {
    return DSL.rpad(varchar(), length, character);
  }
  
  @Deprecated
  public final Field<String> rpad(int length, char character)
  {
    return DSL.rpad(varchar(), length, character);
  }
  
  @Deprecated
  public final Field<String> lpad(Field<? extends Number> length)
  {
    return DSL.lpad(varchar(), length);
  }
  
  @Deprecated
  public final Field<String> lpad(int length)
  {
    return DSL.lpad(varchar(), length);
  }
  
  @Deprecated
  public final Field<String> lpad(Field<? extends Number> length, Field<String> character)
  {
    return DSL.lpad(varchar(), length, character);
  }
  
  @Deprecated
  public final Field<String> lpad(int length, char character)
  {
    return DSL.lpad(varchar(), length, character);
  }
  
  @Deprecated
  public final Field<String> repeat(Number count)
  {
    return DSL.repeat(varchar(), count == null ? 0 : count.intValue());
  }
  
  @Deprecated
  public final Field<String> repeat(Field<? extends Number> count)
  {
    return DSL.repeat(varchar(), count);
  }
  
  @Deprecated
  public final Field<String> replace(Field<String> search)
  {
    return DSL.replace(varchar(), search);
  }
  
  @Deprecated
  public final Field<String> replace(String search)
  {
    return DSL.replace(varchar(), search);
  }
  
  @Deprecated
  public final Field<String> replace(Field<String> search, Field<String> replace)
  {
    return DSL.replace(varchar(), search, replace);
  }
  
  @Deprecated
  public final Field<String> replace(String search, String replace)
  {
    return DSL.replace(varchar(), search, replace);
  }
  
  @Deprecated
  public final Field<Integer> position(String search)
  {
    return DSL.position(varchar(), search);
  }
  
  @Deprecated
  public final Field<Integer> position(Field<String> search)
  {
    return DSL.position(varchar(), search);
  }
  
  @Deprecated
  public final Field<Integer> ascii()
  {
    return DSL.ascii(varchar());
  }
  
  @Deprecated
  public final Field<String> concat(Field<?>... fields)
  {
    return DSL.concat(Utils.combine(this, fields));
  }
  
  @Deprecated
  public final Field<String> concat(String... values)
  {
    return DSL.concat(Utils.combine(this, (Field[])Utils.fields(values).toArray(new Field[0])));
  }
  
  @Deprecated
  public final Field<String> substring(int startingPosition)
  {
    return DSL.substring(varchar(), startingPosition);
  }
  
  @Deprecated
  public final Field<String> substring(Field<? extends Number> startingPosition)
  {
    return DSL.substring(varchar(), startingPosition);
  }
  
  @Deprecated
  public final Field<String> substring(int startingPosition, int length)
  {
    return DSL.substring(varchar(), startingPosition, length);
  }
  
  @Deprecated
  public final Field<String> substring(Field<? extends Number> startingPosition, Field<? extends Number> length)
  {
    return DSL.substring(varchar(), startingPosition, length);
  }
  
  @Deprecated
  public final Field<Integer> length()
  {
    return DSL.length(varchar());
  }
  
  @Deprecated
  public final Field<Integer> charLength()
  {
    return DSL.charLength(varchar());
  }
  
  @Deprecated
  public final Field<Integer> bitLength()
  {
    return DSL.bitLength(varchar());
  }
  
  @Deprecated
  public final Field<Integer> octetLength()
  {
    return DSL.octetLength(varchar());
  }
  
  @Deprecated
  public final Field<Integer> extract(DatePart datePart)
  {
    return DSL.extract(date(), datePart);
  }
  
  @Deprecated
  public final Field<T> greatest(T... others)
  {
    return DSL.greatest(this, (Field[])Utils.fields(others).toArray(new Field[0]));
  }
  
  @Deprecated
  public final Field<T> greatest(Field<?>... others)
  {
    return DSL.greatest(this, others);
  }
  
  @Deprecated
  public final Field<T> least(T... others)
  {
    return DSL.least(this, (Field[])Utils.fields(others).toArray(new Field[0]));
  }
  
  @Deprecated
  public final Field<T> least(Field<?>... others)
  {
    return DSL.least(this, others);
  }
  
  @Deprecated
  public final Field<T> nvl(T defaultValue)
  {
    return DSL.nvl(this, defaultValue);
  }
  
  @Deprecated
  public final Field<T> nvl(Field<T> defaultValue)
  {
    return DSL.nvl(this, defaultValue);
  }
  
  @Deprecated
  public final <Z> Field<Z> nvl2(Z valueIfNotNull, Z valueIfNull)
  {
    return DSL.nvl2(this, valueIfNotNull, valueIfNull);
  }
  
  @Deprecated
  public final <Z> Field<Z> nvl2(Field<Z> valueIfNotNull, Field<Z> valueIfNull)
  {
    return DSL.nvl2(this, valueIfNotNull, valueIfNull);
  }
  
  @Deprecated
  public final Field<T> nullif(T other)
  {
    return DSL.nullif(this, other);
  }
  
  @Deprecated
  public final Field<T> nullif(Field<T> other)
  {
    return DSL.nullif(this, other);
  }
  
  @Deprecated
  public final <Z> Field<Z> decode(T search, Z result)
  {
    return DSL.decode(this, search, result);
  }
  
  @Deprecated
  public final <Z> Field<Z> decode(T search, Z result, Object... more)
  {
    return DSL.decode(this, search, result, more);
  }
  
  @Deprecated
  public final <Z> Field<Z> decode(Field<T> search, Field<Z> result)
  {
    return DSL.decode(this, search, result);
  }
  
  @Deprecated
  public final <Z> Field<Z> decode(Field<T> search, Field<Z> result, Field<?>... more)
  {
    return DSL.decode(this, search, result, more);
  }
  
  @Deprecated
  public final Field<T> coalesce(T option, T... options)
  {
    return DSL.coalesce(this, Utils.combine(Utils.field(option), (Field[])Utils.fields(options).toArray(new Field[0])));
  }
  
  @Deprecated
  public final Field<T> coalesce(Field<T> option, Field<?>... options)
  {
    return DSL.coalesce(this, Utils.combine(option, options));
  }
  
  public boolean equals(Object that)
  {
    if (this == that) {
      return true;
    }
    if ((that instanceof AbstractField))
    {
      if (StringUtils.equals(this.name, ((AbstractField)that).name)) {
        return super.equals(that);
      }
      return false;
    }
    return false;
  }
  
  public int hashCode()
  {
    return this.name.hashCode();
  }
}

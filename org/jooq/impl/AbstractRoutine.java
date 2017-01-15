package org.jooq.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.AggregateFunction;
import org.jooq.AttachableInternal;
import org.jooq.BindContext;
import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Package;
import org.jooq.Parameter;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.Routine;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.conf.Settings;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.tools.Convert;

public abstract class AbstractRoutine<T>
  extends AbstractQueryPart
  implements Routine<T>, AttachableInternal
{
  private static final long serialVersionUID = 6330037113167106443L;
  private static final Clause[] CLAUSES = { Clause.FIELD, Clause.FIELD_FUNCTION };
  private final Schema schema;
  private final Package pkg;
  private final String name;
  private final List<Parameter<?>> allParameters;
  private final List<Parameter<?>> inParameters;
  private final List<Parameter<?>> outParameters;
  private final DataType<T> type;
  private Parameter<T> returnParameter;
  private List<Result<Record>> results;
  private boolean overloaded;
  private boolean hasDefaultedParameters;
  private final Map<Parameter<?>, Field<?>> inValues;
  private final Set<Parameter<?>> inValuesDefaulted;
  private final Set<Parameter<?>> inValuesNonDefaulted;
  private transient Field<T> function;
  private Configuration configuration;
  private final Map<Parameter<?>, Object> outValues;
  private final Map<Parameter<?>, Integer> parameterIndexes;
  
  protected AbstractRoutine(String name, Schema schema)
  {
    this(name, schema, null, null, null, null);
  }
  
  protected AbstractRoutine(String name, Schema schema, Package pkg)
  {
    this(name, schema, pkg, null, null, null);
  }
  
  protected AbstractRoutine(String name, Schema schema, DataType<T> type)
  {
    this(name, schema, null, type, null, null);
  }
  
  protected <X> AbstractRoutine(String name, Schema schema, DataType<X> type, Converter<X, T> converter)
  {
    this(name, schema, null, type, converter, null);
  }
  
  protected <X> AbstractRoutine(String name, Schema schema, DataType<X> type, Binding<X, T> binding)
  {
    this(name, schema, null, type, null, binding);
  }
  
  protected <X, Y> AbstractRoutine(String name, Schema schema, DataType<X> type, Converter<Y, T> converter, Binding<X, Y> binding)
  {
    this(name, schema, null, type, converter, binding);
  }
  
  protected AbstractRoutine(String name, Schema schema, Package pkg, DataType<T> type)
  {
    this(name, schema, pkg, type, null, null);
  }
  
  protected <X> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Converter<X, T> converter)
  {
    this(name, schema, pkg, type, converter, null);
  }
  
  protected <X> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Binding<X, T> binding)
  {
    this(name, schema, pkg, type, null, binding);
  }
  
  protected <X, Y> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Converter<Y, T> converter, Binding<X, Y> binding)
  {
    this.parameterIndexes = new HashMap();
    
    this.schema = schema;
    this.pkg = pkg;
    this.name = name;
    this.allParameters = new ArrayList();
    this.inParameters = new ArrayList();
    this.outParameters = new ArrayList();
    this.results = new ArrayList();
    this.inValues = new HashMap();
    this.inValuesDefaulted = new HashSet();
    this.inValuesNonDefaulted = new HashSet();
    this.outValues = new HashMap();
    this.type = ((converter == null) && (binding == null) ? type : type
    
      .asConvertedDataType(DefaultBinding.newBinding(converter, type, binding)));
  }
  
  protected final void setNumber(Parameter<? extends Number> parameter, Number value)
  {
    setValue(parameter, Convert.convert(value, parameter.getType()));
  }
  
  protected final void setNumber(Parameter<? extends Number> parameter, Field<? extends Number> value)
  {
    setField(parameter, value);
  }
  
  protected final void setValue(Parameter<?> parameter, Object value)
  {
    setField(parameter, DSL.val(value, parameter.getDataType()));
  }
  
  protected final void setField(Parameter<?> parameter, Field<?> value)
  {
    if (value == null)
    {
      setField(parameter, DSL.val(null, parameter.getDataType()));
    }
    else
    {
      this.inValues.put(parameter, value);
      this.inValuesDefaulted.remove(parameter);
      this.inValuesNonDefaulted.add(parameter);
    }
  }
  
  public final void attach(Configuration c)
  {
    this.configuration = c;
  }
  
  public final void detach()
  {
    attach(null);
  }
  
  public final Configuration configuration()
  {
    return this.configuration;
  }
  
  public final int execute(Configuration c)
  {
    Configuration previous = configuration();
    try
    {
      attach(c);
      return execute();
    }
    finally
    {
      attach(previous);
    }
  }
  
  public final int execute()
  {
    this.results.clear();
    this.outValues.clear();
    if (this.type == null) {
      return executeCallableStatement();
    }
    switch (this.configuration.dialect().family())
    {
    case HSQLDB: 
      if (SQLDataType.RESULT.equals(this.type.getSQLDataType())) {
        return executeSelectFrom();
      }
    case H2: 
      return executeSelect();
    }
    return executeCallableStatement();
  }
  
  private final int executeSelectFrom()
  {
    DSLContext create = create(this.configuration);
    Result<?> result = create.selectFrom(DSL.table(asField())).fetch();
    this.outValues.put(this.returnParameter, result);
    return 0;
  }
  
  private final int executeSelect()
  {
    Field<T> field = asField();
    this.outValues.put(this.returnParameter, create(this.configuration).select(field).fetchOne(field));
    return 0;
  }
  
  private final int executeCallableStatement()
  {
    ExecuteContext ctx = new DefaultExecuteContext(this.configuration, this);
    ExecuteListener listener = new ExecuteListeners(ctx);
    try
    {
      Connection connection = ctx.connection();
      
      listener.renderStart(ctx);
      
      ctx.sql(create(this.configuration).render(this));
      listener.renderEnd(ctx);
      
      listener.prepareStart(ctx);
      ctx.statement(connection.prepareCall(ctx.sql()));
      
      listener.prepareEnd(ctx);
      
      listener.bindStart(ctx);
      DSL.using(this.configuration).bindContext(ctx.statement()).visit(this);
      registerOutParameters(this.configuration, (CallableStatement)ctx.statement());
      listener.bindEnd(ctx);
      
      execute0(ctx, listener);
      if (ctx.family() != SQLDialect.FIREBIRD) {
        Utils.consumeResultSets(ctx, listener, this.results, null);
      }
      fetchOutParameters(ctx);
      return 0;
    }
    catch (ControlFlowSignal e)
    {
      throw e;
    }
    catch (RuntimeException e)
    {
      ctx.exception(e);
      listener.exception(ctx);
      throw ctx.exception();
    }
    catch (SQLException e)
    {
      ctx.sqlException(e);
      listener.exception(ctx);
      throw ctx.exception();
    }
    finally
    {
      Utils.safeClose(listener, ctx);
    }
  }
  
  private final void execute0(ExecuteContext ctx, ExecuteListener listener)
    throws SQLException
  {
    try
    {
      listener.executeStart(ctx);
      if (ctx.statement().execute()) {
        ctx.resultSet(ctx.statement().getResultSet());
      }
      listener.executeEnd(ctx);
    }
    catch (SQLException e)
    {
      Utils.consumeExceptions(ctx.configuration(), ctx.statement(), e);
      throw e;
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public void accept(Context<?> ctx)
  {
    if ((ctx instanceof RenderContext)) {
      toSQL0((RenderContext)ctx);
    } else {
      bind0((BindContext)ctx);
    }
  }
  
  final void bind0(BindContext context)
  {
    for (Parameter<?> parameter : getParameters()) {
      if ((!getInParameters().contains(parameter)) || (!this.inValuesDefaulted.contains(parameter)))
      {
        int index = context.peekIndex();
        this.parameterIndexes.put(parameter, Integer.valueOf(index));
        if (getInValues().get(parameter) != null)
        {
          context.visit((QueryPart)getInValues().get(parameter));
          if ((index == context.peekIndex()) && (getOutParameters().contains(parameter))) {
            context.nextIndex();
          }
        }
        else
        {
          context.nextIndex();
        }
      }
    }
  }
  
  final void toSQL0(RenderContext context)
  {
    toSQLBegin(context);
    if (getReturnParameter() != null) {
      toSQLAssign(context);
    }
    toSQLCall(context);
    context.sql("(");
    
    String separator = "";
    for (Parameter<?> parameter : getParameters()) {
      if (!parameter.equals(getReturnParameter()))
      {
        if (getOutParameters().contains(parameter))
        {
          context.sql(separator);
          toSQLOutParam(context, parameter);
        }
        else
        {
          if (this.inValuesDefaulted.contains(parameter)) {
            continue;
          }
          Field<?> value = (Field)getInValues().get(parameter);
          if ((SQLDialect.POSTGRES == context.configuration().dialect()) && (isOverloaded())) {
            value = value.cast(parameter.getType());
          }
          context.sql(separator);
          toSQLInParam(context, parameter, value);
        }
        separator = ", ";
      }
    }
    context.sql(")");
    toSQLEnd(context);
  }
  
  private final void toSQLEnd(RenderContext context)
  {
    context.sql(" }");
  }
  
  private final void toSQLBegin(RenderContext context)
  {
    context.sql("{ ");
  }
  
  private final void toSQLAssign(RenderContext context)
  {
    context.sql("? = ");
  }
  
  private final void toSQLCall(RenderContext context)
  {
    context.sql("call ");
    
    toSQLQualifiedName(context);
  }
  
  private final void toSQLOutParam(RenderContext context, Parameter<?> parameter)
  {
    context.sql("?");
  }
  
  private final void toSQLInParam(RenderContext context, Parameter<?> parameter, Field<?> value)
  {
    context.visit(value);
  }
  
  private final void toSQLQualifiedName(RenderContext context)
  {
    Schema mappedSchema = Utils.getMappedSchema(context.configuration(), getSchema());
    if (context.qualify())
    {
      if (mappedSchema != null)
      {
        context.visit(mappedSchema);
        context.sql(".");
      }
      if (getPackage() != null)
      {
        context.visit(getPackage());
        context.sql(".");
      }
    }
    context.literal(getName());
  }
  
  private final void fetchOutParameters(ExecuteContext ctx)
    throws SQLException
  {
    for (Parameter<?> parameter : getParameters()) {
      if ((parameter.equals(getReturnParameter())) || 
        (getOutParameters().contains(parameter))) {
        fetchOutParameter(ctx, parameter);
      }
    }
  }
  
  private final <U> void fetchOutParameter(ExecuteContext ctx, Parameter<U> parameter)
    throws SQLException
  {
    DefaultBindingGetStatementContext<U> out = new DefaultBindingGetStatementContext(ctx.configuration(), (CallableStatement)ctx.statement(), ((Integer)this.parameterIndexes.get(parameter)).intValue());
    
    parameter.getBinding().get(out);
    this.outValues.put(parameter, out.value());
  }
  
  private final void registerOutParameters(Configuration c, CallableStatement statement)
    throws SQLException
  {
    for (Parameter<?> parameter : getParameters()) {
      if ((parameter.equals(getReturnParameter())) || 
        (getOutParameters().contains(parameter))) {
        registerOutParameter(c, statement, parameter);
      }
    }
  }
  
  private final <U> void registerOutParameter(Configuration c, CallableStatement statement, Parameter<U> parameter)
    throws SQLException
  {
    parameter.getBinding().register(new DefaultBindingRegisterContext(c, statement, ((Integer)this.parameterIndexes.get(parameter)).intValue()));
  }
  
  public final T getReturnValue()
  {
    if (this.returnParameter != null) {
      return (T)getValue(this.returnParameter);
    }
    return null;
  }
  
  public final List<Result<Record>> getResults()
  {
    return this.results;
  }
  
  protected final <Z> Z getValue(Parameter<Z> parameter)
  {
    return (Z)this.outValues.get(parameter);
  }
  
  protected final Map<Parameter<?>, Field<?>> getInValues()
  {
    return this.inValues;
  }
  
  public final List<Parameter<?>> getOutParameters()
  {
    return Collections.unmodifiableList(this.outParameters);
  }
  
  public final List<Parameter<?>> getInParameters()
  {
    return Collections.unmodifiableList(this.inParameters);
  }
  
  public final List<Parameter<?>> getParameters()
  {
    return Collections.unmodifiableList(this.allParameters);
  }
  
  public final Schema getSchema()
  {
    return this.schema;
  }
  
  public final Package getPackage()
  {
    return this.pkg;
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  protected final Parameter<?> getReturnParameter()
  {
    return this.returnParameter;
  }
  
  protected final void setOverloaded(boolean overloaded)
  {
    this.overloaded = overloaded;
  }
  
  protected final boolean isOverloaded()
  {
    return this.overloaded;
  }
  
  private final boolean hasDefaultedParameters()
  {
    return (this.hasDefaultedParameters) && (!this.inValuesDefaulted.isEmpty());
  }
  
  private final void addParameter(Parameter<?> parameter)
  {
    this.allParameters.add(parameter);
    this.hasDefaultedParameters |= parameter.isDefaulted();
  }
  
  protected final void addInParameter(Parameter<?> parameter)
  {
    addParameter(parameter);
    this.inParameters.add(parameter);
    
    this.inValues.put(parameter, DSL.val(null, parameter.getDataType()));
    if (parameter.isDefaulted()) {
      this.inValuesDefaulted.add(parameter);
    } else {
      this.inValuesNonDefaulted.add(parameter);
    }
  }
  
  protected final void addInOutParameter(Parameter<?> parameter)
  {
    addInParameter(parameter);
    this.outParameters.add(parameter);
  }
  
  protected final void addOutParameter(Parameter<?> parameter)
  {
    addParameter(parameter);
    this.outParameters.add(parameter);
  }
  
  protected final void setReturnParameter(Parameter<T> parameter)
  {
    addParameter(parameter);
    this.returnParameter = parameter;
  }
  
  public final Field<T> asField()
  {
    if (this.function == null) {
      this.function = new RoutineField();
    }
    return this.function;
  }
  
  public final Field<T> asField(String alias)
  {
    return asField().as(alias);
  }
  
  public final AggregateFunction<T> asAggregateFunction()
  {
    Field<?>[] array = new Field[getInParameters().size()];
    
    int i = 0;
    for (Parameter<?> p : getInParameters())
    {
      array[i] = ((Field)getInValues().get(p));
      i++;
    }
    Object names = new ArrayList();
    if (this.schema != null) {
      ((List)names).add(this.schema.getName());
    }
    if (this.pkg != null) {
      ((List)names).add(this.pkg.getName());
    }
    ((List)names).add(this.name);
    return (AggregateFunction)DSL.function(DSL.name((String[])((List)names).toArray(new String[((List)names).size()])), this.type, array);
  }
  
  protected static final <T> Parameter<T> createParameter(String name, DataType<T> type)
  {
    return createParameter(name, type, false, null, null);
  }
  
  protected static final <T> Parameter<T> createParameter(String name, DataType<T> type, boolean isDefaulted)
  {
    return createParameter(name, type, isDefaulted, null, null);
  }
  
  protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Converter<T, U> converter)
  {
    return createParameter(name, type, isDefaulted, converter, null);
  }
  
  protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Binding<T, U> binding)
  {
    return createParameter(name, type, isDefaulted, null, binding);
  }
  
  protected static final <T, X, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Converter<X, U> converter, Binding<T, X> binding)
  {
    Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
    
    DataType<U> actualType = (converter == null) && (binding == null) ? type : type.asConvertedDataType(actualBinding);
    
    return new ParameterImpl(name, actualType, isDefaulted, actualBinding);
  }
  
  private class RoutineField
    extends AbstractField<T>
  {
    private static final long serialVersionUID = -5730297947647252624L;
    
    RoutineField()
    {
      super(
        AbstractRoutine.this.type);
    }
    
    public void accept(Context<?> ctx)
    {
      RenderContext local = create(ctx).renderContext();
      AbstractRoutine.this.toSQLQualifiedName(local);
      
      Field<?>[] array = new Field[AbstractRoutine.this.getInParameters().size()];
      
      int i = 0;
      for (Parameter<?> p : AbstractRoutine.this.getInParameters())
      {
        if ((SQLDialect.POSTGRES == ctx.dialect()) && (AbstractRoutine.this.isOverloaded())) {
          array[i] = ((Field)AbstractRoutine.this.getInValues().get(p)).cast(p.getType());
        } else {
          array[i] = ((Field)AbstractRoutine.this.getInValues().get(p));
        }
        i++;
      }
      Object result = DSL.function(local.render(), getDataType(), array);
      if (Boolean.TRUE.equals(Utils.settings(ctx.configuration()).isRenderScalarSubqueriesForStoredFunctions())) {
        result = DSL.select((Field)result).asField();
      }
      ctx.visit((QueryPart)result);
    }
  }
  
  public int hashCode()
  {
    return this.name.hashCode();
  }
}

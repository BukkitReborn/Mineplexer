package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;

class Alias<Q extends QueryPart>
  extends AbstractQueryPart
{
  private static final long serialVersionUID = -2456848365524191614L;
  private static final Clause[] CLAUSES_TABLE_REFERENCE = { Clause.TABLE, Clause.TABLE_REFERENCE };
  private static final Clause[] CLAUSES_TABLE_ALIAS = { Clause.TABLE, Clause.TABLE_ALIAS };
  private static final Clause[] CLAUSES_FIELD_REFERENCE = { Clause.FIELD, Clause.FIELD_REFERENCE };
  private static final Clause[] CLAUSES_FIELD_ALIAS = { Clause.FIELD, Clause.FIELD_ALIAS };
  private final Q wrapped;
  private final String alias;
  private final String[] fieldAliases;
  private final boolean wrapInParentheses;
  
  Alias(Q wrapped, String alias)
  {
    this(wrapped, alias, null, false);
  }
  
  Alias(Q wrapped, String alias, boolean wrapInParentheses)
  {
    this(wrapped, alias, null, wrapInParentheses);
  }
  
  Alias(Q wrapped, String alias, String[] fieldAliases)
  {
    this(wrapped, alias, fieldAliases, false);
  }
  
  Alias(Q wrapped, String alias, String[] fieldAliases, boolean wrapInParentheses)
  {
    this.wrapped = wrapped;
    this.alias = alias;
    this.fieldAliases = fieldAliases;
    this.wrapInParentheses = wrapInParentheses;
  }
  
  final Q wrapped()
  {
    return this.wrapped;
  }
  
  public final void accept(Context<?> context)
  {
    if ((context.declareFields()) || (context.declareTables()))
    {
      SQLDialect dialect = context.configuration().dialect();
      boolean simulateDerivedColumnList = false;
      if (this.fieldAliases != null) {
        if ((Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID, SQLDialect.FIREBIRD }).contains(dialect.family())) && (((this.wrapped instanceof TableImpl)) || ((this.wrapped instanceof CommonTableExpressionImpl))))
        {
          Select<Record> select = DSL.select(Utils.list(new Field[] { DSL.field("*") })).from(((Table)this.wrapped).as(this.alias));
          
          context.sql("(").formatIndentStart().formatNewLine()
            .visit(select).formatIndentEnd().formatNewLine()
            .sql(")");
          break label462;
        }
      }
      if (this.fieldAliases != null) {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE }).contains(dialect.family()))
        {
          simulateDerivedColumnList = true;
          
          SelectFieldList fields = new SelectFieldList();
          for (String fieldAlias : this.fieldAliases)
          {
            switch (dialect.family())
            {
            }
            fields.add(DSL.field("null").as(fieldAlias));
          }
          Object select = DSL.select(fields).where(new Condition[] { DSL.falseCondition() }).unionAll((this.wrapped instanceof DerivedTable) ? ((DerivedTable)this.wrapped)
          
            .query() : (this.wrapped instanceof Select) ? (Select)this.wrapped : 
            DSL.select(DSL.field("*")).from(((Table)this.wrapped).as(this.alias)));
          
          context.sql("(").formatIndentStart().formatNewLine()
            .visit((QueryPart)select).formatIndentEnd().formatNewLine()
            .sql(")");
          break label462;
        }
      }
      toSQLWrapped(context);
      label462:
      toSQLAs(context);
      context.sql(" ");
      context.literal(this.alias);
      if ((this.fieldAliases != null) && (!simulateDerivedColumnList)) {
        toSQLDerivedColumnList(context);
      } else {
        switch (dialect)
        {
        case HSQLDB: 
        case POSTGRES: 
          Object o = this.wrapped;
          if ((context.declareTables()) && ((o instanceof ArrayTable)))
          {
            ArrayTable table = (ArrayTable)o;
            
            context.sql("(");
            Utils.fieldNames(context, table.fields());
            context.sql(")");
          }
          break;
        }
      }
    }
    else
    {
      context.literal(this.alias);
    }
  }
  
  static void toSQLAs(Context<?> context)
  {
    if (Arrays.asList(new SQLDialect[] { SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES }).contains(context.configuration().dialect())) {
      context.sql(" ").keyword("as");
    }
  }
  
  private void toSQLWrapped(Context<?> context)
  {
    context.sql(this.wrapInParentheses ? "(" : "").visit(this.wrapped).sql(this.wrapInParentheses ? ")" : "");
  }
  
  private void toSQLDerivedColumnList(Context<?> context)
  {
    String separator = "";
    
    context.sql("(");
    for (int i = 0; i < this.fieldAliases.length; i++)
    {
      context.sql(separator);
      context.literal(this.fieldAliases[i]);
      
      separator = ", ";
    }
    context.sql(")");
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    if ((ctx.declareFields()) || (ctx.declareTables()))
    {
      if ((this.wrapped instanceof Table)) {
        return CLAUSES_TABLE_ALIAS;
      }
      return CLAUSES_FIELD_ALIAS;
    }
    if ((this.wrapped instanceof Table)) {
      return CLAUSES_TABLE_REFERENCE;
    }
    return CLAUSES_FIELD_REFERENCE;
  }
  
  public final boolean declaresFields()
  {
    return true;
  }
  
  public final boolean declaresTables()
  {
    return true;
  }
}

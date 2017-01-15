package org.jooq.tools.jdbc;

import org.jooq.AttachableInternal;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public class MockResult
{
  public final int rows;
  public final Result<?> data;
  
  public MockResult(Record data)
  {
    this(1, result(data));
  }
  
  public MockResult(int rows, Result<?> data)
  {
    this.rows = rows;
    this.data = data;
  }
  
  private static final Result<?> result(Record data)
  {
    Configuration configuration = (data instanceof AttachableInternal) ? ((AttachableInternal)data).configuration() : new DefaultConfiguration();
    
    Result<Record> result = DSL.using(configuration).newResult(data.fields());
    result.add(data);
    
    return result;
  }
  
  public String toString()
  {
    return "" + this.rows;
  }
}

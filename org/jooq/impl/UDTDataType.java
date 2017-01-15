package org.jooq.impl;

import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.UDT;
import org.jooq.UDTRecord;

class UDTDataType<R extends UDTRecord<R>>
  extends DefaultDataType<R>
{
  private static final long serialVersionUID = 3262508265391094581L;
  
  UDTDataType(UDT<R> udt)
  {
    super(SQLDialect.SQL99, udt.getRecordType(), getQualifiedName(udt));
    
    DataTypes.registerUDTRecord(getQualifiedName(udt), udt.getRecordType());
  }
  
  private static String getQualifiedName(UDT<?> udt)
  {
    StringBuilder sb = new StringBuilder();
    if (udt.getSchema() != null)
    {
      sb.append(udt.getSchema().getName());
      sb.append(".");
    }
    sb.append(udt.getName());
    return sb.toString();
  }
}

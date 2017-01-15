package org.jooq.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class DataTypes
{
  private static final Map<String, Class<?>> UDT_RECORDS = new HashMap();
  
  static final synchronized void registerUDTRecord(String name, Class<?> type)
  {
    if (!UDT_RECORDS.containsKey(name)) {
      UDT_RECORDS.put(name, type);
    }
  }
  
  static final Map<String, Class<?>> udtRecords()
  {
    return Collections.unmodifiableMap(UDT_RECORDS);
  }
}

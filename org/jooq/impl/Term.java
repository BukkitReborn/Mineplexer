package org.jooq.impl;

import org.jooq.SQLDialect;

 enum Term
{
  ATAN2,  BIT_LENGTH,  CHAR_LENGTH,  LIST_AGG,  OCTET_LENGTH,  ROW_NUMBER,  STDDEV_POP,  STDDEV_SAMP,  VAR_POP,  VAR_SAMP;
  
  private Term() {}
  
  abstract String translate(SQLDialect paramSQLDialect);
}

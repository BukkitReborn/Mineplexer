package org.jooq;

public abstract interface WithAsStep
{
  @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract WithStep as(Select<?> paramSelect);
}

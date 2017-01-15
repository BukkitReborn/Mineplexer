package org.jooq.impl;

import java.io.Serializable;
import org.jooq.Field;

class Intern
  implements Serializable
{
  private static final long serialVersionUID = 6455756912567274014L;
  int[] internIndexes;
  Field<?>[] internFields;
  String[] internNames;
  
  final int[] internIndexes(Field<?>[] fields)
  {
    if (this.internIndexes != null) {
      return this.internIndexes;
    }
    if (this.internFields != null) {
      return new Fields(fields).indexesOf(this.internFields);
    }
    if (this.internNames != null) {
      return new Fields(fields).indexesOf(this.internNames);
    }
    return null;
  }
}

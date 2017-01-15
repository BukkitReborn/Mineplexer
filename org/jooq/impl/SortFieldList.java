package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Field;
import org.jooq.SortField;

class SortFieldList
  extends QueryPartList<SortField<?>>
{
  private static final long serialVersionUID = -1825164005148183725L;
  
  SortFieldList()
  {
    this(new ArrayList());
  }
  
  SortFieldList(List<SortField<?>> wrappedList)
  {
    super(wrappedList);
  }
  
  final void addAll(Field<?>... fields)
  {
    SortField<?>[] result = new SortField[fields.length];
    for (int i = 0; i < fields.length; i++) {
      result[i] = fields[i].asc();
    }
    addAll(Arrays.asList(result));
  }
  
  final boolean uniform()
  {
    for (SortField<?> field : this) {
      if (field.getOrder() != ((SortField)get(0)).getOrder()) {
        return false;
      }
    }
    return true;
  }
  
  final boolean nulls()
  {
    for (SortField<?> field : this) {
      if ((((SortFieldImpl)field).getNullsFirst()) || 
        (((SortFieldImpl)field).getNullsFirst())) {
        return true;
      }
    }
    return false;
  }
  
  final List<Field<?>> fields()
  {
    List<Field<?>> result = new ArrayList();
    for (SortField<?> field : this) {
      result.add(((SortFieldImpl)field).getField());
    }
    return result;
  }
}

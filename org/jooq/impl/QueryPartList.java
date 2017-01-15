package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;

class QueryPartList<T extends QueryPart>
  extends AbstractQueryPart
  implements List<T>
{
  private static final long serialVersionUID = -2936922742534009564L;
  private final List<T> wrappedList;
  
  QueryPartList()
  {
    this((Collection)null);
  }
  
  QueryPartList(Collection<? extends T> wrappedList)
  {
    this.wrappedList = new ArrayList();
    if (wrappedList != null) {
      addAll(wrappedList);
    }
  }
  
  QueryPartList(T... wrappedList)
  {
    this(Arrays.asList(wrappedList));
  }
  
  public final void accept(Context<?> ctx)
  {
    if (isEmpty())
    {
      toSQLEmptyList(ctx);
    }
    else
    {
      String separator = "";
      boolean indent = size() > 1;
      if (indent) {
        ctx.formatIndentStart();
      }
      for (T queryPart : this)
      {
        ctx.sql(separator);
        if (indent) {
          ctx.formatNewLine();
        }
        ctx.visit(queryPart);
        separator = ", ";
      }
      if (indent) {
        ctx.formatIndentEnd();
      }
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  protected void toSQLEmptyList(Context<?> context) {}
  
  public final int size()
  {
    return this.wrappedList.size();
  }
  
  public final boolean isEmpty()
  {
    return this.wrappedList.isEmpty();
  }
  
  public final boolean contains(Object o)
  {
    return this.wrappedList.contains(o);
  }
  
  public final Iterator<T> iterator()
  {
    return this.wrappedList.iterator();
  }
  
  public final Object[] toArray()
  {
    return this.wrappedList.toArray();
  }
  
  public final <E> E[] toArray(E[] a)
  {
    return this.wrappedList.toArray(a);
  }
  
  public final boolean add(T e)
  {
    if (e != null) {
      return this.wrappedList.add(e);
    }
    return false;
  }
  
  public final boolean remove(Object o)
  {
    return this.wrappedList.remove(o);
  }
  
  public final boolean containsAll(Collection<?> c)
  {
    return this.wrappedList.containsAll(c);
  }
  
  public final boolean addAll(Collection<? extends T> c)
  {
    return this.wrappedList.addAll(removeNulls(c));
  }
  
  public final boolean addAll(int index, Collection<? extends T> c)
  {
    return this.wrappedList.addAll(index, removeNulls(c));
  }
  
  private final Collection<? extends T> removeNulls(Collection<? extends T> c)
  {
    if (c.contains(null))
    {
      List<T> list = new ArrayList(c);
      Iterator<T> it = list.iterator();
      while (it.hasNext()) {
        if (it.next() == null) {
          it.remove();
        }
      }
      return list;
    }
    return c;
  }
  
  public final boolean removeAll(Collection<?> c)
  {
    return this.wrappedList.removeAll(c);
  }
  
  public final boolean retainAll(Collection<?> c)
  {
    return this.wrappedList.retainAll(c);
  }
  
  public final void clear()
  {
    this.wrappedList.clear();
  }
  
  public final T get(int index)
  {
    return (QueryPart)this.wrappedList.get(index);
  }
  
  public final T set(int index, T element)
  {
    if (element != null) {
      return (QueryPart)this.wrappedList.set(index, element);
    }
    return null;
  }
  
  public final void add(int index, T element)
  {
    if (element != null) {
      this.wrappedList.add(index, element);
    }
  }
  
  public final T remove(int index)
  {
    return (QueryPart)this.wrappedList.remove(index);
  }
  
  public final int indexOf(Object o)
  {
    return this.wrappedList.indexOf(o);
  }
  
  public final int lastIndexOf(Object o)
  {
    return this.wrappedList.lastIndexOf(o);
  }
  
  public final ListIterator<T> listIterator()
  {
    return this.wrappedList.listIterator();
  }
  
  public final ListIterator<T> listIterator(int index)
  {
    return this.wrappedList.listIterator(index);
  }
  
  public final List<T> subList(int fromIndex, int toIndex)
  {
    return this.wrappedList.subList(fromIndex, toIndex);
  }
}

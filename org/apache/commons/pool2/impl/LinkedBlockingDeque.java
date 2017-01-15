package org.apache.commons.pool2.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

class LinkedBlockingDeque<E>
  extends AbstractQueue<E>
  implements Deque<E>, Serializable
{
  private static final long serialVersionUID = -387911632671998426L;
  private transient Node<E> first;
  private transient Node<E> last;
  private transient int count;
  private final int capacity;
  
  private static final class Node<E>
  {
    E item;
    Node<E> prev;
    Node<E> next;
    
    Node(E x, Node<E> p, Node<E> n)
    {
      this.item = x;
      this.prev = p;
      this.next = n;
    }
  }
  
  private final InterruptibleReentrantLock lock = new InterruptibleReentrantLock();
  private final Condition notEmpty = this.lock.newCondition();
  private final Condition notFull = this.lock.newCondition();
  
  public LinkedBlockingDeque()
  {
    this(Integer.MAX_VALUE);
  }
  
  public LinkedBlockingDeque(int capacity)
  {
    if (capacity <= 0) {
      throw new IllegalArgumentException();
    }
    this.capacity = capacity;
  }
  
  public LinkedBlockingDeque(Collection<? extends E> c)
  {
    this(Integer.MAX_VALUE);
    this.lock.lock();
    try
    {
      for (E e : c)
      {
        if (e == null) {
          throw new NullPointerException();
        }
        if (!linkLast(e)) {
          throw new IllegalStateException("Deque full");
        }
      }
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  private boolean linkFirst(E e)
  {
    if (this.count >= this.capacity) {
      return false;
    }
    Node<E> f = this.first;
    Node<E> x = new Node(e, null, f);
    this.first = x;
    if (this.last == null) {
      this.last = x;
    } else {
      f.prev = x;
    }
    this.count += 1;
    this.notEmpty.signal();
    return true;
  }
  
  private boolean linkLast(E e)
  {
    if (this.count >= this.capacity) {
      return false;
    }
    Node<E> l = this.last;
    Node<E> x = new Node(e, l, null);
    this.last = x;
    if (this.first == null) {
      this.first = x;
    } else {
      l.next = x;
    }
    this.count += 1;
    this.notEmpty.signal();
    return true;
  }
  
  private E unlinkFirst()
  {
    Node<E> f = this.first;
    if (f == null) {
      return null;
    }
    Node<E> n = f.next;
    E item = f.item;
    f.item = null;
    f.next = f;
    this.first = n;
    if (n == null) {
      this.last = null;
    } else {
      n.prev = null;
    }
    this.count -= 1;
    this.notFull.signal();
    return item;
  }
  
  private E unlinkLast()
  {
    Node<E> l = this.last;
    if (l == null) {
      return null;
    }
    Node<E> p = l.prev;
    E item = l.item;
    l.item = null;
    l.prev = l;
    this.last = p;
    if (p == null) {
      this.first = null;
    } else {
      p.next = null;
    }
    this.count -= 1;
    this.notFull.signal();
    return item;
  }
  
  private void unlink(Node<E> x)
  {
    Node<E> p = x.prev;
    Node<E> n = x.next;
    if (p == null)
    {
      unlinkFirst();
    }
    else if (n == null)
    {
      unlinkLast();
    }
    else
    {
      p.next = n;
      n.prev = p;
      x.item = null;
      
      this.count -= 1;
      this.notFull.signal();
    }
  }
  
  public void addFirst(E e)
  {
    if (!offerFirst(e)) {
      throw new IllegalStateException("Deque full");
    }
  }
  
  public void addLast(E e)
  {
    if (!offerLast(e)) {
      throw new IllegalStateException("Deque full");
    }
  }
  
  public boolean offerFirst(E e)
  {
    if (e == null) {
      throw new NullPointerException();
    }
    this.lock.lock();
    try
    {
      return linkFirst(e);
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public boolean offerLast(E e)
  {
    if (e == null) {
      throw new NullPointerException();
    }
    this.lock.lock();
    try
    {
      return linkLast(e);
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public void putFirst(E e)
    throws InterruptedException
  {
    if (e == null) {
      throw new NullPointerException();
    }
    this.lock.lock();
    try
    {
      while (!linkFirst(e)) {
        this.notFull.await();
      }
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public void putLast(E e)
    throws InterruptedException
  {
    if (e == null) {
      throw new NullPointerException();
    }
    this.lock.lock();
    try
    {
      while (!linkLast(e)) {
        this.notFull.await();
      }
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public boolean offerFirst(E e, long timeout, TimeUnit unit)
    throws InterruptedException
  {
    if (e == null) {
      throw new NullPointerException();
    }
    long nanos = unit.toNanos(timeout);
    this.lock.lockInterruptibly();
    try
    {
      boolean bool;
      while (!linkFirst(e))
      {
        if (nanos <= 0L) {
          return false;
        }
        nanos = this.notFull.awaitNanos(nanos);
      }
      return true;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public boolean offerLast(E e, long timeout, TimeUnit unit)
    throws InterruptedException
  {
    if (e == null) {
      throw new NullPointerException();
    }
    long nanos = unit.toNanos(timeout);
    this.lock.lockInterruptibly();
    try
    {
      boolean bool;
      while (!linkLast(e))
      {
        if (nanos <= 0L) {
          return false;
        }
        nanos = this.notFull.awaitNanos(nanos);
      }
      return true;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E removeFirst()
  {
    E x = pollFirst();
    if (x == null) {
      throw new NoSuchElementException();
    }
    return x;
  }
  
  public E removeLast()
  {
    E x = pollLast();
    if (x == null) {
      throw new NoSuchElementException();
    }
    return x;
  }
  
  public E pollFirst()
  {
    this.lock.lock();
    try
    {
      return (E)unlinkFirst();
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E pollLast()
  {
    this.lock.lock();
    try
    {
      return (E)unlinkLast();
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E takeFirst()
    throws InterruptedException
  {
    this.lock.lock();
    try
    {
      E x;
      while ((x = unlinkFirst()) == null) {
        this.notEmpty.await();
      }
      return x;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E takeLast()
    throws InterruptedException
  {
    this.lock.lock();
    try
    {
      E x;
      while ((x = unlinkLast()) == null) {
        this.notEmpty.await();
      }
      return x;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E pollFirst(long timeout, TimeUnit unit)
    throws InterruptedException
  {
    long nanos = unit.toNanos(timeout);
    this.lock.lockInterruptibly();
    try
    {
      E x;
      E ?;
      while ((x = unlinkFirst()) == null)
      {
        if (nanos <= 0L) {
          return null;
        }
        nanos = this.notEmpty.awaitNanos(nanos);
      }
      return x;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E pollLast(long timeout, TimeUnit unit)
    throws InterruptedException
  {
    long nanos = unit.toNanos(timeout);
    this.lock.lockInterruptibly();
    try
    {
      E x;
      E ?;
      while ((x = unlinkLast()) == null)
      {
        if (nanos <= 0L) {
          return null;
        }
        nanos = this.notEmpty.awaitNanos(nanos);
      }
      return x;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E getFirst()
  {
    E x = peekFirst();
    if (x == null) {
      throw new NoSuchElementException();
    }
    return x;
  }
  
  public E getLast()
  {
    E x = peekLast();
    if (x == null) {
      throw new NoSuchElementException();
    }
    return x;
  }
  
  public E peekFirst()
  {
    this.lock.lock();
    try
    {
      return this.first == null ? null : this.first.item;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public E peekLast()
  {
    this.lock.lock();
    try
    {
      return this.last == null ? null : this.last.item;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public boolean removeFirstOccurrence(Object o)
  {
    if (o == null) {
      return false;
    }
    this.lock.lock();
    try
    {
      for (Node<E> p = this.first; p != null; p = p.next) {
        if (o.equals(p.item))
        {
          unlink(p);
          return true;
        }
      }
      return 0;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public boolean removeLastOccurrence(Object o)
  {
    if (o == null) {
      return false;
    }
    this.lock.lock();
    try
    {
      for (Node<E> p = this.last; p != null; p = p.prev) {
        if (o.equals(p.item))
        {
          unlink(p);
          return true;
        }
      }
      return 0;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public boolean add(E e)
  {
    addLast(e);
    return true;
  }
  
  public boolean offer(E e)
  {
    return offerLast(e);
  }
  
  public void put(E e)
    throws InterruptedException
  {
    putLast(e);
  }
  
  public boolean offer(E e, long timeout, TimeUnit unit)
    throws InterruptedException
  {
    return offerLast(e, timeout, unit);
  }
  
  public E remove()
  {
    return (E)removeFirst();
  }
  
  public E poll()
  {
    return (E)pollFirst();
  }
  
  public E take()
    throws InterruptedException
  {
    return (E)takeFirst();
  }
  
  public E poll(long timeout, TimeUnit unit)
    throws InterruptedException
  {
    return (E)pollFirst(timeout, unit);
  }
  
  public E element()
  {
    return (E)getFirst();
  }
  
  public E peek()
  {
    return (E)peekFirst();
  }
  
  public int remainingCapacity()
  {
    this.lock.lock();
    try
    {
      return this.capacity - this.count;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public int drainTo(Collection<? super E> c)
  {
    return drainTo(c, Integer.MAX_VALUE);
  }
  
  public int drainTo(Collection<? super E> c, int maxElements)
  {
    if (c == null) {
      throw new NullPointerException();
    }
    if (c == this) {
      throw new IllegalArgumentException();
    }
    this.lock.lock();
    try
    {
      int n = Math.min(maxElements, this.count);
      for (int i = 0; i < n; i++)
      {
        c.add(this.first.item);
        unlinkFirst();
      }
      return n;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public void push(E e)
  {
    addFirst(e);
  }
  
  public E pop()
  {
    return (E)removeFirst();
  }
  
  public boolean remove(Object o)
  {
    return removeFirstOccurrence(o);
  }
  
  public int size()
  {
    this.lock.lock();
    try
    {
      return this.count;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public boolean contains(Object o)
  {
    if (o == null) {
      return false;
    }
    this.lock.lock();
    try
    {
      for (Node<E> p = this.first; p != null; p = p.next) {
        if (o.equals(p.item)) {
          return true;
        }
      }
      return 0;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public Object[] toArray()
  {
    this.lock.lock();
    try
    {
      Object[] a = new Object[this.count];
      int k = 0;
      for (Node<E> p = this.first; p != null; p = p.next) {
        a[(k++)] = p.item;
      }
      return a;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public <T> T[] toArray(T[] a)
  {
    this.lock.lock();
    try
    {
      if (a.length < this.count) {
        a = (Object[])Array.newInstance(a.getClass().getComponentType(), this.count);
      }
      int k = 0;
      for (Node<E> p = this.first; p != null; p = p.next) {
        a[(k++)] = p.item;
      }
      if (a.length > k) {
        a[k] = null;
      }
      return a;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public String toString()
  {
    this.lock.lock();
    try
    {
      return super.toString();
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public void clear()
  {
    this.lock.lock();
    try
    {
      for (Node<E> f = this.first; f != null;)
      {
        f.item = null;
        Node<E> n = f.next;
        f.prev = null;
        f.next = null;
        f = n;
      }
      this.first = (this.last = null);
      this.count = 0;
      this.notFull.signalAll();
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public Iterator<E> iterator()
  {
    return new Itr(null);
  }
  
  public Iterator<E> descendingIterator()
  {
    return new DescendingItr(null);
  }
  
  private abstract class AbstractItr
    implements Iterator<E>
  {
    LinkedBlockingDeque.Node<E> next;
    E nextItem;
    private LinkedBlockingDeque.Node<E> lastRet;
    
    abstract LinkedBlockingDeque.Node<E> firstNode();
    
    abstract LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> paramNode);
    
    AbstractItr()
    {
      LinkedBlockingDeque.this.lock.lock();
      try
      {
        this.next = firstNode();
        this.nextItem = (this.next == null ? null : this.next.item);
      }
      finally
      {
        LinkedBlockingDeque.this.lock.unlock();
      }
    }
    
    void advance()
    {
      LinkedBlockingDeque.this.lock.lock();
      try
      {
        LinkedBlockingDeque.Node<E> s = nextNode(this.next);
        if (s == this.next)
        {
          this.next = firstNode();
        }
        else
        {
          while ((s != null) && (s.item == null)) {
            s = nextNode(s);
          }
          this.next = s;
        }
        this.nextItem = (this.next == null ? null : this.next.item);
      }
      finally
      {
        LinkedBlockingDeque.this.lock.unlock();
      }
    }
    
    public boolean hasNext()
    {
      return this.next != null;
    }
    
    public E next()
    {
      if (this.next == null) {
        throw new NoSuchElementException();
      }
      this.lastRet = this.next;
      E x = this.nextItem;
      advance();
      return x;
    }
    
    public void remove()
    {
      LinkedBlockingDeque.Node<E> n = this.lastRet;
      if (n == null) {
        throw new IllegalStateException();
      }
      this.lastRet = null;
      LinkedBlockingDeque.this.lock.lock();
      try
      {
        if (n.item != null) {
          LinkedBlockingDeque.this.unlink(n);
        }
      }
      finally
      {
        LinkedBlockingDeque.this.lock.unlock();
      }
    }
  }
  
  private class Itr
    extends LinkedBlockingDeque.AbstractItr
  {
    private Itr()
    {
      super();
    }
    
    LinkedBlockingDeque.Node<E> firstNode()
    {
      return LinkedBlockingDeque.this.first;
    }
    
    LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> n)
    {
      return n.next;
    }
  }
  
  private class DescendingItr
    extends LinkedBlockingDeque.AbstractItr
  {
    private DescendingItr()
    {
      super();
    }
    
    LinkedBlockingDeque.Node<E> firstNode()
    {
      return LinkedBlockingDeque.this.last;
    }
    
    LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> n)
    {
      return n.prev;
    }
  }
  
  private void writeObject(ObjectOutputStream s)
    throws IOException
  {
    this.lock.lock();
    try
    {
      s.defaultWriteObject();
      for (Node<E> p = this.first; p != null; p = p.next) {
        s.writeObject(p.item);
      }
      s.writeObject(null);
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  private void readObject(ObjectInputStream s)
    throws IOException, ClassNotFoundException
  {
    s.defaultReadObject();
    this.count = 0;
    this.first = null;
    this.last = null;
    for (;;)
    {
      E item = s.readObject();
      if (item == null) {
        break;
      }
      add(item);
    }
  }
  
  public boolean hasTakeWaiters()
  {
    this.lock.lock();
    try
    {
      return this.lock.hasWaiters(this.notEmpty);
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public int getTakeQueueLength()
  {
    this.lock.lock();
    try
    {
      return this.lock.getWaitQueueLength(this.notEmpty);
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  public void interuptTakeWaiters()
  {
    this.lock.lock();
    try
    {
      this.lock.interruptWaiters(this.notEmpty);
    }
    finally
    {
      this.lock.unlock();
    }
  }
}

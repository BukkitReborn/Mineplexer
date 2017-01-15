package org.jooq.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jooq.FutureResult;
import org.jooq.Record;
import org.jooq.Result;

@Deprecated
class FutureResultImpl<R extends Record>
  implements FutureResult<R>
{
  private final Future<Result<R>> future;
  private final ExecutorService executor;
  
  FutureResultImpl(Future<Result<R>> future)
  {
    this(future, null);
  }
  
  FutureResultImpl(Future<Result<R>> future, ExecutorService executor)
  {
    this.future = future;
    this.executor = executor;
  }
  
  public boolean cancel(boolean mayInterruptIfRunning)
  {
    try
    {
      return this.future.cancel(mayInterruptIfRunning);
    }
    finally
    {
      if (this.executor != null) {
        this.executor.shutdownNow();
      }
    }
  }
  
  public boolean isCancelled()
  {
    return this.future.isCancelled();
  }
  
  public boolean isDone()
  {
    return this.future.isDone();
  }
  
  public Result<R> get()
    throws InterruptedException, ExecutionException
  {
    try
    {
      return (Result)this.future.get();
    }
    finally
    {
      if (this.executor != null) {
        this.executor.shutdownNow();
      }
    }
  }
  
  public Result<R> get(long timeout, TimeUnit unit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    try
    {
      return (Result)this.future.get(timeout, unit);
    }
    finally
    {
      if (this.executor != null) {
        this.executor.shutdownNow();
      }
    }
  }
}

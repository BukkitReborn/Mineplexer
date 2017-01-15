package org.jooq;

import java.util.concurrent.Future;

@Deprecated
public abstract interface FutureResult<R extends Record>
  extends Future<Result<R>>
{}

package redis.clients.jedis;

import redis.clients.jedis.exceptions.JedisDataException;

public class Response<T>
{
  protected T response = null;
  private boolean built = false;
  private boolean set = false;
  private Builder<T> builder;
  private Object data;
  private Response<?> dependency = null;
  private boolean requestDependencyBuild = false;
  
  public Response(Builder<T> b)
  {
    this.builder = b;
  }
  
  public void set(Object data)
  {
    this.data = data;
    this.set = true;
  }
  
  public T get()
  {
    if ((!this.requestDependencyBuild) && (this.dependency != null) && (this.dependency.set) && (!this.dependency.built))
    {
      this.requestDependencyBuild = true;
      this.dependency.build();
    }
    if (!this.set) {
      throw new JedisDataException("Please close pipeline or multi block before calling this method.");
    }
    if (!this.built) {
      build();
    }
    return (T)this.response;
  }
  
  public void setDependency(Response<?> dependency)
  {
    this.dependency = dependency;
    this.requestDependencyBuild = false;
  }
  
  private void build()
  {
    if (this.data != null)
    {
      if ((this.data instanceof JedisDataException)) {
        throw new JedisDataException((JedisDataException)this.data);
      }
      this.response = this.builder.build(this.data);
    }
    this.data = null;
    this.built = true;
  }
  
  public String toString()
  {
    return "Response " + this.builder.toString();
  }
}

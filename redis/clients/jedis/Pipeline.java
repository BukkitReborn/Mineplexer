package redis.clients.jedis;

import java.util.ArrayList;
import java.util.List;
import redis.clients.jedis.exceptions.JedisDataException;

public class Pipeline
  extends MultiKeyPipelineBase
{
  private MultiResponseBuilder currentMulti;
  
  private class MultiResponseBuilder
    extends Builder<List<Object>>
  {
    private List<Response<?>> responses = new ArrayList();
    
    private MultiResponseBuilder() {}
    
    public List<Object> build(Object data)
    {
      List<Object> list = (List)data;
      List<Object> values = new ArrayList();
      if (list.size() != this.responses.size()) {
        throw new JedisDataException("Expected data size " + this.responses.size() + " but was " + list.size());
      }
      for (int i = 0; i < list.size(); i++)
      {
        Response<?> response = (Response)this.responses.get(i);
        response.set(list.get(i));
        values.add(response.get());
      }
      return values;
    }
    
    public void setResponseDependency(Response<?> dependency)
    {
      for (Response<?> response : this.responses) {
        response.setDependency(dependency);
      }
    }
    
    public void addResponse(Response<?> response)
    {
      this.responses.add(response);
    }
  }
  
  protected <T> Response<T> getResponse(Builder<T> builder)
  {
    if (this.currentMulti != null)
    {
      super.getResponse(BuilderFactory.STRING);
      
      Response<T> lr = new Response(builder);
      this.currentMulti.addResponse(lr);
      return lr;
    }
    return super.getResponse(builder);
  }
  
  public void setClient(Client client)
  {
    this.client = client;
  }
  
  protected Client getClient(byte[] key)
  {
    return this.client;
  }
  
  protected Client getClient(String key)
  {
    return this.client;
  }
  
  public void sync()
  {
    List<Object> unformatted = this.client.getAll();
    for (Object o : unformatted) {
      generateResponse(o);
    }
  }
  
  public List<Object> syncAndReturnAll()
  {
    List<Object> unformatted = this.client.getAll();
    List<Object> formatted = new ArrayList();
    for (Object o : unformatted) {
      try
      {
        formatted.add(generateResponse(o).get());
      }
      catch (JedisDataException e)
      {
        formatted.add(e);
      }
    }
    return formatted;
  }
  
  public Response<String> discard()
  {
    this.client.discard();
    this.currentMulti = null;
    return getResponse(BuilderFactory.STRING);
  }
  
  public Response<List<Object>> exec()
  {
    this.client.exec();
    Response<List<Object>> response = super.getResponse(this.currentMulti);
    this.currentMulti.setResponseDependency(response);
    this.currentMulti = null;
    return response;
  }
  
  public Response<String> multi()
  {
    this.client.multi();
    Response<String> response = getResponse(BuilderFactory.STRING);
    
    this.currentMulti = new MultiResponseBuilder(null);
    return response;
  }
}

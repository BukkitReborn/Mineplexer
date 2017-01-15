package redis.clients.jedis;

import java.util.LinkedList;
import java.util.Queue;

public class Queable
{
  private Queue<Response<?>> pipelinedResponses = new LinkedList();
  
  protected void clean()
  {
    this.pipelinedResponses.clear();
  }
  
  protected Response<?> generateResponse(Object data)
  {
    Response<?> response = (Response)this.pipelinedResponses.poll();
    if (response != null) {
      response.set(data);
    }
    return response;
  }
  
  protected <T> Response<T> getResponse(Builder<T> builder)
  {
    Response<T> lr = new Response(builder);
    this.pipelinedResponses.add(lr);
    return lr;
  }
}

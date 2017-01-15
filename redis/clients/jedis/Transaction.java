package redis.clients.jedis;

import java.util.ArrayList;
import java.util.List;
import redis.clients.jedis.exceptions.JedisDataException;

public class Transaction
  extends MultiKeyPipelineBase
{
  protected boolean inTransaction = true;
  
  protected Transaction() {}
  
  public Transaction(Client client)
  {
    this.client = client;
  }
  
  protected Client getClient(String key)
  {
    return this.client;
  }
  
  protected Client getClient(byte[] key)
  {
    return this.client;
  }
  
  public List<Object> exec()
  {
    this.client.exec();
    this.client.getAll(1);
    
    List<Object> unformatted = this.client.getObjectMultiBulkReply();
    if (unformatted == null) {
      return null;
    }
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
  
  public List<Response<?>> execGetResponse()
  {
    this.client.exec();
    this.client.getAll(1);
    
    List<Object> unformatted = this.client.getObjectMultiBulkReply();
    if (unformatted == null) {
      return null;
    }
    List<Response<?>> response = new ArrayList();
    for (Object o : unformatted) {
      response.add(generateResponse(o));
    }
    return response;
  }
  
  public String discard()
  {
    this.client.discard();
    this.client.getAll(1);
    this.inTransaction = false;
    clean();
    return this.client.getStatusCodeReply();
  }
}

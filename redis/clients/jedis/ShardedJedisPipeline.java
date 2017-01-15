package redis.clients.jedis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShardedJedisPipeline
  extends PipelineBase
{
  private BinaryShardedJedis jedis;
  private List<FutureResult> results;
  private Queue<Client> clients;
  
  public ShardedJedisPipeline()
  {
    this.results = new ArrayList();
    this.clients = new LinkedList();
  }
  
  private static class FutureResult
  {
    private Client client;
    
    public FutureResult(Client client)
    {
      this.client = client;
    }
    
    public Object get()
    {
      return this.client.getOne();
    }
  }
  
  public void setShardedJedis(BinaryShardedJedis jedis)
  {
    this.jedis = jedis;
  }
  
  public List<Object> getResults()
  {
    List<Object> r = new ArrayList();
    for (FutureResult fr : this.results) {
      r.add(fr.get());
    }
    return r;
  }
  
  public void sync()
  {
    for (Client client : this.clients) {
      generateResponse(client.getOne());
    }
  }
  
  public List<Object> syncAndReturnAll()
  {
    List<Object> formatted = new ArrayList();
    for (Client client : this.clients) {
      formatted.add(generateResponse(client.getOne()).get());
    }
    return formatted;
  }
  
  @Deprecated
  public void execute() {}
  
  protected Client getClient(String key)
  {
    Client client = ((Jedis)this.jedis.getShard(key)).getClient();
    this.clients.add(client);
    this.results.add(new FutureResult(client));
    return client;
  }
  
  protected Client getClient(byte[] key)
  {
    Client client = ((Jedis)this.jedis.getShard(key)).getClient();
    this.clients.add(client);
    this.results.add(new FutureResult(client));
    return client;
  }
}

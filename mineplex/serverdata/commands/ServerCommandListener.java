package mineplex.serverdata.commands;

import redis.clients.jedis.JedisPubSub;

public class ServerCommandListener
  extends JedisPubSub
{
  public void onPMessage(String pattern, String channelName, String message)
  {
    try
    {
      String commandType = channelName.split(":")[1];
      ServerCommandManager.getInstance().handleCommand(commandType, message);
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
  
  public void onMessage(String channelName, String message) {}
  
  public void onPSubscribe(String pattern, int subscribedChannels) {}
  
  public void onPUnsubscribe(String pattern, int subscribedChannels) {}
  
  public void onSubscribe(String channelName, int subscribedChannels) {}
  
  public void onUnsubscribe(String channelName, int subscribedChannels) {}
}

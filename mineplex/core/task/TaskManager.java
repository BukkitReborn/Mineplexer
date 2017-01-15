package mineplex.core.task;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import mineplex.core.MiniDbClientPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.task.repository.TaskRepository;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class TaskManager
  extends MiniDbClientPlugin<TaskClient>
{
  private static Object _taskLock = new Object();
  private TaskRepository _repository;
  private NautHashMap<String, Integer> _tasks = new NautHashMap();
  
  public TaskManager(JavaPlugin plugin, CoreClientManager clientManager, String webServerAddress)
  {
    super("Task Manager", plugin, clientManager);
    
    this._repository = new TaskRepository(plugin);
    updateTasks();
  }
  
  private void updateTasks()
  {
    List<Task> tasks = this._repository.retrieveTasks();
    synchronized (_taskLock)
    {
      for (Task task : tasks) {
        this._tasks.put(task.Name, Integer.valueOf(task.Id));
      }
    }
  }
  
  protected TaskClient AddPlayer(String playerName)
  {
    return new TaskClient();
  }
  
  public void addTaskForOfflinePlayer(final Callback<Boolean> callback, final UUID uuid, final String task)
  {
    Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        synchronized (TaskManager._taskLock)
        {
          if (!TaskManager.this._tasks.containsKey(task))
          {
            TaskManager.this._repository.addTask(task);
            System.out.println("TaskManager Adding Task : " + task);
          }
        }
        TaskManager.this.updateTasks();
        synchronized (TaskManager._taskLock)
        {
          final boolean success = TaskManager.this._repository.addAccountTask(TaskManager.this.ClientManager.getCachedClientAccountId(uuid), ((Integer)TaskManager.this._tasks.get(task)).intValue());
          if (callback != null) {
            Bukkit.getServer().getScheduler().runTask(TaskManager.this.getPlugin(), new Runnable()
            {
              public void run()
              {
                this.val$callback.run(Boolean.valueOf(success));
              }
            });
          }
        }
      }
    });
  }
  
  public boolean hasCompletedTask(Player player, String taskName)
  {
    synchronized (_taskLock)
    {
      if (!this._tasks.containsKey(taskName)) {
        return false;
      }
      return ((TaskClient)Get(player.getName())).TasksCompleted.contains(this._tasks.get(taskName));
    }
  }
  
  public void completedTask(final Callback<Boolean> callback, final Player player, final String taskName)
  {
    synchronized (_taskLock)
    {
      if (this._tasks.containsKey(taskName)) {
        ((TaskClient)Get(player.getName())).TasksCompleted.add((Integer)this._tasks.get(taskName));
      }
    }
    addTaskForOfflinePlayer(new Callback()
    {
      public void run(Boolean success)
      {
        if (!success.booleanValue())
        {
          System.out.println("Add task FAILED for " + player.getName());
          synchronized (TaskManager._taskLock)
          {
            if (TaskManager.this._tasks.containsKey(taskName)) {
              ((TaskClient)TaskManager.this.Get(player.getName())).TasksCompleted.remove(TaskManager.this._tasks.get(taskName));
            }
          }
        }
        if (callback != null) {
          callback.run(success);
        }
      }
    }, player.getUniqueId(), taskName);
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    Set(playerName, this._repository.loadClientInformation(resultSet));
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return "SELECT taskId FROM accountTasks WHERE accountId = '" + accountId + "';";
  }
  
  /* Error */
  public Integer getTaskId(String taskName)
  {
    // Byte code:
    //   0: getstatic 21	mineplex/core/task/TaskManager:_taskLock	Ljava/lang/Object;
    //   3: dup
    //   4: astore_2
    //   5: monitorenter
    //   6: aload_0
    //   7: getfield 34	mineplex/core/task/TaskManager:_tasks	Lmineplex/core/common/util/NautHashMap;
    //   10: aload_1
    //   11: invokevirtual 162	mineplex/core/common/util/NautHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   14: checkcast 80	java/lang/Integer
    //   17: aload_2
    //   18: monitorexit
    //   19: areturn
    //   20: aload_2
    //   21: monitorexit
    //   22: athrow
    // Line number table:
    //   Java source line #150	-> byte code offset #0
    //   Java source line #152	-> byte code offset #6
    //   Java source line #150	-> byte code offset #20
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	23	0	this	TaskManager
    //   0	23	1	taskName	String
    //   4	17	2	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   6	19	20	finally
    //   20	22	20	finally
  }
}

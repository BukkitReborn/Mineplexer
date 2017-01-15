package mineplex.core.task.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mineplex.core.database.DBPool;
import mineplex.core.database.RepositoryBase;
import mineplex.core.database.ResultSetCallable;
import mineplex.core.database.column.Column;
import mineplex.core.database.column.ColumnInt;
import mineplex.core.database.column.ColumnVarChar;
import mineplex.core.task.Task;
import mineplex.core.task.TaskClient;
import org.bukkit.plugin.java.JavaPlugin;

public class TaskRepository
  extends RepositoryBase
{
  private static String CREATE_ACCOUNT_TASK_TABLE = "CREATE TABLE IF NOT EXISTS accountTasks (id INT NOT NULL, accountId INT, taskID INT);";
  private static String ADD_ACCOUNT_TASK = "INSERT INTO accountTasks (accountId, taskId) VALUES (?, ?);";
  private static String CREATE_TASK_TABLE = "CREATE TABLE IF NOT EXISTS tasks (id INT NOT NULL, name VARCHAR(100));";
  private static String ADD_TASK = "INSERT INTO tasks (name) VALUES (?);";
  private static String RETRIEVE_TASKS = "SELECT id, name FROM tasks;";
  
  public TaskRepository(JavaPlugin plugin)
  {
    super(plugin, DBPool.ACCOUNT);
  }
  
  protected void initialize()
  {
    executeUpdate(CREATE_ACCOUNT_TASK_TABLE, new Column[0]);
    executeUpdate(CREATE_TASK_TABLE, new Column[0]);
  }
  
  protected void update() {}
  
  public boolean addAccountTask(int accountId, int taskId)
  {
    return executeUpdate(ADD_ACCOUNT_TASK, new Column[] { new ColumnInt("accountId", accountId), new ColumnInt("taskId", taskId) }) > 0;
  }
  
  public TaskClient loadClientInformation(ResultSet resultSet)
    throws SQLException
  {
    TaskClient taskClient = new TaskClient();
    while (resultSet.next()) {
      taskClient.TasksCompleted.add(Integer.valueOf(resultSet.getInt(1)));
    }
    return taskClient;
  }
  
  public List<Task> retrieveTasks()
  {
    final List<Task> tasks = new ArrayList();
    
    executeQuery(RETRIEVE_TASKS, new ResultSetCallable()
    {
      public void processResultSet(ResultSet resultSet)
        throws SQLException
      {
        while (resultSet.next()) {
          tasks.add(new Task(resultSet.getInt(1), resultSet.getString(2)));
        }
      }
    }, new Column[0]);
    
    return tasks;
  }
  
  public void addTask(String task)
  {
    executeUpdate(ADD_TASK, new Column[] { new ColumnVarChar("name", 100, task) });
  }
}

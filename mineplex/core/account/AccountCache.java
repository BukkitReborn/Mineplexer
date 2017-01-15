package mineplex.core.account;

import java.util.UUID;
import mineplex.serverdata.data.Data;

public class AccountCache
  implements Data
{
  private UUID _uuid;
  private Integer _id;
  
  public AccountCache(UUID uuid, int id)
  {
    this._uuid = uuid;
    this._id = Integer.valueOf(id);
  }
  
  public UUID getUUID()
  {
    return this._uuid;
  }
  
  public int getId()
  {
    return this._id.intValue();
  }
  
  public String getDataId()
  {
    return this._uuid.toString();
  }
}

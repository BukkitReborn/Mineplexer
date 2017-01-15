package mineplex.serverdata;

import java.io.File;

public enum Region
{
  US,  EU,  ALL;
  
  public static Region currentRegion()
  {
    return !new File("eu.dat").exists() ? US : EU;
  }
}

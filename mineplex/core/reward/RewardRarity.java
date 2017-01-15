package mineplex.core.reward;

import mineplex.core.common.util.C;

public enum RewardRarity
{
  OTHER(
  
    "Other", C.cWhite),  COMMON("Common", C.cWhite),  UNCOMMON("Uncommon", C.cAqua),  RARE("Rare", C.cPurple),  LEGENDARY("Legendary", C.cGreen),  MYTHICAL("Mythical", C.cRed);
  
  private String _name;
  private String _color;
  
  private RewardRarity(String name, String color)
  {
    this._name = name;
    this._color = color;
  }
  
  public String getColor()
  {
    return this._color;
  }
  
  public String getName()
  {
    return this._name;
  }
}

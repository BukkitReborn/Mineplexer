package mineplex.core.treasure.animation;

import mineplex.core.treasure.Treasure;

public abstract class Animation
{
  private Treasure _treasure;
  private boolean _running;
  private int _ticks;
  
  public Animation(Treasure treasure)
  {
    this._treasure = treasure;
    this._running = true;
  }
  
  public void run()
  {
    tick();
    this._ticks += 1;
  }
  
  protected abstract void tick();
  
  protected abstract void onFinish();
  
  public void finish()
  {
    if (this._running)
    {
      this._running = false;
      onFinish();
    }
  }
  
  public boolean isRunning()
  {
    return this._running;
  }
  
  public int getTicks()
  {
    return this._ticks;
  }
  
  public Treasure getTreasure()
  {
    return this._treasure;
  }
}

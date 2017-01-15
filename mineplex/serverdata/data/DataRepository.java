package mineplex.serverdata.data;

import java.util.Collection;

public abstract interface DataRepository<T extends Data>
{
  public abstract Collection<T> getElements();
  
  public abstract T getElement(String paramString);
  
  public abstract Collection<T> getElements(Collection<String> paramCollection);
  
  public abstract void addElement(T paramT, int paramInt);
  
  public abstract void addElement(T paramT);
  
  public abstract void removeElement(T paramT);
  
  public abstract void removeElement(String paramString);
  
  public abstract boolean elementExists(String paramString);
  
  public abstract int clean();
}

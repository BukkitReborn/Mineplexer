package mineplex.core.elo;

public class EloRatingSystem
{
  private static final int DEFAULT_KFACTOR = 25;
  public static final double WIN = 1.0D;
  public static final double DRAW = 0.5D;
  public static final double LOSS = 0.0D;
  private KFactor[] _kFactors = new KFactor[0];
  
  public EloRatingSystem(KFactor... kFactors)
  {
    this._kFactors = kFactors;
  }
  
  public int getNewRating(int rating, int opponentRating, GameResult result)
  {
    switch (result)
    {
    case Draw: 
      return getNewRating(rating, opponentRating, 1.0D);
    case Loss: 
      return getNewRating(rating, opponentRating, 0.0D);
    case Win: 
      return getNewRating(rating, opponentRating, 0.5D);
    }
    return -1;
  }
  
  public int getNewRating(int rating, int opponentRating, double score)
  {
    double kFactor = getKFactor(rating);
    double expectedScore = getExpectedScore(rating, opponentRating);
    int newRating = calculateNewRating(rating, score, expectedScore, kFactor);
    
    return newRating;
  }
  
  private int calculateNewRating(int oldRating, double score, double expectedScore, double kFactor)
  {
    return oldRating + (int)(kFactor * (score - expectedScore));
  }
  
  private double getKFactor(int rating)
  {
    for (int i = 0; i < this._kFactors.length; i++) {
      if ((rating >= this._kFactors[i].getStartIndex()) && (rating <= this._kFactors[i].getEndIndex())) {
        return this._kFactors[i].value;
      }
    }
    return 25.0D;
  }
  
  private double getExpectedScore(int rating, int opponentRating)
  {
    return 1.0D / (1.0D + Math.pow(10.0D, (opponentRating - rating) / 400.0D));
  }
}

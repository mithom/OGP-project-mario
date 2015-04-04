package jumpingalien.exception;

public class PositionOutOfBoundsException extends Exception {
	  static final long serialVersionUID = -3387516993124229948L;
	  public final static int minX =0;
	  public final static int minY =0;
	  public final static int maxX =1023;
	  public final static int maxY =768;
	  private final double x;
	  private final double y;
	  
	  public PositionOutOfBoundsException(double x, double y){
		  this.x = x;
		  this.y = y;
	  }
	  
	  public double[] getLocation(){
		  return new double[]{x,y};
	  }
}

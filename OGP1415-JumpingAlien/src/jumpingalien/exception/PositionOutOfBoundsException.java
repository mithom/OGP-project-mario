package jumpingalien.exception;

import jumpingalien.model.World;

public class PositionOutOfBoundsException extends Exception {
	  static final long serialVersionUID = -3387516993124229948L;
	  public final static int minX =0;
	  public final static int minY =0;
	  public final int maxX ;
	  public final int maxY ;
	  private final double x;
	  private final double y;
	  
	  public PositionOutOfBoundsException(double x, double y, World world){
		  this.x = x;
		  this.y = y;
		  this.maxX = world.getWidth();
		  this.maxY = world.getHeight();
	  }
	  
	  public double[] getLocation(){
		  return new double[]{x,y};
	  }
}

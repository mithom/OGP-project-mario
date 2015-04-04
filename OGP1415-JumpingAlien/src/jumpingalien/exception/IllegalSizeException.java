package jumpingalien.exception;

public class IllegalSizeException extends Exception{
	  private final double width;
	  private final double height;
	  static final long serialVersionUID = -3387516993124229948L;
	  
	  public IllegalSizeException(double width, double height){
		  this.width = width;
		  this.height = height;
	  }
	  
	  public double[] getSize(){
		  return new double[]{width,height};
	  }
}

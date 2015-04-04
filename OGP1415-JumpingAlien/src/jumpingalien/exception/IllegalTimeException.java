package jumpingalien.exception;

import be.kuleuven.cs.som.annotate.*;

public class IllegalTimeException extends IllegalArgumentException{
	  private final double dt;
	  
	  public IllegalTimeException(double dt){
		  this.dt = dt;
	  }
	  private static final long serialVersionUID = -5365630128856068164L;
	  
	  @Basic @Immutable
	  public double getTime(){
		  return this.dt;
	  }
}

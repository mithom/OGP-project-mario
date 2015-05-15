package expressions;

import jumpingalien.part3.programs.SourceLocation;

public abstract class Comparison extends Expression {
	public Object left;
	public Object right;
	
	public Comparison(Object left, Object right, SourceLocation sourceLocation){
		super(sourceLocation);
		this.left = left ;
		this.right = right ;
	}
	
	public double getLeftValue(){
		return (double)left;
	}
	
	public double getRightValue(){
		return (double)right;
	}

}

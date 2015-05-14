package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Comparison extends Expression {
	public DoubleConstant left;
	public DoubleConstant right;
	
	public Comparison(DoubleConstant left, DoubleConstant right, SourceLocation sourceLocation){
		super(sourceLocation);
		this.left = left ;
		this.right = right ;
	}
	
	public DoubleConstant getLeftValue(){
		return left;
	}
	
	public DoubleConstant getRightValue(){
		return right;
	}

}

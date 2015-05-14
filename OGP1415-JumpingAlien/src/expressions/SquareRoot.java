package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class SquareRoot extends Expression {
	
	public DoubleConstant value;
	
	public SquareRoot(DoubleConstant value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.value = value;
	}

	public DoubleConstant getValue(){
		return value;
	}
	
	public double evaluateSquareRoot(){
		return Math.sqrt(value.getValue());
	}
}

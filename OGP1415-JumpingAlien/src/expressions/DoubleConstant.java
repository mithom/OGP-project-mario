package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class DoubleConstant extends Expression {

	public double value;
	
	public DoubleConstant(SourceLocation sourceLocation, double value){
		super(sourceLocation);
		this.value=value;
	}
	
	public double getValue(){
		return value;
	}
	
}

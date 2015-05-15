package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class BooleanExpression extends Expression {
	
	public boolean value;
	
	public BooleanExpression(SourceLocation sourceLocation, boolean value){
		super(sourceLocation);
		this.value=value;
	}
	
	public boolean getValue(){
		return value;
	}
	
	@Override
	public Object evaluate() {
		return value;
	}
	
}

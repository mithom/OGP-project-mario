package expressions;

import jumpingalien.part3.programs.SourceLocation;

public class NullExpression extends Expression {
	
	public NullExpression(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public NullExpression getValue(){
		return null;
	}
}

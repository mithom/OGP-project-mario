package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class SquareRoot extends Expression {
	
	public Object value;
	
	public SquareRoot(Object value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.value = value;
	}

	public Object getValue(){
		return value;
	}

	@Override
	public Object evaluate()  {
		return Math.sqrt(((DoubleConstant)getValue()).getValue());
	}
}

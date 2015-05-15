package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.Shark;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsShark extends Expression {

	public Object object;
	
	public IsShark(Object object, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object = object;
	}
	
	@Override
	public Object evaluate() {
		return (((GameObject)object) instanceof Shark);
	}

}

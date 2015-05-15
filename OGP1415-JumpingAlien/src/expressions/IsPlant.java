package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.Plant;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsPlant extends Expression {

	public Object object;
	
	public IsPlant(Object object, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object = object;
	}
	
	@Override
	public Object evaluate() {
		return (((GameObject)object) instanceof Plant);
	}

}

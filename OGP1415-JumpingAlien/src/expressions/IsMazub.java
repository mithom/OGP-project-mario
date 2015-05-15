package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.Mazub;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsMazub extends Expression {

	public Object object;
	
	public IsMazub(Object object, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object = object;
	}
	
	@Override
	public Object evaluate() {
		return (((GameObject)object) instanceof Mazub);
	}

}

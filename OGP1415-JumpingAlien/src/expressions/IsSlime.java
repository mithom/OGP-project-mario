package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.Slime;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsSlime extends Expression {

	public Object object;
	
	public IsSlime(Object object, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object = object;
	}
	
	@Override
	public Object evaluate() {
		return (((GameObject)object) instanceof Slime);
	}

}

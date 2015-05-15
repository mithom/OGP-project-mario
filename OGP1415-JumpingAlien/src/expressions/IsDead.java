package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsDead extends Expression {

	public Object object;
	
	public IsDead(Object object, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object = object;
	}
	
	@Override
	public Object evaluate() {
		return (((GameObject)object).isDead());
	}
}

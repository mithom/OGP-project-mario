package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetX extends Expression {
	
	public Object object;
	
	public GetX(Object object, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.object= object;
	}
	
	public Object getObject(){
		return object;
	}
	
	@Override
	public Object evaluate() {
		return ((GameObject) getObject()).getPositionX();
	}

}

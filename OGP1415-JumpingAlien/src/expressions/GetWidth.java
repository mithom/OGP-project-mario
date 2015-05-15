package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetWidth extends Expression {
	
	public Object object;
	
	public GetWidth(Object object, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.object= object;
	}
	
	public Object getObject(){
		return object;
	}
	
	@Override
	public Object evaluate() throws NullPointerException, IllegalSizeException {
		return ((GameObject)getObject()).getSize()[0];
	}

}

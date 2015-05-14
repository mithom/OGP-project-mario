package expressions;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class GetHeight extends Expression {
	
	public Object object;
	
	public GetHeight(GameObject object, SourceLocation sourceLocation ){
		super(sourceLocation);
		this.object= object;
	}
	
	public Object getObject(){
		return object;
	}
	
	public double evaluateGetHeight() throws NullPointerException, IllegalSizeException{
		return ((GameObject)getObject()).getSize()[1];
	}
}

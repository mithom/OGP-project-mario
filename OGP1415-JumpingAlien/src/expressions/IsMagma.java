package expressions;

import java.security.InvalidKeyException;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.model.gameObject.GeologicalFeature;
import jumpingalien.part3.programs.SourceLocation;

public class IsMagma extends Expression {

	public Object object;

	public IsMagma(Object object, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.object = object;
	}

	@Override
	public Object evaluate()  {
		return  object == GeologicalFeature.magma;
	}

}

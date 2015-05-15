package expressions;

import java.util.Random;

import jumpingalien.exception.IllegalSizeException;
import jumpingalien.part3.programs.SourceLocation;

public class RandomDouble extends Expression {

	public double maxValue;
	
	public RandomDouble(Object max, SourceLocation sourceLocation){
		super(sourceLocation);
		this.maxValue = ((DoubleConstant)max).getValue();
	}

	@Override
	public Object evaluate() {
		Random rand = new Random();
		return (rand.nextDouble()*maxValue);
	}
}

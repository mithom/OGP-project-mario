package expressions;

import java.util.Random;

import jumpingalien.part3.programs.SourceLocation;

public class RandomDouble extends Expression {

	public double maxValue;
	
	public RandomDouble(DoubleConstant max, SourceLocation sourceLocation){
		super(sourceLocation);
		this.maxValue = max.getValue();
	}
	
	public double getRandomDouble(){
		Random rand = new Random();
		return (rand.nextDouble()*maxValue);
	}
}

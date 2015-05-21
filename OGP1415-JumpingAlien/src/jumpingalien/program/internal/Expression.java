package jumpingalien.program.internal;

import jumpingalien.model.Program;
import jumpingalien.program.util.ActionFor1;
import jumpingalien.program.util.ActionFor2;

//<R> is the returnType of the expression
//G the given value type
public class Expression<R,G extends Value<?>> extends Value<R> {
	private final Object[] expressions;
	private Object action;
	private Value<R> lastState; 
	
	//used for variable reading (and constants->straight at value)//TODO: this function has to be done otherwise (check program factory)
	public Expression(Value<R> value){
		super();
		//this.value = value;
		this.expressions = new Object[]{value};
		action=null;
	}
	
	public Expression(G value, ActionFor1 action){
		super();
		this.expressions = new Object[]{value};
		this.action = action;
	}
	
	//used for calculations, comparisons and logical statements
	public Expression(G left,G right,ActionFor2 action){
		super();
		this.expressions = new Object[]{left,right};
		this.action = action;
	}
	
	@Override
	public void addProgram(Program program){
		for(Object expressionObject:expressions)
			if(expressionObject != null)
				((Value<?>)expressionObject).addProgram(program);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	//TODO: only works if correct program, still need to catch errors
	public R evaluate(double[] dt){//TODO: aanpassen voor previousStatement en dt!
		if(isDone())
			return lastState.evaluate(dt);
		else{
			if(expressions.length==1){
				if(action == null){
					System.out.println("error want geen action?");
					lastState = ((Value<R>)((G)expressions[0])).Copy();
					if(lastState!=null)
						setDoneTrue(dt);
					return lastState.evaluate(dt);
				}else{
					lastState=((Value<R>)((ActionFor1)action).evaluate((G)expressions[0], dt)).Copy();
					if(lastState!=null)
						setDoneTrue(dt);
					return lastState.evaluate(dt);
				}
			}else{
				lastState=((Value<R>)((ActionFor2)action).evaluate((G)expressions[0],(Value<?>)expressions[1], dt)).Copy();
				if(lastState!=null)
					setDoneTrue(dt);
				return lastState.evaluate(dt);
			}
		}
	}
	
	@Override
	public void reset(){
		setDoneFalse();
		for(Object expressionObject:expressions){
			Value<?> expression= (Value<?>)expressionObject;
			expression.setDoneFalse();
		}
	}
	
	
	@Override
	protected Expression<R,G> Copy(){
		/*
		Expression<R, G> copy = (Expression<R,G>)((Value<R>)this).Copy();
		copy.expressions[0]= expressions[0];
		if(action instanceof ActionFor2){
			copy.expressions[1] = expressions[1];
		}
		copy.action = action;
		copy.lastState = lastState.Copy();
		return copy;*/
		System.out.println("zou nooit zijn mogen gebeuren, .Copy() in expression");
		return this;
	}
}

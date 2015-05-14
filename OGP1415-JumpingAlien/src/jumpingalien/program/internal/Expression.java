package jumpingalien.program.internal;

import jumpingalien.program.util.ActionFor1;
import jumpingalien.program.util.ActionFor2;

//<R> is the returnType of the expression
//G the given value type
public class Expression<R,G extends Value<?>> extends Value<R> {
	private final Object[] expressions;
	private final Object action;
	
	//used for variable reading (and constants->straight at value)//TODO: this function has to be done otherwise (check program factory)
	public Expression(Value<R> value){
		super(value.evaluate());
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
	
	@SuppressWarnings("unchecked")
	@Override
	//TODO: only works if correct program, still need to catch errors
	public R evaluate(){//TODO: aanpassen voor previousStatement en dt!
		if(expressions.length==1){
			if(action == null){
				return (R)((G)expressions[0]).evaluate();
			}else{
				return (R)((ActionFor1)action).evaluate((G)expressions[0]).evaluate();
			}
		}else{
			return (R)((ActionFor2)action).evaluate((G)expressions[0],(Value<?>)expressions[1]).evaluate();
		}
	}

}

package jumpingalien.program.internal;

import jumpingalien.model.Program;

public class Value<R> {
	private Program program;
	private boolean done;
	private R value;
	/*private Statement previousStatement;
	private Value<?> previousExpression;
	private enum next{
		STATEMENT,EXPRESSION;
	}
	private next firstNext;
	private final Statement[] nextStatement = new Statement[2];
	private final Value<?>[] nextExpression = new Expression[2];
	*/
	
	public Value(R value){
		done=false;
		this.value = value;
	}
	
	public Value(){
		done=false;
	};
	
	public R evaluate(double[] dt){//TODO: aanpassen voor previousStatement en dt!
		if(!isDone()){
			setDoneTrue(dt);
		}
			/*System.out.println("tijd eraf");
		else
			System.out.println("geen tijd eraf");*/
		return value;
	}
	
	public void reset(){
		done = false;
		//System.out.println("value resetted");
		/*if(previousStatement != null){
			previousStatement.reset();
			previousStatement=null;
		}else{
			if(previousExpression != null){
				previousExpression.reset();
				previousExpression = null;
			}else
				program.resetGlobals();
		}*/
	}
	
	/*
	public final void addPreviousStatement(Statement statement){//TODO: check if you're next statement from prev one.
		previousStatement = statement;
	}
	
	public void addNextStatement(Statement statement){//TODO: add checkers
		if(firstNext==null){
			firstNext = next.STATEMENT;
			nextStatement[0]= statement;
		}else{
			if(firstNext == next.STATEMENT)
				nextStatement[1] = statement;
			else
				nextStatement[0] = statement;
		}
	}
	
	public void addNextExpression(Value<?> expression){
		if(firstNext==null){
			firstNext = next.EXPRESSION;
			nextExpression[0] = expression;
		}else{
			if(firstNext== next.EXPRESSION){
				nextExpression[1]= expression;
			}else
				nextExpression[0] = expression;
				
		}
	}*/
	
	public void addProgram(Program program){
		this.program = program;
	}
	
	public Program getProgram(){
		return program;
	}
	
	protected Value<R> Copy(){
		Value<R> copy = new Value<R>(value);
		copy.addProgram(getProgram());
		copy.done = done;
		/*copy.previousStatement = previousStatement;
		copy.previousExpression = previousExpression;
		copy.firstNext =firstNext;
		copy.nextStatement[0] = nextStatement[0];copy.nextStatement[1]=nextStatement[1];
		copy.nextExpression[0]= nextExpression[0];copy.nextExpression[1] = nextExpression[1];
		*/
		return copy;
	}
	
	public void setDoneTrue(double[] dt){
		dt[0]-= 0.001d;
		done = true;
	}
	
	protected void setDoneFalse(){
		done=false;
	}
	
	public boolean isDone(){
		return done;
	}
}

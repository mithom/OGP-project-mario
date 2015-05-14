package jumpingalien.program.internal;

import jumpingalien.model.Program;

public class Value<R> {
	private Program program;
	private boolean done;
	private R value;
	private Statement previousStatement;
	private Value<?> previousExpression;
	private enum next{
		STATEMENT,EXPRESSION;
	}
	private next firstNext;
	private final Statement[] nextStatement = new Statement[2];
	private final Value<?>[] nextExpression = new Expression[2];
	
	
	public Value(R value){
		this.value = value;
	}
	
	public Value(){};
	
	public R evaluate(){//TODO: aanpassen voor previousStatement en dt!
		return value;
	}
	
	final void reset(){
		done = false;
		if(previousStatement != null){
			previousStatement.reset();
			previousStatement=null;
		}else{
			if(previousExpression != null){
				previousExpression.reset();
				previousExpression = null;
			}else
				program.resetGlobals();
		}	
	}
	
	private final void addPreviousStatement(Statement statement){
		previousStatement = statement;
	}
	
	public boolean isDone(){
		return done;
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
	}
}

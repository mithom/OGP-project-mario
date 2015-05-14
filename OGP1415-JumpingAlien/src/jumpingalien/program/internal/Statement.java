package jumpingalien.program.internal;

import jumpingalien.model.Program;

public class Statement {
	private Program program;
	private boolean done;
	private final Statement[] nextStatement = new Statement[2];
	private final Value<?>[] nextExpression = new Expression[2];
	private Statement previousStatement;
	private Value<?> previousExpression;
	private enum next{
		STATEMENT,EXPRESSION;
	}
	private next firstNext;
	
	public void addProgram(Program program){
		this.program = program;
	}
	
	public Program getProgram(){
		done = false;
		return program;
	}
	
	public Statement(){
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
	
	public boolean isDone(){
		return done;
	}
	
	public double executeNext(double dt){//TODO only if action is if,foreach or while, 2 next statements, 2cnd used if:end of while, after last of kind, and else clause.
		if(dt<=0)
			return 0;
		if(!isDone()){
			execute();
			dt-= 0.001;
		}
		if(nextStatement != null){
			nextStatement[0].addPreviousStatement(this);
			return nextStatement[0].executeNext(dt);
		}
		reset();
		return dt;
	}
	
	private void addPreviousStatement(Statement statement){
		previousStatement = statement;
	}
	
	private void execute(){
		//TODO: implement this function
	}
	
	void reset(){
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
}

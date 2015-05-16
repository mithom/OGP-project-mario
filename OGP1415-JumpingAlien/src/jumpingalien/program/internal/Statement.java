package jumpingalien.program.internal;

import jumpingalien.model.Program;
import jumpingalien.program.statement.util.Action;
import jumpingalien.program.statement.util.Category;
import jumpingalien.program.statement.util.Kind;

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
	
	private Category category;
	private Action action;
	private Kind kind;
	
	public void addConditiond(Value<?> expression){
		if(getCategory()==Category.WHILE || getCategory()==Category.FOREACH || getCategory()==Category.IF){
			addNextExpression(expression);
		}
	}
	
	public void addProgram(Program program){
		this.program = program;
	}
	
	
	public Program getProgram(){
		done = false;
		return program;
	}
	
	public Statement(Category category){
		this.setCategory(category);
	}
	
	public Statement(Action action){//when category is Action
		this.setCategory(Category.ACTION);
		this.setAction(action);
	}
	
	public Statement(Kind kind){//when foreach
		this.setKind(kind);
		this.setCategory(Category.FOREACH);
	}
	
	public Statement(){}//TODO: remove when ready
	
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
			execute(dt);
			dt-= 0.001;
		}
		if(nextStatement != null){
			nextStatement[0].addPreviousStatement(this);
			return nextStatement[0].executeNext(dt);
		}
		reset();
		return dt;
	}
	
	public void addPreviousStatement(Statement statement){
		previousStatement = statement;
	}
	
	public void addPreviousExpression(Value<?> expression){
		previousExpression = expression;
	}
	
	private void execute(double dt){
		if(getCategory()==Category.ACTION){
			//getAction.execute();
			
		}else{
			if(getCategory()==Category.WHILE){
				getCategory().execute(this, dt);
			}
		}
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


	public Category getCategory() {
		return category;
	}


	private void setCategory(Category category) {
		this.category = category;
	}


	public Action getAction() {
		return action;
	}


	private void setAction(Action action) {
		this.action = action;
	}


	public Kind getKind() {
		return kind;
	}


	private void setKind(Kind kind) {
		this.kind = kind;
	}
}

package jumpingalien.program.internal;

import jumpingalien.model.Program;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;
import jumpingalien.program.statement.util.Action;
import jumpingalien.program.statement.util.Category;
import jumpingalien.program.statement.util.Kind;

public class Statement {
	private Program program;
	private boolean done;
	private final Statement[] nextStatements = new Statement[3];
	private final Value<?>[] expressions = new Value<?>[3]; 
	//private final Value<?>[] nextExpression = new Expression[2];
	private Statement previousStatement;
	//private Value<?> previousExpression;
	//private enum next{
	//	STATEMENT,EXPRESSION;
	//}
	//private next firstNext;
	
	private Category category;
	private Action action;
	private Kind kind;
	private Type type;
	/*private SortCriteria sortCriteria;
	private ValidCriteria validCriteria;*/
	private boolean sortAscending = true;//false is sorting descending
	private boolean resetOnEnd = true;

	/*interface SortCriteria {
        Double calculateValue(Value<Double> expression);   
    }
	
	interface ValidCriteria{
		boolean isValidGameObject(Value<Boolean> expression);
	}*/
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setDoneTrue(){
		if(done==true)
			System.out.println("done was al true");
		done=true;
	}

	public void addConditiond(Value<?> expression){
		if(getCategory()==Category.WHILE || getCategory()==Category.FOREACH || getCategory()==Category.IF || getCategory() == Category.ASSIGNMENT){
			if(expressions[0]==null)
				expressions[0] = expression;
			else{
				if(getCategory()==Category.FOREACH|| getCategory() == Category.ASSIGNMENT){
					if(expressions[1]==null)
						expressions[1]=expression;
					else{
						if(expressions[2]==null && getCategory()==Category.FOREACH)
							expressions[2] = expression;
						else
							System.out.println("error, binnenste addCond");
					}
				}else{
					System.out.println("error 2de if addCond");
					System.out.println(category);
				}
			}
		}else
			System.out.println("euhm, error in buitenste idd addCondition");
	}
	
	public void addProgram(Program program){
		if(this.program == null){
			this.program = program;
			for(Statement statement:nextStatements)
				if(statement!=null){
					statement.addProgram(program);
				}
			for(Value<?> expression:expressions){
				if(expression != null)
					expression.addProgram(program);
			}
		}else{
			//System.out.println(this.program);
			System.out.println("oops had al een program");
			//System.out.println(category +","+ expressions[0].evaluate(new double[]{100}));
		}		
	}
	
	
	public Program getProgram(){
		done = false;
		return program;
	}
	
	public Statement(Category category){//TODO add checkers (niet null, acion of foreach)
		this.setCategory(category);
	}
	
	public Statement(Action action){//when category is Action
		this.setCategory(Category.ACTION);
		this.setAction(action);
	}
	
	/*public Statement(Kind kind,SortCriteria sortCriteria,ValidCriteria validCriteria){//when foreach
		setKind(kind);
		setCategory(Category.FOREACH);
		setSortCriteria(sortCriteria);
		setValidCriteria(validCriteria);
	}*/
	
	public Statement(Kind kind){
		setKind(kind);
		setCategory(Category.FOREACH);
	}
	
	public void sortAscending(){
		sortAscending=true;
	}
	
	public void sortDescending(){
		sortAscending=false;
	}
	
	public boolean getSortAscending(){
		return sortAscending;
	}
	
	public void addNextStatement(Statement statement){//TODO: add checkers
		/*if(firstNext==null){
			firstNext = next.STATEMENT;
			nextStatement[0]= statement;
		}else{
			if(firstNext == next.STATEMENT)
				nextStatement[1] = statement;
			else
				nextStatement[0] = statement;
		}*/
		if(nextStatements[0]==null){
			nextStatements[0] = statement;
		}else{
			if(nextStatements[1]==null){
				nextStatements[1] = statement;
			}else{
				if(nextStatements[2]==null){
					nextStatements[2] = statement;
				}else
					System.out.println("error, zou niet mogen voorkomen");
			}
		}
	}
	/*
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
	
	public boolean isDone(){
		return done;
	}
	
	public void executeNext(double[] dt){//TODO only if action is if,foreach or while, 2 next statements, 2cnd used if:end of while, after last of kind, and else clause.
		if(dt[0]<=0)
			return;
		if(!isDone()){
			execute(dt);
		}
		int nextNb;
		if(getCategory()==Category.WHILE || getCategory()==Category.FOREACH)
			nextNb=1;
		else{
			if(getCategory()==Category.IF)
				nextNb = 2;
			else
				nextNb=0;
		}
		if(nextStatements[nextNb] != null){
			nextStatements[nextNb].addPreviousStatement(this);
			nextStatements[nextNb].executeNext(dt);
			return;
		}
		if(resetOnEnd)
			reset(dt);
		//return dt;
	}
	
	public void noReset(){
		resetOnEnd = false;
	}
	
	public void addPreviousStatement(Statement statement){
		previousStatement = statement;
	}
	
	/*public void addPreviousExpression(Value<?> expression){
		previousExpression = expression;
	}*/
	
	private void execute(double[] dt){
		//System.out.println(dt[0]);
		//System.out.println(category);
		category.execute(this, dt);
	}
	
	void reset(double[] dt){
		//System.out.println("resetting");
		if(isDone()){
			done = false;
			for(Value<?> expression:expressions)
				if(expression != null)
					expression.reset();
			if(previousStatement != null){
				previousStatement.reset(dt);
				previousStatement=null;
			}else{
					program.resetGlobals();
			}
		}else{
			if((getCategory()==Category.WHILE || getCategory()==Category.FOREACH || getCategory()==Category.IF)){
				//System.out.println("opnieuw de while in");
				for(Value<?> expression:expressions)
					if(expression != null)
						expression.reset();
			}else
			{
				System.out.println("had nooit mogen gebeuren in reset.");
				System.out.println(category.toString());
				if(category==Category.ACTION)
					System.out.println(action.toString());
				if(category==Category.FOREACH)
					System.out.println(kind.toString());
			}
			//else: gaat verder in while/foreach/if lus (zou anders al op isDone hebben gestaan)
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
	
	public Value<?>[] getExpressions() {//TODO: check if stil works
		return expressions.clone();
	}
	
	public Statement[] getNextStatements(){
		return nextStatements.clone();
	}
	
	public Statement getPreviousStatement(){
		return previousStatement;
	}
	/*
	public SortCriteria getSortCriteria() {
		return sortCriteria;
	}

	public void setSortCriteria(SortCriteria sortCriteria) {
		this.sortCriteria = sortCriteria;
	}

	public ValidCriteria getValidCriteria() {
		return validCriteria;
	}

	public void setValidCriteria(ValidCriteria validCriteria) {
		this.validCriteria = validCriteria;
	}*/

}

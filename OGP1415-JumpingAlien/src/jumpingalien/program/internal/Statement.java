package jumpingalien.program.internal;

import java.util.Iterator;

import jumpingalien.model.Buzam;
import jumpingalien.model.Program;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.statement.util.Action;
import jumpingalien.program.statement.util.Category;
import jumpingalien.program.statement.util.Kind;

public class Statement {
	private Program program;
	private boolean done;
	private final Statement[] nextStatements = new Statement[3];
	private final Value<?>[] expressions = new Value<?>[3]; 
	private Statement previousStatement;
	
	private Category category;
	private Action action;
	private Kind kind;
	private Type type;
	private boolean sortAscending = true;//false is sorting descending
	private boolean resetOnEnd = true;
	private Double timeToWait;
	private Iterator<? extends GameObject> iterObjects;
	private boolean noBreak = true;
	//private boolean hardResetted=false;
	
	//public boolean isHardResetted(){
		//return hardResetted;
	//}
	
	public Type getType() {
		return type;
	}
	
	public void setIterObjects(Iterator<? extends GameObject> iterator){
		iterObjects = iterator;
	}
	
	public Iterator<? extends GameObject> getIterObjects(){
		return iterObjects;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setDoneTrue(){
		if(done==true)
			System.out.println("done was al true: "+category);
		done=true;
	}

	public void addConditiond(Value<?> expression){
		if(getCategory()==Category.WHILE || getCategory()==Category.FOREACH || getCategory()==Category.IF || getCategory() == Category.ASSIGNMENT || getCategory() == Category.ACTION || getCategory() == Category.PRINT){
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
			System.out.println("euhm, error in buitenste if addCondition");
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
			System.out.println("oops had al een program");
		}		
	}
	
	
	public Program getProgram(){
		//done = false;
		return program;
	}
	
	public Statement(Category category){
		if (isValidCategory(category)){
			this.setCategory(category);
		}
	}
	
	public Statement(Action action){//when category is Action
		this.setCategory(Category.ACTION);
		this.setAction(action);
	}
	
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
	
	public void addNextStatement(Statement statement){
		if (statement!=null){
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
	}
	
	public boolean isDone(){
		return done;
	}
	
	public boolean executeNext(double[] dt){
		if(dt[0]<=0.0)
			return false;
		if(!isDone()){
			execute(dt);
		}
		if(!getProgram().hasError()){
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
				return nextStatements[nextNb].executeNext(dt);
			}
			//if(!noBreak)
				//setDoneTrue();
			if(resetOnEnd && isDone()){
				reset();
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void noReset(){
		resetOnEnd = false;
	}
	
	public void addPreviousStatement(Statement statement){
		previousStatement = statement;
	}
	
	private void execute(double[] dt){
		try{
			category.execute(this,dt);
		}catch(Exception e){
			getProgram().setError();
		}
	}
	
	void reset(){
		if(isDone()){
			done = false;
			timeToWait=null;
			iterObjects = null;
			noBreak = true;
			for(Value<?> expression:expressions)
				if(expression != null)
					expression.reset();
			if(previousStatement != null){
				previousStatement.reset();
				previousStatement=null;
			}else{
					program.resetGlobals();
			}
		}else{
			if((getCategory()==Category.WHILE || getCategory()==Category.FOREACH || getCategory()==Category.IF)){
				//System.out.println("expressions while resetted");
				for(Value<?> expression:expressions)
					if(expression != null)
						expression.reset();
			}else{
				System.out.println("had nooit mogen gebeuren in reset.");
				System.out.println(category.toString());
				if(getProgram().getGameObject() instanceof Buzam)
					System.out.println("ja het was buzam");
				if(category==Category.ACTION)
					System.out.println(action.toString());
				if(category==Category.FOREACH)
					System.out.println(kind.toString());
			}
			//else: gaat verder in while/foreach/if lus (zou anders al op isDone hebben gestaan)
		}
	}
	/*
	private void hardReset(){
		done = false;
		timeToWait=null;
		iterObjects = null;
		noBreak = true;
		hardResetted=true;
		for(Value<?> expression:expressions)
			if(expression != null)
				expression.reset();
		if(previousStatement != null){
			previousStatement.hardReset();
			previousStatement=null;
		}else{
				program.resetGlobals();
		}
	}*/

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
	
	public Value<?>[] getExpressions() {
		return expressions.clone();
	}
	
	public Statement[] getNextStatements(){
		return nextStatements.clone();
	}
	
	public Statement getPreviousStatement(){
		return previousStatement;
	}

	public Double getTimeToWait() {
		return timeToWait;
	}

	public void setTimeToWait(Double timeToWait) {
		this.timeToWait = timeToWait;
	}
	
	public String toString(){
		String str="";
		for(Value<?> expr:expressions){
			if(expr != null)
				str += expr.toString();
		}
		return category+","+action+","+str+",done?: "+isDone();
	}
	
	public void BreakDone(){
		noBreak = false;
	}
	
	public boolean getNoBreak(){
		return noBreak;
	}
	
	public boolean isValidCategory(Category category){
		if (category==null || category==Category.ACTION || category==Category.FOREACH ){
				return false;
		}
		return true;
	}
}

package jumpingalien.program.internal;

import jumpingalien.model.Program;

public class Statement {
	private Program program;
	private boolean done;
	private final Statement[] nextStatement = new Statement[2];
	private Statement previousStatement;
	
	public void addProgram(Program program){
		this.program = program;
	}
	
	public Program getProgram(){
		done = false;
		return program;
	}
	
	public Statement(){
	}
	
	public void addStatement(Statement statement){//TODO: add checkers
		if(nextStatement[0]==null){
			nextStatement[0]= statement;
		}else{
			if(nextStatement[1] == null)
				nextStatement[1] = statement;
			else
				System.out.println("too much statements");
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
	
	private void reset(){
		done = false;
		if(previousStatement != null)
			previousStatement.reset();
		else
			program.resetGlobals();
	}
}

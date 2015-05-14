package jumpingalien.program.internal;

import jumpingalien.model.Program;

public class Statement {
	private Program program;
	private boolean done;
	private Statement nextStatement;
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
	
	public boolean isDone(){
		return done;
	}
	
	public double executeNext(double dt){
		if(dt<=0)
			return 0;
		if(!isDone()){
			execute();
			dt-= 0.001;
		}
		if(nextStatement != null){
			nextStatement.addPreviousStatement(this);
			return nextStatement.executeNext(dt);
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

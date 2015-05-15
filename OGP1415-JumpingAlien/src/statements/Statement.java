package statements;

import jumpingalien.part3.programs.SourceLocation;
import Program.Program;

public abstract class Statement {
		
	public SourceLocation sourceLocation;
	public Program program = null;
	
	public Statement(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation(){
		return sourceLocation;
	}
	
	public void setProgram(Program program){
		this.program= program;
	}
	 
	public Program getProgram(){
		return program;
	}
	
	public abstract Object evaluate();

}

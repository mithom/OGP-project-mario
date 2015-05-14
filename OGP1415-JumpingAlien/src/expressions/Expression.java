package expressions;


import Program.Program;
import jumpingalien.part3.programs.SourceLocation;



public class Expression {
	
	public SourceLocation sourceLocation;
	public Program program = null;
	
	public Expression(SourceLocation sourceLocation){
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

}

package jumpingalien.program.statement.util;

import jumpingalien.program.internal.Statement;

public enum Category {
	WHILE{
		public void execute(Statement statement, double dt){
			
		};
	},
	FOREACH{
		public void execute(Statement statement, double dt){
			
		};
	},
	BREAK{
		public void execute(Statement statement, double dt){
			
		};
	},
	IF{
		public void execute(Statement statement, double dt){
			
		};
	},
	PRINT{
		public void execute(Statement statement, double dt){
			
		};
	},
	ACTION{
		public void execute(Statement statement, double dt){
			statement.getAction().execute(statement);
		};
	},
	SEQUENCE{
		public void execute(Statement statement, double dt){
			
		};
	};//eventueel sequence in maken van program afhandelen
	public abstract void execute(Statement statement, double dt); 
}

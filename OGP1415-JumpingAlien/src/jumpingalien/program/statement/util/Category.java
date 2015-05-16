package jumpingalien.program.statement.util;

import jumpingalien.model.Program.Direction;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;

public enum Category {
	WHILE{
		public void execute(Statement statement, double dt){
			statement.getNextStatements()[0].addPreviousStatement(statement);
			while((boolean)statement.getExpressions()[0].evaluate(dt)){
				statement.getNextStatements()[0].executeNext(dt);
			}
		};
	},
	ASSIGNMENT{
		public void execute(Statement statement, double dt){
			if(statement.getType().getType()==Type.type.BOOL)
				statement.getProgram().setBoolean((String)statement.getExpressions()[0].evaluate(dt),(Boolean)statement.getExpressions()[1].evaluate(dt));				
			if(statement.getType().getType()==Type.type.DIRECTION)
				statement.getProgram().setDirection((String)statement.getExpressions()[0].evaluate(dt),(Direction)statement.getExpressions()[1].evaluate(dt));
			if(statement.getType().getType()==Type.type.OBJECT)
				statement.getProgram().setObject((String)statement.getExpressions()[0].evaluate(dt),(GameObject)statement.getExpressions()[1].evaluate(dt));
			if(statement.getType().getType()==Type.type.DOUBLE)
				statement.getProgram().setDouble((String)statement.getExpressions()[0].evaluate(dt),(Double)statement.getExpressions()[1].evaluate(dt));
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

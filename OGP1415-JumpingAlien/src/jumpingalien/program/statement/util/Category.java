package jumpingalien.program.statement.util;

import jumpingalien.model.Program.Direction;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;

public enum Category {
	WHILE{
		public void execute(Statement statement, double[] dt){//TODO onneindige loop atm doordat dt nooit verlaagt
			//System.out.println("begin van while");
			while((boolean)statement.getExpressions()[0].evaluate(dt) && dt[0]>0){
				statement.getNextStatements()[0].addPreviousStatement(statement);
				statement.getNextStatements()[0].executeNext(dt);
			}
			if(dt[0]>0){
				statement.setDoneTrue();
				//System.out.println("uit while, niet door tijd");
				statement.getNextStatements()[1].addPreviousStatement(statement);
				statement.getNextStatements()[1].executeNext(dt);
				
			}/*else{
				System.out.println("uit de while door tijd");
			}*/
		};
	},
	ASSIGNMENT{
		public void execute(Statement statement, double[] dt){
			if(statement.getType().getType()==Type.type.BOOL)
				statement.getProgram().setBoolean((String)statement.getExpressions()[0].evaluate(dt),(Boolean)statement.getExpressions()[1].evaluate(dt));				
			if(statement.getType().getType()==Type.type.DIRECTION)
				statement.getProgram().setDirection((String)statement.getExpressions()[0].evaluate(dt),(Direction)statement.getExpressions()[1].evaluate(dt));
			if(statement.getType().getType()==Type.type.OBJECT)
				statement.getProgram().setObject((String)statement.getExpressions()[0].evaluate(dt),(GameObject)statement.getExpressions()[1].evaluate(dt));
			if(statement.getType().getType()==Type.type.DOUBLE)
				statement.getProgram().setDouble((String)statement.getExpressions()[0].evaluate(dt),(Double)statement.getExpressions()[1].evaluate(dt));
			statement.setDoneTrue();
			dt[0]-=0.001d;
			
			if(statement.getType()==null){
				System.out.println("invalid type in assignment evaluation");
			}
		};
	},
	FOREACH{
		public void execute(Statement statement, double[] dt){
			statement.setDoneTrue();
			dt[0]-=0.001d;
		};
	},
	BREAK{
		public void execute(Statement statement, double[] dt){
			statement.setDoneTrue();
			dt[0]-=0.001d;
		};
	},
	IF{
		public void execute(Statement statement, double[] dt){
			statement.setDoneTrue();
			dt[0]-=0.001d;
		};
	},
	PRINT{
		public void execute(Statement statement, double[] dt){
			statement.setDoneTrue();
			dt[0]-=0.001d;
		};
	},
	ACTION{
		public void execute(Statement statement, double[] dt){
			statement.getAction().execute(statement);
			statement.setDoneTrue();//TODO: in action laten setten
			dt[0]-=0.001d;
		};
	},
	SEQUENCE{
		public void execute(Statement statement, double[] dt){
			statement.setDoneTrue();
			dt[0]-=0.001d;
		};
	};
	public abstract void execute(Statement statement, double[] dt); 
}

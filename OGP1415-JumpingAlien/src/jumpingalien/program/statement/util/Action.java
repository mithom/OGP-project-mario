package jumpingalien.program.statement.util;

import jumpingalien.model.Program;
import jumpingalien.program.internal.Statement;
import jumpingalien.state.Direction;

public enum Action {
	STARTRUN{
		public void execute(Statement statement, double[] dt){
			if(statement.getExpressions()[0].evaluate(dt)==Program.Direction.LEFT && dt[0]>0){
				if(statement.getProgram().getGameObject().toString().contains("nr:2"))
					System.out.println("startrun LEFT");
				statement.getProgram().getGameObject().startMove(Direction.LEFT);
				statement.setDoneTrue();
				dt[0]-=0.001;
			}else{
				if(dt[0]>0){
					if(statement.getProgram().getGameObject().toString().contains("nr:2"))
						System.out.println("startrun RIGHT");
					statement.getProgram().getGameObject().startMove(Direction.RIGHT);
					statement.setDoneTrue();
					dt[0]-=0.001;
				}
			}
		};
	},
	STOPRUN{
		public void execute(Statement statement, double[] dt){
			if(statement.getExpressions()[0].evaluate(dt)==Program.Direction.LEFT && dt[0]>0){
				if(statement.getProgram().getGameObject().toString().contains("nr:2"))
					System.out.println("stoprun LEFT: "+statement.getProgram().getGameObject().toString());
				statement.getProgram().getGameObject().endMove(Direction.LEFT);
				statement.setDoneTrue();
				dt[0]-=0.001;
			}else{
				if(dt[0]>0){
					if(statement.getProgram().getGameObject().toString().contains("nr:2"))
						System.out.println("stoprun RIGHT: "+statement.getProgram().getGameObject().toString());
					statement.getProgram().getGameObject().endMove(Direction.RIGHT);
					statement.setDoneTrue();
					dt[0]-=0.001;
				}
			}
		};
	},
	STARTJUMP{
		public void execute(Statement statement, double[] dt){
			statement.getProgram().getGameObject().startJump();
			dt[0]-=0.001;
			statement.setDoneTrue();
		};
	},
	STOPJUMP{
		public void execute(Statement statement, double[] dt){
			statement.getProgram().getGameObject().endJump();
			dt[0]-=0.001;
			statement.setDoneTrue();
		};
	},
	STARTDUCK{
		public void execute(Statement statement, double[] dt){
			statement.getProgram().getGameObject().startDuck();
			dt[0]-=0.001;
			statement.setDoneTrue();
		};
	},
	STOPDUCK{
		public void execute(Statement statement, double[] dt){
			statement.getProgram().getGameObject().endDuck();
			dt[0]-=0.001;
			statement.setDoneTrue();
		};
	},
	WAIT{
		public void execute(Statement statement, double[] dt){
			Double timeToWait = statement.getTimeToWait();
			if(timeToWait==null)
				timeToWait=(double)statement.getExpressions()[0].evaluate(dt);
			while(timeToWait>0 && dt[0]>0){
				dt[0]-= 0.001d;
				timeToWait -= 0.001d;
			}
			if(dt[0]>0)
				statement.setTimeToWait(timeToWait);
				statement.setDoneTrue();
		};
	};
	public abstract void execute(Statement statement, double[] dt); 
}

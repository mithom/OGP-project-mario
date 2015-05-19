package jumpingalien.program.statement.util;

import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.program.internal.Statement;
import jumpingalien.state.Direction;

public enum Action {
	STARTRUN{
		public void execute(Statement statement, double[] dt){
			if(statement.getExpressions()[0].evaluate(dt)==IProgramFactory.Direction.LEFT && dt[0]>0){
				statement.getProgram().getGameObject().startMove(Direction.LEFT);
				statement.setDoneTrue();
				dt[0]-=0.001;
			}else{
				if(dt[0]>0){
					statement.getProgram().getGameObject().startMove(Direction.RIGHT);
					statement.setDoneTrue();
					dt[0]-=0.001;
				}
			}
		};
	},
	STOPRUN{
		public void execute(Statement statement, double[] dt){
			if(statement.getExpressions()[0].evaluate(dt)==IProgramFactory.Direction.LEFT && dt[0]>0){
				statement.getProgram().getGameObject().endMove(Direction.LEFT);
				statement.setDoneTrue();
				dt[0]-=0.001;
			}else{
				if(dt[0]>0){
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
		};
	},
	STOPJUMP{
		public void execute(Statement statement, double[] dt){
			statement.getProgram().getGameObject().endJump();
			dt[0]-=0.001;
		};
	},
	STARTDUCK{
		public void execute(Statement statement, double[] dt){
			statement.getProgram().getGameObject().startDuck();
			dt[0]-=0.001;
		};
	},
	STOPDUCK{
		public void execute(Statement statement, double[] dt){
			statement.getProgram().getGameObject().endDuck();
			dt[0]-=0.001;
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

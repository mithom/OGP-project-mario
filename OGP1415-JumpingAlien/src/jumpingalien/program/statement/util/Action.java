package jumpingalien.program.statement.util;

import jumpingalien.model.Buzam;
import jumpingalien.model.Program;
import jumpingalien.program.internal.Statement;
import jumpingalien.state.Direction;

public enum Action {//TODO eerst evalueren, dan tijd controleren, dan pas uitvoeren!!!
	STARTRUN{
		public void execute(Statement statement, double[] dt){
			if(statement.getExpressions()[0].evaluate(dt)==Program.Direction.LEFT && dt[0]>0){
				if(statement.getProgram().getGameObject() instanceof Buzam)
					System.out.println("startrun LEFT");
				statement.getProgram().getGameObject().startMove(Direction.LEFT);
				statement.setDoneTrue();
				dt[0]-=0.001;
			}else{
				if(dt[0]>0){
					if(statement.getProgram().getGameObject() instanceof Buzam)
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
				if(statement.getProgram().getGameObject() instanceof Buzam)
					System.out.println("stoprun LEFT: "+statement.getProgram().getGameObject().toString());
				statement.getProgram().getGameObject().endMove(Direction.LEFT);
				statement.setDoneTrue();
				dt[0]-=0.001;
			}else{
				if(dt[0]>0){
					if(statement.getProgram().getGameObject() instanceof Buzam)
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
				timeToWait=(Double)statement.getExpressions()[0].evaluate(dt);
			while(dt[0]>0 && timeToWait>0){//time to wait can be null due to smart booleans =)
				dt[0]-= 0.001d;
				timeToWait -= 0.001d;
			}
			if(timeToWait != null){
				double min = Math.min(dt[0],timeToWait);
				dt[0]-= min;
				timeToWait -= min;
				statement.setTimeToWait(timeToWait);
				if(timeToWait==0)
					statement.setDoneTrue();
			}
		};
	};
	public abstract void execute(Statement statement, double[] dt); 
}

package jumpingalien.program.statement.util;

import jumpingalien.program.internal.Statement;

public enum Action {
	STARTRUN{
		public void execute(Statement statement){};
	},
	STOPRUN{
		public void execute(Statement statement){};
	},
	STARTJUMP{
		public void execute(Statement statement){};
	},
	STOPJUMP{
		public void execute(Statement statement){};
	},
	STARTDUCK{
		public void execute(Statement statement){};
	},
	STOPDUCK{
		public void execute(Statement statement){};
	},
	WAIT{
		public void execute(Statement statement){};
	};
	public abstract void execute(Statement statement); 
}

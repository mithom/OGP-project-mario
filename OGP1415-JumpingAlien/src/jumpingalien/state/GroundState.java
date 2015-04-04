package jumpingalien.state;

public enum GroundState {
	GROUNDED{
		public int getSign(){
			return 0;
		}
	},
	AIR{
		public int getSign(){
			return 1;
		}
	};
	public abstract int getSign();
}

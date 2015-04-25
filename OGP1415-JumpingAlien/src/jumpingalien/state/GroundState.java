package jumpingalien.state;

public enum GroundState {
	GROUNDED{
		public int getMultiplier(){
			return 0;
		}
	},
	AIR{
		public int getMultiplier(){
			return 1;
		}
	};
	public abstract int getMultiplier();
}

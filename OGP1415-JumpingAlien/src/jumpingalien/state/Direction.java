package jumpingalien.state;
/**
 * 
 * @author Gebruiker
 *
 */
public enum Direction {
	LEFT{
		public int getMultiplier(){
			return -1;
		}
	},
	RIGHT{
		public int getMultiplier(){
			return 1;
		}
	},
	STALLED{
		public int getMultiplier(){
			return 0;
		}
	};
	public abstract int getMultiplier();
}

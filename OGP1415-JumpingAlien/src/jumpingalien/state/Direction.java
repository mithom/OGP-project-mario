package jumpingalien.state;
/**
 * 
 * @author Gebruiker
 *
 */
public enum Direction {
	LEFT{
		public int getSign(){//getMultiplier
			return -1;
		}
	},
	RIGHT{
		public int getSign(){
			return 1;
		}
	},
	STALLED{
		public int getSign(){
			return 0;
		}
	};
	public abstract int getSign();
}

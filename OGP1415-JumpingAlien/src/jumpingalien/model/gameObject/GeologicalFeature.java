package jumpingalien.model.gameObject;

import javax.management.openmbean.InvalidKeyException;

public enum GeologicalFeature {
	air{
		public int getEquivalentNumberType(){
			return 0;
		}
	},
	solidGround{
		public int getEquivalentNumberType(){
			return 1;
		}
	},
	water{
		public int getEquivalentNumberType(){
			return 2;
		}
	},
	magma{
		public int getEquivalentNumberType(){
			return 3;
		}
	};
	
	public abstract int getEquivalentNumberType();
	
	static public GeologicalFeature numberTypeToGeologicalFeature(int number) 
			throws InvalidKeyException{
		switch(number){
		case 0:
			return GeologicalFeature.air;
		case 1:
			return GeologicalFeature.solidGround;
		case 2:
			return GeologicalFeature.water;
		case 3:
			return GeologicalFeature.magma;
		default:
			throw new InvalidKeyException("invalid integer, must be in (0,1,2,3)");
		}
	}
}

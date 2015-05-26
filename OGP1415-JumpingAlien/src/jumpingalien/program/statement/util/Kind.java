package jumpingalien.program.statement.util;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.TileObject;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.model.*;

public enum Kind {
	MAZUB{
		public Class<? extends GameObject> getCorrespondingClass(){
			return Mazub.class;
		}
	},
	BUZAM{
		public Class<? extends GameObject> getCorrespondingClass(){
			return Buzam.class;
		}
	},
	SLIME{
		public Class<? extends GameObject> getCorrespondingClass(){
			return Slime.class;
		}
	},
	SHARK{
		public Class<? extends GameObject> getCorrespondingClass(){
			return Shark.class;
		}
	},
	PLANT{
		public Class<? extends GameObject> getCorrespondingClass(){
			return Plant.class;
		}
	},
	TERRAIN{
		public Class<? extends GameObject> getCorrespondingClass(){
			return TileObject.class;
		}
	},
	ANY{
		public Class<? extends GameObject> getCorrespondingClass(){
			return GameObject.class;
		}
	};
	
	public abstract Class<? extends GameObject> getCorrespondingClass();
	
	public static Kind getCorrespondingKind(IProgramFactory.Kind kind){
		switch(kind){
		case MAZUB:
			return MAZUB;
		case BUZAM:
			return BUZAM;
		case SLIME:
			return SLIME;
		case SHARK:
			return SHARK;
		case PLANT:
			return PLANT;
		case TERRAIN:
			return TERRAIN;
		case ANY:
			return ANY;
		default:
			return ANY;
		}
	}
}

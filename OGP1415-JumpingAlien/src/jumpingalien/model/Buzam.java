package jumpingalien.model;

import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.state.GroundState;
import jumpingalien.util.Sprite;

public class Buzam extends Mazub {

	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites)
			throws PositionOutOfBoundsException {
		super(pixelLeftX, pixelBottomY, sprites,0,500,500);
	}

	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites,Program program)
			throws PositionOutOfBoundsException {
		super(pixelLeftX, pixelBottomY, sprites,program,0,500,500);
	}
	
	@Override
	public void addToWorld(World world){
		if(canHaveAsWorld(world)){
			setWorld(world);
			world.addBuzam(this);
			if(overlapsWithWall()[0]){
				setGroundState(GroundState.GROUNDED);
			}else{
				setGroundState(GroundState.AIR);
			}
		}
	}
	//TODO: implement bouncing with mazub etc!
	
	@Override
	public String toString(){
		return "buzam, using program: "+ (getProgram()!=null);
	}
	
	@Override
	public void EffectOnCollisionWith(GameObject gameObject){//TODO: aan te passen
		if(gameObject instanceof Shark || gameObject instanceof Slime){
			if(!isImmune()){
				if(getPerimeters()[1]<gameObject.getPerimeters()[3]){
					//indien mazub niet boven zijn vijand staat (na de botsing) zal hij damage nemen. 
					this.loseHp(50);
					this.imunityTime = 0.6d;
				}
			}
		}
	}
	
	@Override
	public void moveWindow(){
		
	}
}

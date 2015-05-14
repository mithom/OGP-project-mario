package jumpingalien.model;

import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.state.Direction;
import jumpingalien.state.DuckState;
import jumpingalien.state.GroundState;
import jumpingalien.util.Sprite;


public class Buzam extends GameObject {
	private double horizontalVelocity;
	private final static double horizontalAcceleration = 0.9d;
	private final static double verticalAcceleration = -10d;
	private double verticalVelocity;
	private double maxHorizontalVelocity;
	private final double initialHorizontalVelocity;
	private final double initialVerticalVelocity =8d;
	private Direction direction ;
	private GroundState groundState;
	private DuckState duckState;
	private double timeSinceLastAnimation;
	private double timeSinceLastMovement;
	private Direction lastMovingDirection = Direction.STALLED;
	private double maxDuckingVelocity = 1.0d;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws PositionOutOfBoundsException{
		super(pixelLeftX, pixelBottomY, sprites);
		this.maxHorizontalVelocity = 3d;
		this.initialHorizontalVelocity=1d;
		duckState  = DuckState.STRAIGHT;
		direction= Direction.STALLED;
		horizontalVelocity=0.0d;
		verticalVelocity = 0.0d;
	}
	
	public Buzam(int pixelLeftX, int pixelBottomY,double initHorVel,double maxHorVel, Sprite[] sprites)throws PositionOutOfBoundsException{
		super(pixelLeftX, pixelBottomY, sprites);
		this.m = (sprites.length-8)/2;
		this.maxHorizontalVelocity = maxHorVel;
		this.initialHorizontalVelocity = initHorVel;
		duckState  = DuckState.STRAIGHT;
		direction= Direction.STALLED;
	}
	

	@Override
	public void addToWorld(World world) {
		// TODO Auto-generated method stub

	}

	@Override
	public void advanceTime(double dt) throws NullPointerException,
			IllegalMovementException, IllegalMazubStateException,
			IllegalTimeException, PositionOutOfBoundsException,
			IllegalSizeException {
		// TODO Auto-generated method stub

	}

	@Override
	public void EffectOnCollisionWith(GameObject gameObject) {
		// TODO Auto-generated method stub

	}

}

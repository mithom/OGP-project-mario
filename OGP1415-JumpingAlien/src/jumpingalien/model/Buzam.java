package jumpingalien.model;

import jumpingalien.exception.PositionOutOfBoundsException;
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
	
	//TODO: implement bouncing with mazub etc!
}

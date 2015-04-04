
import static org.junit.Assert.*;

import java.security.InvalidKeyException;

import jumpingalien.part1.facade.Facade;
import jumpingalien.part1.facade.IFacade;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.Test;

import static jumpingalien.tests.util.TestUtils.*;


public class MazubTest {

	public World smallWorld;
	public World largeWorld;
	
	public MazubTest(){
	}
	
	@Test
	public void testConstantTimeGeologicalFeaturesSmall()throws InvalidKeyException,PositionOutOfBoundsException{
		smallWorld = new World(1, 100, 100, 10, 10, 1, 1);
		largeWorld = new World(1, 10000, 10000, 10, 10, 50, 50);
		for(float i=0;i<10000;i+= 0.001){
			assertEquals(0, smallWorld.getGeologicalFeature(50, 50));
		}
	}
	
	@Test
	public void testConstantTimeGeologicalFeaturesLarge()throws InvalidKeyException,PositionOutOfBoundsException{
		smallWorld = new World(1, 100, 100, 10, 10, 1, 1);
		largeWorld = new World(1, 10000, 10000, 10, 10, 50, 50);
		for(float i=0;i<10000;i+= 0.001){
			assertEquals(0, largeWorld.getGeologicalFeature(500, 500));
		}//zijn even snel (draai namen om en tijden wisselen mee om!)
	}
	
	@Test
	public void testAdvanceTime() {
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		// dt needs to be positive and less than 0.2
		try{
			facade.advanceTime(alien,0d-Double.MIN_VALUE);
		}catch(ModelException e){
			assertEquals(e.getMessage(),"illegal argument for dt: "+ (0-Double.MIN_VALUE));
		}
		try{
			facade.advanceTime(alien,0.2d+Double.MIN_VALUE);
		}catch(ModelException e){
			assertEquals(e.getMessage(),"illegal argument for dt: "+ (2+Double.MIN_VALUE));
		}
		try{
			facade.advanceTime(alien,Double.NaN);
		}catch(ModelException e){
			assertEquals(e.getMessage(),"illegal argument for dt: "+ Double.NaN);
		}
		
		//acceleration zero when not moving:
		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),Util.DEFAULT_EPSILON); 
		
		alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		//correct movement to the right:
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.1);
		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)
		assertArrayEquals(intArray(10, 0), facade.getLocation(alien));
		
		//Max speed at the right time (movement to the right)
		facade.endMoveRight(alien);
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.1);
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(alien, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(3, 0), facade.getVelocity(alien),Util.DEFAULT_EPSILON);
	
		//Correct movement to the left:
	
		Mazub alien2 = facade.createMazub(1023, 0, spriteArrayForSize(2, 2));
		facade.startMoveLeft(alien2);
		facade.advanceTime(alien2, 0.1);
		
		//location is 10,1255 meter, should be in pixel 1012
		assertArrayEquals(intArray(1012,0),facade.getLocation(alien2));
		
		//Max speed at the right time (movement to the left)
		facade.endMoveLeft(alien2);
		facade.advanceTime(alien2, 0);
		facade.startMoveLeft(alien2);
		facade.advanceTime(alien2, 0.1);
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(alien2, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(-3, 0), facade.getVelocity(alien2),Util.DEFAULT_EPSILON);
		
		//correct initial speed when jumping and when ending the jump
		Mazub alien3 = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startJump(alien3);
		facade.advanceTime(alien3, 0);
		assertArrayEquals(doubleArray(0,8),facade.getVelocity(alien3),Util.DEFAULT_EPSILON);
		
		facade.advanceTime(alien3, 0.05);
		facade.endJump(alien3);
		facade.advanceTime(alien3, 0);
		assertArrayEquals(doubleArray(0,0),facade.getVelocity(alien3),Util.DEFAULT_EPSILON);
		
		//correct speed when falling
		
		Mazub alien4= facade.createMazub(100, 500, spriteArrayForSize(2,2));
		
		facade.advanceTime(alien4, 0.1);
		
		//mazub falls if insatnciated in the air
		assertArrayEquals(doubleArray(0, -1 ), facade.getVelocity(alien4),Util.DEFAULT_EPSILON);
		
		
		// are the animations correct?
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien6= facade.createMazub(0, 0, sprites);

		facade.startMoveRight(alien6);
		facade.advanceTime(alien6, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(alien6, 0.075);
		}

		assertEquals(sprites[8+m], facade.getCurrentSprite(alien6));
		facade.startDuck(alien6);
		facade.advanceTime(alien6, 0);
		assertEquals(facade.getCurrentSprite(alien6),sprites[6]);

		//bottom left pixel within boundaries of the world?
		
		Mazub alien7 = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startMoveLeft(alien7);
		facade.advanceTime(alien7, 0.1);

		assertArrayEquals(intArray(0,0),facade.getLocation(alien7));
		
		Mazub alien8 = facade.createMazub(1023, 0, spriteArrayForSize(2, 2));
		facade.startMoveRight(alien8);
		facade.advanceTime(alien8, 0.1);
		
		assertArrayEquals(intArray(1023,0),facade.getLocation(alien8));
	}

	@Test
	public void testGetVelocity() {
		//the functionality has already been tested in testAdvanceTime()
		//here we'll test if the characteristics have a valid number at all time.
		IFacade facade = new Facade();	
		Mazub alien=facade.createMazub(200,0,spriteArrayForSize(2,2));
		
		assertNotEquals(facade.getAcceleration(alien)[0],Double.NaN);
		assertNotEquals(facade.getAcceleration(alien)[1],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[0],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[1],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[0],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[1],Double.NaN);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien,0);
		
		assertNotEquals(facade.getAcceleration(alien)[0],Double.NaN);
		assertNotEquals(facade.getAcceleration(alien)[1],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[0],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[1],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[0],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[1],Double.NaN);
		
		facade.advanceTime(alien, 0.1);
		assertNotEquals(facade.getAcceleration(alien)[0],Double.NaN);
		assertNotEquals(facade.getAcceleration(alien)[1],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[0],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[1],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[0],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[1],Double.NaN);
		
		facade.endMoveRight(alien);		
		facade.startMoveLeft(alien);
		facade.advanceTime(alien,0.1);
		
		assertNotEquals(facade.getAcceleration(alien)[0],Double.NaN);
		assertNotEquals(facade.getAcceleration(alien)[1],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[0],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[1],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[0],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[1],Double.NaN);
		
		facade.endMoveLeft(alien);
		facade.startJump(alien);
		facade.advanceTime(alien,0.1);
		facade.endJump(alien);
		
		assertNotEquals(facade.getAcceleration(alien)[0],Double.NaN);
		assertNotEquals(facade.getAcceleration(alien)[1],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[0],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[1],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[0],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[1],Double.NaN);
		
		facade.startDuck(alien);
		
		assertNotEquals(facade.getAcceleration(alien)[0],Double.NaN);
		assertNotEquals(facade.getAcceleration(alien)[1],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[0],Double.NaN);
		assertNotEquals(facade.getVelocity(alien)[1],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[0],Double.NaN);
		assertNotEquals(facade.getLocation(alien)[1],Double.NaN);
	}

	@Test
	public void testGetMaxHorizontalVelocity() {
		IFacade facade = new Facade();
		
		Mazub alien=facade.createMazub(0,0,spriteArrayForSize(2,2));
		
		//not ducked
		facade.startMoveRight(alien);
		for(int i=0;i<12;i++){
			facade.advanceTime(alien, 0.2);
		}
		int[] loc = facade.getLocation(alien);
		facade.advanceTime(alien, 0.2);
		loc[0]+= 60;
		assertArrayEquals(loc,facade.getLocation(alien));
		//ducked
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.2);
		loc[0]+= 20;
		assertArrayEquals(loc, facade.getLocation(alien));

	}

	
	@Test
	public void testGetHorizontalAcceleration() {
		IFacade facade = new Facade();
		
		Mazub alien=facade.createMazub(200,0,spriteArrayForSize(2,2));
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien,0.1);
		assertArrayEquals(doubleArray(0.9,0.0), facade.getAcceleration(alien), Util.DEFAULT_EPSILON);
		
		facade.endMoveRight(alien);
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.1);
		
		assertArrayEquals(facade.getAcceleration(alien),doubleArray(-0.9d,0),Util.DEFAULT_EPSILON);
		
		alien=facade.createMazub(200,0,spriteArrayForSize(2,2));
		
		facade.startDuck(alien);
		facade.startMoveRight(alien);
		facade.advanceTime(alien,0.1);
		assertArrayEquals(doubleArray(0.0,0.0), facade.getAcceleration(alien), Util.DEFAULT_EPSILON);
		
		facade.endMoveRight(alien);
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.1);
		
		assertArrayEquals(facade.getAcceleration(alien),doubleArray(-0.0d,0),Util.DEFAULT_EPSILON);
	}

	@Test
	public void testGetVerticalAcceleration() {
		IFacade facade = new Facade();
		
		Mazub alien=facade.createMazub(200,0,spriteArrayForSize(2,2));
		
		facade.startJump(alien);
		facade.advanceTime(alien, 0.1);
		
		assertArrayEquals(facade.getAcceleration(alien),new double[]{0d,-10d},Util.DEFAULT_EPSILON);
		for(int i=0;i<20;i++){
			facade.advanceTime(alien, 0.2);
		}
		assertArrayEquals(doubleArray(0,0), facade.getAcceleration(alien),Util.DEFAULT_EPSILON);
	}

	@Test
	public void testGetVerticalVelocity() {
		IFacade facade = new Facade();
		
		Mazub alien=facade.createMazub(200,0,spriteArrayForSize(2,2));
		
		facade.startJump(alien);
		//0..31-> 32 keer 0.05seconden in de lugt, vooraleer mazub terug op de grond is
		//hierna zal de snelheid 0.0 zijn.		
		for (int i = 0; i<31; i++) {
			facade.advanceTime(alien, 0.05);
			assertArrayEquals(facade.getVelocity(alien),doubleArray(0.0d,8d-0.5*(i+1)),Util.DEFAULT_EPSILON);
		}
		
		facade.advanceTime(alien,0.1);
		assertArrayEquals(facade.getVelocity(alien),doubleArray(0d,0d),Util.DEFAULT_EPSILON);
		facade.advanceTime(alien,0.1);
		assertArrayEquals(facade.getVelocity(alien),doubleArray(0d,0d),Util.DEFAULT_EPSILON);
	}
}

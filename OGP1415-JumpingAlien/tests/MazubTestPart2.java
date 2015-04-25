
import static org.junit.Assert.*;

import java.security.InvalidKeyException;
import java.util.Arrays;

import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.Test;

import static jumpingalien.tests.util.TestUtils.*;


public class MazubTestPart2 {

	public World smallWorld;
	public World largeWorld;
	
	public MazubTestPart2(){
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
	public void testFallingForCorrectTimeAtCorrectSpeed() {
		IFacadePart2 facade = new Facade();

		// 10 vertical tiles, size 500px
		// a
		// .
		// .
		// .
		// X
		World world = facade.createWorld(500, 1, 10, 1, 1, 0, 1);
		facade.setGeologicalFeature(world, 0, 0, 1);

		int m = 300;
		Sprite[] sprites = spriteArrayForSize(3, 3, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 4990, sprites);
		facade.setMazub(world, alien);
		facade.advanceTime(world, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(world, 0.01);
			System.out.println(Arrays.toString(facade.getVelocity(alien)));
			if(i<(299))
				assertArrayEquals(new double[]{0.0d,-(i+1)*0.1d-0.05}, facade.getVelocity(alien),Util.DEFAULT_EPSILON);
			else
				//Mazub reaches the stone floor after 2,996664812754339secondes.
				assertArrayEquals(new double[]{0.0d,0.0d}, facade.getVelocity(alien),Util.DEFAULT_EPSILON);
		}
		//assertArrayEquals(new double[]{0.0d,0.0d}, facade.getVelocity(alien),Util.DEFAULT_EPSILON);
	}

	@Test
	public void testGetMaxHorizontalVelocity() {
		
	}

	
	@Test
	public void testGetHorizontalAcceleration() {
		
	}

	@Test
	public void testGetVerticalAcceleration() {
		
	}

	@Test
	public void testGetVerticalVelocity() {
		
	}
}

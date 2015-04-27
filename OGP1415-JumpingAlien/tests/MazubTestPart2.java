
import static org.junit.Assert.*;

import java.security.InvalidKeyException;
import java.util.Arrays;

import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.*;
import jumpingalien.state.Direction;
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

		int m = 310;
		Sprite[] sprites = spriteArrayForSize(3, 3, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 4990, sprites);
		facade.setMazub(world, alien);
		facade.advanceTime(world, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(world, 0.01);
			if(i<(299))
				assertArrayEquals(new double[]{0.0d,-(i+1)*0.1d-0.05}, facade.getVelocity(alien),Util.DEFAULT_EPSILON);
			else
				//Mazub reaches the stone floor after 2,996664812754339secondes.
				assertArrayEquals(new double[]{0.0d,0.0d}, facade.getVelocity(alien),Util.DEFAULT_EPSILON);
		}
	}

	@Test
	public void testGetMaxHorizontalVelocity() {
		//this is different when ducking or standing straight. For safety we'll also test the case in witch you try to stand straight,
		//but still are in a ducking position due to terrain or obstacles.
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 2, 3, 1, 1, 0, 1);
		facade.setGeologicalFeature(world, 0, 0, 1);
		facade.setGeologicalFeature(world, 1, 0, 1);
		facade.setGeologicalFeature(world, 1, 2, 1);
		
		Mazub alien = facade.createMazub(0, 69, jumpingalien.common.sprites.JumpingAlienSprites.ALIEN_SPRITESET);
		facade.setMazub(world, alien);
		assertEquals(3.0d, alien.getMaxHorizontalVelocity(),Util.DEFAULT_EPSILON);
		facade.startDuck(alien);
		facade.advanceTime(world, 0.005d);
		assertEquals(1.0d, alien.getMaxHorizontalVelocity(),Util.DEFAULT_EPSILON);
		facade.endDuck(alien);
		assertEquals(3.0d, alien.getMaxHorizontalVelocity(),Util.DEFAULT_EPSILON);
		facade.advanceTime(world, 0.1d);
		assertEquals(3.0d, alien.getMaxHorizontalVelocity(),Util.DEFAULT_EPSILON);
		
		//now going underneath a block, trying to get straight and then test it.
		facade.startDuck(alien);
		facade.startMoveRight(alien);
		facade.advanceTime(world, 0.1d);
		assertEquals(1.0d, alien.getMaxHorizontalVelocity(),Util.DEFAULT_EPSILON);
		facade.endDuck(alien);
		facade.endMoveRight(alien);
		facade.advanceTime(world, 0.1d);
		//he should still be ducking because he is partially underneath a rock
		assertEquals(1.0d, alien.getMaxHorizontalVelocity(),Util.DEFAULT_EPSILON);
		facade.startMoveLeft(alien);
		facade.advanceTime(world, 0.1d);
		assertEquals(3.0d, alien.getMaxHorizontalVelocity(),Util.DEFAULT_EPSILON);
		
		
	}

	
	@Test
	public void testGetHorizontalAcceleration() {
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 10, 3, 1, 1, 0, 1);
		for(int i=0;i<10;i++)
			facade.setGeologicalFeature(world, i, 0, 1);//to move normally
		facade.setGeologicalFeature(world, 2, 2, 1);//to duck under
		
		Mazub alien = facade.createMazub(0, 69, jumpingalien.common.sprites.JumpingAlienSprites.ALIEN_SPRITESET);
		facade.setMazub(world, alien);
		//should be 0 if not walking
		assertEquals(0, alien.getHorizontalAcceleration(),Util.DEFAULT_EPSILON);
		facade.startMoveRight(alien);
		assertEquals(0.9d, alien.getHorizontalAcceleration(),Util.DEFAULT_EPSILON);
		facade.advanceTime(world, 0.1);
		assertEquals(0.9d, alien.getHorizontalAcceleration(),Util.DEFAULT_EPSILON);
		for(int i = 0;i<100;i++)
			facade.advanceTime(world, 0.1);
		//walking against a wall
		assertEquals(0.0d, alien.getHorizontalAcceleration(),Util.DEFAULT_EPSILON);
		facade.startDuck(alien);
		//zero when ducking
		assertEquals(0.0d, alien.getHorizontalAcceleration(),Util.DEFAULT_EPSILON);
		facade.advanceTime(world, 0.1d);
		assertEquals(0.0d, alien.getHorizontalAcceleration(),Util.DEFAULT_EPSILON);
		facade.endDuck(alien);
		//still zero because alien is ducked under a rock
		assertEquals(0.0d, alien.getHorizontalAcceleration(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void testDamageByTerrainAndRemovingOnDeath(){
		//setting up a world
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 5, 3, 1, 1, 0, 1);
		for(int i=0;i<5;i++)
			facade.setGeologicalFeature(world, i, 0, 1);//to move normally
		facade.setGeologicalFeature(world, 2, 1, 1);//land
		facade.setGeologicalFeature(world, 0, 1, 3);//lava
		facade.setGeologicalFeature(world, 1, 1, 3);//lava
		facade.setGeologicalFeature(world, 3, 1, 2);//water
		facade.setGeologicalFeature(world, 4, 1, 2);//water
		
		//tests for Slimes
		School school = facade.createSchool();
		Slime slimeForWater = facade.createSlime(280, 75, spriteArrayForSize(70, 40, 2), school);
		Slime slimeForLava = facade.createSlime(0, 75, spriteArrayForSize(70, 40, 2), school);
		facade.addSlime(world, slimeForLava);
		assertEquals(100, slimeForLava.getNbHitPoints());
		facade.advanceTime(world, 0.00005);
		assertEquals(50, slimeForLava.getNbHitPoints());
		facade.advanceTime(world, 0.1d);
		assertEquals(50, slimeForLava.getNbHitPoints());
		facade.advanceTime(world, 0.2d);
		assertEquals(0, slimeForLava.getNbHitPoints());
		assertFalse(world.getSlimes().contains(slimeForLava));
		//slimeForLava is death
		
		facade.addSlime(world, slimeForWater);
		facade.advanceTime(world, 0.00005d);
		assertEquals(100, slimeForWater.getNbHitPoints());
		facade.advanceTime(world, 0.1d);
		assertTrue(slimeForWater.isInWater());
		assertEquals(100, slimeForWater.getNbHitPoints());
		assertTrue(world.getSlimes().contains(slimeForWater));
		for(int i=0;i<50;i++){
			facade.advanceTime(world, 0.2000000005d);
			assertEquals(100-(1+i)*2, slimeForWater.getNbHitPoints());
		}
		assertFalse(world.getSlimes().contains(slimeForWater));
		
		//TODO hier verder werken
		//tests for Mazub
		Mazub mazubForWater = facade.createMazub(70*2, 69, spriteArrayForSize(70, 40, 20));
		Mazub mazubForLava = facade.createMazub(70*2, 69, spriteArrayForSize(70, 40, 20));
		facade.setMazub(world, mazubForLava);
		facade.setMazub(world, mazubForWater);
		
		//tests for Sharks
		Shark sharkForLava = facade.createShark(70*2, 69, spriteArrayForSize(70, 40, 2));
		Shark sharkForWater = facade.createShark(70*2, 69, spriteArrayForSize(70, 40, 2));
		facade.addShark(world, sharkForWater);
		facade.addShark(world, sharkForLava);
		
		//tests for Plants
		Plant plantInLava = facade.createPlant(0, 0, spriteArrayForSize(70, 40, 2));
		Plant plantInAir = facade.createPlant(70*2, 69, spriteArrayForSize(70, 40, 2));
		Plant plantInWater = facade.createPlant(70*4-35, 0, spriteArrayForSize(70, 40, 2));
		facade.addPlant(world, plantInWater);
		facade.addPlant(world, plantInAir);
		facade.addPlant(world, plantInLava);
	}
	//TODO hpverlies door terrein bij verschillende classes
	//TODO overlapping testen
	//TODO schools 
}

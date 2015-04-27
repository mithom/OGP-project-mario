
import static org.junit.Assert.*;

import java.security.InvalidKeyException;

import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.*;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.Position;
import jumpingalien.state.Direction;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;
import jumpingalien.common.sprites.JumpingAlienSprites;

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
		
		Mazub alien = facade.createMazub(0, 69, JumpingAlienSprites.ALIEN_SPRITESET);
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
			facade.advanceTime(world, 0.2d);
			assertEquals(100-(1+i)*2, slimeForWater.getNbHitPoints());
		}
		assertFalse(world.getSlimes().contains(slimeForWater));
		
		//tests for Mazub
		Mazub mazubForWater = facade.createMazub(280, 75, spriteArrayForSize(70, 40, 20));
		Mazub mazubForLava = facade.createMazub(0, 75, spriteArrayForSize(70, 40, 20));
		facade.setMazub(world, mazubForLava);
		assertEquals(100, mazubForLava.getNbHitPoints());
		facade.advanceTime(world, 0.00005);
		assertEquals(50, mazubForLava.getNbHitPoints());
		facade.advanceTime(world, 0.1d);
		assertEquals(50, mazubForLava.getNbHitPoints());
		facade.advanceTime(world, 0.2d);
		assertEquals(0, mazubForLava.getNbHitPoints());
		assertTrue(world.isGameOver());
		//mazubForLava is death
		
		facade.setMazub(world, mazubForWater);
		facade.advanceTime(world, 0.00005d);
		assertEquals(100, mazubForWater.getNbHitPoints());
		facade.advanceTime(world, 0.1d);
		assertTrue(mazubForWater.isInWater());
		assertEquals(100, mazubForWater.getNbHitPoints());
		assertFalse(world.isGameOver());
		for(int i=0;i<50;i++){
			facade.advanceTime(world, 0.2d);
			assertEquals(100-(1+i)*2,mazubForWater.getNbHitPoints());
		}
		assertTrue(world.isGameOver());
		
		//tests for Plants
		Plant plantInLava = facade.createPlant(35, 69, spriteArrayForSize(70, 40, 2));
		Plant plantInAir = facade.createPlant(70*2, 140, spriteArrayForSize(70, 40, 2));
		Plant plantInWater = facade.createPlant(280, 69, spriteArrayForSize(70, 40, 2));
		assertEquals(1, plantInAir.getNbHitPoints());
		assertEquals(1, plantInWater.getNbHitPoints());
		assertEquals(1, plantInLava.getNbHitPoints());
		facade.addPlant(world, plantInWater);
		facade.addPlant(world, plantInAir);
		facade.addPlant(world, plantInLava);
		facade.advanceTime(world, 0.2);
		facade.advanceTime(world, 0.2);
		assertEquals(1, plantInWater.getNbHitPoints());
		assertEquals(1, plantInLava.getNbHitPoints());
		assertEquals(1, plantInAir.getNbHitPoints());
		assertTrue(world.getPlants().contains(plantInWater));
		assertTrue(world.getPlants().contains(plantInLava));
		assertTrue(world.getPlants().contains(plantInAir));
		assertTrue(plantInAir.isInAir());
		assertTrue(plantInLava.isInLava());
		assertTrue(plantInWater.isInWater());
		
		//tests for Sharks
		//making an easier test area for the sharks, because they can move randomly
		World world2 = facade.createWorld(70, 3, 3, 1, 1, 1, 1);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
				world2.setGeologicalFeature(i, j, 1);
		}
		world2.setGeologicalFeature(1, 1, 3);//lava
		Shark sharkForLava = facade.createShark(69, 69, spriteArrayForSize(70, 40, 2));
		Shark sharkForWater = facade.createShark(69, 69, spriteArrayForSize(70, 40, 2));
		facade.addShark(world2, sharkForLava);
		assertEquals(100, sharkForLava.getNbHitPoints());
		facade.advanceTime(world2, 0.00005);
		assertEquals(50, sharkForLava.getNbHitPoints());
		facade.advanceTime(world2, 0.1d);
		assertEquals(50, sharkForLava.getNbHitPoints());
		facade.advanceTime(world2, 0.2d);
		assertEquals(0, sharkForLava.getNbHitPoints());
		assertFalse(world2.getSharks().contains(sharkForLava));
		//sharkForLava is death
		
		world2.setGeologicalFeature(1, 1, 0);//air
		facade.addShark(world2, sharkForWater);
		facade.advanceTime(world2, 0.00005d);
		assertEquals(100, sharkForWater.getNbHitPoints());
		facade.advanceTime(world2, 0.1d);
		assertTrue(sharkForWater.isInAir());
		assertEquals(100, sharkForWater.getNbHitPoints());
		assertTrue(world2.getSharks().contains(sharkForWater));
		for(int i=0;i<50;i++){
			facade.advanceTime(world2, 0.2d);
			assertEquals(100-(1+i)*2, sharkForWater.getNbHitPoints());
		}
		assertFalse(world2.getSharks().contains(sharkForWater));
	}
	
	
	@Test
	public void testBouncingGameObjects(){
		//since sharks move randomly, we just let them move a lot and will check the result.
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 10, 10, 1, 1, 0, 1);
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				facade.setGeologicalFeature(world, i, j, 1);//to move normally
		for(int i=1;i<9;i++)
			for(int j=1;j<9;j++)
				facade.setGeologicalFeature(world, i, j, 2);//water
		Shark shark1 = facade.createShark(70, 70, spriteArrayForSize(70, 40, 2));
		Shark shark2 = facade.createShark(200, 200, spriteArrayForSize(70, 40, 2));
		facade.addShark(world, shark2);
		facade.addShark(world, shark1);
		for(int i=0;i<500;i++){
			facade.advanceTime(world, 0.2d);
			assertFalse(shark1.overlaps(shark2));
		}
		
		for(int q=0;q<100;q++){//next test a lot of times, due to random movement
			World world2 = facade.createWorld(70, 10, 10, 1, 1, 0, 1);
			for(int i=0;i<10;i++)
				for(int j=0;j<10;j++)
					facade.setGeologicalFeature(world2, i, j, 1);//to move normally
			for(int i=1;i<9;i++)
				for(int j=1;j<9;j++)
					facade.setGeologicalFeature(world2, i, j, 2);//water
			Shark shark3 = facade.createShark(70, 70, spriteArrayForSize(70, 40, 2));
			Shark shark4 = facade.createShark(200, 200, spriteArrayForSize(70, 40, 2));
			facade.addShark(world, shark3);
			facade.addShark(world, shark4);
			Slime slime1 = facade.createSlime(100, 100, spriteArrayForSize(70, 40, 2), facade.createSchool());
			Slime slime2 = facade.createSlime(170, 100, spriteArrayForSize(70, 40, 2), facade.createSchool());
			Mazub alien = facade.createMazub(180, 180, spriteArrayForSize(70, 70, 20));
			facade.setMazub(world2, alien);
			facade.addSlime(world2, slime1);
			facade.addSlime(world2, slime2);
			for(int i=0; i<50;i++){
				facade.advanceTime(world2, 0.2d);
				for(GameObject gameObject:world2.getAllGameObjects()){//death objects are filtered out of this getter
					assertEquals(0, gameObject.getOverlappingGameObjects().size());
					assertFalse(gameObject.isDead());
				}
			}
		}
	}
	
	@Test
	public void testChangeSchool(){
		//setting up a world
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 10, 2, 1, 1, 0, 1);
		for(int i=0;i<10;i++)
			facade.setGeologicalFeature(world, i, 0, 1);//to move normally
		School school1 = facade.createSchool();
		School school2 = facade.createSchool();
		School school3 = facade.createSchool();
		Slime slime1 = facade.createSlime(0, 69, spriteArrayForSize(70, 40, 2), school1);
		Slime slime2 = facade.createSlime(71, 69, spriteArrayForSize(70, 40, 2), school2);
		Slime slime3 = facade.createSlime(142, 69, spriteArrayForSize(70, 40, 2), school3);
		facade.addSlime(world, slime1);
		facade.addSlime(world, slime2);
		facade.addSlime(world, slime3);
		
		//here they don't change school because all schools are equally sized.
		facade.advanceTime(world, 0.0000005d);
		slime1.endMove();slime1.startMove(Direction.RIGHT);
		slime2.endMove();slime2.startMove(Direction.LEFT);
		slime3.endMove();slime3.startMove(Direction.LEFT);
		facade.advanceTime(world, 0.2d);
		assertEquals(school1, slime1.getSchool());assertEquals(100, slime1.getNbHitPoints());
		assertEquals(school2, slime2.getSchool());assertEquals(100, slime2.getNbHitPoints());
		assertEquals(school3, slime3.getSchool());assertEquals(100, slime3.getNbHitPoints());
		
		//here they do change order because school 1 is larger then the other ones.
		Slime slime4 = facade.createSlime(500, 75, spriteArrayForSize(70, 40, 2), school1);
		facade.addSlime(world, slime4);
		facade.advanceTime(world, 0.2d);
		assertEquals(school1, slime1.getSchool());assertEquals(98, slime1.getNbHitPoints());
		assertEquals(school1, slime2.getSchool());assertEquals(101, slime2.getNbHitPoints());
		assertEquals(school1, slime3.getSchool());assertEquals(103, slime3.getNbHitPoints());
		assertEquals(school1, slime4.getSchool());assertEquals(98, slime4.getNbHitPoints());
	}
	
	@Test public void testGameOverSituations(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 5, 2, 1, 1, 4, 1);
		for(int i=0;i<5;i++)
			facade.setGeologicalFeature(world, i, 0, 1);//to move normally
		
		//world ended right from start, player spawned on endPosition
		Mazub alien = facade.createMazub(70*4, 69, JumpingAlienSprites.ALIEN_SPRITESET);
		facade.setMazub(world, alien);
		assertTrue(facade.isGameOver(world));
		assertTrue(facade.didPlayerWin(world));
		
		Mazub alien2 = facade.createMazub(70*3-2, 69, JumpingAlienSprites.ALIEN_SPRITESET);
		facade.setMazub(world, alien2);
		assertFalse(facade.isGameOver(world));
		assertFalse(facade.didPlayerWin(world));
		alien2.startMove(Direction.RIGHT);
		facade.advanceTime(world, 0.2d);
		assertTrue(facade.didPlayerWin(world));
		assertTrue(facade.isGameOver(world));
		
		Mazub alien3 = facade.createMazub(70*3-2, 69, JumpingAlienSprites.ALIEN_SPRITESET);
		facade.setMazub(world, alien3);
		School school = facade.createSchool();
		Slime slime = facade.createSlime(70*2-2, 69, spriteArrayForSize(70, 40, 2), school);
		facade.addSlime(world, slime);
		facade.advanceTime(world, 0.00005d);
		slime.endMove();slime.startMove(Direction.RIGHT);
		facade.advanceTime(world, 0.2d);
		facade.advanceTime(world, 0.2d);
		assertFalse(facade.isGameOver(world));
		assertFalse(facade.didPlayerWin(world));
		facade.advanceTime(world, 0.2d);
		facade.advanceTime(world, 0.2d);//to bypass the immunity time
		assertTrue(facade.isGameOver(world));
		assertFalse(facade.didPlayerWin(world));
	}
	
	@Test
	public void testGetTileOfPosition() throws PositionOutOfBoundsException{
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 10, 2, 1, 1, 0, 1);
		assertArrayEquals(new int[2],world.getTileOfPosition(new double[]{0.6,0.6}));//this function works in coordinates, not pixels.
		assertArrayEquals(new int[]{1,1},world.getTileOfPosition(new double[]{0.7,0.7}));
		
		assertArrayEquals(new int[2],world.getTileOfPosition(new Position(world, new double[]{0.6,0.6})));
		assertArrayEquals(new int[]{1,1},world.getTileOfPosition(new Position(world, new double[]{0.7,0.7})));
	}
	
	@Test
	public void testGetVisibleWindow(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 10, 3, 470, 210, 9, 1);
		for(int i=0;i<10;i++)
			facade.setGeologicalFeature(world, i, 0, 1);//to move normally
		Mazub alien = facade.createMazub(200, 70, JumpingAlienSprites.ALIEN_SPRITESET);
		facade.setMazub(world, alien);
		alien.startMove(Direction.RIGHT);
		assertArrayEquals(new int[]{0,0,469,209}, facade.getVisibleWindow(world));
		facade.advanceTime(world, 0.1d);
		assertArrayEquals(new int[]{9,0,478,209}, facade.getVisibleWindow(world));
		//order: left,bottom,right,top
	}

	@Test
	public void noDeathObjectsInGetAllObjects(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(70, 10, 10, 1, 1, 0, 1);
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				facade.setGeologicalFeature(world, i, j, 1);//to move normally
		for(int i=1;i<9;i++)
			for(int j=1;j<9;j++)
				facade.setGeologicalFeature(world, i, j, 3);//lava
		Mazub alien = facade.createMazub(69, 69, spriteArrayForSize(70, 70, 20));
		Slime slime = facade.createSlime(140, 70, spriteArrayForSize(70, 40, 2), facade.createSchool());
		Slime slime2 = facade.createSlime(140, 140, spriteArrayForSize(70, 40, 2), facade.createSchool());
		Shark shark1 = facade.createShark(210, 70, spriteArrayForSize(70, 40, 2));
		Shark shark2 = facade.createShark(210, 140, spriteArrayForSize(70, 40, 2));
		facade.setMazub(world, alien);
		facade.addShark(world, shark2);
		facade.addShark(world, shark1);
		facade.addSlime(world, slime2);
		facade.addSlime(world, slime);
		for(GameObject gameObject:world.getAllGameObjects())
			assertFalse(gameObject.isDead());
		for(int i=0;i<5;i++){
			facade.advanceTime(world, 0.2d);
			for(GameObject gameObject:world.getAllGameObjects())
				assertFalse(gameObject.isDead());
		}
	}
}

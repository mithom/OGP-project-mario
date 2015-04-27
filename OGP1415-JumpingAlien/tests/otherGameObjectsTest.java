import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.model.*;
import jumpingalien.state.Direction;
import jumpingalien.util.Util;

import org.junit.Test;


public class otherGameObjectsTest {

	@Test
	public void testAddToWorld() {
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(500, 1, 10, 1, 1, 0, 1);
		Mazub alien = facade.createMazub(70, 70, spriteArrayForSize(70, 40, 20));
		facade.setMazub(world, alien);
		Slime slime = facade.createSlime(140, 70, spriteArrayForSize(70, 40, 2), facade.createSchool());
		facade.addSlime(world, slime);
		Shark shark =facade.createShark(210, 70, spriteArrayForSize(70, 40, 2));
		facade.addShark(world, shark);
		Plant plant = facade.createPlant(280, 280, spriteArrayForSize(70, 40, 2));
		facade.addPlant(world, plant);
		assertTrue(world.getAllGameObjects().contains(alien));
		assertTrue(world.getAllGameObjects().contains(plant));
		assertTrue(world.getAllGameObjects().contains(slime));
		assertTrue(world.getAllGameObjects().contains(shark));
	}

	@Test
	public void testStartMove() {
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(500, 3, 2, 1, 1, 0, 1);
		facade.setGeologicalFeature(world, 0, 0, 1);
		Shark shark = facade.createShark(0, 499, spriteArrayForSize(3, 3));
		facade.addShark(world, shark);
		facade.advanceTime(world, 0.0000005d);
		double pos = shark.getPositionX();
		shark.endMove();
		shark.startMove(Direction.RIGHT, false);
		facade.advanceTime(world, 0.1);
		assertEquals(0.0075d+pos , shark.getPositionX(),Util.DEFAULT_EPSILON);
		//0.0075 movement in 0.1s
		
		
		world = facade.createWorld(500, 3, 2, 1, 1, 0, 1);
		facade.setGeologicalFeature(world, 0, 0, 1);
		Slime slime = facade.createSlime(0, 499, spriteArrayForSize(3, 3),facade.createSchool());
		facade.addSlime(world, slime);
		facade.advanceTime(world, 0.0000005d);
		pos = slime.getPositionX();
		slime.endMove();
		slime.startMove(Direction.RIGHT);
		facade.advanceTime(world, 0.1);
		assertEquals(0.0035d+pos , slime.getPositionX(),Util.DEFAULT_EPSILON);
		//0.0075 movement in 0.1s

	}

}

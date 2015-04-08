package jumpingalien.part2.facade;

import java.util.Collection;

import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

//zou zonder implements werken mss, niet in opgave, valt noch te bezien
public class Facade extends jumpingalien.part1.facade.Facade implements IFacadePart2 {
	
	/*//al het volgende zit in de extends part1.facade.Facade
	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		// TODO check old version
		return null;
	}

	@Override
	public int[] getLocation(Mazub alien) {
		// TODO check old version
		return null;
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		// TODO check old version
		return null;
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		// TODO check old version
		return null;
	}

	@Override
	public int[] getSize(GameObject alien) {
		// TODO check old version
		return null;
	}

	@Override
	public Sprite getCurrentSprite(GameObject alien) {
		// TODO check old version
		return null;
	}

	@Override
	public void startJump(Mazub alien) {
		// TODO check old version

	}

	@Override
	public void endJump(Mazub alien) {
		// TODO check old version

	}

	@Override
	public void startMoveLeft(Mazub alien) {
		// TODO check old version

	}

	@Override
	public void endMoveLeft(Mazub alien) {
		// TODO check old version

	}

	@Override
	public void startMoveRight(Mazub alien) {
		// TODO check old version
	}

	@Override
	public void endMoveRight(Mazub alien) {
		// TODO check old version

	}

	@Override
	public void startDuck(Mazub alien) {
		// TODO check old version

	}

	@Override
	public void endDuck(Mazub alien) {
		// TODO check old version

	}*/

	@Override
	public void advanceTime(World world, double dt)throws ModelException {
		try{
			world.advanceTime(dt);
		}catch(IllegalMovementException e){
			throw new ModelException("illegalMovementException");
		}catch(IllegalMazubStateException ex){
			throw new ModelException("illegalMazubState");
		}catch(IllegalTimeException exc){
			throw new ModelException("illegalTime");
		}catch(PositionOutOfBoundsException exce){
			throw new ModelException("positionOutOfBounds" + exce.getLocation()[0] + " ," + exce.getLocation()[1]);
		}

	}

	@Override
	public int getNbHitPoints(GameObject alien) {
		return alien.getNbHitPoints();
	}

	@Override
	public boolean isImmune(GameObject alien) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY){
		try{
			return new World(tileSize,nbTilesX,nbTilesY,visibleWindowWidth,
					visibleWindowHeight,targetTileX,targetTileY);
		}catch(PositionOutOfBoundsException e){
			throw new ModelException("invalid position targetTile");
		}
	};
	
	@Override
	public int[] getWorldSizeInPixels(World world){
		return world.getWorldSizeInPixels();
	};
	
	@Override
	public int getTileLength(World world){
		return world.getTileLenght();
	};
	
	@Override
	public void startGame(World world){
		//TODO implement this function
	};
	
	@Override
	public boolean isGameOver(World world){
		//TODO implement this function
		return world.isGameOver();
	};

	@Override
	public boolean didPlayerWin(World world){
		//TODO implement
		return world.didPlayerWin();
	};
	
	@Override
	public int[] getVisibleWindow(World world){
		return world.getVisibleWindow();
	};
	
	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY){
		return world.getBottomLeftPixelOfTile(tileX, tileY);
	};
	
	@Override
	public 	int[][] getTilePositionsIn(World world, int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		return world.getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
	};
	
	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)throws ModelException{
		try{return world.getGeologicalFeature(pixelX, pixelY);}
		catch(Exception e){
			throw new ModelException("invalid key exception");
		}
	};
	
	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY, int tileType){
		try{
			world.setGeologicalFeature(tileX, tileY, tileType);
		}catch(IndexOutOfBoundsException e){
			throw new ModelException(e.getMessage());
		}
	};
	
	@Override
	public void setMazub(World world, GameObject alien){
		alien.addToWorld(world);
	};
	
	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites){
		try{return new Plant(x,y,sprites);}
		catch(PositionOutOfBoundsException e){
			throw new ModelException("invalid position");
		}
	};
	
	@Override
	public void addPlant(World world, Plant plant){
		world.addPlant(plant);
	};
	
	@Override
	public Collection<Plant> getPlants(World world){
		return world.getPlants(); 
	};
	
	@Override
	public int[] getLocation(Plant plant){
		return plant.getPosition().getPixelPosition();
	};
	
	@Override
	public Sprite getCurrentSprite(Plant plant){
		return plant.getCurrentSprite();
	};
	
	@Override
	public Shark createShark(int x, int y, Sprite[] sprites){
		try{return new Shark(x,y,sprites);}
		catch(PositionOutOfBoundsException e){
			throw new ModelException("invalid position");
		}
	};
	
	@Override
	public void addShark(World world, Shark shark){
		world.addShark(shark);
	};
	
	@Override
	public Collection<Shark> getSharks(World world){
		return world.getSharks();
	};
	
	@Override
	public int[] getLocation(Shark shark){
		return shark.getPosition().getPixelPosition();
	};
	
	@Override
	public Sprite getCurrentSprite(Shark shark){
		return shark.getCurrentSprite();
	};
	
	@Override
	public School createSchool(){
		return new School();
	};
	
	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school){
		try{return new Slime(x,y,sprites,school);}
		catch(PositionOutOfBoundsException e){
			throw new ModelException("invalid position");
		}
	};
	
	@Override
	public void addSlime(World world, Slime slime){
		slime.addToWorld(world);
	};
	
	@Override
	public Collection<Slime> getSlimes(World world){
		return world.getSlimes();
	};
	
	@Override
	public int[] getLocation(Slime slime){
		return slime.getPosition().getPixelPosition();
	};
	
	@Override
	public Sprite getCurrentSprite(Slime slime){
		return slime.getCurrentSprite();
	};
	
	@Override
	public School getSchool(Slime slime){
		return slime.getSchool();
	};
}

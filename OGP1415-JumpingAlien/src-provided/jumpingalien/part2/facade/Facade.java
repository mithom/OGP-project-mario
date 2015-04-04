package jumpingalien.part2.facade;

import java.util.ArrayList;
import java.util.Collection;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Mazub alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getSize(GameObject alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(GameObject alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startJump(Mazub alien) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endJump(Mazub alien) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startMoveLeft(Mazub alien) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endMoveLeft(Mazub alien) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startMoveRight(Mazub alien) {
		// TODO Auto-generated method stub
	}

	@Override
	public void endMoveRight(Mazub alien) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startDuck(Mazub alien) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endDuck(Mazub alien) {
		// TODO Auto-generated method stub

	}*/

	@Override
	public void advanceTime(World world, double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNbHitPoints(GameObject alien) {
		// TODO Auto-generated method stub
		return 0;
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
		//TODO implement this function
		return new int[]{};
	};
	
	@Override
	public int getTileLength(World world){
		//TODO implement this function
		return 0;
	};
	
	@Override
	public void startGame(World world){
		//TODO implement this function
	};
	
	@Override
	public boolean isGameOver(World world){
		//TODO implement this function
		return true;
	};

	@Override
	public boolean didPlayerWin(World world){
		//TODO implement this function
		return false;
	};
	
	@Override
	public int[] getVisibleWindow(World world){
		//TODO implement this function
		return new int[]{};
	};
	
	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY){
		return world.getBottomLeftPixelOfTile(tileX, tileY);
	};
	
	@Override
	public 	int[][] getTilePositionsIn(World world, int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		//TODO implement this function
		return new int[][]{};
	};
	
	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException{
		//TODO implement this function
		return 0;
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
		//TODO implement this function
	};
	
	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites){
		//TODO implement this function
		try{return new Plant(x,y,sprites);}
		catch(PositionOutOfBoundsException e){
			throw new ModelException("invalid position");
		}
	};
	
	@Override
	public void addPlant(World world, Plant plant){
		//TODO implement this function
	};
	
	@Override
	public Collection<Plant> getPlants(World world){
		//TODO implement this function
		//can be of other collection then ArrayList
		return new ArrayList<Plant>(); 
	};
	
	@Override
	public int[] getLocation(Plant plant){
		//TODO implement this function
		return new int[]{};
	};
	
	@Override
	public Sprite getCurrentSprite(Plant plant){
		//TODO implement this function
		return new Sprite("hallo", 0, 0);
	};
	
	@Override
	public Shark createShark(int x, int y, Sprite[] sprites){
		//TODO implement this function
		try{return new Shark(x,y,sprites);}
		catch(PositionOutOfBoundsException e){
			throw new ModelException("invalid position");
		}
	};
	
	@Override
	public void addShark(World world, Shark shark){
		//TODO implement this function
	};
	
	@Override
	public Collection<Shark> getSharks(World world){
		//TODO implement this fucntion
		return new ArrayList<Shark>();
	};
	
	@Override
	public int[] getLocation(Shark shark){
		//TODO implement this function
		return new int[]{};
	};
	
	@Override
	public Sprite getCurrentSprite(Shark shark){
		//TODO implment this function
		return new Sprite("hallo", 0, 0);
	};
	
	@Override
	public School createSchool(){
		//TODO implement this function
		return new School();
	};
	
	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school){
		//TODO implement this function
		try{return new Slime(x,y,sprites,school);}
		catch(PositionOutOfBoundsException e){
			throw new ModelException("invalid position");
		}
	};
	
	@Override
	public void addSlime(World world, Slime slime){
		//TODO implement this function
	};
	
	@Override
	public Collection<Slime> getSlimes(World world){
		//TODO implement this function
		return new ArrayList<Slime>();
	};
	
	@Override
	public int[] getLocation(Slime slime){
		//TODO implement this function
		return new int[]{};
	};
	
	@Override
	public Sprite getCurrentSprite(Slime slime){
		//TODO implement this function
		return new Sprite("hallo", 0, 0);
	};
	
	@Override
	public School getSchool(Slime slime){
		//TODO implement this function
		return new School();
	};
}

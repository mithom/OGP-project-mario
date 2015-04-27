package jumpingalien.model;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.exception.IllegalMazubStateException;
import jumpingalien.exception.IllegalMovementException;
import jumpingalien.exception.IllegalSizeException;
import jumpingalien.exception.IllegalTimeException;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.GameObject;
import jumpingalien.model.gameObject.Position;
import jumpingalien.model.gameObject.GeologicalFeature;

public class World {
	private final int height;//in pixels
	private final int width;//in pixels
	final int viewHeight;//in pixels
	final int viewWidth;//in pixels
	private Position cameraLocation;//position is in m
	private final Position targetTile;//position is in m
	private GeologicalFeature[][] tileTypes;
	private final int tileSize;//in pixels
	private ArrayList<Shark> sharks = new ArrayList<Shark>();
	private ArrayList<Plant> plants = new ArrayList<Plant>();
	private ArrayList<School> schools= new ArrayList<School>();
	private ArrayList<Slime> slimes = new ArrayList<Slime>();
	private boolean terminated = false;
	private Mazub mazub;
	
	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) throws PositionOutOfBoundsException{
		height = tileSize * nbTilesY;
		width = tileSize * nbTilesX;
		viewHeight = visibleWindowHeight;
		viewWidth = visibleWindowWidth;
		tileTypes = new GeologicalFeature[nbTilesX][nbTilesY];
		for(GeologicalFeature[] row: tileTypes){
			Arrays.fill(row, GeologicalFeature.air);
		}
		this.tileSize = tileSize;
		targetTile = new Position(this, new double[]{targetTileX*tileSize/100.0d,targetTileY*tileSize/100.0d});
		cameraLocation = new Position(this, new double[]{0,0});
	}
	
	public void removeGameObject(GameObject gameObject){
		if(gameObject instanceof Plant)
			plants.remove(gameObject);
		if(gameObject instanceof Slime){
			slimes.remove(gameObject);
			((Slime)gameObject).getSchool().removeSlime((Slime)gameObject);
			((Slime)gameObject).setSchool(null);
		}
		if(gameObject instanceof Mazub)
			mazub = null;
		if(gameObject instanceof Shark)
			sharks.remove(gameObject);
	}
	
	public void removePlant(Plant plant){
		plants.remove(plant);
	}
	
	@Basic
	public int getHeight(){
		return height;
	}
	
	public void addMazub(Mazub mazub){
		if(mazub.getWorld()==this)
			this.mazub = mazub;
	}
	
	@Basic
	public int getWidth(){
		return width;
	}
	
	public void setGeologicalFeature(int tileX, int tileY, int tileType)throws IndexOutOfBoundsException{
		tileTypes[tileX][tileY] = GeologicalFeature.numberTypeToGeologicalFeature(tileType);
		//System.out.println("set tile ["+tileX+","+tileY+"] to "+tileType);
		//nominaal/defensief/totaal?????
	}
	
	public int getGeologicalFeature(int pixelX, int pixelY )throws InvalidKeyException{
		if(pixelX % tileSize != 0 || pixelY %tileSize !=0)
			throw new InvalidKeyException();
		return tileTypes[pixelX/tileSize][pixelY/tileSize].getEquivalentNumberType();
	}
	
	/*
	 * @param position the pixel position
	 */
	public int getGeologicalFeature(int[] position){
		return tileTypes[position[0]/tileSize][position[1]/tileSize].getEquivalentNumberType();
	}
	
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY){
		return new int[]{tileX*tileSize,tileY*tileSize};
	}
	
	public int[] getTopRightPixelOfTile(int tileX, int tileY){
		return new int[]{(tileX+1)*tileSize,(tileY+1)*tileSize};
	}
	
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		ArrayList<int[]> tilePositions = new ArrayList<int[]>();
		for(int rowPos = (pixelBottom/tileSize)*tileSize; rowPos <= pixelTop; rowPos+=tileSize){
			for(int colPos = (pixelLeft/tileSize)*tileSize;colPos <= pixelRight;colPos+=tileSize){
				if(getHeight()-getTileLenght()>=rowPos && getWidth()-getTileLenght()>=colPos)
					tilePositions.add(new int[]{colPos/tileSize,rowPos/tileSize});
			}
		}
		return tilePositions.toArray(new int[tilePositions.size()][]);
	}
	
	public int[] getVisibleWindow(){
		int[] visibleWindow = new int[4];
		visibleWindow[0]=cameraLocation.getPixelPosition()[0];
		visibleWindow[1]=cameraLocation.getPixelPosition()[1];
		visibleWindow[2] = visibleWindow[0]+ viewWidth;
		visibleWindow[3]= visibleWindow[1] + viewHeight;
		return visibleWindow;
		//order: left,bottom,right,top
	}
	
	public ArrayList<Plant> getPlants(){
		return new ArrayList<Plant>(plants);
	}
	
	public void addPlant(Plant plant){
		if((!hasAsPlant(plant)) && canHaveAsPlant(plant)){
			plants.add(plant);
		}
	}
	
	public boolean hasAsPlant(Plant plant){
		return plants.contains(plant);
	}
	
	public boolean isTerminated(){
		return terminated;
	}
	
	public boolean canHaveAsPlant(Plant plant){
		if((!plant.isTerminated()) && plant.getWorld() == null || plant.getWorld() == this
				&& !this.isTerminated())
			return true;
		return false;
	}
	
	public ArrayList<Shark> getSharks(){
		return new ArrayList<Shark>(sharks);
	}
	
	public void addShark(Shark shark){
		if(!hasAsShark(shark))
			sharks.add(shark);
	}
	
	public boolean hasAsShark(Shark shark){
		return sharks.contains(shark);
	}

	public void addSchool(School school){
		if(schools.size()<10 && !schools.contains(school)){
			schools.add(school);
			school.addWorld(this);
		}
	}
	
	public ArrayList<School> getSchools(){
		return new ArrayList<School>(schools);
	}
	
	public ArrayList<Slime> getSlimes2(){
		ArrayList<Slime> slimes= new ArrayList<Slime>();
		for(School school : schools){
			slimes.addAll(school.getSlimes());
		}
		return slimes;
	}
	
	public ArrayList<Slime> getSlimes(){
		return new ArrayList<Slime>(slimes);
	}
	
	public int getTileLenght(){
		return this.tileSize;
	}
	
	public void advanceTime(double dt)throws IllegalMovementException,IllegalMazubStateException,IllegalTimeException,PositionOutOfBoundsException, NullPointerException, IllegalSizeException{
		ArrayList<GameObject> worldObjects = new ArrayList<GameObject>();
		if(isValidMazub())
			worldObjects.add(mazub);
		worldObjects.addAll(plants);
		worldObjects.addAll(getSlimes());
		worldObjects.addAll(sharks);
		
		for(GameObject worldObject:worldObjects){
			worldObject.advanceTime(dt);
		}
	}
	
	public boolean isValidMazub(){
		return mazub != null && !mazub.isTerminated();
	}
	
	public int[] getWorldSizeInPixels(){
		return new int[]{width,height};
	}
	
	public void addSlime(Slime slime){
		if(schools.contains(slime.getSchool())){
			slimes.add(slime);
		}
	}
	
	public boolean didPlayerWin(){
		if(mazub == null)
			return false;
		/*if(Arrays.equals(getTileOfPosition(targetTile), getTileOfPosition(mazub.getPosition()))){
			return true;
		}*/
		double[] perimeters = mazub.getPerimeters();
		int [][] occupied_tiles = getTilePositionsIn((int)(perimeters[0]*100), (int)(perimeters[1]*100), (int)(perimeters[2]*100),(int)(perimeters[3]*100));
		for(int[] tile:occupied_tiles){
			if(Arrays.equals(getTileOfPosition(targetTile),tile))
				return true;
		}
		return false;
	}
	
	public int[] getTileOfPosition(Position position){
		return getTileOfPosition(position.getPositions());
	}
	
	public int[]getTileOfPosition(double[] position){
		return new int[]{(int)(position[0]*100)/tileSize, (int)(position[1]*100)/tileSize};
	}
	
	public boolean isGameOver(){
		if(mazub == null || didPlayerWin())
			return true;
		return false;
	}
	public void moveWindowTo(double Left, double Bottom)throws PositionOutOfBoundsException{
		cameraLocation = new Position(this, new double[]{Math.min(Math.max(0,Left),(getWidth()-viewWidth-1)/100.0d),Math.min(Math.max(0,Bottom),(getHeight()-viewHeight-1)/100.0d)});
	}
	
	public ArrayList<GameObject> getAllGameObjects(){
		ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
		gameObjects.addAll(getPlants());
		gameObjects.addAll(getSharks());
		gameObjects.addAll(getSlimes());
		if(mazub != null)
			gameObjects.add(mazub);
		return gameObjects;
	}
	
	public void removeSchool(School school){
		if(schools.contains(school)){
			schools.remove(school);
		}
	}
}

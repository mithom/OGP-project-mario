package jumpingalien.model;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.gameObject.Position;
import jumpingalien.model.gameObject.GeologicalFeature;

public class World {
	private final int height;
	private final int width;
	private final int viewHeight;
	private final int viewWidth;
	private final Position targetTile;
	private GeologicalFeature[][] tileTypes;
	private final int tileSize;
	private ArrayList<Shark> sharks = new ArrayList<Shark>();
	private ArrayList<Plant> plants = new ArrayList<Plant>();
	private ArrayList<School> schools= new ArrayList<School>();
	private boolean terminated = false;
	
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
	}
	
	@Basic
	public int getHeight(){
		return height;
	}
	
	@Basic
	public int getWidth(){
		return width;
	}
	
	public void setGeologicalFeature(int tileX, int tileY, int tileType)throws IndexOutOfBoundsException{
		tileTypes[tileX][tileY] = GeologicalFeature.numberTypeToGeologicalFeature(tileType);
		//nominaal/defensief/totaal?????
	}
	
	public int getGeologicalFeature(int pixelX, int pixelY )throws InvalidKeyException{
		if(pixelX % tileSize != 0 || pixelY %tileSize !=0)
			throw new InvalidKeyException();
		return tileTypes[pixelX/tileSize][pixelY/tileSize].getEquivalentNumberType();
	}
	
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY){
		return new int[]{tileX*tileSize,tileY*tileSize};
	}
	
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){//lang leve integer division
		int[][] tilePositions = new int[((pixelRight-pixelLeft)/tileSize)*((pixelTop-pixelBottom)/tileSize)][2];
		for(int TileRow=pixelBottom/tileSize;TileRow<pixelTop/tileSize;TileRow++){
			for(int TileCollumn = pixelLeft/tileSize;TileCollumn<pixelRight/tileSize;TileCollumn++){
				tilePositions[TileRow*((pixelRight-pixelLeft)/tileSize)+TileCollumn]=new int[]{TileCollumn,TileRow};
			}
		}
		return tilePositions;
	}
	
	public int[] getVisibleWindow(){
		//order: left,bottom,right,top
		//TODO implement this function
		return new int[]{};
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
		if(!hasAsShark(shark) && canHaveAsShark(shark))
			sharks.add(shark);
	}
	
	public boolean hasAsShark(Shark shark){
		return sharks.contains(shark);
	}
	
	public boolean canHaveAsShark(Shark shark){
		if(!isTerminated() && !shark.isTerminated() && shark.getWorld()==null)
			return true;
		return false;
	}
	
	public void addSchool(School school){
		//TODO implement this function
	}
	
	public ArrayList<Slime> getSlimes(){
		ArrayList<Slime> slimes= new ArrayList<Slime>();
		for(School school : schools){
			slimes.addAll(school.getSlimes());
		}
		return slimes;
	}
}

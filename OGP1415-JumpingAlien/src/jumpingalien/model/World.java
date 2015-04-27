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

/**
 * World is a class representing the gameworld in which a game shall be played.
 * 
 * @Invar 	the world shall only contain objects that are alive.
 * 			|for(GameObject gameObject:getAllGameObjects())
 * 			|		gameObject.isDead() == false
 * @Invar	each world has a target that is located inside the world
 * 			|Postion.isValidCoordinate(new double[]{targetTileX*tileSize/100.0d,targetTileY*tileSize/100.0d})
 * @Invar	the visible window shall always be inside of the gameWorld
 * 			|getVisibleWindow()[0]>=0
 * 			|getVisibleWindow()[1]>=0
 * 			|getVisiblewindow()[2]<=getWidth()-1
 * 			|getVisibleWindow()[3]<=getHeigth()-1
 */
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
	/**
	 * 
	 * @param gameObject | the gameObject that needs to be removed
	 * @Post if gameObject is a plant, it needs to be removed from plants
	 * 			|plants.remove(gameObject)
	 * @Post if gameObject is a slime, it needs to be removed from slimes and from the school it's in
	 * 			| slimes.remove(gameObject)
				| ((Slime)gameObject).getSchool().removeSlime((Slime)gameObject)
				| ((Slime)gameObject).setSchool(null)
	 * @Post if gameObject is a Mazub, it needs to be removed 
	 * 			| mazub=null
	 * @Post if gameObject is a Shark, it needs to be removed from sharks
	 * 			| sharks.remove(gameObject)
	 */
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

	/**
	 * returns the height of the world
	 * @return | height
	 */
	
	@Basic
	public int getHeight(){
		return height;
	}
	
	/**
	 * 
	 * @param mazub | the mazub that needs to be added to the world
	 * @Post if the given mazub belongs to this world, it will be added
	 * 			| if(mazub.getWorld()==this)
	 *			|	then this.mazub = mazub
	 */
	public void addMazub(Mazub mazub){
		if(mazub.getWorld()==this)
			this.mazub = mazub;
	}
	
	/**
	 * returns the width of the world
	 * @return | width
	 */
	
	@Basic
	public int getWidth(){
		return width;
	}
	

	/**
	 * Modify the geological type of a specific tile in the given world to a
	 * given type.
	 * 
	 * @param world
	 *            The world in which the geological type of a tile needs to be
	 *            modified
	 * @param tileX
	 *            The x-position x_T of the tile for which the type needs to be
	 *            modified
	 * @param tileY
	 *            The y-position y_T of the tile for which the type needs to be
	 *            modified
	 * @param tileType
	 *            The new type for the given tile, where
	 *            <ul>
	 *            <li>the value 0 is provided for an <b>air</b> tile;</li>
	 *            <li>the value 1 is provided for a <b>solid ground</b> tile;</li>
	 *            <li>the value 2 is provided for a <b>water</b> tile;</li>
	 *            <li>the value 3 is provided for a <b>magma</b> tile.</li>
	 *            </ul>
	 */
	public void setGeologicalFeature(int tileX, int tileY, int tileType)throws IndexOutOfBoundsException{
		tileTypes[tileX][tileY] = GeologicalFeature.numberTypeToGeologicalFeature(tileType);
		//System.out.println("set tile ["+tileX+","+tileY+"] to "+tileType);
		//nominaal/defensief/totaal?????
	}
	
	/**
	 * Returns the geological feature of the tile with its bottom left pixel at
	 * the given position.
	 * @param pixelX
	 *            The x-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned.
	 * @param pixelY
	 *            The y-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned.
	 * 
	 * @return The type of the tile with the given bottom left pixel position,
	 *         where
	 *         <ul>
	 *         <li>the value 0 is returned for an <b>air</b> tile;</li>
	 *         <li>the value 1 is returned for a <b>solid ground</b> tile;</li>
	 *         <li>the value 2 is returned for a <b>water</b> tile;</li>
	 *         <li>the value 3 is returned for a <b>magma</b> tile.</li>
	 *         </ul>
	 * 
	 * @note This method must return its result in constant time.
	 * 
	 * @throw InvalidKeyException if the given position does not correspond to the
	 *        bottom left pixel of a tile.
	 */
	public int getGeologicalFeature(int pixelX, int pixelY )throws InvalidKeyException{
		if(pixelX % tileSize != 0 || pixelY %tileSize !=0)
			throw new InvalidKeyException();
		return tileTypes[pixelX/tileSize][pixelY/tileSize].getEquivalentNumberType();
	}
	
	/**
	 * Returns the geological feature of the tile with its bottom left pixel at
	 * the given position.
	 * @param position
	 *            The x-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned in the first slot
	 *            The y-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned in the second slot
	 * 
	 * @return The type of the tile with the given bottom left pixel position,
	 *         where
	 *         <ul>
	 *         <li>the value 0 is returned for an <b>air</b> tile;</li>
	 *         <li>the value 1 is returned for a <b>solid ground</b> tile;</li>
	 *         <li>the value 2 is returned for a <b>water</b> tile;</li>
	 *         <li>the value 3 is returned for a <b>magma</b> tile.</li>
	 *         </ul>
	 * 
	 * @note This method must return its result in constant time.
	 */
	public int getGeologicalFeature(int[] position){
		return tileTypes[position[0]/tileSize][position[1]/tileSize].getEquivalentNumberType();
	}
	/**
	 * Returns the bottom left pixel coordinate of the tile at the given tile
	 * position.
	 * @param tileX
	 *            The x-position x_T of the tile
	 * @param tileY
	 *            The y-position y_T of the tile
	 * @return An array which contains the x-coordinate and y-coordinate of the
	 *         bottom left pixel of the given tile, in that order.
	 */
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY){
		return new int[]{tileX*tileSize,tileY*tileSize};
	}
	
	/**
	 * Returns the bottom left pixel coordinate of the tile at the given tile
	 * position.
	 * @param tileX
	 *            The x-position x_T of the tile
	 * @param tileY
	 *            The y-position y_T of the tile
	 * @return An array which contains the x-coordinate and y-coordinate of the
	 *         top right pixel of the given tile, in that order.
	 */
	
	public int[] getTopRightPixelOfTile(int tileX, int tileY){
		return new int[]{(tileX+1)*tileSize,(tileY+1)*tileSize};
	}
	
	/**
	 * Returns the tile positions of all tiles within the given rectangular
	 * region.
	 * 
	 * @param pixelLeft
	 *            The x-coordinate of the left side of the rectangular region.
	 * @param pixelBottom
	 *            The y-coordinate of the bottom side of the rectangular region.
	 * @param pixelRight
	 *            The x-coordinate of the right side of the rectangular region.
	 * @param pixelTop
	 *            The y-coordinate of the top side of the rectangular region.
	 * 
	 * @return An array of tile positions, where each position (x_T, y_T) is
	 *         represented as an array of 2 elements, containing the horizontal
	 *         (x_T) and vertical (y_T) coordinate of a tile in that order.
	 *         The returned array is ordered from left to right,
	 *         bottom to top: all positions of the bottom row (ordered from
	 *         small to large x_T) precede the positions of the row above that.
	 * 
	 */
	
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		ArrayList<int[]> tilePositions = new ArrayList<int[]>();
		for(int rowPos = (pixelBottom/tileSize)*tileSize; rowPos <= pixelTop; rowPos+=tileSize){
			for(int colPos = (pixelLeft/tileSize)*tileSize;colPos <= pixelRight;colPos+=tileSize){
				if(getHeight()-getTileLength()>=rowPos && getWidth()-getTileLength()>=colPos)
					tilePositions.add(new int[]{colPos/tileSize,rowPos/tileSize});
			}
		}
		return tilePositions.toArray(new int[tilePositions.size()][]);
	}
	
	/**
	 * Return the coordinates of the rectangular visible window that moves
	 * together with Mazub.
	 * 
	 * @return The pixel coordinates of the visible window, in the order
	 *         <b>left, bottom, right, top</b>.
	 */
	
	public int[] getVisibleWindow(){
		int[] visibleWindow = new int[4];
		visibleWindow[0]=cameraLocation.getPixelPosition()[0];
		visibleWindow[1]=cameraLocation.getPixelPosition()[1];
		visibleWindow[2] = visibleWindow[0]+ viewWidth-1;
		visibleWindow[3]= visibleWindow[1] + viewHeight-1;
		return visibleWindow;
		//order: left,bottom,right,top
	}
	
	/**
	 * Returns all the plants currently located in the world.
	 * 
	 * @return All plants that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each plant may only be returned once.
	 */
	
	public ArrayList<Plant> getPlants(){
		return new ArrayList<Plant>(plants);
	}
	
	/**
	 * Add the given plant as a game object to the  world.
	 * 
	 * @param plant
	 *            The plant that needs to be added to the world.
	 */
	
	public void addPlant(Plant plant){
		if((!hasAsPlant(plant)) && canHaveAsPlant(plant)){
			plants.add(plant);
		}
	}
	/**
	 * 
	 * @param plant | the plant that needs to be checked
	 * @return return true if the world contains the given plant. Else return false
	 * 			|plants.contains(plant)
	 */
	public boolean hasAsPlant(Plant plant){
		return plants.contains(plant);
	}
	
	/**
	 * returns if it is terminated
	 * @return | terminated
	 */
	public boolean isTerminated(){
		return terminated;
	}
	
	/**
	 * 
	 * @param plant |the plant that needs to be checked
	 * @return true if the world can have the given plant as a plant. This means that the plant isn't terminated 
	 * 		   and doesn't have a world yet (or the world contains the plant) and the world isn't terminated yet
	 * 				| if (!plant.isTerminated()) && plant.getWorld() == null || plant.getWorld() == this
					|		&& !this.isTerminated())
					|				then true		
					|				
					| else 				 false
	 */
	public boolean canHaveAsPlant(Plant plant){
		if((!plant.isTerminated() || !plant.isDead()) && plant.getWorld() == null || plant.getWorld() == this
				&& !this.isTerminated())
			return true;
		return false;
	}
	/**
	 * Returns all the sharks currently located in the world.
	 * 
	 * @return All sharks that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each shark may only be returned once.
	 */
	public ArrayList<Shark> getSharks(){
		return new ArrayList<Shark>(sharks);
	}
	
	/**
	 * Add the given shark as a game object to the world.
	 * 
	 * @param shark
	 *            The shark that needs to be added to the world.
	 */
	
	public void addShark(Shark shark){
		if(!hasAsShark(shark))
			sharks.add(shark);
	}
	
	/**
	 * 
	 * @param shark | the shark that needs to be checked
	 * @return return true if the world contains the given shark. Else return false
	 * 			|sharks.contains(plant)
	 */
	
	public boolean hasAsShark(Shark shark){
		return sharks.contains(shark);
	}
	
	/**
	 * Add the given school to the world.
	 * 
	 * @param school
	 *            The school that needs to be added to the world.
	 */
	public void addSchool(School school){
		if(schools.size()<10 && !schools.contains(school)){
			schools.add(school);
			school.addWorld(this);
		}
	}
	
	/**
	 * Returns the current schools in the world. 
	 * @return The schools of the world | ArrayList<School>(schools)
	 */
	public ArrayList<School> getSchools(){
		return new ArrayList<School>(schools);
	}
	
	/**
	 * Returns all the slimes currently located in the world.
	 * 
	 * @return All slimes that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each slime may only be returned once.
	 */
	
	public ArrayList<Slime> getSlimes2(){
		ArrayList<Slime> slimes= new ArrayList<Slime>();
		for(School school : schools){
			slimes.addAll(school.getSlimes());
		}
		return slimes;
	}
	/**
	 * Returns all the slimes currently located in the world.
	 * 
	 * @return All slimes that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each slime may only be returned once.
	 */
	
	public ArrayList<Slime> getSlimes(){
		return new ArrayList<Slime>(slimes);
	}
	/**
	 * Returns the length of a square tile side in the world.
	 * 
	 * @return The length of a square tile side, expressed as a number of
	 *         pixels.
	 */
	@Basic
	public int getTileLength(){
		return this.tileSize;
	}
	
	//no documentation needed
	
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
		return mazub != null && !mazub.isTerminated() && !mazub.isDead();
	}
	
	/**
	 * Returns the size of the game world, in number of pixels.
	 * 
	 * @return The size of the game world, in pixels, as an array of two
	 *         elements: width (X) and height (Y), in that order.
	 */
	public int[] getWorldSizeInPixels(){
		return new int[]{width,height};
	}
	
	/**
	 * Add the given slime as a game object to the world.
	 * 
	 * @param slime
	 *            The slime that needs to be added to the world.
	 */
	public void addSlime(Slime slime){
		if(schools.contains(slime.getSchool())){
			slimes.add(slime);
		}
	}
	
	/**
	 * Returns whether the game played in the world has finished and the
	 * player has won. The player wins when Mazub has reached the target tile.
	 * 
	 * @return true if the game is over and the player has won; false otherwise.
	 */
	
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
	
	/**
	 * 
	 * @param position |the position in the world of which the tile needs to be searched
	 * @return |getTileOfPosition(position.getPositions())
	 */
	public int[] getTileOfPosition(Position position){
		return getTileOfPosition(position.getPositions());
	}
	
	/**
	 * @param position |the position in the world of which the tile needs to be searched
	 * @return |int[]{(int)(position[0]*100)/tileSize, (int)(position[1]*100)/tileSize}
	 */
	
	public int[]getTileOfPosition(double[] position){
		return new int[]{(int)(position[0]*100)/tileSize, (int)(position[1]*100)/tileSize};
	}
	
	/**
	 * Returns whether the game, played in the game world, is over.
	 * The game is over when Mazub has died, or has reached the target tile.
	 * 
	 * @return true if the game is over, false otherwise.
	 */
	
	public boolean isGameOver(){
		if(mazub == null || didPlayerWin())
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param Left | the most left position to which the window needs to be moved
	 * @param Bottom | the lowest position to which the window needs to be moved
	 * @throws PositionOutOfBoundsException		
	 * 			the window has an illegal position
	 * 
	 */
	public void moveWindowTo(double Left, double Bottom)throws PositionOutOfBoundsException{
		cameraLocation = new Position(this, new double[]{Math.min(Math.max(0,Left),(getWidth()-viewWidth)/100.0d),Math.min(Math.max(0,Bottom),(getHeight()-viewHeight)/100.0d)});//getHeight 1 to big, but viewHeight too, it compensates
	}
	
	/**
	 *  
	 * @return an array containing all the gameobjects
	 */
	public ArrayList<GameObject> getAllGameObjects(){
		ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
		gameObjects.addAll(getPlants());
		gameObjects.addAll(getSharks());
		gameObjects.addAll(getSlimes());
		if(mazub != null)
			gameObjects.add(mazub);
		return gameObjects;
	}
	
	/**
	 * 
	 * @param school | the shool that needs to be removed
	 * @Post the given school is removed
	 * 			|schools.contains(school)==false
	 */
	
	public void removeSchool(School school){
		if(schools.contains(school)){
			schools.remove(school);
		}
	}
}

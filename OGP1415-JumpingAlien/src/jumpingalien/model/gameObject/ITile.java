package jumpingalien.model.gameObject;

/**
 * A class representing a tile from the gameWorld
 *
 */
public interface ITile {
	
	/**
	 * true if the ITile is passable for other gameObjects
	 * false if other gameObjects should bounce against this tile.
	 */
	public boolean isPassable();
	
	/**
	 * true if this ITile is a water tile, false otherwise
	 */
	public boolean isWater();
	
	/**
	 * true if this ITile is a magma tile, false otherwise
	 */
	public boolean isMagma();
	
	/**
	 * true if this ITile is an air tile, false otherwise
	 */
	public boolean isAir();
	
	/**
	 * returns and integer number corresponding to the geological number of the ITile. 
	 *<ul>
	 *<li>the value 0 is returned for an <b>air</b> ITile;</li>
	 *<li>the value 1 is returned for a <b>solid ground</b> ITile;</li>
	 *<li>the value 2 is returned for a <b>water</b> ITile;</li>
	 *<li>the value 3 is returned for a <b>magma</b> ITile.</li>
	 *</ul>
	 */
	public int getGeologicalFeatureNr();
	
	/**
	 * returns the geologicalFeature of the ITile
	 */
	public GeologicalFeature getGeologicalFeature();
}

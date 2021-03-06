package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of bullets for the game asteroids.
 * 
 * @invar	The ship associated with this bullet must be a valid ship.
 * 			| hasProperShip()
 * 
 * @version  1.2
 * @author   Frederik Van Eeghem (1st master Mathematical engineering), 
 *		 	 Pieter Lietaert (1st master Mathematical engineering)
 */

public class Bullet extends SpatialElement{
	/**
	 * Initialize this new bullet.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new bullet.
	 * @param 	radius
	 * 			The radius for this new bullet.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new bullet.
	 * @param 	maxSpeed
	 * 			The maximum allowed speed for this new bullet.
	 * @param	mass
	 * 			The mass for this new bullet.
	 * @param	ship
	 * 			The ship that is the source of the bullet.
	 * @post	The given Ship is set as the source of this bullet.
	 * 			| this.getShip() == ship
	 * @effect	This new bullet is initialized as a spatial element with the given position,
	 * 			radius, velocity, maximum speed and mass derived from the size and density 
	 * 			of the bullet.
	 * 			| let mass= 4/3*PI*radius^3*getMassDensity()
	 * 			| in 
	 * 			| super(position, radius, velocity, maxSpeed, mass)
	 * @throws	IllegalArgumentException
	 * 			The given ship can not be added as source of this bullet.
	 * 			| !canHaveAsShip(shooter)
	 */
	@Raw
	public Bullet(Vector2D position, double radius, Vector2D velocity, double maxSpeed, Ship shooter)
	throws IllegalArgumentException{
		super(position,radius,velocity,maxSpeed,4/3*Math.PI*Math.pow(radius,3)*massDensity);
		if(!canHaveAsShip(shooter))
			throw new IllegalArgumentException("Invalid bullet source.");
		this.ship = shooter;
	}
	
	/**
     * Get the ship that is the owner of this bullet.
     */
	public Ship getShip() {
		return this.ship;
	}
	
	/**
	 * Check whether this bullet can have the given ship as its owner. 
	 * 
	 * @param  	ship
	 * 		   	The ship to check.
	 * @return  True if and only is the given ship is effective.
	 * 			| result == (ship != null)
	 */
	public boolean canHaveAsShip(Ship ship){
		return ship != null;
	}

	/**
     * Check whether this bullet has a proper ship.
     * 
     * @return  True if and only if the ship associated with this bullet is effective.
     * 			| this.ship != null
     */
	public boolean hasProperShip() {
		return this.ship != null;
	}
	
	/**
     * The ship that is the owner of this bullet.
     */
	private final Ship ship;

	/**
	 * Get the mass density of this bullet.
	 */
	@Basic
	public static double getMassDensity(){
		return massDensity;
	}

	/**
	 * The mass density of this bullet expressed in kg/km^3
	 */
	private static final double massDensity = 7.8*Math.pow(10,12);

	/**
	 * Check whether this bullet has already bounced of a wall.
	 */
	@Basic
	public boolean getHasBounced(){
		return hasBounced;
	}

	/**
	 * Bounce the bullet of a wall.
	 * 
	 * @post	| (new this).hasBounced() == true
	 */
	// No setter necessary because physically not logical to set back to false 
	// and private setter would only be used in this method.
	public void bounce(){
		hasBounced = true;
	}
	
	/**
	 * Variable registering whether or not this bullet
	 * has bounced of wall.
	 */
	private boolean hasBounced = false;
}

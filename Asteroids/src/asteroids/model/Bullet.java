package asteroids.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of ships for the game asteroids.
 * 
 * @version  1.1
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
	 * @effect	This new bullet is initialized as a spatial element with the given position,
	 * 			radius, velocity, maximum speed and mass.
	 * 			| super(position, radius, velocity, maxSpeed, mass)
	 */
	@Raw
	public Bullet(Vector2D position, double radius, Vector2D velocity, double maxSpeed, double mass)
	throws IllegalArgumentException, NullPointerException{
		super(position,radius,velocity,maxSpeed,mass);
	}
	
	/**
	 * Check whether this bullet can have the given ship as its owner. 
	 * 
	 * @param  ship
	 * 		   The ship to check.
	 * @return  | ship != null
	 */
	// Maar je zet ship wel op null als je element dood gaat? Je zou dit toch moete checke in setAsShip maar dan gaat die niet meer?
	// Tenzij je ook checked of isTerminated in setAsShip (zoals nu geïmplementeerd.)
	public boolean canHaveAsShip(Ship ship){
		return ship != null;
	}

	/**
     * Check whether this bullet has a proper ship.
     * 
     * @return  | this.ship != null
     */
	public boolean hasProperShip() {
		return this.ship != null;
	}
	
	/**
     * Return the ship that is the owner of this bullet.
     * 
     * @return  | this.ship
     */
	public Ship getShip() {
		return this.ship;
	}

	/**
     * Set the given ship as the owner for this bullet.
     * 
     * @post  	| this.getShip() == ship
     * @throws	IllegalArgumentException
     * 			| canHaveAsShip(ship)
     */
	public void setShip(Ship ship) throws IllegalArgumentException {
		if ((!this.isTerminated()) && (!canHaveAsShip(ship))) {
			throw new IllegalArgumentException("Given ship is not a valid ship for this bullet.");
		}
		this.ship = ship;
	}

	/**
     * The ship that is the owner of this bullet.
     */
	private Ship ship;
	
	// Waarom is al die terminated code herhaald hier als die exact hetzelfde in de superklasse staat?
//	/**
//	 * Check whether this bullet is already terminated.
//	 */
//	public boolean isTerminated(){
//		return this.isTerminated;
//	}
//
//	/**
//     * Terminate this bullet.
//     *
//     * @post  | (new this).isTerminated()
//     */
//	public void terminate(){
//		this.isTerminated = true;
//	}
//
//	/**
//	 * Variable registering whether or not this bullet is
//	 * terminated.
//	 */
//	private boolean isTerminated;

	public void die(){
		this.terminate();
		this.setShip(null);
		this.getWorld().removeAsSpatialElement(this);
	}

	/**
	 * Get the mass density of this bullet.
	 */
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
	public boolean hasBounced(){
		return hasBounced;
	}

	/**
	 * Bounce the bullet of a wall.
	 * 
	 * @post	| (new this).hasBounced() == true
	 */
	public void bounce(){
		setHasBounced(true);
	}
	
	/**
	 * Set whether this bullet has already bounced of a wall.
	 * 
	 * @post	| (new this).hasBounced() == bounce
	 */
	private void setHasBounced(boolean bounce){
		this.hasBounced = bounce;
	}
	
	/**
	 * Variable registering whether or not this bullet
	 * has bounced of wall.
	 */
	private boolean hasBounced;
}

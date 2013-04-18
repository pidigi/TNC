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
	 * @post	The given ship is set as the source of this bullet.
	 * 			| this.getShip() == ship
	 * @effect	This new bullet is initialized as a spatial element with the given position,
	 * 			radius, velocity, maximum speed and mass derived from the size and density 
	 * 			of the bullet.
	 * 			| let mass= 4/3*PI*radius^3*getMassDensity()
	 * 			| in 
	 * 			| super(position, radius, velocity, maxSpeed, mass)
	 */
	@Raw
	public Bullet(Vector2D position, double radius, Vector2D velocity, double maxSpeed, Ship shooter)
	throws IllegalArgumentException, NullPointerException{
		super(position,radius,velocity,maxSpeed,4/3*Math.PI*Math.pow(radius,3)*massDensity);
		if(shooter == null)
			throw new IllegalArgumentException("Invalid bullet source.");
		this.ship = shooter;
	}
	
	/**
     * Get the ship that is the owner of this bullet.
     */
	// TODO Probleem is dat je eigenlijk Ship moeten teruggeven, maar dan zouden
	// externen hiervan eigenschappen kunnen veranderen terwijl ze wel nog in
	// onze database zit dus dat zou niet zo koosjer zijn. Alternatief om een
	// kopie terug te geven werkt niet omdat echt de source van Ship gebruikt
	// wordt... Mijn voorstel zou zijn om een methode te schrijven
	// 'isSource(Ship)' die in deze klasse checkt of het inderdaad om de source
	// gaat... Probleem is dat dat deze methode toch public (of default) moet zijn
	// om ze te kunnen meegeven in Facade
	// => Niet erg, alleen rekening houden met fouten die de gebruiker zou kunnen maken,
	// niet met dingen die ze express mis zouden doen.
	// Als de methodes van ship duidelijk zijn in wat ze doen is dit geen probleem.
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
	// Maar je zet ship wel op null als je element dood gaat? Je zou dit toch moete checke in setAsShip maar dan gaat die niet meer?
	// Tenzij je ook checked of isTerminated in setAsShip (zoals nu geïmplementeerd.)
	// => Zet ship niet meer op null als dat dood gaat!
	public boolean canHaveAsShip(Ship ship){
		return ship != null;
	}

	// Deze methode niet nodig?
	// => Voor de invariante van de klasse?
	/**
     * Check whether this bullet has a proper ship.
     * 
     * @return  True if and only if the ship associated with this bullet is effective.
     * 			| this.ship != null
     */
	public boolean hasProperShip() {
		return this.ship != null;
	}
	
	//TODO: Niet meer nodig want in opgave duidelijk insinuatie dat het final moet zijn
//	/**
//     * Set the given ship as the owner for this bullet.
//     * 
//     * @post  	| this.getShip() == ship
//     * @throws	IllegalArgumentException
//     * 			| canHaveAsShip(ship)
//     */
//	public void setShip(Ship ship) throws IllegalArgumentException {
//		if ((!this.isTerminated()) && (!canHaveAsShip(ship))) {
//			throw new IllegalArgumentException("Given ship is not a valid ship for this bullet.");
//		}
//		this.ship = ship;
//	}

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
	// No setter necassary because physically not logical to set back to false 
	// and private setter would only be used in this method.
	public void bounce(){
		hasBounced = true;
	}
	
	//TODO: Niet nodig, zie bounce.
//	/**
//	 * Set whether this bullet has already bounced of a wall.
//	 * 
//	 * @post	| (new this).hasBounced() == bounce
//	 */
//	private void setHasBounced(boolean bounce){
//		this.hasBounced = bounce;
//	}
	
	/**
	 * Variable registering whether or not this bullet
	 * has bounced of wall.
	 */
	private boolean hasBounced = false;
}

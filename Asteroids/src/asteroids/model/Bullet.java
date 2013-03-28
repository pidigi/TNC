package asteroids.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of ships for the game asteroids.
 * 
 * @version  1.0
 * @author   Frederik Van Eeghem (1st master Mathematical engineering), 
 *		 	 Pieter Lietaert (1st master Mathematical engineering)
 */

public class Bullet extends SpacialElement{
	@Raw
	public Bullet(Vector2D position, double radius, Vector2D velocity, double maxSpeed, double mass)
			throws IllegalArgumentException, NullPointerException{
		super(position,radius,velocity,maxSpeed,mass);
	}
	
	public boolean canHaveAsShip(Ship ship){
		return ship != null;
	}
	
	public boolean hasProperShip() {
		return this.ship != null;
	}
	
	public void setShip(Ship ship){
		this.ship = ship;
	}
	
	private Ship ship;
	
	
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	public void terminate(){
		this.isTerminated = true;
		this.ship = null;
	}
	
	private boolean isTerminated;
}

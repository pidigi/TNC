package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
/**
 * A class of ships for the game asteroids.
 * 
 * @invar	The angle of each ship must be a valid angle for a ship.
 *			| isValidAngle(getAngle())
 * 
 * @version  2.5
 * @author   Frederik Van Eeghem (1st master Mathematical engineering), 
			 Pieter Lietaert (1st master Mathematical engineering)
 */
// Link to dropbox folder with files: https://www.dropbox.com/sh/tp0rjutudne3vji/hyFRz4TEUn

// GENERAL REMARK:
// The setters setPosition, setVelocity and setAngle have been made private because 
// changing the position, velocity or angle of the ship directly in other places makes little sense.
// The methods move, thrust and turn can be used to indirectly change these attributes.

public class Ship extends SpacialElement{
	/**
	 * Initialize this new ship with given position, angle, radius, velocity and maximum speed.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new ship.
	 * @param 	angle
	 * 			The angle for this new ship.
	 * @param 	radius
	 * 			The radius for this new ship.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new ship.
	 * @param 	maxSpeed
	 * 			The maximum allowed speed for this new ship.
	 * @post	The radius of this new ship is equal to the given radius.
	 * 			| (new this).getRadius() == radius
	 * @post	If the maximum speed is a number that is positive and smaller than or equal to the speed of light,
	 * 			the maximum speed of this new ship is equal to the given maximum speed.
	 * 			|if ((!Double.isNaN(maxSpeed)) &&  (maxSpeed  >=0) && (maxSpeed <= 300000))
	 * 			|	then (new this).getMaxSpeed() == maxSpeed
	 * @post	If the maximum speed is NaN, a negative number or larger than the speed of light,
	 * 			the maximum speed of this new ship is equal to the speed of light.
	 * 			|if (Double.isNaN(maxSpeed) || (maxSpeed  < 0) || (maxSpeed > 300000))
	 * 			|	then (new this).getMaxSpeed() == 300000;
	 * @effect	The given position is set as the position of this new ship.
	 *       	| this.setPostion(position)
	 * @effect 	The given angle is set as the angle of this new ship.
	 * 			| this.setAngle(angle)
	 * @effect	The given velocity is set as the velocity of this new ship.
	 * 			| this.setVelocity(velocity)
	 * @throws	IllegalArgumentException
	 * 			Check if the given radius is valid.
	 * 			| ! this.isValidRadius(radius)
	 */
	@Raw
	public Ship(Vector2D position, double angle, double radius, Vector2D velocity, double maxSpeed, double mass)
			throws IllegalArgumentException, NullPointerException{
		super(position,radius,velocity,maxSpeed,mass);
		setAngle(angle);
	}
		
	/**
	 * Initialize this new ship with given position, angle, radius, velocity and maximum speed set as the speed of light.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new ship.
	 * @param 	angle
	 * 			The angle for this new ship.
	 * @param 	radius
	 * 			The radius for this new ship.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new ship.
	 * @param 	maxSpeed
	 * 			The maximum allowed speed for this new ship.
	 * @effect	The new ship is initialized with position equal to the given position, 
	 * 			angle equal to the given angle, radius equal to the given 
	 * 			radius, velocity equal to given velocity and maximum speed 
	 * 			equal to the speed of light.
	 * 			| this(position, angle, radius, velocity, 300000)
	 */
	@Raw
	public Ship(Vector2D position, double angle, double radius, Vector2D velocity, double mass)
			throws IllegalArgumentException, NullPointerException{
		super(position,radius,velocity,300000,mass);
		setAngle(angle);
	}
	
	/**
	 * Initialize this new ship.
	 * 
	 * @effect	The new ship is initialized with the components of position equal to 0, 
	 * 			the angle equal to 0, the radius equal to the minimum radius,
	 * 			the velocity components equal to 0 and the maximum speed 
	 * 			equal to the speed of light.
	 * 			| this(new Vector(0,0), 0, getMinRadius(), new Vector(0,0))
	 */
	@Raw
	public Ship() 
			throws IllegalArgumentException, NullPointerException{
		super(new Vector2D(0,0),10,new Vector2D(0,0),100);
		setAngle(angle);
	}
		
	/**
	 * Return the angle of this ship.
	 */
	@Basic @Raw
	public double getAngle(){
		return this.angle;
	}
	
	/**
	 * Check whether the given angle is a valid angle for a ship.
	 * 
	 * @param	angle
	 * 			The angle to check.
	 * @return	True if and only if the angle is a number between 0 and 2 pi
	 * 			| result == (angle != Double.NaN) && (angle >= 0) && (angle < 2* Math.PI)
	 */
	public boolean isValidAngle(double angle){
		return (!Double.isNaN(angle)) && (angle >= 0) && (angle < 2* Math.PI);
	}
	
	/**
	 * Set the angle of this ship to the given angle.
	 * 
	 * @pre		The given angle must be a valid angle for a ship.
	 * 			| isValidAngle(angle)
	 * @post	The angle of this ship is equal to the given angle.
	 * 			| (new this).getAngle() == angle.
	 */
	@Raw
	private void setAngle(double angle){
		assert isValidAngle(angle);
		this.angle = angle;
	}
	
	/**
	 * Variable registering the angle of the ship.
	 * The angle is measured in Radians. The positive direction is taken to be counterclockwise. 
	 * Zero angle coincides with the positive x-direction.
	 */
	private double angle;
	
	/** 
	 * Turn this ship over the given angle.
	 * 
	 * @param 	angle
	 * 		  	The angle to turn this ship in the counterclockwise direction.
	 * @effect	The sum of the angle of this ship and the given angle is set as the angle of this ship.
	 * 			| this.setAngle(this.getAngle() + angle)
	 * @note	The sum of the current angle and the given angle should be a valid angle according to the precondition.
	 * 			This means, before using the method turn, the user should calculate the resulting angle and if this angle
	 * 			does not lie within the 0-2pi range (because it surpasses 2 pi, see isValidAngle), 
	 *			recalculate the angle so that the resulting	angle lies within the range. 			
	 */
	// The method is implemented in a nominal way as the assignment specifies. It would however be more intuitive to 
	// implement it in a total way by correcting the angle within the method.
	public void turn(double angle){
		this.setAngle(this.getAngle() + angle);
	}
	
//	/**
//	 * Accelerate this ship according to the given acceleration.
//	 * 
//	 * @param	acceleration
//	 * 			The acceleration of this ship.
//	 * @post	If the given acceleration is negative, the velocity of this ship remains unchanged.
//	 * 			| if(acceleration < 0)
//	 * 			|	then (new this).getVelocity() == this.getVelocity
//	 * @effect	If the given acceleration is positive, the given acceleration in the direction 
//	 * 			of angle is added to the velocity of this ship and set as the velocity of this ship.
//	 * 			| if(acceleration >= 0)
//	 * 			|	then let Vector2D deltaVelocity == new Vector2D(Math.cos(this.getAngle())*acceleration,
//	 * 			|													Math.sin(this.getAngle())*acceleration)
//	 * 			|		 in this.setVelocity(this.getVelocity().add(deltaVelocity));
//	 */
	public void thrust(double acceleration){
		if(acceleration >= 0){
			Vector2D deltaVelocity = new Vector2D(Math.cos(this.getAngle())*acceleration,Math.sin(this.getAngle())*acceleration);
			this.setVelocity(this.getVelocity().add(deltaVelocity));
		}
	}
	
	public boolean isThrusterActive() {
		return thrusterActive;
	}

	public void setThrusterActive(boolean thrusterActive) {
		this.thrusterActive = thrusterActive;
	}

	private boolean thrusterActive;
	
	public void fireBullet(){
		Vector2D shootingDirection = new Vector2D(Math.cos(this.getAngle()),
				Math.sin(this.getAngle()));
		Vector2D bulletPosition = this.getPosition().add(shootingDirection.multiply(getAngle()));
		Vector2D bulletVelocity = shootingDirection.multiply(250);
		double bulletRadius = 3;
		double bulletMass = 4/3*Math.PI*Math.pow(bulletRadius, 3)*Bullet.getMassDensity();
		SpacialElement bullet = new Bullet(bulletPosition, 3, bulletVelocity, 300000, bulletMass);
		if(!((Bullet)bullet).canHaveAsShip(this))
			throw new IllegalArgumentException();
		this.getWorld().addAsSpacialElement(bullet);
		((Bullet)bullet).setShip(this);
	}
	
	
	
}

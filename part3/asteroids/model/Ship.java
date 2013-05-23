package asteroids.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of ships for the game asteroids.
 * 
 * @invar	The angle of each ship must be a valid angle for a ship.
 *			| isValidAngle(getAngle())
 * 
 * @version  3.2
 * @author   Frederik Van Eeghem (1st master Mathematical engineering), 
			 Pieter Lietaert (1st master Mathematical engineering)
 */

public class Ship extends SpatialElement{
	
	/**
	 * Initialize this new ship with given position, angle, radius, velocity, maximum speed and mass.
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
	 * @param	mass
	 * 			The mass of this new ship.
	 * @pre		The given angle must be a valid angle.
	 * 			| (new this).isValidAngle(angle)
	 * @post	The angle of this new ship is equal to the given angle.
	 * 			| (new this).getAngle() == angle
	 * @effect	This new ship is initialized as a new spatial element with the given position,
	 * 			radius, velocity, maximum speed and mass.
	 * 			| super(position, radius, velocity, maxSpeed, mass)
	 * @effect	The maximum allowed number of bullets is set to 3.
	 * 			| setMaxNbBullets(3)
	 */
	@Raw
	public Ship(Vector2D position, double angle, double radius, Vector2D velocity, double maxSpeed, double mass)
			throws IllegalArgumentException{
		super(position,radius,velocity,maxSpeed,mass);
		assert isValidAngle(angle);
		setAngle(angle);
		setMaxNbBullets(3);
	}
		
	/**
	 * Initialize this new ship with given position, angle, radius, velocity, maximum speed set as the 
	 * speed of light and the mass equal to the given mass.
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
	 * @param	mass
	 * 			The mass of this new ship.
	 * @effect	The new ship is initialized with position equal to the given position, 
	 * 			angle equal to the given angle, radius equal to the given 
	 * 			radius, velocity equal to given velocity, maximum speed 
	 * 			equal to the speed of light and the mass equal to the given mass.
	 * 			| this(position, angle, radius, velocity, 300000, mass)
	 */
	@Raw
	public Ship(Vector2D position, double angle, double radius, Vector2D velocity, double mass)
			throws IllegalArgumentException{
		this(position,angle,radius,velocity,300000,mass);
	}
	
	/**
	 * Initialize this new ship.
	 * 
	 * @effect	The new ship is initialized with the components of position equal to 0, 
	 * 			the angle equal to 0, the radius equal to the minimum radius,
	 * 			the velocity components equal to 0, the maximum speed 
	 * 			equal to the speed of light and the mass equal to 100.
	 * 			| this(new Vector(0,0), 0, getMinRadius(), new Vector(0,0),100)
	 */
	@Raw
	public Ship() 
			throws IllegalArgumentException{
		this(new Vector2D(0,0),0,10,new Vector2D(0,0),1E5);
	}
		
	/**
	 * Terminate this ship.
	 * 
	 * @effect	If the current program is effective it will be terminated.
	 * 			| if(getProgram() != null)
	 * 			| then getProgram().terminate()
	 */
	@Override
	public void terminate() throws IllegalArgumentException, NullPointerException{
		if(getProgram() != null)
			getProgram().terminate();
		super.terminate();
	}
	
	/**
	 * Return the angle of this ship.
	 */
	@Basic 
	@Raw
	public double getAngle(){
		return this.angle;
	}
	
	/**
	 * Check whether the given angle is a valid angle for this ship.
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
	 * @param	angle
	 * 			The given angle to set.
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
	 * Variable registering the angle of this ship.
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
	
	/** 
	 * Check the status of the thruster of this ship.
	 * 
	 * @return	The status of the thruster of this ship.
	 * 			| result == thrusterActive
	 */
	@Basic
	public boolean isThrusterActive() {
		return thrusterActive;
	}

	/** 
	 * Set the status of the thruster.
	 * 
	 * @param	thrusterActive
	 * 			The status of the thruster to set.
	 * @post 	The status of the thruster is equal to the given status thrusterActive.
	 * 			| (new this).isThrusterActive() == thrusterActive
	 */	
	public void setThrusterActive(boolean thrusterActive) {
		this.thrusterActive = thrusterActive;
	}

	/** 
	 * Boolean indicating if the thruster of this ship is active.
	 */
	private boolean thrusterActive = false;
	
	/**
	 * Accelerate this ship according to the given acceleration.
	 * 
	 * @param	acceleration
	 * 			The acceleration of this ship.
	 * @post	If the given acceleration is NaN or negative, the velocity of this ship remains unchanged.
	 * 			| if(acceleration.isNaN || acceleration < 0)
	 * 			|	then (new this).getVelocity() == this.getVelocity
	 * @effect	If the given acceleration is positive, the given acceleration in the direction 
	 * 			of angle is added to the velocity of this ship and set as the velocity of this ship.
	 * 			| if(acceleration >= 0)
	 * 			|	then let Vector2D deltaVelocity == new Vector2D(Math.cos(this.getAngle())*acceleration,
	 * 			|													Math.sin(this.getAngle())*acceleration)
	 * 			|		 in this.setVelocity(this.getVelocity().add(deltaVelocity));
	 */
	public void thrust(double acceleration){
		if(acceleration >= 0){
			Vector2D deltaVelocity = new Vector2D(Math.cos(this.getAngle())*acceleration,Math.sin(this.getAngle())*acceleration);
			this.setVelocity(this.getVelocity().add(deltaVelocity));
		}
	}	
	
	/**
	 * Check whether the given element can be added to the list of bullets of this ship.
	 * 	
	 * @param 	element
	 * 			The element to check.
	 * @return	True if and only if the element is an effective bullet and
	 * 			its source is this ship and it is not terminated.
	 * 			| result == (element != null && element.getShip() == this 
	 * 			|			&& !element.isTerminated())
	 */
	public boolean canHaveAsBullet(Bullet element){
			return (element != null && element.getShip() == this &&
					!element.isTerminated());
	}
	
	/**
	 * Add the given element to the set of effective bullets.
	 * 
	 * @param 	element
	 * 			The element to add.
	 * @effect	If the element is not yet in the list, it is added.
	 * 			| bullets.add(element)
	 * @throws 	IllegalArgumentException
	 * 			The element cannot be added.
	 * 			| !this.canHaveAsBullet(element)
	 */
	public void addAsBullet(Bullet element) throws IllegalArgumentException{
		if (!this.canHaveAsBullet(element)) {
			throw new IllegalArgumentException("The element cannot be added.");
		}
		bullets.add(element);
	}
	
	/**
	 * Remove the given element from the set of effective bullets.
	 * 
	 * @param 	element
	 * 			The element to remove.
	 * @effect	If the element is in the list, it is removed.
	 * 			| bullets.remove(element)
	 * @throws	NullPointerException
	 * 			The element is non effective
	 * 			| element == null
	 * @throws 	IllegalArgumentException
	 * 			The element is not terminated.
	 * 			| !element.isTerminated();
	 */
	public void removeAsBullet(Bullet element) throws NullPointerException, IllegalArgumentException{
		if (element == null)
			throw new NullPointerException();
		if (!element.isTerminated())
			throw new IllegalArgumentException("Cannot remove element");
		bullets.remove(element);
	}
	
	/**
	 * Get the current number of non-terminated bullets fired by the ship.
	 */
	@Basic
	public int getNbBullets(){
		return bullets.size();
	}
	
	
	/**
	 * A set containing all nonterminated bullets.
	 * 
	 * @invar	The structure is effective.
	 * 			| bullets != null
	 * @invar	Each bullet in bullets is effective.
	 * 			| for each bullet in collisions:
	 * 			| bullet != null
	 * @invar	Each bullet in bullets is not terminated
	 * 			| for each bullet in collisions:
	 * 			| !bullet.isTerminated()
	 */
	private final Set<Bullet> bullets = new HashSet<Bullet>();
	
	/**
	 * Get the maximum number of bullets.
	 */
	@Basic
	public int getMaxNbBullets() {
		return maxNbBullets;
	}
	
	/**
	 * Set the maximum number of bullets
	 * 
	 * @param 	maxNbBullets
	 * 			The new allowed number of bullets
	 * @post	The new maximum number of bullets allowed is equal to the given value.
	 * 			| (new this).getMaxNbBullets() = maxNbBullets
	 * @throws	IllegalArgumentException
	 * 			The given number is smaller than zero.
	 * 			| maxNbBullets < 0
	 */
	@Basic
	public void setMaxNbBullets(int maxNbBullets) throws IllegalArgumentException {
		if(maxNbBullets < 0)
			throw new IllegalArgumentException("Illegal maximum amount of bullets.");
		this.maxNbBullets = maxNbBullets;
	}
	
	/**
	 * Variable containing the maximum number of effective bullets fired by a ship.
	 */
	private int maxNbBullets;
	
	/** 
	 * Fire a bullet from this ship and add this to the world that contains this ship.
	 * 
	 * @post	If this ship is located in a world and the current number of bullets 
	 * 			is smaller than the maximum allowed, a new bullet is created with 
	 * 			position equal to the position of this ship added to the sum of 
	 * 			the radius of this ship and the bullet, in the direction of angle, 
	 * 			radius equal to 3, velocity equal to 250 in the direction of angle, 
	 * 			maximum speed equal to the speed of light and
	 * 			added as a spatial element to the world containing ship and 
	 * 			this ship is appointed as the owner of the bullet.
	 * 			| if(getWorld() != null && getNbBullets() < getMaxNbBullets())
	 * 			| then let Bullet newBullet
	 * 			| 	in
	 * 			| 	(newBullet.getPosition().subtract(this.getPosition()).getDirection().getXComponent() 
	 * 			| 		== Math.cos(this.getAngle())
	 * 			| 	(newBullet.getPosition().subtract(this.getPosition()).getDirection().getYComponent() 
	 * 			| 		== Math.sin(this.getAngle())
	 * 			| 	(newBullet.getPosition().subtract(this.getPosition()).getNorm() == this.getRadius()
	 * 			| 	newBullet.getRadius() == 3
	 * 			| 	newBullet.getVelocity().getNorm() == 250
	 * 			| 	newBullet.getVelocity().getDirection().getXComponent() == Math.cos(this.getAngle())
	 * 			| 	newBullet.getVelocity().getDirection().getYComponent() == Math.sin(this.getAngle())
	 * 			| 	newBullet.getMaxSpeed() == 300000
	 * 			| 	newBullet.getMass() == 4/3*PI*3^3*Bullet.getMassDensity()
	 * 			| 	newBullet.getShip() == this
	 * 			| 	this.getWorld().hasAsSpatialElement(newBullet)
	 * @throws	NullpointerException
	 * 			The ship does not have a proper world.
	 * 			| !this.hasProperWorld()
	 */
	public void fireBullet() throws NullPointerException {
		if(!this.hasProperWorld())
			throw new IllegalArgumentException("Ship not located within a proper world.");
		if(this.getWorld() != null && getNbBullets() < getMaxNbBullets()){
		Vector2D shootingDirection = new Vector2D(Math.cos(this.getAngle()),
				Math.sin(this.getAngle()));
		Vector2D bulletPosition = this.getPosition().add(shootingDirection.multiply(getRadius()));
		Vector2D bulletVelocity = shootingDirection.multiply(250);
		double bulletRadius = 3;
		Bullet bullet = new Bullet(bulletPosition, bulletRadius, bulletVelocity, 300000, this);
		this.addAsBullet(bullet);
		this.getWorld().addAsSpatialElement(bullet);
		}
	}
	
	/**
	 * Check whether this ship can have the given program as its program.
	 * 
	 * @param 	program
	 * 			The program to check.
	 * @return	True if and only if program is noneffective or if
	 * 			the program is typecorrect and not terminated.
	 * 			| result == (program == null || 
	 * 			|	(program.typeCheck() && !program.isTerminated()))
	 */
	public boolean canHaveAsProgram(Program program){
		return program == null || 
			(program.typeCheck() && !program.isTerminated());
	}
	
	
	/**
	 * Get the program of this ship.
	 */
	@Basic
	public Program getProgram() {
		return this.program;
	}
	
	/**
	 * Set the program for this ship.
	 * 
	 * @param	program
	 * 			The program to set
	 * @post	The new program of this ship is the given program if it is effective.
	 * 			| if (program != null)
	 * 			| then (new this).getProgram() == program
	 * @post	If this ship was already referring to a program, this program
	 * 			will not reference a ship anymore.
	 * 			| if (getProgram() != null)
	 * 			|	(new getProgram()).getShip() == null
	 * @effect	The ship of the program is set to this ship.
	 * 			| program.setShip(this)
	 * @throws	IllegalArgumentException
	 * 			This ship is terminated.
	 * 			| this.isTerminated()
	 * @throws	IllegalArgumentException
	 * 			This ship cannot have the given program as its program
	 * 			| !canHaveAsProgram()
	 */
	@Basic
	public void setProgram(Program program) throws IllegalArgumentException{
		if(this.isTerminated())
			throw new IllegalArgumentException("Ship is terminated.");
		if(!this.canHaveAsProgram(program))
			throw new IllegalArgumentException("Cannot add program.");
		Program temp = getProgram();
		this.program = program;
		// remove old association
		if (temp != null)
			temp.setShip(null);
		// set new association
		if (program != null)
			program.setShip(this);
	}
	
	/**
	 * The program of this ship.
	 */
	private Program program;
		
	/**
	 * Check if the collision of this element and the given element
	 * is a valid object collision.
	 * 
	 * @return	If the element is an asteroid or a ship, the result is
	 * 			the boolean indicating whether this is a valid object overlap.
	 * 			| if(element.isAsteroid() || element.isShip())
	 *			| then result == isValidObjectOverlap(element)
	 * @return	If the element is not an asteroid or a ship, the result
	 * 			is the boolean indicating whether the collision of the given element
	 * 			with this element is valid.
	 * 			| if(!element.isAsteroid() && !element.isShip())
	 *			| then result == element.isValidObjectCollision(this)
	 */
	@Override
	public boolean isValidObjectCollision(SpatialElement element){
		if(element == null)
			return false;
		if(element.isAsteroid() || element.isShip())
			return isValidObjectOverlap(element);
		return element.isValidObjectCollision(this);
	}
	
	/**
	 * Resolve the initial condition of this and the given element.
	 * 
	 * @effect	If the overlapping element is not a ship, let it
	 * 			resolve the initial conditions with this ship.
	 * 			| if (!overlappingElement.isShip())
	 * 			| then overlappingElement.resolveInitialCondition(this)
	 */
	@Override
	public void resolveInitialCondition(SpatialElement overlappingElement) throws IllegalArgumentException, NullPointerException{
		if(!isValidObjectCollision(overlappingElement))
			throw new IllegalArgumentException("Element cannot be resolved.");
		if(!overlappingElement.isShip())
			overlappingElement.resolveInitialCondition(this);
	}
	
	/**
	 * Resolve a collision of this ship and another element.
	 * 
	 * @effect	If the other element is a ship, resolve it by bouncing.
	 * 			| if(otherElement.isShip())
	 * 			|	then resolveBounce(otherElement)
	 * @effect	If the other element is not a ship, let it resolve this ship.
	 * 			| if(!otherElement.isShip())
	 * 			|	then otherElement.resolve(this)
	 */
	@Override
	public void resolve(SpatialElement otherElement) throws IllegalArgumentException, NullPointerException{
		if(!isValidObjectCollision(otherElement))
			throw new IllegalArgumentException("Element cannot be resolved.");
		if(otherElement.isShip())
			resolveBounce(otherElement);
		else
			otherElement.resolve(this);
	}
	
}

package asteroids.model;

import static asteroids.Util.fuzzyLessThanOrEqualTo;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of spatial elements for the game world of the game Asteroids.
 * 
 * @version	1.1
 * @author 	Frederik Van Eeghem, Pieter Lietaert
 */

public class SpatialElement{
	/**
	 * Initialize this new spatial element with given position, radius, velocity, maximum speed and mass.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new spatial element.
	 * @param 	radius
	 * 			The radius for this new spatial element.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new spatial element.
	 * @param 	maxSpeed
	 * 			The maximum allowed speed for this new spatial element.
	 * @param	mass
	 * 			The mass for this new spatial element.
	 * @post	The radius of this new spatial element is equal to the given radius.
	 * 			| (new this).getRadius() == radius
	 * @post	If the maximum speed is a number that is positive and smaller than or equal to the speed of light,
	 * 			the maximum speed of this new spatial element is equal to the given maximum speed.
	 * 			|if ((!Double.isNaN(maxSpeed)) && (maxSpeed  >=0) && (maxSpeed <= 300000))
	 * 			|	then (new this).getMaxSpeed() == maxSpeed
	 * @post	If the maximum speed is NaN, a negative number or larger than the speed of light,
	 * 			the maximum speed of this new spatial element is equal to the speed of light.
	 * 			|if (Double.isNaN(maxSpeed) || (maxSpeed  < 0) || (maxSpeed > 300000))
	 * 			|	then (new this).getMaxSpeed() == 300000;
	 * @post	The mass of this new spatial element is equal to the given mass.
	 * 			| (new this).getMass() == mass
	 * @effect	The given position is set as the position of this new spatial element.
	 *       	| this.setPostion(position)
	 * @effect	The given velocity is set as the velocity of this new spatial element.
	 * 			| this.setVelocity(velocity)
	 * @throws	IllegalArgumentException
	 * 			Check if the given radius is valid.
	 * 			| ! this.isValidRadius(radius)
	 * @throws	IllegalArgumentException
	 * 			Check if the given mass is valid.
	 * 			| ! this.isValidRadius(mass)
	 */
	@Raw
	public SpatialElement(Vector2D position, double radius, Vector2D velocity, double maxSpeed, double mass)
			throws IllegalArgumentException, NullPointerException{
		if (!isValidRadius(radius)){
			throw new IllegalArgumentException("Given radius was invalid while constructing new spatial element.");
		}
		if (!isValidMass(mass)){
			throw new IllegalArgumentException("Given mass was invalid while constructing new spatial element");
		}
		setPosition(position);
		this.radius = radius;
		
		if ((!Double.isNaN(maxSpeed)) && (maxSpeed  >=0) && (maxSpeed <= 300000)){
			this.maxSpeed = maxSpeed;
		}
		else{
			this.maxSpeed = 300000;
		}
		setVelocity(velocity);	
		this.mass = mass;
	}
		
	
	/**
	 * Initialize this new spatial element with given position, angle, radius, velocity and maximum speed set as the speed of light.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new spatial element.
	 * @param 	radius
	 * 			The radius for this new spatial element.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new spatial element.
	 * @param	mass
	 * 			The mass for this new spatial element
	 * @effect	The new spatial element is initialized with position equal to the given position, 
	 * 			radius equal to the given radius, velocity equal to given velocity, maximum speed 
	 * 			equal to the speed of light and mass equal to the given mass.
	 * 			| this(position, angle, radius, velocity, 300000, mass)
	 */
	@Raw
	public SpatialElement(Vector2D position, double radius, Vector2D velocity, double mass)
			throws IllegalArgumentException, NullPointerException{
		this(position, radius, velocity, 300000, mass);
	}
	
	/**
	 * Initialize this new spatial element.
	 * 
	 * @effect	The new spatial element is initialized with the components of position equal to 0, 
	 * 			the radius equal to the minimum radius,
	 * 			the velocity components equal to 0, the maximum speed 
	 * 			equal to the speed of light and the mass equal to 1E15.
	 * 			| this(new Vector(0,0), 0, getMinRadius(), new Vector(0,0), 1E15)
	 */
	@Raw
	public SpatialElement() 
			throws IllegalArgumentException, NullPointerException{
		this(new Vector2D(0,0), minRadius, new Vector2D(0,0), 1E15);
	}
		
	/**
	 * Return the position of this element.
	 * The position is a 2D vector that contains the coordinates of this element.
	 */
	@Basic @Raw
	public Vector2D getPosition() {
		return this.position;
	}
	
	/**
	 * Check whether the given position is a valid position for an element.
	 * 
	 * @param	position
	 * 			The position to check.
	 * @return	True if and only if the given 2D position vector is effective and does not contain NaN.
	 *			| result == (position != null && !position.containsNaN())
	 * @note	This checker checks for effectiveness and NaN at the same time. This means
	 * 			that this function is not useful when different exceptions are 
	 * 			desired for the different cases (e.g. NullPointerException 
	 * 			and IllegalArgumentException), it is however simpler towards the user.
	 */
	public boolean isValidPosition(Vector2D position) {
		return (position != null && !position.containsNaN());
	}
	
	/**
	 * Set the position of this spatial element as a 2D vector containing the coordinates of this spatial element.
	 * 
	 * @param	position
	 * 			The new position for this spatial element.
	 * @post	The position of this spatial element is equal to the given position.
	 * 			| (new this).getPosition == position
	 * @throws	IllegalArgumentException
	 * 			The given position is not a valid position for a spatial element.
	 * 			| ! isValidPosition(position)
	 */
	private void setPosition(Vector2D position) throws IllegalArgumentException {
		if (!isValidPosition(position))
			throw new IllegalArgumentException("Invalid position.");
		 this.position = position;
	}
	
	/**
	 * Variable registering the position of this spatial element.
	 * The coordinates are measured in km.
	 */
	private Vector2D position;
		
	/**
	 * Check whether the given time period is a valid time period.
	 * 
	 * @param 	deltaT
	 * 			The time period to check.
	 * @return	True if and only if the given time period is a number that is positive.
	 * 			| result == (!Double.isNaN(deltaT)) && (deltaT >= 0)
	 * @note	Checking for NaN is actually redundant since the boolean (deltaT >= 0) returns false in case 
	 * 			deltaT is NaN. It is however excluded explicitly for clarity towards the user.
	 */
	public static boolean isValidTime(double deltaT){
		return (!Double.isNaN(deltaT)) && (deltaT >= 0);
	}
	
	/**
	 * Calculate new position of this spatial element after a time period of deltaT based on current position and velocity.
	 * 
	 * @param 	deltaT
	 * 			Time period over which this spatial element moves.
	 * @effect	The resulting position of this spatial element is the sum of on the one hand the position of this spatial element,
	 * 			and on the other hand the product of the given time with the velocity of this spatial element.
	 * 			| (new this).getPosition == this.getPosition().add(this.getVelocity().multiply(deltaT))) 
	 * @throws	IllegalArgumentException
	 * 			The given time period is not a valid time period.
	 * 			| !isValidTime(deltaT)
	 */
	// Note that we do not check explicitly for an infinite time. This would either lead to an infinite position or a NaN position,
	// which are both cases that can be handled within setPosition (either without problems or by throwing an exception).
	public void move(double deltaT) throws IllegalArgumentException {
		if (!isValidTime(deltaT))
			throw new IllegalArgumentException("Invalid time step while attempting to move the spatial element.");
		this.setPosition(this.getPosition().add(this.getVelocity().multiply(deltaT)));
	}
	
	/**
	 * Return the velocity of this spatial element. 
	 * The velocity is a 2D vector that contains the velocity in the x-direction and the y-direction.
	 */
	@Basic @Raw
	public Vector2D getVelocity(){
		return this.velocity;
	}
	
	/**
	 * Set the velocity of this spatial element as a 2D vector containing the velocity in the x-direction and the y-direction.
	 * 
	 * @param	velocity
	 * 			The new velocity for this spatial element.
	 * @post	If the given velocity is effective, does not contain any NaN entries and 
	 * 			if the norm of the given velocity is smaller than or equal to the maximum allowed speed,
	 * 			the velocity of this spatial element is equal to the given velocity.
	 * 			| if ((velocity != null) && (!velocity.containsNaN()) && (fuzzyLessThanOrEqualTo(velocity.getNorm(),this.getMaxSpeed())))
	 * 			|		then (new this).getVelocity().equals(velocity)
	 * @post	If the given velocity is effective, does not contain any NaN entries and 
	 * 			if the norm of the given velocity exceeds the maximum allowed speed, the velocity 
	 * 			of this spatial element has the same direction as the given velocity and a norm equal 
	 * 			to the maximum allowed speed.
	 * 			| if ((velocity != null) &&  (!velocity.containsNaN()) && (!fuzzyLessThanOrEqualTo(velocity.getNorm(),this.getMaxSpeed())))
	 * 			|	then (new this).getVelocity.getNorm() == this.getMaxSpeed()
	 * 			|		 (new this).getVelocity.getDirection().equals(velocity.getDirection())
	 * @post 	If the given velocity is non-effective or contains a NaN entry, 
	 * 			the velocity of this spatial element is set to zero.
	 * 			|if ((velocity == null) || (velocity.containsNaN())
	 * 			|	then (new this).getVelocity == new Vector2D(0,0)
	 */
	public void setVelocity(Vector2D velocity){
		if (velocity == null || velocity.containsNaN()){
			this.velocity = new Vector2D(0,0);
		} else {
			// Use of fuzzyLessThanOrEqualTo to save on calculation time when
			// the given velocity is only slightly higher than the maximum speed.
			// The effect will be the same as when the speed is reset to the maximum speed.
			if (fuzzyLessThanOrEqualTo(velocity.getNorm(),this.getMaxSpeed())){
				this.velocity = velocity;
			} else {
				this.velocity = velocity.getDirection().multiply(this.getMaxSpeed());
				}
		}
	}
	
	/**
	 * Variable registering the velocity of this spatial element.
	 * The velocity is a 2D vector, containing the velocity in x-direction and y-direction, expressed in km/s.
	 */
	private Vector2D velocity;
	
	/**
	 * Return the maximum speed of this spatial element.
	 */
	@Basic @Immutable @Raw
	public double getMaxSpeed(){
		return this.maxSpeed;
	}
	
	/**
	 * Variable registering the maximum allowed total speed of this spatial element.
	 * This variable is expressed in km/s.
	 */
	private final double maxSpeed;
	
	/**
	 * Calculate the distance between this spatial element and the given spatial element.
	 * 
	 * @param 	otherElement
	 * 			The other spatial element to which the distance is calculated.
	 * @return	The distance between this spatial element and the given spatial element if the given spatial element is effective, 
	 * 			zero if this spatial element and the given spatial element are the same.
	 * 			| if(this != otherElement)
	 * 			|		result == this.getPosition().subtract(otherElement.getPosition()).getNorm() - 
	 * 			|            (this.getRadius()+otherElement.getRadius())
	 * @return	Zero if this spatial element and the given spatial element are the same.
	 * 			| if(this == otherElement)
	 * 			|		result == 0
	 * @throws	NullPointerException
	 * 			The other element is non existent.
	 * 			| otherElement == null
	 */
	public double getDistanceBetween(SpatialElement otherElement) throws NullPointerException{
		if(otherElement == null)
			throw new NullPointerException("The other element is non existent.");
		if(this == otherElement)
			return 0;
		else 
			return this.getPosition().subtract(otherElement.getPosition()).getNorm() - 
					(this.getRadius()+otherElement.getRadius());
	}
	
	/**
	 * Check whether this element and the given element overlap.
	 * 
	 * @param 	otherElement
	 * 			The other spatial element for which the overlap with this spatial element is checked.
	 * @return	True if this spatial element and the given spatial element are the same or
	 * 			if this spatial element and the given spatial element overlap.
	 *			| result == ((this == otherElement) || (this.getDistanceBetween(otherElement) < 0))
	 */
	public boolean overlap(SpatialElement otherElement) throws NullPointerException{
		return ((this == otherElement) || (this.getDistanceBetween(otherElement) < 0));
	}
	
	/**
	 * The time to collision between this spatial element and the given spatial element.
	 * 
	 * @param 	otherspatial element
	 * 			The spatial element for which the time to collision is calculated.
	 * @return	Double.POSITIVE_INFINITY if this spatial element and the given spatial element are the same.
	 * 			| if (this == otherElement)
	 * 			|	result == Double.POSITIVE_INFINITY
	 * @return	The time until collision according to current position and velocity
	 * 			if this spatial element and the given spatial element (different from this spatial element), 
	 * 			both moving in their current direction,	will ever collide, 
	 * 			else Double.POSITIVE_INFINITY.
	 * 			| if (this != otherElement)
	 * 			|	let
	 * 			| 		allOverlapTimes = {collisionTime in Double | (when (this.move(collisionTime)  
	 * 			|								&& otherElement.move(collisionTime)) then this.overlap(otherspatial element))
	 * 			|								&& (collisionTime != Double.POSITIVE_INFINITY)}
	 * 			|	 in
	 * 			|	 if (!isEmpty(allOverlapTimes))
	 * 			|		fuzzyEquals(result, min(allOverlapTimes)) == true
	 * 			|	 else
	 * 			|		result == Double.POSITIVE_INFINITY
	 * @throws	NullPointerException
	 * 			The other spatial element is non existent
	 * 			| otherElement == null
	 */

	public double getTimeToCollision(SpatialElement otherElement)throws NullPointerException{
		if (otherElement == null)
			throw new NullPointerException("The other spatial element is non existent.");
		if (this != otherElement) {
			Vector2D dr = this.getPosition().subtract(otherElement.getPosition());
			Vector2D dv = this.getVelocity().subtract(otherElement.getVelocity());
			double drdr = dr.getDotProduct(dr);
			double dvdv = dv.getDotProduct(dv);
			double dvdr = dr.getDotProduct(dv);
			double d = dvdr * dvdr - dvdv * (drdr - 
					Math.pow((this.getRadius() + otherElement.getRadius()), 2));
			if (dvdr >= 0 || d <= 0)
				return Double.POSITIVE_INFINITY;
			else
				return -1 * (dvdr + Math.sqrt(d)) / dvdv;
		} 
		else {
			return Double.POSITIVE_INFINITY;
		}
	}
	
	/**
	 * The collision point of this spatial element and the other spatial element.
	 * 
	 * @param 	otherspatial element
	 * 			The spatial element on which the collision point of this spatial element is calculated.
	 * @return	If this spatial element and the other spatial element will collide, the collision position is returned
	 * 			as the middle of the connecting line on the time of collision.
	 *  		| let collisionTime = this.getTimeToCollision(otherElement)
	 *  		| in
	 *  		|	if(collisionTime != Double.POSITIVE_INFINITY)
	 *  		|		when(otherElement.move(collisionTime) && this.move(collisionTime))
	 *  		|		then
	 *  		|			(otherElement.getPosition().subtract(result)).getNorm() <= otherElement.getRadius()
	 *  		|			(this.getPosition().subtract(result)).getNorm() <= this.getRadius()
	 * @return	If this spatial element and the other spatial element never collide, null is returned.
	 * 			| if(this.getTimeToCollision(other spatial element) == Double.POSITIVE_INFINITY)
	 * 			|	then result == null
	 * @throws	NullPointerException
	 * 			The other spatial element is non existent
	 * 			| otherElement == null
	 */
	public Vector2D getCollisionPosition(SpatialElement otherElement) throws NullPointerException{
		double timeToCollision = this.getTimeToCollision(otherElement);
		if(timeToCollision != Double.POSITIVE_INFINITY){
			SpatialElement thisClone = new SpatialElement(this.getPosition(), this.getRadius(), this.getVelocity(), this.getMaxSpeed());
			SpatialElement otherClone = new SpatialElement(otherElement.getPosition(), otherElement.getRadius(), otherElement.getVelocity(), otherElement.getMaxSpeed());

			thisClone.move(timeToCollision);
			otherClone.move(timeToCollision);
			Vector2D newPositionThis = thisClone.getPosition();
			Vector2D newPositionOther = otherClone.getPosition();
			
			Vector2D collisionPosition = newPositionThis.add(newPositionOther.subtract(newPositionThis).getDirection().multiply(this.getRadius()));
			return collisionPosition;

		} else{
			return null;
		}
	}
	
	/**
	 * Return the time to a collision of this spatial element with an infinitely long 
	 * vertical wall located at the y-coordinate yBound.
	 * 
	 * @param	yBound
	 * 			The coordinate of the wall that this element could collide with.
	 * @return	| if((yBound - yComponent) * yVelocity <= 0)
	 * 			| then Double.POSITIVE_INFINITY
	 * @return	| if((yBound - yComponent) * yVelocity > 0)
	 * 			| then (yBound - this.getPosition().getYComponent - this.getRadius()*sign(this.getVelocity().getYComponent()))/this.getVelocity().getYComponent()
	 */
	// Nog beter commentaar
	public double getTimeToHorizontalWallCollision(double yBound){
		double yVelocity = this.getVelocity().getYComponent();
		double yComponent = this.getPosition().getYComponent();
		if ((yBound - yComponent) * yVelocity > 0) {
			return (yBound - yComponent - getRadius()*Math.signum(yVelocity))/yVelocity;
		}
		return Double.POSITIVE_INFINITY;	
	}
	
	/**
	 * Return the time to a collision of this spatial element with an infinitely long 
	 * horizontal wall located at the height of the x-coordinate xBound.
	 * 
	 * @param	xBound
	 * 			The coordinate of the wall that this element could collide with.
	 * @return	| if((xBound - xComponent) * xVelocity <= 0)
	 * 			| then Double.POSITIVE_INFINITY
	 * @return	| if((xBound - xComponent) * xVelocity > 0)
	 * 			| then (xBound - this.getPosition().getXComponent - this.getRadius()*sign(this.getVelocity().getXComponent()))/this.getVelocity().getXComponent()
	 */
	public double getTimeToVerticalWallCollision(double xBound){
		double xVelocity = this.getVelocity().getXComponent();
		double xComponent = this.getPosition().getXComponent();
		if ((xBound - xComponent) * xVelocity > 0) {
			return (xBound - xComponent - getRadius()*Math.signum(xVelocity))/xVelocity;
		}
		return Double.POSITIVE_INFINITY;
	}

	/**
	 * Return the radius of this spatial element.
	 */
	@Basic @Immutable @Raw
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * Check whether the given radius is a valid radius for a spatial element.
	 * 
	 * @param	radius
	 * 			Radius to check.
	 * @return	True if and only if the given radius is a number 
	 * 			larger than the minimum radius and smaller than or equal to the maximum double value.
	 * 			| result == (!Double.isNaN(radius)) && ((radius >= this.getMinRadius()) && (radius <= Double.MAX_VALUE))
	 */
	public boolean isValidRadius(double radius){
		return ((!Double.isNaN(radius)) && (this.getMinRadius() < radius) 
				&& (radius <= Double.MAX_VALUE));
	}
	
	/**
	 * Return the minimum radius of this spatial element.
	 */
	@Basic @Immutable @Raw
	public double getMinRadius(){
		return minRadius;
	}
	
	/**
	 * Variable registering radius of this spatial element.
	 * The radius is expressed in km.
	 */
	private final double radius;
	
	/**
	 * Variable registering the minimum allowed radius of this spatial element.
	 * The minimum radius is expressed in km.
	 */
	private static final double minRadius = 0;
	
	
	/**
	 * Check whether the given mass is a valid mass for an element.
	 * 
	 * @param	mass
	 * 			Mass to check.
	 * @return	True if and only if the given mass is a number 
	 * 			larger than 0.
	 * 			| result == mass>0
	 */
	private boolean isValidMass(double mass) {
		return mass>0;
	}
	
	/**
	 * Return the mass of this spatial element.
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * Variable registering the mass of this spatial element.
	 * The mass is expressed in kg.
	 */
	private final double mass;
	
	/**
	 * Check whether this world is already terminated.
	 */
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
     * Terminate this spatial element.
     *
     * @post  | (new this).isTerminated()
     */
	public void terminate(){
		this.isTerminated = true;
	}
	
	/**
	 * Variable registering whether or not this world is
	 * terminated.
	 */
	private boolean isTerminated;
	
	// TODO vragen aan assistent of dit een goed idee is
	public void die(){
		this.terminate();
		this.getWorld().removeAsSpatialElement(this);
	}
	
	/**
	 * Get the world this spatial element resides in.
	 */
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Check whether this spatial element can have the given world as its world. 
	 * 
	 * @param  world
	 * 		   The world to check.
	 * @return  | result == ((world != null) && (!world.isTerminated()))
	 */
	public boolean canHaveAsWorld(World world){
		return (world != null) && (!world.isTerminated());
	}
	
	/**
     * Check whether this spatial element has a proper world.
     * 
     * @return  | (this.world != null)
     */
	public boolean hasProperWorld() {
		return this.world != null;
	}
	
	/**
	 * Set the given world as the world for this spatial element.
	 * 
	 * @param  world
	 * 		   The world to set as the world for this spatial element.
	 * @post  | this.getWorld() == world
	 */
	public void setWorld(World world){
		this.world = world;
	}
	
	private World world;
	
	// Nog eens zien hoe we dit aanpakken.
	public boolean isShip(){
		return (this instanceof Ship);
	}
	
	public boolean isAsteroid(){
		return (this instanceof Asteroid);
	}
	
	public boolean isBullet(){
		return (this instanceof Bullet);
	}
}

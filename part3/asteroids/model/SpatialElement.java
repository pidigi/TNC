package asteroids.model;

import static asteroids.Util.fuzzyLessThanOrEqualTo;
import asteroids.Util;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of spatial elements for the game Asteroids.
 * 
 * @invar	The spatial element has a valid position.
 * 			| isValidPosition()
 * @invar	The spatial element has a valid radius.
 * 			| isValidRadius()
 * @invar	The spatial element has a valid mass.
 * 			| isValidMass()
 * @invar 	The spatial element has a proper world associated with it.
 * 			| hasProperWorld()
 * 
 * @version 1.2
 * @author Frederik Van Eeghem (1st master Mathematical engineering), 
 * 		   Pieter Lietaert (1st master Mathematical engineering)
 */

//Link to repository: https://github.com/Extrimius/TNC.git

// Approach to methods:
// Position: Defensively
// Velocity: Total
// Radius: Defensively
// Mass: Undetermined (Chosen: Defensively)

// GENERAL REMARKS:
// The setter setPosition has been made private because 
// changing the position the spatial element directly other than within spatial element
// makes little sense. The method move can be used to indirectly change the position attribute.

public abstract class SpatialElement {
	/**
	 * Initialize this new spatial element with given position, radius,
	 * velocity, maximum speed and mass.
	 * 
	 * @param	position
	 *          The 2D vector containing the position coordinates for this new
	 *          spatial element.
	 * @param 	radius
	 *          The radius for this new spatial element.
	 * @param 	velocity
	 *          The 2D vector containing the velocity components for this new
	 *          spatial element.
	 * @param 	maxSpeed
	 *          The maximum allowed speed for this new spatial element.
	 * @param 	mass
	 *          The mass for this new spatial element.
	 * @post 	The radius of this new spatial element is equal to the given
	 *       	radius. 
	 *       	| (new this).getRadius() == radius
	 * @post 	If the maximum speed is a number that is positive and smaller than
	 *       	or equal to the speed of light (NaN implicitly excluded),
	 *       	the maximum speed of this new spatial element is 
	 *       	equal to the given maximum speed. 
	 *       	| if ((maxSpeed >=0) && (maxSpeed <= 300000)) 
	 *       	| then (new this).getMaxSpeed() == maxSpeed
	 * @post 	If the maximum speed is NaN, a negative number or larger than the
	 *       	speed of light, the maximum speed of this new spatial element is
	 *       	equal to the speed of light.
	 *       	| if (Double.isNaN(maxSpeed) || (maxSpeed < 0) || (maxSpeed > 300000)) 
	 *       	| then (new this).getMaxSpeed() == 300000;
	 * @post 	The mass of this new spatial element is equal to the given mass. 
	 * 			| (new this).getMass() == mass
	 * @effect 	The given position is set as the position of this new spatial
	 *        	element.
	 *        	| this.setPostion(position)
	 * @effect 	The given velocity is set as the velocity of this new spatial
	 *         	element. 
	 *          | this.setVelocity(velocity)
	 * @throws 	IllegalArgumentException
	 *          Check if the given radius is valid.
	 *          | !this.isValidRadius(radius)
	 * @throws 	IllegalArgumentException
	 *          Check if the given mass is valid.
	 *          | !this.isValidMass(mass)
	 */
	@Raw
	public SpatialElement(Vector2D position, double radius, Vector2D velocity,
			double maxSpeed, double mass) throws IllegalArgumentException {
		if (!isValidRadius(radius)) {
			throw new IllegalArgumentException(
					"Given radius was invalid while constructing new spatial element.");
		}
		if (!isValidMass(mass)) {
			throw new IllegalArgumentException(
					"Given mass was invalid while constructing new spatial element");
		}
		setPosition(position);
		this.radius = radius;
		if ((!Double.isNaN(maxSpeed)) && (maxSpeed >= 0)
				&& (maxSpeed <= 300000)) {
			this.maxSpeed = maxSpeed;
		} else {
			this.maxSpeed = 300000;
		}
		setVelocity(velocity);
		this.mass = mass;
	}
	
	/**
	 * Initialize this new spatial element with given position, angle, radius,
	 * velocity and maximum speed set as the speed of light.
	 * 
	 * @param	position
	 *          The 2D vector containing the position coordinates for this new
	 *          spatial element.
	 * @param 	radius
	 *          The radius for this new spatial element.
	 * @param 	velocity
	 *          The 2D vector containing the velocity components for this new
	 *          spatial element.
	 * @param 	mass
	 *          The mass for this new spatial element
	 * @effect 	The new spatial element is initialized with position equal to the
	 *         	given position, radius equal to the given radius, velocity equal
	 *         	to the given velocity, maximum speed equal to the speed of light and
	 *         	mass equal to the given mass.
	 *          | this(position, angle, radius, velocity, 300000, mass)
	 */
	@Raw
	public SpatialElement(Vector2D position, double radius, Vector2D velocity,
			double mass) throws IllegalArgumentException {
		this(position, radius, velocity, 300000, mass);
	}
	
	/**
	 * Check whether this world is already terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this spatial element.
	 * 
	 * @post	This spatial element is terminated.
	 * 			| (new this).isTerminated()
	 * @post 	This spatial element no longer references an effective 
	 * 			world.
	 * 			| (new this).getWorld() == null
	 * @post	This spatial element is no longer one of the spatial
	 * 			elements for the world to which this spatial element
	 * 			belonged, if this element was associated with an 
	 * 			effective world.
	 * 			| if (this.getWorld() != null)
	 * 		    | then this.getWorld().hasAsSpatialElement(this)
	 */
	// RemoveAsSpatialElement won't throw an exception since it's called via this.getWorld()
	public void terminate() {
		if (this.getWorld() != null) {
			this.getWorld().removeAsSpatialElement(this);
		}
		this.isTerminated = true;
	}
	
	/**
	 * Variable registering whether or not this world is terminated.
	 */
	private boolean isTerminated = false;	
	
	/**
	 * Get the world this spatial element is associated with.
	 */
	@Basic
	@Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Check whether this spatial element can have the given world as its world.
	 * 
	 * @param	world
	 *          The world to check.
	 * @return 	True if and only if the given world is not effective 
	 * 			or if the given world can have this spatial element 
	 * 			associated with it.
	 * 			| result == ((world == null) || world.canHaveAsSpatialElement(this))
	 */
	public boolean canHaveAsWorld(World world) {
		return ((world == null) || (world.canHaveAsSpatialElement(this)));
	}
	
	/**
	 * Check whether this spatial element has a proper world.
	 * 
	 * @return 	True if and only if this spatial element can have the world 
	 * 			to which it is attached to as its world, and either this spatial
	 * 			element is not attached to a world, or that world references
	 * 			this spatial element as its spatial element.
	 * 			| result ==
	 * 			| (canHaveAsWorld(getWorld()) && ((getWorld() == null) 
	 *			| (getWorld().hasAsSpatialElement(this)))); 
	 */
	public boolean hasProperWorld() {
		return (canHaveAsWorld(getWorld()) && ((getWorld() == null) 
				|| (getWorld().hasAsSpatialElement(this))));
	}

	/**
	 * Set the given world as the world for this spatial element.
	 * 
	 * @param	world
	 *          The world to set as the world for this spatial element.
	 * @post	This spatial element references the given world 
	 * 			as the world it is associated to.
	 * 			| (new this).getWorld() == world
	 * @throws	IllegalArgumentException
	 * 			Check if the given world is effective and this spatial 
	 * 			element has the given world already attached to it.
	 * 			| ((world != null) && !world.hasAsSpatialElement(this))
	 * @throws	IllegalArgumentException
	 * 			Check if the given world is not effective and this spatial 
	 * 			element has a world attached to it and this attached world 
	 * 			references this spatial element.
	 * 			| ((world == null) && (getWorld() != null)
	 *			| && getWorld().hasAsSpatialElement(this))
	 */
	// M.O. hasAsSpatialElement impliceert dat de canHaveAsWorld reeds gecheckt
	// werd
	public void setWorld(World world) throws IllegalArgumentException {
		if ((world != null) && !world.hasAsSpatialElement(this)) {
			throw new IllegalArgumentException(
					"Given world can not be set as the world of this spatial element");
		}
		if ((world == null) && (getWorld() != null)
				&& getWorld().hasAsSpatialElement(this)) {
			throw new IllegalArgumentException(
					"Given world can not be set as the world of this spatial element");
		}
		this.world = world;
	}
	
	/**
	 * Variable referencing the world to which this spatial element is associated with.
	 */
	private World world;
	
	/**
	 * Return the position of this element. The position is a 2D vector that
	 * contains the coordinates of this element.
	 */
	@Basic
	@Raw
	public Vector2D getPosition() {
		return this.position;
	}

	/**
	 * Check whether the given position is a valid position for an element.
	 * 
	 * @param	position
	 *          The position to check.
	 * @return 	True if and only if the given 2D position vector is effective and
	 *         	does not contain NaN. 
	 *          | result == (position != null && !position.containsNaN())
	 * @note 	This checker checks for effectiveness and NaN at the same time.
	 *       	This means that this function is not useful when different
	 *       	exceptions are desired for the different cases (e.g.
	 *       	NullPointerException and IllegalArgumentException), it is however
	 *       	simpler towards the user.
	 */
	@Basic
	public boolean isValidPosition(Vector2D position) {
		return (position != null && !position.containsNaN());
	}

	/**
	 * Set the position of this spatial element as a 2D vector containing the
	 * coordinates of this spatial element.
	 * 
	 * @param	position
	 *          The new position for this spatial element.
	 * @post 	The position of this spatial element is equal to the given
	 *       	position. 
	 *          | (new this).getPosition == position
	 * @throws 	IllegalArgumentException
	 *          The given position is not a valid position for a spatial
	 *          element. 
	 *          | !isValidPosition(position)
	 */
	private void setPosition(Vector2D position) throws IllegalArgumentException {
		if (!isValidPosition(position))
			throw new IllegalArgumentException("Invalid position.");
		this.position = position;
	}

	/**
	 * Variable registering the position of this spatial element. The
	 * coordinates are measured in km.
	 */
	private Vector2D position;

	/**
	 * Check whether the given time period is a valid time period.
	 * 
	 * @param	deltaT
	 *          The time period to check.
	 * @return 	True if and only if the given time period is a number that is
	 *         	positive. 
	 *          | result == (deltaT >= 0)
	 * @note 	NaN is implicitly excluded.
	 */
	@Basic
	public static boolean isValidTime(double deltaT) {
		return (deltaT >= 0);
	}

	/**
	 * Calculate new position of this spatial element after a time period of
	 * deltaT based on current position and velocity.
	 * 
	 * @param	deltaT
	 *          Time period over which this spatial element moves.
	 * @effect 	The resulting position of this spatial element is the sum of on
	 *         	the one hand the position of this spatial element, and on the
	 *         	other hand the product of the given time with the velocity of
	 *         	this spatial element. 
	 *          | setPosition(this.getPosition().
	 *          | add(this.getVelocity().multiply(deltaT)))
	 * @throws 	IllegalArgumentException
	 *          The given time period is not a valid time period. 
	 *          | !isValidTime(deltaT)
	 * @note	Note that there is no explicit check for an infinite time. This would
	 *			either lead to an infinite position or a NaN position,
	 * 			which are both cases that can be handled within setPosition (either
	 * 			without problems or by throwing an exception).
	 */
	public void move(double deltaT) throws IllegalArgumentException {
		if (!isValidTime(deltaT))
			throw new IllegalArgumentException(
					"Invalid time step while attempting to move the spatial element.");
		this.setPosition(this.getPosition().add(
				this.getVelocity().multiply(deltaT)));
	}

	/**
	 * Return the velocity of this spatial element. The velocity is a 2D vector
	 * that contains the velocity in the x-direction and the y-direction.
	 */
	@Basic
	@Raw
	public Vector2D getVelocity() {
		return this.velocity;
	}

	/**
	 * Set the velocity of this spatial element as a 2D vector containing the
	 * velocity in the x-direction and the y-direction.
	 * 
	 * @param 	velocity
	 *          The new velocity for this spatial element.
	 * @post 	If the given velocity is effective, does not contain any NaN
	 *       	entries and if the norm of the given velocity is smaller than or
	 *       	equal to the maximum allowed speed, the velocity of this spatial
	 *       	element is equal to the given velocity. 
	 *       	| if ((velocity != null) && (!velocity.containsNaN()) &&
	 *       	| (fuzzyLessThanOrEqualTo(velocity.getNorm(),this.getMaxSpeed()))) 
	 *       	| then (new this).getVelocity().equals(velocity)
	 * @post 	If the given velocity is effective, does not contain any NaN
	 *       	entries and if the norm of the given velocity exceeds the maximum
	 *      	allowed speed, the velocity of this spatial element has the same
	 *       	direction as the given velocity and a norm equal to the maximum
	 *       	allowed speed.
	 *       	| if ((velocity != null) && (!velocity.containsNaN()) &&
	 *       	| (!fuzzyLessThanOrEqualTo(velocity.getNorm(),this.getMaxSpeed()))) 
	 *       	| then (new this).getVelocity.getNorm() == this.getMaxSpeed() 
	 *       	| (new this).getVelocity.getDirection().equals(velocity.getDirection())
	 * @post 	If the given velocity is non-effective or contains a NaN entry, the
	 *       	velocity of this spatial element is set to zero. 
	 *       	| if ((velocity == null) || (velocity.containsNaN()) 
	 *       	| then (new this).getVelocity == new Vector2D(0,0)
	 */
	void setVelocity(Vector2D velocity) {
		if (velocity == null || velocity.containsNaN()) {
			this.velocity = new Vector2D(0, 0);
		} else {
			// Use of fuzzyLessThanOrEqualTo to save on calculation time when
			// the given velocity is only slightly higher than the maximum
			// speed. The effect will be the same as when the speed is reset to the
			// maximum speed.
			if (fuzzyLessThanOrEqualTo(velocity.getNorm(), this.getMaxSpeed())) {
				this.velocity = velocity;
			} else {
				this.velocity = velocity.getDirection().multiply(
						this.getMaxSpeed());
			}
		}
	}

	/**
	 * Variable registering the velocity of this spatial element. The velocity
	 * is a 2D vector, containing the velocity in x-direction and y-direction,
	 * expressed in km/s.
	 */
	private Vector2D velocity;

	/**
	 * Return the maximum speed of this spatial element.
	 */
	@Basic
	@Immutable
	@Raw
	public double getMaxSpeed() {
		return this.maxSpeed;
	}

	/**
	 * Variable registering the maximum allowed total speed of this spatial
	 * element. This variable is expressed in km/s.
	 */
	private final double maxSpeed;

	/**
	 * Calculate the distance between this spatial element and the given spatial
	 * element.
	 * 
	 * @param	otherElement
	 *          The other spatial element to which the distance is calculated.
	 * @return 	The distance between this spatial element and the given spatial
	 *         	element if the given spatial element is effective. 
	 *         	| if(this != otherElement) 
	 *         	| 	result == this.getPosition().subtract(otherElement.getPosition()).getNorm()
	 *         	| 	- (this.getRadius()+otherElement.getRadius())
	 * @return 	Zero if this spatial element and the given spatial element are
	 *         	the same.
	 *          | if(this == otherElement) 
	 *          | 	result == 0
	 * @throws 	NullPointerException
	 *          The other element is non existent. 
	 *          | otherElement == null
	 */
	public double getDistanceBetween(SpatialElement otherElement)
			throws NullPointerException {
//		if (otherElement == null)
//			throw new NullPointerException("The other element is non existent.");
		if (this == otherElement)
			return 0;
		else
			return this.getPosition().subtract(otherElement.getPosition())
					.getNorm()
					- (this.getRadius() + otherElement.getRadius());
	}

	/**
	 * Check whether this element and the given element overlap.
	 * 
	 * @param	otherElement
	 *          The other spatial element for which the overlap with this
	 *          spatial element is checked.
	 * @return 	True if this spatial element and the given spatial element are
	 *         	the same or if this spatial element and the given spatial element
	 *         	overlap. 
	 *          | result == ((this == otherElement) ||
	 *          | (this.getDistanceBetween(otherElement) < -Util.EPSILON))
	 * @throws	NullPointerException
	 * 			The given element is non effective.
	 * 			| (otherElement == null)
	 */
	public boolean overlap(SpatialElement otherElement)
			throws NullPointerException {
		return ((this == otherElement) || (this
				.getDistanceBetween(otherElement) < -Util.EPSILON));
	}
	
	/**
	 * Return the time it will take for this spatial element to collide with the other spatial element.
	 *  
	 * @param   other
	 *          The other spatial element to collide with.
	 * @return  The resulting time is not negative and different from Double.NaN
	 *        	| Util.fuzzyLeq(0.0,result) && (! Double.isNaN(result))
	 * @return  If the resulting time is finite, the distance between both
	 *          ships would be fuzzy equal to zero if they would both move
	 *          during the resulting time.
	 *        	| if (result < Double.POSITIVE_INFINITY) then
	 *        	|	when this.move(result) && other.move(result)
	 *        	|   Util.fuzzyEquals(this.getDistanceBetween(other),0.0)
	 * @return  If the resulting distance is finite, the distance between both ships
	 *          would be fuzzy different from zero if they would move for a time shorter than the
	 *          resulting time.
	 *        	| if (result < Double.POSITIVE_INFINITY) then
	 *        	|   for each time in 0.0..result:
	 *        	|     if (time < result)
	 *        	|		when this.move(time) && other.move(time)
	 *        	|       then ! Util.fuzzyEquals(this.getDistanceBetween(other),0.0)
	 * @return  If the resulting time is infinite, this ship is the same as the
	 *          other ship or the distance between both
	 *          ships would be different from zero for each finite time they would move.
	 *        	| if (result == Double.POSITIVE_INFINITY) then
	 *        	|   (this == other) ||
	 *        	|   (for each time in 0.0..Double.POSITIVE_INFINITY:
	 *        	|     if (! Double.isInfinite(time)) then
	 *        	|		when this.move(time) && other.move(time)
	 *        	|       (! Util.fuzzyEquals(this.getDistanceBetween(other),0.0))
	 * @throws  NullPointerException
	 *          The other ship is not effective.
	 *        	| other == null
	 */
	public double getTimeToCollision(SpatialElement other) throws NullPointerException {
		if (this == other) {
			return Double.POSITIVE_INFINITY;
		}
		Vector2D dposition = this.getPosition().subtract(other.getPosition());
		Vector2D dspeed = this.getVelocity().subtract(other.getVelocity());
		double sigma = this.getRadius() + other.getRadius();

		double a = dspeed.getXComponent()*dspeed.getXComponent() + dspeed.getYComponent()*dspeed.getYComponent();
		double b = 2 * (dposition.getXComponent()*dspeed.getXComponent() + dposition.getYComponent()*dspeed.getYComponent());
		double c = (dposition.getXComponent()*dposition.getXComponent() + dposition.getYComponent()*dposition.getYComponent()) - Math.pow(sigma, 2);
		if (0 <= b) {
			return Double.POSITIVE_INFINITY;
		}
		double discriminant = b * b - 4 * a * c;
		if (discriminant < 0) {
			return Double.POSITIVE_INFINITY;
		} else {
			double solution1 = (-b + Math.sqrt(discriminant)) / (2 * a);
			double solution2 = (-b - Math.sqrt(discriminant)) / (2 * a);
			if (solution2 < 0) {
				if (solution1 < 0) {
					return Double.POSITIVE_INFINITY;
				} else {
					return solution1;
				}
			} else {
				return solution2;
			}
		}
	}

	/**
	 * Calculate the collision point of this spatial element and the other spatial
	 * element.
	 * 
	 * @param	otherElement
	 * 			The spatial element on which the collision point of
	 *          this spatial element is calculated.
	 * @return 	If this spatial element and the other spatial element will
	 *         	collide, the collision position is returned as the middle of the
	 *         	connecting line on the time of collision. 
	 *          | let collisionTime = this.getTimeToCollision(otherElement) 
	 *          | in 
	 *          | 	if(collisionTime != Double.POSITIVE_INFINITY) 
	 *          | 	then when(otherElement.move(collisionTime) && this.move(collisionTime)) 
	 *          |		 then 
	 *          | 		(otherElement.getPosition().subtract(result)).getNorm() <= 
	 *          |		otherElement.getRadius()
	 *          | 		(this.getPosition().subtract(result)).getNorm() <=
	 *          | 		this.getRadius()
	 * @return 	If this spatial element and the other spatial element never
	 *        	collide, null is returned. 
	 *        	| if(this.getTimeToCollision(otherElement) == Double.POSITIVE_INFINITY) 
	 *        	| then result == null
	 * @throws 	NullPointerException
	 *          The other spatial element is non existent 
	 *          | otherElement == null
	 */
	public Vector2D getCollisionPosition(SpatialElement otherElement)
			throws NullPointerException {
		double timeToCollision = this.getTimeToCollision(otherElement);
		if (timeToCollision != Double.POSITIVE_INFINITY) {
			SpatialElement thisClone = new Ship(this.getPosition(),0,
					this.getRadius(), this.getVelocity(), this.getMaxSpeed());
			SpatialElement otherClone = new Ship(
					otherElement.getPosition(),0, otherElement.getRadius(),
					otherElement.getVelocity(), otherElement.getMaxSpeed());

			thisClone.move(timeToCollision);
			otherClone.move(timeToCollision);
			Vector2D newPositionThis = thisClone.getPosition();
			Vector2D newPositionOther = otherClone.getPosition();

			Vector2D collisionPosition = newPositionThis.add(newPositionOther
					.subtract(newPositionThis).getDirection()
					.multiply(this.getRadius()));
			return collisionPosition;
		} else {
			return null;
		}
	}
	
	/**
	 * Return the time to a collision of this spatial element with an infinitely
	 * long vertical wall located at the y-coordinate yBound.
	 * 
	 * @param 	yBound
	 *          The coordinate of the wall that this element could collide
	 *          with.
	 * @return 	...
	 * 			| if((yBound - yComponent - getRadius() * Math.signum(yVelocity)) 
	 * 			|	* yVelocity <= 0) 
	 * 			| then result == Double.POSITIVE_INFINITY
	 * @return 	...
	 * 			| if((yBound - yComponent - getRadius() * Math.signum(yVelocity)) 
	 * 			|	* yVelocity > 0) 
	 * 			| then fuzzyEquals(Math.abs(yBound - (getPosition().getYCoordinate 
	 * 			|	+ getVelocity().getYComponent()*result)),getRadius())
	 * @note	NaN case is implicitly excluded (returns Double.Double.POSITIVE_INFINITY).
	 */
	public double getTimeToHorizontalWallCollision(double yBound) {
		double yVelocity = this.getVelocity().getYComponent();
		double yComponent = this.getPosition().getYComponent();
		if ((yBound - yComponent - getRadius() * Math.signum(yVelocity)) * yVelocity > 0) {
			return (yBound - yComponent - getRadius() * Math.signum(yVelocity))
					/ yVelocity;
		}
		return Double.POSITIVE_INFINITY;
	}

	/**
	 * Return the time to a collision of this spatial element with an infinitely
	 * long horizontal wall located at the height of the x-coordinate xBound.
	 * 
	 * @param	xBound
	 *          The coordinate of the wall that this element could collide
	 *          with.
	 * @return 	...
	 * 			| if((xBound - xComponent - getRadius() * Math.signum(xVelocity)) 
	 * 			|	* xVelocity <= 0) 
	 * 			| then result == Double.POSITIVE_INFINITY
	 * @return 	...
	 * 			| if((xBound - xComponent - getRadius() * Math.signum(xVelocity)) 
	 * 			|	* xVelocity > 0) 
	 * 			| then fuzzyEquals(Math.abs(xBound - (getPosition().getXCoordinate 
	 * 			|	+ getVelocity().getXComponent()*result)),getRadius())
	 * @note	NaN case is implicitly excluded (returns Double.Double.POSITIVE_INFINITY).
	 */
	public double getTimeToVerticalWallCollision(double xBound) {
		double xVelocity = this.getVelocity().getXComponent();
		double xComponent = this.getPosition().getXComponent();
		if ((xBound - xComponent - getRadius() * Math.signum(xVelocity)) * xVelocity > 0) {
			return (xBound - xComponent - getRadius() * Math.signum(xVelocity))
					/ xVelocity;
		}
		return Double.POSITIVE_INFINITY;
	}

	/**
	 * Return the radius of this spatial element.
	 */
	@Basic
	@Immutable
	@Raw
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Check whether the given radius is a valid radius for a spatial element.
	 * 
	 * @param	radius
	 *          Radius to check.
	 * @return 	True if and only if the given radius is a number larger than the
	 *         	minimum radius and smaller than or equal to the maximum double
	 *         	value. 
	 *         	| result == ((radius >= this.getMinRadius()) && (radius <= Double.MAX_VALUE))
	 * @note	NaN is implicitly excluded (returns false).
	 */
	public boolean isValidRadius(double radius) {
		return ((this.getMinRadius() < radius) && (radius <= Double.MAX_VALUE));
	}

	/**
	 * Variable registering radius of this spatial element. The radius is
	 * expressed in km.
	 */
	private final double radius;

	/**
	 * Return the minimum radius of this spatial element.
	 */
	@Basic
	@Immutable
	@Raw
	public double getMinRadius() {
		return minRadius;
	}
	
	/**
	 * Variable registering the minimum allowed radius of this spatial element.
	 * The minimum radius is expressed in km.
	 */
	private static final double minRadius = 0;

	/**
	 * Return the mass of this spatial element.
	 */
	@Basic
	@Raw
	public double getMass() {
		return mass;
	}
	
	/**
	 * Check whether the given mass is a valid mass for a spatial element.
	 * 
	 * @param	mass
	 *          Mass to check.
	 * @return 	True if and only if the given mass is a number larger than 0. 
	 * 			| result == (mass>0)
	 * @note 	NaN is implicitly excluded (returns false).
	 */
	@Basic
	@Raw
	private boolean isValidMass(double mass) {
		return mass > 0;
	}
	
	/**
	 * Variable registering the mass of this spatial element. The mass is
	 * expressed in kg.
	 */
	private final double mass;
	
	/**
	 * Check whether the this spatial element is a Ship object.
	 * 
	 * @return	...
	 * 			| result == (this instanceof Ship)
	 */
	// Tried to combine the three methods but did not succeed.
	public boolean isShip() {
		return false;
	}

	/**
	 * Check whether the this spatial element is an Asteroid object.
	 * 
	 * @return	...
	 * 			| result == (this instanceof Asteroid)
	 */
	public boolean isAsteroid() {
		return false;
	}

	/**
	 * Check whether the this spatial element is a Bullet object.
	 * 
	 * @return	...
	 * 			| result == (this instanceof Bullet)
	 */
	public boolean isBullet() {
		return false;
	}
	
	
	/**
	 * Check if the collision between the given spatial element 1 and 
	 * spatial element 2 is a valid object collision.
	 * 
	 * @return	...
	 * 			| if(element1 == element2)
	 * 			| then result == false
	 * @return	...
	 * 			| if((element1.getWorld() != null) && (element1.getWorld() != null) && 
	 *			| (element1.getWorld() != element2.getWorld()))
	 *			| then result == false
	 * @return	...
	 * 			| result == !(((element1.isBullet() && element2.isShip())
	 *			| && (element1.getShip() == element2)
	 *			| || ((element2.isBullet() && element1.isShip())
	 *			| && element2).getShip() == element1))
	 */
	public boolean isValidObjectCollision(SpatialElement element){
			if (element == null)
				return false;
			if (this == element)
				return false;
			if ((this.getWorld() != null) && (element.getWorld() != null))
				return (this.getWorld() == element.getWorld());
			return true;
	}
	
//	/**
//	 * Check whether a collision of this and the given element can be resolved.
//	 * 
//	 * @param 	otherElement
//	 * 			The element to check resolving with.
//	 * @return	True if and only if the other element is effective and
//	 * 			the worlds of this and the given element are effective and the same.
//	 * 			| result == !(otherElement == null || this.getWorld() == null ||
//	 * 			|	otherElement.getWorld() == null || otherElement.getWorld() != this.getWorld())
//	 */
//	public boolean canResolve(SpatialElement otherElement){
//		if(otherElement == null)
//			return false;
////		if(this.getWorld() == null || otherElement.getWorld() == null || 
////				otherElement.getWorld() != this.getWorld())
////			return false;
////		return true;
//		return true;
//	}
	
	/**
	 * Resolve this and the given element.
	 * 
	 * @param 	otherElement
	 * 			The element to resolve with.
	 * @return	...
	 */
	public abstract void resolve(SpatialElement otherElement);
	
	/**
	 * Resolve the bouncing of given spatial elements.
	 * 
	 * @pre		...
	 * 			| (element1 != null) && (element2 != null)
	 * 
	 * @effect	...
	 * 			| let unitNormal = (element1.getPosition().
	 *			| 	  	subtract(element2.getPosition())).getDirection()
	 *			| 	  unitTangent = new Vector2D(-unitNormal.getYComponent(),
	 *			|		unitNormal.getXComponent())
	 *			|	  velocity1 = element1.getVelocity()
	 *			|	  velocity2 = element2.getVelocity()
	 *			|    
	 *			|	  element1Normal = velocity1.getDotProduct(unitNormal)
	 * 			|	  element1Tangent = velocity1.getDotProduct(unitTangent)
	 *			|	  element2Normal = velocity2.getDotProduct(unitNormal)
	 *			|	  element2Tangent = velocity2.getDotProduct(unitTangent)
	 *			|		 
	 *			|	  element1NormalUpdated = (element1Normal*(massInvolved1 -
	 *			|		massInvolved2) + 2 * massInvolved2 * element2Normal)/
	 *		    |       (massInvolved1 + massInvolved2)
	 *		    |     element2NormalUpdated = (element2Normal*(massInvolved2 - 
	 *			|		massInvolved1) + 2 * massInvolved1 * element1Normal)/
	 *		    |       (massInvolved1 + massInvolved2)
	 *		    |
	 *		    | in element1.setVelocity(unitNormal.multiply(element1NormalUpdated)
	 *			|		.add(unitTangent.multiply(element1Tangent)))
	 *			|		 
	 *			|	 element2.setVelocity(unitNormal.multiply(element2NormalUpdated)
	 *			|		.add(unitTangent.multiply(element2Tangent)))
	 */
	public void resolveBounce(SpatialElement otherElement){
		if(!this.isValidObjectCollision(otherElement))
			throw new IllegalArgumentException("Elements cannot collide.");
		double sumOfRadius = this.getRadius() + otherElement.getRadius();
		double mass1 = this.getMass();
		double mass2 = otherElement.getMass();
		Vector2D deltaV = otherElement.getVelocity().subtract(this.getVelocity());
		Vector2D deltaPos = otherElement.getPosition().subtract(this.getPosition());
		
		double J = 2*mass1*mass2*(deltaV.getDotProduct(deltaPos))
					/(sumOfRadius*(mass1 + mass2));
		
		double Jx = J*deltaPos.getXComponent()/sumOfRadius;
		double Jy = J*deltaPos.getYComponent()/sumOfRadius;
		
		Vector2D newVel1 = new Vector2D(this.getVelocity().getXComponent() + Jx/mass1,
				this.getVelocity().getYComponent() + Jy/mass1);
		Vector2D newVel2 = new Vector2D(otherElement.getVelocity().getXComponent() - Jx/mass2,
				otherElement.getVelocity().getYComponent() - Jy/mass2);
		
		this.setVelocity(newVel1);
		otherElement.setVelocity(newVel2);
	}
	
	public void resolveInitialCondition(SpatialElement overlappingElement) {
		// do nothing
	}
	
	public void collide(){
		this.terminate();
	}

	public void resolveWall(boolean horizontal) {
		double xComp = this.getVelocity().getXComponent();
		double yComp = this.getVelocity().getYComponent();
		if(horizontal)
			this.setVelocity(new Vector2D(xComp, -1*yComp));
		else
			this.setVelocity(new Vector2D(-1*xComp, yComp));
	}
	
}

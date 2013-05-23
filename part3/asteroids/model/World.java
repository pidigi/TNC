package asteroids.model;

import java.util.*;
// import static asteroids.Util.*;
import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of worlds for the game Asteroids.
 * 
 * @invar	The world has a valid width
 * 			| isValidWidth()
 * @invar	The world has a valid height
 * 			| isValidHeight
 * @invar 	The world has proper spatial elements associated with it.
 * 			| hasProperSpatialElements()
 * 
 * @version 1.3
 * @author  Frederik Van Eeghem (1st master Mathematical engineering), 
 * 			Pieter Lietaert (1st master Mathematical engineering)
 */

// Approach to methods:
// Height,width: Not specified (Chosen: Defensively)
// Associations: Defensively

public class World{
	/**
	 * Initialize this new world with given width and height.
	 * 
	 * @param 	width
	 *        	The width for this new world.
	 * @param 	heigth
	 *          The height for this new world.
	 * @post 	The height of this new world is equal to the given height.
	 * 			| (new this).getHeigth() == height
	 * @post 	The width of this new world is equal to the given width.
	 * 			| (new this).getWidth() == width
	 * @throws 	IllegalArgumentException
	 * 			Check if the given width is valid.
	 *          | ! this.isValidWidth(width)
	 * @throws 	IllegalArgumentException
	 * 			Check if the given height is valid.
	 *          | ! this.isValidHeight(height)
	 */
	@Raw
	public World(double width, double height) throws IllegalArgumentException {
		if (!isValidWidth(width) || !isValidHeight(height))
			throw new IllegalArgumentException(
					"Illegal dimension given for the new game world.");
		this.width = width;
		this.height = height;
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
	 * Terminate this world.
	 * 
	 * @post 	...
	 * 			| (new this).isTerminated()
	 * @post 	...
	 * 			| for each element in elements: 
	 * 			| ((new element).isTerminated())
	 * 			|	&& !this.hasAsSpatialElement(element)
	 */
	public void terminate() {
		if (!isTerminated()) {
			Set<SpatialElement> clonedSet = new HashSet<SpatialElement>(elements);
			for (SpatialElement element : clonedSet) {
				// Element from elements cannot be null (invariant)
				element.terminate();
			}
			this.isTerminated = true;
		}
	}

	/**
	 * Variable registering whether or not this world is terminated.
	 */
	private boolean isTerminated = false;

	/**
	 * Get the width of this world.
	 */
	@Basic
	@Raw
	public double getWidth() {
		return width;
	}
	
	/**
	 * Check whether the given width is a valid width for this world.
	 * 
	 * @param 	width
	 *          Width to check.
	 * @return	True if and only if the given width is a number that is 
	 * 			positive and smaller than the maximum width of this world.
	 * 			| result == (width >= 0) && (width <= maxWidth)
	 * @note 	NaN is implicitly excluded.
	 */
	public boolean isValidWidth(double width) {
		return (width >= 0) && (width <= getMaxWidth());
	}

	/**
	 * Variable registering the width of this world in km.
	 */
	private final double width;

	/**
	 * Get the height of this game world in km.
	 */
	@Basic
	@Raw
	public double getHeight() {
		return height;
	}

	/**
	 * Check whether the given height is a valid height for this game world.
	 * 
	 * @param 	height
	 *          Height to check.
	 * @return 	True if and only if the given height is a number that is 
	 * 			positive and smaller than the maximum height of this world.
	 * 			| result == (height >= 0) && (height <= maxHeight)
	 * @note 	NaN is implicitly excluded.
	 */
	public boolean isValidHeight(double height) {
		return (height >= 0) && (height <= getMaxHeight());
	}

	/**
	 * Variable registering the height of this world in km.
	 */
	private final double height;

	/**
	 * Get the maximum value for the height of this world.
	 */
	@Basic
	@Raw
	public static double getMaxHeight() {
		return maxHeight;
	}
	
	/**
	 * Variable registering the maximum height of this world in km.
	 */
	private final static double maxHeight = Double.MAX_VALUE;
	
	/**
	 * Get the maximum value for the width of this game world in km.
	 */
	@Basic
	@Raw
	public static double getMaxWidth() {
		return maxWidth;
	}
	
	/**
	 * Variable registering the maximum width of this world in km.
	 */
	private final static double maxWidth = Double.MAX_VALUE;
	
	/**
	 * Get all the ships in this game world.
	 * 
	 * @return 	A set containing all the ship elements associated with this world.
	 * 			| let result == Set<Ship>
	 * 			| in
	 * 			| (for all ship in this.elements
	 * 			|	if(ship.isShip())
	 * 			|	result.contains(ship))
	 * 			| && (for all ship in result
	 * 			|	   this.hasAsSpatialElement(ship))
	 */
	public Set<Ship> getShips() {
		Set<Ship> allShips = new HashSet<Ship>();
		for (SpatialElement element : elements) {
			if (element.isShip())
				allShips.add((Ship) element);
		}
		return allShips;
	}
	
	/**
	 * Get all the bullets in this game world.
	 * 
	 * @return 	A set containing all the bullet elements associated with this world.
	 * 			| let result == Set<Bullet>
	 * 			| in
	 * 			| (for all bullet in this.elements
	 * 			|	if(bullet.isBullet())
	 * 			|	result.contains(bullet))
	 * 			| && (for all bullet in result
	 * 			|	   this.hasAsSpatialElement(bullet))
	 */
	public Set<Bullet> getBullets() {
		Set<Bullet> allBullets = new HashSet<Bullet>();
		for (SpatialElement element : elements) {
			if (element.isBullet())
				allBullets.add((Bullet) element);
		}
		return allBullets;
	}

	/**
	 * Get all the asteroids in this game world.
	 * 
	 * @return 	A set containing all the asteroid elements associated with this world.
	 * 			| let result == Set<Asteroid>
	 * 			| in
	 * 			| (for all asteroid in this.elements
	 * 			|	if(asteroid.isAsteroid())
	 * 			|	result.contains(asteroid))
	 * 			| && (for all asteroid in result
	 * 			|	   this.hasAsSpatialElement(asteroid))
	 */
	public Set<Asteroid> getAsteroids() {
		Set<Asteroid> allAsteroids = new HashSet<Asteroid>();
		for (SpatialElement element : elements) {
			if (element.isAsteroid())
				allAsteroids.add((Asteroid) element);
		}
		return allAsteroids;
	}
	
	/**
	 * Check whether the given element overlaps with other elements in this world.
	 * 
	 * @return 	...
	 * 			| (result.overlap(element) && 
	 * 			| otherElement.isValidObjectCollision(element)) ||
	 * 			| ((result == null) &&
	 * 			| (for each elem in this.elements
	 * 			| (!result.overlap(elem) || 
	 * 			| otherElement.isValidObjectCollision(element))))
	 */
	public SpatialElement getIllegalOverlap(SpatialElement element){
		for (SpatialElement otherElement: elements){
			if(element.overlap(otherElement) && element.isValidObjectCollision(otherElement)){
				return otherElement;
			}
		}
		return null;
	}
	
	/**
	 * Check whether the given element is within the bounds of this world.
	 * 
	 * @param	element
	 * 			The spatial element to check.
	 * @return	...
	 * 			| result = (element != null) &&
	 * 			| (0+element.getRadius() < element.getXComponent() < width-element.getRadius()) &&
	 * 			| (0+element.getRadius() < element.getYComponent() < height-element.getRadius())
	 */
	public boolean withinBounds(SpatialElement element){
		return (element != null && 
				(0 < element.getPosition().getXComponent() - element.getRadius())
				&& (0 < element.getPosition().getYComponent() - element.getRadius())
				&& (element.getPosition().getXComponent() + element.getRadius() < getWidth())
				&& element.getPosition().getYComponent() + element.getRadius() < getHeight());
	}
	
	/**
	 * Check whether this world can have the given spatial element.
	 * 
	 * @param 	element
	 *          The spatial element to check.
	 * @return	False if the element is not effective
	 * 			| if(element == null)
	 * 			| then result == false
	 * @return	True if the element is not terminated and this world is not terminated
	 * 			| result == (!this.isTerminated()) && (!element.isTerminated())
	 * @note	A bullet outside the boundaries or overlapping with other elements is seen as
	 * 			a case that should be resolved on appearing on screen, so canHaveAsSpatialElement
	 * 			does not need to check such cases.
	 */
	@Raw
	public boolean canHaveAsSpatialElement(SpatialElement element) {
		if(element == null)
			return false;
		return (!this.isTerminated()) && (!element.isTerminated());
	}
	
	/**
	 * Check whether this world has proper spatial elements.
	 * 
	 * @return 	All elements in the set of elements associated to this world
	 * 			must reference this world as the world they are associated to
	 * 			and it must be possible for this world to have the given element
	 * 			associated to it.
	 * 			| if (for each element in Elements: 
	 * 		   	| 	(canHaveAsSpatialElement(element)) && 
	 * 			| 		(element.getWorld() == this))
	 * 			| then result == true
	 */
	public boolean hasProperSpatialElements() {
		for (SpatialElement element : elements) {
			if (!canHaveAsSpatialElement(element))
				return false;
			if (element.getWorld() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether this world has the given element as one of its spatial
	 * elements.
	 * 
	 * @return	The list of spatial elements associated with this world 
	 * 			contains the spatial element.
	 * 			| elements.contains(element)
	 */
	@Basic
	@Raw
	public boolean hasAsSpatialElement(SpatialElement element) {
		return elements.contains(element);
	}

	/**
	 * Add the given spatial element to the array of spatial elements registered
	 * in this world.
	 * 
	 * @param 	element
	 *          The spatial element to be added.
	 * @post	...
	 * 			| if(!withinBound(element))
	 * 			| 	then element.isTerminated() && 
	 * 			| 	!this.hasAsSpatialElement(element)
	 * @effect	...
	 * 			| if( getIllegalOverlap(element) != null )
	 * 			| 	then element.resolveInitialCondition(getIllegalOverlap(element))
	 * @post	...
	 * 			| if( withinBound(element) && getIllegalOverlap(element) == null)
	 * 			|	then (hasAsSpatialElement(element) &&
	 * 			|	element.getWorld == this )
	 * @effect	...
	 * 			| if( withinBound(element) && getIllegalOverlap(element) == null)
	 * 			|	this.addAsCollision(element);
	 * @throws 	IllegalArgumentException
	 * 			This world can not have the given 
	 * 			element as Spatial element.
	 *          | !canHaveAsSpatialElement(element)
	 * @throws 	IllegalArgumentException
	 *			The given element already has a 
	 *			world associated to it.
	 *          | (element.getWorld() != null)
	 */
	public void addAsSpatialElement(SpatialElement element)
			throws IllegalArgumentException {
		if (!canHaveAsSpatialElement(element))
			throw new IllegalArgumentException("Element can't be added to the game world.");
		if (element.getWorld() != null)
			throw new IllegalArgumentException("Element already refers to a world.");
		if (!withinBounds(element)){
			// do not add the element and terminate it
			element.terminate();
		} else {
			SpatialElement overlappingElement = getIllegalOverlap(element);
			if(overlappingElement != null){
				// do not add the element and resolve
				element.resolveInitialCondition(overlappingElement);
			} else {
				elements.add(element);
				element.setWorld(this);
				this.addAsCollision(element);
			}
		}
	}

	/**
	 * Remove the association between the given spatial element and this world.
	 * 
	 * @param	element
	 *          The spatial element to be removed.
	 * @post 	The given element is no longer associated with this world.
	 * 			| ! (new this).hasAsSpatialElement(element) && 
	 * 			| element.getWorld == null
	 * @effect	The given element no longer appears in the list of collisions.
	 * 			| removeAsCollision(element)
	 * @throws 	IllegalArgumentException
	 * 			Check if this world has the given element associated to it.
	 *          | ! hasAsSpatialElement(element)
	 */
	public void removeAsSpatialElement(SpatialElement element)
			throws IllegalArgumentException {
		if (!this.hasAsSpatialElement(element))
			throw new IllegalArgumentException("Element not assigned to this world.");
		elements.remove(element);
		removeAsCollision(element);
		element.setWorld(null);
	}

	/**
	 * Variable referencing an array containing all the spatial elements
	 * registered in this world.
	 * 
	 * @invar 	The referenced array is effective. 
	 * 			| elements != null
	 * @invar 	This world can have each element of elements as a spatial element. 
	 *        	| for each element in elements: 
	 *        	| canHaveAsSpatialElement(element)
	 * @invar	Each element in the set has this world as associated world.
	 * 			| for each element in elements:
	 * 			| (element.getWorld() == this)
	 */
	private final Set<SpatialElement> elements = new HashSet<SpatialElement>();
	
	/**
	 * Check whether this world has the given collision associated with it.
	 * 
	 * @return	The list of collisions of this world contains the given collision.
	 * 			| collisions.contains(collision)
	 */
	public boolean hasAsCollision(Collision collision) {
		return collisions.contains(collision);
	}
	
	/**
	 * Get all the collisions within this world.
	 * 
	 * @return 	A set containing all the collisions associated with this world.
	 * 			| let result == Set<Collision>
	 * 			| in
	 * 			| for all collision in result
	 * 			|	this.hasAsCollision(collision)
	 */
	public Set<Collision> getCollisions() {
		Set<Collision> collisions_get = new HashSet<Collision>();
		for(Collision collision: collisions) {
			collisions_get.add(collision);
		}
		return collisions_get;
	}
	
	/**
	 * Add all collisions with the given element involved 
	 * to the set of upcoming collisions of this world.
	 * 
	 * @param	element
	 * 			The element to add collisions of.
	 * @effect	...
	 * 			| for each element2 in {element | element in elements && 
	 * 			|		element1.getTimeToCollision(element2) != Double.POSITIVE_INFINITY
	 * 			|		&& element1.isValidObjectCollision(element2)}:
	 * 			|	collisions.add(new ObjectCollision(element2,element1))
	 * @effect	...
	 * 			| collisions.add(new WallCollision(element1))
	 * @throws	NullpointerException
	 * 			| element1 == null
	 * @throws	IllegalArgumentException
	 * 			This world does not have element1 as a spatial element.
	 * 			| !this.hasAsSpatialElement(element1)
	 */
	// TODO Methode veranderd. -> Documentatie aanpassen.
	public void addAsCollision(SpatialElement element1) throws IllegalArgumentException, 
	NullPointerException{
		if (element1 == null) {
			throw new NullPointerException("Element for collision is null.");
		}
		if (!this.hasAsSpatialElement(element1)) {
			throw new IllegalArgumentException("Element for collision does not belong to this world.");
		}
		collisions.add(new WallCollision(element1));
		for (SpatialElement element2: elements) {
			if (element1.getTimeToCollision(element2) != Double.POSITIVE_INFINITY
					&& element1.isValidObjectCollision(element2)) {
					collisions.add(new ObjectCollision(element2,element1));
			}
		}
	}	
	
	/**
	 * Remove all collisions where the given element is involved in
	 * from the set of upcoming collisions of this world.
	 * 
	 * @param	element
	 * 			The elements to remove all the collisions of.
	 * @post	...
	 * 			| for each collision in collisions:
	 * 			|	!collision.contains(element)
	 */
	public void removeAsCollision(SpatialElement element) {
		List<Collision> collisionsRemoved = new ArrayList<Collision>();
		for (Collision collision: collisions) {
			if (collision.contains(element)) {
				collisionsRemoved.add(collision);
			}
		}
		collisions.removeAll(collisionsRemoved);
	}
	
	/**
	 * Update the set of collisions of this world, so that all 
	 * collisions involving the given spatial element are up to date.
	 * 
	 * @pre		...
	 * 			| elementsToUpdat != null
	 * @pre		...
	 * 			| for each element in elementsToUpdate
	 * 			|	element != null
	 * @effect	...
	 * 			| for each element in elementsToUpdate
	 * 			| if !element.isTerminated()
	 * 			| then this.removeCollision(element)
	 * 			| 	   this.addCollisoin(element)
	 */
	private void updateElementCollisions(Set<SpatialElement> elementsToUpdate){
		assert elementsToUpdate != null;
		for(SpatialElement element: elementsToUpdate){
			 assert element != null;
		}	
		for(SpatialElement element: elementsToUpdate){
			if(!element.isTerminated()){
				this.removeAsCollision(element);
				this.addAsCollision(element);
			}
		}
	}
	
	/**
	 * A set containing all upcoming collisions in this world.
	 * 
	 * @invar	The structure is effective.
	 * 			| collisions != null
	 * @invar	Each collision in collisions is effective.
	 * 			| for each collision in collisions:
	 * 			| collision != null
	 */
	private final PriorityQueue<Collision> collisions = new PriorityQueue<Collision>();
	
	
	/**
	 * Evolve the world by the time deltaT.
	 * 
	 * @pre		...
	 * 			| (deltaT >= 0)
	 * 
	 * @effect	...
	 * 			| timeLeft = deltaT
	 * 			| do	
	 * 			| 	minCollisionTime = Double.POSITIVE_INFINITY;
	 *			| 	if(!collisions.isEmpty())
	 *			|	then minCollisionTime = collisions.peek().getCollisionTime()
	 *			| 
	 *			| 	for each element in elements
	 *			|		element.move(min(minCollisionTime, timeLeft))
	 *			|
	 *			|	if( minCollsionTime < timeLeft )
	 *			|	then timeLeft -= minCollisionTime
	 *			|		 firstCollision = collision.poll()
	 * 			|		 firstCollision.resolve(collisionListener)
	 * 			|		 updateElementCollision(firstCollision.getAllElements())
	 * 			|	else
	 * 			|		for each element in {element | element is in elements && element.isThrusterActive()}
	 * 			|			element.thrust(deltaT * 1.1E18 / element.getMass())
	 * 			|		element.updateElementCollisions({element | element is in elements && element.isThrusterActive()})
	 * 			|		timeLeft -=	minCollisionTime
	 * 			|	while(0 < timeLeft)
	 */
	public void evolve(Double deltaT, CollisionListener collisionListener) {
		assert (deltaT >= 0);
		if(!this.isTerminated()){
			double timeLeft = deltaT;
			do {
				double minCollisionTime = Double.POSITIVE_INFINITY;
				if(!collisions.isEmpty())
					minCollisionTime = collisions.peek().getCollisionTime();
				
				Set<SpatialElement> thrusting = new HashSet<SpatialElement>();
				for (SpatialElement element : elements) {
					element.move(Math.min(minCollisionTime, timeLeft));
					if (element.isShip() && ((Ship) element).isThrusterActive()) {
						thrusting.add(element);
					}
				}
				
				if (minCollisionTime < timeLeft) {
					timeLeft -= minCollisionTime;
					Collision firstCollision = collisions.poll();
					firstCollision.resolve(collisionListener);
					updateElementCollisions(firstCollision.getAllElements());
				} else {
					for (SpatialElement element : thrusting) {				
						Double acc = deltaT * 1.1E18 / element.getMass();
						((Ship) element).thrust(acc);
					}
					for (Ship ship: this.getShips()) {
						Program program = ship.getProgram();
						if (program != null)
							program.advanceProgram(deltaT);
					}
					updateElementCollisions(thrusting);
					timeLeft -= minCollisionTime;
				}
			} while (0 < timeLeft);
		}
	}
}

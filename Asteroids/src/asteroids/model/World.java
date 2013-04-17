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
 * @version 1.2
 * @author  Frederik Van Eeghem (1st master Mathematical engineering), 
 * 			Pieter Lietaert (1st master Mathematical engineering)
 */

// Height,width: Not specified (Chosen: Defensively)
// Associations: Defensively

// TODO: Overflow checking.
// TODO: Within bounds is invar? En nog andere invars? Zoals nooit overlap, behalve met kogel van eigen ship?
// TODO: Vraag assistent: terminate van asteroid.
// TODO: Vraag assistent: betere manier dan instance of?
// TODO: Vraag assistent: commentaar bij evolve?
// TODO: Vraag assistent: Hoe louche is boolean teruggeven en iets veranderen? 
// TODO: Vraag assistent: Exception gooien binnen checker ok?


public class World {
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
		this.isTerminated = false;
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
	 */
	// TODO: Element kan niet null zijn?
	// TODO: Terminating asteorids.
	public void terminate() {
		if (!isTerminated()) {
			Set<SpatialElement> clonedSet = new HashSet<SpatialElement>(elements);
			for (SpatialElement element : clonedSet) {
				if (element.isAsteroid()) {
					((Asteroid) element).forceTerminate();
				}
				else {
					element.terminate();
				}
			}
			this.isTerminated = true;
		}
	}

	/**
	 * Variable registering whether or not this world is terminated.
	 */
	private boolean isTerminated;

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
	
	// Nog eens goed checken of er geen problemen zijn met acces rights
	// (vb dat ze dingen kunnen veranderen aan objecten die in ons systeem
	// zitten)
	// OPLOSSING: Alleen problemen als ze dingen kunnen veranderen zonder 
	// dat ze het door hebben, wat niet het geval is omdat de methodes van 
	// ship, asteroid en bullet duidelijk gedefinieerd zijn.
	/**
	 * Get all the ships in this game world.
	 * 
	 * @return 	A set containing all the ship elements associated with this world.
	 * 			| let result == Set<Ship>
	 * 			| in
	 * 			| for Ship ship in result
	 * 			|	this.hasAsSpatialElement(ship)
	 */
	public Set<Ship> getShips() {
		Set<Ship> allShips = new HashSet<Ship>();
		for (SpatialElement element : elements) {
			if (element instanceof Ship)
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
	 * 			| for Bullet bullet in result
	 * 			|	this.hasAsSpatialElement(bullet)
	 */
	public Set<Bullet> getBullets() {
		Set<Bullet> allBullets = new HashSet<Bullet>();
		for (SpatialElement element : elements) {
			if (element instanceof Bullet)
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
	 * 			| for Asteroid asteroid in result
	 * 			|	this.hasAsSpatialElement(asteroid)
	 */
	public Set<Asteroid> getAsteroids() {
		Set<Asteroid> allAsteroids = new HashSet<Asteroid>();
		for (SpatialElement element : elements) {
			if (element instanceof Asteroid)
				allAsteroids.add((Asteroid) element);
		}
		return allAsteroids;
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
	 * Check whether this world can have the given spatial element.
	 * 
	 * @param 	element
	 *          The spatial element to check.
	 * @return 	True if and only if the given element is effective, not
	 *         	terminated and the element can be an element of this world. 
	 *         	|result == (element != null) && (element.canHaveAsWorld(this)) &&
	 *          | (!element.isTerminated());
	 */
	@Raw
	public boolean canHaveAsSpatialElement(SpatialElement element) {
		return (element != null) && ( (!this.isTerminated())
				&& (!element.isTerminated()) );
	}

	/**
	 * Add the given spatial element to the array of spatial elements registered
	 * in this world.
	 * 
	 * @param 	element
	 *          The spatial element to be added.
	 * @post 	This world has the given element associated with it.
	 * 			| (new this).hasAsSpatialElement(element)
	 * @throws 	IllegalArgumentException
	 * 			Check if this world can have the given 
	 * 			element associated with it.
	 *          | !canHaveAsSpatialElement(element)
	 * @throws 	IllegalArgumentException
	 *			Check if the given element doesn't already has a 
	 *			world associated to it.
	 *          | (element.getWorld() != null)
	 */
	// TODO: Documentatie aanpassen aan resolveInitialCondition
	// TODO: collisionListener in verwerken...
	// M.O. Als element niet mag, wordt hij gewoon niet toegevoegd aan de lijst 
	// (vb bij slechte gestarte asteroids of outOfBounds)
	public void addAsSpatialElement(SpatialElement element)
			throws IllegalArgumentException {
		if (!canHaveAsSpatialElement(element))
			throw new IllegalArgumentException("Element can't be added to the game world.");
		if (element.getWorld() != null)
			throw new IllegalArgumentException("Element already refers to a world.");
		boolean resolved = resolveInitialCondition(element);
		if(!resolved){
			// Only add the ones that don't give any problems.
			elements.add(element);
			element.setWorld(this);
			this.addCollision(element);
		}
	}

	/**
	 * Remove the association between the given spatial element and this world.
	 * 
	 * @param	element
	 *          The spatial element to be removed.
	 * @post 	The given element is no longer associated with this world.
	 * 			| ! (new this).hasAsSpatialElement(element)
	 * @throws 	IllegalArgumentException
	 * 			Check if this world has the given element associated to it.
	 *          | ! hasAsSpatialElement(element)
	 * @throws 	IllegalArgumentException
	 * 			Check if the given element has this world associated to it.
	 *          | element.getWorld() != this
	 */
	public void removeAsSpatialElement(SpatialElement element)
			throws IllegalArgumentException {
		if (!this.hasAsSpatialElement(element))
			throw new IllegalArgumentException("Element not assigned to this world.");
		elements.remove(element);
		removeCollision(element);
		element.setWorld(null);
	}

	/**
	 * Check whether this world has proper spatial elements.
	 * 
	 * @return 	All elements in the set of elements associated to this world
	 * 			must reference this world as the world they are associated to
	 * 			and it must be possible for this world to have the given element
	 * 			associated to it.
	 * 			| for each element in Elements: 
	 * 		   	| (canHaveAsSpatialElement(element)) && 
	 * 			| 	(element.getWorld() == this)
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
	 * Check whether the given element illegally overlaps with other elements in this world.
	 * 
	 * @return 	One of the other spatial elements that the given element 
	 * 			illegally overlaps with or a non-effective element
	 * 			if none of the other elements illegally overlap with 
	 * 			the given element.
	 * 			| ( result.overlap(element) && isValidObjectCollision(result, element)) || 
	 * 			| ((result == null) &&
	 * 			| (for each element in this.elements)
	 * 			|	(!result.overlap(element)) || (!isValidObjectCollision(result, element))))
	 */
	public SpatialElement getIllegalOverlap(SpatialElement element){
		for (SpatialElement otherElement: elements){
			if(element.overlap(otherElement) 
				&& isValidObjectCollision(element, otherElement)
				&& element != otherElement){
				return otherElement;
			}
		}
		return null;
	}
	
	/**
	 * Terminate element if it illegally overlaps with one of the other elements
	 * associated with this world, except if this element or the conflicting element
	 * is a bullet in which case the conflict is resolved as a collision with a bullet.
	 * 
	 * @
	 * 
	 */
	// TODO: What to do whit elements that are wrongly placed, except for bullets?
	public boolean resolveInitialCondition(SpatialElement element){
		SpatialElement conflictingElement = this.getIllegalOverlap(element);
		if (!withinBounds(element)) {
			element.terminate();
			return true;
		} else if (conflictingElement != null) {
			 if((element.isBullet()) || (conflictingElement.isBullet())){
				 (new ObjectCollision(element, conflictingElement)).resolveBullet(element, conflictingElement);
			 }
			 return true;
		}
		return false;
	}
	
	
	/**
	 * Check whether the given element is within the bounds of this world.
	 * 
	 * @param	element
	 * 			...
	 * @return	...
	 * 			| result = (element != null) &&
	 * 			| (0+element.getRadius() < element.getXComponent() < width-element.getRadius()) &&
	 * 			| (0+element.getRadius() < element.getYComponent() < heigth-element.getRadius())
	 */
	//TODO: Ook een algemene checker maken voor alle elementen als invar?
	public boolean withinBounds(SpatialElement element){
		return ((0 < element.getPosition().getXComponent() - element.getRadius())
				&& (0 < element.getPosition().getYComponent() - element.getRadius())
				&& (element.getPosition().getXComponent() + element.getRadius() < getWidth())
				&& element.getPosition().getYComponent() + element.getRadius() < getHeight());
	}
	
	// TODO nodig?
	//	public boolean canAddAsSpatialElement(SpatialElement element){


	/**
	 * Variable referencing an array containing all the spatial elements
	 * registered in this world.
	 * 
	 * @invar 	The referenced array is effective. 
	 * 			| elements != null
	 * @invar 	Each element in the array is an effective, non-terminated spatial
	 *        	element. 
	 *        	| for each element in elements: 
	 *        	| (element != null) && (! element.isTerminated()) && 
	 *        	| (element.getWorld() == this)
	 */
	//TODO: Zetten we deze invars in?
	private final List<SpatialElement> elements = new ArrayList<SpatialElement>();
	
	/**
	 * Remove all collisions where the given element is involved in
	 * from the set of upcoming collisions of this world.
	 * 
	 * @param	element
	 * 			...
	 * @post	...
	 * 			| for each collision in collisions
	 * 			|	!collision.contains(element)
	 * 
	 */
	// Opmerking: element die null is kan geen kwaad, want gaat niet gevonden worden in de lijst.
	public void removeCollision(SpatialElement element) {
		List<Collision> collisionsRemoved = new ArrayList<Collision>();
		for (Collision collision: collisions) {
			if (collision.contains(element)) {
				collisionsRemoved.add(collision);
			}
		}
		collisions.removeAll(collisionsRemoved);
	}
	
	/**
	 * Add all collisions whit the given element involved 
	 * to the set of upcoming collisions of this world.
	 * 
	 * @param	element
	 * 			...
	 * @post	...
	 * 			|
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| !this.hasAsSpatialElement(element1)
	 */
	// TODO: Only save the first collision, instead of all the elements? Doesn't gain so much.
	public void addCollision(SpatialElement element1) throws IllegalArgumentException{
		if (this.hasAsSpatialElement(element1)) {
			throw new IllegalArgumentException("Element for collision does not belong to this world.");
		}
		collisions.add(new WallCollision(element1));
		for (SpatialElement element2: elements) {
			if (element1.getTimeToCollision(element2) != Double.POSITIVE_INFINITY
					&& isValidObjectCollision(element1, element2)) {
					collisions.add(new ObjectCollision(element2,element1));
			}
		}
	}
	
	/**
	 * A set containing all upcoming collisions in this world.
	 */
	// TODO: invariants
	private final PriorityQueue<Collision> collisions = new PriorityQueue<Collision>();
	
	/**
	 * Check if the collision between the given spatial element 1 and 
	 * spatial element 2 is a valid object collision.
	 * 
	 * @return	...
	 * 			| if((element1.getWorld() != null) && (element1.getWorld() != null) && 
				| (element1.getWorld() != element2.getWorld()))
				| then result == false
	 * @return	...
	 * 			| result == !(((element1.isBullet() && element2.isShip())
	 *			| && (element1.getShip() == element2)
	 *			| || ((element2.isBullet() && element1.isShip())
	 *			| && element2).getShip() == element1))
	 */
	public boolean isValidObjectCollision(SpatialElement element1, SpatialElement element2){
			if ((element1.getWorld() != null) && (element1.getWorld() != null) && 
			(element1.getWorld() != element2.getWorld())){
				return false;
			}	
			return !(((element1.isBullet() && element2.isShip())
				&& ((Bullet) element1).getShip() == ((Ship) element2))
				|| ((element2.isBullet() && element1.isShip())
				&& ((Bullet) element2).getShip() == ((Ship) element1)));
	}
	
	/**
	 * Update the set of collisions of this world, so that all 
	 * collisions involving the given spatial element are up to date.
	 * 
	 * @pre		...
	 * 			| elementsToUpdat != null
	 * @pre		...
	 * 			| for each element in elementsToUpdat
	 * 			|	element != null
	 * 
	 * @effect	...
	 * 			| for each element in elementsToUpdate
	 * 			| if !element.isTerminated()
	 * 			| then this.removeCollision(element)
	 * 			| 	   this.addCollisoin(element)
	 */
	public void updateElementCollisions(Set<SpatialElement> elementsToUpdate){
		assert elementsToUpdate != null;
		for(SpatialElement element: elementsToUpdate){
			 assert element != null;
		}	
		for(SpatialElement element: elementsToUpdate){
			if(!element.isTerminated()){
				this.removeCollision(element);
				this.addCollision(element);
			}
		}
	}
	
	/**
	 * Evolve.
	 */
	public void evolve(Double deltaT, CollisionListener collisionListener) {
		double timeLeft = deltaT;
		do {
			double minCollisionTime = Double.POSITIVE_INFINITY;
			if(!collisions.isEmpty())
				minCollisionTime = collisions.peek().getCollisionTime();
			
			//TODO hier of toch best binnen de else?
			// evt ook met getAllShips?
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
				updateElementCollisions(thrusting);
				timeLeft -= minCollisionTime;
			}
		} while (0 < timeLeft);
	}
}

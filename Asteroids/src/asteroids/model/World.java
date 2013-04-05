package asteroids.model;

import java.util.*;
import static asteroids.Util.*;
import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * This class implements the game world of Asteroids.
 * 
 * @invar ... | hasProperSpatialElements()
 * 
 * @version 1.1
 * @author Frederik Van Eeghem, Pieter Lietaert
 */

// Q: Samenvoegen van de checkers voor width en height? Naar bv isValidSide
// A: zou niet weten waarom want je zit met maxWidth en maxHeight etc...
public class World {
	/**
	 * Initialize this new world with given width and height.
	 * 
	 * @param width
	 *            The width for this new world.
	 * @param heigth
	 *            The height for this new world.
	 * @post | (new this).getHeigth() == height
	 * @post | (new this).getWidth() == width
	 * @throws IllegalArgumentException
	 *             | ! this.isValidWidth(width)
	 * @throws IllegalArgumentException
	 *             | ! this.isValidHeight(height)
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
	 * @post | (new this).isTerminated()
	 * @post | for each element in elements: | ((new element).isTerminated())
	 */
	// Element kan niet null zijn?
	public void terminate() {
		if (!isTerminated()) {
			for (SpatialElement element : elements) {
				element.terminate();
			}
			this.isTerminated = true;
		}
	}

	/**
	 * Variable registering whether or not this world is terminated.
	 */
	private boolean isTerminated;

	/**
	 * Get the width of this game world in km.
	 */
	@Basic
	@Raw
	public double getWidth() {
		return width;
	}

	/**
	 * Variable registering the width of this world in km.
	 */
	private final double width;

	/**
	 * Check whether the given height is a valid height for this game world.
	 * 
	 * @param width
	 *            Width to check.
	 * @return | result == (width >= 0) && (width <= maxWidth)
	 */
	public boolean isValidWidth(double width) {
		return (width >= 0) && (width <= getMaxWidth());
	}

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
	 * @param height
	 *            Height to check.
	 * @return | result == (height >= 0) && (height <= maxHeight)
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

	// TODO: Nog eens goed checken of er geen problemen zijn met acces rights
	// (vb dat ze dingen kunnen veranderen aan objecten die in ons systeem
	// zitten)
	public Set<Ship> getShips() {
		Set<Ship> allShips = new HashSet<Ship>();
		for (SpatialElement element : elements) {
			if (element instanceof Ship)
				allShips.add((Ship) element);
		}
		return allShips;
	}

	public Set<Bullet> getBullets() {
		Set<Bullet> allBullets = new HashSet<Bullet>();
		for (SpatialElement element : elements) {
			if (element instanceof Bullet)
				allBullets.add((Bullet) element);
		}
		return allBullets;
	}

	public Set<Asteroid> getAsteroids() {
		Set<Asteroid> allAsteroids = new HashSet<Asteroid>();
		for (SpatialElement element : elements) {
			if (element instanceof Asteroid)
				allAsteroids.add((Asteroid) element);
		}
		return allAsteroids;
	}

	// public Set<SpatialElement> getSpatialElementsOfClass(Class wantedClass){
	// // // TODO implement; tricky, weet niet of Class argument wel lukt...
	// // // op deze manier errors + set van wantedClass wss geen subklasse van
	// set van spatialelement...
	// // Set<wantedClass> wantedElements = new HashSet<SpatialElement>();
	// // for(SpatialElement element: elements){
	// // if(element instanceof wantedClass)
	// // wantedElements.add(element);
	// // }
	// return null;
	// }

	/**
	 * Check whether this world has the given element as one of its spatial
	 * elements.
	 */
	@Basic
	@Raw
	public boolean hasAsSpatialElement(SpatialElement element) {
		return elements.contains(element);
	}

	/**
	 * Check whether this world can have the given spatial element.
	 * 
	 * @param element
	 *            The spatial element to check.
	 * @return True if and only if the given element is effective, not
	 *         terminated and the element can be an element of this world. |
	 *         result == (element != null) && (element.canHaveAsWorld(this)) &&
	 *         (!element.isTerminated());
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
	 * @param element
	 *            The spatial element to be added.
	 * @post | (new this).hasAsSpatialElement(element)
	 * @throws IllegalArgumentException
	 *             | !canHaveAsSpatialElement(element)
	 * @throws IllegalArgumentException
	 *             | this.hasAsSpatialElement(element)
	 */
	public void addAsSpatialElement(SpatialElement element)
			throws IllegalArgumentException {
		if (!canHaveAsSpatialElement(element))
			throw new IllegalArgumentException(
					"The given spacial element can't be added to the game world.");
		if (element.getWorld() != null)
			throw new IllegalArgumentException("Element already refers to a world.");
		elements.add(element);
		element.setWorld(this);
	}

	/**
	 * Remove the given spatial element from the array of spatial elements
	 * registered in this world.
	 * 
	 * @param element
	 *            The spatial element to be removed.
	 * @post | ! (new this).hasAsSpatialElement(element)
	 * @throws IllegalArgumentException
	 *             | ! hasAsSpatialElement(element)
	 * @throws IllegalArgumentException
	 *             | element.getWorld() != this
	 */
	public void removeAsSpatialElement(SpatialElement element)
			throws IllegalArgumentException {
		if (!this.hasAsSpatialElement(element))
			throw new IllegalArgumentException(
					"Element not assigned to this world.");
		elements.remove(element);
		element.setWorld(null);		
	}

	/**
	 * Check whether this world has proper spatial elements.
	 * 
	 * @return | for each element in Elements: |
	 *         (canHaveAsSpatialElement(element)) && (element.getWorld() ==
	 *         this)
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
	
	public SpatialElement overlapWith(SpatialElement element){
		for (SpatialElement otherElement: elements){
			if(element.overlap(otherElement)){
				return otherElement;
			}
		}
		return null;
	}
	
	public boolean withinBounds(SpatialElement element){
		return (fuzzyLessThanOrEqualTo(0, element.getPosition().getXComponent())
				&& fuzzyLessThanOrEqualTo(0, element.getPosition().getYComponent())
				&& fuzzyLessThanOrEqualTo(element.getPosition().getXComponent(), getMaxWidth())
				&& fuzzyLessThanOrEqualTo(element.getPosition().getYComponent(), getMaxHeight()));
	}
	
//	public boolean canAddAsSpatialElement(SpatialElement element){
//		for (SpatialElement otherElement: elements){
//			result
//		}
//		return false;
//	}

	/**
	 * Variable referencing an array containing all the spatial elements
	 * registered in this world.
	 * 
	 * @invar The referenced array is effective. | elements != null
	 * @invar Each element in the array is an effective, non-terminated spatial
	 *        element. | for each element in elements: | (element != null) && |
	 *        (! element.isTerminated()) && | (element.getWorld() == this)
	 */
	// Zetten we deze invars in?
	private final List<SpatialElement> elements = new ArrayList<SpatialElement>();

	private void resolveBounce(SpatialElement involved1, SpatialElement involved2){
		// Suppose perfectly elastic collision.
		Vector2D unitNormal = (involved1.getPosition()
				.subtract(involved2.getPosition())).getDirection();
		Vector2D unitTangent = new Vector2D(
				-unitNormal.getYComponent(), unitNormal.getXComponent());
		double involved1NormalComponent = involved1.getVelocity()
				.getDotProduct(unitNormal);
		double involved1TangentComponent = involved1.getVelocity()
				.getDotProduct(unitTangent);
		double involved2NormalComponent = involved2.getVelocity()
				.getDotProduct(unitNormal);
		double involved2TangentComponent = involved2.getVelocity()
				.getDotProduct(unitTangent);
		// Update velocities
		double massInvolved1 = involved1.getMass();
		double massInvolved2 = involved2.getMass();
		double involved1NormalComponentUpdated = (involved1NormalComponent
				* (massInvolved1 - massInvolved2) + 2 * massInvolved2
				* involved2NormalComponent)
				/ (massInvolved1 + massInvolved2);
		double involved2NormalComponentUpdated = (involved2NormalComponent
				* (massInvolved2 - massInvolved1) + 2 * massInvolved1
				* involved1NormalComponent)
				/ (massInvolved1 + massInvolved2);
		involved1.setVelocity(unitNormal.multiply(
				involved1NormalComponentUpdated).add(
				unitTangent.multiply(involved1TangentComponent)));
		involved2.setVelocity(unitNormal.multiply(
				involved2NormalComponentUpdated).add(
				unitTangent.multiply(involved2TangentComponent)));
	}
	
	private void resolveBullet(SpatialElement involved1, SpatialElement involved2, CollisionListener collisionListener){
			if ((involved1.isBullet() && involved2.isShip())) {
				if (((Bullet) involved1).getShip() != ((Ship) involved2)) {
					involved1.terminate();
					involved2.terminate();
				}
			} else if ((involved1.isShip() && involved2.isBullet())) {
				if (((Bullet) involved2).getShip() != ((Ship) involved1)) {
					involved1.terminate();
					involved2.terminate();
				}
			} else {
				Vector2D collisionPosition;
				if(involved1.isBullet())
					collisionPosition = involved1.getPosition();
				else
					collisionPosition = involved2.getPosition();
				involved1.terminate();
				involved2.terminate();
				collisionListener.objectCollision(involved1, involved2, collisionPosition.getXComponent(), 
						collisionPosition.getYComponent());
			}
	}
	
	
	private void resolveElements(SpatialElement involved1, SpatialElement involved2,
			CollisionListener collisionListener) {
			if ((involved1.isShip() && (involved2.isShip()))
					|| (involved1.isAsteroid()) && (involved2.isAsteroid())) {
				resolveBounce(involved1,involved2);
			}
			if ((involved1.isBullet()) || (involved2.isBullet())) {
				resolveBullet(involved1,involved2,collisionListener);
			}
			if ((involved1.isShip()) && (involved2.isAsteroid())) {
				involved1.terminate();

			} else if ((involved1.isAsteroid()) && (involved2.isShip())) {
				involved2.terminate();
			}
	}

	private void resolveWall(SpatialElement involved, boolean horizontal){
		double xComp = involved.getVelocity().getXComponent();
		double yComp = involved.getVelocity().getYComponent();
		
		if(horizontal){
			involved.setVelocity(new Vector2D(xComp, -1*yComp));
		} else {
			involved.setVelocity(new Vector2D(-1*xComp, yComp));
		}
		if(involved.isBullet()){
			if (((Bullet) involved).getHasBounced()) {
				involved.terminate();
			} else {
				((Bullet) involved).bounce();
			}
		}		
	}
	

	public void evolve(Double deltaT, CollisionListener collisionListener) {
		double timeLeft = deltaT;
		do {
			SpatialElement involved1 = null;
			SpatialElement involved2 = null;
			SpatialElement involvedWall = null;
			double minCollisionTimeElement = Double.POSITIVE_INFINITY;
			double minCollisionTimeWall = Double.POSITIVE_INFINITY;
			boolean horizontal = false;
			for (SpatialElement element1: elements) {
				double collisionTimeHorizontalWall = Math.min(element1
								.getTimeToHorizontalWallCollision(0), element1
								.getTimeToHorizontalWallCollision(getHeight()));
				double collisionTimeVerticalWall = Math.min(
										element1.getTimeToVerticalWallCollision(0),
										element1.getTimeToVerticalWallCollision(getWidth()));
				double collisionTimeWall = Math.min(collisionTimeHorizontalWall, collisionTimeVerticalWall);
				if(collisionTimeWall < minCollisionTimeWall && collisionTimeWall > 0){
					minCollisionTimeWall = collisionTimeWall;
					involvedWall= element1;
					horizontal = ( collisionTimeHorizontalWall < collisionTimeVerticalWall);
				}

				for (SpatialElement element2: elements) {
					double collisionTimeElement = element1.getTimeToCollision(element2);
					if (collisionTimeElement < minCollisionTimeElement && collisionTimeElement > 0) {
						minCollisionTimeElement = collisionTimeElement;
						involved1 = element1;
						involved2 = element2;
					}
				}
			}
			
			double minCollisionTime = Math.min(minCollisionTimeWall, minCollisionTimeElement);
			
			if (!fuzzyLessThanOrEqualTo(timeLeft, minCollisionTime) && !fuzzyLessThanOrEqualTo(minCollisionTime,0)) {
				for (SpatialElement element : elements) {
					element.move(minCollisionTime);
				}
				timeLeft -= minCollisionTime;
				if(minCollisionTimeElement < minCollisionTimeWall)
					this.resolveElements(involved1, involved2, collisionListener);
				else
					this.resolveWall(involvedWall, horizontal);
			} else{
				for (SpatialElement element : elements) {
					element.move(timeLeft);
					if (element.isShip() && ((Ship) element).isThrusterActive()) {
						Double acc = deltaT * 1.1E18 / element.getMass();
						((Ship) element).thrust(acc);
					}				
				}
				timeLeft -= minCollisionTime;
			}
		} while (0 < timeLeft);
		
		
	}
}

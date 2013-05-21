package asteroids.model;

import java.util.HashSet;
import java.util.Set;

import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of object collisions for the world of the game Asteroids.
 * 
 * @invar	The collision has a valid first spatial element.
 * 			| isValidElement(getElement1()) 
 * @invar	The collision has a valid second spatial element
 * 			| isValidElement(getElement2())
 * 
 * @version 1.1
 * @author 	Frederik Van Eeghem, Pieter Lietaert
 */
public class ObjectCollision extends Collision{
	/**
	 * Initialize this new object collision with the given 
	 * spatial elements element1 and element2 as the involved elements.
	 * 
	 * @param 	element1
	 * 			The first spatial element involved in this object collision.
	 * @param 	element2
	 * 			The second spatial element involved in this object collision.
	 * @post	...
	 * 			| (new this).getElement1() == element1
	 * 			| (new this).getElement2() == element2
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| !isValidElement(element1) || !isValidElement(element2)
	 */
	public ObjectCollision(SpatialElement element1, SpatialElement element2) throws IllegalArgumentException{
		if(!isValidElement(element1) || !isValidElement(element2))
			throw new IllegalArgumentException("Non-valid element(s) given while constructing new object collision.");
		this.element1 = element1;
		this.element2 = element2;
	}
	
	/**
	 * Check whether this object collision is equal to the given collision.
	 * 
	 * @param	otherCollision
	 * 			The other collision to check equality with.
	 * @return	...
	 * 			| result == (otherCollision != null  && otherCollision.isObjectCollision()
	 *			| && (getElement1() == otherCollision.getElement1() 
	 *			| || getElement1() == otherCollision.getElement2())
	 *			| && (getElement2() == otherCollision.getElement1() 
	 *			| || getElement2() == otherCollision.getElement2()))
	 */
	public boolean equals(Collision otherCollision) {
		return  (otherCollision != null  && otherCollision.isObjectCollision()) &&
				((getElement1() == ((ObjectCollision) otherCollision).getElement1() 
				|| getElement1() == ((ObjectCollision) otherCollision).getElement2())
				&& (getElement2() == ((ObjectCollision) otherCollision).getElement1() 
				|| getElement2() == ((ObjectCollision) otherCollision).getElement2()));
	}
	
	/**
	 * Check whether this object collision contains the given spatial element.
	 * 
	 * @param	otherElement
	 * 			The element to be checked.
	 * @return	...
	 * 			| result == ((getElement1() == otherElement) 
	 * 			|			|| (getElement2() == otherElement))
	 */
	public boolean contains(SpatialElement otherElement){
		return (getElement1() == otherElement || getElement2() == otherElement);
	}
	
	/**
	 * Return the first element of this object collision.
	 */
	@Basic @Immutable
	public SpatialElement getElement1(){
		return element1;
	}
	
	/**
	 * Variable registering the first element involved in this collision.
	 */
	private final SpatialElement element1;
	
	/**
	 * Return the second element of this object collision.
	 */
	@Basic @Immutable
	public SpatialElement getElement2(){
		return element2;
	}
	
	/**
	 * Variable registering the second element involved in this collision.
	 */
	private final SpatialElement element2;
	
	/**
	 * Get the time to collision of both elements.
	 * 
	 * @return	...
	 * 			| result == element1.getTimeToCollision(element2)
	 */
	public double getCollisionTime(){
		return element1.getTimeToCollision(element2);
	}
	
	/**
	 * Get the point on the edge of element1 of this collision in the direction of the 
	 * connecting line between the positions of element1 and element2 of this collision.
	 * 
	 * @return	...
	 * 			| fuzzyEquals(result.subtract(getElement1().getPosition()).getNorm(),
	 * 			| getElement1().getRadius())
	 * @return	...
	 * 			| result.getDirection().equals(getElement2().getPosition()
	 * 			| .subtract(getElement1().getPosition()).getDirection())
	 */
	@Override
	public Vector2D getConnectingEdgePoint() {
		Vector2D unitDirection = getElement2().getPosition().subtract(getElement1().getPosition()).getDirection();
		return getElement1().getPosition().add(unitDirection.multiply(getElement1().getRadius()));
	}

	/**
	 * Resolve this collision using the given collisionListener.
	 * 
	 * @effect	...
	 * 			| if(collisionListener != null)
	 * 			| then collisionListener.objectCollision(getElement1(), getElement2(), 
	 *			| 	   this.getConnectingEdgePoint().getXComponent(),
	 *			| 	   this.getConnectingEdgePoint().getYComponent())
	 * @effect	If element1 and element2 of this collision are both ships or both asteroids
	 * 			they bounce against each other.
	 * 			| if ((getElement1().isShip() && getElement2().isShip()) 
	 * 			| || (getElement1().isAsteroid() && getElement2().isAsteroid()))
	 * 			| then resolveBounce(getElement1(),getElement2())
	 * @effect	If one of the elements in this collision is a ship and 
	 * 			the other one is an asteroid, the ship is terminated.
	 * 			| if ((getElement1().isShip() && getElement2().isAsteroid()) 
	 * 			| then getElement1().terminate()
	 * 			| else if (getElement1().isAsteroid() && getElement2().isShip()))
	 * 			| then getElement2().terminate()
	 * @effect	If one of the elements in this collision is a bullet, the collision
	 * 			with the bullet is resolved.
	 * 			| if ((getElement1().isBullet() || getElement2().isBullet()) 
	 * 			| then resolveBullet(getElement1(),getElement2())
	 */
	@Override
	public void resolve(CollisionListener collisionListener) {
		if(collisionListener != null)
		collisionListener.objectCollision(getElement1(), getElement2(), 
				this.getConnectingEdgePoint().getXComponent(),
				this.getConnectingEdgePoint().getYComponent());
		
		getElement1().resolve(getElement2());
	}
	
	/**
	 * Return all spatial elements involved in this collision.
	 * 
	 * @return	...
	 * 			| result.contains(getElement1())
	 * 			| && result.contains(getElement2())
	 * @return 	...
	 * 			| result.size() == 2
	 */
	@Override
	public Set<SpatialElement> getAllElements() {
		Set<SpatialElement> allElements = new HashSet<SpatialElement>();
		allElements.add(getElement1());
		allElements.add(getElement2());
		return allElements;
	}
}

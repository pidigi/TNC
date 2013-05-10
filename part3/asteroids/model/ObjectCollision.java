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
	 * Check if the given spatial element is a valid element.
	 * 
	 * @param	element
	 * 			The spatial element to check.
	 * @return	True if and only if the given spatial element is effective.
	 * 			| result == (element != null)
	 */
	public boolean isValidElement(SpatialElement element) {
		return (element != null);
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
		
//		if ((getElement1().isShip() && (getElement2().isShip()))
//				|| (getElement1().isAsteroid()) && (getElement2().isAsteroid())) {
//			resolveBounce(getElement1(),getElement2());
//		}
//		if ((getElement1().isBullet()) || (getElement2().isBullet())) {
//			resolveBullet(getElement1(), getElement2());
//		}
//		if ((getElement1().isShip()) && (getElement2().isAsteroid())) {
//			getElement1().terminate();
//		} else if ((getElement1().isAsteroid()) && (getElement2().isShip())) {
//			getElement2().terminate();
//		}
	}
//
//	/**
//	 * Resolve the bouncing of given spatial elements.
//	 * 
//	 * @pre		...
//	 * 			| (element1 != null) && (element2 != null)
//	 * 
//	 * @effect	...
//	 * 			| let unitNormal = (element1.getPosition().
//	 *			| 	  	subtract(element2.getPosition())).getDirection()
//	 *			| 	  unitTangent = new Vector2D(-unitNormal.getYComponent(),
//	 *			|		unitNormal.getXComponent())
//	 *			|	  velocity1 = element1.getVelocity()
//	 *			|	  velocity2 = element2.getVelocity()
//	 *			|    
//	 *			|	  element1Normal = velocity1.getDotProduct(unitNormal)
//	 * 			|	  element1Tangent = velocity1.getDotProduct(unitTangent)
//	 *			|	  element2Normal = velocity2.getDotProduct(unitNormal)
//	 *			|	  element2Tangent = velocity2.getDotProduct(unitTangent)
//	 *			|		 
//	 *			|	  element1NormalUpdated = (element1Normal*(massInvolved1 -
//	 *			|		massInvolved2) + 2 * massInvolved2 * element2Normal)/
//	 *		    |       (massInvolved1 + massInvolved2)
//	 *		    |     element2NormalUpdated = (element2Normal*(massInvolved2 - 
//	 *			|		massInvolved1) + 2 * massInvolved1 * element1Normal)/
//	 *		    |       (massInvolved1 + massInvolved2)
//	 *		    |
//	 *		    | in element1.setVelocity(unitNormal.multiply(element1NormalUpdated)
//	 *			|		.add(unitTangent.multiply(element1Tangent)))
//	 *			|		 
//	 *			|	 element2.setVelocity(unitNormal.multiply(element2NormalUpdated)
//	 *			|		.add(unitTangent.multiply(element2Tangent)))
//	 */
//	void resolveBounce(SpatialElement element1,
//			SpatialElement element2) {
//		double sumOfRadius = element1.getRadius() + element2.getRadius();
//		double mass1 = element1.getMass();
//		double mass2 = element2.getMass();
//		Vector2D deltaV = element2.getVelocity().subtract(element1.getVelocity());
//		Vector2D deltaPos = element2.getPosition().subtract(element1.getPosition());
//		
//		double J = 2*mass1*mass2*(deltaV.getDotProduct(deltaPos))
//					/(sumOfRadius*(mass1 + mass2));
//		
//		double Jx = J*deltaPos.getXComponent()/sumOfRadius;
//		double Jy = J*deltaPos.getYComponent()/sumOfRadius;
//		
//		Vector2D newVel1 = new Vector2D(element1.getVelocity().getXComponent() + Jx/mass1,
//					element1.getVelocity().getYComponent() + Jy/mass1);
//		Vector2D newVel2 = new Vector2D(element2.getVelocity().getXComponent() - Jx/mass2,
//				element2.getVelocity().getYComponent() - Jy/mass2);
//		
//		element1.setVelocity(newVel1);
//		element2.setVelocity(newVel2);
//		
//	}
//
//	/**
//	 * Resolve the collision between at least one bullet.
//	 * 
//	 * @pre		...
//	 * 			| (((element1 != null) && (element1 != null)) &&
//	 * 			| (element1.isBullet() || element2.isBullet()))
//	 * @post	...
//	 * 			| if(element1.isBullet())
//	 * 			| then  element1.terminate
//	 * 			| 		element2.terminate
//	 * 			| else  element2.terminate
//	 * 			|		element1.terminate
//	 * 			
//	 */
//	// First terminate bullet because on terminating an asteroids,
//	// two smaller asteroids might appear that instantly collide with
//	// the not yet terminated bullet.
//	void resolveBullet(SpatialElement element1,
//			SpatialElement element2) {
//			if(element1.isBullet()){ 
//				element1.terminate();
//				element2.terminate();
//			}else{
//				element2.terminate();
//				element1.terminate();
//			}
//	}
	
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

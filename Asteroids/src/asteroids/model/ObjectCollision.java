package asteroids.model;

import java.util.HashSet;
import java.util.Set;

import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of objectcollisions for the game world of the game Asteroids.
 * 
 * @version 1.0
 * @author Frederik Van Eeghem, Pieter Lietaert
 */

public class ObjectCollision extends Collision{

	/**
	 * Initialize this new objectcollision with the given elements.
	 * 
	 * @param 	element1
	 * 			The first given spatial element for this objectcollision.
	 * @param 	element2
	 * 			The second given spatial element for this objectcollision.
	 * @post	...
	 * 			| (new this).getElement1() == element1
	 * 			| (new this).getElement2() == element2
	 * @throws	NullPointerException
	 * 			...
	 * 			| (element1 == null) || (element2 == null)
	 */
	public ObjectCollision(SpatialElement element1, SpatialElement element2){
		if(element1 == null || element2 == null)
			throw new NullPointerException("Non-effective element(s)");
		this.element1 = element1;
		this.element2 = element2;
	}
	
	/**
	 * Check whether this objectcollision is equal to the given collision.
	 * 
	 * @param	otherCollision
	 * 			The other collision to check equality with.
	 * @return	...
	 * 			| result == (otherCollision != null  && otherCollision.isObjectCollision())
	 *			| && ((getElement1() == otherCollision.getElement1() 
	 *			| || getElement1() == otherCollision.getElement2())
	 *			| && (getElement2() == otherCollision.getElement1() 
	 *			| || getElement2() == otherCollision.getElement2()))
	 */
	public boolean equals(Collision otherCollision){
		return  (otherCollision != null  && otherCollision.isObjectCollision()) &&
				((getElement1() == ((ObjectCollision) otherCollision).getElement1() 
				|| getElement1() == ((ObjectCollision) otherCollision).getElement2())
				&& (getElement2() == ((ObjectCollision) otherCollision).getElement1() 
				|| getElement2() == ((ObjectCollision) otherCollision).getElement2()));
	}
	
	/**
	 * Check whether this objectcollision contains the given spatial element.
	 * 
	 * @param	otherElement
	 * 			The element to be checked.
	 * @return	...
	 * 			| result == (getElement1() == otherElement 
	 * 			|			|| getElement2() == otherElement)
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
	 * Get the vector containing the position of impact on 
	 * the edge of element1 if the collision would happen instantly.
	 * 
	 * @return	...
	 * 			| let
	 * 			| 	Vector2D unitDirection = 
	 * 			|		getElement2().getPosition().
	 * 			|		subtract(getElement1().getPosition()).getDirection();
	 * 			| in
	 * 			|	result == getElement1().getPosition().add(unitDirection.multiply(getElement1().getRadius()))
	 */
	@Override
	public Vector2D getCollisionEdge() {
		Vector2D unitDirection = getElement2().getPosition().subtract(getElement1().getPosition()).getDirection();
		return getElement1().getPosition().add(unitDirection.multiply(getElement1().getRadius()));
	}

	@Override
	public void resolve(CollisionListener collisionListener) {
		collisionListener.objectCollision(getElement1(), getElement2(), 
				this.getCollisionEdge().getXComponent(),
				this.getCollisionEdge().getYComponent());
		
		if ((getElement1().isShip() && (getElement2().isShip()))
				|| (getElement1().isAsteroid()) && (getElement2().isAsteroid())) {
			resolveBounce(getElement1(),getElement2());
		}
		if ((getElement1().isBullet()) || (getElement2().isBullet())) {
			resolveBullet(getElement1(), getElement2());
		}
		if ((getElement1().isShip()) && (getElement2().isAsteroid())) {
			getElement1().terminate();
		} else if ((getElement1().isAsteroid()) && (getElement2().isShip())) {
			getElement2().terminate();
		}
	}

	private void resolveBounce(SpatialElement element1,
			SpatialElement element2) {
		double sumOfRadius = element1.getRadius() + element2.getRadius();
		double mass1 = element1.getMass();
		double mass2 = element2.getMass();
		Vector2D deltaV = element2.getVelocity().subtract(element1.getVelocity());
		Vector2D deltaPos = element2.getPosition().subtract(element1.getPosition());
		
		double J = 2*mass1*mass2*(deltaV.getDotProduct(deltaPos))
					/(sumOfRadius*(mass1 + mass2));
		
		double Jx = J*deltaPos.getXComponent()/sumOfRadius;
		double Jy = J*deltaPos.getYComponent()/sumOfRadius;
		
		Vector2D newVel1 = new Vector2D(element1.getVelocity().getXComponent() + Jx/mass1,
					element1.getVelocity().getYComponent() + Jy/mass1);
		Vector2D newVel2 = new Vector2D(element2.getVelocity().getXComponent() - Jx/mass2,
				element2.getVelocity().getYComponent() - Jy/mass2);
		
		element1.setVelocity(newVel1);
		element2.setVelocity(newVel2);
		
	}

	public void resolveBullet(SpatialElement element1,
			SpatialElement element2) {
			if(element1.isBullet()){ //TODO is overlap van nog te verdwijnen kogel met nieuwe asteroid mogelijk?
				element1.terminate();
				element2.terminate();
			}else{
				element2.terminate();
				element1.terminate();
			}
	}
	
	@Override
	public Set<SpatialElement> getAllElements() {
		Set<SpatialElement> allElements = new HashSet<SpatialElement>();
		allElements.add(getElement1());
		allElements.add(getElement2());
		return allElements;
	}
}

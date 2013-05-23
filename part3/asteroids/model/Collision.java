package asteroids.model;

import java.util.Set;

import asteroids.CollisionListener;

/**
 * An abstract class of collisions for the world of the game Asteroids.
 * 
 * @version 1.1
 * @author Frederik Van Eeghem, Pieter Lietaert
 */

public abstract class Collision implements Comparable<Collision>{
	/**
	 * Check whether this collision is equal to the given collision.
	 * 
	 * @param 	otherCollision
	 * 			The given collision to check equality with.
	 * @return	...
	 */
	public abstract boolean equals(Collision otherCollision);
	
	/**
	 * Check whether this collision contains the given spatial element.
	 * 
	 * @param 	otherElement
	 * 			The given element to check.
	 * @return	...
	 */
	public abstract boolean contains(SpatialElement otherElement);
	
	/**
	 * Get the time to collision of this collision.
	 * 
	 * @return	...
	 * 
	 * @throws	NullPointerException
	 * 			...
	 * 			... ? this instanceof ObjectCollision
	 */
	public abstract double getCollisionTime() throws NullPointerException;
	
	/**
	 * Get the point on the edge of an object involved in the collision
	 * closest to the other object involved in the collision.
	 * 
	 * @return	...
	 */
	public abstract Vector2D getConnectingEdgePoint();
	
	/**
	 * Check if the element is a valid spatial element.
	 * 
	 * @param 	Element
	 * 			The spatial element to check.
	 * @return	True if and only if the element is effective.
	 * 			| result == (element != null)
	 */
	public boolean isValidElement(SpatialElement element) {
		return (element != null);
	}
	
	/**
	 * Compare the time to collision of this collision to the
	 * time to collision of the given other collision to see if 
	 * it is smaller, equal or larger.
	 * 
	 * @param	otherCollision
	 * 			The collision to compare the time to collision to.
	 * @return	...
	 * 			| if(this.getCollisionTime() < otherCollision.getCollisionTime())
	 * 			| then result == -1
	 * 			| else if(this.getCollisionTime() == otherCollision.getCollisionTime())
	 * 			| then result == 0
	 * 			| else result == 1
	 * @throws	NullPointerException
	 * 			...
	 * 			| otherCollision == null
	 */
	@Override
	public int compareTo(Collision otherCollision) {
		if(otherCollision == null)
			throw new NullPointerException("Non-effective collision");
		if(this.getCollisionTime() < otherCollision.getCollisionTime())
			return -1;
		if(this.getCollisionTime() == otherCollision.getCollisionTime())
			return 0;
		return 1;
	}
	
	/**
	 * Check whether this collision is a wall collision.
	 * 
	 * @return	...
	 * 			| result == (this instanceof WallCollision)
	 */
	public boolean isWallCollision(){
		return this instanceof WallCollision;
	}
	
	/**
	 * Check whether this collision is an object collision.
	 * 
	 * @return	...
	 * 			| result == (this instanceof ObjectCollision)
	 */
	public boolean isObjectCollision(){
		return this instanceof ObjectCollision;
	}
	
	/**
	 * Resolve the collision.
	 * 
	 * @param 	collisionListener
	 * 			The collisionListener used to visualize the resolve.
	 * @post 	...
	 * 
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			... ? this instanceof ObjectCollision
	 * @throws	NullPointerException
	 * 			...
	 * 			... ? this instanceof ObjectCollision
	 */
	public abstract void resolve(CollisionListener collisionListener) throws IllegalArgumentException, NullPointerException;
	
	/**
	 * Return all spatial elements involved in this collision.
	 * 
	 * @return	...
	 */
	public abstract Set<SpatialElement> getAllElements();
	
}


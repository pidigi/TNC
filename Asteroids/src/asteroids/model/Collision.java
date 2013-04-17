package asteroids.model;

import java.util.Set;

import asteroids.CollisionListener;

/**
 * An abstract class of collisions for the game world of the game Asteroids.
 * 
 * @version 1.0
 * @author Frederik Van Eeghem, Pieter Lietaert
 */

// TODO van alle var getters / checkers / setters nagaan.
// TODO ook intern alles via setters aanpassen, niet rechtstreeks...

public abstract class Collision implements Comparable<Collision>{

	/**
	 * Check whether this collision is equal to the given collision.
	 * 
	 * @param 	otherCollision
	 * 			The given collision to check equality with
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
	 */
	public abstract double getCollisionTime();
	
	
	/**
	 * Get the vector containing the position of impact on 
	 * the edge of an element involved in the collision
	 * if the collision would happen instantly.
	 * 
	 * @return	...
	 */
	public abstract Vector2D getCollisionEdge();
	
	/**
	 * Compare this collision to the other collision to see if it is smaller, equal or larger.
	 * 
	 * @param	otherCollision
	 * 			The collision to compare this collision with.
	 * @return	...
	 * 			| if(this.getCollisionTime() < otherCollision.getCollisionTime())
	 * 			|	result == -1
	 * 			| else if(this.getCollisionTime() == otherCollision.getCollisionTime())
	 * 			|			result == 0
	 * 			| 	   else 
	 * 			|			result == 1
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
	 * Check whether this collision is a wallcollision
	 * 
	 * @return	...
	 * 			| result == (this instanceof WallCollision)
	 */
	public boolean isWallCollision(){
		return this instanceof WallCollision;
	}
	
	/**
	 * Check whether this collision is an objectcollision
	 * 
	 * @return	...
	 * 			| result == (this instanceof ObjectCollision)
	 */
	public boolean isObjectCollision(){
		return this instanceof ObjectCollision;
	}
	
	/**
	 * Resolve the collision
	 * 
	 * @param 	collisionListener
	 * 			The collisionlistener used to visualize the resolve.
	 * @post 	...
	 */
	public abstract void resolve(CollisionListener collisionListener);
	
	/**
	 * Return all spatial elements involved in this collision.
	 * 
	 * @return	...
	 */
	public abstract Set<SpatialElement> getAllElements();
	
}


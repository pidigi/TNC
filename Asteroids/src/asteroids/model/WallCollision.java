package asteroids.model;

import static asteroids.Util.fuzzyEquals;

import java.util.*;

import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of wallcollisions for the game world of the game Asteroids.
 * 
 * @version 1.0
 * @author Frederik Van Eeghem, Pieter Lietaert
 */

public class WallCollision extends Collision{
	
	
	/**
	 * Initialize this new wallcollision with the given element.
	 * 
	 * @param	element
	 * 			The given spatial element for this wallcollision.
	 * @post	...
	 * 			| (new this).getElement() == element
	 * @throws	NullPointerException
	 * 			...
	 * 			| element == null
	 */
	public WallCollision(SpatialElement element){
		if (element == null)
			throw new NullPointerException("Non-effective element");
		this.element = element;
	}
	
	/**
	 * Check whether this wallcollision is equal to the given collision.
	 * 
	 * @param	otherCollision
	 * 			The collision to check equality with.
	 * @return	...
	 * 			| result == (otherCollision != null && otherCollision.isWallCollision()
	 * 			|	&& this.getElement() == otherCollision.getElement()
	 * 			|  	&& this.getWallPosition() == otherCollision.getWallPosition())
	 */
	@Override
	public boolean equals(Collision otherCollision) {
		return (otherCollision != null && otherCollision.isWallCollision()) &&
				(getElement() == ((WallCollision) otherCollision).getElement());
	}

	
	/**
	 * Check whether this wallcollision contains the given spatial element.
	 * 
	 * @param	otherElement
	 * 			The element to be checked.
	 * @return	...
	 * 			| result == (getElement() == otherElement)
	 */
	@Override
	public boolean contains(SpatialElement otherElement) {
		return (getElement() == otherElement);
	}

	/**
	 * Get the time to the first collision with a wall of the containing world, if any.
	 * 
	 * @effect	...
	 * 			| //TODO setWallPosition
	 * @return	...
	 * 			| if(getElement().getWorld() == null)
	 * 			|	result == Double.POSITIVE_INFINITY
	 * 			| else
	 * 			|	//TODO
	 */
	@Override
	public double getCollisionTime() {
		if(getElement().getWorld() != null){
			double timeTop = getElement().getTimeToHorizontalWallCollision(getElement().getWorld().getHeight());
			double timeBottom = getElement().getTimeToHorizontalWallCollision(0);
			double timeRight = getElement().getTimeToVerticalWallCollision(getElement().getWorld().getWidth());;
			double timeLeft = getElement().getTimeToVerticalWallCollision(0);
			return Math.min(Math.min(timeTop, timeBottom),Math.min(timeLeft, timeRight));
		}
		return Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Get the vector containing the position of impact on 
	 * the edge of element if the collision would happen instantly.
	 * 
	 * @return	...
	 * 			| let
	 * 			|	TODO	
	 */
	@Override
	public Vector2D getCollisionEdge(){
		Vector2D posAtColl = getElement().getPosition();//.add(getElement().getVelocity().multiply(getCollisionTime()));
		double xComp, yComp, height, width;
		xComp = posAtColl.getXComponent();
		yComp = posAtColl.getYComponent();
		height = getElement().getWorld().getHeight();
		width = getElement().getWorld().getWidth();
		
		if(Math.min(Math.abs(yComp - height), Math.abs(yComp))
				< Math.min(Math.abs(xComp - width), Math.abs(xComp))){
			if(Math.abs(yComp - height) < Math.abs(yComp))
				return new Vector2D(xComp, height);
			else
				return new Vector2D(xComp, 0);
		} else{
			if(Math.abs(xComp - width) < Math.abs(xComp))
				return new Vector2D(width, yComp);
			else
				return new Vector2D(0, yComp);
		}
	}
	
	/**
	 * Return the element of this wall collision.
	 */
	@Basic @Immutable
	public SpatialElement getElement(){
		return this.element;
	}
	
	/**
	 * Variable registering the element involved in this collision.
	 */
	private final SpatialElement element;

	@Override
	public void resolve(CollisionListener collisionListener) {
		collisionListener.boundaryCollision(getElement(),
				this.getCollisionEdge().getXComponent(),
				this.getCollisionEdge().getYComponent());
		
		double xComp = getElement().getVelocity().getXComponent();
		double yComp = getElement().getVelocity().getYComponent();
//		double xPos = getCollisionEdge().getXComponent();
		double yPos = getCollisionEdge().getYComponent();
		
		if(fuzzyEquals(yPos, 0) || fuzzyEquals(yPos, getElement().getWorld().getHeight())){
			getElement().setVelocity(new Vector2D(xComp, -1*yComp));
		} else {
			getElement().setVelocity(new Vector2D(-1*xComp, yComp));
		}
		if(getElement().isBullet()){
			if (((Bullet) getElement()).getHasBounced()) {
				getElement().terminate();
			} else {
				((Bullet) getElement()).bounce();
			}
		}
		
	}

	/**
	 * Return all spatial elements involved in this collision
	 * 
	 * @return	...
	 * 			| result.contains(getElement())
	 * @return 	...
	 * 			| result.size() == 1
	 */
	@Override
	public Set<SpatialElement> getAllElements() {
		Set<SpatialElement> allElements = new HashSet<SpatialElement>();
		allElements.add(getElement());
		return allElements;
	}
}

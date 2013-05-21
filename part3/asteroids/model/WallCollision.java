package asteroids.model;

import static asteroids.Util.fuzzyEquals;

import java.util.*;

import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of wall collisions for the world of the game Asteroids.
 * 
 * @invar	...
 * 			| isValidElement()
 * 
 * @version 1.1
 * @author  Frederik Van Eeghem, Pieter Lietaert
 */
public class WallCollision extends Collision{
	/**
	 * Initialize this new wall collision with the given element.
	 * 
	 * @param	element
	 * 			The given spatial element for this wall collision.
	 * @post	...
	 * 			| (new this).getElement() == element
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| !isValidElement(element)
	 */
	public WallCollision(SpatialElement element) throws IllegalArgumentException{
		if (!isValidElement(element))
			throw new IllegalArgumentException("Non-valid element given while constructing wall collision.");
		this.element = element;
	}
	
	/**
	 * Check whether this wall collision is equal to the given collision.
	 * 
	 * @param	otherCollision
	 * 			The collision to check equality with.
	 * @return	...
	 * 			| result == (otherCollision != null && otherCollision.isWallCollision())
	 * 			|	&& (this.getElement() == otherCollision.getElement())
	 */
	@Override
	public boolean equals(Collision otherCollision) {
		return (otherCollision != null) && otherCollision.isWallCollision() &&
				(getElement() == ((WallCollision) otherCollision).getElement());
	}

	
	/**
	 * Check whether this wall collision contains the given spatial element.
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
	 * @return	...
	 * 			| if(getElement().getWorld() == null)
	 * 			| then result == Double.POSITIVE_INFINITY
	 * @return	...
	 * 			| if(getElement().getWorld() != null)
	 * 			| then element = this.getElement()
	 * 			| 	   let times == {time in double | when (element.move(time)) then
     *      	|		(element.getXComponent() == element.getRadius()
	 * 			|		|| element.getXComponent() == element.getWorld().getWidth()-element.getRadius()
	 * 			|   	|| element.getYComponent() == element.getRadius()
	 * 			|		|| element.getYComponent() == element.getWorld().getHeigth()-getElement().getRadius())}
	 * 			| 	   in result == minimum(times)
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
	 * Get the point on the edge of the walls of this collision 
	 * closest to the element of this wall collision object.
	 * 
	 * @return	...
	 * 			| let
	 * 			|	height = getElement().getWorld().getHeight()
	 * 			|	width = getElement().getWorld().getWidth()
	 * 			| in boundaryPoints == {point in Vector2D | (point.getXComponent() == 0 || 
	 * 			|	point.getXComponent() == width || point.getYComponent() == 0 ||
	 * 			|	point.getYComponent() == height)}
     *      	|
     *      	| find result in boundaryPoints so that
     *      	|   minimum(abs(point.getXComponent()), abs(result.getYComponent()),
     *      	|			abs(width-point.getXComponent()), abs(height-result.getYComponent()))
     *      	| is minimized
	 */
	@Override
	public Vector2D getConnectingEdgePoint(){
		Vector2D posAtColl = getElement().getPosition();
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

	/**
	 * Resolve this wall collision by bouncing the element of this
	 * collision against one of the walls and using the 
	 * given collision listener.
	 * 
	 * @post	...
	 * 			| if(collisionListener != null)
	 * 			| then collisionListener.boundaryCollision(getElement(),
	 *			| 	   this.getConnectingEdgePoint().getXComponent(),
	 *			| 	   this.getConnectingEdgePoint().getYComponent())
	 * @post	The x-component of the velocity of the element of this wall collision
	 * 			is reversed if the element collides with a vertical wall,
	 * 			else the y-component is reversed.
	 * 			| let yPos == getConnectingEdgePoint().getYComponent()
	 * 			| in 
	 * 			| if(fuzzyEquals(yPos, 0) || 
	 * 			|	fuzzyEquals(yPos, getElement().getWorld().getHeight()))
	 * 			| then 
	 * 			| ((new this).getVelocity().getYComponent() == 
	 * 			| 		this.getVelocity().getYComponent*-1)
	 * 			| else 
	 * 			| ((new this).getVelocity().getXComponent() == 
	 * 			| 		this.getVelocity().getXComponent*-1) &&
	 */
	@Override
	public void resolve(CollisionListener collisionListener) {
		if(collisionListener != null)
		collisionListener.boundaryCollision(getElement(),
				this.getConnectingEdgePoint().getXComponent(),
				this.getConnectingEdgePoint().getYComponent());

		double yPos = getConnectingEdgePoint().getYComponent();
		
		getElement().resolveWall(fuzzyEquals(yPos, 0) || fuzzyEquals(yPos, getElement().getWorld().getHeight()));
	}

	/**
	 * Return all spatial elements involved in this wall collision.
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

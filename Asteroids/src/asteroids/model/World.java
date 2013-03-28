package asteroids.model;

import java.util.*;
import static asteroids.Util.fuzzyLessThanOrEqualTo;

import be.kuleuven.cs.som.annotate.*;

/**
 * This class implements the interface IFacade to use the 
 * class Ship to control ships in the GUI of Asteroids.
 * 
 * @version	1.0
 * @author 	Frederik Van Eeghem, Pieter Lietaert
 */

public class World {

	public World(double width, double height) throws IllegalArgumentException{
		if(!isValidWidth(width) || !isValidHeight(height))
			throw new IllegalArgumentException("Illegal dimension.");
		this.width = width;
		this.height = height;
		this.isTerminated = false;
	}

	public boolean isTerminated(){
		return this.isTerminated;
	}

	public void terminate(){
		if(!isTerminated()){
			for(SpacialElement element: elements){
				element.terminate();
			}
			this.isTerminated = true;
		}
	}

	private boolean isTerminated;


	public double getWidth(){
		return width;
	}

	// evt fuzzylessorequalto (bij 0 wel zo houden)
	public boolean isValidWidth(double width){
		return (width >= 0) && (width <= World.getMaxWidth());		
	}

	public double getHeight() {
		return height;
	}

	public boolean isValidHeight(double height){
		return (height >= 0) && (height <= World.getMaxHeight());		
	}

	private final double width;

	private final double height;

	/**
	 * Get the maximum value for the width of this game world.
	 */
	public static double getMaxHeight() {
		return maxHeight;
	}

	/**
	 * Get the maximum value for the width of this game world.
	 */
	public static double getMaxWidth() {
		return maxWidth;
	}

	// Nog eens goed checken of er geen problemen zijn met acces rights 
	// (vb dat ze dingen kunnen veranderen aan objecten die in ons systeem zitten)
	public Set<Ship> getShips(){
		Set<Ship> allShips = new HashSet<Ship>();
		for(SpacialElement element : elements){
			if(element instanceof Ship)
				allShips.add((Ship) element);
		}
		return allShips;
	}

	public Set<Bullet> getBullets(){
		Set<Bullet> allBullets = new HashSet<Bullet>();
		for(SpacialElement element : elements){
			if(element instanceof Bullet)
				allBullets.add((Bullet) element);
		}
		return allBullets;
	}

	public Set<Asteroid> getAsteroids(){
		Set<Asteroid> allAsteroids = new HashSet<Asteroid>();
		for(SpacialElement element : elements){
			if(element instanceof Asteroid)
				allAsteroids.add((Asteroid) element);
		}
		return allAsteroids;
	}

	//	public Set<SpacialElement> getSpacialElementsOfClass(Class wantedClass){
	////		// TODO implement; tricky, weet niet of Class argument wel lukt...
	////		// op deze manier errors + set van wantedClass wss geen subklasse van set van spacialelement...
	////		Set<wantedClass> wantedElements = new HashSet<SpacialElement>();
	////		for(SpacialElement element: elements){
	////			if(element instanceof wantedClass)
	////				wantedElements.add(element);
	////		}
	//		return null;
	//	}

	public boolean hasAsSpacialElement(SpacialElement element){
		return elements.contains(element);
	}

	// checken of het terminated moet zijn?????
	public boolean canHaveAsSpacialElement(SpacialElement element){
		return (element != null) && (element.canHaveAsWorld(this)) && (!element.isTerminated());
	}

	public void addAsSpacialElement(SpacialElement element) throws IllegalArgumentException{
		if(!canHaveAsSpacialElement(element))
			throw new IllegalArgumentException();
		boolean successfullyAdded = elements.add(element);
		if(!successfullyAdded)
			throw new IllegalArgumentException("Element already present.");
		element.setWorld(this);
	}

	public void removeAsSpacialElement(SpacialElement element){
		if(element.getWorld() != this)
			throw new IllegalArgumentException("Element not assigned to this world.");
		// TODO nog andere exceptions?????
		element.setWorld(null);
		elements.remove(element);
	}

	public boolean hasProperSpacialElements(){
		for(SpacialElement element: elements){
			if(!canHaveAsSpacialElement(element))
				return false;
			if(element.getWorld() != this)
				return false;
		}
		return true;
	}



	private void resolve(SpacialElement involved1,SpacialElement involved2){
		// Case of two ships or two asteroids that collide
		// For now just exchange the velocity of both ships/asteroids
		if (involved2 != null) {
			if ((involved1.isShip() && (involved2.isShip())) || 
					(involved1.isAsteroid()) && (involved2.isAsteroid())){
				// suppose perfectly elestic collision
				Vector2D unitNormal = (involved1.getPosition().subtract(involved2.getPosition())).getDirection();
				Vector2D unitTangent = new Vector2D(-unitNormal.getYComponent(),unitNormal.getXComponent());
				double involved1NormalComponent = involved1.getVelocity().getDotProduct(unitNormal);
				double involved1TangentComponent = involved1.getVelocity().getDotProduct(unitTangent);
				double involved2NormalComponent = involved2.getVelocity().getDotProduct(unitNormal);
				double involved2TangentComponent = involved2.getVelocity().getDotProduct(unitTangent);
				// Update velocities
				double massInvolved1 = involved1.getMass();
				double massInvolved2 = involved2.getMass();
				double involved1NormalComponentUpdated = (involved1NormalComponent*
						(massInvolved1 - massInvolved2)	+ 2 * massInvolved2 * involved2NormalComponent)
						/(massInvolved1 + massInvolved2);
				double involved2NormalComponentUpdated = (involved2NormalComponent*
						(massInvolved2 - massInvolved1)	+ 2 * massInvolved1 * involved1NormalComponent)
						/(massInvolved1 + massInvolved2);
				involved1.setVelocity(unitNormal.multiply(involved1NormalComponentUpdated).add(
						unitTangent.multiply(involved1TangentComponent)));
				involved2.setVelocity(unitNormal.multiply(involved2NormalComponentUpdated).add(
						unitTangent.multiply(involved2TangentComponent)));
			}
			// Case of bullet colliding with something
			if((involved1.isBullet()) || (involved2.isBullet())){
				// TODO : disappearing of the bullet?
				if((involved1.isBullet() && involved2.isShip())) {
					if (((Bullet)involved1).getShip() != ((Ship)involved2)){
						involved1.die();
						involved2.die();
					}
				} else if((involved1.isShip() && involved2.isBullet())) {
					if (((Bullet)involved2).getShip() != ((Ship)involved1)){
						involved1.die();
						involved2.die();
					}
				}else {
					involved1.die();
					involved2.die();
				}
			}
			// Case of asteroid colliding with ship
			else if ((involved1.isShip()) && (involved2.isAsteroid())) {
				involved1.die();

			}
			else if ((involved1.isAsteroid()) && (involved2.isShip())) {
				involved2.die();
			}
		}
		else if(involved1 != null) {
			if(involved1.isBullet()) {
				if (((Bullet)involved1).getHasBounced()) {
					involved1.die();
				}
				else {
					((Bullet)involved1).bounce();
				}
					
			}			
			if (Math.min(involved1.getTimeToHorizontalWallCollision(0), involved1.getTimeToHorizontalWallCollision(getHeight())) 
					> Math.min(involved1.getTimeToVerticalWallCollision(0), involved1.getTimeToVerticalWallCollision(getWidth()))){
				involved1.setVelocity(new Vector2D(-1*involved1.getVelocity().getXComponent(),involved1.getVelocity().getYComponent()));
			}
			else {
				involved1.setVelocity(new Vector2D(involved1.getVelocity().getXComponent(),-1*involved1.getVelocity().getYComponent()));
			}
		}
	}

	public void evolve(Double deltaT){
		double minCollisionTime = Double.POSITIVE_INFINITY;
		double totalToCollisionTime = deltaT;
		do {
			SpacialElement element1 = null;
			SpacialElement element2 = null;
			SpacialElement involved1 = null;
			SpacialElement involved2 = null;

			for(int i = 0; i < this.elements.size(); i++) {
				element1 = this.elements.get(i);				
				
				double collisionTimeWall = Math.min(
					Math.min(element1.getTimeToHorizontalWallCollision(0),
							 element1.getTimeToHorizontalWallCollision(getHeight())),
					Math.min(element1.getTimeToVerticalWallCollision(0),
							 element1.getTimeToVerticalWallCollision(getWidth()))
					);
				
				if (collisionTimeWall < minCollisionTime && collisionTimeWall>0) {
					minCollisionTime = collisionTimeWall;
					involved1 = element1;
					involved2 = null;
				}
				for(int j=i+1; j < this.elements.size(); j++) {
					element2 = this.elements.get(j);
					double collisionTime = element1.getTimeToCollision(element2);
					if (collisionTime < minCollisionTime && collisionTime>0) {
						
						minCollisionTime = collisionTime;
						involved1 = element1;
						involved2 = element2;
					}
				}
			}
			if (totalToCollisionTime > minCollisionTime) {
				for(SpacialElement element : elements) {
					element.move(minCollisionTime);
				}
				totalToCollisionTime -= minCollisionTime;
				this.resolve(involved1,involved2);
			}
			else {
				for(SpacialElement element : elements) {
					element.move(totalToCollisionTime);
					if (element.isShip() && ((Ship) element).isThrusterActive()) {
						Double acc = deltaT*1.1E18/element.getMass();
						((Ship) element).thrust(acc);
					}
				}
			}
		} while (0>totalToCollisionTime);
	}
	

	private final static double maxHeight = Double.MAX_VALUE;

	private final static double maxWidth = Double.MAX_VALUE;

	private final ArrayList<SpacialElement> elements = new ArrayList<SpacialElement>();
}

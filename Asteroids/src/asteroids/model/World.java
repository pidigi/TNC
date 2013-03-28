package asteroids.model;

import java.util.*;

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
	
	
	private void resolve(SpacialElement Involved1,SpacialElement Involved2, 
			double timeToCollision, double restT){
		// Case of two ships that collide
		if (((Involved1 instanceof Ship) && (Involved2 instanceof Ship)) || 
				((Involved1 instanceof Asteroid) && (Involved2 instanceof Asteroid))){
			// TODO : Incalculate the mass of both elements as well.
			Involved1.move(timeToCollision);
			Involved2.move(timeToCollision);
			Vector2D vel1 = Involved1.getVelocity();
			Vector2D vel2 = Involved2.getVelocity();
			Vector2D dir1 = vel1.getDirection();
			Vector2D dir2 = vel2.getDirection();
			
			Involved1.setVelocity(dir2.multiply(vel1.getNorm()));
			Involved2.setVelocity(dir1.multiply(vel2.getNorm()));
			Involved1.move(restT);
			Involved2.move(restT);
		}
		// Case of bullet colliding with something
		else if((Involved1 instanceof Bullet) || (Involved2 instanceof Bullet)) {
			// TODO : disappearing of the bullet?
			Involved1.move(timeToCollision);
			Involved2.move(timeToCollision);
			if((Involved1 instanceof Bullet) && (Involved2 instanceof Ship)) {
				if (((Bullet)Involved1).getShip() != ((Ship)Involved2)){
					Involved1.die();
					Involved2.die();
				}
				else {
					Involved1.move(restT);
					Involved2.move(restT);	
				}
			}
			else if((Involved2 instanceof Bullet) && (Involved1 instanceof Ship)) {
				if (((Bullet)Involved2).getShip() != ((Ship)Involved1)){
					Involved1.die();
					Involved2.die();
				}
				else {
					Involved1.move(restT);
					Involved2.move(restT);	
				}
			}
			else
				Involved1.die();
				Involved2.die();
			}
		// Case of asteroid colliding with ship
		else if ((Involved1 instanceof Ship) && (Involved2 instanceof Asteroid)) {
			Involved1.move(timeToCollision);
			Involved2.move(timeToCollision);
			Involved1.terminate();
			Involved2.move(restT);
			
		}
		else if ((Involved1 instanceof Asteroid) && (Involved2 instanceof Ship)) {
			Involved1.move(timeToCollision);
			Involved2.move(timeToCollision);
			Involved2.terminate();
			Involved1.move(restT);
		}
	}
	
	private void resolve(SpacialElement involved1,SpacialElement involved2){
		// Case of two ships or two asteroids that collide
		// For now just exchange the velocity of both ships/asteroids
		if ((involved1.isShip() && (involved2.isShip())) || 
				(involved1.isAsteroid()) && (involved2.isAsteroid())){
			// TODO : Take the mass of both elements into account.
			Vector2D vel1 = involved1.getVelocity();
			Vector2D vel2 = involved2.getVelocity();
			Vector2D dir1 = vel1.getDirection();
			Vector2D dir2 = vel2.getDirection();
			involved1.setVelocity(dir2.multiply(vel1.getNorm()));
			involved2.setVelocity(dir1.multiply(vel2.getNorm()));
			
			// suppose perfectly elestic collision
			Vector2D unitNormal = (involved1.getPosition().subtract(involved2.getPosition())).getDirection();
			Vector2D unitTangent = new Vector2D(-unitNormal.getYComponent(),unitNormal.getXComponent());
			double involved1NormalComponent = 0;
			
			
			
			
			
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
		
		// TODO botsen met randen
	}
	
	
	// Changing back to hashset with iterator?
	public void evolve(Double deltaT){
		
		
		// Getting the minimum collision time
		double minTime = Double.MAX_VALUE;
		double collisionTime;
		SpacialElement involved1 = null;
		SpacialElement involved2 = null;
		for (SpacialElement element1: elements){
			for (SpacialElement element2: elements){
				collisionTime = element1.getTimeToCollision(element2);
				if(collisionTime < minTime){
					minTime = collisionTime;
					involved1 = element1;
					involved2 = element2;
				}
			}
		}
		
		for(SpacialElement element: elements){
			element.move(Math.min(minTime, deltaT));
			if (element instanceof Ship){
				if (((Ship)element).isThrusterActive()) {
					Double acc = Math.min(minTime, deltaT) * 1.1E18 / element.getMass();
					((Ship) element).thrust(acc);
				}
			}
		}
			
		if(minTime <= deltaT){
			this.resolve(involved1,involved2);
			evolve(deltaT - minTime);
		}

	}
	

	private final static double maxHeight = Double.MAX_VALUE;
	
	private final static double maxWidth = Double.MAX_VALUE;
	
	private final ArrayList<SpacialElement> elements = new ArrayList<SpacialElement>();
}

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
					Involved1.terminate();
					Involved2.terminate();
				}
				else {
					Involved1.move(restT);
					Involved2.move(restT);	
				}
			}
			else if((Involved2 instanceof Bullet) && (Involved1 instanceof Ship)) {
				if (((Bullet)Involved2).getShip() != ((Ship)Involved1)){
					Involved1.terminate();
					Involved2.terminate();
				}
				else {
					Involved1.move(restT);
					Involved2.move(restT);	
				}
			}
			else
				Involved1.terminate();
				Involved2.terminate();
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
	
	// Changing back to hashset with iterator?
	public void evolve(Double deltaT){
		// Getting the times to collision
		ArrayList<Double> timesToCollision = new ArrayList<Double>();
		for (int i = 0; i < elements.size(); i = i+1)
			for (int j = 0; j < elements.size(); j = j+1)
				timesToCollision.add(elements.get(i).getTimeToCollision(elements.get(j)));
		// Finding the minimum of the list
		Double min = Double.MAX_VALUE;
		Boolean go = true;
		ArrayList<SpacialElement>  elementsRemoved = new ArrayList<SpacialElement>();
		while (go) {
			for (Double collisionTime : timesToCollision) {
				if (collisionTime <= min) {
					min = collisionTime;
					}
				}
			timesToCollision.remove(min);
			// Resolving time to collision
			if (min <= Double.MAX_VALUE) {
				if (min < deltaT){
					go = false;
					}
				else{
					int indexOfCollision = timesToCollision.indexOf(min);
					int indexInvolved1 = indexOfCollision%elements.size();
					int indexInvolved2 = (indexOfCollision - indexOfCollision%elements.size())/elements.size()+1;
					SpacialElement involved1 = elements.get(indexInvolved1);
					SpacialElement involved2 = elements.get(indexInvolved2);
					this.resolve(involved1,involved2,min,deltaT-min);
					elementsRemoved.add(involved1);
					elementsRemoved.add(involved2);
					timesToCollision.remove(min);
				}
			}
			else {
				go = false;
			}
		}
		
		for(SpacialElement element : elements) {
			if (!elementsRemoved.contains(element)) {
				element.move(deltaT);
			}
		}
		
	}
	
	private final static double maxHeight = Double.MAX_VALUE;
	
	private final static double maxWidth = Double.MAX_VALUE;
	
	private final ArrayList<SpacialElement> elements = new ArrayList<SpacialElement>();
}

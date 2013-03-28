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


/**
 * @invar	...
 * 			| hasProperSpacialElements()
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
//		Set<wantedClass> wantedElements = new HashSet<SpacialElement>();
//		for(SpacialElement element: elements){
//			if(element instanceof wantedClass)
//				wantedElements.add(element);
//		}
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
	
	public void removeAsSpacialElement(SpacialElement element) throws IllegalArgumentException{
		if(!hasAsSpacialElement(element))
			throw new IllegalArgumentException("Element not assigned to this world.");
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
	
	public void evolve(){
		
	}
	
	private final static double maxHeight = Double.MAX_VALUE;
	
	private final static double maxWidth = Double.MAX_VALUE;
	
	private final Set<SpacialElement> elements = new HashSet<SpacialElement>();
}

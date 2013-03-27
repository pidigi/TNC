package asteroids.model;

import java.util.*;

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
	}
	
	
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
	
	public boolean hasAsSpacialElement(SpacialElement element){
		return elements.contains(element);
	}
	
	public boolean canHaveAsShip(){
		return false;
	}
	
	public Set<SpacialElement> getSpacialElementsOfClass(Class wantedClass){
		// TODO implement; tricky, weet niet of Class argument wel lukt...
		// op deze manier errors + set van wantedClass wss geen subklasse van set van spacialelement...
		Set<wantedClass> wantedElements = new HashSet<SpacialElement>();
		for(SpacialElement element: elements){
			if(element instanceof wantedClass)
				wantedElements.add(element);
		}
		return null;
	}
	
	public void addSpacialElement(SpacialElement element) throws IllegalArgumentException, NullPointerException{
		if(element == null)
			throw new NullPointerException();
//		if(element.isTerminated())
//			throw new IllegalArgumentException();
		boolean successfullyAdded = elements.add(element);
		if(!successfullyAdded)
			throw new IllegalArgumentException("Element already present.");
//		element.setWorld(this);
	}
	
	private final static double maxHeight = Double.MAX_VALUE;
	
	private final static double maxWidth = Double.MAX_VALUE;
	
	private final Set<SpacialElement> elements = new HashSet<SpacialElement>();
}

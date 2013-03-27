package asteroids.model;

import java.util.*;

/**
 * This class implements the interface IFacade to use the 
 * class Ship to control ships in the GUI of Asteroids.
 * 
 * @version	1.0
 * @author 	Frederik Van Eeghem, Pieter Lietaert
 */

public class GameWorld {
	
	//public GameWorld(){
		
	//}
	
	
	public double getWidth(){
		return width;
	}
	
	public double getHeigth() {
		return heigth;
	}
	
	private final double width;
	
	private final double heigth;

	
	public static double getMaxheigth() {
		return maxHeigth;
	}
	
	/**
	 * Get the maximum value for the width of this game world.
	 */
	public static double getMaxwidth() {
		return maxWidth;
	}
	
	public boolean hasAsShip(Ship ship){
		return ships.contains(ship);
	}
	
	public boolean canHaveAsShip(){
		return false;
	}
	
	// Moet dit wel final zijn? Kan in principe veranderen nadat de gameworld is aangemaakt.
	private final static double maxHeigth = Double.MAX_VALUE;
	
	private final static double maxWidth = Double.MAX_VALUE;
	
	private final Set<Ship> ships = new HashSet<Ship>();
}

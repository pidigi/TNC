package asteroids.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of ships for the game asteroids.
 * 
 * @version  1.0
 * @author   Frederik Van Eeghem (1st master Mathematical engineering), 
 *		 	 Pieter Lietaert (1st master Mathematical engineering)
 */
public class AsteroidRock extends SpacialElement{
	@Raw
	public AsteroidRock(Vector2D position, double radius, Vector2D velocity, double maxSpeed, double mass)
			throws IllegalArgumentException, NullPointerException{
		super(position,radius,velocity,maxSpeed,mass);
	}
}

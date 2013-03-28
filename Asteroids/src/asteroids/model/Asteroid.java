package asteroids.model;

import java.util.Random;
import static asteroids.Util.*;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of ships for the game asteroids.
 * 
 * @version  1.0
 * @author   Frederik Van Eeghem (1st master Mathematical engineering), 
 *		 	 Pieter Lietaert (1st master Mathematical engineering)
 */
public class Asteroid extends SpacialElement{
	@Raw
	public Asteroid(Vector2D position, double radius, Vector2D velocity, double maxSpeed, double mass)
			throws IllegalArgumentException, NullPointerException{
		super(position,radius,velocity,maxSpeed,mass);
		random = new Random();
	}
	
	@Raw
	public Asteroid(Vector2D position, double radius, Vector2D velocity)
			throws IllegalArgumentException, NullPointerException{
		this(position,radius,velocity,300000,4/3*Math.PI*Math.pow(radius, 3)*Asteroid.getMassDensity());
	}
	
	public Asteroid(Vector2D position, double radius, 
			Vector2D velocity, Random random) {
		this(position, radius, velocity);
		this.random = random;
	}
	
	public void die(){
		if(fuzzyLessThanOrEqualTo(30, getRadius())){
			double newRandomAngle = random.nextDouble()*2*Math.PI;
			Vector2D randomDirection = new Vector2D(Math.cos(newRandomAngle),
					Math.sin(newRandomAngle));
			double radiusChild = getRadius()/2;
			Vector2D positionChild1 = getPosition().add(randomDirection.multiply(radiusChild));
			Vector2D positionChild2 = getPosition().add(randomDirection.multiply(-radiusChild));
			Vector2D velocityChild1 = randomDirection.multiply(getVelocity().getNorm()*1.5);
			SpacialElement childAsteroid1 = new Asteroid(positionChild1, radiusChild, velocityChild1, random);
			SpacialElement childAsteroid2 = new Asteroid(positionChild2, radiusChild, velocityChild1.multiply(-1), random);
			getWorld().addAsSpacialElement(childAsteroid1);
			getWorld().addAsSpacialElement(childAsteroid2);
		}
		this.terminate();
		this.getWorld().removeAsSpacialElement(this);
	}
	
	public static double getMassDensity(){
		return massDensity;
	}
	
	private static final double massDensity = 2.65*Math.pow(10,12);
	
	private Random random;
}

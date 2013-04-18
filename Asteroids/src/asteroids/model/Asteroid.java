package asteroids.model;

import java.util.Random;
import static asteroids.Util.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of asteroids for the game asteroids.
 * 
 * @version  1.2
 * @author   Frederik Van Eeghem (1st master Mathematical engineering), 
 *		 	 Pieter Lietaert (1st master Mathematical engineering)
 */
public class Asteroid extends SpatialElement{
	/**
	 * Initialize this new asteroid.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new asteroid.
	 * @param 	radius
	 * 			The radius for this new asteroid.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new asteroid.
	 * @param 	maxSpeed
	 * 			The maximum allowed speed for this new asteroid.
	 * @param	random
	 * 			The random number generator.
	 * @post	The random generator of this asteroid is equal to the given random generator.
	 * 			| this.random == random
	 * @effect	This new asteroid is initialized as a spatial element with the given position,
	 * 			radius, velocity, maximum speed and mass derived from the mass density and size.
	 * 			| let mass = 4/3*PI*radius^3*getMassDensity()
	 * 			| in
	 * 			| super(position, radius, velocity, maxSpeed, mass)
	 */
	@Raw
	public Asteroid(Vector2D position, double radius, Vector2D velocity, double maxSpeed, Random random)
			throws IllegalArgumentException, NullPointerException{
		super(position,radius,velocity,maxSpeed,4/3*Math.PI*Math.pow(radius,3)*massDensity);
		this.random = random;
	}
	
	/**
	 * Initialize this new asteroid.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new asteroid.
	 * @param 	radius
	 * 			The radius for this new asteroid.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new asteroid.
	 * @param	random
	 * 			The random number generator.
	 * @post	The random generator of this asteroid is equal to the given random generator.
	 * 			| this.random == random
	 * @effect	This new asteroid is initialized as a spatial element with the given position,
	 * 			radius, velocity, maximum speed equal to the speed of light
	 * 			and mass derived from the mass density and size.
	 * 			| let mass = 4/3*PI*radius^3*getMassDensity()
	 * 			| in
	 * 			| super(position, radius, velocity, maxSpeed, mass)
	 */
	@Raw
	public Asteroid(Vector2D position, double radius, 
			Vector2D velocity, Random random) {
		this(position, radius, velocity, 300000, random);
	}
	
	/**
	 * Initialize this new asteroid.
	 * 
	 * @param 	position
	 * 			The 2D vector containing the position coordinates for this new asteroid.
	 * @param 	radius
	 * 			The radius for this new asteroid.
	 * @param 	velocity
	 * 			The 2D vector containing the velocity components for this new asteroid.
	 * @post	The random generator of this asteroid is equal to a newly created random generator.
	 * 			| this.random == new Random()
	 * @effect	This new asteroid is initialized as a spatial element with the given position,
	 * 			radius, velocity, maximum speed equal to the speed of light
	 * 			and mass derived from the mass density and size.
	 * 			| let mass = 4/3*PI*radius^3*getMassDensity()
	 * 			| in
	 * 			| super(position, radius, velocity, maxSpeed, mass)
	 */
	@Raw
	public Asteroid(Vector2D position, double radius, Vector2D velocity)
			throws IllegalArgumentException, NullPointerException{
		this(position,radius,velocity,300000,new Random());
	}
	
	/**
	 * Terminate this asteroid and create two new smaller asteroids.
	 * 
	 * @effect	Two new asteroids are created, moving in opposite random directions.
	 * 			| if(fuzzyLessThanOrEqualTo(30, getRadius()) && this.hasProperWorld() && !isTerminated())
	 * 			|		velocity1.equals(velocity2.multiply(-1))
	 * 			|		fuzzyEquals(velocity1.getNorm(),this.getVelocity().getNorm()*1.5)
	 * 			|		position1.minus(this.getPosition()).getDirection()
	 * 			|			.equals(velocity1.getDirection())
	 * 			|		fuzzyEquals(this.getPosition().minus(position1).getNorm(),
	 * 			|			this.getPosition().minus(position2).getNorm())
	 * 			| 		position1.minus(position2).getNorm() == this.getRadius()
	 * 			|		newRadius = this.getRadius()/2
	 * 			|		asteroid1 = new Asteroid(position1, newRadius, velocity1, new Random())
	 * 			|		asteroid2 = new Asteroid(position2, newRadius, velocity2, new Random())
	 * 			|		this.getWorld().addAsSpatialElement(asteroid1);
	 *			|		this.getWorld().addAsSpatialElement(asteroid2);
	 * @effect	The asteroid is terminated as a spatial element.
	 * 		    | super.terminate();
	 */
	public void terminate(){
		if(fuzzyLessThanOrEqualTo(30, getRadius()) && this.hasProperWorld() && !isTerminated()){
			double newRandomAngle = random.nextDouble()*2*Math.PI;
			Vector2D randomDirection = new Vector2D(Math.cos(newRandomAngle),
					Math.sin(newRandomAngle));
			double radiusChild = getRadius()/2;
			Vector2D positionChild1 = getPosition().add(randomDirection.multiply(radiusChild));
			Vector2D positionChild2 = getPosition().add(randomDirection.multiply(-radiusChild));
			Vector2D velocityChild1 = randomDirection.multiply(getVelocity().getNorm()*1.5);
			World temp = getWorld();
			super.terminate();
			SpatialElement childAsteroid1 = new Asteroid(positionChild1, radiusChild, velocityChild1,new Random());
			SpatialElement childAsteroid2 = new Asteroid(positionChild2, radiusChild, velocityChild1.multiply(-1),new Random());
			temp.addAsSpatialElement(childAsteroid1);
			temp.addAsSpatialElement(childAsteroid2);
		}
		else {
			super.terminate();
		}
	}
	
	/**
	 * Terminate this asteroid.
	 * 
	 * @effect	This asteroid is terminated as a spatial element.
	 * 		    | super.terminate();
	 */
	public void forceTerminate() {
		super.terminate();
	}
	
	
	/** 
	 * Get the mass density of this asteroid.
	 */
	public static double getMassDensity(){
		return massDensity;
	}
	
	/** 
	 * The mass density of this asteroid.
	 */
	private static final double massDensity = 2.65*Math.pow(10,12);
	
	/**
	 * Get the random object of this class.
	 */
	@Basic
	public Random getRandom(){
		return this.random;
	}
	
	/**
	 * Set the given Random object as the Random object of this class.
	 * 
	 * @param 	random
	 * 			The given Random object to set.
	 * @post	...
	 * 			| (new this).getRandom() == random;
	 */
	@Basic
	public void setRandom(Random random){
		this.random = random;
	}
	/**
	 * The random generator associated with this asteroid.
	 */
	private Random random;
}

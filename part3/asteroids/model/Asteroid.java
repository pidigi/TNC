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
		this.setRandom(random);
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
	public Asteroid(Vector2D position, double radius,Vector2D velocity, Random random) 
			throws IllegalArgumentException, NullPointerException{
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
	@Override
	public void collide() throws IllegalArgumentException{
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
	 * @throws	NullPointerException
	 * 			...
	 * 			| (random == null)
	 */
	@Basic
	public void setRandom(Random random) throws NullPointerException{
		if (random == null)
			throw new NullPointerException("Given random generator is non-effective while constructing asteroid.");
		this.random = random;
	}
	
	/**
	 * The random generator associated with this asteroid.
	 */
	private Random random;
	
	/**
	 * Resolve a collision of this asteroid and another element.
	 * 
	 * @param	otherElement
	 * 			Element to resolve the collision with.
	 * @effect	If the other element is a ship, terminate it.
	 * 			| if(otherElement.isShip())
	 * 			|	then otherElement.terminate()
	 * @effect	If the other element is a bullet, terminate both.
	 * 			| if(otherElement.isBullet())
	 * 			|	then otherElement.terminate()
	 * 			|		 this.terminate()
	 * @effect	If the other element is an asteroid, resolve by bouncing.
	 * 			| if(otherElement.isAsteroid())
	 * 			|	then resolveBounce(otherElement)
	 * @effect	If the other element is not a ship, bullet or asteroid, let it resolve this ship.
	 * 			| if(!otherElement.isShip() && !otherElement.isBullet() && !otherElement.isAsteroid())
	 * 			|	then otherElement.resolve(this)
	 * @throws	NullPointerException
	 * 			The other element is not effective.
	 * 			| otherElement == null
	 */
	@Override
	public void resolve(SpatialElement otherElement) throws NullPointerException{
		if(!isValidObjectCollision(otherElement))
			throw new IllegalArgumentException("Element cannot be resolved.");
		if(otherElement.isAsteroid())
			resolveBounce(otherElement);
		else if(otherElement.isBullet()){
			otherElement.collide();
			this.collide();
		}
		else if(otherElement.isShip()){
			otherElement.collide();
		} else
			otherElement.resolve(this);
	}
	
	/**
	 * Check whether this is a Asteroid object.
	 * 
	 * @return	True
	 * 			| result == true
	 */
	@Override
	public boolean isAsteroid() {
		return true;
	}
	
	/**
	 * Check if the collision between the given spatial element 1 and 
	 * spatial element 2 is a valid object collision.
	 * 
	 * @return	...
	 * 			| if(element1 == element2)
	 * 			| then result == false
	 * @return	...
	 * 			| if((element1.getWorld() != null) && (element1.getWorld() != null) && 
	 *			| (element1.getWorld() != element2.getWorld()))
	 *			| then result == false
	 * @return	...
	 * 			| result == !(((element1.isBullet() && element2.isShip())
	 *			| && (element1.getShip() == element2)
	 *			| || ((element2.isBullet() && element1.isShip())
	 *			| && element2).getShip() == element1))
	 */
	@Override
	public boolean isValidObjectCollision(SpatialElement element){
		if(element.isAsteroid())
			return super.isValidObjectCollision(element);
		return element.isValidObjectCollision(this);
	}
	
	
	// TODO doc
	public void resolveInitialCondition(SpatialElement overlappingElement) {
		if(!isValidObjectCollision(overlappingElement))
			throw new IllegalArgumentException("Element cannot be resolved.");
		if(!overlappingElement.isShip() && !overlappingElement.isAsteroid())
			overlappingElement.resolveInitialCondition(this);
	}
	
}

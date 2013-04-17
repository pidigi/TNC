package asteroids.test;

import java.util.*;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.*;
import static asteroids.Util.*;
import asteroids.model.*;

public class AsteroidTest {

	private static Asteroid standardAsteroid;
	private static Random standardRandom;
	private static World standardWorld;
	
	/**
	 * Set up an immutable test fixture
	 * 
	 * @post 	The variable standard ship references a new ship at the origin with zero velocity,
	 * 			an angle of 0, radius of 10 and maximum velocity of 300000.
	 */
	@BeforeClass
	public static void setUpImmutableFixture() throws Exception{
		standardRandom = new Random();
		standardAsteroid = new Asteroid(new Vector2D(0,0),40,new Vector2D(0,0),300000, standardRandom);
		standardWorld = new World(1000,1000);
		standardWorld.addAsSpatialElement(standardAsteroid);
		standardAsteroid.setWorld(standardWorld);
	}
	
	@Test
	public final void constructor1_NormalCase() throws Exception{
		Asteroid newAsteroid= new Asteroid(new Vector2D(50,100),15,new Vector2D(2000,10000),300000,new Random());
		assertEquals(50, newAsteroid.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newAsteroid.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newAsteroid.getRadius(),EPSILON);
		assertEquals(2000, newAsteroid.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newAsteroid.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newAsteroid.getMaxSpeed(),EPSILON);
		assertEquals(4/3*Math.PI*Math.pow(15,3)*Asteroid.getMassDensity(), newAsteroid.getMass(),EPSILON);
	}
	
	@Test
	public final void constructor2_NormalCase() throws Exception{
		Asteroid newAsteroid = new Asteroid(new Vector2D(50,100),15,new Vector2D(2000,10000),new Random());
		assertEquals(50, newAsteroid.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newAsteroid.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newAsteroid.getRadius(),EPSILON);
		assertEquals(2000, newAsteroid.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newAsteroid.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newAsteroid.getMaxSpeed(),EPSILON);
		assertEquals(4/3*Math.PI*Math.pow(15,3)*Asteroid.getMassDensity(), newAsteroid.getMass(),EPSILON);
		
	}
	
	// Since all constructors have the same effect as the most extended constructor,
	// only the latter will be tested for all exceptional cases.
	
	@Test
	public final void terminate_NormalCase(){
		standardAsteroid.terminate();
		assertTrue(standardAsteroid.isTerminated());
		Set<Asteroid> asteroids = standardWorld.getAsteroids();
		for(Asteroid asteroid:asteroids) {
//			assertEquals(asteroid.getVelocity().getDirection().getXComponent(),Direction.getXComponent(),EPSILON);
//			assertEquals(asteroid.getVelocity().getDirection().getYComponent(),Direction.getYComponent(),EPSILON);
//			assertTrue(( (asteroid.getPosition().subtract(standardAsteroid.getPosition())).getDirection().getXComponent() == Direction.getXComponent())
//					|| ( (asteroid.getPosition().subtract(standardAsteroid.getPosition())).getDirection().getXComponent() == -1*Direction.getXComponent()));
//			assertTrue(( (asteroid.getPosition().subtract(standardAsteroid.getPosition())).getDirection().getYComponent() == Direction.getYComponent())
//					|| ( (asteroid.getPosition().subtract(standardAsteroid.getPosition())).getDirection().getYComponent() == -1*Direction.getYComponent()));
			assertEquals((asteroid.getPosition().subtract(standardAsteroid.getPosition())).getNorm(),standardAsteroid.getRadius()/2,EPSILON);
			assertEquals(asteroid.getVelocity().getNorm(),standardAsteroid.getVelocity().getNorm()*1.5,EPSILON);
			assertEquals(asteroid.getRadius(),standardAsteroid.getRadius()/2,EPSILON);
		}
	}
}

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
	 * Set up an mutable test fixture
	 * 
	 * @post	The variable standardRandom references a new Random object.
	 * @post 	The variable standardAsteroid references a new asteroid at (50,50) with zero velocity,
	 * 			radius of 40, and maximum velocity of 300000 and standardRandom as random object.
	 * @post	The variable standardWorld references a new World with dimensions (1000,1000)
	 * @effect 	standardWorld.addAsSpatialElement(standardAsteroid)
	 */
	@Before
	public void setUpMutableFixture() throws Exception{
		standardRandom = new Random();
		standardAsteroid = new Asteroid(new Vector2D(50,50),40,new Vector2D(0,0),300000, standardRandom);
		standardWorld = new World(1000,1000);
		standardWorld.addAsSpatialElement(standardAsteroid);
	}
	
	@Test
	public final void constructor1_NormalCase() throws Exception{
		Random rand = new Random();
		Asteroid newAsteroid= new Asteroid(new Vector2D(50,100),15,new Vector2D(2000,10000),300000,rand);
		assertEquals(50, newAsteroid.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newAsteroid.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newAsteroid.getRadius(),EPSILON);
		assertEquals(2000, newAsteroid.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newAsteroid.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newAsteroid.getMaxSpeed(),EPSILON);
		assertEquals(4/3*Math.PI*Math.pow(15,3)*Asteroid.getMassDensity(), newAsteroid.getMass(),EPSILON);
		assertTrue(rand == newAsteroid.getRandom());
	}
	
	@Test
	public final void constructor2_NormalCase() throws Exception{
		Random rand = new Random();
		Asteroid newAsteroid = new Asteroid(new Vector2D(50,100),15,new Vector2D(2000,10000),rand);
		assertEquals(50, newAsteroid.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newAsteroid.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newAsteroid.getRadius(),EPSILON);
		assertEquals(2000, newAsteroid.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newAsteroid.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newAsteroid.getMaxSpeed(),EPSILON);
		assertEquals(4/3*Math.PI*Math.pow(15,3)*Asteroid.getMassDensity(), newAsteroid.getMass(),EPSILON);
		assertTrue(rand == newAsteroid.getRandom());
	}
	
	@Test
	public final void constructor3_NormalCase() throws Exception{
		Asteroid newAsteroid = new Asteroid(new Vector2D(50,100),15,new Vector2D(2000,10000));
		assertEquals(50, newAsteroid.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newAsteroid.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newAsteroid.getRadius(),EPSILON);
		assertEquals(2000, newAsteroid.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newAsteroid.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newAsteroid.getMaxSpeed(),EPSILON);
	}
	
	// Since all constructors have the same effect as the constructor of the super class,
	// only the latter will be tested for all exceptional cases.
	
	
	@Test
	public final void forceTerminate_SingleCase(){
		standardAsteroid.forceTerminate();
		assertTrue(standardAsteroid.isTerminated());
	}
	
	@Test
	// TODO check velocity direction of asteroids
	public final void terminate_NormalCase(){
		standardAsteroid.terminate();
		assertTrue(standardAsteroid.isTerminated());
		assertFalse(standardWorld.hasAsSpatialElement(standardAsteroid));
		Set<Asteroid> asteroids = standardWorld.getAsteroids();
		for(Asteroid asteroid:asteroids) {
			assertEquals((asteroid.getPosition().subtract(standardAsteroid.getPosition())).getNorm(),standardAsteroid.getRadius()/2,EPSILON);
			assertEquals(asteroid.getVelocity().getNorm(),standardAsteroid.getVelocity().getNorm()*1.5,EPSILON);
			assertEquals(asteroid.getRadius(),standardAsteroid.getRadius()/2,EPSILON);
		}
	}
	
	@Test
	public final void terminate_TooSmall(){
		Asteroid smallAsteroid = new Asteroid(new Vector2D(200,200),20,new Vector2D(0,0),300000, standardRandom);
		standardWorld.addAsSpatialElement(smallAsteroid);
		smallAsteroid.terminate();
		assertFalse(standardWorld.hasAsSpatialElement(smallAsteroid));
		for(SpatialElement asteroid: standardWorld.getAsteroids())
			assertTrue(asteroid == standardAsteroid);
		//Last test indicates that this is the only asteroid object in the world
		// (no child asteroids were added)
	}
	
	@Test
	public final void terminate_AlreadyTerminated(){
		standardAsteroid.terminate();
		assertTrue(standardAsteroid.isTerminated());
		standardAsteroid.terminate();
		assertTrue(standardWorld.getAsteroids().size() == 2);
		// No extra child asteroids were made after the first terminate.
	}
	
	// for the case on hasProperWorld() see spatial element test.
	
	
}

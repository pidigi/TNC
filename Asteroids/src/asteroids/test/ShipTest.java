package asteroids.test;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

import static asteroids.Util.*;
import asteroids.model.*;

public class ShipTest {

	private static Ship standardShip;
	
	/**
	 * Set up an immutable test fixture
	 * 
	 * @post 	The variable standard ship references a new ship at the origin with zero velocity,
	 * 			an angle of 0, radius of 10 and maximum velocity of 300000.
	 */
	@BeforeClass
	public static void setUpImmutableFixture() throws Exception{
		standardShip = new Ship(new Vector2D(0,0),0,10,new Vector2D(0,0),300000);
	}
	
	private static Ship ship100, ship100PiD4, shipWorld;
	
	/**
	 * Set up a mutable test fixture
	 * 
	 * @post 	The variable ship100 references a new ship at (100,0) with 
	 * 			a velocity of -10 in the x-direction and zero in the y-direction,
	 * 			an angle of 0, radius of 10, a maximum velocity of 300000
	 * 			and a mass of 1E5.
	 * @post 	The variable ship100PiD4 references a new ship at (100,0) with 
	 * 			a velocity of zero, an angle of PI/4, a radius of 10, a maximum velocity of 300000
	 * 			and a mass of 1E5.
	 * @post 	The variable shipWorld references a new ship at (100,100) with 
	 * 			a velocity of zero, an angle of PI/4, a radius of 10, a maximum velocity of 300000
	 * 			and a mass of 1E5.
	 * @post	The variable newWorld references a new World with dimensions (1000,1000)
	 * @effect	newWorld.addAsSpatialElement(shipWorld)
	 */
	@Before
	public void setUpMutableFixture() throws Exception{
		ship100 = new Ship(new Vector2D(100,0),0,10,new Vector2D(-10,0),300000,1E5);
		ship100PiD4 = new Ship(new Vector2D(100,0),Math.PI/4,10,new Vector2D(0,0),300000,1E5);
		shipWorld = new Ship(new Vector2D(100,100),Math.PI/4,10,new Vector2D(0,0),300000,1E5);
		World newWorld = new World(1000,1000);
		newWorld.addAsSpatialElement(shipWorld);
	}
	
	@Test
	public final void constructor1_NormalCase() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),300000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor2_NormalCase() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor3_NormalCase() throws Exception{
		Ship newShip = new Ship();
		assertEquals(0, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(0, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(0, newShip.getAngle(),EPSILON);
		assertEquals(10, newShip.getRadius(),EPSILON);
		assertEquals(0, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	// Since all constructors have the same effect as the most extended constructor,
	// only the latter will be tested for all exceptional cases.

	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,Double.NaN,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooLowRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,-5,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooHighRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,Double.POSITIVE_INFINITY,new Vector2D(2000,10000),300000,1E5);
	}
			
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNPosition() throws Exception{
		new Ship(new Vector2D(Double.NaN,100),Math.PI/2,15,new Vector2D(2000,10000),300000,1E5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NullPosition() throws Exception{
		new Ship(null,Math.PI/2,15,new Vector2D(2000,10000),300000);
	}
	
	@Test
	public final void constructor_NaNMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),Double.NaN,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_TooLowMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),-50,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_TooHighMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),400000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_NaNVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(Double.NaN,10000),300000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(0, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_NullVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,null,300000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(0, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test
	public final void constructor_TooHighVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(300000,300000),300000,1E5);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(300000/Math.sqrt(2), newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(300000/Math.sqrt(2), newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
		assertEquals(1E5, newShip.getMass(),EPSILON);
		assertFalse(newShip.isThrusterActive());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooLowMass() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(300000,300000),300000,-10);
	}
	
	// Checkers are tested because they are public methods, 
	// although you could say that they already have been tested through 
	// the tests for the constructor.
	
	@Test
	public final void isValidAngle_TrueCase() {
		assertTrue(ship100.isValidAngle(Math.PI));
	}
	
	@Test
	public final void isValidAngle_FalseCaseNaN(){
		assertFalse(ship100.isValidAngle(Double.NaN));
	}
	
	@Test
	public final void isValidAngle_FalseCaseOutOfRange1(){
		assertFalse(ship100.isValidAngle(-Math.PI));
	}
	
	@Test
	public final void isValidAngle_FalseCaseOutOfRange2(){
		assertFalse(ship100.isValidAngle(2*Math.PI));
	}
		
	@Test
	public final void turn_NormalCase(){
		ship100.turn(Math.PI/4);
		assertEquals(Math.PI/4,ship100.getAngle(),EPSILON);
	}
	
	@Test
	public final void thrust_NormalCase(){
		ship100PiD4.thrust(100);
		assertEquals(100*Math.cos(Math.PI/4),ship100PiD4.getVelocity().getXComponent(),EPSILON);
		assertEquals(100*Math.sin(Math.PI/4),ship100PiD4.getVelocity().getYComponent(),EPSILON);
	}
	
	@Test
	public final void thrust_NegativeAcceleration(){
		ship100PiD4.thrust(-100);
		assertEquals(0,ship100PiD4.getVelocity().getXComponent(),EPSILON);
		assertEquals(0,ship100PiD4.getVelocity().getYComponent(),EPSILON);
	}
	
	@Test
	public final void thrust_NaNAcceleration(){
		ship100PiD4.thrust(Double.NaN);
		assertEquals(0,ship100PiD4.getVelocity().getXComponent(),EPSILON);
		assertEquals(0,ship100PiD4.getVelocity().getYComponent(),EPSILON);
	}
	
	public final void setThrusterActive_SingleCase(){
		standardShip.setThrusterActive(true);
		assertTrue(standardShip.isThrusterActive());
	}
	
	@Test
	public final void fireBullet_NormalCase() throws Exception {
		shipWorld.fireBullet();
		Set<Bullet> bullets = shipWorld.getWorld().getBullets();
		assertTrue(bullets.size() == 1);
		for (Bullet bullet: bullets) {
			assertEquals(bullet.getPosition().subtract(shipWorld.getPosition()).getDirection().getXComponent(), 
					Math.cos(shipWorld.getAngle()),EPSILON);
			assertEquals(bullet.getPosition().subtract(shipWorld.getPosition()).getDirection().getYComponent(), 
					Math.sin(shipWorld.getAngle()),EPSILON);
			assertEquals(bullet.getPosition().subtract(shipWorld.getPosition()).getNorm(), 
					shipWorld.getRadius(),EPSILON);
			assertEquals(bullet.getRadius(),3,EPSILON);
			assertEquals(bullet.getVelocity().getNorm(),250,EPSILON);
			assertEquals(bullet.getVelocity().getDirection().getXComponent(),
					Math.cos(shipWorld.getAngle()),EPSILON);
			assertEquals(bullet.getVelocity().getDirection().getYComponent(),
					Math.sin(shipWorld.getAngle()),EPSILON);
			assertEquals(bullet.getMaxSpeed(),300000,EPSILON);
			assertEquals(bullet.getMass(),4/3*Math.PI*Math.pow(3,3)*Bullet.getMassDensity(),EPSILON);
			assertTrue(bullet.getShip() == shipWorld);
			assertTrue(shipWorld.getWorld().hasAsSpatialElement(bullet));
		}
	}
}

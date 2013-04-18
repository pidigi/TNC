package asteroids.test;

import static org.junit.Assert.*;
import org.junit.*;
import static asteroids.Util.*;
import asteroids.model.*;

public class BulletTest {

	private static Bullet standardBullet;
	private static Ship standardShip;
	
	/**
	 * Set up an mutable test fixture
	 * 
	 * @post 	The variable standard ship references a new ship at the origin with zero velocity,
	 * 			an angle of 0, radius of 10 and maximum velocity of 300000.
	 */
	@Before
	public void setUpMutableFixture() throws Exception{
		standardShip = new Ship();
		standardBullet = new Bullet(new Vector2D(10,10),10,new Vector2D(0,0),300000,standardShip);
		World newWorld = new World(1000,1000);
		newWorld.addAsSpatialElement(standardBullet);
	}
	
	@Test
	public final void constructor_NormalCase() throws Exception{
		Ship newShip = new Ship();
		Bullet newBullet = new Bullet(new Vector2D(50,100),15,new Vector2D(2000,10000),300000,newShip);
		assertEquals(50, newBullet.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newBullet.getPosition().getYComponent(),EPSILON);
		assertEquals(15, newBullet.getRadius(),EPSILON);
		assertEquals(2000, newBullet.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newBullet.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newBullet.getMaxSpeed(),EPSILON);
		assertEquals(4/3*Math.PI*Math.pow(15,3)*Bullet.getMassDensity(), newBullet.getMass(),EPSILON);
		assertTrue(newBullet.getShip() == newShip);
		assertFalse(newBullet.getHasBounced());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NonEffectiveShip(){
		new Bullet(new Vector2D(50,100),15,new Vector2D(2000,10000),300000,null);
	}
	
	// Since all constructors have the same effect as the constructor of the super class,
	// only the latter will be tested for all exceptional cases.
	
	@Test
	public final void canHaveAsShip_TrueCase() {
		assertTrue(standardBullet.canHaveAsShip(standardShip));
	}
	
	@Test
	public final void canHaveAsShip_FalseCase() {
		assertFalse(standardBullet.canHaveAsShip(null));
	}
	
	@Test
	public final void bounce_SingleCase() {
		standardBullet.bounce();
		assertTrue(standardBullet.getHasBounced());
	}
}
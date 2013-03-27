package asteroids.test;

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
	
	private Ship ship100, ship100PiD4;
	
	/**
	 * Set up a mutable test fixture
	 * 
	 * @post 	The variable ship100 references a new ship at (100,0) with 
	 * 			a velocity of -10 in the x-direction and zero in the y-direction,
	 * 			an angle of 0, radius of 10 and maximum velocity of 300000.
	 * @post 	The variable ship100PiD4 references a new ship at (100,0) with 
	 * 			a velocity of zero, an angle of PI/4, a radius of 10 and maximum velocity of 300000.
	 */
	@Before
	public void setUpMutableFixture() throws Exception{
		ship100 = new Ship(new Vector2D(100,0),0,10,new Vector2D(-10,0),300000);
		ship100PiD4 = new Ship(new Vector2D(100,0),Math.PI/4,10,new Vector2D(0,0),300000);
	}
	
	@Test
	public final void constructor1_NormalCase() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),300000);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor2_NormalCase() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000));
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
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
	}
	
	// Since all constructors have the same effect as the most extended constructor,
	// only the latter will be tested for all exceptional cases.
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,Double.NaN,new Vector2D(2000,10000),300000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooLowRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,5,new Vector2D(2000,10000),300000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_TooHighRadius() throws Exception{
		new Ship(new Vector2D(50,100),Math.PI/2,Double.POSITIVE_INFINITY,new Vector2D(2000,10000),300000);
	}
			
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NaNPosition() throws Exception{
		new Ship(new Vector2D(Double.NaN,100),Math.PI/2,15,new Vector2D(2000,10000),300000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructor_NullPosition() throws Exception{
		new Ship(null,Math.PI/2,15,new Vector2D(2000,10000),300000);
	}
	
	@Test
	public final void constructor_NaNMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),Double.NaN);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_TooLowMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),-50);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_TooHighMaxSpeed() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(2000,10000),400000);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(2000, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(10000, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_NaNVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(Double.NaN,10000),300000);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(0, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_NullVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,null,300000);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(0, newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(0, newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
	}
	
	@Test
	public final void constructor_TooHighVelocity() throws Exception{
		Ship newShip = new Ship(new Vector2D(50,100),Math.PI/2,15,new Vector2D(300000,300000),300000);
		assertEquals(50, newShip.getPosition().getXComponent(),EPSILON);
		assertEquals(100, newShip.getPosition().getYComponent(),EPSILON);
		assertEquals(Math.PI/2, newShip.getAngle(),EPSILON);
		assertEquals(15, newShip.getRadius(),EPSILON);
		assertEquals(300000/Math.sqrt(2), newShip.getVelocity().getXComponent(),EPSILON);
		assertEquals(300000/Math.sqrt(2), newShip.getVelocity().getYComponent(),EPSILON);
		assertEquals(300000, newShip.getMaxSpeed(),EPSILON);
	}
	
	// Checkers are tested because they are public methods, 
	// although you could say that they already have been tested through 
	// the tests for the constructor.
	
	@Test
	public final void isValidPosition_TrueCase(){
		assertTrue(ship100.isValidPosition(new Vector2D(5,10)));
	}
	
	@Test
	public final void isValidPosition_FalseCaseNull(){
		assertFalse(ship100.isValidPosition(null));
	}
	
	@Test
	public final void isValidPosition_FalseCaseNaN(){
		assertFalse(ship100.isValidPosition(new Vector2D(Double.NaN,10)));
	}	
	
	@Test
	public final void move_NormalCase() throws Exception{
		ship100.move(1);
		assertEquals(90,ship100.getPosition().getXComponent(),EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_NegativeTime() throws Exception{
		ship100.move(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_InfTime() throws Exception{
		ship100.move(Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void move_NaNTime() throws Exception{
		ship100.move(Double.NaN);
	}
	
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
	
	@Test
	public final void getDistanceBetween_NormalCase() throws Exception{
		double newDistance = ship100.getDistanceBetween(standardShip);
		assertEquals(80,newDistance,EPSILON);
	}
	
	@Test
	public final void getDistanceBetween_SameShip() throws Exception{
		double newDistance = standardShip.getDistanceBetween(standardShip);
		assertEquals(0,newDistance,EPSILON);
	}
	
	@Test
	public final void getDistanceBetween_CompleteOverlap() throws Exception{
		double newDistance = ship100.getDistanceBetween(ship100PiD4);
		assertEquals(-20,newDistance,EPSILON);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getDistanceBetween_NullShip() throws Exception{
		ship100.getDistanceBetween(null);
	}
	
	@Test
	public final void overlap_TrueCase() throws Exception{
		ship100.overlap(ship100PiD4);
	}
	
	@Test
	public final void overlap_FalseCase() throws Exception{
		ship100.overlap(standardShip);
	}
	
	@Test
	public final void overlap_TrueCaseSameShip() throws Exception{
		ship100.overlap(ship100);
	}
	
	@Test(expected = NullPointerException.class)
	public final void overlap_NullCase() throws Exception{
		ship100.overlap(null);
	}
	
	@Test
	public final void getTimeToCollision_NormalCase() throws Exception{
		double newCollisionTime = ship100.getTimeToCollision(standardShip);
		assertEquals(8,newCollisionTime,EPSILON);
	}
	
	@Test
	public final void getTimeToCollision_NoCollisionCase() throws Exception{
		double newCollisionTime = ship100PiD4.getTimeToCollision(standardShip);
		assertTrue(newCollisionTime == Double.POSITIVE_INFINITY);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getTimeToCollision_NullCase()throws Exception{
		ship100PiD4.getTimeToCollision(null);
	}
	
	@Test
	public final void getCollisionPosition_NormalCase(){
		Vector2D newCollisionPosition = ship100.getCollisionPosition(standardShip);
		assertEquals(10,newCollisionPosition.getXComponent(),EPSILON);
		assertEquals(0,newCollisionPosition.getYComponent(),EPSILON);
	}
	
	@Test
	public final void getCollisionPosition_NoCollision(){
		Vector2D newCollisionPosition = ship100PiD4.getCollisionPosition(standardShip);
		assertTrue(newCollisionPosition == null);
	}
	
	@Test(expected = NullPointerException.class)
	public final void getCollisionPosition_NullCase(){
		ship100PiD4.getCollisionPosition(null);
	}
	
	@Test
	public final void isValidRadius_TrueCase(){
		assertTrue(ship100.isValidRadius(20));
	}
	
	@Test
	public final void isValidRadius_FalseCaseNaN(){
		assertFalse(ship100.isValidRadius(Double.NaN));
	}
	
	@Test
	public final void isValidRadius_FalseCaseTooLow(){
		assertFalse(ship100.isValidRadius(9.99));
	}
	
	@Test
	public final void isValidRadius_FalseCaseInf(){
		assertFalse(ship100.isValidRadius(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public final void isValidTime_TrueCase(){
		assertTrue(Ship.isValidTime(8.0));
	}
	
	@Test
	public final void isValidTime_FalseCaseNaN(){
		assertFalse(Ship.isValidTime(Double.NaN));
	}
	
	@Test
	public final void isValidTime_FalseCaseNegative(){
		assertFalse(Ship.isValidTime(-8.0));
	}
}
